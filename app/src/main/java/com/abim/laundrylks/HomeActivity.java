package com.abim.laundrylks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    EditText et_email, et_pass;
    Button btn_login;
    Context ctx;
    RequestQueue queue;
    Session s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        ctx = this;
        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        queue = Volley.newRequestQueue(ctx);
        s = new Session(ctx);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_email.getText().length() < 1 || et_pass.getText().length() < 1){
                    AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                    dialog.setTitle("Error");
                    dialog.setMessage("All Fields Must be Filled");
                    dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else{
                    StringRequest request = new StringRequest(Request.Method.POST, MyRequest.getLoginUrl(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    s.setEmployee(obj.getInt("id"), obj.getString("name"), obj.getString("email"));

                                    Intent intent = new Intent(HomeActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            else{
                                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                                dialog.setTitle("Error");
                                dialog.setMessage("Can't Find User");
                                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                            dialog.setTitle("Error");
                            dialog.setMessage(""+error);
                            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String email = et_email.getText().toString();
                            String password = et_pass.getText().toString();
                            Map<String, String > params = new HashMap<>();
                            params.put("email_employee", email);
                            params.put("password_employee", password);

                            return params;
                        }
                    };

                    queue.add(request);
                }
            }
        });

    }
}