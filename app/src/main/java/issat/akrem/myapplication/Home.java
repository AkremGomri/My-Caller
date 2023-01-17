package issat.akrem.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class Home extends AppCompatActivity {
    public static ArrayList<ContactUser> data = new ArrayList<ContactUser>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Automatically starts Contact activity
        Intent i = new Intent(Home.this, Contact.class);
        startActivity(i);
    }
}