package project2;


public class Person {
	private String name;
	private String phnumber;

	
	public Person(String name, String phnumber)
	{
		this.name=name;
		this.phnumber=phnumber;
	}
	
	public String getName() {return name;}
	public String getPhnumber() {return phnumber;}
	
	public boolean equals(Person person)
	{
		if(phnumber.equals(person.getPhnumber()))return true;
		return false;
	}
	
	public String toString()
	{
		return name+", "+phnumber;
	}
}