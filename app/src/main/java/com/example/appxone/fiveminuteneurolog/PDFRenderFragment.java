package com.example.appxone.fiveminuteneurolog;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pdfrender, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Retain view references.
        image = (ImageView) view.findViewById(R.id.image);
        btnPrevious = (EditText) view.findViewById(R.id.edittext);
        btnNext = (Button) view.findViewById(R.id.btn_next);
        zoom = (ZoomControls) view.findViewById(R.id.zoomControls);


       // btn_previous_top = (Button) view.findViewById(R.id.btn_previous);
       // btn_nxt_top = (Button) view.findViewById(R.id.btn_next1);

        //set buttons event
        //  btn_previous_top.setOnClickListener(onActionListener_click(-1)); //previous button clicked
        // btn_nxt_top.setOnClickListener(onActionListener_click(1));


        btnNext.setOnClickListener(onActionListener(1)); //next button clicked


        int index = 0;
        // If there is a savedInstanceState (screen orientations, etc.), we restore the page index.
        if (null != savedInstanceState) {
            index = savedInstanceState.getInt("current_page", 0);
        }
        showPage(index);


        zoom.setOnZoomInClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                float x = image.getScaleX();
                float y = image.getScaleY();

                image.setScaleX((float) (x + 0.1));
                image.setScaleY((float) (y + 0.1));
            }
        });

        zoom.setOnZoomOutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                float x = image.getScaleX();
                float y = image.getScaleY();

                image.setScaleX((float) (x - 0.1));
                image.setScaleY((float) (y - 0.1));
            }
        });
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
        image.setImageBitmap(bitmap);
        updateUIData();
    }

    /**
     * Updates the state of 2 control buttons in response to the current page index.
     */
    private void updateUIData() {
        int index = currentPage.getIndex();
        int pageCount = pdfRenderer.getPageCount();
        //  btnPrevious.setEnabled(0 != index);
        //  btnNext.setEnabled(index + 1 < pageCount);
        getActivity().setTitle(getString(R.string.app_name, index + 1, pageCount));
    }

    private View.OnClickListener onActionListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String page_No = btnPrevious.getText().toString();


//                if (i < 0) {//go to previous page
//                    showPage(currentPage.getIndex() - 1);
//                }
                //else {
                if (page_No.equalsIgnoreCase("")) {
                    Toast t = Toast.makeText(getActivity().getApplicationContext(), "enter page no", Toast.LENGTH_SHORT);
                    t.show();
                } else {
                    int p_n = Integer.parseInt(page_No);
                    showPage(p_n);
                }
                //}
            }
        };
    }


}
