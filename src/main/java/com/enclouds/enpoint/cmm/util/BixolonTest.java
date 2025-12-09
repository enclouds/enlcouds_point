package com.enclouds.enpoint.cmm.util;

import javax.print.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BixolonTest {

    public static void main(String[] args) {
        // ESC/POS 명령어 (텍스트 + 줄바꿈 + 컷팅 명령 포함)
        String printData = (
                "\u001B@"+                    // Initialize printer
                        "=== USB 포트 테스트 ===\n" +
                        "상품: 아메리카노\n" +
                        "수량: 1\n" +
                        "금액: 4,000원\n" +
                        "------------------------\n" +
                        "\n" + "\n" + "\n" + "\n" +
                        "\u001DVA0"                   // Full cut
        );

        // --- EUC-KR 또는 MS949 인코딩 사용 ---
        byte[] escposData = printData.getBytes(Charset.forName("EUC-KR"));
        // 또는 byte[] escposData = printData.getBytes(Charset.forName("MS949"));

        // 1. 기본 인쇄 서비스 찾기
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

        if (defaultService != null) {
            try {
                // 2. 인쇄 작업 설정
                DocPrintJob job = defaultService.createPrintJob();
                InputStream stream = new ByteArrayInputStream(escposData);

                // --- DocFlavor는 AUTOSENSE로 변경 ---
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

                Doc doc = new SimpleDoc(stream, flavor, null);

                // 3. 인쇄 실행
                job.print(doc, null);

                System.out.println("✅ EUC-KR 인코딩으로 출력 명령 전송 완료 (AUTOSENSE)");

            } catch (PrintException e) {
                e.printStackTrace();
                System.err.println("❌ 인쇄 중 오류 발생: 드라이버 설정 또는 연결 확인 필요.");
            }
        } else {
            System.err.println("❌ 기본 프린터 서비스를 찾을 수 없습니다.");
        }
    }
}
