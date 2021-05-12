package com.PauloChaves.ProjetoCursoUdemy.resources.exception;

import com.PauloChaves.ProjetoCursoUdemy.services.exception.AuthorizationExceptions;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.DatabaseExceptions;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.FileException;
import com.PauloChaves.ProjetoCursoUdemy.services.exception.ObjectNotFoundException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(),e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
    @ExceptionHandler(DatabaseExceptions.class)
    public ResponseEntity<StandardError> dataBaseExceptions(DatabaseExceptions e, HttpServletRequest request){
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
        ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(),"Erro Validacao",System.currentTimeMillis());
        for (FieldError x : e.getBindingResult().getFieldErrors()){
            err.addError(x.getField(),x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AuthorizationExceptions.class)
    public ResponseEntity<StandardError> authorization(AuthorizationExceptions e, HttpServletRequest request){
        StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(),e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }
    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> file(FileException e, HttpServletRequest request){
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonService(AmazonServiceException e, HttpServletRequest request){

        HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
        StandardError err = new StandardError(code.value(),e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(code).body(err);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(AmazonClientException e, HttpServletRequest request){
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonClient(AmazonS3Exception e, HttpServletRequest request){
        StandardError err = new StandardError(HttpStatus.BAD_REQUEST.value(),e.getMessage(),System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
