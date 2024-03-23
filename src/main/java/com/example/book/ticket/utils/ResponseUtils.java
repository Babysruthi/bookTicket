package com.example.book.ticket.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {

    ResponseUtils(){

    }

    public static ResponseEntity<HttpStatusResponse> prepareCreatedSuccessResponse(String responseMessage, Object data){
        return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.OK.value(),responseMessage,data), HttpStatus.CREATED);
    }

    public static ResponseEntity<HttpStatusResponse> prepareSuccessResponse(String responseMessage, Object data){
        return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.OK.value(),responseMessage,data), HttpStatus.OK);
    }

    public static  ResponseEntity<HttpStatusResponse> prepareUnProcessableResponse(String responseMessage, Object data){
        return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), responseMessage,data),HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ResponseEntity<HttpStatusResponse> prepareNoRecordsFoundResponse(String responseMessage, Object data){
        return new ResponseEntity<>(new HttpStatusResponse(HttpStatus.NO_CONTENT.value(), responseMessage,data),HttpStatus.NO_CONTENT);
    }
}
