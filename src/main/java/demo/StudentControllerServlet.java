package demo;

import java.io.IOException;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.tomcat.jdbc.pool.DataSource;
import javax.sql.DataSource;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private StudentDBUtil studentDBUtil;

	@Resource(name = "jdbc/web_student")
	private DataSource dataSource;

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			studentDBUtil = new StudentDBUtil(dataSource);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());

		try {
			String theCommand = req.getParameter("command");

			if (theCommand == null) {
				theCommand = "LIST";
			}

			switch (theCommand) {
			case "LIST": {

				listStudent(req, resp);
				break;
			}
			case "ADD": {

				addStudent(req, resp);
				System.out.println("ADD \n\n\n");
				break;
			}
			case "LOAD": {

				loadStudent(req, resp);
				break;
			}
			case "UPDATE": {

				updateStudent(req, resp);
				break;
			}
			case "DELETE": {
				System.out.println("DELETE");

				deleteStudent(req, resp);
				break;
			}
			default: {
				listStudent(req, resp);
			}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void listStudent(HttpServletRequest req, HttpServletResponse res) throws Exception {
		List<Student> students = studentDBUtil.getStudents();

		req.setAttribute("STUDENT_LIST", students);

		RequestDispatcher dispatcher = req.getRequestDispatcher("list-students.jsp");
		dispatcher.forward(req, res);
	}

	private void addStudent(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");

		Student theStudent = new Student(firstName, lastName, email);

		studentDBUtil.addStudent(theStudent);

		listStudent(req, res);
	}

	private void loadStudent(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String theStudentId = req.getParameter("studentId");
		Student student = studentDBUtil.getStudent(theStudentId);

		req.setAttribute("THE_STUDENT", student);
		RequestDispatcher dispatcher = req.getRequestDispatcher("update-student-form.jsp");
		dispatcher.forward(req, res);

	}

	private void updateStudent(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int theStudentId = Integer.parseInt(req.getParameter("studentId"));
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");

		Student theStudent = new Student(theStudentId, firstName, lastName, email);
		studentDBUtil.updateStudent(theStudent);

		listStudent(req, res);

	}

	private void deleteStudent(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String theStudentId = req.getParameter("studentId");
		studentDBUtil.deleteStudent(theStudentId);
		listStudent(req, res);
	}
}
