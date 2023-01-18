package issat.akrem.myapplication.services;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import issat.akrem.myapplication.models.ContactUser;

public class ContactUserManager {
    SQLiteDatabase db=null;
    Context con;
    String createdByEmail;

    public ContactUserManager(Context con) {
        this.con = con;
        SharedPreferences preferences = con.getSharedPreferences("myUserAccount", Context.MODE_PRIVATE);
        createdByEmail = preferences.getString("email", "");
    }

    public void openDB(){
        ContactUserHelper helper=new ContactUserHelper(con, ContactUserHelper.table_user, null, 1);
        db=helper.getWritableDatabase();
    }

    public int deleteContactUserById(String id){
        return db.delete(
                ContactUserHelper.table_user,  // Where to delete
                ContactUserHelper.col_id+" = ?",
                new String[]{id});  // What to delete
    }

    public ContactUser getContactUserById(String id){
        ContactUser c;
        Cursor cr=db.query(
                ContactUserHelper.table_user,
                new String[]{ContactUserHelper.col_id , ContactUserHelper.col_nom, ContactUserHelper.col_prenom, ContactUserHelper.col_numero},
                null, null, null, null, null
        );
        cr.moveToFirst();
        while(!cr.isAfterLast()){
            if(cr.getString(0) == id){
                c=new ContactUser(cr.getString(1), cr.getString(2), cr.getString(3), cr.getString(4));
                return c;
            }
        }

        Toast.makeText(con, "Couldn't find the user", Toast.LENGTH_SHORT).show();
        return null;
    }

    public long addContactUser(ContactUser c){
        ContentValues values= new ContentValues();
        values.put(ContactUserHelper.col_nom, c.nom);
        values.put(ContactUserHelper.col_prenom, c.prenom);
        values.put(ContactUserHelper.col_numero, c.numero);
        values.put(ContactUserHelper.col_createdBy, c.createdBy);
        long a= db.insert(ContactUserHelper.table_user, null, values);
        Toast.makeText(con, "Contact inserted: "+c, Toast.LENGTH_SHORT).show();
        return a;
    }

    public long editContactUser(ContactUser c){
        long a= -1;
        ContentValues values= new ContentValues();
        values.put(ContactUserHelper.col_id, c.id);
        values.put(ContactUserHelper.col_nom, c.nom);
        values.put(ContactUserHelper.col_prenom, c.prenom);
        values.put(ContactUserHelper.col_numero, c.numero);
        a=db.update(ContactUserHelper.table_user, values, ContactUserHelper.col_id +"="+c.id, null);
        return a;
    }

    public ArrayList<ContactUser> getAllContactUsers(){


        ArrayList<ContactUser> usersList = new ArrayList<ContactUser>();
        Cursor cr=db.query(
                ContactUserHelper.table_user,
                new String[]{
                        ContactUserHelper.col_id,
                        ContactUserHelper.col_nom,
                        ContactUserHelper.col_prenom,
                        ContactUserHelper.col_numero},
                "createdBy = ?", new String[]{createdByEmail}, null, null, null
        );

        cr.moveToFirst();
        while(!cr.isAfterLast()){
            String id = cr.getString(0);
            String nom = cr.getString(1);
            String prenom = cr.getString(2);
            String numero = cr.getString(3);
            usersList.add(new ContactUser(id, nom, prenom, numero, createdByEmail));
            cr.moveToNext();
            System.out.println("nom: "+nom+" prenom "+prenom+" numero "+numero);
        }

        return usersList;
    }

    public ArrayList<ContactUser> filterContactUsers(CharSequence str){
        ArrayList<ContactUser> usersList = new ArrayList<ContactUser>();
        Cursor cr=db.query(
                ContactUserHelper.table_user,
                new String[]{
                        ContactUserHelper.col_id,
                        ContactUserHelper.col_nom,
                        ContactUserHelper.col_prenom,
                        ContactUserHelper.col_numero},
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
        db.execSQL("DELETE FROM "+ ContactUserHelper.table_user);
    }

    public void closeDB(){
        db.close();
    }
}
