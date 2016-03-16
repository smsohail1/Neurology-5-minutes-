package com.example.appxone.fiveminuteneurolog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import android.content.IntentFilter;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.IOException;

/**
 * Created by APPXONE on 3/14/2016.
 */
public class PDFRenderFragment extends Fragment {
    private ParcelFileDescriptor fileDescriptor;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ImageView image;
    private EditText btnPrevious;
    private Button btnNext;
    private Button btn_previous_top, btn_nxt_top;

    private ZoomControls zoom;


    //  private Matrix matrix = new Matrix();
    //  private float scale = 1f;
    private ScaleGestureDetector SGD;

    LayoutInflater inflater;


    private ScaleGestureDetector mScaleDetector;


    private Matrix matrix = new Matrix();
    private float scale = 1f;
    private ScaleGestureDetector scaleGestureDetector;
    SubsamplingScaleImageView imageView;
    Toast t1,t2;

    int w,h;
    int pagecount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pdfrender, container, false);
    }





    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Retain view references.
      //  image = (ImageView) view.findViewById(R.id.image);

        btnPrevious = (EditText) view.findViewById(R.id.edittext);
        btnNext = (Button) view.findViewById(R.id.btn_go);
        zoom = (ZoomControls) view.findViewById(R.id.zoomControls);

w=100;
        h=100;
        btn_previous_top = (Button) view.findViewById(R.id.btn_previous);
        btn_nxt_top = (Button) view.findViewById(R.id.btn_next1);


        //set buttons event
        btn_nxt_top.setOnClickListener(onActionListener_click_next(1)); //previous button clicked
        btn_previous_top.setOnClickListener(onActionListener_click_next(-1));


        btnNext.setOnClickListener(onActionListener_click(1)); //next button clicked

        imageView = (SubsamplingScaleImageView)view.findViewById(R.id.image);


        // SGD = new ScaleGestureDetector(getActivity().getApplicationContext(),new ScaleListener());


        int index = 0;
        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        if (null != savedInstanceState) {
            index = savedInstanceState.getInt("current_page", 0);
        }



//        btnPrevious.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        zoom.setOnZoomInClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                float x = imageView.getScaleX();
                float y = imageView.getScaleY();



                imageView.setScaleX((float) (x + 0.1));
              imageView.setScaleY((float) (y + 0.1));

//
//                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                p.addRule(RelativeLayout.BELOW, R.id.header);
//
//                imageView.setLayoutParams(p);
//
//                RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                p1.addRule(RelativeLayout.ABOVE, R.id.footer);
//
//                imageView.setLayoutParams(p1);

                imageView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT/2;
                imageView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT/2;
                imageView.requestLayout();
            }
        });

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                float x = imageView.getScaleX();
                float y = imageView.getScaleY();

                imageView.setScaleX((float) (x - 0.1));
                imageView.setScaleY((float) (y - 0.1));


            }
        });

        showPage(index);


//        btnPrevious.setFocusableInTouchMode(true);
//        btnPrevious.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

//        final InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
//                .getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputMethodManager.showSoftInput(btnPrevious, InputMethodManager.SHOW_IMPLICIT);




//        btnPrevious.post(new Runnable() {
//            public void run() {
//                btnPrevious.requestFocusFromTouch();
//                InputMethodManager lManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                lManager.showSoftInput(btnPrevious, 0);
//            }
//        });

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

    /**
     * Create a PDF renderer
     *
     * @param activity
     * @throws IOException
     */
    private void openRenderer(Activity activity) throws IOException {
        // Reading a PDF file from the assets directory.
        fileDescriptor = activity.getAssets().openFd("5 Minute Neurology Consult - 2nd Edition.pdf").getParcelFileDescriptor();

        // This is the PdfRenderer we use to render the PDF.
        pdfRenderer = new PdfRenderer(fileDescriptor);
    }

    /**
     * Closes PdfRenderer and related resources.
     */
    private void closeRenderer() throws IOException {
        if (null != currentPage) {
            currentPage.close();
        }
        pdfRenderer.close();
        fileDescriptor.close();
    }

    /**
     * Shows the specified page of PDF file to screen
     *
     * @param index The page index.
     */
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
        Bitmap bitmap = Bitmap.createBitmap(currentPage.getWidth(), currentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        // Here, we render the page onto the Bitmap.
        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // showing bitmap to an imageview
        imageView.setImage(ImageSource.bitmap(bitmap));

      //  image.setImageBitmap(bitmap);
        updateUIData();
    }

    /**
     * Updates the state of 2 control buttons in response to the current page index.
     */
    private void updateUIData() {
        int index = currentPage.getIndex();
        pagecount = pdfRenderer.getPageCount();
//        Toast t = Toast.makeText(getActivity().getApplicationContext(), pagecount + "", Toast.LENGTH_SHORT);
//        t.show();
        btn_previous_top.setEnabled(0 != index);
        btn_nxt_top.setEnabled(index + 1 < pagecount);
        getActivity().setTitle(getString(R.string.app_name, index + 1, pagecount));
    }

    private View.OnClickListener onActionListener_click(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String page_No = btnPrevious.getText().toString();

               // int p_n = Integer.parseInt(page_No);
              //  int p_n=   Integer.parseInt(page_No);
                if (page_No.equalsIgnoreCase("")) {
                     t1 = Toast.makeText(getActivity().getApplicationContext(), " Enter Page No", Toast.LENGTH_SHORT);
                    t1.show();
                }
                else if(Integer.parseInt(page_No) >484)
                {
                     t2 = Toast.makeText(getActivity().getApplicationContext(), "Invalid Page No", Toast.LENGTH_SHORT);
                    t2.show();
                }
                else {
                    int   p_n = Integer.parseInt(page_No);
                    showPage(p_n);
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }


            }
        };
    }


    private View.OnClickListener onActionListener_click_next(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (i < 0) {//go to previous page
                    if (currentPage.getIndex() != 0) {

                        showPage(currentPage.getIndex() - 1);
                    }
                } else {
                    showPage(currentPage.getIndex() + 1);
                }

            }
        };
    }


}
