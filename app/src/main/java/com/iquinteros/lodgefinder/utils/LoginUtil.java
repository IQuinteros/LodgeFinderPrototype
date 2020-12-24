package com.iquinteros.lodgefinder.utils;

import android.content.Context;

import com.iquinteros.lodgefinder.MainActivity;
import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;
import com.iquinteros.lodgefinder.services.GetSuccessResult;
import com.iquinteros.lodgefinder.services.GetUserResult;
import com.iquinteros.lodgefinder.services.LoginAPI;
import com.iquinteros.lodgefinder.services.UserAPI;

public class LoginUtil {

    public static void login(final Context context, final String email, final String password, final GetSuccessResult getSuccessResult){
        UserAPI.userAPI.getUserByEmailAndPassword(email, password, new GetUserResult() {
            @Override
            public void onReady(User user) {
                if(user != null){
                    LoginAPI loginAPI = new LoginAPI(context);

                    // Sobreescribir
                    loginAPI.deleteLogin();

                    Login login = new Login(1, email, password);
                    loginAPI.insert(login);

                    getSuccessResult.onReady(true);
                }
                else{
                    // User es nulo.
                    getSuccessResult.onReady(false); // El email o la contraseña no coinciden.
                }
                ((MainActivity)context).updateLoginUser();
            }
        });
    }

    public static boolean logout(Context context){
        LoginAPI loginAPI = new LoginAPI(context);
        boolean result = loginAPI.deleteLogin();
        ((MainActivity)context).updateLoginUser();
        return result;
    }

    public static void checkLogin(Context context, final GetLoginResult getLoginResult){
        LoginAPI loginAPI = new LoginAPI(context);
        final Login login = loginAPI.findLogin();

        if(login == null){ getLoginResult.onReady(null, null); return; }

        UserAPI.userAPI.getUserByEmailAndPassword(login.getEmail(), login.getPassword(), new GetUserResult() {
            @Override
            public void onReady(User user) {
                if(user != null)
                    getLoginResult.onReady(login, user);
                else
                    getLoginResult.onReady(null, null);
            }
        });
    }

}
