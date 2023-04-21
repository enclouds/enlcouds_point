package com.enclouds.enpoint.user.controller;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.cmm.login.service.LoginService;
import com.enclouds.enpoint.cmm.util.DateUtils;
import com.enclouds.enpoint.cmm.util.StringUtils;
import com.enclouds.enpoint.jackpot.dto.JackpotDto;
import com.enclouds.enpoint.jackpot.service.JackpotService;
import com.enclouds.enpoint.user.dto.LoginDto;
import com.enclouds.enpoint.user.dto.PointDto;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.CustomUserService;
import com.enclouds.enpoint.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자 정보 Controller
 * @author Enclouds
 * @since 2020.12.11
 */

@Controller
public class UserController {

   @Autowired
   private LoginService loginService;

   @Autowired
   private UserService userService;

   @Autowired
   private CustomUserService customUserService;

   @Autowired
   private JackpotService jackpotService;

   /**
    * 로그인 페이지 이동
    *
    * @return
    */
   @RequestMapping("/login")
   public String login(){
      return "index.html";
   }

   /**
    * 로그아웃 처리
    *
    * @return
    */
   @GetMapping("/logout")
   public String logout(){

      return "index.html";
   }

   @RequestMapping("/loginDirect")
   public ModelAndView loginDirect(@ModelAttribute LoginDto loginDto) throws Exception{
      ModelAndView mv = new ModelAndView("/login.html");

      LoginDto loginInfo = loginService.selectPaymoaLoginInfo(loginDto);

      mv.addObject("loginDto",loginInfo);
      return mv;
   }

