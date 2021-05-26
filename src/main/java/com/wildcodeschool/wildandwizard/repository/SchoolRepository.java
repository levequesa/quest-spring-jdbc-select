package com.wildcodeschool.wildandwizard.repository;

import com.wildcodeschool.wildandwizard.entity.School;
import com.wildcodeschool.wildandwizard.util.JdbcUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchoolRepository {

    private final static String DB_URL = "jdbc:mysql://localhost:3306/spring_jdbc_quest?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "Horcrux4life!";

    public List<School> findAll() {
       	
    	Connection connection = null;
    	PreparedStatement request = null;
    	ResultSet result = null;
    	
    	try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			request= connection.prepareStatement("select * from school;");
			result = request.executeQuery();
			List<School> schools = new ArrayList<School>();
			while (result.next()) {
				Long id = result.getLong("id");
				String name = result.getString("name");
				Long capacity = result.getLong("capacity");
				String country = result.getString("country");
				schools.add(new School(id,name,capacity,country));
			}
			return schools;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(result);
			JdbcUtils.closeStatement(request);
			JdbcUtils.closeConnection(connection);
		}
        return null;
    }

    public School findById(Long id) {

    	Connection connection = null;
    	PreparedStatement request = null;
    	ResultSet result = null;
    	
    	try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			request = connection.prepareStatement("select * from school where id=?;");
			request.setLong(1, id);
			result = request.executeQuery();
			if (result.next()) {
				Long capacity = result.getLong("capacity");
				String name = result.getString("name");
				String country = result.getString("country");
				School school = new School(id, name, capacity, country);
				return (school);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(result);
			JdbcUtils.closeStatement(request);
			JdbcUtils.closeConnection(connection);
		}
        return null;
    }

    public List<School> findByCountry(String country) {

    	Connection connection = null;
    	PreparedStatement request = null;
    	ResultSet result = null;
    	try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			request = connection.prepareStatement("select * from school where country=?;");
			request.setString(1, country);
			result = request.executeQuery();
			List<School> schools = new ArrayList<School>();
			while (result.next()) {
				Long id = result.getLong("id");
				String name = result.getString("name");
				Long capacity = result.getLong("capacity");
				schools.add(new School(id, name, capacity, country));
			}
			return schools;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.closeResultSet(result);
			JdbcUtils.closeStatement(request);
			JdbcUtils.closeConnection(connection);
		}
        return null;
    }
}