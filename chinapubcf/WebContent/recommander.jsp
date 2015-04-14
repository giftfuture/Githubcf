<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userId = request.getParameter("userId");
%>
<!DOCTYPE html>
<html>
<head>
  <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" contect="zh-CN">
<title>已购图书、推荐图书</title>
<link rel="stylesheet" href="style/style.css" type="text/css" />
<script src="script/prototype.js" type="text/javascript"></script>
<script src="script/ui.js" type="text/javascript"></script>
<script type="text/javascript">
/* loadUserBooks(userId);
loadUserRecommendBooks(userId);
loadItemRecommendBooks(userId);
loadSlopeRecommendBooks(userId); */ 
loadUserBooks(<%=userId%>);
loadChinapubrecommendBooks(<%=userId%>);
<%-- loadUserRecommendBooks(<%=userId%>);
loadItemRecommendBooks(<%=userId%>);
loadSlopeRecommendBooks(<%=userId%>); 
$("books").show(); --%>
var recmdlogout = function(){
  	new Ajax.Request('/chinapubcf/logout',
	  		{
	    		method:'post',
	    		onSuccess: function(transport){
					$("userBooks").innerHTML = "";
					$("chinapubrecommandatedBooks").innerHTML = "";
					$('books').hide();
					$("logoutbtn").hide();
					window.location.href="http://localhost:8080/chinapubcf/index.jsp";
	    		},
	    		onFailure: function(){ alert('登出系统失败，请检查网络是否异常...'); }
	  		});
};
</script>
</head>
<body>
<img alt="logo" src="images/cp_logo.jpg">
	<table width="100%">
		<tr>
			<td colspan="4" align="center"><b>互动出版社图书推荐引擎</b><br />
				<!-- <div class="dashed_x" /> --></td>
		</tr>
			<tr>
			<td width="80%" valign="middle">
			<span class='logout'><button id="logoutbtn" style="display:none" onclick="recmdlogout()">退出</button><!-- <a onclick="logout()" href='javascript:void(0)'>退出</a> --></span>
				<table width="100%" border="1" class="bordered_table" id="books"
					style="display: block">
					<tr>
						<td valign="top"  class="items"><br><br><b>已购图书</b>
							<div class="dashed_x"></div> <br />
							<div style="margin: 5px;" id="my_books_loading">
								<img src="images/loading.gif"></img>
							</div>
							<div id="userBooks"></div></td>
					  </tr>
					  <tr>
						<td valign="top" width="50%" height="50" class="items" border="1">
						<b>推荐图书</b>
							<div class="dashed_x"></div>
							<br />
							<div style="margin: 5px;" id="chinapubrecommand_books_loading">
								<img src="images/loading.gif"></img>
							</div>
							<div id="chinapubrecommandatedBooks"></div></td>
						</tr>
				<!-- 	<tr>
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
						</td></tr> -->
				</table>
			</td>
		</tr>
	</table>
</body>
</html>