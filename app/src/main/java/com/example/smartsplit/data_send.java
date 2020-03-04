package com.example.smartsplit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.util.Log;
import android.view.View;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class data_send extends Activity {

    public void send(Boolean splitEqually, Double amount, String contact) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Enter the correct url for your api service site
        JSONObject j = new JSONObject();
        //"https://aislepay.herokuapp.com/testdata";
        String url = "https://paisa.free.beeceptor.com";
        try {
          /*  JSONObject j1 = new JSONObject();
            JSONArray array = new JSONArray();
            j1.put("number",123);
            j1.put("amount",123);
            j1.put("number",69);
            j1.put("amount",69);
            array.put(j1);
            j.put("splitEqually",true);
            j.put("paidAmount",100);
            j.put("paidBy",1234);
            j.put("paidFor",array);*/
          j.put("splitEqually",splitEqually);
          j.put("amount",amount);
          j.put("contact",contact);

        }catch (Exception e){};
        // parser.parse(j);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, j,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("response",response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("response",error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
