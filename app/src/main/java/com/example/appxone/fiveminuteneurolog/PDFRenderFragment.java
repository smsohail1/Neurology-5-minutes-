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
    Toast t1, t2;
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
        btnNext = (Button) view.findViewById(R.id.btn_next);
        zoom = (ZoomControls) view.findViewById(R.id.zoomControls);


        btn_previous_top = (Button) view.findViewById(R.id.btn_previous);
        btn_nxt_top = (Button) view.findViewById(R.id.btn_next1);


        //set buttons event
        btn_nxt_top.setOnClickListener(onActionListener_click_next(1)); //previous button clicked
        btn_previous_top.setOnClickListener(onActionListener_click_next(-1));


        btnNext.setOnClickListener(onActionListener_click(1)); //next button clicked

        imageView = (SubsamplingScaleImageView) view.findViewById(R.id.image);


        // SGD = new ScaleGestureDetector(getActivity().getApplicationContext(),new ScaleListener());


        int index = 0;
        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        if (null != savedInstanceState) {
            index = savedInstanceState.getInt("current_page", 0);
        }
        showPage(index);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInputFromInputMethod(view1.getWindowToken(), 0);
                }
            }
        });


//        mScaleDetector = new ScaleGestureDetector(getActivity().getApplicationContext(), new ScaleGestureDetector.OnScaleGestureListener() {
//            @Override
//            public void onScaleEnd(ScaleGestureDetector detector) {
//            }
//            @Override
//            public boolean onScaleBegin(ScaleGestureDetector detector) {
//                return true;
//            }
//            @Override
//            public boolean onScale(ScaleGestureDetector detector) {
//
//                scale *= detector.getScaleFactor();
//                scale = Math.max(0.1f, Math.min(scale, 5.0f));
//
//                matrix.setScale(scale, scale);
//                image.setImageMatrix(matrix);
//
//              //  Log.d(LOG_KEY, "zoom ongoing, scale: " + detector.getScaleFactor());
//                return false;
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
        //scaleGestureDetector = new ScaleGestureDetector(getActivity().getApplicationContext(), new ScaleListener());
    }

//    public boolean onTouchEvent(MotionEvent ev) {
//        super.getActivity();
//        scaleGestureDetector.onTouchEvent(ev);
//        return true;
//    }

//    private class ScaleListener extends
//            ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            scale *= detector.getScaleFactor();
//            scale = Math.max(0.1f, Math.min(scale, 5.0f));
//            matrix.setScale(scale, scale);
//            image.setImageMatrix(matrix);
//            return true;
//        }
//    }


//    private class ScaleListener extends ScaleGestureDetector.
//
//            SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            scale *= detector.getScaleFactor();
//            scale = Math.max(0.1f, Math.min(scale, 5.0f));
//
//            matrix.setScale(scale, scale);
//            image.setImageMatrix(matrix);
//            return true;
//        }
//    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


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

        btnPrevious.setEnabled(0 != index);
        btnNext.setEnabled(index + 1 < pagecount);
        getActivity().setTitle(getString(R.string.app_name, index + 1, pagecount));
    }

    private View.OnClickListener onActionListener_click(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String page_No = btnPrevious.getText().toString();


                if (page_No.equalsIgnoreCase("")) {
                    t1 = Toast.makeText(getActivity().getApplicationContext(), " Enter Page No", Toast.LENGTH_SHORT);
                    t1.show();
                } else if (Integer.parseInt(page_No) > 484) {
                    t2 = Toast.makeText(getActivity().getApplicationContext(), "Invalid Page No", Toast.LENGTH_SHORT);
                    t2.show();
                } else {
                    int p_n = Integer.parseInt(page_No);
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
