package com.iquinteros.lodgefinder.services;

import org.json.JSONArray;

public interface GetDataResult {
    public void onGetData(JSONArray result);
    public void onFail();
}
