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

/**
 * Created by Gianfranco on 23/03/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.register_username_ET) EditText emailText;
    @InjectView(R.id.register_psw_ET) EditText pswText;
    @InjectView(R.id.register_register_btn) Button registerBtn;
    @InjectView(R.id.register_login_btn) TextView loginLink;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        registerBtn.setOnClickListener(this);
        loginLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == loginLink){
            startActivity(new Intent(v.getContext(), LoginActivity.class));
            finish();
        }

        if (v == registerBtn){
            signUp();
        }
    }

    private void signUp() {

        if (!validate()) {
            onSignUpFailed();
            return;
        }

        String email, psw;
        email = emailText.getText().toString();
        psw = pswText.getText().toString();
        registerBtn.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account ...");
        progressDialog.show();


        Toast.makeText(getBaseContext(), "Account Created", Toast.LENGTH_SHORT).show();
        onSignUpSuccess();// In attesa della API per creare l'accounr, invio della mail ecc..

        progressDialog.dismiss();


    }

    private void onSignUpSuccess() {
        startActivity(new Intent(this, ScanActivity.class)); //Solo per arrivare allo scann
        finish();
    }

    private void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Register Failed", Toast.LENGTH_SHORT).show();
        emailText.setText("");
        pswText.setText("");
        registerBtn.setEnabled(true);
    }

    private boolean validate() {

        boolean valid = true;

        String email, psw;
        email = emailText.getText().toString();
        psw = pswText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (psw.isEmpty() || psw.length()<8){
            pswText.setError("enter a valid password");
            valid = false;
        } else {
            pswText.setError(null);
        }

        return valid;
    }
}
