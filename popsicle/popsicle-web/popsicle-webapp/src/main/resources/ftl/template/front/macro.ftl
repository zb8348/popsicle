<#include "/ftl/template/front/contants.ftl">

<#macro head title>
<head>
	    <meta charset="UTF-8">
	    <!--IE Compatibility modes-->
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <!--Mobile first-->
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <title>${title}</title>
	    <!-- Bootstrap -->
	    <link rel="stylesheet" href="${statics}/statics/lib/bootstrap/css/bootstrap.min.css?version=${version}">
	    <!-- Font Awesome :需设置跨域 -->
	    <link rel="stylesheet" href="${ctx}/statics/lib/Font-Awesome-master/css/font-awesome.min.css?version=${version}">
	    
	    <link href="${statics}/statics/lib/bootstrap-switch/bootstrap3/bootstrap-switch.min.css?version=${version}" rel="stylesheet" type="text/css" />
	    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	      <script src="${statics}/lib/html5shiv/html5shiv.js?version=${version}"></script>
	      <script src="${statics}/lib/respond/respond.min.js?version=${version}"></script>
	    <![endif]-->
	    <!--jQuery 2.1.1 -->
    	<script src="${statics}/statics/lib/jquery/jquery.min.js?version=${version}"></script>
    	
    	<script>
    	 var statcis='${statics}';
    	</script>
	    <#nested>
</head>
</#macro>


<#macro foot>
<!-- 主要内容 -->
<div class="container summary">
    <!-- 特性 -->
    <hr class="feature-divider">
    <ul class="nav nav-tabs" role="tablist" id="feature-tab">
        <li class="active"><a href="#tab-chrome" role="tab" data-toggle="tab">Chrome</a></li>
        <li><a href="#tab-firefox" role="tab" data-toggle="tab">Firefox</a></li>
        <li><a href="#tab-safari" role="tab" data-toggle="tab">Safari</a></li>
        <li><a href="#tab-opera" role="tab" data-toggle="tab">Opera</a></li>
        <li><a href="#tab-ie" role="tab" data-toggle="tab">IE</a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" id="tab-chrome">
            <div class="row feature">
                <div class="col-md-7">
                </div>
                <div class="col-md-5">
                </div>
            </div>
        </div>
        <div class="tab-pane" id="tab-firefox">
            <div class="row feature">
                <div class="col-md-5">
                </div>
                <div class="col-md-7">
                </div>
            </div>
        </div>
        <div class="tab-pane" id="tab-safari">
            <div class="row feature">
                <div class="col-md-7">
                </div>
                <div class="col-md-5">
                </div>
            </div>
        </div>
        <div class="tab-pane" id="tab-opera">
            <div class="row feature">
                <div class="col-md-5">
                </div>
                <div class="col-md-7">
                </div>
            </div>
        </div>
        <div class="tab-pane" id="tab-ie">
            <div class="row feature">
                <div class="col-md-7">
                </div>
                <div class="col-md-5">
                </div>
            </div>
        </div>
    </div>

	<footer class="Footer bg-dark dker">
	   <p class="pull-right"><a href="#top">回到顶部</a></p>
       <p>2015 &copy; All Right Reserved</p>
    </footer><!-- /#footer -->
    <!--Bootstrap -->
    <script src="${statics}/statics/lib/bootstrap/js/bootstrap.min.js?version=${version}"></script>
    <#nested>
</div>
</#macro>



<#macro navbar>
	<style type='text/css'>
	    body {
	        padding-top: 50px;
	    }
	</style>
<!-- 顶部导航 -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation" id="menu-nav">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">切换导航</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${ctx}">云单</a>
        </div>
        <div  id="pop_nav" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li id="share"><a href="${ctx}/share">晒单</a></li>
                <li><a href="#summary-container">简述</a></li>
                <@shiro.user>
                <li id="front" class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">我的控制台<span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li id="front_dashboard"><a href="${ctx}/front/dashboard">仪表盘</a></li>
                    </ul>
                </li>
                 </@shiro.user>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">特点 <span class="caret"></span></a>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#feature-tab" data-tab="tab-chrome">Chrome</a></li>
                        <li><a href="#feature-tab" data-tab="tab-firefox">Firefox</a></li>
                        <li><a href="#feature-tab" data-tab="tab-safari">Safari</a></li>
                        <li><a href="#feature-tab" data-tab="tab-opera">Opera</a></li>
                        <li><a href="#feature-tab" data-tab="tab-ie">IE</a></li>
                    </ul>
                </li>
                <li><a href="#" data-toggle="modal" data-target="#about-modal" data-backdrop="static">关于</a></li>
            </ul>

			<@shiro.user>
			 <ul class="nav navbar-nav navbar-right">
                <li><a>欢迎您：<@shiro.principal property="name"/>  </a></li>
                <li><a class="navbar-link" href="${ctx}/logout">注销</a></li>
                <@shiro.hasAnyRoles name="管理员">
                <li><a class="navbar-link" href="${ctx}/backend">控制台</a></li>
                </@shiro.hasAnyRoles>
             </ul>
             </@shiro.user>
           	<@shiro.guest>
             <ul class="nav navbar-nav navbar-right">
                <li id="security_register"><a href="${ctx}/register">注册</a></li>
                <li id="security_login"><a href="${ctx}/login" >登录</a></li>
             </ul>
            </@shiro.guest>
        </div>
    </div>
</div>
</#macro>



<#macro aboutme>
    <!-- 关于 -->
	<div class="modal fade" id="about-modal" tabindex="-1" role="dialog" aria-labelledby="modal-label" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
	                <h4 class="modal-title" id="modal-label">关于</h4>
	            </div>
	            <div class="modal-body">
	                <p>恩，还没想好说什么。
	                </p>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">了解了</button>
	            </div>
	        </div>
	    </div>
	</div>
</#macro>
