import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;

public class MathExam6445 {

	static int n, grade;

	public static void main(String[] args) {
		if (check(args[0], args[1])) {
			create();
			try {
				System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("out.txt")), true));
			} catch (FileNotFoundException e) {
				System.out.println("����ض���ʧ��ʧ�ܣ�");
				e.printStackTrace();
			}
			work();
		}
	}

	/**
	 * �������Ĳ����Ƿ�Ϸ�
	 * 
	 * @param strN������
	 * @param strGrade���꼶
	 * @return �Ϸ�����true�����򷵻�false
	 */
	public static boolean check(String strN, String strGrade) {
		// ��������Ϊ����
		if (!strN.matches("\\d*")) {
			// ������Ϊ����+���֣���Ϊ������
			if (strN.matches("-\\d*")) {
				System.out.println("����һ����ȷ������Ŷ������Ϊ��");
				return false;
			} else {
				// ����Ϊ��������ַ�
				System.out.println("�������������������!");
				return false;
			}
		} else {
			// �ַ������͵�����ת������
			n = Integer.parseInt(strN);
			if (n > 100) {
				System.out.println("�������������������Сѧ��Ŷ");
				return false;
			} else if (n == 0) {
				System.out.println("���������Ϊ�㣬���һ�Ҫ��Ҫ����ѽ");
			}
		}

		if (!strGrade.matches("[1-2]")) {
			System.out.println("�꼶ֻ���� 1 ���� 2 Ŷ");
			return false;
		} else {
			grade = Integer.parseInt(strGrade);
		}
		return true;
	}

	/**
	 * ����͸��������ļ�
	 */
	public static void work() {
		int num1, num2, op;
		String str = "", result = "";
		Random rand = new Random();
		for (int i = 1; i <= n; i++) {
			// �������0~100����
			num1 = rand.nextInt(101);
			num2 = rand.nextInt(101);
			if (grade == 1) {
				// һ�꼶ֻ�мӼ�
				op = rand.nextInt(2);
			} else
				// ���꼶�漰�˳�
				op = rand.nextInt(4);
			// op = 0�ӷ���1������2�˷���3����
			if (op == 0) {
				// ������֮�ͳ���100������������
				if ((num1 + num2) > 100) {
					// �������ǲ��ܼ���������ѭ��������Ҫ��һ
					i--;
					continue;
				}
				// �ַ������£���ע��windowsϵͳ�£����з�Ϊ��\r\n��
				str += "(" + i + ") " + num1 + " + " + num2 + "\r\n";
				result += "(" + i + ") " + num1 + " + " + num2 + " = " + (num1 + num2) + "\r\n";
			} else if (op == 1) {
				// ����֮����ָ���������������
				if ((num1 - num2) < 0) {
					// ͬ��ѭ��������Ҫ��һ
					i--;
					continue;
				}
				str += "(" + i + ") " + num1 + " - " + num2 + "\r\n";
				result += "(" + i + ") " + num1 + " - " + num2 + " = " + (num1 - num2) + "\r\n";
			} else if (op == 2) {
				// �˳���ֵΪ0~10
				num1 = rand.nextInt(11);
				num2 = rand.nextInt(11);
				// ͬ������֮������100������������
				if ((num1 * num2) > 100) {
					i--;
					continue;
				}
				str += "(" + i + ") " + num1 + " �� " + num2 + "\r\n";
				result += "(" + i + ") " + num1 + " �� " + num2 + " = " + (num1 * num2) + "\r\n";
			} else {
				num1 = rand.nextInt(11);
				num2 = rand.nextInt(11);
				// ͬ����������Ϊ0������������
				if (num2 == 0) {
					i--;
					continue;
				}
				str += "(" + i + ") " + num1 + " �� " + num2 + "\r\n";
				if (num1 % num2 != 0) {
					result += "(" + i + ") " + num1 + " �� " + num2 + " = " + (num1 / num2) + "..." + (num1 % num2)
							+ "\r\n";
				} else {
					result += "(" + i + ") " + num1 + " �� " + num2 + " = " + (num1 / num2) + "\r\n";
				}
			}
		}
		System.out.println(str);
		System.out.print(result);
	}

	public static void create() {
		File file = new File("out.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("�ļ�����ʧ�ܣ�");
				e.printStackTrace();
			}
		}
	}
}