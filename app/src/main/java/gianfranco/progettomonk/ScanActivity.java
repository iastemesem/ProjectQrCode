package gianfranco.progettomonk;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Gianfranco on 23/03/2017.
 */

public class ScanActivity extends AppCompatActivity  implements ZXingScannerView.ResultHandler {

   private Toolbar toolbar;
    private ZXingScannerView viewScanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_scan);

        toolbar = (Toolbar) findViewById(R.id.scan_toolbar);
        setSupportActionBar(toolbar);
        viewScanner = (ZXingScannerView) findViewById(R.id.scan_zxing);

        viewScanner.setResultHandler(this);
        viewScanner.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewScanner.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.w("handleResult", result.getText());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan result");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        
        viewScanner.resumeCameraPreview(this);
    }
}
