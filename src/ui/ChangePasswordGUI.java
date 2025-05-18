package ui;

import serivce.LoginService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePasswordGUI {

    public ChangePasswordGUI() {
        JFrame changePasswordFrame = new JFrame("Change Password"); // 패스워드 변경 프레임 생성
        changePasswordFrame.setSize(360, 160); // 크기 설정
        changePasswordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 동작 설정
        changePasswordFrame.setLayout(new FlowLayout()); // 레이아웃 설정
        changePasswordFrame.setLocationRelativeTo(null); // 화면 가운데 정렬
        changePasswordFrame.setResizable(false); // 크기 조정 비활성화

        JLabel newPasswordLabel = new JLabel("New Password:"); // 새 비밀번호 라벨 생성
        JPasswordField newPasswordField = new JPasswordField(); // 새 비밀번호 입력 필드 생성
        newPasswordField.setPreferredSize(new Dimension(160, 30)); // 크기 설정

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:"); // 비밀번호 확인 라벨 생성
        JPasswordField confirmPasswordField = new JPasswordField(); // 비밀번호 확인 입력 필드 생성
        confirmPasswordField.setPreferredSize(new Dimension(160, 30)); // 크기 설정

        JButton changeButton = new JButton("Change"); // 변경 버튼 생성

        // 변경 버튼 액션 리스너 추가
        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char[] newPasswordChars = newPasswordField.getPassword();
                String newPassword = new String(newPasswordChars);
                char[] confirmPasswordChars = confirmPasswordField.getPassword();
                String confirmPassword = new String(confirmPasswordChars);

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(changePasswordFrame, "비밀번호를 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
                } else if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(changePasswordFrame, "비밀번호가 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
                } else if (!LoginService.isValidPassword(newPassword)) {
                    JOptionPane.showMessageDialog(changePasswordFrame, "비밀번호는 특수문자와 숫자를 포함하여 8자리 이상이어야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
                } else {
                    LoginService.setPassword(newPassword);
                    JOptionPane.showMessageDialog(changePasswordFrame, "비밀번호가 변경되었습니다.");
                    changePasswordFrame.dispose();
                }
            }
        });

        changePasswordFrame.add(newPasswordLabel); // 새 비밀번호 라벨 추가
        changePasswordFrame.add(newPasswordField); // 새 비밀번호 입력 필드 추가
        changePasswordFrame.add(confirmPasswordLabel); // 비밀번호 확인 라벨 추가
        changePasswordFrame.add(confirmPasswordField); // 비밀번호 확인 입력 필드 추가
        changePasswordFrame.add(changeButton); // 변경 버튼 추가

        changePasswordFrame.setVisible(true); // 프레임 표시
    }
}
