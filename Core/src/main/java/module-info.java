module Core {
    requires Common;
    requires CommonBullet;
    requires Player;
    requires EnemyPlayer;
    requires javafx.graphics;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.web;
    exports dk.sdu.mmmi.cbse.main;
    opens dk.sdu.mmmi.cbse.main to javafx.graphics,spring.core;
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
}


