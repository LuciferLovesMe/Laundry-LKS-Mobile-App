package com.abim.laundrylks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickupActivity extends AppCompatActivity {
    Spinner cust_spinner, package_spinner;
    TextView tv_address;
    Button btn_pick;
    Context ctx;
    RequestQueue queue;
    int id_cust, id_employee, id_package;

    List<Customer> customers;
    List<String> package_list;
    List<Integer> package_id;
    List<String > customer_list;
    Session s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickup);
        getSupportActionBar().setTitle("Pick Up");

        ctx = this;

        cust_spinner = findViewById(R.id.spin_cust);
        package_spinner = findViewById(R.id.spin_package);
        tv_address = findViewById(R.id.tv_address);
        btn_pick = findViewById(R.id.btn_pickup);
        queue = Volley.newRequestQueue(ctx);

        customers = new ArrayList<>();
        package_list = new ArrayList<>();
        package_id = new ArrayList<>();
        customer_list = new ArrayList<>();
        s = new Session(ctx);
        package_list.clear();
        package_id.clear();

        getCustomer();
        getPackage();

        cust_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Customer customer = customers.get(i);
                id_cust = customer.getId();
                tv_address.setText(customer.getAddress());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        package_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_package = Integer.valueOf(package_id.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    JSONObject obj = new JSONObject();

                    obj.put("id_employee", s.getId());
                    obj.put("id_customer", id_cust);
                    obj.put("id_package", id_package);

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, MyRequest.getCheckoutUrl(), obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (Boolean.valueOf(String.valueOf(response)) == true) {
                                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                                dialog.show();
                                dialog.setTitle("Information");
                                dialog.setMessage("Success");
                                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    queue.add(request);
                }
                catch (JSONException exception){
                    Toast.makeText(ctx, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    void getCustomer (){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MyRequest.getCustomerUrl(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            customers.add(new Customer(obj.getInt("id"), obj.getString("name"), obj.getString("address")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                for (Customer customer : customers){
                    customer_list.add(customer.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ctx, R.layout.support_simple_spinner_dropdown_item, customer_list);
                cust_spinner.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                dialog.setTitle("Error");
                dialog.setMessage(""+error);
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        queue.add(request);
    }

    void getPackage(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MyRequest.getPackageUrl(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        package_id.add(obj.getInt("id"));
                        package_list.add(obj.getString("name"));
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String >(ctx, R.layout.support_simple_spinner_dropdown_item, package_list);
                package_spinner.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_SHORT).show();
                AlertDialog dialog = new AlertDialog.Builder(ctx).create();
                dialog.setTitle("Error");
                dialog.setMessage(""+error);
                dialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        queue.add(request);
    }
}