package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        Entity player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {
        Entity newPlayer = new Player();
        newPlayer.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        float middleX = gameData.getDisplayHeight() / 2;
        float middleY = gameData.getDisplayWidth() / 2;
        newPlayer.setX(middleX);
        newPlayer.setY(middleY);
        newPlayer.setRadius(8);        
        return newPlayer;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
}
