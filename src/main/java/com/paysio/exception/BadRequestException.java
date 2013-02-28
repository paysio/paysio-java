package com.paysio.exception;

import java.util.List;

import com.paysio.rest.ParameterError;

@SuppressWarnings("serial")
public class BadRequestException extends PaysioException {
    
    private List<ParameterError> parameterErrors;

    public BadRequestException(String message, List<ParameterError> params) {
        super(message);
        this.parameterErrors = params;
    }

    public List<ParameterError> getParameterErrors() {
        return parameterErrors;
    }

}
