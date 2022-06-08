package com.fengmaster.game.crossoneline.event;

import com.fengmaster.game.crossoneline.Block;
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
