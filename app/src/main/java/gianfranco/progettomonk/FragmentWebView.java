package gianfranco.progettomonk;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


/**
 * Created by Gianfranco on 22/03/2017.
 */

public class FragmentWebView extends android.support.v4.app.Fragment {

    private final String URL = "url";
    private ProgressDialog progressDialog;
    String longUrl = "";
    boolean lanciato = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        final String shortenedUrl = getArguments().getString(URL,"http://www.google.it");


        View v = inflater.inflate(R.layout.fragment_for_webview, container, false);

        final WebView myWebView = (WebView) v.findViewById(R.id.fragment_web_view);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        myWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        myWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        myWebView.getSettings().setSupportMultipleWindows(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Toast.makeText(getContext(),"Long Url-->"+url,Toast.LENGTH_LONG).show();
                if (url.contains(".pdf") && lanciato == false){
                    view.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+shortenedUrl);
                    lanciato = true;
                }
                progressDialog.dismiss();
            }
        });

        myWebView.loadUrl(shortenedUrl);
        return v;

    }

}
