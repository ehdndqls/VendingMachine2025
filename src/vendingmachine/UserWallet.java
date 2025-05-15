package vendingmachine;

import datastrcture.Queue;
import error.MoneyExceededException;

public class UserWallet {
    int totalAmount;    // 입력받은 화폐의 총량
    int totalPaper;     // 입력받은 지폐의 개수
    Queue money;        // 화폐를 입력받는 변수

    // 생성자 >> 변수 초기화
    public UserWallet() {
        this.totalAmount = 0;
        this.totalPaper = 0;
    }

    // 화폐를 받아 queue에 입력 >> 변수가 할당이 안되어있으면 변수 할당
    public void insertCoin(int coin){
        if(money.isEmpty())
            money = new Queue();

        money.enqueue(coin);
    }

    public int dequeueCoin(){
        int coin = money.dequeue();

        totalAmount -= coin;
        if(coin == 1000)
            totalPaper--;

        if(money.isEmpty())
            System.gc();
        return coin;
    }

    // 입력 금액의 범위 체크
    public void checkExceedMoney(int coin){
        if(coin == 1000 && totalPaper >= 5){
            throw new MoneyExceededException("금액 초과: " + coin + "원권 5장 이상 사용할 수 없습니다. ");
        }
        if(totalAmount + coin > 7000){
            throw new MoneyExceededException("금액 초과: 최대 7000원을 초과할 수 없습니다.");
        }
    }

    // 초기화 >> 화폐를 입력받는 변수는 메모리할당을 해제
    public void init(){
        totalAmount = 0;
        totalPaper = 0;
        money = null;
        System.gc();
    }

    // 입력받은 변수의 총액을 반환하는 함수
    public int getTotalAmount(){
        return totalAmount;
    }
}