   @RequestMapping(value = "/user/list", method = RequestMethod.GET)
   public ModelAndView userList(HttpServletResponse response, @ModelAttribute UserDto userDto) throws Exception{
      ModelAndView mv = new ModelAndView("user/list");

      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserDto userInfo = null;
      String userId = "";

      try {
         if(principal != "anonymousUser") {
            UserDetails userDetails = (UserDetails) principal;
            userId = userDetails.getUsername();
            userInfo = userService.getUserInfo(userId);

            if(userId.equals("raise")){
               return new ModelAndView("redirect:/game/list");
            }

            userDto.setAgentCode(userInfo.getAgentCode());

            if(userDto.getSchCond1() == null){
               userDto.setSchCond1("nick");
            }

            List<UserDto> userList = customUserService.selectCustomUserList(userDto);
            mv.addObject("userList", userList);

            //잭팟 정보(사용중중 최근 잭팟)
            JackpotDto jackpotDto = new JackpotDto();
            jackpotDto.setStoreGbn("hunters");
            JackpotDto jackpotInfo = jackpotService.selectJackPotInfo(jackpotDto);
            mv.addObject("jackpotInfo", jackpotInfo);
         }else {
            return new ModelAndView("redirect:/");
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      mv.addObject("userInfo", userInfo);
      mv.addObject("params", userDto);

      return mv;
   }

   @PostMapping("/user/insertUserAjax")
   public @ResponseBody Map<String, Object> insertUserAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            int cnt = customUserService.selectDuplUser(userDto);

            if(cnt > 0){
               result.put("resultCode", -1);
               result.put("resultMsg", "이미 존재하는 전화번호 입니다.");
            }else {
               userDto.setAgentCode(userInfo.getAgentCode());
               userDto.setAgentName(userInfo.getAgentName());
               resultCode = customUserService.insertUser(userDto);

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

   @PostMapping("/user/updateUserAjax")
   public @ResponseBody Map<String, Object> updateUserAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            resultCode = customUserService.updateUser(userDto);

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

   @PostMapping("/user/deleteUserAjax")
   public @ResponseBody Map<String, Object> deleteUserAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            resultCode = customUserService.deleteUser(userDto);

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

   @PostMapping("/user/updateUserAddPointAjax")
   public @ResponseBody Map<String, Object> updateUserAddPointAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            userDto.setAgentCode(userInfo.getAgentCode());

            resultCode = customUserService.updateUserAddPoint(userDto);

            if (resultCode > 0) {
               result.put("resultCode", 0);
               result.put("resultMsg", "정상적으로 적립 되었습니다.");
            } else {
               if(resultCode == -2){
                  result.put("resultCode", -2);
                  result.put("resultMsg", "적립할 포인트가 보유포인트보다 작습니다. 확인 바랍니다.");
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

   @PostMapping("/user/useTicketAjax")
   public @ResponseBody Map<String, Object> useTicketAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            userDto.setAgentCode(userInfo.getAgentCode());

            resultCode = customUserService.useTicket(userDto);

            if (resultCode > 0) {
               result.put("resultCode", 0);
               result.put("resultMsg", "정상적으로 사용 되었습니다.");
            } else {
                  result.put("resultCode", -1);
                  result.put("resultMsg", "사용에 실패 하였습니다.");
            }
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      return result;
   }

   @PostMapping("/user/updateUserAddCouponPointAjax")
   public @ResponseBody Map<String, Object> updateUserAddCouponPointAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

               userDto.setAgentCode(userInfo.getAgentCode());

               resultCode = customUserService.updateUserAddCouponPoint(userDto);

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

   @PostMapping("/user/updateUserAddTicketAjax")
   public @ResponseBody Map<String, Object> updateUserAddTicketAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            userDto.setAgentCode(userInfo.getAgentCode());

            resultCode = customUserService.updateUserAddTicket(userDto);

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

   @PostMapping("/user/updateUserAddRankPointAjax")
   public @ResponseBody Map<String, Object> updateUserAddRankPointAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            userDto.setAgentCode(userInfo.getAgentCode());

            resultCode = customUserService.updateUserAddRankPoint(userDto);

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

   @PostMapping("/user/updateUserMinusRankPointAjax")
   public @ResponseBody Map<String, Object> updateUserMinusRankPointAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            userDto.setAgentCode(userInfo.getAgentCode());

            resultCode = customUserService.updateUserMinusRankPoint(userDto);

            if (resultCode > 0) {
               result.put("resultCode", 0);
               result.put("resultMsg", "정상적으로 차감 되었습니다.");
            } else {
               result.put("resultCode", -1);
               result.put("resultMsg", "적립에 차감 하였습니다.");
            }
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      return result;
   }

   @PostMapping("/user/updateUserRankDefAjax")
   public @ResponseBody Map<String, Object> updateUserRankDefAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            userDto.setAgentCode(userInfo.getAgentCode());

            resultCode = customUserService.updateUserRankDefAjax(userDto);

            if (resultCode > 0) {
               result.put("resultCode", 0);
               result.put("resultMsg", "정상적으로 초기화 되었습니다.");
            } else {
               result.put("resultCode", -1);
               result.put("resultMsg", "초기화에 실패 하였습니다.");
            }
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      return result;
   }

   @PostMapping("/user/updateAllUserRankMoveAjax")
   public @ResponseBody Map<String, Object> updateAllUserRankMoveAjax() throws Exception{
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

            resultCode = customUserService.updateAllUserRankMoveAjax();

            if (resultCode > 0) {
               result.put("resultCode", 0);
               result.put("resultMsg", "정상적으로 이관 되었습니다.");
            } else {
               result.put("resultCode", -1);
               result.put("resultMsg", "이관에 실패 하였습니다.");
            }
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      return result;
   }

   @PostMapping("/user/updateAllUserWeekRankDefAjax")
   public @ResponseBody Map<String, Object> updateAllUserWeekRankDefAjax() throws Exception{
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

            resultCode = customUserService.updateAllUserWeekRankDefAjax();

            if (resultCode > 0) {
               result.put("resultCode", 0);
               result.put("resultMsg", "정상적으로 초기화 되었습니다.");
            } else {
               result.put("resultCode", -1);
               result.put("resultMsg", "초기화에 실패 하였습니다.");
            }
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      return result;
   }

   @PostMapping("/user/updateAllUserRankDefAjax")
   public @ResponseBody Map<String, Object> updateAllUserRankDefAjax() throws Exception{
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

            resultCode = customUserService.updateAllUserRankDefAjax();

            if (resultCode > 0) {
               result.put("resultCode", 0);
               result.put("resultMsg", "정상적으로 초기화 되었습니다.");
            } else {
               result.put("resultCode", -1);
               result.put("resultMsg", "초기화에 실패 하였습니다.");
            }
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      return result;
   }

   @PostMapping("/user/updateUserMinusPointAjax")
   public @ResponseBody Map<String, Object> updateUserMinusPointAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            userDto.setAgentCode(userInfo.getAgentCode());

            resultCode = customUserService.updateUserMinusPoint(userDto);

            if (resultCode > 0) {
               result.put("resultCode", 0);
               result.put("resultMsg", "정상적으로 차감 되었습니다.");
            } else {
               if(resultCode == -2){
                  result.put("resultCode", -2);
                  result.put("resultMsg", "차감할 포인트가 보유포인트보다 많습니다. 확인 바랍니다.");
               }else {
                  result.put("resultCode", -1);
                  result.put("resultMsg", "차감에 실패 하였습니다.");
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

   @PostMapping("/user/updateUserMinusCouponPointAjax")
   public @ResponseBody Map<String, Object> updateUserMinusCouponPointAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

                  userDto.setAgentCode(userInfo.getAgentCode());

                  resultCode = customUserService.updateUserMinusCouponPoint(userDto);

                  if (resultCode > 0) {
                        result.put("resultCode", 0);
                        result.put("resultMsg", "정상적으로 차감 되었습니다.");
                  } else {
                        if(resultCode == -2){
                           result.put("resultCode", -2);
                           result.put("resultMsg", "차감할 쿠폰포인트가 보유 쿠폰포인트보다 많습니다. 확인 바랍니다.");
                        }else {
                           result.put("resultCode", -1);
                           result.put("resultMsg", "차감에 실패 하였습니다.");
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

   @PostMapping("/user/updateUserHumanAjax")
   public @ResponseBody Map<String, Object> updateUserHumanAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
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

            userDto.setAgentCode(userInfo.getAgentCode());

            resultCode = customUserService.updateUserHumanAjax(userDto);

            if (resultCode > 0) {
               result.put("resultCode", 0);
               result.put("resultMsg", "정상적으로 휴먼처리 되었습니다.");
            } else {
               result.put("resultCode", -1);
               result.put("resultMsg", "휴먼처리에 실패 하였습니다.");
            }
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      return result;
   }

   @PostMapping("/user/selectPointHistoryAjax")
   public @ResponseBody Map<String, Object> selectPointHistoryAjax(@ModelAttribute("userDto") UserDto userDto) throws Exception{
      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserDto userInfo = null;
      String userId = "";

      Map<String, Object> result = new HashMap<String, Object>();

      try {
         if (principal != "anonymousUser") {
            UserDetails userDetails = (UserDetails) principal;
            userId = userDetails.getUsername();
            userInfo = userService.getUserInfo(userId);

            List<PointDto> historyPointList = customUserService.selectPointHistory(userDto);

            result.put("historyPointList", historyPointList);
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      return result;
   }

   @RequestMapping(value = "/user/point/list", method = RequestMethod.GET)
   public ModelAndView userPointList(HttpServletResponse response, @ModelAttribute PointDto pointDto) throws Exception{
      ModelAndView mv = new ModelAndView("user/point/list");

      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserDto userInfo = null;
      String userId = "";

      try {
         if(principal != "anonymousUser") {
            UserDetails userDetails = (UserDetails) principal;
            userId = userDetails.getUsername();
            userInfo = userService.getUserInfo(userId);

            pointDto.setAgentCode(String.valueOf(userInfo.getAgentCode()));

            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Date todayDate = format.parse(DateUtils.getToday());
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            String today = format2.format(todayDate);

            if(StringUtils.isEmpty(pointDto.getSchDate())){
               pointDto.setSchDate(today);
            }

            List<PointDto> userPointList = customUserService.selectUserPointList(pointDto);
            mv.addObject("userPointList", userPointList);

            PointDto totalPoint = customUserService.selectUserPointTotal(pointDto);
            mv.addObject("totalPoint", totalPoint);
         }else {
            return new ModelAndView("redirect:/");
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      mv.addObject("userInfo", userInfo);
      mv.addObject("params", pointDto);

      return mv;
   }

   @RequestMapping(value = "/user/rank/list", method = RequestMethod.GET)
   public ModelAndView userRankList(HttpServletResponse response, @ModelAttribute UserDto userDto) throws Exception{
      ModelAndView mv = new ModelAndView("user/rank/list");

      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserDto userInfo = null;
      String userId = "";

      try {
         if(principal != "anonymousUser") {
            UserDetails userDetails = (UserDetails) principal;
            userId = userDetails.getUsername();
            userInfo = userService.getUserInfo(userId);

            if(userDto.getSchCond1() == null){
               userDto.setSchCond1("week");
            }

            List<UserDto> userRankList = customUserService.selectUserRankList(userDto);
            mv.addObject("userRankList", userRankList);
         }else {
            return new ModelAndView("redirect:/");
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      mv.addObject("userInfo", userInfo);
      mv.addObject("params", userDto);

      return mv;
   }

   @RequestMapping(value = "/user/ticket/rank/list", method = RequestMethod.GET)
   public ModelAndView userTicketRankList(HttpServletResponse response, @ModelAttribute UserDto userDto) throws Exception{
      ModelAndView mv = new ModelAndView("user/ticket/rank/list");

      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      UserDto userInfo = null;
      String userId = "";

      try {
         if(principal != "anonymousUser") {
            UserDetails userDetails = (UserDetails) principal;
            userId = userDetails.getUsername();
            userInfo = userService.getUserInfo(userId);

            List<UserDto> userTicketRankList = customUserService.selectUserTicketRankList(userDto);
            mv.addObject("userTicketRankList", userTicketRankList);
         }else {
            return new ModelAndView("redirect:/");
         }
      } catch (ClassCastException cce){
         DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
         userId = auth2User.getName();
         userInfo = userService.getUserInfo(userId);
      }

      mv.addObject("userInfo", userInfo);
      mv.addObject("params", userDto);

      return mv;
   }

   @RequestMapping(value = "/user/privacy", method = RequestMethod.GET)
   public ModelAndView userPrivacy(HttpServletResponse response, @ModelAttribute UserDto userDto) throws Exception{
      ModelAndView mv = new ModelAndView("user/privacy");

      return mv;
   }

}
