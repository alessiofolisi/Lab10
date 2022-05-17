package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.add(new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	
public List<Flow> getAllFlow(Map<Integer,River> idMap) {
		
		final String sql = "SELECT id, day, flow, river "
				+ "FROM flow";

		List<Flow> flows = new ArrayList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Flow flow= new Flow(res.getTimestamp("day").toLocalDateTime().toLocalDate(), res.getDouble("flow"), idMap.get(res.getInt("river")));
				flows.add(flow);
			}

			conn.close();
			return flows;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		
	}

	public List<Flow> getDateFlow(River r , Map<Integer,River> idMap){
		
		final String sql = "SELECT id, day, flow, river "
				+ "FROM flow "
				+ "WHERE river = ?";
		
		List<Flow> dateFlows = new ArrayList<Flow>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				Flow flow= new Flow(res.getTimestamp("day").toLocalDateTime().toLocalDate(), res.getDouble("flow"), idMap.get(res.getInt("river")));
				dateFlows.add(flow);
			}
			conn.close();
			return dateFlows;
			
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	
	}

	
	
	
	
}
