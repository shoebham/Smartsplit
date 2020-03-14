package com.example.smartsplit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    public  Button split2;

    public  TextView hidden1,hidden2,hidden3,hidden4;
    public  Toolbar toolbaradd;
    public EditText amountEditText;
    public ImageButton clearAll;
    public RelativeLayout relativeLayout;
    public EditText et_friend_share1,et_friend_share2,et_friend_share3,et_friend_share4;
    public  TextView friend_share1,friend_share2,friend_share3,friend_share4;
    Map<String,String> contact_details = new LinkedHashMap<>();
    Map<String,Integer> unEqualMap = new LinkedHashMap<>();
    ArrayList<String> numbers = new ArrayList<>();
    //unequal share Editext



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        relativeLayout= (RelativeLayout)findViewById(R.id.relative_layout);
        //setting IDS
       // your_share_text = findViewById(R.id.your_share_text);
       // friend_share_text=findViewById(R.id.friend_share_text);
         //   et_your_share =findViewById(R.id.et_your_share);
      //  et_friend_share=findViewById(R.id.et_friend_share);
        hidden1 = findViewById(R.id.hidden1);
        hidden2 = findViewById(R.id.hidden2);
        hidden3 = findViewById(R.id.hidden3);
        hidden4 = findViewById(R.id.hidden4);
        amountEditText=findViewById(R.id.amount);
        equally = findViewById(R.id.equally);
        unequally = findViewById(R.id.unequally);
        clearAll = findViewById(R.id.clearall);
        friend_share1 = findViewById(R.id.friend1_share);
        friend_share2 = findViewById(R.id.friend2_share);
        friend_share3 = findViewById(R.id.friend3_share);
        friend_share4 = findViewById(R.id.friend4_share);
        et_friend_share1=findViewById(R.id.et_friend1_share);
        et_friend_share2=findViewById(R.id.et_friend2_share);
        et_friend_share3=findViewById(R.id.et_friend3_share);
        et_friend_share4=findViewById(R.id.et_friend4_share);
        split2 = findViewById(R.id.split2);
        // if(getSupportActionBar() != null) {
        //    getSupportActionBar().setTitle(getString("Add Expense"));
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //}

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
                        Log.i("number",contact_details+" map size is "+contact_details.size());
                    }
                    for(String s:contact_details.keySet()) {
                        if (contact_details.size() == 1) {
                            hidden1.setVisibility(View.VISIBLE);
                            clearAll.setVisibility(View.VISIBLE);
                            hidden1.setText("Name: " + contact_details.get(number) + " Number: " + s);
                            friend_share1.setText(contact_details.get(number)+":-");
                        }if (contact_details.size() == 2) {
                            hidden2.setVisibility(View.VISIBLE);
                            hidden2.setText("Name: " + contact_details.get(number) + " Number: " + s);
                            friend_share2.setText(contact_details.get(number)+":-");
                        }if (contact_details.size()==3){
                            hidden3.setVisibility(View.VISIBLE);
                            hidden3.setText("Name: " + contact_details.get(number) + " Number: " + s);
                            friend_share3.setText(contact_details.get(number)+":-");
                        }if (contact_details.size()==4){
                            hidden4.setVisibility(View.VISIBLE);
                            hidden4.setText("Name: " + contact_details.get(number) + " Number: " + s);
                            friend_share4.setText(contact_details.get(number)+":-");
                        }

                    }
                }
            }
        }
    }


public void clearAll(View v){
        contact_details.clear();
         hidden1.setVisibility(View.GONE);
         hidden2.setVisibility(View.GONE);
         hidden3.setVisibility(View.GONE);
         hidden4.setVisibility(View.GONE);
         clearAll.setVisibility(View.GONE);
        Log.i("numbers",contact_details+"");
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
      Log.i("numbers",contact_details.size()+"");
      if(contact_details.size()==0){
          relativeLayout.setVisibility(View.GONE);
          split2.setVisibility(View.VISIBLE);
      }
        else{  relativeLayout.setVisibility(View.VISIBLE); split2.setVisibility(View.GONE);}



    }
    public void makeInVisible(View v){
        //making this invisible
        relativeLayout.setVisibility(View.GONE);
        split2.setVisibility(View.VISIBLE);


    }
    //sending data to server

        public void send (View v){
        try{
        //AutoCompleteTextView contactsTextView= findViewById(R.id.contactAdd);
        //Boolean splitEqually=true;
        //String contacts = contactsTextView.getText().toString();
        Double amount = Double.parseDouble(amountEditText.getText().toString());
        for (String s : contact_details.keySet()) {
            numbers.add(s);
        }
        if (equally.isChecked()) {
            sendEqually(true, amount, contact_details);
        } else if (unequally.isChecked()) {
            Log.i("numbers", "unequal map is" + unEqualMap);
            String s1 = friend_share1.getText().toString();
            int amountFriend1 = Integer.parseInt(et_friend_share1.getText().toString());
            int amountFriend2 = Integer.parseInt(et_friend_share2.getText().toString());
            int amountFriend3 = Integer.parseInt(et_friend_share3.getText().toString());
            int amountFriend4 = Integer.parseInt(et_friend_share4.getText().toString());
            unEqualMap.put(numbers.get(0), amountFriend1);
            unEqualMap.put(numbers.get(1), amountFriend2);
            unEqualMap.put(numbers.get(2), amountFriend3);
            unEqualMap.put(numbers.get(3), amountFriend4);
            Log.i("numbers", unEqualMap + "unequal map");
            sendUnEqually(false, amount, unEqualMap);
            Toast.makeText(this, "Data sent", Toast.LENGTH_LONG).show();
        }
        }catch(Exception e){Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();}
    }

    public void sendEqually(Boolean splitEqually, Double amount,Map<String,String> paidFor) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Enter the correct url for your api service site
        JSONObject j = new JSONObject();
        JSONArray array = new JSONArray();
        String url = "https://paisa.free.beeceptor.com";
        try {
            double splitAmount = amount/(paidFor.size()+1);

            for(String s:paidFor.keySet()) {
                JSONObject j1 = new JSONObject();
                j1.put("number", s);
                j1.put("amount", splitAmount);
                array.put(j1);
            }
            j.put("splitEqually",splitEqually);
            j.put("amount",amount);
            j.put("paidFor",array);

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
        String url = "https://paisa.free.beeceptor.com";
        try {

            JSONArray array = new JSONArray();
            for(String s:paidFor.keySet()){
                JSONObject j1 = new JSONObject();
                Log.i("numbers","s is "+s);
                j1.put("number",s);
                j1.put("amount",paidFor.get(s));
                Log.i("numbers","array is "+j1);
                array.put(j1);
            }

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
