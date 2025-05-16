package serivce;

import main.VendingMachine;
import util.error.MoneyExceededException;
import util.error.NotEnoughChangeException;
import ui.InsertCoinGUI;
import repository.VendingMachineRepository;

import javax.swing.*;
import java.util.Stack;

public class VendingMachineService {

    private static VendingMachineRepository vendingMachineRepository;


    // 화폐 삽입
    public void insertCoin(){
        VendingMachine.insertCoinGUI = new InsertCoinGUI();
    }

    public void insertCoin(int coin) {
        // 금액 초과 확인
        try {
            vendingMachineRepository.getUserWallet().checkExceedMoney(coin);
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
        Stack<Integer> change = null;                       // 거스름 돈이 저장될 변수


        // 1. 입력된 금액 >> 자판기 돈통
        while(vendingMachineRepository.getUserWallet().getTotalAmount() > 0){
            vendingMachineRepository.getVendingMachineWallet().insertNode(vendingMachineRepository.getUserWallet().dequeueCoin(), 1);
        }
        try {
            // 2. 자판기 돈통 -> 거스름돈
            change = vendingMachineRepository.getVendingMachineWallet().returnChange(payment - price);

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

        // 구매 성공
        JOptionPane.showMessageDialog(VendingMachine.vendingMachineGUI, "주문완료: " + vendingMachineRepository.getBeverages().get(index).getName() + "(" + price + "원)");
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

    // 관리자 모드 진입
    public void SwitchAdminMode(){
        /*
        1. 로그인 요청
        2. 암호 확인
        3. 호출
         */
    }
}
