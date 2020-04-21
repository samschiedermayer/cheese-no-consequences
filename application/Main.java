package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import backend.CheeseFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private static TextField yearTextField;
	private static TextField farmIdTextField;
	private static TextField monthTextField;
	private static TextField startMonthTextField;
	private static TextField startDayTextField;
	private static TextField startYearTextField;
	private static TextField endMonthTextField;
	private static TextField endDayTextField;
	private static TextField endYearTextField;
	private static CheeseFactory factory;
	
	@Override
	public void start(Stage primaryStage) {
		homeScreen(primaryStage);
	}

	void homeScreen(Stage stage) {
    	try { 
    		
        	BorderPane root = new BorderPane();

        	VBox mainvbox = new VBox();
        	root.setCenter(mainvbox);
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
            Button selectFileButton = new Button("Load .CSV"); 
      
            // Event Handler for Report Selections Selections
            EventHandler<ActionEvent> confirmReport =  new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                	VBox selectedReportvBox = new VBox();
                	Scene selectedReportScene = new Scene(selectedReportvBox, WINDOW_WIDTH, WINDOW_HEIGHT);
                	selectedReportvBox.getChildren().add(titleLabel);
                	Label selectedReportLabel; 
                	Label  yearLabel;
                	Label  monthLabel;
                	Label dayLabel;
                	
                	switch(combo_box.getValue()) 
                    { 
                        case "Farm": 
                        	selectedReportLabel = new Label("Farm Report");
                        	Label  farmIDLabel= new Label("Farm Id Number:");
                        	farmIdTextField = new TextField ();
                        	yearLabel= new Label("Enter Year:");
                        	yearTextField = new TextField ();
                        	Button generateFarmButton = new Button("Generate Farm Report");
                        	yearTextField.getText();
                        	selectedReportvBox.getChildren().add(selectedReportLabel);
                        	selectedReportvBox.getChildren().add(farmIDLabel);
                        	selectedReportvBox.getChildren().add(farmIdTextField);
                        	selectedReportvBox.getChildren().add(yearLabel);
                        	selectedReportvBox.getChildren().add(yearTextField);
                        	selectedReportvBox.getChildren().add(generateFarmButton);
                        	
                        	// Event Handler for file Selections
                            EventHandler<ActionEvent> callFarm =  new EventHandler<ActionEvent>() { 
                                public void handle(ActionEvent e) 
                                {	
                                	System.out.println("CallFarm("+ " " + farmIdTextField.getText() + ", " + yearTextField.getText() + ")");
                                	//farmReportScreen(Stage stage, String this.filePath, String farmIdField.getText(),String yearTextField.getText());
                                } 
                            }; 
                            generateFarmButton.setOnAction(callFarm);
                        	
                        	
                            break; 
                        case "Annual": 
                        	selectedReportLabel = new Label("Annual Report"); 
                        	yearLabel= new Label("Enter Year:");
                        	yearTextField = new TextField ();
                        	Button generateAnnualButton = new Button("Generate Farm Report");
                        	selectedReportvBox.getChildren().add(selectedReportLabel);
                        	selectedReportvBox.getChildren().add(yearLabel);
                        	selectedReportvBox.getChildren().add(yearTextField);
                        	selectedReportvBox.getChildren().add(generateAnnualButton);
                        	
                        	// Event Handler for file Selections
                            EventHandler<ActionEvent> callAnnual =  new EventHandler<ActionEvent>() { 
                                public void handle(ActionEvent e) 
                                {	
                                	System.out.println("CallAnnual("+" " + yearTextField.getText() + ")");
                                	//annualReportScreen(Stage stage, String this.filePath,String yearTextField.getText());
                                } 
                            }; 
                            generateAnnualButton.setOnAction(callAnnual);
                            break; 
                        case "Monthly": 
                        	selectedReportLabel = new Label("Monthly Report");
                        	yearLabel= new Label("Enter Year:");
                        	yearTextField = new TextField ();
                        	monthLabel= new Label("Enter Month:");
                        	monthTextField = new TextField();
                        	Button generateMonthlyButton = new Button("Generate Farm Report");
                        	selectedReportvBox.getChildren().add(selectedReportLabel);
                        	selectedReportvBox.getChildren().add(yearLabel);
                        	selectedReportvBox.getChildren().add(yearTextField);
                        	selectedReportvBox.getChildren().add(monthLabel);
                        	selectedReportvBox.getChildren().add(monthTextField);
                        	selectedReportvBox.getChildren().add(generateMonthlyButton);
                        	
                        	// Event Handler for file Selections
                            EventHandler<ActionEvent> callMonthly =  new EventHandler<ActionEvent>() { 
                                public void handle(ActionEvent e) 
                                {	
                                	System.out.println("CallMonthly("+" " + yearTextField.getText() + " " + monthTextField.getText() + ")");
                                	//monthlyReportScreen(Stage stage, String this.filePath,String yearTextField.getText());
                                } 
                            }; 
                            generateMonthlyButton.setOnAction(callMonthly);
                            break; 
                        case "Date Range": 
                        	selectedReportLabel = new Label("Date Range Report");
                        	Label startLabel= new Label("Enter Starting Date:");
                        	Label endLabel= new Label("Enter Ending Date:");
                        	Label monthLabel1= new Label("Enter Month:");
                        	Label dayLabel1= new Label("Enter Day:");
                        	Label yearLabel1= new Label("Enter Year:");
                        	endLabel= new Label("Enter Ending Date:");
                        	yearLabel= new Label("Enter Year:");
                        	dayLabel = new Label("Enter Day:");
                        	monthLabel = new Label ("Enter Month:");
                        	
                        	//Starting Date
                        	startDayTextField = new TextField(); 
                        	startYearTextField = new TextField();
                        	startMonthTextField = new TextField ();
                        	
                        	//Ending Date
                        	endMonthTextField = new TextField();
                        	endDayTextField = new TextField();
                        	endYearTextField = new TextField();
                        	
                        	Button generateDateRangeButton = new Button("Generate Farm Report");
                        	selectedReportvBox.getChildren().add(selectedReportLabel);
                        	
                        	//adding starting elements
                        	selectedReportvBox.getChildren().add(startLabel);
                        	selectedReportvBox.getChildren().add(monthLabel);
                        	selectedReportvBox.getChildren().add(startMonthTextField);
                        	selectedReportvBox.getChildren().add(dayLabel);
                        	selectedReportvBox.getChildren().add(startDayTextField);
                        	selectedReportvBox.getChildren().add(yearLabel);
                        	selectedReportvBox.getChildren().add(startYearTextField);
                        	
                        	//adding ending elements
                        	selectedReportvBox.getChildren().add(endLabel);
                        	selectedReportvBox.getChildren().add(monthLabel1);
                        	selectedReportvBox.getChildren().add(endMonthTextField);
                        	selectedReportvBox.getChildren().add(dayLabel1);
                        	selectedReportvBox.getChildren().add(endDayTextField);
                        	selectedReportvBox.getChildren().add(yearLabel1);
                        	selectedReportvBox.getChildren().add(endYearTextField);
                        	
                        	//generate button
                        	selectedReportvBox.getChildren().add(generateDateRangeButton);
                        	
                        	// Event Handler for file Selections
                            EventHandler<ActionEvent> callDateRange =  new EventHandler<ActionEvent>() { 
                                public void handle(ActionEvent e) 
                                {	
                                	System.out.println("CallDateRange("+ " Start Month: " + startMonthTextField.getText() + " Start Day: " + startDayTextField.getText() + " Start Year: " + startYearTextField.getText() + " End Month: " + endMonthTextField.getText() +  " End Day: " + endDayTextField.getText()+ " End Year: " + endYearTextField.getText() + ")");
                                	//DateRangeReportScreen(Stage stage, String this.filePath,String yearTextField.getText());
                                } 
                            }; 
                            generateDateRangeButton.setOnAction(callDateRange);
                            break; 
 
                    }
                	stage.setScene(selectedReportScene);
                	stage.show();
                } 
            }; 
            selectReportButton.setOnAction(confirmReport);
            
            // Event Handler for file Selections
            EventHandler<ActionEvent> selectFile =  new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    File file = file_chooser.showOpenDialog(stage); 
                    if (file != null) { 
                        
                    	try {
                    		factory.importFarmData(file.getAbsolutePath());
                    	}catch (Exception a) {
                    		a.printStackTrace();
                    		selectFilesLabel.setText("Loading File Failed Select a New File"); 
                    	}
                        
                    } 
                } 
            }; 
      
            //set the action of the button
            selectFileButton.setOnAction(selectFile); 
            
            //add children to vbox
            mainvbox.getChildren().add(titleLabel);
            mainvbox.getChildren().add(selectFilesLabel);
            mainvbox.getChildren().add(selectFileButton);
            mainvbox.getChildren().add(reportSelectLabel);
            mainvbox.getChildren().add(combo_box);
            mainvbox.getChildren().add(selectReportButton);
            
            
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
		factory = new CheeseFactory();
		launch(args);
	}
}