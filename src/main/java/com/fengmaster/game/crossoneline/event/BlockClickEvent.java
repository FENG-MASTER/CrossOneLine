package com.fengmaster.game.crossoneline.event;

import com.fengmaster.game.crossoneline.Block;
import javafx.event.EventType;
import lombok.Getter;

public class BlockClickEvent  extends javafx.event.Event{

    public static EventType<BlockClickEvent> BLOCK_CLICK_EVENT =new EventType<>(EventType.ROOT,"BlockClickEvent");

    @Getter
    private Block block;


    public BlockClickEvent(Block block){
        super(BLOCK_CLICK_EVENT);
        this.block=block;
    }
}
