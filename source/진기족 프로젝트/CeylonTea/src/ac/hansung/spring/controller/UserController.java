package ac.hansung.spring.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ac.hansung.spring.invade.HansungInvadeService;
import ac.hansung.spring.service.EVLectureService;
import ac.hansung.spring.service.LectureService;
import ac.hansung.spring.service.MemberService;
import ac.hansung.spring.vo.EVLectureVO;
import ac.hansung.spring.vo.HansungVO;
import ac.hansung.spring.vo.LectureVO;
import ac.hansung.spring.vo.MemberVO;

@Controller
public class UserController {
	// 1. ����, ���ǵ�� ����(������)
	@Autowired
	private HansungInvadeService hansungService;
	
	// 2. ȸ������ ����
	@Autowired
	private MemberService memberService;
	
	// 3. ��� �������� ����
	@Autowired
	private LectureService lectureService;
	
	// 4. ���� ���� �� ����
	@Autowired		
	private EVLectureService evLectureService;

	@RequestMapping("/certify.do")
	public String certify(HttpServletRequest request) { // 1. ����
		// ���� ������ ���
		String id = request.getParameter("id");
		HansungVO hansung = new HansungVO();
		hansung.setHansung_id(id);
		
		if (hansungService.certifyUser(hansung) == true) {
			System.out.println("���� �����̴�.");
			return "memberJoinForm.jsp";

		} else { // ���� ������ ���
					// TODO 1. �������� �޼��� ����
					// 2. ȭ���� ���� ���� �ӹ���

			System.out.println("���� ���д�");
		}
		return null;
	}

	// 2. ����
	@RequestMapping("/join.do")
	public String join(@ModelAttribute MemberVO member, HttpServletRequest request) {
		// TODO ������ ������ ���(1. �й� �ߺ��˻�, 2. ��й�ȣ �˻� 3. ����׸� �Է� ) ��� HTML���� ó��

		System.out.println("���� ����");

		memberService.insertMember(member);// DB�� ���

		// ������
		hansungService.certifyUser(new HansungVO(request.getParameter("hansungID"), request.getParameter("hansungPW")));

		System.out.println("���Ⱑ�� ȣ���");
		// ���� ���� ���� ����ֱ�
		hansungService.addMyLectures(member.getMember_id());

		System.out.println(member.getMember_id() + "," + member.getMember_name());

		return "introPage.html";
		// ������ �����Ѱ��(���� Exception)->���޼��� �Է� �� ���� ������ �ӹ���
	}

	// 3. ��� ���� ��� - �������� ���, ������ �޼���
	@RequestMapping("/insertAllLectures.do")
	public String addAllLectures() {

		if (hansungService.insertAllLectures() == true) {
			// TODO ��� ���ǰ� ���������� ��� �Ǿ��ٸ� -

			return "memberJoinForm.html";
		} else {

		}

		return "introPage.html";

	}

	// 4. �α��� ó�� �� ���������� �̵�
	@RequestMapping("/login.do")
	public ModelAndView loginProcess(MemberVO member, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MemberVO loginMember = memberService.getMember(member.getMember_id(), member.getMember_pw());

		session.setAttribute("member", member.getMember_id());

		mav.addObject("member", loginMember);
		mav.setViewName("mainPage.html");
		return mav;
	}

	// 5. �˻� (���Ǹ�, ������ ���� �˻�)
	@RequestMapping(value = "/search1.do", method = RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request) {

		// Enumeration<String> e = request.getParameterNames();
		// while (e.hasMoreElements()) {
		// System.out.println(e.nextElement());
		// }

		String year = request.getParameter("year");
		String semester = request.getParameter("semester");
		String search_value = request.getParameter("search_value");
		if (search_value == null) {
			search_value = " ";
		}

		HashMap<String, String> map = new HashMap<>();
		map.put("year", year);
		map.put("semester", semester);
		map.put("search_value", "%" + search_value + "%");

		List<LectureVO> lectureList = lectureService.searchLecture(map);

		System.out.println("�� ���ڽ��ϴ�~~~~~~~~~~");
		// test_print
		for (int i = 0; i < lectureList.size(); i++) {
			System.out.println(lectureList.get(i).toString());
		}

		return new ModelAndView("searchPage.jsp", "lectureList", lectureList);
	}

