package ac.hansung.spring.dao;

import java.util.List;

import ac.hansung.spring.vo.EVLectureVO;

public interface EVLectureDao {
	public List<EVLectureVO> getMyEvalList(String member_id);
	
	public void addEval(EVLectureVO vo);
}