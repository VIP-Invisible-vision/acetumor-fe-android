package com.example.acetumor.components;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.acetumor.R;

import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TIntentWap;
import org.devio.takephoto.model.TResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class UploadPhotoActivity extends org.devio.takephoto.app.TakePhotoActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomHelper customHelper = new CustomHelper(findViewById(R.id.activity_test_image));
        customHelper.onClick(R.id.test_photo, getTakePhoto());
//        setContentView(R.layout.activity_test_image);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        File file = new File(result.getImage().getOriginalPath());
        try {
            post(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        Log.d("msg", Integer.toString(bytes.length));
        return new String(Base64.encodeToString(bytes, Base64.DEFAULT));
    }

    public void post(File file) throws Exception {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://www.minqz2009.com/api/upload-img";
        final String fileEncode = encodeFileToBase64Binary(file);
        final Activity thisActivity = this;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("msg", "Response is: "+ response);
                        try {
                            JSONObject json = new JSONObject(response);
                            int b = json.getInt("b");
                            int i = json.getInt("i");
                            int iv = json.getInt("iv");
                            int n = json.getInt("n");
                            Intent result = new Intent(thisActivity, TestResultActivity.class);
                            result.putExtra("b", b);
                            result.putExtra("i", i);
                            result.putExtra("iv", iv);
                            result.putExtra("n", n);
                            startActivity(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("msg", "That didn't work!");
                Log.d("msg", error.toString());
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {

                byte[] body = new byte[0];
                JSONObject object = new JSONObject();
                try {
                    object.put("img", fileEncode);
                    object.put("cat", "1123");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    body = object.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e("msg", "Unable to gets bytes from JSON", e.fillInStackTrace());
                }
                return body;
            }


            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }
}
