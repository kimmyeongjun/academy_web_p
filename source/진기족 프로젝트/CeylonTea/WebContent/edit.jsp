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
	text-align: center;
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
		<br /> <br /> <br /> <b>수정 화면 </b><br /> <br />
		<div>
			<form method="post" action="update.do">
				<input type="hidden" name="userId"  value="${map.user.userId}" />
				<table>
					<tr>
						<td>UserId :</td>
						<td>${map.user.userId}</td>
					</tr>
					<tr>
						<td>Name :</td>
						<td><input type="text" name="name" value="${map.user.name}" />
						</td>
					</tr>
					<tr>
						<td>Gender :</td>
						<td>
								<c:forEach items='${map.genderList}' var='genderName'>
									<c:choose>
										<c:when test="${genderName eq map.user.gender}">
											<input type="radio" name="gender" value="${genderName}"
												checked="checked">${genderName}
										</c:when>
										<c:otherwise>
											<input type="radio" name="gender" value="${genderName}">${genderName}
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</td>
					</tr>
					<tr>

						<td>City :</td>
						<td>
								<select name="city">
									<c:forEach items='${map.cityList}' var='cityName'>
										<c:choose>
											<c:when test="${cityName eq map.user.city}">
												<option value="${cityName}" selected>${cityName}</option>
											</c:when>
											<c:otherwise>
												<option value="${cityName}">${cityName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
						</td>
					</tr>
					<tr>
						<td colspan="2" ><p align="center"><input type="submit" value="수정" /></p></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>