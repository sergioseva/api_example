package articles.service;

import articles.exception.NotFoundException;
import articles.model.Article;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static articles.constants.ArticleType.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

// TODO: Add more tests
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleServiceTestCandidate {

    private ArticleService service = new ArticleService();
    private List<Article> articles;

    @Before
    public void setUp() {
        Article article1 = new Article(1,randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Article article2 = new Article(2,randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
        Article article3 = new Article(3,randomAlphabetic(10), randomAlphabetic(200), LITERATURE_REVIEW, newArrayList());
        Article article4 = new Article(4,randomAlphabetic(10), randomAlphabetic(200), METHODOLOGIC, newArrayList());
        Article article5 = new Article(5,randomAlphabetic(10), randomAlphabetic(200), THEORIC, newArrayList());
        Article article6 = new Article(6,randomAlphabetic(10), randomAlphabetic(200), THEORIC, newArrayList());
        articles = newArrayList(article1, article2, article3, article4, article5, article6);
        ArticleService.articles = articles;
    }

    @Test
    public void shouldFindAllArticles() {
        List<Article> articlesResultList = service.findAll();

        assertThat(articlesResultList).hasSize(articles.size()).isEqualTo(articles);
    }
    @Test
    public void shouldFindArticleById() {
        int articleId = 2;
        Article expectedArticle = articles.get(articleId - 1);

        Article article = service.findById(articleId);

        assertThat(article).isEqualTo(expectedArticle);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenArticleIdNotFound() {
        int articleId = 999;

        assertThrows(NotFoundException.class, () -> {
            service.findById(articleId);
        });
    }

    @Test
    public void shouldSaveNewArticle() {
        Article newArticle = new Article(20, "New Title", "New Body", METHODOLOGIC, newArrayList());

        Article savedArticle = service.save(newArticle);

        assertThat(savedArticle).isEqualTo(newArticle);
        assertThat(articles).contains(savedArticle);
    }

    @Test
    public void shouldUpdateExistingArticle() {
        int articleId = 1;
        Article updatedArticle = new Article(articleId, "Updated Title", "Updated Body", THEORIC, newArrayList());

        Article updated = service.update(articleId, updatedArticle);

        assertThat(updated).isEqualTo(updatedArticle);
        assertThat(service.findById(articleId)).isEqualTo(updatedArticle);
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenUpdatingNonexistentArticle() {
        int articleId = 999;
        Article updatedArticle = new Article(articleId, "Updated Title", "Updated Body", THEORIC, newArrayList());

        assertThrows(NotFoundException.class, () -> {
            service.update(articleId, updatedArticle);
        });
    }

    @Test
    public void shouldDeleteExistingArticle() {
        int articleId = 2;
        assertDoesNotThrow(() -> {service.delete(articleId);});
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenDeletingNonexistentArticle() {
        int articleId = 999;

        assertThrows(NotFoundException.class, () -> {
            service.delete(articleId);
        });
    }
}
