package com.hoangdv.variable;


public class Lady {
	private String LINK;
	private String LINK_IMG;
	private String TITLE;
	private String VIEW;
	private String CMT;
	private String LIKE;


	public Lady(String lINK, String lINK_IMG, String tITLE, String vIEW,
			String cMT, String lIKE) {
		super();
		LINK = lINK;
		LINK_IMG = lINK_IMG;
		TITLE = tITLE;
		VIEW = vIEW;
		CMT = cMT;
		LIKE = lIKE;
	}

	public Lady() {
		super();
		this.LIKE = "0";
		this.CMT = "0";
		this.VIEW = "0";
	}

	public String getLINK() {
		return LINK;
	}

	public void setLINK(String lINK) {
		LINK = lINK;
	}

	public String getLINK_IMG() {
		return LINK_IMG;
	}

	public void setLINK_IMG(String lINK_IMG) {
		LINK_IMG = lINK_IMG;
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	public String getVIEW() {
		return VIEW;
	}

	public void setVIEW(String vIEW) {
		VIEW = vIEW;
	}

	public String getCMT() {
		return CMT;
	}

	public void setCMT(String cMT) {
		CMT = cMT;
	}

	public String getLIKE() {
		return LIKE;
	}

	public void setLIKE(String lIKE) {
		LIKE = lIKE;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getTITLE();
	}

}
