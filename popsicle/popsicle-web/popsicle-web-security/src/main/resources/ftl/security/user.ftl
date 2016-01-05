<#include "/ftl/common/include.ftl">
<#import "/ftl/macro/ajaxTemplate.ftl" as ajax>


<@ajax.template '用户列表'>
      	<@f.htmlrow>
      		<@f.htmlcol 12>
                  	<form id="${portletid}user_search_form" class="form-horizontal" action="#" method="post">
						<div class="form-group">
							<label class="control-label col-sm-1">姓名：</label>
	                        <@f.htmlcol 3>
	                        	<input id="${portletid}search_name" name="name" class="form-control" type="text"/>
	                        </@f.htmlcol>
	                        <label class="control-label col-sm-1">账号：</label>
	                        <@f.htmlcol 3>
	                        	<input id="${portletid}search_loginName" name="loginName" class="form-control" type="text"/>
	                        </@f.htmlcol>
	                        <label class="control-label col-sm-1">状态：</label>
	                        <@f.htmlcol 3>
	                        	<select class="form-control" name="traderName" id="${portletid}search_trader_status">
		                    		<option value="">全部</option>
		                    		<option value="0">申请</option>
		                    		<option value="1">正常</option>
		                    		<option value="2">不可用</option>
		                    	</select>
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
               	 			<button type="button" class="btn btn-info btn-sm ${portletid}addNewData">开通账号</button>
               	 			<button type="button" class="btn btn-primary btn-sm ${portletid}updateSelectedData">修改</button>
                            <button type="button" class="btn btn-danger btn-sm ${portletid}deleteSelectedData">删除</button>
                            <button type="button" class='btn btn-success btn-sm ${portletid}setRoleLink'>分配角色</button>
               	 		</div>
                  	</div>
                  	 <table id="${portletid}user_table" class="table table-striped table-bordered">
				        <thead>
				        <tr>
				        	<th>ID</th>
				            <th>姓名</th>
				            <th>账号</th>
				            <th>状态</th>
				            <th class="hidden-xs hidden-sm">创建时间</th>
				            <th class="hidden-xs hidden-sm">创建人</th>
				            <th>操作</th>
				        </tr>
				        </thead>      
				        <tbody></tbody>
				        <!-- tbody是必须的 -->
				    </table>
      		</@f.htmlcol>     
      	</@f.htmlrow>
</@ajax.template>

<@ajax.modal id=portletid+'userModal' title='新增'>
	<form id="${portletid}user_edit_form" class="form-horizontal">
		<input type="hidden" name="id" value="0"/>
        <div class="form-group">
            <label for="name">用户名</label>
            <input type="text" id="name" class="form-control" name="name" type="text">
        </div>
        <div class="form-group">
            <label for="signupName">登录名</label>
            <input type="text" id="loginName" class="form-control" name="loginName" type="text">
        </div>
        <div class="form-group">
            <label for="email">邮箱</label>
            <input type="text" id="email" class="form-control" name="email" type="text">
        </div>
        <div class="form-group">
            <label for="signupPassword">密码</label>
            <input type="password" id="signupPassword" class="form-control" name="password" type="text">
        </div>
        <div class="form-group">
            <label for="confirmPassword">确认密码</label>
            <input type="password" id="confirmPassword" class="form-control" name="confirmPassword" type="text">
        </div>
        <!--
        <div class="form-group">
            <input type="text" class="form-control" name="imgUrl" placeholder="图像url">
        </div>
         -->
         <div class="form-group">
            <label class="checkbox-inline">
            	<input type="checkbox" name="isAdmin" > 系统管理员
            </label>
        </div>
        <div class="form-group">
        	<#list UserStatusMap?keys as k>
        		<label class="radio-inline">
	            <input type="radio" name="status" value="${k}" >${UserStatusMap[k]}
	            </label>
			</#list>
        </div>
     </form>
</@ajax.modal>


<@ajax.lgModal id=portletid+'userRoletModal' title='角色分配'>
</@ajax.lgModal>


<script type="text/javascript">

var ${portletid}UserStatus = {};  
<#list UserStatusMap?keys as k>
	${portletid}UserStatus['${k}']='${UserStatusMap[k]}';
</#list>

