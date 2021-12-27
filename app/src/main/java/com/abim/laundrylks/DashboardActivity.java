package com.abim.laundrylks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.card_pickup){
            Intent intent = new Intent(DashboardActivity.this, PickupActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.card_notif){
            Intent intent = new Intent(DashboardActivity.this, NotifActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.card_info){
            Intent intent = new Intent(DashboardActivity.this, InfoActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.card_logout){
            Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }
}