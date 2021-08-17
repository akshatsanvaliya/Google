package com.example.google;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

import java.util.ArrayList;
import java.util.List;

public class github extends AppCompatActivity {
Button Login;
EditText email;
FirebaseAuth mAuth;
String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        Login=findViewById(R.id.Login);
        email=findViewById(R.id.email);
        mAuth=FirebaseAuth.getInstance();

        Login.setOnClickListener( view -> {
            String mail= email.getText().toString();
            if(!mail.matches(emailpattern)){
                Toast.makeText(github.this, "Wrong Format of Email", Toast.LENGTH_SHORT).show();
            }else{
                OAuthProvider.Builder provider = OAuthProvider.newBuilder("github.com");
                provider.addCustomParameter("login",mail);

                List<String> scopes=
                        new ArrayList<String>()
                        {
                            {
                                add("user.email");
                            }
                        };
                provider.setScopes(scopes);
                Task<AuthResult> pendingResultTask = mAuth.getPendingAuthResult();
                if (pendingResultTask != null) {
                    // There's something already here! Finish the sign-in for your user.
                    pendingResultTask
                            .addOnSuccessListener(
                                    authResult -> {
                                        // User is signed in.
                                        // IdP data available in
                                        // authResult.getAdditionalUserInfo().getProfile().
                                        // The OAuth access token can also be retrieved:
                                        // authResult.getCredential().getAccessToken().
                                        // The OAuth secret can be retrieved by calling:
                                        // authResult.getCredential().getSecret().
                                    } )
                            .addOnFailureListener(
                                    e -> Toast.makeText(github.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show() );
                } else {
                    mAuth
                            .startActivityForSignInWithProvider(/* activity= */ github.this, provider.build())
                            .addOnSuccessListener(
                                    authResult -> openNextActivity() )
                            .addOnFailureListener(
                                    e -> Toast.makeText(github.this, "="+e.getMessage(), Toast.LENGTH_SHORT).show() );

                }



            }
        } );
    }

    private void openNextActivity() {
        Intent intent = new Intent(github.this, MainActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
}
}