package ac.hansung.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.hansung.spring.dao.MemberDao;
import ac.hansung.spring.vo.MemberVO;
//Server������Ʈ�� �κ������� �ϳ��ε�, �Ȱ��̾���ȴٰ�

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao memberdao;

	@Override // 1. ȸ�� ����
	public void insertMember(MemberVO member) {
		memberdao.insert(member);
	}

	@Override // 2. id�� �˻�
	public MemberVO getMember(String id, String pw) {
		return memberdao.getMember(id, pw);
	}

	@Override // 3. ȸ������ ����
	public void updateMember(MemberVO member) {
		memberdao.update(member);

	}

	@Override
	public boolean checkById(String id) {
		// TODO Auto-generated method stub
		return memberdao.checkById(id);
	}
	/*
	 * @Override public List<MemberVO> getMemberList() { return
	 * memberdao.readAll(); }
	 * 
	 * 
	 * @Override public void deleteUser(String id) { memberdao.delete(id);
	 * 
	 * }
	 */

}
