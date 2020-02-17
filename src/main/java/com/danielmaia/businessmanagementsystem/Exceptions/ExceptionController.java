package com.danielmaia.businessmanagementsystem.Exceptions;


import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {

    private Log logger = LogFactory.getLog(ExceptionController.class);

    @ExceptionHandler(value = Exception.class)
    public String handleError(HttpServletRequest req, Exception exception) {

        logger.error("Request: " + req.getRequestURL() + " raised " + exception);
        return "redirect:/dashboard";
    }


}
