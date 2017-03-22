package gianfranco.progettomonk;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Gianfranco on 22/03/2017.
 */

public class MainActivity extends AppCompatActivity {


    private final String URL = "url";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(this, contents, Toast.LENGTH_LONG).show();
                Bundle bundleInfoUrl = new Bundle();
                bundleInfoUrl.putString(URL, contents);
                Fragment fr = new FragmentWebView();
                fr.setArguments(bundleInfoUrl);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_fragment, fr);
                ft.commit();


            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }
}
