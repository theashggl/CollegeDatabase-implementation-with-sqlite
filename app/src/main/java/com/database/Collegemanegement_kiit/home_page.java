package com.database.Collegemanegement_kiit;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class home_page extends AppCompatActivity {
Button lout,homepage,showperson,pictre;
TextView showit;
LinearLayout homecard,logcard,imagecard,datacard;
public boolean falses=false;
    Form ob;
    String ids;
cameraactivity cm;
databases dbss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        lout = (Button) findViewById(R.id.out);
        homepage = (Button) findViewById(R.id.home);
        showit=(TextView) findViewById(R.id.showtextdata);
        logcard=(LinearLayout)findViewById(R.id.logcard);
        imagecard=(LinearLayout)findViewById(R.id.imagecard);
        datacard=(LinearLayout)findViewById(R.id.datacard);
        showperson=(Button)findViewById(R.id.showdata);
        cm=new cameraactivity();
        homecard=(LinearLayout)findViewById(R.id.homecard);
        pictre=(Button)findViewById(R.id.images);
        dbss=new databases(home_page.this);
      ob =new Form();
      ids=getIntent().getStringExtra("idoflogged");
        Toast.makeText(home_page.this, "your user id is"+ids, Toast.LENGTH_SHORT).show();
        homecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect=new Intent(home_page.this,home_page.class);
                startActivity(redirect);
            }
        });
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect=new Intent(home_page.this,home_page.class);
                startActivity(redirect);
            }
        });
        logcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout=new Intent(home_page.this,log_in.class);
                startActivity(logout);
            }
        });
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout=new Intent(home_page.this,log_in.class);
                        startActivity(logout);
            }
        });
        datacard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to=new Intent(home_page.this,Form.class);
                to.putExtra("buttonclik",false);
                startActivity(to);
            }
        });
        showperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent to=new Intent(home_page.this,Form.class);
                to.putExtra("buttonclik",false);
                startActivity(to);
            }
        });
        imagecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(home_page.this,cameraactivity.class);
                it.putExtra("idl",ids);
                falses=true;
                startActivity(it);
            }
        });
        pictre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(home_page.this,cameraactivity.class);
                it.putExtra("idl",ids);
                falses=true;
                startActivity(it);
            }
        });

}}