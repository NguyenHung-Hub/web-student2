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
	<form action="StudentControllerServlet" method="GET">
		<input type="hidden" name="command" value="UPDATE">
		<c:set var = "st" scope = "session" value = "${THE_STUDENT}"/>
		<input type="hidden" name="studentId" value="${st.id }">
		
		
		<label>First Name</label>
		<input type="text" name="firstName" value="${st.firstName }"> <br>
		
		
		<label>Last	Name</label> 
		<input type="text" name="lastName" value="${st.lastName }"> <br>
		 
		<label>Email</label>
		<input type="text" name="email" value="${st.email }"> <br> 
		<input type="submit" value="Submit">
	</form>
</body>
</html>