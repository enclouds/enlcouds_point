package com.enclouds.enpoint.app.controller;

import com.enclouds.enpoint.app.dto.AppDto;
import com.enclouds.enpoint.app.service.AppService;
import com.enclouds.enpoint.bdd.dto.BddDto;
import com.enclouds.enpoint.bdd.service.BddService;
import com.enclouds.enpoint.game.dto.GameDto;
import com.enclouds.enpoint.user.dto.PointDto;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.CustomUserService;
import com.enclouds.enpoint.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private BddService bddService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView index(HttpServletResponse response, @ModelAttribute AppDto appDto, HttpSession httpSession) throws Exception{
        ModelAndView mv = new ModelAndView("app/login");
        String userId = "";
        userId = (String)httpSession.getAttribute("appUserId");

        if(userId != null) {
            if (!userId.equals("")) {
                mv.setViewName("redirect:/app/main");
            }
        }

        return mv;
    }

    @PostMapping("/loginAjax")
    public @ResponseBody
    Map<String, Object> loginAjax(@ModelAttribute("appDto") AppDto appDto, HttpSession httpSession) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        int resultCode = appService.login(appDto);

        if (resultCode > 0) {
            result.put("resultCode", 0);
            result.put("resultMsg", "정상적으로 로그인 되었습니다.");
            httpSession.setAttribute("appUserId", appDto.getId());
        } else {
            result.put("resultCode", -1);
            result.put("resultMsg", "로그인에 실패 하였습니다.\n신규가입자는 본인인증을 하여 비밀번호 설정후 로그인 하십시오.");
        }

        return result;
    }

    @PostMapping("/getAuthCodeAjax")
    public @ResponseBody
    Map<String, Object> getAuthCodeAjax(@ModelAttribute("appDto") AppDto appDto) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        int resultCode = appService.getAuthCodeAjax(appDto);

        if (resultCode > 0) {
            result.put("resultCode", 0);
            result.put("resultMsg", "정상적으로 발송 되었습니다.\n인증번호를 입력후 비밀번호 설정을 하십시오.");
        } else {
            if(resultCode == -2){
                result.put("resultCode", -2);
                result.put("resultMsg", "존재하지 않는 회원 휴대폰번호입니다.\n 관리자에게 문의 바랍니다.(010-7733-4561)");
            }else {
                result.put("resultCode", -1);
                result.put("resultMsg", "인증번호 발송에 실패 하였습니다.");
            }
        }

        return result;
    }

    @PostMapping("/getAuthCodeChkAjax")
    public @ResponseBody
    Map<String, Object> getAuthCodeChkAjax(@ModelAttribute("appDto") AppDto appDto) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        int resultCode = appService.getAuthCodeChkAjax(appDto);

        if (resultCode > 0) {
            result.put("resultCode", 0);
            result.put("resultMsg", "인증 완료 되었습니다.\n아래 비밀번호를 입력후 비밀번호 설정을 하십시오.");
        } else {
            result.put("resultCode", -1);
            result.put("resultMsg", "인증에 실패 하였습니다.");
        }

        return result;
    }

    @PostMapping("/setPasswordAjax")
    public @ResponseBody
    Map<String, Object> setPasswordAjax(@ModelAttribute("appDto") AppDto appDto) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        int resultCode = appService.setPasswordAjax(appDto);

        if (resultCode > 0) {
            result.put("resultCode", 0);
            result.put("resultMsg", "비밀번호 설정이 완료 되었습니다.\n로그인 하십시오.");
        } else {
            result.put("resultCode", -1);
            result.put("resultMsg", "비밀번호 설정이 실패 하였습니다.");
        }

        return result;
    }

    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public ModelAndView auth(HttpServletResponse response, @ModelAttribute AppDto appDto) throws Exception{
        ModelAndView mv = new ModelAndView("app/auth");

        return mv;
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public ModelAndView main(HttpServletResponse response, @ModelAttribute AppDto appDto, HttpSession httpSession) throws Exception{
        ModelAndView mv = new ModelAndView("app/main");
        String userId = "";
        userId = (String)httpSession.getAttribute("appUserId");

        if(userId != null){
            if(!userId.equals("")){
                UserDto param = new UserDto();
                //param.setPhoneNum(appDto.getId());
                param.setPhoneNum(userId);
                UserDto userInfo = customUserService.selectCustomUserInfo(param);

                mv.addObject("userInfo", userInfo);

                PointDto pointDto = new PointDto();
                pointDto.setPhoneNum(userId);
                List<PointDto> pointList = customUserService.selectPointHistory(userInfo);

                mv.addObject("pointList", pointList);
            }
        }else {
            mv.setViewName("redirect:/app");
        }

        return mv;
    }

    @RequestMapping(value = "/bdd", method = RequestMethod.GET)
    public ModelAndView bdd(HttpServletResponse response, @ModelAttribute AppDto appDto, HttpSession httpSession) throws Exception{
        ModelAndView mv = new ModelAndView("app/bdd");

        String userId = "";
        userId = (String)httpSession.getAttribute("appUserId");

        if(userId != null) {
            if (!userId.equals("")) {
                UserDto param = new UserDto();
                //param.setPhoneNum(appDto.getId());
                param.setPhoneNum("01082086200");
                UserDto userInfo = customUserService.selectCustomUserInfo(param);

                mv.addObject("userInfo", userInfo);

                BddDto bddDto = new BddDto();
                List<BddDto> bddList = bddService.selectBddList(bddDto);

                mv.addObject("bddList", bddList);
            }
        }

        return mv;
    }

    @RequestMapping(value = "/bdd/detail", method = RequestMethod.GET)
    public ModelAndView bddDetail(HttpServletResponse response, @ModelAttribute AppDto appDto, HttpSession httpSession) throws Exception{
        ModelAndView mv = new ModelAndView("app/bddDetail");

        String userId = "";
        userId = (String)httpSession.getAttribute("appUserId");

        if(userId != null) {
            if (!userId.equals("")) {
                UserDto param = new UserDto();
                //param.setPhoneNum(appDto.getId());
                param.setPhoneNum("01082086200");
                UserDto userInfo = customUserService.selectCustomUserInfo(param);

                mv.addObject("userInfo", userInfo);

                BddDto bddDto = new BddDto();
                bddDto.setSeq(appDto.getBddSeq());
                BddDto bddInfo = bddService.selectBddDetail(bddDto);

                mv.addObject("bddInfo", bddInfo);
            }
        }

        return mv;
    }

}
