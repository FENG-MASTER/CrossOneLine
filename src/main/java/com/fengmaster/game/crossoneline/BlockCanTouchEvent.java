package com.fengmaster.game.crossoneline;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class BlockCanTouchEvent extends javafx.event.Event{

    public static EventType<BlockCanTouchEvent> BLOCK_CAN_TOUCH_EVENT =new EventType<>(EventType.ROOT,"BlockCanTouchEvent");

    @Getter
    private int cellX;
    @Getter
    private int cellY;


    public BlockCanTouchEvent( int cellX, int cellY) {
        super(BLOCK_CAN_TOUCH_EVENT);
        this.cellX=cellX;
        this.cellY=cellY;
    }
}
