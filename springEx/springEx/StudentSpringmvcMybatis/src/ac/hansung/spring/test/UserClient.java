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
		System.out.println("------���ڵ� ����--------");
		service.deleteUser("vega2k");
		service.deleteUser("dooly");
		service.deleteUser("jungwu");
		System.out.println("------���ڵ� ����--------");
		service.insertUser(new UserVO("vega2k", "�ȳ�", "��", "����"));
		service.insertUser(new UserVO("dooly", "�Ѹ�", "��", "����"));
		service.insertUser(new UserVO("jungwu", "����", "��", "�λ�"));
		System.out.println("------���ڵ� ���--------");
		for (UserVO user : service.getUserList()) {
			System.out.println(user);
		}
		System.out.println("----userid= dooly ���ڵ� ����  -----");
		service.updateUser(new UserVO("dooly", "�Ѹ�", "��", "����"));

		System.out.println("----userid = dooly ���ڵ� ��ȸ -----");
		System.out.println(service.getUser("dooly"));
	}
	
	public static void xmlTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("config/Beans.xml");
		
		System.out.println(context.getBean("sqlSessionFactory"));
		
		SqlSession session = context.getBean(SqlSession.class);
		UserVO user = new UserVO("dooly", "�Ѹ�", "��", "����");
		
		session.update("userNS.insertUser", user);
		System.out.println("��ϵ� Record UserId=" + user.getUserId() + " Name=" + user.getName());
	}

	public static void main(String[] args) {
		xmlTest();
	}
}
