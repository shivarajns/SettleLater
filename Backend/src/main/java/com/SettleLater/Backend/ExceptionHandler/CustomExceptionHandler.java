package com.SettleLater.Backend.ExceptionHandler;

import com.SettleLater.Backend.common.ApiResponse.ApiResponseDTO;
import com.SettleLater.Backend.customer.exceptions.CustomerAlreadyExists;
import com.SettleLater.Backend.customer.exceptions.ShopNotExists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExists.class)
    public ResponseEntity<ApiResponseDTO<?>> handleCustomerAlreadyExists(
            CustomerAlreadyExists ex
    ){
        ApiResponseDTO<?> response = ApiResponseDTO.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<?>> handleGenericException(
            Exception ex) {

        ApiResponseDTO<?> response = ApiResponseDTO.builder()
                .success(false)
                .message("Something went wrong")
                .data(null)
                .build();

        return new ResponseEntity<>(response,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ShopNotExists.class)
    public ResponseEntity<ApiResponseDTO<?>> handleShopNotExists(
            CustomerAlreadyExists ex
    ){
        ApiResponseDTO<?> response = ApiResponseDTO.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
