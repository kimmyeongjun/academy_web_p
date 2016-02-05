package ac.hansung.spring.invade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ac.hansung.spring.vo.LectureVO;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

public class Jericho {

	public void getPageAtScore(String str) {
		Source source;
		List<Element> tableList;
		Element table;
		List<Element> trList;
		Element tr;
		List<Element> tdList;

		source = new Source(str);

		tableList = source.getAllElements(HTMLElementName.TABLE);
		// 성적 조회 페이지의 테이블은 한 학기당 3개, 그리고 최종 결과를 표시하기 위해 1개 추가로 사용된다.
		for (int i = 0; i < (tableList.size() - 1) / 3; i++) {
			// 년도와 학기를 받아오는 부분
			table = tableList.get(i * 3);
			String time = table.getAllElements(HTMLElementName.TD).get(0).getTextExtractor().toString();
			System.out.print(time.substring(0, 4));
			System.out.println(" " + time.substring(9, 10));

			// 과목명과 과목코드를 받아오는 부분
			table = tableList.get((i * 3) + 2);
			trList = table.getAllElements(HTMLElementName.TR);
			// 선택한 <table>안에서, <tr>태그의 갯수만큼 돌아가는 for문. 첫줄은 범례이므로 제외
			for (int j = 1; j < trList.size(); j++) {
				tr = trList.get(j);
				tdList = tr.getAllElements(HTMLElementName.TD);
				System.out.println(tdList.get(0).getTextExtractor().toString() + "   "
						+ tdList.get(1).getTextExtractor().toString());
			}
		}
	}

	// 강의평가 결과조회 페이지에서 고를수있는 연도를 가져온다.
	public static String[] getYearAtPResult(String str) {
		String[] returnArr;
		Source source;

		source = new Source(str);

		// 0번 태그(index 0부터 해서 2)는 연도가 들어있다.
		Element select = source.getAllElements(HTMLElementName.SELECT).get(0);
		List<Element> optionList = select.getAllElements(HTMLElementName.OPTION);

		if (optionList.size() == 0) {
			System.out.println("@Jericho 고를 수 있는 연도에서 문제가 발생했습니다.");
			return null;
		}

		returnArr = new String[optionList.size()];
		for (int i = 0; i < optionList.size(); i++) {
			returnArr[i] = optionList.get(i).getAttributeValue("value");
		}
		return returnArr;
	}

	// 강의평가 결과조회 페이지에서 강의번호 목록을 가져온다.
	public static String[] getLectureNumberListAtPResult(String str) {
		String[] returnArr;
		Source source;
		source = new Source(str);

		// 2번 태그에는 강의목록이 들어있다.
		Element select = source.getAllElements(HTMLElementName.SELECT).get(2);
		List<Element> optionList = select.getAllElements(HTMLElementName.OPTION);

		if (optionList.size() == 0) {
			System.out.println("@Jericho 강의번호목록 불러오기에서 검색된 값 없음.");
			return null;
		}

		returnArr = new String[optionList.size()];
		for (int i = 0; i < optionList.size(); i++) {
			returnArr[i] = optionList.get(i).getAttributeValue("value");
		}
		return returnArr;
	}

	// 강의평가 결과조회 페이지에서 강의자 이름을 받아온다.
	public static String getPNameAtPResult(String str) {
		Source source;
		source = new Source(str);

		Element table = source.getAllElements(HTMLElementName.TABLE).get(4);
		List<Element> trList = table.getAllElements(HTMLElementName.TR);

		String PName = trList.get(1).getAllElements(HTMLElementName.TD).get(0).getFirstElement(HTMLElementName.B)
				.getContent().toString().replaceAll(" ", "");

		return PName;
	}

	// 강의평가 결과조회 페이지에서 강의 평점을 받아온다.
	public static double getAverageGradeAtPResult(String str) {
		Source source;
		source = new Source(str);

		Element table = source.getAllElements(HTMLElementName.TABLE).get(4);
		List<Element> trList = table.getAllElements(HTMLElementName.TR);

		double AverageGrade = Double.parseDouble(trList.get(1).getAllElements(HTMLElementName.TD).get(1)
				.getFirstElement(HTMLElementName.B).getContent().toString());

		return AverageGrade;
	}

