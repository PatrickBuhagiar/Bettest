package com.bettest.site.bt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.bettest.site.bt.dao.business.BetDAO;
import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.User;

public class ImplBetDAO implements BetDAO {
	private DataSource dataSource;
	public ImplBetDAO(){
		
	}
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	
	public int create(Bet b) {
		String sql = "";
		Connection conn = null;
		ResultSet genIDs;
		try {
			sql = "INSERT INTO `tbl_bet`(`bet_id`, `bet_user_id`, `bet_amount`, `bet_risk_level`, `bet_timestamp`)";
			sql += " VALUES (NULL," + b.getBet_user_id() + "," + b.getBet_amount() + "," + b.getBet_risk_level() + ",'" + b.getBet_timestamp() + "')";
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.executeUpdate();
			genIDs = ps.getGeneratedKeys();
			int result;
			if(genIDs.next() && genIDs != null){
				   result = genIDs.getInt(1);
			} else {
				   result = -1;
			}
			ps.close();
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	/*
	public Bet read(int id) {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		try{
			sql = "SELECT * FROM `tbl_bet` WHERE `bet_id` = " + id;
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Bet b = new Bet();
				b.setBet_id(rs.getInt("bet_id"));
				b.setBet_user_id(rs.getInt("bet_user_id"));
				b.setBet_timestamp(rs.getTimestamp("bet_timestamp"));
				b.setBet_risk_level(rs.getInt("bet_risk_level"));
				b.setBet_amount(rs.getFloat("bet_amount"));
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {}
				}
				return b;
			}
			return null;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	
	public List<Bet> readAll() {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Bet> res = new ArrayList<Bet>();
		try{
			sql = "SELECT * FROM `tbl_bet`";
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				Bet b = new Bet();
				b.setBet_id(rs.getInt("bet_id"));
				b.setBet_user_id(rs.getInt("bet_user_id"));
				b.setBet_timestamp(rs.getTimestamp("bet_timestamp"));
				b.setBet_risk_level(rs.getInt("bet_risk_level"));
				b.setBet_amount(rs.getFloat("bet_amount"));
				res.add(b);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return res;
	}*/
		
	/*
	public void update(Bet u) {
		String sql = "";
		Connection conn = null;
		try {
			sql = "UPDATE `tbl_bet` SET `bet_id`=" + u.getBet_id() + ",`bet_user_id`=" + u.getBet_user_id() + ",`bet_amount`=" + 
			u.getBet_amount() + ",`bet_risk_level`=" + u.getBet_risk_level() + ",`bet_timestamp`='" + u.getBet_timestamp().toString() + "' WHERE `bet_id` = " + u.getBet_id();
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	
	public void delete(int id) {
		String sql = "";
		Connection conn = null;
		try {
			sql = "DELETE FROM `tbl_bet` WHERE `bet_id` = " + id;
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
 
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	*/
	public List<Bet> readByUserId(int id) {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Bet> res = new ArrayList<Bet>();
		try{
			sql = "SELECT * FROM `tbl_bet`";
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				if(rs.getInt("bet_user_id") == id){
					Bet b = new Bet();
					b.setBet_amount(rs.getFloat("bet_amount"));
					b.setBet_id(rs.getInt("bet_id"));
					b.setBet_risk_level(rs.getInt("bet_risk_level"));
					b.setBet_timestamp(rs.getTimestamp("bet_timestamp"));
					b.setBet_user_id(rs.getInt("bet_user_id"));
					res.add(b);
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return res;
	}

	
	public long getCountByUserId(int id) {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Bet> res = new ArrayList<Bet>();
		long total = 0;
		try{
			sql = "SELECT COUNT(*) FROM `tbl_bet` WHERE `bet_user_id` =" + id;
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
					total = total + rs.getLong(1);
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return total;
	}

	
	public float getUserTotalBetted(int id) {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Bet> res = new ArrayList<Bet>();
		float total = 0;
		try{
			sql = "SELECT * FROM `tbl_bet` WHERE `bet_user_id` =" + id;
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
					total = total + rs.getFloat("bet_amount");
			}
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
		}
		return total;
	}

}
