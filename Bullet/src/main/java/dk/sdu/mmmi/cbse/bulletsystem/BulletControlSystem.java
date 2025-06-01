package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {
    
    private static final int BULLET_SPEED = 3;
    private static final int SPAWN_DISTANCE = 10;
    
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            double bulletDirectionX = Math.cos(Math.toRadians(bullet.getRotation()));
            double bulletDirectionY = Math.sin(Math.toRadians(bullet.getRotation()));
            
            double newXPosition = bullet.getX() + (bulletDirectionX * BULLET_SPEED);
            double newYPosition = bullet.getY() + (bulletDirectionY * BULLET_SPEED);
            
            bullet.setX(newXPosition);
            bullet.setY(newYPosition);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        
        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1); 
        
       
        double spawnDirectionX = Math.cos(Math.toRadians(shooter.getRotation()));
        double spawnDirectionY = Math.sin(Math.toRadians(shooter.getRotation()));
        
        double spawnX = shooter.getX() + (spawnDirectionX * SPAWN_DISTANCE);
        double spawnY = shooter.getY() + (spawnDirectionY * SPAWN_DISTANCE);
        
        bullet.setX(spawnX);
        bullet.setY(spawnY);
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(1);
        
        return bullet;
    }
}