	// 시간표 조회 페이지에서 연도를 얻어온다.
	public static String[] getYearAtSiganpyoSelect(String str) {
		String[] reString = null;

		Source source;
		source = new Source(str);
		List<Element> selectList = source.getAllElements(HTMLElementName.SELECT);

		// 1번째 태그에는 연도가 들어있다.
		List<Element> optionList = selectList.get(0).getAllElements(HTMLElementName.OPTION);
		// 고를 수 있는 연도를 yearArr에 저장한다.
		reString = new String[optionList.size()];
		for (int i = 0; i < optionList.size(); i++) {
			reString[i] = optionList.get(i).getContent().toString();
			reString[i] = reString[i].trim();
		}

		return reString;
	}

	// 시간표 조회 페이지에서 학과코드,학과명을 얻어온다.
	public static HashMap<String, String> getMajorInfoAtSiganpyoSelect(String str) {
		HashMap<String, String> returnMap = new HashMap<String, String>();

		Source source;
		source = new Source(str);
		List<Element> selectList = source.getAllElements(HTMLElementName.SELECT);

		// 3번째 태그에는 코드와 과목명이 들어있다. //이 문장에서 에러가 나는데 왜그런지 모르겠는 상태.
		List<Element> optionList = selectList.get(2).getAllElements(HTMLElementName.OPTION);

		// 고를 수 있는 과 분류코드와 과 분류이름을 저장한다.
		String[] codeAndName = new String[optionList.size()];
		String[] codes = new String[optionList.size()];
		String[] names = new String[optionList.size()];

		for (int i = 0; i < optionList.size(); i++) {
			codeAndName[i] = optionList.get(i).getContent().toString();
			codes[i] = codeAndName[i].substring(1, 5);
			names[i] = codeAndName[i].substring(7);
			returnMap.put(codes[i], names[i]);
		}

		return returnMap;
	}

	// 시간표 페이지에서 다양한 값을 받아옵니다.
	public static ArrayList<LectureVO> getManyInfoAtSinganpyo(String str, String year, String semester) {
		ArrayList<LectureVO> ReturnList = new ArrayList<LectureVO>();

		String s_code = null; // 과목 코드
		String s_name = null; // 과목명
		String g_unit = null; // 단위 학점
		String c_division = null;// 분반
		String p_name = null; // 교수명
		String s_type = null; // 강의 타입 , 전선, 전지, 등등..
		int p_number; // 사번

		Source source;
		source = new Source(str);
		List<Element> tableList = source.getAllElements(HTMLElementName.TABLE);

		List<Element> TRList = tableList.get(4).getAllElements(HTMLElementName.TR);

		// TRList의 사이즈가 0이나 1이라면,강의 정보가 들어있지 않은 것이다.
		if (TRList.size() < 2)
			return null;

		for (int i = 1; i < TRList.size(); i++) {
			Element TR = TRList.get(i);
			List<Element> TDList = TR.getAllElements(HTMLElementName.TD);

			c_division = TDList.get(5).getContent().toString().trim();
			// 분반이 하이퍼링크로 들어가있으므로, 값이 20보다 길면 유효한 값으로 인식한다.
			if (c_division.length() > 20) {
				// 과목코드가 7글자면 유효한 값으로 인식한다.
				if (TDList.get(0).getContent().toString().trim().length() == 7) {
					s_code = TDList.get(0).getContent().toString().trim(); // 과목코드
																			// 값을
																			// 받아온다.
					s_name = TDList.get(1).getContent().toString().trim(); // 과목명
					s_type = TDList.get(3).getContent().toString().trim(); // 강의
																			// 타입
																			// ,
																			// 전선,
																			// 전지,
																			// 등등..
					g_unit = TDList.get(4).getContent().toString().trim(); // 단위
																			// 학점
				}
				p_number = Integer.parseInt(
						TDList.get(5).getFirstElement(HTMLElementName.A).getAttributeValue("href").substring(34, 40)); // 사번을
																														// 받아온다.
				c_division = TDList.get(5).getFirstElement(HTMLElementName.A).getContent().toString().trim(); // 하이퍼링크
																												// 태그를
																												// 제거한
																												// 분반값을
																												// 받아온다.
				p_name = TDList.get(8).getContent().toString().replaceAll(" ", ""); // 교수명

				int semesterFix = 0;
				if (semester.equals("1") || semester.equals("3")) {
					semesterFix = 1;
				}
				if (semester.equals("2") || semester.equals("4")) {
					semesterFix = 2;
				}
				ReturnList.add(new LectureVO(s_code, s_name, s_type, Integer.parseInt(year), semesterFix, p_name,
						g_unit, c_division, p_number));
			}
		}
		return ReturnList;
	}
}