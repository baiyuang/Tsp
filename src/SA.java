import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class SA {

	public static final Double E = 2.7182818284590452354 ;
	public double T;   //��ʼ�¶�
	public double Speed;   //˥����
	public int CityNum;
	
	int begin=0;  //��ʼ��
	ArrayList<Integer>solution=new ArrayList<Integer>();  //tsp��
	Util tool=new Util();
	CityPoint []city;
	
	int num=0;
	
	public ArrayList<Integer>contain=new ArrayList<Integer>();
	
	double dis=0;
	public SA(double T,double speed)
	{
		this.T=T;
		this.Speed=speed;
	}
	
	
	/**
	 * ������ʼ��
	 * @param begin  ������
	 */
	public void Initi()
	{
	
		for(int i=0;i<CityNum;i++)
		{
			solution.add(i);
		}
	
		solution.remove(begin);
		Collections.shuffle(solution);
		//System.out.println("��ʼ�⣺");
		//System.out.println(solution);
  
	//	solution=tool.GetBest("E://a280.opt.tour");
	}
	
	/**
	 * �����¶�
	 */
	public void UpdateT()
	{
		T=T*Speed;
	
	}
	
	/**
	 * ͨ�����������㣬�����½�
	 * @param old
	 */
	public ArrayList<Integer> ExChangeTwo(ArrayList<Integer>solution)
	{
		ArrayList<Integer>newSolution=new ArrayList<Integer>();
		for(int temp:solution)
			newSolution.add(temp);
	
		Random random=new Random();
		int x=random.nextInt(CityNum-1);
		int y=random.nextInt(CityNum-1);
		while(x==y)
			y=random.nextInt(CityNum-1);
		
		
		int temp=newSolution.get(x);
		newSolution.set(x, y);
		newSolution.set(y, temp);
		return newSolution;
	}
	
	/**
	 * ͨ�����������㣬�����½�
	 * @param old
	 */
	public ArrayList<Integer> SortTwo(ArrayList<Integer>solution)
	{
		ArrayList<Integer>newSolution=new ArrayList<Integer>();
		for(int temp:solution)
			newSolution.add(temp);
	
		Random random=new Random();
		int x=random.nextInt(CityNum-1);
		int y=random.nextInt(CityNum-1);
		
		num++;
	//	System.out.print(x+"  "+y+";");
		
		while(x==y)
			y=random.nextInt(CityNum-1);
		if(x>y)
		{
			int temp=y;
			y=x;
			x=temp;
		}
		
		List<Integer> temp=new ArrayList<Integer>();
		for(int i=x;i<=y;i++)
		  temp.add(newSolution.get(i));
		Collections.reverse(temp);
		for(int i=0;i<temp.size();i++)
		{
			newSolution.set(x, temp.get(i));
			x++;
		}
		return newSolution;
	}
	
	
	/**
	 * ��������λ
	 * @param solution
	 * @return
	 */
	public ArrayList<Integer> ShiftCity(ArrayList<Integer>solution)
	{
		ArrayList<Integer>newSolution=new ArrayList<Integer>();
		for(int temp:solution)
			newSolution.add(temp);
	
		Random random=new Random();
		int x=random.nextInt(CityNum-1);    //Ҫ�Ƶ�λ��
		int y=random.nextInt(CityNum-1);   //�Ƶ���λ��

		while(x==y)
			y=random.nextInt(CityNum-1);
		if(x<y)
		{
			int xfirst=newSolution.get(x);
			for(int i=x;i<y;i++)
			{
				newSolution.set(i, newSolution.get(i+1));
			}
			newSolution.set(y,xfirst);
		}
		else
		{
			int xfirst=newSolution.get(x);
			for(int i=x;i<y;i++)
			{
				newSolution.set(i, newSolution.get(i-1));
			}
			newSolution.set(y,xfirst);
		}
		return newSolution;
	}
	
	
	/**
	 * ����λ
	 * @param solution
	 * @return
	 */
	public ArrayList<Integer> ReverseAndShiftCity(ArrayList<Integer>solution)
	{
		ArrayList<Integer>newSolution=new ArrayList<Integer>();
		for(int temp:solution)
			newSolution.add(temp);
	
		Random random=new Random();
		int x=random.nextInt(CityNum-1);
		int y=random.nextInt(CityNum-1);
		while(x==y)
			y=random.nextInt(CityNum-1);
		if(x>y)
		{
			int temp=y;
			y=x;
			x=temp;
		}
		
		List<Integer> temp=new ArrayList<Integer>();
		for(int i=x;i<=y;i++)
		  temp.add(newSolution.get(i));
		//Collections.reverse(temp);

		int z=random.nextInt(CityNum-y-1)+y;

				for(int i=y+1;i<=z;i++)
				{
					newSolution.set(x, newSolution.get(i));
					x++;
				}
				for(int j=0;j<temp.size();j++)
				{
					newSolution.set(z, temp.get(j));
					z--;
				}

		
		return newSolution;
	}
	
	
	
	/**
	 * �������
	 * @param solution
	 * @return
	 */
	public double DistanceCost(ArrayList<Integer>solution)
	{
		double instanceFirst = 0;//���·������
		int index=begin;
		for(int temp:solution)
		{
			instanceFirst+=tool.Distance(city[index], city[temp]);
			index=temp;
		}
		return instanceFirst;
				
	}
	
	/**
	 * ����
	 */
	public void TspAS()
	{
		dis=DistanceCost(solution);
		while(T>1)
		{		
		double E1=DistanceCost(solution);
		System.out.println(dis);
		ArrayList<Integer> newSolution;
		Random random=new Random();
		int num=random.nextInt(3);
		/*
		if(num==0)
			newSolution=ReverseAndShiftCity(solution);
		else if(num==1)
			newSolution=ShiftCity(solution);
		else if(num==2)
			newSolution=SortTwo(solution);
		else
			newSolution=ExChangeTwo(solution);
		*/
		
		newSolution=ShiftCity(solution);
		double E2=DistanceCost(newSolution);
		double diff=E1-E2;
		if(diff>0)
		{
			solution=newSolution;
			dis=E2;
		}
		else
		{
			 double probability=Math.pow(E,diff/T);
			 double x=random.nextDouble();
			 if(probability>random.nextDouble())
			 {
				 solution=newSolution;
				 dis=E2;
			 }
		
		}
		UpdateT();
		}
		
		
	}
	
	public double Best(String file)
	{
		ArrayList <Integer>list=new ArrayList<Integer>();
		list=tool.GetBest(file);
		list.remove(begin);
		System.out.println("���Ŵ��ۣ�"+DistanceCost(list));
	    return DistanceCost(list);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		ArrayList <Integer>list=new ArrayList<Integer>();
		for(int i=0;i<20;i++)
		{
			list.add(i);
		}
		*/
		
		Util tool=new Util();
		SA test=new SA(1000000,0.95);

		test.city=tool.ReadFile("E://att48.tsp");
		test.CityNum=test.city.length;
		//test.Best("E://att48.opt.tour");
		double  mean=Double.MAX_VALUE;
		for(int i=0;i<1;i++)
		{
		test.T=100000;
		test.Speed=0.95;
		test.Initi();
		test.TspAS();
		if(test.dis<mean)
        mean=test.dis;
		test.solution.clear();
		
		
		
		}
		System.out.println("��ǰ����"+mean+" "+"��ֵ"+(mean-test.Best("E://att48.opt.tour")));
	
	}

}
