package repository;

/*
   음료 객체를 다루는 클래스 Beverage
   재고 수는 연결리스트로 관리
 */
public class Beverage {

    private String name;
    private Integer price;
    private StockNode stock; // 연결 리스트로 재고 관리

    // 내부 클래스: 재고 노드
    private static class StockNode {
        StockNode next;
    }

    // 생성자
    public Beverage(String name, Integer price, int stockCount, Boolean isSoldout) {
        this.name = name;
        this.price = price;
        this.stock = null;
        addStock(stockCount);
    }

    // 초기 생성자: 기본 재고 10개
    public Beverage(String name, Integer price) {
        this.name = name;
        this.price = price;
        this.stock = null;
        addStock(10);
    }

    // 재고 수만큼 노드 추가
    public void addStock(int count) {
        for (int i = 0; i < count; i++) {
            StockNode newNode = new StockNode();
            newNode.next = stock;
            stock = newNode;
        }
    }

    // 재고 한 개 제거
    public void removeOneStock() {
        if (stock != null) {
            stock = stock.next;
        }
    }

    // 현재 재고 개수 반환
    public int getStock() {
        int count = 0;
        StockNode temp = stock;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    // getter
    public String getName() { return name; }
    public Integer getPrice() { return price; }

    // setter
    public void setName(String name) { this.name = name; }
    public void setPrice(Integer price) { this.price = price; }

    // 품절 여부 반환
    public Boolean checkSoldout() { return stock == null; }

    public Boolean checkEnoughMoney(int price){
        return price >= getPrice();
    }

}
