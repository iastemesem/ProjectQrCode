package gianfranco.progettomonk;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import gianfranco.progettomonk.R;

/**
 * Created by Gianfranco on 22/03/2017.
 */

public class MainActivity extends AppCompatActivity {

    private Button login, register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (Button) findViewById(R.id.main_login_btn);
        register = (Button) findViewById(R.id.main_register_btn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(),RegisterActivity.class));
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), LoginActivity.class));
                finish();
            }
        });




    }

}

