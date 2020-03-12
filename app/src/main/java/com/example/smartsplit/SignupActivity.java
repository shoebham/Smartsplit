package com.example.smartsplit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {


    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = "+" + code + number;

                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
}
/*
*
*
public class SignupActivity extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignUp = findViewById(R.id.button2);
        tvSignIn = findViewById(R.id.textView);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //long email = Integer.parseInt(emailId.getText().toString());
                String pwd = password.getText().toString();
                if(emailId.getText().toString().isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(emailId.getText().toString().isEmpty()&& pwd.isEmpty()){
                    Toast.makeText(SignupActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(emailId.getText().toString().isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(emailId.getText().toString(), pwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Log.i("SIGNUP EXCEPTION",task.getException().getMessage());
                                Toast.makeText(SignupActivity.this,"SignUp Unsuccessful, Please Try Again",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                createUserAndSession(emailId.getText().toString());
                                //startActivity(new Intent(SignupActivity.this,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(SignupActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });
    }
}

 */
    /*


    public void createUserAndSession(String userEmail) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Enter the correct url for your api service site
        JSONObject emailJSON = new JSONObject();
        String url = "https://aislepay.herokuapp.com/createNewUser";
        try {
            emailJSON.put("email", userEmail);
        }catch (Exception e){
            Log.i("EXCEPTION", e.getMessage());
        };
        // parser.parse(j);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, emailJSON,
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
}*/
/*
*
* RequestQueue requestQueue = Volley.newRequestQueue(this);
        // Enter the correct url for your api service site
        JSONObject j = new JSONObject();
        JSONArray array = new JSONArray();
        String url = "https://paisa.free.beeceptor.com";
        try {
            double splitAmount = amount/(paidFor.size()+1);
            JSONObject j1 = new JSONObject();
            for(String s:paidFor.keySet()) {
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
        requestQueue.add(jsonObjectRequest);*/