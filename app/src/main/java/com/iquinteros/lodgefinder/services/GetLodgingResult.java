package com.iquinteros.lodgefinder.services;

import com.iquinteros.lodgefinder.models.Lodging;

public interface GetLodgingResult {
    public void onReady(Lodging lodging);
}
