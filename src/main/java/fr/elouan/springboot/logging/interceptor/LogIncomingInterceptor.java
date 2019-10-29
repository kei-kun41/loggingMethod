package fr.elouan.springboot.logging.interceptor;

import fr.elouan.springboot.logging.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogIncomingInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    LoggingService loggingService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object body) {
        loggingService.logRequest(request, body);
        return true;
    }

    @Override
    public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object body, Exception ex) {
        loggingService.logResponse(request, response, body);
    }
}
