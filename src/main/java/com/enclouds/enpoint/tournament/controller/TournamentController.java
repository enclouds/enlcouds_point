package com.enclouds.enpoint.tournament.controller;

import com.enclouds.enpoint.game.dto.BlindDto;
import com.enclouds.enpoint.game.dto.GameDto;
import com.enclouds.enpoint.tournament.dto.TournamentDto;
import com.enclouds.enpoint.tournament.service.EscposFormatter;
import com.enclouds.enpoint.tournament.service.TournamentService;
import com.enclouds.enpoint.user.dto.PointDto;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.UserService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.print.*;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tournament")
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private UserService userService;

    @Autowired
    private EscposFormatter escposFormatter;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView tournamentList(HttpServletResponse response, @ModelAttribute TournamentDto tournamentDto) throws Exception{
        ModelAndView mv = new ModelAndView("tournament/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                if(tournamentDto.getSchCond1() == null){
                    tournamentDto.setSchCond1("name");
                }

                List<TournamentDto> tournamentList = tournamentService.selectTournamentList(tournamentDto);

                mv.addObject("tournamentList", tournamentList);

            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", tournamentDto);

        return mv;
    }

    @RequestMapping(value = "/tournamentRegList", method = RequestMethod.GET)
    public ModelAndView tournamentRegList(HttpServletResponse response, @ModelAttribute TournamentDto tournamentDto) throws Exception{
        ModelAndView mv = new ModelAndView("tournament/reg/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                List<TournamentDto> tournamentRegList = tournamentService.selectTournamentRegList(tournamentDto);

                mv.addObject("tournamentRegList", tournamentRegList);

            }else {
                return new ModelAndView("redirect:/");
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        mv.addObject("userInfo", userInfo);
        mv.addObject("params", tournamentDto);

        return mv;
    }

    @PostMapping("/insertTournamentAjax")
    public @ResponseBody
    Map<String, Object> insertTournamentAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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
                tournamentDto.setRegId(userId);
                resultCode = tournamentService.insertTournament(tournamentDto);

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

    @PostMapping("/updateTournamentAjax")
    public @ResponseBody Map<String, Object> updateTournamentAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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

                resultCode = tournamentService.updateTournament(tournamentDto);

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

    @PostMapping("/deleteTournamentAjax")
    public @ResponseBody Map<String, Object> deleteTournamentAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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

                resultCode = tournamentService.deleteTournament(tournamentDto);

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

    @PostMapping("/registrationAjax")
    public @ResponseBody
    Map<String, Object> registrationAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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

                tournamentDto.setRegId(userId);
                tournamentDto.setAgentCode(userInfo.getAgentCode());
                resultCode = tournamentService.registration(tournamentDto);

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

    @PostMapping("/buyin/print")
    @ResponseBody
    public String printBuyIn(@RequestBody TournamentDto dto) {
        return escposFormatter.generateBuyInReceipt(dto);
    }

    @GetMapping(value = "/registerList/buildNDownload")
    public void registerListBuildNDownload(HttpServletResponse response, @ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if (principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                List<TournamentDto> registerList = tournamentService.selectTournamentRegList(tournamentDto);

                if(!registerList.isEmpty()){
                    String fileName = registerList.get(0).getTournamentName() + "_" + registerList.get(0).getStartDate();
                    response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("utf-8"),"8859_1") +".xlsx");

                    Workbook workbook = new XSSFWorkbook();
                    DecimalFormat decimalFormat = new DecimalFormat("###,###");

                    Sheet sheet = workbook.createSheet(registerList.get(0).getTournamentName());
                    Row header = sheet.createRow(0);

                    header.createCell(0).setCellValue("Register No.");
                    header.createCell(1).setCellValue("핸드폰 번호");
                    header.createCell(2).setCellValue("닉네임");
                    header.createCell(3).setCellValue("레지 시간");
                    header.createCell(4).setCellValue("Entry");
                    header.createCell(5).setCellValue("사용 티켓");

                    int rowNum = 1;
                    for (int i = 0; i < registerList.size(); i++) {
                        TournamentDto dto = registerList.get(i);
                        Row row = sheet.createRow(rowNum++);

                        row.createCell(0).setCellValue(dto.getRnum());
                        row.createCell(1).setCellValue(dto.getRegPhoneNum());
                        row.createCell(2).setCellValue(dto.getNickName());
                        row.createCell(3).setCellValue(dto.getRegDate());
                        row.createCell(4).setCellValue(dto.getEntryCount());
                        row.createCell(5).setCellValue(dto.getInfoDesc());
                    }

                    workbook.write(response.getOutputStream());
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }
    }


}
