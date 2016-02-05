package ac.hansung.spring.vo;

public class HansungVO {

	private String hansung_id;
	private String hansung_pw;
	
	public HansungVO() {
		
	}

	public HansungVO(String hansung_id, String hansung_pw) {
		super();
		this.hansung_id = hansung_id;
		this.hansung_pw = hansung_pw;
	}

	public String getHansung_id() {
		return hansung_id;
	}

	public void setHansung_id(String hansung_id) {
		this.hansung_id = hansung_id;
	}

	public String getHansung_pw() {
		return hansung_pw;
	}

	public void setHansung_pw(String hansung_pw) {
		this.hansung_pw = hansung_pw;
	}

	@Override
	public String toString() {
		return "HansungVO [hansung_id=" + hansung_id + ", hansung_pw=" + hansung_pw + "]";
	}
	
}
