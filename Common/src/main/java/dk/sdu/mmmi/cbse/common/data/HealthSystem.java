package dk.sdu.mmmi.cbse.common.data;

public interface HealthSystem {
    int getHealth();
    void setHealth(int health);
    int getMaxHealth();
    void setMaxHealth(int maxHealth);
    void takeDamage(int damage);
    boolean isDestroyed();
} 