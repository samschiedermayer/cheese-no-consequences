package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import backend.CheeseFactory;
import backend.Date;
import backend.Farm;
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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
	
    /**
     * Displays farm report screen
     * 
     * @param primaryStage
     */
    void farmReportScreen(Stage primaryStage) {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root, 1000, 800);
      scene.getStylesheets().add("cheese-factory.css");
      
      // report title and farm info
      GridPane top = new GridPane();
      top.setAlignment(Pos.CENTER);
      top.setPadding(new Insets(10, 10, 10, 10));
      Label titleLabel = new Label("Farm Report");
      
      // add title to top
      titleLabel.setId("report-title");
      top.add(titleLabel, 0, 0);
      GridPane.setHalignment(titleLabel, HPos.CENTER);
      
      // add report info to top      
      GridPane info = new GridPane();
      Label disFarmId = new Label("Farm: 1");
      Label year = new Label("Year: 2020");
      Label totalWeight = new Label("Total weight: 2107");
      Label percentTotal = new Label("Percentage of Total Farms: 20%");
      disFarmId.setId("report-info");
      year.setId("report-info");
      totalWeight.setId("report-info");
      percentTotal.setId("report-info");
      info.add(disFarmId, 0, 0);
      info.add(year, 0, 1);
      info.add(totalWeight, 1, 0);
      info.add(percentTotal, 1, 1);
      top.add(info, 0, 1);
      info.setHgap(20);
      GridPane.setHalignment(disFarmId, HPos.LEFT);
      GridPane.setHalignment(year, HPos.LEFT);
      GridPane.setHalignment(totalWeight, HPos.LEFT);
      GridPane.setHalignment(percentTotal, HPos.LEFT);
      
      root.setTop(top);
      
      TableView<String> reportTable = new TableView<String>();
      reportTable.autosize();
      
      // set up columns
      TableColumn<String, String> farmId = new TableColumn<String, String>("Farm ID");
      farmId.setCellValueFactory(new PropertyValueFactory<>("farmId"));
      farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<String, String> milkWeight = new TableColumn<String, String>("Milk Weight");
      milkWeight.setCellValueFactory(new PropertyValueFactory<>("milkWeight"));
      milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<String, String> percentMilk = new TableColumn<String, String>("% of Total Milk");
      percentMilk.setCellValueFactory(new PropertyValueFactory<>("percentMilk"));
      percentMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      
      reportTable.getColumns().add(farmId);
      reportTable.getColumns().add(milkWeight);
      reportTable.getColumns().add(percentMilk);
//    reportTable.setItems(data);
      root.setCenter(reportTable);
      
      // table for report values
      GridPane reportGrid = new GridPane();
      final int numCols = 3;
      
      // set number of columns based on report
      for (int i = 0; i < numCols; i++) {
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(50.0 / numCols);
        reportGrid.getColumnConstraints().add(colConst);
      }
      
      reportGrid.setPadding(new Insets(10, 10, 10, 10));
      reportGrid.setGridLinesVisible(true);
      reportGrid.setAlignment(Pos.BASELINE_CENTER);
      
      // add data
      // for loop to go through data

      primaryStage.setTitle("Report Screen");
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    
    /**
     * Displays annual report screen
     * 
     * @param primaryStage
     */
    void annualReportScreen(Stage primaryStage) {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root, 1000, 800);
      scene.getStylesheets().add("cheese-factory.css");
      
      // report title and farm info
      GridPane top = new GridPane();
      top.setAlignment(Pos.CENTER);
      top.setPadding(new Insets(10, 10, 10, 10));
      Label titleLabel = new Label("Annual Report");
      
      // add title to top
      titleLabel.setId("report-title");
      top.add(titleLabel, 0, 0);
      GridPane.setHalignment(titleLabel, HPos.CENTER);
      
      // add report info to top
      StackPane info = new StackPane();
      Label year = new Label("Year: 2020");
      year.setId("report-info");
      info.getChildren().add(year);
      top.add(info, 0, 1);
      
      root.setTop(top);
      
      TableView<String> reportTable = new TableView<String>();
      reportTable.autosize();
      
      // set up columns
      TableColumn<String, String> farmId = new TableColumn<String, String>("Farm ID");
      farmId.setCellValueFactory(new PropertyValueFactory<>("farmId"));
      farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<String, String> milkWeight = new TableColumn<String, String>("Milk Weight");
      milkWeight.setCellValueFactory(new PropertyValueFactory<>("milkWeight"));
      milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<String, String> percentMilk = new TableColumn<String, String>("% of Total Milk");
      percentMilk.setCellValueFactory(new PropertyValueFactory<>("percentMilk"));
      percentMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      
      reportTable.getColumns().add(farmId);
      reportTable.getColumns().add(milkWeight);
      reportTable.getColumns().add(percentMilk);
