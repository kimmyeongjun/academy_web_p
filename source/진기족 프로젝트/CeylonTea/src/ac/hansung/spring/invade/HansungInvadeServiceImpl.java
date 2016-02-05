package ac.hansung.spring.invade;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.hansung.spring.dao.LectureDao;
import ac.hansung.spring.vo.HansungVO;
import ac.hansung.spring.vo.LectureVO;

@Service("hansungInvadeService")
public class HansungInvadeServiceImpl implements HansungInvadeService {
	@Autowired
	private SqlSession session;

	Header[] header;

	@Autowired
	LectureDao lecturedao;

	@Autowired
	HttpComponent httpComponent;
	CloseableHttpClient httpClient = HttpClients.createDefault();

	@Override
	public boolean certifyUser(HansungVO hansung) {
		if (httpComponent.identify(hansung, httpClient) == true) {
			return true;
		}
		return false;
	}

	@Override
	public boolean insertAllLectures() {
		ArrayList<LectureVO> returnList = httpComponent.insertAllLectures(httpClient);
		if (returnList != null) {
			System.out.println("불리니?");
			for (int i = 0; i < returnList.size(); i++) {
				lecturedao.insert(returnList.get(i));
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addMyLectures(String ID) {
		System.out.println("addmylectures 호출됨");
		
		ArrayList<LectureVO> returnList = httpComponent.addMyLectures(httpClient);
		
		System.out.println(returnList);
		
		if (returnList == null) {
			System.out.println("returnList is NULL");
		}
		if (returnList != null) {

			System.out.println("lectureVO의 수 = " + returnList.size());
			// 식별을 위한 5개의 값을 가지고 시퀀스 값을 알아낸다. select
			for (LectureVO lecture : returnList) {

				System.out.println("렉쳐" + lecture);
				Integer seq = session.selectOne("lectureNS.searchBy5Info", lecture);
				if (seq != null)
					lecture.setSequence_num(seq);
				System.out.println("시퀀스 = " + seq);

				if (seq != null) {
					// 시퀀스 값을 가지고, AllLecture테이블에 학생들이 평가한 평균점수를 입력한다. update
					session.update("lectureNS.updateAvgGrade", lecture);
					System.out.println("업데이트문 실행 완료");

					// SIlecture 테이블에 (아이디값, 시퀀스값) 을 저장한다.
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("sequence_num", seq + "");
					map.put("user_id", ID);
					session.insert("lectureNS.addSeqAndId", map);
					System.out.println("insert 실행 완료");
				}
			}
			System.out.println("********************");

			return true;
		}
		return false;
	}

}