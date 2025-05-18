package main;

import repository.VendingMachineRepository;
import serivce.VendingMachineService;
import ui.InsertCoinGUI;
import ui.VendingMachineGUI;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class VendingMachine {
    public static InsertCoinGUI insertCoinGUI;
    public static VendingMachineGUI vendingMachineGUI;
    public static VendingMachineService vendingMachineService;
    public static VendingMachineRepository vendingMachineRepository;
    public static String password = "sch20204054!!";

    public static void main(String[] args) {
        // 1. 리포지토리 생성
        vendingMachineRepository = new VendingMachineRepository();

        // 2. 서비스 생성 (인스턴스 방식일 경우)
        vendingMachineService = new VendingMachineService();

        // 3. GUI 생성
        vendingMachineGUI = new VendingMachineGUI(vendingMachineRepository.getBeverages());

        // 필요 시 저장
        vendingMachineRepository.saveVendingMachine();
    }
}