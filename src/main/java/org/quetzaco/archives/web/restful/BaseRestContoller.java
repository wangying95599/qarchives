package org.quetzaco.archives.web.restful;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dong on 2017/3/15.
 */
@RestController
public class BaseRestContoller {
    protected <T> HttpEntity<T> buildEntity(T t, HttpStatus status) {
        return new ResponseEntity<T>(t, status);
    }

    protected <T> HttpEntity<T> buildEntity(T t) {
        return buildEntity(t, HttpStatus.OK);
    }
}