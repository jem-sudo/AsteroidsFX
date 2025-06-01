package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Player extends Entity {
    private int health;
    private int maxHealth;
    private long lastHitTime;
    private boolean isHit;

    public Player() {
        this.maxHealth = 5;
        this.health = maxHealth;
        this.lastHitTime = 0;
        this.isHit = false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
        this.lastHitTime = System.currentTimeMillis();
        this.isHit = true;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public boolean isHit() {
        if (isHit && System.currentTimeMillis() - lastHitTime > 200) {
            isHit = false;
        }
        return isHit;
    }
}
