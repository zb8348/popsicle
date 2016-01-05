<#include "/ftl/common/include.ftl">
<#import "/ftl/macro/ajaxTemplate.ftl" as ajax>


<@ajax.template '用户会话列表'>
      <@f.htmlrow>
  		<@f.htmlcol 12>
          	<form id="${portletid}session_search_form" class="form-horizontal" action="#" method="post">
				<div class="form-group">
					<label class="control-label col-sm-1">账号：</label>
                    <@f.htmlcol 3>
                    	<input id="${portletid}search_name" name="name" class="form-control" type="text"/>
                    </@f.htmlcol>
                    <label class="control-label col-sm-1">登录IP：</label>
                    <@f.htmlcol 3>
                    	<input id="${portletid}search_loginIP" name="loginIP" class="form-control" type="text"/>
                    </@f.htmlcol>
                 </div>
              	<div class="form-group">
                <label class="control-label col-sm-1"></label>
                <@f.htmlcol 1>
                  <input class="btn btn-info" type="submit" value="搜索"/>
                </@f.htmlcol>
                <@f.htmlcol 1>
                  <input class="btn btn-primary" type="reset" value="清空"/>
                </@f.htmlcol>
              </div>
            </form>
  		</@f.htmlcol>     
  	</@f.htmlrow>
	<@f.htmlrow>
  		<@f.htmlcol 12>
  			<div id="${portletid}session_tools" class="form-inline">
          		<div class="text-right" style="padding-bottom: 2px;">
                    <button type="button" class="btn btn-danger btn-sm ${portletid}failureSession">失效</button>
                	<input type="text" id = "${portletid}session_timer_refresh" class="form-control valid" style="width:50px;" name="timer" value="${timer}" placeholder="刷新时间"/>秒
                	<button type="button" id="${portletid}updateSeconds" class="btn btn-primary btn-sm ${portletid}setTimerfailureSession">更新时间</button>
       	 		</div>
          	</div>
          	 <table id="${portletid}session_table" class="table table-striped table-bordered">
		        <thead>
		        <tr>
		        	<th>会话ID</th>
		            <th>账号</th>
		            <th class="hidden-xs hidden-sm">登录ip</th>
		            <th class="hidden-xs hidden-sm">登录时间</th>
		            <th class="hidden-xs hidden-sm">最后操作时间</th>
		        </tr>
		        </thead>      
		        <tbody></tbody>
		        <!-- tbody是必须的 -->
		    </table>
  		</@f.htmlcol>     
  	</@f.htmlrow>
</@ajax.template>



<script type="text/javascript">
	page_table.selected('${portletid}session_table');
	var ${portletid}table = $('#${portletid}session_table').DataTable({
    	searching: false,
    	ordering: true,
    	processing:false,
    	serverSide: false,
        ajax: {
            url: "${ctx}/security/session/find",
            dataSrc: "data",
            type: "POST",
            data:function(d){
            	d.name=$("#${portletid}search_name").val();
            	d.loginIP=$("#${portletid}search_loginIP").val();
            }
        },
        columns: [
        	{"data": "sesssionId"},
            {"data": "ssoUser.loginName"},
            {"data": "ip",class:'hidden-xs hidden-sm'},
            {"data": "loginTime",class:'hidden-xs hidden-sm'},
            {"data": "lastAccessTime",class:'hidden-xs hidden-sm'}
        ],
        dom: page_table.dom,
        language:page_table.language
	});
	
	
	
	
    //查询session
	$("#${portletid}session_search_form").validate({
		rules: {
			name: {minlength: 2,maxlength:30},
			loginIP: {minlength: 2,maxlength:30}
		},
		submitHandler: function(form) {
			$("#${portletid}userModalBtn").prop("disabled",true);
			${portletid}table.ajax.reload();
		}
	});
	//使session失效
	$("#${portletid}session_tools").on("click",".${portletid}failureSession",function(){
		var row = ${portletid}table.row('.selected');
		if(row&&row.data()){
    		var sessionId = row.data().sesssionId;
    		$.ajax({
	            url: "${ctx}/security/session/failureSession",
	            type: "POST",
	            data: {
	                "sessionId": sessionId
	            }, 
	            success: function (data) {
	            	if(data.success){
		                sysAlert("操作成功");
		                ${portletid}table.ajax.reload();
	            	}else{
	            		sysAlert(data.errorMessage);
	            	}
	            }
	        });
    	}else{
    		sysAlert("请选择操作的数据！");
    	}
	});
	
	//重新设置刷新时间
	$("#${portletid}session_tools").on("click",".${portletid}setTimerfailureSession",function(){
		var timerValue = $("#${portletid}session_timer_refresh").val();
		if(!(/^[0-9]*$/g.test(timerValue))){
			sysAlert("只能输入数字");
			return;
		}
		clearInterval(timerSession);
			$.ajax({
				url:"${ctx}/security/session",
				type: "POST",
				data: {"timer":$("#${portletid}session_timer_refresh").val()},
				dataType:'html',
				success:function(html){
					var doms = $.parseHTML(html,true);
					$("#${portletid}session_tools").parent().parent().parent().parent().next().remove();
					$("#${portletid}session_tools").parent().parent().parent().parent().remove();
					$('#popsicle_main_content').prepend(doms);//.empty()
					//加载外部js，并执行: 窗口最大最小化缺陷——动态新增html元素，jquery 动态事件绑定，需重新加载，
					var urls=[statcis+'/js/core.min.js'];
					$.getScript(urls, function() {
					});
				}
			});
	});
	
	var ${portletid}timer = $("#${portletid}session_timer_refresh").val();
	var ${portletid}Updatetimer=${portletid}timer;
	setInterval("${portletid}timerRefreshSession()",(${portletid}timer*1000));
	setInterval("${portletid}updateSeconds()",1000);
	function ${portletid}timerRefreshSession(){
		var l = $('#${portletid}session_table').length;
		if(l==1){
			${portletid}table.ajax.reload();
		}
	}
	function ${portletid}updateSeconds(){
		${portletid}Updatetimer--;
		if(${portletid}Updatetimer<=0){
			${portletid}Updatetimer=${portletid}timer;
		}
		$('#${portletid}updateSeconds').empty().append("更新时间 "+${portletid}Updatetimer);
	}
</script>
