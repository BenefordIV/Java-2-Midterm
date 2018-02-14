/**
 * @author benst
 * This code will use the ObjectInputStream of the FileInputStream of the text file that I had created in the BSJava2Midterm project.
 * It looks at the file and will run a loop, displaying information based on the amount of times that a person entered in their information.
 * I ran into a small problem that Tim had helped me out with in that when I would run this code, when it got to the last instance of an object in the file
 * it would run an infinite loop on that instance and clog the screen. To fix this problem he told me to add a break; statement to my exception in my do loop.
 * 
 * There is one thing that I don't like about the code and that is I used catch all exceptions becuase I was getting pretty annoyed on picking which one I had to use
 * 
 * This code you can either run seperatly through this project, or you could run it through the "Print" button on the other class which is my personal intended way of 
 * using the program.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class BSJava2MidtermPart2 {
	public static void main(String[] args){
		File file = new File("BenSteierMidterm.txt");
		//Create the object output stream that takes a file input stream
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
			try{
				//Create an object variable that is equal to the OIS.readObject() method
				Object o = input.readObject();

				//do/while loop that will take the information that is found from the objects in the file and print them out to the console (I originally did alerts for this but
				//quickly realized that if you entered in a lot of numbers you would in part get A LOT of alerts. And I didn't feel like dinking around with this loop to figure out
				//how to make it work with Alerts.
				do{
					//An instance of the UserInfo that is cast to the object that is input.readObject();
					UserInfo ui = (UserInfo)o;
					//Print out the information that is needed in a nice format.
					System.out.println("The number entered: " + ui.getUserNumber() + " The Total at this point: " + ui.getTotalNumber() + " The time this was entered: " + ui.getTime()
					+ "\n" + "-----------------");
					try{
						o = input.readObject();
					} catch(Exception e){
						//break statement that was giving me problems
						break;
					}
				} while (o instanceof UserInfo);
			} catch(Exception e){
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
