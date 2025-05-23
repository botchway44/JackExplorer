package tile;


import model.Entity;
import utilities.Entities;
import view.GamePanel;

import java.util.HashMap;

public class CollisionChecker {

    final GamePanel gamePanel;

    public CollisionChecker(GamePanel panel) {
        this.gamePanel = panel;
    }


    public void checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;

        int entityTopWorldY = entity.worldY + entity.solidArea.y;

        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;


        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;

        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;


        int tileNum1, tileNum2;

        switch (entity.direction) {

            case UP:
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];

                if (gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }

                break;

            case DOWN:
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;

                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                if (gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }

                break;
            case LEFT:
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];

                if (gamePanel.tileManager.tile[tileNum1].collision == true || gamePanel.tileManager.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                }

                break;

            case RIGHT:
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];

                // tile should be lessthan the size of the array
                if (gamePanel.tileManager.tile[tileNum1].collision || gamePanel.tileManager.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }

                break;


        }
    }


    public int checkObject(Entity entity, Boolean player) {
        int index = -1; // if the object is negative, that means we found nothing

        for (int i = 0; i < gamePanel.gameObjects.length; i++) {
            if (gamePanel.gameObjects[i] != null) {
                //Get entity's solid position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get the objects solid area position
                gamePanel.gameObjects[i].solidArea.x = gamePanel.gameObjects[i].worldX + gamePanel.gameObjects[i].solidArea.x;
                gamePanel.gameObjects[i].solidArea.y = gamePanel.gameObjects[i].worldY + gamePanel.gameObjects[i].solidArea.y;

                switch (entity.direction) {
                    case UP:
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            System.out.println("Collision Detected Up");

                            if (gamePanel.gameObjects[i].collision) {
                                entity.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case DOWN:
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            System.out.println("Collision Detected Down");

                            if (gamePanel.gameObjects[i].collision) {
                                entity.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case LEFT:
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            System.out.println("Collision Detected Left");

                            if (gamePanel.gameObjects[i].collision) {
                                entity.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case RIGHT:
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            System.out.println("Collision Detected Right");

                            if (gamePanel.gameObjects[i].collision) {
                                entity.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;

                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.gameObjects[i].solidArea.x = gamePanel.gameObjects[i].solidAreaDefaultX;
                gamePanel.gameObjects[i].solidArea.y = gamePanel.gameObjects[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public int checkNPCs(Entity entity, Boolean player) {
        int index = -1; // if the object is negative, that means we found nothing

        for (int i = 0; i < gamePanel.gameObjects.length; i++) {
            if (gamePanel.gameObjects[i] != null) {
                //Get entity's solid position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // get the objects solid area position
                gamePanel.gameObjects[i].solidArea.x = gamePanel.gameObjects[i].worldX + gamePanel.gameObjects[i].solidArea.x;
                gamePanel.gameObjects[i].solidArea.y = gamePanel.gameObjects[i].worldY + gamePanel.gameObjects[i].solidArea.y;

                switch (entity.direction) {
                    case UP:
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            System.out.println("Collision Detected Up");

                            if (gamePanel.gameObjects[i].collision) {
                                entity.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case DOWN:
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            System.out.println("Collision Detected Down");

                            if (gamePanel.gameObjects[i].collision) {
                                entity.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case LEFT:
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            System.out.println("Collision Detected Left");

                            if (gamePanel.gameObjects[i].collision) {
                                entity.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;
                    case RIGHT:
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.gameObjects[i].solidArea)) {
                            System.out.println("Collision Detected Right");

                            if (gamePanel.gameObjects[i].collision) {
                                entity.collisionOn = true;
                            }

                            if (player) {
                                index = i;
                            }
                        }
                        break;

                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.gameObjects[i].solidArea.x = gamePanel.gameObjects[i].solidAreaDefaultX;
                gamePanel.gameObjects[i].solidArea.y = gamePanel.gameObjects[i].solidAreaDefaultY;
            }
        }
        return index;
    }


    //not used now
    public Entities checkEntityCollision(Entity entity, HashMap<Entities, Entity> targets) {
        for (Entities e : targets.keySet()) {
                 //Get entity's solid position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                Entity target = targets.get(e);

                // get the objects solid area position
            target.solidArea.x = target.worldX + target.solidArea.x;
            target.solidArea.y = target.worldY + target.solidArea.y;

                switch (entity.direction) {
                    case UP:
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(target.solidArea)) {
                            System.out.println("Collision Detected Up");
                            entity.collisionOn = true;

                               return e;

                        }
                        break;
                    case DOWN:
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(target.solidArea)) {
                            System.out.println("Collision Detected Down");
                            entity.collisionOn = true;

                            return e;
                        }
                        break;
                    case LEFT:
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target.solidArea)) {
                            System.out.println("Collision Detected Left");
                            entity.collisionOn = true;

                            return e;
                         }
                        break;
                    case RIGHT:
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target.solidArea)) {
                            System.out.println("Collision Detected Right");
                            entity.collisionOn = true;

                            return e;
                         }
                        break;

                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target.solidArea.x = target.solidAreaDefaultX;
                target.solidArea.y = target.solidAreaDefaultY;
            }

        return null;
    }

    //check for NPC or monsters
    public int checkEntityCollision(Entity entity,Entity[] targets) {
        int index = -999;

        for (int i = 0; i < targets.length; i++) {
            if(targets[i] == null) continue;
            //Get entity's solid position
            entity.solidArea.x = entity.worldX + entity.solidArea.x;
            entity.solidArea.y = entity.worldY + entity.solidArea.y;

            Entity target = targets[i];

            // get the objects solid area position
            target.solidArea.x = target.worldX + target.solidArea.x;
            target.solidArea.y = target.worldY + target.solidArea.y;

            switch (entity.direction) {
                case UP:
                    entity.solidArea.y -= entity.speed;
//                    if (entity.solidArea.intersects(target.solidArea)) {
//                        System.out.println("Collision Detected Up");
//                        entity.collisionOn = true;
//                        return i;
//
//                    }
                    break;
                case DOWN:
                    entity.solidArea.y += entity.speed;
//                    if (entity.solidArea.intersects(target.solidArea)) {
//                        System.out.println("Collision Detected Down");
//                        entity.collisionOn = true;
//
//                        return i;
//                    }
                    break;
                case LEFT:
                    entity.solidArea.x -= entity.speed;
//                    if (entity.solidArea.intersects(target.solidArea)) {
//                        System.out.println("Collision Detected Left");
//                        entity.collisionOn = true;
//
//                        return i;
//                    }
                    break;
                case RIGHT:
                    entity.solidArea.x += entity.speed;
//                    if (entity.solidArea.intersects(target.solidArea)) {
//                        System.out.println("Collision Detected Right");
//                        entity.collisionOn = true;
//
//                        return i;
//                    }
                    break;

            }

            if (entity.solidArea.intersects(target.solidArea)) {
                if(target != entity){
                    entity.collisionOn = true;
                    index = i;
                }
            }

            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            target.solidArea.x = target.solidAreaDefaultX;
            target.solidArea.y = target.solidAreaDefaultY;
        }

        return index;
    }



    public boolean checkPlayerCollision(Entity entity) {

        boolean contactPlayer = false;

             //Get entity's solid position
            entity.solidArea.x = entity.worldX + entity.solidArea.x;
            entity.solidArea.y = entity.worldY + entity.solidArea.y;

            Entity target = this.gamePanel.player;

            // get the objects solid area position
            target.solidArea.x = target.worldX + target.solidArea.x;
            target.solidArea.y = target.worldY + target.solidArea.y;

            switch (entity.direction) {
                case UP:
                    entity.solidArea.y -= entity.speed;
//                    if (entity.solidArea.intersects(target.solidArea)) {
//                        System.out.println("Collision Detected Up");
//                        entity.collisionOn = true;
//
//                    }
                    break;
                case DOWN:
                    entity.solidArea.y += entity.speed;
//                    if (entity.solidArea.intersects(target.solidArea)) {
//                        System.out.println("Collision Detected Down");
//                        entity.collisionOn = true;
//                    }
                    break;
                case LEFT:
                    entity.solidArea.x -= entity.speed;
//                    if (entity.solidArea.intersects(target.solidArea)) {
//                        System.out.println("Collision Detected Left");
//                        entity.collisionOn = true;
//                    }
                    break;
                case RIGHT:
                    entity.solidArea.x += entity.speed;
//                    if (entity.solidArea.intersects(target.solidArea)) {
//                        System.out.println("Collision Detected Right");
//                        entity.collisionOn = true;
//                    }
                    break;

            }

        if (entity.solidArea.intersects(target.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            target.solidArea.x = target.solidAreaDefaultX;
            target.solidArea.y = target.solidAreaDefaultY;

            return contactPlayer;
    }
}

