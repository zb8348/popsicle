<#include "/ftl/common/include.ftl">
<#import "/ftl/macro/ajaxTemplate.ftl" as ajax>


<@ajax.template '角色列表'>
      	<@f.htmlrow>
      		<@f.htmlcol 12>
                  	<form id="${portletid}role_search_form" class="form-horizontal" action="#" method="post">
						<div class="form-group">
							<label class="control-label col-sm-1">名称：</label>
	                        <@f.htmlcol 3>
	                        	<input id="${portletid}search_name" name="name" class="form-control" type="text"/>
	                        </@f.htmlcol>
	                        <label class="control-label col-sm-1">代码：</label>
	                        <@f.htmlcol 3>
	                        	<input id="${portletid}search_code" name="code" class="form-control" type="text"/>
	                        </@f.htmlcol>
	                        <label class="control-label col-sm-1">备注：</label>
	                        <@f.htmlcol 3>
	                        	<input id="${portletid}search_remark" name="remark" class="form-control" type="text"/>
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
                  	<div id="${portletid}table_tools">
                  		<div class="text-right" style="padding-bottom: 2px;">
               	 			<button type="button" class="btn btn-info btn-sm ${portletid}addSelectedData">添加</button>
               	 			<button type="button" class="btn btn-primary btn-sm ${portletid}updateSelectedData">修改</button>
                            <button type="button" class="btn btn-danger btn-sm ${portletid}deleteSelectedData">删除</button>
                            <button type="button" class="btn btn-success btn-sm ${portletid}setResourceSelectedData">分配菜单</button>
                            <button type="button" class="btn btn-sm ${portletid}updatePermission">更新系统授权</button>
                           
               	 		</div>
                  	</div>
                  	 <table id="${portletid}role_table" class="table table-striped table-bordered">
				        <thead>
				        <tr>
				        	<th>ID</th>
				            <th>名称</th>
				            <th>代码</th>
				            <th class="hidden-xs hidden-sm">备注</th>
				            <!--<th>分配权限</th>-->
				        </tr>
				        </thead>      
				        <tbody></tbody>
				        <!-- tbody是必须的 -->
				    </table>
      		</@f.htmlcol>     
      	</@f.htmlrow>
</@ajax.template>

<@ajax.modal id=portletid+'roleModal' title='新增'>
	<form id="${portletid}role_edit_form" class="form-horizontal">
        <div class="form-group">
            <input type="text" class="form-control" id="role_code" name = "code" placeholder="角色编码">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" id="role_name" name = "name" placeholder="角色名称">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" id="role_remark" name = "remark" placeholder="角色描述">
        </div>
        <input type="hidden" id="role_id" name="id" value="-1"/>
        <#-- 
        <div class="form-group">
            <input type="text" class="form-control" name="isAdmin" placeholder="是否系统管理员"
                   data-date-format="yyyy/mm/dd">
        </div>
        -->
     </form>
</@ajax.modal>

<!-- 这里用来弹出一个图层 -->
<@ajax.lgModal id=portletid+'roleResourceModal' title='资源分配'>
</@ajax.lgModal>

