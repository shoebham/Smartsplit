package com.example.smartsplit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
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
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.QuickContactBadge;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddExpense extends AppCompatActivity {
    private static final String[] CONTACTS = new String[]{
            "Angad", "Deekshant", "Preetam", "Shubham", "Vibhu"
    };
    public String phone_number_user;
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
    public  Chip  hidden1;
    public  Chip hidden2,hidden3,hidden4;
    public  Toolbar toolbaradd;
    public EditText amountEditText;
    public ImageButton clearAll;
    public TextInputEditText et_friend_share1;
    public RelativeLayout relativeLayout;
    public EditText et_friend_share2,et_friend_share3,et_friend_share4;
    public  TextView friend_share1,friend_share2,friend_share3,friend_share4;
    Map<String,String> contact_details = new LinkedHashMap<>();
    Map<String,Integer> unEqualMap = new LinkedHashMap<>();
    ArrayList<String> numbers = new ArrayList<String>();
    ArrayList<String> name_of_contacts = new ArrayList<>();
    private Listview_Adapter adapter_contacts;
    int start = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Intent i =getIntent();
        phone_number_user=i.getStringExtra("phone_number_user");
        Log.i("numbers",i.getStringExtra("phone_number_user")+" in AddExpense");
        //setting IDS
        amountEditText=findViewById(R.id.amount);
        equally = findViewById(R.id.equally);
        unequally = findViewById(R.id.unequally);
        split2 = findViewById(R.id.split2);
        // if(getSupportActionBar() != null) {
        //    getSupportActionBar().setTitle(getString("Add Expense"));
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //}

//        Button saveButton = findViewById(R.id.saveButton);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Snackbar.make(v, "Data Saved", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
//            }
//        });

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
        ImageView contactAdd = findViewById(R.id.imageButton);
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
                    if (contact_details.size()>=100){
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
    //opening contact selection screen
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
                    }
                    put_in_contacts();
                    Log.i("number",contact_details+" map size is "+contact_details.size());
                }
            }
        }
    }

    //Contact Chips
    public void put_in_contacts(){
      //  clearAll.setVisibility(View.VISIBLE);
        final ChipGroup chipGroup = findViewById(R.id.chip_group);
        List<String> numbers_list = new ArrayList<String>(contact_details.keySet());
        for(int i=start;i<numbers_list.size();i++){
            start = start+1;
            Log.i("numbers",start+"");
            final String key = numbers_list.get(i);
            final String tagName = contact_details.get(key);
            final Chip chip = new Chip(this);
            int paddingDp = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10,
                    getResources().getDisplayMetrics());
            chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
            chip.setText(tagName);
            chip.setOnCloseIconClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contact_details.remove(key);
                    start-=1;
                    chipGroup.removeView(chip);
                }
            });

            chip.setCloseIconEnabled(true);
            chipGroup.addView(chip);

        }

    }

    public void openMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private int getString(String add_expense) {
        return 0;
    }

    //showing unequal fields
    public void makeVisible(View v) {
        final Dialog dialog = new Dialog(AddExpense.this);
        dialog.setContentView(R.layout.test);
        dialog.setTitle("Title...");

        for (String s : contact_details.keySet()) {
            name_of_contacts.add(contact_details.get(s));
            Log.i("numbers",name_of_contacts+"");
        }
            adapter_contacts = new Listview_Adapter(this, name_of_contacts);
            ListView listView = dialog.findViewById(R.id.unequal_list);
            listView.setAdapter(adapter_contacts);
            dialog.show();

    }

    public void makeInVisible(View v){

    }
    //sending data to server
        public void send (View v){
        try{
        Double amount = Double.parseDouble(amountEditText.getText().toString());
        for (String s : contact_details.keySet()) {
            numbers.add(s);
        }
        if (equally.isChecked()) {
            sendEqually(true, amount, contact_details,phone_number_user);
            Toast.makeText(this, "Data sent", Toast.LENGTH_LONG).show();
        } else if (unequally.isChecked()) {
            sendUnEqually(false, amount, unEqualMap,phone_number_user);
            Toast.makeText(this, "Data sent", Toast.LENGTH_LONG).show();
        }
        }catch(Exception e){Toast.makeText(this,"Error",Toast.LENGTH_LONG).show();}
    }
    //Send equally
    public void sendEqually(Boolean splitEqually, Double amount,Map<String,String> paidFor,String paidBy) {
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
            j.put("paidBy",paidBy);

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
    public void sendUnEqually(Boolean splitEqually, Double amount, Map<String,Integer> paidFor,String paidBy) {
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
            j.put("paidBy",paidBy);

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

