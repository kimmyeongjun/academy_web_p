package ac.hansung.spring.invade;

import ac.hansung.spring.vo.HansungVO;

public interface HansungInvadeService {
   
   //���̵� �н������ �α����ϰ� �����ޱ�
   public boolean certifyUser(HansungVO hansung);
   
   //��� �������� �����ͺ��̽��� �ֱ�
   public boolean insertAllLectures();
 
   //���� ���� ���� �����ͺ��̽��� �ֱ�
   public boolean addMyLectures(String ID);
}