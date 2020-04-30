package application;

import static java.util.Map.entry;    

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.beans.value.ChangeListener;

import backend.CheeseFactory;
import backend.DataOperation;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	
	private static int numImported = 0;

	@Override
	public void start(Stage primaryStage) {
		homeScreen(primaryStage);
	}

	void homeScreen(Stage stage) {
		try {
//		  Image cowPrint = new Image("cow_print.jpg");
          //create background Image with cowPrint Image
//          BackgroundImage background_image = new BackgroundImage(cowPrint, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT); 
//          Background background = new Background(background_image); 

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
			combo_box.getItems().addAll("Farm", "Annual", "Monthly", "Date Range");
			combo_box.getSelectionModel().selectFirst();

			reportHBox.getChildren().add(reportSelectLabel);
			reportHBox.getChildren().add(combo_box);

			Button selectReportButton = new Button("Generate");
			selectReportButton.setFont(buttonFont);

			// Select Files label, button, and file chooser
			Label importLabel = new Label("Import Data");
			importLabel.setFont(titleFont);

			FileChooser file_chooser = new FileChooser();

			Label selectFilesLabel = new Label("no files loaded");

			Button selectFileButton = new Button("Import .csv");
			selectFileButton.setFont(buttonFont);

			// File exporter
			Label exportLabel = new Label("Export Data");
			exportLabel.setFont(titleFont);

			Label exportSuccessLabel = new Label("no files exported");
			FileChooser exportChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            exportChooser.getExtensionFilters().add(extFilter);

			Button exportButton = new Button("Export .csv");
			exportButton.setFont(buttonFont);
			
			Button clearAllButton = new Button("Clear all Data");
			clearAllButton.setFont(buttonFont);

			//Title, fields, and button for manually inserting data
            Label insertLabel = new Label("Insert or Remove Data");
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
            datePicker.getEditor().setDisable(true);
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
					int maxLength = 9;
  					if (newValue.length() > maxLength) {
  		                String s = newValue.substring(0, maxLength);
  		                milkField.setText(s);
  		            }
				}
            });
			HBox milkInfoHBox = new HBox();
			milkInfoHBox.getChildren().add(milkLabel);
			milkInfoHBox.setMargin(milkLabel, new Insets(0, 14, 0, 0));
			milkInfoHBox.getChildren().add(milkField);

			Button insertDataButton = new Button("Insert Data");
			insertDataButton.setFont(buttonFont);
			
			Button removeDataButton = new Button("Remove Data");
			removeDataButton.setFont(buttonFont);
			
			Label historyLabel = new Label("History");
			historyLabel.setFont(labelFont);
			
			ListView<DataOperation> historyListView = new ListView<>();
			historyListView.setItems(factory.history);
			historyListView.setCellFactory(list -> {
				ListCell<DataOperation> cell = new ListCell<DataOperation>() {
			    @Override
			    protected void updateItem(DataOperation op, boolean empty) {
			        super.updateItem(op, empty);

			        if (empty || op == null) {
			            setText(null);
			        } else {
			            setText(op.toString());
			        }
			    	}
				};
				return cell;
			});
			
			Button undoButton = new Button("Undo");
			undoButton.setFont(buttonFont);
			
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
                      
                      Label  farmIDLabel= new Label("Farm Id:");
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
                      yearTextField.textProperty().addListener(new ChangeListener<String>() {
          				@Override
          				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
          					if (!newValue.matches("\\d*")) {
          			            yearTextField.setText(newValue.replaceAll("[^\\d]", ""));
          			        }
          					int maxLength = 9;
          					if (newValue.length() > maxLength) {
          		                String s = newValue.substring(0, maxLength);
          		                yearTextField.setText(s);
          		            }
          				}
                      });
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
 
