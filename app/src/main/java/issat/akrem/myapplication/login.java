package issat.akrem.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    TextView tv_pass_welc;
    TextView tv_email_welc;
    ImageView img_show_hide_pwd;
    EditText ed_pwd_welcome;
    EditText ed_email_welcome;
    Button btn_validate;
    Button btn_quit;

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

                if(str_email_content.equals("akrem") && str_pwd_content.equals("azerty")){
                    Intent i = new Intent(login.this, Home.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(login.this, "email ou mot de passe incorrecte", Toast.LENGTH_LONG).show();
                }
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