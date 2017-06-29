package com.example.fanhongyi.croopinion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private static final String user="admin",pass="123";
    private EditText username=null,password=null;
    private CheckBox remember,autologin;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText)findViewById(R.id.editText1);
        password = (EditText)findViewById(R.id.editText2);
        remember = (CheckBox) findViewById(R.id.remember);
        autologin = (CheckBox) findViewById(R.id.autologin);

        sp = getSharedPreferences("userInfo", 0);
        String name=sp.getString("USER_NAME", "");
        String pass =sp.getString("PASSWORD", "");

        boolean choseRemember =sp.getBoolean("remember", false);
        boolean choseAutoLogin =sp.getBoolean("autologin", false);

        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if(choseRemember){
            username.setText(name);
            password.setText(pass);
            remember.setChecked(true);
        }
        //如果上次登录选了自动登录，那进入登录页面也自动勾选自动登录
        if(choseAutoLogin){
            autologin.setChecked(true);
            
            Toast.makeText(getApplicationContext(), "Logged in",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            intent.putExtra("u", username.getText().toString());
            startActivity(intent);
        }

    }

    public void login(View view){
        SharedPreferences.Editor editor =sp.edit();
        if(username.getText().toString().equals(user) &&
                password.getText().toString().equals(pass)){

            //保存用户名和密码
            editor.putString("USER_NAME", username.getText().toString());
            editor.putString("PASSWORD", password.getText().toString());

            //是否记住密码
            if(remember.isChecked()){
                editor.putBoolean("remember", true);
            }else{
                editor.putBoolean("remember", false);
            }


            //是否自动登录
            if(autologin.isChecked()){
                editor.putBoolean("autologin", true);
            }else{
                editor.putBoolean("autologin", false);
            }
            editor.commit();

            Toast.makeText(getApplicationContext(), "Logged in",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            intent.putExtra("u", username.getText().toString());
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
        }

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

}
