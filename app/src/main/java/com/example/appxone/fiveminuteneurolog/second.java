package com.example.appxone.fiveminuteneurolog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;

import android.os.ParcelFileDescriptor;
//import android.support.v4.app.FragmentActivity;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import java.io.IOException;

/**
 * Created by APPXONE on 3/17/2016.
 */
//@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class second extends android.support.v4.app.Fragment {
    int pos;
    SubsamplingScaleImageView imageView;
    int pos_int;

    private ParcelFileDescriptor fileDescriptor;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    int pagecount;
    Bitmap bitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pdflayout, container, false);
    }

    //  @Override
    //  public void onCreate(Bundle savedInstanceState) {
    //  super.onCreate(savedInstanceState);
    //   setContentView(R.layout.pdflayout);


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        imageView=(SubsamplingScaleImageView) view.findViewById(R.id.image);

        SharedPreferences sharedpreferences = getActivity().getSharedPreferences("shared", Context.MODE_PRIVATE);
        pos_int= sharedpreferences.getInt("pos",0);


        showPage(pos_int);
   // Intent j = getIntent();
   // pos=j.getIntExtra("posit",0);

    /// pos_int = Integer.parseInt(pos);


//    try
//
//    {
//      //  openRenderer(second.this);
//        showPage(pos);
//    }
//
//    catch(
//    IOException e
//    )
//
//    {
//        e.printStackTrace();
//        Log.i("Fragment", "Error occurred!");
//        Log.e("Fragment", e.getMessage());
//        second.this.finish();
//    }


    //openRenderer(getApplicationContext());


    //  }



}


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            openRenderer(activity);

        } catch (IOException e) {
            e.printStackTrace();
            Log.i("Fragment", "Error occurred!");
            Log.e("Fragment", e.getMessage());
            activity.finish();
        }
    }

    @Override
    public void onDestroy() {
        PDFRenderFragment.calculator_List.setVisibility(View.VISIBLE);
        try {

            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != currentPage) {
            outState.putInt("current_page", currentPage.getIndex());
        }
    }


    private void openRenderer(Activity activity) throws IOException {
        // Reading a PDF file from the assets directory.
        fileDescriptor = activity.getAssets().openFd("5 Minute Neurology Consult - 2nd Edition.pdf").getParcelFileDescriptor();

        //This is the PdfRenderer we use to render the PDF.
        pdfRenderer = new PdfRenderer(fileDescriptor);
    }


    private void closeRenderer() throws IOException {
        if (null != currentPage) {
            currentPage.close();
        }
        pdfRenderer.close();
        fileDescriptor.close();
    }


    private void showPage(int index) {
        if (pdfRenderer.getPageCount() <= index) {
            return;
        }
        // Make sure to close the current page before opening another one.
        if (null != currentPage) {
            currentPage.close();
        }
        //open a specific page in PDF file
        currentPage = pdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).
        bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(),
                Bitmap.Config.ARGB_8888);

        //  bitmap_viewpager.add(bitmap);


        // imageView.setImage(ImageSource.bitmap(bitmap));

        // Here, we render the page onto the Bitmap.
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        //  showing bitmap to an imageview
        imageView.setImage(ImageSource.bitmap(bitmap));

        // image.setImageBitmap(bitmap);
        updateUIData();
    }

    private void updateUIData() {
        int index = currentPage.getIndex();
        pagecount = pdfRenderer.getPageCount();
        //  Toast t = Toast.makeText(getActivity().getApplicationContext(), pagecount + "", Toast.LENGTH_SHORT);
        // t.show();
        // btn_previous_top.setEnabled(0 != index);
        // btn_nxt_top.setEnabled(index + 1 < pagecount);
        // getActivity().setTitle(getString(R.string.app_name, index + 1, pagecount));
    }

}
