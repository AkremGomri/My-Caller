package issat.akrem.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import issat.akrem.myapplication.models.ContactUser;
import issat.akrem.myapplication.services.ContactUserManager;

public class AddContact extends AppCompatActivity {

    ImageView img_return;

    TextView tv_title, tv_name, tv_lastName, tv_number;

    EditText ed_name, ed_lastName, ed_number;

    Button btn_addContact, btn_cancelAddContact;

    String name, lastName, number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        img_return=findViewById(R.id.img_return_addActivity);

        tv_title=findViewById(R.id.tv_title_addContact);
        tv_name=findViewById(R.id.tv_name_addContact);
        tv_lastName=findViewById(R.id.tv_lastName_addContact);
        tv_number=findViewById(R.id.tv_number_addContact);

        ed_name=findViewById(R.id.ed_name_addContact);
        ed_lastName=findViewById(R.id.ed_lastName_addContact);
        ed_number=findViewById(R.id.ed_number_addContact);

        btn_addContact=findViewById(R.id.btn_add_addContact);
        btn_cancelAddContact=findViewById(R.id.btn_cancel_addContact);

        // Make first letter uppercased
        tv_title.setText(tv_title.getText().toString().substring(0, 1).toUpperCase() + tv_title.getText().toString().substring(1).toLowerCase());
        tv_name.setText(tv_name.getText().toString().substring(0, 1).toUpperCase() + tv_name.getText().toString().substring(1).toLowerCase());
        tv_lastName.setText(tv_lastName.getText().toString().substring(0, 1).toUpperCase() + tv_lastName.getText().toString().substring(1).toLowerCase());
        tv_number.setText(tv_number.getText().toString().substring(0, 1).toUpperCase() + tv_number.getText().toString().substring(1).toLowerCase());

        // Return to the last visited page on click on the return icon
        img_return.setOnClickListener(v->{
            finish();
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Log.i("TAG", "hello there: npoooooooooo");
            String nom = extras.getString("nom");
            String prenom = extras.getString("prenom");
            String numero = extras.getString("numero");

            ed_name.setText(nom);
            ed_lastName.setText(prenom);
            ed_number.setText(numero);

            //
            String id = extras.getString("id");
            Toast.makeText(this, "id: "+id, Toast.LENGTH_SHORT).show();


        }

        // listeners for add button and cancel button
        btn_addContact.setOnClickListener(v -> {

            name = ed_name.getText().toString();
            lastName = ed_lastName.getText().toString();
            number = ed_number.getText().toString();

            if (extras != null) {
                //The key argument here must match that used in the other activity
                /*for(int i=0; i<100000; i++){
                    Log.i("iter", "i: "+i);
                }*/

                String id = extras.getString("id");

                ContactUserManager manager=new ContactUserManager(AddContact.this);

                manager.openDB();
                long a = manager.editContactUser(new ContactUser(id, name, lastName, number, null));
                manager.closeDB();

                if(a==1){
                    finish();
                } else {
                    Toast.makeText(this, "operation failed !", Toast.LENGTH_SHORT).show();
                }
            } else {
                //The key argument here must match that used in the other activity
                SharedPreferences preferences = getSharedPreferences("myUserAccount", MODE_PRIVATE);
                String email = preferences.getString("email", "");


                ContactUserManager manager = new ContactUserManager(AddContact.this);
                manager.openDB();
                System.out.println("here we are: "+email);
                long a = manager.addContactUser(new ContactUser(name, lastName, number, email));
                System.out.println("here we are");
                manager.closeDB();
                if (a == -1) {
                    Toast.makeText(AddContact.this, "Problem incountred, could not add user", Toast.LENGTH_LONG).show();
                } else {
//                Contact.
                    Toast.makeText(AddContact.this, "Successfully created ", Toast.LENGTH_LONG).show();
                }
            }
            finish();
        });

        btn_cancelAddContact.setOnClickListener(v ->{
/*            Intent i=new Intent(AddContact.this, Contact.class);
            startActivity(i);
 */           finish();
        });
    }
    }
