package com.loserstar.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import com.loserstar.config.annotation.Controller;
import com.loserstar.utils.idgen.LoserStarIdGenUtil;

@Controller(controllerKey = "/contractPayFile")
public class ContractPayFileController extends BaseController {
	
	public void test() {
		renderJson("ContractPayFileController!!!");
	}
	
	public void fileUpload() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "s");
		result.put("reason", "成功");
		try {
			String fileId = getPara("fileId");
			String path = "/disk1/contractPay/"+fileId+"/";
			String fileName = getFile().getOriginalFileName();
			fileName = super.uploadFile(path, null, false, fileName);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "e");
			result.put("reason", "成功");
		}
		renderJson(result);
	}
	
	/**
	 * 通过java后端进行http附件上传
	 * @param args
	 */
	public static void main(String[] args) {
		String urlStr = "http://127.0.0.1:8080/ExtWebService/contractPayFile/fileUpload.do?fileId="+LoserStarIdGenUtil.uuidLong();
		File file = new File("D:\\printDiskGroup.txt");
		String result = "";
		String BOUNDARY = "letv"; // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        try {
            URL  url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(6000);
            conn.setConnectTimeout(6000);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", "utf-8"); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);

            if (file != null) {
                /**
                 * 当文件不为空，把文件包装并且上传
                 */
                DataOutputStream dos = new DataOutputStream(
                        conn.getOutputStream());
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"oof\"; filename=\""
                        + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/ctet-stream" + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024 * 1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                        .getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                System.out.println("response code:" + res);
                // if(res==200)
                // {
                System.out.println("request success");
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();
                result = new String(result.getBytes("iso8859-1"), "utf-8");
                System.out.println("result : " + result);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
