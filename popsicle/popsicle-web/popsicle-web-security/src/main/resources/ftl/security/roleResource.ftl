<#include "/ftl/common/include.ftl">
<#import "/ftl/macro/ajaxTemplate.ftl" as ajax>
<link href="${statics}/css/tree.css" rel="stylesheet" type="text/css" />
<input type="hidden" value="${role.id}" id="${portletid}roleId" />
<!--
<@ajax.template '给角色“${role.name}”分配资源'>
-->
      	<@f.htmlrow>
      		<@f.htmlcol 6>
			 <div class="col-sm-6" id="${portletid}delResource_tool">
				<button type="button" id="${portletid}roleResource_deleteButton" class="btn btn-primary btn-sm">删除</button>
			</div>    
                <div class="tree well" id="${portletid}roleResourceJsonTree" style="padding-top: 40px;"></div>
      		</@f.htmlcol>
      		<@f.htmlcol 6>
      			<div class="col-sm-6" id="${portletid}addResource_tool">
					<button type="button" id="${portletid}roleResource_addButton" class="btn btn-primary btn-sm">添加</button>
				</div> 
                <div class="tree well" id="${portletid}resourceJsonTree" style="padding-top: 40px;"></div>
      		</@f.htmlcol>
      	</@f.htmlrow>
      	<!--
