package articles.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// TODO: Complete this
public class Comment implements Comparable<Comment>{
    private int id;
    @NotNull(message = "article can't be null")
    private Article article;
    @NotEmpty(message = "Email can't be null/empty")
    @Email(message = "Email must be a valid e-mail")
    private String email;
    @NotEmpty(message = "Text can't be null/empty")
    private String text;

    public Comment() {

    }
    public Comment(int id, Article article, String email, String text) {
        this.id = id;
        this.article = article;
        this.email = email;
        this.text = text;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(Comment c) {
        if (this.getId()>c.getId()) {
            return 1;
        }else if (this.getId()<c.getId()) {
            return -1;
        }else {
            return 0;
        }
    }
}
