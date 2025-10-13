package com.udemy.sprinboot.ecommerce.springboot_ecommerce.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MiGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>  miMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> respuesta = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String fieldNombre = ((FieldError)err).getField();
            String mensaje = err.getDefaultMessage();
            respuesta.put(fieldNombre,mensaje);
        });
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(RecursoNoEncotradoException.class)
    public ResponseEntity<APIRespuesta> miRecursoNoEncotradoException(RecursoNoEncotradoException ex){
        String mensaje = ex.getMessage();
        APIRespuesta respuesta = new APIRespuesta(mensaje,false);
        return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIRespuesta> miAPIException(APIException ex){
        String mensaje = ex.getMessage();
        APIRespuesta respuesta = new APIRespuesta(mensaje,false);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }
}
