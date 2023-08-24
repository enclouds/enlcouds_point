package com.enclouds.enpoint.jackpot.controller;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.cmm.util.DateUtils;
import com.enclouds.enpoint.cmm.util.StringUtils;
import com.enclouds.enpoint.game.dto.BlindDto;
import com.enclouds.enpoint.game.dto.GameDto;
import com.enclouds.enpoint.game.dto.PrizeDto;
import com.enclouds.enpoint.jackpot.dto.JackpotDto;
import com.enclouds.enpoint.jackpot.service.JackpotService;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/jackpot")
public class JackpotController {

    @Autowired
    private UserService userService;

    @Autowired
    private JackpotService jackpotService;

    @Autowired
    private AgentService agentService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView jackpotList(HttpServletResponse response, @ModelAttribute JackpotDto jackpotDto) throws Exception{
        ModelAndView mv = new ModelAndView("jackpot/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                jackpotDto.setSchText(userId);

                List<JackpotDto> jackpotList = jackpotService.selectJackpotList(jackpotDto);
                mv.addObject("jackpotList", jackpotList);

            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", jackpotDto);

        return mv;
    }

    @PostMapping("/insertJackpotAjax")
    public @ResponseBody
    Map<String, Object> insertJackpotAjax(@ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
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

                jackpotDto.setStoreGbn(userId);

                resultCode = jackpotService.insertJackpot(jackpotDto);

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

    @PostMapping("/updateJackpotAjax")
    public @ResponseBody Map<String, Object> updateJackpotAjax(@ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
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

                resultCode = jackpotService.updateJackpot(jackpotDto);

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

    @PostMapping("/deleteJackpotAjax")
    public @ResponseBody Map<String, Object> deleteJackpotAjax(@ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
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

                resultCode = jackpotService.deleteJackpot(jackpotDto);

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

    @PostMapping("/jackpotAddPointAjax")
    public @ResponseBody Map<String, Object> jackpotAddPointAjax(@ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
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

                jackpotDto.setRegAgentCode(userInfo.getAgentCode());
                jackpotDto.setJackpotPrize(10000);
                jackpotDto.setJackpotGbn("A");
                jackpotDto.setAlertContent("[" + userInfo.getAgentName() + "]에서 JackPot 추가 10,000 적립 되었습니다.");

                resultCode = jackpotService.jackpotAddPointAjax(jackpotDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 잭팟이 추가 되었습니다.");
                } else {
                    if(resultCode == -2){
                        result.put("resultCode", -2);
                        result.put("resultMsg", "잭팟에 추가할 포인트가 보유포인트보다 작습니다. 확인 바랍니다.");
                    }else {
                        result.put("resultCode", -1);
                        result.put("resultMsg", "잭팟에 추가를 실패 하였습니다.");
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

    @RequestMapping(value = "/notiList", method = RequestMethod.GET)
    public ModelAndView notiList(HttpServletResponse response, @ModelAttribute JackpotDto jackpotDto) throws Exception{
        ModelAndView mv = new ModelAndView("jackpot/notiList");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);
                jackpotDto.setSchText(userId);

                List<JackpotDto> jackpotList = jackpotService.selectJackpotListTotal(jackpotDto);
                mv.addObject("jackpotList", jackpotList);

                if(jackpotList.size() > 0){
                    if(jackpotDto.getJackpotSeq() < 1){
                        jackpotDto.setJackpotSeq(((JackpotDto)jackpotList.get(0)).getSeq());
                    }
                }

                List<JackpotDto> notiList = jackpotService.selectJackpotNotiList(jackpotDto);
                mv.addObject("notiList", notiList);
                mv.addObject("jackpotSeq", jackpotDto.getJackpotSeq());
            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", jackpotDto);

        return mv;
    }

    @PostMapping("/insertNotiAjax")
    public @ResponseBody
    Map<String, Object> insertNotiAjax(@ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
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

                resultCode = jackpotService.insertNotiAjax(jackpotDto);

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

    @PostMapping("/updateNotiAjax")
    public @ResponseBody Map<String, Object> updateNotiAjax(@ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
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

                resultCode = jackpotService.updateNotiAjax(jackpotDto);

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

    @PostMapping("/deleteNotiAjax")
    public @ResponseBody Map<String, Object> deleteNotiAjax(@ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
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

                resultCode = jackpotService.deleteNotiAjax(jackpotDto);

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

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ModelAndView gamePlayList(HttpServletResponse response, @ModelAttribute JackpotDto jackpotDto) throws Exception{
        ModelAndView mv = new ModelAndView("jackpot/view");

        JackpotDto jackPotInfo = jackpotService.selectJackPotInfoTotal(jackpotDto);
        mv.addObject("jackPotInfo", jackPotInfo);

        List<JackpotDto> notiList = jackpotService.selectJackpotNotiListTotal(jackpotDto);
        mv.addObject("notiList", notiList);

        mv.addObject("today", jackPotInfo.getToday());

        return mv;
    }

    @PostMapping("/getPrizeInfoAjax")
    public @ResponseBody
    Map<String, Object> getPrizeInfoAjax(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        JackpotDto jackPotInfo = jackpotService.selectJackPotInfoTotal(jackpotDto);

        result.put("today", DateUtils.formatDate(DateUtils.getToday(), "-"));
        result.put("prize", jackPotInfo.getJackpotPrize());

        return result;
    }

    @PostMapping("/addJackpotAjax")
    public @ResponseBody Map<String, Object> addJackpotAjax(@ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
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

                resultCode = jackpotService.addJackpotAjax(jackpotDto);

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

    @PostMapping("/minusJackpotAjax")
    public @ResponseBody Map<String, Object> minusJackpotAjax(@ModelAttribute("jackpotDto") JackpotDto jackpotDto) throws Exception{
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

                resultCode = jackpotService.minusJackpotAjax(jackpotDto);

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

    @RequestMapping(value = "/history/list", method = RequestMethod.GET)
    public ModelAndView historyList(HttpServletResponse response, @ModelAttribute JackpotDto jackpotDto) throws Exception{
        ModelAndView mv = new ModelAndView("jackpot/historyList");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);
                jackpotDto.setRegAgentCode(userInfo.getAgentCode());

                mv.addObject("agentTotalList", agentService.selectAgentTotalListAsAG());

                if(StringUtils.isEmpty(jackpotDto.getSchCond1())){
                    jackpotDto.setSchCond1("");
                }

                String startDateStr = DateUtils.addDay(DateUtils.getToday(), -7);
                SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                Date startDateDte = format.parse(startDateStr);
                SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
                String startDate = format2.format(startDateDte);

                if(StringUtils.isEmpty(jackpotDto.getSchStartDte())){
                    jackpotDto.setSchStartDte(startDate);
                }

                Date todayDate = format.parse(DateUtils.getToday());
                String today = format2.format(todayDate);
                if(StringUtils.isEmpty(jackpotDto.getSchEndDte())){
                    jackpotDto.setSchEndDte(today);
                }

                List<JackpotDto> jackpotHistoryList = jackpotService.selectJackpotHistoryList(jackpotDto);
                mv.addObject("jackpotHistoryList", jackpotHistoryList);

            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", jackpotDto);

        return mv;
    }

}
