package ac.hansung.spring.dao;

import ac.hansung.spring.vo.MemberVO;

public interface MemberDao {
	public void insert(MemberVO member); // ȸ�� ����

	public MemberVO getMember(String member_id, String member_pw); // member_id��
																	// ��ȸ(1. ���̵�
																	// �ߺ� Ȯ�� 2.
																	// )

	public void update(MemberVO member);

	public boolean checkById(String id);
	/*
	 * public List<MemberVO> readAll(); public void delete(String id);
	 */
}