$(function () {
	var ${portletid}table = $('#${portletid}user_table').DataTable({
    	searching: false,
    	ordering:false,
    	processing:true,
    	serverSide: true,
        ajax: {
            url: "${ctx}/security/user/find",
            dataSrc: "data",
            type: "POST",
            data:function(d){
            	d.name=$("#${portletid}search_name").val();
            	d.loginName=$("#${portletid}search_loginName").val();
            	//alert($("#${portletid}search_trader_status").val());
            	d.status=$("#${portletid}search_trader_status").val();
            	
            }
        },
        columns: [
        	{"data": "id"},
            {"data": "name"},
            {"data": "loginName"},
            {"data": "status"},
            {"data": "createTime",class:'hidden-xs hidden-sm'},
            {"data": "creator",class:'hidden-xs hidden-sm'},
            {"data": null}
        ],
        columnDefs: [
        	{ 
        		targets: 0,
        	  	visible: false,
        	  	searchable: false		
        	},
        	{ 
        		targets: 3,
        	  	render: function (a, b, c, d) {
                	var html = ${portletid}UserStatus[c.status];
                    return html;
                }	
        	},
            {
                targets: 6,
                render: function (dataContent) {
                	var switchType = '';
                	if(dataContent.status == 1){
                		switchType = 'checked="true"';
                	}
                	var html = '<input class="${portletid}switch" data-id="'+dataContent.id+'" type="checkbox" '+switchType+' name="vaild" value="true"/>';
                    return html;
                }
            }
        ],
        dom: page_table.dom,
        language:page_table.language,
        footerCallback: function ( row, data, start, end, display ) {
        	var urls=['${statics}/lib/bootstrap-switch/bootstrap-switch.min.js?version=${version}'];
			$.getScript(urls, function() {
				$('.${portletid}switch').wrap('<div class="switch has-switch"/>').bootstrapSwitch({
				 	size: "mini",
				 	disabled:false,
				 	readonly:false,
				 	onText:'正常',
				 	offText:'无效',
				 	onColor:'primary',
				 	offColor:'warning',
					onSwitchChange: function(event, eventData) {
						var status = "";
						if($(this).attr("checked") == "checked"){
							$(this).removeAttr("checked");
							status = 2;
						}else{
							$(this).attr("checked","true");
							status = 1;
						}
						var userId = $(this).attr('data-id');
						var user = {"id":userId,"status":status};
						$.ajax({
							url: "${ctx}/security/user/saveOrUpdate",
							type:"POST",
							data: user,
							success: function(data){
								if(data.success==true){
			    					$('#${portletid}userModal').modal('hide') 
			    					//${portletid}table.ajax.reload();
			    					${portletid}table.draw();
			    				}else{
			    					sysAlert(data.errorMessage);
			    				}
							}
						});
					}
				 });
			});
        }
	});
       page_table.selected('${portletid}user_table'); 
	    
	   //分配角色按钮绑定事件 
	   $('#${portletid}table_tools').on('click','.${portletid}setRoleLink',function(){
	   		var row = ${portletid}table.row('.selected');
	    	if(row&&row.data()){
	    		var userId = row.data().id;
	    		//查询资源，并且获得当前角色的资源
    			sysToModal("${ctx}/security/user/findRoleAndUserRole?id="+userId,'${portletid}userRoletModal','给“'+row.data().name+'”角色分配');
	    	}else{
	    		sysAlert("请选择操作的数据！");
	    	}
    	});
	    //更新用户按钮，查询用户信息
        $('#${portletid}table_tools').on('click','.${portletid}updateSelectedData',function () {
	    	var row = ${portletid}table.row('.selected');
	    	if(row&&row.data()){
	    		$('#${portletid}userModal .modal-title').empty().html("更新用户:"+row.data().name);
	    		$("#${portletid}user_edit_form").resetForm();
	    		$("#${portletid}user_edit_form input[name=id]").val(row.data().id);
	    		//$("#${portletid}user_edit_form input[name=name]").val(row.data().name);
	    		$("#${portletid}user_edit_form input[name=loginName]").val(row.data().loginName);
	    		$("#${portletid}user_edit_form input[name=loginName]").attr("readonly","readonly");
	    		
				
	    		$("#${portletid}user_edit_form input[name=name]").val(row.data().name);
	    		$("#${portletid}user_edit_form input[name=email]").val(row.data().email);
	    		
	    		if(row.data().isAdmin&&row.data().isAdmin==true){
	    			$("#${portletid}user_edit_form input[name=isAdmin]").attr('checked',true);
	    		}else{
	    			$("#${portletid}user_edit_form input[name=isAdmin]").attr('checked',false);
	    		}
	    		$("#${portletid}user_edit_form input[name=status][value="+row.data().status+"]").attr('checked',true);
            
	    		$('#${portletid}userModal').modal('show');
	    	}else{
	    		sysAlert("请选择操作的数据！");
	    	}
	    } );
	     $('#${portletid}table_tools').on('click','.${portletid}addNewData',function () {
	    		$('#${portletid}userModal .modal-title').empty().html("新增账号");
	    		$("#${portletid}user_edit_form input[name=loginName]").removeAttr("readonly");
	    		$("#${portletid}user_edit_form").resetForm();
	    		$("#${portletid}user_edit_form input[name=id]").val(0);
	    		$("#${portletid}user_edit_form input[name=isAdmin]").attr('checked',false);
	    		$('#${portletid}userModal').modal('show');
	    } );
	    //删除用户
	    $('#${portletid}table_tools').on('click','.${portletid}deleteSelectedData', function () {
	    	var row = ${portletid}table.row('.selected');
	    	if(row&&row.data()){
	    		var userId = row.data().id;
	    		 $.ajax({
		            url: "${ctx}/security/user/deleteUser",
		            type: "POST",
		            data: {
		                "userId": userId
		            }, 
		            success: function (data) {
		                //${portletid}table.ajax.reload();
		                ${portletid}table.draw();
		                sysAlert("删除成功");
		            }
		        });
	    	}else{
	    		sysAlert("请选择一条删除！");
	    	}
	    });
    
	    var ${portletid}Searchform = $("#${portletid}user_search_form").validate({
			rules: {
				name:{maxlength:20},
				loginName:{maxlength:20}
			},
			submitHandler: function(form) {
			}
		});
		$("#${portletid}user_search_form").submit(function(){
			${portletid}table.draw();
			//${portletid}table.ajax.reload();
			return false;
		});
 		$("#${portletid}user_edit_form").validate({
			rules: {
				name: {required:true,minlength: 2,maxlength:30},
				loginName: {required:true,minlength: 4,maxlength:30},
				password: {required:true,minlength: 5,maxlength:20},
				email: {required:true,email:true,maxlength:50},
				confirmPassword:{required:true,minlength: 5,maxlength:20,equalTo:'#signupPassword'},
				status:"required"
			},
			messages: {
	            name: {
	                required: "没有填写用户名",
	                minlength: "用户名不能小于{0}个字符",
	                maxlength: "用户名不能大于{0}个字符"
	            },
	            loginName: {
	                required: "没有填写登录名",
	                minlength: "登录名不能小于{0}个字符",
	                maxlength: "登录名不能大于{0}个字符"
	            },
	            password: {
	                required: "没有填写密码",
	                minlength: "密码不能小于{0}个字符",
	                maxlength: "密码不能大于{0}个字符"
	            },
	             email: {
	                required: "没有填写邮箱",
	                maxlength: "邮箱不能大于{0}个字符"
	            },
	            confirmPassword: {
	                required: "没有确认密码",
	                minlength: "确认密码不能小于{0}个字符",
	                maxlength: "确认密码不能大于{0}个字符",
	                equalTo: "两次输入密码不一致"
	            }
	        },
			submitHandler: function(form) {
				$("#${portletid}userModalBtn").prop("disabled",true);
				if($("#${portletid}user_edit_form input[name=isAdmin]").get(0).checked){
					$("#${portletid}user_edit_form input[name=isAdmin]").val(true);
				}else{
					$("#${portletid}user_edit_form input[name=isAdmin]").val(false);
				}
	    		$(form).ajaxSubmit({
	    			type:"post",
	    			data:{"isAdmin":$("#${portletid}user_edit_form input[name=isAdmin]").val()},
	    			dataType:"json",
	    			url:"${ctx}/security/user/saveOrUpdate",
	    			success:function(data){
	    				if(data.success==true){
	    					$('#${portletid}userModal').modal('hide') 
	    					//${portletid}table.ajax.reload();
	    					${portletid}table.draw();
	    				}else{
	    					sysAlert(data.errorMessage);
	    				}
	    				$("#${portletid}userModalBtn").prop("disabled",false);
	                }  
	            });
			}
		});
		
		$("#${portletid}userModal").on('click','#${portletid}userModalBtn',function(){
			$("#${portletid}user_edit_form").submit();
		});
});
</script>
