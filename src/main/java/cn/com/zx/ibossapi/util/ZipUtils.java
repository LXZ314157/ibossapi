package cn.com.zx.ibossapi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.*;

//import java.util.ArrayList;

//import net.lingala.zip4j.core.ZipFile;
//import net.lingala.zip4j.model.ZipParameters;
//import net.lingala.zip4j.util.Zip4jConstants;

/**
 * ZIP压缩工具
 * 
 */
public class ZipUtils {

	public static final String EXT = ".zip";
	private static final String BASE_DIR = "";

	// 符号"/"用来作为目录标识判断符
	private static final String PATH = "/";
	private static final int BUFFER = 1024;

	public static Logger logger = LoggerFactory.getLogger(ZipUtils.class);

	/**
	 * 压缩
	 * 
	 * @param srcFile
	 * @throws Exception
	 */
	public static void compress(File srcFile) throws Exception {
		String name = srcFile.getName();
		String basePath = srcFile.getParent();
		String destPath = basePath + name + EXT;
		compress(srcFile, destPath);
	}

	/**
	 * 压缩
	 * 
	 * @param srcFile
	 *            源路径
	 * @param destPath
	 *            目标路径
	 * @throws Exception
	 */
	public static void compress(File srcFile, File destFile) throws Exception {

		// 对输出文件做CRC32校验
		CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
				destFile), new CRC32());

		ZipOutputStream zos = new ZipOutputStream(cos);

		compress(srcFile, zos, BASE_DIR);

		zos.flush();
		zos.close();
	}

	/**
	 * 压缩文件
	 * 
	 * @param srcFile
	 * @param destPath
	 * @throws Exception
	 */
	public static void compress(File srcFile, String destPath) throws Exception {
		compress(srcFile, new File(destPath));
	}

	/**
	 * 压缩
	 * 
	 * @param srcFile
	 *            源路径
	 * @param zos
	 *            ZipOutputStream
	 * @param basePath
	 *            压缩包内相对路径
	 * @throws Exception
	 */
	private static void compress(File srcFile, ZipOutputStream zos,
			String basePath) throws Exception {
		if (srcFile.isDirectory()) {
			compressDir(srcFile, zos, basePath);
		} else {
			compressFile(srcFile, zos, basePath);
		}
	}

	/**
	 * 压缩
	 * 
	 * @param srcPath
	 * @throws Exception
	 */
	public static void compress(String srcPath) throws Exception {
		File srcFile = new File(srcPath);

		compress(srcFile);
	}

	/**
	 * 文件压缩
	 * 
	 * @param srcPath
	 *            源文件路径
	 * @param destPath
	 *            目标文件路径
	 * 
	 */
	public static void compress(String srcPath, String destPath)
			throws Exception {
		File srcFile = new File(srcPath);

		compress(srcFile, destPath);
	}

	/**
	 * 压缩目录
	 * 
	 * @param dir
	 * @param zos
	 * @param basePath
	 * @throws Exception
	 */
	private static void compressDir(File dir, ZipOutputStream zos,
			String basePath) throws Exception {

		File[] files = dir.listFiles();

		// 构建空目录
		// if (files.length < 1) {
		ZipEntry entry = new ZipEntry(basePath + dir.getName() + PATH);

		zos.putNextEntry(entry);
		zos.closeEntry();
		// }

		for (File file : files) {
			// 递归压缩
			// compress(file, zos, file.getName() + PATH);
			compress(file, zos, basePath + dir.getName() + PATH);
		}
	}

	/**
	 * 文件压缩
	 * 
	 * @param file
	 *            待压缩文件
	 * @param zos
	 *            ZipOutputStream
	 * @param dir
	 *            压缩文件中的当前路径
	 * @throws Exception
	 */
	private static void compressFile(File file, ZipOutputStream zos, String dir)
			throws Exception {

		/**
		 * 压缩包内文件名定义
		 * 
		 * <pre>
		 * 如果有多级目录，那么这里就需要给出包含目录的文件名 
		 * 如果用WinRAR打开压缩包，中文名将显示为乱码
		 * </pre>
		 */
		// ZipEntry entry = new ZipEntry(file.getName());
		ZipEntry entry = new ZipEntry(dir + file.getName());

		zos.putNextEntry(entry);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = bis.read(data, 0, BUFFER)) != -1) {
			zos.write(data, 0, count);
		}
		bis.close();

		zos.closeEntry();
	}

	/**
	 * 
	 * @param srcfile
	 * @param zipfile
	 * @throws IOException
	 * @throws Exception
	 */
	public static void ZipFiles(File[] srcfile, File zipfile)
			throws IOException {
		byte[] buf = new byte[BUFFER];

		ZipOutputStream out = null;
		FileInputStream in = null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipfile));
			for (int i = 0; i < srcfile.length; i++) {
				in = new FileInputStream(srcfile[i]);
				out.putNextEntry(new ZipEntry(srcfile[i].getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.closeEntry();
			}
		} catch (FileNotFoundException e) {
			throw e;
		} finally {
			out.close();
		}
	}

	public static void decompress(String zipFilePath, String descDir)
			throws Exception {

		try {
			File zipfile = new File(zipFilePath);
			File outFile = new File(descDir);
			if (!outFile.exists()) {
				outFile.mkdirs();
			}
			ZipFile zf = new ZipFile(zipfile, Charset.forName("GBK"));
			for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				String zipEntryName = entry.getName();
				InputStream in = zf.getInputStream(entry);
				String outPath = (descDir + zipEntryName)
						.replaceAll("\\*", "/");
				;
				// 判断路径是否存在,不存在则创建文件路径
				File file = new File(outPath.substring(0,
						outPath.lastIndexOf('/')));
				if (!file.exists()) {
					file.mkdirs();
				}
				// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}
				// 输出文件路径信息
				System.out.println(outPath);

				OutputStream out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
			}
			System.out.println("******************解压完毕********************");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		String path = "D:/mnt/nsat/files/asset/AST146115057EC84C63AEDE89C91C647421/1/20170907114431.zip";
		try {
			decompress(path,
					"D:/mnt/nsat/files/asset/AST146115057EC84C63AEDE89C91C647421/1/20170907114431/");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 加密压缩
	 */
	// public static boolean zipEncrypt(String zipFileName, String htmlFileName,
	// String pwd){
	// try{
	// ZipFile zipFile = new ZipFile(zipFileName);
	//
	// ArrayList<File> filesToAdd = new ArrayList<File>();
	// filesToAdd.add(new File(htmlFileName));
	//
	//
	// ZipParameters parameters = new ZipParameters();
	// parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
	//
	// parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
	// parameters.setEncryptFiles(true);
	//
	// parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
	//
	//
	// parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
	// parameters.setPassword(pwd);
	//
	// zipFile.addFiles(filesToAdd, parameters);
	// return true;
	//
	// }catch(Exception e){
	// logger.error("打包加密压缩文件异常");
	// return false;
	// }
	//
	// }

}
