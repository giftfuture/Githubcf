/* Function:
 * 1. Login, render user's name on the div[id=userinfo]
 *    URL: http://host:port/chinapubcf/user?email={email}
 * 2. Load User's Book
 *	  URL: http://host:port/chinapubcf/book?userID={userID}
 * 3. Load Recommended Book
 *    URL: http://host:port/chinapubcf/recommend?userID={userID}
 */
  var userlogin = function(){
	  if(event.keyCode==13){
		  login();
	  }
  }
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
				var info = "Hi, "+name+"("+email+"), your user id is "+userId;
				console.debug(info);
				userinfo.innerHTML = info;
				
				//show book table
				var bookTable = $('books');
				bookTable.show();
				loadUserBooks(userId);
				loadRecommendBooks(userId);
    		},
    		onFailure: function(){ alert('login went wrong...'); }
  		});
      
    }
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
			renderBooks(data, "my_books_loading", "userBooks");
			//renderBooks(response, "my_books_loading", "userBooks");
    		},
    		onFailure: function(){ alert('loadUserBooks went wrong...'); }
  		});
    };
    
    var loadRecommendBooks = function(user_id){
    	new Ajax.Request('/chinapubcf/recommand',
  		{
    		method:'post',
    		parameters: {userId: user_id, format:"json",count:30},
    		onSuccess: function(transport){
      		var response = transport.responseText || "no response text";
      			console.debug("response", response);
      			var data = response.evalJSON();
      			console.debug("data", data);
				renderBooks(data, "recommand_books_loading", "recommandatedBooks");
    		},
    		onFailure: function(){ alert('loadRecommendBooks went wrong...'); }
  		});
    };
    var renderBooks = function(data, loading_id, dom_id){
    	if(Object.isArray(data)){
    		console.debug("data is array");
    		var loading = $(loading_id);
    		var node = $(dom_id);
//    		var fragment = document.createDocumentFragment();			
    		var table = document.createElement("table");
    		//table.setAttribute("id", dom_id+"table");
    		//Element.addClassName(table, "book_table");
    		var tablehead = document.createElement("thead");
    		var head_content = '<tr class="odd_row"><td><b>Book Name</b></td><td><b>Author</b></td><td><b>Translator</td><td><b>SeriesName</td><td><b>Press</b></td><td><b>PressTime</b></td><td><b>Version</b></td><td><b>ShelfTime</b></td><td><b>Category</b></td><td><b>Price</b></td><td><b>Sales</b></td><td><b>Score</b></td></tr>';
    		//alert(head_content);
    		Element.insert(tablehead, head_content);
//    		tablehead.innerHTML='<tr><td width="40%">Movie Name</td><td width="25%">Published Year</td><td width="25%">Movie Type</td><td width="10%">Score</td></tr>';
    		table.appendChild(tablehead);
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
  				var content = "<td>"+name+"</td><td>"+author+"</td><td>"+translator+"</td><td>"+seriesName+"</td><td>"+press+"</td><td>"+pressTime+"</td><td>"+version+"</td><td>"+shelfTime+"</td><td>"+category+"</td><td>"+price+"</td><td>"+sales+"</td><td>"+score+"</td>";	
  			//	alert(content);
  				Element.insert(tr, content);
//  			tr.innerHTML = "<td>"+movie.name+"</td><td>"+movie.publish_year+"</td><td>"+movie.type+"</td><td>"+score+"</td>";
  				tablebody.appendChild(tr);
			}
			table.appendChild(tablebody);
			node.appendChild(table);
			loading.hide();

   		} else {
   			console.debug("data is not array");
   		}
    }
