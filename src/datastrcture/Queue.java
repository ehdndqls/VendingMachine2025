package datastrcture;

public class Queue {
    private int[] queue;
    private int front;
    private int rear;
    private int size;

    public Queue() {
        queue = new int[1];
        front = 0;
        rear = -1;
        size = 0;
    }

    // 원소 삽입
    public void enqueue(int value) {
        if (size == queue.length) {
            resize(queue.length * 2); // 크기 두 배로 증가
        }
        rear = (rear + 1) % queue.length;
        queue[rear] = value;
        size++;
    }

    // 원소 제거
    public int dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            System.gc();
            return -1;
        }
        int value = queue[front];
        front = (front + 1) % queue.length;
        size--;
        return value;
    }

    // 현재 front 값 확인
    public int peek() {
        if (isEmpty()) {
            System.out.println("Queue is empty!");
            return -1;
        }
        return queue[front];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    private void resize(int newCapacity) {
        int[] newQueue = new int[newCapacity];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }
        queue = newQueue;
        front = 0;
        rear = size - 1;
    }

}

