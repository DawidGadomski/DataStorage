package pl.pwr.edu.s241223.datastorage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class BoardComp extends View {

    private ArrayList<Segment> segments;
    private Paint playerPaint;
    private Paint textPaint;
    private RectF boardOval;
    private int compWidth, compHeight;



    public BoardComp(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set){
        playerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        playerPaint.setStyle(Paint.Style.FILL);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100);


    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld)
    {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        compWidth = getWidth();
        compHeight = getHeight();
        boardOval = new RectF(5*compWidth/100f,25*compHeight/100f,
                compWidth - (5*compWidth/100f),90*compWidth/100f + 25*compHeight/100f);
        for(Segment segment : segments){
            segment.setOval(boardOval);
        }

    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        for(Segment segment : this.segments){
            segment.drawSegment(canvas);
        }


    }

    public void setSegments(ArrayList<Segment> segments){
        this.segments = segments;
    }
}

