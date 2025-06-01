package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerMovementTest {
    private PlayerControlSystem playerControlSystem;
    private GameData gameData;
    private World world;
    private Player player;

    @BeforeEach
    void setUp() {
        playerControlSystem = new PlayerControlSystem();
        gameData = new GameData();
        world = new World();
        player = new Player();
        
        player.setX(100);
        player.setY(100);
        player.setRotation(0);
        
        world.addEntity(player);
    }

    @Test
    void testPlayerMovement() {
        
        double initialX = player.getX();
        
        
        gameData.getKeys().setKey(GameKeys.UP, true);
        
        playerControlSystem.process(gameData, world);
        
        assertTrue(player.getX() > initialX, "Player went the wrong way (Should have moved right)");
    }
} 