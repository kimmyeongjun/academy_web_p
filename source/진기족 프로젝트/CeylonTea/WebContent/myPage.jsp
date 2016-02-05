<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<script src="http://code.jquery.com/jquery.min.js"></script>

<link href="./css/bootstrap.css" rel="stylesheet">
<!-- Optional theme -->

<link rel="stylesheet" href="./css/bootstrap-theme.css">

<script src="./js/bootstrap.js"></script>
<script src="js/bootstrap-dropdown.js"></script>
<script src="js/bootstrap-tab.js"></script>

<script>
   //idCheck.do
   function upw_ok() {
      var upw = $("#u_pw").val();
      $.ajax({
         type : "POST",
         url : "certify2.do",
         data : {
            USERPW : upw
         },
         datatype : 'json',
         success : function(msg) {
            msg = msg[7];

            if (msg == 0) {
               $("#re_login").css("display", "none");
               $("#edit_info").css("display", "inline")
            } else if (msg == 1) {
               alert("불일치하는 PW 입니다.");
               $("#u_pw").val("");
            } else {
               alert(msg);
            }
         }
      });
   }
</script>

<style>
html {
   width: auto;
   height: 100%;
   margin: 0px 100px 0px 100px;
   padding: 0px;
}

body {
   background-size: cover;
}

header {
   padding: 5px 0px;
   margin: 0px 0px 10px 0px;
}

.form input {
   width: 250px;
}

