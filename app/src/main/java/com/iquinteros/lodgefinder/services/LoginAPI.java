package com.iquinteros.lodgefinder.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iquinteros.lodgefinder.models.Login;

import java.util.ArrayList;
import java.util.List;

public class LoginAPI {

    private SqliteConnection connection;

    public LoginAPI(Context context) {
        connection = new SqliteConnection(context);
    }

    public void insert(Login login){
        SQLiteDatabase db = connection.getWritableDatabase();

        try{

            ContentValues contentValues = new ContentValues();

            contentValues.put("id", 1);
            contentValues.put("email", login.getEmail());
            contentValues.put("password", login.getPassword());

            db.insert("login", null, contentValues);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }
    }

    public List<Login> getLogins(){

        SQLiteDatabase db = connection.getReadableDatabase();
        List<Login> list = new ArrayList<>();

        try{

            Cursor cursor = db.rawQuery("SELECT * FROM login", null);
            while (cursor.moveToNext()){
                Login login = new Login(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );

                list.add(login);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }

        return list;
    }

    public Login findLogin(){
        SQLiteDatabase db = connection.getReadableDatabase();
        Login foundLogin = null;

        Cursor cursor = db.rawQuery("SELECT * FROM login WHERE id = 1", new String[]{ });

        while(cursor.moveToNext()){
            foundLogin = new Login(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        }

        return foundLogin;
    }

    public boolean modifyLogin(Login login){
        SQLiteDatabase db = connection.getWritableDatabase();

        boolean modified = false;

        try{

            ContentValues contentValues = new ContentValues();

            contentValues.put("email", login.getEmail());
            contentValues.put("password", login.getPassword());

            modified = db.update("login", contentValues, "id = 1", new String[]{ }) > 0;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }

        return modified;
    }

    public boolean deleteLogin(){
        SQLiteDatabase db = connection.getWritableDatabase();
        return db.delete("login", "id = 1", new String[]{ }) > 0;
    }
}
