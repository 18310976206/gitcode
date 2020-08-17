package com.platform.common.action;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@Controller
@RequestMapping("/upJson.do")
public class UpJson {
//	Logger logger = Logger.getLogger(this.getClass().getName());
	private BASE64Encoder encoder = new sun.misc.BASE64Encoder();    
    private BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
	 //kingeditor 上传图片
	/**
	 * error 1 错误， error 0 上传成功
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	 @RequestMapping(params="method=uploadFile",method=RequestMethod.POST)
	 public void uploadFile(HttpServletRequest request,HttpServletResponse response)throws Exception{
		 response.setContentType("text/html; charset=UTF-8"); 
		 ServletContext application = request.getSession().getServletContext();  
	      // 最大文件大小  
		 long maxSize = 3*1024*1024;
		 JSONObject msg = new JSONObject(); 
		 if (!ServletFileUpload.isMultipartContent(request)) {
			  msg.put("error", 1);  
		      msg.put("message", "请选择文件!");  
		      response.getWriter().write(msg.toString());
		 }else{
			 FileItemFactory factory = new DiskFileItemFactory();  
			 ServletFileUpload upload = new ServletFileUpload(factory);  
			 upload.setHeaderEncoding("UTF-8");  
			 List items = upload.parseRequest((RequestContext) request);  
			 Iterator itr = items.iterator();  
			          while (itr.hasNext()) {  
			              FileItem item = (FileItem) itr.next();  
			              String fileName = item.getName();  
			              if (!item.isFormField()) {  
			                  // 检查文件大小  
			             if (item.getSize() > maxSize) { 
			   			     msg.put("error", 1);  
			   		         msg.put("message", "请上传3M以下的文件");  
			   		         response.getWriter().write(msg.toString()); 
			             }else{
					           //处理压缩图片
					     		int bg_width = 800; int bg_height = 600; int set_width = 750;//处理压缩成该宽度
					     		BufferedImage bg_src = javax.imageio.ImageIO.read(item.getInputStream());
					     		BufferedImage tag = null;
					     		
					     		if(bg_src != null){//上传的文件是图片的时候
							     		int real_width = bg_src.getWidth();//图片的真实宽度
							     		int real_height = bg_src.getHeight();//图片的真实高度
							     		if(real_width > set_width){//当图片高度超过该限制后才压缩图片
										     		double compute_height = (double)set_width/real_width*real_height;//等比例计算出来的图片高度
										     		bg_width = set_width;
										     		bg_height = (int)compute_height; 
										     		tag = new BufferedImage(bg_width, bg_height, BufferedImage.TYPE_INT_RGB);
										     		Graphics2D g2d = tag.createGraphics();
										            g2d.drawImage(bg_src, 0, 0, bg_width, bg_height, null);
										            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,1.0f)); //透明度设置开始  
										            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER)); //透明度设置 结束
								       }else{
								     				System.out.println("图片宽度小于限制，不用压缩..");
								     				bg_src = null;
								       }
					     		}
					     		 // 检查扩展名  
				                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();  
				                String text="";
				                if (fileExt.equals("gif")||fileExt.equals("jpg")||fileExt.equals("jpeg")||fileExt.equals("png")||fileExt.equals("bmp")) { 
				                	 try {
				 	                    if(bg_src != null){//上传的文件是图片的时候
				 	                    	  text = getImageBinary(tag);
				 	          			}else{
				 	          				  text = getImageBinary(item.getInputStream());
				 	          			}
				 	                  msg.put("error", 0);  
				 	                  msg.put("url", "data:image/jpg;base64,"+text);    
				 		   		      response.getWriter().write(msg.toString()); 
				 	                }catch (Exception e){  
			 			   			     msg.put("error", 1);  
			 			   		         msg.put("message", "文件上传失败!");  
			 			   		         response.getWriter().write(msg.toString());    	                		    
				 	                }
				                }else{
				                   msg.put("error", 1);  
						   		   msg.put("message", "上传文件扩展名是不允许的扩展名。只允许gif,jpg,jpeg,png,bmp格式。");  
						   		   response.getWriter().write(msg.toString()); 		   
				    } 
			   } 
		   }  
	}
 }  

            
}
	/**
	 * 得到base64 二进制文件
	 * @param input
	 * @return
	 */
	 public String getImageBinary(InputStream input) {          
	        BufferedImage bi;    
	        try {    
	            bi =  ImageIO.read(input);
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();    
	            ImageIO.write(bi, "jpg", baos);    
	            byte[] bytes = baos.toByteArray();  
	            return encoder.encodeBuffer(bytes).trim();    
	        } catch (IOException e) {    
	            e.printStackTrace();    
	        }    
	        return null; 
	}
	 /**
	  * 
	  * @param input
	  * @param bi
	  * @return
	  */
	 public String getImageBinary(BufferedImage bi){   
	        try {    
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();    
	            ImageIO.write(bi, "jpg", baos);    
	            byte[] bytes = baos.toByteArray();  
	            return encoder.encodeBuffer(bytes).trim();    
	        } catch (IOException e) {    
	            e.printStackTrace();    
	        }    
	        return null; 
	}
	 
	 
	 @RequestMapping(params="method=fileManager",method=RequestMethod.POST)
	 public void fileManager(HttpServletRequest request,HttpServletResponse response)throws Exception{
		         ServletContext application = request.getSession().getServletContext();  
		         ServletOutputStream out = response.getOutputStream();  
		         // 根目录路径，可以指定绝对路径，比如 /var/www/attached/  
		         String rootPath = application.getRealPath("/") + "attached/";  
		         // 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/  
		         String rootUrl = request.getContextPath() + "/attached/";  
		         // 图片扩展名  
		         String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };  
		   
