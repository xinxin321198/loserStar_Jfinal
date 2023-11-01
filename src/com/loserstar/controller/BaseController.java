package com.loserstar.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.upload.MultipartRequest;
import com.jfinal.upload.UploadFile;
import com.loserstar.config.redner.MyFileRender;
import com.loserstar.constants.DsConstans;
import com.loserstar.utils.ObjectMapConvert.LoserStarObjMapConvertUtil;
import com.loserstar.utils.file.LoserStarFileUtil;

/**
 * 公共Controller
 */
public abstract class BaseController extends Controller {
	
	protected void renderError(String msg) {
		setAttr("error", msg);
		renderFreeMarker("/500.html");
	}

	/**
	 * 
	 * @param path 上传目录
	 * @param fileParamName 二进制文件数据的参数名称
	 * @param isNewFileName 是否重命名保存到硬盘上的文件名（防止文件覆盖）
	 * @param newFileName 保存到硬盘上重命名的文件名，如果不存则以uuid自动生成
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	protected String uploadFile(String path, String fileParamName,boolean isNewFileName,String newFileName) throws Exception {
		String realpath = getRequest().getSession().getServletContext().getRealPath("upload"); // 获取默认上传目录，upload目录所在的绝对路径
		UploadFile uploadFile = null;
		if (fileParamName!=null&&!fileParamName.equals("")) {
			uploadFile = getFile(fileParamName);
		}else {
			uploadFile = getFile();
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String os = System.getProperty("os.name");
		String sourceEncode = "utf-8";
		String targetEncode = System.getProperty("file.encoding");
		if (os.equalsIgnoreCase("AIX")) {
			sourceEncode = "GBK";
		}

		String sourceFilename = uploadFile.getFileName();
		String filename = sourceFilename;
		if (isNewFileName) {
			if (newFileName==null||newFileName.equals("")) {
				filename = LoserStarFileUtil.generateUUIDFileName(filename);
			}else {
				filename = newFileName;
			}
		}
		String fileUrl = path + filename;
		path += filename;

		path = new String(path.getBytes(sourceEncode), targetEncode);
		InputStream stream = new FileInputStream(uploadFile.getFile());
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(path)));
		byte[] imgBufTemp = new byte[102401];
		int length;
		while ((length = stream.read(imgBufTemp)) != -1) {
			bos.write(imgBufTemp, 0, length);
		}
		bos.flush();
		bos.close();
		stream.close();

		realpath = realpath + "/" + sourceFilename; // 拼接文件路径
		File f = new File(realpath);
		if (f.exists()) {// 删除文件
			f.delete();
		}
		return sourceFilename;
	}
	
	
	/**
	 * 
	 * @param path 上传目录
	 * @param fileParamName 二进制文件数据的参数名称
	 * @param isNewFileName 是否重命名保存到硬盘上的文件名（防止文件覆盖）
	 * @param newFileName 保存到硬盘上重命名的文件名，如果不存则以uuid自动生成
	 * @param MultipartRequest 通过MultipartRequest（cos组件）获取文件，不使用jfinal自带的
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	protected String uploadFile2(String path, String fileParamName,boolean isNewFileName,String newFileName,MultipartRequest mRequest) throws Exception {
		String realpath = mRequest.getSession().getServletContext().getRealPath("upload"); // 获取默认上传目录，upload目录所在的绝对路径
		List<UploadFile> uploadFiles = mRequest.getFiles();
		UploadFile uploadFile = null;
		if (fileParamName!=null&&!fileParamName.equals("")) {
			for (UploadFile uploadFileTemp : uploadFiles) {
				if (uploadFile.getParameterName().equals(fileParamName)) {
					uploadFile = uploadFileTemp;
				}
			}
		}else {
			uploadFile = uploadFiles.size() > 0 ? uploadFiles.get(0) : null;
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String os = System.getProperty("os.name");
		String sourceEncode = "utf-8";
		String targetEncode = System.getProperty("file.encoding");
		if (os.equalsIgnoreCase("AIX")) {
			sourceEncode = "GBK";
		}

		String sourceFilename = uploadFile.getFileName();
		String filename = sourceFilename;
		if (isNewFileName) {
			if (newFileName==null||newFileName.equals("")) {
				filename = LoserStarFileUtil.generateUUIDFileName(filename);
			}else {
				filename = newFileName;
			}
		}
		String fileUrl = path + filename;
		path += filename;

		path = new String(path.getBytes(sourceEncode), targetEncode);
		InputStream stream = new FileInputStream(uploadFile.getFile());
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(path)));
		byte[] imgBufTemp = new byte[102401];
		int length;
		while ((length = stream.read(imgBufTemp)) != -1) {
			bos.write(imgBufTemp, 0, length);
		}
		bos.flush();
		bos.close();
		stream.close();

		realpath = realpath + "/" + filename; // 拼接文件路径
		File f = new File(realpath);
		if (f.exists()) {// 删除文件
			f.delete();
		}
		return sourceFilename;
	}

	/**
	 * 上传文件(基于jqwidgets的上传文件方式，确保二进制文件参数名为fileToUpload)
	 * 
	 * @param path
	 *            上传目录
	 * @return
	 * @throws Exception
	 */
	protected String uploadFileForJqwidgets(String path) throws Exception {
		return uploadFile(path, "fileToUpload",false,null);
	}