<script type="text/javascript">
page_table.selected('${portletid}role_table');
var ${portletid}table = $('#${portletid}role_table').DataTable({
    	searching: false,
    	ordering:false,
    	processing:true,
    	serverSide: true,
        ajax: {
            url: "${ctx}/security/role/find",
            dataSrc: "data",
            type: "POST",
            data:function(d){
            	d.name=$("#${portletid}search_name").val();
            	d.code=$("#${portletid}search_code").val();
            	d.remark=$("#${portletid}search_remark").val();
            }
        },
        columns: [
        	{"data": "id"},
            {"data": "name"},
            {"data": "code"},
            {"data": "remark",class:'hidden-xs hidden-sm'}
            //{"data": null}
        ],
        dom: page_table.dom,
        language:page_table.language
      });
        

	    //点击修改按钮
        $('#${portletid}table_tools').on('click','.${portletid}updateSelectedData',function () {
	    	var row = ${portletid}table.row('.selected');
	    	if(row.length > 0){
	    		findRoleById(row.data().id,"update");
	    	}else{
	    		sysAlert("请选择一条修改！");
	    	}
	    } );
	    
	    //点击新增按钮
	    $('#${portletid}table_tools').on('click','.${portletid}addSelectedData',function () {
	    	$("#${portletid}role_edit_form label").remove(); //校验时会加上label。但是新增需要将出现的label删除
            $("#${portletid}roleModal :hidden").val("-1");
	    	$("#${portletid}roleModal :text").val("").prop("readonly",false);
            $("#${portletid}roleModalLabel").text("新增");
            $('#${portletid}roleModal').modal('show');
	    });
	    
	    //点击删除按钮，删除
	    $('#${portletid}table_tools').on('click','.${portletid}deleteSelectedData', function () {
	    	var row = ${portletid}table.row('.selected');
	    	if(row.length > 0){
	    		var r = confirm("你确定删除“"+row.data().name+"”吗？");
	    		if(r){
		    		del(row.data().id);
	    		}
	    	}else{
	    		sysAlert("请选择一条删除！");
	    	}
	    } );
    	
    	/**
    	//查询可用资源和当前用户已分配资源
    	$('#${portletid}role_table').on('click',"[id^='${portletid}setResourceLink_']",function(){
    		var roleIdTemp = $(this).attr("id").split("_");
    		var roleId = roleIdTemp[(roleIdTemp.length-1)];
    		//查询资源，并且获得当前角色的资源
    		findResourceAndRoleResource(roleId);
    	});
    	*/
	    var ${portletid}Searchform = $("#${portletid}role_search_form").validate({
			rules: {
				name:{maxlength:20},
				loginName:{maxlength:20}
			},
			submitHandler: function(form) {
	    		${portletid}table.ajax.reload();
			}
		});

		$("#${portletid}role_edit_form").validate({
				rules : {
					code:{required:true,maxlength:32},
					name:{required:true,maxlength:32},
					remark:{required:true,maxlength:256}
				},
				messages : {
					code:{required:"必填",maxlength:"最大长度32"},
					name:{required:"必填",maxlength:"最大长度32"},
					remark:{required:"必填",maxlength:"最大长度256"}
				},
				submitHandler: function(form) {
					$("#${portletid}roleModalBtn").prop("disabled",true); //防止多次点击按钮造成数据错误
					//新增操作
					if($("#role_id").val() < 0){
						$(form).ajaxSubmit({
							url:"${ctx}/security/role/add",
							type:"post",  
							dataType:"json", 
							success:function(data){ 
								${portletid}table.ajax.reload();
				                $("#${portletid}roleModal :hidden").val("-1");
				                $("#${portletid}roleModal :text").val("");
				                $("#${portletid}roleModal").modal("hide");
				                if(data.success){
				                	sysAlert("保存成功！")
				                }else{
				                	sysAlert("保存失败！失败原因："+data.errorMessage);
				                }
				                $("#${portletid}roleModalBtn").prop("disabled",false);
							}  
						});
					}
					//修改操作
					else{
						$(form).ajaxSubmit({
							url:"${ctx}/security/role/update",
							type:"post",  
							dataType:"json", 
							success:function(data){ 
								${portletid}table.ajax.reload();
				                $("#${portletid}roleModal :hidden").val("-1");
				                $("#${portletid}roleModal :text").val("");
				                $("#${portletid}roleModal").modal("hide");
				                if(data.success){
				                	sysAlert("修改成功！")
				                }else{
				                	sysAlert("修改失败！失败原因："+data.errorMessage);
				                }
				                $("#${portletid}roleModalBtn").prop("disabled",false);
							}  
						});
					}
				}
			});
		$("#${portletid}roleModalBtn").on('click',function(){
			$("#${portletid}role_edit_form").submit();
		});
 
 
    /**
     * 删除数据
     * @param name
     */
    function del(role_id) {
        $.ajax({
            url: "${ctx}/security/role/delete",
            data: {
                "id": role_id
            }, 
            success: function (data) {
                ${portletid}table.ajax.reload();
                if(data.success){
	                sysAlert("删除成功"	);
                }else{
                	sysAlert(data.errorMessage);
                }
            }
        });
	}
	
	function findRoleById(roleId,updateflag){
		$.ajax({
			url:"${ctx}/security/role/findById",
			data:{"id":roleId},
			success:function(data){
				$("#role_name").val(data.name);
				$("#role_code").val(data.code).prop("readonly",true);
				$("#role_remark").val(data.remark);
				$("#role_id").val(data.id);
				if(updateflag == "update"){
					$("#${portletid}roleModalLabel").text("修改");
				}else{
					$("#${portletid}roleModalLabel").text("查看");
					$("#${portletid}roleModal :text").prop("readonly",true);
				}
				$("#${portletid}roleModal").modal("show");
				
			}
		});
	}
	//分配资源
	function findResourceAndRoleResource(roleId,roleName){
		sysToModal("${ctx}/security/role/findAllResources?id="+roleId,'${portletid}roleResourceModal','给“'+roleName+'”分配资源');
		//toUrl("${ctx}/security/role/findAllResources?id="+roleId);
	}
	
	//分配资源
	$('#${portletid}table_tools').on('click','.${portletid}setResourceSelectedData', function () {
    	var row = ${portletid}table.row('.selected');
    	if(row.length > 0){
    		findResourceAndRoleResource(row.data().id,row.data().name);
    	}else{
    		sysAlert("请选择一条分配资源！");
    	}
    });
    
    $('#${portletid}table_tools').on('click','.${portletid}updatePermission', function () {
    	$.ajax({
			url:"${ctx}/security/role/updatePermission",
			success:function(data){
				if(data.success){
	                sysAlert("系统全局授权信息更新成功");
                }else{
                	sysAlert(data.errorMessage);
                }
			}
		});
    });
</script>
