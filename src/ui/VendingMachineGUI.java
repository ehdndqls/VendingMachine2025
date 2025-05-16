package ui;

import model.Beverage;
import serivce.VendingMachineService;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class VendingMachineGUI extends JFrame {
    private JButton[] beverageButtons;
    private JButton depositButton;
    private JButton returnChangeButton;
    private JButton adminModeButton;
    private JLabel[] beverageLabels;
    private JLabel[] nameLabels;
    private JLabel[] priceLabels;
    private JLabel totalAmountLabel;
    private final VendingMachineService vendingMachineService;

    public VendingMachineGUI(ArrayList<Beverage> beverages) {

        vendingMachineService = new VendingMachineService();

        setTitle("자판기");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // 현재 입력된 금액의 총량을 나타내는 라벨 생성
        totalAmountLabel = new JLabel("현재 금액: 0원");
        totalAmountLabel.setBounds(220, 380, 150, 30);
        totalAmountLabel.setHorizontalAlignment(JLabel.CENTER);
        add(totalAmountLabel);

        // 음료 이름 라벨 생성
        nameLabels = new JLabel[6];
        for (int i = 0; i < nameLabels.length; i++) {
            nameLabels[i] = new JLabel(beverages.get(i).getName());
            nameLabels[i].setBounds(20 + i % 3 * 120, 30 + i / 3 * 150, 100, 20);
            nameLabels[i].setHorizontalAlignment(JLabel.CENTER);
            add(nameLabels[i]);
        }

        // 음료 라벨 생성
        beverageLabels = new JLabel[6];
        for (int i = 0; i < beverageLabels.length; i++) {
            ImageIcon icon = new ImageIcon(beverages.get(i).getName() + ".png");
            beverageLabels[i] = new JLabel(icon);
            beverageLabels[i].setBounds(40 + i % 3 * 120, 50 + i / 3 * 150, 60, 60);
            add(beverageLabels[i]);
        }

        // 음료 가격 라벨 생성
        priceLabels = new JLabel[6];
        for (int i = 0; i < priceLabels.length; i++) {
            priceLabels[i] = new JLabel(beverages.get(i).getPrice() + "원");
            priceLabels[i].setBounds(20 + i % 3 * 120, 110 + i / 3 * 150, 100, 20);
            priceLabels[i].setHorizontalAlignment(JLabel.CENTER);
            add(priceLabels[i]);
        }

        // 음료 버튼 생성
        beverageButtons = new JButton[6];
        for (int i = 0; i < beverageButtons.length; i++) {
            beverageButtons[i] = new JButton();
            beverageButtons[i].addActionListener(new BeverageButtonListener(i));
            beverageButtons[i].setBounds(20 + i % 3 * 120, 140 + i / 3 * 150, 100, 20);
            add(beverageButtons[i]);
        }

        // 돈 삽입 버튼 생성
        depositButton = new JButton("돈 삽입");
        depositButton.setBounds(220, 430, 150, 30);
        depositButton.addActionListener(new DepositListener());
        add(depositButton);

        // 잔돈 반환 버튼 생성
        returnChangeButton = new JButton("잔돈 반환");
        returnChangeButton.setBounds(220, 470, 150, 30);
        returnChangeButton.addActionListener(new ReturnChangeListener());
        add(returnChangeButton);

        // 관리자 모드 버튼 생성
        adminModeButton = new JButton("관리자 모드");
        adminModeButton.setBounds(220, 510, 150, 30);
        adminModeButton.addActionListener(new AdminModeButtonListener());
        add(adminModeButton);

        updateBeverageButtons(beverages, 0);
        setVisible(true);
    }


    // 현재 자판기에 입력된 총 금액을 표시하는 레이블을 업데이트하는 메서드
    public void updateTotalAmountLabel(Integer totalAmount) {
        totalAmountLabel.setText("현재 금액: " + totalAmount + "원");
    }

    // 음료의 이름과 가격을 표시하는 레이블을 업데이트하는 메서드
    public void updateBeverageLabel(String beverageName, Integer beveragePrice) {
        for (int i = 0; i < beverageLabels.length; i++) {
            nameLabels[i] = new JLabel(beverageName);
            priceLabels[i] = new JLabel(beveragePrice + "원");
        }
    }

    // 음료 버튼을 업데이트하는 메서드
    public void updateBeverageButtons(ArrayList<Beverage> beverages, int price) {
        for (int i = 0; i < beverageButtons.length; i++) {
            if (beverages.get(i).checkSoldout()) {
                beverageButtons[i].setText("품절");
                beverageButtons[i].setEnabled(false);
            } else if (!beverages.get(i).checkEnoughMoney(price)) {  // 여기 수정
                beverageButtons[i].setText("");
                beverageButtons[i].setEnabled(false);
            } else {
                beverageButtons[i].setText("구매");
                beverageButtons[i].setEnabled(true);
            }
        }
    }

    private class BeverageButtonListener implements ActionListener {
        private final int index;
        // 생성자: 정수 매개변수를 받아서 beverageNum에 저장합니다.
        public BeverageButtonListener(int beverageNum) {
            this.index = beverageNum;
        }

        public void actionPerformed(ActionEvent e) {
            vendingMachineService.purchase(index); // 구매
        }
    }

    private class DepositListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 돈 삽입 버튼이 클릭되었을 때의 동작
            vendingMachineService.insertCoin();
        }
    }

    private class ReturnChangeListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 돈 반환 버튼이 클릭되었을 때의 동작
            vendingMachineService.returnMoney();
        }
    }

    private class AdminModeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 관리자 모드 버튼이 클릭되었을 때의 동작
            /*if (vendingMachine.CurrentMoney != null) {
                JOptionPane.showMessageDialog(VendingMachineGUI.this, "잔돈이 반환되지 않았습니다.");
            } else {
                Login = new LoginSystem();
                Login.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        if (Login.isAuthenticated()) {
                            // 인증에 성공하면 AdminSystem을 호출합니다.
                            dispose();
                            AdminSystem adminSystem = new AdminSystem(vendingMachine);
                        }
                    }
                });
            }*/
        }
    }
}



