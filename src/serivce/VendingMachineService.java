package serivce;

import main.AdminSystem;
import main.VendingMachine;
import ui.LoginGUI;
import util.FileManager;
import util.error.LoginFailedException;
import util.error.MoneyExceededException;
import util.error.NotEnoughChangeException;
import ui.InsertCoinGUI;
import repository.VendingMachineRepository;

import javax.swing.*;
import java.util.Stack;

public class VendingMachineService {

    private VendingMachineRepository vendingMachineRepository;

    public VendingMachineService() {
        this.vendingMachineRepository = VendingMachine.vendingMachineRepository;
    }

    // 화폐 삽입
    public void insertCoin(){
        // 화폐를 입력받을 수 있는 GUI 생성
        VendingMachine.insertCoinGUI = new InsertCoinGUI();
    }

    public void insertCoin(int coin) {
        // 금액 초과 확인
        try {
            if(vendingMachineRepository.getUserWallet() != null) {
                vendingMachineRepository.getUserWallet().checkExceedMoney(coin);
            }
        } catch (MoneyExceededException e){
            // 초과된 경우
            JOptionPane.showMessageDialog(VendingMachine.insertCoinGUI, e.getMessage());
            return;

        }

        // 금액 삽입
        vendingMachineRepository.getUserWallet().insertCoin(coin);
        JOptionPane.showMessageDialog(VendingMachine.insertCoinGUI, coin +"원 삽입  총 금액: " + vendingMachineRepository.getUserWallet().getTotalAmount());

        // 총 금액 업데이트
        VendingMachine.vendingMachineGUI.updateTotalAmountLabel(vendingMachineRepository.getUserWallet().getTotalAmount());
        // 판매가능 여부 업데이트
        VendingMachine.vendingMachineGUI.updateBeverageButtons(vendingMachineRepository.getBeverages(), vendingMachineRepository.getUserWallet().getTotalAmount());
    }

    // 화폐 반환
    public void returnMoney(){
        if(vendingMachineRepository.getUserWallet() != null) {
            // 반환
            JOptionPane.showMessageDialog(VendingMachine.vendingMachineGUI, vendingMachineRepository.getUserWallet().getTotalAmount() + "원이 반환 되었습니다.");
            vendingMachineRepository.getUserWallet().init();  // 메모리 해제

            // 총 금액 업데이트
            VendingMachine.vendingMachineGUI.updateTotalAmountLabel(vendingMachineRepository.getUserWallet().getTotalAmount());
            // 판매가능 여부 업데이트
            VendingMachine.vendingMachineGUI.updateBeverageButtons(vendingMachineRepository.getBeverages(), vendingMachineRepository.getUserWallet().getTotalAmount());
        }
    }


    // 물품 구매
    public void purchase(int index){
        int price = vendingMachineRepository.getBeverages().get(index).getPrice();        // 가격
        int payment = vendingMachineRepository.getUserWallet().getTotalAmount();          // 입력된 금액
        Stack<Integer> change = null;                                                     // 거스름 돈이 저장될 변수


        // 1. 입력된 금액 >> 자판기 돈통
        while(vendingMachineRepository.getUserWallet().getTotalAmount() > 0){   // 입력된 금액이 0보다 크면
            vendingMachineRepository.getVendingMachineWallet().insertNode(vendingMachineRepository.getUserWallet().dequeueCoin(), 1);   // 하나씩 빼서 자판기로 이동
           // System.out.println(vendingMachineRepository.getUserWallet().getTotalAmount());
        }
        System.out.println(vendingMachineRepository.getUserWallet().getTotalAmount());
        vendingMachineRepository.getVendingMachineWallet().printInOrder();
        try {
            // 2. 자판기 돈통 -> 거스름돈
            change = vendingMachineRepository.getVendingMachineWallet().returnChange(payment - price);
            System.out.println("잔돈: "+change);

        } catch (NotEnoughChangeException e) {
            // 실패: 거스름돈 부족
            // 거스름돈 >> 자판기 돈통
            if(change != null)
                for (Integer coin : change)
                    vendingMachineRepository.getVendingMachineWallet().insertNode(coin, 1);
            // 자판기 돈통 >> 사용자 입력금액
            change = vendingMachineRepository.getVendingMachineWallet().returnChange(payment);
            for (Integer coin : change)
                vendingMachineRepository.getUserWallet().insertCoin(coin);

            JOptionPane.showMessageDialog(VendingMachine.vendingMachineGUI, "에러: " + e.getMessage());
        }

        // 사용자에게 거스름돈주고
        // 재고까고

        // 구매 성공
        JOptionPane.showMessageDialog(VendingMachine.vendingMachineGUI, "주문완료: " + vendingMachineRepository.getBeverages().get(index).getName() + "(" + price + "원)");
        FileManager.recordSale("src/resources/sales.txt",vendingMachineRepository.getBeverages().get(index).getName(), price);
        vendingMachineRepository.getBeverages().get(index).removeOneStock();  // 재고 하나 차감

        // 거스름 돈 체크 후 사용자 돈통에 삽입
        if(change != null){
            for(Integer coin : change){
                vendingMachineRepository.getUserWallet().insertCoin(coin);
            }
        }
        // 반환
        returnMoney();
    }

    // 자판기 종료 함수
    public void exitVendingMachine(){
        vendingMachineRepository.saveVendingMachine();  // 현재 데이터들을 저장하고
        VendingMachine.vendingMachineGUI.dispose();     // 종료
    }

    // 관리자 모드 진입
    public void SwitchAdminMode() {
        // 비밀번호를 입력받을 수 있는 GUI 생성
        new LoginGUI("Login");
    }

    public void loadAdminMode(){
        exitVendingMachine();
        new AdminSystem();
    }

}
