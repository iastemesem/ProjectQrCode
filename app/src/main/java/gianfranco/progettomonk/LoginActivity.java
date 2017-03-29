package gianfranco.progettomonk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gianfranco.progettomonk.R;

/**
 * Created by Gianfranco on 23/03/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @InjectView(R.id.login_username_ET) EditText emailTextt;
    @InjectView(R.id.login_psw_ET) EditText passwordText;
    @InjectView(R.id.login_login_btn) Button loginButton;
    @InjectView(R.id.login_register_btn) TextView registerLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        loginButton.setOnClickListener(this);
        registerLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == loginButton){
            login();
        }
        if (v == registerLink){
            finish();
            startActivity(new Intent(this, RegisterActivity.class));

        }
    }

    private void login (){
        if (!validate()) {
            onLoginFailed();
            return;
        }
        loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Login...");
        progressDialog.show();

        String email = emailTextt.getText().toString();
        String password = passwordText.getText().toString();

        progressDialog.dismiss();

        //Collegamento alle API per il login creare un nuovo thread.
        onLoginSuccess();

    }

    private boolean validate(){
        boolean valid = true;

        String email, psw;
        email = emailTextt.getText().toString();
        psw = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailTextt.setError("enter a valid email address");
            valid = false;
            } else {
               emailTextt.setError(null);
            }

        if (psw.isEmpty() || psw.length()<8){
            passwordText.setError("enter a valid password");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    private void onLoginFailed(){
        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        loginButton.setEnabled(true);
    }

    private void onLoginSuccess(){
        finish();
        startActivity(new Intent(this, ScanActivity.class)); //Solo per passare dalla login allo scanner

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
}
