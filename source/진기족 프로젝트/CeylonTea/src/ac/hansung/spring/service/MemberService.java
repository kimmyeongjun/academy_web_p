package ac.hansung.spring.service;

import ac.hansung.spring.vo.MemberVO;

public interface MemberService {
	public void insertMember(MemberVO member); // ȸ�� ����

	public MemberVO getMember(String member_id, String member_pw); // member_id��
																	// ȸ�� ��ȸ

	public void updateMember(MemberVO member);

	public boolean checkById(String id);
	/*
	 * public List<MemberVO> getMemberList(); public void deleteUser(String id);
	 */
}
