package ac.hansung.spring.service;

import java.util.HashMap;
import java.util.List;

import ac.hansung.spring.vo.LectureVO;

public interface LectureService {
	public List<LectureVO> searchLecture(HashMap<String, String> lecture);//

	public LectureVO selectBySequence(int sequence);

	public List<LectureVO> getMyLectureList(String member_id);
	
	public void updateAllLectures(LectureVO lecture);
}
