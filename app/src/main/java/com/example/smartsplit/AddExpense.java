package com.example.smartsplit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddExpense extends AppCompatActivity {
    private static final String[] CONTACTS = new String[]{
            "Angad", "Deekshant", "Preetam", "Shubham", "Vibhu"
    };

    public String number;
    public String name;
    public  AutoCompleteTextView contacts;
    public  EditText amount;
    public  TextView textView;
    public  Boolean splitEqually;
    public  RadioButton equally;
    public  RadioButton unequally;
    public  Button saveButton;

    public  TextView your_share_text;
    public  TextView friend_share_text;
    public  EditText et_your_share;
    public  EditText et_friend_share;
    public  TextView hidden1,hidden2,hidden3,hidden4;
    public  Toolbar toolbaradd;
    public EditText amountEditText;
    public ImageView hidden1_image,hidden2_image,hidden3_image,hidden4_image;
    Map<String,String> contact_details = new HashMap<>();

    //unequal share Editext



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);


        //setting IDS
        your_share_text = findViewById(R.id.your_share_text);
        friend_share_text=findViewById(R.id.friend_share_text);
        et_your_share =findViewById(R.id.et_your_share);
        et_friend_share=findViewById(R.id.et_friend_share);
        hidden1 = findViewById(R.id.hidden1);
        hidden2 = findViewById(R.id.hidden2);
        hidden3 = findViewById(R.id.hidden3);
        hidden4 = findViewById(R.id.hidden4);
        hidden1_image = findViewById(R.id.hidden1_image);
        hidden2_image = findViewById(R.id.hidden2_image);
        hidden3_image = findViewById(R.id.hidden3_image);
        hidden4_image = findViewById(R.id.hidden4_image);
        amountEditText=findViewById(R.id.amount);
        equally = findViewById(R.id.equally);
        unequally = findViewById(R.id.unequally);

        // if(getSupportActionBar() != null) {
        //    getSupportActionBar().setTitle(getString("Add Expense"));
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //}

//Share UNequal


        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Data Saved", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });

        Toolbar toolbaradd = findViewById(R.id.toolbaradd);
        setSupportActionBar(toolbaradd);
        toolbaradd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_SHORT)
                //        .setAction("Action", null).show();
                openMainActivity();
            }
        });
        ImageButton contactAdd = findViewById(R.id.imageButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, CONTACTS);
        //contactAdd.setAdapter(adapter);

        contactAdd.setOnClickListener(new Button.OnClickListener(){

            //opening contact
            public void onClick(View view){
                ActivityCompat.requestPermissions(AddExpense.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        1);

            }
        });

    }

    //asking permission
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (contact_details.size()>=4){
                        Toast.makeText(this,"Contact length exceeds limit",Toast.LENGTH_LONG).show();
                        break;
                    }
                    Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
                    startActivityForResult(intent, 1);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(AddExpense.this, "Permission denied to read your Contacts", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    //opening contact
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==1){

            if(resultCode == RESULT_OK){
                Uri contactData = data.getData();
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor =  contentResolver.query(contactData, null, null, null, null);
                if(cursor.moveToFirst()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                    Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null);
                    while (phones.moveToNext()) {
                   number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)).trim();
                   name= phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        //Log.i("number", "The phone number is " + number);
                        //textView = findViewById(R.id.textView);
                        //contactName.setText(name);
                        contact_details.put(number,name);
                        for(String s:contact_details.keySet()) {
                            if (contact_details.size() == 1) {
                                hidden1.setVisibility(View.VISIBLE);
                                hidden1_image.setVisibility(View.VISIBLE);
                                hidden1.setText("Name: " + contact_details.get(number) + " Number: " + s);
                            }if (contact_details.size() == 2) {
                                hidden2.setVisibility(View.VISIBLE);
                                hidden2_image.setVisibility(View.VISIBLE);
                                hidden2.setText("Name: " + contact_details.get(number) + " Number: " + s);
                            }if (contact_details.size()==3){
                                hidden3.setVisibility(View.VISIBLE);
                                hidden3_image.setVisibility(View.VISIBLE);
                                hidden3.setText("Name: " + contact_details.get(number) + " Number: " + s);
                            }if (contact_details.size()==4){
                                hidden4.setVisibility(View.VISIBLE);
                                hidden4_image.setVisibility(View.VISIBLE);
                                hidden4.setText("Name: " + contact_details.get(number) + " Number: " + s);
                            }

                        }
                        Log.i("number",contact_details+" map size is "+contact_details.size());
                    }
                }
            }
        }
    }



    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private int getString(String add_expense) {
        return 0;
    }

    public void makeVisible(View v){
        //making this visible
        your_share_text.setVisibility(View.VISIBLE);
        friend_share_text.setVisibility(View.VISIBLE);
        et_your_share.setVisibility(View.VISIBLE);
        et_friend_share.setVisibility(View.VISIBLE);
    }

    public void makeInVisible(View v){
        //making this visible
        your_share_text.setVisibility(View.GONE);
        friend_share_text.setVisibility(View.GONE);
        et_your_share.setVisibility(View.GONE);
        et_friend_share.setVisibility(View.GONE);
    }



    //sending data to server
    public void send(View v){
        //AutoCompleteTextView contactsTextView= findViewById(R.id.contactAdd);

        Boolean splitEqually=true;
        //String contacts = contactsTextView.getText().toString();
        Double amount = Double.parseDouble(amountEditText.getText().toString());
        if(equally.isChecked()) {
            sendEqually(splitEqually, amount,number);

        }
        else if(unequally.isChecked()){
            Map<String,Integer> map = new HashMap<>();

            String s = et_friend_share.getText().toString();
            Integer amountFriend= Integer.parseInt(s);
            number = number.trim();
            map.put(number,amountFriend);
            sendUnEqually(false,amount,map);
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
        }

    }
    public void sendEqually(Boolean splitEqually, Double amount, String contact) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Enter the correct url for your api service site
        JSONObject j = new JSONObject();
        //"";
        String url = "https://aislepay.herokuapp.com/testdata";
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

    //Send data unEqually
    public void sendUnEqually(Boolean splitEqually, Double amount, Map<String,Integer> paidFor) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Enter the correct url for your api service site
        JSONObject j = new JSONObject();
        //"https://aislepay.herokuapp.com/testdata";
        String url = "https://aislepay.herokuapp.com/testdata";
        try {
            JSONObject j1 = new JSONObject();
            JSONArray array = new JSONArray();
            for(String s:paidFor.keySet()){
                j1.put("number",s);
                j1.put("amount",paidFor.get(s));
            }

            array.put(j1);
            j.put("splitEqually",splitEqually);
            j.put("paidAmount",amount);
            j.put("paidFor",array);
            //j.put("splitEqually",splitEqually);
           // j.put("amount",amount);
          //  j.put("contact",contact);

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
