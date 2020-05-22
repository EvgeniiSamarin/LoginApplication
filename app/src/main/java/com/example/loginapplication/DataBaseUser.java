package com.example.loginapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataBaseUser extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "email";
    public static final String COL_3 = "password";
    public static final String COL_4 = "fio";
    public static final String COL_5 = "telephone";

    public DataBaseUser(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT, fio TEXT, telephone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", user.getEmail());
        contentValues.put("password", user.getPassword());
        contentValues.put("fio", user.getFio());
        contentValues.put("telephone", user.getTelephone());
        long res = database.insert("registeruser", null, contentValues);
        database.close();
        return res;
    }

    public boolean checkUser(User user) {
        String[] columns = {COL_1};
        SQLiteDatabase database = getReadableDatabase();
        String selection = COL_2 + "=? " + " and " + COL_3 + "=? ";
        String [] selectionArgs = { user.getEmail(), user.getPassword()};
        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null,null);
        int count = cursor.getCount();
        cursor.close();
        database.close();

        if(count > 0) {
            return true;
        } else {
            return  false;
        }
    }

    public User getUser(String email) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, "email=?", new String[]{email}, null, null, null);
        String emaill;
        String password;
        String fio;
        String telephone;

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    password = cursor.getString(cursor.getColumnIndex("password"));
                    fio = cursor.getString(cursor.getColumnIndex("fio"));
                    telephone = cursor.getString(cursor.getColumnIndex("telephone"));


                } while (cursor.moveToNext());
                User user = new User(fio, email, password, telephone);
                return user;
            }

        }
        return null;
    }


    /*private Map<String, User> map;
    private Map<Boolean, User> authMap;
    File file = new File("db.txt");

    public DataBaseUser() {
        map = new HashMap<>();

    }

    public boolean checkUser(User user) {
        if(map.isEmpty()) return false;
        if(map.containsKey(user.getEmail())) {
            return true;
        }
        return false;
    }
    public boolean getUser(String email, String password) {
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] str = line.split("\\|");
                if(str[0].equals(email) && str[1].equals(password)) {
                    return true;
                }
            }

        } catch (IOException ex) {
            //Toast.makeText(DataBaseUser.this, " User created successfully ", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean registeredUser(User user) {
        try{
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            map.put(user.getEmail(), user);
            for(int i = map.size(); i > 0; i++) {
                bufferedWriter.write(map.get(i).getEmail() + "\\|" + map.get(i).getPassword() + "\\|" + map.get(i).getFio() + "\\|" + map.get(i).getTelephone() + "\n");
            }
            bufferedWriter.write(user.getEmail() + "\\|" + user.getPassword() + "\\|" + user.getFio() + "\\|" + user.getTelephone() + "\n");

            fileWriter.flush();
            fileWriter.close();   
            bufferedWriter.close();
            return true;
        } catch (IOException ex) {
            //TOAST
        }
        return false;
    }

    /*
    public static final String SP_NAME = "userInformation";
    private SharedPreferences userLocalDatabases;

    public DataBaseUser(Context context) {
        userLocalDatabases = context.getSharedPreferences(SP_NAME, 0);

    }

    public void storeUser(User user) {
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        spEditor.putString("email", user.getEmail());
        spEditor.putString("fio", user.getFio());
        spEditor.putString("password", user.getPassword());
        spEditor.putString("telephone", user.getTelephone());
        spEditor.commit();
    }

    public User getLoggedinUser() {
        String fio = userLocalDatabases.getString("fio", "");
        String email = userLocalDatabases.getString("email", "");
        String password = userLocalDatabases.getString("password", "");
        String telephone = userLocalDatabases.getString("telephone", "");
        return new User(fio, email, password, telephone);

    }

    public void setUserLogIn(boolean loggedin) {
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        spEditor.putBoolean("loggedin", loggedin);
        spEditor.commit();
    }

    public void clearUserData() {
        SharedPreferences.Editor spEditor = userLocalDatabases.edit();
        spEditor.clear();
        spEditor.commit();
    }

    public boolean getUserLoggedIn() {
        if (userLocalDatabases.getBoolean("loggedin", false)) {
            return true;
        } else {
            return false;
        }
    }

     */


}
