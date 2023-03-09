import com.google.gson.Gson;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class Api {

    static final String baseUrl = "https://api.mangadex.org";

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

        Response json = gson.fromJson(response.body(), Response.class);
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
            json = gson.fromJson(response.body(), Response.class);
            mangas.addAll(List.of(json.data));

        }

        return mangas.toArray(new Manga[0]);
    }



}
