<#include "/ftl/template/front/contants.ftl">
<#import "/ftl/template/front/template.ftl" as template>

<@template.html title="登录">
<script>
	selectMen('security_login');
</script>

	<div class="row">
		<div class="col-md-offset-3 col-sm-3" style="padding-top: 20px;">
			<div class="form-signin">
				<hr>
		      	<div class="text-center">系统登录</div>
		      	<hr>
		      <div class="tab-content">
		        <div id="login" class="tab-pane active">
		          <form action="${ctx}/login" method="POST">
				        <#if error?exists>
				          	<p class="text-muted text-center" style="color:red;">
				              	<div class="alert alert-error">${error}登录失败，请重试.</div>
				            </p>
			            </#if>
			            <#if shiroLoginFailure?exists>
				          	<p class="text-muted text-center" style="color:red;">
				              	<div class="alert alert-error">${shiroLoginFailure}</div>
				            </p>
			            </#if>
			            
			            <@form.errors path="*"/>  
	                    <div class="modal-body">
	                        <div class="form-group">
	                            <label for="signupName">用户名</label>
	                            <input type="text" id="signupName" class="form-control" name="username" type="text" value="${username}">
	                        </div>
	                        <div class="form-group">
	                            <label for="signupPassword">密码</label>
	                            <input type="password" id="signupPassword" class="form-control" name="password" type="text">
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
</@template.html>

