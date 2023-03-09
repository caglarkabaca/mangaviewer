import com.google.gson.Gson;

import java.io.*;
import java.net.*;
import java.net.http.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public final class Api {

    static final String baseUrl = "https://api.mangadex.org";
    static HashMap<String, String> downloadedImages = new HashMap<>();

    public static Manga[] getMangaList(String name) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        int limit = 100;
        int offset = 0;

        String url = baseUrl
                + String.format("/manga?limit=%d&offset=%d&title=", limit, offset)
                + URLEncoder.encode(name, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        MResponse json = gson.fromJson(response.body(), MResponse.class);
        ArrayList<Manga> mangas = new ArrayList<>(List.of(json.data));

        while (json.offset + json.limit < json.total) {
            offset += limit;
            url = baseUrl
                + String.format("/manga?limit=%d&offset=%d&title=", limit, offset)
                + URLEncoder.encode(name, StandardCharsets.UTF_8);

            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            json = gson.fromJson(response.body(), MResponse.class);
            mangas.addAll(List.of((Manga[]) json.data));

        }

        return mangas.toArray(new Manga[0]);
    }

    public static Chapter[] getChapterList(Manga manga) throws Exception{

        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        int limit = 100;
        int offset = 0;

        String url = baseUrl
                + String.format("/chapter?limit=%d&offset=%d&manga=", limit, offset)
                + URLEncoder.encode(manga.id, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        CResponse json = gson.fromJson(response.body(), CResponse.class);
        ArrayList<Chapter> chapters = new ArrayList<>(List.of(json.data));

        while (json.offset + json.limit < json.total) {
            offset += limit;
            url = baseUrl
                    + String.format("/chapter?limit=%d&offset=%d&manga=", limit, offset)
                    + URLEncoder.encode(manga.id, StandardCharsets.UTF_8);

            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            json = gson.fromJson(response.body(), CResponse.class);
            chapters.addAll(List.of(json.data));

        }

        chapters.sort(Comparator.comparing(o -> o.attributes.translatedLanguage));
        return chapters.toArray(new Chapter[0]);
    }

    public static String[] getChapterImageUrls (Chapter chapter) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        ArrayList<String> fileUrls = new ArrayList<>();
        String url = String.format("https://api.mangadex.org/at-home/server/%s", chapter.id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        IResponse json = gson.fromJson(response.body(), IResponse.class);

        for (String e : json.chapter.data) {
            fileUrls.add(String.format("%s/data/%s/%s",
                    json.baseUrl,
                    json.chapter.hash,
                    e
            ));
        }

        return fileUrls.toArray(new String[0]);
    }

    public static String downloadChapter(String imageUrl) throws Exception {

        if (downloadedImages.containsKey(imageUrl))
            return downloadedImages.get(imageUrl);

        Path temp = Files.createTempFile("imageViewer", ".png");
        try (BufferedInputStream in = new BufferedInputStream(new URL(imageUrl).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(temp.toFile())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            downloadedImages.put(imageUrl, temp.toString());
            return temp.toString();
        }
    }

    public static void clearDownloadedImages() {
        for (String url: Api.downloadedImages.values()) {
            if (!(new File(url)).delete())
                System.out.println("failed to delete " + url);
        }
    }

}
