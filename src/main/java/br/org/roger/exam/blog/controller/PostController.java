package br.org.roger.exam.blog.controller;

import br.org.roger.exam.blog.exception.PostArgumentNotValidException;
import br.org.roger.exam.blog.exception.PostNotFoundException;
import br.org.roger.exam.blog.model.Post;
import br.org.roger.exam.blog.model.json.PostJson;
import br.org.roger.exam.blog.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
@RestController
@RequestMapping("/blog")
public class PostController {

    @Autowired
    private PostService postService;

    @ApiOperation(
            value = "Returns all posts from users",
            nickname = "findAll")
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ResponseEntity<List<PostJson>> findAll() {

        try {
            List<Post> allPosts =  postService.findAll();
            if (allPosts.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(PostJson.fromModelList(allPosts));
        } catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ApiOperation(
            value = "Returns one Post with the given id",
            nickname = "findById")
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ResponseEntity<PostJson> findById(@PathVariable final Long id) {

        return postService.findById(id)
            .map(post -> {
                return ResponseEntity.ok(PostJson.fromModel(post));
            })
            .orElse(ResponseEntity.notFound().build());

    }

    @ApiOperation(
            value = "Creates one Post with the data in the payload",
            nickname = "create")
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public ResponseEntity<PostJson> create(@RequestBody final PostJson postJson) {

        try {
            Post post = postService.create(postJson);
            return ResponseEntity.ok(PostJson.fromModel(post));
        } catch(PostArgumentNotValidException manve) {
            return ResponseEntity.badRequest().build();
        }

    }

    @ApiOperation(
            value = "Updates one Post with the data in the payload",
            nickname = "update")
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PostJson> update(@PathVariable final Long id,
                                           @RequestBody final PostJson postJson) {

        try {
            Post post = postService.update(id, postJson);
            return ResponseEntity.ok(PostJson.fromModel(post));
        } catch(PostNotFoundException pnfe) {
            return ResponseEntity.notFound().build();
        } catch(PostArgumentNotValidException manve) {
            return ResponseEntity.badRequest().build();
        }

    }

    @ApiOperation(
            value = "Deletes one Post with the given id",
            nickname = "deleteOne")
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOne(@PathVariable final Long id) {
        try {
            postService.delete(id);
            return ResponseEntity.ok().build();
        } catch(PostNotFoundException pnfe) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(
            value = "Deletes all Posts entered by the users",
            nickname = "deleteAll")
    @RequestMapping(value = "/posts", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAll() {
        try {
            postService.deleteAll();
            return ResponseEntity.ok().build();
        } catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
