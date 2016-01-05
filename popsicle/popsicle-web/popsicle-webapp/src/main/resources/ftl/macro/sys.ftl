<#include "/ftl/common/include.ftl">


<#macro head title>
<head>
	    <meta charset="UTF-8">
	    <title>${title}</title>
	    <!--IE Compatibility modes-->
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <!--Mobile first-->
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <!-- Bootstrap -->
	    <link rel="stylesheet" href="${statics}/lib/bootstrap/css/bootstrap.min.css?version=${version}">
	    <!-- Font Awesome :需设置跨域 -->
	    <link rel="stylesheet" href="${ctx}/statics/lib/Font-Awesome-master/css/font-awesome.min.css?version=${version}">
	    
	    <link href="${statics}/lib/bootstrap-switch/bootstrap3/bootstrap-switch.min.css?version=${version}" rel="stylesheet" type="text/css" />
	    
	    <!-- Metis core stylesheet -->
	    <link rel="stylesheet" href="${statics}/css/main.min.css?version=${version}">
	    <!-- metisMenu stylesheet -->
	    <link rel="stylesheet" href="${statics}/lib/metismenu/metisMenu.min.css?version=${version}">
	    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	      <script src="${statics}/lib/html5shiv/html5shiv.js?version=${version}"></script>
	      <script src="${statics}/lib/respond/respond.min.js?version=${version}"></script>
	    <![endif]-->
	    <!--Modernizr 2.8.2-->
	    <script src="${statics}/lib/modernizr/modernizr.min.js?version=${version}"></script>
	    <!--jQuery 2.1.1 -->
    	<script src="${statics}/lib/jquery/jquery.min.js?version=${version}"></script>
    	
    	<script>
    	 var statcis='${statics}';
    	</script>
	    <#nested>
</head>
</#macro>

<#macro foot>
	<footer class="Footer bg-dark dker">
      <p>2015 &copy; All Right Reserved</p>
    </footer><!-- /#footer -->

    <!-- #helpModal -->
    <div id="helpModal" class="modal fade">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">系统说明</h4>
          </div>
          <div class="modal-body">
            <p>
             	系统为原移动平台的扩展和延迟平台。采用响应式布局，支持手机端浏览。
            </p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal --><!-- /#helpModal -->
    
    <div id="sysAlertModal" class="modal fade">
      <div class="modal-dialog modal-sm">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">系统提示</h4>
          </div>
          <div id="sysAlertModal_modal_body" class="modal-body">
          </div>
        </div>
      </div>
    </div>
    
    <!--Bootstrap -->
    <script src="${statics}/lib/bootstrap/js/bootstrap.min.js?version=${version}"></script>
    <!-- MetisMenu -->
    <script src="${statics}/lib/metismenu/metisMenu.min.js?version=${version}"></script>
    <!-- Screenfull -->
    <script src="${statics}/lib/screenfull/screenfull.js?version=${version}"></script>
    
    <#nested>
    <script src="${statics}/lib/bootstrap-switch/bootstrap-switch.min.js?version=${version}"></script>
    <!-- Metis core scripts -->
    <script src="${statics}/js/core.min.js?version=${version}"></script>
    <!-- Metis demo scripts
    <script src="${statics}/js/app.min.js?version=${version}"></script>
     -->
   
</#macro>



