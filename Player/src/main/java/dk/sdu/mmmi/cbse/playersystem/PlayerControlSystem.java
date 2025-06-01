package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Collection;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            handlePlayerMovement(player, gameData);
            handlePlayerShooting(player, gameData, world);
            keepPlayerInBounds(player, gameData);
           
            keepPlayerInBounds(player, gameData);
        }
    }

    private void handlePlayerMovement(Entity player, GameData gameData) {
        if (gameData.getKeys().isDown(GameKeys.LEFT)) {
            player.setRotation(player.getRotation() - 5);
        }
        
        if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
            player.setRotation(player.getRotation() + 5);
        }
        
        if (gameData.getKeys().isDown(GameKeys.UP)) {
            double angleInRadians = Math.toRadians(player.getRotation());
            double moveX = Math.cos(angleInRadians);
            double moveY = Math.sin(angleInRadians);
            
            double newX = player.getX() + moveX;
            double newY = player.getY() + moveY;
            
            player.setX(newX);
            player.setY(newY);
        }
    }

    
    private void handlePlayerShooting(Entity player, GameData gameData, World world) {
        if (gameData.getKeys().isDown(GameKeys.SPACE)) {
            Collection<? extends BulletSPI> bulletServices = getBulletSPIs();
            
            if (!bulletServices.isEmpty()) {
                BulletSPI bulletService = bulletServices.iterator().next();
                Entity bullet = bulletService.createBullet(player, gameData);
                world.addEntity(bullet);
            }
        }
    }

    
    private void keepPlayerInBounds(Entity player, GameData gameData) {
        
        if (player.getX() < 0) {
            player.setX(1);
        }
        
        
        if (player.getX() > gameData.getDisplayWidth()) {
            player.setX(gameData.getDisplayWidth() - 1);
        }
        

        if (player.getY() < 0) {
            player.setY(1);
        }
        

        if (player.getY() > gameData.getDisplayHeight()) {
            player.setY(gameData.getDisplayHeight() - 1);
        }
    }

    
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader
            .load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}