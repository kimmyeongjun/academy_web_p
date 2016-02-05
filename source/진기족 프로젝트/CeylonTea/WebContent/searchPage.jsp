<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SearchPage</title>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="./js/jquery.tablesorter.min.js"></script>
<script type="text/javascript">
$(function(){
   $("#myTable").tablesorter({
      headers: {
         2: { sorter: false } , 
         3: { sorter: false },
         4: { sorter: false }
      }
   }); 
});
</script>
<link href="./css/bootstrap.css" rel="stylesheet">
<!-- Optional theme -->
<link rel="stylesheet" href="./css/bootstrap-theme.css">
<script src="js/bootstrap.js"></script>
<script src="js/bootstrap-dropdown.js"></script>
<style>
html {
   width: auto;
   height: 100%;
   margin: 0px 100px 0px 100px;
   padding: 0px;
}

header {
   padding: 5px 0px;
   margin: 0px 0px 10px 0px;
}

body {
   background-color: #F3F3F3;
}

table input {
   text-align: center;
}

.data_table {
   background-color: #FFFFFF;
}

header img {
   margin: 15px;
}
</style>
<script>
function clickTrEvent(sequence_num) { 
   submitfrm.sequence_num.value = sequence_num;
   submitfrm.action = "detail.do"
   submitfrm.method = "post"
   submitfrm.submit();
}
</script>
<style>
table thead tr .header {
   background-image: url(./images/bg.gif);
   background-repeat: no-repeat;
   background-position: center right;
   cursor: pointer;
}

table thead tr .headerSortUp {
   background-image: url(./images/asc.gif);
   background-repeat: no-repeat;
   background-position: center right;
}

table thead tr .headerSortDown {
   background-image: url(./images/desc.gif);
   background-repeat: no-repeat;
   background-position: center right;
}
</style>
</head>
<form name="submitfrm">
   <input type="hidden" name="sequence_num">
</form>
<body style="overflow-x: hidden"
   background="./images/real_background.jpg">
   <header>
      <a href="mainPage.html"> <font size="6px" face="서울한강체"
         color="black">한성대학교</font></a> <font size="3px" face="서울한강체">진기족</font> <a
         href="getinfolist.do"><img align="right" width="80px"
         src="./images/shoes.png"></a>
   </header>
	<section>
		<article>
			<form action="search1.do" method="get">
				<table align="center" width="100%">
					<tr>
						<td width="12%"><select name="year" class="form-control">
								<option value="">연도 선택</option>
								<option value="2013">2013년</option>
								<option value="2014">2014년</option>
								<option value="2015">2015년</option>
						</select></td>
						<td width="12%"><select name="semester" class="form-control">
								<option value="">학기 선택</option>
								<option value="1">1학기</option>
								<option value="2">2학기</option>
						</select></td>
						<td><input type="text" class="form-control"
							name="search_value" id="exampleInputEmail3"
							placeholder="강의명.교수명"></td>
						<td width="5px"><input type="submit" class="btn btn-default"
							value="찾기"></td>
					</tr>
				</table>
			</form>
			<br>
		</article>

		<article>
			<br> <br>
			<div class="container">
				<div>
					<div class="col-md-9">

						<div>

							<!-- <table class="table table-bordered table table-hover"> -->
							<%--          <caption>사용자 목록</caption>  --%>
							<table width="100%" id="myTable"
								class="table table-hover table-striped">
								<thead>
									<tr>
										<th>구분</th>
										<th>강의명</th>
										<th>분반</th>
										<th>교수</th>
										<th>학점</th>
										<th>평점</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="lecture" items="${lectureList}">
										<tr onclick="javascript:clickTrEvent(${lecture.sequence_num})">
											<td>${lecture.s_type}</td>
											<td>${lecture.s_name}</td>
											<td>${lecture.c_division}</td>
											<td>${lecture.p_name}</td>
											<td>${lecture.g_unit}</td>
											<td>${lecture.avg_evaluation}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="col-md-3">
						<ul class="nav nav-tabs nav-stacked affix" id="myNav">
							<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								내가 본 강의 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
							<li class="active"><a href="#one">Tutorial One</a></li>
							<li><a href="#two">Tutorial Two</a></li>
							<li><a href="#three">Tutorial Three</a></li>

						</ul>

					</div>
				</div>
			</div>
		</article>
	</section>
</body>
</html>