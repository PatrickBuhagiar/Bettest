package com.bettest.site.bt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.bettest.site.bt.dao.business.CardDAO;
import com.bettest.site.bt.model.Card;

public class ImplCardDAO implements CardDAO{
	private DataSource dataSource;

    public ImplCardDAO(){
		
	}
    
	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
	}
	
	
	public int create(Card c) throws ParseException {
		//Add Card
		String card_sql = "INSERT INTO `bettest`.`tbl_card` "
				+ "(`card_id`,`card_type_name`,`card_holder_name`,`card_number`,`card_exp_date`,`card_cvv`) "
				+ "VALUES (NULL,'" + c.getCard_type_name() + "','" + c.getCard_holder_name() + "'," + 
				c.getCard_number() + ",'" +  c.getCard_exp_date() + "'," +  c.getCard_cvv() +")";
		Connection card_conn = null;
		ResultSet genIDs;
		try {
			card_conn = dataSource.getConnection();
			PreparedStatement ps = card_conn.prepareStatement(card_sql, Statement.RETURN_GENERATED_KEYS);
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
			if (card_conn != null) {
				try {
					card_conn.close();
				} catch (SQLException e) {}
			}
		}		
	}
	
    /*
    public Card read(int id) {
            String sql = "SELECT * FROM `bettest`.`tbl_card` WHERE `card_id` ="+ id;
            Connection conn = null;
           
            try {
                    conn = dataSource.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    Card card = null;
                    ResultSet rs = ps.executeQuery();
                    if(rs.next()) {
                            card = new Card(rs.getInt("card_id"), rs.getInt("card_cvv"),
                                            rs.getLong("card_number"), rs.getString("card_type_name"), rs.getString("card_holder_name"), rs.getDate("card_exp_date"));
                    }
                    rs.close();
                    ps.close();
                    return card;
            } catch (SQLException e) {
                    throw new RuntimeException(e);
            } finally {
                    if (conn != null) {
                            try {
                                    conn.close();
                            } catch(SQLException e){}
                    }
            }
    }*/
   
    /*
    public List<Card> readAll() {
            String sql;
            Connection conn = null;
            Statement stmt = null;
            ArrayList<Card> res = new ArrayList<Card>();
            try{
                    sql = "SELECT * FROM `tbl_card`";
                    conn = dataSource.getConnection();
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    while(rs.next()){
                            Card c = new Card(rs.getInt("card_id"), rs.getInt("card_cvv"),
                                            rs.getLong("card_number"), rs.getString("card_type_name"),
                                            rs.getString("card_holder_name"), rs.getDate("card_exp_date"));
                            res.add(c);
                    }
            } catch(Exception e){
                    e.printStackTrace();
            } finally {
                    if (conn != null) {
                            try {
                                    conn.close();
                            } catch(SQLException e){}
                    }              
            }
            return res;
           
    }
   
    public void update(Card c) {
            String sql = "";
            Connection conn = null;
            try {
                    sql = "UPDATE `tbl_card` SET `card_id`=" + c.getCard_id() + ",`card_type_name`='" + c.getCard_type_name() + "',`card_holder_name`='" +
                    c.getCard_holder_name() + "',`card_number`=" + c.getCard_number() + ",`card_exp_date`='" + c.getCard_exp_date() + "', `card_cvv`=" +
                                    c.getCard_cvv() +" WHERE `card_id` = " + c.getCard_id();
                    conn = dataSource.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.executeUpdate();
                    ps.close();
            } catch (SQLException e) {
                    throw new RuntimeException(e);

            } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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
                    sql = "DELETE FROM `tbl_card` WHERE `card_id` = " + id;
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
}