import util.FileManager;

import java.util.Arrays;
import java.util.List;

public class test {

    public static void main(String[] args) {
        // 테스트용 음료 데이터
        List<String> beverageData = Arrays.asList(
                "믹스커피 200 10",
                "고급믹스커피 300 10",
                "물 450 10",
                "캔커피 500 10",
                "이온음료 550 10",
                "고급캔커피 700 10",
                "탄산음료 750 10",
                "특화음료 800 10"
        );

        // 테스트용 동전 데이터
        List<String> moneyData = Arrays.asList(
                "10 3",
                "50 2",
                "100 5",
                "500 2",
                "1000 1"
        );

        // 파일 저장 테스트
        FileManager.saveToFile("src/resources/beverages.txt", beverageData);
        FileManager.saveToFile("src/resources/money.txt", moneyData);
    }
}
