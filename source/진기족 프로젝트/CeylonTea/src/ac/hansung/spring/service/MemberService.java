package ac.hansung.spring.service;

import ac.hansung.spring.vo.MemberVO;

public interface MemberService {
	public void insertMember(MemberVO member); // 회원 가입

	public MemberVO getMember(String member_id, String member_pw); // member_id로
																	// 회원 조회

	public void updateMember(MemberVO member);

	public boolean checkById(String id);
	/*
	 * public List<MemberVO> getMemberList(); public void deleteUser(String id);
	 */
}
