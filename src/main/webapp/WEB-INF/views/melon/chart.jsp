<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>::Melon Chart::</title>
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.min.js"></script>
<!-- google chart cdn -->
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<!-- ----------------------------------------------- -->
<style>
	#wrap{
		width:90%;
		margin:2em auto;
		padding:2em;
		/* border:1px solid red; */
	}
	#menu{
		width:100%;
		text-align:center;
		margin-bottom:1em;
	}
	#melonList{
		width:49%;
		/* border:1px solid blue; */
		float:left;
	}
	#melonList ul.melon_rank>li{
		list-style:none;
		float:left;
		height:120px;
		margin-bottom:5px;
	}
	#melonList ul.melon_rank>li:nth-child(3n+1){
		width:10%;
		font-weight:bold;
		font-size:1.7em;
		color:#ddd;
	}
	#melonList ul.melon_rank>li:nth-child(3n+2){
		width:25%;
	}
	#melonList ul.melon_rank>li:nth-child(3n){
		width:65%;
		font-size:1.2em;
		font-weight:bold;
	}
	#melonSingerCnt{
		width:49%;
		/* border:1px solid red; */
		float:right;
	}
	.singerCnt li{
		list-style:none;
		height: 30px;
		line-height:30px;
		margin-bottom:5px;
		font-size:1.2em;
	}
</style>
<script>
	$(()=>{
		//구글 차트 로드
		//Load the Visualization API and the corechart package.
		google.charts.load('current', {'packages':['corechart','bar']});
		getMelonList();
		$('#btn1').on('click', ()=>{
			$.ajax({
				type:'get',
				url:'crawling',
				dataType:'json',
				cache:false
			})
			.done((res)=>{
				//alert(JSON.stringify(res));
				if(res.result==100){
					$('#melonList').html("<h2>크롤링 완료</h2>")
					$('#btn2').trigger('click');//크롤링 완료 후 멜론 차트 목록 클릭 이벤트 발생
				}
			})
			.fail((err)=>{
				alert('err: '+err.status);
			})
		})//btn1----------------
		
		$('#btn2').on('click', ()=>{
			//alert('Hi');
			getMelonList();
		})//btn2--------------
		$('#btn3').on('click', ()=>{
			//alert('Hi');
			getMelonChart();
		})//btn3--------------
	})//$() end-----------------
	
	function getMelonChart(){
		$.ajax({
			type:'get',
			url:'aggregate',
			dataType:'json',
			cache:false
		})
		.done((res)=>{
			//alert(JSON.stringify(res))
			showSingerCount(res);
		})
		.fail((err)=>{
			alert('err: '+err.status); 
		})
	}//--------------------
	
	function showSingerCount(jsonArr){
		// Create the data table.
		var data = new google.visualization.DataTable();
	    data.addColumn('string', 'Singer');//컬럼 정보 (자료형, 컬럼명)
	    data.addColumn('number', 'Song Count');
		
	    let mydata=[];//2차원 배열로 데이터를 담자
	    
		let str="<ul class='singerCnt'>";
		$.each(jsonArr,(i, obj)=>{
			let rowData = [obj.singer, obj.singerCnt];//행 데이터
			mydata.push(rowData);//mydata에 저장
			str+=`
			<li>
			\${obj.singer} : \${obj.singerCnt}
			</li>
			`
		})
		console.log('mydata:',mydata);
		data.addRows(mydata);//그래프로 표현할 데이터를 추가
		renderChart(data); // 그래프로 그려주는 함수 호출
		str+="</ul>";
		$('#singerList').html(str);
	}//--------------------
	
	function renderChart(data){
		// Set chart options
		var options = {
		        'title': '멜론 차트 가수별 노래수',
		        'width': '100%',
		        'height': 600,
		        'fontSize':11,
		        'fortName': 'Verdana'
		      };
		//Instantiate and draw out chart, passing in some options
		var chart = new google.visualization.PieChart(document.getElementById('myPieChart'));
		chart.draw(data, options);

		var chart2 = new google.visualization.BarChart(document.getElementById('myBarChart'));
		chart2.draw(data, options);
	}//--------------------------
	
	function getMelonList(){
		$.ajax({
			type:'get',
			url:'melonList',
			dataType:'json',
			cache:false
		})
		.done((res)=>{
			//alert(JSON.stringify(res))
			if(res.length==0){
				alert('크롤링을 먼저 하세요');
				return;
			}
			showList(res);
		})
		.fail((err)=>{
			alert('err: '+err.status); 
		})
	}//--------------------
	
	function showList(jsonArr){
		let str="<ul class='melon_rank'>";
		$.each(jsonArr,(i, item)=>{
			str+=`
				<li>\${item.rank}</li>
				<li><img src="\${item.albumImage}"></img></li>
				<li>
					<span class="title" >\${item.title}</span><br>
					<span class="singer">[\${item.singer}]</span>
				</li>
			`
		})
		str+="</ul>";
		$('#melonList').html(str);
	}
</script>
</head>
<body>
	<div id="wrap">
		<div id="menu">
			<h1>오늘의 Melon Chart - ${today}</h1>
			<button id = "btn1">멜론 차트 크롤링</button>
			<button id = "btn2">멜론 차트 목록</button>
			<button id = "btn3">멜론 차트 가수별 노래</button>
		</div>
		<div id="melonList">
		<!-- 여기에 멜론 차트 목록 들어옴 -->
		
		</div>
		<div id="melonSingerCnt">
		<!-- 가수별 노래 수 들어옴 -->
			<div id="singerList">
			
			</div>
			<div id="myBarChart">
	
			</div>
			<div id="myPieChart">
	
			</div>
		</div>
	
	</div>
</body>
</html>