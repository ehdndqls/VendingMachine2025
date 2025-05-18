package serivce;

import main.VendingMachine;
import util.error.LoginFailedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginService {
    private static String password = "sch20204054!!";

    // 로그인 함수 자판기 비밀번호와 사용자가 입력한 비밀번호가 같으면 성공 다르면 실패
    public static void Login(String inputPassword){
        if(password.equals(inputPassword))
            System.out.println("성공ㅎㅎ");
        else{
            throw new LoginFailedException("로그인 실패");
        }
    }


    // 비밀번호 유효성 검사 메서드
    public static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasSpecialChar = password.matches(".*[^a-zA-Z0-9].*");
        boolean hasDigit = password.matches(".*\\d.*");
        return hasSpecialChar && hasDigit;
    }

    public static void setPassword(String newPassword) {
        password = newPassword;
    }


}
