package com.enclouds.enpoint.cmm.batch;

import com.enclouds.enpoint.user.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchJobs {

    @Autowired
    private CustomUserService customUserService;

    //매일 새벽6시 주간승점 누적승점으로 변경
    /*@Scheduled(cron = "0 0 6 * * *")
    public void rankBatch() throws Exception{
        customUserService.updateWeekRankPoint();
    }*/

}
