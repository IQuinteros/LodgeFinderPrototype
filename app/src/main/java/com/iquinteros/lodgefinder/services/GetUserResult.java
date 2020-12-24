package com.iquinteros.lodgefinder.services;

import com.iquinteros.lodgefinder.models.User;

import org.json.JSONArray;

public interface GetUserResult {
    public void onReady(User user);
}
