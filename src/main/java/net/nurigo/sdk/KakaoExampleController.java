package net.nurigo.sdk;

import com.enclouds.enpoint.nurigo.KakaoDto;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.*;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 모든 발송 API에는 발신, 수신번호 입력 항목에 +82 또는 +8210, 010-0000-0000 같은 형태로 기입할 수 없습니다.
 * 수/발신 가능한 예시) 01000000000, 020000000 등
 */
@RestController
@RequestMapping("/kakao")
public class KakaoExampleController {

    private final DefaultMessageService messageService;
    private final static String OWNER_TEL = "010-7733-4561";
    private final static String OWNER_TEL2 = "01077334561";

    /**
     * 발급받은 API KEY와 API Secret Key를 사용해주세요.
     */
    public KakaoExampleController() {
        this.messageService = NurigoApp.INSTANCE.initialize("NCSJPUW3ODGLXGCS", "2WZ40LXNIGTMJ8MDOCO1HBMQHVLAWBFT", "https://api.solapi.com");
    }

    /**
     * 알림톡 한건 발송 예제
     */
    @PostMapping("/send-one-ata")
    public SingleMessageSentResponse sendOneAta(KakaoDto kakaoDto) throws Exception{
        KakaoOption kakaoOption = new KakaoOption();
        kakaoOption.setDisableSms(true);

        kakaoOption.setPfId("KA01PF221011061537068VAKu28zMQ3D");
        kakaoOption.setTemplateId(kakaoDto.getTemplateId());

        //적립
        if(kakaoDto.getTemplateId().equals("KA01TP221011062400156k4kpTZGoW5f")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getAddPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{담당자 전화번호}", OWNER_TEL);
            kakaoOption.setVariables(variables);
            //사용
        }else if(kakaoDto.getTemplateId().equals("KA01TP2210110625131785Wo8nGYwQLh")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{사용포인트}", kakaoDto.getMinusPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{담당자 전화번호}", OWNER_TEL);
            kakaoOption.setVariables(variables);
        }else if(kakaoDto.getTemplateId().equals("KA01TP221206060900681elE0OAyzmRT")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{휴먼포인트}", kakaoDto.getMinusPoint());
            variables.put("#{담당자 전화번호}", OWNER_TEL);
            kakaoOption.setVariables(variables);
        }else if(kakaoDto.getTemplateId().equals("KA01TP230308085912775kWORi72OAz5")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{인증번호}", kakaoDto.getAuthCode());
            kakaoOption.setVariables(variables);
            //New 적립
        }else if(kakaoDto.getTemplateId().equals("KA01TP231127074830060CQH0Bq6e7I5")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getAddPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{주간티켓}", kakaoDto.getTicket1());
            variables.put("#{프리티켓}", kakaoDto.getTicket2());
            variables.put("#{씨티티켓}", kakaoDto.getTicket3());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점1}", "창원 상남점");
            variables.put("#{담당자 전화번호1}", "010-4383-4561");
            variables.put("#{지점2}", "창원 봉곡점");
            variables.put("#{담당자 전화번호2}", "010-4215-9876");
            variables.put("#{지점3}", "김해 내외점");
            variables.put("#{담당자 전화번호3}", "010-8390-7466");
            variables.put("#{지점4}", "부산 서면점");
            variables.put("#{담당자 전화번호4}", "010-3974-9279");
            variables.put("#{지점5}", "대구 동성로");
            variables.put("#{담당자 전화번호5}", "010-5895-0167");
            variables.put("#{지점6}", "대구 광장코아");
            variables.put("#{담당자 전화번호6}", "010-6301-0569");
            variables.put("#{지점7}", "부산 동래점");
            variables.put("#{담당자 전화번호7}", "010-2732-1268");
            variables.put("#{지점8}", "부산 덕천점");
            variables.put("#{담당자 전화번호8}", "010-7591-4963");
            variables.put("#{지점9}", "마산 합성점");
            variables.put("#{담당자 전화번호9}", "010-3919-6360");
            variables.put("#{지점10}", "부산 경성대점");
            variables.put("#{담당자 전화번호10}", "010-3823-2244");
            kakaoOption.setVariables(variables);
            //New 차감
        }else if(kakaoDto.getTemplateId().equals("KA01TP231127074911495jJtEBsek5Wq")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getMinusPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{주간티켓}", kakaoDto.getTicket1());
            variables.put("#{프리티켓}", kakaoDto.getTicket2());
            variables.put("#{씨티티켓}", kakaoDto.getTicket3());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점1}", "창원 상남점");
            variables.put("#{담당자 전화번호1}", "010-4383-4561");
            variables.put("#{지점2}", "창원 봉곡점");
            variables.put("#{담당자 전화번호2}", "010-4215-9876");
            variables.put("#{지점3}", "김해 내외점");
            variables.put("#{담당자 전화번호3}", "010-8390-7466");
            variables.put("#{지점4}", "부산 서면점");
            variables.put("#{담당자 전화번호4}", "010-3974-9279");
            variables.put("#{지점5}", "대구 동성로");
            variables.put("#{담당자 전화번호5}", "010-5895-0167");
            variables.put("#{지점6}", "대구 광장코아");
            variables.put("#{담당자 전화번호6}", "010-6301-0569");
            variables.put("#{지점7}", "부산 동래점");
            variables.put("#{담당자 전화번호7}", "010-2732-1268");
            variables.put("#{지점8}", "부산 덕천점");
            variables.put("#{담당자 전화번호8}", "010-7591-4963");
            variables.put("#{지점9}", "마산 합성점");
            variables.put("#{담당자 전화번호9}", "010-3919-6360");
            variables.put("#{지점10}", "부산 경성대점");
            variables.put("#{담당자 전화번호10}", "010-3823-2244");
            kakaoOption.setVariables(variables);
        }else if(kakaoDto.getTemplateId().equals("KA01TP240207000313733KDbd3vtZ2Uq")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getAddPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{주간티켓}", kakaoDto.getTicket1());
            variables.put("#{프리티켓}", kakaoDto.getTicket2());
            variables.put("#{씨티티켓}", kakaoDto.getTicket3());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
        }else if(kakaoDto.getTemplateId().equals("KA01TP2403212343075794DuTjfujlVd")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getMinusPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{주간티켓}", kakaoDto.getTicket1());
            variables.put("#{프리티켓}", kakaoDto.getTicket2());
            variables.put("#{씨티티켓}", kakaoDto.getTicket3());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
        }else if(kakaoDto.getTemplateId().equals("KA01TP241219020324371IRqxVxK9p6J")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getAddPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{주간티켓}", kakaoDto.getTicket1());
            variables.put("#{프리티켓}", kakaoDto.getTicket2());
            variables.put("#{씨티티켓}", kakaoDto.getTicket3());
            variables.put("#{팀프로티켓}", kakaoDto.getTicket4());
            variables.put("#{온라인티켓}", kakaoDto.getTicket5());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
        }else if(kakaoDto.getTemplateId().equals("KA01TP241219020224105xGE9ug9SxpI")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getMinusPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{주간티켓}", kakaoDto.getTicket1());
            variables.put("#{프리티켓}", kakaoDto.getTicket2());
            variables.put("#{씨티티켓}", kakaoDto.getTicket3());
            variables.put("#{팀프로티켓}", kakaoDto.getTicket4());
            variables.put("#{온라인티켓}", kakaoDto.getTicket5());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
        }else if(kakaoDto.getTemplateId().equals("KA01TP250309223401715US4RDxP1qyv")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getAddPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{주간티켓}", kakaoDto.getTicket1());
            variables.put("#{프리티켓}", kakaoDto.getTicket2());
            variables.put("#{씨티티켓}", kakaoDto.getTicket3());
            variables.put("#{팀프로티켓}", kakaoDto.getTicket4());
            variables.put("#{KLPT티켓}", kakaoDto.getTicket5());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
        }else if(kakaoDto.getTemplateId().equals("KA01TP250309223458599ffvAchTj6Dp")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getMinusPoint());
            variables.put("#{누적포인트}", kakaoDto.getTotalPoint());
            variables.put("#{주간티켓}", kakaoDto.getTicket1());
            variables.put("#{프리티켓}", kakaoDto.getTicket2());
            variables.put("#{씨티티켓}", kakaoDto.getTicket3());
            variables.put("#{팀프로티켓}", kakaoDto.getTicket4());
            variables.put("#{KLPT티켓}", kakaoDto.getTicket5());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
            //250619 변경 포인트 사용 V7.0
        }else if(kakaoDto.getTemplateId().equals("KA01TP2506160655126477tal2slzrSM")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{사용포인트}", kakaoDto.getMinusPoint());
            variables.put("#{잔여포인트}", kakaoDto.getTotalPoint());
            variables.put("#{COUPON_NM}", "주간티켓");
            variables.put("#{COUPON}", kakaoDto.getTicket1());
            variables.put("#{COUPON2_NM}", "프리티켓");
            variables.put("#{COUPON2}", kakaoDto.getTicket2());
            variables.put("#{COUPON3_NM}", "월간티켓");
            variables.put("#{COUPON3}", kakaoDto.getTicket3());
            variables.put("#{COUPON4_NM}", "K초대권");
            variables.put("#{COUPON4}", kakaoDto.getTicket4());
            variables.put("#{COUPON5_NM}", "KLPT티켓");
            variables.put("#{COUPON5}", kakaoDto.getTicket5());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
            //250619 포인트 적립 V7.0
        }else if(kakaoDto.getTemplateId().equals("KA01TP250616065621950ChywLjQor3r")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getAddPoint());
            variables.put("#{잔여포인트}", kakaoDto.getTotalPoint());
            variables.put("#{COUPON_NM}", "주간티켓");
            variables.put("#{COUPON}", kakaoDto.getTicket1());
            variables.put("#{COUPON2_NM}", "프리티켓");
            variables.put("#{COUPON2}", kakaoDto.getTicket2());
            variables.put("#{COUPON3_NM}", "월간티켓");
            variables.put("#{COUPON3}", kakaoDto.getTicket3());
            variables.put("#{COUPON4_NM}", "K초대권");
            variables.put("#{COUPON4}", kakaoDto.getTicket4());
            variables.put("#{COUPON5_NM}", "KLPT티켓");
            variables.put("#{COUPON5}", kakaoDto.getTicket5());
            variables.put("#{외식쿠폰}", kakaoDto.getCouponPoint());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
            //250802 포인트 적립 V8.0
        }else if(kakaoDto.getTemplateId().equals("KA01TP250718035738792tH46idsueEi")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{적립포인트}", kakaoDto.getAddPoint());
            variables.put("#{잔여포인트}", kakaoDto.getTotalPoint());
            variables.put("#{COUPON_NM}", "주간티켓");
            variables.put("#{COUPON}", kakaoDto.getTicket1());
            variables.put("#{COUPON2_NM}", "프리티켓");
            variables.put("#{COUPON2}", kakaoDto.getTicket2());
            variables.put("#{COUPON3_NM}", "월간티켓");
            variables.put("#{COUPON3}", kakaoDto.getTicket3());
            variables.put("#{COUPON4_NM}", "K초대권");
            variables.put("#{COUPON4}", kakaoDto.getTicket4());
            variables.put("#{COUPON5_NM}", "KLPT티켓");
            variables.put("#{COUPON5}", kakaoDto.getTicket5());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
            //250802 포인트 사용 V8.0
        }else if(kakaoDto.getTemplateId().equals("KA01TP250718035801166Xcs2U3M9zHd")){
            HashMap<String, String> variables = new HashMap<>();
            variables.put("#{닉네임}", kakaoDto.getNickName());
            variables.put("#{상점명}", kakaoDto.getStoreNm());
            variables.put("#{사용포인트}", kakaoDto.getMinusPoint());
            variables.put("#{잔여포인트}", kakaoDto.getTotalPoint());
            variables.put("#{COUPON_NM}", "주간티켓");
            variables.put("#{COUPON}", kakaoDto.getTicket1());
            variables.put("#{COUPON2_NM}", "프리티켓");
            variables.put("#{COUPON2}", kakaoDto.getTicket2());
            variables.put("#{COUPON3_NM}", "월간티켓");
            variables.put("#{COUPON3}", kakaoDto.getTicket3());
            variables.put("#{COUPON4_NM}", "K초대권");
            variables.put("#{COUPON4}", kakaoDto.getTicket4());
            variables.put("#{COUPON5_NM}", "KLPT티켓");
            variables.put("#{COUPON5}", kakaoDto.getTicket5());
            variables.put("#{지점}", kakaoDto.getStoreNm());
            variables.put("#{담당자 전화번호}", kakaoDto.getAgentTel());
            kakaoOption.setVariables(variables);
            //250619 포인트 적립 V7.0
        }

        Message message = new Message();
        message.setFrom(OWNER_TEL2);
        message.setTo(kakaoDto.getRcvNum());
        message.setKakaoOptions(kakaoOption);

        //SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        SingleMessageSentResponse response = null;
        try {
            response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception e) {
            // 에러 로그 출력
            System.err.println("메시지 전송 중 오류 발생: " + e.getMessage());
            // 필요 시 e.printStackTrace(); 사용
        }
        return response;
    }

    /**
     * 여러 메시지 발송 예제
     * 한 번 실행으로 최대 10,000건 까지의 메시지가 발송 가능합니다.
     */
    @PostMapping("/send-many-ata")
    public MultipleDetailMessageSentResponse sendMany() {
        ArrayList<Message> messageList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            KakaoOption kakaoOption = new KakaoOption();
            // 등록하신 카카오 비즈니스 채널의 pfId를 입력해주세요.
            kakaoOption.setPfId("");
            // 등록하신 카카오 알림톡 템플릿의 templateId를 입력해주세요.
            kakaoOption.setTemplateId("");

            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom("발신번호 입력");
            message.setTo("수신번호 입력");
            message.setText("한글 45자, 영자 90자 이하 입력되면 자동으로 SMS타입의 메시지가 추가됩니다." + i);
            message.setKakaoOptions(kakaoOption);

            messageList.add(message);
        }

        try {
            // send 메소드로 단일 Message 객체를 넣어도 동작합니다!
            MultipleDetailMessageSentResponse response = this.messageService.send(messageList);

            // 중복 수신번호를 허용하고 싶으실 경우 위 코드 대신 아래코드로 대체해 사용해보세요!
            //MultipleDetailMessageSentResponse response = this.messageService.send(messageList, true);

            System.out.println(response);

            return response;
        } catch (NurigoMessageNotReceivedException exception) {
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @PostMapping("/send-ata-scheduled-messages")
    public MultipleDetailMessageSentResponse sendScheduledMessages() {
        ArrayList<Message> messageList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            KakaoOption kakaoOption = new KakaoOption();
            // 등록하신 카카오 비즈니스 채널의 pfId를 입력해주세요.
            kakaoOption.setPfId("");
            // 등록하신 카카오 알림톡 템플릿의 templateId를 입력해주세요.
            kakaoOption.setTemplateId("");

            Message message = new Message();
            // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
            message.setFrom("발신번호 입력");
            message.setTo("수신번호 입력");
            message.setText("한글 45자, 영자 90자 이하 입력되면 자동으로 SMS타입의 메시지가 추가됩니다." + i);
            message.setKakaoOptions(kakaoOption);

            messageList.add(message);
        }

        try {
            // 과거 시간으로 예약 발송을 진행할 경우 즉시 발송처리 됩니다.
            LocalDateTime localDateTime = LocalDateTime.parse("2022-05-27 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(localDateTime);
            Instant instant = localDateTime.toInstant(zoneOffset);

            // 단일 발송도 지원하여 ArrayList<Message> 객체가 아닌 Message 단일 객체만 넣어도 동작합니다!
            MultipleDetailMessageSentResponse response = this.messageService.send(messageList, instant);

            // 중복 수신번호를 허용하고 싶으실 경우 위 코드 대신 아래코드로 대체해 사용해보세요!
            //MultipleDetailMessageSentResponse response = this.messageService.send(messageList, instant, true);

            System.out.println(response);

            return response;
        } catch (NurigoMessageNotReceivedException exception) {
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    /**
     * 친구톡 한건 발송 예제, send 호환, 다량 발송의 경우 위 send-many-ata 코드를 참조해보세요!
     * 친구톡 내 버튼은 최대 5개까지만 생성 가능합니다.
     */
    @PostMapping("/send-cta")
    public SingleMessageSentResponse sendOneCta() {
        KakaoOption kakaoOption = new KakaoOption();
        // disableSms를 true로 설정하실 경우 문자로 대체발송 되지 않습니다.
        // kakaoOption.setDisableSms(true);

        // 등록하신 카카오 비즈니스 채널의 pfId를 입력해주세요.
        kakaoOption.setPfId("");
        kakaoOption.setVariables(null);

        // 친구톡에 버튼을 넣으실 경우에만 추가해주세요.
        ArrayList<KakaoButton> kakaoButtons = new ArrayList<>();
        // 웹링크 버튼
        KakaoButton kakaoWebLinkButton = new KakaoButton(
                "테스트 버튼1", KakaoButtonType.WL,
                "https://example.com", "https://example.com",
                null, null
        );

        // 앱링크 버튼
        KakaoButton kakaoAppLinkButton = new KakaoButton(
                "테스트 버튼2", KakaoButtonType.AL,
                null, null,
                "exampleapp://test", "exampleapp://test"
        );

        // 봇 키워드 버튼, 버튼을 클릭하면 버튼 이름으로 수신자가 발신자에게 채팅을 보냅니다.
        KakaoButton kakaoBotKeywordButton = new KakaoButton(
                "테스트 버튼3", KakaoButtonType.BK, null, null, null, null
        );

        // 메시지 전달 버튼, 버튼을 클릭하면 버튼 이름과 친구톡 메시지 내용을 포함하여 수신자가 발신자에게 채팅을 보냅니다.
        KakaoButton kakaoMessageDeliveringButton = new KakaoButton(
                "테스트 버튼4", KakaoButtonType.MD, null, null, null, null
        );

        /*
         * 상담톡 전환 버튼, 상담톡 서비스를 이용하고 있을 경우 상담톡으로 전환. 상담톡 서비스 미이용시 해당 버튼 추가될 경우 발송 오류 처리됨.
         * @see <a href="https://business.kakao.com/info/bizmessage/">상담톡 딜러사 확인</a>
         */
        /*KakaoButton kakaoBotCustomerButton = new KakaoButton(
                "테스트 버튼6", KakaoButtonType.BC, null, null, null, null
        );*/

        // 봇전환 버튼, 해당 비즈니스 채널에 카카오 챗봇이 없는 경우 동작안함.
        // KakaoButton kakaoBotTransferButton = new KakaoButton("테스트 버튼7", KakaoButtonType.BT, null, null, null, null);

        kakaoButtons.add(kakaoWebLinkButton);
        kakaoButtons.add(kakaoAppLinkButton);
        kakaoButtons.add(kakaoBotKeywordButton);
        kakaoButtons.add(kakaoMessageDeliveringButton);

        kakaoOption.setButtons(kakaoButtons);

        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("발신번호 입력");
        message.setTo("수신번호 입력");
        message.setText("친구톡 테스트 메시지");
        message.setKakaoOptions(kakaoOption);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }

    /**
     * 친구톡 이미지 단건 발송, send 호환, 다량 발송의 경우 위 send-many-ata 코드를 참조해보세요!
     * 친구톡 내 버튼은 최대 5개까지만 생성 가능합니다.
     */
    @PostMapping("/send-cti")
    public SingleMessageSentResponse sendOneCti() throws IOException {
        ClassPathResource resource = new ClassPathResource("static/cti.jpg");
        File file = resource.getFile();
        // 이미지 크기는 가로 500px 세로 250px 이상이어야 합니다, 링크도 필수로 기입해주세요.
        String imageId = this.messageService.uploadFile(file, StorageType.KAKAO, "https://example.com");

        KakaoOption kakaoOption = new KakaoOption();
        // disableSms를 true로 설정하실 경우 문자로 대체발송 되지 않습니다.
        // kakaoOption.setDisableSms(true);

        // 등록하신 카카오 비즈니스 채널의 pfId를 입력해주세요.
        kakaoOption.setPfId("");
        kakaoOption.setImageId(imageId);
        kakaoOption.setVariables(null);

        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom("발신번호 입력");
        message.setTo("수신번호 입력");
        message.setText("테스트");
        message.setKakaoOptions(kakaoOption);

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);

        return response;
    }
}
