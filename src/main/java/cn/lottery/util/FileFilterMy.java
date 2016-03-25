package cn.lottery.util;
import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FileFilterMy extends FileFilter {

	@Override
	public boolean accept(File pathname) {
		String fileName = pathname.getAbsolutePath().toUpperCase();
		if (fileName.endsWith(".GIF") || fileName.endsWith(".JPG")
				|| fileName.endsWith(".JPEG") || fileName.endsWith(".PNG")
				|| fileName.endsWith(".BMP"))
			return true;

		return false;
	}

	@Override
	public String getDescription() {
		return "图像文件(*.gif; *.jpg; *.jpeg; *.png; *.bmp)";
	}

}