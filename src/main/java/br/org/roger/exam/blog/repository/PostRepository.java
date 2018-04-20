package br.org.roger.exam.blog.repository;


import br.org.roger.exam.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 * Transaction read only by default
 */
@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);

}
