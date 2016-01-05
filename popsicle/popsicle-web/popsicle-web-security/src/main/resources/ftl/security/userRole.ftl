<#include "/ftl/common/include.ftl">
<#import "/ftl/macro/ajaxTemplate.ftl" as ajax>
<!--
<@ajax.template '给用户“${user.name}”分配角色'>
-->
<input type="hidden" id = "${portletid}userId" value="${user.id}" />
	<@f.htmlrow>
		<@f.htmlcol 6>
			<div>
				<div style="float:left;"><h5 class="h5">用户拥有的角色</h5></div> 
				<div style="float:right;"><button type="button" id="${portletid}userRole_deleteButton" class="btn btn-primary btn-sm">删除</button></div>
			</div>
	          	 <table id="${portletid}userRole_table" class="table table-striped table-bordered">
			        <thead>
			        <tr>
			        	<th>删除</th>
			            <th>名称</th>
			            <th class="hidden-xs hidden-sm">备注</th>
			        </tr>
			        </thead>      
			        <!-- tbody是必须的 -->
			        <tbody>
			        </tbody>
			    </table>
		</@f.htmlcol>     
	
		<@f.htmlcol 6>
			<div>
				<div style="float:left;"><h5 class="h5">角色列表</h5></div> 
				<div style="float:right;"><button type="button" id="${portletid}userRole_addButton" class="btn btn-primary btn-sm">添加</button></div>
			</div>
	          	 <table id="${portletid}role_table" class="table table-striped table-bordered">
			        <thead>
			        <tr>
			        	<th>添加</th>
			            <th>名称</th>
			            <th class="hidden-xs hidden-sm">备注</th>
			        </tr>
			        </thead>      
			        <!-- tbody是必须的 -->
			        <tbody></tbody>
			    </table>
		</@f.htmlcol>     
	</@f.htmlrow>
	<!--
</@ajax.template>
-->
<script type="text/javascript">
var ${portletid}roletable = $('#${portletid}role_table').DataTable({
    	searching: false,
    	ordering:false,
    	processing:true,
    	serverSide: true,
        ajax: {
            url: "${ctx}/security/user/findRoleExitUserRole",
            data: {"id":$("#${portletid}userId").val()},
            type: "POST"
        },
        columns: [
        	{"data": null},
            {"data": "name"},
            {"data": "remark",class:'hidden-xs hidden-sm'}
        ],
        columnDefs: [
        	{
        		targets : 0,
        		render : function(dataContent){
        			return '<input type="checkbox" name="roleId" value="'+dataContent.id+'">';
        		}
        	}
        ],
        dom: page_table.domEL,
        language:page_table.language
        });
        
		var ${portletid}userRoletable = $('#${portletid}userRole_table').DataTable({
	    	searching: false,
    		ordering:false,
    		processing:true,
    		serverSide: true,
    		paging:false,
	        ajax: {
	            url: "${ctx}/security/user/findUserRole",
	            data: {"id":$("#${portletid}userId").val()},
	            type: "POST"
	        },
	        columns: [
	        	{"data": null},
	            {"data": "name"},
	            {"data": "remark",class:'hidden-xs hidden-sm'}
	        ],
	        columnDefs: [
	        	{
	        		targets : 0,
	        		render : function(dataContent){
	        			return '<input type="checkbox" name="roleId" value="'+dataContent.id+'">';
	        		}
	        	}
	        ],
	        dom: page_table.domEL,
        	language:page_table.language
		});
       
        //给用户删除角色
        $("#${portletid}userRole_deleteButton").on('click',function(){
        	var checkboxArr = $("#${portletid}userRole_table :checkbox:checked");
        	var roleId = new Array();
        	for(var i = 0 ; i < checkboxArr.length ; i++){
        		
	        	roleId.push($(checkboxArr[i]).val());
        	}
        	if(roleId.length <= 0 ){
        		sysAlert("请选择要删除的用户角色");
        		return;
        	}
        	$.ajax({
        		type:"POST",
        		data:{
        			"userId":$("#${portletid}userId").val(),
        			"roleId":roleId.toString()
        		},
        		url:"${ctx}/security/user/deleteUserRole",
        		dataType:"json",
        		success:function(data){
        			if(data.success){
        				${portletid}roletable.ajax.reload();
        				${portletid}userRoletable.ajax.reload();
	        			sysAlert("删除角色成功！");
        			}else{
        				sysAlert(data.errorMessage);
        			}
        			
        		}
        	});
        });
        
        //给用户添加角色
        $("#${portletid}userRole_addButton").on('click',function(){
        	var checkboxArr = $("#${portletid}role_table :checkbox:checked");
        	var roleId = new Array();
        	for(var i = 0 ; i < checkboxArr.length ; i++){
	        	roleId.push($(checkboxArr[i]).val());
        	}
        	$.ajax({
        		type:"POST",
        		data:{
        			"userId":$("#${portletid}userId").val(),
        			"roleId":roleId.toString()
        		},
        		url:"${ctx}/security/user/addUserRole",
        		dataType:"json",
        		success:function(data){
        			if(data.success){
        				${portletid}roletable.ajax.reload();
        				${portletid}userRoletable.ajax.reload();
	        			sysAlert("添加角色成功！");
        			}else{
        				sysAlert(data.errorMessage);
        			}
        		}
        	});
        });
</script>