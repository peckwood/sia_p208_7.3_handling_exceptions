package spittr.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import spittr.exception.DuplicateSpittleExceptionGlobal;

//this get scanned like @Controller
@ControllerAdvice
public class AppWideExceptionHandler {
	@ExceptionHandler(DuplicateSpittleExceptionGlobal.class)
	  public String duplicateSpittleHandler(){
		  return "error/duplicate-global";
	  }
}
