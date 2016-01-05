
<#macro template>
<!doctype html>
<html class="no-js">
 <@f.head title="移动后台">
 	 <script src="${statics}/lib/jquery-cookie/jquery.cookie.js?version=${version}"></script>
	 <script src="${statics}/popsicle/js/sys.js?version=${version}"></script>
	 <!-- DataTables CSS -->
	 <link rel="stylesheet" type="text/css" href="${statics}/lib/DataTables-1.10.7/media/css/jquery.dataTables.min.css?version=${version}">
	 <script src="${statics}/lib/My97DatePicker/WdatePicker.js?version=${version}"></script>
 </@f.head>
	<body class="  ">
		<div class="bg-dark dk" id="wrap">
			<div id="top">
				<@f.sysnav/>
		 		<#--<@f.header/>-->
		 	</div><!-- /#top -->
		 	
		 	<@f.left>
				<@f.menu/>
      		</@f.left>
      		
      		<@f.content>
      					<#nested>
      		</@f.content>
      		
      		<@f.right/>      
    	</div><!-- /#wrap -->
		<@f.foot>
			<!-- DataTables -->
			<script type="text/javascript" charset="utf8" src="${statics}/lib/DataTables-1.10.7/media/js/jquery.dataTables.min.js?version=${version}"></script>



			<script src="${statics}/lib/jquery-form/jquery.form.js?version=${version}"></script>
			<script type="text/javascript" src="${statics}/lib/jquery-validation/dist/jquery.validate.min.js"></script>
			<script type="text/javascript" src="${statics}/lib/jquery-validation/src/localization/messages_zh.js"></script>

		    <link rel="stylesheet" href="${statics}/popsicle/css/sys.css?version=${version}"></script>
    	</@f.foot>
	</body>
</html>
</#macro>

