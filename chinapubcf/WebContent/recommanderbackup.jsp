<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html>
<html>
<head>
  <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" contect="zh-CN">
<title>chinapub Recommendation Engine</title>
<link rel="stylesheet" href="style/style.css" type="text/css" />
<script src="script/prototype.js" type="text/javascript"></script>
<script src="script/ui.js" type="text/javascript"></script>
<script type="text/javascript">
var purchaseCars = function(obj){
	var bIdscores = obj.attributes["bidscores"];
	//alert(bIdscores.nodeValue);
	//var userid = $("userId").getValue();
	var userId = <%=request.getSession().getAttribute("userId")%>;
	new Ajax.Request('/chinapubcf/rating',
	  		{
	    		method:'post',
	    		parameters: {bookIdScores: bIdscores.nodeValue,userId:userId},
	    		onSuccess: function(transport){
	    			//var response = transport.responseText || "no response text";
	      			alert("购买结算成功！");
	      			$(obj).setAttribute("bIdscores", "");
	    		},
	    		onFailure: function(){ alert('购买结算失败，请检查购买信息后重试...'); }
	  		});
};
</script>
</head>
<body>
<img alt="logo" src="images/cp_logo.jpg">
	<table width="100%">
		<tr>
			<td colspan="4" align="center">互动出版社图书推荐引擎<br />
				<!-- <div class="dashed_x" /> --></td>
		</tr>
		<tr>
			<td valign="top">
			    <br>没有帐号？<a href="register.html">注册</a>
				<table width="100%" border="0" id="login_form">
					<tr>
						<td colspan="2" style="padding-left:100px;">登录</td>
					</tr>
					<tr>
						<td align="right" width="10%">电邮/用户名:</td>
						<td width="90%"><input size="25" value="" maxlength="50"
							 id="email" onkeydown="userlogin()" /></td>
					</tr>
					<tr>
						<td align="right" width="10%">密码:</td>
						<td width="90%"><input size="25" value="" maxlength="50" id="password"
							type="password" onkeydown="userlogin()" /></td>
					</tr>
					<tr>
					   <td colspan="2" width="20%" style="padding-left:100px;">
					   <input  id="login" type="image" src="images/sign.jpg"  onclick="login()" onkeydown="userlogin()" /></td>
					</tr>
				</table>
			</td>
			</tr>
			<tr>
			
			<td width="80%" valign="middle">
				<table width="100%" border="1" class="bordered_table" id="books"
					style="display: none">
					<tr>
						<td width="50" class="items" colspan="2"><div id="userinfo"></div></td>
					</tr>
					<tr>
						<td valign="top"  class="items"><br><br>已购图书
							<div class="dashed_x"></div> <br />
							<div style="margin: 5px;" id="my_books_loading">
								<img src="images/loading.gif"></img>
							</div>
							<div id="userBooks"></div></td>
					</tr>
					<tr>
						<td valign="top"  class="items">
						    <br><br><b>图书信息</b>
							<div class="dashed_x"></div> <br />
							<div style="margin: 5px;" id="all_books_loading">
								<img src="images/loading.gif"></img>
							</div>
							<div id="allBooks"></div>
							<p><input type='button' style='float:left' value='购买结算' bIdscores='' name='purchase' id='purchase' onclick='purchaseCars(this)' />
    	    				<div style='float:right'><span><a href=''>首页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span><a href=''>上一页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span><a href=''>下一页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span><a href=''>尾页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;</div></p>
							</td>
					</tr>
					<tr>
						<td valign="top" width="50%" height="50" class="items" border="1">
						<b>基于用户推荐图书</b>
							<div class="dashed_x"></div>
							<br />
							<div style="margin: 5px;" id="userrecommand_books_loading">
								<img src="images/loading.gif"></img>
							</div>
							<div id="userrecommandatedBooks"></div></td>
							</tr>
						<tr>
						<td valign="top" width="50%" height="50" class="items" border="1">
						<b>基于图书推荐图书</b>
							<div class="dashed_x"></div>
							<br />
							<div style="margin: 5px;" id="itemrecommand_books_loading">
								<img src="images/loading.gif"></img>
							</div>
							<div id="itemrecommandatedBooks"></div></td>
							</tr>
								<tr>
						<td valign="top" width="50%" height="50" class="items" border="1">
						    <b>SlopeOne算法推荐图书</b>
							<div class="dashed_x"></div>
							<br />
							<div style="margin: 5px;" id="sloperecommand_books_loading">
								<img src="images/loading.gif"></img>
							</div>
							<div id="sloperecommandatedBooks"></div>
						</td></tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>