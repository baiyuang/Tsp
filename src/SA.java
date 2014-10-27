import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SA {

	public static final Double E = 2.7182818284590452354;
	public double T; // ��ʼ�¶�
	public double Speed; // ˥����
	public int CityNum;   //���и���
	public int loop; // ��������
	
	int begin = 0; // ��ʼ��
	
	ArrayList<Integer> solution = new ArrayList<Integer>(); // tsp��
	Util tool = new Util();
	CityPoint[] city;

	int num = 0;
	double dis = 0;   //tsp ��

	public SA(double T, double speed) {
		this.T = T;
		this.Speed = speed;
	}

	/**
	 * ������ʼ��
	 * 
	 * @param begin
	 *            ������
	 */
	public void Initi() {

		for (int i = 0; i < CityNum; i++) {
			solution.add(i);
		}

		solution.remove(begin);
		Collections.shuffle(solution);
	}

	/**
	 * �����¶�
	 */
	public void UpdateT() {
		T = T * Speed;

	}

	/**
	 * ͨ�����������㣬�����½�
	 * 
	 * @param old
	 */
	public ArrayList<Integer> ExChangeTwo(ArrayList<Integer> solution) {
		ArrayList<Integer> newSolution = new ArrayList<Integer>();
		for (int temp : solution)
			newSolution.add(temp);

		Random random = new Random();
		int x = random.nextInt(CityNum - 1);
		int y = random.nextInt(CityNum - 1);
		while (x == y)
			y = random.nextInt(CityNum - 1);
		int temp = newSolution.get(x);
		newSolution.set(x, newSolution.get(y));
		newSolution.set(y, temp);
		return newSolution;
	}

	/**
	 * ���������м�ĵ���з���
	 * 
	 * @param old
	 */
	public ArrayList<Integer> SortTwo(ArrayList<Integer> solution) {
		ArrayList<Integer> newSolution = new ArrayList<Integer>();
		for (int temp : solution)
			newSolution.add(temp);

		Random random = new Random();
		int x = random.nextInt(CityNum - 1);
		int y = random.nextInt(CityNum - 1);

		num++;
		while (x == y)
			y = random.nextInt(CityNum - 1);
		if (x > y) {
			int temp = y;
			y = x;
			x = temp;
		}

		List<Integer> temp = new ArrayList<Integer>();
		for (int i = x; i <= y; i++)
			temp.add(newSolution.get(i));
		Collections.reverse(temp);
		for (int i = 0; i < temp.size(); i++) {
			newSolution.set(x, temp.get(i));
			x++;
		}
		return newSolution;
	}

	/**
	 * ��������λ
	 * 
	 * @param solution
	 * @return
	 */
	public ArrayList<Integer> ShiftCity(ArrayList<Integer> solution) {
		ArrayList<Integer> newSolution = new ArrayList<Integer>();
		for (int temp : solution)
			newSolution.add(temp);

		Random random = new Random();
		int x = random.nextInt(CityNum - 1); // Ҫ�Ƶ�λ��
		int y = random.nextInt(CityNum - 1); // �Ƶ���λ��

		while (x == y)
			y = random.nextInt(CityNum - 1);

		if (x < y) {
			int xfirst = newSolution.get(x);
			for (int i = x; i < y; i++) {
				newSolution.set(i, newSolution.get(i + 1));
			}
			newSolution.set(y, xfirst);

		} else {
			int xfirst = newSolution.get(x);
			for (int i = x; i > y; i--) {
				newSolution.set(i, newSolution.get(i - 1));
			}
			newSolution.set(y, xfirst);
		}

		return newSolution;
	}

	/**
	 * ������λ
	 * 
	 * @param solution
	 * @return
	 */
	public ArrayList<Integer> ReverseAndShiftCity(ArrayList<Integer> solution) {
		ArrayList<Integer> newSolution = new ArrayList<Integer>();
		for (int temp : solution)
			newSolution.add(temp);

		Random random = new Random();
		int x = random.nextInt(CityNum - 1);
		int y = random.nextInt(CityNum - 1);
		while (x == y)
			y = random.nextInt(CityNum - 1);
		if (x > y) {
			int temp = y;
			y = x;
			x = temp;
		}

		List<Integer> temp = new ArrayList<Integer>();
		for (int i = x; i <= y; i++)
			temp.add(newSolution.get(i));
		// Collections.reverse(temp);

		int z = random.nextInt(CityNum - y - 1) + y;

		for (int i = y + 1; i <= z; i++) {
			newSolution.set(x, newSolution.get(i));
			x++;
		}
		for (int j = 0; j < temp.size(); j++) {
			newSolution.set(z, temp.get(j));
			z--;
		}

		return newSolution;
	}

	/**
	 * ������۾���
	 * 
	 * @param solution
	 * @return
	 */
	public double DistanceCost(ArrayList<Integer> solution) {
		double instanceFirst = 0;// ���·������
		int index = begin;
		for (int temp : solution) {
			instanceFirst += tool.Distance(city[index], city[temp]);
			index = temp;
		}
		instanceFirst += tool.Distance(city[index], city[begin]);
		return instanceFirst;

	}

	/**
	 * ģ���˻����
	 */
	public void TspAS() {
		int temp = loop;
		dis = DistanceCost(solution);
		double E1;    //x�����ֵ
		double E2;    //�½����ֵ
		double diff;
		int num;
		while (T > 1) {
			while (loop > 0) {
				E1 = DistanceCost(solution);
				ArrayList<Integer> newSolution;
				Random random = new Random();
				num = random.nextInt(3);
/*
				if (num == 0)
					newSolution = ReverseAndShiftCity(solution);
				else if (num == 1)
					newSolution = ShiftCity(solution);
				else if (num == 2)
					newSolution = SortTwo(solution);
				else
					newSolution = ExChangeTwo(solution);
*/
				newSolution = ExChangeTwo(solution);   //�����½�
				E2 = DistanceCost(newSolution);
				diff = E1 - E2;

				if (diff > 0) {
					solution = newSolution;
					dis = E2;
				} else {
					double probability = Math.pow(E, diff / T);
					double x = random.nextDouble();
					if (probability > random.nextDouble()) {
						solution = newSolution;
						dis = E2;
					}

				}
				loop--;
			}
			UpdateT();
			System.out.println(dis);
			loop = temp;
		}
		System.out.println("���Ž��ƽ⣺");
		System.out.println(solution);
		// System.out.println(dis);
	}

	/**
	 * ��ȡ���Ž�
	 * @param file
	 * @return
	 */
	public double Best(String file) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list = tool.GetBest(file);
		list.remove(begin);
		System.out.println("���Ŵ��ۣ�" + DistanceCost(list));
		return DistanceCost(list);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Util tool = new Util();
		SA test = new SA(1000000, 0.95);
		test.city = tool.ReadFile("E://att48.tsp");
		test.CityNum = test.city.length;
		test.loop = test.city.length * 3;
		test.Initi();
		test.TspAS();
		System.out.println("��ǰ����" + test.dis + " " + "��ֵ"
				+ (test.dis - test.Best("E://att48.opt.tour")));

	}

}
