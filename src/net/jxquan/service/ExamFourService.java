package net.jxquan.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.jxquan.dao.ExamFourDAO;
import net.jxquan.entity.ExamFour;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service(value="examFourService")
public class ExamFourService {
	private ExamFourDAO ExamFourDAO;
    
	public ExamFourDAO getExamFourDAO() {
		return ExamFourDAO;
	}
	
    @Autowired
	public void setExamFourDAO(ExamFourDAO ExamFourDAO) {
		this.ExamFourDAO = ExamFourDAO;
	}
    
    @Cacheable(value="examFourCache",key="#root.methodName")
    public List<ExamFour> getAllExam(){
    	return ExamFourDAO.findAllExamFour();
    }
    
    @Cacheable(value="examFourCache",key="#root.methodName+#type")
    public List<ExamFour> getExamByType(int type){
    	return ExamFourDAO.findExamFourByType(type);
    }
    
    @Cacheable(value="examFourCache",key="#root.methodName+#difficulty")
    public List<ExamFour> getExamByDeifficulty(int difficulty){
    	return ExamFourDAO.findExamFourByDifficulty(difficulty);
    }
    
//    @Cacheable(value="ExamFourCache",key="#root.methodName+#difficulty")
    public ExamFour getExamById(long id){
    	return ExamFourDAO.findExamFourByID(id);
    }
    
    public List<ExamFour> getGivenExam(String range){
    	return ExamFourDAO.findGivenExam(range);
    }
    
    /**
     * 模拟练习
     * imitate:模拟(用英文装一下B)
     */
    public Set<ExamFour> imitate(){
    	List<ExamFour> totalList=getAllExam();
    	Set<ExamFour> targetSet=new HashSet<ExamFour>();
    	while(targetSet.size()<100){
    		targetSet.add(totalList.get(RandomUtils.nextInt(totalList.size())));
    	}
    	return targetSet;
    }

	public List<ExamFour> getNumberedExam(int beginExam, int number) {
		return ExamFourDAO.findNumberedExam(beginExam, number);
	}
    
}
