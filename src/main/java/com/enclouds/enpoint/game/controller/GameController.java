package com.enclouds.enpoint.game.controller;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.cmm.util.DateUtils;
import com.enclouds.enpoint.game.dto.BlindDto;
import com.enclouds.enpoint.game.dto.GameDto;
import com.enclouds.enpoint.game.dto.PrizeDto;
import com.enclouds.enpoint.game.service.GameService;
import com.enclouds.enpoint.jackpot.dto.JackpotDto;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView gameList(HttpServletResponse response, @ModelAttribute GameDto gameDto) throws Exception{
        ModelAndView mv = new ModelAndView("game/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                if(gameDto.getSchCond1() == null){
                    gameDto.setSchCond1("name");
                }

                gameDto.setSchCond2(userId);

                List<GameDto> gameList = gameService.selectGameList(gameDto);
                mv.addObject("gameList", gameList);

            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", gameDto);

        return mv;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView gameUserList(HttpServletResponse response, @ModelAttribute GameDto gameDto) throws Exception{
        ModelAndView mv = new ModelAndView("game/gameList");

        gameDto.setSchCond2("hunters");

        List<GameDto> gameList = gameService.selectGameList(gameDto);
        mv.addObject("gameList", gameList);

        return mv;
    }

    @RequestMapping(value = "/play", method = RequestMethod.GET)
    public ModelAndView gamePlayList(HttpServletResponse response, @ModelAttribute GameDto gameDto) throws Exception{
        ModelAndView mv = new ModelAndView("play/index");

        BlindDto blindDto = new BlindDto();
        blindDto.setGameSeq(gameDto.getSeq());
        List<BlindDto> blindList = gameService.selectBlindList(blindDto);

        PrizeDto prizeDto = new PrizeDto();
        prizeDto.setGameSeq(gameDto.getSeq());
        List<PrizeDto> prizeList1 = gameService.selectPrizeList1(prizeDto);
        List<PrizeDto> prizeList2 = gameService.selectPrizeList2(prizeDto);

        int totalPrize = 0;
        if(prizeList1 != null){
            for(int i=0 ; i<prizeList1.size() ; i++){
                totalPrize += ((PrizeDto)prizeList1.get(i)).getPrize();
            }
        }

        if(prizeList2 != null){
            for(int x=0 ; x<prizeList2.size() ; x++){
                totalPrize += ((PrizeDto)prizeList2.get(x)).getPrize();
            }
        }

        GameDto gameInfo = gameService.selectGameInfo(gameDto);
        String title = "";
        if(gameInfo.getRegUserId().equals("hunters")){
            title = "KINGS LOUNGE";
        }else if(gameInfo.getRegUserId().equals("raise")){
            title = "RAISE";
        }

        mv.addObject("blindList", blindList);
        mv.addObject("prizeList1", prizeList1);
        mv.addObject("prizeList2", prizeList2);
        mv.addObject("totalPrize", totalPrize);
        mv.addObject("gameInfo", gameInfo);
        mv.addObject("imgUrl", "/static/img/play/logo_"+gameInfo.getRegUserId()+".png");
        mv.addObject("title", title);

        return mv;
    }

    @PostMapping("/getPrizeInfoAjax")
    public @ResponseBody
    Map<String, Object> getPrizeInfoAjax(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("gameDto") GameDto gameDto) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();

        PrizeDto prizeDto = new PrizeDto();
        prizeDto.setGameSeq(gameDto.getSeq());
        List<PrizeDto> prizeList1 = gameService.selectPrizeList1(prizeDto);
        List<PrizeDto> prizeList2 = gameService.selectPrizeList2(prizeDto);

        int totalPrize = 0;
        if(prizeList1 != null){
            for(int i=0 ; i<prizeList1.size() ; i++){
                totalPrize += ((PrizeDto)prizeList1.get(i)).getPrize();
            }
        }

        if(prizeList2 != null){
            for(int x=0 ; x<prizeList2.size() ; x++){
                totalPrize += ((PrizeDto)prizeList2.get(x)).getPrize();
            }
        }

        result.put("prizeList1", prizeList1);
        result.put("prizeList2", prizeList2);
        result.put("totalPrize", totalPrize);

        return result;
    }

    @RequestMapping(value = "/blind/list", method = RequestMethod.GET)
    public ModelAndView gameBlindList(HttpServletResponse response, @ModelAttribute BlindDto blindDto) throws Exception{
        ModelAndView mv = new ModelAndView("game/blind/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                GameDto gameDto = new GameDto();
                gameDto.setSchCond2(userId);

                List<GameDto> gameList = gameService.selectGameListTotal(gameDto);
                mv.addObject("gameList", gameList);

                if(gameList.size() > 0){
                    if(blindDto.getGameSeq() == null){
                        blindDto.setGameSeq(((GameDto)gameList.get(0)).getSeq());
                    }
                }

                List<BlindDto> blindList = gameService.selectBlindList(blindDto);
                mv.addObject("blindList", blindList);
                mv.addObject("gameSeq", blindDto.getGameSeq());
            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", blindDto);

        return mv;
    }

    @RequestMapping(value = "/prize/list", method = RequestMethod.GET)
    public ModelAndView gameprizeList(HttpServletResponse response, @ModelAttribute PrizeDto prizeDto) throws Exception{
        ModelAndView mv = new ModelAndView("game/prize/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                GameDto gameDto = new GameDto();
                gameDto.setSchCond2(userId);

                List<GameDto> gameList = gameService.selectGameListTotal(gameDto);
                mv.addObject("gameList", gameList);

                if(gameList.size() > 0){
                    if(prizeDto.getGameSeq() == null){
                        prizeDto.setGameSeq(((GameDto)gameList.get(0)).getSeq());
                    }
                }

                List<PrizeDto> prizeList = gameService.selectPrizeList(prizeDto);
                mv.addObject("prizeList", prizeList);
                mv.addObject("gameSeq", prizeDto.getGameSeq());
            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", prizeDto);

        return mv;
    }

    @PostMapping("/insertGameAjax")
    public @ResponseBody
    Map<String, Object> insertGameAjax(@ModelAttribute("gameDto") GameDto gameDto) throws Exception{
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
                gameDto.setRegUserId(userId);
                resultCode = gameService.insertGame(gameDto);

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

    @PostMapping("/blind/insertBlindAjax")
    public @ResponseBody
    Map<String, Object> insertBlindAjax(@ModelAttribute("blindDto") BlindDto blindDto) throws Exception{
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

                resultCode = gameService.insertBlind(blindDto);

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

    @PostMapping("/prize/insertPrizeAjax")
    public @ResponseBody
    Map<String, Object> insertPrizeAjax(@ModelAttribute("prizeDto") PrizeDto prizeDto) throws Exception{
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

                resultCode = gameService.insertPrize(prizeDto);

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

    @PostMapping("/updateGameAjax")
    public @ResponseBody Map<String, Object> updateGameAjax(@ModelAttribute("gameDto") GameDto gameDto) throws Exception{
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

                resultCode = gameService.updateGame(gameDto);

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

    @PostMapping("/blind/updateBlindAjax")
    public @ResponseBody Map<String, Object> updateBlindAjax(@ModelAttribute("blindDto") BlindDto blindDto) throws Exception{
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

                resultCode = gameService.updateBlind(blindDto);

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

    @PostMapping("/prize/updatePrizeAjax")
    public @ResponseBody Map<String, Object> updatePrizeAjax(@ModelAttribute("prizeDto") PrizeDto prizeDto) throws Exception{
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

                resultCode = gameService.updatePrize(prizeDto);

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

    @PostMapping("/deleteGameAjax")
    public @ResponseBody Map<String, Object> deleteGameAjax(@ModelAttribute("gameDto") GameDto gameDto) throws Exception{
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

                resultCode = gameService.deleteGame(gameDto);

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

    @PostMapping("/blind/deleteBlindAjax")
    public @ResponseBody Map<String, Object> deleteBlindAjax(@ModelAttribute("blindDto") BlindDto blindDto) throws Exception{
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

                resultCode = gameService.deleteBlind(blindDto);

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

    @PostMapping("/prize/deletePrizeAjax")
    public @ResponseBody Map<String, Object> deletePrizeAjax(@ModelAttribute("prizeDto") PrizeDto prizeDto) throws Exception{
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

                resultCode = gameService.deletePrize(prizeDto);

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
