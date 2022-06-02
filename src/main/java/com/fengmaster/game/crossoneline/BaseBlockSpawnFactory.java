package com.fengmaster.game.crossoneline;

import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;

public class BaseBlockSpawnFactory implements EntityFactory {

    @Spawns("block")
    public Block newBlock(SpawnData spawnData){
        int cellX = Integer.parseInt(spawnData.get("cellX").toString());
        int cellY = Integer.parseInt(spawnData.get("cellY").toString());

        Block block = new Block(cellX,cellY,Integer.parseInt(spawnData.getData().get("crossNumber").toString()));
        block.setPosition(new Point2D(spawnData.getX(),spawnData.getY()));
        return block;
    }

}
