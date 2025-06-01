package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidProcessor implements IEntityProcessingService {
    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImplementation();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            updateAsteroidPosition(asteroid, gameData);
        }
    }

    private void updateAsteroidPosition(Entity asteroid, GameData gameData) {
        double angle = Math.toRadians(asteroid.getRotation());
        double speed = 0.5;
        asteroid.setX(asteroid.getX() + Math.cos(angle) * speed);
        asteroid.setY(asteroid.getY() + Math.sin(angle) * speed);

        wrapPosition(asteroid, gameData);
    }

    private void wrapPosition(Entity asteroid, GameData gameData) {
        double x = asteroid.getX();
        double y = asteroid.getY();
        int width = gameData.getDisplayWidth();
        int height = gameData.getDisplayHeight();

        
        if (x < 0) x = x - width;
        if (x > width) x = x % width;
        
        
        if (y < 0) y = y - height;
        if (y > height) y = y % height;

        asteroid.setX(x);
        asteroid.setY(y);
    }

    public void setAsteroidSplitter(IAsteroidSplitter splitter) {
        this.asteroidSplitter = splitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter splitter) {
        this.asteroidSplitter = null;
    }
}