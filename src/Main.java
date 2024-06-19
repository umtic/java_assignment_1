import java.io.*;
import java.util.*;
public class Main {
	//Useful methods to simplify other methods.
	public static String read(String file) {
		String output="";
		try {
			File text=new File(file);
			Scanner reader=new Scanner(text);
			while (reader.hasNextLine()) {
				String data=reader.nextLine();
				output+=data+"\n";}
			reader.close();
		} catch (FileNotFoundException error1) {
			System.out.println("File" + file + "not found");
			error1.printStackTrace();}
		return output;}
	public static void write(String file,String input) {
		try {
			FileWriter writer=new FileWriter(file);
			writer.write(input);
			writer.close();
		} catch (IOException error2) {
			System.out.println("An error occured.");
			error2.printStackTrace();}}
	public static int overWorkSalaryCalculation(int workingHours,int overWork,int overWorkLimit,int[]weeks) {
		//This method is used to calculate overtime salaries of faculty members, officers and full time employees..
		int overWorkSalary=0;
		for (int i=0;i<weeks.length;i++) {
			if (weeks[i]>workingHours) {
				if (weeks[i]<workingHours+overWorkLimit) {overWorkSalary+=(weeks[i]-workingHours)*overWork;}
				else {overWorkSalary+=overWorkLimit*overWork;}}}
		return overWorkSalary;}
	public static int baseSalaryCalculation(int hourOfWorkPay,int[]weeks) {
		//This method is used to calculate base salaries of security and part time employees.
		int baseSalary=0;
		for(int i=0;i<weeks.length;i++) {baseSalary+=weeks[i]*hourOfWorkPay;}
		return baseSalary;}
	public static double salaryCalculation(Personnel entry) {
		//Categorizes entries using up and down casting and calculates the salaries using the two methods above.
		double salary=0;
		String position=entry.getPosition();
		entry.setSeverancePay();
		double severancePay=entry.getSeverancePay();
		salary+=severancePay;
		int[]weeks=entry.getWeeks();
		int weekOfWork=weeks.length;
		if(position.equals("FACULTY_MEMBER")) {
			entry=new FacultyMember();
			int workingHours=((FacultyMember) entry).getWorkingHours();
			int addCourse=((FacultyMember) entry).getAddCourse();
			int addCourseLimit=((FacultyMember) entry).getAddCourseLimit();
			int addCourseFee=overWorkSalaryCalculation(workingHours,addCourse,addCourseLimit,weeks);
			double ssBenefits=((FacultyMember) entry).getSSBenefits();
			int baseSalary=((FacultyMember) entry).getBaseSalary();
			salary+=addCourseFee+baseSalary+baseSalary*ssBenefits;}
		else if(position.equals("RESEARCH_ASSISTANT")||position.equals("RESEARCH_ASISTANT")) {
			entry=new ResearchAssistant();
			double ssBenefits=((ResearchAssistant) entry).getSSBenefits();
			int baseSalary=((ResearchAssistant) entry).getBaseSalary();
			salary+=baseSalary+baseSalary*ssBenefits;}
		else if(position.equals("OFFICER")) {
			entry=new Officer();
			int workingHours=((Officer) entry).getWorkingHours();
			int overWork=((Officer)entry).getOverWork();
			int overWorkLimit=((Officer)entry).getOverWorkLimit();
			int overWorkSalary=overWorkSalaryCalculation(workingHours,overWork,overWorkLimit,weeks);
			double ssBenefits=((Officer)entry).getSSBenefits();
			int baseSalary=((Officer)entry).getBaseSalary();
			salary+=overWorkSalary+baseSalary+baseSalary*ssBenefits;}
		else if(position.equals("SECURITY")) {
			entry=new Security();
			int hourOfWorkPay=((Security) entry).getHourOfWorkPay();
			int baseSalary=baseSalaryCalculation(hourOfWorkPay,weeks);
			int dayOfWork=((Security)entry).getDayOfWork();
			int transMoney=((Security)entry).getTransMoney();
			int foodMoney=((Security)entry).getFoodMoney();
			salary+=baseSalary+weekOfWork*dayOfWork*(transMoney+foodMoney);}
		else if(position.equals("CHIEF")) {
			entry=new Chief();
			int workingHours=((Chief)entry).getWorkingHours();
			int overWork=((Chief)entry).getOverWork();
			int overWorkLimit=((Chief)entry).getOverWorkLimit();
			int overWorkSalary=overWorkSalaryCalculation(workingHours,overWork,overWorkLimit,weeks);
			int dailyWage=((Chief) entry).getDailyWage();
			int dayOfWork=((Chief) entry).getDayOfWork();
			salary+=dailyWage*dayOfWork*weekOfWork+overWorkSalary;}
		else if(position.equals("WORKER")) {
			entry=new Worker();
			int workingHours=((Worker)entry).getWorkingHours();
			int overWork=((Worker)entry).getOverWork();
			int overWorkLimit=((Worker)entry).getOverWorkLimit();
			int overWorkSalary=overWorkSalaryCalculation(workingHours,overWork,overWorkLimit,weeks);
			int dailyWage=((Worker) entry).getDailyWage();
			int dayOfWork=((Worker) entry).getDayOfWork();
			salary+=dailyWage*dayOfWork*weekOfWork+overWorkSalary;}
		else if(position.equals("PARTTIME_EMPLOYEE")) {
			entry=new PartTime();
			int hourOfWorkPay=((PartTime) entry).getHourOfWorkPay();
			int baseSalary=baseSalaryCalculation(hourOfWorkPay,weeks);
			salary+=baseSalary;}
		return salary;}
	public static Personnel personnelProcession(String name,String surname,String registerNumber,String position,int yearOfStart) {
		//This method fills the personnel's information gained from personnel.txt.
		Personnel entry=new Personnel();
		entry.setName(name);
		entry.setSurname(surname);
		entry.setRegisterNumber(registerNumber);
		entry.setPosition(position);
		entry.setYearOfStart(yearOfStart);
		return entry;}
	public static Personnel monitoringProcession(Personnel entry,String monitoring) {
		//This method fills the personnel's information gained from monitoring.txt.
		int weekOfWork=0;
		String monitoringLine="";
		String[]monitoringLines=monitoring.split("\n");
		for (int i=0;i<monitoringLines.length;i++) {
			String[]monitoringWords=monitoringLines[i].split("\t");
			if(entry.getRegisterNumber().equals(monitoringWords[0])) {
				weekOfWork+=monitoringWords.length-1;
				monitoringLine+=monitoringLines[i];
				}}
		int[]weeks=new int[weekOfWork];
		String[]monitoringWords=monitoringLine.split("\t");
		for(int j=1,k=0;j<monitoringWords.length;j++) {
			weeks[k++]=Integer.parseInt(monitoringWords[j]);
		}
		entry.setWeeks(weeks);
		return entry;}
	public static void output(Personnel entry) {
		//This method fills the output text files using the filled information.
		String name=entry.getName();
		String surname=entry.getSurname();
		String registerNumber=entry.getRegisterNumber();
		String position=entry.getPosition();
		int yearOfStart=entry.getYearOfStart();
		double salary=salaryCalculation(entry);
		String file=registerNumber+".txt";
		String input="Name : "+name+"\n";
		input+="Surname : "+surname+"\n";
		input+="Registiration Number : "+registerNumber+"\n";
		input+="Position : "+position+"\n";
		input+="Year of Start : "+yearOfStart+"\n";
		input+="Total Salary : "+salary+"0 TL\n";
		write(file,input);}
	//Main method
	public static void execute(String personnel,String monitoring) {
		String[]personnelLine=personnel.split("\n");
		for (int i=0;i<personnelLine.length;i++) {
			String[]personnelWord=personnelLine[i].split("\t");
			String name=personnelWord[0];
			String surname=personnelWord[1];
			String registerNumber=personnelWord[2];
			String position=personnelWord[3];
			int yearOfStart=Integer.parseInt(personnelWord[4]);
			Personnel entry=personnelProcession(name,surname,registerNumber,position,yearOfStart);
			entry=monitoringProcession(entry,monitoring);
			output(entry);}}

	public static void main(String[] args) {execute(read(args[0]),read(args[1]));}}
		
