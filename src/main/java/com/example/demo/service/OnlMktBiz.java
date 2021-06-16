package com.example.demo.service;

/**
 * 통신판매사업자 도메인 클래스
 * @author paten
 *
 */
public class OnlMktBiz {
	private String regno;
	private String orgname;
	private String bizname;
	private String bizregno;
	private String crp_prv_cd;
	private String ceoname;
	private String tel;
	private String email;
	private String regdate;
	private String address;
	private String address_road;
	private String status;
	private String orgtel;
	private String salestype;
	private String product;
	private String domain;
	private String hostlocation;
	
	public OnlMktBiz(String regno, String orgname, String bizname, String bizregno, String crp_prv_cd,
			String ceoname, String tel, String email, String regdate, String address, String address_road,
			String status, String orgtel, String salestype, String product, String domain, String hostlocation) {
		super();
		this.regno = regno;
		this.orgname = orgname;
		this.bizname = bizname;
		this.bizregno = bizregno;
		this.crp_prv_cd = crp_prv_cd;
		this.ceoname = ceoname;
		this.tel = tel;
		this.email = email;
		this.regdate = regdate;
		this.address = address;
		this.address_road = address_road;
		this.status = status;
		this.orgtel = orgtel;
		this.salestype = salestype;
		this.product = product;
		this.domain = domain;
		this.hostlocation = hostlocation;
	}

	public OnlMktBiz(String raw) {

		String[] arr = raw.split(",", 17);
		this.regno = arr[0];
		this.orgname = arr[1];
		this.bizname = arr[2];
		this.bizregno = (arr[3] != null) ? arr[3].replace("-", "") : null;
		this.crp_prv_cd = arr[4];
		this.ceoname = arr[5];
		this.tel = (arr[6] != null) ? arr[6].replace("-", "") : null;
		this.email = arr[7];
		this.regdate = (arr[8] != null) ? arr[8].replace("-", "") : null;
		this.address = arr[9];
		this.address_road = arr[10];
		this.status = arr[11];
		this.orgtel = (arr[12] != null) ? arr[12].replace("-", "") : null;
		this.salestype = arr[13];
		this.product = arr[14];
		this.domain = arr[15];
		this.hostlocation = arr[16];
	}

	public String getRegno() {
		return regno;
	}

	public void setRegno(String regno) {
		this.regno = regno;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getBizname() {
		return bizname;
	}

	public void setBizname(String bizname) {
		this.bizname = bizname;
	}

	public String getBizregno() {
		return bizregno;
	}

	public void setBizregno(String bizregno) {
		this.bizregno = bizregno;
	}

	public String getCrp_prv_cd() {
		return crp_prv_cd;
	}

	public void setCrp_prv_cd(String crp_prv_cd) {
		this.crp_prv_cd = crp_prv_cd;
	}

	public String getCeoname() {
		return ceoname;
	}

	public void setCeoname(String ceoname) {
		this.ceoname = ceoname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress_road() {
		return address_road;
	}

	public void setAddress_road(String address_road) {
		this.address_road = address_road;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrgtel() {
		return orgtel;
	}

	public void setOrgtel(String orgtel) {
		this.orgtel = orgtel;
	}

	public String getSalestype() {
		return salestype;
	}

	public void setSalestype(String salestype) {
		this.salestype = salestype;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getHostlocation() {
		return hostlocation;
	}

	public void setHostlocation(String hostlocation) {
		this.hostlocation = hostlocation;
	}

	@Override
	public String toString() {
		return "OnlineMktBiz [regno=" + regno + ", orgname=" + orgname + ", bizname=" + bizname + ", bizregno="
				+ bizregno + ", crp_prv_cd=" + crp_prv_cd + ", ceoname=" + ceoname + ", tel=" + tel + ", email=" + email
				+ ", regdate=" + regdate + ", address=" + address + ", address_road=" + address_road + ", status="
				+ status + ", orgtel=" + orgtel + ", salestype=" + salestype + ", product=" + product + ", domain="
				+ domain + ", hostlocation=" + hostlocation + "]";
	}

}
