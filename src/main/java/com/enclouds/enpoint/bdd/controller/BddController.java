package com.enclouds.enpoint.bdd.controller;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.bdd.dto.BddDto;
import com.enclouds.enpoint.bdd.service.BddService;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bdd")
public class BddController {

    @Autowired
    private UserService userService;

    @Autowired
    private BddService bddService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView bddList(HttpServletResponse response, @ModelAttribute BddDto bddDto) throws Exception{
        ModelAndView mv = new ModelAndView("bdd/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                if(bddDto.getSchCond1() == null){
                    bddDto.setSchCond1("title");
                }

                List<BddDto> bddList = bddService.selectBddList(bddDto);
                mv.addObject("bddList", bddList);

            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", bddDto);

        return mv;
    }

    @PostMapping("/insertBddAjax")
    public @ResponseBody
    Map<String, Object> insertBddAjax(@ModelAttribute("bddDto") BddDto bddDto) throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";
        int resultCode;
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            if (principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                resultCode = bddService.insertBdd(bddDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 등록 되었습니다.");
                } else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "등록에 실패 하였습니다.");
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

    @PostMapping("/updateBddAjax")
    public @ResponseBody Map<String, Object> updateBddAjax(@ModelAttribute("bddDto") BddDto bddDto) throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";
        int resultCode;
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            if (principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                resultCode = bddService.updateBdd(bddDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 수정 되었습니다.");
                } else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "수정에 실패 하였습니다.");
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

    @PostMapping("/deleteBddAjax")
    public @ResponseBody Map<String, Object> deleteBddAjax(@ModelAttribute("bddDto") BddDto bddDto) throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";
        int resultCode;
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            if (principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                resultCode = bddService.deleteBdd(bddDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 삭제 되었습니다.");
                } else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "삭제에 실패 하였습니다.");
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

}
