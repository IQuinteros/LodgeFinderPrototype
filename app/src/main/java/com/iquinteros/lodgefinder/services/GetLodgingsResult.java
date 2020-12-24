package com.iquinteros.lodgefinder.services;

import com.iquinteros.lodgefinder.models.Lodging;

import java.util.ArrayList;

public interface GetLodgingsResult {
    public void onReady(ArrayList<Lodging> lodgings);
}
