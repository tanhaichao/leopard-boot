package io.leopard.boot.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.ToDoubleFunction;

/**
 * 小数运算工具类.
 * 
 * @author 谭海潮
 *
 */
public class DecimalUtil {

	public static void isSecure(double decimal) {
		isSecure(decimal, 2);
	}

	public static void isSecure(double decimal, String name) {
		isSecure(decimal, 2, name);
	}

	public static void isSecure1(double decimal) {
		isSecure(decimal, 1);
	}

	public static void isSecure1(double decimal, String name) {
		isSecure(decimal, 1, name);
	}

	public static void isSecure2(double decimal) {
		isSecure(decimal, 2);
	}

	public static void isSecure2(double decimal, String name) {
		isSecure(decimal, 2, name);
	}

	public static void isSecure3(double decimal) {
		isSecure(decimal, 3);
	}

	public static void isSecure3(double decimal, String name) {
		isSecure(decimal, 3, name);
	}

	public static void isSecure4(double decimal) {
		isSecure(decimal, 4);
	}

	public static void isSecure4(double decimal, String name) {
		isSecure(decimal, 4, name);
	}

	public static void isSecure5(double decimal) {
		isSecure(decimal, 5);
	}

	public static void isSecure5(double decimal, String name) {
		isSecure(decimal, 5, name);
	}

	public static void isSecure6(double decimal) {
		isSecure(decimal, 6);
	}

	public static void isSecure6(double decimal, String name) {
		isSecure(decimal, 6, name);
	}

	public static void isSecure(double decimal, int precision) {
		isSecure(decimal, precision, null);
	}

	/**
	 * 小数点是否安全
	 * 
	 * @param price
	 * @param precision
	 * @param name
	 */
	public static void isSecure(double decimal, int precision, String name) {
		if (name == null) {
			name = "";
		}
		if (decimal <= 0) {
			throw new IllegalArgumentException(name + "必须大于0[" + toString(decimal) + "].");
		}
		// TODO 这里是否有问题?
		// int power = (int) Math.pow(10, precision);// 10的N次方
		// if (decimal > power) {
		// throw new IllegalArgumentException(name + "不能超过" + power + "[" + toString(decimal) + "].");
		// }
		if (DecimalUtil.count(decimal) > 6) {
			throw new IllegalArgumentException(name + "小数点位数不能超过" + precision + "位[" + toString(decimal) + "].");
		}
	}

	/**
	 * 判断是否2位小数点.
	 * 
	 * @param num
	 */
	public static void isScale(double num) {
		int count = count(num);
		if (count > 2) {
			throw new IllegalArgumentException("小数点位数不能超过2位[" + count + "].");
		}
	}

	/**
	 * 判断是否4位小数点.
	 * 
	 * @param num
	 */
	public static void isScale4(double num) {
		int count = count(num);
		if (count > 4) {
			throw new IllegalArgumentException("小数点位数不能超过4位[" + count + "].");
		}
	}

	/**
	 * 获取小数点位数
	 * 
	 * @param num
	 */
	public static int count(double num) {
		if ((num - 1) > 99999999999D) {// 999亿， 小数点只能计算到5位
			throw new IllegalArgumentException("超过了99999999999(999亿)，不能正确计算小数点位数[" + num + "].");
		}
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(5);
		String s = nf.format(num);
		int index = s.indexOf(".");
		if (index <= 0) {
			return 0;
		}
		int count = s.length() - index - 1;
		return count;
	}

	// /**
	// * 获取小数点位数
	// *
	// * @param num
	// */
	// public static int count2(double num) {
	// long num2 = (long) num;
	// BigDecimal b1 = new BigDecimal(Double.toString(num));
	// BigDecimal b2 = new BigDecimal(Long.toString(num2));
	// double num3 = b1.subtract(b2).doubleValue();
	// System.out.println("num3:" + num3);
	// return 0;
	// }

	// /**
	// * 获取小数点位数
	// *
	// * @param num
	// */
	// public static int count2(double num) {
	// double abc1 = num * 1000000;
	// long abc2 = (long) abc1;
	//
	// long num2 = (long) num * 100000;
	// double num3 = num - num2;
	// System.out.println("num2:" + num2 + " num3:" + num3 + " abc1:" + abc1 + " abc2:" + abc2);
	// if ((num - 1) > 99999999999D) {// 999亿， 小数点只能计算到5位
	// // throw new IllegalArgumentException("超过了99999999999(999亿)，不能正确计算小数点位数[" + num + "].");
	// }
	// NumberFormat nf = NumberFormat.getNumberInstance();
	// nf.setMaximumFractionDigits(5);
	// String s = nf.format(num);
	// int index = s.indexOf(".");
	// if (index == 0) {
	// return 0;
	// }
	// int count = s.length() - index - 1;
	// return count;
	// }

	/**
	 * 4舍5入，保留2位小数点
	 * 
	 * @param num
	 * @return
	 */
	public static double scale(double num) {
		return new BigDecimal(num).setScale(2, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 保留2位小数点
	 * 
	 * @param num
	 * @return
	 */
	public static double scaleRoundFloor(double num) {
		return new BigDecimal(num).setScale(2, java.math.BigDecimal.ROUND_FLOOR).doubleValue();
	}

	/**
	 * 4舍5入，保留4位小数点
	 * 
	 * @param num
	 * @return
	 */
	public static double scale4(double num) {
		return new BigDecimal(num).setScale(4, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double sum(double v1, double... nums) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal sum = b1;
		for (double num : nums) {
			BigDecimal b2 = new BigDecimal(Double.toString(num));
			sum = b1.add(b2);
		}
		return sum.doubleValue();
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1 被加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算(v1-v2)。
	 * 
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double divide(double v1, double v2) {
		return divide(v1, v2, 10);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 */
	public static double divide(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	private static String toString(double decimal) {
		// TODO 还没有处理精度问题
		return new BigDecimal(decimal).toString();
	}

	public static double sum(List<Double> numList) {
		double sum = 0;
		for (double num : numList) {
			sum = add(sum, num);
		}
		return sum;
	}

	/**
	 * 获取比率.
	 * 
	 * @param current 分子
	 * @param total 分母
	 * @param scale 精度
	 * @return 比率
	 */
	public static double rate(double current, double total) {
		return rate(current, total, 2);
	}

	public static double rate(double current, double total, int scale) {
		double rate = divide(current, total);
		return new BigDecimal(rate).setScale(scale, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static <T> double sumDouble(List<T> list, ToDoubleFunction<? super T> mapper) {
		return list.stream().mapToDouble(mapper).sum();
	}

	/**
	 * 转换成数字的fee
	 * 
	 * @param fee 费用/金额/价格
	 * @param scale 精度
	 * @return
	 */
	public static int toIntFee(double fee, int scale) {
		int multiple = (int) Math.pow(10, scale);// 10的scale次方
		int intFee = (int) DecimalUtil.multiply(fee, multiple);// TODO 2位精度
		if (true) {
			double fee2 = DecimalUtil.divide(intFee, multiple);
			// System.out.println("intFee:" + intFee + " fee:" + fee + " fee2:" + fee2 + " multiple:" + multiple);
			if (fee2 != fee) {
				throw new RuntimeException("精度有问题[" + fee + "  " + fee2 + "].");
			}
		}
		return intFee;
	}
}
