package com.enclouds.enpoint.config;

import com.enclouds.enpoint.cmm.login.service.LoginService;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 로그인 완료시 이벤트 핸들러
 * @author Enclouds
 * @since 2020.12.11
 */

@Configuration
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    public MyLoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                try {
                    userInfo = userService.getUserInfo(userDetails.getUsername());
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            try {
                userInfo = userService.getUserInfo(auth2User.getName());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        if(userInfo != null){
            userInfo.setLoginIp(this.getClientIp(httpServletRequest));
            try {
                loginService.saveLoginHist(userInfo);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        if (session != null) {
            String redirectUrl = (String) session.getAttribute("prevPage");
            if (redirectUrl != null) {
                session.removeAttribute("prevPage");
                getRedirectStrategy().sendRedirect(httpServletRequest, httpServletResponse, redirectUrl);
            } else {
                super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
            }
        } else {
            super.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        }

    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