//                      selectedReportvBox.setBackground(background);

						// Event Handler for file Selections
						EventHandler<ActionEvent> callFarm = new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								if (farmIdTextField.getText().isEmpty()) {
									Alert errorAlert = new Alert(AlertType.ERROR);
									errorAlert.setHeaderText(null);
									if (yearTextField.getText().isEmpty())
										errorAlert.setContentText("Please Enter a Valid Farm ID and Year.");
									else
										errorAlert.setContentText("Please Enter a Valid Farm ID.");
									errorAlert.showAndWait();
									return;
								}
								if (yearTextField.getText().isEmpty()) {
									Alert errorAlert = new Alert(AlertType.ERROR);
									errorAlert.setHeaderText(null);
									errorAlert.setContentText("Please Enter a Valid Year.");
									errorAlert.showAndWait();
									return;
								}
								farmReportScreen(stage, farmIdTextField.getText(), Integer.parseInt(yearTextField.getText()));
								
							}
						};
						generateFarmButton.setOnAction(callFarm);

						break;
					case "Annual":
                    	selectedReportvBox.setAlignment(Pos.TOP_CENTER);
                    	selectedReportLabel = new Label("Annual Report");
                    	
                    	yearLabel= new Label("Enter Year:");
                    	yearTextField = new TextField ();
                    	yearTextField.textProperty().addListener(new ChangeListener<String>() {
            				@Override
            				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            					if (!newValue.matches("\\d*")) {
            			            yearTextField.setText(newValue.replaceAll("[^\\d]", ""));
            			        }
            					int maxLength = 9;
              					if (newValue.length() > maxLength) {
              		                String s = newValue.substring(0, maxLength);
              		                yearTextField.setText(s);
              		            }
            				}
                        });
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

//                        selectedReportvBox.setBackground(background);
                        
                    	// Event Handler for file Selections
                        EventHandler<ActionEvent> callAnnual =  new EventHandler<ActionEvent>() { 
                            public void handle(ActionEvent e) 
                            {
								try {
	                            	annualReportScreen(stage, Integer.parseInt(yearTextField.getText()));
								}catch(Exception excep) {
									excep.printStackTrace();
									Alert errorAlert = new Alert(AlertType.ERROR);
									errorAlert.setHeaderText(null);
									if(yearTextField.getText().trim().isEmpty()) {
										errorAlert.setContentText("Please Enter a Valid Year");
									}
									errorAlert.showAndWait();
								}
                            }
                        }; 
                        generateAnnualButton.setOnAction(callAnnual);
                        break; 

					case "Monthly":
						selectedReportLabel = new Label("Monthly Report");
						selectedReportLabel.setFont(titleFont);
						
						yearLabel = new Label("Enter Year:");
						yearLabel.setFont(labelFont);
                        yearTextField = new TextField();
                    	yearTextField.textProperty().addListener(new ChangeListener<String>() {
            				@Override
            				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            					if (!newValue.matches("\\d*")) {
            			            yearTextField.setText(newValue.replaceAll("[^\\d]", ""));
            			        }
            					int maxLength = 9;
              					if (newValue.length() > maxLength) {
              		                String s = newValue.substring(0, maxLength);
              		                yearTextField.setText(s);
              		            }
            				}
                        });
                        HBox yearInfoHBox2 = new HBox();
                        yearInfoHBox2.getChildren().add(yearLabel);
                        yearInfoHBox2.setMargin(yearLabel, new Insets(0,6,0,0));
                        yearInfoHBox2.getChildren().add(yearTextField);
                        yearInfoHBox2.setAlignment(Pos.CENTER);
						
						ComboBox<String> comboBox = new ComboBox<>();
						
						ObservableList<String> months = FXCollections.observableArrayList(
								"January",
								"February",
								"March",
								"April",
								"May",
								"June",
								"July",
								"August",
								"September",
								"October",
								"November",
								"December"
						);
						
						comboBox.setItems(months);
						
						comboBox.getSelectionModel().selectFirst();
						
						

						monthLabel = new Label("Select Month:");
						monthLabel.setFont(labelFont);
						HBox monthLabelHBox = new HBox();
						monthLabelHBox.getChildren().add(monthLabel);
						monthLabelHBox.setMargin(monthLabel, new Insets(0,6,0,0));
						monthLabelHBox.getChildren().add(comboBox);
						monthLabelHBox.setAlignment(Pos.CENTER);
						
						Button generateMonthlyButton = new Button("Generate Monthly Report");
						backButton = new Button("Back");

						selectedReportvBox.getChildren().add(selectedReportLabel);
						selectedReportvBox.getChildren().add(yearInfoHBox2);
						selectedReportvBox.getChildren().add(monthLabelHBox);
						selectedReportvBox.setSpacing(5);
						selectedReportvBox.getChildren().add(generateMonthlyButton);

						selectedReportvBox.getChildren().add(backButton);
						selectedReportvBox.setAlignment(Pos.TOP_CENTER);

						backButton.setOnAction(back);
						
