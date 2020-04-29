package application;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;

import backend.CheeseFactory;
import backend.Farm;
import exceptions.DuplicateAdditionException;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
public class Main extends javafx.application.Application {

	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 400;
	private static final String APP_TITLE = "Milk No Consequences";
	private static TextField yearTextField;
	private static TextField farmIdTextField;
	private static TextField monthTextField;
	private static LocalDate startDate;
	private static LocalDate endDate;
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

			// Title and Header
			stage.setTitle(APP_TITLE);

			// Choose what type of report to make
			Label reportLabel = new Label("Generate Report");
			reportLabel.setFont(titleFont);

			HBox reportHBox = new HBox(12);
			reportHBox.setAlignment(Pos.CENTER);

			Label reportSelectLabel = new Label("Report Type:");
			reportSelectLabel.setFont(labelFont);
			ComboBox<String> combo_box = new ComboBox<String>();
			combo_box.getItems().addAll("Annual", "Monthly", "Farm", "Date Range");
			combo_box.getSelectionModel().selectFirst();

			reportHBox.getChildren().add(reportSelectLabel);
			reportHBox.getChildren().add(combo_box);

			Button selectReportButton = new Button("Generate");
			selectReportButton.setFont(buttonFont);

			// Select Files label, button, and file chooser
			Label importLabel = new Label("Import Data");
			importLabel.setFont(titleFont);

			FileChooser file_chooser = new FileChooser();

			Label selectFilesLabel = new Label("no files selected");

			Button selectFileButton = new Button("Import .csv");
			selectFileButton.setFont(buttonFont);

			// File exporter
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
            datePicker.setValue(LocalDate.now());
            HBox dateInfoHBox = new HBox();
            dateInfoHBox.getChildren().add(dateLabel);
            dateInfoHBox.setMargin(dateLabel, new Insets(0,6,0,0));
            dateInfoHBox.getChildren().add(datePicker);
            
