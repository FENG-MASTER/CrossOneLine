package com.fengmaster.game.crossoneline;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.input.MouseEventData;
import com.almasb.fxgl.input.Trigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.input.UserAction;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


@Log
public class COLGameApplication extends GameApplication implements EventHandler<BlockCrossedEvent> {


    private int[][] riddles;

    private List<Entity> entities;


    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(720);
        gameSettings.setHeight(480);
        gameSettings.setTitle("一线生机");
//        gameSettings.setDefaultLanguage(Language.CHINESE);
        gameSettings.setVersion("0.1");

    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new BaseBlockSpawnFactory());
        riddles = new int[][]{
                {1, 1, 1, 0, 0},
                {2, 2, 2, 1, 1},
                {2, 2, 1, 0, 1},
                {1, 2, 2, 1, 1},
                {1, 2, 1, 0, 0}};
        entities = new ArrayList<>();

        for (int i = 0; i < riddles.length; i++) {
            for (int j = 0; j < riddles[i].length; j++) {
                SpawnData data = new SpawnData(i * 50, j * 50);
                data.put("crossNumber", riddles[i][j]);
                data.put("cellX", i);
                data.put("cellY", j);

                entities.add(FXGL.spawn("block", data));
            }
        }

        FXGL.getEventBus().addEventHandler(BlockCrossedEvent.BLOCK_CROSSED_EVENT, this);


    }

    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
//        log.info("z:"+currentZ);
    }

    @Override
    protected void initInput() {

        FXGL.getInput().addAction(new UserAction("start") {
            @Override
            protected void onActionBegin() {
                entities.stream().map(entity -> (Block) entity).forEach(new Consumer<Block>() {
                    @Override
                    public void accept(Block block) {
                        block.setCanCross(true);
                    }
                });
                log.info("开始画线");

            }

            @Override
            protected void onAction() {

            }

            @Override
            protected void onActionEnd() {
                entities.stream().map(entity -> (Block) entity).forEach(Block::reset);
                log.info("结束画线");
            }
        }, MouseButton.PRIMARY);


    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(BlockCrossedEvent blockCrossedEvent) {
        int cellX = blockCrossedEvent.getBlock().getCellX();
        int cellY = blockCrossedEvent.getBlock().getCellY();

        if (cellX - 1 >= 0&&cellY>=0 &&cellX-1<5&&cellY<5) {
            FXGL.getEventBus().fireEvent(new BlockCanTouchEvent(cellX-1,cellY));
        }
        if (cellX + 1 >= 0&&cellY>=0 &&cellX+1<5&&cellY<5) {
            FXGL.getEventBus().fireEvent(new BlockCanTouchEvent(cellX+1,cellY));
        }
        if (cellX >= 0&&cellY-1>=0 &&cellX<5&&cellY-1<5) {
            FXGL.getEventBus().fireEvent(new BlockCanTouchEvent(cellX,cellY-1));
        }
        if (cellX  >= 0&&cellY+1>=0 &&cellX<5&&cellY+1<5) {
            FXGL.getEventBus().fireEvent(new BlockCanTouchEvent(cellX,cellY+1));
        }
    }
}
