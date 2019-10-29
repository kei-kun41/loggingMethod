package fr.elouan.springboot.logging.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoggingServiceImpl implements LoggingService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void logRequest(HttpServletRequest httpServletRequest, Object body) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, String> parameters = buildParametersMap(httpServletRequest);

        stringBuilder.append("\n======================== Begin incoming request =============================");
        stringBuilder.append("\n=== ").append(httpServletRequest.getMethod());
        stringBuilder.append("\n=== PATH :").append(httpServletRequest.getRequestURI());
        stringBuilder.append("\n=== HEADERS : ").append(buildHeadersMap(httpServletRequest));

        if (!parameters.isEmpty()) {
            stringBuilder.append("\n=== PARAMETERS : ").append(parameters);
        }

        if (body != null) {
            stringBuilder.append("\n=== BODY : " + body );
        }
        stringBuilder.append("\n========================= End incoming request ==============================");

        log.info(stringBuilder.toString());
    }

    @Override
    public void logResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object body) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n======================== Begin incoming response =============================");
        stringBuilder.append("\n=== ").append(httpServletRequest.getMethod());
        stringBuilder.append("\n=== PATH :").append(httpServletRequest.getRequestURI());
        stringBuilder.append("\n=== HEADERS : ").append(buildHeadersMap(httpServletRequest));
        stringBuilder.append("\n=== RESPONSEHEADERS : ").append(buildHeadersMap(httpServletResponse));
        stringBuilder.append("\n=== RESPONSEBODY : ").append(body);
        stringBuilder.append("\n========================= End incoming response ==============================");

        log.info(stringBuilder.toString());
    }

    private Map<String, String> buildParametersMap(HttpServletRequest httpServletRequest) {
        Map<String, String> resultMap = new HashMap<>();
        Enumeration<String> parameterNames = httpServletRequest.getParameterNames();

        while (parameterNames.hasMoreElements()) {
            String key = parameterNames.nextElement();
            String value = httpServletRequest.getParameter(key);
            resultMap.put(key, value);
        }

        return resultMap;
    }

    private Map<String, String> buildHeadersMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private Map<String, String> buildHeadersMap(HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();

        Collection<String> headerNames = response.getHeaderNames();
        for (String header : headerNames) {
            map.put(header, response.getHeader(header));
        }

        return map;
    }
}
