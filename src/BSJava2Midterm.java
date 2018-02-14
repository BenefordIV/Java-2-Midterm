import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/***
 * A lot of my normal synopsis for this program is in my "Instructions" alert button event. So I won't delve too much into that up here. There is a few things that I added.
 * First being my start method throwing NullPointerException. I did this becuase there were a couple of times where for some reason that exception would be thrown and it was getting
 * kind of annoying. The next is the buttons that will show how to use the program and display the information in the file. I thought these would be nice from a user standpoint
 * since I didn't have clear labels in my code that tell you how to run the program. 
 * @author benst
 *
 */

public class BSJava2Midterm extends Application {
//Class Level variables that will be called later in the program
	File file = new File("BenSteierMidterm.txt");
	int userNumber;
	int total;
	Label quickInstructions = new Label("Enter a number between 1 and 100");
	Button inDepthInstructions = new Button("Instructions");
	TextField taUserInfo = new TextField();
	Button printInfo = new Button("Print");
//Tim told me to use the Calendar class instead of LocalTime and I questioned it but I like it more
	Calendar calendar;

	TextField time = new TextField();
//Pane for later in the code
	Pane pane = new Pane();

	//start method
	@Override
	public void start(Stage ps) throws NullPointerException{
//simple deletion of the file so that everytime you run the program it will delete the previous file. I think I may peck around with having multiple windows on this
		//program and asking the user if they wish to delete the file to start things out with or if they wish to continue.
		if(file.exists())
			file.delete();

		//Create a thread that will dynamically update the time label with current time
		//I did need to have help on this because I'm still not the most comfortable with Threads. But 
		Thread timeThread = new Thread(() ->{
			try{
				while(true){
					calendar = Calendar.getInstance();
					//every second these will update.
					int hour = calendar.get(Calendar.HOUR_OF_DAY);
					int minute = calendar.get(Calendar.MINUTE);
					int second = calendar.get(Calendar.SECOND);

					//update the text of the label with the new times every second
					time.setText(hour + ":" +minute+":" + second);

					Thread.sleep(1000);
				}
			} catch(InterruptedException IE){

			}
			catch(NullPointerException npe){

			}
		});
		//Starting the thread right away because why not.
		timeThread.start();
		
		//Create the object output stream in a try catch block
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

			//Event handler that is triggered when a user presses Enter
			taUserInfo.setOnKeyPressed(e -> {
				if(e.getCode() == KeyCode.ENTER){
					//try catch block to see if the number in the userInfo textarea is actually 
					//an integer. If not it will throw an exception and give an error message.
					try{
						userNumber = Integer.parseInt(taUserInfo.getText());
					} catch(NumberFormatException fe){
						Alert error3 = new Alert(AlertType.ERROR);
						error3.setContentText("You need to enter a number between 1 and 100");
						error3.show();
					}
					//if the user's number is between 1 and 100 it will update the total with the new number 
					if(userNumber >= 1 && userNumber <= 100){
						//Total goes from being 0 to being new number + previous numbers
						total += userNumber;
						//Create an instance object of the UserInfo class that takes three parameters (number input, total, and the string for the current time).
						UserInfo uf = new UserInfo(userNumber, total, calendar.getTime().toString());
						//Clear the area because it feels better when running the program.
						taUserInfo.clear();
					
						//if all the criteria is met an instance object of the UserInfo class will be made that will take the parameters of the userNumber, total, and the time.ToString()
						//while also increasing the total by the amount the user has entered + previous amounts.
						//then it will use the ObjectOutputStream to write the object to the file BenSteierMidterm.txt.
						try {
							oos.writeObject(uf);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					else {
						Alert error4 = new Alert(AlertType.INFORMATION);
						error4.setContentText("Please enter a number between 1 and 100");
						error4.show();
					}
				}
			});
			//catch blocks for creating the ObjectOutputStream.
		} catch (FileNotFoundException e) {
			Alert error1 = new Alert(AlertType.ERROR);
			error1.setContentText("File not found");
			e.printStackTrace();
			error1.show();
		} catch (IOException e) {
			Alert error2 = new Alert(AlertType.ERROR);
			error2.setContentText("IOException Thrown");
			e.printStackTrace();
			error2.show();
		}
		catch(NullPointerException npe){

		}


		//just a little tidbit thing that I cooked up to have the user get a better time with the code. It's just instructions and the use of the code.
		inDepthInstructions.setOnMouseClicked(e -> {
			Alert information = new Alert(AlertType.INFORMATION);
			information.setContentText("1.) Press Enter to store a number between 1 and 100 into a file. \n"
					+ "2.) If you wish to see the contents of the file press the \"Print\" button and the console will display the results. \n"
					+ "This program uses the ObjectOutputStream object with a FileOutputStream object to place an object into a file.\n "
					+ "At the same time it uses the Calender object class to dynamically update with the time of day. When the user presses\n"
					+ " enter it will take the number that is in the text box, compare it to see if it is between 1 and 100, and then create an object\n"
					+ " of the UserInfo class that will store the number they enter, update the total to be the number they just entered plus the previous \n"
					+ "and then populate the time that the number was entered into the console.");
			information.show();
		});
		
		//Print button that I added that will pull the main method from my other class and prints to the console all of the information you need to see from the object file.
		printInfo.setOnMouseClicked(e ->{
			BSJava2MidtermPart2.main(null);
		});

		//simple borderpane and gui setup
		BorderPane bp = new BorderPane();

		bp.setLeft(quickInstructions);
		bp.setCenter(taUserInfo);
		bp.setBottom(inDepthInstructions);
		bp.setTop(time);
		bp.setRight(printInfo);

		pane.getChildren().add(bp);

		Scene scene = new Scene(pane, 400, 100);
		ps.setTitle("Ben Steier Midterm");
		ps.setScene(scene);
		ps.show();
 

	}

	//stop method to close all threads
	public void stop(){
		System.exit(0);
	}

	//main method to launch args
	public static void main(String[] args){
		launch(args);
	}
}

