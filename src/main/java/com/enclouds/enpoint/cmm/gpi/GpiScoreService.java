package com.enclouds.enpoint.cmm.gpi;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class GpiScoreService {

    private static final double MAX_BUYIN_FACTOR = 20.0;
    private static final double MAX_FIELD_FACTOR = 20.0;
    private static final double MAX_SCORE = 3000.0;

    /**
     * 개별 대회 점수 계산
     */
    public double calculateTournamentScore(int rank, int totalPlayers, double buyIn) {
        if (rank <= 0 || totalPlayers <= 0 || buyIn <= 0) {
            throw new IllegalArgumentException("순위, 참가자 수, 바이인은 0보다 커야 합니다.");
        }

        // Buy-in Factor
        double buyInFactor = Math.min(Math.log(buyIn), MAX_BUYIN_FACTOR);

        // Field Size Factor
        double fieldFactor = Math.min(Math.log(totalPlayers), MAX_FIELD_FACTOR);

        // Base Score
        double baseScore = buyInFactor * fieldFactor;

        // Position Factor
        double positionFactor = Math.max(0.1, 1.0 - ((double)(rank - 1) / totalPlayers));

        // Final Score
        double rawScore = baseScore * positionFactor;

        return Math.min(rawScore, MAX_SCORE);
    }

    /**
     * 최근 3년간 성적 중 상위 4개씩만 반영
     */
    public double calculatePlayerGpi(List<Double> tournamentScores) {
        // 최근 3년간 성적만 들어왔다고 가정
        // 상위 4개만 반영
        return tournamentScores.stream()
                .sorted(Comparator.reverseOrder())
                .limit(4)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public static void main(String[] args) {
        GpiScoreService service = new GpiScoreService();

        // 예시: 참가자 500명, 1등, 바이인 $10,000
        double score = service.calculateTournamentScore(2, 226, 100000);
        System.out.println("대회 점수: " + score);

        // 누적 예시
        List<Double> scores = Arrays.asList(score, 100.0, 800.0, 600.0, 400.0);
        double gpi = service.calculatePlayerGpi(scores);
        System.out.println("플레이어 GPI 총점: " + gpi);
    }
}
