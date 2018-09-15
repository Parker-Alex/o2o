package com.smart.o2o.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "结果没有找到")
public class MyException extends RuntimeException {
}
