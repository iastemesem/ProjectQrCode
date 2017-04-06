package gianfranco.progettomonk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static gianfranco.progettomonk.LoginActivity.utenteLogin;


/**
 * Created by Gianfranco on 29/03/2017.
 */

public class WebViewActivity extends AppCompatActivity{

    private static final String TAG = "WEB VIEW ACTIVITY" ;
    private Toolbar toolbar;
    private Intent i;
    private ProgressDialog progressDialog;
    boolean lanciato = false, inserito = false;
    private String shortenedUrl;
    private String tipo = "";
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private Date today = new Date();
    private String reportDate = df.format(today);


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
        //myWebView.getSettings().setUserAgentString("Chrome/32.0.1667.0" );

        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //Toast.makeText(getBaseContext(), url,Toast.LENGTH_LONG).show();
                /*if (url.contains(".pdf") && lanciato == false){
                    view.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+shortenedUrl);
                    lanciato = true;
                }*/
                if (url.contains("youtube") && lanciato == false){
                    tipo = "video";
                    lanciato = true;
                  /*  int start = shortenedUrl.indexOf("=");
                    String id = shortenedUrl.substring(start+1, shortenedUrl.length());
                 //   Toast.makeText(view.getContext(), id , Toast.LENGTH_SHORT).show();
                    lanciato = true;
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    toolbar.setVisibility(View.GONE);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    view.loadData(" <DOCTYPE html>" +
                            "<html>" +
                            " <iframe style=\"padding=\"0\" margin=\"0\" \" width=\"100%\" height=\"95%\" src=\"https://www.youtube.com/embed/"+id+"?autoplay=1\" frameborder=\"0\" allowfullscreen></iframe>"+
                            "</html>", "text/html", "utf-8");*/

                } else if (url.contains(".jpg") || url.contains(".png") || url.contains(".jpeg") || url.contains(".gif") && lanciato == false){
                    tipo = "image";
                    lanciato = true;
                }else  {
                    tipo = "link";
                    lanciato = true;
                }
                progressDialog.dismiss();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
               // Toast.makeText(getBaseContext(), url,Toast.LENGTH_LONG).show();
            }
        });

        myWebView.loadUrl(shortenedUrl);
        uploadQrCode(shortenedUrl, tipo);
    }

    private void uploadQrCode(String shortenedUrl, String tipo) {

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("Accept", "application/json");
        headers.put("Authorization", utenteLogin.getToken());
        ApiInterface apiService = ApiClient.getInstance(headers);
        Call<ResponseBody> call = apiService.insertUrl(reportDate, shortenedUrl, tipo);
        Log.d(TAG, "uploadQrCode: 0"+call.toString()+"tipo-->"+tipo);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG, "onResponse: "+response.code()+"DATE->"+reportDate);
                if (response.code() == 200){
                    if (response.body().getStatus() != 0){
                        onUploadFailure();
                    }else {
                        Toast.makeText(getBaseContext(), "YESSSS", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getBaseContext(), response.message(), Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "onResponse: "+ response.body().getErrorCode());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Please check your connetivity", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void onUploadFailure(){
        for (int i =0 ; i <2 ; i++){
            uploadQrCode(shortenedUrl, tipo);
        }
        Toast.makeText(getBaseContext(), "E' stato impossibile caricare il QrCode", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
