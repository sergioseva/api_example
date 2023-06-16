package articles.service;

import java.util.*;
import articles.exception.*;
import articles.model.Article;
import articles.repository.*;
import org.springframework.beans.*;
import org.springframework.stereotype.*;

@Service
public class ArticleService implements CrudRepository<Article> {

    public static List<Article> articles = new ArrayList<>();

    public List<Article> findAll() {
        return articles;
    }

    public Article findById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        throw new NotFoundException("Article not found with ID: " + id);
    }

    public Article save(Article article) {
        articles.add(article);
        return article;
    }

    public void delete(int id) {
        Article article = this.findById(id);
        articles.remove(article);
    }

    // For update, we can follow the approach where we first delete the existing article and then add the updated one
    public Article update(int id, Article updatedArticle) {
        // first check if the article exists, if not will trown an exception
        this.findById(id);
        delete(id);
        articles.add(updatedArticle);
        updatedArticle.setId(id);
        return updatedArticle;
    }
}