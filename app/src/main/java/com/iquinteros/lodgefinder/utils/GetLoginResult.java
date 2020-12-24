package com.iquinteros.lodgefinder.utils;

import com.iquinteros.lodgefinder.models.Login;
import com.iquinteros.lodgefinder.models.User;

public interface GetLoginResult {
    public void onReady(Login login, User user);
}
