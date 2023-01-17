package issat.akrem.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class UserManager {
    SQLiteDatabase db=null;
    Context con;

    public UserManager(Context con) {
        this.con = con;
    }

    public void openDB(){
        UserHelper helper=new UserHelper(con, UserHelper.table_user, null, 1);
        db=helper.getWritableDatabase();
    }

    public int deleteContactUserById(String id){
        return db.delete(
                UserHelper.table_user,  // Where to delete
                UserHelper.col_id+" = ?",
                new String[]{id});  // What to delete
    }

    public ContactUser getContactUserById(String id){
        ContactUser c;
        Cursor cr=db.query(
                UserHelper.table_user,
                new String[]{UserHelper.col_id ,UserHelper.col_nom, UserHelper.col_prenom, UserHelper.col_numero},
                null, null, null, null, null
        );
        cr.moveToFirst();
        while(!cr.isAfterLast()){
            if(cr.getString(0) == id){
                c=new ContactUser(cr.getString(1), cr.getString(2), cr.getString(3));
                return c;
            }
        }

        Toast.makeText(con, "Couldn't find the user", Toast.LENGTH_SHORT).show();
        return null;
    }

    public long addContactUser(ContactUser c){
        ContentValues values= new ContentValues();
        values.put(UserHelper.col_nom, c.nom);
        values.put(UserHelper.col_prenom, c.prenom);
        values.put(UserHelper.col_numero, c.numero);
        long a= db.insert(UserHelper.table_user, null, values);
        Toast.makeText(con, "this is the contact "+c, Toast.LENGTH_SHORT).show();
        return a;
    }

    public long editContactUser(ContactUser c){
        long a= -1;
        ContentValues values= new ContentValues();
        values.put(UserHelper.col_id, c.id);
        values.put(UserHelper.col_nom, c.nom);
        values.put(UserHelper.col_prenom, c.prenom);
        values.put(UserHelper.col_numero, c.numero);
        a=db.update(UserHelper.table_user, values, UserHelper.col_id +"="+c.id, null);
        return a;
    }

    public ArrayList<ContactUser> getAllContactUsers(){
        ArrayList<ContactUser> usersList = new ArrayList<ContactUser>();
        Cursor cr=db.query(
                UserHelper.table_user,
                new String[]{
                        UserHelper.col_id,
                        UserHelper.col_nom,
                        UserHelper.col_prenom,
                        UserHelper.col_numero},
                null, null, null, null, null
        );

        cr.moveToFirst();
        while(!cr.isAfterLast()){
            String id = cr.getString(0);
            String nom = cr.getString(1);
            String prenom = cr.getString(2);
            String numero = cr.getString(3);
            usersList.add(new ContactUser(id, nom, prenom, numero));
            cr.moveToNext();
            System.out.println("nom: "+nom+" prenom "+prenom+" numero "+numero);
        }

        return usersList;
    }

    public ArrayList<ContactUser> filterContactUsers(CharSequence str){
        ArrayList<ContactUser> usersList = new ArrayList<ContactUser>();
        Cursor cr=db.query(
                UserHelper.table_user,
                new String[]{
                        UserHelper.col_id,
                        UserHelper.col_nom,
                        UserHelper.col_prenom,
                        UserHelper.col_numero},
                null, null, null, null, null
        );

        cr.moveToFirst();
        while(!cr.isAfterLast()){
            String id = cr.getString(0);
            String nom = cr.getString(1);
            String prenom = cr.getString(2);
            String numero = cr.getString(3);

            String fullLowercasedName = prenom.toLowerCase() + " " + nom.toLowerCase();
            String fullLowercasedNameRev = nom.toLowerCase() + " " + prenom.toLowerCase();

            if(fullLowercasedName.contains(str.toString().toLowerCase()) || fullLowercasedNameRev.contains(str.toString().toLowerCase()))
            {
                usersList.add(new ContactUser(id, nom, prenom, numero));
            }
            cr.moveToNext();
            System.out.println("nom: "+nom+" prenom "+prenom+" numero "+numero);
        }

        return usersList;
    }


    public void deleteAllContactUsers(){
        db.execSQL("DELETE FROM "+UserHelper.table_user);
    }

    public void closeDB(){
        db.close();
    }
}
