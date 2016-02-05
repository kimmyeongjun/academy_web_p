<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Detail Page</title>
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css"
	rel="stylesheet">

<link href="./css/star-rating.css" media="all" rel="stylesheet"
	type="text/css" />
<link href="./css/bootstrap-responsive.css" rel="stylesheet">
<script	src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="./js/star-rating.js" type="text/javascript"></script>
<script src="js/bootstrap.js"></script>
<script>
	function openPlan(num) {
		if (num == 0) {
			window
					.open(
							"lecturePlan/${lecture.year}${lecture.semester}${lecture.s_code}${lecture.c_division}lecPlan.html",
							"popup", "width=800,height=1300");

		}
		if (num == 1) {
			window
					.open(
							"lecturePlan/${lecture.year}${lecture.semester}${lecture.s_code}${lecture.c_division}15WPlan.html",
							"popup", "width=800,height=1300");
		}
	}
</script>
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

.btn-mini {
	padding: 0 6px;
	font-size: 10.5px;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
}

header img {
	margin: 15px;
}
</style>

</head>
<body background="./images/real_background.jpg">
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
							name="search_value" id="exampleInputEmail3" placeholder="강의명.교수명"></td>
						<td width="5px"><input type="submit" class="btn btn-default"
							value="찾기"></td>
					</tr>
				</table>
			</form>
		</article>
		<article>
			<br>
			<br>
			<br>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span5">
						<div class="well sidebar-nav" align="center">
							<input id="myBtn" type="button" disabled="disabled"
								class="btn btn-warning" value="${lecture.s_type}"> <i>${lecture.s_name}(${lecture.c_division})</i>
							<div align="right">
								<button type="button" class="btn btn-warning btn-xs"
									data-toggle="modal" data-target="#myModal">작성</button>
							</div>
							<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
								aria-labelledby="myModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<form action="write.do" method="post">
											<input type="hidden" name="sequence_num"
												value=${lecture.sequence_num}>
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<h4 class="modal-title" id="myModalLabel">
													<button type="button" class="btn btn-warning"
														disabled="disabled">${lecture.s_type}</button>
													${lecture.s_name}(${lecture.c_division}) /
													${lecture.p_name}
												</h4>
											</div>
											<div class="modal-body">
												<table class="detail">
													<tr>
														<td>열정</td>
														<td><input id="input-21e" name="passion" value="0"
															type="number" class="rating" min=0 max=5 step=0.5
															data-size="xs"></td>
													</tr>
													<tr>
														<td>소통</td>
														<td><input id="input-21e" name="communication"
															value="0" type="number" class="rating" min=0 max=5
															step=0.5 data-size="xs"></td>

													</tr>
													<tr>
														<td>공정</td>
														<td><input id="input-21e" name="fairness" value="0"
															type="number" class="rating" min=0 max=5 step=0.5
															data-size="xs"></td>
													</tr>
													<tr>
														<td>이득</td>
														<td><input id="input-21e" name="benefit" value="0"
															type="number" class="rating" min=0 max=5 step=0.5
															data-size="xs"></td>
													</tr>
												</table>
												<div align="left">
													<br>강의 의견<br>
													<textarea class="form-control" name="opinion" rows="3"></textarea>
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-default"
													data-dismiss="modal">닫기</button>
												<input type="submit" class="btn btn-primary" value="저장">
											</div>
										</form>
									</div>
								</div>
							</div>
							<table class="detail">
								<tr>
									<td>열정</td>
									<td><input id="input-21e" value="${lecture.avg_passion}"
										type="number" class="rating" min=0 max=5 step=0.5
										data-size="xs" disabled="disabled"></td>
								</tr>
								<tr>
									<td>소통</td>
									<td><input id="input-21e"
										value="${lecture.avg_communication}" type="number"
										class="rating" min=0 max=5 step=0.5 data-size="xs"
										disabled="disabled"></td>

								</tr>
								<tr>
									<td>공정</td>
									<td><input id="input-21e" value="${lecture.avg_fairness}"
										type="number" class="rating" min=0 max=5 step=0.5
										data-size="xs" disabled="disabled"></td>
								</tr>
								<tr>
									<td>이득</td>
									<td><input id="input-21e" value="${lecture.avg_benefit}"
										type="number" disabled="disabled" class="rating" min=0 max=5
										step=0.5 data-size="xs"></td>
								</tr>
							</table>
							<div class="row">
								<div class="col-md-6" align="center">
									<button type="button" class="btn btn-default"
										onclick="javascript:openPlan(0)">강의계획서</button>
								</div>
								<div class="col-md-6" align="center">
									<button type="button" class="btn btn-default"
										onclick="javascript:openPlan(1)">강의스케쥴</button>
								</div>
							</div>
						</div>

					</div>
					<div class="span7">
						<div class="well sidebar-nav">
							<div>
								<table width="100%" height="180px">
									<tr>
										<td>
											<div class="well sidebar-nav">
												<table width="100%" height="180px">
													<tr>
														<td>ID</td>
														<td>작성일</td>
													</tr>
													<tr>
														<td colspan="2">평가내용<br></td>
													</tr>
													<tr>
														<td colspan="2">강의수강일<br></td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="well sidebar-nav">
												<table width="100%" height="180px">
													<tr>
														<td>ID</td>
														<td>작성일</td>
													</tr>
													<tr>
														<td colspan="2">강의난이도<br></td>
													</tr>
													<tr>
														<td colspan="2">강의수강일<br></td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</article>
	</section>
	<script>
		jQuery(document).ready(function() {
			$("#input-21f").rating({
				starCaptions : function(val) {
					if (val < 3) {
						return val;
					} else {
						return 'high';
					}
				},
				starCaptionClasses : function(val) {
					if (val < 3) {
						return 'label label-danger';
					} else {
						return 'label label-success';
					}
				},
				hoverOnClear : false
			});

			$('#rating-input').rating({
				min : 0,
				max : 5,
				step : 1,
				size : 'lg',
				showClear : false
			});

			$('#btn-rating-input').on('click', function() {
				$('#rating-input').rating('refresh', {
					showClear : true,
					disabled : true
				});
			});

			$('.btn-danger').on('click', function() {
				$("#kartik").rating('destroy');
			});

			$('.btn-success').on('click', function() {
				$("#kartik").rating('create');
			});

			$('#rating-input').on('rating.change', function() {
				alert($('#rating-input').val());
			});

			$('.rb-rating').rating({
				'showCaption' : true,
				'stars' : '3',
				'min' : '0',
				'max' : '3',
				'step' : '1',
				'size' : 'xs',
				'starCaptions' : {
					0 : 'status:nix',
					1 : 'status:wackelt',
					2 : 'status:geht',
					3 : 'status:laeuft'
				}
			});
		});
	</script>

</body>
</html>