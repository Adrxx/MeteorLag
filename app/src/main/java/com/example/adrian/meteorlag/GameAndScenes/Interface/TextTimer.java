package com.example.adrian.meteorlag.GameAndScenes.Interface;

import android.util.Log;

import com.example.adrian.meteorlag.GameAndScenes.ResourcesController;

import org.andengine.entity.Entity;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

/**
 * Created by Adrian on 4/20/15.
 */
public class TextTimer extends Entity {

    public Text textTimer;
    public double secs = 0;
    public double mins = 0;

    public TextTimer(float pX, float pY, float pWidth, float pHeight) {
        super(pX, pY, pWidth, pHeight);

        this.textTimer = ResourcesController.getInstance().generateText("00:00",this.getWidth()/2,this.getHeight()/2,80,0xFFFFFFFF);

        this.attachChild(this.textTimer);
    }


    public void reset()
    {
        this.textTimer.setText("00:00");
    }

    public void updateTimeInSecs(float secs)
    {
        this.mins = Math.floor(secs/60.0);
        this.secs = secs - (60*this.mins);

        this.updateTime();

    }

    public void substractTime(float value)
    {
        this.secs -= value;

        if (this.secs <= 0)
        {
            this.secs = 60;
            this.mins -= 1;
        }

        this.updateTime();
    }

    public void addTime(float value) {
        this.secs += value;

        if (this.secs > 59)
        {
            this.secs = 0;
            this.mins += 1;
        }

    }

    private void updateTime() {

        Double dm = Math.floor(this.mins);
        String m = String.format("%01d", dm.intValue() );

        Double ds = Math.floor(this.secs);
        String s = String.format("%02d", ds.intValue() );


        this.textTimer.setText(m+":"+s);
    }

}
