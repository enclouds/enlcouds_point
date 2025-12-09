package com.enclouds.enpoint.tournament.service;

import com.enclouds.enpoint.tournament.dto.TournamentDto;
import org.springframework.stereotype.Service;

@Service
public class EscposFormatter {

    private static final String ESC = "\u001B";
    private static final String GS  = "\u001D";

    public String generateBuyInReceipt(TournamentDto dto) {

        final String AlignCenter   = ESC + "a" + "\u0001";
        final String AlignLeft     = ESC + "a" + "\u0000";
        final String BoldOn        = ESC + "E" + "\u0001";
        final String BoldOff       = ESC + "E" + "\u0000";
        final String DoubleSizeOn  = GS + "!" + "\u0011";
        final String DoubleSizeOff = GS + "!" + "\u0000";
        final String Cut           = GS + "V" + "\u0001";

        String entryNm = dto.getEntryCount() == 1 ? "Entry" : "RE Entry";

        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n").append(AlignCenter).append(BoldOn).append(DoubleSizeOn)
                .append("* ").append(dto.getTitle()).append(" *\n")
                .append("---------------------\n")
                .append(dto.getTournamentName()).append("\n")
                .append(dto.getInfoDesc()).append("\n\n")
                .append(DoubleSizeOn)
                .append(dto.getNickName()).append("\n")
                .append(dto.getEntryCount()).append(" / ").append(dto.getTotalEntryCount()).append("\n")
                .append(entryNm).append("\n\n")
                .append(DoubleSizeOff).append(BoldOff)
                .append(dto.getRegDate()).append("\n")
                .append("---------------------\n")
                .append(Cut);

        return sb.toString();
    }
}