	// 6. ���̵� �ߺ� Ȯ��
	   @RequestMapping("/idCheck.do")
	   public void idCheck(HttpServletRequest request, HttpServletResponse response) {
	      String inputID = request.getParameter("USERID");
	      System.out.println("�Է¹��� inputID = " + inputID);

	      JSONObject obj = new JSONObject();

	      System.out.println("����Ʈ��");
	      System.out.println(obj);
	      System.out.println(obj.toString());

	      if (memberService.checkById(inputID)) {
	         // ���̵� �����Ѵٸ�(���� �Ұ���)
	         obj.put("msg", 1);
	      } else {
	         // ���̵� �������� �ʴ´ٸ�(���� ����)
	         obj.put("msg", 0);
	      }

	      try {
	         response.setCharacterEncoding("UTF-8");
	         response.getWriter().print(obj.toString());
	      } catch (IOException e) {
	         e.printStackTrace();
	      }

	   }
	
	
	// �� ��û���� ������ ��ȣ 1���ۿ� �ȳѾ��.
	// 8. ���� �������� ��� TODO -����
	@RequestMapping("/detail.do")
	public ModelAndView detail(HttpServletRequest request,HttpSession session) {
		int sequence = Integer.parseInt(request.getParameter("sequence_num"));
		LectureVO vo = lectureService.selectBySequence(sequence);

		// TODO �������� �Ķ���ͷ� ��񿡼� �˻��ؼ� �� ����� �ϳ��� �ѷ��ش�
		System.out.println("������ ������ �������� �������� �˻��� ����� " + vo);
		// return null;
		return new ModelAndView("detailPage.jsp", "lecture", vo);
	}

