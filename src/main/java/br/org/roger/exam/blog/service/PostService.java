package br.org.roger.exam.blog.service;

import br.org.roger.exam.blog.model.Post;
import br.org.roger.exam.blog.model.json.PostJson;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
public interface PostService {

    List<Post> findAll();

    Optional<Post> findById(Long id);

    Post update(Long id, PostJson postJson);

    Post create(PostJson postJson);

    void delete(Long id);

    void deleteAll();
}
