package com.example.carddelivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button req_button;
    private Button status_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regToken();

        req_button = findViewById(R.id.main_req_button);
        req_button.setOnClickListener(this);
        status_button = findViewById(R.id.main_status_button);
        status_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_req_button:
                Intent intentReq = new Intent(this, Requistion.class);
                startActivity(intentReq);
                break;
            case R.id.main_status_button:
                Intent intentStatus = new Intent(this, ListRequistion.class);
                startActivity(intentStatus);
                break;
        }
    }
    public void regToken(){
        /*
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                       // String msg = getString(R.string.msg_token_fmt, token);
                        String msg = token;
                        Log.d("TAG", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

         */
    }
}
