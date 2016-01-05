<#include "/ftl/template/front/contants.ftl">
<#import "/ftl/template/front/template.ftl" as template>

<@template.html title="仪表盘">
	 <!-- DataTables CSS -->
	 <link rel="stylesheet" type="text/css" href="${statics}/statics/lib/DataTables-1.10.7/media/css/jquery.dataTables.min.css?version=${version}">
	 <script src="${statics}/statics/popsicle/js/sys.js?version=${version}"></script>
<script>
		selectMen('share');
</script>
<style type='text/css'>
    body {
        padding-top: 50px;
    }
    form label.error{width: 200px;margin-left: 10px; color: Red;}
    p{ word-wrap:break-word; word-break:normal;word-break:break-all; }
</style>	
	<div class="container">
		<div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">${tg.name}</h1>
            </div>
        </div>
        <div class='row'>
	        <div class='col-md-12 kschema_data_btns'>
				<button type="button" class="btn btn-default btn-xs kschema_data">1M</button>
				<button type="button" class="btn btn-primary btn-xs kschema_data">5M</button>
				<button type="button" class="btn btn-success btn-xs kschema_data">15M</button>
				<button type="button" class="btn btn-info btn-xs kschema_data">30M</button>
				<button type="button" class="btn btn-warning btn-xs kschema_data">1H</button>
				<button type="button" class="btn btn-danger btn-xs kschema_data">4H</button>
				<button type="button" class="btn btn-link btn-xs kschema_data" id="kschema_data_init">1D</button>
				<button type="button" class="btn btn-link btn-xs kschema_data">1W</button>
				<button type="button" class="btn btn-link btn-xs kschema_data">1MONTH</button>
	        </div>
	    </div>
	    <div class='row jumbotron'>
	    	<!--col-md-offset-1-->
	        <div class='col-md-7' id="k" style="height:200px;"></div>
	        <div class='col-md-5' id="trend" style="height:200px;"></div>
	    </div>

	    <div class="row">
	        <div class='col-md-6'>
		        <div>
		        	<span class="bg-info">'开仓'</span>
			        <span id="openPosition" class="bg-info"></span>
			        <span id="openTime" class="bg-info"></span>
		        </div>
		        <div>
			    	<span class="bg-info">'了结'</span>
			        <span id="closePosition" class="bg-info"></span>
			        <span id="closeTime" class="bg-info"></span>
		        </div>
		        <div id="second" style="height:300px;"></div>
		        <div>
		        	<form id="talkEditor_form" action="${ctx}/front/talk/save" method="POST">
		        		<input type="hidden" name="objectId" value="${tg.id}"/>
		        		<input type="hidden" id="targetId" name="targetId" value="0"/>
		        		<input type="hidden" id="targetStr" name="targetStr" value=""/>
		        		
		        		
		        		<div>
		        			<textarea name="content" id="talkEditor"></textarea>
		        		</div>
	        		  <p class="text-right">
	        		  	<@shiro.guest>
	        		  		<a class="btn btn-warning btn-xs" href="/pop/login" target="_blank">登录</a>
	        		  	</@shiro.guest>
	        		  	<button id="talk_btn_submit" class="btn btn-primary btn-xs">发言</button>
	        		  </p>
	        		</form>  
	        	</div>
	        </div>
	        <div class='col-md-6'>
	        	<div id="talkList${tg.id}"></div>
        	    <ul class="pager">
			      <li><a class="list_more">更多</a></li>
			    </ul>
	        </div>
	    </div>
	</div>


<script src="${statics}/statics/lib/echarts-2.2.7/echarts-all.js"></script>
<script src="${statics}/statics/lib/moment/moment.min.js"></script>
<script src="${statics}/statics/front/js/chartDataParser.js"></script>

<script src="${statics}/statics/lib/jquery-form/jquery.form.js?version=${version}"></script>
<script type="text/javascript" src="${statics}/statics/lib/jquery-validation/dist/jquery.validate.min.js"></script>
<script type="text/javascript" src="${statics}/statics/lib/jquery-validation/src/localization/messages_zh.js"></script>	
 <script src="${statics}/statics/lib/ckeditor4.5.4/ckeditor.js"></script>
    

