<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="wrapper">
		<div class="header">
			<h2>University</h2>
		</div>
	</div>

	<div class="container">
		<div class="content">
			<input type="button" value="Add student"
				onclick="window.location.href='form.jsp'; return false;"
				class="add-student-btn" />
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
			</table>
			
			<table>

			<c:forEach var="tempStudent" items="${STUDENT_LIST}">
				<c:url var="tempLink" value="StudentControllerServlet">
					<c:param name="command" value="LOAD" />
					<c:param name="studentId" value="${tempStudent.id }" />
				</c:url>

				<c:url var="deleteLink" value="StudentControllerServlet">
					<c:param name="command" value="DELETE" />
					<c:param name="studentId" value="${tempStudent.id }" />
				</c:url>
				
				<tr>
					<td> ${tempStudent.firstName } </td>
					<td>${tempStudent.lastName }</td>
					<td>${tempStudent.email}</td>
					<td>
						<a href="${tempLink }">Update</a>
						<a href="${deleteLink}" onclick="if (!(confirm('Are you sure you want to delete this student?'))) return false">Delete</a>
					</td>
				</tr>
				
				
				
				
			</c:forEach>
			</table>

		</div>
	</div>

</body>
</html>