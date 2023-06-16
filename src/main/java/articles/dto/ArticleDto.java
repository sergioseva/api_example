package articles.dto;

import articles.constants.ArticleType;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

public class ArticleDto implements Serializable {
    private int id;
    @NotNull(message = "Title cannot be null")
    private String title;
    @NotNull(message = "Body cannot be null")
    private String body;
    private ArticleType type;

    public ArticleDto(String title, String body, ArticleType type) {
        this.title = title;
        this.body = body;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public ArticleType getType() {
        return type;
    }

    public void setType(ArticleType type) {
        this.type = type;
    }
}