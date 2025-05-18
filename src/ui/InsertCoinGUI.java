package ui;

import main.VendingMachine;
import serivce.VendingMachineService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InsertCoinGUI extends JFrame {

    private final VendingMachineService vendingMachineService;

    public InsertCoinGUI() {

        vendingMachineService = VendingMachine.vendingMachineService;

        setTitle("Insert Money");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 3)); // 2행 3열의 그리드 레이아웃 설정

        // 버튼 배열 생성
        JButton[] moneyButtons = new JButton[5];
        moneyButtons[0] = new JButton(10 + "원"); // 버튼 텍스트 설정
        moneyButtons[1] = new JButton(50 + "원"); // 버튼 텍스트 설정
        moneyButtons[2] = new JButton(100 + "원"); // 버튼 텍스트 설정
        moneyButtons[3] = new JButton(500 + "원"); // 버튼 텍스트 설정
        moneyButtons[4] = new JButton(1000 + "원"); // 버튼 텍스트 설정
        for (JButton moneyButton : moneyButtons) {
            moneyButton.addActionListener(new MoneyButtonListener());
            add(moneyButton); // 프레임에 버튼 추가
        }

        // 닫기 버튼 생성
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new CloseButtonListener());
        add(closeButton); // 프레임에 닫기 버튼 추가

        setVisible(true); // 프레임 표시
    }

    // 돈 버튼 액션 리스너
    private class MoneyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 돈 삽입

            // 눌린 버튼의 금액을 확인후
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();
            int moneyAmount = Integer.parseInt(buttonText.substring(0, buttonText.length() - 1));
            // 전송
            vendingMachineService.insertCoin(moneyAmount);
        }
    }

    // 닫기 버튼 액션 리스너
    private class CloseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}
