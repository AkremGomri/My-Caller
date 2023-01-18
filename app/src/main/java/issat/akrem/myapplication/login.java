package issat.akrem.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import issat.akrem.myapplication.models.User;
import issat.akrem.myapplication.services.UsersManager;

public class login extends AppCompatActivity {
    TextView tv_pass_welc;
    TextView tv_email_welc;
    ImageView img_show_hide_pwd;
    EditText ed_pwd_welcome;
    EditText ed_email_welcome;
    Button btn_validate;
    Button btn_quit;
    TextView signUp;

    String str_email_content;
    String str_pwd_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_pass_welc = (TextView) findViewById(R.id.tv_password_login);
        tv_email_welc = (TextView) findViewById(R.id.tv_email_login);
        ed_email_welcome = (EditText) findViewById(R.id.ed_email_login);
        ed_pwd_welcome = (EditText) findViewById(R.id.ed_password_login);
        img_show_hide_pwd = (ImageView) findViewById(R.id.img_show_hide_pwd);
        btn_validate = findViewById(R.id.btn_val_login);
        btn_quit = findViewById(R.id.btn_quit_login);
        signUp = findViewById(R.id.signUp_welcome);

        tv_pass_welc.setText(tv_pass_welc.getText().toString().substring(0, 1).toUpperCase() + tv_pass_welc.getText().toString().substring(1).toLowerCase());
        tv_pass_welc.setAutofillHints(tv_pass_welc.AUTOFILL_HINT_PASSWORD);
        tv_email_welc.setText(tv_email_welc.getText().toString().substring(0, 1).toUpperCase() + tv_email_welc.getText().toString().substring(1).toLowerCase());

        //show hide password
        img_show_hide_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_pwd_welcome.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    ed_pwd_welcome.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_show_hide_pwd.setImageResource(R.drawable.hide_password_icon);
                    ed_pwd_welcome.setSelection(ed_pwd_welcome.getText().length());

                } else {
                    ed_pwd_welcome.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_show_hide_pwd.setImageResource(R.drawable.show_password_icon);
                    ed_pwd_welcome.setSelection(ed_pwd_welcome.getText().length());

                }
            }
        });

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_email_content = ed_email_welcome.getText().toString();
                str_pwd_content = ed_pwd_welcome.getText().toString();

                User newUser = new User(str_email_content, str_pwd_content);

                UsersManager manager = new UsersManager(login.this);

                manager.openDB();

                long a = manager.checkCredentials(newUser);
                manager.closeDB();

                if(a>0){

//                    Toast.makeText(SignUp.this, "test a= "+a, Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("myUserAccount", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong("id", a);
                    editor.putString("email", newUser.email);
                    editor.apply(); // or editor.commit();

                    Intent i = new Intent(login.this, Home.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(login.this, "wrong credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
                finish();
            }
        });

        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}