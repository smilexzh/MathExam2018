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
	public static String[] operator = { " + ", " - ", " × ", " ÷ " };

	/**
	 * 主方法 用来控制输入和输出，并调用其它方法
	 * @param args  参数输入 题数和年级
	 */
	public static void main(String[] args) {
		if (check(args)) {
			System.out.println("通过检查");
			create();
			// 输出重定向
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

	/**
	 * 出题函数 根据输入的年级和题数生成对应题目，并打印题目和最后答案
	 */
	public static void work() {

		int num1, num2, answer, op;
		Random rand = new Random();
		// src:题目字符串。 result:答案字符串。lineBreak:换行字符串。
		String src = "";
		String result = "";
		String lineBreak = "\r\n";
		// 标记为为外层循坏，以便continue退出。
		outerLoop: for (int i = 1; i <= n; i++) {
			if (grade == 1) {
				num1 = rand.nextInt(101);
				num2 = rand.nextInt(101);
				// 一年级只有加减，所以运算符随机生成数为0和1
				op = rand.nextInt(2);
				// 符合条件则计算答案
				if (op == 0 && num1 + num2 <= 100) {
					answer = num1 + num2;
				} else if (op == 1 && num1 - num2 > 0) {
					answer = num1 - num2;
				} else {
					// 不符合条件则退出本次循环，并i--重新生成这一题
					i--;
					continue;
				}
				// 将题目添加到题目字符串，答案添加到答案字符串中
				src += "(" + i + ") " + num1 + operator[op] + num2 + lineBreak;
				result += "(" + i + ") " + num1 + operator[op] + num2 + " = " + answer + lineBreak;
			} else if (grade == 2) {
				// 二年级涉及乘除，所以运算符随机生成数为0到3
				op = rand.nextInt(4);
				if (op < 2) {
					// 加减为百以内的加减
					num1 = rand.nextInt(101);
					num2 = rand.nextInt(101);
				} else {
					// 乘除为表内乘除，所以取值范围是0到10
					num1 = rand.nextInt(11);
					num2 = rand.nextInt(11);
				}
				// 符合条件则计算答案
				if (op == 0 && num1 + num2 <= 100) {
					answer = num1 + num2;
				} else if (op == 1 && num1 - num2 >= 0) {
					answer = num1 - num2;
				} else if (op == 2 && num1 * num2 <= 100) {
					answer = num1 * num2;
				} else if (op == 3 && num2 != 0) {
					answer = num1 / num2;
				} else {
					// 不符合条件则退出本次循环，并i--重新生成这一题
					i--;
					continue;
				}
				// 将题目添加到题目字符串中
				src += "(" + i + ") " + num1 + operator[op] + num2 + lineBreak;
				// 将答案添加到答案字符串中，如果是除法要考虑是否有余数
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
				// 三年级，新增功能
				num1 = rand.nextInt(101);
				// 这里answer变量不是用来存储结果，而是用来存储上一轮的第二个随机生成数。
				answer = num1;
				// isBracket随机生成数0和1，用来存储是否有括号，0没有括号，1有括号。
				int isBracket = rand.nextInt(2);
				if (isBracket == 0) {
					// opNum随机生成数2到4，用来存储运算符个数
					int opNum = rand.nextInt(3) + 2;
					// 定义一个字符串，用来存储这一题的题目生成，初始化为第一个随机生成数
					String str = num1 + "";
					// 标记为为内层循坏，以便continue退出。
					innerLoop: for (int j = 1; j <= opNum; j++) {
						op = rand.nextInt(4);
						num2 = rand.nextInt(101);
						// 不符合题目生成规则则退出本次循环，并j--重新生成运算符和第二个随机生成数
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
						// 符合题目生成条件，先计算这步结果，如果这步结果是负数或者超出小学生三年级范围，退到外层循环，并i--重新生成这题
						String s = str + operator[op] + num2;
						num1 = Integer.parseInt(answer(s));
						if (num1 < 0 || num1 > 10000) {
							i--;
							continue outerLoop;
						}
						// 结果也符合，将这步添加到题目
						str += operator[op] + num2;
						answer = num2;
					}
					// 最后将生成的题目添加到题目字符串和答案字符串中
					src += "(" + i + ") " + str + lineBreak;
					result += "(" + i + ") " + str + " = " + answer(str.substring(str.indexOf(')') + 1)) + lineBreak;
				} else {
					// 最多四个运算符，有括号的情況有：
					// （a op b）op c 和 a op（b op c）
					// 所以从括号的位置可以分为 在前和在后，随机生成括号位置site，0在前，1在后
					int site = rand.nextInt(2);
					if (site == 0) {
						// 定义一个字符串，用来存储这一题的题目生成，初始化为 "("+第一个随机生成数
						String str = "( " + num1;
						// 由于括号在前，括号内的运算符只需加减即可，乘除加括号没有意义（都需从左到右计算）
						op = rand.nextInt(2);
						num2 = rand.nextInt(101);
						// 同理，不符合题目生成规则则退出循环，并i--重新生成题目
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
						// 符合就添加到str中
						str += operator[op] + num2 + " )";
						num1 = Integer.parseInt(answer(str));
						// 括号外边的运算符为乘除，否则括号加了没有意义
						op = rand.nextInt(2) + 2;
						// 同理，判断是否符合题目生成规则
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
						// 将题目和答案分别添加到题目字符串和答案字符串中
						src += "(" + i + ") " + str + lineBreak;
						result += "(" + i + ") " + str + " = " + answer(str) + lineBreak;
					} else {
						// 括号在后，逻辑和括号在前类似
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
	 * 解题函数
	 * 根据给出的题目通过调度场算法和逆波兰表达式的思路得出计算结果
	 * @param src 参数是带求解的题目字符串
	 * @return 返回值是一个字符串：最后结果
	 */
	private static String answer(String src) {
		// 定义两个栈，用来存储数字和运算符，即输出栈和符号栈
		Stack<String> print = new Stack<String>();
		Stack<String> operator = new Stack<String>();
		// 去掉题目中的空格
		String str = src.replace(" ", "");
		// 定义一个number字符串，用来存储数字，不会将数字拆分，例如23不会拆成2和3
		String number = "";
		// 遍历字符串str，是数字添加到输出栈，是符号就和符号栈栈顶元素比较优先级
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				number += c + "";
				// 如果到达最后，将number入栈（输出栈），同时将number清空
				if (i + 1 >= str.length()) {
					print.push(number);
					number = "";
				}
			} else {
				// 如果number里有数字，将number入栈（输出栈），同时将number清空
				if (!number.isEmpty()) {
					print.push(number);
					number = "";
				}
				// 如果当前符号（1）是左括号或者（2）符号栈为空或者（3）当前符号优先级>栈顶符号优先级，直接入栈
				if (c == '(' || operator.isEmpty() || comparePriority(c + "", operator.peek()) == 1) {
					operator.push(c + "");
				} else if (c == ')') {
					// 如果当前符号为右括号，需要出栈直到遇到第一个左括号为止。
					String stackTop = operator.pop();
					while (!stackTop.equals("(")) {
						// 出栈的时候，同时进行逆波兰表达式的计算，取出输出栈的前两个数
						int number1 = Integer.parseInt(print.pop());
						int number2 = Integer.parseInt(print.pop());
						int number3;
						// 按出栈的符号计算得出结果number3
						if (stackTop.equals("+")) {
							number3 = number2 + number1;
						} else if (stackTop.equals("-")) {
							number3 = number2 - number1;
						} else if (stackTop.equals("×")) {
							number3 = number2 * number1;
						} else {
							number3 = number2 / number1;
						}
						// 并将结果入栈（输出栈）
						print.push(number3 + "");
						stackTop = operator.pop();
					}
				} else if (comparePriority(c + "", operator.peek()) != 1) {
					// 如果当前符号优先级<=栈顶符号优先级，需要出栈直到当前符号优先级>栈顶符号优先级为止
					while (!operator.empty() && comparePriority(c + "", operator.peek()) < 1) {
						// 同理，出栈的时候，同时进行逆波兰表达式的计算，取出输出栈的前两个数
						int number1 = Integer.parseInt(print.pop());
						int number2 = Integer.parseInt(print.pop());
						int number3;
						// 按出栈的符号计算得出结果number3
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
						// 并将结果入栈（输出栈）
						print.push(number3 + "");
						stackTop = operator.pop();
					}
					// 最后将当前符号入栈（符号栈）
					operator.push(c + "");
				}
			}
		}

		// 最后需要将符号栈中的符号依次出栈，同时进行逆波兰表达式计算
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

	/**
	 * 比较运算符优先级方法
	 * @param a 此参数为其中一个运算符
	 * @param b 此参数为另一个运算符
	 * @return 	如果第一个运算符的优先级大于第二个运算符的优先级，返回 1。 
	 * 			如果第一个运算符的优先级等于第二个运算符的优先级，返回 0。
	 *         	如果第一个运算符的优先级小于第二个运算符的优先级，返回 -1。
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
	 * 查询运算符优先级方法
	 * @param op 参数为待查询的运算符
	 * @return 返回运算符的优先级
	 */
	private static int Priority(String op) {
		if (op.equals("×") || op.equals("÷")) {
			return 2;
		} else if (op.equals("+") || op.equals("-")) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 检查函数 检查输入的参数是否符合标准
	 * @param s 参数是 输入的字符串
	 * @return 如果输入的参数符合标准，返回true；否则返回false。
	 */
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

	/**
	 * 检查数据函数 由check检查函数调用，在检查中起到检查数据的作用。
	 * @param strN 此参数待检查的题数
	 * @param strGrade 此参数为待检查的年级
	 * @return 如果题数和年级都符合标准，返回true；否则返回false。
	 */
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

	/**
	 * 检查格式函数 由check检查函数调用，在检查中起到检查格式的作用。
	 * @param s 参数为待检查的字符串数组
	 * @return 如果输入的字符串数组的格式符合标准，返回true；否则返回false。
	 */
	private static boolean checkFormat(String[] s) {
		if ((s[0].matches("-n") && s[2].matches("-grade")) || (s[0].matches("-grade") && s[2].matches("-n"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 创建输出文件方法
	 */
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