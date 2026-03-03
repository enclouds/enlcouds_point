package com.enclouds.enpoint.univ.contorller;

import com.enclouds.enpoint.univ.dto.UnivDto;
import com.enclouds.enpoint.univ.service.UnivService;
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
@RequestMapping("/univ")
public class UnivController {

    @Autowired
    private UnivService univService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView univList(HttpServletResponse response, @ModelAttribute UnivDto univDto) throws Exception{
        ModelAndView mv = new ModelAndView("univ/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                if(univDto.getSchCond1() == null){
                    univDto.setSchCond1("name");
                }

                List<UnivDto> univList = univService.selectUnivList(univDto);
                mv.addObject("univList", univList);

            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", univDto);

        return mv;
    }

    @PostMapping("/insertUnivAjax")
    public @ResponseBody
    Map<String, Object> insertUnivAjax(@ModelAttribute("univDto") UnivDto univDto) throws Exception{
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

                resultCode = univService.insertUniv(univDto);

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

    @PostMapping("/updateUnivAjax")
    public @ResponseBody Map<String, Object> updateUnivAjax(@ModelAttribute("univDto") UnivDto univDto) throws Exception{
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

                resultCode = univService.updateUniv(univDto);

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

    @PostMapping("/deleteUnivAjax")
    public @ResponseBody Map<String, Object> deleteUnivAjax(@ModelAttribute("univDto") UnivDto univDto) throws Exception{
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

                resultCode = univService.deleteUniv(univDto);

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

    @PostMapping("/updateUnivAddTicketAjax")
    public @ResponseBody Map<String, Object> updateUnivAddTicketAjax(@ModelAttribute("univDto") UnivDto univDto) throws Exception{
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

                univDto.setAgentCode(userInfo.getAgentCode());

                resultCode = univService.updateUnivAddTicket(univDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 적립 되었습니다.");
                } else {
                    if(resultCode == -2){
                        result.put("resultCode", -2);
                        result.put("resultMsg", "적립할 티켓이 보유티켓보다 작습니다. 확인 바랍니다.");
                    }else {
                        result.put("resultCode", -1);
                        result.put("resultMsg", "적립에 실패 하였습니다.");
                    }
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

    @PostMapping("/updateUnivMinusTicketAjax")
    public @ResponseBody Map<String, Object> updateUnivMinusTicketAjax(@ModelAttribute("univDto") UnivDto univDto) throws Exception{
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

                univDto.setAgentCode(userInfo.getAgentCode());

                resultCode = univService.updateUnivMinusTicket(univDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 차감 되었습니다.");
                } else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "차감에 실패 하였습니다.");
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