</@ajax.template>
-->
<script type="text/javascript">
	
	//$(function() {
		//加载资源树结构
	   	function ${portletid}Tree(jsonData){
	   		var html="";
	   		if(jsonData&&jsonData.length>0){
	   			html+="<ul>";
	   			$.each(jsonData, function (n, r) {  
					var nodeHtml="<li><input type='checkbox' value='"+r.node.id+"' name='${portletid}resourceCheckbox'><span>"+r.node.name+"</span>";
				    nodeHtml+=${portletid}Tree(r.children);
				    nodeHtml+="</li>";
				    html+=nodeHtml;
	          	});  
	          	html+="</ul>";
	   		}
	   		return html;
	   	}
	    function ${portletid}loadTree(){
	    	 $.ajax({ 
				url: "${ctx}/security/resource/loadTree",
				dataType:"json",
				async:false,  
				success: function(treeData){
					var treeHtml = "<ul>";
					$.each(treeData, function (n, r) {  
						var nodeHtml="<li><input type='checkbox' value='"+r.node.id+"' name='${portletid}resourceCheckbox'><span>"+r.node.name+"</span>";
					    nodeHtml+=${portletid}Tree(r.children);
					    nodeHtml+="</li>";
					    treeHtml+=nodeHtml;
		          	});  
          			treeHtml+="</ul>";
					$("#${portletid}resourceJsonTree").empty().append(treeHtml);
					
					$('#${portletid}resourceJsonTree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
				    $('#${portletid}resourceJsonTree li.parent_li > span').on('click', function (e) {
				        var children = $(this).parent('li.parent_li').find(' > ul > li');
				        if (children.is(":visible")) {
				            children.hide('fast');
				            $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
				        } else {
				            children.show('fast');
				            $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
				        }
				        e.stopPropagation();
				    });
				}
			});
	    }
		${portletid}loadTree();
		//加载个人资源树
		function ${portletid}loadRoleTree(){
	    	 $.ajax({ 
				url: "${ctx}/security/resource/loadRoleTree",
				data:{"id":$("#${portletid}roleId").val()},
				dataType:"json",
				async:false, 
				success: function(treeData){
					var treeHtml = "<ul>";
					$.each(treeData, function (n, r) {  
						var nodeHtml="<li><input type='checkbox' value='"+r.node.id+"' name='${portletid}roleResourceCheckbox'><span>"+r.node.name+"</span>";
					    nodeHtml+=${portletid}RoleTree(r.children);
					    nodeHtml+="</li>";
					    treeHtml+=nodeHtml;
		          	});  
          			treeHtml+="</ul>";
					$("#${portletid}roleResourceJsonTree").empty().append(treeHtml);
					
					$('#${portletid}roleResourceJsonTree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
				    $('#${portletid}roleResourceJsonTree li.parent_li > span').on('click', function (e) {
				        var children = $(this).parent('li.parent_li').find(' > ul > li');
				        if (children.is(":visible")) {
				            children.hide('fast');
				            $(this).attr('title', 'Expand this branch').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
				        } else {
				            children.show('fast');
				            $(this).attr('title', 'Collapse this branch').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
				        }
				        e.stopPropagation();
				    });
				    ${portletid}checkOutRoleResource();
				}
			});
	    }
	    function ${portletid}RoleTree(jsonData){
	   		var html="";
	   		if(jsonData&&jsonData.length>0){
	   			html+="<ul>";
	   			$.each(jsonData, function (n, r) {  
					var nodeHtml="<li><input type='checkbox' value='"+r.node.id+"' name='${portletid}roleResourceCheckbox'><span>"+r.node.name+"</span>";
				    nodeHtml+=${portletid}RoleTree(r.children);
				    nodeHtml+="</li>";
				    html+=nodeHtml;
	          	});  
	          	html+="</ul>";
	   		}
	   		return html;
	   	}
	    ${portletid}loadRoleTree();
	    
	    //将所有角色资源找出来，然后对应到资源树中这样免得查询数据库
	    function ${portletid}checkOutRoleResource(){
	    	var checkboxArr1 = $("#${portletid}roleResourceJsonTree :checkbox");
	    	var resourceCodeArr1 = new Array();
	    	for(var i = 0 ; i < checkboxArr1.length ; i++){
	    		resourceCodeArr1.push($(checkboxArr1[i]).val());
	    	}
	    	var checkArr2 = $("#${portletid}resourceJsonTree :checkbox");
	    	for(var i = 0 ; i < checkArr2.length ; i ++){
	    		for(var j = 0 ; j < resourceCodeArr1.length ; j++){
	    			if($(checkArr2[i]).val() == resourceCodeArr1[j]){
	    				$(checkArr2[i]).prop("checked",true);
	    				break;
	    			}
	    		}
	    	}
	    	
	    	//防止资源树没有加载完成，导致部分checkbox没法选上    所以在这里再判断一次
	    	if($("#${portletid}resourceJsonTree :checkbox:checked").length < checkboxArr1.length){
	    		${portletid}checkOutRoleResource();
	    	}
	    	
	    }
	    
	    
	    //为资源树中checkbox添加选中事件
	    $("#${portletid}resourceJsonTree").on("click",":checkbox",function(){
	    	var parentClass = $(this).parent().attr("class");
	    	if(parentClass == "parent_li" && $(this).prop("checked")){
	    		$(this).parent().find(":checkbox").prop("checked",true);
	    	}
	    	if(parentClass == "parent_li" && !$(this).prop("checked")){
	    		$(this).parent().find(":checkbox").prop("checked",false);
	    	}
	    });
	    //为个人资源树中checkbox添加选中事件
	    $("#${portletid}roleResourceJsonTree").on("click",":checkbox",function(){
	    	var parentClass = $(this).parent().attr("class");
	    	if(parentClass == "parent_li" && $(this).prop("checked")){
	    		$(this).parent().find(":checkbox").prop("checked",true);
	    	}
	    	if(parentClass == "parent_li" && !$(this).prop("checked")){
	    		$(this).parent().find(":checkbox").prop("checked",false);
	    	}
	    });
	    
	//});
       

	
	//执行增加操作
	$("#${portletid}addResource_tool").on("click","#${portletid}roleResource_addButton",function(){
	    	var checkboxArr = $("#${portletid}resourceJsonTree :checkbox:checked");
	    	var resourceId = new Array();
	    	if(checkboxArr.length<=0){
	    		sysAlert("请选择需要添加的菜单");
	    		return false;
	    	}
	    	for(var i = 0 ; i < checkboxArr.length ; i++){
	        	resourceId.push($(checkboxArr[i]).val());
	    	}
	    	$.ajax({
	    		type:"POST",
	    		data:{
	    			"roleId":$("#${portletid}roleId").val(),
	    			"resourceId":resourceId.toString()
	    		},
	    		url:"${ctx}/security/role/addRoleResource",
	    		dataType:"json",
	    		success:function(data){
	    			if(data.success){
	    				sysAlert("菜单添加成功");
	    				${portletid}loadTree();
	    				${portletid}loadRoleTree();
	    			}else{
	    				sysAlert(data.errorMessage);
	    			}
	    		}
	    	});
	    });
	    
	    //给角色删除资源
    $("#${portletid}roleResource_deleteButton").on('click',function(){
    	var checkboxArr = $("#${portletid}roleResourceJsonTree :checkbox");
    	
    	var resourceId = new Array();
    	
    	if(checkboxArr.length <= 0){
    		sysAlert("请选择需要移除的菜单");
    		return;
    	}
    	
    	for(var i = 0 ; i < checkboxArr.length ; i++){
    		if($(checkboxArr[i]).prop("checked")){
    			//选中的checkbox,不做操作
    		}else{
    			//没有选中的，就是不删除的。从另一方面认为就是新增的
	        	resourceId.push($(checkboxArr[i]).val());
    		}
    	}
    	$.ajax({
    		type:"POST",
    		data:{
    			"roleId":$("#${portletid}roleId").val(),
    			"resourceId":resourceId.toString()
    		},
    		url:"${ctx}/security/role/addRoleResource",
    		dataType:"json",
    		success:function(data){
    			if(data.success){
    				sysAlert("菜单删除成功");
    				${portletid}loadTree();
	    			${portletid}loadRoleTree();
    			}else{
    				sysAlert(data.errorMessage);
    			}
    			
    		}
    	});
	});
</script>