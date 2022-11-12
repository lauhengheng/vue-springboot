package com.lauheng.interceptor;

import com.lauheng.jwt.JwtUtil;
import com.lauheng.model.Userinfo;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor
        implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        //拦截器进行判断放行
        if(request.getMethod().toUpperCase().equals("OPTIONS")){
            return true;
        }
        //如果是图片
        if(request.getRequestURI().contains("images")){
            return true;
        }
        //获取Token
        String token=request.getHeader("token");
        if(token==null){ //没有token
            return false;
        }else {//存在
            //是否有效
            boolean bs= JwtUtil.checkToken(token);
            if(bs){
                Userinfo info=new Userinfo();
                info.setType(JwtUtil.getUserType(token));
                info.setId(JwtUtil.getUserId(token));
                info.setUsername(JwtUtil.getMemberIdByJwtToken(token));
                token=JwtUtil.getJwtToken(info);
                response.addHeader("token",token);
                return true;
            }else{
                response.setHeader("code","50008");
                return false;
            }
        }

    }
}
