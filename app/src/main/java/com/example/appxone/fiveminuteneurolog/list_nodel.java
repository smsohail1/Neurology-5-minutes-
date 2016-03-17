package com.example.appxone.fiveminuteneurolog;

/**
 * Created by APPXONE on 3/17/2016.
 */
public class list_nodel {

    //  private int caltulator_Image;

    private String count = "0";

    private String calcuator_Title;
    private int id, older_page, page_no, section_id;
    private boolean isCounterVisible = false;

    public list_nodel() {
    }

    public list_nodel(int id, String calcuator_Title, int older_page, int page_no, int section_id
    ) {

        // this.caltulator_Image = caltulator_Image;

        this.id = id;
        this.calcuator_Title = calcuator_Title;
        this.older_page = older_page;
        this.page_no = page_no;
        this.section_id = section_id;


    }


    public list_nodel(int id, String calcuator_Title, int older_page, int page_no, int section_id
            , boolean isCounterVisible, String count

    ) {

        //this.caltulator_Image = caltulator_Image;
        this.id = id;
        this.calcuator_Title = calcuator_Title;
        this.older_page = older_page;
        this.page_no = page_no;
        this.section_id = section_id;


        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }


    public String getCalcuator_Title() {
        return this.calcuator_Title;
    }
    public  int getId()
    {
        return  this.id;
    }

    public  int getPage_no()
    {
        return  this.page_no;
    }
    public  int getOlder_page()
    {
        return  this.older_page;
    }
    public  int getSection_id()
    {
        return  this.section_id;
    }


//    public int getImg() {
//        return this.caltulator_Image;
//    }

    public String getCount() {
        return this.count;
    }

    public boolean getCounterVisibility() {
        return this.isCounterVisible;
    }


    public void setCalcuator_Title(String title) {
        this.calcuator_Title = title;
    }

//    public void setImg(int img) {
//        this.caltulator_Image = img;
//    }


    public void setCount(String count) {
        this.count = count;
    }

    public void setCounterVisibility(boolean isCounterVisible) {
        this.isCounterVisible = isCounterVisible;
    }

}
