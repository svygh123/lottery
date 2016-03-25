package cn.lottery.util;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;


public class Lotteryini {
	private static Properties ini = null;

	static {
		try {
			ini = new Properties();
			ini.load(new FileInputStream(Common.CONFIG_PATH));
		} catch (Exception ex) {
			System.out.println("Load CONFIG.INI is false!!");
		}
	}

	/**
	 * =======================================================================**
	 * [## private sunini() {} ]: 构造函数 参数 ：无 返回值 ：无 修饰符 ：private 功能
	 * ：防止实例化sunini对象
	 * =======================================================================**
	 */
	private Lotteryini() {
	}

	/**
	 * =======================================================================**
	 * [## public static String getIniKey (String k) {} ]: 参数 ：String对象表示键 返回值
	 * ：String对象表示k键所对应的键值，如果失败则返回空串 修饰符 ：public static 可以不实例化对象而直接调用方法 功能
	 * ：获得INI文件中的指定键的键值
	 * =======================================================================**
	 */
	public static String getIniKey(String k) {
		if (!ini.containsKey(k)) { // 是否有 k 这个键
			System.out.println("The [ " + k + " ] Key is not exist!!");
			return "";
		}// End if(!ini.containsKey (k))
		return ini.get(k).toString();
	}

	/**
	 * =======================================================================**
	 * [## public static void setIniKey (String k, String v) {} ]: 参数 ：String
	 * k对象表示键,String v对象表示键值 返回值 ：无 修饰符 ：public static 可以不实例化对象而直接调用方法 功能
	 * ：设置k键的键值为v对象
	 * =======================================================================**
	 */
	public static void setIniKey(String k, String v) {
		if (!ini.containsKey(k)) { // 是否有 k 这个键
			System.out.println("The [ " + k + " ] Key is not exist!!");
			return;
		}// End if(!ini.containsKey (k))
		ini.put(k, v);
	}

	/**
	 * =======================================================================**
	 * [## public static void saveIni (String k[]) {} ]: 参数 ：String
	 * k[]字符串数组表示要保存的所有键 返回值 ：无 修饰符 ：public static 可以不实例化对象而直接调用方法 功能
	 * ：将k字符数组中所有键所对应的键值保存到INI文件中
	 * =======================================================================**
	 */
	public static void saveIni(String k[]) {
		try {
			FileWriter fw = new FileWriter(Common.CONFIG_PATH);
			BufferedWriter bw = new BufferedWriter(fw);
			// 循环变量i是k字符串数组的下标
			for (int i = 0; i < k.length; i++) {
				bw.write(k[i] + "=" + getIniKey(k[i]));
				bw.newLine();
			}// End for
			bw.close();
			fw.close();
		} catch (Exception ex) {
			System.out.println("Save CONFIG.INI is false.");
		}// End try
	}

}
