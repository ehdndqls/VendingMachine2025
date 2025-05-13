package vendingmechine;

import error.NotEnoughChangeException;
import repository.Beverage;
import repository.Wallet;

import java.util.ArrayList;
import java.util.Stack;

public class VendingMachine {

    static Wallet wallet;   // 자판기 보유 금액
    static Wallet tempWallet; // 사용자 입력금액
    static ArrayList<Beverage> beverages;

    static VendingMachineGUI vendingMachineGUI;

    public VendingMachine() {
        // 초기화
        // 돈통
        wallet = new Wallet(10);
        tempWallet = null;
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

        vendingMachineGUI = new VendingMachineGUI(beverages);
    }

    // 화폐 삽입
    public void pushCoin(int coin) {
        if(tempWallet == null){
            tempWallet = new Wallet();
        }
        tempWallet.insertNode(coin, 1);

        // 판매가능 여부 업데이트
        vendingMachineGUI.updateBeverageButtons(beverages, tempWallet.getTotal());
    }

    // 물품 구매
    public static void purchase(int index){


        Stack<Integer> change = null;
        try {
            change = wallet.purchase(beverages.get(index).getPrice());
            System.out.println("구매 완료 및 거스름돈 반환");
        } catch (NotEnoughChangeException e) {
            // 거스름돈 부족 시 change에 있는 동전들을 꺼내서 원래의 지갑에 다시 넣기
            if(change != null)
                for (Integer coin : change)
                    wallet.insertNode(coin, 1);

            System.err.println("에러: " + e.getMessage());
        }

        // 재고 차감
        // 거스름돈 반환
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
