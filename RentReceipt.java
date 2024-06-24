package project2;


import java.util.*;

public class RentReceipt implements Comparable<RentReceipt> 
{
	
	private int charge = 0; //대여요금
	private Vehicle rentcar; //대여차량
	private Person person; //고객 정보
	private int days;//대여기간
	private Date rentdate; //대여 날짜
	private Date returndate; //반납할 날짜
	private int VehicleId; //대려차량 id
	private boolean rentalStatus = false; //대여여부
	private boolean returnStatus = false; //반납여부
	
	public RentReceipt( Vehicle rentcar, Person person, Date rentdate, int days)
	{
		this.rentcar = rentcar;
		this.person = person;
		this.rentdate = rentdate;
		this.days = days;
		returndate =rentdate.plusDays(days);
		this.VehicleId = rentcar.getId();
		charge = CalculateRentalFee(rentcar);
	}//constructor 끝
	
	private int CalculateRentalFee(Vehicle veh)//여기 class에서만 쓰일것 같아서 private
	{
		//대여요금계산하기
		int capa= veh.getCapa();
		int feePerDay=0;//하루당 요금
		switch(veh.getSort()) 
		{
		case 'c': {feePerDay = (capa / 1000 + 3) * 10000; break;}
		case 's':{
			if(capa<150) feePerDay = 20000;
			else if(capa>=150&&capa<250)feePerDay = 40000;
			else if(capa>=250&&capa<350) feePerDay = 60000;
			else feePerDay = 80000; break;}
		case 't':{
			if(capa<=1) return 20000 * days;
			else if (capa>=2 && capa<=4) return 40000 * days;
			else return 60000 * days;}
		}
		if(days <=5)
			return feePerDay * days;
		else return days*(feePerDay-10000)+50000;
		
	}//CalculateRentalFee() 끝
	
	public String toString()//예약차량보기
	{
		return rentcar.toString()+", "+ rentdate.toString()+"("+days+"일), "+person.toString();
	}
	
	public String toString(int a)//대여차량보기
	{
		return rentcar.toString()+"("+VehicleId+"), "+ rentdate.toString()+"("+days+"일), "+person.toString();	
	}
	
	public String toString(int a, int b)//수입보기
	{
		return rentcar.toString()+", "+ returndate.toString()+"("+days+"일), "+ charge+"원";	
	}
	
	public String toString(int a, int b, int c)//반납할떄 출력문
	{
		return rentcar.toString()+"("+VehicleId+"), "+ returndate.toString()+"("+days+"일), "+person.toString();	
	}
	
	
	public boolean isRentPossible(Date rentdate, int days)
	{
		Date returndate = rentdate.plusDays(days);//인수로 들어오는 객체의 returndate계산
		if(rentdate.toCompare(this.returndate)>0 || this.rentdate.toCompare(returndate)>0)
			return true;
		return false;
	}
	
	public boolean isRentPossibleByPerson(Date rentdate, int days)
	{
		Date returndate = rentdate.plusDays(days);//인수로 들어오는 객체의 returndate계산
		if(rentdate.toCompare(this.returndate)>=0 || this.rentdate.toCompare(returndate)>=0)
			return true;
		return false;
	}

	public boolean isThisReceipt(int Id)
	{
		if(this.VehicleId == Id) return true;
		return false;
	}
	
	
	public boolean isThisReceipt(String number, Date date)
	{
		if(person.getPhnumber().equals(number)&& rentdate.equals(date)) return true;
		return false;
	}
	
	public boolean isThisReceipt(String number)
	{
		if(person.getPhnumber().equals(number)) return true;
		return false;
	}
	
	public boolean isThisReceipt(Person person)
	{
		if(this.person.equals(person)) return true;
		return false;
	}
	
	public boolean isThisReceipt(Date today)
	{
		if(rentdate.toCompare(today)>=0 && !rentalStatus && !returnStatus) return true;
		return false;
	}

	public boolean isThisReceipt(int year, int month)
	{
		if(returndate.getYear()==year && returndate.getMonth()==month) return true;
		return false;
	}
	
	public void setRentalStatus()
	{
		if(rentalStatus) rentalStatus = false;
		else rentalStatus = true;
	}
	
	public void setReturnStatus() {returnStatus = true;}
	public boolean getRentalStatus() {return rentalStatus;}
	public boolean getReturnStatus() {return returnStatus;}
	public Date getRentDate() {return rentdate;}
	public Date getReturnDate() {return returndate;}
	public int getCharge() {return charge;}

	public void chageChargeAndReturndate(Date today)
	{
		returndate = today; //오늘로 returndate변경
		days = rentdate.getDays(today);//대여날로부터 today로 days도 변경
		charge = CalculateRentalFee(rentcar);//바뀐 days로 charge도 새로 계싼
	}
	
	public int compareTo(RentReceipt other) 
	{
		return rentdate.toCompare(other.getRentDate());
	}
	

	
}//RentReceipt class 끝

class returnDateComparator implements Comparator<RentReceipt>
{
	public int compare(RentReceipt first, RentReceipt second) 
	{
		return first.getReturnDate().toCompare(second.getReturnDate());
	}
}