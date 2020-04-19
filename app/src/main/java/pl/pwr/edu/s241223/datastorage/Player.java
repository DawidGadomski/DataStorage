package pl.pwr.edu.s241223.datastorage;

import android.graphics.Canvas;
import android.graphics.Color;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int color;
    private int points;
    private boolean block;
    private int[] colors;

    public Player(String name, int color) {

        colors = new int[]{Color.MAGENTA, Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN};

        this.name = name;
        this.color = colors[color];
        this.points = 0;
        this.block = false;
    }

    public void score(int points){
        this.points += points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    @Override
    public String toString(){
        return "Player " + this.name;
    }
}
