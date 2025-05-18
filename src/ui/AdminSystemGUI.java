package ui;

import main.AdminSystem;
import serivce.AdminSystemService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminSystemGUI extends JFrame {
    private JButton changePasswordButton;       // 비밀번호 변경
    private JButton beverageStatusButton;       // 물품 관리
    private JButton replenishBeverageButton;     // 재고 추가
    private JButton exitButton;                 // 종료
    private JButton searchButton;               // 검색
    private JButton collectMoneyButton;         // 화폐 수금
    private JButton replenishMoneyButton;       // 화폐보충
    private JLabel moneyStatusLabel;            // 화폐 현황
    private JLabel dailySalesLabel;             // 일별 매출
    private JLabel salesListLabel;              // 매출리스트
    private JLabel monthlySalesLabel;           // 월별 매출

    private final AdminSystemService adminSystemService;

    public AdminSystemGUI() {

        adminSystemService = AdminSystem.adminSystemService;

        setTitle("AdminSystem");
        setSize(1000, 630);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("자판기 관리자 시스템", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(24f));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setPreferredSize(new Dimension(200, 600));

        // 좌측 버튼 4개
        changePasswordButton = new JButton("비밀번호 변경");
        beverageStatusButton = new JButton("판매 물품 관리");
        replenishBeverageButton = new JButton("재고 추가");
        exitButton = new JButton("종료");

        changePasswordButton.setBounds(20, 250, 150, 50);
        beverageStatusButton.setBounds(20, 320, 150, 50);
        replenishBeverageButton.setBounds(20, 390, 150, 50);
        exitButton.setBounds(20, 460, 150, 50);

        changePasswordButton.addActionListener(new changePasswordListener());
        beverageStatusButton.addActionListener(new beverageManagementListener());
        replenishBeverageButton.addActionListener(new replenishBeverageListener());
        exitButton.addActionListener(new exitButtonListener());

        buttonPanel.add(changePasswordButton);
        buttonPanel.add(beverageStatusButton);
        buttonPanel.add(replenishBeverageButton);
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.WEST);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null);

        // 콤보박스 및 검색 버튼
        JComboBox<String> beverageComboBox = new JComboBox<>(new String[]{"콜라", "사이다", "커피"});
        JComboBox<String> yearComboBox = new JComboBox<>(new String[]{"2024", "2025"});
        JComboBox<String> monthComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"});
        JComboBox<String> dayComboBox = new JComboBox<>(new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15"});
        searchButton = new JButton("검색");

        beverageComboBox.setBounds(320, 10, 100, 20);
        yearComboBox.setBounds(430, 10, 80, 20);
        monthComboBox.setBounds(520, 10, 60, 20);
        dayComboBox.setBounds(590, 10, 60, 20);
        searchButton.setBounds(660, 10, 80, 20);

        searchButton.addActionListener(new searchButtonListener());

        centerPanel.add(beverageComboBox);
        centerPanel.add(yearComboBox);
        centerPanel.add(monthComboBox);
        centerPanel.add(dayComboBox);
        centerPanel.add(searchButton);

        // 왼쪽 상단 - 화폐 현황
        moneyStatusLabel = new JLabel("화폐 현황:");
        moneyStatusLabel.setBounds(0, 60, 100, 20);
        collectMoneyButton = new JButton("수금");
        replenishMoneyButton = new JButton("보충");
        collectMoneyButton.setBounds(100, 60, 80, 20);
        replenishMoneyButton.setBounds(190, 60, 80, 20);

        collectMoneyButton.addActionListener(new collectButtonListener());
        replenishMoneyButton.addActionListener(new replenishButtonListener());

        JTextArea moneyTextArea = new JTextArea();
        JScrollPane moneyScrollPane = new JScrollPane(moneyTextArea);
        moneyScrollPane.setBounds(0, 90, 360, 100);

        centerPanel.add(moneyStatusLabel);
        centerPanel.add(collectMoneyButton);
        centerPanel.add(replenishMoneyButton);
        centerPanel.add(moneyScrollPane);

        // 오른쪽 상단 - 일별 매출
        dailySalesLabel = new JLabel("일별 매출:");
        dailySalesLabel.setBounds(390, 60, 100, 20);
        JTextArea dailySalesArea = new JTextArea();
        JScrollPane dailySalesScroll = new JScrollPane(dailySalesArea);
        dailySalesScroll.setBounds(390, 90, 360, 200);

        centerPanel.add(dailySalesLabel);
        centerPanel.add(dailySalesScroll);

        // 왼쪽 하단 - 매출 리스트
        salesListLabel = new JLabel("매출 리스트:");
        salesListLabel.setBounds(0, 190, 100, 20);
        JTextArea salesListArea = new JTextArea();
        JScrollPane salesListScroll = new JScrollPane(salesListArea);
        salesListScroll.setBounds(0, 220, 360, 300);
        centerPanel.add(salesListLabel);
        centerPanel.add(salesListScroll);

        // 오른쪽 하단 - 월별 매출
        monthlySalesLabel = new JLabel("월별 매출:");
        monthlySalesLabel.setBounds(390, 310, 100, 30);
        JTextArea monthlySalesArea = new JTextArea();
        JScrollPane monthlySalesScroll = new JScrollPane(monthlySalesArea);
        monthlySalesScroll.setBounds(390, 350, 360, 165);
        centerPanel.add(monthlySalesLabel);
        centerPanel.add(monthlySalesScroll);

        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private class changePasswordListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 비밀번호 변경 로직
            adminSystemService.authenticatePassword();
        }
    }

    private class beverageManagementListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 음료 관리 로직

        }
    }

    private class replenishBeverageListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 재고 추가 로직
        }
    }

    private class exitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class searchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 검색 버튼 로직
        }
    }

    private class collectButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 수금 버튼 로직
        }
    }

    private class replenishButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 보충 버튼 로직
        }
    }

    public static void main(String[] args) {
        new AdminSystemGUI();
    }
}
