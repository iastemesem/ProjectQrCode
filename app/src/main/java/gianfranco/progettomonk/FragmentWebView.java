package gianfranco.progettomonk;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

/**
 * Created by Gianfranco on 22/03/2017.
 */

public class FragmentWebView extends android.support.v4.app.Fragment {

    private final String URL = "url";
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        String shortenedUrl = getArguments().getString(URL, "http://www.google.it");

        View v = inflater.inflate(R.layout.fragment_for_webview, container, false);

        WebView myWebView = (WebView) v.findViewById(R.id.fragment_web_view);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.setWebViewClient(new WebViewClient());

        if (shortenedUrl.contains("http://q-r.to/") || shortenedUrl.contains(".pdf")){
            myWebView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+shortenedUrl);
       }else {myWebView.loadUrl(shortenedUrl);}
        return v;

    }

}
