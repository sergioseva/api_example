package articles.controller;

import articles.dto.CommentDto;
import articles.model.Comment;
import articles.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Complete this
@RestController
@RequestMapping(value = "comments")
public class CommentController {

    private CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping
    public @ResponseBody List<Comment> findAllComments() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public @ResponseBody CommentDto findCommentById(@PathVariable("id") int id) {
        Comment comment = service.findById(id);
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comment, commentDto);
        return commentDto;
    }

    @PostMapping
    public @ResponseBody Comment saveComment(@Valid @RequestBody CommentDto commentDto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);
        return service.save(comment);
    }

    @PutMapping(value = "/{id}")
    public @ResponseBody Comment updateComment(@Valid @PathVariable("id") int id, @RequestBody CommentDto updatedCommentDto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(updatedCommentDto, comment);
        return service.update(id, comment);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteComment(@PathVariable("id") int id) {
        service.delete(id);
    }
}