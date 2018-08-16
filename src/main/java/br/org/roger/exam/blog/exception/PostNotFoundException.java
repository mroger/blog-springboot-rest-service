package br.org.roger.exam.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No post found")
public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() { }

    public PostNotFoundException(String message) {
        super(message);
    }
}
