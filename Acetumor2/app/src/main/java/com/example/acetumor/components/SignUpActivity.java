package com.example.acetumor.components;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acetumor.MainActivity;
import com.example.acetumor.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button back = findViewById(R.id.signup_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", "user");
                startActivity(intent);
            }
        });
        final EditText username = findViewById(R.id.signup_username);
        final EditText password1 = findViewById(R.id.signup_password_1);
        final EditText password2 = findViewById(R.id.signup_password_2);

        Button submit = findViewById(R.id.signup_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u = username.getText().toString();
                String p  = password1.getText().toString();
                if (password2.getText().equals(password1.getText())) {
                    Toast.makeText(view.getContext(), "Two passwords don't match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                post(u, p);
            }
        });
    }
    public void post(String u, String p) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.0.254:3000/api/user/r";
        final Activity thisActivity = this;
        final String username = u;
        final String password = p;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("msg", "Response is: "+ response);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user", "user");
                        intent.putExtra("login", true);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("msg", "That didn't work!");
                Log.d("msg", error.toString());
            }
        }){
            @Override
            public byte[] getBody() {

                byte[] body;
                JSONObject object = new JSONObject();
                try {
                    object.put("username", username);
                    object.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                body = object.toString().getBytes(StandardCharsets.UTF_8);
                return body;
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}