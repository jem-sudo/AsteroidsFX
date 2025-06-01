package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.main.Score;

public class AsteroidSplitterImplementation implements IAsteroidSplitter {
    private static final float MIN_SIZE = 15f;

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        Asteroid asteroid = (Asteroid) e;
        world.removeEntity(asteroid);

        float size = asteroid.getRadius();
        
        if (size > MIN_SIZE) {
            float smallerSize = size / 2;
            
            Asteroid piece1 = makeSmallAsteroid(asteroid, smallerSize);
            Asteroid piece2 = makeSmallAsteroid(asteroid, smallerSize);
            
            world.addEntity(piece1);
            world.addEntity(piece2);
        } else {
            Score.incrementAsteroids();
            System.out.println("Score: " + Score.getDestroyedAsteroids());
        }
    }

    private Asteroid makeSmallAsteroid(Asteroid parent, float size) {
        Asteroid piece = new Asteroid();
        piece.setRadius(size);
        piece.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        piece.setX(parent.getX());
        piece.setY(parent.getY());
        piece.setRotation((float) (Math.random() * 360));
        return piece;
    }
}