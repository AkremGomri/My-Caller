package issat.akrem.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomArraylist extends BaseAdapter {
    private static final int REQUST_CALL = 1;
    Context con;
    ArrayList<ContactUser> data;

    public CustomArraylist(Context con, ArrayList<ContactUser> data) {
        this.con = con;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inf=LayoutInflater.from(con);
        View v=inf.inflate(R.layout.custom_array_view, null);

        TextView name = v.findViewById(R.id.tv_name_custom);
        TextView lastName = v.findViewById(R.id.tv_lastName_custom);
        TextView number = v.findViewById(R.id.tv_number_custom);

        ImageView ic_call = v.findViewById(R.id.img_call_custom);
        ImageView ic_deleteContact = v.findViewById(R.id.img_delete_custom);
        ImageView ic_edit = v.findViewById(R.id.img_edit_custom);

        ContactUser c = data.get(position);

        String nom = c.nom.substring(0,1).toUpperCase() + c.nom.substring(1).toLowerCase();
        String prenom = c.prenom.substring(0,1).toUpperCase() + c.prenom.substring(1).toLowerCase();
        String numero = c.numero.substring(0,1).toUpperCase() + c.numero.substring(1).toLowerCase();

        name.setText(nom);
        lastName.setText(prenom);
        number.setText(numero);

        ic_call.setOnClickListener(view -> {
            ContactUser c_rm = data.get(position);
            String number_to_call = number.getText().toString();
            if(number_to_call.trim().length() > 0){
                if(ContextCompat.checkSelfPermission(
                        con,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            (Activity) con,
                            new String[] { Manifest.permission.CALL_PHONE},
                            REQUST_CALL
                    );
                } else {
                    String dial = "tel:" +  number_to_call;
                    con.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }
            } else {
                Toast.makeText(con, "Enter a phone number", Toast.LENGTH_SHORT).show();
            }
        });

        ic_edit.setOnClickListener(view -> {
            Log.i("sa5ta", "emchiiiiii: ");
            ContactUser c_rm = data.get(position);
            Intent i = new Intent(con, AddContact.class);
            i.putExtra("id", c_rm.id);
            i.putExtra("nom", c_rm.nom);
            i.putExtra("prenom", c_rm.prenom);
            i.putExtra("numero", c_rm.numero);
            con.startActivity(i);
        });

        ic_deleteContact.setOnClickListener(view -> {
            /* UserManager manager=new UserManager(con);
            manager.deleteContactUserById(); */
            ContactUser c_rm = data.get(position);
            UserManager manager=new UserManager(con);
            manager.openDB();
            int success = manager.deleteContactUserById(c_rm.id);
            if(success != 0){
                data.remove(position);
                notifyDataSetChanged();
                Toast.makeText(con, "The user has been deleted "+Integer.toString(success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(con, "Failed to delete the user", Toast.LENGTH_SHORT).show();
            }
            manager.closeDB();


        });

        return v;
    }
}
