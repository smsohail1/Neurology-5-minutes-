package com.example.appxone.fiveminuteneurolog;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends FragmentActivity {


    private View btnRender;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // btnRender = (View)findViewById(R.id.btn_render);
        container = (LinearLayout)findViewById(R.id.fragment_layout);

        container.setVisibility(View.VISIBLE);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_layout, new PDFRenderFragment());
        ft.commit();

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

