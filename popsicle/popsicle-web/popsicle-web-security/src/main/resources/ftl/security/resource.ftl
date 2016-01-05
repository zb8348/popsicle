<#include "/ftl/common/include.ftl">
<#import "/ftl/macro/ajaxTemplate.ftl" as ajax>
<!--tree look http://jsfiddle.net/jhfrench/GpdgF/-->
<link href="${statics}/css/tree.css" rel="stylesheet" type="text/css" />
<@ajax.template '子系统与菜单'>
      	<@f.htmlrow>
      		<@f.htmlcol 12>
      			<div id="${portletid}Btns" class="text-right" style="padding-bottom: 2px;">
      				<strong>如需添加子节点，请先选择父节点</strong>
                  	<button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#${portletid}resourceModal" id="${portletid}add">新增</button>
                  	<button type="button" class="btn btn-primary btn-sm" data-toggle="modal" id="${portletid}BtnUpdate" >修改</button>
                  	<button type="button" class="btn btn-danger btn-sm" data-toggle="modal" id="${portletid}BtnDel">删除</button>
                  	<button type="button" class="btn btn-success btn-sm" data-toggle="modal" id="${portletid}BtnSee">查看</button>
                  	<button type="button" class="btn btn-default btn-sm" data-toggle="modal" id="${portletid}BtnClear">清空</button>
                </div>
                <div class="tree well" id="${portletid}resourceJsonTree"></div>
      		</@f.htmlcol>     
      	</@f.htmlrow>
</@ajax.template>


<@ajax.modal id=portletid+'resourceModal' title='编辑'>
	<form id="${portletid}resource_edit_form" action="${ctx}/security/resource/saveOrUpdate" method="POST" class="form-horizontal">
		<input type="hidden" name="id" value="-1"/>
        <div class="form-group">
            <input type="text" class="form-control" name="name" placeholder="名称">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="code" placeholder="代码">
        </div>
        <div class="checkbox">
            <label>
            	<input type="radio" value="1" name="type" class="uniform">(子系统)-最多支持两级
            </label>
        </div>
        <div class="checkbox">
            <label>
              	<input type="radio" value="2" name="type" class="uniform">(菜单)-必须挂在子系统下面
            </label>
        </div>
        <div class="checkbox">
            <label>
              	<input type="radio" value="3" name="type" class="uniform">最终的请求,下面不能挂菜单
            </label>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="url" placeholder="url">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="authUrl" placeholder="url授权标注 /xxxx/** 下的所有url hasAnyRoles 的才算通过">
        </div>
        
        <div class="form-group">
            <input type="text" class="form-control" readonly = "readonly" name="hasAnyRoles" placeholder="只用于展示不能增加">
        </div>
        
        <div class="form-group">
            <input type="text" class="form-control" name="orderNo" placeholder="排序">
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="parentCode" placeholder="父节点编码">
        </div>
     </form>
</@ajax.modal>

