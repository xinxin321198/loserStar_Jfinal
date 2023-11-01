package com.loserstar.config.redner;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;

import com.jfinal.core.JFinal;
import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import com.jfinal.render.RenderFactory;
import com.jfinal.render.RenderManager;

@SuppressWarnings("unused")
public class MyFileRender extends Render {
	private File file;
	private String fileName = "";
	private ServletContext servletContext;

	/**
	 * 
	 */
	private static final long serialVersionUID = 4107102174376957468L;
	
	public MyFileRender(File file) {
		this.file = file;
		this.servletContext = JFinal.me().getServletContext();
	}

	public MyFileRender(File file, String fileName) {
		this.file = file;
		this.fileName = fileName;
		this.servletContext = JFinal.me().getServletContext();
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		if (file == null || !file.isFile() || file.length() > Integer.MAX_VALUE) {
			// response.sendError(HttpServletResponse.SC_NOT_FOUND);
			// return;

			// throw new RenderException("File not found!");
			RenderManager.me().getRenderFactory().getErrorRender(404)
					.setContext(request, response).render();
			return;
		}
		String _fileName = file.getName();
		if(!fileName.equals("")){
			_fileName = fileName;
		}
		try {
			String encode = System.getProperty("file.encoding");
			String os = System.getProperty("os.name");

			if (encode.equalsIgnoreCase("ISO8859-1")) {
				response.addHeader("Content-disposition",
						"attachment; filename=" + _fileName);
			} else if(!encode.equalsIgnoreCase("ISO8859-1")&&(os.contains("Windows"))) {
				response.addHeader("Content-disposition",
						"attachment; filename="
								+ new String(_fileName.getBytes("GBK"),
										"ISO8859-1"));
			}else {
				response.addHeader("Content-disposition",
						"attachment; filename="
								+ new String(_fileName.getBytes(encode),
										"ISO8859-1"));
			}
		} catch (UnsupportedEncodingException e) {
			response.addHeader("Content-disposition", "attachment; filename="
					+ _fileName);
		}

		String contentType = servletContext.getMimeType(_fileName);
		if (contentType == null) {
			contentType = "application/octet-stream"; ; // "application/octet-stream"; DEFAULT_FILE_CONTENT_TYPE
		}

		response.setContentType(contentType);
		response.setContentLength((int) file.length());
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
			outputStream = response.getOutputStream();
			byte[] buffer = new byte[1024];
			for (int n = -1; (n = inputStream.read(buffer)) != -1;) {
				outputStream.write(buffer, 0, n);
			}
			outputStream.flush();
		} catch (Exception e) {
			throw new RenderException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
