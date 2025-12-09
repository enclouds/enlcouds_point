package com.enclouds.enpoint.tournament.service;

import com.enclouds.enpoint.cmm.paging.PaginationInfo;
import com.enclouds.enpoint.game.dto.GameDto;
import com.enclouds.enpoint.game.mapper.GameMapper;
import com.enclouds.enpoint.tournament.dto.TournamentDto;
import com.enclouds.enpoint.tournament.mapper.TournamentMapper;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.CustomUserService;
import com.enclouds.enpoint.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService{

    @Autowired
    private TournamentMapper tournamentMapper;

    @Autowired
    private CustomUserService customUserService;

    @Override
    public List<TournamentDto> selectTournamentList(TournamentDto tournamentDto) throws Exception {
        int tournamentTotalCount = tournamentMapper.selectTournamentListTotalCount(tournamentDto);

        PaginationInfo paginationInfo = new PaginationInfo(tournamentDto);
        paginationInfo.setTotalRecordCount(tournamentTotalCount);
        tournamentDto.setPaginationInfo(paginationInfo);

        return tournamentMapper.selectTournamentList(tournamentDto);
    }

    @Override
    public List<TournamentDto> selectTournamentRegList(TournamentDto tournamentDto) throws Exception{
        return tournamentMapper.selectTournamentRegList(tournamentDto);
    }

    @Override
    public int insertTournament(TournamentDto tournamentDto) throws Exception {
        return tournamentMapper.insertTournament(tournamentDto);
    }

    @Override
    public int updateTournament(TournamentDto tournamentDto) throws Exception {
        return tournamentMapper.updateTournament(tournamentDto);
    }

    @Override
    public int deleteTournament(TournamentDto tournamentDto) throws Exception {
        // 등록 내역 삭제
        tournamentMapper.deleteTournamentRegInfo(tournamentDto);
        return tournamentMapper.deleteTournament(tournamentDto);
    }

    @Override
    @Transactional
    public int registration(TournamentDto tournamentDto) throws Exception{
        int returnCode = 1;
        String infoDesc = "";

        if(tournamentDto.getOnlineChecked().equals("true")){
            infoDesc += "온라인 예약 등록";
        }else {
            //프리티켓 차감
            if(Integer.parseInt(tournamentDto.getTicket2()) > 0){
                UserDto userDto3 = new UserDto();
                userDto3.setMinusTicket(tournamentDto.getTicket2());
                userDto3.setPhoneNum(tournamentDto.getPhoneNum());
                userDto3.setAgentCode(tournamentDto.getAgentCode());
                customUserService.updateUserMinusTicket2(userDto3);

                infoDesc += " 프리티켓 : " + tournamentDto.getTicket2() + "장";
            }

            //초대권 차감
            if(Integer.parseInt(tournamentDto.getTicket4()) > 0){
                UserDto userDto2 = new UserDto();
                userDto2.setMinusTicket(tournamentDto.getTicket4());
                userDto2.setPhoneNum(tournamentDto.getPhoneNum());
                userDto2.setAgentCode(tournamentDto.getAgentCode());
                customUserService.updateUserMinusTicket4(userDto2);

                infoDesc += " 초대권 : " + tournamentDto.getTicket4() + "장";
            }

            //KLPT차감
            if(Integer.parseInt(tournamentDto.getTicket5()) > 0){
                UserDto userDto = new UserDto();
                userDto.setPhoneNum(tournamentDto.getPhoneNum());
                userDto.setMinusTicket(tournamentDto.getTicket5());
                userDto.setAgentCode(tournamentDto.getAgentCode());
                customUserService.updateUserMinusTicket5(userDto);

                infoDesc += " KLPT : " + tournamentDto.getTicket5() + "장";
            }
        }

        //레지스트레이션
        tournamentDto.setInfoDesc(infoDesc);
        //엔트리 수 조회
        int entryCount = tournamentMapper.registrationCount(tournamentDto);
        tournamentDto.setEntryCount(entryCount);

        tournamentMapper.registration(tournamentDto);

        long seq = tournamentMapper.selectLastInsertId();
        tournamentDto.setSeq(seq);
        TournamentDto printDto = tournamentMapper.selectPrintInfo(tournamentDto);
        //this.printReceipt(printDto);

        return returnCode;
    }

    @Override
    public TournamentDto getPrintInfo(TournamentDto tournamentDto) throws Exception {
        return tournamentMapper.selectPrintInfo(tournamentDto);
    }

    /*public void printReceipt(TournamentDto printDto) {
        final String ESC = "\u001B";
        final String GS = "\u001D";

        // 정렬 명령어 상수 정의
        final String AlignLeft = ESC + "a" + "\u0000";   // 왼쪽 정렬
        final String AlignCenter = ESC + "a" + "\u0001"; // 가운데 정렬
        final String AlignRight = ESC + "a" + "\u0002";  // 오른쪽 정렬

        // 글씨 크기/굵기 명령어
        final String BoldOn = ESC + "E" + "\u0001";
        final String BoldOff = ESC + "E" + "\u0000";
        final String DoubleSizeOn = GS + "!" + "\u0011";
        final String DoubleSizeOff = GS + "!" + "\u0000";

        final String Cut = GS + "V" + "\u0001";

        String entryNm = "";
        if(printDto.getEntryCount() == 1){
            entryNm = "Entry";
        }else {
            entryNm = "RE Entry";
        }

        // ESC/POS 초기화 및 컷팅 명령어를 텍스트 앞뒤에 추가
        String customizedText = "\n\n\n" + AlignCenter +
                BoldOn + DoubleSizeOn +
                "* " +printDto.getTitle() + " *" +"\n" + // ✅ 첫 번째 줄: 크게, 굵게, 가운데 정렬
                "---------------------\n" +
                // DoubleSizeOff + BoldOff +  <-- 이 부분을 제거하여 설정을 유지합니다.
                printDto.getTournamentName() +"\n" +   // ✅ 두 번째 줄: 첫 번째 줄과 동일한 설정 유지 (크게, 굵게)
                printDto.getInfoDesc() + "\n\n" +                                  // 세 번째 줄 (기본 크기/굵기)
                DoubleSizeOn +                                                   // 네 번째 줄부터 다시 크게 설정
                printDto.getNickName() +"\n" +                                   // 네 번째 줄 (크게)
                printDto.getEntryCount() + " / " + printDto.getTotalEntryCount() +"\n" +
                entryNm + "\n\n" +
                DoubleSizeOff +                                                  // 크기 다시 해제
                BoldOff +                               // <-- 세 번째 줄부터는 크기와 굵기를 다시 기본으로 되돌립니다.
                printDto.getRegDate() + "\n" +                                   // 마지막 줄 (기본 크기)
                DoubleSizeOn +                                                   // 네 번째 줄부터 다시 크게 설정
                "---------------------\n" +
                DoubleSizeOff +                                                  // 크기 다시 해제
                "\n\n\n\n\n\n" + AlignLeft +                                                      // 마지막에 왼쪽 정렬로 복귀
                Cut;


        // --- EUC-KR 또는 MS949 인코딩 사용 (한글 깨짐 방지) ---
        byte[] escposData = customizedText.getBytes(Charset.forName("EUC-KR"));

        PrintService bixolonPrinter = findPrinter("BIXOLON SRP-330II");

        if (bixolonPrinter != null) {
            try {
                DocPrintJob job = bixolonPrinter.createPrintJob();
                InputStream stream = new ByteArrayInputStream(escposData);
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(stream, flavor, null);

                job.print(doc, null);
                System.out.println("✅ BIXOLON SRP-330II로 인쇄 명령 전송 완료");

            } catch (PrintException e) {
                e.printStackTrace();
                System.err.println("❌ 인쇄 중 오류 발생: 드라이버 설정 또는 연결 확인 필요.");
            }
        } else {
            System.err.println("❌ 기본 프린터 서비스를 찾을 수 없습니다. 기본 프린터 설정을 확인하세요.");
        }
    }

    private PrintService findPrinter(String printerName) {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }
        return null; // 해당 이름의 프린터가 없는 경우
    }*/


}