<script type="text/javascript">
	 $(function () {
		   	function ${portletid}Tree(jsonData){
		   		var html="";
		   		if(jsonData&&jsonData.length>0){
		   			html+="<ul>";
		   			$.each(jsonData, function (n, r) {  
						var nodeHtml="<li><input type='radio' name='flag' value='"+r.node.code+"_"+r.node.id+"'><span>"+r.node.name+"</span>";
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
					success: function(treeData){
						var treeHtml = "<ul>";
						$.each(treeData, function (n, r) {  
							var nodeHtml="<li><input type='radio' name='flag' value='"+r.node.code+"_"+r.node.id+"'><span>"+r.node.name+"</span>";
						    nodeHtml+=${portletid}Tree(r.children);
						    nodeHtml+="</li>";
						    treeHtml+=nodeHtml;
			          	});  
	          			treeHtml+="</ul>";
						$("#${portletid}resourceJsonTree").empty().append(treeHtml);
						
						$('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Collapse this branch');
					    $('.tree li.parent_li > span').on('click', function (e) {
					        var children = $(this).parent('li.parent_li').find(' > ul > li');
					        if (children.is(":visible")) {
					            children.hide('fast');
					            //将 icon-plus-sign 替换成 glyphicon glyphicon-plus
					            //$(this).attr('title', 'Expand this branch').addClass('icon-plus-sign');
					            $(this).attr('title', 'Expand this branch').find(' > li').addClass('icon-plus-sign').removeClass('icon-minus-sign');
					        } else {
					            children.show('fast');
					            $(this).attr('title', 'Collapse this branch').find(' > li').addClass('icon-minus-sign').removeClass('icon-plus-sign');
					        }
					        e.stopPropagation();
					    });
					}
				});
		    }
			
			${portletid}loadTree();
							
			$('#${portletid}Btns').on('click','#${portletid}BtnDel',function(){
				var code = $("#${portletid}resourceJsonTree input[name='flag']:checked").val();
				code = code.split("_")[0];
				if(confirm("确认删除代码为"+code+"的元素?")){
					$.ajax({ 
						url: "${ctx}/security/resource/deleteResource",
						data:{'code':code}, 
						dataType:"json", 
						success: function(){
							sysAlert("删除完毕");
							${portletid}loadTree();
						}
					});
				}
			});
			
			$('#${portletid}add').on('click',function(){
				$("#${portletid}resource_edit_form :text").val("");
				$("#${portletid}resource_edit_form :text").prop("readonly",false);
				$("#${portletid}resource_edit_form input[name='type']").prop('checked',false);
				$("#${portletid}resource_edit_form input[name='type']").prop("disabled",false);
				$("#${portletid}resource_edit_form input[name='id']").val(0);
				$("#${portletid}resource_edit_form :input[name='hasAnyRoles']").prop("readonly",true);
				$("#${portletid}resource_edit_form :input[name='parentCode']").prop("readonly",true);
				$("#${portletid}resourceModal #${portletid}resourceModalLabel").text("新增");
				$("#${portletid}resourceModal #${portletid}resourceModalBtn").prop("disabled",false);
				//如果没有选中radio，说明是1级菜单
				var resourceCode = $("#${portletid}resourceJsonTree input[name='flag']:checked").val();
				if(resourceCode ==  undefined){
					
				}else{
					$("#${portletid}resource_edit_form input[name='parentCode']").val(resourceCode.split("_")[0]);//code
					//resourceCode.split("_")[1];//id
				}
			});
			
			//查看资源节点
			$('#${portletid}Btns').on('click','#${portletid}BtnSee',function(){
				var code = $("#${portletid}resourceJsonTree input[name='flag']:checked").val();
				if(code == undefined){
					sysAlert("请选择查看的菜单节点!");
					return;
				}
				code = code.split("_")[0];
				findResourceByCode(code,true);
				
			});
			
			//修改资源节点
			$('#${portletid}Btns').on('click','#${portletid}BtnUpdate',function(){
				var code = $("#${portletid}resourceJsonTree input[name='flag']:checked").val();
				if(code == undefined){
					sysAlert("请选择修改的菜单节点!");
					return;
				}
				code = code.split("_")[0];
				findResourceByCode(code,false);
			});
			
			function findResourceByCode(resourceCode,seeFlag){
				$("#${portletid}resource_edit_form").resetForm();
				$("input[name='type']").prop('checked',false);
				var node;
				$.ajax({ 
					url: "${ctx}/security/resource/selectResource",
					data:{'code':resourceCode}, 
					dataType:"json", 
					success: function(data){
						$("#${portletid}resourceModal").modal("show");
						node = data.result;
						$("#${portletid}resource_edit_form :hidden[name='id']").val(node.id);
						$("#${portletid}resource_edit_form input[name='name']").val(node.name);
						$("#${portletid}resource_edit_form input[name='code']").val(node.code);
						var radios = $("#${portletid}resource_edit_form :radio[name='type']");
						for(var i = 0 ; i < radios.length ; i++){
							if($(radios[i]).val() == node.type){
								$(radios[i]).prop("checked",true);
								break;
							}
						}
						$("#${portletid}resource_edit_form input[name='url']").val(node.url);
						$("#${portletid}resource_edit_form input[name='authUrl']").val(node.authUrl);
						$("#${portletid}resource_edit_form input[name='hasAnyRoles']").val(node.hasAnyRoles);
						$("#${portletid}resource_edit_form input[name='orderNo']").val(node.orderNo);
						$("#${portletid}resource_edit_form input[name='parentCode']").val(node.parentCode);
						
						//如果是查看，将所有录入框设置为只读
						if(seeFlag){
							$("#${portletid}resource_edit_form input").prop("readonly",true);
							$("#${portletid}resource_edit_form :radio").prop("disabled",true);
							$("#${portletid}resourceModal #${portletid}resourceModalLabel").text("查看");
							$("#${portletid}resourceModal #${portletid}resourceModalBtn").prop("disabled",true);
						}else{
							$("#${portletid}resourceModal #${portletid}resourceModalLabel").text("修改");
							$("#${portletid}resource_edit_form input").prop("readonly",false);
							$("#${portletid}resource_edit_form :radio").prop("disabled",false);
							$("#${portletid}resourceModal #${portletid}resourceModalBtn").prop("disabled",false);
							//资源代码，父节点，资源拥有的角色  这三个字段不能修改
							$("#${portletid}resource_edit_form input[name='code']").prop("readonly",true);
							$("#${portletid}resource_edit_form input[name='parentCode']").prop("readonly",true);
							$("#${portletid}resource_edit_form input[name='hasAnyRoles']").prop("readonly",true);
						}
					}
				});
			}
			
			$("#${portletid}resourceModal").on('click','#${portletid}resourceModalBtn',function(){
				$("#${portletid}resource_edit_form").submit();
			});
			
			//取消选择
			$('#${portletid}Btns').on('click','#${portletid}BtnClear',function(){
				$("#${portletid}resourceJsonTree :radio[name='flag']").prop("checked",false);
			});
			
			$("#${portletid}resource_edit_form").validate({
				rules: {
					name: {required:true,minlength: 2,maxlength:10},
					code: {required:true,minlength: 2,maxlength:20},
					type: {required:true},
					url: {maxlength:50},
					authUrl: {maxlength:200},
					hasAnyRoles: {maxlength:200},
					orderNo: {required:true,number:true,min:0},
					parentCode: {minlength: 2,maxlength:20}
				},
				submitHandler: function(form) {
					$("#${portletid}resourceModalBtn").prop("disabled",true);
					$(form).ajaxSubmit({
						type:"post",  
						dataType:"json", 
						success:function(data){ 
							if(data.success){
								${portletid}loadTree();
								$("#${portletid}resourceModal").modal('hide');
								$("#${portletid}resource_edit_form").resetForm();
								$("#${portletid}resource_edit_form input:hidden").val("-1");
							}else if(data.errorCode == -2){
								sysAlert("代码重复,请检查");
							}
							$("#${portletid}resourceModalBtn").prop("disabled",false);
						}  
					}); 
					return false; 
				}
			});
	});
</script>