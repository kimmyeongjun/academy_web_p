package ac.hansung.spring.invade;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import ac.hansung.spring.vo.HansungVO;
import ac.hansung.spring.vo.LectureVO;

@Component
public class HttpComponent {

	public boolean identify(HansungVO hansung, CloseableHttpClient httpClient) {
		try {
			String id = hansung.getHansung_id();
			String pwd = hansung.getHansung_pw();
			String uri = "https://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl";

			// 포스트 요청을 준비한다.
			HttpPost httpPost = new HttpPost();
			httpPost.setURI(new URI(uri));

			// 매개변수를 준비한다.
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id", id));
			nameValuePairs.add(new BasicNameValuePair("passwd", pwd));

			// 포스트 요청에 매개변수를 넣는다.
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8);
			httpPost.setEntity(entity);

			// 요청을 보낸다.
			CloseableHttpResponse response = httpClient.execute(httpPost);

			Header[] headers = response.getAllHeaders();
			// sso토큰이 왔다면 로그인 성공이다.
			for (Header obj : headers) {
				if (obj.toString().length() > 20 && obj.toString().substring(0, 20).equals("Set-Cookie: ssotoken")) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<LectureVO> insertAllLectures(CloseableHttpClient httpClient) {
		ArrayList<LectureVO> returnList = new ArrayList<LectureVO>();

		try {
			HttpGet httpGet = new HttpGet();
			httpGet.setURI(new URI("http://info.hansung.ac.kr/servlet/s_jik.jik_siganpyo_s_up"));
			CloseableHttpResponse response = httpClient.execute(httpGet);
			HttpEntity respEntity = response.getEntity();
			String content = EntityUtils.toString(respEntity);
			// System.out.println(content);

			// 선택가능한 연도 목록을 뽑아라.
			String[] yearArr = Jericho.getYearAtSiganpyoSelect(content);

			// 연도-학기를 이용하여 새로운 강좌분류를 받는다.
			// 연도를 다루는 for문
			for (int i = 0; i < 3; i++) {
				// for (int i = 0; i < 1; i++) {
				System.out.println("=== 연도 = " + yearArr[i]);
				// 학기를 다루는 for문
				for (int j = 1; j < 5; j++) {
					// for (int j = 1; j < 2; j++) {
					System.out.println("=== 학기 = " + j);
					// 매개변수를 준비한다.
					URI uri = new URIBuilder().setScheme("http").setHost("info.hansung.ac.kr")
							.setPath("/servlet/s_jik.jik_siganpyo_s_up").setParameter("year", yearArr[i])
							.setParameter("semester", j + "").build();
					httpGet = new HttpGet(uri);

					// 요청을 보낸다.
					response = httpClient.execute(httpGet);
					respEntity = response.getEntity();
					content = EntityUtils.toString(respEntity);

					// 과목분류 코드와 과목분류명을 파싱해서 얻어낸다.
					HashMap<String, String> codeAndName = Jericho.getMajorInfoAtSiganpyoSelect(content);

					// 과목 분류명은 당장은 쓰지않는걸로 하고, 과목분류 코드를 가지고 새로운 페이지를 요청한다.
					Iterator<String> iterator = codeAndName.keySet().iterator();

					while (iterator.hasNext()) {
						uri = new URIBuilder().setScheme("http").setHost("info.hansung.ac.kr")
								.setPath("/servlet/s_jik.jik_siganpyo_s_list").setParameter("year", yearArr[i])
								.setParameter("semester", j + "").setParameter("majorcode", iterator.next()).build();
						httpGet = new HttpGet(uri);

						// 요청을 보낸다.
						response = httpClient.execute(httpGet);
						respEntity = response.getEntity();
						content = EntityUtils.toString(respEntity);

						ArrayList<LectureVO> particleList = Jericho.getManyInfoAtSinganpyo(content, yearArr[i], j + "");
						if (particleList != null) {
							for (LectureVO item : particleList) {
								returnList.add(item);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("@Httpcompoent 리턴합니다.");
		return returnList;
	}

	public ArrayList<LectureVO> addMyLectures(CloseableHttpClient httpClient) {
		ArrayList<LectureVO> returnList = new ArrayList<LectureVO>();

		for (int i = 0; i < returnList.size(); i++) {
			System.out.println("과연!!" + returnList.get(i));
		}
		try {
			HttpPost httpPost = new HttpPost();
			String uri = "http://info.hansung.ac.kr/jsp/suup_pyunga/suup_pyunga_result_h.jsp";
			httpPost.setURI(new URI(uri));

			// 강의만족도 결과조회 페이지를 요청한다.
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity respEntity = response.getEntity();
			String content = EntityUtils.toString(respEntity);

			// 고를 수 있는 연도를 받아온다.
			String[] yearArr = Jericho.getYearAtPResult(content);

			// 연도를 반복하는 for문
			for (int i = 0; i < yearArr.length; i++) {
				// 학기를 반복하는 for문
				for (int j = 1; j < 3; j++) {
					// 요청을 위한 변수 준비
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("hakneando", yearArr[i]));
					nameValuePairs.add(new BasicNameValuePair("hakgi", j + ""));
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
					// 요청
					response = httpClient.execute(httpPost);
					// 결과 가공
					respEntity = response.getEntity();
					content = EntityUtils.toString(respEntity);

					// 고를 수 있는 강의목록을 받아온다.
					String[] lectureArr = Jericho.getLectureNumberListAtPResult(content);

					// 2015학년도 2학기는 아직 강의평가가 없으니까 넘어가야한다.
					if (lectureArr == null)
						continue;

					for (int k = 0; k < lectureArr.length; k++) {
						nameValuePairs = new ArrayList<NameValuePair>();
						nameValuePairs.add(new BasicNameValuePair("hakneando", yearArr[i]));
						nameValuePairs.add(new BasicNameValuePair("hakgi", j + ""));
						nameValuePairs.add(new BasicNameValuePair("gwamok", lectureArr[k]));
						httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
						response = httpClient.execute(httpPost);
						respEntity = response.getEntity();
						content = EntityUtils.toString(respEntity);
						String PName = Jericho.getPNameAtPResult(content);
						double AverageGrade = Jericho.getAverageGradeAtPResult(content);

						returnList.add(new LectureVO(lectureArr[k].substring(0, 7), lectureArr[k].substring(7, 8),
								Integer.parseInt(yearArr[i]), j, PName, AverageGrade));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
}
//
// httpGet.setURI(new URI(uri));
// CloseableHttpResponse response = httpClient.execute(httpGet);
// HttpEntity respEntity = response.getEntity();
// String content = EntityUtils.toString(respEntity);
//
// System.out.println("여기까지 왔습니다.@@@@@");
// System.out.println(content);
// // 고를 수 있는 연도를 받아온다.
// String[] yearArr = jeri.getYear(content);
// for (int i = 0; i < yearArr.length; i++) {
// System.out.println("@httpcomponent 연도입니다." + yearArr[i]);
//
// for (int j = 1; j < 3; j++) {
// System.out.println("@httpcomponent 학기입니다." + j);
// // 한학기를 선택하고 요청한다.
// List<NameValuePair> nameValuePairs = new
// ArrayList<NameValuePair>();
// nameValuePairs.add(new BasicNameValuePair("hakneando",
// yearArr[i]));
// nameValuePairs.add(new BasicNameValuePair("hakgi", j + ""));
// httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
// Consts.UTF_8));
// response = httpClient.execute(httpPost);
// respEntity = response.getEntity();
// content = EntityUtils.toString(respEntity);
//
// // 고를 수 있는 강의목록을 받아온다.
// String[] lectureArr = jeri.getLectureNumberList(content);
//
// // 2015학년도 2학기는 아직 강의평가가 없으니까 넘어가야한다.
// if (lectureArr == null)
// continue;
// for (int k = 0; k < lectureArr.length; k++) {
// System.out.println("@httpcomponent 강의 :" + lectureArr[k]);
//
// nameValuePairs = new ArrayList<NameValuePair>();
// nameValuePairs.add(new BasicNameValuePair("hakneando",
// yearArr[i]));
// nameValuePairs.add(new BasicNameValuePair("hakgi", j + ""));
// nameValuePairs.add(new BasicNameValuePair("gwamok",
// lectureArr[k]));
// httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,
// Consts.UTF_8));
// response = httpClient.execute(httpPost);
// respEntity = response.getEntity();
// content = EntityUtils.toString(respEntity);
// String[] teacherNameArr = jeri.getTeacherName(content);
// String[] teacherGradeArr = jeri.getTeacherGrade(content);
// }
// }
// }

// // Header[] 배열을 Set-Cookie 갯수만큼 선언한다.
// Header[] headers2 = new Header[count];
// int count2 = 0;
// // 집어넣는다.
// for (Header obj : headers) {
// System.out.println(obj.toString());
// if (obj.toString().length() > 9 && obj.toString().substring(0,
// // 10).equals("Set-Cookie")) {
// headers2[count2]=obj;
// count2++;
// }
// }
//
//
// HttpGet httpGet3 = new HttpGet();
// // String uri3 = "http://info.hansung.ac.kr/h_jik/jik_siganpyo_s_main.html";
// String uri3 = "http://info.hansung.ac.kr/servlet/s_jik.jik_siganpyo_s_up";
//
// httpGet3.setURI(new URI(uri3));
// httpGet3.setHeaders(headers2);
//
// CloseableHttpResponse response2 = httpClient.execute(httpGet3);
// HttpEntity respEntity2 = response2.getEntity();
// String content2 = EntityUtils.toString(respEntity2);
//
// System.out.println("여기까지 왔습니다.@@@@@");
// System.out.println(content2);

// private Jericho jeri = new Jericho();
// private CloseableHttpClient httpClient = HttpClients.createDefault();
// private HttpPost httpPost = new HttpPost();
// private HttpEntity respEntity;

// public HttpComponent() {
// try {
// /// 첫번째 요청 - 아이디 비밀번호를 가지고 접속한다.
// identify(httpClient);
//
// /// 두번째 요청 - 누적성적 페이지로 들어가서 내 정보를 받아온다.
// workAtScorePage(httpClient);
// System.out.println();
// System.out.println();
// System.out.println("뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱뾰로롱");
// System.out.println();
// System.out.println();
// /// 세번째 요청 - 강의만족도 결과조회 페이지로 들어가서 연도,학기,과목코드,분반,교수이름,교수평점 을 가져온다.
// workAtLectureEvaluateResult(httpClient);
//
// } catch (Exception e) {
// e.printStackTrace();
// }
// }

// //아이디 비밀번호로 인증, 접속한다.
// public boolean identify(HansungVO hansung) {
// try{
// String id = hansung.getHansung_id();
// String pwd = hansung.getHansung_pw();
// String uri = "https://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl";
//
// // 포스트 요청을 준비한다.
// httpPost.setURI(new URI(uri));
// // 매개변수를 준비한다.
// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
// nameValuePairs.add(new BasicNameValuePair("id", id));
// nameValuePairs.add(new BasicNameValuePair("passwd", pwd));
// // 포스트 요청에 매개변수를 넣는다.
// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs,
// Consts.UTF_8);
// httpPost.setEntity(entity);
// // 접속을 시도한다.
// CloseableHttpResponse response = httpClient.execute(httpPost);
// HttpEntity respEntity = response.getEntity();
//
// Header[] cookie = null;// mj
// cookie = (Header[]) response.getHeaders("Set-Cookie");
// for(int i=0; i<cookie.length; i++){
// System.out.println(cookie[i]);
// }
//
//
// }
// catch(Exception e){
// e.printStackTrace();
// }
// return false;
// }

// // 성적조회 누적 페이지에서 값을 가져온다.
// public void workAtScorePage(CloseableHttpClient httpClient)
// throws URISyntaxException, ClientProtocolException, IOException {
// String uri = "https://info.hansung.ac.kr/fuz/seongjeok/seongjeok.jsp";
//
// httpPost.setURI(new URI(uri));
// CloseableHttpResponse response = httpClient.execute(httpPost);
// HttpEntity respEntity = response.getEntity();
// String content = EntityUtils.toString(respEntity);
// jeri.getAtScorePage(content);
// }
//
// // 강의만족도 결과 조회 페이지에서 값을 가져온다.
// public void workAtLectureEvaluateResult(CloseableHttpClient httpClient)
// throws URISyntaxException, ClientProtocolException, IOException {
// String uri =
// // "https://info.hansung.ac.kr/jsp/suup_pyunga/suup_pyunga_result_h.jsp";
//
// // 강의만족도 결과조회 페이지를 요청한다.
// httpPost.setURI(new URI(uri));
// CloseableHttpResponse response = httpClient.execute(httpPost);
// HttpEntity respEntity = response.getEntity();
// String content = EntityUtils.toString(respEntity);
//
// // 고를 수 있는 연도를 받아온다.
// String[] yearArr = jeri.getYear(content);
// for (int i = 0; i < yearArr.length; i++) {
// System.out.println("@httpcomponent 연도입니다." + yearArr[i]);
//
// for (int j = 1; j < 3; j++) {
// System.out.println("@httpcomponent 학기입니다." + j);
// // 한학기를 선택하고 요청한다.
// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
// nameValuePairs.add(new BasicNameValuePair("hakneando", yearArr[i]));
// nameValuePairs.add(new BasicNameValuePair("hakgi", j + ""));
// httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
// response = httpClient.execute(httpPost);
// respEntity = response.getEntity();
// content = EntityUtils.toString(respEntity);
//
// // 고를 수 있는 강의목록을 받아온다.
// String[] lectureArr = jeri.getLectureNumberList(content);
//
// // 2015학년도 2학기는 아직 강의평가가 없으니까 넘어가야한다.
// if (lectureArr == null)
// continue;
// for (int k = 0; k < lectureArr.length; k++) {
// System.out.println("@httpcomponent 강의 :" + lectureArr[k]);
//
// nameValuePairs = new ArrayList<NameValuePair>();
// nameValuePairs.add(new BasicNameValuePair("hakneando", yearArr[i]));
// nameValuePairs.add(new BasicNameValuePair("hakgi", j + ""));
// nameValuePairs.add(new BasicNameValuePair("gwamok", lectureArr[k]));
// httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
// response = httpClient.execute(httpPost);
// respEntity = response.getEntity();
// content = EntityUtils.toString(respEntity);
// String[] teacherNameArr = jeri.getTeacherName(content);
// String[] teacherGradeArr = jeri.getTeacherGrade(content);
// }
// }
// }
//
// // 고를 수 있는 과목코드를 받아온다.
// // 과목코드를 하나 고른다. 요청한다.
// // 강의자 이름, 평점을 받아온다.
// }
//
// }

/*
 * //첫번째 요청이 성공했을 때 / 로그인 비밀번호 틀리면 안돌아가게 바꿔야 할 듯.. if (respEntity != null) {
 * //두번째 요청에 대한 일 - 성적 페이지로 들어간다. HttpResponse response2 =
 * httpclient.execute(httppost2); HttpEntity respEntity2 =
 * response2.getEntity(); String content = EntityUtils.toString(respEntity);
 * String content2 = EntityUtils.toString(respEntity2); //System.out.println(
 * "###결과입니다. " + content + content2); // jeri.getTableDataTest(content2);
 * 
 * //세번째 요청에 대한 일 - 강의만족도 페이지로 들어간다. ArrayList<NameValuePair> nameValuePairs3 =
 * new ArrayList<NameValuePair>(); nameValuePairs3.add(new
 * BasicNameValuePair("hakneando", "2013")); nameValuePairs3.add(new
 * BasicNameValuePair("hakgi", "2")); nameValuePairs3.add(new
 * BasicNameValuePair("gwamok", "ISE0012A"));
 * 
 * httppost3.setEntity(new UrlEncodedFormEntity(nameValuePairs3, HTTP.UTF_8));
 * HttpResponse response3 = httpclient.execute(httppost3); HttpEntity
 * respEntity3 = response3.getEntity(); String content3 =
 * EntityUtils.toString(respEntity3); // System.out.println( "###결과입니다. " +
 * content3);
 * 
 * //강의 만족도 페이지로 들어간다. //선택 가능한 연도를 얻는다. //for문 //연도 하나를 누르고 페이지를 요청한다. //페이지에서
 * 과목코드 목록을 얻는다. //for문 //과목코드 하나를 고르고 페이지를 요청한다. //페이지에서 강의명, 분반, 강사명, 평가점수, }
 */