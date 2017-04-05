package gianfranco.progettomonk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Gianfranco on 23/03/2017.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static User utente;

    @InjectView(R.id.register_username_ET) EditText emailText;
    @InjectView(R.id.register_psw_ET) EditText pswText;
    @InjectView(R.id.register_register_btn) Button registerBtn;
    @InjectView(R.id.register_login_btn) TextView loginLink;
    @InjectView(R.id.register_psw_conferma_ET) EditText pswConfermaText;

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

        String email, psw, pswConferma;
        email = emailText.getText().toString();
        psw = pswText.getText().toString();

        registerBtn.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account ...");
        progressDialog.show();

        ApiInterface apiService = ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ResponseBody> call = apiService.insertUser(emailText.getText().toString(),pswText.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if(response.body().getStatus() != 0){
                    Toast.makeText(getBaseContext(), "Impossibile completare la registrazione, è possibile che le credenziali siano già in uso", Toast.LENGTH_LONG).show();
                    emailText.setText("");
                    onSignUpFailed();
                }else {
                    ResponseUser responseUser = response.body().getResponse();
                    utente = new User();
                    utente.setEmail(emailText.getText().toString());
                    utente.setPsw(pswText.getText().toString());
                    utente.setToken(responseUser.getId());
                    Log.d("WEEEEEEEEEEEEEEEEE", "id-->"+responseUser.getUserId());
                    onSignUpSuccess();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                //Toast.makeText(getBaseContext(), t.getMessage() , Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), "Please check your connettivity", Toast.LENGTH_LONG).show();
                onSignUpFailed();
            }
        });

        //Toast.makeText(getBaseContext(), "Account Created", Toast.LENGTH_SHORT).show();
        // In attesa della API per creare l'accounr, invio della mail ecc..

    }

    private void onSignUpSuccess() {
        startActivity(new Intent(getApplicationContext(), ScanActivity.class)); //Solo per arrivare allo scann
        finish();
    }

    private void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Register Failed", Toast.LENGTH_SHORT).show();
        pswConfermaText.setText("");
        registerBtn.setEnabled(true);
    }

    private boolean validate() {

        boolean valid = true;

        String email, psw, pswConferma;
        email = emailText.getText().toString();
        psw = pswText.getText().toString();
        pswConferma = pswConfermaText.getText().toString();

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

        if (pswConferma.isEmpty() || !pswConferma.equals(psw)){
            pswConfermaText.setError("enter a valid confirm password");
            valid = false;
        }else {
            pswConfermaText.setError(null);
        }

        return valid;
    }
}
