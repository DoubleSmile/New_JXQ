package net.jxquan.service;

import java.util.List;

import net.jxquan.dao.ImageDAO;
import net.jxquan.entity.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
	
	private ImageDAO imageDAO;

	public ImageDAO getImageDAO() {
		return imageDAO;
	}
	
    @Autowired
	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}
    
    @CacheEvict(value="imageCache",allEntries=true)
    public void addImage(String key,String url) throws Exception{
    	Image image=new Image();
    	image.setKey(key);
    	image.setUrl(url);
    	try{
    		imageDAO.addImage(image);
    	}catch(Exception e){
    		throw e;
    	}
    }
    
    @CacheEvict(value="imageCache",allEntries=true)
    public void deleteImageByID(long id){
    	imageDAO.deleteImage(id);
    }
    
//    @Cacheable(value="imageCache",key="#root.methodName+#schoolID")
    public List<Image> findImageByKey(long schoolID){
    	return imageDAO.findImageByKey(schoolID);
    }
    
//    @Cacheable(value="imageCache",key="#root.methodName+#schoolID+#location")
    public Image findImageByUrlKey(long schoolID,String location){
    	String urlKey=String.valueOf(schoolID)+"_"+location;
    	return (Image)imageDAO.findImageByKey(urlKey);
    }
    
    public Image findByKey(String key){
    	return imageDAO.findImageByKey(key);
    }
    
//    @Cacheable(value="imageCache",key="#root.methodName+#schoolID+#location")
    public List<Image> findImagesByUrlKey(long schoolID,String location){
    	String urlKey=String.valueOf(schoolID)+"_"+location;
    	return (List<Image>)imageDAO.findImagesByKey(urlKey);
    }
    
    public void updateImage(Image image){
    	imageDAO.updateImage(image);
    }

}
