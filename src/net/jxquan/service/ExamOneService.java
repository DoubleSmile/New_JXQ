package net.jxquan.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.jxquan.dao.ExamOneDAO;
import net.jxquan.entity.ExamOne;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service(value="examOneService")
public class ExamOneService {
	private ExamOneDAO examOneDAO;
    
	public ExamOneDAO getexamOneDAO() {
		return examOneDAO;
	}
	
    @Autowired
	public void setexamOneDAO(ExamOneDAO examOneDAO) {
		this.examOneDAO = examOneDAO;
	}
    
    @Cacheable(value="examOneCache",key="#root.methodName")
    public List<ExamOne> getAllExam(){
    	return examOneDAO.findAllExamOne();
    }
    
    @Cacheable(value="examOneCache",key="#root.methodName+#type")
    public List<ExamOne> getExamByType(int type){
    	return examOneDAO.findExamOneByType(type);
    }
    
    @Cacheable(value="examOneCache",key="#root.methodName+#difficulty")
    public List<ExamOne> getExamByDeifficulty(int difficulty){
    	return examOneDAO.findExamOneByDifficulty(difficulty);
    }
    
//    @Cacheable(value="examOneCache",key="#root.methodName+#difficulty")
    public ExamOne getExamById(long id){
    	return examOneDAO.findExamOneByID(id);
    }
    
    public List<ExamOne> getGivenExam(String range){
    	return examOneDAO.findGivenExam(range);
    }
    
    
    /**
     * 模拟练习
     * imitate:模拟(用英文装一下B)
     */
    public Set<ExamOne> imitate(){
    	List<ExamOne> totalList=getAllExam();
    	Set<ExamOne> targetSet=new HashSet<ExamOne>();
    	while(targetSet.size()<100){
    		targetSet.add(totalList.get(RandomUtils.nextInt(totalList.size())));
    	}
    	return targetSet;
    }

	public List<ExamOne> getNumberedExam(int beginExam, int number) {
		return examOneDAO.findNumberedExam(beginExam, number);
	}
    
}
