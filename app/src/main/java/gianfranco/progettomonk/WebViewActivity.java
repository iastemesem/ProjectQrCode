package gianfranco.progettomonk;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static java.security.AccessController.getContext;

/**
 * Created by Gianfranco on 29/03/2017.
 */

public class WebViewActivity extends AppCompatActivity  implements View.OnClickListener{

    private Toolbar toolbar;
    private Intent i;
    private ProgressDialog progressDialog;
    boolean lanciato = false;
    private String shortenedUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_for_webview);

        toolbar = (Toolbar) findViewById(R.id.web_view_toolbar);
        setSupportActionBar(toolbar);

        i = getIntent();



        shortenedUrl = i.getStringExtra("URL");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading..");
        progressDialog.show();

        final WebView myWebView = (WebView) findViewById(R.id.fragment_web_view);
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
                if (url.contains(".pdf") && lanciato == false){
                    view.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+shortenedUrl);
                    lanciato = true;
                }
                if (url.contains("youtube") && lanciato == false){
                    Toast.makeText(view.getContext(), "Ciaoooo", Toast.LENGTH_SHORT).show();
                    /*view.loadData(" <DOCTYPE html>" +
                            "<html>" +
                            " <iframe width=\"300\" height=\"315\" src=\"https://www.youtube.com/embed/kJQP7kiw5Fk\" frameborder=\"0\" allowfullscreen></iframe>"+
                            "</html>", "text/html", "utf-8");*/
                    lanciato = true;
                }
                progressDialog.dismiss();
            }
        });

        myWebView.loadUrl(shortenedUrl);




    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
