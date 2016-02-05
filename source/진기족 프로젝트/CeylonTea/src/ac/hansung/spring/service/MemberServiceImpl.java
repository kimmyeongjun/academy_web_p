package ac.hansung.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.hansung.spring.dao.MemberDao;
import ac.hansung.spring.vo.MemberVO;
//Server컴포넌트의 부분집합중 하나인데, 똑같이쓰면된다고

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberDao memberdao;

	@Override // 1. 회원 가입
	public void insertMember(MemberVO member) {
		memberdao.insert(member);
	}

	@Override // 2. id로 검색
	public MemberVO getMember(String id, String pw) {
		return memberdao.getMember(id, pw);
	}

	@Override // 3. 회원정보 수정
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
