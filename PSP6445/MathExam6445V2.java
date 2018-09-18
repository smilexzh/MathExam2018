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

public class MathExam6445V2{
	public static int n, grade, N = 100;
	public static String[] operator = { " + ", " - ", " × ", " ÷ " };

	public static void main(String[] args) {

		if (check(args)) {
			System.out.println("通过检查");
			create();
			try {
				System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("out.txt")), true));
			} catch (FileNotFoundException e) {
				System.out.println("输出重定向失败失败！");
				e.printStackTrace();
			}
			if (args[0].equals("-n")) {
				work();
			} else {
				work();
			}
		}
	}

	public static void work() {
		int num1, num2, answer, op;
		Random rand = new Random();
		String src = "";
		String result = "";
		String lineBreak = "\r\n";
		outerLoop: for (int i = 1; i <= n; i++) {
			if (grade == 1) {
				num1 = rand.nextInt(101);
				num2 = rand.nextInt(101);
				op = rand.nextInt(2);
				if (op == 0 && num1 + num2 <= 100) {
					answer = num1 + num2;
				} else if (op == 1 && num1 - num2 > 0) {
					answer = num1 - num2;
				} else {
					i--;
					continue;
				}
				src += "(" + i + ") " + num1 + operator[op] + num2 + lineBreak;
				result += "(" + i + ") " + num1 + operator[op] + num2 + " = " + answer + lineBreak;
			} else if (grade == 2) {
				op = rand.nextInt(4);
				if (op < 2) {
					num1 = rand.nextInt(101);
					num2 = rand.nextInt(101);
				} else {
					num1 = rand.nextInt(11);
					num2 = rand.nextInt(11);
				}
				if (op == 0 && num1 + num2 <= 100) {
					answer = num1 + num2;
				} else if (op == 1 && num1 - num2 >= 0) {
					answer = num1 - num2;
				} else if (op == 2 && num1 * num2 <= 100) {
					answer = num1 * num2;
				} else if (op == 3 && num2 != 0) {
					answer = num1 / num2;
				} else {
					i--;
					continue;
				}
				src += "(" + i + ") " + num1 + operator[op] + num2 + lineBreak;
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
				num1 = rand.nextInt(101);
				answer = num1;
				int isBracket = rand.nextInt(2);
				if (isBracket == 0) {
					int opNum = rand.nextInt(3) + 2;
					String str = num1 + "";
					innerLoop: for (int j = 1; j <= opNum; j++) {
						op = rand.nextInt(4);
						num2 = rand.nextInt(101);
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
						String s = str + operator[op] + num2;
						num1 = Integer.parseInt(answer(s));
						if (num1 < 0 || num1 > 10000) {
							i--;
							continue outerLoop;
						}
						str += operator[op] + num2;
						answer = num2;
					}
					src += "(" + i + ") " + str + lineBreak;
					result += "(" + i + ") " + str + " = " + answer(str.substring(str.indexOf(')') + 1)) + lineBreak;
				} else {
					int site = rand.nextInt(2);
					if (site == 0) {
						String str = "( " + num1;
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
						str += operator[op] + num2 + " )";
						num1 = Integer.parseInt(answer(str));
						op = rand.nextInt(2)+2;
						if(op==2) {
							if(num1*num2>1000) {
								i--;
								continue outerLoop;
							}
						} else {
							if(num2==0 || num1%num2!=0) {
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
						src += "(" + i + ") " + str + lineBreak;
						result += "(" + i + ") " + str + " = " + answer(str) + lineBreak;
					} else {
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
						if(op==2) {
							if(num1*num2>1000) {
								i--;
								continue outerLoop;
							}
						} else {
							if(num2==0 || num1%num2!=0) {
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

	private static String answer(String src) {
		Stack<String> print = new Stack<String>();
		Stack<String> operator = new Stack<String>();
		String str = src.replace(" ", "");
		String number = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				number += c + "";
				if (i + 1 >= str.length()) {
					print.push(number);
					number = "";
				}
			} else {
				if (!number.isEmpty()) {
					print.push(number);
					number = "";
				}
				if (c == '(' || operator.isEmpty() || comparePriority(c + "", operator.peek()) == 1) {
					operator.push(c + "");
				} else if (c == ')') {
					String stackTop = operator.pop();
					while (!stackTop.equals("(")) {
						int number1 = Integer.parseInt(print.pop());
						int number2 = Integer.parseInt(print.pop());
						int number3;
						if (stackTop.equals("+")) {
							number3 = number2 + number1;
						} else if (stackTop.equals("-")) {
							number3 = number2 - number1;
						} else if (stackTop.equals("×")) {
							number3 = number2 * number1;
						} else {
							number3 = number2 / number1;
						}
						print.push(number3 + "");
						stackTop = operator.pop();
					}
				} else if (comparePriority(c + "", operator.peek()) != 1) {
					while (!operator.empty() && comparePriority(c + "", operator.peek()) < 1) {
						int number1 = Integer.parseInt(print.pop());
						int number2 = Integer.parseInt(print.pop());
						int number3;
						String stackTop = operator.peek();
						if (stackTop.equals("+")) {
							number3 = number2 + number1;
						} else if (stackTop.equals("-")) {
							number3 = number2 - number1;
						} else if (stackTop.equals("×")) {
							number3 = number2 * number1;
						} else {
							number3 = number2 / number1;
						}
						print.push(number3 + "");
						stackTop = operator.pop();
					}
					operator.push(c + "");
				}
			}
		}

		while (!operator.empty()) {
			String stackTop = operator.pop();
			int number1 = Integer.parseInt(print.pop());
			int number2 = Integer.parseInt(print.pop());
			int number3;
			if (stackTop.equals("+")) {
				number3 = number2 + number1;
			} else if (stackTop.equals("-")) {
				number3 = number2 - number1;
			} else if (stackTop.equals("×")) {
				number3 = number2 * number1;
			} else {
				number3 = number2 / number1;
			}
			print.push(number3 + "");
		}
		return print.peek();
	}

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

	private static int Priority(String op) {
		if (op.equals("×") || op.equals("÷")) {
			return 2;
		} else if (op.equals("+") || op.equals("-")) {
			return 1;
		} else {
			return 0;
		}
	}

	public static boolean check(String[] s) {
		if (checkFormat(s)) {
			if (s[0].equals("-n"))
				return checkData(s[1], s[3]);
			else
				return checkData(s[3], s[1]);
		} else {
			System.out.println("亲，输入格式错误哦");
			return false;
		}
	}

	private static boolean checkData(String strN, String strGrade) {
		if (!strN.matches("\\d*")) {
			if (strN.matches("-\\d*")) {
				System.out.println("输入一个正确的数字哦，不能为负");
				return false;
			} else {
				System.out.println("输入的题数必须是数字!");
				return false;
			}
		} else {
			n = Integer.parseInt(strN);
			if (n > 100) {
				System.out.println("输入的题数过大，体谅下小学生哦");
				return false;
			} else if (n == 0) {
				System.out.println("输入的题数为零，那我还要不要出题呀");
				return false;
			}
		}

		if (!strGrade.matches("[1-3]")) {
			System.out.println("对不起，亲，目前只有1到3年级哦");
			return false;
		} else {
			grade = Integer.parseInt(strGrade);
		}

		return true;
	}

	private static boolean checkFormat(String[] s) {
		if ((s[0].matches("-n") && s[2].matches("-grade")) || (s[0].matches("-grade") && s[2].matches("-n"))) {
			return true;
		} else {
			return false;
		}
	}

	public static void create() {
		File file = new File("out.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("文件创建失败！");
				e.printStackTrace();
			}
		}
	}
}