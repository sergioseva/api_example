package articles.controller;

import articles.dto.CommentDto;
import articles.model.Article;
import articles.model.Comment;
import articles.service.ArticleService;
import articles.service.CommentService;
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

import static articles.constants.ArticleType.CASE_STUDY;
import static articles.constants.ArticleType.EMPIRICAL_STUDY;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: Add more tests
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTestCandidate {
    private List<Comment> comments;
    private List<Article> articles;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        Article article1 = new Article(1,randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Article article2 = new Article(2,randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());

        Comment comment1 = new Comment(1,article1,"test@gmail.com",randomAlphabetic(100));
        Comment comment2 = new Comment(2,article1,"test2@gmail.com",randomAlphabetic(100));
        Comment comment3 = new Comment(3,article2,"test3@gmail.com",randomAlphabetic(100));

        comments = newArrayList(comment1, comment2, comment3);
        articles = newArrayList(article1, article2);
        ArticleService.articles = articles;
        CommentService.comments = comments;
    }

    @Test
    public void shouldRetrieveAllComments() throws Exception {
        this.mockMvc.perform(get("/comments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(comments.size())));
    }

    @Test
    public void shouldRetrieveCommentById() throws Exception {
        int commentId = 1;
        Comment comment = comments.get(commentId - 1);

        this.mockMvc.perform(get("/comments/{id}", commentId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comment.getId())))
                .andExpect(jsonPath("$.email", is(comment.getEmail())))
                .andExpect(jsonPath("$.text", is(comment.getText())));
    }

    @Test
    public void shouldReturnBadRequestWhenSaveCommentWithInvalidData() throws Exception {
        CommentDto commentDto = new CommentDto(5, "", randomAlphabetic(100));

        this.mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isBadRequest());
    }

}