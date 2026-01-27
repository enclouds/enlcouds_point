package com.enclouds.enpoint.klpi.controller;

import com.enclouds.enpoint.klpi.dto.KlpiRankDto;
import com.enclouds.enpoint.klpi.service.KlpiService;
import com.enclouds.enpoint.user.dto.RankDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/rank")
public class RankViewController {

    @Autowired
    private KlpiService klpiService;

    @GetMapping("/general")
    public String generalRank(Model model) throws Exception{
        model.addAttribute("generalRanks", klpiService.selectKlpiRankList("N"));
        model.addAttribute("universityRanks", klpiService.selectKlpiRankList("U"));

        return "rank";
    }

}
