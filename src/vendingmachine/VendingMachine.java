package vendingmachine;

import error.MoneyExceededException;
import error.NotEnoughChangeException;
import vendingmachine.gui.InsertCoinGUI;
import vendingmachine.gui.VendingMachineGUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Stack;

public class VendingMachine {

    static VendingMachineWallet vendingMachineWallet;   // 자판기 보유 금액
    static UserWallet userWallet; // 사용자 입력금액
    static ArrayList<Beverage> beverages;   // 음료 데이터

    static VendingMachineGUI vendingMachineGUI;
    static InsertCoinGUI insertCoinGUI;

    public VendingMachine() {
        // 초기화

        // 돈통
        vendingMachineWallet = new VendingMachineWallet(10);
        userWallet = new UserWallet();
        // 음료
        beverages = new ArrayList<>();
        beverages.add(new Beverage("믹스커피", 200));
        beverages.add(new Beverage("고급믹스커피", 300));
        beverages.add(new Beverage("물", 450));
        beverages.add(new Beverage("캔커피", 500));
        beverages.add(new Beverage("이온음료", 550));
        beverages.add(new Beverage("고급캔커피", 700));
        beverages.add(new Beverage("탄산음료", 750));
        beverages.add(new Beverage("특화음료", 800));

        // GUI 호출
        vendingMachineGUI = new VendingMachineGUI(beverages);
    }

    // 화폐 삽입
    public static void insertCoin(){
        insertCoinGUI = new InsertCoinGUI();
    }

    public static void insertCoin(int coin) {
        // 금액 초과 확인
        try {
            userWallet.checkExceedMoney(coin);
        } catch (MoneyExceededException e){
            // 초과된 경우
            JOptionPane.showMessageDialog(insertCoinGUI, e.getMessage());
            return;
        }

        // 금액 삽입
        userWallet.insertCoin(coin);
        JOptionPane.showMessageDialog(insertCoinGUI, coin +"원 삽입  총 금액: " + userWallet.getTotalAmount());

        // 총 금액 업데이트
        vendingMachineGUI.updateTotalAmountLabel(userWallet.getTotalAmount());
        // 판매가능 여부 업데이트
        vendingMachineGUI.updateBeverageButtons(beverages, userWallet.getTotalAmount());
    }

    // 화폐 반환
    public static void returnMoney(){
        if(userWallet != null) {
            // 반환
            JOptionPane.showMessageDialog(vendingMachineGUI, userWallet.getTotalAmount() + "원이 반환 되었습니다.");
            userWallet.init();  // 메모리 해제

            // 총 금액 업데이트
            vendingMachineGUI.updateTotalAmountLabel(userWallet.getTotalAmount());
            // 판매가능 여부 업데이트
            vendingMachineGUI.updateBeverageButtons(beverages, userWallet.getTotalAmount());
        }
    }


    // 물품 구매
    public static void purchase(int index){
        int price = beverages.get(index).getPrice();        // 가격
        int payment = userWallet.getTotalAmount();          // 입력된 금액
        Stack<Integer> change = null;                       // 거스름 돈이 저장될 변수


        // 1. 입력된 금액 >> 자판기 돈통
        while(userWallet.getTotalAmount() > 0){
            vendingMachineWallet.insertNode(userWallet.dequeueCoin(), 1);
        }
        try {
            // 2. 자판기 돈통 -> 거스름돈
            change = vendingMachineWallet.returnChange(payment - price);

        } catch (NotEnoughChangeException e) {
            // 실패: 거스름돈 부족
            // 거스름돈 >> 자판기 돈통
            if(change != null)
                for (Integer coin : change)
                    vendingMachineWallet.insertNode(coin, 1);
            // 자판기 돈통 >> 사용자 입력금액
            change = vendingMachineWallet.returnChange(payment);
            for (Integer coin : change)
                userWallet.insertCoin(coin);

            JOptionPane.showMessageDialog(vendingMachineGUI, "에러: " + e.getMessage());
        }

        // 구매 성공
        JOptionPane.showMessageDialog(vendingMachineGUI, "주문완료: " + beverages.get(index).getName() + "(" + price + "원)");
        beverages.get(index).removeOneStock();  // 재고 하나 차감

        // 거스름 돈 체크 후 사용자 돈통에 삽입
        if(change != null){
            for(Integer coin : change){
                userWallet.insertCoin(coin);
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
