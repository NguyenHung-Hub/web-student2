<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	
	<form action="StudentControllerServlet" method="GET">
		<input type="hidden" name="command" value="ADD"> 
		<label>First Name</label> 
		<input type="text" name="firstName"> <br> <label>Last Name</label> <input
			type="text" name="lastName"> <br> <label>Email</label> <input
			type="text" name="email"> <br> <input type="submit" value="Submit">
	</form>
</body>
</html>