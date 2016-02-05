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
		// ���� ��ȸ �������� ���̺��� �� �б�� 3��, �׸��� ���� ����� ǥ���ϱ� ���� 1�� �߰��� ���ȴ�.
		for (int i = 0; i < (tableList.size() - 1) / 3; i++) {
			// �⵵�� �б⸦ �޾ƿ��� �κ�
			table = tableList.get(i * 3);
			String time = table.getAllElements(HTMLElementName.TD).get(0).getTextExtractor().toString();
			System.out.print(time.substring(0, 4));
			System.out.println(" " + time.substring(9, 10));

			// ������ �����ڵ带 �޾ƿ��� �κ�
			table = tableList.get((i * 3) + 2);
			trList = table.getAllElements(HTMLElementName.TR);
			// ������ <table>�ȿ���, <tr>�±��� ������ŭ ���ư��� for��. ù���� �����̹Ƿ� ����
			for (int j = 1; j < trList.size(); j++) {
				tr = trList.get(j);
				tdList = tr.getAllElements(HTMLElementName.TD);
				System.out.println(tdList.get(0).getTextExtractor().toString() + "   "
						+ tdList.get(1).getTextExtractor().toString());
			}
		}
	}

	// ������ �����ȸ ���������� �����ִ� ������ �����´�.
	public static String[] getYearAtPResult(String str) {
		String[] returnArr;
		Source source;

		source = new Source(str);

		// 0�� �±�(index 0���� �ؼ� 2)�� ������ ����ִ�.
		Element select = source.getAllElements(HTMLElementName.SELECT).get(0);
		List<Element> optionList = select.getAllElements(HTMLElementName.OPTION);

		if (optionList.size() == 0) {
			System.out.println("@Jericho �� �� �ִ� �������� ������ �߻��߽��ϴ�.");
			return null;
		}

		returnArr = new String[optionList.size()];
		for (int i = 0; i < optionList.size(); i++) {
			returnArr[i] = optionList.get(i).getAttributeValue("value");
		}
		return returnArr;
	}

	// ������ �����ȸ ���������� ���ǹ�ȣ ����� �����´�.
	public static String[] getLectureNumberListAtPResult(String str) {
		String[] returnArr;
		Source source;
		source = new Source(str);

		// 2�� �±׿��� ���Ǹ���� ����ִ�.
		Element select = source.getAllElements(HTMLElementName.SELECT).get(2);
		List<Element> optionList = select.getAllElements(HTMLElementName.OPTION);

		if (optionList.size() == 0) {
			System.out.println("@Jericho ���ǹ�ȣ��� �ҷ����⿡�� �˻��� �� ����.");
			return null;
		}

		returnArr = new String[optionList.size()];
		for (int i = 0; i < optionList.size(); i++) {
			returnArr[i] = optionList.get(i).getAttributeValue("value");
		}
		return returnArr;
	}

	// ������ �����ȸ ���������� ������ �̸��� �޾ƿ´�.
	public static String getPNameAtPResult(String str) {
		Source source;
		source = new Source(str);

		Element table = source.getAllElements(HTMLElementName.TABLE).get(4);
		List<Element> trList = table.getAllElements(HTMLElementName.TR);

		String PName = trList.get(1).getAllElements(HTMLElementName.TD).get(0).getFirstElement(HTMLElementName.B)
				.getContent().toString().replaceAll(" ", "");

		return PName;
	}

	// ������ �����ȸ ���������� ���� ������ �޾ƿ´�.
	public static double getAverageGradeAtPResult(String str) {
		Source source;
		source = new Source(str);

		Element table = source.getAllElements(HTMLElementName.TABLE).get(4);
		List<Element> trList = table.getAllElements(HTMLElementName.TR);

		double AverageGrade = Double.parseDouble(trList.get(1).getAllElements(HTMLElementName.TD).get(1)
				.getFirstElement(HTMLElementName.B).getContent().toString());

		return AverageGrade;
	}

	// �ð�ǥ ��ȸ ���������� ������ ���´�.
	public static String[] getYearAtSiganpyoSelect(String str) {
		String[] reString = null;

		Source source;
		source = new Source(str);
		List<Element> selectList = source.getAllElements(HTMLElementName.SELECT);

		// 1��° �±׿��� ������ ����ִ�.
		List<Element> optionList = selectList.get(0).getAllElements(HTMLElementName.OPTION);
		// �� �� �ִ� ������ yearArr�� �����Ѵ�.
		reString = new String[optionList.size()];
		for (int i = 0; i < optionList.size(); i++) {
			reString[i] = optionList.get(i).getContent().toString();
			reString[i] = reString[i].trim();
		}

		return reString;
	}

	// �ð�ǥ ��ȸ ���������� �а��ڵ�,�а����� ���´�.
	public static HashMap<String, String> getMajorInfoAtSiganpyoSelect(String str) {
		HashMap<String, String> returnMap = new HashMap<String, String>();

		Source source;
		source = new Source(str);
		List<Element> selectList = source.getAllElements(HTMLElementName.SELECT);

		// 3��° �±׿��� �ڵ�� ������� ����ִ�. //�� ���忡�� ������ ���µ� �ֱ׷��� �𸣰ڴ� ����.
		List<Element> optionList = selectList.get(2).getAllElements(HTMLElementName.OPTION);

		// �� �� �ִ� �� �з��ڵ�� �� �з��̸��� �����Ѵ�.
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

	// �ð�ǥ ���������� �پ��� ���� �޾ƿɴϴ�.
	public static ArrayList<LectureVO> getManyInfoAtSinganpyo(String str, String year, String semester) {
		ArrayList<LectureVO> ReturnList = new ArrayList<LectureVO>();

		String s_code = null; // ���� �ڵ�
		String s_name = null; // �����
		String g_unit = null; // ���� ����
		String c_division = null;// �й�
		String p_name = null; // ������
		String s_type = null; // ���� Ÿ�� , ����, ����, ���..
		int p_number; // ���

		Source source;
		source = new Source(str);
		List<Element> tableList = source.getAllElements(HTMLElementName.TABLE);

		List<Element> TRList = tableList.get(4).getAllElements(HTMLElementName.TR);

		// TRList�� ����� 0�̳� 1�̶��,���� ������ ������� ���� ���̴�.
		if (TRList.size() < 2)
			return null;

		for (int i = 1; i < TRList.size(); i++) {
			Element TR = TRList.get(i);
			List<Element> TDList = TR.getAllElements(HTMLElementName.TD);

			c_division = TDList.get(5).getContent().toString().trim();
			// �й��� �����۸�ũ�� �������Ƿ�, ���� 20���� ��� ��ȿ�� ������ �ν��Ѵ�.
			if (c_division.length() > 20) {
				// �����ڵ尡 7���ڸ� ��ȿ�� ������ �ν��Ѵ�.
				if (TDList.get(0).getContent().toString().trim().length() == 7) {
					s_code = TDList.get(0).getContent().toString().trim(); // �����ڵ�
																			// ����
																			// �޾ƿ´�.
					s_name = TDList.get(1).getContent().toString().trim(); // �����
					s_type = TDList.get(3).getContent().toString().trim(); // ����
																			// Ÿ��
																			// ,
																			// ����,
																			// ����,
																			// ���..
					g_unit = TDList.get(4).getContent().toString().trim(); // ����
																			// ����
				}
				p_number = Integer.parseInt(
						TDList.get(5).getFirstElement(HTMLElementName.A).getAttributeValue("href").substring(34, 40)); // �����
																														// �޾ƿ´�.
				c_division = TDList.get(5).getFirstElement(HTMLElementName.A).getContent().toString().trim(); // �����۸�ũ
																												// �±׸�
																												// ������
																												// �йݰ���
																												// �޾ƿ´�.
				p_name = TDList.get(8).getContent().toString().replaceAll(" ", ""); // ������

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