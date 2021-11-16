package com.church.barnabas.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.church.barnabas.dto.ResultReturnList;
import com.church.barnabas.dto.ResultReturnObject;

@Service
public class BannerServiceImpl implements BannerService{

	@Override
	public ResultReturnObject uploadBannerImage(MultipartFile file,String contactNo) {
		try {

    File directory = new File(System.getProperty("user.dir")+File.separator+"BannerImages");

   if (! directory.exists()){
        directory.mkdirs();
        // If you require it to make the entire directory path including parents,
        // use directory.mkdirs(); here instead.
    }
    
   System.out.println("file.getOriginalFilename()"+file.getOriginalFilename());
   String ext = FilenameUtils.getExtension(file.getOriginalFilename());
	String fname=String.valueOf(System.currentTimeMillis())+"."+ext;
   
    String fileName = System.getProperty("user.dir")+File.separator+"BannerImages"+File.separator+fname;

    if (!file.isEmpty()){
    File f = new File(fileName);

		file.transferTo(f);
    }
		}catch(Exception e) {
			e.printStackTrace();
			return new ResultReturnObject(false,"Something Went Wrong!!","");
		}
		return new ResultReturnObject(true,"Banner Image uploaded successfully","");
	}

	@Override
	public ResultReturnList bannerImageList( String contactNo) {


		 File downloadFile = new File(System.getProperty("user.dir")+File.separator+"BannerImages");
		 
		 
		 File[] files = downloadFile.listFiles();

		List<String> imageList = new ArrayList<>();
			
				
			for(File fileinx:files) {
				
				String fileName=fileinx.getName();
				
				String path="/downloadBannerImage?fileName="+fileName;
				
				imageList.add(path);
				
			}		
		 
		return new ResultReturnList(true,"Banner Image uploaded successfully",imageList);
	}
	
	@Override
	public ResultReturnList bannerImageListMobile() {
		File downloadFile = new File(System.getProperty("user.dir")+File.separator+"BannerImages");
		 
		 
		 File[] files = downloadFile.listFiles();

			List<String> imageList = new ArrayList<>();
			
				
			for(File fileinx:files) {
				
				String fileName=fileinx.getName();
				
				String path="/downloadBannerImage?fileName="+fileName;
				
				imageList.add(path);
				
			}

		 
		 
		return new ResultReturnList(true,"Banner Image uploaded successfully",imageList);
	}
	
	
	@Override
	public ResponseEntity<InputStreamResource> download(String path) throws Exception{
		
		
		/*final String FILE_UPLOAD_LOCATION= "E:\\Upload";*/
		
	      System.out.println("path"+path);
		  String currentDir=System.getProperty("user.dir");

 	      File file = new File(currentDir+File.separator+"Classifier"+File.separator+path);

	 	
 	      
 	      
 	      System.out.println("file"+file);
 	      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

 	      return ResponseEntity.ok()
 	            .header(HttpHeaders.CONTENT_DISPOSITION,
 	                  "attachment;filename=" + file)
 	            .contentType(MediaType.IMAGE_JPEG).contentLength(file.length())
 	            .body(resource);
 	   

		 
	}

	@Override
 public ResponseEntity<InputStreamResource> downloadBannerImage( String fileName) throws Exception {

		 
		  String currentDir=System.getProperty("user.dir");
		 		 
		 		 
		 	      File file = new File(currentDir+File.separator+"BannerImages"+File.separator+fileName);
		 	      
		 	      
		 	      System.out.println("file"+file);
		 	      InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		 	      return ResponseEntity.ok()
		 	            .header(HttpHeaders.CONTENT_DISPOSITION,
		 	                  "attachment;filename=" + file)
		 	            .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(file.length())
		 	            .body(resource);
		 	   }
	
	
@Override
public ResultReturnObject deleteBannerImage(String contactNo,String fileName) throws FileNotFoundException{
	try
	{
	
	 File file = new File(System.getProperty("user.dir")+File.separator+"BannerImages"+File.separator+fileName);
//	 System.out.println("file..."+file);
	 
	  if(!file.exists()){
       	return new ResultReturnObject(false,"No file Exist!!","");
		 }   else {
			  file.delete();
		 }
          
	}
	catch(Exception e)
	{
		return new ResultReturnObject(false,"Something Went Wrong!!","");
	}
  return new ResultReturnObject(true,"Banner Image deleted successfully","");
	
}


}