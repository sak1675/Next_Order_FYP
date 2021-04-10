package com.example.nextorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class RegisterActivity extends AppCompatActivity {
TextView register_login;
Button register;
EditText fullname, username, email, password, cpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        register_login = findViewById(R.id.register_login);
        register = findViewById(R.id.register);
        fullname = findViewById(R.id.fullnametxt);
        username = findViewById(R.id.usernametxt);
        email = findViewById(R.id.emailtxt);
        password = findViewById(R.id.passwordtxt);
        cpassword = findViewById(R.id.confirmpasswordtxt);

        trustEveryone();
        //takes LoginActivity from RegisterActivity
        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Sign Up btn
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fullname.toString().equals("") || username.toString().equals("") || email.toString().equals("") || password.toString().equals("") || cpassword.toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please enter data",Toast.LENGTH_SHORT).show();
                }
                else if(!password.getText().toString().equals(cpassword.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Your password are not matching",Toast.LENGTH_SHORT).show();
                }
                else{
                    String fullnametxt = fullname.getText().toString();
                    String usernametxt = username.getText().toString();
                    String emailtxt = email.getText().toString();
                    String passwordtxt = password.getText().toString();

                    register(fullnametxt,usernametxt,emailtxt,passwordtxt);

                }
            }
        });


    }



    private void register( final String fullname, final String username, final String email, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("Registering your account");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://192.168.1.114/nextorder/signup.php";


        StringRequest request = new StringRequest(Method.POST, uRl, response -> {

            if (response.equals("You are registered successfully")){
                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                progressDialog.dismiss();
                finish();
            }else {
                Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

        }, error -> {
            Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("fullname",fullname);
                param.put("username",username);
                param.put("email",email);
                param.put("password",password);

                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(RegisterActivity.this).addToRequestQueue(request);

    }
    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}