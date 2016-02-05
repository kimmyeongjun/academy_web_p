package ac.hansung.spring.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ac.hansung.spring.vo.LectureVO;
import ac.hansung.spring.vo.MemberVO;

@Repository("memberDao")
public class MemberDaoImpl implements MemberDao {
	@Autowired
	private SqlSession session; // mybatis�� �̿��� db ����

	
	@Override
	// 1. ȸ�� ����
	public void insert(MemberVO member) {
		session.update("memberNS.insertMember", member);
		System.out.println("ȣ��Ǿ����ϴ�." + member.getMember_id() + "," + member.getMember_email());
	}

	@Override
	// 2. member_id�� ȸ�� ��ȸ
	public MemberVO getMember(String member_id, String member_pw) {
		MemberVO vo = null;
		HashMap<String, String> hashmap = new HashMap<String, String>();
		System.out.println(member_id + " . " + member_pw);
		hashmap.put("member_id", member_id);
		hashmap.put("member_pw", member_pw);
		vo = session.selectOne("memberNS.selectLoginMember", hashmap);

		System.out.println("vo = " + vo);
		return vo;
	}

	@Override
	// 3. ȸ������ ������Ʈ
	public void update(MemberVO member) {
		session.update("memberNS.updateMember", member);
	}

	@Override
	public boolean checkById(String id) {
		MemberVO vo = null;
		vo = session.selectOne("memberNS.checkById", id);
		if (vo == null) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * @Override //3. ��ü ����� ��� ��ȸ public List<MemberVO> readAll() {
	 * List<MemberVO> memberList =
	 * session.selectList("memberNS.selectMemberList"); return memberList; }
	 * 
	 * 
	 * 
	 * @Override public void delete(String id) {
	 * session.delete("memberNS.delteMember",id);
	 * 
	 * }
	 */

}
