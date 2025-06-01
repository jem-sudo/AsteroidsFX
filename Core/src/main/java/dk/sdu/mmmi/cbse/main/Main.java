package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {
    @Override
    public void start(Stage window) throws Exception {
      
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ModuleConfig.class);
        
        
        Game game = context.getBean(Game.class);
        game.start(window);
        game.render();
    }

    public static void main(String[] args) {
        launch(Main.class);
    }
}