import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class MathExam6445V2 {

	public static int n, grade, N = 100;
	public static String[] operator = { " + ", " - ", " �� ", " �� " };

	/**
	 * ������ ��������������������������������
	 * @param args  �������� �������꼶
	 */
	public static void main(String[] args) {
		if (check(args)) {
			System.out.println("ͨ�����");
			create();
			// ����ض���
			try {
				System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("out.txt")), true));
			} catch (FileNotFoundException e) {
				System.out.println("����ض���ʧ��ʧ�ܣ�");
				e.printStackTrace();
			}

			if (args[0].equals("-n")) {
				work();
			} else {
				work();
			}
		}
	}

	/**
	 * ���⺯�� ����������꼶���������ɶ�Ӧ��Ŀ������ӡ��Ŀ������
	 */
	public static void work() {

		int num1, num2, answer, op;
		Random rand = new Random();
		// src:��Ŀ�ַ����� result:���ַ�����lineBreak:�����ַ�����
		String src = "";
		String result = "";
		String lineBreak = "\r\n";
		// ���ΪΪ���ѭ�����Ա�continue�˳���
		outerLoop: for (int i = 1; i <= n; i++) {
			if (grade == 1) {
				num1 = rand.nextInt(101);
				num2 = rand.nextInt(101);
				// һ�꼶ֻ�мӼ���������������������Ϊ0��1
				op = rand.nextInt(2);
				// ��������������
				if (op == 0 && num1 + num2 <= 100) {
					answer = num1 + num2;
				} else if (op == 1 && num1 - num2 > 0) {
					answer = num1 - num2;
				} else {
					// �������������˳�����ѭ������i--����������һ��
					i--;
					continue;
				}
				// ����Ŀ��ӵ���Ŀ�ַ���������ӵ����ַ�����
				src += "(" + i + ") " + num1 + operator[op] + num2 + lineBreak;
				result += "(" + i + ") " + num1 + operator[op] + num2 + " = " + answer + lineBreak;
			} else if (grade == 2) {
				// ���꼶�漰�˳���������������������Ϊ0��3
				op = rand.nextInt(4);
				if (op < 2) {
					// �Ӽ�Ϊ�����ڵļӼ�
					num1 = rand.nextInt(101);
					num2 = rand.nextInt(101);
				} else {
					// �˳�Ϊ���ڳ˳�������ȡֵ��Χ��0��10
					num1 = rand.nextInt(11);
					num2 = rand.nextInt(11);
				}
				// ��������������
				if (op == 0 && num1 + num2 <= 100) {
					answer = num1 + num2;
				} else if (op == 1 && num1 - num2 >= 0) {
					answer = num1 - num2;
				} else if (op == 2 && num1 * num2 <= 100) {
					answer = num1 * num2;
				} else if (op == 3 && num2 != 0) {
					answer = num1 / num2;
				} else {
					// �������������˳�����ѭ������i--����������һ��
					i--;
					continue;
				}
				// ����Ŀ��ӵ���Ŀ�ַ�����
				src += "(" + i + ") " + num1 + operator[op] + num2 + lineBreak;
				// ������ӵ����ַ����У�����ǳ���Ҫ�����Ƿ�������
				if (op != 3)
					result += "(" + i + ") " + num1 + operator[op] + num2 + " = " + answer + lineBreak;
				else {
					if (num1 % num2 != 0)
						result += "(" + i + ") " + num1 + operator[op] + num2 + " = " + answer + "..." + (num1 % num2)
								+ lineBreak;
					else
						result += "(" + i + ") " + num1 + operator[op] + num2 + " = " + answer + lineBreak;
				}
			} else {
				// ���꼶����������
				num1 = rand.nextInt(101);
				// ����answer�������������洢��������������洢��һ�ֵĵڶ��������������
				answer = num1;
				// isBracket���������0��1�������洢�Ƿ������ţ�0û�����ţ�1�����š�
				int isBracket = rand.nextInt(2);
				if (isBracket == 0) {
					// opNum���������2��4�������洢���������
					int opNum = rand.nextInt(3) + 2;
					// ����һ���ַ����������洢��һ�����Ŀ���ɣ���ʼ��Ϊ��һ�����������
					String str = num1 + "";
					// ���ΪΪ�ڲ�ѭ�����Ա�continue�˳���
					innerLoop: for (int j = 1; j <= opNum; j++) {
						op = rand.nextInt(4);
						num2 = rand.nextInt(101);
						// ��������Ŀ���ɹ������˳�����ѭ������j--��������������͵ڶ������������
						if (op == 0) {
							if (num1 + num2 >= 1000) {
								j--;
								continue innerLoop;
							}
						} else if (op == 1) {
							if (num1 - num2 < 0) {
								j--;
								continue innerLoop;
							}
						} else if (op == 2) {
							if (answer * num2 > 1000) {
								j--;
								continue innerLoop;
							}
						} else if (op == 3) {
							if (num2 == 0 || answer % num2 != 0) {
								j--;
								continue innerLoop;
							}
						}
						// ������Ŀ�����������ȼ����ⲽ���������ⲽ����Ǹ������߳���Сѧ�����꼶��Χ���˵����ѭ������i--������������
						String s = str + operator[op] + num2;
						num1 = Integer.parseInt(answer(s));
						if (num1 < 0 || num1 > 10000) {
							i--;
							continue outerLoop;
						}
						// ���Ҳ���ϣ����ⲽ��ӵ���Ŀ
						str += operator[op] + num2;
						answer = num2;
					}
					// ������ɵ���Ŀ��ӵ���Ŀ�ַ����ʹ��ַ�����
					src += "(" + i + ") " + str + lineBreak;
					result += "(" + i + ") " + str + " = " + answer(str.substring(str.indexOf(')') + 1)) + lineBreak;
				} else {
					// ����ĸ�������������ŵ���r�У�
					// ��a op b��op c �� a op��b op c��
					// ���Դ����ŵ�λ�ÿ��Է�Ϊ ��ǰ���ں������������λ��site��0��ǰ��1�ں�
					int site = rand.nextInt(2);
					if (site == 0) {
						// ����һ���ַ����������洢��һ�����Ŀ���ɣ���ʼ��Ϊ "("+��һ�����������
						String str = "( " + num1;
						// ����������ǰ�������ڵ������ֻ��Ӽ����ɣ��˳�������û�����壨��������Ҽ��㣩
						op = rand.nextInt(2);
						num2 = rand.nextInt(101);
						// ͬ����������Ŀ���ɹ������˳�ѭ������i--����������Ŀ
						if (op == 0) {
							if (num1 + num2 >= 1000) {
								i--;
								continue outerLoop;
							}
						} else {
							if (num1 - num2 < 0) {
								i--;
								continue outerLoop;
							}
						}
						// ���Ͼ���ӵ�str��
						str += operator[op] + num2 + " )";
						num1 = Integer.parseInt(answer(str));
						// ������ߵ������Ϊ�˳����������ż���û������
						op = rand.nextInt(2) + 2;
						// ͬ���ж��Ƿ������Ŀ���ɹ���
						if (op == 2) {
							if (num1 * num2 > 1000) {
								i--;
								continue outerLoop;
							}
						} else {
							if (num2 == 0 || num1 % num2 != 0) {
								i--;
								continue outerLoop;
							}
						}
						str += operator[op] + num2;
						num1 = Integer.parseInt(answer(str));
						if (num1 < 0 || num1 > 10000) {
							i--;
							continue outerLoop;
						}
						// ����Ŀ�ʹ𰸷ֱ���ӵ���Ŀ�ַ����ʹ��ַ�����
						src += "(" + i + ") " + str + lineBreak;
						result += "(" + i + ") " + str + " = " + answer(str) + lineBreak;
					} else {
						// �����ں��߼���������ǰ����
						op = rand.nextInt(2);
						num2 = rand.nextInt(101);
						if (op == 0) {
							if (num1 + num2 >= 1000) {
								i--;
								continue outerLoop;
							}
						} else {
							if (num1 - num2 < 0) {
								i--;
								continue outerLoop;
							}
						}
						String str = "( " + num1 + operator[op] + num2 + " )";
						num2 = Integer.parseInt(answer(str));
						op = rand.nextInt(2) + 2;
						num1 = rand.nextInt(101);
						if (op == 2) {
							if (num1 * num2 > 1000) {
								i--;
								continue outerLoop;
							}
						} else {
							if (num2 == 0 || num1 % num2 != 0) {
								i--;
								continue outerLoop;
							}
						}
						str = num1 + operator[op] + str;
						num1 = Integer.parseInt(answer(str));
						if (num1 < 0 || num1 > 10000) {
							i--;
							continue outerLoop;
						}
						src += "(" + i + ") " + str + lineBreak;
						result += "(" + i + ") " + str + " = " + answer(str) + lineBreak;
					}
				}
			}
		}
		System.out.println(src);
		System.out.print(result);
	}

	/**
	 * ���⺯��
	 * ���ݸ�������Ŀͨ�����ȳ��㷨���沨�����ʽ��˼·�ó�������
	 * @param src �����Ǵ�������Ŀ�ַ���
	 * @return ����ֵ��һ���ַ����������
	 */
	private static String answer(String src) {
		// ��������ջ�������洢���ֺ�������������ջ�ͷ���ջ
		Stack<String> print = new Stack<String>();
		Stack<String> operator = new Stack<String>();
		// ȥ����Ŀ�еĿո�
		String str = src.replace(" ", "");
		// ����һ��number�ַ����������洢���֣����Ὣ���ֲ�֣�����23������2��3
		String number = "";
		// �����ַ���str����������ӵ����ջ���Ƿ��žͺͷ���ջջ��Ԫ�رȽ����ȼ�
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				number += c + "";
				// ���������󣬽�number��ջ�����ջ����ͬʱ��number���
				if (i + 1 >= str.length()) {
					print.push(number);
					number = "";
				}
			} else {
				// ���number�������֣���number��ջ�����ջ����ͬʱ��number���
				if (!number.isEmpty()) {
					print.push(number);
					number = "";
				}
				// �����ǰ���ţ�1���������Ż��ߣ�2������ջΪ�ջ��ߣ�3����ǰ�������ȼ�>ջ���������ȼ���ֱ����ջ
				if (c == '(' || operator.isEmpty() || comparePriority(c + "", operator.peek()) == 1) {
					operator.push(c + "");
				} else if (c == ')') {
					// �����ǰ����Ϊ�����ţ���Ҫ��ջֱ��������һ��������Ϊֹ��
					String stackTop = operator.pop();
					while (!stackTop.equals("(")) {
						// ��ջ��ʱ��ͬʱ�����沨�����ʽ�ļ��㣬ȡ�����ջ��ǰ������
						int number1 = Integer.parseInt(print.pop());
						int number2 = Integer.parseInt(print.pop());
						int number3;
						// ����ջ�ķ��ż���ó����number3
						if (stackTop.equals("+")) {
							number3 = number2 + number1;
						} else if (stackTop.equals("-")) {
							number3 = number2 - number1;
						} else if (stackTop.equals("��")) {
							number3 = number2 * number1;
						} else {
							number3 = number2 / number1;
						}
						// ���������ջ�����ջ��
						print.push(number3 + "");
						stackTop = operator.pop();
					}
				} else if (comparePriority(c + "", operator.peek()) != 1) {
					// �����ǰ�������ȼ�<=ջ���������ȼ�����Ҫ��ջֱ����ǰ�������ȼ�>ջ���������ȼ�Ϊֹ
					while (!operator.empty() && comparePriority(c + "", operator.peek()) < 1) {
						// ͬ����ջ��ʱ��ͬʱ�����沨�����ʽ�ļ��㣬ȡ�����ջ��ǰ������
						int number1 = Integer.parseInt(print.pop());
						int number2 = Integer.parseInt(print.pop());
						int number3;
						// ����ջ�ķ��ż���ó����number3
						String stackTop = operator.peek();
						if (stackTop.equals("+")) {
							number3 = number2 + number1;
						} else if (stackTop.equals("-")) {
							number3 = number2 - number1;
						} else if (stackTop.equals("��")) {
							number3 = number2 * number1;
						} else {
							number3 = number2 / number1;
						}
						// ���������ջ�����ջ��
						print.push(number3 + "");
						stackTop = operator.pop();
					}
					// ��󽫵�ǰ������ջ������ջ��
					operator.push(c + "");
				}
			}
		}

		// �����Ҫ������ջ�еķ������γ�ջ��ͬʱ�����沨�����ʽ����
		while (!operator.empty()) {
			String stackTop = operator.pop();
			int number1 = Integer.parseInt(print.pop());
			int number2 = Integer.parseInt(print.pop());
			int number3;
			if (stackTop.equals("+")) {
				number3 = number2 + number1;
			} else if (stackTop.equals("-")) {
				number3 = number2 - number1;
			} else if (stackTop.equals("��")) {
				number3 = number2 * number1;
			} else {
				number3 = number2 / number1;
			}
			print.push(number3 + "");
		}
		return print.peek();
	}

	/**
	 * �Ƚ���������ȼ�����
	 * @param a �˲���Ϊ����һ�������
	 * @param b �˲���Ϊ��һ�������
	 * @return 	�����һ������������ȼ����ڵڶ�������������ȼ������� 1�� 
	 * 			�����һ������������ȼ����ڵڶ�������������ȼ������� 0��
	 *         	�����һ������������ȼ�С�ڵڶ�������������ȼ������� -1��
	 */
	private static int comparePriority(String a, String b) {
		if (a.equals(b)) {
			return 0;
		} else if (Priority(a) > Priority(b)) {
			return 1;
		} else if (Priority(a) < Priority(b)) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * ��ѯ��������ȼ�����
	 * @param op ����Ϊ����ѯ�������
	 * @return ��������������ȼ�
	 */
	private static int Priority(String op) {
		if (op.equals("��") || op.equals("��")) {
			return 2;
		} else if (op.equals("+") || op.equals("-")) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * ��麯�� �������Ĳ����Ƿ���ϱ�׼
	 * @param s ������ ������ַ���
	 * @return �������Ĳ������ϱ�׼������true�����򷵻�false��
	 */
	public static boolean check(String[] s) {
		if (checkFormat(s)) {
			if (s[0].equals("-n"))
				return checkData(s[1], s[3]);
			else
				return checkData(s[3], s[1]);
		} else {
			System.out.println("�ף������ʽ����Ŷ");
			return false;
		}
	}

	/**
	 * ������ݺ��� ��check��麯�����ã��ڼ�����𵽼�����ݵ����á�
	 * @param strN �˲�������������
	 * @param strGrade �˲���Ϊ�������꼶
	 * @return ����������꼶�����ϱ�׼������true�����򷵻�false��
	 */
	private static boolean checkData(String strN, String strGrade) {
		if (!strN.matches("\\d*")) {
			if (strN.matches("-\\d*")) {
				System.out.println("����һ����ȷ������Ŷ������Ϊ��");
				return false;
			} else {
				System.out.println("�������������������!");
				return false;
			}
		} else {
			n = Integer.parseInt(strN);
			if (n > 100) {
				System.out.println("�������������������Сѧ��Ŷ");
				return false;
			} else if (n == 0) {
				System.out.println("���������Ϊ�㣬���һ�Ҫ��Ҫ����ѽ");
				return false;
			}
		}

		if (!strGrade.matches("[1-3]")) {
			System.out.println("�Բ����ף�Ŀǰֻ��1��3�꼶Ŷ");
			return false;
		} else {
			grade = Integer.parseInt(strGrade);
		}

		return true;
	}

	/**
	 * ����ʽ���� ��check��麯�����ã��ڼ�����𵽼���ʽ�����á�
	 * @param s ����Ϊ�������ַ�������
	 * @return ���������ַ�������ĸ�ʽ���ϱ�׼������true�����򷵻�false��
	 */
	private static boolean checkFormat(String[] s) {
		if ((s[0].matches("-n") && s[2].matches("-grade")) || (s[0].matches("-grade") && s[2].matches("-n"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ��������ļ�����
	 */
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