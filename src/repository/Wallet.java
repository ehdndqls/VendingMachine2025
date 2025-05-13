package repository;

import datastrcture.BinarySearchTree;
import error.NotEnoughChangeException;

import java.util.Stack;

/*
   화쳬 객체를 다루는 클래스 Money
   용도는 나중에
 */
public class Wallet extends BinarySearchTree{

    Stack<Integer>change;
    int price;

    // 생성자
    public Wallet(){
    }

    public Wallet(int count) {
        insertNode(10, count);         // 10원 10개
        insertNode(50, count);         // 50원 10개
        insertNode(100, count);        // 100원 10개
        insertNode(500, count);        // 500원 10개
        insertNode(1000, count);       // 1000원 10개
        change = new Stack<>();
    }

    // 구매
    public Stack<Integer> purchase(int price){
        this.change.clear();    // 거스름돈 초기화
        this.price = price;     // 물품 금액
        purchase(getRoot());    // root노드 호출
        if (price > 0) {
            throw new NotEnoughChangeException("거스름돈 부족: " + price + "원이 부족합니다.");
        }
        return change;
    }


    private void purchase(Node node) {
        // 남은 금액이 0이거나 node가 null이면 종료
        if (node == null || price <= 0) return;

        // 내림차순
        purchase(node.getRight());

        while(node.getKey() <= price && node.getValue() > 0){
            reduceValue(node);
            price -= node.getKey();
            change.push(node.getKey());
        }

        purchase(node.getLeft());
    }


    // 중위 순회 (오름차순 출력)
    public int getTotal() {
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

    public boolean checkCoin(int coin){
        return coin == 10 || coin == 50 || coin == 100 || coin == 500 || coin == 1000;
    }

}




