package gianfranco.progettomonk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Gianfranco on 22/03/2017.
 */

public class FragmentBtnCamera extends android.support.v4.app.Fragment implements View.OnClickListener {
    private final String URL = "url";

    public FragmentBtnCamera() {
    }

    private ImageButton camera;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_for_camera, container, false);
        camera = (ImageButton) v.findViewById(R.id.fragment_btn_camera_btn);
        camera.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {

        IntentIntegrator integrator = new IntentIntegrator((AppCompatActivity)getContext());
        integrator.setPrompt("Scan");
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.forSupportFragment(FragmentBtnCamera.this).initiateScan();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MainActivity", "Scanned");
                Toast.makeText(getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Bundle bundleInfoUrl = new Bundle();
                bundleInfoUrl.putString(URL, result.getContents());
                android.support.v4.app.Fragment fr =  new FragmentWebView();
                fr.setArguments(bundleInfoUrl);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_fragment, fr);
                ft.commit();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
