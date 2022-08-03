package com.serv.servletassignments;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.Driver;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException
	{
		String userName = req.getParameter("username");
		String password = req.getParameter("password");
		String course = req.getParameter("course");
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		
		
		try {
			Driver myDriver = new com.mysql.cj.jdbc.Driver();
			DriverManager.registerDriver(myDriver);
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/accounts", "root", "Prequelbot");
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE user_name = ? and password = ?;");
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				pstmt = conn.prepareStatement("UPDATE users SET course = ? WHERE user_name = ?;");
				pstmt.setString(1, course);
				pstmt.setString(2, userName);
				pstmt.executeUpdate();
				
				out.println("Hi, "+userName);
				out.println("We have registered you for: "+course);
				out.println("You are in this course with: ");
				
				pstmt = conn.prepareStatement("SELECT user_name FROM users WHERE course = ?;");
				pstmt.setString(1, course);
				
				rs = pstmt.executeQuery();
				
				while(rs.next())
				{
					out.println(rs.getString(1));
				}
				
				
			}
			else
			{
				
				out.println("You weren't in our system!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		
	}

	
	
}
