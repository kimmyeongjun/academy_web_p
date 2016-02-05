package ac.hansung.spring.dao;

import ac.hansung.spring.vo.MemberVO;

public interface MemberDao {
	public void insert(MemberVO member); // 회원 가입

	public MemberVO getMember(String member_id, String member_pw); // member_id로
																	// 조회(1. 아이디
																	// 중복 확인 2.
																	// )

	public void update(MemberVO member);

	public boolean checkById(String id);
	/*
	 * public List<MemberVO> readAll(); public void delete(String id);
	 */
}
