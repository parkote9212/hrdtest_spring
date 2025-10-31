package com.pgc.book.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @RestControllerAdvice : 모든 @RestController (즉, 모든 Controller)에서 발생하는
 * 예외(Exception)를 감지하고 처리하는 클래스임을 선언합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * [  1. Validation 예외 처리  ]
     *
     * @ExceptionHandler(MethodArgumentNotValidException.class) : @Valid 어노테이션으로 인한 유효성 검사(Validation)가 실패했을 때
     * 발생하는 'MethodArgumentNotValidException'을 전문적으로 처리합니다.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // ex.getBindingResult(): 발생한 오류 정보들을 가져옴
        // .getFieldErrors(): 그 중 '필드'에 대한 오류들만 가져옴 (List)
        // .forEach(...): 각 오류(error)에 대해 람다식 실행
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();     // 오류가 발생한 필드 이름
            String errorMessage = error.getDefaultMessage(); // DTO에 설정한 오류 메시지
            errors.put(fieldName, errorMessage);
        });
        // 400 Bad Request 상태 코드와 오류 맵(JSON)을 반환
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    /**
     * [ ⭐️ 2. 모든 기타 예외 처리 ⭐️ ]
     * @ExceptionHandler(Exception.class)
     * : (1)번 Validation 예외를 제외한,
     * 서버에서 발생하는 '모든' 예외(Exception)를 처리합니다.
     * (예: DB 오류, 0으로 나누기, NullPointerException 등)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "서버 내부 오류가 발생했습니다.");
        errorResponse.put("message", ex.getMessage()); // (개발 단계에서만 메시지 노출)

        // (실제 운영 환경에서는 ex.getMessage() 대신,
        //  log.error("서버 오류 발생", ex) 처럼 로그 파일에만 기록해야 합니다.)

        // 500 Internal Server Error 상태 코드와 오류 메시지를 반환
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
