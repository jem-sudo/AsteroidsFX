package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.enemyplayersystem.EnemyPlayer;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Game {
    private GameData gameData = new GameData();
    private World world = new World();
    private Map<Entity, Polygon> shapes = new ConcurrentHashMap<>();
    private Pane gameScreen = new Pane();
    private List<IGamePluginService> pluginServices;
    private List<IEntityProcessingService> entityServices;
    private List<IPostEntityProcessingService> postServices;
    private long lastScore = 0;
    private static final long SCORE_DELAY = 500;
    private Text scoreDisplay;

    public Game(List<IGamePluginService> plugins, List<IEntityProcessingService> entityServices, List<IPostEntityProcessingService> postServices) {
        this.pluginServices = plugins;
        this.entityServices = entityServices;
        this.postServices = postServices;
    }

    public void start(Stage window) throws Exception {
        gameScreen.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        
        Image background = new Image(getClass().getResourceAsStream("/images/space_background.png"));
        ImageView bgView = new ImageView(background);
        bgView.setFitWidth(gameData.getDisplayWidth());
        bgView.setFitHeight(gameData.getDisplayHeight());
        gameScreen.getChildren().add(bgView);
        
        scoreDisplay = new Text(10, 20, "Destroyed asteroids: 0");
        scoreDisplay.setFill(Color.YELLOW);
        gameScreen.getChildren().add(scoreDisplay);

        Scene scene = new Scene(gameScreen);
        setupKeyHandling(scene);

        for (IGamePluginService plugin : pluginServices) {
            plugin.start(gameData, world);
        }

        for (Entity entity : world.getEntities()) {
            addEntityToGame(entity);
        }

        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    private void setupKeyHandling(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode() == KeyCode.RIGHT) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode() == KeyCode.UP) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode() == KeyCode.SPACE) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode() == KeyCode.RIGHT) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode() == KeyCode.UP) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode() == KeyCode.SPACE) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
        });
    }

    public void render() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGame();
                drawGame();
                gameData.getKeys().update();
            }
        };
        gameLoop.start();
    }

    private void updateGame() {
        for (IEntityProcessingService service : entityServices) {
            service.process(gameData, world);
        }
        for (IPostEntityProcessingService service : postServices) {
            service.process(gameData, world);
        }
    }

    private void drawGame() {
        removeDeadEntities();
        updateEntities();
        updateScore();
    }

    private void removeDeadEntities() {
        for (Entity entity : shapes.keySet()) {
            if (!world.getEntities().contains(entity)) {
                Polygon shape = shapes.get(entity);
                shapes.remove(entity);
                gameScreen.getChildren().remove(shape);
            }
        }
    }

    private void updateEntities() {
        for (Entity entity : world.getEntities()) {
            if (!shapes.containsKey(entity)) {
                addEntityToGame(entity);
            }
            Polygon shape = shapes.get(entity);
            shape.setTranslateX(entity.getX());
            shape.setTranslateY(entity.getY());
            shape.setRotate(entity.getRotation());

            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (player.isHit()) {
                    shape.setFill(Color.RED);
                    shape.setStroke(Color.RED);
                } else {
                    shape.setFill(Color.GREEN);
                    shape.setStroke(Color.GREEN);
                }
            } else if (entity instanceof EnemyPlayer) {
                EnemyPlayer enemy = (EnemyPlayer) entity;
                if (enemy.isHit()) {
                    shape.setFill(Color.YELLOW);
                    shape.setStroke(Color.YELLOW);
                } else {
                    shape.setFill(Color.RED);
                    shape.setStroke(Color.RED);
                }
            }
        }
    }

    private void addEntityToGame(Entity entity) {
        Polygon shape = new Polygon(entity.getPolygonCoordinates());
        if (entity instanceof EnemyPlayer) {
            shape.setFill(Color.RED);
            shape.setStroke(Color.RED);
        } else if (entity instanceof Player) {
            shape.setFill(Color.GREEN);
            shape.setStroke(Color.GREEN);
        } else {
            shape.setFill(Color.WHITE);
            shape.setStroke(Color.WHITE);
        }
        shapes.put(entity, shape);
        gameScreen.getChildren().add(shape);
    }

    private void updateScore() {
        long now = System.currentTimeMillis();
        if (now - lastScore >= SCORE_DELAY) {
            scoreDisplay.setText("Destroyed asteroids: " + Score.getDestroyedAsteroids());
            lastScore = now;
        }
    }

    public List<IGamePluginService> getGamePluginServices() {
        return pluginServices;
    }

    public List<IEntityProcessingService> getEntityProcessingServices() {
        return entityServices;
    }

    public List<IPostEntityProcessingService> getPostEntityProcessingServices() {
        return postServices;
    }
}