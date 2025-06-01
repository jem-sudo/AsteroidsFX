import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module EnemyPlayer {
    requires javafx.graphics;
    requires Common;
    exports dk.sdu.mmmi.cbse.enemyplayersystem;
    requires CommonBullet;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides IGamePluginService with dk.sdu.mmmi.cbse.enemyplayersystem.EnemyPlayerPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.enemyplayersystem.EnemyPlayerControlSystem;
}