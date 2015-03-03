package com.bettest.site.bt.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;






import java.sql.Statement;

import com.bettest.site.bt.dao.business.UserDAO;
import com.bettest.site.bt.model.Bet;
import com.bettest.site.bt.model.User;
import com.bettest.site.bt.util.Validation;

public class ImplUserDAO implements UserDAO {
	private DataSource dataSource;
	private DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
	Validation v = new Validation();
	public ImplUserDAO(){
		
	}
	
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}

	public int create(User u) {		
		// Add User
		String user_sql = "INSERT INTO `bettest`.`tbl_users` "
				+ "(`user_id`, `user_user_type_id`, `user_username`, `user_password`, `user_email`, `user_name`,"
				+ " `user_surname`, `user_dob`, `user_address`, `user_city`, `user_country`, `user_postcode`, `user_card_id`)"
				+ "VALUES (NULL, " + u.getUser_type_id() + ", '"+ u.getUser_username() +"', '"+ u.getUser_password() + "', '" + u.getUser_email()
				+ "', '"+ u.getUser_name() + "', '"+ u.getUser_surname() + "', '"+ df.format(u.getUser_dob()) + "', '" + u.getUser_address() +"', '"+ u.getUser_city() +"', '"
				+ u.getUser_country() + "', '"+ u.getUser_postcode() + "', " + u.getUser_card_id() +")";
		Connection user_conn = null;
		ResultSet genIDs;
		try {
			user_conn = dataSource.getConnection();
			PreparedStatement ps = user_conn.prepareStatement(user_sql, Statement.RETURN_GENERATED_KEYS);
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
			if (user_conn != null) {
				try {
					user_conn.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	public User read(int id) {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		try{
			sql = "SELECT * FROM `tbl_users` WHERE `user_id` = " + id;
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				User u = new User();
				u.setUser_address(rs.getString("user_address"));
				u.setUser_card_id(rs.getInt("user_card_id"));
				u.setUser_city(rs.getString("user_city"));
				u.setUser_country(rs.getString("user_country"));
				u.setUser_id(rs.getInt("user_id"));
				u.setUser_name(rs.getString("user_name"));
				u.setUser_password(rs.getString("user_password"));
				u.setUser_email(rs.getString("user_email"));
				u.setUser_postcode(rs.getString("user_postcode"));
				u.setUser_surname(rs.getString("user_surname"));
				u.setUser_dob(rs.getDate("user_dob"));
				u.setUser_type_id(rs.getInt("user_user_type_id"));
				u.setUser_username(rs.getString("user_username"));
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {}
				}
				return u;
			}
			return null;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public User find_username(String username) {
		if (!v.validate_empty(username)){
			return null;
		}
		
		String sql;
		Connection conn = null;
		Statement stmt = null;
		try{
			System.out.println("Entered sql creation of username");
			sql = "SELECT * FROM `tbl_users` WHERE `user_username` = '" + username +"'";
			try {
				conn = dataSource.getConnection();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("failed get connection");
				e1.printStackTrace();
			}
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				User u = new User();
				u.setUser_address(rs.getString("user_address"));
				u.setUser_card_id(rs.getInt("user_card_id"));
				u.setUser_city(rs.getString("user_city"));
				u.setUser_country(rs.getString("user_country"));
				u.setUser_id(rs.getInt("user_id"));
				u.setUser_name(rs.getString("user_name"));
				u.setUser_password(rs.getString("user_password"));
				u.setUser_email(rs.getString("user_email"));
				u.setUser_postcode(rs.getString("user_postcode"));
				u.setUser_surname(rs.getString("user_surname"));
				u.setUser_dob(rs.getDate("user_dob"));
				u.setUser_type_id(rs.getInt("user_user_type_id"));
				u.setUser_username(rs.getString("user_username"));
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {System.out.println("failed sql");}
				}
				return u;
			}
			return null;
		} catch(Exception e){
			System.out.println("failed a");
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * @param mode 1 to Flush Bets only, 2 to Flush all data.
	 */
	public void flushUser(String un, boolean mode){
		String sql = "";
		Connection conn = null;
		Statement stmt = null;
		try {
			User u = find_username(un);
			if(u != null){
				conn = dataSource.getConnection();
				PreparedStatement ps;
				String flush_bet, flush_user, flush_card;
				flush_bet = "DELETE FROM `tbl_bet` WHERE `tbl_bet`.bet_user_id = " + u.getUser_id() + ";";
				ps = conn.prepareStatement(flush_bet);
				ps.executeUpdate();
				if(mode == true){
					ps.executeUpdate();
					flush_user = "DELETE FROM `tbl_users` WHERE `tbl_users`.user_id = " + u.getUser_id() + ";";
					ps = conn.prepareStatement(flush_user);
					ps.executeUpdate();
					flush_card = "DELETE FROM `tbl_card` WHERE `tbl_card`.card_id = " + u.getUser_card_id() + ";";
					ps = conn.prepareStatement(flush_card);
					ps.executeUpdate();
				}
				ps.close();
			}
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
	public List<User> readAll() {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		ArrayList<User> res = new ArrayList<User>();
		try{
			sql = "SELECT * FROM `tbl_users`";
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				User u = new User();
				u.setUser_address(rs.getString("user_address"));
				u.setUser_card_id(rs.getInt("user_card_id"));
				u.setUser_city(rs.getString("user_city"));
				u.setUser_country(rs.getString("user_country"));
				u.setUser_id(rs.getInt("user_id"));
				u.setUser_name(rs.getString("user_name"));
				u.setUser_password(rs.getString("user_password"));
				u.setUser_email(rs.getString("user_email"));
				u.setUser_postcode(rs.getString("user_postcode"));
				u.setUser_surname(rs.getString("user_surname"));
				u.setUser_dob(rs.getDate("user_dob"));
				u.setUser_type_id(rs.getInt("user_user_type_id"));
				u.setUser_username(rs.getString("user_city"));
				res.add(u);
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
	*/
	
	public User readByLogin(String un, String pw) {
		String sql;
		Connection conn = null;
		Statement stmt = null;
		try{
			sql = "SELECT * FROM `tbl_users` WHERE `user_username` = '" + un + "' AND `user_password` = '" + pw + "'";
			conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			User u;
			while(rs.next()){
				u = new User();
				u.setUser_address(rs.getString("user_address"));
				u.setUser_card_id(rs.getInt("user_card_id"));
				u.setUser_city(rs.getString("user_city"));
				u.setUser_country(rs.getString("user_country"));
				u.setUser_id(rs.getInt("user_id"));
				u.setUser_name(rs.getString("user_name"));
				u.setUser_password(rs.getString("user_password"));
				u.setUser_email(rs.getString("user_email"));
				u.setUser_postcode(rs.getString("user_postcode"));
				u.setUser_surname(rs.getString("user_surname"));
				u.setUser_dob(rs.getDate("user_dob"));
				u.setUser_type_id(rs.getInt("user_user_type_id"));
				u.setUser_username(rs.getString("user_username"));
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {}
				}
				return u;
			}
			return null;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	public void update(User u) {
		String sql = "";
		Connection conn = null;
		try {
			sql = "UPDATE `tbl_users` SET `user_id`=" + u.getUser_id() + ",`user_user_type_id`=" + u.getUser_type_id()
					+ ",`user_username`='" + u.getUser_username() + "',`user_password`='" + u.getUser_password()
					+ "',`user_email`='" + u.getUser_email() + "',`user_name`='" + u.getUser_name() + "',`user_surname`='" + u.getUser_surname()
					+ "',`user_dob`='" + df.format(u.getUser_dob()) + "',`user_address`='" + u.getUser_address() + "',`user_city`='" + u.getUser_city()
					+ "',`user_country`='" + u.getUser_country() + "',`user_postcode`='" + u.getUser_postcode()
					+ "',`user_card_id`=" + u.getUser_card_id()
					+ " WHERE `user_id` = " + u.getUser_id();
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
			sql = "DELETE FROM `tbl_users` WHERE `user_id` = " + id;
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
	}*/
}
