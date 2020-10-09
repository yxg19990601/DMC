package com.ems.szy.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ems.szy.dto.ResponseDto;
import com.ems.szy.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 跨域会产生OPTIONS 方法， 在此需要放行
        if ("OPTIONS".equals(request.getMethod())){
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return true;
        }


        ResponseDto result = new ResponseDto(20000, "请求成功！",null);
        //请求头中获取token
        String token = request.getHeader("token");

        //判断异常
        try {
            // 校验token
            JwtUtil.verity(token);
            return true;
        } catch (SignatureVerificationException e) {
            result.setCode(5008);
            result.setMessage("无效签名！");
            e.printStackTrace();
        } catch (TokenExpiredException e) {
            result.setCode(50014);
            result.setMessage("token过期！");
            e.printStackTrace();
        } catch (AlgorithmMismatchException e) {
            result.setCode(50008);
            result.setMessage("token算法不一致！");
            e.printStackTrace();
        } catch (Exception e) {
            //如果没有token
            if (token == null) {
                result.setCode(50012);
                result.setMessage("用户未认证！");
            } else{
                result.setCode(500);
                result.setMessage(e.getMessage());
            }
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(result));
        return false;
    }


}
