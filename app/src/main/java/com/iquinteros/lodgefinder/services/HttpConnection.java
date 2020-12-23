package com.iquinteros.lodgefinder.services;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import cz.msebera.android.httpclient.Header;

public class HttpConnection {
    // Singleton
    public static HttpConnection httpConnection;

    // Constructor
    public HttpConnection(Context context) {
        this.currentContext = context;
    }

    private Context currentContext;

    public Context getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    private AsyncHttpClient client = new AsyncHttpClient();

    public JSONArray getDataFromUrl(String url, String[] keys, String[] params){
        RequestParams requestParams = new RequestParams();

        for (int i = 0; i < keys.length; i++){
            requestParams.add(keys[i], params[i]);
        }

        final String[] toReturn = new String[0];

        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response;
                response = new String(responseBody);

                if(response.equals("[]")){
                    printToast("No encontrado");
                }
                else{
                    toReturn[0] = response;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                printToast("Ha ocurrido un error");
            }
        });

        try {
            JSONArray jsonResponse = new JSONArray(toReturn[0]);

            return jsonResponse;
        }
        catch(Exception e) {
            System.out.println("Error JSON: " + e.toString());
            e.printStackTrace();
            return null;
        }

    }

    private void printToast(String msg){
        Toast.makeText(currentContext, msg,Toast.LENGTH_SHORT).show();
    }
}
