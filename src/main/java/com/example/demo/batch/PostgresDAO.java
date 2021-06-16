package com.example.demo.batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.batch.OnlMktBiz;

/**
 * PostgreSQL용 단순 JDBC DAO 클래스
 *
 */
public class PostgresDAO {

	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = (Connection) DriverManager.getConnection(
					"jdbc:postgresql://database-kcdcb-1.czd3xnww0nlt.ap-northeast-2.rds.amazonaws.com:5432/kcdcb",
					"", "");
		} catch (Exception e) {
			System.out.println(e);
		}
		return conn;
	}

	/**
	 * List 파라미터를 받아서 
	 * onl_mkt_biz_raw 테이블에 배치처리로 저장함
	 * 
	 * @param list
	 */
	public void insertOnlMktBizList(List<OnlMktBiz> list) {
		try (Connection conn = this.getConnection()) {
			String sql = "insert into onl_mkt_biz_raw (regno,orgname,bizname,bizregno,crp_prv_cd,ceoname,tel,email,regdate,address,"
					+ "address_road,status,orgtel,salestype,product,domain,hostlocation) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);

			for (OnlMktBiz o : list) {
				pstmt.setString(1, o.getRegno());
				pstmt.setString(2, o.getOrgname());
				pstmt.setString(3, o.getBizname());
				pstmt.setString(4, o.getBizregno());
				pstmt.setString(5, o.getCrp_prv_cd());
				pstmt.setString(6, o.getCeoname());
				pstmt.setString(7, o.getTel());
				pstmt.setString(8, o.getEmail());
				pstmt.setString(9, o.getRegdate());
				pstmt.setString(10, o.getAddress());
				pstmt.setString(11, o.getAddress_road());
				pstmt.setString(12, o.getStatus());
				pstmt.setString(13, o.getOrgtel());
				pstmt.setString(14, o.getSalestype());
				pstmt.setString(15, o.getProduct());
				pstmt.setString(16, o.getDomain());
				pstmt.setString(17, o.getHostlocation());
				pstmt.addBatch();
			}
			pstmt.executeBatch();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<OnlMktBiz> selectOnlMktBizs(String _bizregno) {

		List<OnlMktBiz> result = new ArrayList<>();
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = this.getConnection();
			String sql = "select regno,orgname,bizname,bizregno,crp_prv_cd,ceoname,tel,email,regdate,address,"
					+ "address_road,status,orgtel,salestype,product,domain,hostlocation from onl_mkt_biz_mst "
					+ "where bizregno > ? order by bizregno limit 100000";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, _bizregno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String regno = rs.getString("regno");
				String orgname = rs.getString("orgname");
				String bizname = rs.getString("bizname");
				String bizregno = rs.getString("bizregno");
				String crp_prv_cd = rs.getString("crp_prv_cd");
				String ceoname = rs.getString("ceoname");
				String tel = rs.getString("tel");
				String email = rs.getString("email");
				String regdate = rs.getString("regdate");
				String address = rs.getString("address");
				String address_road = rs.getString("address_road");
				String status = rs.getString("status");
				String orgtel = rs.getString("orgtel");
				String salestype = rs.getString("salestype");
				String product = rs.getString("product");
				String domain = rs.getString("domain");
				String hostlocation = rs.getString("hostlocation");
				OnlMktBiz o = new OnlMktBiz(regno, orgname, bizname, bizregno, crp_prv_cd, ceoname, tel, email, regdate,
						address, address_road, status, orgtel, salestype, product, domain, hostlocation);
				result.add(o);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}