//						selectedReportvBox.setBackground(background);

						// Event Handler for file Selections
						EventHandler<ActionEvent> callMonthly = new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								try {
									Integer year = Integer.valueOf(yearTextField.getText());
									Map<String, Integer> monthMap = Map.ofEntries(
											entry("January",1),
											entry("February",2),
											entry("March",3),
											entry("April",4),
											entry("May",5),
											entry("June",6),
											entry("July",7),
											entry("August",8),
											entry("September",9),
											entry("October",10),
											entry("November",11),
											entry("December",12)
									);
									monthlyReportScreen(stage, year, monthMap.get(comboBox.getValue()));
								}catch(Exception excep) {
									excep.printStackTrace();
									Alert errorAlert = new Alert(AlertType.ERROR);
									errorAlert.setHeaderText(null);
									if(yearTextField.getText().trim().toString().isEmpty()) {
										errorAlert.setContentText("Please Enter a Valid Year");	
									}else {
										errorAlert.setContentText("Please Select a Month");
									}
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
						startDatePicker.getEditor().setDisable(true);
			            startDatePicker.setValue(LocalDate.now());

						DatePicker endDatePicker = new DatePicker();
						endDatePicker.getEditor().setDisable(true);
			            endDatePicker.setValue(LocalDate.now());

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
						
//						selectedReportvBox.setBackground(background);

						// Event Handler for file Selections
						EventHandler<ActionEvent> callDateRange = new EventHandler<ActionEvent>() {
							public void handle(ActionEvent e) {
								try {
									LocalDate startDate = startDatePicker.getValue();
									LocalDate endDate = endDatePicker.getValue();
									if(endDate.isBefore(startDate)) {
										throw new IllegalArgumentException();
									}	
									dateRangeReportScreen(stage, startDatePicker.getValue(), endDatePicker.getValue());
								}catch(Exception error) {
									Alert errorAlert = new Alert(AlertType.ERROR);
									errorAlert.setHeaderText(null);
									if(startDatePicker.getValue() == null) {
										errorAlert.setContentText("Please Select a Valid Start Date");
									}
									else if(endDatePicker.getValue() == null) {
										errorAlert.setContentText("Please Select a Valid End Date");
									}
									else {
										errorAlert.setContentText("End Date is Before Start Date");
									}
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
					List<File> fileList = file_chooser.showOpenMultipleDialog(stage);
					if(fileList != null) {
						List<File> failedList = new ArrayList<>();
						for(File file : fileList) {

							try {
								factory.importFarmData(file.getAbsolutePath());
								++numImported;
								selectFilesLabel.setText(numImported + ((numImported == 1) ? " file imported" : " files imported"));
							
							} catch (Exception a) {
								failedList.add(file);
							}
						}
						if (!failedList.isEmpty()) {
							String errMsg = "Failed to load files:\n";
							for (int i = 0; i < failedList.size() - 1; ++i) {
								errMsg += failedList.get(i) + "\n";
							}
							errMsg += failedList.get(failedList.size() - 1);
							
							Alert errorAlert = new Alert(AlertType.ERROR);
							errorAlert.setHeaderText(null);
							errorAlert.setContentText(errMsg);
							errorAlert.showAndWait();
						} else {
							Alert errorAlert = new Alert(AlertType.INFORMATION);
							errorAlert.setHeaderText(null);
							if (fileList.size() == 1)
								errorAlert.setContentText("Successfully loaded 1 file.");
							else
								errorAlert.setContentText("Successfully loaded " + fileList.size() + " files.");
							errorAlert.showAndWait();
						}
					}
				}
			};

			// Event Handler for file exports
			EventHandler<ActionEvent> selectExport = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					File file = file_chooser.showSaveDialog(stage);
					if(file != null) {
						try {
							factory.exportFarmData(file.getAbsolutePath());
							exportSuccessLabel.setText(file.getName() + " exported");
						} catch (Exception a) {
							a.printStackTrace();
							selectFilesLabel.setText("Loading File Failed Select a New File");
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
            		
            		try {
            			factory.addDataPoint(farm, milk, date, true);
            			
            			Alert successAlert = new Alert(AlertType.INFORMATION, null, ButtonType.OK);
            			successAlert.setTitle("Info");
            			successAlert.setHeaderText(null);
            			successAlert.setContentText("Successfully added data point for farm: "+ farm + " on " + date.toString() +" of " + milk + "lb.");
            			
            			successAlert.showAndWait();
            		} catch (DuplicateAdditionException dae) {
            			int prev = dae.getWeight();
            			
            			Alert alert = new Alert(AlertType.WARNING, null, ButtonType.CANCEL, ButtonType.OK);
            			alert.setTitle("Warning");
            			alert.setHeaderText(null);
            			alert.setContentText("Data point for farm: "+ farm + " already exists on " + date.toString() +" for " + prev + "lb." +
            					"\nWould you like to replace this entry with "+ milk + "lb?");
            			
            			Optional<ButtonType> result = alert.showAndWait();
            			
            			if(result.isPresent() && result.get() == ButtonType.OK) {
            				factory.forceAddDataPoint(farm, milk, date, true);
            				
            				Alert successAlert = new Alert(AlertType.INFORMATION, null, ButtonType.OK);
                			successAlert.setTitle("Info");
                			successAlert.setHeaderText(null);
                			successAlert.setContentText("Successfully modified data point for farm: "+ farm + " on " + date.toString() + 
                					" from " + prev + "lb to " + milk + "lb.");
                			
                			successAlert.showAndWait();
            			}

            		}
            		
            		farmNameField.clear();
            		milkField.clear();
            		
            	}
            };
            
         // Event Handler for manually removing raw data
            EventHandler<ActionEvent> removeDataHandler = new EventHandler<ActionEvent>() {
            	public void handle(ActionEvent e) {
            		String farm = "";
            		
            		LocalDate date = null;
            		farm = farmNameField.getText();
            		date = datePicker.getValue();
            			
            		if (farm.contentEquals("")) {
            			Alert errorAlert = new Alert(AlertType.ERROR);
                		errorAlert.setHeaderText(null);
                		errorAlert.setContentText("Must enter a farm ID.");
                		errorAlert.showAndWait();
                		return;
            		}
            			
            		
            		
            		Alert confirmAlert = new Alert(AlertType.WARNING, null, ButtonType.CANCEL, ButtonType.OK);
        			confirmAlert.setTitle("Warning");
        			confirmAlert.setHeaderText(null);
        			confirmAlert.setContentText("Are you sure that you want to remove entry on "+ date + " for farm: " + farm + "?");
        			
        			Optional<ButtonType> confirmResult = confirmAlert.showAndWait();
        			
        			if(confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
            		
       					Integer prev = factory.removeDataPoint(farm, date, true);
            			
       					Alert alert = new Alert(AlertType.INFORMATION, null, ButtonType.OK);
       					alert.setTitle("Warning");
       					alert.setHeaderText(null);
       					if (prev == null)
       						alert.setContentText("Could not find data point on " + date + " at farm: " + farm + ".");
       					else
       						alert.setContentText("Successfully removed data point of " + prev + " on " + date + " at farm: " + farm + ".");
       					
        				alert.showAndWait();
            		
        				farmNameField.clear();
        				milkField.clear();
        			}
            		
            	}
            };
            
            // Event handler for the exit button
            EventHandler<ActionEvent> exitHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					Alert confirmAlert = new Alert(AlertType.WARNING, null, ButtonType.CANCEL, ButtonType.OK);
        			confirmAlert.setTitle("Warning");
        			confirmAlert.setHeaderText(null);
        			confirmAlert.setContentText("Are you sure that you want to exit the application?\n" +
        										"All unsaved data will be lost.");
        			
        			Optional<ButtonType> confirmResult = confirmAlert.showAndWait();
        			
        			if(confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
        				Stage stage = (Stage) root.getScene().getWindow();
        				stage.close();
        			}
				}
            	
            };
            
            //handler for undo button
            EventHandler<ActionEvent> undoHandler = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					if (factory.history.size() != 0) {
						DataOperation selected = factory.history.get(0);
			        	
			        	Alert confirmAlert = new Alert(AlertType.WARNING, null, ButtonType.CANCEL, ButtonType.OK);
	        			confirmAlert.setTitle("Warning");
	        			confirmAlert.setHeaderText(null);
	        			confirmAlert.setContentText("Would you like to undo this action?\n" + selected);
	        			
	        			Optional<ButtonType> confirmResult = confirmAlert.showAndWait();
	        			
	        			if(confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
	        				factory.reverseDataOperation(selected);
	        				
	        				factory.history.remove(0);
	        				
	        				Alert alert = new Alert(AlertType.INFORMATION, null, ButtonType.OK);
	       					alert.setTitle("Success");
	       					alert.setHeaderText(null);
	       					alert.setContentText("Successfully undid action:\n" + selected);
	       					
	        				alert.showAndWait();
	        			}
					} else {
						Alert alert = new Alert(AlertType.WARNING, null, ButtonType.OK);
       					alert.setTitle("Unable to Complete Action");
       					alert.setHeaderText(null);
       					alert.setContentText("No actions to undo.");
       					
        				alert.showAndWait();
					}
				}
            	
            };
            
            EventHandler<ActionEvent> clearAllHandler = new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
		        	
		        	Alert confirmAlert = new Alert(AlertType.WARNING, null, ButtonType.CANCEL, ButtonType.OK);
        			confirmAlert.setTitle("Warning");
        			confirmAlert.setHeaderText(null);
        			confirmAlert.setContentText("Do you really want to clear all imported and manually entered data?");
        			
        			Optional<ButtonType> confirmResult = confirmAlert.showAndWait();
        			
        			if(confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
        				factory.clearAllData();
        				numImported = 0;
        				selectFilesLabel.setText("no files loaded");
        				exportSuccessLabel.setText("no files exported");
        				        				
        				Alert alert = new Alert(AlertType.INFORMATION, null, ButtonType.OK);
       					alert.setTitle("Success");
       					alert.setHeaderText(null);
       					alert.setContentText("Successfully cleared all data.");
       					
        				alert.showAndWait();
        			}
				}

            };
            
      
            //set the action of the button
            selectFileButton.setOnAction(selectFile); 
            exportButton.setOnAction(selectExport);
            insertDataButton.setOnAction(insertDataHandler);
            removeDataButton.setOnAction(removeDataHandler);
            undoButton.setOnAction(undoHandler);
            clearAllButton.setOnAction(clearAllHandler);
            
            // add exit button
            HBox exitHBox = new HBox();
            Button exitButton = new Button("Exit");
            exitButton.setFont(new Font(16));
            exitHBox.getChildren().add(exitButton);
            exitButton.setOnAction(exitHandler);

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
			
			Separator lowerHorizontalSeparator = new Separator(Orientation.HORIZONTAL);
			lVBox.getChildren().add(lowerHorizontalSeparator);
			lVBox.setMargin(lowerHorizontalSeparator, new Insets(24, 0, 0, 0));
			lVBox.getChildren().add(clearAllButton);

			cVBox.getChildren().add(reportLabel);
			cVBox.setMargin(reportLabel, new Insets(0, 0, 12, 0));
			cVBox.getChildren().add(reportHBox);
			cVBox.getChildren().add(selectReportButton);
			Separator chSeparator = new Separator(Orientation.HORIZONTAL);
			cVBox.getChildren().add(chSeparator);
			cVBox.setMargin(chSeparator, new Insets(192, 0, 0, 0));
			cVBox.getChildren().add(exitHBox);
            exitHBox.setAlignment(Pos.BOTTOM_CENTER);

			rVBox.getChildren().add(insertLabel);
			rVBox.setMargin(insertLabel, new Insets(0, 0, 12, 0));
			rVBox.getChildren().add(farmInfoHBox);
			rVBox.getChildren().add(dateInfoHBox);
			rVBox.getChildren().add(milkInfoHBox);
			
			HBox insertRemoveHBox = new HBox(8);
			insertRemoveHBox.getChildren().add(insertDataButton);
			insertRemoveHBox.getChildren().add(removeDataButton);
			insertRemoveHBox.setMargin(insertDataButton, new Insets(0, 0, 0, 24));

			rVBox.getChildren().add(insertRemoveHBox);
			rVBox.getChildren().add(historyLabel);
			rVBox.getChildren().add(historyListView);
			rVBox.getChildren().add(undoButton);
			
			// add children to hbox
			root.setLeft(lVBox);
			root.setMargin(lVBox, new Insets(12, 0, 12, 36));
			root.setCenter(cHBox);
			root.setMargin(cHBox, new Insets(12, 12, 12, 12));
			root.setRight(rVBox);
			root.setMargin(rVBox, new Insets(12, 42, 12, 0));
