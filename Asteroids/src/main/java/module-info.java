import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Asteroids {
    requires Core;
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.asteroid.AsteroidProcessor;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.asteroid.AsteroidRespawn;
    provides dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter with dk.sdu.mmmi.cbse.asteroid.AsteroidSplitterImplementation;
}