package articles.service;

import java.util.*;
import articles.exception.*;
import articles.model.*;
import articles.repository.*;
import org.springframework.beans.*;
import org.springframework.stereotype.*;

// TODO: Complete this
@Service
public class CommentService implements CrudRepository<Comment>{
    public static List<Comment> comments = new ArrayList<>();

    public List<Comment> findAll() {
        return comments;
    }

    public Comment findById(int id) {
        for (Comment comment : comments) {
            if (comment.getId() == id) {
                return comment;
            }
        }
        throw new NotFoundException("Comment not found with ID: " + id);
    }

    public Comment save(Comment comment) {
        comments.add(comment);
        return comment;
    }

    public Comment update(int id, Comment updatedComment) {
        this.findById(id);
        delete(id);
        comments.add(updatedComment);
        return updatedComment;
    }

    public void delete(int id) {
        Comment comment = findById(id);
        if (comment != null) {
            comments.remove(comment);
        }
    }
}