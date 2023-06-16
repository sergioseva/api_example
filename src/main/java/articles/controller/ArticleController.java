package articles.controller;

import articles.model.Article;
import articles.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Complete this
@RestController
@RequestMapping(value = "articles")
public class ArticleController {

    private ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping
    public @ResponseBody List<Article> findAllArticles() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody Article findArticleById(@PathVariable("id") int id) {
        Article article = service.findById(id);
        return article;
    }

    @PostMapping
    public @ResponseBody Article saveArticle(@Valid @RequestBody Article article) {
        return service.save(article);
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody Article updateArticle(@Valid @PathVariable("id") int id, @RequestBody Article updatedArticle) {
        Article article = service.findById(id);
        return service.update(id, updatedArticle);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteArticle(@PathVariable("id") int id) {
        Article article = service.findById(id);
        service.delete(id);
    }
}