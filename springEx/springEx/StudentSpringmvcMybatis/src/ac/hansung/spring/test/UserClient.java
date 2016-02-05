package ac.hansung.spring.test;

import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ac.hansung.spring.service.UserService;
import ac.hansung.spring.vo.UserVO;

public class UserClient {
	public static void serviceTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("config/Beans.xml");

		UserService service = context.getBean(UserService.class);
		System.out.println("------레코드 삭제--------");
		service.deleteUser("vega2k");
		service.deleteUser("dooly");
		service.deleteUser("jungwu");
		System.out.println("------레코드 생성--------");
		service.insertUser(new UserVO("vega2k", "안나", "여", "서울"));
		service.insertUser(new UserVO("dooly", "둘리", "남", "지구"));
		service.insertUser(new UserVO("jungwu", "정우", "남", "부산"));
		System.out.println("------레코드 목록--------");
		for (UserVO user : service.getUserList()) {
			System.out.println(user);
		}
		System.out.println("----userid= dooly 레코드 갱신  -----");
		service.updateUser(new UserVO("dooly", "둘리", "여", "우주"));

		System.out.println("----userid = dooly 레코드 조회 -----");
		System.out.println(service.getUser("dooly"));
	}
	
	public static void xmlTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("config/Beans.xml");
		
		System.out.println(context.getBean("sqlSessionFactory"));
		
		SqlSession session = context.getBean(SqlSession.class);
		UserVO user = new UserVO("dooly", "둘리", "남", "지구");
		
		session.update("userNS.insertUser", user);
		System.out.println("등록된 Record UserId=" + user.getUserId() + " Name=" + user.getName());
	}

	public static void main(String[] args) {
		xmlTest();
	}
}
