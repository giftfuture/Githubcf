/* Function:
 * 1. Login, render user's name on the div[id=userinfo]
 *    URL: http://host:port/chinapubcf/user?email={email}
 * 2. Load User's Book
 *	  URL: http://host:port/chinapubcf/book?userID={userID}
 * 3. Load Recommended Book
 *    URL: http://host:port/chinapubcf/recommend?userID={userID}
 */
    var emailReg = /^([a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+)?$/;
    var nameReg = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
    //注册页面验证逻辑
	var register = function(){
		var usrname = $('usrname').getValue();
	  	if(usrname==null || usrname == ""){
			alert("注册用户名不能为空。");
			return false;
		}
	  	if(!nameReg.test(usrname)||usrname.length>20||usrname.length<4){
	  		alert("用户名只能由中文、大小写英文字母、数字和下划线组成,长度4-20位");
	  		return false;
	  	}
	  	var usrpwd = $('usrpwd').getValue();
	  	if(usrpwd==null || usrpwd == ""){
			alert("注册密码不能为空。");
			return false;
		}
	  	var usrpwd2  = $('usrpwd2').getValue();	
	  	if(usrpwd2==null || usrpwd2 == ""){
			alert("注册确认密码不能为空。");
			return false;
		}
	  	if(usrpwd!=usrpwd2){
			alert("两次输入密码不一致，请核实。");
			return false;
		}
	  	var regEmail = $('email').getValue();
		if(regEmail==null || regEmail == ""){
			alert("注册邮箱不能为空。");
			return false;
		}
		if(!emailReg.test(regEmail)){
			alert("邮箱格式不正确，请核实。");
			return false;
		}
	  	var knowfrom = $('knowfrom').getValue();
	  	//prototype获取select下拉框选中项
	  	var status = $$('input[type="radio"][name="status"]').select(function(i){return i.checked });	//.each(function(i){alert(i.value)})
	  	//prototype使用getElementsByName获取“关注领域”多选框的选中项
	  	var admirefield = "";
	  		var admirechkbox = document.getElementsByName('admirefield');     
	        var admirenodes = $A(admirechkbox);
	        var admirefieldNodes =  admirenodes.select(function(node)     
	        {  
	         return node.checked;     
	        });  
	        admirefieldNodes.each(function(node)    
	        {    
	        	 admirefield += node.value+",";
	         }); 
	        //prototype使用getElementsByName获取“擅长领域”多选框的选中项
	        	var expertat = "";
		  		var expertatchkbox = document.getElementsByName('expertat');     
		        var expertatnodes = $A(expertatchkbox);     
		    var goodatnodes = expertatnodes.select(function(node)     
		        {     
		         return node.checked;     
		        });  
		    goodatnodes.each(function(node)    
		        {    
		        	expertat += node.value+",";
		           //alert(node.value);    
		        }); 
		    //注册ajax request函数
	  	new Ajax.Request('/chinapubcf/register',
	  		{
	    		method:'post',
	    		parameters: {email: regEmail,usrpwd:usrpwd,name:usrname,knowfrom:knowfrom,status:status[0].value,admirefield:admirefield,expertat:expertat,tag:"读书"},
	    		onSuccess: function(transport){
	    			//var response = transport.responseText || "no response text";
	      			alert("恭喜您注册成功！");
	      			$('email').setValue('');
	      			$('usrpwd').setValue('');
	      		  	$('usrname').setValue('');
	      		  	$('knowfrom').setValue('');
	      		  	window.location.href="http://localhost:8080/chinapubcf/index.jsp";
	    		},
	    		onFailure: function(){ alert('登录系统失败，请检查登录信息后重试...'); }
	  		});
	      
	};
	//显示登录窗口
	var loginwin = function(){
		var login_form = $('login_form');
		login_form.show();
	}
	//单击ENTER键登录系统
  var userlogin = function(){
	  if(event.keyCode==13){
		  login();
	  }
  };
  //前端登录逻辑js函数
  var login = function(){
  	var emailString = $('email').getValue();
  	var password = $('password').getValue();
      new Ajax.Request('/chinapubcf/user',
  		{
    		method:'post',
    		parameters: {email: emailString,usrpwd:password},
    		onSuccess: function(transport){
    			var response = transport.responseText || "no response text";
      			var data = response.evalJSON();
				var userId = data.userId;
				var name = data.name;
				var email = data.email;
				//hide login form
				var login_form = $('login_form');
				login_form.hide();
				
				//render user info
				var userinfo = $('userinfo');
				var info = "欢迎您, "+name+"("+email+")";//, your user id is "+userId;
				console.debug(info);
				userinfo.innerHTML = info;
				//show book table
				var bookTable = $('books');
				bookTable.show();
				$("logoutbtn").show();
			// loadUserBooks(userId);
		     loadAllBooks(1);
			 //loadUserRecommendBooks(userId);
			 //loadItemRecommendBooks(userId);
			 //loadSlopeRecommendBooks(userId);
			 //loadChinapubrecommendBooks(userId);
    		},
    		onFailure: function(){ alert('登录系统失败，请检查登录信息后重试...'); }
  		});
    };
    //加载用户已购买
    var loadUserBooks = function(user_id){
    	new Ajax.Request('/chinapubcf/books',
  		{
    		method:'post',
    		parameters: {userId: user_id},
    		encoding:  'UTF-8',
    		asynchronous: true, //�Ƿ��첽
    		requestHeaders: {Accept:'application/json'}, 
    	    contentType:  'application/x-www-form-urlencoded',
    		onSuccess: function(transport){
      		var response = transport.responseText || "no response text";
      		console.debug("loadUserBooksresponse", response);
      		var data = response.evalJSON();//Object.toJSON(response);///
      		console.debug("data", data);
      		//alert(data);
			renderBooks(data, "my_books_loading", "userBooks");
			//renderBooks(response, "my_books_loading", "userBooks");
    		},
    		onFailure: function(){ alert('用户已购图书加载出错...'); }
  		});
    };
    
    var loadAllBooks = function(pagenono){
    	new Ajax.Request('/chinapubcf/booklist',
  		{
    		method:'post',
    		parameters: {pageno: pagenono},
    		encoding:  'UTF-8',
    		asynchronous: true, //�Ƿ��첽
    		requestHeaders: {Accept:'application/json'}, 
    	    contentType:  'application/x-www-form-urlencoded',
    		onSuccess: function(transport){
	      		var response = transport.responseText || "no response text";
	      		console.debug("loadAllBooks:response", response);
	      		var data = response.evalJSON();//Object.toJSON(response);///
	      		//alert("loadAllBooks="+pageno+data);
	      		console.debug("data", data);
	      		renderAllBooks(pagenono,data, "all_books_loading", "allBooks");
    		},
    		onFailure: function(){ alert('网站图书信息加载出错...'); }
  		});
    };
    
    /**基于其他用户信息为用户推荐图书信息*/
  /*  var loadUserRecommendBooks = function(user_id){
    	new Ajax.Request('/chinapubcf/userrecmd',
  		{
    		method:'post',
    		parameters: {userId: user_id, format:"json",count:30},
    		onSuccess: function(transport){
      		var response = transport.responseText || "no response text";
      			console.debug("response", response);
      			var data = response.evalJSON();
      			console.debug("data", data);
				renderBooks(data, "userrecommand_books_loading", "userrecommandatedBooks");
    		},
    		onFailure: function(){ alert('基于其他用户信息为用户推荐图书信息加载出错...'); }
  		});
    };*/
    /**基于图书信息为用户推荐图书信息*/
 /*   var loadItemRecommendBooks = function(user_id){
    	new Ajax.Request('/chinapubcf/itemrecmd',
  		{
    		method:'post',
    		parameters: {userId: user_id, format:"json",count:30},
    		onSuccess: function(transport){
      		var response = transport.responseText || "no response text";
      			console.debug("response", response);
      			var data = response.evalJSON();
      			console.debug("data", data);
				renderBooks(data, "itemrecommand_books_loading", "itemrecommandatedBooks");
    		},
    		onFailure: function(){ alert('基于图书信息为用户推荐图书信息加载出错...'); }
  		});
    };*/
    /**SlopeOne算法为用户推荐图书信息*/
/*    var loadSlopeRecommendBooks = function(user_id){
    	new Ajax.Request('/chinapubcf/sloperecmd',
  		{
    		method:'post',
    		parameters: {userId: user_id, format:"json",count:30},
    		onSuccess: function(transport){
      		var response = transport.responseText || "no response text";
      			console.debug("response", response);
      			var data = response.evalJSON();
      			console.debug("data", data);
				renderBooks(data, "sloperecommand_books_loading", "sloperecommandatedBooks");
    		},
    		onFailure: function(){ alert('SlopeOne推荐算法为用户推荐图书信息加载出错...'); }
  		});
    };*/
    
    /**综合三种推荐算法为用户推荐图书信息*/
    var loadChinapubrecommendBooks = function(user_id){
    	new Ajax.Request('/chinapubcf/chinapub',
  		{
    		method:'post',
    		parameters: {userId: user_id, format:"json",count:30},
    		onSuccess: function(transport){
      		var response = transport.responseText || "no response text";
      			console.debug("response", response);
      			var data = response.evalJSON();
      			console.debug("data", data);
				renderBooks(data, "chinapubrecommand_books_loading", "chinapubrecommandatedBooks");
    		},
    		onFailure: function(){ alert('推荐算法为用户推荐图书信息加载出错...'); }
  		});
    };
    
    var renderBooks = function(data, loading_id, dom_id){
    	if(Object.isArray(data)){
    		console.debug("data is array");
    		var loading = $(loading_id);
    		var node = $(dom_id);
    		var table = document.createElement("table");
//    		var fragment = document.createDocumentFragment();			
    		//table.setAttribute("id", dom_id+"table");
    		//Element.addClassName(table, "book_table");
    		var tablehead = document.createElement("thead");
    		var head_content = '<tr class="odd_row"><td align="center"><b>书名</b></td><td align="center"><b>作者</b></td><td align="center"><b>译者</td><td align="center"><b>丛书名</td><td align="center"><b>出版社</b></td><td align="center"><b>出版日期</b></td><td align="center"><b>版次</b></td><td align="center"><b>上架时间</b></td><td align="center"><b>所属分类</b></td><td align="center"><b>定价</b></td><td align="center"><b>销量</b></td><td align="center"><b>评分</b></td></tr>';
    		//alert(head_content);
    		Element.insert(tablehead, head_content);
    		table.appendChild(tablehead);
//    		tablehead.innerHTML='<tr><td width="40%">Movie Name</td><td width="25%">Published Year</td><td width="25%">Movie Type</td><td width="10%">Score</td></tr>';
    		var tablebody = document.createElement("tbody");
    		for (var index = 0, len = data.length; index < len; ++index) {
  				var item = data[index];
  				var name = item.book.name;
  				var author = item.book.author;
  				var translator = item.book.translator;
  				var seriesName = item.book.seriesName;
  				var press = item.book.press;
  				var pressTime = item.book.pressTime;
  				var version = item.book.version;
  				var shelfTime = item.book.shelfTime;
  				var category = item.book.category;
  				var price = item.book.price;
  				var sales = item.book.sales;
  				var score = item.score;
  				var tr = document.createElement("tr");
  				if(index%2 == 0){
  					tr.style.backgroundColor='#DDDDEE';
  				}
  				var content = "<td align='center'>"+name+"</td><td align='center'>"+author+"</td><td align='center'>"+translator+"</td><td align='center'>"+seriesName+"</td><td align='center'>"+press+"</td><td align='center'>"+pressTime+"</td><td align='center'>"+version+"</td><td align='center'>"+shelfTime+"</td><td align='center'>"+category+"</td><td align='center'>"+price+"</td><td align='center'>"+sales+"</td><td align='center'>"+score+"</td>";	
  			//	alert(content);
  				Element.insert(tr, content);
//  			tr.innerHTML = "<td>"+movie.name+"</td><td>"+movie.publish_year+"</td><td>"+movie.type+"</td><td>"+score+"</td>";
  				tablebody.appendChild(tr);
			}
			table.appendChild(tablebody);
			node.appendChild(table);
			loading.hide();

   		} else {
   			/*var content = "<td align='center'>  </td><td align='center'>  </td><td align='center'>  </td><td align='center'>  </td><td align='center'>  </td><td align='center'> </td><td align='center'> </td><td align='center'> </td><td align='center'> </td><td align='center'></td><td align='center'> </td><td align='center'> </td>";	
   			Element.insert(tr, content);
   			tablebody.appendChild(tr);
   			table.appendChild(tablebody);
			node.appendChild(table);
			loading.hide();*/
   			console.debug("data is not array");
   		}
    };
    var value_ms = function(obj){
    	var val = $(obj).getValue();
    	if(val>=2){
    		val = parseInt(val)-1;
    		$(obj).setValue(val);
    	}
    }
    var value_up = function(obj){
    	var val = $(obj).getValue();
    	val = parseInt(val)+1;
    	$(obj).setValue(val);
    }
    		//分页加载所有图书信息
    	   var renderAllBooks = function(pagenono,data, loading_id, dom_id){
    		 //  alert(pageno+"renderAllBooks"+data);
    	    	if(Object.isArray(data)){
    	    		console.debug("renderAllBooks data is array");
    	    		var loading = $(loading_id);
    	    		var node = $(dom_id);
    	    		var table = document.createElement("table");
    	    		var tablehead = document.createElement("thead");
    	    		var head_content = '<tr class="odd_row"><td colspan="2" align="center"><b>加入购物车</b></td><td align="center"><b>书名</b></td><td align="center"><b>作者</b></td><td align="center"><b>译者</td><td align="center"><b>丛书名</td><td align="center"><b>出版社</b></td><td align="center"><b>出版日期</b></td><td align="center"><b>版次</b></td><td align="center"><b>上架时间</b></td><td align="center"><b>所属分类</b></td><td align="center"><b>定价</b></td><td align="center"><b>销量</b></td><td align="center"><b>请评分</b></td></tr>';
    	    		Element.insert(tablehead, head_content);
    	    		table.appendChild(tablehead);
    	    		var tablebody = document.createElement("tbody");
    	    		for (var index = 0, len = data.length; index < len; ++index) {
    	  				var book = data[index];
    	  				var bookId = book.bookId;
    	  				var name = book.name;
    	  				var author = book.author;
    	  				var translator = book.translator;
    	  				var seriesName = book.seriesName;
    	  				var press = book.press;
    	  				var pressTime = book.pressTime;
    	  				var version = book.version;
    	  				var shelfTime = book.shelfTime;
    	  				var category = book.category;
    	  				var price = book.price;
    	  				var sales = book.sales;
    	  				var tr = document.createElement("tr");
    	  				if(index%2 == 0){
    	  					tr.style.backgroundColor='#DDDDEE';
    	  				}
    	  				var score = "<select name='score"+bookId+"' id='score"+bookId+"'><option value='0.0'>0.0</option><option value='0.1'>0.1</option><option value='0.2'>0.2</option><option value='0.3'>0.3</option><option value='0.4'>0.4</option><option value='0.5'>0.5</option><option value='0.6'>0.6</option><option value='0.7'>0.7</option><option value='0.8'>0.8</option><option value='0.9'>0.9</option>" +
    	  						"<option value='1.0'>1.0</option><option value='1.1'>1.1</option><option value='1.2'>1.2</option><option value='1.3'>1.3</option><option value='1.4'>1.4</option><option value='1.5'>1.5</option><option value='1.6'>1.6</option><option value='1.7'>1.7</option><option value='1.8'>1.8</option><option value='1.9'>1.9</option>" +
    	  						"<option value='2.0'>2.0</option><option value='2.1'>2.1</option><option value='2.2'>2.2</option><option value='2.3'>2.3</option><option value='2.4'>2.4</option><option value='2.5'>2.5</option><option value='2.6'>2.6</option><option value='2.7'>2.7</option><option value='2.8'>2.8</option><option value='2.9'>2.9</option>" +
    	  						"<option value='3.0'>3.0</option><option value='3.1'>3.1</option><option value='3.2'>3.2</option><option value='3.3'>3.3</option><option value='3.4'>3.4</option><option value='3.5'>3.5</option><option value='3.6'>3.6</option><option value='3.7'>3.7</option><option value='3.8'>3.8</option><option value='3.9'>3.9</option>" +
    	  						"<option value='4.0'>4.0</option><option value='4.1'>4.1</option><option value='4.2'>4.2</option><option value='4.3'>4.3</option><option value='4.4'>4.4</option><option value='4.5'>4.5</option><option value='4.6'>4.6</option><option value='4.7'>4.7</option><option value='4.8'>4.8</option><option value='4.9'>4.9</option><option value='5.0'>5.0</option></select>";
    	  				var content = "<td><input type='button' value='加入购物车' onclick='purchaseBooks("+bookId+")'/></td><td class='number'>" +
    	  				"<a class='lower_book_q' alt='减少数量' onclick='value_ms(\"booknum"+bookId+"\")'></a>"+
    	  				"<input type='text' class='text' value='1' id='booknum"+bookId+"' name='booknum"+bookId+"'  onkeyup=\"value=value.replace(/[^0-9\.]/g,'')\" onpaste='javascript:return false;'>"+
    	  				"<a class='add_book_q' alt='增加数量' onclick='value_up(\"booknum"+bookId+"\")'></a>"+
    	  				"</td><td align='center'>"+name+"</td><td align='center'>"+author+"</td><td align='center'>"+translator+"</td><td align='center'>"+seriesName+"</td><td align='center'>"+press+"</td><td align='center'>"+pressTime+"</td><td align='center'>"+version+"</td><td align='center'>"+shelfTime+"</td><td align='center'>"+category+"</td><td align='center'>"+price+"</td><td align='center'>"+sales+"</td><td align='center'>"+score+"</td>";	
    	  				Element.insert(tr, content);
    	  		//  	tr.innerHTML = "<td>"+movie.name+"</td><td>"+movie.publish_year+"</td><td>"+movie.type+"</td><td>"+score+"</td>";
    	  				tablebody.appendChild(tr);
    	  			}
    	    		var buybtn ="<td colspan='14'><input type='button' style='float:left' value='购买结算' bIdscores='' name='purchase' id='purchase' onclick='purchaseCars(this)' />"+
    	    				"<div style='float:right'><span><a href='javascript:void(0)' onclick='firstpage("+pagenono+")'>首页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;<span>"+
    	    				"<a href='javascript:void(0)' onclick='prepage("+pagenono+")'>上一页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;"+
    	    				"<span><a onclick='nextpage("+pagenono+","+allpages+")' href='javascript:void(0)'>下一页</a></span>"+
    	    				"&nbsp;&nbsp;&nbsp;&nbsp;<span><a href='javascript:void(0)' onclick='lastpage("+pagenono+","+allpages+")'>尾页</a></span>&nbsp;&nbsp;&nbsp;&nbsp;"+
    	    				"<span>共"+bookCount+"条记录,当前第"+pagenono+"页，共"+allpages+"页</span></div></td>";
    	    		var tr = document.createElement("tr");
    	    		Element.insert(tr, buybtn);
    	    		tablebody.appendChild(tr);
    				table.appendChild(tablebody);
    				//alert(pageno+table.outerHTML);
    				$("allBooks").innerHTML= table.outerHTML;
    				//node.appendChild(table);
    				loading.hide();

    	   		} else {
    	   			/*var content = "<td align='center'>  </td><td align='center'>  </td><td align='center'>  </td><td align='center'>  </td><td align='center'>  </td><td align='center'> </td><td align='center'> </td><td align='center'> </td><td align='center'> </td><td align='center'></td><td align='center'> </td><td align='center'> </td>";	
    	   			Element.insert(tr, content);
    	   			tablebody.appendChild(tr);
    	   			table.appendChild(tablebody);
    				node.appendChild(table);
    				loading.hide();*/
    	   			console.debug("data is not array");
    	   		}
    };

    
    var purchaseValid = function(bookId){
    	var buyScore = document.getElementById("score"+bookId);
    	var ratingScore = buyScore.value;
    		//$("score"+bookId).getValue();
    	//alert(ratingScore);
    	//$('purchase')
    	var puttoCars = document.getElementById("purchase").attributes["bIdscores"];
    	if(ratingScore=='0.0'){
    		alert("请对要购买图书进行评分.");
    		return false;
    	}else{
    		puttoCars.nodeValue+=bookId+"-"+ratingScore+",";
    		document.getElementById("purchase").setAttribute("bIdscores", puttoCars.nodeValue);
    		alert("已放入购物车。");
    	}
    };
    
    var purchaseBooks = function(bookId){
    	if(usrIsLogin()){    		
    		purchaseValid(bookId);
    	}
    };
    
 /*   var purchaseCars = function(obj,usrId){
    	var bIdscores = obj.attributes["bIdscores"];
    	//alert(bIdscores.nodeValue);
    	new Ajax.Request('/chinapubcf/rating',
    	  		{
    	    		method:'post',
    	    		parameters: {bookIdScores: bIdscores.nodeValue,userId:userid,},
    	    		onSuccess: function(transport){
    	    			//var response = transport.responseText || "no response text";
    	      			alert("购买结算成功！");
    	      			$(obj).setAttribute("bIdscores", "");
    	    		},
    	    		onFailure: function(){ alert('购买结算失败，请检查购买信息后重试...'); }
    	  		});
    };*/

