package utilities;

import model.Entity;
import model.NPCOldMan;
import object.OBJ_Generic;
import object.OBJ_Key;
import view.GamePanel;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void setObject() {
        this.gamePanel.gameObjects[0] = new OBJ_Key(gamePanel.tileSize, gamePanel.tileSize);
        this.gamePanel.gameObjects[0].worldX = 23 * gamePanel.tileSize;
        this.gamePanel.gameObjects[0].worldY = 7 * gamePanel.tileSize;


        this.gamePanel.gameObjects[1] = new OBJ_Generic("/objects/Brown_ruins1.png", "Brown RUINS", true, gamePanel.tileSize, gamePanel.tileSize);
//		System.out.print("LOADED IMAGE" + this.gamePanel.gameObjects[1].image.toString());
        this.gamePanel.gameObjects[1].worldX = 23 * gamePanel.tileSize;
        this.gamePanel.gameObjects[1].worldY = 40 * gamePanel.tileSize;

        this.gamePanel.gameObjects[2] = new OBJ_Generic("/objects/spr_dig.gif", "SPRITE DIG", true, gamePanel.tileSize, gamePanel.tileSize);
//		System.out.print("LOADED IMAGE" + this.gamePanel.gameObjects[1].image.toString());
        this.gamePanel.gameObjects[2].worldX = 26 * gamePanel.tileSize;
        this.gamePanel.gameObjects[2].worldY = 40 * gamePanel.tileSize;

        this.gamePanel.gameObjects[3] = new OBJ_Generic("/objects/spr_dig.gif", Constants.DOOR, true, gamePanel.tileSize, gamePanel.tileSize);
//		System.out.print("LOADED IMAGE" + this.gamePanel.gameObjects[1].image.toString());
        this.gamePanel.gameObjects[3].worldX = 26 * gamePanel.tileSize;
        this.gamePanel.gameObjects[3].worldY = 40 * gamePanel.tileSize;


        this.gamePanel.gameObjects[3] = new OBJ_Generic("/objects/boots.png", Constants.BOOTS, true, gamePanel.tileSize, gamePanel.tileSize);
//		System.out.print("LOADED IMAGE" + this.gamePanel.gameObjects[1].image.toString());
        this.gamePanel.gameObjects[3].worldX = 22 * gamePanel.tileSize;
        this.gamePanel.gameObjects[3].worldY = 40 * gamePanel.tileSize;
    }

    public void setNPC(){
        Entity oldMan = new NPCOldMan(gamePanel);
        oldMan.worldX = 21 * gamePanel.tileSize;
        oldMan.worldY = 21 * gamePanel.tileSize;
        oldMan.setSpeed(1);
        oldMan.setDirection(EntityDirection.DOWN); //Convert to enum as arguement
        this.gamePanel.npcs.put(Entities.OLD_MAN, oldMan);
    }
}
