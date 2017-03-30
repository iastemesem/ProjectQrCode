package gianfranco.progettomonk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


/**
 * Created by Gianfranco on 29/03/2017.
 */

public class WebViewActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private Intent i;
    private ProgressDialog progressDialog;
    boolean lanciato = false;
    private String shortenedUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

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
        myWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        myWebView.getSettings().setSupportMultipleWindows(true);
        myWebView.getSettings().setSupportZoom(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setAllowFileAccess(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.setInitialScale(1);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setAllowContentAccess(true);
        myWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        myWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        myWebView.getSettings().setUserAgentString("Chrome/32.0.1667.0" );

        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.contains(".pdf") && lanciato == false){
                    view.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+shortenedUrl);
                    lanciato = true;
                }
                if (url.contains("youtube") && lanciato == false){


                    int start = shortenedUrl.indexOf("=");
                    String id = shortenedUrl.substring(start+1, shortenedUrl.length());

                    Toast.makeText(view.getContext(), id , Toast.LENGTH_SHORT).show();

                    view.loadData(" <DOCTYPE html>" +
                            "<html>" +
                            " <iframe style=\"padding=\"0\" margin=\"0\" \" width=\"100%\" height=\"95%\" src=\"https://www.youtube.com/embed/"+id+"?autoplay=1\" frameborder=\"0\" allowfullscreen></iframe>"+
                            "</html>", "text/html", "utf-8");
                    lanciato = true;

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    toolbar.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                }
                progressDialog.dismiss();
            }
        });

        myWebView.loadUrl(shortenedUrl);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
