package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.ufo.model.AnnoAvvistamenti;
import it.polito.tdp.ufo.model.Sighting;
import it.polito.tdp.ufo.model.StatiSuccessivi;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Sighting(res.getInt("id"),
							res.getTimestamp("datetime").toLocalDateTime(),
							res.getString("city"), 
							res.getString("state"), 
							res.getString("country"),
							res.getString("shape"),
							res.getInt("duration"),
							res.getString("duration_hm"),
							res.getString("comments"),
							res.getDate("date_posted").toLocalDate(),
							res.getDouble("latitude"), 
							res.getDouble("longitude"))) ;
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<AnnoAvvistamenti> getAnniEAvvistamenti() {
		String sql = "SELECT YEAR(datetime) AS anno, COUNT(id) AS avvistamenti " + 
				"FROM sighting " + 
				"WHERE country='us' " + 
				"GROUP BY anno";
		List<AnnoAvvistamenti> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				int anno = res.getInt("anno");
				int numeroAvvistamenti = res.getInt("avvistamenti");
				AnnoAvvistamenti temp = new AnnoAvvistamenti(anno, numeroAvvistamenti);
				
				result.add(temp);
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getStatiByAnno(int anno) {
		String sql = "SELECT state " + 
				"FROM sighting " + 
				"WHERE country = 'us' AND YEAR(datetime) = ? " + 
				"GROUP BY state ORDER BY state";
		List<String> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery();
			
			while(res.next())
				result.add(res.getString("state"));
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<StatiSuccessivi> getConnessioniTraStati(int anno) {
		String sql = "SELECT s1.state AS st1, s2.state AS st2 " + 
				"FROM sighting s1, sighting s2 " + 
				"WHERE s1.country = 'us' AND s2.country = 'us' " + 
				"AND YEAR(s1.datetime) = ? AND YEAR(s2.datetime) = ? " + 
				"AND s2.datetime > s1.datetime AND s1.state != s2.state " + 
				"GROUP BY st1, st2";
		List<StatiSuccessivi> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				StatiSuccessivi temp = new StatiSuccessivi(res.getString("st1"), res.getString("st2"));
				result.add(temp);
			}
			
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
