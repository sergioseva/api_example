package articles.service;

import articles.model.Article;
import articles.model.Comment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static articles.constants.ArticleType.CASE_STUDY;
import static articles.constants.ArticleType.EMPIRICAL_STUDY;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
// TODO: Add more tests
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CommentServiceTestCandidate {

    private CommentService commentService = new CommentService();
    private List<Comment> comments;
    private List<Article> articles;

    @Before
    public void setUp() {
        Article article1 = new Article(1,randomAlphabetic(10), randomAlphabetic(200), CASE_STUDY, newArrayList());
        Comment comment1 = new Comment(1,article1,"test@gmail.com",randomAlphabetic(100));
        Comment comment2 = new Comment(2,article1,"test2@gmail.com",randomAlphabetic(100));
        Comment comment3 = new Comment(3,article1,"test3@gmail.com",randomAlphabetic(100));

        Article article2 = new Article(2,randomAlphabetic(10), randomAlphabetic(200), EMPIRICAL_STUDY, newArrayList());
        Comment comment4 = new Comment(4,article2,"test4@gmail.com",randomAlphabetic(100));
        Comment comment5 = new Comment(5,article2,"test5@gmail.com",randomAlphabetic(100));
        Comment comment6 = new Comment(6,article2,"test6@gmail.com",randomAlphabetic(100));

        comments = newArrayList(comment1, comment2, comment3, comment4, comment5, comment6);
        articles = newArrayList(article1, article2);
        ArticleService.articles = articles;
        CommentService.comments = comments;
    }

    @Test
    public void shouldFindAllComments() {
        List<Comment> commentsResultList = commentService.findAll();

        assertThat(commentsResultList).hasSize(comments.size()).isEqualTo(comments);
    }

    @Test
    public void shouldSaveComment() {
        Article article = new Article(3, "Title 3", "Body 3", CASE_STUDY, newArrayList());
        Comment comment = new Comment(7, article, "test7@gmail.com", "Comment 7");

        commentService.save(comment);

        assertThat(commentService.findAll()).contains(comment);
    }

    @Test
    public void shouldUpdateComment() {
        int commentId = 1;
        String updatedText = "Updated Comment 1";

        Comment comment = commentService.findById(commentId);
        comment.setText(updatedText);

        commentService.update(commentId, comment);

        Comment updatedComment = commentService.findById(commentId);
        assertEquals(updatedText, updatedComment.getText());
    }

}
