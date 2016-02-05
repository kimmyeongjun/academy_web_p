package ac.hansung.spring.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ac.hansung.spring.vo.LectureVO;
import ac.hansung.spring.vo.MemberVO;

@Repository("lectureDao")
public class LectureDaoImpl implements LectureDao {
	@Autowired
	private SqlSession session;

	@Override
	// 관리자에서 모든 강의 정보 등록
	public void insert(LectureVO lecture) {
		session.update("lectureNS.insertLecture", lecture);

	}

	// 강의 검색(년도, 학기, 교수이름, 강의명)
	@Override
	public List<LectureVO> search(HashMap<String, String> lecture) {
		List<LectureVO> lectureList = session.selectList("lectureNS.selectSearchList", lecture);
		return lectureList;
	}

	@Override
	public LectureVO searchBySequence(int sequence) {
		LectureVO vo = session.selectOne("lectureNS.searchBySequence", sequence);
		return vo;
	}

	@Override
	public List<LectureVO> getMyLectureList(String member_id) {
		List<LectureVO> lectureList = session.selectList("lectureNS.getMyInfoList", member_id);
		return lectureList;
	}
	
	@Override
	public void updateAllLectures(LectureVO lecture) {
		System.out.println("@LectureDaoImpl updateAllLectures 직전에 lecture = "+ lecture);
		 session.selectOne("lectureNS.updateAllLectures", lecture);
	}

}
