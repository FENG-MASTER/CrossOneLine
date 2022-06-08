package com.fengmaster.game.crossoneline.base;

import javafx.scene.paint.Color;

import java.util.HashMap;

public class BlockColorMapper {

    public  static Color[] colors ={
            new Color(1,1,1,0),
            new Color(1,0,0,1),
            new Color(1,0.5,0,1),
            new Color(1,1,0,1),
            new Color(0.5,1,0,1),
            new Color(0,1,0,1),
            new Color(0,1,0.5,1),
            new Color(0,1,1,1),
            new Color(0,0.5,1,1),
            new Color(0,0,1,1),
            new Color(0.5,0,1,1),
            new Color(1,0,1,1),
            new Color(1,0,0.5,1),

    };



    public static Color getBlockColorByNumber(int number){
        return colors[number];
    }

}
