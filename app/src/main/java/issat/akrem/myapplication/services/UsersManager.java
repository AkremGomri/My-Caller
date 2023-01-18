package issat.akrem.myapplication.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import issat.akrem.myapplication.models.ContactUser;
import issat.akrem.myapplication.models.User;


public class UsersManager {
    SQLiteDatabase db;
    Context con;

    public UsersManager(Context con) {
        this.con = con;
    }

    public void openDB(){
        UserHelper helper = new UserHelper(con, UserHelper.table_name, null, 1);
        db = helper.getReadableDatabase();
    }

    public void closeDB(){
        db.close();
    }

    public long createNewUser(User u){
        long a=-3;
        try {
            ContentValues c = new ContentValues();
            c.put(UserHelper.col_email, u.email);
            c.put(UserHelper.col_password, u.password);
            c.put(UserHelper.col_salt, u.salt);
            a = db.insert(UserHelper.table_name, null, c);
            if (a<0){
                Toast.makeText(con, "problem while the user", Toast.LENGTH_SHORT).show();
            }
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }

    public long checkCredentials(User u){
        User c;
        String HashOfPassword;

        Cursor cr=db.query(
                UserHelper.table_name,
                new String[]{UserHelper.col_id , UserHelper.col_email, UserHelper.col_password, UserHelper.col_salt},
                null, null, null, null, null
        );

        cr.moveToFirst();
        while(!cr.isAfterLast()){
            if(cr.getString(1).equals(u.email)){
                c=new User(cr.getString(1), cr.getString(2), cr.getString(3));
                HashOfPassword = BCrypt.hashpw(u.password, c.salt);

                if(!c.password.equals(HashOfPassword)){
                    Toast.makeText(con, "password is incorrect", Toast.LENGTH_SHORT).show();
                    return -1;
                } else {
                    return cr.getInt(0);
                }
            }
            cr.moveToNext();
        }
        Toast.makeText(con, "user not found", Toast.LENGTH_SHORT).show();
        return -1;
    }
}
