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

			// ����Ʈ ��û�� �غ��Ѵ�.
			HttpPost httpPost = new HttpPost();
			httpPost.setURI(new URI(uri));

			// �Ű������� �غ��Ѵ�.
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("id", id));
			nameValuePairs.add(new BasicNameValuePair("passwd", pwd));

			// ����Ʈ ��û�� �Ű������� �ִ´�.
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8);
			httpPost.setEntity(entity);

			// ��û�� ������.
			CloseableHttpResponse response = httpClient.execute(httpPost);

			Header[] headers = response.getAllHeaders();
			// sso��ū�� �Դٸ� �α��� �����̴�.
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

			// ���ð����� ���� ����� �̾ƶ�.
			String[] yearArr = Jericho.getYearAtSiganpyoSelect(content);

			// ����-�б⸦ �̿��Ͽ� ���ο� ���ºз��� �޴´�.
			// ������ �ٷ�� for��
			for (int i = 0; i < 3; i++) {
				// for (int i = 0; i < 1; i++) {
				System.out.println("=== ���� = " + yearArr[i]);
				// �б⸦ �ٷ�� for��
				for (int j = 1; j < 5; j++) {
					// for (int j = 1; j < 2; j++) {
					System.out.println("=== �б� = " + j);
					// �Ű������� �غ��Ѵ�.
					URI uri = new URIBuilder().setScheme("http").setHost("info.hansung.ac.kr")
							.setPath("/servlet/s_jik.jik_siganpyo_s_up").setParameter("year", yearArr[i])
							.setParameter("semester", j + "").build();
					httpGet = new HttpGet(uri);

					// ��û�� ������.
					response = httpClient.execute(httpGet);
					respEntity = response.getEntity();
					content = EntityUtils.toString(respEntity);

					// ����з� �ڵ�� ����з����� �Ľ��ؼ� ����.
					HashMap<String, String> codeAndName = Jericho.getMajorInfoAtSiganpyoSelect(content);

					// ���� �з����� ������ �����ʴ°ɷ� �ϰ�, ����з� �ڵ带 ������ ���ο� �������� ��û�Ѵ�.
					Iterator<String> iterator = codeAndName.keySet().iterator();

					while (iterator.hasNext()) {
						uri = new URIBuilder().setScheme("http").setHost("info.hansung.ac.kr")
								.setPath("/servlet/s_jik.jik_siganpyo_s_list").setParameter("year", yearArr[i])
								.setParameter("semester", j + "").setParameter("majorcode", iterator.next()).build();
						httpGet = new HttpGet(uri);

						// ��û�� ������.
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
		System.out.println("@Httpcompoent �����մϴ�.");
		return returnList;
	}

	public ArrayList<LectureVO> addMyLectures(CloseableHttpClient httpClient) {
		ArrayList<LectureVO> returnList = new ArrayList<LectureVO>();

		for (int i = 0; i < returnList.size(); i++) {
			System.out.println("����!!" + returnList.get(i));
		}
		try {
			HttpPost httpPost = new HttpPost();
			String uri = "http://info.hansung.ac.kr/jsp/suup_pyunga/suup_pyunga_result_h.jsp";
			httpPost.setURI(new URI(uri));

			// ���Ǹ����� �����ȸ �������� ��û�Ѵ�.
			CloseableHttpResponse response = httpClient.execute(httpPost);
			HttpEntity respEntity = response.getEntity();
			String content = EntityUtils.toString(respEntity);

			// �� �� �ִ� ������ �޾ƿ´�.
			String[] yearArr = Jericho.getYearAtPResult(content);

			// ������ �ݺ��ϴ� for��
			for (int i = 0; i < yearArr.length; i++) {
				// �б⸦ �ݺ��ϴ� for��
				for (int j = 1; j < 3; j++) {
					// ��û�� ���� ���� �غ�
					List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("hakneando", yearArr[i]));
					nameValuePairs.add(new BasicNameValuePair("hakgi", j + ""));
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
					// ��û
					response = httpClient.execute(httpPost);
					// ��� ����
					respEntity = response.getEntity();
					content = EntityUtils.toString(respEntity);

					// �� �� �ִ� ���Ǹ���� �޾ƿ´�.
					String[] lectureArr = Jericho.getLectureNumberListAtPResult(content);

					// 2015�г⵵ 2�б�� ���� �����򰡰� �����ϱ� �Ѿ���Ѵ�.
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
// System.out.println("������� �Խ��ϴ�.@@@@@");
// System.out.println(content);
// // �� �� �ִ� ������ �޾ƿ´�.
// String[] yearArr = jeri.getYear(content);
// for (int i = 0; i < yearArr.length; i++) {
// System.out.println("@httpcomponent �����Դϴ�." + yearArr[i]);
//
// for (int j = 1; j < 3; j++) {
// System.out.println("@httpcomponent �б��Դϴ�." + j);
// // ���б⸦ �����ϰ� ��û�Ѵ�.
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
// // �� �� �ִ� ���Ǹ���� �޾ƿ´�.
// String[] lectureArr = jeri.getLectureNumberList(content);
//
// // 2015�г⵵ 2�б�� ���� �����򰡰� �����ϱ� �Ѿ���Ѵ�.
// if (lectureArr == null)
// continue;
// for (int k = 0; k < lectureArr.length; k++) {
// System.out.println("@httpcomponent ���� :" + lectureArr[k]);
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

// // Header[] �迭�� Set-Cookie ������ŭ �����Ѵ�.
// Header[] headers2 = new Header[count];
// int count2 = 0;
// // ����ִ´�.
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
// System.out.println("������� �Խ��ϴ�.@@@@@");
// System.out.println(content2);

// private Jericho jeri = new Jericho();
// private CloseableHttpClient httpClient = HttpClients.createDefault();
// private HttpPost httpPost = new HttpPost();
// private HttpEntity respEntity;

// public HttpComponent() {
// try {
// /// ù��° ��û - ���̵� ��й�ȣ�� ������ �����Ѵ�.
// identify(httpClient);
//
// /// �ι�° ��û - �������� �������� ���� �� ������ �޾ƿ´�.
// workAtScorePage(httpClient);
// System.out.println();
// System.out.println();
// System.out.println("�ϷηջϷηջϷηջϷηջϷηջϷηջϷηջϷηջϷηջϷηջϷηջϷηջϷηջϷηջϷη�");
// System.out.println();
// System.out.println();
// /// ����° ��û - ���Ǹ����� �����ȸ �������� ���� ����,�б�,�����ڵ�,�й�,�����̸�,�������� �� �����´�.
// workAtLectureEvaluateResult(httpClient);
//
// } catch (Exception e) {
// e.printStackTrace();
// }
// }

// //���̵� ��й�ȣ�� ����, �����Ѵ�.
// public boolean identify(HansungVO hansung) {
// try{
// String id = hansung.getHansung_id();
// String pwd = hansung.getHansung_pw();
// String uri = "https://info.hansung.ac.kr/servlet/s_gong.gong_login_ssl";
//
// // ����Ʈ ��û�� �غ��Ѵ�.
// httpPost.setURI(new URI(uri));
// // �Ű������� �غ��Ѵ�.
// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
// nameValuePairs.add(new BasicNameValuePair("id", id));
// nameValuePairs.add(new BasicNameValuePair("passwd", pwd));
// // ����Ʈ ��û�� �Ű������� �ִ´�.
// UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nameValuePairs,
// Consts.UTF_8);
// httpPost.setEntity(entity);
// // ������ �õ��Ѵ�.
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

// // ������ȸ ���� ���������� ���� �����´�.
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
// // ���Ǹ����� ��� ��ȸ ���������� ���� �����´�.
// public void workAtLectureEvaluateResult(CloseableHttpClient httpClient)
// throws URISyntaxException, ClientProtocolException, IOException {
// String uri =
// // "https://info.hansung.ac.kr/jsp/suup_pyunga/suup_pyunga_result_h.jsp";
//
// // ���Ǹ����� �����ȸ �������� ��û�Ѵ�.
// httpPost.setURI(new URI(uri));
// CloseableHttpResponse response = httpClient.execute(httpPost);
// HttpEntity respEntity = response.getEntity();
// String content = EntityUtils.toString(respEntity);
//
// // �� �� �ִ� ������ �޾ƿ´�.
// String[] yearArr = jeri.getYear(content);
// for (int i = 0; i < yearArr.length; i++) {
// System.out.println("@httpcomponent �����Դϴ�." + yearArr[i]);
//
// for (int j = 1; j < 3; j++) {
// System.out.println("@httpcomponent �б��Դϴ�." + j);
// // ���б⸦ �����ϰ� ��û�Ѵ�.
// List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
// nameValuePairs.add(new BasicNameValuePair("hakneando", yearArr[i]));
// nameValuePairs.add(new BasicNameValuePair("hakgi", j + ""));
// httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, Consts.UTF_8));
// response = httpClient.execute(httpPost);
// respEntity = response.getEntity();
// content = EntityUtils.toString(respEntity);
//
// // �� �� �ִ� ���Ǹ���� �޾ƿ´�.
// String[] lectureArr = jeri.getLectureNumberList(content);
//
// // 2015�г⵵ 2�б�� ���� �����򰡰� �����ϱ� �Ѿ���Ѵ�.
// if (lectureArr == null)
// continue;
// for (int k = 0; k < lectureArr.length; k++) {
// System.out.println("@httpcomponent ���� :" + lectureArr[k]);
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
// // �� �� �ִ� �����ڵ带 �޾ƿ´�.
// // �����ڵ带 �ϳ� ����. ��û�Ѵ�.
// // ������ �̸�, ������ �޾ƿ´�.
// }
//
// }

/*
 * //ù��° ��û�� �������� �� / �α��� ��й�ȣ Ʋ���� �ȵ��ư��� �ٲ�� �� ��.. if (respEntity != null) {
 * //�ι�° ��û�� ���� �� - ���� �������� ����. HttpResponse response2 =
 * httpclient.execute(httppost2); HttpEntity respEntity2 =
 * response2.getEntity(); String content = EntityUtils.toString(respEntity);
 * String content2 = EntityUtils.toString(respEntity2); //System.out.println(
 * "###����Դϴ�. " + content + content2); // jeri.getTableDataTest(content2);
 * 
 * //����° ��û�� ���� �� - ���Ǹ����� �������� ����. ArrayList<NameValuePair> nameValuePairs3 =
 * new ArrayList<NameValuePair>(); nameValuePairs3.add(new
 * BasicNameValuePair("hakneando", "2013")); nameValuePairs3.add(new
 * BasicNameValuePair("hakgi", "2")); nameValuePairs3.add(new
 * BasicNameValuePair("gwamok", "ISE0012A"));
 * 
 * httppost3.setEntity(new UrlEncodedFormEntity(nameValuePairs3, HTTP.UTF_8));
 * HttpResponse response3 = httpclient.execute(httppost3); HttpEntity
 * respEntity3 = response3.getEntity(); String content3 =
 * EntityUtils.toString(respEntity3); // System.out.println( "###����Դϴ�. " +
 * content3);
 * 
 * //���� ������ �������� ����. //���� ������ ������ ��´�. //for�� //���� �ϳ��� ������ �������� ��û�Ѵ�. //����������
 * �����ڵ� ����� ��´�. //for�� //�����ڵ� �ϳ��� ���� �������� ��û�Ѵ�. //���������� ���Ǹ�, �й�, �����, ������, }
 */