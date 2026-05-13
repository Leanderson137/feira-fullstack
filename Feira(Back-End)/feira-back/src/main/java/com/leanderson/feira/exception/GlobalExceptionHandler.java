package com.leanderson.feira.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, String>> tratarRegraNegocio(RegraNegocioException exception) {

        Map<String, String> erro = new LinkedHashMap<>();
        erro.put("erro", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> tratarIllegalArgument(IllegalArgumentException exception) {

        Map<String, String> erro = new LinkedHashMap<>();
        erro.put("erro", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarValidacao(MethodArgumentNotValidException exception) {

        Map<String, String> erro = new LinkedHashMap<>();

        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        erro.put("erro", mensagem);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(erro);
    }
}