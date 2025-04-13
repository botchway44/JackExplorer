package utilities;

import model.Entity;
import model.EnemyFighterTank;
import model.Enemy_GreenSlime;
import object.OBJ_Key;
import view.GamePanel;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void setObject() {
        this.gamePanel.gameObjects[0] = new OBJ_Key(gamePanel);
        this.gamePanel.gameObjects[0].worldX = 23 * gamePanel.tileSize;
        this.gamePanel.gameObjects[0].worldY = 7 * gamePanel.tileSize;




//        this.gamePanel.gameObjects[1] = new OBJ_Generic(gamePanel, "/objects/Brown_ruins1.png", "Brown RUINS", true, gamePanel.tileSize, gamePanel.tileSize);
//         this.gamePanel.gameObjects[1].worldX = 23 * gamePanel.tileSize;
//        this.gamePanel.gameObjects[1].worldY = 40 * gamePanel.tileSize;

//        this.gamePanel.gameObjects[2] = new OBJ_Generic("/objects/spr_dig.gif", "SPRITE DIG", true, gamePanel.tileSize, gamePanel.tileSize);
////		System.out.print("LOADED IMAGE" + this.gamePanel.gameObjects[1].image.toString());
//        this.gamePanel.gameObjects[2].worldX = 26 * gamePanel.tileSize;
//        this.gamePanel.gameObjects[2].worldY = 40 * gamePanel.tileSize;

//        this.gamePanel.gameObjects[2] = new OBJ_Generic(gamePanel,"/objects/player-down-1.png", Constants.DOOR, true, gamePanel.tileSize, gamePanel.tileSize);
//         this.gamePanel.gameObjects[2].worldX = 26 * gamePanel.tileSize;
//        this.gamePanel.gameObjects[2].worldY = 40 * gamePanel.tileSize;
//
//
//        this.gamePanel.gameObjects[3] = new OBJ_Generic(gamePanel,"/objects/Chest.png", Constants.CHEST, true, (int) (gamePanel.tileSize * 0.5), (int) (gamePanel.tileSize * 0.5));
//         this.gamePanel.gameObjects[3].worldX = 22 * gamePanel.tileSize;
//        this.gamePanel.gameObjects[3].worldY = 40 * gamePanel.tileSize;



    }

    public void setNPC(){
//        Entity oldMan = new NPCOldMan(gamePanel);
//        oldMan.worldX = 21 * gamePanel.tileSize;
//        oldMan.worldY = 21 * gamePanel.tileSize;
//        oldMan.setSpeed(1);
//        oldMan.setDirection(EntityDirection.DOWN); //Convert to enum as arguement
//        this.gamePanel.npcs[0] = oldMan;

//        Entity armor3 = new NPCOldMan(gamePanel);
//        armor3.worldX = 25 * gamePanel.tileSize;
//        armor3.worldY = 21 * gamePanel.tileSize;
//        armor3.setSpeed(1);
//
//        armor3.setDirection(EntityDirection.DOWN); //Convert to enum as arguement
//        this.gamePanel.npcs.put(Entities.ARMOR_CAR_3, armor3);
//
//        Entity armor4 = new NPCOldMan(gamePanel);
//        armor4.worldX = 15 * gamePanel.tileSize;
//        armor4.worldY = 21 * gamePanel.tileSize;
//        armor4.setSpeed(1);
//        armor4.setDirection(EntityDirection.DOWN); //Convert to enum as arguement
//        this.gamePanel.npcs.put(Entities.ARMOR_CAR_4, armor4);
//
//        Entity armor2 = new NPCOldMan(gamePanel);
//        armor2.worldX = 30 * gamePanel.tileSize;
//        armor2.worldY = 21 * gamePanel.tileSize;
//        armor2.setSpeed(1);
//        armor2.setDirection(EntityDirection.DOWN); //Convert to enum as arguement
//        this.gamePanel.npcs.put(Entities.ARMOR_CAR_2, armor2);
    }

    public void setMonsters(){

        for (int i = 0; i < 5; i++) {
            Entity monster = null;
            if(i % 2 == 0){
                monster = new EnemyFighterTank(gamePanel);
            }else{
                monster = new Enemy_GreenSlime(gamePanel);
            }
            monster.worldX = 23 * gamePanel.tileSize;
            monster.worldY = (40-i) * gamePanel.tileSize;
            gamePanel.monsters[i] = monster;
        }



    }
}
