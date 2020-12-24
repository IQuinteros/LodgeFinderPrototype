package com.iquinteros.lodgefinder.utils;

import android.content.Context;

import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.LoginAPI;
import com.iquinteros.lodgefinder.services.UserAPI;

public class LoginUtil {

    public static boolean login(Context context, String email, String password){
        User user = UserAPI.userAPI.getUserByEmailAndPassword(email, password);

        if(user != null){
            LoginAPI loginAPI = new LoginAPI(context);

            // Sobreescribir
            loginAPI.deleteLogin();

            Login login = new Login(1, email, password);
            loginAPI.insert(login);

            return true;
        }
        else{
            // User es nulo.
            return false; // El email o la contraseña no coinciden.
        }
    }

    public static boolean logout(Context context){
        LoginAPI loginAPI = new LoginAPI(context);
        return loginAPI.deleteLogin();
    }

    public static Login checkLogin(Context context){
        LoginAPI loginAPI = new LoginAPI(context);
        Login login = loginAPI.findLogin();

        if(login == null){ return null; }

        User user = UserAPI.userAPI.getUserByEmailAndPassword(login.getEmail(), login.getPassword());

        return user != null? login : null;
    }

}
