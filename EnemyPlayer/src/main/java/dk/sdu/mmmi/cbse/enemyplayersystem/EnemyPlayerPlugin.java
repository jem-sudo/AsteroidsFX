package dk.sdu.mmmi.cbse.enemyplayersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlayerPlugin implements IGamePluginService {
    
    private Entity enemy;

    public EnemyPlayerPlugin() {
    }


    @Override
    public void start(GameData gameData, World world) {
        Entity enemy = makeNewEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity makeNewEnemy(GameData gameData) {
        Entity newEnemy = new EnemyPlayer();
        
        newEnemy.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        
        float positionX = gameData.getDisplayWidth() / 2.0f;
        float positionY = gameData.getDisplayHeight() / 4.0f;
        newEnemy.setX(positionX);
        newEnemy.setY(positionY);
        newEnemy.setRadius(8);
        return newEnemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}