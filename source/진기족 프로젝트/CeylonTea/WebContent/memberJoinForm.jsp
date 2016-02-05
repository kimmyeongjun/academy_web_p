<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>

<script src="http://code.jquery.com/jquery.min.js"></script>

<link rel="stylesheet" href="./css/bootstrap.css">

<link data-href="./css/bootstrap-theme.css" rel="stylesheet"
   id="bs-theme-stylesheet">
<style>
html {
   width: auto;
   height: 100%;
   margin: 0px 100px 0px 100px;
   padding: 0px;
}

header {
   padding: 5px 100px;
}

body {
   
}

.join {
   width: 100%;
   height: 100%;
   text-align: center;
   margin: 60px auto;
}

.join_title {
   height: 60px;
}

table {
   height: 300px;
}

.form input {
   width: 250px;
}
</style>

<script>
   //idCheck.do
   function uid_ok() {
      var uid = $("#u_id").val();

      $.ajax({
         type : "POST",
         url : "idCheck.do",
         data : {
            USERID : uid
         },
         datatype : 'json',
         success : function(msg) {
            msg = msg[7];

            if (msg == 0) {
               alert("사용 가능한 ID입니다.");

            } else if (msg == 1) {
               alert("사용 불가능한 ID입니다.");
               $("#u_id").val("");
            } else {
               alert(msg);
            }
         }
      });
   }
</script>

</head>
<body background="./images/real_background.jpg">
   <header>
      <font size="6px" face="HY크리스탈M">한성대학교</font> <font size="3px"
         face="한컴 바겐세일 B">진기족</font>
   </header>
   <section>
      <article>
         <div class="join">
            <div class="join_title">
               <font>회원 가입</font>
            </div>
            <div class="join_form">

               <form action="join.do" method="post" accept-charset="utf-8">
                  <table class="form" align="center">
                     <tr>
                        <td>아이디</td>
                        <td><input type="text" class="form-control"
                           name="member_id" id="u_id" placeholder="아이디의 중복을 확인해주세요."></td>
                        <td><input type="button" class="btn btn-default" value="인증"
                           id="u_id_ok" onclick="uid_ok();"></td>
                     </tr>
                     <tr>
                        <td>비밀번호</td>
                        <td><input type="password" class="form-control"
                           name="member_pw" id="exampleInputEmail3"
                           placeholder="비밀번호를 입력해주세요."></td>
                        <td></td>
                     </tr>
                     <tr>
                        <td>비밀번호확인</td>
                        <td><input type="password" class="form-control"
                           name="member_repw" id="exampleInputEmail3"
                           placeholder="비밀번호를 확인해주세요."></td>
                        <td></td>
                     </tr>
                     <tr>
                        <td>이름</td>
                        <td><input type="text" class="form-control"
                           name="member_name" id="exampleInputEmail3"></td>
                        <td></td>
                     </tr>
                     <tr>
                        <td>이메일</td>
                        <td><input type="email" class="form-control"
                           name="member_email" id="exampleInputEmail3"></td>
                        <td></td>
                     </tr>
                  </table>
                  <input type="submit" class="btn btn-default" value="가입">
                  <button type="submit" class="btn btn-default">&nbsp;&nbsp;취소&nbsp;&nbsp;</button>
               </form>
            </div>
         </div>
      </article>
   </section>

</body>
</html>