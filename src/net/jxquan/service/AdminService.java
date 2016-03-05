package net.jxquan.service;

import java.util.List;

import net.jxquan.dao.UserDAO;
import net.jxquan.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service(value="adminService")
public class AdminService {
	private UserDAO userDAO;

	public UserDAO getUserDAO() {
		return userDAO;
	}
    @Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
    
    @Cacheable(value="adminCache",key="#root.methodName")
    public List<User> getAllUsers(){
    	return userDAO.findAllUsers();
    }
    
}
