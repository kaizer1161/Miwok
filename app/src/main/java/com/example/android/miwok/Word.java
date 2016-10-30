package com.example.android.miwok;

/**
 * Created by kaizer on 9/11/16.
 */
public class Word {

    private String engNumber;
    private String miwokNumber;
    private int item_sound;

    /** Image resource ID for the word */
    private int item_image = NO_IMAGE_PROVIDED;

    /**  */

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    /*
    *constructor to set English, Miwok, and Image.
    */
    public Word(String engNum, String miwokNum, int image, int sound){

        engNumber = engNum;
        miwokNumber = miwokNum;
        item_image = image;
        item_sound = sound;

    }

    /*
   *constructor to set English, Miwok word only.
   */
    public Word(String engNum, String miwokNum, int sound){

        engNumber = engNum;
        miwokNumber = miwokNum;
        item_sound = sound;

    }

    /*
    *Get English translation of the word
    */

    public String getEngWord(){

        return engNumber;

    }

    /*
    *Get Miwok translation of the word
    */
    public String getMiwokWord(){

        return miwokNumber;

    }

    public int getImageResourceId(){

        return item_image;

    }

    public int getSoundResourceId(){

        return item_sound;

    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return item_image != NO_IMAGE_PROVIDED;
    }

}
