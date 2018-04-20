package br.org.roger.exam.blog.exception;

/**
 *
 * @author Marcos (mroger.oliveira@gmail.com)
 *
 */
public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException() { }

    public PostNotFoundException(String message) {
        super(message);
    }
}
