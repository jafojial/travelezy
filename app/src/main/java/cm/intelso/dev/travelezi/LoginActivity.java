package cm.intelso.dev.travelezi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

import cm.intelso.dev.travelezi.apiclient.RetrofitClient;
import cm.intelso.dev.travelezi.dto.AuthToken;
import cm.intelso.dev.travelezi.dto.User;
import cm.intelso.dev.travelezi.utils.DataUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText tvEmail, tvPassword;
    private Button btnLogin;
    private ProgressBar progressbar;

    // Progress dialog
    private ProgressDialog pDialog;

    private static String TAG = LoginActivity.class.getSimpleName();

//    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // taking instance of FirebaseAuth
//        mAuth = FirebaseAuth.getInstance();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pDialog = DataUtils.createProgressDialog(LoginActivity.this, getApplicationContext().getString(R.string.wait), Boolean.FALSE);

        // initialising all views through id defined above
        tvEmail = findViewById(R.id.tv_email);
        tvPassword = findViewById(R.id.tv_password);
        btnLogin = findViewById(R.id.btn_login);
        progressbar = findViewById(R.id.progressBar);

        // Set on Click Listener on Sign-in button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount()
    {

        // show the visibility of progress bar to show loading
//        progressbar.setVisibility(View.VISIBLE);
        DataUtils.showProgressDialog(pDialog);

        // Take the value of two edit texts in Strings
        String email, password;
        email = tvEmail.getText().toString();
        password = tvPassword.getText().toString();

        // validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter email!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                    "Please enter password!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // signin existing user
        getAuthToken(email, password);
    }

    private void getAuthToken(String username, String pwd) {
        Call<AuthToken> call = RetrofitClient.getInstance().getMyApi().requestAuthToken(new User(username, pwd));
        call.enqueue(new Callback<AuthToken>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<AuthToken> call, Response<AuthToken> response) {

                Log.i(TAG, "URL : " + call.request().url().toString());
                AuthToken authToken = response.body();
                if (authToken != null) {
                    Log.i(TAG, "TOKEN : " + authToken.getToken());
                    String roles = null;
                    try {
                        //OK, we can trust this JWT
                        Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(DataUtils.getPublicKey()).build().parseClaimsJws(authToken.getToken());
                        roles = Objects.requireNonNull(jws.getBody().get("roles")).toString();
                        Log.i(TAG, "USER ROLES : " + roles);
                        Log.i(TAG, "CLAIMS : " + jws.toString());
                    } catch (JwtException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                        //don't trust the JWT!
                        Log.i(TAG, "JWT TOKEN EXCEPTION : " + e.getMessage());
                    }
                    Toast.makeText(getApplicationContext(),
                            "Login successful!!",
                            Toast.LENGTH_LONG)
                            .show();

                    // hide the progress bar
//                    progressbar.setVisibility(View.GONE);
                    DataUtils.hideProgressDialog(pDialog);

                    // if sign-in is successful
                    if(roles.contains("ROLE_DRIVER")){
                        // Driver
                        // Intent to driver home activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else if(roles.equals("[ROLE_USER]")){
                        // Passenger
                        // Intent to passenger home activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                else {

                    // sign-in failed
                    Toast.makeText(getApplicationContext(), "Login failed!!", Toast.LENGTH_LONG).show();

                    // hide the progress bar
//                    progressbar.setVisibility(View.GONE);
                    DataUtils.hideProgressDialog(pDialog);
                }

            }

            @Override
            public void onFailure(Call<AuthToken> call, Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                Toast.makeText(getApplicationContext(), "An error has occured when fetch url " + call.request().url() + ": " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                // hide the progress bar
//                progressbar.setVisibility(View.GONE);
                DataUtils.hideProgressDialog(pDialog);
            }

        });
    }

}