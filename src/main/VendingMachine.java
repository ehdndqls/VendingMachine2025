package main;

import repository.VendingMachineRepository;
import ui.InsertCoinGUI;
import ui.VendingMachineGUI;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class VendingMachine {
    public static InsertCoinGUI insertCoinGUI;
    public static VendingMachineGUI vendingMachineGUI;
    public static void main(String[] args) {
        VendingMachineRepository vendingMachineRepository = new VendingMachineRepository();
        //VendingMachine vendingMachine = new VendingMachine();
        vendingMachineRepository.saveVendingMachine();
    }
}