package ac.hansung.spring.dao;

import java.util.HashMap;
import java.util.List;

import ac.hansung.spring.vo.LectureVO;

public interface LectureDao {
	public void insert(LectureVO lecture); // 모든 강의 정보 입력

	public List<LectureVO> search(HashMap<String, String> lecture);

	public LectureVO searchBySequence(int sequence);

	public List<LectureVO> getMyLectureList(String member_id);
	
	public void updateAllLectures(LectureVO lecture);
}
