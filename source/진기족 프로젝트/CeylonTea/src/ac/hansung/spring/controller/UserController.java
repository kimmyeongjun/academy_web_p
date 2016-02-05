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
	// 1. 인증, 강의등록 서비스(관리자)
	@Autowired
	private HansungInvadeService hansungService;
	
	// 2. 회원관리 서비스
	@Autowired
	private MemberService memberService;
	
	// 3. 모든 강의정보 서비스
	@Autowired
	private LectureService lectureService;
	
	// 4. 내가 평가한 글 서비스
	@Autowired		
	private EVLectureService evLectureService;

	@RequestMapping("/certify.do")
	public String certify(HttpServletRequest request) { // 1. 인증
		// 인증 성공한 경우
		String id = request.getParameter("id");
		HansungVO hansung = new HansungVO();
		hansung.setHansung_id(id);
		
		if (hansungService.certifyUser(hansung) == true) {
			System.out.println("인증 성공이다.");
			return "memberJoinForm.jsp";

		} else { // 인증 실패한 경우
					// TODO 1. 인증실패 메세지 띄우기
					// 2. 화면은 같은 곳에 머물기

			System.out.println("인증 실패다");
		}
		return null;
	}

	// 2. 가입
	@RequestMapping("/join.do")
	public String join(@ModelAttribute MemberVO member, HttpServletRequest request) {
		// TODO 가입을 성공한 경우(1. 학번 중복검사, 2. 비밀번호 검사 3. 모든항목 입력 ) 모두 HTML에서 처리

		System.out.println("가입 성공");

		memberService.insertMember(member);// DB에 등록

		// 재인증
		hansungService.certifyUser(new HansungVO(request.getParameter("hansungID"), request.getParameter("hansungPW")));

		System.out.println("여기가지 호출됨");
		// 내가 들은 강의 집어넣기
		hansungService.addMyLectures(member.getMember_id());

		System.out.println(member.getMember_id() + "," + member.getMember_name());

		return "introPage.html";
		// 가입을 실패한경우(위의 Exception)->경고메세지 입력 후 같은 페이지 머물기
	}

	// 3. 모든 강의 등록 - 서버에서 사용, 관리자 메서드
	@RequestMapping("/insertAllLectures.do")
	public String addAllLectures() {

		if (hansungService.insertAllLectures() == true) {
			// TODO 모든 강의가 성공적으로 등록 되었다면 -

			return "memberJoinForm.html";
		} else {

		}

		return "introPage.html";

	}

	// 4. 로그인 처리 후 메인페이지 이동
	@RequestMapping("/login.do")
	public ModelAndView loginProcess(MemberVO member, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		MemberVO loginMember = memberService.getMember(member.getMember_id(), member.getMember_pw());

		session.setAttribute("member", member.getMember_id());

		mav.addObject("member", loginMember);
		mav.setViewName("mainPage.html");
		return mav;
	}

	// 5. 검색 (강의명, 교수명 으로 검색)
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

		System.out.println("또 찍어보겠습니다~~~~~~~~~~");
		// test_print
		for (int i = 0; i < lectureList.size(); i++) {
			System.out.println(lectureList.get(i).toString());
		}

		return new ModelAndView("searchPage.jsp", "lectureList", lectureList);
	}

	// 6. 아이디 중복 확인
	   @RequestMapping("/idCheck.do")
	   public void idCheck(HttpServletRequest request, HttpServletResponse response) {
	      String inputID = request.getParameter("USERID");
	      System.out.println("입력받은 inputID = " + inputID);

	      JSONObject obj = new JSONObject();

	      System.out.println("투스트링");
	      System.out.println(obj);
	      System.out.println(obj.toString());

	      if (memberService.checkById(inputID)) {
	         // 아이디가 존재한다면(가입 불가능)
	         obj.put("msg", 1);
	      } else {
	         // 아이디가 존재하지 않는다면(가입 가능)
	         obj.put("msg", 0);
	      }

	      try {
	         response.setCharacterEncoding("UTF-8");
	         response.getWriter().print(obj.toString());
	      } catch (IOException e) {
	         e.printStackTrace();
	      }

	   }
	
	
	// 이 요청에는 시퀀스 번호 1개밖에 안넘어옴.
	// 8. 강의 세부정보 출력 TODO -수정
	@RequestMapping("/detail.do")
	public ModelAndView detail(HttpServletRequest request,HttpSession session) {
		int sequence = Integer.parseInt(request.getParameter("sequence_num"));
		LectureVO vo = lectureService.selectBySequence(sequence);

		// TODO 시퀀스를 파라미터로 디비에서 검색해서 그 결과값 하나를 뿌려준다
		System.out.println("선택한 과목의 시퀀스를 기준으로 검색한 결과는 " + vo);
		// return null;
		return new ModelAndView("detailPage.jsp", "lecture", vo);
	}

	// 9. 아이디, 패스워드로 인증하기
	@RequestMapping("/certify2.do")
	public void certify2(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String inputID = (String) session.getAttribute("member");
		String inputPW = request.getParameter("USERPW");

		System.out.println("입력받은 inputID = " + inputID);

		JSONObject obj = new JSONObject();

		System.out.println("투스트링");
		System.out.println(obj);
		System.out.println(obj.toString());

		if (memberService.getMember(inputID, inputPW) == null) {
			// 아이디가 존재한다면(가입 불가능)
			obj.put("msg", 1);
		} else {
			// 아이디가 존재하지 않는다면(가입 가능)
			obj.put("msg", 0);
		}

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	// 10. 내가 수강한 강의 정보 , 내가 쓴 글 목록
	@RequestMapping("/getinfolist.do")
	public ModelAndView getMyInfoList(HttpSession session) {
		// 세션에서 내 정보 불러오기
		String member_id = (String) session.getAttribute("member");
		// 내가 수강한 강의 리스트
		List<LectureVO> myLectureList = lectureService.getMyLectureList(member_id);

		System.out.println("내가 수강한 강의 정보  찍어보겠습니다~~~~~~~~~~");

		// test_print
		for (int i = 0; i < myLectureList.size(); i++) {
			System.out.println(myLectureList.get(i).toString());
		}
		
		
		System.out.println("내가 쓴 글들을 찍어봅니다.다~~~~~~~~~~");
		
		List<EVLectureVO> myEvalList= null;
		myEvalList= evLectureService.getMyEvalList(member_id);
		
		System.out.println("내가 쓴 글들을 찍어봅니다.다~~~~~~~~~~!!");
		
		if(myEvalList==null){
			System.out.println("수강한 강의가 없습니다.");
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
	
	// 11. 내 정보 수정하기
	@RequestMapping("/update.do")
	public String updateInfo(@ModelAttribute MemberVO member){
		System.out.println("============= "+member);
		memberService.updateMember(member);
		return "redirect:/getinfolist.do";
	}
	
	// 12. 강의평가 등록하기
	@RequestMapping("/write.do")
	public void addEval(@ModelAttribute EVLectureVO vo, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		vo.setSequence_num(Integer.parseInt(request.getParameter("sequence_num")));
		vo.setMember_id((String) session.getAttribute("member"));
		// vo.setWrite_date(sys);
		System.out.println("@usercontrol write : vo = " + vo);

		evLectureService.addEval(vo);
		
		
		// 평균 구하기
		LectureVO lecture = lectureService.selectBySequence(vo.getSequence_num());

		// 평균 열정
		lecture.setAvg_passion(
				(lecture.getAvg_passion() * lecture.getEval_count() + vo.getPassion()) / lecture.getEval_count() + 1);
		// 평균 소통
		lecture.setAvg_communication((lecture.getAvg_communication() * lecture.getEval_count() + vo.getCommunication())
				/ lecture.getEval_count() + 1);
		// 평균 공정성
		lecture.setAvg_fairness(
				(lecture.getAvg_fairness() * lecture.getEval_count() + vo.getFairness()) / lecture.getEval_count() + 1);
		// 평균 이득
		lecture.setAvg_benefit(
				(lecture.getAvg_benefit() * lecture.getEval_count() + vo.getBenefit()) / lecture.getEval_count() + 1);

		// 카운트 증가
		lecture.setEval_count(lecture.getEval_count() + 1);

		// 총 평균
		lecture.setAvg_evaluation((lecture.getAvg_passion() + lecture.getAvg_benefit() + lecture.getAvg_communication()
				+ lecture.getAvg_fairness()) / 4);

		// 업데이트된 lecture객체를 DB에 업데이트
		lectureService.updateAllLectures(lecture);
		System.out.println("@ucontrol lecture = "+lecture);

		request.setAttribute("sequence_num", vo.getSequence_num());
		System.out.println("됬으면 좋겠다.");
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
 * ArrayList<String>(); genderList.add("남"); genderList.add("여");
 * 
 * List<String> cityList = new ArrayList<String>(); cityList.add("서울");
 * cityList.add("부산"); cityList.add("대구"); cityList.add("제주");
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
 * List<String> genderList = new ArrayList<String>(); genderList.add("남");
 * genderList.add("여");
 * 
 * List<String> cityList = new ArrayList<String>(); cityList.add("서울");
 * cityList.add("부산"); cityList.add("대구"); cityList.add("제주");
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
