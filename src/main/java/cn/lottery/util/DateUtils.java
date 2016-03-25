package cn.lottery.util;
import java.util.Date;
import java.text.SimpleDateFormat;

public class DateUtils {

	public static String getCurrentDateTime(String format) {
		long nCurrentTime = System.currentTimeMillis();
		Date utilDate = new Date(nCurrentTime);
		SimpleDateFormat df = new SimpleDateFormat(format);
		String currentDateTime = df.format(utilDate);
		return currentDateTime;
	}

	public static String getCurrentDateTime() {
		long nCurrentTime = System.currentTimeMillis();
		Date utilDate = new Date(nCurrentTime);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = df.format(utilDate);
		return currentDateTime;
	}

	public static String getDateStr() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
		return formatter.format(new Date());
	}

}
