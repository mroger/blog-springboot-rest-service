package br.org.roger.exam.blog.exception;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
public class PostArgumentNotValidException extends RuntimeException {

    public PostArgumentNotValidException() { }

    public PostArgumentNotValidException(String message) {
        super(message);
    }
}
