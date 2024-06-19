
public class Personnel {
	String name;
	String surname;
	String registerNumber;
	String position;
	int yearOfStart;
	int currentYear=2023;
	double severancePay;
	int[]weeks;
	public String getName() {return name;}
	public String getSurname() {return surname;}
	public String getRegisterNumber() {return registerNumber;}
	public String getPosition() {return position;}
	public int getYearOfStart() {return yearOfStart;}
	public int[] getWeeks() {return weeks;}
	public double getSeverancePay() {return severancePay;}
	public void setName(String newName) {this.name=newName;}
	public void setSurname(String newSurname) {this.surname=newSurname;}
	public void setRegisterNumber(String newRegisterNumber) {this.registerNumber=newRegisterNumber;}
	public void setPosition(String newPosition) {this.position=newPosition;}
	public void setYearOfStart(int newYearOfStart) {this.yearOfStart=newYearOfStart;}
	public void setWeeks(int[] newWeeks) {this.weeks=newWeeks;}
	public void setSeverancePay() {this.severancePay=(currentYear-yearOfStart)*20*0.8d;}}