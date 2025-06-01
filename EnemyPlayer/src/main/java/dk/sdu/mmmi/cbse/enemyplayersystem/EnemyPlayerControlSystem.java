package dk.sdu.mmmi.cbse.enemyplayersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;

public class EnemyPlayerControlSystem implements IEntityProcessingService {
    private final Random random = new Random();
    private int moveCounter = 0;
    private int shootCounter = 0;
    private double direction = 0;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(EnemyPlayer.class)) {
            handleEnemyMovement(enemy);
            
            handleEnemyShooting(enemy, gameData, world);
            
            keepEnemyInBounds(enemy, gameData);
        }
    }

    private void handleEnemyMovement(Entity enemy) {
        if (moveCounter++ > 60) {
            direction = random.nextDouble() * 360;
            moveCounter = 0;
        }
        
        enemy.setRotation(direction);
        
        double angleInRadians = Math.toRadians(enemy.getRotation());
        double moveX = Math.cos(angleInRadians);
        double moveY = Math.sin(angleInRadians);
        
        double speedMultiplier = 1.5;
        double newX = enemy.getX() + (moveX * speedMultiplier);
        double newY = enemy.getY() + (moveY * speedMultiplier);
        
        enemy.setX(newX);
        enemy.setY(newY);
    }

    private void handleEnemyShooting(Entity enemy, GameData gameData, World world) {
        if (shootCounter++ > 40) {
            Collection<? extends BulletSPI> bulletServices = getBulletSPIs();
            
            if (!bulletServices.isEmpty()) {
                BulletSPI bulletService = bulletServices.iterator().next();
                Entity bullet = bulletService.createBullet(enemy, gameData);
                world.addEntity(bullet);
            }
            
            shootCounter = 0;
        }
    }

    private void keepEnemyInBounds(Entity enemy, GameData gameData) {
        if (enemy.getX() < 0) {
            enemy.setX(gameData.getDisplayWidth() - 1);
        }
        
        if (enemy.getX() > gameData.getDisplayWidth()) {
            enemy.setX(1);
        }
        
        if (enemy.getY() < 0) {
            enemy.setY(gameData.getDisplayHeight() - 1);
        }
        
        if (enemy.getY() > gameData.getDisplayHeight()) {
            enemy.setY(1);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}