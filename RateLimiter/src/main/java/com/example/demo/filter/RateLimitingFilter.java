package com.example.demo.filter;

import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class RateLimitingFilter implements Filter {

    private final Bucket bucket;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (bucket.tryConsume(1)){
            filterChain.doFilter(request, response); // forward the request if rate limiting is not hit
        } else {
            ((HttpServletResponse) response).setStatus(429); // return 429 if rate limit is exceeded
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
