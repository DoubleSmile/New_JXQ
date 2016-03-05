package net.jxquan.dao;

import java.util.List;

import net.jxquan.entity.Image;
import net.jxquan.util.CloneUtils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value="imageDAO")
public class ImageDAO {
	
	private SessionFactory sessionFactory;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
    @Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
    /**
	 * 得到相关的Session
	 */
    public Session getCurrentSession(){
    	return this.getSessionFactory().getCurrentSession();
    }
    
    /**
	 * 添加Image
     * @throws Exception 
	 */
    @Transactional
    public void addImage(Image image) throws Exception{
    	try{
    	this.getCurrentSession().save(image);
    	}catch(Exception e){
    		throw e;
    	}
    }
    
    /**
	 *删除Image
	 */
    @Transactional
    public void deleteImage(Image image){
    	this.getCurrentSession().delete(image);
    }
    
    /**
	 *根据ID删除Image
	 */
    @Transactional
    public void deleteImage(long id){
    	Image image=(Image)this.getCurrentSession().load(Image.class,id);
        this.getCurrentSession().delete(image);
    }
    
    /**
   	 *更新图片 
   	 */
    @Transactional
    public void updateImage(Image image){
//    	Image tempImage=(Image)this.getCurrentSession().load(Image.class, image.getId());
//    	CloneUtils.clone(tempImage, image);
    	this.getCurrentSession().saveOrUpdate(image);
    }
    
    /**
   	 *根据ID查找图片 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public Image findImageByID(long id){
    	return (Image)this.getCurrentSession().load(Image.class, id);
    }
    
    /**
   	 *根据Key查找图片 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public Image findImageByKey(String key){
    	return (Image)this.getCurrentSession()
    			.createQuery("FROM Image i WHERE i.key = :key")
    			.setString("key", key).uniqueResult();
    }
    
    /**
   	 *根据Key查找图片 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Image> findImagesByKey(String key){
    	String key_=key+"%";
    	return (List<Image>)this.getCurrentSession()
    			.createQuery("FROM Image i WHERE i.key like ?")
    			.setString(0,key_).list();
    }
    
    /**
   	 *根据schoolID进行模糊查找
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Image> findImageByKey(Long schoolID){
    	String temp=Long.valueOf(schoolID).toString()+"%";
    	return (List<Image>)this.getCurrentSession()
    			.createQuery("FROM Image i WHERE i.key like ?")
    			.setString("key",temp).list();
    }
    
    /**
   	 *得到所有图片 
   	 */
    @Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
    public List<Image> findAllImage(){
        return (List<Image>)this.getCurrentSession()
        		.createQuery("FROM Image").list();
    }

}
