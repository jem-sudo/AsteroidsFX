package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class AsteroidRespawn implements IPostEntityProcessingService {

    private int oldAsteroidCount = 0;

    @Override
    public void process(GameData gameData, World world) {
        int asteroidCount = world.getEntities(Asteroid.class).size();

        if (asteroidCount < oldAsteroidCount) {
            Entity asteroid = AsteroidPlugin.createAsteroid(gameData);
            world.addEntity(asteroid);
        }

        if (asteroidCount == 0) {
            for (int i = 0; i < 3; i++) {
                Entity asteroid = AsteroidPlugin.createAsteroid(gameData);
                world.addEntity(asteroid);
            }
        }

        oldAsteroidCount = world.getEntities(Asteroid.class).size();
    }
}