//			root.setBottom(buttonHBox);

			// set the scene and start the show
			stage.setScene(mainScene);
			stage.show();
		}

		catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}

	public class FarmsModel {
		private SimpleStringProperty info;
		private SimpleStringProperty month;
		private SimpleDoubleProperty milkWeight;
		private SimpleDoubleProperty percentMilk;
		private SimpleDoubleProperty avgMilk;
		private SimpleDoubleProperty minMilk;
		private SimpleDoubleProperty maxMilk;

		public FarmsModel(String info, Double milkWeight, Double percentMilk, Double avgMilk, Double minMilk, Double maxMilk) {
			this.info = new SimpleStringProperty(info);
			this.milkWeight = new SimpleDoubleProperty(milkWeight);
			this.percentMilk = new SimpleDoubleProperty(percentMilk);
			this.avgMilk = new SimpleDoubleProperty(avgMilk);
			this.minMilk = new SimpleDoubleProperty(minMilk);
			this.maxMilk = new SimpleDoubleProperty(maxMilk);
		}
		

        public String getInfo() {
            return info.get();
        }

        public void setInfo(SimpleStringProperty info) {
            this.info = info;
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

        public double getAvgMilk() {
            return avgMilk.get();
        }

        public void setAvgMilk(SimpleDoubleProperty avgMilk) {
            this.avgMilk = avgMilk;
        }
        
        public double getMinMilk() {
          return minMilk.get();
        }

        public void setMinMilk(SimpleDoubleProperty minMilk) {
          this.minMilk = minMilk;
        }
        
        public double getMaxMilk() {
          return maxMilk.get();
        }

        public void setMaxMilk(SimpleDoubleProperty maxMilk) {
          this.maxMilk = maxMilk;
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
      primaryStage.setResizable(false);

      TableView<FarmsModel> reportTable = reportTable("farm", inputFarmId, inputYear, -1, null, null);
      
      double totalMilkWeight = 0.0;
      double[][] farmInfo = factory.getFarmReport(inputFarmId, inputYear);
      
      for (int i = 0; i < farmInfo.length; i++) {
        totalMilkWeight += farmInfo[i][0];
      }
      
      root.setCenter(reportTable);

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
      FlowPane information = new FlowPane();
      Label disFarmId = new Label("Farm: " + inputFarmId);
      Label year = new Label("Year: " + inputYear);
      Label totalWeight = new Label("Total weight: " + totalMilkWeight);
      disFarmId.setId("report-info");
      year.setId("report-info");
      totalWeight.setId("report-info");
      information.getChildren().addAll(disFarmId, year, totalWeight);
      top.add(information, 0, 1);
      information.setHgap(20);
      information.setAlignment(Pos.CENTER);

      root.setTop(top);      
      
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
		TableView<FarmsModel> reportTable = reportTable("annual", null, inputYear, -1, null, null);

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
	void monthlyReportScreen(Stage primaryStage, int inputYear, int inputMonth) {
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
		
		Label month = new Label("Month: " + getMonth(inputMonth));

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
		TableView<FarmsModel> reportTable = reportTable("monthly", null, inputYear, inputMonth, null, null);	

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

		TableView<FarmsModel> reportTable = reportTable("date", null, -1, -1, startDateObject, endDateObject);
		root.setCenter(reportTable);

		// back button
		Button backButton = new Button("Back");
		backButton(root, primaryStage, backButton);

		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private TableView<FarmsModel> reportTable(String reportType, String farmId, int inputYear, int inputMonth, LocalDate startDateObject, LocalDate endDateObject) {
      TableView<FarmsModel> reportTable = new TableView<FarmsModel>(); // table for report data
      
      String infoType;
      if (reportType.equals("farm")) {
        infoType = "Month";
      } else {
        infoType = "Farm ID";
      }

      // set up columns
      TableColumn<FarmsModel, String> info = new TableColumn<FarmsModel, String>(infoType);
      info.setCellValueFactory(new PropertyValueFactory<>("info"));
      info.prefWidthProperty().bind(reportTable.widthProperty().divide(6));
      
      // sort annual, monthly, and date range reports alphabetically
      if (!reportType.equals("farm")) {
        info.setSortType(TableColumn.SortType.ASCENDING);
      }
      
      TableColumn<FarmsModel, Double> milkWeight = new TableColumn<FarmsModel, Double>("Milk Weight");
      milkWeight.setCellValueFactory(new PropertyValueFactory<>("milkWeight"));
      milkWeight.prefWidthProperty().bind(reportTable.widthProperty().divide(6));
      
      TableColumn<FarmsModel, Double> percentMilk = new TableColumn<FarmsModel, Double>("% of Total Milk");
      percentMilk.setCellValueFactory(new PropertyValueFactory<>("percentMilk"));
      percentMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(6));
      
      TableColumn<FarmsModel, Double> avgMilk = new TableColumn<FarmsModel, Double>("Average");
      avgMilk.setCellValueFactory(new PropertyValueFactory<>("avgMilk"));
      avgMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(6));
      
      TableColumn<FarmsModel, Double> minMilk = new TableColumn<FarmsModel, Double>("Minimum");
      minMilk.setCellValueFactory(new PropertyValueFactory<>("minMilk"));
      minMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(6));
      
      TableColumn<FarmsModel, Double> maxMilk = new TableColumn<FarmsModel, Double>("Maximum");
      maxMilk.setCellValueFactory(new PropertyValueFactory<>("maxMilk"));
      maxMilk.prefWidthProperty().bind(reportTable.widthProperty().divide(6));

      // add columns to table
      reportTable.getColumns().add(info);
      reportTable.getColumns().add(milkWeight);
      reportTable.getColumns().add(percentMilk);
      reportTable.getColumns().add(avgMilk);
      reportTable.getColumns().add(minMilk);
      reportTable.getColumns().add(maxMilk);
      
	  ObservableList<FarmsModel> farmsModels = FXCollections.observableArrayList();
	  DecimalFormat decFormat = new DecimalFormat("0.00"); // format percent double values
	  
      switch (reportType) {
        case "farm":
          double[][] farmInfo = factory.getFarmReport(farmId, inputYear);
          
          // put all farm info into model for display
          for (int i = 0; i < farmInfo.length; i++) {
            if (Double.isNaN((farmInfo[i][1]))) {
              farmInfo[i][1] = Double.parseDouble(decFormat.format(0.00));
            } else {
              farmInfo[i][1] = Double.parseDouble(decFormat.format(farmInfo[i][1]));
            }
            
            farmsModels.add(new FarmsModel(getMonth(i + 1), farmInfo[i][0], farmInfo[i][1], farmInfo[i][2], farmInfo[i][3], farmInfo[i][4]));
          }
          break;
        case "annual":
          HashMap<String, double[]> annualInfo = factory.getAnnualReport(inputYear);
          
          // add data to model
          annualInfo.forEach((id, milkInfo) -> {
            milkInfo[1] = Double.parseDouble(decFormat.format(milkInfo[1]));
            farmsModels.add(new FarmsModel(id, milkInfo[0], milkInfo[1], milkInfo[2], milkInfo[3], milkInfo[4]));
          });
          break;
        case "monthly":
          HashMap<String, double[]> monthlyInfo = factory.getMonthlyReport(inputYear, inputMonth);
          
          // add data to model
          monthlyInfo.forEach((id, milkInfo) -> {
            milkInfo[1] = Double.parseDouble(decFormat.format(milkInfo[1]));
            farmsModels.add(new FarmsModel(id, milkInfo[0], milkInfo[1], milkInfo[2], milkInfo[3], milkInfo[4]));
          });
          break;
        case "date":
          HashMap<String, double[]> rangeInfo = factory.getDateRangeReport(startDateObject, endDateObject);     
          
          // add data to model
          rangeInfo.forEach((id, milkInfo) -> {
            milkInfo[1] = Double.parseDouble(decFormat.format(milkInfo[1]));
            farmsModels.add(new FarmsModel(id, milkInfo[0], milkInfo[1], milkInfo[2], milkInfo[3], milkInfo[4]));
          });
          break;
        default:
          break;
      }
      
      // add data to table
      reportTable.setItems(farmsModels);
      if (!reportType.equals("farm")) {
        reportTable.getSortOrder().add(info);
      }
      
      return reportTable;
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
		buttonHBox.setPadding(new Insets(5, 0, 5, 0));
		backButton.setMaxHeight(200);
//		backButton.setId("back-button");
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