package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.enemyplayersystem.EnemyPlayer;

import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    private IAsteroidSplitter getAsteroidSplitter() {
        ServiceLoader<IAsteroidSplitter> loader = ServiceLoader.load(IAsteroidSplitter.class);
        IAsteroidSplitter splitter = null;
        for (IAsteroidSplitter s : loader) {
            splitter = s;
        }
        return splitter;
    }

    @Override
    public void process(GameData gameData, World world) {
        IAsteroidSplitter splitter = getAsteroidSplitter();

        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                if (collides(entity1, entity2)) {
                    if (entity1 instanceof Bullet) {
                        handleBulletCollision(entity2, entity1, world, splitter);
                    } else if (entity2 instanceof Bullet) {
                        handleBulletCollision(entity1, entity2, world, splitter);
                    } else if (entity1 instanceof Asteroid) {
                        handleAsteroidCollision(entity1, entity2, world);
                    } else if (entity2 instanceof Asteroid) {
                        handleAsteroidCollision(entity2, entity1, world);
                    }
                }
            }
        }
    }

    private void handleBulletCollision(Entity target, Entity bullet, World world, IAsteroidSplitter splitter) {
        if (target instanceof Asteroid && splitter != null) {
            splitter.createSplitAsteroid(target, world);
            world.removeEntity(bullet);
        } else if (target instanceof Player) {
            Player player = (Player) target;
            player.takeDamage(1);
            world.removeEntity(bullet);
            
            if (player.isDestroyed()) {
                world.removeEntity(target);
            }
        } else if (target instanceof EnemyPlayer) {
            EnemyPlayer enemy = (EnemyPlayer) target;
            enemy.takeDamage(1);
            world.removeEntity(bullet);
            
            if (enemy.isDestroyed()) {
                world.removeEntity(target);
            }
        }
    }

    private void handleAsteroidCollision(Entity asteroid, Entity ship, World world) {
        if (ship instanceof Player) {
            Player player = (Player) ship;
            player.takeDamage(player.getHealth());
            if (player.isDestroyed()) {
                world.removeEntity(ship);
            }
            world.removeEntity(asteroid);
        } else if (ship instanceof EnemyPlayer) {
            EnemyPlayer enemy = (EnemyPlayer) ship;
            enemy.takeDamage(enemy.getHealth());
            if (enemy.isDestroyed()) {
                world.removeEntity(ship);
            }
            world.removeEntity(asteroid);
        }
    }

    private boolean collides(Entity e1, Entity e2) {
        double dx = e1.getX() - e2.getX();
        double dy = e1.getY() - e2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (e1.getRadius() + e2.getRadius());
    }
}