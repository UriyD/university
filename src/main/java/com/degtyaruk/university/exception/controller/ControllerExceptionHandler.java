package com.degtyaruk.university.exception.controller;

import com.degtyaruk.university.exception.EntityNotFoundException;
import com.degtyaruk.university.exception.InvalidEntityException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class ControllerExceptionHandler {

    private static final String EXCEPTION_VIEW_FOR_NO_ELEMENT = "errors/errorNoSuchElem";

    private static final String EXCEPTION_VIEW_FOR_INVALID_ELEMENT = "errors/errorInvalidEntity";

    @ExceptionHandler(EntityNotFoundException.class)
    public ModelAndView handleNoSuchElementException(Exception e) {
        return prepareModel(e, EXCEPTION_VIEW_FOR_NO_ELEMENT);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ModelAndView handleInvalidEntityException(Exception e) {
        return prepareModel(e, EXCEPTION_VIEW_FOR_INVALID_ELEMENT);
    }

    private ModelAndView prepareModel(Exception e, String view) {
        ModelAndView modelAndView = new ModelAndView(view);
        modelAndView.addObject("message", e.getMessage());
        return modelAndView;
    }
}

