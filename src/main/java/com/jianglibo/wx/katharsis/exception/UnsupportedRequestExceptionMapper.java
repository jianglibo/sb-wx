package com.jianglibo.wx.katharsis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapper;

@Component
public class UnsupportedRequestExceptionMapper implements ExceptionMapper<UnsupportedRequestException> {

	@Override
	public ErrorResponse toErrorResponse(UnsupportedRequestException e) {
		ErrorData ed = ErrorData
				.builder()
				.setTitle(e.getTitle())
				.setCode(e.getCode())
				.setDetail(e.getDetail())
				.build();
		return ErrorResponse.builder().setStatus(HttpStatus.BAD_REQUEST.value())
		.setSingleErrorData(ed).build();
	}

	@Override
	public UnsupportedRequestException fromErrorResponse(ErrorResponse errorResponse) {
		ErrorData ed =  errorResponse.getErrors().iterator().next();
		UnsupportedRequestException ae = new UnsupportedRequestException(ed.getDetail());
		return ae;
	}

	@Override
	public boolean accepts(ErrorResponse errorResponse) {
		return errorResponse.getHttpStatus() == HttpStatus.BAD_REQUEST.value();
	}

}
