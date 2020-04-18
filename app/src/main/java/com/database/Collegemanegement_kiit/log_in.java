package com.database.Collegemanegement_kiit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class log_in extends AppCompatActivity {
    TextView addac, forget;
    Button log;
    databases dbs;
    Form fm;
    EditText user, passwd;
    String userid,pword;
    cameraactivity hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        hp = new cameraactivity();
        fm=new Form();
        dbs=new databases(log_in.this);
        passwd = (EditText) findViewById(R.id.paswd);
        user = (EditText) findViewById(R.id.id);
        log = (Button) findViewById(R.id.login);
        addac = (TextView) findViewById(R.id.register);
        forget = (TextView) findViewById(R.id.forgt);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid=user.getText().toString();
                pword=passwd.getText().toString();
                Cursor obcursor = dbs.loginuser(userid, pword);
                if (obcursor.getCount()>0) {
                    Intent redirect = new Intent(log_in.this, home_page.class);
                    redirect.putExtra("idoflogged",userid);
                   fm.hsi=userid;
                    Intent sendid=new Intent(log_in.this,cameraactivity.class);
                    sendid.putExtra("id1",userid);
                    startActivity(redirect);
                    Toast.makeText(log_in.this, "login success", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(log_in.this, "userid or password incorrect", Toast.LENGTH_SHORT).show();
                    user.requestFocus();
                }
            }
        });
        addac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o=new Intent(log_in.this,Form.class);
                o.putExtra("buttonclik",true);
                startActivity(o);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(log_in.this, "Sorry nothing to do for now", Toast.LENGTH_SHORT).show();
                Intent intit=new Intent(log_in.this,Form.class);
                intit.putExtra("buttonclik",false);
                startActivity(intit);
            }
        });
}
}