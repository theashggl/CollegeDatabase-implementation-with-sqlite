package com.database.Collegemanegement_kiit;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Form extends AppCompatActivity {
    databases mydb;
    EditText frt,pasword,father,rollid;
    TextView first,fathers,ids;
    Button clik,updt,datakill;
    home_page hps;
    String hsi;
    public boolean truth=true;
    public String er,bg,yu,pswrd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mydb=new databases(Form.this);
        frt=(EditText)findViewById(R.id.firstone);
        first=(TextView)findViewById(R.id.first);
        fathers=(TextView)findViewById(R.id.fathers);
        ids=(TextView)findViewById(R.id.ids);
        father=(EditText)findViewById(R.id.Father);
        rollid=(EditText)findViewById(R.id.roll);
        hps=new home_page();
        clik=(Button)findViewById(R.id.registerdata);
        pasword=(EditText)findViewById(R.id.password);
        updt=(Button)findViewById(R.id.update);
        datakill=(Button)findViewById(R.id.delete);
        truth=getIntent().getExtras().getBoolean("buttonclik");
                //i.getBooleanExtra(truth,"buttonclik");
       // hsi=getIntent().getExtras().getString("it");
        if(truth==false)
        {
            Toast.makeText(Form.this, "do"+hsi, Toast.LENGTH_SHORT).show();

           // rollid.setText("g"+hsi+"hj");
            rollid.setEnabled(false);
            clik.setText("show your data");
            clik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    first.setText("your id is: "+hsi);
                    Cursor obcursr=mydb.loginusershow(hsi);
                    if(obcursr.getCount()==0)
                    {
                        Toast.makeText(hps, "No data to show", Toast.LENGTH_SHORT).show();
                    }
                    else{
                    //StringBuffer bufs=new StringBuffer();
//                    fathers.setText(bufs.append("Fathers name :" +obcursr.getString(2)+"\n"));
//                    first.setText(bufs.append("First name :"+obcursr.getString(0)+"\n"));
//                    ids.setText(bufs.append("Roll :"+obcursr.getString(3)+"\n"));
                        while(obcursr.moveToNext())
                        {
                            Toast.makeText(getApplicationContext(), "First Name: "+obcursr.getString(0), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Father's Name: "+obcursr.getString(2), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Roll: "+obcursr.getString(3), Toast.LENGTH_SHORT).show();
                        }
                }}
            });
        }
        else
        {
            rollid.setHint("Enter your roll");
            datakill.setVisibility(View.GONE);
            updt.setVisibility(View.GONE);
            clik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    er = frt.getText().toString();
                    pswrd = pasword.getText().toString();
                    bg = father.getText().toString();
                    yu = rollid.getText().toString();


                    if (er.isEmpty()) {
                        frt.setError("Enter your first name");
                        frt.requestFocus();
                    } else if (bg.equalsIgnoreCase("")) {
                        father.setError("Enter your father's name");
                        father.requestFocus();
                    } else if (yu.equals("")) {
                        rollid.setError("Enter your roll");
                        rollid.requestFocus();
                    }// else if (grp1.getCheckedRadioButtonId() == -1) {
                    //  Toast.makeText(Form.this, "Please select an item from the radio button", Toast.LENGTH_SHORT).show();
                    // else if (grp2.getCheckedRadioButtonId() == -1) {
                    // Toast.makeText(Form.this, "Please select an item from the radio button", Toast.LENGTH_SHORT).show();



                    else {

                        //int selectedId = grp1.getCheckedRadioButtonId();
                        //int secondradio = grp2.getCheckedRadioButtonId();
                        // find the radiobutton by returned id
                        //gen = (RadioButton) findViewById(selectedId);
                        //gender = gen.getText().toString();
                        //nat = (RadioButton) findViewById(secondradio);
                        //nation = nat.getText().toString();

                        boolean insertdata = mydb.insertdata(er, pswrd, bg, yu);
                        if (insertdata==true) {
                            Toast.makeText(Form.this, "Data inserted Successfully", Toast.LENGTH_SHORT).show();
                            Intent jumpto=new Intent(Form.this,log_in.class);
                            startActivity(jumpto);
                        } else {
                            Toast.makeText(Form.this, "Data not inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
//        {
//        truth=getIntent().getExtras().getBoolean("buttonclik");
//            Toast.makeText(Form.this, "It is"+truth, Toast.LENGTH_SHORT).show();
//        }
//        if(truth==false)
//        {
//            Toast.makeText(Form.this, "the truth value is false", Toast.LENGTH_SHORT).show();

//        }

        updt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                er = frt.getText().toString();
                pswrd = pasword.getText().toString();
                bg = father.getText().toString();
                yu = rollid.getText().toString();
                if (yu.equals("")) {
                    rollid.setError("Enter your roll");
                    rollid.requestFocus();
                }
                else{
                boolean datatruth=mydb.update(er,pswrd,bg,yu);


                    if (datatruth == true) {
                        Toast.makeText(Form.this, "success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Form.this, "unsuccessful", Toast.LENGTH_SHORT).show();
                    }
            }}
        });

        datakill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yu = rollid.getText().toString();
                if (yu.equals("")) {
                    rollid.setError("Enter your roll");
                    rollid.requestFocus();
                }
                else
                {
                    Integer deletedrows=mydb.deletedata(yu);
                    if(deletedrows>0)
                    {
                        Toast.makeText(Form.this, "successfully deleted ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Form.this, "unsuccessful deletion", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    public Button getButton()
    {
        return clik;
    }
}