	/**
	 * 上传文件(基于Web Uploader的上传文件方式，确保二进制文件参数名为file)
	 * http://fex.baidu.com/webuploader/ https://github.com/fex-team/webuploader
	 * 
	 * @param path
	 *            上传目录
	 * @return
	 * @throws Exception
	 */
	protected String uploadFileForWebUploader(String path) throws Exception {
		return uploadFile(path, "file",false,null);
	}
	/**
	 * 上传文件(基于Web Uploader的上传文件方式，确保二进制文件参数名为file)
	 * http://fex.baidu.com/webuploader/ https://github.com/fex-team/webuploader
	 * 
	 * @param path
	 *            上传目录
	 * @param newFileName
	 *            文件的新名称，如果不传，以uuid自动生成
	 * @return 原始文件名称
	 * @throws Exception
	 */
	protected String uploadFileForWebUploaderForGenNewFileName(String path,String newFileName) throws Exception {
		return uploadFile(path, "file",true,newFileName);
	}

	/**
	 * 下载问价，传入文件的储存路径
	 * 
	 * @param url
	 * @throws UnsupportedEncodingException
	 */
	protected void downFile(String path) throws UnsupportedEncodingException {
		String os = System.getProperty("os.name");
		String sourceEncode = "utf-8";
		String targetEncode = System.getProperty("file.encoding");
		if (os.equalsIgnoreCase("AIX")) {
			sourceEncode = "GBK";
		}

		path = new String(path.getBytes(sourceEncode), targetEncode);
		File file = new File(path);
		if (!file.exists()) {
			renderHtml("文件已被删除");
		} else {
			render(new MyFileRender(file));
		}

	}
	
	/**
	 * 下载文件（指定下载时显示的文件名，如不指定则使用硬盘上的原始名）
	 * @param path
	 * @param displayFileName
	 * @throws UnsupportedEncodingException
	 */
	protected void downFile(String path,String displayFileName) throws UnsupportedEncodingException {
		String os = System.getProperty("os.name");
		String sourceEncode = "utf-8";
		String targetEncode = System.getProperty("file.encoding");
		if (os.equalsIgnoreCase("AIX")) {
			sourceEncode = "GBK";
		}

		path = new String(path.getBytes(sourceEncode), targetEncode);
		File file = new File(path);
		if (!file.exists()) {
			renderHtml("文件已被删除");
		} else {
			if (displayFileName==null||displayFileName.equals("")) {
				render(new MyFileRender(file));
			}else {
				render(new MyFileRender(file,displayFileName));
				
			}
		}

	}
	
	protected Map<String, Object> toMap(Object object) throws Exception {
		return LoserStarObjMapConvertUtil.ConvertToMap(object);
	}
	protected  Integer toInteger(Object object) {
		return Integer.valueOf(object.toString());
	}
	protected  Double toDouble(Object object) {
		return Double.valueOf(object.toString());
	}
	protected  Boolean toBoolean(Object object) {
		return Boolean.valueOf(object.toString());
	}
	protected  String toString(Object object) {
		return object==null?"":object.toString();
	}
	
	/**
	 * 获取分页的页码参数
	 * @return
	 */
	protected int getPageNumber() {
		int pageNumber = getParaToInt("pageNumber",1);
		return pageNumber;
	}
	
	/**
	 * 获取分页的每页条数参数
	 * @return
	 */
	protected int getPageSize() {
		int pageSize = getParaToInt("pageSize",DsConstans.PageCfg.defPageSize);
		return pageSize;
	}
	
	/**
	 * 校验空值
	 * @param s
	 * @return null或空字符串返回false，有值返回true
	 */
	protected boolean checkNull(String s) {
		if (s==null||s.equals("")) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
	 * 获取排序字段
	 * @param fieldName
	 * @return
	 */
	protected String getSortField(String fieldName) {
		if (checkNull(fieldName)) {
			return fieldName.substring("sort_".length(), fieldName.length());
		}else {
			return "";
		}
	}
	

}