	// 9. ���̵�, �н������ �����ϱ�
	@RequestMapping("/certify2.do")
	public void certify2(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String inputID = (String) session.getAttribute("member");
		String inputPW = request.getParameter("USERPW");

		System.out.println("�Է¹��� inputID = " + inputID);

		JSONObject obj = new JSONObject();

		System.out.println("����Ʈ��");
		System.out.println(obj);
		System.out.println(obj.toString());

		if (memberService.getMember(inputID, inputPW) == null) {
			// ���̵� �����Ѵٸ�(���� �Ұ���)
			obj.put("msg", 1);
		} else {
			// ���̵� �������� �ʴ´ٸ�(���� ����)
			obj.put("msg", 0);
		}

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	// 10. ���� ������ ���� ���� , ���� �� �� ���
	@RequestMapping("/getinfolist.do")
	public ModelAndView getMyInfoList(HttpSession session) {
		// ���ǿ��� �� ���� �ҷ�����
		String member_id = (String) session.getAttribute("member");
		// ���� ������ ���� ����Ʈ
		List<LectureVO> myLectureList = lectureService.getMyLectureList(member_id);

		System.out.println("���� ������ ���� ����  ���ڽ��ϴ�~~~~~~~~~~");

		// test_print
		for (int i = 0; i < myLectureList.size(); i++) {
			System.out.println(myLectureList.get(i).toString());
		}
		
		
		System.out.println("���� �� �۵��� ���ϴ�.��~~~~~~~~~~");
		
		List<EVLectureVO> myEvalList= null;
		myEvalList= evLectureService.getMyEvalList(member_id);
		
		System.out.println("���� �� �۵��� ���ϴ�.��~~~~~~~~~~!!");
		
		if(myEvalList==null){
			System.out.println("������ ���ǰ� �����ϴ�.");
		}
		else{

			for(EVLectureVO evLectureVO : myEvalList){
				LectureVO returnVO = lectureService.selectBySequence(evLectureVO.getSequence_num());
				evLectureVO.setLectureVO(returnVO);
			}
		}
		
		   Map<String, Object> map = new HashMap<String, Object>();
		   map.put("myLectureList", myLectureList);
		   map.put("myEvalList", myEvalList);

		return new ModelAndView("myPage.jsp", "map", map);
	}
	
	// 11. �� ���� �����ϱ�
	@RequestMapping("/update.do")
	public String updateInfo(@ModelAttribute MemberVO member){
		System.out.println("============= "+member);
		memberService.updateMember(member);
		return "redirect:/getinfolist.do";
	}
	
	// 12. ������ ����ϱ�
	@RequestMapping("/write.do")
	public void addEval(@ModelAttribute EVLectureVO vo, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		vo.setSequence_num(Integer.parseInt(request.getParameter("sequence_num")));
		vo.setMember_id((String) session.getAttribute("member"));
		// vo.setWrite_date(sys);
		System.out.println("@usercontrol write : vo = " + vo);

		evLectureService.addEval(vo);
		
		
		// ��� ���ϱ�
		LectureVO lecture = lectureService.selectBySequence(vo.getSequence_num());

		// ��� ����
		lecture.setAvg_passion(
				(lecture.getAvg_passion() * lecture.getEval_count() + vo.getPassion()) / lecture.getEval_count() + 1);
		// ��� ����
		lecture.setAvg_communication((lecture.getAvg_communication() * lecture.getEval_count() + vo.getCommunication())
				/ lecture.getEval_count() + 1);
		// ��� ������
		lecture.setAvg_fairness(
				(lecture.getAvg_fairness() * lecture.getEval_count() + vo.getFairness()) / lecture.getEval_count() + 1);
		// ��� �̵�
		lecture.setAvg_benefit(
				(lecture.getAvg_benefit() * lecture.getEval_count() + vo.getBenefit()) / lecture.getEval_count() + 1);

		// ī��Ʈ ����
		lecture.setEval_count(lecture.getEval_count() + 1);

		// �� ���
		lecture.setAvg_evaluation((lecture.getAvg_passion() + lecture.getAvg_benefit() + lecture.getAvg_communication()
				+ lecture.getAvg_fairness()) / 4);

		// ������Ʈ�� lecture��ü�� DB�� ������Ʈ
		lectureService.updateAllLectures(lecture);
		System.out.println("@ucontrol lecture = "+lecture);

		request.setAttribute("sequence_num", vo.getSequence_num());
		System.out.println("������ ���ڴ�.");
		RequestDispatcher dis = request.getRequestDispatcher("detail.do");
		try {
			dis.forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

/*
 * @RequestMapping("/register.do") public ModelAndView
 * registerUser(@ModelAttribute UserVO user) { List<String> genderList = new
 * ArrayList<String>(); genderList.add("��"); genderList.add("��");
 * 
 * List<String> cityList = new ArrayList<String>(); cityList.add("����");
 * cityList.add("�λ�"); cityList.add("�뱸"); cityList.add("����");
 * 
 * Map<String, List> map = new HashMap<String, List>(); map.put("genderList",
 * genderList); map.put("cityList", cityList); return new
 * ModelAndView("register.jsp", "map", map); }
 * 
 * @RequestMapping("/insert.do") public String inserData(@ModelAttribute UserVO
 * user) { if (user != null) userService.insertUser(user); return
 * "redirect:/getList.do"; }
 * 
 * @RequestMapping("/getList.do") public ModelAndView getUserLIst() {
 * List<UserVO> userList = userService.getUserList(); return new
 * ModelAndView("userList.jsp", "userList", userList); }
 * 
 * @RequestMapping("/edit.do") public ModelAndView editUser(@RequestParam String
 * id,
 * 
 * @ModelAttribute UserVO user) {
 * 
 * user = userService.getUser(id);
 * 
 * List<String> genderList = new ArrayList<String>(); genderList.add("��");
 * genderList.add("��");
 * 
 * List<String> cityList = new ArrayList<String>(); cityList.add("����");
 * cityList.add("�λ�"); cityList.add("�뱸"); cityList.add("����");
 * 
 * Map<String, Object> map = new HashMap<String, Object>();
 * map.put("genderList", genderList); map.put("cityList", cityList);
 * map.put("user", user);
 * 
 * return new ModelAndView("edit.jsp", "map", map);
 * 
 * }
 * 
 * 
 * }
 * 
 * @RequestMapping("/delete.do") public String deleteUser(@RequestParam String
 * id) { System.out.println("id = " + id); userService.deleteUser(id); return
 * "redirect:/getList.do"; }
 */
