<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>bookStore注册页面</title>
<%--导入css --%>
<link rel="stylesheet" href="css/main.css" type="text/css" />
	<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript">
	function changeImage() {

		document.getElementById("img").src = "${pageContext.request.contextPath}/imageCode?time="
				+ new Date().getTime();
	}

	/*function checkemail(){
       if($("#email").val()==""){
           alert("邮箱不能为空");
           return false;
       }else{
           return true;
       }
    }*/
	//
	$(function () {
		/*$("#userform").submit(function(){
			var email = $("#email");
			var username = $("#username");
			var telephone = $("#telephone");
			var introduce = $("#introduce");
			var msg="";

			if(email.val()==""){
				msg="邮箱不能为空";
				email.focus();
			}else if(username.val()==""){
				msg="会员名不能为空";
				username.focus();
			}else if(telephone.val()==""){
				msg="手机号码不能为空";
				telephone.focus();
			}else if(!/^1[3|5|8]\d{9}$/.test(telephone.val())){
				msg="手机号码格式不正确";
				telephone.focus();
			}else if(introduce.val()==""){
				msg="自我描述不能为空";
				introduce.focus();
			}
			if (msg != ""){
				alert(msg);
				return false;
			}else{
				return true;
			}
			/*alert("静安里");
			checkemail();
			return false;///
		});*/
        var email = $("#email");
		email.blur(function () {
            if(email.val()==""){
                alert("邮箱不能为空");
            }
        });

	});
</script>
</head>


<body class="main">
	<%@include file="head.jsp"%>
	<%--导入头 --%>
	<%@include file="menu_search.jsp"%><%--导入导航条与搜索 --%>

	<div id="divcontent">
		<form id="userform" action="${pageContext.request.contextPath}/register" method="post">
			<table width="850px" border="0" cellspacing="0">
				<tr>
					<td style="padding:30px">
						<h1>新会员注册</h1>
						
						<table width="70%" border="0" cellspacing="2" class="upline">

							<tr>
								<td></td>
								<td colspan="3"><span style="color: red">&nbsp;&nbsp;&nbsp;${register_error}</span></td>
								<td></td>
							</tr>
							<tr>
								<td style="text-align:right; width:20%">会员邮箱：</td>
								<td style="width:40%">
								<input type="email" class="textinput"
									name="email" id="email"/></td>
								<td><font color="#999999"></font></td>
							</tr>
							<tr>
								<td style="text-align:right">会员名：</td>
								<td>
									<input type="text" class="textinput" name="username" id="username"/>
								</td>
								<td><font color="#999999">用户名设置至少6位</font></td>
							</tr>
							<tr>
								<td style="text-align:right">密码：</td>
								<td><input type="password" class="textinput"
									name="password" /></td>
								<td><font color="#999999">密码设置至少6位</font></td>
							</tr>

							<tr>
								<td style="text-align:right">重复密码：</td>
								<td><input type="password" class="textinput"
									name="repassword" /></td>
								<td>&nbsp;</td>
							</tr>

							<tr>
								<td style="text-align:right">性别：</td>
								<td colspan="2">&nbsp;&nbsp;<input type="radio"
									name="gender" value="男" checked="checked" /> 男
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"
									name="gender" value="女" /> 女</td>
							</tr>

							<tr>
								<td style="text-align:right">联系电话：</td>
								<td ><input type="text" class="textinput"
									style="width:350px" name="telephone" id="telephone"/></td>
							</tr>
							<tr>
								<td style="text-align:right">个人介绍：</td>
								<td colspan="2"><textarea class="textarea" name="introduce" id="introduce"></textarea>
								</td>
							</tr>

						</table>



						<h1>注册校验</h1>
						<table width="80%" border="0" cellspacing="2" class="upline">
							<tr>
								<td style="text-align:right; width:20%">输入校验码：</td>
								<td style="width:50%"><input type="text" class="textinput" name="checkCode"/>
									<span style="color: red"></span>
									${checkcode_error}
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td style="text-align:right;width:20%;">&nbsp;</td>
								<td colspan="2" style="width:50%"><img
									src="${pageContext.request.contextPath}/imageCode" width="180"
									height="30" class="textinput" style="height:30px;" id="img" />&nbsp;&nbsp;
									<a href="javascript:void(0);" onclick="changeImage()">看不清换一张</a>
								</td>
							</tr>
						</table>



						<table width="70%" border="0" cellspacing="0">
							<tr>
								<td style="padding-top:20px; text-align:center">
									<!--<input type="image" src="images/signup.gif" name="submit" border="0">-->
									<input type="submit" value="注册"/>&nbsp;
								</td>
							</tr>
						</table></td>
				</tr>
			</table>
		</form>
	</div>



	<div id="divfoot">
		<table width="100%" border="0" cellspacing="0">
			<tr>
				<td rowspan="2" style="width:10%"><img
					src="images/bottomlogo.gif" width="195" height="50"
					style="margin-left:175px" /></td>
				<td style="padding-top:5px; padding-left:50px"><a href="#"><font
						color="#747556"><b>CONTACT US</b> </font> </a></td>
			</tr>
			<tr>
				<td style="padding-left:50px"><font color="#CCCCCC"><b>COPYRIGHT
							2008 &copy; eShop All Rights RESERVED.</b> </font></td>
			</tr>
		</table>
	</div>


</body>
</html>
