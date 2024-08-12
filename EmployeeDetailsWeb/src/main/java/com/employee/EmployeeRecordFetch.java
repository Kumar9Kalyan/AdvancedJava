package com.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EmployeeRecordFetch extends HttpServlet {

	

	

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ServletContext context1 = getServletContext();
		
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		
		String driver = context1.getInitParameter("Driver");
		String url = context1.getInitParameter("URL");
		String username = context1.getInitParameter("username");
		String password = context1.getInitParameter("password");
			
		String query = "select * from employee";

		Connection con = null;
		Statement stm = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url,username,password);
			stm = con.createStatement();
			rs = stm.executeQuery(query);

		
			out.println("<table border =\"1\" align=\"center\">");
			out.println("<tr><th>eno</th><th>ename</th><th>gender</th><th>dob</th><th>salary</th></tr>");

			while (rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getInt("eno") + "</td>");
				out.println("<td>" + rs.getString("ename") + "</td>");
				out.println("<td>" + rs.getString("gender") + "</td>");
				out.println("<td>" + rs.getDate("dob") + "</td>");
				out.println("<td>" + rs.getDouble("salary") + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
		} catch (ClassNotFoundException | SQLException e) {
			response.sendError(500, e.toString());
		}

	}

}
