package ac.hansung.spring.vo;

public class EVLectureVO {
	private LectureVO lectureVO;
	private int sequence_num; // ½ÃÄö½º ¹øÈ£
	private String member_id; // ¸â¹ö ID
	private double passion;
	private double communication;
	private double fairness;
	private double benefit;
	private String opinion;
	private String write_date;

	public EVLectureVO() {
		super();
	}

	public EVLectureVO(int sequence_num, String member_id, double passion,
			double communication, double fairness, double benefit,
			String opinion, String write_date) {
		super();
		this.sequence_num = sequence_num;
		this.member_id = member_id;
		this.passion = passion;
		this.communication = communication;
		this.fairness = fairness;
		this.benefit = benefit;
		this.opinion = opinion;
		this.write_date = write_date;
	}
	
	
	public LectureVO getLectureVO() {
		return lectureVO;
	}

	public void setLectureVO(LectureVO lectureVO) {
		this.lectureVO = lectureVO;
	}

	public int getSequence_num() {
		return sequence_num;
	}

	public void setSequence_num(int sequence_num) {
		this.sequence_num = sequence_num;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public double getPassion() {
		return passion;
	}

	public void setPassion(double passion) {
		this.passion = passion;
	}

	public double getCommunication() {
		return communication;
	}

	public void setCommunication(double communication) {
		this.communication = communication;
	}

	public double getFairness() {
		return fairness;
	}

	public void setFairness(double fairness) {
		this.fairness = fairness;
	}

	public double getBenefit() {
		return benefit;
	}

	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	@Override
	public String toString() {
		return "EVLectureVO [sequence_num=" + sequence_num + ", member_id="
				+ member_id + ", passion=" + passion + ", communication="
				+ communication + ", fairness=" + fairness + ", benefit="
				+ benefit + ", opinion=" + opinion + ", write_date="
				+ write_date + "]";
	}

}
	
 