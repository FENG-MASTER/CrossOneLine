package com.fengmaster.game.crossoneline;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import static javafx.scene.text.TextBoundsType.LOGICAL_VERTICAL_CENTER;

@Log
public class Block extends Entity implements EventHandler<BlockCanTouchEvent> {

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

    private Text text;

    @Getter
    private int cellX;

    @Getter
    private int cellY;



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

        this.getViewComponent().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (canCross){
                    log.info("选择方块"+cellX+","+cellY);
                    addCrossNumberLeft(-1);
                    FXGL.getEventBus().fireEvent(new BlockCrossedEvent(Block.this));
                }else {
                    log.warning("不能选择方块"+cellX+","+cellY);
                    return;
                }
            }
        });

        FXGL.getEventBus().addEventHandler(BlockCanTouchEvent.BLOCK_CAN_TOUCH_EVENT,this);

    }

    private void addCrossNumberLeft(int n){
        crossNumberLeft+=n;
        updateText();
    }

    private void updateText(){
        text.setText(""+crossNumberLeft);
    }



    public void setTexture(String texture) {
        this.getViewComponent().addChild(FXGL.getAssetLoader().loadTexture(texture, 50, 50));

    }

    public void reset(){
        crossNumberLeft=crossNumberTarget;
        updateText();
    }

    @Override
    public void handle(BlockCanTouchEvent blockCanTouchEvent) {
        if (blockCanTouchEvent.getCellX()==cellX&&blockCanTouchEvent.getCellY()==cellY){
            canCross=true;
        }else {
            canCross=false;
        }
    }
}
