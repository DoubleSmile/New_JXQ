package net.jxquan.service;

import java.util.Date;
import java.util.List;

import net.jxquan.dao.CarDAO;
import net.jxquan.entity.Car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service(value="carService")
public class CarService {
	private CarDAO carDAO;

	public CarDAO getCarDAO() {
		return carDAO;
	}
	
    @Autowired
	public void setCarDAO(CarDAO carDAO) {
		this.carDAO = carDAO;
	}
    
    @Cacheable(value="carCache",key="#root.methodName")
    public List<Car> getAllCars(){
    	return carDAO.findAllCars();
    }
    
    @CacheEvict(value="carCache",allEntries=true)
    public void addCar(Car Car){
    	this.getCarDAO().addCar(Car);
    }
    
    @CacheEvict(value="carCache",allEntries=true)
	public void deleteCar(long CarID){
    	this.getCarDAO().deleteCarByID(CarID);
	}
    
    @Cacheable(value="carCache",key="#root.methodName+#schoolID")
	public List<Car> getCarForSchool(long schoolID){
		return carDAO.findCarsForSchool(schoolID);
	}
    
    public Car findCarByID(long carID){
    	return carDAO.findCarByID(carID);
    }
    
    public boolean addNumber(long carID){
    	Car car=carDAO.findCarByID(carID);
    	if(car.getCurSize()<8){
    		car.setCurSize(car.getCurSize()+1);
    		carDAO.updateCar(car);
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public List<Car> findAvaiableCars(long schoolID){
    	return carDAO.findAvailableCars(schoolID);
    }
    
}
