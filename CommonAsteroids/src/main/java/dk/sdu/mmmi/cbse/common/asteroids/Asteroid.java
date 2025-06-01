package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
    private float radius;

    @Override
    public float getRadius() {
        return radius;
    }

    @Override
    public void setRadius(float radius) {
        this.radius = radius;
    }
}