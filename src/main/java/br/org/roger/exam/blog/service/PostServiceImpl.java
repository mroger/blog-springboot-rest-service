package br.org.roger.exam.blog.service;

import br.org.roger.exam.blog.exception.PostArgumentNotValidException;
import br.org.roger.exam.blog.exception.PostNotFoundException;
import br.org.roger.exam.blog.model.Post;
import br.org.roger.exam.blog.model.json.PostJson;
import br.org.roger.exam.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */

/**
 * http://www.baeldung.com/spring-cache-tutorial
 * http://infinispan.org/tutorials/embedded/
 * https://github.com/infinispan/infinispan-spring-boot
 */
@Service
@CacheConfig(cacheNames = { "blogpost, blogposts" })
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    @Cacheable("blogposts")
    public List<Post> findAll() {
        System.out.println("findAll()");
        return postRepository.findAll();
    }

    @Override
    @Cacheable(value ="blogpost", key = "#id", unless="#result == null")
    public Optional<Post> findById(final Long id) {
        System.out.println("findById()");
        return postRepository.findById(id);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value ="blogposts", allEntries = true),
        @CacheEvict(value ="blogpost", key = "#id", beforeInvocation = true)
    })
    @Cacheable(value ="blogpost")
    public Post update(Long id, final PostJson postJson) {

        return postRepository.findById(id)
            .map(p -> {
                Post post = postJson.updateModel(p);
                return postRepository.save(post);
            })
            .orElseThrow(() -> new PostNotFoundException("No post found for update"));
    }

    @Override
    @CacheEvict(value ="blogposts", allEntries = true)
    public Post create(PostJson postJson) {
        if (postJson.getId() != null) {
            throw new PostArgumentNotValidException("Id must be null for create");
        }
        Post post = postJson.createModel();
        return postRepository.save(post);
    }

    @Override
    @Caching(evict = {
        @CacheEvict(value ="blogposts", allEntries = true),
        @CacheEvict(value ="blogpost", key = "#id")
    })
    public void delete(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (!post.isPresent()) {
            throw new PostNotFoundException();
        }

        postRepository.delete(post.get());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value ="blogposts", allEntries = true),
            @CacheEvict(value ="blogpost", allEntries = true)
    })
    public void deleteAll() {
        postRepository.deleteAll();
    }
}
