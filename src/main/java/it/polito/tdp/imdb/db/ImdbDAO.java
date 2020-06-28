package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Adiacenza;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public void listAllActors(Map<Integer, Actor> idMap){
		String sql = "SELECT * FROM actors";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				idMap.put(actor.getId(),actor);
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public List<String> listAllGeneri(){
		String sql = "SELECT DISTINCT genre from movies_genres order by genre";
		List<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				
				result.add(res.getString("genre"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Actor> listAllVertici(String genere, Map<Integer, Actor> idMap){
		String sql = "SELECT distinct actor_id as id  from movies_genres mg, roles r WHERE mg.movie_id=r.movie_id AND mg.genre=?";
		List<Actor> result= new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(idMap.containsKey(res.getInt("id"))) {
					result.add(idMap.get(res.getInt("id")));
				}
				
			
			}
			
			conn.close();
				return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return null;
	}
	
	public List<Adiacenza> listAllAdiacenza(String genere, Map<Integer, Actor> idMap){
		String sql = "SELECT  r1.actor_id as id1, r2.actor_id as id2, COUNT(*) as peso "
				+ "from movies_genres mg, roles r1,roles r2 "
				+ "WHERE mg.movie_id=r1.movie_id AND mg.genre=? AND  mg.movie_id=r2.movie_id  "
				+ "AND r1.actor_id>r2.actor_id GROUP BY id1, id2" ;
		List<Adiacenza> result= new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(idMap.containsKey(res.getInt("id1")) && idMap.containsKey(res.getInt("id1")) ) {
					result.add(new Adiacenza(idMap.get(res.getInt("id1")), idMap.get(res.getInt("id2")), res.getDouble("peso")));
				}
				
			
			}
			
			conn.close();
				return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return null;
	}
	
}
