package project2;


import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class RentTest {
	
	public static void main(String[] args) throws IOException 
	{
		//차량 정보 list 입력받기
		Vehicle[] vehs= new Vehicle[100]; 
		
		char sort;
		int capa;
		int id;
		
		int size = 0;//vehicle의 수 저장할 변수
		int i=0; //vehicle 배열 인덱스
		
		System.out.println("파일경로를 입력하세요");
    	Scanner in = new Scanner(System.in);
    	String fileroute = in.nextLine(); 	
    	Scanner scanner = new Scanner(new File(fileroute));
    	
    	while (scanner.hasNext()) 
         {
           	sort = scanner.next().charAt(0);
           	capa = scanner.nextInt();
           	id = scanner.nextInt();

			if(sort=='c') vehs[i] = new Car(id, capa);
			else if(sort=='s') vehs[i] = new Suv(id,capa);
			else vehs[i] = new Truck(id, capa);
			i++;
           }
		
		size=i;
		System.out.println("입력된 차량들 list");
		for(int j=0; j<size; j++)
			System.out.println(vehs[j].toString());
		
		functions(size, vehs); 
		
		
	}
	
	//기능입력받고 실행
	static void functions(int size, Vehicle[] vehs)
	{
		System.out.println("명령들을 실행하세요.");
		RentReceipt[] receipts = new RentReceipt[100];
		Date today = null;
		String instruction = null; char sort = 0;
		int capa=0; int year=0; int month=0; int day=0; int days=0;
		String name = null; String phnumber=null; String something = null;
		
		Scanner in = new Scanner(System.in);
		
		while(in.hasNext())
		{
			instruction = in.next(); 
			switch(instruction) 
			{case "r" : {sort = in.next().charAt(0); 
			capa = in.nextInt(); year = in.nextInt(); month = in.nextInt();
			day = in.nextInt(); days = in.nextInt(); name = in.next(); 
			phnumber = in.next(); break;}
			case "c": {phnumber = in.next();  year = in.nextInt();
			month = in.nextInt(); day = in.nextInt(); break;}
			case "o": {phnumber = in.next(); break;}
			case "i": {phnumber = in.next(); break;}
			case "v": break;
			case "a": break;
			case "p": break;
			case "d" : {year = in.nextInt(); month = in.nextInt(); day=in.nextInt(); break;}
			default: {something = in.nextLine(); System.out.println("명령이 유효하지 않습니다. 다시 입력하십시오.");}
			}
			
			switch(instruction)
			{case "r" : {Date rentdate = new Date(year, month, day); Person person = new Person(name, phnumber);
			makeReservation(size, vehs, today, sort, capa, rentdate, days, person, receipts); break;}
			case "c" : {cancelReservation(phnumber, year, month, day, receipts); break;}
			case "o" : {checkOut(phnumber, today, receipts);break;}
			case "i" : {checkIn(phnumber, today, receipts);break;}
			case "v" : {viewAllReservedVehicles(today, receipts);break;}
			case "a" : {viewAllRentedVehicles(today, receipts); break;}
			case "p" : {viewIncome(today, receipts); break;}
			case "d":
			{ 
				if(today == null || today.toCompare(new Date(year,month,day))<=0)
				{
					today = new Date(year, month, day); 
					System.out.println("현재 날짜를 "+year+"년 "+month+"월 "+day+"일로 설정");
					setOmmiteds(today, receipts); break;	
				}
				else {System.out.println("현재 날짜보다 이전 날짜를 설정할 수는 없습니다."); break;}
			}
			default: break;
			}
			
		}
		
		System.out.println("프로그램 종료");
	}//functions 끝
	
	static void makeReservation(int size, Vehicle[] vehs, Date today,
			char sort, int capa, Date rentdate, int days, Person person, RentReceipt[] receipts) 
	{
		if(rentdate.toCompare(today)<0) {System.out.println("현재 날짜보다 이전 날짜로는 대여불가합니다.");return;}  
		Vehicle rentcar = null; //아직 배정 못받음
		
		int i=0;//차량리스트인덱스
		
		while(rentcar == null)
		{
			if(vehs[i].isthisvehicle(sort, capa))//차량 리스트에 있는 차량 다 뒤지기
			{
				for(int k=0; k<100; k++)//receipts 다 뒤지기
				{
					if(receipts[k] != null && receipts[k].isThisReceipt(vehs[i].getId()))
						rentcar=vehs[i];//우선 배정
				}
			   	if(rentcar == null) {rentcar = vehs[i]; break;} //receips 다 뒤졌는데 id같은거 없는 거니까 렌트카배정

			   	int impossible=0;//겹치는지여부나타냄
			   	//한장이라도 이 vehicle에 대해서 receipt가 있는 경우만 여기에 도달
			   	for(int k=0; k<100; k++)
			   	{
			   		if(receipts[k] != null && receipts[k].isThisReceipt(vehs[i].getId())
			   				&&!receipts[k].isRentPossible(rentdate, days))
			   			impossible=1;
				}
			   	if(impossible==0) //죄다 가능했다는거임 
			   		break;
			   	else rentcar = null; //vehs[i]에대한 receipts들 중에 하나라도 겹치는게 있었다는 뜻이므로 배정못함.  	
			} 
			i++;
			if(i>=size) break;
		}
		
		if(rentcar == null) {System.out.println("현재 예약 가능한 차량이 없습니다"); return;}
		//for문돌고 왔는데 아직도 렌트카가 널이면 아예 그차량이 없거나 대여가능한 차량이 없거나임 걍 return
	
		//현재 person의 대여기간들 조회해서 대여가능여부 마지막으로 확인
		for(int k=0; k<100; k++)
		{
			if(receipts[k]!=null && receipts[k].isThisReceipt(person)) 
				if(receipts[k].isRentPossibleByPerson(rentdate, days));
				else {rentcar = null ; break;}//배정됐던 렌트카 다시 뻇기
		}
		
		if(rentcar == null) {System.out.println("고객님이 예약하셨던 다른 차량과 대여기간이 겹쳐 대여가 불가능합니다."); return;}
	
		//다통과하면 배정된 rentcar로 receipt객체만들기
		for(int k=0; k<100; k++)
		{
			if(receipts[k]==null)
			{
				receipts[k] = new RentReceipt(rentcar, person, rentdate, days);
				System.out.println(receipts[k].toString() + ", 예약");
				break;
			}
		}
		
	}//makeReservation 끝
	
	static void cancelReservation(String phnumber, int year, int month, int day, RentReceipt[] receipts) 
	{
		Date rentdate = new Date(year, month, day);
		
		for(int k=0; k<100; k++)
		{
			if(receipts[k] != null && receipts[k].isThisReceipt(phnumber, rentdate))
			{    
				System.out.println(receipts[k].toString() + ", 취소");
				receipts[k] = null; return;
			}
		}
		System.out.println("예약된 차량이 없습니다.");
	}
	
	static void checkOut(String phnumber, Date today, RentReceipt[] receipts)
	{
		for(int k=0; k<100; k++)
		{
			if(receipts[k]!=null && receipts[k].isThisReceipt(phnumber, today))
			{
				receipts[k].setRentalStatus(); 
				System.out.println(receipts[k].toString(1) + ", 대여"); return;
			}
		}
		System.out.println("현재 날짜로 예약된 차량이 없어 대여할 수 없습니다.");
	}
	
	static void checkIn(String phnumber, Date today, RentReceipt[] receipts)
	{
		for(int k=0; k<100; k++)
		{
			if(receipts[k]!=null && receipts[k].isThisReceipt(phnumber)&& receipts[k].getRentalStatus())
			{
				receipts[k].setRentalStatus(); receipts[k].setReturnStatus();
				receipts[k].chageChargeAndReturndate(today); 
				System.out.println(receipts[k].toString(1,1,1) + ", 반납"); return;
			}
		}
		System.out.println("현재 대여중인 차량이 없습니다.");
	}
	
	static void viewAllReservedVehicles(Date today, RentReceipt[] receipts)
	{	
		int size=0;
		for(int k=0; k<100; k++)
		{if(receipts[k] !=null && receipts[k].isThisReceipt(today))size++;}
		
		if(size==0) {System.out.println("예약 차량이 한대도 없습니다."); return;}
		
		RentReceipt[] reservedReceipts = new RentReceipt[size];
		
		int j=0;
		for(int k=0; k<100; k++)
		{ 
			if(receipts[k] !=null && receipts[k].isThisReceipt(today))
			{
				reservedReceipts[j] = receipts[k]; j++;
			}
		}
	
		Arrays.sort(reservedReceipts);
		
		for(RentReceipt r: reservedReceipts)
			System.out.println(r.toString());
		
	}
	
	static void viewAllRentedVehicles(Date today, RentReceipt[] receipts)
	{
		int size=0;
		for(int k=0; k<100; k++)
		{if(receipts[k] !=null && receipts[k].getRentalStatus())size++;}
		
		if(size==0) {System.out.println("대여중인 차량이 한대도 없습니다."); return;}
		
		RentReceipt[] nowRentReceipts = new RentReceipt[size];
		
		int j=0;
		for(int k=0; k<100; k++)
		{
			if(receipts[k] !=null && receipts[k].getRentalStatus())
			{
				nowRentReceipts[j] = receipts[k]; j++;
			}
		}
	
		Arrays.sort(nowRentReceipts);
		
		for(RentReceipt r: nowRentReceipts)
			System.out.println(r.toString(1));
		
	}
	
	static void viewIncome(Date today, RentReceipt[] receipts)
	{
		int size=0;
		for(int k=0; k<100; k++)
		{if(receipts[k] !=null && receipts[k].isThisReceipt(today.getYear(), today.getMonth()))size++;}
		
		if(size==0) {System.out.println("이번달 수입은 0원입니다."); return;}
		
		RentReceipt[] incomeRecipts = new RentReceipt[size];
		
		int j=0; int totalIncome = 0;
		for(int k=0; k<100; k++)
		{
			if(receipts[k] !=null && receipts[k].isThisReceipt(today.getYear(), today.getMonth()))
			{
				incomeRecipts[j] = receipts[k]; j++; totalIncome +=receipts[k].getCharge();
			}
		}
	
		Arrays.sort(incomeRecipts, new returnDateComparator());
		
		System.out.println(today.getYear()+"년 "+today.getMonth()+"월 대여 수입");
		for(RentReceipt r: incomeRecipts)
			System.out.println(r.toString(1, 1));
		System.out.println("총 수입: "+totalIncome+"원");
	}
	
	static void setOmmiteds(Date today, RentReceipt[] receipts)
	{
		for(int k=0; k<100; k++)
		{
			if(receipts[k] !=null && receipts[k].getRentDate().toCompare(today)<0)
				{if(!receipts[k].getRentalStatus()&& !receipts[k].getReturnStatus()) receipts[k]=null;}
		}
	}
}//RentTest 끝

