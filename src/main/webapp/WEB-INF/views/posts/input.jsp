<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>::POSTS::</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"></script>
<style>
	#wrap{
		padding:3em;
	}
	#frm li{
		list-style:none;
		margin: 10px 5px;
	}
	#frm input,textarea,button{
		padding:7px;
	}
	#frm button{
		width:100px;
	}
	#postList ul>li{
		float:left;
		height:40px;
		border-bottom:1px solid #ddd;
		width:15%;
		list-style:none;
	}
	#postList ul>li:nth-child(5n+2){
		width:40%;
	}
</style>
<script>
	$(function(){
		showPosts();//포스트 목록 가져오기
		$('#frm').submit(function(e){
			e.preventDefault();//기본 동작 막기
			let str=$('#btn').text();
			if(str=='글쓰기'){
				addPosts();
			}else if(str=='글수정'){
				updatePosts();
			}
		})//submit()------------
	})//$() end-----------
	const addPosts=function(){
		//alert('a');
		//[1] 작성자, title값 얻어오기
		let author = $('#author').val();
		let title = $('#title').val();
		//[2] ==> json 유형의 객체로 만들기
		let data={
				title:title,
				author:author
		}
		//ajax요청 보내기. url: posts, method:post
		$.ajax({
			type:'post',
			url:'posts',
			data:data,
			dataType:'json',
			cache:false,
			success:function(res){
				//alert(JSON.stringify(res));
				if(res.result>0){
					showPosts();//전체 포스트 목록 가져오기
					$('#author').val('');
					$('#title').val('');
					$('#id').val('');
					$('#author').focus();
					
				}
			},
			error:function(err){
				alert('err: '+err.status);
			}
		})
	}//-------------------------
	const updatePosts=function(){
		//alert('update');
		let data={
				id:$('#id').val(),
				title:$('#title').val(),
				author:$('#author').val()
		}
		$.ajax({
			type:'post',
			url:'postEdit',
			data:data,
			dataType:'json',
			cache:false
		})
		.done((res)=>{
			//alert(res.result);
			if(res.result>0){
				showPosts();//전체 목록 가져오기
				$('#id').val('');
				$('#title').val('');
				$('#author').val('').focus();
				$('#frm button').text("글쓰기");
			}
		})
		.fail((err)=>{
			alert('err: '+err.status)
		})
	}//-------------------------
	const showPosts=function(){
		$.ajax({
			type:'get',
			url:'postsAll',
			dataType:'json',
			cache:false,
			success:function(res){
				//alert(JSON.stringify(res));
				renderPosts(res);
			},
			error:function(err){
				alert('err: '+err.status);
				console.log('err: '+err.responseText)
			}
		})
	}//-----------------------
	const renderPosts=function(data){
		let str = `<ul>`
		$.each(data, function(i, post){ 
			str+=`
			<li>\${post.no}</li>
			<li>\${post.title}</li>
			<li>\${post.author}</li>
			<li>\${post.wdate}</li>
			<li>
			<a href="javascript:postsEdit('\${post.id}')">수정</a>
			<a href="javascript:postsDel('\${post.id}')">삭제</a>
			</li>
			`
		}) //$ each함수는 data를 하나씩 끄집어서 i에 넣어줌
		str+=`</ul>`;
		$('#postList').html(str);
	}//------------------
	const postsEdit=function(id){
		//alert(id);
		//$('#id').val(id);
		$.ajax({
			type:'post',
			url:'postFind',
			data:{
				id:id
			},
			dataType:'json',
			cache:false,
		})
		.done(function(res){
			//alert(JSON.stringify(res));
			$('#id').val(res.id);
			$('#title').val(res.title);
			$('#author').val(res.author);
			$('#frm button').text("글수정");
		})
		.fail(function(err){
			alert('err: '+err.status);
		})
	}//---------------------
	const postsDel=function(id){
		//alert(id);
		$('#id').val(id);
		$.ajax({
			type:'post',
			url: 'postDelete',
			data:{
				id:id
			},
			cache:false,
			success:function(res){
				//alert(JSON.stringify(res));
				if(res.result>0){
					showPosts();
				}
			},
			error:function(err){
				alert('err: '+err.status);
			}
		})
	}//---------------------
</script>
</head>
<body>
<div id="wrap" class="container">
    <h1>Posts 글쓰기</h1>
    <form id="frm" name="frm">
        <input type="hidden" name="id" id="id">
        <ul>
            <li>Author: </li>
            <li>
                <input type="text" name="author" id="author" placeholder="Author" required>
            </li>
            <li>
                <textarea name="title" id="title" placeholder="Title" rows="5" cols="70"></textarea>
            </li>
            <li>
                <button id="btn">글쓰기</button>
            </li>
        </ul>
    </form>
    
    <div id="postList">
        여기에 포스트 목록 들어올 예정
    </div>
</div>
접기
</body>
</html>