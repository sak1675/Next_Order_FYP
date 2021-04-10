package com.example.nextorder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
TextView login_register, forget_Password;
EditText usernametxt, passwwordtxt;
Button login;
    public static String uRl = "http://192.168.1.114/nextorder/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_register = findViewById(R.id.login_regiserr);
        forget_Password = findViewById(R.id.forgetpassword);
        usernametxt = findViewById(R.id.loginusernametxt);
        passwwordtxt = findViewById(R.id.loginpasswordtxt);
        login = findViewById(R.id.login);


        //sign in button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernametxt.toString().equals("") || passwwordtxt.toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter data",Toast.LENGTH_SHORT).show();
                }
                else{
                    String username = usernametxt.getText().toString();
                    String passowrd = passwwordtxt.getText().toString();
                    login(username,passowrd);


                }
            }
        });

        //takes RegisterActivity from LoginActivity
        login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this , RegisterActivity.class);
                startActivity(intent);
            }
        });

        //takes reset password from LoginActivity
        forget_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent( LoginActivity.this , resetPasswordActivity.class);
                startActivity(intent1);
            }
        });
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    private void login(final String username, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loging your account");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("Login Success")){
                    Toast.makeText(LoginActivity.this, "Login Success Welcome", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(LoginActivity.this, "login Fail", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("username",username);
                param.put("password",password);
                setDefaults("username",username,getApplicationContext());
                return param;

            }
        };


        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);
    }

}