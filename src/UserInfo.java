import java.io.Serializable;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;


public class UserInfo implements Serializable {
	int USERNUMBER;
	int TOTALNUMBER;
	String time;
	
	
	
	
	public UserInfo(int number, int total, String timer){
		USERNUMBER = number;
		TOTALNUMBER = total;
		time = timer;
	}
	
	//getters
	public int getUserNumber(){
		return USERNUMBER;
	}
	
	public int getTotalNumber(){
		return TOTALNUMBER;
	}
	
	public String getTime(){
		return time;
	}

//setters
	public void setUserNumber(int number){
		USERNUMBER = number;
	}
	
	public void setTotalNumber(int total){
		TOTALNUMBER = total;
	}
	
	public void setTime(String timer){
		time = timer;
	}
}
