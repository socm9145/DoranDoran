package com.purple.hello.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtGetUserIdFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // JWT 토큰에서 userId를 추출하는 작업 수행
        long userId = 123L;

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpRequest.setAttribute("userId", userId);

        chain.doFilter(request, response);
    }
}
