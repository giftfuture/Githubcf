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
<script src="script/pager.js" type="text/javascript"></script>
<script type="text/javascript">
loadAllBooks(1);
var emailstr = '<%=request.getSession().getAttribute("uemail")%>';
var namestr = '<%=request.getSession().getAttribute("username")%>';
var upwdstr = '<%=request.getSession().getAttribute("usrpwd")%>';
var pageno = <%=request.getSession().getAttribute("pageno")%>;
var bookCount = <%=request.getSession().getAttribute("bookCount")%>;
var allpages = <%=request.getSession().getAttribute("allpages")%>;
<%-- alert(<%=request.getSession().getAttribute("pageno")%>+"     "+pageno);
 --%>console.debug("pageno="+pageno);
var usrIsLogin = function(){
		var login_form = $('login_form');
	if(upwdstr!=null && upwdstr != '' && upwdstr != ' ' && upwdstr.length>25 ){
		login_form.hide();
		return true;
	}else{
		alert("请先登录网站");
		login_form.show();   
		return false;
	}
};
new Ajax.Request('/chinapubcf/sessionlogin',
  		{
    		method:'post',
    		parameters: {email: emailstr,usrpwd:upwdstr,name:namestr},
    		onSuccess: function(transport){
    			var response = transport.responseText || "no response text";
      			var data = response.evalJSON();
				var userId = data.userId;
				var name = data.name;
				var email = data.email;
				//alert(userId+name+email);
				//hide login form
				var login_form = $('login_form');
				login_form.hide();
				//render user info
				var userinfo = $('userinfo');
				var info = "欢迎您, "+name+"("+email+")"; //, your user id is "+userId;
				console.debug(info);
				userinfo.innerHTML = info;
				//show book table
				var bookTable = $('books');
				bookTable.show();
			// loadUserBooks(userId);
		     loadAllBooks(<%=request.getSession().getAttribute("pageno")%>);
			// loadUserRecommendBooks(userId);
			// loadItemRecommendBooks(userId);
			// loadSlopeRecommendBooks(userId);
    		}//,
    		//onFailure: function(){ alert('登录系统失败，请检查登录信息后重试...'); }
  		});
  	//购物车	
  var purchaseCars = function(obj){
	var bIdscores = obj.attributes["bidscores"];
	if(bIdscores!=null && bIdscores.nodeValue!=null && bIdscores.nodeValue != ''){
		//alert(bIdscores.nodeValue);
		//var userid = $("userId").getValue();
		var userId = <%=request.getSession().getAttribute("userId")%>;
		if(userId==null || userId == '' ||userId==' '||userId=='null'){
			alert("请先登录网站");
			login_form.show(); 
		}else{
			   new Ajax.Request('/chinapubcf/rating',{
		    		method:'post',
		    		parameters: {bookIdScores: bIdscores.nodeValue,userId:userId},
		    		onSuccess: function(transport){
		    			//var response = transport.responseText || "no response text";
		      			alert("购买结算成功！");
		      			$(obj).setAttribute("bIdscores", "");
		    		},
		    		onFailure: function(){ alert('购买结算失败，请检查购买信息后重试...'); }
		  	  });
		}
	}else{
		alert("购物车为空，请放入图书！");
	}
};

var logout = function(){
  	new Ajax.Request('/chinapubcf/logout',
	  		{
	    		method:'post',
	    		onSuccess: function(transport){
	    			var login_form = $('login_form');
					login_form.show();
					$('userinfo').innerHTML = "";
					$("allBooks").innerHTML = "";
					$('books').show();
					$("logoutbtn").hide();
					$("email").setValue('');
					$("password").setValue('');
					loadAllBooks(1);
	    		},
	    		onFailure: function(){ alert('登出系统失败，请检查网络是否异常...'); }
	  		});
	};
	
  var lookupbooks = function(){
	var login_form = $('login_form');
	if(upwdstr!=null && upwdstr != '' && upwdstr != ' ' && upwdstr.length>25 ){
		login_form.hide();
		window.location.href = 'recommander.jsp?userId=<%=request.getSession().getAttribute("userId")%>';
	}else{
		alert("请先登录网站");
		login_form.show();   
	};
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
			<td valign="top">
			    <br>没有帐号？<a href="register.html">注册</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="loginwin()">登录</a>
				<table width="100%" border="0" id="login_form" style="display:none">
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
					   <td colspan="2" width="20%" style="padding-left:100px">
					   <input  id="login" type="image" src="images/sign.jpg"  onclick="login()" onkeydown="userlogin()" /></td>
					</tr>
				</table>
			</td>
			</tr>
			<tr>
			<td width="80%" valign="middle">
				<table width="100%" border="1" class="bordered_table" id="books">
					<tr>
						<td width="50" class="items" colspan="2"><div id="userinfo"></div>
						<span class='logout'><button id="logoutbtn" style="display:none" onclick="logout()">退出</button><!-- <a onclick="logout()" href='javascript:void(0)'>退出</a> --></span></td>
					</tr>
				<!-- 	<tr>
						<td valign="top"  class="items"><br><br>已购图书
							<div class="dashed_x"></div> <br />
							<div style="margin: 5px;" id="my_books_loading">
								<img src="images/loading.gif"></img>
							</div>
							<div id="userBooks"></div></td>
					</tr> -->
					<tr>
						<td valign="top" class="items">
						    <button onclick="lookupbooks(this)">查看已购图书，推荐图书</button>
						    <br><br><b>图书信息</b>
							<div class="dashed_x"></div> <br />
							<div style="margin: 5px;" id="all_books_loading">
								<img src="images/loading.gif"></img>
							</div>
							<div id="allBooks"></div>
							<%-- <p><input type='button' style='float:left' value='购买结算' bIdscores='' name='purchase' id='purchase' onclick='purchaseCars(this)' />
    	    				<div style='float:right'><span><a href='javascript:void(0)' onclick="firstpage(<%=session.getAttribute("pageno")%>)">首页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span>
    	    				<a href='javascript:void(0)' onclick="prepage(<%=session.getAttribute("pageno")%>)">上一页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
    	    				<span><a onclick="nextpage(<%=session.getAttribute("pageno")%>,<%=session.getAttribute("allpages")%>)" href='javascript:void(0)'>下一页</a></span>
    	    				&nbsp;&nbsp;&nbsp;&nbsp;<span><a href='javascript:void(0)' onclick="lastpage(<%=session.getAttribute("pageno")%>,<%=session.getAttribute("allpages")%>)">尾页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;
    	    				<span>共<%=session.getAttribute("bookCount")%>条记录,当前第<%=session.getAttribute("pageno")%>页，共<%=session.getAttribute("allpages")%>页</span></div></p> --%>
							</td>
					</tr>
					<!-- <tr>
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