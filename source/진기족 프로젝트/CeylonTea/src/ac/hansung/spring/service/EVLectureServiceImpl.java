package ac.hansung.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.hansung.spring.dao.EVLectureDao;
import ac.hansung.spring.vo.EVLectureVO;

@Service("EVlectureService")
public class EVLectureServiceImpl implements EVLectureService {

	@Autowired
	EVLectureDao evlecturedao;

	// ���� ���� �� ��� ����Ʈ
	@Override
	public List<EVLectureVO> getMyEvalList(String member_id) {
		return evlecturedao.getMyEvalList(member_id);
	}

	@Override
	public void addEval(EVLectureVO vo) {
		evlecturedao.addEval(vo);
	}
}