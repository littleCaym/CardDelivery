package com.example.carddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Status extends AppCompatActivity implements ValueEventListener, View.OnClickListener {

    FirebaseDatabase database;
    private static final String CMD = "cmd";
    private static final String KEYY = "keyy";
    private static final String AT_WORK = "at_work";
    private static final String APROVED = "aproved";
    private static final String DENIED = "denied";
    private static final String TO_DELIVERY = "to_delivery";


    TextView dateFil_text;
    TextView surname_text;
    ImageView status1_img;
    TextView status1_text;
    ImageView status2_img;
    TextView status2_text;
    ImageView status3_img;
    TextView status3_text;
    ImageView status4_img;
    TextView status4_text;
    ImageButton refresh_btn;

    private String cmd;
    private String at_work;
    private String aproved;
    private String denied;
    private String to_delivery;

    private String key;
    private int flag; //флажок на обнуление поля команды

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        flag = 0;
        cmd = ""; at_work=""; aproved=""; denied = ""; to_delivery="";

        dateFil_text = findViewById(R.id.status_text_datefil);
        surname_text = findViewById(R.id.status_text_surname);

        Intent intentList = getIntent();
        key = intentList.getStringExtra("keyy");
        dateFil_text.setText(intentList.getStringExtra("date_fil"));
        surname_text.setText(intentList.getStringExtra("surname"));

        status1_img = findViewById(R.id.status_img1);
        status1_img.setVisibility(View.GONE);
        status1_text = findViewById(R.id.status_text1);
        status1_text.setVisibility(View.GONE);
        status2_img = findViewById(R.id.status_img2);
        status2_img.setVisibility(View.GONE);
        status2_text = findViewById(R.id.status_text2);
        status2_text.setVisibility(View.GONE);
        status3_img = findViewById(R.id.status_img3);
        status3_img.setVisibility(View.GONE);
        status3_text = findViewById(R.id.status_text3);
        status3_text.setVisibility(View.GONE);
        status4_img = findViewById(R.id.status_img4);
        status4_img.setVisibility(View.GONE);
        status4_text = findViewById(R.id.status_text4);
        status4_text.setVisibility(View.GONE);

        refresh_btn = findViewById(R.id.status_refresh);
        refresh_btn.setOnClickListener(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Отправляем запрос
        DatabaseReference myRef_sendcmd = database.getReference(CMD);
        myRef_sendcmd.setValue("need_status");
        DatabaseReference myRef_sendkey = database.getReference(KEYY);
        myRef_sendkey.setValue(key);

        //Принимаем ответ
        DatabaseReference ref_cmd = database.getReference(CMD);
        ref_cmd.addValueEventListener(this);
        /*
        DatabaseReference ref_key = database.getReference(KEYY);
        ref_key.addValueEventListener(this);
         */
        DatabaseReference ref_work = database.getReference(AT_WORK);
        ref_work.addValueEventListener(this);
        DatabaseReference ref_prove = database.getReference(APROVED);
        ref_prove.addValueEventListener(this);
        DatabaseReference ref_deny = database.getReference(DENIED);
        ref_deny.addValueEventListener(this);
        DatabaseReference ref_send = database.getReference(TO_DELIVERY);
        ref_send.addValueEventListener(this);


    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        //TODO: обеспечить открытие БД
        Object object = new Object();
        switch (dataSnapshot.getRef().getKey()){
            case CMD:
                object = dataSnapshot.getValue();
                Log.d(CMD, object.toString());
                cmd = object.toString();
                    flag++;
                break;
       /*     case KEYY:
                object = dataSnapshot.getValue();
                Log.d(KEYY, object.toString());
                key = object.toString();
                flag++;
                break;
        */
            case AT_WORK:
                object = dataSnapshot.getValue();
                Log.d(AT_WORK, object.toString());
                at_work = object.toString();
                if (!object.toString().equals(""))
                    flag++;
                Log.d("FLAG = ", String.valueOf(flag));
                break;
            case APROVED:
                object = dataSnapshot.getValue();
                Log.d(APROVED, object.toString());
                aproved = object.toString();
                if (!object.toString().equals(""))
                    flag++;
                Log.d("FLAG = ", String.valueOf(flag));
                break;
            case DENIED:
                object = dataSnapshot.getValue();
                Log.d(DENIED, object.toString());
                denied = object.toString();
                if (!object.toString().equals(""))
                    flag++;
                Log.d("FLAG = ", String.valueOf(flag));
                break;
            case TO_DELIVERY:
                object = dataSnapshot.getValue();
                Log.d(TO_DELIVERY, object.toString());
                to_delivery = object.toString();
                if (!object.toString().equals(""))
                    flag++;
                Log.d("FLAG = ", String.valueOf(flag));
                break;
        }
        //TODO: обнулить поле команды при получении
        //if ((flag >= 10) && cmd.equals("status")){
        if (cmd.equals("status") && !denied.equals("") && !aproved.equals("") && !at_work.equals("") && !to_delivery.equals("")){
            Log.d("FINAL FLAG = ", String.valueOf(flag));
            if (denied.equals("v")){ //TODO: тут пустые значения, хотя в облаке все ок
                status1_img.setVisibility(View.VISIBLE);
                status1_text.setVisibility(View.VISIBLE);
            }
            if (aproved.equals("v")){
                status2_img.setVisibility(View.VISIBLE);
                status2_text.setVisibility(View.VISIBLE);
            }
            if (at_work.equals("v")){
                status3_img.setVisibility(View.VISIBLE);
                status3_text.setVisibility(View.VISIBLE);
            }
            if (to_delivery.equals("v")){
                status4_img.setVisibility(View.VISIBLE);
                status4_text.setVisibility(View.VISIBLE);
            }

            //чистим поля

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference  myRef_clear = database.getReference(CMD);
            myRef_clear.setValue("");
            myRef_clear = database.getReference(KEYY);
            myRef_clear.setValue("");
            myRef_clear = database.getReference(AT_WORK);
            myRef_clear.setValue("");
            myRef_clear = database.getReference(APROVED);
            myRef_clear.setValue("");
            myRef_clear = database.getReference(DENIED);
            myRef_clear.setValue("");
            myRef_clear = database.getReference(TO_DELIVERY);
            myRef_clear.setValue("");


            flag = 0;
            denied = ""; aproved=""; at_work=""; to_delivery="";

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }

    @Override
    public void onClick(View v) {
        finish();
        startActivity(getIntent());
        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Отправляем запрос
        DatabaseReference myRef_sendcmd = database.getReference(CMD);
        myRef_sendcmd.setValue("need_status");
        DatabaseReference myRef_sendkey = database.getReference(KEYY);
        myRef_sendkey.setValue(key);

         */
    }
}