<#macro sysnav>
	  <!-- .navbar -->
        <nav class="navbar navbar-inverse navbar-static-top">
          <div class="container-fluid">

            <!-- Brand and toggle get grouped for better mobile display -->
            <header class="navbar-header">
              <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span> 
                <span class="icon-bar"></span> 
                <span class="icon-bar"></span> 
                <span class="icon-bar"></span> 
              </button>
              <a href="${ctx}/login" class="navbar-brand">
	              <#if runtime == "test">
	              	<p class="text-danger" style="padding-top: 15px; padding-left: 30px;font-size:x-large; color:red;">${runtime}</p>
	              <#else>
	              	<li class="fa fa-home fa-3x"></li>
	              </#if>
              </a> 
            </header>
            <div class="topnav">
              <div class="btn-group">
                <a data-placement="bottom" data-original-title="全屏" data-toggle="tooltip" class="btn btn-default btn-sm" id="toggleFullScreen">
                  <i class="glyphicon glyphicon-fullscreen"></i>
                </a> 
              </div>
              <div class="btn-group">
                <a data-placement="bottom" id="${portletid}showInnerMail" data-original-title="问题反馈" data-toggle="tooltip" class="btn btn-default btn-sm">
                  <i class="fa fa-envelope"></i>
                  <span class="label label-warning">问题反馈</span> 
                </a> 
                <a data-placement="bottom" data-original-title="消息" href="#" data-toggle="tooltip" class="btn btn-default btn-sm">
                  <i class="fa fa-comments"></i>
                  <span class="label label-danger">4</span> 
                </a> 
                <a data-toggle="modal" data-original-title="帮助" data-placement="bottom" class="btn btn-default btn-sm" href="#helpModal">
                  <i class="fa fa-question"></i>
                </a> 
              </div>
              <div class="btn-group">
                <a href="${ctx}/logout" data-toggle="tooltip" data-original-title="退出" data-placement="bottom" class="btn btn-metis-1 btn-sm">
                  <i class="fa fa-power-off"></i>
                </a> 
              </div>
              <div class="btn-group">
                <a data-placement="bottom" data-original-title="显示/隐藏 左侧" data-toggle="tooltip" class="btn btn-primary btn-sm toggle-left" id="menu-toggle">
                  <i class="fa fa-bars"></i>
                </a> 
                <a data-placement="bottom" data-original-title="显示/隐藏 右侧" data-toggle="tooltip" class="btn btn-default btn-sm toggle-right"> <span class="glyphicon glyphicon-comment"></span>  </a> 
              </div>
            </div>
            <div class="collapse navbar-collapse navbar-ex1-collapse">
              <!-- .nav -->
              <ul class="nav navbar-nav">
              	<#list sys_menus as s> 
              		<@shiro.hasAnyRoles name="${s.node.hasAnyRoles}">
              		<#if s.node.type==1>
	              		<#if s.children?exists>
	              			<li class='dropdown '>
	              				<#assign num=0/>
	              				<ul class="dropdown-menu">
			                  	<#list s.children as c>
              						<@shiro.hasAnyRoles name="${c.node.hasAnyRoles}">
			                  		<#if c.node.type==1>
			                  		<#assign num=num+2/>
			                  			 <li> <a href="#" onclick="javascript:sysnavSelect('${c.node.code}','${c.node.name}');">${c.node.name}</a>  </li>
			                  		</#if>
			                  		</@shiro.hasAnyRoles>
			                    </#list>
			                  </ul>
			                  <#if num==0>
			                  	<a href="#" onclick="javascript:sysnavSelect('${s.node.code}','${s.node.name}');">${s.node.name}</a>
			                  <#else>
			                  	 <a href="#" class="dropdown-toggle" data-toggle="dropdown">
				                    ${s.node.name}
				                    <b class="caret"></b>
				                  </a> 
			                  </#if>
			                </li>
	              		<#else>
	              			<li> <a href="#" onclick="javascript:sysnavSelect('${s.node.code}','${s.node.name}');">${s.node.name}</a>  </li>
	              		</#if>
              		</#if>
              		</@shiro.hasAnyRoles>
                </#list>
              </ul>
              <!-- /.nav -->
            </div>
          </div><!-- /.container-fluid -->
        </nav><!-- /.navbar -->
	<#nested>
</#macro>


<#macro header>
 		<header class="head">
          <div class="search-bar">
            <form class="main-search" action="">
              <div class="input-group">
                <input type="text" class="form-control" placeholder="Live Search ...">
                <span class="input-group-btn">
	            <button class="btn btn-primary btn-sm text-muted" type="button">
	                <i class="fa fa-search"></i>
	            </button>
	        	</span> 
              </div>
            </form><!-- /.main-search -->
          </div><!-- /.search-bar -->
          <div class="main-bar">
            <h3>
              <i class="fa fa-home"></i>&nbsp; Metis</h3>
          </div><!-- /.main-bar -->
        </header><!-- /.head -->
</#macro>

<#macro listMens menus>
	<ul>
	<#list menus as m>
		<@shiro.hasAnyRoles name="${m.node.hasAnyRoles}">
		<#if m.node.type==2>
			<#if m.children?exists>
				<li>
		            <a href="javascript:;">${m.node.name}  <span class="fa arrow"></span>  </a> 
		            <ul>
		              <@listMens menus=m.children/>
		            </ul>
		          </li>
			<#else>
				<a href="#" onclick="toUrl('${ctx}${m.node.url}')"><span class="link-title">&nbsp;${m.node.name}</span></a> 
			</#if>
		</#if>
		</@shiro.hasAnyRoles>
	</#list>
	</ul>
</#macro>

