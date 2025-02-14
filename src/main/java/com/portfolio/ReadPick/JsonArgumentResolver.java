package com.portfolio.ReadPick;

import java.util.stream.Collectors;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.ReadPick.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JsonArgumentResolver implements HandlerMethodArgumentResolver {

    private final ObjectMapper objectMapper;

    public JsonArgumentResolver(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserVo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            return null;
        }
        String jsonBody = request.getReader().lines().collect(Collectors.joining());
        return objectMapper.readValue(jsonBody, parameter.getParameterType());
    }
}
