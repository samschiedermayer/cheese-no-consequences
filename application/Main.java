package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Filename: Main.java Project: Cheese-No-Consequences
 * 
 * Main controls the GUI created with Java FX
 */
public class Main extends Application {

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 400;
	private static final String APP_TITLE = "Milk No Consequences";
	
	@Override
	public void start(Stage primaryStage) {

		homeScreen(primaryStage);
	}

	void homeScreen(Stage stage) {
    	try { 
    		
        	BorderPane root = new BorderPane();

        	VBox vbox = new VBox();
        	root.setCenter(vbox);
        	Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		           
            //Title and Header
            stage.setTitle(APP_TITLE); 
            Label titleLabel = new Label(APP_TITLE);
            titleLabel.setFont(new Font("Arial", 24));
            
            //Choose what type of report to make
            Label reportSelectLabel = new Label("Select What Type of Report to Generate:");
        	ComboBox<String> combo_box = new ComboBox<String>();
        	combo_box.getItems().addAll("Farm","Annual","Monthly","Date Range");
        	 Button selectReportButton = new Button("Select");
            
            //Select Files label, button, and file chooser
            FileChooser file_chooser = new FileChooser(); 
            Label selectFilesLabel = new Label("no files selected"); 
            Button selectFileButton = new Button("Select File"); 
      
            // Event Handler for Report Selections Selections
            EventHandler<ActionEvent> confirmReport =  new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                	switch(combo_box.getValue()) 
                    { 
                        case "Farm": 
                            System.out.println("one"); 
                            break; 
                        case "Annual": 
                            System.out.println("two"); 
                            break; 
                        case "Monthly": 
                            System.out.println("three"); 
                            break; 
                        case "Date Range": 
                            System.out.println("three"); 
                            break; 
 
                    } 
                } 
            }; 
            selectReportButton.setOnAction(confirmReport);
            
            // Event Handler for file Selections
            EventHandler<ActionEvent> selectFile =  new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    File file = file_chooser.showOpenDialog(stage); 
                    if (file != null) { 
                        selectFilesLabel.setText(file.getAbsolutePath()); 
                    } 
                } 
            }; 
      
            //set the action of the button
            selectFileButton.setOnAction(selectFile); 
            
            //add children to vbox
            vbox.getChildren().add(titleLabel);
            vbox.getChildren().add(reportSelectLabel);
            vbox.getChildren().add(combo_box);
            vbox.getChildren().add(selectReportButton);
            vbox.getChildren().add(selectFilesLabel);
            vbox.getChildren().add(selectFileButton);
            
            //set the scene and start the show
        	stage.setScene(mainScene);
        	stage.show();
        } 
      
        catch (Exception e) { 
      
            System.out.println(e.getMessage()); 
        } 
    }

	void reportScreen(Stage primaryStage) {
		// test git push
		// another test git push

	}

	public static void main(String[] args) {
		launch(args);
	}
}