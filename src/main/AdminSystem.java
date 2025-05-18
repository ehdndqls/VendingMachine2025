package main;

import repository.VendingMachineRepository;
import serivce.AdminSystemService;
import serivce.VendingMachineService;
import ui.AdminSystemGUI;
import ui.InsertCoinGUI;
import ui.VendingMachineGUI;

public class AdminSystem {

    public static AdminSystemGUI adminSystemGUI;
    public static AdminSystemService adminSystemService;
    //public static AdminSystemRepository adminSystemRepository;

    public AdminSystem() {
        // 1. 리포지토리 생성
        //AdminSystemRepository = new adminSystemRepository();

        // 2. 서비스 생성 (인스턴스 방식일 경우)
        adminSystemService = new AdminSystemService();

        // 3. GUI 생성
        adminSystemGUI = new AdminSystemGUI();
    }
    /*
    1. 비밀번호 변경
    2. 일/월별 매출 산출
    3. 각 음료의 일/월별 매출 산출
    4. 각 음료의 재고 보충
    5. 각 음료의 이름, 가격 변경
    6. 자판기 내 화폐현황 출력
    7. 수금
    8. 잔돈 보충
    */


}
