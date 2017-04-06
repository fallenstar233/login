/* 登录界面JS脚本程序 */

$(function(){
	//为登录按钮绑定单击事件
	$('#login').click(loginAction);
	//用户名和密码框获得焦点时清空提示，失去焦点时按情况给与提示
	$('#count').focus(clear).blur(checkName);
	$('#password').focus(clear).blur(checkPassword);
});

//登录按钮的动作
function loginAction(){
	//收集用户名和密码数据
	var name = $('#count').val();
	var password = $('#password').val();
	//验证用户名和密码
	var pass = checkName()+checkPassword();  
	//pass!=2时说明 用户名或者密码中有一个是false
	if(pass!=2){
		return;
	}
	//用户名和密码 这些数据打包
	var paramter={'name':name, 'password':password};
	//发送ajax请求
	$.ajax({
		//请求路径
		url:'user/login.do',
		//需要发送给服务器的数据
		data:paramter,
		//告知服务器，发送数据的类型
		dataType:'json',
		//请求的类型
		type:'POST',
		//成功时回调这个函数
		success:function(result){
			if(result.state==0){
				//console.log("SUCCESS");
				//console.log(result.data);
				//获得返回结果了的data
				var user = result.data;
				//设置cookie
				setCookie("userId",user.id);
				location.href='success.html';
				return;
			}else if(result.state==2){
				$('#count-msg').html(result.message);
				return;
			}else if(result.state==3){
				$('#password-msg').html(result.message);
				return;
			}
			alert(result.message);
		},
		error:function(){
			alert('Ajax请求失败');
		}
	});
	
}
//获得焦点时清空右侧提示的函数
function clear(){
	$(this).next().empty();
}

function checkName(){
	var name = $('#count').val();
	if(name==null || name==""){
		$('#count-msg').html('不能空');
		return false;
	}
	var reg = /^\w{3,10}$/;
	if(! reg.test(name)){
		$('#count-msg').html('长度不对');
		return false;
	}
	return true;
} 

function checkPassword(){
	var password = $('#password').val();
	if(password==null || password==""){
		$('#password-msg').html('不能空');
		return false;
	}
	var reg = /^\w{3,10}$/;
	if(! reg.test(password)){
		$('#password-msg').html('长度不对');
		return false;
	}
	return true;
}






