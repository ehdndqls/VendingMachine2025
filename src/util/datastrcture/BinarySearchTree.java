package util.datastrcture;

// 트리 - 사용 : 돈관리

public class BinarySearchTree {
    protected class Node {
        int key;    // 키값 - 화폐단위
        int value; // 데이터 - 화폐개수
        Node left, right;   // 자식노드

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    // getter
        public int getKey(){ return key; }
        public int getValue() { return value; }
        public Node getLeft() { return left; }
        public Node getRight() { return right; }
    }

    private Node root;  // 루트노드


    // 삽입 (이미 존재하면 기존값 += 새로운값)
    public void insertNode(int key, int value) {
        root = insertNode(root, key, value);
    }

    private Node insertNode(Node node, int key, int value) {
        if (node == null) return new Node(key, value);

        if (key < node.key) {
            node.left = insertNode(node.left, key, value);
        } else if (key > node.key) {
            node.right = insertNode(node.right, key, value);
        } else {
            node.value += value; // 값 갱신
        }

        return node;
    }

    // getter: root
    public Node getRoot(){
        return root;
    }

    // getter: value
    public Integer get(int key) {
        Node node = root;
        while (node != null) {
            if (key < node.key) node = node.left;
            else if (key > node.key) node = node.right;
            else return node.value;
        }
        return null; // 없으면 null
    }

    // 포함 여부
    public boolean containsKey(int key) {
        return get(key) != null;
    }

    // 중위 순회 (오름차순 출력)
    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(Node node) {
        if (node == null) return;
        printInOrder(node.left);
        System.out.println(node.key + " => " + node.value);
        printInOrder(node.right);
    }

    // 화폐 개수 차감
    public void reduceValue(Node node){
        node.value--;
        if(node.value <= 0)     // value가 0이면 삭제
            remove(node.key);
    }

    // 키 삭제 (단순 구현: 두 자식 있는 경우는 오른쪽 최소값 대체)
    public void remove(int key) {
        root = remove(root, key);
    }


    private Node remove(Node node, int key) {
        if (node == null) return null;

        if (key < node.key) {
            node.left = remove(node.left, key);
        } else if (key > node.key) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // 두 자식이 있을 경우: 오른쪽 서브트리에서 최소 노드 찾기
            Node minNode = getMin(node.right);
            node.key = minNode.key;
            node.value = minNode.value;
            node.right = remove(node.right, minNode.key);
        }

        return node;
    }

    private Node getMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }
}
