package com.fengmaster.game.crossoneline;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.texture.Texture;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import static javafx.scene.text.TextBoundsType.LOGICAL_VERTICAL_CENTER;

@Log
public class Block extends Entity implements EventHandler<Event> {

    /**
     * 目标划线数
     */
    private int crossNumberTarget;

    /**
     * 剩余划线数
     */
    private int crossNumberLeft;

    @Setter
    private boolean canCross=false;
    @Setter
    private boolean drawLineFlag=false;

    private Text text;

    @Getter
    private int cellX;

    @Getter
    private int cellY;

    private Texture blockTexture;



    public Block(int cellX,int cellY){
       this(cellX,cellY,0);
    }

    public Block(int cellX,int cellY,int crossNumberTarget){
        this.crossNumberTarget = crossNumberTarget;
        this.crossNumberLeft=crossNumberTarget;
        this.cellX=cellX;
        this.cellY=cellY;
        init();
    }

    private void init(){
        setTexture("obj/cubeBg.png");
        text = new Text("" + crossNumberTarget);
        text.setBoundsType(LOGICAL_VERTICAL_CENTER);
        text.setTextOrigin(VPos.CENTER);
        text.setX(25);
        text.setY(25);
        this.getViewComponent().addChild(text);
        this.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(50,50)));

        this.getViewComponent().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //开始
                canCross=true;
                FXGL.getEventBus().fireEvent(new BlockClickEvent(Block.this));
            }
        });

        FXGL.getEventBus().addEventHandler(BlockClickEvent.BLOCK_CLICK_EVENT, new EventHandler<BlockClickEvent>() {
            @Override
            public void handle(BlockClickEvent blockClickEvent) {
                drawLineFlag=!drawLineFlag;
                if (!drawLineFlag){
                    reset();
                }
            }
        });

        this.getViewComponent().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!drawLineFlag){
                    return;
                }

                if (canCross){
                    log.info("选择方块"+cellX+","+cellY);
                    addCrossNumberLeft(-1);
                    FXGL.getEventBus().fireEvent(new BlockCrossedEvent(Block.this));
                }else {
                    log.info("不能选择方块"+cellX+","+cellY);
                }

            }
        });

        FXGL.getEventBus().addEventHandler(BlockCanTouchEvent.BLOCK_CAN_TOUCH_EVENT,this);
        FXGL.getEventBus().addEventHandler(BlockCannotTouchEvent.BLOCK_CAN_NOT_TOUCH_EVENT,this);

    }

    private void addCrossNumberLeft(int n){
        crossNumberLeft+=n;
        updateTextAndImg();
    }

    private void updateTextAndImg(){
        text.setText(""+crossNumberLeft);
        if (canCross){
           blockTexture.setBlendMode(BlendMode.RED);
        }else {
            blockTexture.setBlendMode(BlendMode.SRC_ATOP);


        }

    }



    public void setTexture(String texture) {
        blockTexture = FXGL.getAssetLoader().loadTexture(texture, 50, 50);
        this.getViewComponent().addChild(blockTexture);

    }

    public void reset(){
        crossNumberLeft=crossNumberTarget;
        canCross=false;
        updateTextAndImg();
    }

    @Override
    public void handle(Event blockTouchEvent) {
        if (blockTouchEvent.getEventType().equals(BlockCanTouchEvent.BLOCK_CAN_TOUCH_EVENT)){
            BlockCanTouchEvent blockCanTouchEvent= (BlockCanTouchEvent) blockTouchEvent;
            if (blockCanTouchEvent.getCellX()==cellX&&blockCanTouchEvent.getCellY()==cellY&&this.crossNumberLeft!=0){
                canCross=true;
                log.info("方块"+cellX+","+cellY+"确认可以连线");
            }
        }
        if (blockTouchEvent.getEventType().equals(BlockCannotTouchEvent.BLOCK_CAN_NOT_TOUCH_EVENT)){
            BlockCannotTouchEvent blockCannotTouchEvent= (BlockCannotTouchEvent) blockTouchEvent;
            if (blockCannotTouchEvent.getCellX()==cellX&&blockCannotTouchEvent.getCellY()==cellY){
                canCross=false;
                log.info("方块"+cellX+","+cellY+"确认不可以连线");
            }
        }


        updateTextAndImg();

    }


}
