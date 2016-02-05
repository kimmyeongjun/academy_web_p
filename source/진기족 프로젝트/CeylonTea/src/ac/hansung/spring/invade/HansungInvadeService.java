package ac.hansung.spring.invade;

import ac.hansung.spring.vo.HansungVO;

public interface HansungInvadeService {
   
   //아이디 패스워드로 로그인하고 인증받기
   public boolean certifyUser(HansungVO hansung);
   
   //모든 강의정보 데이터베이스에 넣기
   public boolean insertAllLectures();
 
   //내가 들은 강의 데이터베이스에 넣기
   public boolean addMyLectures(String ID);
}