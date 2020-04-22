package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import backend.CheeseFactory;
import backend.Date;
import backend.Farm;
import exceptions.DuplicateAdditionException;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
	
	private static Font buttonFont = new Font(14);
	private static Font titleFont = new Font(24);
	private static Font labelFont = new Font(16);
	
	@Override
	public void start(Stage primaryStage) {
		homeScreen(primaryStage);
	}

	void homeScreen(Stage stage) {
    	try { 
    		
        	BorderPane root = new BorderPane();

        	
        	
        	Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        	mainScene.getStylesheets().add("application/application.css");
		           
            //Title and Header
            stage.setTitle(APP_TITLE);
            
            //Choose what type of report to make
            Label reportLabel = new Label("Generate Report");
            reportLabel.setFont(titleFont);
            
            HBox reportHBox = new HBox(12);
            reportHBox.setAlignment(Pos.CENTER);
            
            Label reportSelectLabel = new Label("Report Type:");
            reportSelectLabel.setFont(labelFont);
        	ComboBox<String> combo_box = new ComboBox<String>();
        	combo_box.getItems().addAll("Annual","Monthly","Farm","Date Range");
        	combo_box.getSelectionModel().selectFirst();
        	
            reportHBox.getChildren().add(reportSelectLabel);
            reportHBox.getChildren().add(combo_box);

        	Button selectReportButton = new Button("Generate");
        	selectReportButton.setFont(buttonFont);
            
            //Select Files label, button, and file chooser
        	Label importLabel = new Label("Import Data");
        	importLabel.setFont(titleFont);
        	
            FileChooser file_chooser = new FileChooser(); 
            
            Label selectFilesLabel = new Label("no files selected");
            
            Button selectFileButton = new Button("Import .csv");
            selectFileButton.setFont(buttonFont);
            
            //File exporter
            Label exportLabel = new Label("Export Data");
            exportLabel.setFont(titleFont);
            
            Label exportSuccessLabel = new Label("no files exported");
            FileChooser exportChooser = new FileChooser();
            
            Button exportButton = new Button("Export .csv");
            exportButton.setFont(buttonFont);
            
            
            
            //Title, fields, and button for manually inserting data
            Label insertLabel = new Label("Insert Data");
        	insertLabel.setFont(titleFont);

            Label farmNameLabel = new Label("Farm: ");
            farmNameLabel.setFont(labelFont);
            TextField farmNameField = new TextField();
            HBox farmInfoHBox = new HBox();
            farmInfoHBox.getChildren().add(farmNameLabel);
            farmInfoHBox.setMargin(farmNameLabel, new Insets(0,12,0,0));
            farmInfoHBox.getChildren().add(farmNameField);
            
            Label dateLabel = new Label("Date: ");
            dateLabel.setFont(labelFont);
            DatePicker datePicker = new DatePicker();
            HBox dateInfoHBox = new HBox();
            dateInfoHBox.getChildren().add(dateLabel);
            dateInfoHBox.setMargin(dateLabel, new Insets(0,6,0,0));
            dateInfoHBox.getChildren().add(datePicker);
            
            Label milkLabel = new Label("Milk: ");
            milkLabel.setFont(labelFont);
            TextField milkField = new TextField();
            HBox milkInfoHBox = new HBox();
            milkInfoHBox.getChildren().add(milkLabel);
            milkInfoHBox.setMargin(milkLabel, new Insets(0,14,0,0));
            milkInfoHBox.getChildren().add(milkField);
            
            Button insertDataButton = new Button("Insert Data");
            insertDataButton.setFont(buttonFont);
            
      
            //Event Handler for Report Selections Selections
            EventHandler<ActionEvent> confirmReport =  new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                	VBox selectedReportvBox = new VBox();
                	Scene selectedReportScene = new Scene(selectedReportvBox, WINDOW_WIDTH, WINDOW_HEIGHT);
                	Label selectedReportLabel; 
                	Label  yearLabel;
                	Label  monthLabel;
                	Label dayLabel;
                	Button backButton;
                	
                	// Event Handler for Back Button
                    EventHandler<ActionEvent> back =  new EventHandler<ActionEvent>() { 
                        public void handle(ActionEvent e) 
                        {	
                        	homeScreen(stage);
                        } 
                    };
                	
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
                        	backButton = new Button("Back");
                        	
                        	selectedReportvBox.getChildren().add(selectedReportLabel);
                        	selectedReportvBox.getChildren().add(farmIDLabel);
                        	selectedReportvBox.getChildren().add(farmIdTextField);
                        	selectedReportvBox.getChildren().add(yearLabel);
                        	selectedReportvBox.getChildren().add(yearTextField);
                        	selectedReportvBox.getChildren().add(generateFarmButton);
                        	selectedReportvBox.getChildren().add(backButton);
                        	
                        	
                            backButton.setOnAction(back);
                        	
                        	// Event Handler for file Selections
                            EventHandler<ActionEvent> callFarm =  new EventHandler<ActionEvent>() { 
                                public void handle(ActionEvent e) 
                                {	                 
                                	//System.out.println("CallFarm("+ " " + farmIdTextField.getText() + ", " + yearTextField.getText() + ")");
                                	farmReportScreen(stage, farmIdTextField.getText(), yearTextField.getText()); //String farmIdTextField.getText(),String yearTextField.getText()
                                } 
                            }; 
                            generateFarmButton.setOnAction(callFarm);
                        	
                        	
                            break; 
                        case "Annual": 
                        	selectedReportLabel = new Label("Annual Report"); 
                        	yearLabel= new Label("Enter Year:");
                        	yearTextField = new TextField ();
                        	Button generateAnnualButton = new Button("Generate Farm Report");
                        	backButton = new Button("Back");
                        	selectedReportvBox.getChildren().add(selectedReportLabel);
                        	selectedReportvBox.getChildren().add(yearLabel);
                        	selectedReportvBox.getChildren().add(yearTextField);
                        	selectedReportvBox.getChildren().add(generateAnnualButton);
                        	
                        	selectedReportvBox.getChildren().add(backButton);
                        	 
                            backButton.setOnAction(back);
                            
                        	// Event Handler for file Selections
                            EventHandler<ActionEvent> callAnnual =  new EventHandler<ActionEvent>() { 
                                public void handle(ActionEvent e) 
                                {
                                	//System.out.println("CallAnnual("+" " + yearTextField.getText() + ")");
                                	annualReportScreen(stage, yearTextField.getText());
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
                        	backButton = new Button("Back");
                        	
                        	selectedReportvBox.getChildren().add(selectedReportLabel);
                        	selectedReportvBox.getChildren().add(yearLabel);
                        	selectedReportvBox.getChildren().add(yearTextField);
                        	selectedReportvBox.getChildren().add(monthLabel);
                        	selectedReportvBox.getChildren().add(monthTextField);
                        	selectedReportvBox.getChildren().add(generateMonthlyButton);
                     
                        	selectedReportvBox.getChildren().add(backButton);
                        	
                            backButton.setOnAction(back);
                            
                        	// Event Handler for file Selections
                            EventHandler<ActionEvent> callMonthly =  new EventHandler<ActionEvent>() { 
                                public void handle(ActionEvent e) 
                                {	
                                	//System.out.println("CallMonthly("+" " + yearTextField.getText() + " " + monthTextField.getText() + ")");
                                	monthlyReportScreen(stage, (yearTextField.getText()!=null) ? yearTextField.getText() : "2019", (monthTextField.getText()!=null) ? monthTextField.getText() : "1");
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
                        	backButton = new Button("Back");
                        	
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
                        	selectedReportvBox.getChildren().add(backButton);
                        	
                        	
                            backButton.setOnAction(back);
                        	
                        	// Event Handler for file Selections
                            EventHandler<ActionEvent> callDateRange =  new EventHandler<ActionEvent>() { 
                                public void handle(ActionEvent e) 
                                {	
                                	//System.out.println("CallDateRange("+ " Start Month: " + startMonthTextField.getText() + " Start Day: " + startDayTextField.getText() + " Start Year: " + startYearTextField.getText() + " End Month: " + endMonthTextField.getText() +  " End Day: " + endDayTextField.getText()+ " End Year: " + endYearTextField.getText() + ")");
                                	dateRangeReportScreen(stage, startMonthTextField.getText(),startDayTextField.getText(),startYearTextField.getText(),endMonthTextField.getText(),endDayTextField.getText(), endYearTextField.getText());
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
            
            // Event Handler for file imports
            EventHandler<ActionEvent> selectFile =  new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    File file = file_chooser.showOpenDialog(stage); 
                    if (file != null) { 
                        
                    	try {
                    		factory.importFarmData(file.getAbsolutePath());
                    		selectFilesLabel.setText(file.getName() + " imported");
                    	}catch (Exception a) {
                    		a.printStackTrace();
                    		selectFilesLabel.setText("Loading File Failed Select a New File"); 
                    	}
                        
                    } 
                } 
            };
            
         // Event Handler for file exports
            EventHandler<ActionEvent> selectExport =  new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    File file = exportChooser.showOpenDialog(stage); 
                    if (file != null) { 
                        
                    	try {
                    		factory.exportFarmData(file.getAbsolutePath());
                    		exportSuccessLabel.setText(file.getName() + " exported");
                    	}catch (Exception a) {
                    		a.printStackTrace();
                    		selectFilesLabel.setText("failed to export"); 
                    	}
                        
                    } 
                } 
            };
      
            //set the action of the button
            selectFileButton.setOnAction(selectFile); 
            exportButton.setOnAction(selectExport);
            
            //set up vertical boxes for categories of actions
            VBox lVBox = new VBox(12);
            lVBox.setAlignment(Pos.TOP_CENTER);
            
            VBox cVBox = new VBox(12);
            cVBox.setAlignment(Pos.TOP_CENTER);
            
            VBox rVBox = new VBox(12);
            rVBox.setAlignment(Pos.TOP_CENTER);
            
            //set up the middle hbox to have separators
            HBox cHBox = new HBox();
            cHBox.setAlignment(Pos.TOP_CENTER);
            Separator leftSeparator = new Separator(Orientation.VERTICAL);
            cHBox.getChildren().add(leftSeparator);
            cHBox.setMargin(leftSeparator, new Insets(0,24,0,0));
            
            cHBox.getChildren().add(cVBox);

            Separator rightSeparator = new Separator(Orientation.VERTICAL);
            cHBox.getChildren().add(rightSeparator);
            cHBox.setMargin(rightSeparator, new Insets(0,0,0,24));
            
            //add children to respective vboxes
            lVBox.getChildren().add(importLabel);
            lVBox.setMargin(importLabel, new Insets(0,0,12,0));
            lVBox.getChildren().add(selectFilesLabel);
            lVBox.getChildren().add(selectFileButton);
            
            Separator horizontalSeparator = new Separator(Orientation.HORIZONTAL);
            lVBox.getChildren().add(horizontalSeparator);
            
            lVBox.getChildren().add(exportLabel);
            lVBox.setMargin(exportLabel, new Insets(0,0,12,0));
            lVBox.getChildren().add(exportSuccessLabel);
            lVBox.getChildren().add(exportButton);
            
            cVBox.getChildren().add(reportLabel);
            cVBox.setMargin(reportLabel, new Insets(0,0,12,0));
            cVBox.getChildren().add(reportHBox);
            cVBox.getChildren().add(selectReportButton);
            
            rVBox.getChildren().add(insertLabel);
            rVBox.setMargin(insertLabel, new Insets(0,0,12,0));
            rVBox.getChildren().add(farmInfoHBox);
            rVBox.getChildren().add(dateInfoHBox);
            rVBox.getChildren().add(milkInfoHBox);
            rVBox.getChildren().add(insertDataButton);
            
            //add children to hbox
            root.setLeft(lVBox);
            root.setMargin(lVBox, new Insets(12,0,12,36));
            root.setCenter(cHBox);
            root.setMargin(cHBox, new Insets(12,12,12,12));
            root.setRight(rVBox);
            root.setMargin(rVBox, new Insets(12,42,12,0));
            
            //set the scene and start the show
        	stage.setScene(mainScene);
        	stage.show();
        } 
      
        catch (Exception e) { 
      
            System.out.println(e.getMessage()); 
        } 
    }
	
    public class FarmsModel {
      private SimpleStringProperty farmId;
      private SimpleStringProperty month;
      private SimpleIntegerProperty milkWeight;
      private SimpleDoubleProperty percentMilk;
      
      public FarmsModel(String month, Integer milkWeight) {
        this.month = new SimpleStringProperty(month);
        this.milkWeight = new SimpleIntegerProperty(milkWeight);
      }
      
      public FarmsModel(String farmId, Integer milkWeight, Double percentMilk) {
        this.farmId = new SimpleStringProperty(farmId);
        this.milkWeight = new SimpleIntegerProperty(milkWeight);
        this.percentMilk = new SimpleDoubleProperty(percentMilk);
      }
      
      public String getFarmId() {
        return farmId.get();
      }
      
      public void setFarmID(SimpleStringProperty farmId) {
        this.farmId = farmId;
      }      
      
      public String getMonth() {
        return month.get();
      }
      
      public void setMonth(SimpleStringProperty month) {
        this.month = month;
      }
      
      public int getMilkWeight() {
        return milkWeight.get();
      }
      
      public void setMilkWeight(SimpleIntegerProperty milkWeight) {
        this.milkWeight = milkWeight;
      }
      
      public double getPercentMilk() {
        return percentMilk.get();
      }
      
      public void setPercentMilk(SimpleDoubleProperty percentMilk) {
        this.percentMilk = percentMilk;
      }
    }
    
    /**
     * Displays farm report screen
     * 
     * @param primaryStage
     */
    void farmReportScreen(Stage primaryStage, String inputFarmId, String inputYear) {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
      scene.getStylesheets().add("application/application.css");
      
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
      Label percentTotal = new Label("Percent of Total Farms: 20%");
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
      GridPane.setHalignment(disFarmId, HPos.RIGHT);
      GridPane.setHalignment(year, HPos.RIGHT);
      GridPane.setHalignment(totalWeight, HPos.CENTER);
      GridPane.setHalignment(percentTotal, HPos.CENTER);
      
      root.setTop(top);
      
      TableView<FarmsModel> reportTable = new TableView<FarmsModel>();
      reportTable.autosize();
      
      // set up columns
      TableColumn<FarmsModel, String> month = new TableColumn<FarmsModel, String>("Month");
      month.setCellValueFactory(new PropertyValueFactory<FarmsModel, String>("Month"));
      month.prefWidthProperty().bind(reportTable.widthProperty().divide(2));
      TableColumn<FarmsModel, Integer> milkWeight = new TableColumn<FarmsModel, Integer>("Milk Weight");
      milkWeight.setCellValueFactory(new PropertyValueFactory<FarmsModel, Integer>("MilkWeight"));
      milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(2));
      
      reportTable.getColumns().add(month);
      reportTable.getColumns().add(milkWeight);
      ObservableList<FarmsModel> farmsModels = FXCollections.observableArrayList(
          new FarmsModel("April", 1000),
          new FarmsModel("May", 2304));
      reportTable.setItems(farmsModels);
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
      
      // back button
      Button backButton = new Button("Back");
      backButton(root, primaryStage, backButton);
      
      primaryStage.setScene(scene);
      primaryStage.show();
    }
    
    /**
     * Displays annual report screen
     * 
     * @param primaryStage
     */
    void annualReportScreen(Stage primaryStage, String inputYear) {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
      scene.getStylesheets().add("application/application.css");
      
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
      
      TableView<Farm> reportTable = new TableView<Farm>();
      reportTable.autosize();
      
      // set up columns
      TableColumn<Farm, String> farmId = new TableColumn<Farm, String>("Farm ID");
      farmId.setCellValueFactory(new PropertyValueFactory<>("farmId"));
      farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<Farm, Integer> milkWeight = new TableColumn<Farm, Integer>("Milk Weight");
      milkWeight.setCellValueFactory(new PropertyValueFactory<>("milkWeight"));
      milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<Farm, Double> percentMilk = new TableColumn<Farm, Double>("% of Total Milk");
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
      // for loop to go through data
      
      // back button
      Button backButton = new Button("Back");
      backButton(root, primaryStage, backButton);

      primaryStage.setScene(scene);
      primaryStage.show();
    }
    
    /**
     * Displays monthly report screen
     * 
     * @param primaryStage
     */
    void monthlyReportScreen(Stage primaryStage, String inputYear, String inputMonth) {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
      scene.getStylesheets().add("application/application.css");
      
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
      Label year = new Label("Year: " + inputYear);
      Label month = new Label("Month: " + inputMonth.substring(0, 1).toUpperCase() + inputMonth.substring(1).toLowerCase());
      year.setId("report-info");
      month.setId("report-info");
      info.add(year, 0, 0);
      info.add(month, 1, 0);
      top.add(info, 0, 1);
      info.setHgap(20);
      info.setAlignment(Pos.CENTER);
      GridPane.setHalignment(year, HPos.CENTER);
      GridPane.setHalignment(month, HPos.CENTER);
      root.setTop(top);
      
      TableView<FarmsModel> reportTable = new TableView<FarmsModel>();
      
      // set up columns
      TableColumn<FarmsModel, String> farmId = new TableColumn<FarmsModel, String>("Farm ID");
      farmId.setCellValueFactory(new PropertyValueFactory<>("FarmId"));
      farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<FarmsModel, Integer> milkWeight = new TableColumn<FarmsModel, Integer>("Milk Weight");
      milkWeight.setCellValueFactory(new PropertyValueFactory<>("MilkWeight"));
      milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<FarmsModel, Double> percentMilk = new TableColumn<FarmsModel, Double>("% of Total Milk");
      percentMilk.setCellValueFactory(new PropertyValueFactory<>("PercentMilk"));
      percentMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      
      reportTable.getColumns().add(farmId);
      reportTable.getColumns().add(milkWeight);
      reportTable.getColumns().add(percentMilk);
      
      ObservableList<FarmsModel> farmsModels = FXCollections.observableArrayList(
          new FarmsModel("1", 1023, 10.0),
          new FarmsModel("2", 2989, 22.3));
      reportTable.setItems(farmsModels);
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
      
      // back button
      Button backButton = new Button("Back");
      backButton(root, primaryStage, backButton);

      primaryStage.setScene(scene);
      primaryStage.show();
    }
    
    /**
     * Displays date range report screen
     * 
     * @param primaryStage
     */
    void dateRangeReportScreen(Stage primaryStage, String inputStartMonth, String inputStartDay, String inputStartYear, String endStartMonth, String endStartDay, String endStartYear) {
      BorderPane root = new BorderPane();
      Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
      scene.getStylesheets().add("application/application.css");
      
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
      
      // set up columns
      TableColumn<Farm, String> farmId = new TableColumn<Farm, String>("Farm ID");
      farmId.setCellValueFactory(new PropertyValueFactory<>("farmId"));
      farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
      TableColumn<Farm, Integer> milkWeight = new TableColumn<Farm, Integer>("Milk Weight");
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

      // back button
      Button backButton = new Button("Back");
      backButton(root, primaryStage, backButton);
      
      primaryStage.setScene(scene);
      primaryStage.show();
    }

    void backButton(BorderPane root, Stage primaryStage, Button backButton) {
      HBox buttonHBox = new HBox();
      backButton.setMaxHeight(200);
      buttonHBox.getChildren().add(backButton);
      buttonHBox.setAlignment(Pos.BASELINE_CENTER);
      root.setBottom(buttonHBox);
      
      // back button functionality
      backButton.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
          homeScreen(primaryStage);
        }
      });
    }

	public static void main(String[] args) {
		factory = new CheeseFactory();
		launch(args);
	}
}