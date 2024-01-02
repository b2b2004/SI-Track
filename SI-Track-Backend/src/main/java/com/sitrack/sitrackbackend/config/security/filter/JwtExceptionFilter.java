package com.sitrack.sitrackbackend.config.security.filter;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(request, response);
        }catch (JwtException e){
            e.printStackTrace();
            setResponse(response, e.getMessage());
        }catch (NullPointerException e) {
            e.printStackTrace();
            setResponse(response, e.getMessage());
        }
    }

    private void setResponse(HttpServletResponse response, String code) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        try {
            responseJson.put("status", HttpStatus.UNAUTHORIZED);
            responseJson.put("message", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.getWriter().print(responseJson);
    }
}
