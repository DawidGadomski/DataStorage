package pl.pwr.edu.s241223.datastorage;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Segment {
    private int startAngle, sweepAngle;
    private int color;
    private boolean isClickable, isBlocked;
    private RectF oval;
    private Paint segmentPaint;
    private int quarter;
    private float whichDegreeUserToCalcSin;



    public Segment(int startAngle, int sweepAngle, int color){
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
        this.color = color;
        this.isClickable = false;
        this.isBlocked = false;
        this.segmentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    public void blockPlayer(){
        this.isBlocked = true;
    }
    public void unblockPlayer(){
        this.isBlocked = false;
    }

    public void drawSegment(Canvas canvas){
        if(!this.isBlocked){
            this.segmentPaint.setColor(this.color);
            canvas.drawArc(this.oval, this.startAngle, this.sweepAngle, true, segmentPaint);
        }

    }

    public boolean click(float posX, float posY){
        float x = posX - this.oval.width()/2f - this.oval.left;
        float y = posY - this.oval.height()/2f - this.oval.top;


        if (x < 0 && y > 0){
            this.quarter = 1;
            whichDegreeUserToCalcSin = Math.abs(x);
        }
        else if (x < 0 && y < 0){
            this.quarter = 2;
            whichDegreeUserToCalcSin = Math.abs(y);
        }
        else if (x > 0 && y < 0){
            this.quarter = 3;
            whichDegreeUserToCalcSin = x;
        }
        else {
            this.quarter = 0;
            whichDegreeUserToCalcSin = y;

        }

        if(this.isClickable){
            double R = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
            double pointDegreeSin = Math.toDegrees(Math.asin(whichDegreeUserToCalcSin/R)) + this.quarter * 90;
            if(pointDegreeSin > this.startAngle && pointDegreeSin < (this.startAngle + this.sweepAngle) ){
                if(this.oval.width()/2f > R){
                    return true;
                }
            }
        }
        return false;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    public RectF getOval() {
        return oval;
    }

    public void setOval(RectF oval) {
        this.oval = oval;
    }

    public Paint getSegmentPaint() {
        return segmentPaint;
    }

    public void setSegmentPaint(Paint segmentPaint) {
        this.segmentPaint = segmentPaint;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public float getWhichDegreeUserToCalcSin() {
        return whichDegreeUserToCalcSin;
    }

    public void setWhichDegreeUserToCalcSin(float whichDegreeUserToCalcSin) {
        this.whichDegreeUserToCalcSin = whichDegreeUserToCalcSin;
    }
}
