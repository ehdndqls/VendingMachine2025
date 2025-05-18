package ui;

import main.VendingMachine;
import serivce.VendingMachineService;
import util.error.LoginFailedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame {
    private JPasswordField passwordField; // 비밀번호 입력 필드
    private static String initialPassword = "sch20204054!"; // 초기 비밀번호
    private boolean authenticated; // 로그인 성공 여부를 나타내는 변수
    private final VendingMachineService vendingMachineService;

    public LoginGUI() {

        vendingMachineService = VendingMachine.vendingMachineService;

        setTitle("AdminSystem Login");
        setSize(360, 80);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout()); // 레이아웃 설정
        setLocationRelativeTo(null); // 화면 가운데 정렬
        setResizable(false); // 크기 조정 비활성화

        JLabel passwordLabel = new JLabel("PassWord:"); // 비밀번호 라벨 생성
        passwordField = new JPasswordField(); // 비밀번호 입력 필드 생성
        passwordField.setPreferredSize(new Dimension(160, 30)); // 크기 설정
        JButton loginButton = new JButton("Login"); // 로그인 버튼 생성

        add(passwordLabel); // 비밀번호 라벨 추가
        add(passwordField); // 비밀번호 입력 필드 추가
        add(new JLabel()); // 빈 라벨 추가 (레이아웃 맞추기용)
        add(loginButton); // 로그인 버튼 추가

        setVisible(true); // 프레임 표시
    }

    // 돈 버튼 액션 리스너
    private class LoginButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            try{
                vendingMachineService.Login(password);
            } catch (LoginFailedException ex) {
                JOptionPane.showMessageDialog(LoginGUI.this, "에러: " + ex.getMessage());
            }
            vendingMachineService.setAdminMode();
        }
    }
}
