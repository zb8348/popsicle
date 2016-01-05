<#include "/ftl/template/front/contants.ftl">
<#import "/ftl/template/front/template.ftl" as template>

<@template.html title="注册">
<style  type='text/css'>
form label.error{width: 200px;margin-left: 10px; color: Red;}
</style>
<script>
	selectMen('security_register');
</script>
	<div class="row">
		<div class="col-md-offset-3 col-sm-3" style="padding-top: 20px;">
			<div class="form-signin">
				<hr>
		      	<div class="text-center">系统登录</div>
		      	<hr>
		      <div class="tab-content">
		        <div id="register" class="tab-pane active">
		          <form id="register_form" action="${ctx}/register" method="POST">
				        <#if error?exists>
				          	<p class="text-muted text-center" style="color:red;">
				              	<div class="alert alert-error">${error}注册失败，请重试.</div>
				            </p>
			            </#if>
			            <@form.errors path="*"/>  
	                    <div class="modal-body">
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
	                    </div>
	                    <div class="modal-footer">
	                        <button class="btn btn-success" type="submit">提交</button>
	                    </div>
		          </form>
		        </div>
		      </div>
		    </div>
		</div>
	</div>
<script src="${statics}/statics/lib/jquery-form/jquery.form.js?version=${version}"></script>
<script type="text/javascript" src="${statics}/statics/lib/jquery-validation/dist/jquery.validate.min.js"></script>
<script type="text/javascript" src="${statics}/statics/lib/jquery-validation/src/localization/messages_zh.js"></script>	
<script type="text/javascript">	
	$("#register_form").validate({
			rules: {
				name: {required:true,minlength: 2,maxlength:30},
				loginName: {required:true,minlength: 4,maxlength:30},
				password: {required:true,minlength: 5,maxlength:20},
				email: {required:true,email:true,maxlength:50},
				confirmPassword:{required:true,minlength: 5,maxlength:20,equalTo:'#signupPassword'}
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
	        }
		});
</script>
</@template.html>
