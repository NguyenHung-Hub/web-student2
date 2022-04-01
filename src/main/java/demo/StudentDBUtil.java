package demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
//import org.apache.tomcat.jdbc.pool.DataSource;
import javax.sql.DataSource;

public class StudentDBUtil {
	@Resource(name = "jdbc/web_student")
	private DataSource dataSource;

	public StudentDBUtil(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	public List<Student> getStudents() throws SQLException {

		List<Student> students = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			String sql = "select * from student order by lastName";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("firstName");
				String lastName = resultSet.getString("lastName");
				String email = resultSet.getString("email");

				Student student = new Student(id, firstName, lastName, email);

				students.add(student);

			}
		} finally {
			closeConnection(connection, statement, resultSet);
		}

		return students;
	}

	public Student getStudent(String theStudentId) throws Exception {

		Student student = null;

		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;

		int studentId;

		try {
			studentId = Integer.parseInt(theStudentId);

			connection = dataSource.getConnection();
			String sql = "select * from student where id = ?";
			preStmt = connection.prepareStatement(sql);
			preStmt.setInt(1, studentId);
			rs = preStmt.executeQuery();

			if (rs.next()) {
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");

				student = new Student(studentId, firstName, lastName, email);
			} else {
				throw new Exception("Could not found student id: " + studentId);
			}
			return student;
		} finally {
			closeConnection(connection, preStmt, rs);
		}

	}

	public void addStudent(Student student) throws Exception {
		Connection connection = null;
		PreparedStatement preStmt = null;

		try {
			connection = dataSource.getConnection();
			String sql = "insert into student(firstName, lastName, email) values(?, ?, ?)";

			preStmt = connection.prepareStatement(sql);
			preStmt.setString(1, student.getFirstName());
			preStmt.setString(2, student.getLastName());
			preStmt.setString(3, student.getEmail());

			preStmt.execute();
		} finally {
			closeConnection(connection, preStmt, null);
		}
	}

	public void updateStudent(Student student) throws Exception {
		Connection connection = null;
		PreparedStatement preStmt = null;
		
		System.out.println(student);

		try {
			connection = dataSource.getConnection();
			String sql = "update student  set firstName= ?, lastName=?, email= ? where id = ?";

			preStmt = connection.prepareStatement(sql);
			preStmt.setString(1, student.getFirstName());
			preStmt.setString(2, student.getLastName());
			preStmt.setString(3, student.getEmail());
			preStmt.setInt(4, student.getId());

			preStmt.execute();
		} finally {
			closeConnection(connection, preStmt, null);
		}
	}

	public void deleteStudent(String id) throws Exception {
		Connection connection = null;
		PreparedStatement preStmt = null;

		try {
			
			int idStudent = Integer.parseInt(id);
			System.out.println(id);
			
			connection = dataSource.getConnection();
			String sql = "delete student where id = ?";

			preStmt = connection.prepareStatement(sql);
			preStmt.setInt(1, idStudent);

			preStmt.execute();
		} finally {
			closeConnection(connection, preStmt, null);
		}
	}

	private void closeConnection(Connection con, Statement stm, ResultSet rs) {
		try {
			if (con != null) {
				con.close();
			}
			if (stm != null) {
				stm.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
