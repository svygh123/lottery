package cn.lottery.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JOptionPane;

public class FileUtil {
	/**
	 * 删除文件，可以是单个文件或文件夹
	 * 
	 * @param fileName
	 *            待删除的文件名
	 * @return 文件删除成功返回true,否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			// System.out.println("删除文件失败：" + fileName + "文件不存在");
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			// System.out.println("删除单个文件" + fileName + "成功！");
			return true;
		} else {
			// System.out.println("删除单个文件" + fileName + "失败！");
			return false;
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// System.out.println("删除目录失败" + dir + "目录不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			// System.out.println("删除目录失败");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			// System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			// System.out.println("删除目录" + dir + "失败！");
			return false;
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param srcFileName
	 *            被复制的文件路径
	 * @param destFileName
	 *            目标文件路径(复制到的文件路径,含有文件名)
	 * @param warnMessage
	 *            当目标文件已经存在时，提示是否覆盖
	 * @return 文件复制成功返回true,否则返回false
	 */
	public static boolean copyFile(String srcFileName, String destFileName,
			String warnMessage) {
		// 判断原文件是否存在
		File srcFile = new File(srcFileName);

		if (!srcFile.exists()) {
			// System.out.println("复制文件失败：原文件" + srcFileName + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			// System.out.println("复制文件失败：" + srcFileName + "不是一个文件！");
			return false;
		}

		// 判断目标文件是否存在
		File destFile = new File(destFileName);

		if (destFile.exists()) {
			// 如果目标文件存在，而且复制时允许覆盖。
			int isOverride = 0;// YES_OPTION = 0; NO_OPTION = 1;
			isOverride = JOptionPane.showConfirmDialog(null, warnMessage,
					"确认提示", JOptionPane.YES_NO_OPTION);

			if (isOverride == 0) {
				// 删除已存在的目标文件，无论目标文件是目录还是单个文件
				// System.out.println("目标文件已存在，准备删除它！");
				if (!FileUtil.delete(destFileName)) {
					// System.out.println("复制文件失败：删除目标文件" + destFileName +
					// "失败！");
					return false;
				}

			} else {
				// System.out.println("复制文件失败：目标文件" + destFileName + "已存在！");
				return false;
			}
		} else {
			if (!destFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				// System.out.println("目标文件所在的目录不存在，准备创建它！");
				if (!destFile.getParentFile().mkdirs()) {
					// System.out.println("复制文件失败：创建目标文件所在的目录失败！");
					return false;
				}
			}
		}

		// 准备复制文件
		int byteread = 0; // 读取的位数
		InputStream in = null;
		OutputStream out = null;

		try {
			// 打开原文件
			in = new FileInputStream(srcFile);
			// 打开连接到目标文件的输出流
			out = new FileOutputStream(destFile);

			byte[] buffer = new byte[1024];

			// 一次读取1024个字节，当byteread为-1时表示文件已经读完
			while ((byteread = in.read(buffer)) != -1) {
				// 将读取的字节写入输出流
				out.write(buffer, 0, byteread);
			}

			// System.out.println("复制单个文件" + srcFileName + "至" + destFileName
			// + "成功！");

			return true;
		} catch (Exception e) {
			// System.out.println("复制文件失败：" + e.getMessage());
			return false;
		} finally {
			// 关闭输入输出流，注意先关闭输出流，再关闭输入流
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 判断文件及目录是否存在，若不存在则创建文件及目录
	 * 
	 * @param filepath
	 * @return
	 * @throws Exception
	 */
	public static File checkExist(String filepath) throws Exception {
		File file = new File(filepath);

		if (file.exists()) {// 判断文件目录的存在
			// System.out.println("文件夹存在！");
			if (file.isDirectory()) {// 判断文件的存在性
				// System.out.println("文件存在！");
			} else {
				file.createNewFile();// 创建文件
				// System.out.println("文件不存在，创建文件成功！");
			}
		} else {
			// System.out.println("文件夹不存在！");
			File file2 = new File(file.getParent());
			file2.mkdirs();
			// System.out.println("创建文件夹成功！");
			if (file.isDirectory()) {
				// System.out.println("文件存在！");
			} else {
				file.createNewFile();// 创建文件
				// System.out.println("文件不存在，创建文件成功！");
			}
		}
		return file;
	}

	/**
	 * 判断文件是否是图片文件
	 * 
	 * @param  filepath
	 * @return 文件是图片文件返回true,否则返回false
	 */
	public static boolean isImage(String filePath) {
		if ((!filePath.toUpperCase().endsWith("JPG"))
				&& (!filePath.toUpperCase().endsWith("JPEG"))
				&& (!filePath.toUpperCase().endsWith("PNG"))
				&& (!filePath.toUpperCase().endsWith("BMP"))
				&& (!filePath.toUpperCase().endsWith("GIF"))) {
			return false;
		}
		return true;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param  filepath
	 * @return 文件存在返回true,否则返回false
	 */
	public static boolean isExists(String filePath) {
		File file = new File(filePath);

		if (file.exists()) {
			return true;
		}
		return false;
	}
	// public static void main(String[] args) {
	// String fileName = "g:/temp/xwz.txt";
	// DeleteFileUtil.deleteFile(fileName);
	// String fileDir = "G:/temp/temp0/temp1";
	// DeleteFileUtil.deleteDirectory(fileDir);
	// FileUtil.delete(fileDir);
	// }
}