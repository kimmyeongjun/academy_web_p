<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>사용자 관리</title>
<style>
body {
	font-size: 20px;
	color: teal;
	font-family: Calibri;
}

td {
	font-size: 15px;
	color: black;
	width: 100px;
	height: 22px;
	text-align: left;
}

.heading {
	font-size: 18px;
	color: white;
	font: bold;
	background-color: orange;
	border: thick;
}
</style>
</head>
<body>
	<div align="center">
		<br /> <br /> <br /> <b> 등록 화면 </b> <br />
		<br />
		<div>
			<form method="post" action="insert.do"  >
				<table>
					<tr>
						<td>UserId :</td>
						<td><input type="text" name="userId" /></td>
					</tr>
					<tr>
						<td>Name :</td>
						<td><input type="text" name="name" /></td>
					</tr>
					<tr>
						<td>Gender :</td>
						<td>
						<c:forEach var="genderName" items="${map.genderList}">
							<input type="radio" name="gender" value="${genderName}">${genderName}
						</c:forEach>
						</td>
					</tr>
					<tr>
						<td>City :</td>
						<td>
						<select name="city">
							<c:forEach var="cityName" items="${map.cityList}">
								<option value="${cityName}">${cityName}</option>
							</c:forEach>
						</select>
						</td>
					</tr>
					<tr>
					<td colspan="2" ><p align="center"><input type="submit" value="저장" /></p></td>
					</tr>
					<tr>					
						<td colspan="2"><p align="center"><a href="getList.do">사용자 목록보기</a></p></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>