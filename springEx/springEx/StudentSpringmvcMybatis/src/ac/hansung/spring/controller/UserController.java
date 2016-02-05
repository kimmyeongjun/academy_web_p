package ac.hansung.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ac.hansung.spring.service.UserService;
import ac.hansung.spring.vo.UserVO;

@Controller
//@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/register.do")
	public ModelAndView registerUser(@ModelAttribute UserVO user) {
		List<String> genderList = new ArrayList<String>();
		genderList.add("남");
		genderList.add("여");

		List<String> cityList = new ArrayList<String>();
		cityList.add("서울");
		cityList.add("부산");
		cityList.add("대구");
		cityList.add("제주");
		
		Map<String, List> map = new HashMap<String, List>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		return new ModelAndView("register.jsp", "map", map);
	}
	
	@RequestMapping("/insert.do")
	public String inserData(@ModelAttribute UserVO user) {		
		if (user != null)
			userService.insertUser(user);
		return "redirect:/getList.do";
	}
	
	@RequestMapping("/getList.do")
	public ModelAndView getUserLIst() {
		List<UserVO> userList = userService.getUserList();
		return new ModelAndView("userList.jsp", "userList", userList);
	}
	
	@RequestMapping("/edit.do")
	public ModelAndView editUser(@RequestParam String id,
			@ModelAttribute UserVO user) {

		user = userService.getUser(id);
		
		List<String> genderList = new ArrayList<String>();
		genderList.add("남");
		genderList.add("여");
		
		List<String> cityList = new ArrayList<String>();
		cityList.add("서울");
		cityList.add("부산");
		cityList.add("대구");
		cityList.add("제주");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("genderList", genderList);
		map.put("cityList", cityList);
		map.put("user", user);

		return new ModelAndView("edit.jsp", "map", map);

	}

	@RequestMapping("/update.do")
	public String updateUser(@ModelAttribute UserVO user) {
		userService.updateUser(user);
		return "redirect:/getList.do";

	}

	@RequestMapping("/delete.do")
	public String deleteUser(@RequestParam String id) {
		System.out.println("id = " + id);
		userService.deleteUser(id);
		return "redirect:/getList.do";
	}
}