//    reportTable.setItems(data);
      root.setCenter(reportTable);
      
      // table for report values
      GridPane reportGrid = new GridPane();
      final int numCols = 3;
      
      // set number of columns based on report
      for (int i = 0; i < numCols; i++) {
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(50.0 / numCols);
        reportGrid.getColumnConstraints().add(colConst);
      }
      
      reportGrid.setPadding(new Insets(10, 10, 10, 10));
      reportGrid.setGridLinesVisible(true);
      reportGrid.setAlignment(Pos.BASELINE_CENTER);
      
      // add data
      // for loop to go through data

      primaryStage.setTitle("Report Screen");
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    
    /**
     * Displays monthly report screen
     * 
     * @param primaryStage
     */
    void monthlyReportScreen(Stage primaryStage) {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root, 1000, 800);
      scene.getStylesheets().add("cheese-factory.css");
      
      // report title and farm info
      GridPane top = new GridPane();
      top.setAlignment(Pos.CENTER);
      top.setPadding(new Insets(10, 10, 10, 10));
      Label titleLabel = new Label("Monthly Report");
      
      // add title to top
      titleLabel.setId("report-title");
      top.add(titleLabel, 0, 0);
      GridPane.setHalignment(titleLabel, HPos.CENTER);
      
      // add report info to top      
      GridPane info = new GridPane();
      Label year = new Label("Year: 2020");
      Label month = new Label("Month: May");
      year.setId("report-info");
      month.setId("report-info");
      info.add(year, 0, 0);
      info.add(month, 1, 0);
      top.add(info, 0, 1);
      info.setHgap(20);
      GridPane.setHalignment(year, HPos.CENTER);
      GridPane.setHalignment(month, HPos.CENTER);
      
      root.setTop(top);
      
      TableView<String> reportTable = new TableView<String>();
      reportTable.autosize();
      
      // set up columns
      TableColumn<String, String> farmId = new TableColumn<String, String>("Farm ID");
      farmId.setCellValueFactory(new PropertyValueFactory<>("farmId"));
      farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<String, String> milkWeight = new TableColumn<String, String>("Milk Weight");
      milkWeight.setCellValueFactory(new PropertyValueFactory<>("milkWeight"));
      milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<String, String> percentMilk = new TableColumn<String, String>("% of Total Milk");
      percentMilk.setCellValueFactory(new PropertyValueFactory<>("percentMilk"));
      percentMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      
      reportTable.getColumns().add(farmId);
      reportTable.getColumns().add(milkWeight);
      reportTable.getColumns().add(percentMilk);
//    reportTable.setItems(data);
      root.setCenter(reportTable);
      
      // table for report values
      GridPane reportGrid = new GridPane();
      final int numCols = 3;
      
      // set number of columns based on report
      for (int i = 0; i < numCols; i++) {
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(50.0 / numCols);
        reportGrid.getColumnConstraints().add(colConst);
      }
      
      reportGrid.setPadding(new Insets(10, 10, 10, 10));
      reportGrid.setGridLinesVisible(true);
      reportGrid.setAlignment(Pos.BASELINE_CENTER);
      
      // add data
      // for loop to go through data

      primaryStage.setTitle("Report Screen");
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    
    /**
     * Displays date range report screen
     * 
     * @param primaryStage
     */
    void dateRangeReportScreen(Stage primaryStage) {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root, 1000, 800);
      scene.getStylesheets().add("cheese-factory.css");
      
      // report title and farm info
      GridPane top = new GridPane();
      top.setAlignment(Pos.CENTER);
      top.setPadding(new Insets(10, 10, 10, 10));
      Label titleLabel = new Label("Date Range Report");
      
      // add title to top
      titleLabel.setId("report-title");
      top.add(titleLabel, 0, 0);
      GridPane.setHalignment(titleLabel, HPos.CENTER);
      
      // add report info to top      
      GridPane info = new GridPane();
      Label startDate = new Label("Start date: April 3, 2020");
      Label endDate = new Label("End Date: June 8, 2020");
      startDate.setId("report-info");
      endDate.setId("report-info");
      info.add(startDate, 0, 0);
      info.add(endDate, 1, 0);
      top.add(info, 0, 1);
      info.setHgap(20);
      GridPane.setHalignment(startDate, HPos.CENTER);
      GridPane.setHalignment(endDate, HPos.CENTER);
      
      root.setTop(top);
      
      TableView<Farm> reportTable = new TableView<Farm>();
      reportTable.autosize();
      
      // set up columns
      TableColumn<Farm, String> farmId = new TableColumn<Farm, String>("Farm ID");
      farmId.setCellValueFactory(new PropertyValueFactory<>("farmId"));
      farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<Farm, String> milkWeight = new TableColumn<Farm, String>("Milk Weight");
      milkWeight.setCellValueFactory(new PropertyValueFactory<>("milkWeight"));
      milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<Farm, String> percentMilk = new TableColumn<Farm, String>("% of Total Milk");
      percentMilk.setCellValueFactory(new PropertyValueFactory<>("percentMilk"));
      percentMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      
      reportTable.getColumns().add(farmId);
      reportTable.getColumns().add(milkWeight);
      reportTable.getColumns().add(percentMilk);
      root.setCenter(reportTable);
      
      // table for report values
      GridPane reportGrid = new GridPane();
      final int numCols = 3;
      
      // set number of columns based on report
      for (int i = 0; i < numCols; i++) {
        ColumnConstraints colConst = new ColumnConstraints();
        colConst.setPercentWidth(50.0 / numCols);
        reportGrid.getColumnConstraints().add(colConst);
      }
      
      reportGrid.setPadding(new Insets(10, 10, 10, 10));
      reportGrid.setGridLinesVisible(true);
      reportGrid.setAlignment(Pos.BASELINE_CENTER);
      
      // add data
      // for loop to go through data?

      primaryStage.setTitle("Report Screen");
      primaryStage.setScene(scene);
      primaryStage.show();
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