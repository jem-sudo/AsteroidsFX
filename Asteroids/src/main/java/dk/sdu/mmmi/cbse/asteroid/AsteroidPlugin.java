package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private static final Random rnd = new Random();

    @Override
    public void start(GameData gameData, World world) {
        int asteroidCount = 7;
        for (int i = 0; i < asteroidCount; i++) {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    public static Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        int size = rnd.nextInt(15) + 16;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
        asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(360));
        return asteroid;
    }
}
