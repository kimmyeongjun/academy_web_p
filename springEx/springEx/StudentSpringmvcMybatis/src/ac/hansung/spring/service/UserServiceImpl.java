package ac.hansung.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ac.hansung.spring.dao.UserDao;
import ac.hansung.spring.vo.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userdao;

	@Override
	public void insertUser(UserVO user) {
		userdao.insert(user);
	}

	@Override
	public List<UserVO> getUserList() {
		return userdao.readAll();
	}

	@Override
	public void deleteUser(String id) {
		userdao.delete(id);
		
	}

	@Override
	public UserVO getUser(String id) {
		return userdao.read(id);
	}

	@Override
	public void updateUser(UserVO user) {
		userdao.update(user);
		
	}


	
}
