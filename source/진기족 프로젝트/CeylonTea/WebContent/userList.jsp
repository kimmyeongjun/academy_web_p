<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>����� ����</title>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>
	<div class="container">
		<h2>����� ���</h2>
		<table class="table table-bordered table table-hover"> 
<%-- 			<caption>����� ���</caption>  --%>
			<thead> 
				<tr> 
					<th>User Id</th> 
					<th>Name</th> 
					<th>Gender</th>
					<th>City</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr> 
		</thead> 
		<tbody> 
			<c:forEach var="user" items="${userList}">
				<tr>
					<td>${user.userId}</td>
					<td>${user.name}</td>
					<td>${user.gender}</td>
					<td>${user.city}</td>
					<td><a href="edit.do?id=${user.userId}">Edit</a></td>
					<td><a href="delete.do?id=${user.userId}">Delete</a></td>
				</tr>
			</c:forEach>
			<tr><td colspan="7"><a href="register.do">Add New User</a></td></tr>
		</tbody> 
	</table>

	</div>
</body>
</html>