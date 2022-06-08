package com.fengmaster.game.crossoneline;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class BlockCannotTouchEvent extends Event{

    public static EventType<BlockCannotTouchEvent> BLOCK_CAN_NOT_TOUCH_EVENT =new EventType<>(EventType.ROOT,"BlockCannotTouchEvent");

    @Getter
    private int cellX;
    @Getter
    private int cellY;


    public BlockCannotTouchEvent(int cellX, int cellY) {
        super(BLOCK_CAN_NOT_TOUCH_EVENT);
        this.cellX=cellX;
        this.cellY=cellY;
    }
}
