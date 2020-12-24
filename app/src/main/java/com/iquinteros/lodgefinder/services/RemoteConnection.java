package com.iquinteros.lodgefinder.services;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import cz.msebera.android.httpclient.Header;


public class RemoteConnection {
    // Singleton
    public static RemoteConnection remoteConnection = new RemoteConnection();

    private AsyncHttpClient client = new AsyncHttpClient();


    public void getDataFromUrl(String url, String[] keys, String[] params, final GetDataResult getDataResult){
        RequestParams requestParams = new RequestParams();

        for (int i = 0; i < keys.length; i++){
            requestParams.add(keys[i], params[i]);
        }

        final ArrayList<String> toReturn = new ArrayList<String>();

        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response;
                response = new String(responseBody);

                if(response.isEmpty()){
                    System.out.println("Error HTTP: Nada encontrado");
                }
                else{
                    toReturn.add(response);
                }

                try {
                    JSONArray jsonResponse = new JSONArray(toReturn.get(0));
                    getDataResult.onGetData(jsonResponse);
                }
                catch(Exception e) {
                    System.out.println("Error JSON: " + e.toString());
                    e.printStackTrace();
                    getDataResult.onFail();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Error HTTP: " + error.getMessage());
                getDataResult.onFail();
            }
        });

    }
}
