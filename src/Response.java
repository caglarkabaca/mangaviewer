public class Response {
    int limit;
    int offset;
    int total;
}

class MResponse extends Response {
    Manga[] data;
}

class CResponse extends Response {
    Chapter[] data;
}

class IResponse {
    String baseUrl;
    CData chapter;
}

class CData {
    String hash;
    String[] data;
    String[] dataSaver;
}

class Manga {
    String id;
    MangaAttributes attributes;
}

class Chapter {
    String id;
    ChapterAttributes attributes;
}

class MangaAttributes {
    Title title;
    Description description;
    Name name;
    Tag[] tags;
}

class ChapterAttributes {
    String volume;
    String chapter;
    String title;
    String translatedLanguage;
    int pages;
}

class Title {
    String en;
}

class Description {
    String en;
}

class Tag {
    MangaAttributes attributes;
}

class Name { // FOR TAGS
    String en;
}
