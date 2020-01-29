package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.alura.forum.dto.output.ValidationErrorsOutputDto;

@RestControllerAdvice
public class ExceptionRestHandlerController {
	
	private MessageSource messageSouce;

	public ExceptionRestHandlerController(MessageSource messageSouce) {
		this.messageSouce = messageSouce;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ValidationErrorsOutputDto handleValidationError(MethodArgumentNotValidException exception) {
		List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		
		ValidationErrorsOutputDto validationErrors = new ValidationErrorsOutputDto();
		
		globalErrors.forEach(error -> validationErrors.addError(getErrorMessage(error)));
		
		fieldErrors.forEach(error -> {
			validationErrors.addFieldError(error.getField(), getErrorMessage(error));
		});
		
		return validationErrors;
	}

	private String getErrorMessage(ObjectError error) { 
		return messageSouce.getMessage(error, LocaleContextHolder.getLocale());
	}
}