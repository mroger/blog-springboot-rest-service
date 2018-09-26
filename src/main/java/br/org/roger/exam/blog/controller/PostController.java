package br.org.roger.exam.blog.controller;

import br.org.roger.exam.blog.exception.PostNotFoundException;
import br.org.roger.exam.blog.model.Post;
import br.org.roger.exam.blog.model.json.PostJson;
import br.org.roger.exam.blog.service.PostService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
            throw new RuntimeException("There was an unexpected error while listing all posts");
        }
    }

    @ApiOperation(
        value = "Returns one Post with the given id",
        nickname = "findById")
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public Resource<PostJson> findById(@PathVariable final Long id) {

        PostJson postJson = postService.findById(id)
            .map(PostJson::fromModel)
            .orElseThrow(PostNotFoundException::new);

        Resource<PostJson> postResource = new Resource<PostJson>(postJson);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findAll());
        postResource.add(linkTo.withRel("parent"));

        return postResource;
    }

    @ApiOperation(
        value = "Creates one Post with the data in the payload",
        nickname = "create")
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public ResponseEntity<PostJson> create(@RequestBody @Valid final PostJson postJson) {

        Post post = postService.create(postJson);
        return ResponseEntity.ok(PostJson.fromModel(post));

    }

    @ApiOperation(
        value = "Updates one Post with the data in the payload",
        nickname = "update")
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PostJson> update(@PathVariable final Long id,
                                           @RequestBody @Valid final PostJson postJson) {

        Post post = postService.update(id, postJson);
        return ResponseEntity.ok(PostJson.fromModel(post));

    }

    @ApiOperation(
        value = "Deletes one Post with the given id",
        nickname = "deleteOne")
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteOne(@PathVariable final Long id) {

        postService.delete(id);
        return ResponseEntity.ok().build();
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
            throw new RuntimeException("There was an error while deleting all posts");
        }
    }
}