            Label milkLabel = new Label("Milk: ");
            milkLabel.setFont(labelFont);
            TextField milkField = new TextField();
            milkField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*")) {
			            milkField.setText(newValue.replaceAll("[^\\d]", ""));
			        }
				}
            });
			HBox milkInfoHBox = new HBox();
			milkInfoHBox.getChildren().add(milkLabel);
			milkInfoHBox.setMargin(milkLabel, new Insets(0, 14, 0, 0));
			milkInfoHBox.getChildren().add(milkField);

			Button insertDataButton = new Button("Insert Data");
			insertDataButton.setFont(buttonFont);

			// Event Handler for Report Selections Selections
			EventHandler<ActionEvent> confirmReport = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					VBox selectedReportvBox = new VBox();
					Scene selectedReportScene = new Scene(selectedReportvBox, WINDOW_WIDTH, WINDOW_HEIGHT);
					selectedReportScene.getStylesheets().add("application/application.css");
					Label selectedReportLabel;
					Label yearLabel;
					Label monthLabel;
					Label dayLabel;
					Button backButton;

					// Event Handler for Back Button
					EventHandler<ActionEvent> back = new EventHandler<ActionEvent>() {
						public void handle(ActionEvent e) {
							homeScreen(stage);
						}
					};

					switch (combo_box.getValue()) {
					case "Farm":
					  selectedReportLabel = new Label("Farm Report");
                      selectedReportLabel.setFont(titleFont);
                      
                      Label  farmIDLabel= new Label("Farm Id Number:");
                      farmIDLabel.setFont(labelFont);
                      farmIdTextField = new TextField ();
                      HBox farmIdInfoHBox = new HBox();
                      farmIdInfoHBox.getChildren().add(farmIDLabel);
                      farmIdInfoHBox.setMargin(farmIDLabel, new Insets(0,6,0,0));
                      farmIdInfoHBox.getChildren().add(farmIdTextField);
                      farmIdInfoHBox.setAlignment(Pos.CENTER);
                      
                      yearLabel= new Label("Enter Year:");
                      yearLabel.setFont(labelFont);
                      yearTextField = new TextField ();
                      HBox yearInfoHBox = new HBox();
                      yearInfoHBox.getChildren().add(yearLabel);
                      yearInfoHBox.setMargin(yearLabel, new Insets(0,6,0,0));
                      yearInfoHBox.getChildren().add(yearTextField);
                      yearInfoHBox.setAlignment(Pos.CENTER);
                      
                      
                      Button generateFarmButton = new Button("Generate Farm Report");
                      yearTextField.getText();
                      backButton = new Button("Back");
                      
                      selectedReportvBox.getChildren().add(selectedReportLabel);
                      selectedReportvBox.getChildren().add(farmIdInfoHBox);
                      selectedReportvBox.getChildren().add(yearInfoHBox);
                      selectedReportvBox.getChildren().add(generateFarmButton);
                      selectedReportvBox.getChildren().add(backButton);
                      selectedReportvBox.setSpacing(5);
                      
                      backButton.setOnAction(back);

                      selectedReportvBox.setAlignment(Pos.TOP_CENTER);
                      
                      Image cowPrint = new Image("cow_print.jpg");
                      
                      //create background Image with cowPrint Image
                      BackgroundImage background_fill = new BackgroundImage(cowPrint, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,  
                          BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT); 
            
                      Background background = new Background(background_fill); 
                      selectedReportvBox.setBackground(background);

						// Event Handler for file Selections
						EventHandler<ActionEvent> callFarm = new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								// System.out.println("CallFarm("+ " " + farmIdTextField.getText() + ", " +
								// yearTextField.getText() + ")");
								farmReportScreen(stage, farmIdTextField.getText(), Integer.parseInt(yearTextField.getText())); // String
																												// farmIdTextField.getText(),String
																												// yearTextField.getText()
							}
						};
						generateFarmButton.setOnAction(callFarm);

						break;
					case "Annual":
                    	selectedReportvBox.setAlignment(Pos.TOP_CENTER);
                    	selectedReportLabel = new Label("Annual Report");
                    	
                    	yearLabel= new Label("Enter Year:");
                    	yearTextField = new TextField ();
                    	HBox yearInfoHBox1 = new HBox();
                    	yearInfoHBox1.getChildren().add(yearLabel);
                    	yearInfoHBox1.setMargin(yearLabel, new Insets(0,14,0,0));
                    	yearInfoHBox1.getChildren().add(yearTextField);
                    	yearInfoHBox1.setAlignment(Pos.TOP_CENTER);

                    	Button generateAnnualButton = new Button("Generate Annual Report");
                    	backButton = new Button("Back");
                    	
                    	selectedReportvBox.getChildren().add(selectedReportLabel);
                    	selectedReportvBox.getChildren().add(yearInfoHBox1);
                    	selectedReportvBox.getChildren().add(generateAnnualButton);
                    	selectedReportvBox.getChildren().add(backButton);
                    	
                    	selectedReportLabel.setFont(titleFont);
                    	yearLabel.setFont(labelFont);
                    	backButton.setFont(buttonFont);
                    	generateAnnualButton.setFont(buttonFont);
                    	selectedReportvBox.setSpacing(5);
                    	
                        backButton.setOnAction(back);
                        
                    	// Event Handler for file Selections
                        EventHandler<ActionEvent> callAnnual =  new EventHandler<ActionEvent>() { 
                            public void handle(ActionEvent e) 
                            {
                            	//System.out.println("CallAnnual("+" " + yearTextField.getText() + ")");
                            	annualReportScreen(stage, Integer.parseInt(yearTextField.getText()));
                            }
                        }; 
                        generateAnnualButton.setOnAction(callAnnual);
                        break; 

					case "Monthly":
						selectedReportLabel = new Label("Monthly Report");
						selectedReportLabel.setFont(titleFont);
						yearLabel = new Label("Enter Year:");
						
						ComboBox<String> combo_box = new ComboBox<String>();
						
						combo_box.getItems().addAll("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER");
						combo_box.getSelectionModel().selectFirst();
						
						yearLabel.setFont(labelFont);
						yearTextField = new TextField();
						monthLabel = new Label("Select Month:");
						monthLabel.setFont(labelFont);
						Button generateMonthlyButton = new Button("Generate Monthly Report");
						backButton = new Button("Back");

						selectedReportvBox.getChildren().add(selectedReportLabel);
						selectedReportvBox.getChildren().add(yearLabel);
						selectedReportvBox.getChildren().add(yearTextField);
						selectedReportvBox.getChildren().add(monthLabel);
						selectedReportvBox.getChildren().add(combo_box);
						selectedReportvBox.setSpacing(5);
						selectedReportvBox.getChildren().add(generateMonthlyButton);

						selectedReportvBox.getChildren().add(backButton);
						selectedReportvBox.setAlignment(Pos.TOP_CENTER);

						backButton.setOnAction(back);

						// Event Handler for file Selections
						EventHandler<ActionEvent> callMonthly = new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								try {
									Integer year = Integer.valueOf(yearTextField.getText());
									monthlyReportScreen(stage, year, combo_box.getValue());
								}catch(Exception excep) {
									Alert errorAlert = new Alert(AlertType.ERROR);
									errorAlert.setHeaderText(null);
									errorAlert.setContentText("Please Enter a Valid Year");
									errorAlert.showAndWait();
								}
							}
						};
						generateMonthlyButton.setOnAction(callMonthly);
						break;
					case "Date Range":
						selectedReportLabel = new Label("Date Range Report");
						selectedReportLabel.setFont(titleFont);
						Label startLabel = new Label("Enter Starting Date:");
						startLabel.setFont(labelFont);
						Label endLabel = new Label("Enter Ending Date:");

						endLabel = new Label("Enter Ending Date:");
						endLabel.setFont(labelFont);

						backButton = new Button("Back");
						DatePicker startDatePicker = new DatePicker();
						DatePicker endDatePicker = new DatePicker();

						Button generateDateRangeButton = new Button("Generate Date Range Report");
						selectedReportvBox.getChildren().add(selectedReportLabel);

						// adding starting elements
						selectedReportvBox.getChildren().add(startLabel);
						selectedReportvBox.getChildren().add(startDatePicker);
						// adding ending elements
						selectedReportvBox.getChildren().add(endLabel);
						selectedReportvBox.getChildren().add(endDatePicker);

						// generate button
						selectedReportvBox.getChildren().add(generateDateRangeButton);
						selectedReportvBox.setSpacing(5);
						selectedReportvBox.getChildren().add(backButton);

						selectedReportvBox.setAlignment(Pos.TOP_CENTER);

						backButton.setOnAction(back);

						// Event Handler for file Selections
						EventHandler<ActionEvent> callDateRange = new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {

								try {
									dateRangeReportScreen(stage, startDatePicker.getValue(), endDatePicker.getValue());
								}catch(Exception error) {
									Alert errorAlert = new Alert(AlertType.ERROR);
									errorAlert.setHeaderText(null);
									errorAlert.setContentText("Please Select Valid Dates");
									errorAlert.showAndWait();
								}
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
			EventHandler<ActionEvent> selectFile = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					File file = file_chooser.showOpenDialog(stage);
					if (file != null) {

						try {
							factory.importFarmData(file.getAbsolutePath());
							selectFilesLabel.setText(file.getName() + " imported");
						} catch (Exception a) {
							a.printStackTrace();
							selectFilesLabel.setText("Loading File Failed Select a New File");
						}

					}
				}
			};

			// Event Handler for file exports
			EventHandler<ActionEvent> selectExport = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					File file = exportChooser.showOpenDialog(stage);
					if (file != null) {

						try {
//                    		factory.exportFarmData(file.getAbsolutePath());
							exportSuccessLabel.setText(file.getName() + " exported");
						} catch (Exception a) {
							a.printStackTrace();
							selectFilesLabel.setText("failed to export");
						}

					}
				}
			};

			// Event Handler for inputting raw data
            EventHandler<ActionEvent> insertDataHandler = new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent e) {
            		String farm = "";
            		int milk = 0;
            		LocalDate date = null;
            		try {
            			farm = farmNameField.getText();
            			milk = Integer.parseInt(milkField.getText());
            			date = datePicker.getValue();
            			
            			if (farm.contentEquals("")) {
            				Alert errorAlert = new Alert(AlertType.ERROR);
                			errorAlert.setHeaderText(null);
                			errorAlert.setContentText("Must enter a farm ID.");
                			errorAlert.showAndWait();
                			return;
            			}
            			
            		} catch (NumberFormatException nfe) {
            			Alert errorAlert = new Alert(AlertType.ERROR);
            			errorAlert.setHeaderText(null);
            			if (farm.contentEquals(""))
            				errorAlert.setContentText("Must enter a number for milk weight and a farm ID.");
            			else
            				errorAlert.setContentText("Must enter a number for milk weight.");
            			errorAlert.showAndWait();
            			return;
            		}
            		
            		
            	}
            };
      
            //set the action of the button
            selectFileButton.setOnAction(selectFile); 
            exportButton.setOnAction(selectExport);
            insertDataButton.setOnAction(insertDataHandler);

			// set up vertical boxes for categories of actions
			VBox lVBox = new VBox(12);
			lVBox.setAlignment(Pos.TOP_CENTER);

			VBox cVBox = new VBox(12);
			cVBox.setAlignment(Pos.TOP_CENTER);

			VBox rVBox = new VBox(12);
			rVBox.setAlignment(Pos.TOP_CENTER);

			// set up the middle hbox to have separators
			HBox cHBox = new HBox();
			cHBox.setAlignment(Pos.TOP_CENTER);
			Separator leftSeparator = new Separator(Orientation.VERTICAL);
			cHBox.getChildren().add(leftSeparator);
			cHBox.setMargin(leftSeparator, new Insets(0, 24, 0, 0));

			cHBox.getChildren().add(cVBox);

			Separator rightSeparator = new Separator(Orientation.VERTICAL);
			cHBox.getChildren().add(rightSeparator);
			cHBox.setMargin(rightSeparator, new Insets(0, 0, 0, 24));

			// add children to respective vboxes
			lVBox.getChildren().add(importLabel);
			lVBox.setMargin(importLabel, new Insets(0, 0, 12, 0));
			lVBox.getChildren().add(selectFilesLabel);
			lVBox.getChildren().add(selectFileButton);

			Separator horizontalSeparator = new Separator(Orientation.HORIZONTAL);
			lVBox.getChildren().add(horizontalSeparator);

			lVBox.getChildren().add(exportLabel);
			lVBox.setMargin(exportLabel, new Insets(0, 0, 12, 0));
			lVBox.getChildren().add(exportSuccessLabel);
			lVBox.getChildren().add(exportButton);

			cVBox.getChildren().add(reportLabel);
			cVBox.setMargin(reportLabel, new Insets(0, 0, 12, 0));
			cVBox.getChildren().add(reportHBox);
			cVBox.getChildren().add(selectReportButton);

			rVBox.getChildren().add(insertLabel);
			rVBox.setMargin(insertLabel, new Insets(0, 0, 12, 0));
			rVBox.getChildren().add(farmInfoHBox);
			rVBox.getChildren().add(dateInfoHBox);
			rVBox.getChildren().add(milkInfoHBox);
			rVBox.getChildren().add(insertDataButton);

			// add children to hbox
			root.setLeft(lVBox);
			root.setMargin(lVBox, new Insets(12, 0, 12, 36));
			root.setCenter(cHBox);
			root.setMargin(cHBox, new Insets(12, 12, 12, 12));
			root.setRight(rVBox);
			root.setMargin(rVBox, new Insets(12, 42, 12, 0));

			// set the scene and start the show
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
		private SimpleDoubleProperty milkWeight;
		private SimpleDoubleProperty percentMilk;

		public FarmsModel(String month, Double milkWeight) {
			this.month = new SimpleStringProperty(month);
			this.milkWeight = new SimpleDoubleProperty(milkWeight);
		}

		public FarmsModel(String farmId, Double milkWeight, Double percentMilk) {
			this.farmId = new SimpleStringProperty(farmId);
			this.milkWeight = new SimpleDoubleProperty(milkWeight);
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

		public double getMilkWeight() {
			return milkWeight.get();
		}

		public void setMilkWeight(SimpleDoubleProperty milkWeight) {
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
	 * Gets a month index and turns it into the string name representation of that month
	 * 
	 * @param monthIndex
	 * @return string of month name
	 */
	private String getMonth(int monthIndex) {
      switch (monthIndex) {
        case 1:
          return "January";
        case 2:
          return "February";
        case 3:
          return "March";
        case 4:
          return "April";
        case 5:
          return "May";
        case 6:
          return "June";
        case 7:
          return "July";
        case 8:
          return "August";
        case 9:
          return "September";
        case 10:
          return "October";
        case 11:
          return "November";
        case 12:
          return "December";
        default:
          return null;
      }
    }

	/**
	 * Displays farm report screen
	 * 
	 * @param primaryStage
	 */
	void farmReportScreen(Stage primaryStage, String inputFarmId, int inputYear) {
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
		Label disFarmId = new Label("Farm: " + inputFarmId);
		Label year = new Label("Year: " + inputYear);
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
		TableColumn<FarmsModel, Double> milkWeight = new TableColumn<FarmsModel, Double>("Milk Weight");
		milkWeight.setCellValueFactory(new PropertyValueFactory<FarmsModel, Double>("MilkWeight"));
		milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(2));

		reportTable.getColumns().add(month);
		reportTable.getColumns().add(milkWeight);
		ObservableList<FarmsModel> farmsModels = FXCollections.observableArrayList();
		
		double[][] farmInfo = factory.getFarmReport(inputFarmId, inputYear);
		
		// put all farm info into model for display
	     for (int i = 1; i <= farmInfo[0].length; i++) {
	       for (int j = 0; j < farmInfo.length; j++) {
	         farmsModels.add(new FarmsModel(getMonth(i), farmInfo[i][j]));
	       }
	     }
		
		reportTable.setItems(farmsModels);

		root.setCenter(reportTable);

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
	void annualReportScreen(Stage primaryStage, int inputYear) {
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
		Label year = new Label("Year: " + inputYear);
		year.setId("report-info");
		info.getChildren().add(year);
		top.add(info, 0, 1);

		root.setTop(top);

		// table for report data
		TableView<FarmsModel> reportTable = new TableView<FarmsModel>();
		reportTable.autosize();

		// set up columns
		TableColumn<FarmsModel, String> farmId = new TableColumn<FarmsModel, String>("Farm ID");
		farmId.setCellValueFactory(new PropertyValueFactory<>("farmId"));
		farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
		TableColumn<FarmsModel, Double> milkWeight = new TableColumn<FarmsModel, Double>("Milk Weight");
		milkWeight.setCellValueFactory(new PropertyValueFactory<>("milkWeight"));
		milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
		TableColumn<FarmsModel, Double> percentMilk = new TableColumn<FarmsModel, Double>("% of Total Milk");
		percentMilk.setCellValueFactory(new PropertyValueFactory<>("percentMilk"));
		percentMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(3));

		reportTable.getColumns().add(farmId);
		reportTable.getColumns().add(milkWeight);
		reportTable.getColumns().add(percentMilk);

		ObservableList<FarmsModel> farmsModels = FXCollections.observableArrayList();
		reportTable.setItems(farmsModels);

		root.setCenter(reportTable);

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
	void monthlyReportScreen(Stage primaryStage, int inputYear, String inputMonth) {
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
		Label month = new Label(
				"Month: " + inputMonth.substring(0, 1).toUpperCase() + inputMonth.substring(1).toLowerCase());
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

		// table for report data
		TableView<FarmsModel> reportTable = new TableView<FarmsModel>();

		// set up columns
		TableColumn<FarmsModel, String> farmId = new TableColumn<FarmsModel, String>("Farm ID");
		farmId.setCellValueFactory(new PropertyValueFactory<>("FarmId"));
		farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
		TableColumn<FarmsModel, Double> milkWeight = new TableColumn<FarmsModel, Double>("Milk Weight");
		milkWeight.setCellValueFactory(new PropertyValueFactory<>("MilkWeight"));
		milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
		TableColumn<FarmsModel, Double> percentMilk = new TableColumn<FarmsModel, Double>("% of Total Milk");
		percentMilk.setCellValueFactory(new PropertyValueFactory<>("PercentMilk"));
		percentMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(3));

		reportTable.getColumns().add(farmId);
		reportTable.getColumns().add(milkWeight);
		reportTable.getColumns().add(percentMilk);

		// mock data
		ObservableList<FarmsModel> farmsModels = FXCollections.observableArrayList();
		reportTable.setItems(farmsModels);

		root.setCenter(reportTable);

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
	void dateRangeReportScreen(Stage primaryStage, LocalDate startDateObject, LocalDate endDateObject) {
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
		Label startDate = new Label("Start Date: " + startDateObject.getMonth() + " " + startDateObject.getDayOfMonth()
				+ ", " + startDateObject.getYear());
		Label endDate = new Label("End Date: " + endDateObject.getMonth() + " " + endDateObject.getDayOfMonth() + ", "
				+ endDateObject.getYear());
		startDate.setId("report-info");
		endDate.setId("report-info");
		info.add(startDate, 0, 0);
		info.add(endDate, 1, 0);
		top.add(info, 0, 1);
		info.setHgap(20);
		GridPane.setHalignment(startDate, HPos.CENTER);
		GridPane.setHalignment(endDate, HPos.CENTER);
		root.setTop(top);

		// table for report data
		TableView<FarmsModel> reportTable = new TableView<FarmsModel>();

		// set up columns
		TableColumn<FarmsModel, String> farmId = new TableColumn<FarmsModel, String>("Farm ID");
		farmId.setCellValueFactory(new PropertyValueFactory<>("farmId"));
		farmId.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
		TableColumn<FarmsModel, Double> milkWeight = new TableColumn<FarmsModel, Double>("Milk Weight");
		milkWeight.setCellValueFactory(new PropertyValueFactory<>("milkWeight"));
		milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(3));
		TableColumn<FarmsModel, String> percentMilk = new TableColumn<FarmsModel, String>("% of Total Milk");
		percentMilk.setCellValueFactory(new PropertyValueFactory<>("percentMilk"));
		percentMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(3));

		reportTable.getColumns().add(farmId);
		reportTable.getColumns().add(milkWeight);
		reportTable.getColumns().add(percentMilk);

		// mock data
		ObservableList<FarmsModel> farmsModels = FXCollections.observableArrayList();
		reportTable.setItems(farmsModels);

		root.setCenter(reportTable);

		// back button
		Button backButton = new Button("Back");
		backButton(root, primaryStage, backButton);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Creates a back button and the functionality to go back to a specific page
	 * 
	 * @param root
	 * @param primaryStage
	 * @param backButton
	 */

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