		         String dirName = request.getParameter("dir");  
		         if (dirName != null) {  
		             if (!Arrays.<String> asList(new String[] { "image"}).contains(dirName)) {  
		                out.println("Invalid Directory name.");  
		                return;  
		             }  
		             rootPath += dirName + "/";  
		             rootUrl += dirName + "/";  
		             File saveDirFile = new File(rootPath);  
		             if (!saveDirFile.exists()) {  
		                 saveDirFile.mkdirs();  
		             }  
		         }  
		        // 根据path参数，设置各路径和URL  
		         String path = request.getParameter("path") != null ? request.getParameter("path") : "";  
		         String currentPath = rootPath + path;  
		         String currentUrl = rootUrl + path;  
		         String currentDirPath = path;  
		         String moveupDirPath = "";  
		         if (!"".equals(path)) {  
		           String str = currentDirPath.substring(0,  
		                   currentDirPath.length() - 1);  
		             moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0,  
		                     str.lastIndexOf("/") + 1) : "";  
		       }  
		   
		         // 排序形式，name or size or type  
		        String order = request.getParameter("order") != null ? request.getParameter("order").toLowerCase() : "name";  
		   
		         // 不允许使用..移动到上一级目录  
		         if (path.indexOf("..") >= 0) {  
		            out.println("Access is not allowed.");  
		            return;  
		         }  
		         // 最后一个字符不是/  
		         if (!"".equals(path) && !path.endsWith("/")) {  
		           out.println("Parameter is not valid.");  
		             return;  
		         }  
		         // 目录不存在或不是目录  
		         File currentPathFile = new File(currentPath);  
		         if (!currentPathFile.isDirectory()) {  
		           out.println("Directory does not exist.");  
		            return;  
		         }  
		         // 遍历目录取的文件信息  
		         List<Hashtable> fileList = new ArrayList<Hashtable>();  
		         if (currentPathFile.listFiles() != null) {  
		             for (File file : currentPathFile.listFiles()) {  
		                 Hashtable<String, Object> hash = new Hashtable<String, Object>();  
		               String fileName = file.getName();  
		                if (file.isDirectory()) {  
		                     hash.put("is_dir", true);  
		                    hash.put("has_file", (file.listFiles() != null));  
		                    hash.put("filesize", 0L);  
		                     hash.put("is_photo", false);  
		                     hash.put("filetype", "");  
		                 } else if (file.isFile()) {  
		                     String fileExt = fileName.substring(  
		                             fileName.lastIndexOf(".") + 1).toLowerCase();  
		                     hash.put("is_dir", false);  
		                     hash.put("has_file", false);  
		                    hash.put("filesize", file.length());  
		                     hash.put("is_photo", Arrays.<String> asList(fileTypes)  
		                             .contains(fileExt));  
		                     hash.put("filetype", fileExt);  
		                 }  
		                 hash.put("filename", fileName);  
		                hash.put("datetime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));  
		                 fileList.add(hash);  
		             }  
		         }  
		   
		         if ("size".equals(order)) {  
		             Collections.sort(fileList, new SizeComparator());  
		         } else if ("type".equals(order)) {  
		             Collections.sort(fileList, new TypeComparator());  
		         } else {  
		            Collections.sort(fileList, new NameComparator());  
		         }  
		         
		         JSONObject result = new JSONObject();
		         /*result.toString();
		         Map<String, Object> msg = new HashMap<String, Object>();  */
		         result.put("moveup_dir_path", moveupDirPath);  
		         result.put("current_dir_path", currentDirPath);  
		         result.put("current_url", currentUrl);  
		         result.put("total_count", fileList.size());  
		         result.put("file_list", fileList);  
		         response.setContentType("application/json; charset=UTF-8");  
		       
		         out.println(result.toString());  
		     }  

	
		
		     class NameComparator implements Comparator {  
		        public int compare(Object a, Object b) {  
		            Hashtable hashA = (Hashtable) a;  
		             Hashtable hashB = (Hashtable) b;  
		             if (((Boolean) hashA.get("is_dir"))  
		                     && !((Boolean) hashB.get("is_dir"))) {  
		                 return -1;  
		             } else if (!((Boolean) hashA.get("is_dir"))  
		                    && ((Boolean) hashB.get("is_dir"))) {  
		               return 1;  
		           } else {  
		                 return ((String) hashA.get("filename"))  
		                         .compareTo((String) hashB.get("filename"));  
		           }  
		         }  
		     }  
		   
		     class SizeComparator implements Comparator {  
		         public int compare(Object a, Object b) {  
		             Hashtable hashA = (Hashtable) a;  
		             Hashtable hashB = (Hashtable) b;  
		             if (((Boolean) hashA.get("is_dir"))  
		                     && !((Boolean) hashB.get("is_dir"))) {  
		                return -1;  
		            } else if (!((Boolean) hashA.get("is_dir"))  
		                     && ((Boolean) hashB.get("is_dir"))) {  
		               return 1;  
		            } else {  
		                if (((Long) hashA.get("filesize")) > ((Long) hashB  
		                        .get("filesize"))) {  
		                   return 1;  
		                } else if (((Long) hashA.get("filesize")) < ((Long) hashB  
		                         .get("filesize"))) {  
		                     return -1;  
		                 } else {  
		                     return 0;  
		                 }  
		             }  
		         }  
		     }  
		  
		    class TypeComparator implements Comparator {  
		        public int compare(Object a, Object b) {  
		            Hashtable hashA = (Hashtable) a;  
		            Hashtable hashB = (Hashtable) b;  
		            if (((Boolean) hashA.get("is_dir"))  
		                    && !((Boolean) hashB.get("is_dir"))) {  
		                return -1;  
		           } else if (!((Boolean) hashA.get("is_dir"))  
		                    && ((Boolean) hashB.get("is_dir"))) {  
		                 return 1;  
		             } else {  
		               return ((String) hashA.get("filetype"))  
		                         .compareTo((String) hashB.get("filetype"));  
		             }  
		         }  
		    } 
}
