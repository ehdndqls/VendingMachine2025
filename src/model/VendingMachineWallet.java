package model;

import util.datastrcture.BinarySearchTree;
import util.error.NotEnoughChangeException;

import java.util.Stack;

/*
   화쳬 객체를 다루는 클래스 Money
   용도는 나중에
 */
public class VendingMachineWallet extends BinarySearchTree{

    Stack<Integer>change;
    int remaining;

    // 생성자
    public VendingMachineWallet(){
        change = new Stack<>();
    }

    public VendingMachineWallet(int count) {
        insertNode(10, count);         // 10원 10개
        insertNode(50, count);         // 50원 10개
        insertNode(100, count);        // 100원 10개
        insertNode(500, count);        // 500원 10개
        insertNode(1000, count);       // 1000원 10개
        change = new Stack<>();
    }

    // 기존의 돈에서 거스름돈만큼 빼서 리턴
    public Stack<Integer> returnChange(int price){
        this.change.clear();    // 거스름돈 초기화
        this.remaining = price;     // 반환해야할 금액
        if(price == 0) return null;
        System.out.println("함수실행 전 "+remaining);
        returnChange(getRoot());    // root노드 호출
        System.out.println("함수실행 후 "+remaining);
        if (remaining > 0) {             // 거스름돈이 부족한 경우
            for(int coin : change){     // 뺀 만큼의 돈을 다시 삽입
                insertNode(coin, 1);
            }
            throw new NotEnoughChangeException("거스름돈 부족: " + remaining + "원이 부족합니다.");  // 오류
        }
        return change;
    }

    private void returnChange(Node node) {  // 재귀함수를 통해 price에 해당하는 돈을 순차적으로 제거
        // 남은 금액이 0이거나 node가 null이면 종료
        if (node == null || remaining <= 0) return;

        // 내림차순
        returnChange(node.getRight());

        while(node.getKey() <= remaining && node.getValue() > 0){
            reduceValue(node);
            remaining -= node.getKey();
            change.push(node.getKey());
        }
        returnChange(node.getLeft());
    }

    // 총 보유 금액 반환
    public int getTotal() {
        if(getRoot() == null) return 0; // 만약 아무것도 없다면 0원 반환
        return getTotal(getRoot());
    }

    private int getTotal(Node node) {
        if (node == null) return 0;
        int total = 0;
        total += getTotal(node.getLeft());
        total += node.getValue() * node.getKey();
        total += getTotal(node.getRight());
        return total;
    }
}