<script type="text/javascript">
		var lastId = 0;
		function genHtmlTalk(talk){
			var html = '<div class="media" style="margin-bottom: 5px;" data-id="'+talk.id+'">'
				+'<div class="media-left">'
				+' <a href="#"><img class="media-object" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB3aWR0aD0iNjQiIGhlaWdodD0iNjQiIHZpZXdCb3g9IjAgMCA2NCA2NCIgcHJlc2VydmVBc3BlY3RSYXRpbz0ibm9uZSI+PCEtLQpTb3VyY2UgVVJMOiBob2xkZXIuanMvNjR4NjQKQ3JlYXRlZCB3aXRoIEhvbGRlci5qcyAyLjYuMC4KTGVhcm4gbW9yZSBhdCBodHRwOi8vaG9sZGVyanMuY29tCihjKSAyMDEyLTIwMTUgSXZhbiBNYWxvcGluc2t5IC0gaHR0cDovL2ltc2t5LmNvCi0tPjxkZWZzPjxzdHlsZSB0eXBlPSJ0ZXh0L2NzcyI+PCFbQ0RBVEFbI2hvbGRlcl8xNTBjYjZjMThhMSB0ZXh0IHsgZmlsbDojQUFBQUFBO2ZvbnQtd2VpZ2h0OmJvbGQ7Zm9udC1mYW1pbHk6QXJpYWwsIEhlbHZldGljYSwgT3BlbiBTYW5zLCBzYW5zLXNlcmlmLCBtb25vc3BhY2U7Zm9udC1zaXplOjEwcHQgfSBdXT48L3N0eWxlPjwvZGVmcz48ZyBpZD0iaG9sZGVyXzE1MGNiNmMxOGExIj48cmVjdCB3aWR0aD0iNjQiIGhlaWdodD0iNjQiIGZpbGw9IiNFRUVFRUUiLz48Zz48dGV4dCB4PSIxMi41IiB5PSIzNi44Ij42NHg2NDwvdGV4dD48L2c+PC9nPjwvc3ZnPg==" alt="图像"></a>'
				+'</div>'
				+'<div class="media-body well well-sm">'
				+'<ul class="list-inline"><li><h4 class="media-heading">'+talk.talker+'</h4></li><li><small>'+talk.createTime+'</small></li><li style="display:none;"><span class="label label-default">回复</span></li></ul>'
				+'<small>'+talk.targetStr+'</small><p>'+talk.content+'</p>';
			if(talk.longContent){
				html +='<p class="text-right"><a data-key="'+talk.longContentKey+'" class="view_talk_long_content">详情</a></p>';
			}
			html +='</div>'
				+'</div>';
			return html;
		}
		function genHtmlTalkList(targetEl,talks){
			var html = '<ul class="media-list">'
				+'<li class="media">';
				$.each(talks,function(index,obj){
					html+=genHtmlTalk(obj);
				});
				html +='</li>'+'</ul>';
			return html;
		}
		
		setInterval("updateList('talkList${tg.id}',${tg.id});",10000);//1000为1秒钟
		function updateList(elId,objectId){
			$.ajax({
				url:'${ctx}/view/latest/'+objectId+'/'+lastId,
				type:'post',
				dataType:"json", 
				success:function(data){
					var html = '';
					$.each(data,function(index,i){
						if(index==0){
							lastId = i.id;
						}
						
						html+='<li>'+genHtmlTalk(i)+'</il>';
						
						$('#'+elId+' ol').prepend(html).slideDown("slow");
						$('#'+elId+' ol li:last').remove().slideDown("slow");
						html = '';
					});
					
				}
			});
		}
		
