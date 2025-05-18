package serivce;

import ui.ChangePasswordGUI;
import ui.LoginGUI;

public class AdminSystemService {

    public AdminSystemService() {
    }

    public void authenticatePassword(){
        new LoginGUI("ChangePassword");
    }

    // 비밀번호 변경
    public void ChangePassword(){
        new ChangePasswordGUI();
    }



    //
}
