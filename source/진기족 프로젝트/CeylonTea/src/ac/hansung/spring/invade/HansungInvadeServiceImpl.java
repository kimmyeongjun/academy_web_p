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
			System.out.println("�Ҹ���?");
			for (int i = 0; i < returnList.size(); i++) {
				lecturedao.insert(returnList.get(i));
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean addMyLectures(String ID) {
		System.out.println("addmylectures ȣ���");
		
		ArrayList<LectureVO> returnList = httpComponent.addMyLectures(httpClient);
		
		System.out.println(returnList);
		
		if (returnList == null) {
			System.out.println("returnList is NULL");
		}
		if (returnList != null) {

			System.out.println("lectureVO�� �� = " + returnList.size());
			// �ĺ��� ���� 5���� ���� ������ ������ ���� �˾Ƴ���. select
			for (LectureVO lecture : returnList) {

				System.out.println("����" + lecture);
				Integer seq = session.selectOne("lectureNS.searchBy5Info", lecture);
				if (seq != null)
					lecture.setSequence_num(seq);
				System.out.println("������ = " + seq);

				if (seq != null) {
					// ������ ���� ������, AllLecture���̺� �л����� ���� ��������� �Է��Ѵ�. update
					session.update("lectureNS.updateAvgGrade", lecture);
					System.out.println("������Ʈ�� ���� �Ϸ�");

					// SIlecture ���̺� (���̵�, ��������) �� �����Ѵ�.
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("sequence_num", seq + "");
					map.put("user_id", ID);
					session.insert("lectureNS.addSeqAndId", map);
					System.out.println("insert ���� �Ϸ�");
				}
			}
			System.out.println("********************");

			return true;
		}
		return false;
	}

}