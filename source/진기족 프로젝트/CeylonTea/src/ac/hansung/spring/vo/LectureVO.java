package ac.hansung.spring.vo;

public class LectureVO {
	private int sequence_num; // ������ ��ȣ
	private String s_code; // �����ڵ�
	private String s_name; // �����̸�
	private String s_type; // ����(����,����,����)
	private int year; // �⵵
	private int semester;// �б�
	private String p_name; // ������
	private String g_unit; // ����
	private String c_division;// �й�(A�� B�� O��)
	
	
	private double avg_evaluation;// �Ѽ����б� ���� ��� ����
	private int p_number; // ���� ��ȣ
	private double avg_passion; //����
	private double avg_communication; //����
	private double avg_fairness;//����
	private double avg_benefit; //�̵�
	private double eval_count; //�� ����


	public LectureVO() {
		super();
	}
	
	//�����ڴ� �ݵ�� ������ ���ԵǾ���� - ��������
	//���� ������ ��� ��ȸ���� ������ ���� ���� ��ü 
	public LectureVO(String s_code, String c_division, int year, int semester, String p_name,
			double avg_evaluation) {
		super();
		this.s_code = s_code;
		this.c_division = c_division;
		this.year = year;
		this.semester = semester;
		this.p_name = p_name;
		this.avg_evaluation = avg_evaluation;
	}


	// ��� ���� �������⿡�� �����
	public LectureVO(String s_code, String s_name, String s_type, int year,
			int semester, String p_name, String g_unit, String c_division) {
		super();
		this.s_code = s_code;
		this.s_name = s_name;
		this.s_type = s_type;
		this.year = year;
		this.semester = semester;
		this.p_name = p_name;
		this.g_unit = g_unit;
		this.c_division = c_division;
	}

	public LectureVO(int sequence_num, String s_code, String s_name,
			String s_type, int year, int semester, String p_name,
			String g_unit, String c_division, double avg_evaluation,
			int p_number) {
		super();
		this.sequence_num = sequence_num;
		this.s_code = s_code;
		this.s_name = s_name;
		this.s_type = s_type;
		this.year = year;
		this.semester = semester;
		this.p_name = p_name;
		this.g_unit = g_unit;
		this.c_division = c_division;
		this.avg_evaluation = avg_evaluation;
		this.p_number = p_number;
	}

	//�ð�ǥ��ȸ �� ���ǰ�ȹ�� ��ȸ ���������� ���� �����ö� ����ϴ� ������
	public LectureVO(String s_code, String s_name, String s_type, int year,
			int semester, String p_name, String g_unit, String c_division,
			int p_number) {
		super();
		this.s_code = s_code;
		this.s_name = s_name;
		this.s_type = s_type;
		this.year = year;
		this.semester = semester;
		this.p_name = p_name;
		this.g_unit = g_unit;
		this.c_division = c_division;
		this.p_number = p_number;
	}

	// db������ ��ġ
	public LectureVO(int sequence_num, String s_code, String s_name, int year,
			int semester, String p_name, String g_unit, String c_division,
			String s_type) {
		super();
		this.sequence_num = sequence_num;
		this.s_code = s_code;
		this.s_name = s_name;
		this.s_type = s_type;
		this.year = year;
		this.semester = semester;
		this.p_name = p_name;
		this.g_unit = g_unit;
		this.c_division = c_division;
	}

	public LectureVO(int sequence_num, String s_code, String s_name,
			String s_type, int year, int semester, String p_name,
			String g_unit, String c_division, double avg_evaluation) {
		super();
		this.sequence_num = sequence_num;
		this.s_code = s_code;
		this.s_name = s_name;
		this.s_type = s_type;
		this.year = year;
		this.semester = semester;
		this.p_name = p_name;
		this.g_unit = g_unit;
		this.c_division = c_division;
		this.avg_evaluation = avg_evaluation;
	}

	public LectureVO(String s_name, int year, int semester, String p_name) {
		super();
		this.s_name = s_name;
		this.year = year;
		this.semester = semester;
		this.p_name = p_name;
	}

	public double getEval_count() {
		return eval_count;
	}

	public void setEval_count(double eval_count) {
		this.eval_count = eval_count;
	}

	public double getAvg_passion() {
		return avg_passion;
	}

	public void setAvg_passion(double avg_passion) {
		this.avg_passion = avg_passion;
	}

	public double getAvg_communication() {
		return avg_communication;
	}

	public void setAvg_communication(double avg_communication) {
		this.avg_communication = avg_communication;
	}

	public double getAvg_fairness() {
		return avg_fairness;
	}

	public void setAvg_fairness(double avg_fairness) {
		this.avg_fairness = avg_fairness;
	}

	public double getAvg_benefit() {
		return avg_benefit;
	}

	public void setAvg_benefit(double avg_benefit) {
		this.avg_benefit = avg_benefit;
	}
	public int getSequence_num() {
		return sequence_num;
	}

	public void setSequence_num(int sequence_num) {
		this.sequence_num = sequence_num;
	}

	public String getS_code() {
		return s_code;
	}

	public void setS_code(String s_code) {
		this.s_code = s_code;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getS_type() {
		return s_type;
	}

	public void setS_type(String s_type) {
		this.s_type = s_type;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getG_unit() {
		return g_unit;
	}

	public void setG_unit(String g_unit) {
		this.g_unit = g_unit;
	}

	public String getC_division() {
		return c_division;
	}

	public void setC_division(String c_division) {
		this.c_division = c_division;
	}

	public double getAvg_evaluation() {
		return avg_evaluation;
	}

	public void setAvg_evaluation(double avg_evaluation) {
		this.avg_evaluation = avg_evaluation;
	}

	public int getP_number() {
		return p_number;
	}

	public void setP_number(int p_number) {
		this.p_number = p_number;
	}

	@Override
	public String toString() {
		return "LectureVO [sequence_num=" + sequence_num + ", s_code=" + s_code + ", s_name=" + s_name + ", s_type="
				+ s_type + ", year=" + year + ", semester=" + semester + ", p_name=" + p_name + ", g_unit=" + g_unit
				+ ", c_division=" + c_division + ", avg_evaluation=" + avg_evaluation + ", p_number=" + p_number
				+ ", avg_passion=" + avg_passion + ", avg_communication=" + avg_communication + ", avg_fairness="
				+ avg_fairness + ", avg_benefit=" + avg_benefit + "]";
	}

}