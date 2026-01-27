package com.enclouds.enpoint.tournament.controller;

import com.enclouds.enpoint.tournament.dto.TournamentDto;
import com.enclouds.enpoint.tournament.service.EscposFormatter;
import com.enclouds.enpoint.tournament.service.TournamentService;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

                List<TournamentDto> tournamentRegList = new ArrayList<>();

                if(tournamentDto.getGbn().equals("N")){
                    tournamentRegList = tournamentService.selectTournamentRegList(tournamentDto);
                }else {
                    tournamentRegList = tournamentService.selectTournamentRegListUniv(tournamentDto);
                }

                TournamentDto totalInfo = tournamentService.selectTournamentRegTotalCnt(tournamentDto);

                mv.addObject("tournamentRegList", tournamentRegList);
                mv.addObject("totalInfo", totalInfo);
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

    @RequestMapping(value = "/tournamentPrizeList", method = RequestMethod.GET)
    public ModelAndView tournamentPrizeList(HttpServletResponse response, @ModelAttribute TournamentDto tournamentDto) throws Exception{
        ModelAndView mv = new ModelAndView("tournament/prize/list");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if(principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                List<TournamentDto> tournamentPrizeList = new ArrayList<>();

                if(tournamentDto.getGbn().equals("U")){
                    tournamentPrizeList = tournamentService.selectTournamentPrizeListUniv(tournamentDto);
                }else {
                    tournamentPrizeList = tournamentService.selectTournamentPrizeList(tournamentDto);
                }

                mv.addObject("tournamentPrizeList", tournamentPrizeList);
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

    @PostMapping("/updateMemoAjax")
    public @ResponseBody Map<String, Object> updateMemoAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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

                resultCode = tournamentService.updateMemo(tournamentDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 저장 되었습니다.");
                } else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "저장에 실패 하였습니다.");
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

                boolean hasRole = userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_GAME") || auth.getAuthority().equals("ROLE_ADMIN"));

                if(hasRole){
                    tournamentDto.setRegId(userId);
                    tournamentDto.setAgentCode(userInfo.getAgentCode());
                    HashMap<String, Object> map = tournamentService.registration(tournamentDto);

                    if ((Integer) map.get("resultCode") > 0) {
                        result.put("resultCode", 0);
                        result.put("resultMsg", "정상적으로 등록 되었습니다.");
                        result.put("seq", map.get("seq"));
                    } else {
                        result.put("resultCode", -1);
                        result.put("resultMsg", "등록에 실패 하였습니다.");
                    }
                }else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "권한이 없습니다. 로그인 정보를 확인 하여 주십시오.");
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

    @PostMapping("/prizeInsertAjax")
    public @ResponseBody
    Map<String, Object> prizeInsertAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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

                tournamentDto.setRegUserId(userId);
                tournamentDto.setAgentCode(userInfo.getAgentCode());
                resultCode = tournamentService.prizeInsert(tournamentDto);

                /**
                 * 대학부 KLPI 점수 적용
                 */


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

    @PostMapping("/updatePrizeAjax")
    public @ResponseBody Map<String, Object> updatePrizeAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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

                resultCode = tournamentService.updatePrize(tournamentDto);

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

    @PostMapping("/updateAddPrizeAjax")
    public @ResponseBody Map<String, Object> updateAddPrizeAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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
                tournamentDto.setAgentCode(userInfo.getAgentCode());

                resultCode = tournamentService.updateAddPrize(tournamentDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 지급처리 되었습니다.");
                } else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "지급처리에 실패 하였습니다.");
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }

        return result;
    }

    @PostMapping("/deletePrizeAjax")
    public @ResponseBody Map<String, Object> deletePrizeAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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

                resultCode = tournamentService.deletePrize(tournamentDto);

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

    @PostMapping("/buyin/print")
    public @ResponseBody
    Map<String, Object> getPrintInfoAjax(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();

        TournamentDto printData = tournamentService.selectPrintInfo(tournamentDto);
        result.put("printData", printData);

        return result;
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

    @GetMapping(value = "/prize/list/buildNDownload")
    public void prizeListBuildNDownload(HttpServletResponse response, @ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userInfo = null;
        String userId = "";

        try {
            if (principal != "anonymousUser") {
                UserDetails userDetails = (UserDetails) principal;
                userId = userDetails.getUsername();
                userInfo = userService.getUserInfo(userId);

                List<TournamentDto> prizeList = tournamentService.selectTournamentPrizeList(tournamentDto);

                if(!prizeList.isEmpty()){
                    String fileName = "프라이즈_"+prizeList.get(0).getTournamentName()+"_"+System.currentTimeMillis();
                    response.setHeader("Content-disposition", "attachment; filename="+new String(fileName.getBytes("utf-8"),"8859_1") +".xlsx");

                    Workbook workbook = new XSSFWorkbook();
                    DecimalFormat decimalFormat = new DecimalFormat("###,###");

                    Sheet sheet = workbook.createSheet(prizeList.get(0).getTournamentName());

                    /* ==========================================
                     *   ▶▶ 제목 추가 (0번 Row)
                     * ========================================== */
                    Row titleRow = sheet.createRow(0);
                    titleRow.setHeightInPoints(40); // 제목 높이

                    Cell titleCell = titleRow.createCell(0);
                    titleCell.setCellValue(prizeList.get(0).getStartDate() + " " + prizeList.get(0).getTournamentName() + " 입상자 명단");

                    // 0~2 컬럼 병합
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

                    // 제목 스타일
                    CellStyle titleStyle = workbook.createCellStyle();
                    titleStyle.setAlignment(HorizontalAlignment.CENTER);
                    titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

                    Font titleFont = workbook.createFont();
                    titleFont.setFontHeightInPoints((short) 22);
                    titleFont.setBold(true);
                    titleStyle.setFont(titleFont);

                    // 병합된 시작 셀에만 스타일 적용 (안전)
                    titleCell.setCellStyle(titleStyle);

                    /* ==========================================
                     *   ▶▶ 기존 헤더는 1번 Row로 이동
                     * ========================================== */
                    Row header = sheet.createRow(1);

                    header.createCell(0).setCellValue("등수");
                    header.createCell(1).setCellValue("이름");
                    header.createCell(2).setCellValue("닉네임");
                    header.createCell(3).setCellValue("자점");
                    header.createCell(4).setCellValue("전화번호");
                    header.createCell(5).setCellValue("주민번호");
                    header.createCell(6).setCellValue("계좌번호");
                    header.createCell(7).setCellValue("상금");
                    header.createCell(8).setCellValue("추가시상");

                    /* ==========================================
                     *   ▶▶ 데이터는 2번 Row부터
                     * ========================================== */
                    int rowNum = 2;
                    for (int i = 0; i < prizeList.size(); i++) {
                        TournamentDto dto = prizeList.get(i);
                        Row row = sheet.createRow(rowNum++);

                        row.createCell(0).setCellValue(dto.getRankNum());
                        row.createCell(1).setCellValue(dto.getRegName());
                        row.createCell(2).setCellValue(dto.getNickName());
                        row.createCell(3).setCellValue(dto.getAgentName());
                        row.createCell(4).setCellValue(dto.getPhoneNum());
                        row.createCell(5).setCellValue(dto.getRegId());
                        row.createCell(6).setCellValue(dto.getBankNum());
                        row.createCell(7).setCellValue(dto.getPrizeStr());
                        row.createCell(8).setCellValue("K초대권 " + dto.getAddPrize() + "장");
                    }

                    rowNum += 5;
                    Row sumRow = sheet.createRow(rowNum);
                    Cell sumCell = sumRow.createCell(0);

                    String memo = prizeList.get(0).getMemo();
                    if (memo != null) {
                        memo = memo.replace("\r\n", "\n");
                    }

                    String memoText = "메모 :\n" + memo;
                    sumCell.setCellValue(memoText);

                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 3));

                    CellStyle sumStyle = workbook.createCellStyle();
                    sumStyle.setWrapText(true);
                    sumStyle.setAlignment(HorizontalAlignment.LEFT);
                    sumStyle.setVerticalAlignment(VerticalAlignment.TOP);

                    Font sumFont = workbook.createFont();
                    sumFont.setBold(true);
                    sumFont.setFontHeightInPoints((short) 10);
                    sumFont.setColor(IndexedColors.BLUE.getIndex());   // ← 글자색 적용!
                    sumStyle.setFont(sumFont);

                    sumCell.setCellStyle(sumStyle);

                    sumRow.setHeightInPoints(80);

                    workbook.write(response.getOutputStream());
                }
            }
        } catch (ClassCastException cce){
            DefaultOAuth2User auth2User = (DefaultOAuth2User) principal;
            userId = auth2User.getName();
            userInfo = userService.getUserInfo(userId);
        }
    }

    @PostMapping("/klpiUpdateAjax")
    public @ResponseBody Map<String, Object> klpiUpdateAjax(@ModelAttribute("tournamentDto") TournamentDto tournamentDto) throws Exception{
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

                tournamentDto.setRegUserId(userId);

                resultCode = tournamentService.klpiUpdate(tournamentDto);

                if (resultCode > 0) {
                    result.put("resultCode", 0);
                    result.put("resultMsg", "정상적으로 적용 처리 되었습니다.");
                } else {
                    result.put("resultCode", -1);
                    result.put("resultMsg", "적용에 실패 하였습니다.");
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