<#macro menu>
 <!-- #menu -->
        <ul id="menu" class="bg-blue dker">
          <li id="sys_nav_header" class="nav-header"></li>
          <li class="nav-divider"></li>
          <#assign firstMenu = 0>
          <#list sys_menus_nodes as m>
          	<#if m.node.type==2>
          	<#if m.node.hasAnyRoles?exists>
          	<@shiro.hasAnyRoles name="${m.node.hasAnyRoles}">
          		<#if firstMenu==0>
      			<#assign firstMenu = m.node.parentCode>
      			</#if>
      			
      			<#if firstMenu== m.node.parentCode>
      			<li class="sys_menus sys_menus_${m.node.parentCode}">
          		<#else>
      			<li class="sys_menus sys_menus_${m.node.parentCode}" style="display: none;">
          		</#if>
	          	<#if m.children?exists>
	          		<a href="javascript:;">
	          			<i class="fa fa-folder"></i>
	              		<span class="link-title">${m.node.name}</span> 
	              		<span class="fa arrow"></span> 
	            	</a> 
	            	<@listMens menus=m.children/>
	          	<#else>
	            	<a href="#" onclick="toUrl('${ctx}${m.node.url}')">
	            		<i class="fa fa-folder-o"></i>
	            		<span class="link-title">&nbsp;${m.node.name}</span>
	            	</a> 
	            </#if>
	          </li>
	         </@shiro.hasAnyRoles>
	         <#else>
	         </#if>
	         </#if>
          </#list>
        </ul><!-- /#menu -->
</#macro>

<#macro content>
	<div id="content">
	    <div class="outer">
	      <div class="inner bg-light lter">
		<#nested>
		 </div><!-- /.inner -->
        </div><!-- /.outer -->
      </div><!-- /#content -->
</#macro>

<#macro htmlrow>
	<div class="row">
		<#nested>
	</div>
</#macro>

<#macro htmlcol n>
	<div class="col-sm-${n}">
		<#nested>
	</div>
</#macro>

<#macro htmlbox title>
	<div class="box inverse">
      <header>
      	<div class="icons">
          <i class="fa fa-th-large"></i>
        </div>
        <h5>${title}</h5>
        <div class="toolbar">
        	<nav style="padding: 8px;">
                <a class="btn btn-default btn-xs collapse-box" href="javascript:;">
                  <i class="fa fa-minus"></i>
                </a> 
                <a class="btn btn-default btn-xs full-box" href="javascript:;">
                  <i class="fa fa-expand"></i>
                </a> 
                <a class="btn btn-danger btn-xs close-box" href="javascript:;">
                  <i class="fa fa-times"></i>
                </a> 
              </nav>
        </div>
      </header>
      <div class="body collapse in" aria-expanded="true">
		<#nested>
	  </div>
    </div>
</#macro>



<#macro left>
<div id="left">
		<div class="media user-media bg-dark dker">
          <div class="user-media-toggleHover">
            <span class="fa fa-user"></span> 
          </div>
          <div class="user-wrapper bg-dark">
            <a class="user-link" href="">
              <img class="media-object img-thumbnail user-img" alt="User Picture" src="${statics}/img/user.gif">
              <span class="label label-danger user-label">16</span> 
            </a> 
            <div class="media-body">
              <h5 class="media-heading"><@shiro.principal property="name"/></h5>
              <ul class="list-unstyled user-info">
                <li> <a href=""><@shiro.principal/></a>  </li>
              </ul>
            </div>
          </div>
        </div>
        
        <#nested>
</div><!-- /#left -->
</#macro>

<#macro right>
	 <div id="right" class="bg-light lter">
	 
	 	<div class="alert alert-danger">
          <button type="button" class="close" data-dismiss="alert">&times;</button>
          <p><strong>系统说明：</strong></p>
          	原业务功能请使用 <a href="http://m.yhd.com/tsmm" target="_blank">http://m.yhd.com/tsmm</a>.
        </div>

        <!-- .well well-small -->
        <div class="well well-small dark">
        	<p>系统属性：</p>
          <ul class="list-unstyled">
            <li>启用时间 <span class="inlinesparkline pull-right">2015-7</span></li>
            <li>部门<span class="dynamicsparkline pull-right">移动事业部</span></li>
            <li>团队<span class="dynamicsparkline pull-right">MOB201</span></li>
            <li>业务问题联系<span class="dynamicsparkline pull-right">yeliang1</span></li>
            <li>系统问题联系<span class="dynamicsparkline pull-right">wulibing</span></li>
          </ul>
        </div><!-- /.well well-small -->
			<#nested>
	</div><!-- /#right -->
</#macro>

<#macro checkTemplate>
<script>
		var tt=$('#wrap',window.parent.document);
		if(!(tt&&tt.html()&&tt.html().length>100)){
			window.location="${ctx}/home";
		}
</script>
</#macro>
