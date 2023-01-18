package issat.akrem.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.UserManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.mindrot.jbcrypt.BCrypt;

import issat.akrem.myapplication.models.User;
import issat.akrem.myapplication.services.UsersManager;

public class SignUp extends AppCompatActivity {

    TextView tv_email_signUp;
    TextView tv_pass_signUp;
    TextView tv_repass_signUp;
    ImageView img_show_hide_pwd;
    ImageView img_show_hide_rePwd;
    EditText ed_email_signUp;
    EditText ed_pwd_signUp;
    EditText ed_repwd_signUp;
    Button btn_validate;
    Button btn_quit;
    TextView login;

    String str_email_content;
    String str_pwd_content;
    String str_repwd_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tv_pass_signUp = (TextView) findViewById(R.id.tv_password_signUp);
        tv_repass_signUp = (TextView) findViewById(R.id.tv_repassword_signUp);
        tv_email_signUp = (TextView) findViewById(R.id.tv_email_signUp);
        ed_email_signUp = (EditText) findViewById(R.id.ed_email_signUp);
        ed_pwd_signUp = (EditText) findViewById(R.id.ed_password_signUp);
        ed_repwd_signUp = (EditText) findViewById(R.id.ed_repassword_signUp);
        img_show_hide_pwd = (ImageView) findViewById(R.id.img_showHidePwd_signUp);
        img_show_hide_rePwd = (ImageView) findViewById(R.id.img_showHideRePwd_signUp);
        btn_validate = findViewById(R.id.btn_val_signUp);
        btn_quit = findViewById(R.id.btn_quit_signUp);
        login = findViewById(R.id.redirectToSignIn_signUp);

        tv_pass_signUp.setText(tv_pass_signUp.getText().toString().substring(0, 1).toUpperCase() + tv_pass_signUp.getText().toString().substring(1).toLowerCase());
        tv_pass_signUp.setAutofillHints(tv_pass_signUp.AUTOFILL_HINT_PASSWORD);
        tv_email_signUp.setText(tv_email_signUp.getText().toString().substring(0, 1).toUpperCase() + tv_email_signUp.getText().toString().substring(1).toLowerCase());

        //show hide password
        img_show_hide_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_pwd_signUp.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    ed_pwd_signUp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_show_hide_pwd.setImageResource(R.drawable.hide_password_icon);
                    ed_pwd_signUp.setSelection(ed_pwd_signUp.getText().length());

                } else {
                    ed_pwd_signUp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_show_hide_pwd.setImageResource(R.drawable.show_password_icon);
                    ed_pwd_signUp.setSelection(ed_pwd_signUp.getText().length());

                }
            }
        });

        img_show_hide_rePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_repwd_signUp.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    ed_repwd_signUp.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    img_show_hide_rePwd.setImageResource(R.drawable.hide_password_icon);
                    ed_repwd_signUp.setSelection(ed_repwd_signUp.getText().length());

                } else {
                    ed_repwd_signUp.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    img_show_hide_rePwd.setImageResource(R.drawable.show_password_icon);
                    ed_repwd_signUp.setSelection(ed_repwd_signUp.getText().length());

                }
            }
        });

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_email_content = ed_email_signUp.getText().toString();
                str_pwd_content = ed_pwd_signUp.getText().toString();
                str_repwd_content = ed_repwd_signUp.getText().toString();

                if (!str_pwd_content.equals(str_repwd_content)){
                    Toast.makeText(SignUp.this, "Password and repeated password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                String salt = BCrypt.gensalt();
                String HashOfPassword = BCrypt.hashpw(str_pwd_content, salt);
                User newUser = new User(str_email_content, HashOfPassword, salt);

                UsersManager manager = new UsersManager(SignUp.this);
                manager.openDB();
                long a = manager.createNewUser(newUser);
                manager.closeDB();

                if(a>0){
//                    Toast.makeText(SignUp.this, "test a= "+a, Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("myUserAccount", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong("id", a);
                    editor.putString("email", newUser.email);
                    editor.apply(); // or editor.commit();

                    Intent i = new Intent(SignUp.this, Home.class);
                    startActivity(i);
                    finish();
                }


            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, login.class);
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