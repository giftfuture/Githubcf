/**
 * pageination function
 */
/**第一页*/
function firstpage(pageno){
	if(parseInt(pageno)<=1){
		pageno = 1;
		alert("当前已是第一页");
	}else{
		pageno = 1;
		$("allBooks").innerHTML="";
		loadAllBooks(1);
	}
}
 /**上一页*/
function prepage(pageno){
	if(parseInt(pageno)<=1){
		pageno = 1;
		alert("当前已是第一页");
	}else{
		$("allBooks").innerHTML="";
		loadAllBooks(parseInt(pageno)-1);
	}
}
/**下一页*/
function nextpage(pageno,allpages){
	if(parseInt(pageno)>=parseInt(allpages)){		
		pageno = parseInt(allpages);
		alert("当前已是最后一页");
	}else{
		$("allBooks").innerHTML="";
	//	alert("parseInt(pageno)+1="+(parseInt(pageno)+1));
		loadAllBooks(parseInt(pageno)+1);
	}
}
/**最后一页*/
function lastpage(pageno,allpages){
	if(parseInt(pageno)>=parseInt(allpages)){
		pageno = parseInt(allpages);
		alert("当前已是最后一页");
	}else{
		$("allBooks").innerHTML="";
		loadAllBooks(allpages);
	}
}