package Model;

public class Item{
    DocType type;

    public DocType getType() {
        return type;
    }

    public void setType(DocType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String title;
    String content;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Item(DocType type, String title, String content, String link) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.link = link;
    }

    String link;

}
