package br.org.roger.exam.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The received argument is not valid")
public class PostArgumentNotValidException extends RuntimeException {

    public PostArgumentNotValidException(String message) {
        super(message);
    }
}
