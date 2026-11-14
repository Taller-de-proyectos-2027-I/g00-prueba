package pe.edu.usmp.taller.g00.web;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * Una entrada inválida es un error del cliente (400), no del servidor (500).
 * Corrige los hallazgos de ZAP "Buffer Overflow", "Format String Error" y
 * "Server Error response code" (CWE-388): todos venían de validaciones que
 * terminaban en 500. La respuesta no revela detalles internos.
 */
@RestControllerAdvice
public class ManejadorErrores {

    private static final String DETALLE = "Parámetros de entrada inválidos";

    @ExceptionHandler({ConstraintViolationException.class, HandlerMethodValidationException.class})
    public ProblemDetail entradaInvalida(Exception ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, DETALLE);
    }
}
