package net.jxquan.service;

import net.jxquan.dao.UserDAO;
import net.jxquan.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service(value="userService")
public class UserService {
	private UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}
	
    @Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
    
    @CacheEvict(value="userCache",allEntries=true)
    public void register(String mobile,String password,String checkCode){
    	User user=new User();
    	user.setMobile(Long.valueOf(mobile));
    	user.setPassword(password);
    	user.setCheckCode(checkCode);
    	user.setWeChat("x");
    	user.setAddress("x");
    	userDAO.addUser(user);
    }
    
    //@Cacheable(value="userCache",key="#root.methodName+#mobile")
    public User findByMobile(String mobile){
    	return userDAO.findUserByMobile(Long.valueOf(mobile));
    }
    
    //@Cacheable(value="userCache",key="#root.methodName+#mobile+#password")
    public User findByMobileAndPassword(String mobile,String password){
    	return userDAO.findUserByMobileAndPassword(Long.valueOf(mobile), password);
    }
    
    @CacheEvict(value="userCache",allEntries=true)
    public void updateUser(User user){
    	userDAO.updateUser(user);
    }
    
    @CacheEvict(value="userCache",allEntries=true)
    public void updateUserMsg(User user){
    	userDAO.updateUserMsg(user);
    }
    
    @CacheEvict(value="userCache",allEntries=true)
    public void deleteUser(long mobile){
    	User user=userDAO.findUserByMobile(mobile);
    	userDAO.deleteUser(user);
    }
    
    public User findByID(long userID){
    	return userDAO.findUserByID(userID);
    }
    
    
    
    

}
