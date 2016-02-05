package ac.hansung.spring.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ac.hansung.spring.vo.EVLectureVO;

@Repository("EVlectreDao")
public class EVLectureDaoImpl implements EVLectureDao {
	
	@Autowired
	private SqlSession session;
	
	@Override
	public List<EVLectureVO> getMyEvalList(String member_id) {
		List<EVLectureVO> evalList = session.selectList(
				"EVlectureNS.getMyEvalList", member_id);
		return evalList;
	}
	
	@Override
	public void addEval(EVLectureVO vo) {
		session.update("EVlectureNS.addEval", vo);
	}
	

}