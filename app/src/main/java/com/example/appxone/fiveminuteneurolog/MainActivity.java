package com.example.appxone.fiveminuteneurolog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends FragmentActivity {


    private View btnRender;
    private LinearLayout container;
    WebView webvew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (LinearLayout) findViewById(R.id.fragment_layout);
        container.setVisibility(View.VISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_layout, new PDFRenderFragment());
        ft.commit();

        //  webvew = (WebView) findViewById(R.id.webview);
        //btn = (Button) findViewById(R.id.btn_render);
        // btnRender = (View)findViewById(R.id.btn_render);

        //   WebView webView=new WebView(MainActivity.this);


        // webvew.getSettings().setJavaScriptEnabled(true);


        //webvew.settings.setBuiltInZoomControls(true);
        // webvew.getSettings().setPluginState(WebSettings.PluginState.ON);


//        webvew.getSettings().setBuiltInZoomControls(true);
//        webvew.getSettings().setSupportZoom(true);
//        webvew.getSettings().setLoadsImagesAutomatically(true);
//        webvew.getSettings().setJavaScriptEnabled(true);
//        webvew.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        //  String pdfURL = "file:///android_asset/5 Minute Neurology Consult - 2nd Edition.pdf";
        //webvew.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdfURL);
        //  webvew.loadUrl(pdfURL);

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent intent = new Intent(MainActivity.this, PDFRenderFragment.class);
//                // startActivity(intent);
//
//                container = (LinearLayout) findViewById(R.id.fragment_layout);
//                btn.setVisibility(View.GONE);
//              //  container.setVisibility(View.VISIBLE);
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.fragment_layout, new PDFRenderFragment()).addToBackStack(null);
//                ft.commit();
//            }
//        });


        //webvew.loadUrl(rank[position]);

        // container = (LinearLayout)findViewById(R.id.fragment_layout);

//        container.setVisibility(View.VISIBLE);
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragment_layout, new PDFRenderFragment());
//        ft.commit();


        //gone button after all
        //   btnRender.setVisibility(View.GONE);


        //set event handling for button
        //  btnRender.setOnClickListener(onClickListener());

    }
//    private View.OnClickListener onClickListener() {
//        return new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //replace fragment when clicked
////                FragmentManager fm = getSupportFragmentManager();
////                FragmentTransaction ft = fm.beginTransaction();
////                ft.replace(R.id.fragment_layout, new PDFRenderFragment());
////                ft.commit();
////
////                //gone button after all
////                btnRender.setVisibility(View.GONE);
////                container.setVisibility(View.VISIBLE);
//            }
//        };
//    }

}

