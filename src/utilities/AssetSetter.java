package utilities;

import model.Entity;
import model.EnemyFighterTank;
import model.Enemy_GreenSlime;
import model.FighterShip;
import object.OBJ_Key;
import object.OBJ_Plane;
import object.OBJ_Ships;
import view.GamePanel;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void setObject() {
//        this.gamePanel.gameObjects[0] = new OBJ_Key(gamePanel);
//        this.gamePanel.gameObjects[0].worldX = 23 * gamePanel.tileSize;
//        this.gamePanel.gameObjects[0].worldY = 7 * gamePanel.tileSize;


        this.gamePanel.gameObjects[1] = new OBJ_Plane(gamePanel, PlaneType.FIGHTER);
        this.gamePanel.gameObjects[1].worldX = 47 * gamePanel.tileSize;
        this.gamePanel.gameObjects[1].worldY = 8 * gamePanel.tileSize;

        this.gamePanel.gameObjects[2] = new OBJ_Plane(gamePanel, PlaneType.SIMPLE);
        this.gamePanel.gameObjects[2].worldX = 49 * gamePanel.tileSize;
        this.gamePanel.gameObjects[2].worldY = 8 * gamePanel.tileSize;


        this.gamePanel.gameObjects[3] = new OBJ_Plane(gamePanel, PlaneType.ALIEN);
        this.gamePanel.gameObjects[3].worldX = 51 * gamePanel.tileSize;
        this.gamePanel.gameObjects[3].worldY = 8 * gamePanel.tileSize;


        this.gamePanel.gameObjects[4] = new OBJ_Plane(gamePanel, PlaneType.WAR);
        this.gamePanel.gameObjects[4].worldX = 53 * gamePanel.tileSize;
        this.gamePanel.gameObjects[4].worldY = 12 * gamePanel.tileSize;

        this.gamePanel.gameObjects[5] = new OBJ_Ships(gamePanel, ShipType.WAR);
        this.gamePanel.gameObjects[5].worldX = 12 * gamePanel.tileSize;
        this.gamePanel.gameObjects[5].worldY = 12 * gamePanel.tileSize;

        this.gamePanel.gameObjects[6] = new OBJ_Ships(gamePanel, ShipType.SIMPLE);
        this.gamePanel.gameObjects[6].worldX = 13 * gamePanel.tileSize;
        this.gamePanel.gameObjects[6].worldY = 12 * gamePanel.tileSize;

        this.gamePanel.gameObjects[7] = new OBJ_Ships(gamePanel, ShipType.ALIEN);
        this.gamePanel.gameObjects[7].worldX = 14 * gamePanel.tileSize;
        this.gamePanel.gameObjects[7].worldY = 12 * gamePanel.tileSize;

        this.gamePanel.gameObjects[8] = new OBJ_Ships(gamePanel, ShipType.FIGHTER);
        this.gamePanel.gameObjects[8].worldX = 15 * gamePanel.tileSize;
        this.gamePanel.gameObjects[8].worldY = 12 * gamePanel.tileSize;

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

        FighterShip ship = new FighterShip(gamePanel);
        ship.worldX = 10 * gamePanel.tileSize;
        ship.worldY = 12 * gamePanel.tileSize;
        this.gamePanel.npcs[0] = ship;


        FighterShip ship2 = new FighterShip(gamePanel);
        ship2.worldX = 10 * gamePanel.tileSize;
        ship2.worldY = 15 * gamePanel.tileSize;
        this.gamePanel.npcs[1] = ship2;


        FighterShip ship3 = new FighterShip(gamePanel);
        ship3.worldX = 10 * gamePanel.tileSize;
        ship3.worldY = 20 * gamePanel.tileSize;
        this.gamePanel.npcs[3] = ship3;

        FighterShip ship4 = new FighterShip(gamePanel);
        ship4.worldX = 60 * gamePanel.tileSize;
        ship4.worldY = 20 * gamePanel.tileSize;
        this.gamePanel.npcs[4] = ship4;

        FighterShip ship5 = new FighterShip(gamePanel);
        ship5.worldX = 60 * gamePanel.tileSize;
        ship5.worldY = 50 * gamePanel.tileSize;
        this.gamePanel.npcs[5] = ship5;

        FighterShip ship6 = new FighterShip(gamePanel);
        ship6.worldX = 20 * gamePanel.tileSize;
        ship6.worldY = 65 * gamePanel.tileSize;
        this.gamePanel.npcs[6] = ship6;


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
