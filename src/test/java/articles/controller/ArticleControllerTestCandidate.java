package articles.controller;

import articles.dto.ArticleDto;
import articles.model.Article;
import articles.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static articles.constants.ArticleType.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: Add more tests
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTestCandidate {

    private List<Article> articles;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        Article article1 = new Article(1, randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Article article2 = new Article(2, randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
        Article article3 = new Article(3, randomAlphabetic(10), randomAlphabetic(200), LITERATURE_REVIEW, newArrayList());
        articles = newArrayList(article1, article2, article3);
        ArticleService.articles = articles;
    }

    @Test
    public void shouldRetrieveAllArticles() throws Exception {
        this.mockMvc.perform(get("/articles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(articles.size())));
    }

    @Test
    public void shouldRetrieveArticleById() throws Exception {
        int articleId = 1;
        Article article = articles.get(articleId - 1);

        this.mockMvc.perform(get("/articles/{id}", articleId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(article.getId())))
                .andExpect(jsonPath("$.title", is(article.getTitle())))
                .andExpect(jsonPath("$.body", is(article.getBody())))
                .andExpect(jsonPath("$.type", is(article.getType().toString())));
    }

    @Test
    public void shouldReturnNotFoundForNonExistingArticle() throws Exception {
        int articleId = 999;

        this.mockMvc.perform(get("/articles/{id}", articleId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewArticle() throws Exception {
        ArticleDto articleDto = new ArticleDto(randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY);
        String requestBody = objectMapper.writeValueAsString(articleDto);

        this.mockMvc.perform(post("/articles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.title", is(articleDto.getTitle())))
                .andExpect(jsonPath("$.body", is(articleDto.getBody())))
                .andExpect(jsonPath("$.type", is(articleDto.getType().toString())));
    }

    @Test
    public void shouldUpdateArticle() throws Exception {
        int articleId = 1;
        Article existingArticle = articles.get(articleId - 1);
        ArticleDto updatedArticleDto = new ArticleDto(existingArticle.getTitle(), existingArticle.getBody() + " updated", existingArticle.getType());
        String requestBody = objectMapper.writeValueAsString(updatedArticleDto);

        this.mockMvc.perform(put("/articles/{id}", articleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingArticle.getId())))
                .andExpect(jsonPath("$.title", is(existingArticle.getTitle())))
                .andExpect(jsonPath("$.body", is(updatedArticleDto.getBody())))
                .andExpect(jsonPath("$.type", is(existingArticle.getType().toString())));
    }

    @Test
    public void shouldDeleteArticle() throws Exception {
        int articleId = 1;

        this.mockMvc.perform(delete("/articles/{id}", articleId))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/articles/{id}", articleId))
                .andExpect(status().isNotFound());
    }
}
