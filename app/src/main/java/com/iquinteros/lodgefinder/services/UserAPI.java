package com.iquinteros.lodgefinder.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iquinteros.lodgefinder.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserAPI {

    private SqliteConnection connection;

    public UserAPI(Context context) {
        connection = new SqliteConnection(context);
    }

    public void insert(User user){
        SQLiteDatabase db = connection.getWritableDatabase();

        try{

            ContentValues contentValues = new ContentValues();

            contentValues.put("id", user.getId());
            contentValues.put("nombres", user.getNombres());
            contentValues.put("apellidos", user.getApellidos());
            contentValues.put("email", user.getEmail());
            contentValues.put("rut", user.getRut());
            contentValues.put("contacto", user.getNumeroContacto());
            contentValues.put("foto", user.getFoto());
            contentValues.put("empresa", user.isEmpresa());

            db.insert("usuario", null, contentValues);

        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }
    }

    public List<User> getUser(){

        SQLiteDatabase db = connection.getReadableDatabase();
        List<User> list = new ArrayList<>();

        try{

            Cursor cursor = db.rawQuery("SELECT * FROM usuario", null);
            while (cursor.moveToNext()){
                User user = new User(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        cursor.getInt(6),
                        cursor.getInt(7) == 1
                );

                list.add(user);
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

}
