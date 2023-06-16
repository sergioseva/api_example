package articles.model;

import articles.constants.ArticleType;

import java.util.List;

// TODO: Complete this
public class Article implements Comparable<Article>{
    private int id;
    private String title;
    private String body;
    private ArticleType type;
    private List<Comment> comments;

    public Article() {}

    public Article(int id, String title, String body, ArticleType type, List<Comment> comments ) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.type = type;
        this.comments = comments;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }

    @Override
    public int compareTo(Article a) {
        if (this.getId()>a.getId()) {
            return 1;
        }else if (this.getId()<a.getId()) {
            return -1;
        }else {
            return 0;
        }
    }
}


