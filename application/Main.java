package application;
 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
/**
 * Filename: Main.java 
 * Project: Cheese-No-Consequences 
 * 
 * Main controls the GUI created with Java FX
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {

    	homeScreen(primaryStage);
    }
    
    void homeScreen(Stage primaryStage) {
    	//Changes, ahsdklfsa;dklfj
    	/*
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);

 Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    	*/
    	
    	
    }
    
    void reportScreen(Stage primaryStage) {
    	
    	
    }
    
 public static void main(String[] args) {
        launch(args);
    }
}