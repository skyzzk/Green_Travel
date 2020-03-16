package api.app.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import api.app.pojo.Advertisement;
import api.app.service.AdvertisementService;
import api.app.utils.RandomID;

/**
 * 图片控制层
 * 上传图片
 * @author sky
 *
 */
@CrossOrigin(origins = {"http://localhost:3000"})
@Controller
@RequestMapping("api")
public class PictureController {
	
	
	@Autowired
	private AdvertisementService advertisementService;
	
	/**
	 * 上传图片
	 */
	
	
	


	@RequestMapping(value="pictureUtil",method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> pictureUtil(MultipartFile image ,String name) {
		//设置图片上传路径	
		int code = 1;
		String imgurl = null;	
		Map<String,Object> map = new HashMap<String,Object>();
		//获取年月日
		//String url = file.getCanonicalPath().toString();
		try {
			String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();					
			//获取工程路径，
			//File file = new File("");
			//String filePath = file.getCanonicalPath() + File.separator+"uploadCommodity\\" +data;
				
			String filePath = "E:\\software\\node\\Item\\green-travel-backstage-server\\upload\\" + name +"\\" +data;
		
			
			//根据时间创建文件夹
			File f = new File(filePath);
			if (!f.exists()) {
			    f.mkdirs();
			} 
			
			//获取原始图片的
			String originalFilename = image.getOriginalFilename();
			//获取原始图片的后缀名
			String fileTyle = originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());     
			//新的文件名字并去掉四个“-"并加上后缀名
			String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileTyle;       
			
			
			//封装上传文件位置的全路径
			File targetFile = new File(filePath,newFileName); 
			
			//把本地文件上传到封装上传文件位置的全路径
			image.transferTo(targetFile);      
			
			//图片新路径
			String url = filePath.replace("E:\\software\\node\\Item\\green-travel-backstage-server","");
			imgurl = url.replaceAll("\\\\", "/") + "/" + newFileName;
			code = 0;
			
					
			if(name.equals("advertisement")) {
				Advertisement advertisement = new Advertisement();
				//获取当前时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(sdf.format(new Date()));
				RandomID Id = new RandomID();
				int id = Id.randomID();
				advertisement.setAd_id(id);
				advertisement.setAd_path(imgurl);
				advertisement.setAd_data(date);
				advertisementService.insertAdvertisement(advertisement);
			}			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", code);
			return map;
		} 
       
        map.put("code", code);
        map.put("path", imgurl);
		return map;
	}

}
