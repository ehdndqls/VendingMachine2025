package util;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileManager {

    // 데이터 저장 함수
    public static void saveToFile(String filePath, List<String> dataList) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(filePath),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            // 빈 줄로 구분
            writer.newLine();

            // 현재 시간 기록
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
            writer.write(currentTime);
            writer.newLine();

            for (String data : dataList) {
                writer.write(data);
                writer.newLine();
            }
            System.out.println("[파일 저장 성공] " + filePath);
        } catch (IOException e) {
            System.err.println("[파일 저장 실패] " + e.getMessage());
        }
    }

    // 매출 기록 함수
    public static void recordSale(String filePath, String beverageName, Integer price){
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(filePath),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            // 현재 시간 기록
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
            writer.write(currentTime + " " + beverageName + " " + price);
            writer.newLine();
            System.out.println("[매출 기록 성공] " + filePath);
        } catch (IOException e) {
            System.err.println("[매출 기록 실패] " + e.getMessage());
        }
    }

    // 데이터 로드 함수
    public static String[] loadFromFile(String filePath) {
        List<String> allLines = new ArrayList<>();
        List<String> currentBlock = new ArrayList<>();
        List<String> latestBlock = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isBlockStarted = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // 날짜/시간 포맷 감지 (예: 2024-01-01 00:05)
                if (line.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$")) {
                    // 새 블록 시작 -> 이전 블록 저장
                    if (!currentBlock.isEmpty()) {
                        latestBlock = new ArrayList<>(currentBlock);
                        currentBlock.clear();
                    }
                    isBlockStarted = true;
                }

                if (isBlockStarted) {
                    currentBlock.add(line);
                }
            }

            // 마지막 블록 처리
            if (!currentBlock.isEmpty()) {
                latestBlock = currentBlock;
            }

        } catch (IOException e) {
            System.err.println("파일 읽기 실패: " + e.getMessage());
        }

        // 첫 줄은 날짜 -> 제외하고 반환
        return latestBlock.stream()
                .skip(1)
                .toArray(String[]::new);
    }



}