.edit_info {
   display: none;
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
</head>
<body style="overflow-x: hidden;" background="./images/jangwon.png">
   <form name="submitfrm">
      <input type="hidden" name="sequence_num">
   </form>

   <header>
      <a href="mainPage.html"> <font size="6px" face="서울한강체"
         color="black">한성대학교</font></a> <font color="black" size="3px"
         face="서울한강체">진기족</font>

   </header>
   <section>
      <article>
         <div>
            <ul class="nav nav-tabs navbar-right" role="tablist" id="myTab">
               <li role="presentation"><a href="#home" aria-controls="home"
                  role="tab" data-toggle="tab"><font size="3px" face="나눔바른고딕"
                     color="black">회원정보 수정</font></a></li>
               <li role="presentation"><a href="#profile"
                  aria-controls="profile" role="tab" data-toggle="tab"><font
                     size="3px" face="나눔바른고딕" color="black">수강한 강의</font></a></li>
               <li role="presentation" class="active"><a href="#messages"
                  aria-controls="messages" role="tab" data-toggle="tab"><font
                     size="3px" face="나눔바른고딕" color="black">게시글</font></a></li>
            </ul>
         </div>
      </article>
      <article>
         <div class="tab-content" style="height: 100%;">
            <br> <br> <br> <br>
            <div role="tabpanel" class="tab-pane fade" id="home">
               <div id="edit_info" class="edit_info" style="height: 100%;">
                  <form action="update.do" method="post">
                     <input type="hidden" name="member_id"
                        value="<%=session.getAttribute("member")%>">
                     <table class="form" align="center" height="300px"
                        style="text-align: center;">
                        <tr>
                           <td><font size="3px" face="나눔바른고딕" color="black">아이디</font></td>
                           <td><input type="text" class="form-control"
                              name="nononononono" id="exampleInputEmail3"
                              disabled="disabled"
                              value="<%=session.getAttribute("member")%>"></td>
                        </tr>
                        <tr>
                           <td><font size="3px" face="나눔바른고딕" color="black">비밀번호</font></td>
                           <td><input type="password" class="form-control"
                              name="member_pw" id="password" placeholder="새 비밀번호를 입력해주세요."></td>
                           <td></td>
                        </tr>
                        <tr>
                           <td><font size="3px" face="나눔바른고딕" color="black">비밀번호확인</font></td>
                           <td><input type="password" class="form-control"
                              name="member_pw_confirm" id="repassword"
                              placeholder="새 비밀번호를 다시 입력해주세요."></td>
                           <td></td>
                        </tr>
                        <tr>
                           <td><font size="3px" face="나눔바른고딕" color="black">이름</font></td>
                           <td><input type="text" class="form-control"
                              name="member_name" id="exampleInputEmail3"></td>
                           <td></td>
                        </tr>
                        <tr>
                           <td><font size="3px" face="나눔바른고딕" color="black">이메일</font></td>
                           <td><input type="email" class="form-control"
                              name="member_email" id="exampleInputEmail3"></td>
                           <td></td>
                        </tr>
                     </table>
                     <div align="center">
                        <input type="submit" class="btn btn-default" value="수정">
                        <button type="submit" class="btn btn-default">&nbsp;&nbsp;취소&nbsp;&nbsp;</button>
                     </div>
                  </form>
               </div>

               <div id="re_login">
                  <table class="form" align="center" height="100px">
                     <tr>
                        <td><font size="3px" face="나눔바른고딕" color="black">아이디</font></td>
                        <td><input type="text" class="form-control" name="id"
                           id="exampleInputEmail3" disabled="disabled"
                           value="<%=session.getAttribute("member")%>"></td>
                     </tr>
                     <tr>
                        <td><font size="3px" face="나눔바른고딕" color="black">비밀번호</font></td>
                        <td><input type="password" class="form-control"
                           name="password" id="u_pw" placeholder="비밀번호를 입력해주세요."></td>
                     </tr>
                  </table>
                  <div align="center">
                     <input type="button" class="btn btn-default" value="확인"
                        id="u_pw_ok" onclick="upw_ok();">
                     <button type="submit" class="btn btn-default">취소</button>
                  </div>
               </div>
            </div>
            <div role="tabpanel" class="tab-pane fade" id="profile"
               style="height: 100%;">
               <div align="center">
                  <table width="90%" height="200px" align="center"
                     style="text-align: center;"
                     class="table table-hover table-nonstriped">
                     <thead>
                        <tr>
                           <td>수강 학기</td>
                           <td>이수구분</td>
                           <td>과목코드</td>
                           <td>과목이름</td>
                           <td>분반</td>
                           <td>학점</td>
                           <td>교수이름</td>
                        </tr>
                     </thead>
                     <tbody>
                        <c:forEach var="lecture" items="${map.myLectureList}">
                           <tr onclick="javascript:clickTrEvent(${lecture.sequence_num})">
                              <td width="20%"><font size="3px" face="나눔바른고딕"
                                 color="black">${lecture.year} - ${lecture.semester}학기</font></td>
                              <td><font size="3px" face="나눔바른고딕" color="black">${lecture.s_type}</font></td>
                              <td><font size="3px" face="나눔바른고딕" color="black">${lecture.s_code}</font></td>
                              <td><font size="3px" face="나눔바른고딕" color="black">${lecture.s_name}</font></td>
                              <td><font size="3px" face="나눔바른고딕" color="black">${lecture.c_division }</font></td>
                              <td><font size="3px" face="나눔바른고딕" color="black">${lecture.g_unit }</font></td>
                              <td><font size="3px" face="나눔바른고딕" color="black">${lecture.p_name }</font></td>
                           </tr>
                        </c:forEach>
                     </tbody>
                  </table>
               </div>
            </div>
            <div role="tabpanel" class="tab-pane fade in active" id="messages">
               <div align="center">
                  <table width="90%" height="200px" align="center">
                     <c:forEach var="eval" items="${map.myEvalList}">
                        <tr>
                           <td width="20%" align="left"><font size="3px"
                              face="나눔바른고딕" color="black">${eval.lectureVO.s_name}(${eval.lectureVO.c_division})
                                 / ${eval.lectureVO.p_name}</font></td>
                           <td width="50%" align="center"><font size="3px"
                              face="나눔바른고딕" color="black">${eval.opinion}</font></td>
                           <td align="right">
                              <table>
                                 <tr>
                                    <td><font size="3px" face="나눔바른고딕" color="black">${eval.write_date}</font></td>
                                 </tr>

                              </table>
                           </td>
                        </tr>
                     </c:forEach>
                  </table>
               </div>
            </div>
         </div>

      </article>
   </section>

</body>
</html>