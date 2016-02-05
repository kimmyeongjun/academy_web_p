package ac.hansung.spring.service;

import java.util.List;

import ac.hansung.spring.vo.EVLectureVO;

public interface EVLectureService {
	public List<EVLectureVO> getMyEvalList(String member_id);
	
	 public void addEval(EVLectureVO vo);
}
