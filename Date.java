package project2;

import java.time.LocalDate;

public class Date {
	
	private int year;
	private int month;
	private int day;
	
	public Date(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public int getYear() {return year;}
	public int getMonth() {return month;}
	public int getDay() {return day;}
	
	public int toCompare(Date other)
	{
		//현재 객체가 인수 객체보다 더 이후이면 1반환
		//현재 객체가 인수 객체보다 더 이전이면 -1반환
		//둘이 똑같으면 0반환
		if(year > other.getYear()) return 1;
		else if(year<other.getYear()) return -1;
		else {
			if(month > other.getMonth()) return 1;
			else if(month<other.getMonth()) return -1;
			else {
				if(day > other.getDay()) return 1;
				else if(day<other.getDay()) return -1;
				else return 0;
			}
		}
	}

	public Date plusDays(int days)//days후면 달력상에서 며칠인지 알아내는 method
	{
		LocalDate nowdate = LocalDate.of(year, month, day);  
		LocalDate someDaysLater = nowdate.plusDays(days-1);
		int newyear = someDaysLater.getYear();
		int newmonth = someDaysLater.getMonthValue();
		int newday = someDaysLater.getDayOfMonth();
		
		Date addedDate = new Date(newyear, newmonth, newday);
		return addedDate;
	}
	
	public boolean equals(Date date)
	{
		if(year==date.getYear()&& month==date.getMonth()&& day==date.getDay()) return true;
				return false;
	}
	
	public int getDays(Date date)
	{
		LocalDate nowdate = LocalDate.of(year, month, day);
		Date tempdate = new Date(year, month, day);
		int days=1;
		while(!tempdate.equals(date))
		{
			nowdate = nowdate.plusDays(1);
			tempdate = new Date(nowdate.getYear(), nowdate.getMonthValue(), nowdate.getDayOfMonth());
			days++;
		}
		return days;	
	}
	public String toString()
	{
		return year+ "년" + month +"월"+ day+"일";
	}
}