$(function () { 

	$('.pager').on('click','.list_more',function(){
		var _t = this;
		var id = $('#talkList${tg.id} .media:last').attr('data-id');
		if(id&&id>0){
			$.ajax({
				url:'${ctx}/view/before/${tg.id}/'+id,
				type:'post',
				dataType:"json", 
				success:function(data){
					var html = '';
					if(data&&data.length>0){
						$.each(data,function(index,i){
							html+='<li>'+genHtmlTalk(i)+'</il>';
							$('#talkList${tg.id} ol').append(html).slideDown("slow");
							html = '';
						});
					}else{
						$(_t).parent().parent().remove();
					}
				}
			});
		}else{
			$(_t).parent().parent().remove();
		}
	});
		
		var editor = CKEDITOR.replace( 'talkEditor', {
		    customConfig: '${statics}/statics/front/js/ckeditor/config_base.js'
		});

		$("#talkEditor_form").validate({
			rules: {
				content: {required:true,minlength: 5,maxlength:1000}
			},
			messages: {
	            content: {
	                required: "内容必须填写",
	                minlength: "内容不能小于{0}个字符",
	                maxlength: "内容不能大于{0}个字符"
	            }
	        },
	        ignore: '',
	        errorPlacement: function(error, element) {
	            if (element.attr('name') == 'content') {
	                error.insertAfter($(element).parent().children().last());
	            } else {
	                error.insertAfter(element);
	            }
	        },
			submitHandler: function(form) {
	    		$(form).ajaxSubmit({
	    			type:"post",
	    			dataType:"json",
	    			timeout:3000,
	    			error: function(XMLHttpRequest, textStatus, errorThrown) {
						 sysAlert("请检查是否已登录！");
 					},
	    			success:function(data){
	    				$("#talkEditor_form").prop("disabled",true);
	    				if(data.success==true){
							//$("#talkEditor_form")[0].reset();
							editor.setData('');
							$("#talkEditor").val('');
							$("#targetId").val(0);
							$('#targetStr').val('');
	    					updateList('talkList${tg.id}',${tg.id});
	    				}else{
	    					sysAlert(data.errorMessage);
	    				}
	    				$("#talkEditor_form").prop("disabled",false);
	                }  
	            });
	            return false;
			}
		});
		
		$("#talkEditor_form").on('click','#talk_btn_submit',function(){
	        $('#talkEditor').val(editor.getData());
			//$("#talkEditor_form").submit(false);
		});
		
var _tsData;

	//K线图 
 	var kChart = echarts.init(document.getElementById('k')); 
    
    function queryXY(param){
        var seriesIndex = param.seriesIndex;
        var dataIndex = param.dataIndex;
        var seriesName = param.seriesName;
        var name = param.name;
        var data = param.data;
        var value = param.value;
     }
      $('.kschema_data_btns').on('click','.kschema_data',function(){
    	var duration = $(this).html();
    	var openTime = $('#openTime').html();
    	if(openTime!=null&&openTime.length>5){
	    	openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').format('YYYY-MM-DD HH:mm:SS');
	    }else{
		    openTime = moment().format('YYYY-MM-DD HH:mm:SS');
	    }
    	var closeTime =  $('#closeTime').html();
    	if(closeTime!=null&&closeTime.length>5){
	    	closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').format('YYYY-MM-DD HH:mm:SS');
	    }else{
	    	closeTime = moment().format('YYYY-MM-DD HH:mm:SS');
	    }
    	loadKData(duration,openTime,closeTime);
    });
	
 function k(){
    	 var _option = {
                        animation : false,
                        tooltip : {
                            trigger: 'axis',
                            formatter: function (params) {
                                var res = params[0].seriesName + ' ' + params[0].name;
                                res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : ' + params[0].value[3];
                                res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : ' + params[0].value[2];
                                return res;
                            }
                        },
                        toolbox: {
                        	x:'left',
                            show : true,
                            feature : {
                                mark : {show: true},
                                dataZoom : {show: true},
                                dataView : {show: true, readOnly: false},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        dataZoom : {
                            show : true,
                            realtime: true,
                            start : 30,
                            end : 70
                        },
                        grid :{
                        	x:50,
                        	y:40,
                        	x2:20,
                        	y2:60
                        },
                        xAxis : [
                            {
                                type : 'category',
                                boundaryGap : true,
                                axisTick: {onGap:false},
                                splitLine: {show:false},
                                data:[1]
                            }
                        ],
                        yAxis : [
                            {
                                type : 'value',
                                scale:true,
                                boundaryGap: [0.01, 0.01]
                            }
                        ],
                        series : [
                            {
                                name:'上证指数',
                                type:'k',
                                barMaxWidth: 8,
                                itemStyle: {
                                    normal: {
                                         color: '#fff',          // 阳线填充颜色
					                	color0: '#00aa11',      // 阴线填充颜色
                                        lineStyle: {
                                            width: 1,
                                            color: '#ff3200',   // 阳线边框颜色
					                    	color0: '#00aa11'   // 阴线边框颜色
                                        }
                                    },
                                    emphasis: {
                                        color: 'white',         // 阳线填充颜色
                                        color0: 'black'         // 阴线填充颜色
                                    }
                                },
                                data:[[1,1,1,1]]
                            }
                        ]
                };
                kChart.setOption(_option);
    } 
    
    function loadKData(duration,openTime,closeTime){
    		var seconds = moment(closeTime,'YYYY-MM-DD HH:mm:SS').valueOf() - moment(openTime,'YYYY-MM-DD HH:mm:SS').valueOf();
    		if(duration==null){
    			var tempDuration = '';
	        	if (seconds<60000) {//1分钟
			        tempDuration = "1M";
			        openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(1,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(1,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(seconds<300000) {
			    	tempDuration = "1M";
			        //tempDuration = "5M";
			         openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(1,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(1,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(seconds<900000) {
			        tempDuration = "5M";
			        //tempDuration = "15M";
			        openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(1,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(1,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(seconds<1800000) {
			        tempDuration = "15M";
			        //tempDuration = "30M";
			        openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(2,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(2,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(seconds<3600000) {
			        tempDuration = "30M";
			        //tempDuration = "1H";
			         openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(2,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(2,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(seconds<14400000) {
			        tempDuration = "1H";
			        //tempDuration = "4H";
			         openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(3,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(3,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(seconds<86400000) {
			        tempDuration = "4H";
			        //tempDuration = "1D";
			        openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(3,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(3,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(seconds<604800000) {
			        tempDuration = "1D";
			        //tempDuration = "1W";
			        openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(15,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(15,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else {
			        tempDuration = "1W";
			        //tempDuration = "1MONTH";
			        openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(365,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(365,'day').format('YYYY-MM-DD HH:mm:SS');
			    } 
		    	duration = tempDuration;
		    }else{
	    		if ( duration == "1M") {//1分钟
	    			 openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(1,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(1,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if( duration == "5M") {
			    	 openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(1,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(1,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(duration == "15M") {
			    	 openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(2,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(2,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if( duration == "30M") {
			    	 openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(2,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(2,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(duration == "1H") {
			    	 openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(3,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(3,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(duration == "4H") {
			    	 openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(3,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(3,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(duration == "1D") {
			    	 openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(15,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(15,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else if(duration == "1W") {
			    	 openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(365,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(365,'day').format('YYYY-MM-DD HH:mm:SS');
			    } else {
			        //duration = "1MONTH";
			         openTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').subtract(365,'day').format('YYYY-MM-DD HH:mm:SS');
	        		closeTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').add(365,'day').format('YYYY-MM-DD HH:mm:SS');
			    } 
		    }
		    
    	var ticker = "EURUSD";
    	$.ajax({
            url: "${ctx}/k/load/"+ticker+"/"+duration,
            type: "POST",
            data: {openTime:openTime,closeTime:closeTime}, 
            success: function (data) {
            	var l = data.length;
            	if(l<=0){
            		return;
            	}
            	
            	var _option = kChart.getOption();
	            var option = chartDataParser.parseK(data);
                    
	            _option.xAxis[0].data = option.xAxis[0].data;
	            _option.series[0].data = option.series[0].data;
	            _option.series[0].name = ticker;
	
				_option.dataZoom.start=30;
				_option.dataZoom.end=70;
                kChart.clear();   
                kChart.hideLoading();
	            kChart.setOption(_option, true);
	            
	            
	            addMarkPoints(data,duration);
            }
        });
    }
   
    k();
    $('#kschema_data_init').click();
    
      
     var myChart = echarts.init(document.getElementById('trend'));
    //盈亏曲线图
    function gain(tsData){
    	 var _option = {
	        title: {
	            text: '盈亏曲线图',
	            subtext: '点数累计'
	        },
	        animation: false,
	        tooltip: {
	            trigger: 'axis',
	            showDelay: 0,
	        },
	        toolbox: {
	            show: true,
	            feature: {
	                mark: {
	                    show: false
	                },
	                dataView: {
	                    show: false,
	                    readOnly: false
	                },
	                magicType: {
	                    show: false,
	                    type: ['line', 'bar', 'stack', 'tiled']
	                },
	                restore: {
	                    show: false
	                },
	                saveAsImage: {
	                    show: true
	                }
	            }
	        },
	        calculable: false,
	        xAxis: [{
	            type: 'category',
	            boundaryGap: false,
	            data: []
	        }],
	        yAxis: [{
	            type: 'value'
	        }],
	        series: [{
	            name: '成交',
	            type: 'line',
	            smooth: true,
	            symbol : 'none',
	            itemStyle: {
	                normal: {
	                    areaStyle: {
	                        type: 'default'
	                    }
	                }
	            },
	            data: []
	        }]
	    };
	
	    var option = chartDataParser.parseTrend(tsData);
	    _option.xAxis[0].data = option.xAxis[0].data;
	    _option.series[0].data = option.series[0].data;
	    myChart.setOption(_option);
    }
    
    
    
    function addMarkPoints(data,duration){
     		if(data==null){
     			return ;
		    }
		    var l = data.length;
		    if(l==0){
		    	return;
		    }
		    
    		var openTime = $('#openTime').html();
    		var closeTime = $('#closeTime').html();
    		
            var startTime = 0;
            if(openTime!=null&&openTime.length>5){
            	startTime = moment(openTime,'YYYY-MM-DD HH:mm:SS').valueOf();
		    }
		    var endTime = 0;
	    	if(closeTime!=null&&closeTime.length>5){
	    		endTime = moment(closeTime,'YYYY-MM-DD HH:mm:SS').valueOf();
		    }
		    
		    if(startTime==0&&endTime==0){
		    	return;
		    }
		    
		    var markPoints = {
	            clickable:true,
	            symbol: 'pin',
                itemStyle:{
                        normal:{label:{position:'top'}}
                },
                data : []
		    }
		    if(l==1){
            	var x = moment(data[0].time).format('YYYY/MM/DD HH:mm');
            	var y = data[0].high;
		    	markPoints.data=[
		    		{name: '开仓',value:$('#openPosition').html(), xAxis:x, yAxis:y},
		    		{name: '了结',value:$('#closePosition').html(), xAxis:x, yAxis:y}
		    	];
		    }else{
		    	var p_time = 0;
		    	var _time = 0;
		    	var lastNode = data[0];
		    	for(var index=1;index<l;index++){
		    		if(startTime==0&&endTime==0){
		    			break;
		    		}
		    		p_time = moment(lastNode.time).valueOf();
		    		_time = moment(data[index].time).valueOf();
		    		if(startTime>0){
		    			if(index==1&&p_time>=startTime){
		    				markPoints.data.push(
		    					{name: '开仓',value:$('#openPosition').html(), xAxis:moment(lastNode.time).format('YYYY/MM/DD HH:mm'), yAxis:lastNode.high}
		    				);
		    			}else{
							if(startTime<=_time&&startTime>p_time){
								markPoints.data.push(
			    					{name: '开仓',value:$('#openPosition').html(), xAxis:moment(data[index].time).format('YYYY/MM/DD HH:mm'), yAxis:data[index].high}
			    				);
			    				startTime = 0;
							}
		    			}
		    		}
		    		if(endTime>0){
		    			if(index==1&&p_time>=endTime){
		    				markPoints.data.push(
		    					{name: '了结',value:$('#closePosition').html(), xAxis:moment(lastNode.time).format('YYYY/MM/DD HH:mm'), yAxis:lastNode.high}
		    				);
		    			}else{
							if(endTime<=_time&&endTime>p_time){
								markPoints.data.push(
			    					{name: '了结',value:$('#closePosition').html(), xAxis:moment(data[index].time).format('YYYY/MM/DD HH:mm'), yAxis:data[index].high}
			    				);
			    				endTime = 0;
							}		    				
		    			}
		    		}
		    		lastNode = data[index];
		    	}
		    }
		    try{
		    	kChart.delMarkPoint(0,"开仓");
		    	kChart.delMarkPoint(0,"了结");
		    	kChart.addMarkPoint(0,markPoints);
		    	kChart.refresh();
		    }catch(e){console.log(e);}
    }
    
     function scotterClick(param) {
        var tsIndex = this.getOption().series[param.seriesIndex].id[param.dataIndex]; 
        var d = _tsData[tsIndex];
        if(d!=null){
        	$('#openPosition').empty().append(d.openPosition);
        	$('#openTime').empty().append(d.openTime);
        	$('#closePosition').empty().append(d.closePosition);
        	$('#closeTime').empty().append(d.closeTime);
        	
        	
        	var duration = '1M';
        	
        	var openTime = d.openTime;
        	var closeTime = d.closeTime;
        	loadKData(null,openTime,closeTime);
        }
    }
       //交易时长盈亏散点图
      function gain2(tsData){
      	 var _option = {
                    title: {
                        text: '交易时长及盈亏散点图',
                    },
                    tooltip: {
                        trigger: 'axis',
                        showDelay: 0,
                        axisPointer: {
                            show: true,
                            type: 'none',
                            lineStyle: {
                                type: 'dashed',
                                width: 1
                            }
                        },
                        formatter : function(params) {
                            return params.seriesName + ' :<br/>' + params.value[0] + '分钟 ' + params.value[1] + '点 ' + params.value[2]+ '手';
                        }
                    },
                    legend: {
                        data: []
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {
                                show: false
                            },
                            dataZoom: {
                                show: true
                            },
                            dataView: {
                                show: false,
                                readOnly: false
                            },
                            restore: {
                                show: false
                            },
                            saveAsImage: {
                                show: true
                            }
                        }
                    },
                     grid :{
		            	x:50,
		            	y:30,
		            	x2:20,
		            	y2:60
		            },
                    xAxis: [{
                        type: 'value',
                        scale: true,
                        axisLabel: {
                            formatter: '{value} 分钟'
                        }
                    }],
                    yAxis: [{
                        type: 'value',
                        scale: true,
                        axisLabel: {
                            formatter: '{value} 点'
                        }
                    }],
                    series: []
                };

                var option = chartDataParser.parseScatter(tsData);

                _option.legend = option.legend;

                for (var i=0; i< option.series.length; i++){
                    _option.series[i] = {};
                    _option.series[i].name = option.series[i].name;
                    _option.series[i].data = option.series[i].data;
                    _option.series[i].type = 'scatter',
                    _option.series[i].symbol = 'circle',
                    _option.series[i].symbolSize = function(value){
                        return value[2]*8;
                    };
                    _option.series[i].id = option.series[i].id;
                };

                var scatterChart = echarts.init(document.getElementById('second'));
                scatterChart.setOption(_option);
                
                scatterChart.on(echarts.config.EVENT.CLICK, scotterClick);
      }
      
     
      
	function initTsData(){
    	 $.ajax({ 
			url: "${ctx}/tg/ts/${tg.id}/1",
			dataType:"json", 
			success: function(data){
				_tsData=data;
				gain(data);
				gain2(data);
			}
		});
    }
	initTsData(); 
		
		function loadList(elId,objectId){
			$.ajax({
				url:'${ctx}/viewTg/talks/'+objectId,
				//data:,
				type:'post',
				dataType:"json", 
				success:function(data){
					var html = '<ol class="list-unstyled">';
					$.each(data,function(index,i){
						if(index==0){
							lastId = i.id;
						}
						
						html+='<li>'+genHtmlTalk(i)+'</il>';
						
					});
					html += '</ol>';
					$('#'+elId).empty().append(html).slideDown("slow");
				}
			});
		}
		loadList('talkList${tg.id}',${tg.id});
		
		
		$('#talkList${tg.id}').on('click','.view_talk_long_content',function(){
			var key = $(this).attr('data-key');
			if(key.length<=1){
				return;
			}
			var ap = $(this);
			var p = $(this).parent().prev();
			$.ajax({
				url:'${ctx}/talk/longContent/${tg.id}/'+key,
				type:'post',
				dataType:"json", 
				success:function(data){
					if(data){
						p.empty().html(data.content);
						ap.remove();
					}
				}
			});
		});
		
	});
</script>   

</@template.html>


