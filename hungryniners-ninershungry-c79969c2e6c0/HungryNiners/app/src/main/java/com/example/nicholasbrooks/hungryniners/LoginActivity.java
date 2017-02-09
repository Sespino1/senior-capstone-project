package com.example.nicholasbrooks.hungryniners;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import static android.Manifest.permission.READ_CONTACTS;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, OnClickListener {
    SignInButton signInButton;
    Button signOut;
    EditText checkEmail;
    ImageView logo;
    GoogleApiClient mGoogleAppClient;
    private static final String TAG = "signInActivity";
    private static final String UNCC = "@uncc.edu";
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signOut = (Button)findViewById(R.id.signOut);
        signOut.setOnClickListener(this);
        checkEmail = (EditText)findViewById(R.id.emailCheck);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleAppClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton = (SignInButton)findViewById(R.id.googleButton);
        signInButton.setOnClickListener(this);


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == signInButton.getId()){
            if(checkEmail.getText().toString().contains(UNCC)){
                  signIn();

            }else{
                Toast.makeText(this, "UNCC Emails Only",Toast.LENGTH_SHORT).show();

            }


        }else if(v.getId() == signOut.getId()){
            Auth.GoogleSignInApi.signOut(mGoogleAppClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    Toast.makeText(LoginActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    clearDefault();
                }
            });

        }
    }

    public void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleAppClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else{
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("Name", "Null");
            intent.putExtra("Email", "NUl");
            startActivity(intent);

        }
    }



    public void handleSignInResult(GoogleSignInResult result){
        Log.d(TAG,"handleSignInResult " + result.isSuccess());
        if(result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("Name", acct.getDisplayName());
            intent.putExtra("Email", acct.getEmail());
            startActivity(intent);

        }else{
                Log.d("Did not", "Didnt sign in ");
                Toast.makeText(this,"Sign in failed!", Toast.LENGTH_SHORT).show();

        }
    }

    public void revokeAccess(){
        Auth.GoogleSignInApi.revokeAccess(mGoogleAppClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.d("access", "Revoked access");

            }
        });

    }


    public void clearDefault(){
        mGoogleAppClient.clearDefaultAccountAndReconnect().setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {

                Log.d("defaultAccount", "Cleared out");
            }
        });

    }


}







