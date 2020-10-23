package com.water.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.water.base.R;

@RestControllerAdvice
public class SystemExceptionHandler {
	@ExceptionHandler(ValidatorException.class)
	public R handleValidatorException(ValidatorException e) {
		System.out.print("");
		return R.fail(e.getMessage());
	}

}
