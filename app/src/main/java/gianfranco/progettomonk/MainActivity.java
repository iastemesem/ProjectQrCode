package gianfranco.progettomonk;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
  *//*      if (requestCode == 0) {
            if (resultCode == RESULT_OK) {*//*
                if (result != null) {
                    if (result.getContents() == null) {
                        Log.d("MainActivity", "Cancelled scan");
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("MainActivity", "Scanned");
                        Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                        Bundle bundleInfoUrl = new Bundle();
                        bundleInfoUrl.putString(URL, result.getContents());
                        Fragment fr = new FragmentWebView();
                        fr.setArguments(bundleInfoUrl);
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.replace(R.id.main_fragment, fr);
                        ft.commit();
                    }
                }else {
                    // This is important, otherwise the result will not be passed to the fragment
                    super.onActivityResult(requestCode, resultCode, data);
                }
            *//*}*//*
*//*            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }*//*
        }*/



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

