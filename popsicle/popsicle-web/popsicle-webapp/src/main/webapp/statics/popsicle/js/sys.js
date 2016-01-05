function sysnavSelect(code,name){
	$('#sys_nav_header').empty().append(name);
	$('.sys_menus').hide();//"slow"
	$('.sys_menus_'+code).show();//"slow"
	
	//signedSelectedSysInCookies(code,name,false);
}

function signedSelectedSysInCookies(code,name,isdefualt){
	if(isdefualt===true){
		$.cookie('selected_defualt_system_name', name);
		$.cookie('selected_defualt_system_code', code);
	}else{
		$.removeCookie('selected_system_name');
		$.removeCookie('selected_system_code');
		$.cookie('selected_system_name', name);
		$.cookie('selected_system_code', code);
	}
}

function loadSelectedMenu(){
	if($.cookie('selected_system_code')){
		sysnavSelect($.cookie('selected_system_code'),$.cookie('selected_system_name'));
	}else{
		sysnavSelect($.cookie('selected_defualt_system_code'),$.cookie('selected_defualt_system_name'));
	}
}

function toUrl(url){
	//$('#content_iframe').attr("src",url); 
	$.ajax({
		url:url,
		type: "POST",
		dataType:'html',
		success:function(html){
			var doms = $.parseHTML(html,true);
			$('#popsicle_main_content').empty().prepend(doms);//.empty()
			
			//加载外部js，并执行: 窗口最大最小化缺陷——动态新增html元素，jquery 动态事件绑定，需重新加载，
			var urls=[statcis+'/js/core.min.js'];
			$.getScript(urls, function() {
			});
		}
	});
}

function sysAlert(content){
	alert(content);
	//$('#sysAlertModal_modal_body').empty().html(content);
	//$('#sysAlertModal').modal('show');
}

function sysToModal(url,modeId,title){
	$.ajax({
		url:url,
		type: "POST",
		dataType:'html',
		success:function(html){
			var doms = $.parseHTML(html,true);
			$('#'+modeId+"Label").empty().append(title);
			$('#'+modeId+"_modal_body").empty().append(html);
			
			$('#'+modeId).modal('show');
			//加载外部js，并执行: 窗口最大最小化缺陷——动态新增html元素，jquery 动态事件绑定，需重新加载，
			var urls=[statcis+'/js/core.min.js'];
			$.getScript(urls, function() {
			});
		}
	});
}

var page_table={
	dom : "<'row'r>t<'row'<'col-xs-6'li><'col-xs-6'p>>",
	domEL : "<'row'r>t<'row'<'col-xs-6'i><'col-xs-6'p>>",
	language:{
			"processing": "处理中...",
			"search": "搜索:",
			"loadingRecords": "载入中...",
		    "lengthMenu": "每页_MENU_",
		    "zeroRecords": "没有找到记录",
		    "info": "当前 _START_到 _END_条，共 _TOTAL_条记录",
		    //"infoEmpty": "无数据",
		    "infoEmpty":"",
		    //"infoFiltered": "(从 _MAX_ 条记录过滤)",
		    "infoFiltered":"",
		    "paginate": {
		         "first": "首页",
		         "previous": "上一页 ",
		         "next": "下一页 ",
		         "last": "尾页 "
		 	}
		},
	selected:function(tableid)	{
		$('#'+tableid+' tbody').on('click','tr',function(){
		        if ( $(this).hasClass('selected') ) {
		            $(this).removeClass('selected');
		        } else {
		        	$('#'+tableid+' tr.selected').removeClass('selected');
		            $(this).addClass('selected');
		        }
		    } );
	},
	//fixed:datatable本地分页，换页时，前一页所选行不会消失
	localSelected:function(tableid,table){
		this.selected(tableid);
		$('#'+tableid).on( 'page.dt', function () {
			$( table.row('.selected').nodes()).removeClass( 'selected' );
		} );
	},
	//fixed：当出现2层弹出框：（完全）关闭弹窗时将父窗口重新聚焦
	closeChildModal:function(childId){
		$('#'+childId).on('hidden.bs.modal', function () {
			//$('#${portletid}activityModal').modal('toggle'); 
			//$('#${portletid}activityModal').modal('show');
			 $("body").toggleClass("modal-open");
		});
	}
};

