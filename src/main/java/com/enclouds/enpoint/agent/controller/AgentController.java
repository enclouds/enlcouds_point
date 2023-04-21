package com.enclouds.enpoint.agent.controller;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.user.dto.PointDto;
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
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView agentList(HttpServletResponse response, @ModelAttribute AgentDto agentDto) throws Exception{
        ModelAndView mv = new ModelAndView("agent/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                agentDto.setSchAgentCode(userInfo.getAgentCode());

                if(agentDto.getSchCond1() == null){
                    agentDto.setSchCond1("name");
                }

                List<AgentDto> agentList = agentService.selectAgentList(agentDto);
                mv.addObject("agentList", agentList);

            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", agentDto);

        return mv;
    }

    @PostMapping("/insertAgentAjax")
    public @ResponseBody
    Map<String, Object> insertAgentAjax(@ModelAttribute("agentDto") AgentDto agentDto) throws Exception{
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

                int cnt = agentService.selectDuplAgent(agentDto);

                if(cnt > 0){
                    result.put("resultCode", -1);
                    result.put("resultMsg", "이미 존재하는 ID 입니다.");
                }else {
                    agentDto.setAgentUpCode(userInfo.getAgentCode());

                    resultCode = agentService.insertAgent(agentDto);

                    if (resultCode > 0) {
                        result.put("resultCode", 0);
                        result.put("resultMsg", "정상적으로 등록 되었습니다.");
                    } else {
                        result.put("resultCode", -1);
                        result.put("resultMsg", "등록에 실패 하였습니다.");
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

    @PostMapping("/updateAgentAjax")
    public @ResponseBody Map<String, Object> updateAgentAjax(@ModelAttribute("agentDto") AgentDto agentDto) throws Exception{
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

                resultCode = agentService.updateAgent(agentDto);

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

    @PostMapping("/deleteAgentAjax")
    public @ResponseBody Map<String, Object> deleteAgentAjax(@ModelAttribute("agentDto") AgentDto agentDto) throws Exception{
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

                resultCode = agentService.deleteAgent(agentDto);

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

    @PostMapping("/updateAgentAddPointAjax")
    public @ResponseBody Map<String, Object> updateAgentAddPointAjax(@ModelAttribute("agentDto") AgentDto agentDto) throws Exception{
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

                resultCode = agentService.updateAgentAddPoint(agentDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 적립 되었습니다.");
                } else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "적립에 실패 하였습니다.");
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

    @PostMapping("/updateAgentAddTicketAjax")
    public @ResponseBody Map<String, Object> updateAgentAddTicketAjax(@ModelAttribute("agentDto") AgentDto agentDto) throws Exception{
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

                resultCode = agentService.updateAgentAddTicket(agentDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 적립 되었습니다.");
                } else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "적립에 실패 하였습니다.");
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

    @PostMapping("/updateAgentMinusPointAjax")
    public @ResponseBody Map<String, Object> updateAgentMinusPointAjax(@ModelAttribute("agentDto") AgentDto agentDto) throws Exception{
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

                resultCode = agentService.updateAgentMinusPoint(agentDto);

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

    @PostMapping("/updateAgentMinusTicketAjax")
    public @ResponseBody Map<String, Object> updateAgentMinusTicketAjax(@ModelAttribute("agentDto") AgentDto agentDto) throws Exception{
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

                resultCode = agentService.updateAgentMinusTicket(agentDto);

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

    @PostMapping("/selectAgentPointHistoryAjax")
    public @ResponseBody Map<String, Object> selectAgentPointHistoryAjax(@ModelAttribute("agentDto") AgentDto agentDto) throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            if (principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                List<PointDto> historyPointList = agentService.selectAgentPointHistory(agentDto);

                result.put("historyPointList", historyPointList);
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

    @PostMapping("/selectAgentTicketHistoryAjax")
    public @ResponseBody Map<String, Object> selectAgentTicketHistoryAjax(@ModelAttribute("agentDto") AgentDto agentDto) throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        Map<String, Object> result = new HashMap<String, Object>();

        try {
            if (principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                List<PointDto> historyTicketList = agentService.selectAgentTicketHistory(agentDto);

                result.put("historyTicketList", historyTicketList);
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

}
