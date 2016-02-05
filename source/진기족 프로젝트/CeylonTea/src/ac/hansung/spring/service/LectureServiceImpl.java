package ac.hansung.spring.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.hansung.spring.dao.LectureDao;
import ac.hansung.spring.vo.LectureVO;

@Service("lectureService")
public class LectureServiceImpl implements LectureService {

	@Autowired
	LectureDao lecturedao;

	@Override
	public List<LectureVO> searchLecture(HashMap<String, String> lecture) {
		return lecturedao.search(lecture);
	}

	@Override
	public LectureVO selectBySequence(int sequence) {
		return lecturedao.searchBySequence(sequence);
	}

	@Override
	public List<LectureVO> getMyLectureList(String member_id) {
		return lecturedao.getMyLectureList(member_id);
	}
	
	@Override
	public void updateAllLectures(LectureVO lecture) {
		 lecturedao.updateAllLectures(lecture);
		
	}
}
