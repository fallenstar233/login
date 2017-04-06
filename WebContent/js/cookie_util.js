//两个参数，一个是cookie的名字，一个是值
function setCookie(name,value){
	//此 cookie 将被保存 30 天
    var Days = 30; 
    var exp = new Date();    
    exp.setTime(exp.getTime() + Days*24*60*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}


