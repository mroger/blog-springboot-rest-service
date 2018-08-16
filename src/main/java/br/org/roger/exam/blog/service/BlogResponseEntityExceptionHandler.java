package br.org.roger.exam.blog.service;

import br.org.roger.exam.blog.exception.ErrorDetails;
import br.org.roger.exam.blog.exception.PostArgumentNotValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Locale;

@ControllerAdvice
@RestController
public class BlogResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /*@Autowired
    private MessageSource messageSource;*/

    @ExceptionHandler(PostArgumentNotValidException.class)
    public final ResponseEntity<ExceptionResponse> argumentNotValid(final PostArgumentNotValidException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(),
                "The argument is really wrong");
        return new ResponseEntity<ExceptionResponse>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();

        return processFieldError(error);
    }

    private ResponseEntity<Object> processFieldError(FieldError error) {
        ErrorDetails message = null;
        if (error != null) {
            message = new ErrorDetails(new Date(), "Validation Failed", error.getDefaultMessage());
        }
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }
}
