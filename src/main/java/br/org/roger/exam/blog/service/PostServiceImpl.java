package br.org.roger.exam.blog.service;

import br.org.roger.exam.blog.exception.PostArgumentNotValidException;
import br.org.roger.exam.blog.exception.PostNotFoundException;
import br.org.roger.exam.blog.model.Post;
import br.org.roger.exam.blog.model.json.PostJson;
import br.org.roger.exam.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(final Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Post update(Long id, final PostJson postJson) {

        return postRepository.findById(id)
            .map(p -> {
                Post post = postJson.updateModel(p);
                return postRepository.save(post);
            })
            .orElseThrow(() -> new PostNotFoundException("No post found for update"));
    }

    @Override
    public Post create(PostJson postJson) {
        if (postJson.getId() != null) {
            throw new PostArgumentNotValidException();
        }
        Post post = postJson.createModel();
        return postRepository.save(post);
    }

    @Override
    public void delete(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (!post.isPresent()) {
            throw new PostNotFoundException();
        }

        postRepository.delete(post.get());
    }

    @Override
    public void deleteAll() {
        postRepository.deleteAll();
    }
}
