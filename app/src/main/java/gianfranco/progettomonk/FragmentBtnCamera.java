package gianfranco.progettomonk;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Gianfranco on 22/03/2017.
 */

public class FragmentBtnCamera extends Fragment implements View.OnClickListener {

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

        try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes
            ((MainActivity)v.getContext()).startActivityForResult(intent, 0);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            v.getContext().startActivity(marketIntent);
        }

    }
}
