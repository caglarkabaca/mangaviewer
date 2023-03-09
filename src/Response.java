public class Response {
    Manga[] data;
    int limit;
    int offset;
    int total;
}

class Manga {
    String id;
    Attributes attributes;
}

class Attributes {
    Title title;
    Description description;
    Name name;
    Tag[] tags;
}

class Title {
    String en;
}

class Description {
    String en;
}

class Tag {
    Attributes attributes;
}

class Name { // FOR TAGS
    String en;
}
