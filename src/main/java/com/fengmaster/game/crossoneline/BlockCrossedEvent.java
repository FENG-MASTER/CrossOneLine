package com.fengmaster.game.crossoneline;

import javafx.event.Event;
import javafx.event.EventType;
import lombok.Getter;

public class BlockCrossedEvent  extends javafx.event.Event{

    public static EventType<BlockCrossedEvent> BLOCK_CROSSED_EVENT =new EventType<>(EventType.ROOT,"BlockCrossedEvent");

    @Getter
    private Block block;


    public BlockCrossedEvent(Block block){
        super(BLOCK_CROSSED_EVENT);
        this.block=block;
    }

}
