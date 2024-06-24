package project2;


public class Vehicle 
{
	private int Id;
	private int capa;
	
	public Vehicle(int Id, int capa) {
			this.Id = Id;
			this.capa = capa;
	}
	
	public boolean isthisvehicle(char sort, int capa)
	{return true;}
	
	public int getCapa() {return capa;}
	public int getId() {return Id;}
	public char getSort() {return 0;}
	public String toString() {return "ID: "+Id + "용량"+capa;}
	
}

class Car extends Vehicle{
	
	private static final char sort='c';

	public Car(int Id, int cc) {
		super(Id, cc);
	}
	
	public boolean isthisvehicle(char sort, int capa) 
	{
		if(this.sort == sort && this.getCapa() == capa)
			return true;
		return false;
	}
	
	public char getSort() {return sort;}
	public String toString() {return "car "+this.getCapa()+"cc";}
}

class Suv extends Vehicle{
	
	private static final char sort='s';
	
	public Suv(int Id, int hp) {
		super(Id, hp);
	}
	
	public boolean isthisvehicle(char sort, int capa) 
	{
		if(this.sort == sort && this.getCapa() == capa)
			return true;
		return false;
	}
	public char getSort() {return sort;}
	public String toString() {return "suv "+this.getCapa()+"hp";}
}

class Truck extends Vehicle{
	
	private static final char sort='t';
	
	public Truck(int Id, int ton) {
		super(Id, ton);
	}
	
	public boolean isthisvehicle(char sort, int capa) //내용도 똑같은데 overriding할 필요 없나..ㅠ
	{
		if(this.sort == sort && this.getCapa() == capa)
			return true;
		return false;
	}
	
	public char getSort() {return sort;}
	public String toString() {return "truck "+this.getCapa()+"ton";}
	
}