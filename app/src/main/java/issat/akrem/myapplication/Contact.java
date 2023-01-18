package issat.akrem.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import issat.akrem.myapplication.models.ContactUser;
import issat.akrem.myapplication.services.ContactUserManager;

public class Contact extends AppCompatActivity {
    ImageView plus_icon_addContact;
    ImageView img_reload;
    EditText ed_rech;
    ListView lv_contacts;
    ArrayList<ContactUser> usersList;
    CustomArraylist cal;
    ContactUserManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // get holder on views
        plus_icon_addContact = findViewById(R.id.img_plus_icon_contact);
        img_reload = findViewById(R.id.ic_reload_contact);
        lv_contacts = findViewById(R.id.lv_Contact);
        ed_rech = findViewById(R.id.ed_rech_contact);


        img_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Contact.this, Contact.class);
                finish();
                startActivity(i);
            }
        });
        // set buttons click  listeners
        plus_icon_addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Contact.this, AddContact.class);
                startActivity(i);
            }
        });


        /*
        ArrayAdapter adapter = new ArrayAdapter(Contact.this, android.R.layout.simple_list_item_1, Home.data);
        lv_contacts.setAdapter(adapter);
         */

        ed_rech.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("here", "onStart: ");
        //cal.notifyDataSetChanged();
        // viewing all the contacts
        manager=new ContactUserManager(Contact.this);
        manager.openDB();
        usersList = manager.getAllContactUsers();
        manager.closeDB();

        cal = new CustomArraylist(Contact.this, usersList);
        lv_contacts.setAdapter(cal);

        ed_rech.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.i("user", "*********************************: "+ s);
                ArrayList<ContactUser> usersList1 = new ArrayList<>(usersList);

                for(int i=0; i<usersList1.size(); i++){
                    ContactUser c = usersList1.get(i);
                    Log.i("user", "user "+ i +" : " + c.prenom + " " + c.nom);
                    String fullName = c.prenom.toLowerCase() + " " + c.nom.toLowerCase();
                    String fullNameRev = c.nom.toLowerCase() + " " + c.prenom.toLowerCase();
                    if(!fullName.contains(s) && !fullNameRev.contains(s)){
                        Log.i("user", "removing "+ c.prenom + " " + c.nom);
                        usersList1.remove(i);
                        i = i-1;
                    }
                }


                /*        method using many requests to the database
                usersList.clear();
                manager.openDB();
                usersList = manager.filterContactUsers(s);
                manager.closeDB();

                 */
                cal = new CustomArraylist(Contact.this, usersList1);
                lv_contacts.setAdapter(cal);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}