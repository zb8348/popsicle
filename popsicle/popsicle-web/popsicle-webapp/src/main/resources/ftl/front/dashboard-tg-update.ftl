<#include "/ftl/template/front/contants.ftl">
<#import "/ftl/template/front/template.ftl" as template>

<@template.html title="仪表盘-交易组导入">
	 <!-- DataTables CSS -->
	 <script src="${statics}/statics/popsicle/js/sys.js?version=${version}"></script>
	
	 <link rel="stylesheet" type="text/css" href="${statics}/statics/lib/DataTables-1.10.7/media/css/jquery.dataTables.min.css?version=${version}">
	  <link rel="stylesheet" type="text/css" href="${statics}/statics/front/css/front.css?version=${version}">
<style  type='text/css'>
td.details-control {
	background: url('${statics}/statics/lib/DataTables-1.10.7/examples/resources/details_open.png') no-repeat center center;
	width:40px;
	cursor: pointer;
}
tr.shown td.details-control {
	background: url('${statics}/statics/lib/DataTables-1.10.7/examples/resources/details_close.png') no-repeat center center;
}
</style>
<script>
	selectMen('front','front_dashboard');
</script>
	
	
<div class='container' id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">交易组信息</h1>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
        	 <div class="col-lg-6">
                <!--enctype="multipart/form-data"-->
                <form class="form-horizontal" method="post" id='newTGForm'>
                	<input type="hidden" name="id" value="${id}">
                    <div class="form-group">
                        <label for="name" class="col-sm-3 control-label">标识 ： </label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="name" name="name"  value="${tg.name}">
                        </div>
                    </div>
                    
                    <div class="text-right">
                	 <button type="submit" class="btn btn-primary btn_submit">修改</button>
                	</div> 
                </form>
            </div>
        	 <div class="col-lg-6">
             	<blockquote>
             		<small>详情：
					  	<dl class="dl-horizontal">
					  	  <dt>name</dt><dd id="tg_name">${tg.name}</dd>
						  <dt>fileName</dt> <dd>${tg.fileName}</dd>
						  <dt>broker</dt> <dd>${tg.broker}</dd>
						  <dt>type</dt> <dd>${tg.type}</dd>
						  <dt>accountName</dt> <dd>${tg.accountName}</dd>
						  <dt>address</dt> <dd>${tg.address}</dd>
						  <dt>analysisAmount</dt> <dd id="tg_analysisAmount">${tg.analysisAmount}</dd>
						  <dt>analysisPl</dt> <dd id="tg_analysisPl">${tg.analysisPl}</dd>
						</dl>
					</small>
					<footer>${tg.creator} @ <cite title="创建时间">${tg.createTime?string('yyyy-MM-dd HH:mm:ss')}</cite></footer>
				</blockquote>
				
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
        	<div class="col-lg-12">
                  	 <table id="tg_table"  class="display" cellspacing="0" width="100%">
				        <thead>
				        <tr>
				        	<th></th>
				        	<th><input id='checkall' type="checkbox" name="checkall" value="全选"/></th>
				        	<th>status</th>
				        	<th>ticket</th>
				            <th>currency</th>
				            <th>volume</th>
				            <th>lots</th>
				            <th>openTime</th>
				            <th>closeTime</th>
				            <th>openType</th>
				            <th>closeType</th>
				            <th>direction</th>
				            <th>openPosition</th>
				            <th>closePosition</th>
				            <th>netPL</th>
				            <th>grossPL</th>
				        </tr>
				        </thead>      
				        <tbody></tbody>
				        <!-- tbody是必须的 -->
				    </table>
			</div>	    
        </div>
    </div>
    <!-- /#page-wrapper -->
    </div>
</div>
<!-- /#wrapper -->





<!-- Modal -->
<div class="modal fade" id="saveAs" tabindex="-1" role="dialog" aria-labelledby="saveAsLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="saveAsLabel">另存为新的交易组</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form class="form-horizontal" id="saveAs_form" action="${ctx}/front/tg/saveAs" method="post">
                    	<input type="hidden" name="groupId" value="${tg.id}"/>
                    	<input type="hidden" id="saveAs_form_ids" name="ids[]" value=""/>
                        <div class="form-group">
                            <label for="name" class="col-sm-3 control-label">标识 ： </label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" name="name" placeholder="取个名字" value=""/>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary saveAs_form_submit">保存</button>
            </div>
        </div>
    </div>
</div>


<script src="${statics}/statics/lib/jquery-form/jquery.form.js?version=${version}"></script>
<script type="text/javascript" src="${statics}/statics/lib/jquery-validation/dist/jquery.validate.min.js"></script>
<script type="text/javascript" src="${statics}/statics/lib/jquery-validation/src/localization/messages_zh.js"></script>	
<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="${statics}/statics/lib/DataTables-1.10.7/media/js/jquery.dataTables.min.js?version=${version}"></script>
<script src="${statics}/statics/front/js/js-xlsx/dist/xlsx.core.min.js"></script>
<script src="${statics}/statics/front/js/excel.js"></script>
<script src="${statics}/statics/lib/moment/moment.min.js"></script>

<script type="text/javascript">

	$(function() {
		var tg_table = $('#tg_table').DataTable({
	    	//searching: false,
	    	ordering:true,
	    	"processing": true,
        	//data: dataTableData,
	    	serverSide: false,
	    	ajax: {
	            url: "${ctx}/front/tg/ts/${tg.id}",
	            dataSrc: ""
	        },
	    	"scrollY": 300,
        	"scrollX": true,
        	scrollCollapse: true,
        	paging:false,
	        columns: [
		         {
	                "className":      'details-control',
	                searchable: false,
	                orderable: false,
	                "data":null,
	                "defaultContent": ''
	            },
	            {
	            	"data": null,
	            	searchable: false,
	                orderable: false,
	                "defaultContent": '<input class="switch select_btn" type="checkbox" name="checkedBtn" value="1"/>'},
	            {"data": "status"},
	        	{"data": "ticket"},
	            {"data": "currency"},
	            {"data": "volume"},
	            {"data": "lots"},
	            {"data": "openTime"},
	            {"data": "closeTime"},
	            {"data": "openType"},
	            {"data": "closeType"},
	            {"data": "direction"},
	            {"data": "openPosition"},
	            {"data": "closePosition"},
	            {"data": "netPL"},
	            {"data": "grossPL"}
	        ],
	         order: [ 3, 'asc' ],
	         columnDefs: [
	            {
	                targets: 2,
	                render: function (a,b,c,d) {
	                	var switchType = '无效';
	                	if(c.status == 1){
	                		switchType = '';
	                	}
	                    return switchType;
	                }
	            }
	         ],
	       // dom: page_table.dom,
	        language:page_table.language,
	        footerCallback: function ( row, data, start, end, display ) {
	        }
		});
                       
		var btns = '<div id="table_tools" style="width:200px;float:left;margin-bottom: 5px;"><div class="text-left">'
                        +'<button type="button" class="btn btn-danger btn-sm deleteSelectedData">删除</button>'
                        +'<button type="button" class="btn btn-info btn-sm saveAsSelectedData" data-toggle="modal" data-target="#saveAs" data-backdrop="static">数据另存</button>'
                        +'</div></div>';
                  $('.dataTables_filter').prepend(btns);
		
		
		$('#tg_table').on('click','.select_btn',function(){
			if(this.checked){
				$(this).closest('tr').addClass('selected');
			}else{
				$(this).closest('tr').removeClass('selected');
			}
		});
		$('.dataTable').on('click','#checkall',function(){
            $('.select_btn').trigger("click")
		});
		
		
		function format(c){
			var html = ' <form class="form-horizontal tupdate_form" id="tupdate_form_'+c.ticket+'" action="#" method="post" cellspacing="0" border="0" style="padding-left:50px;">          '
			  +'   	<input type="hidden" name="id" value="'+c.id+'"><input type="hidden" name="groupId" value="'+c.groupId+'">                                                     '
			  +'   	<table>                                                                                                  '
			  +' 		  <tr>                                                                                               '
			  +' 		  	<td colspan=4><button class="btn btn-primary btn-xs tupdate_form_submit" data-form-id="tupdate_form_'+c.ticket+'">保存</button></td>            '
			  +'           </tr>                                                                                             '
			  +' 		  <tr>                                                                                               '
			  +'   			  <td><label style="width:60px;">ticket:</label><input type="text" name="ticket" value="'+c.ticket+'"/></td>                        '
			  +' 			  <td><label style="width:60px;">status:</label><input type="text" name="status" value="'+c.status+'"/></td>                        '
			  +' 			  <td><label style="width:60px;">currency:</label><input type="text" name="currency" value="'+c.currency+'"/></td>                  '
			  +'              <td><label style="width:60px;">volume:</label><input type="text" name="volume" value="'+c.volume+'"/></td>                       '
			  +'           </tr>                                                                                             '
			  +'           <tr>                                                                                              '
			  +'               <td><label style="width:60px;">lots:</label><input type="text" name="lots" value="'+c.lots+'"/></td>                             '
			  +'               <td><label style="width:60px;">openTime:</label><input type="text" name="openTime" value="'+c.openTime+'"/></td>                 '
			  +'               <td><label style="width:60px;">closeTime:</label><input type="text" name="closeTime" value="'+c.closeTime+'"/></td>              '
			  +'               <td><label style="width:60px;">openType:</label><input type="text" name="openType" value="'+c.openType+'"/></td>                 '
			  +'           </tr>                                                                                             '
			  +'           <tr>                                                                                              '
			  +'               <td><label style="width:60px;">closeType:</label><input type="text" name="closeType" value="'+c.closeType+'"/></td>              '
			  +'               <td><label style="width:60px;">direction:</label><input type="text" name="direction" value="'+c.direction+'"/></td>              '
			  +'               <td><label style="width:60px;">openPosition:</label><input type="text" name="openPosition" value="'+c.openPosition+'"/></td>     '
			  +'               <td><label style="width:60px;">closePosition:</label><input type="text" name="closePosition" value="'+c.closePosition+'"/></td>  '
			  +'           </tr>                                                                                             '
			  +'           <tr>                                                                                              '
			  +'               <td><label style="width:60px;">netPL:</label><input type="text" name="netPL" value="'+c.netPL+'"/></td>                          '
			  +'               <td><label style="width:60px;">grossPL:</label><input type="text" name="grossPL" value="'+c.grossPL+'"/></td>                    '
			  +'               <td></td>                                                                                     '
			  +'               <td></td>                                                                                     '
			  +'           </tr>                                                                                             '
			  +'       </table>                                                                                              '
			  +'  </form>                                                                                                    ';
	       return html;
		}
		 $('#table_tools').on('click','.deleteSelectedData',function(e){
		 	var rows = tg_table.rows('.selected');
		 	if(rows&&rows.data().length>0){
		 		var ids=[];
		 		$.each(rows.data(),function(i,v){
		 			ids.push(this.id);
		 		});
		 		if(ids.length>0){
		 			$.ajax({
						url: "${ctx}/front/tg/ts/delete/${tg.id}",
						type:"POST",
						data: {ids:ids},
						success: function(data){
							if(data.success==true){
		    					flushData(data.result)
		    				}else{
		    					sysAlert(data.errorMessage);
		    				}
						}
					});
		 		}else{
			 		sysAlert("请选择操作的数据！");
			 	}
		 	}else{
		 		sysAlert("请选择操作的数据！");
		 	}
		 	//tg_table.rows('.selected').remove().draw();
		 });
		 
	    $('#tg_table tbody').on('click', 'td.details-control', function () {
	        var tr = $(this).closest('tr');
	        var row = tg_table.row( tr );
	        if ( row.child.isShown() ) {
	            row.child.hide();
	            tr.removeClass('shown');
	        }else {
	            row.child( format(row.data()) ).show();
	            tr.addClass('shown');
	        }
	    } );
    	
    	$("#newTGForm").submit(function(){
			return false;
		});
    	$("#newTGForm").validate({
			rules: {
				name: {required:true,minlength: 2,maxlength:30}
			},
			messages: {
	            name: {
	                required: "输入交易组名称",
	                minlength: "名称不能小于{0}个字符",
	                maxlength: "名称不能大于{0}个字符"
	            }
	        },
	        submitHandler: function(form) { 
	        	//var name = $('#name').val();
	        	$(form).ajaxSubmit({
	    			type:"post",
	    			//data:{"id":${tg.id},"name":name},
	    			dataType:"json",
	    			url:"${ctx}/front/tg/updateInfo",
	    			success:function(data){
	    				if(data.success==true){
	    					sysAlert("修改成功");
	    					$('#tg_name').empty().append(name);
	    				}else{
	    					sysAlert(data.errorMessage);
	    				}
	                }  
	            });
	        }
		});
		
		$('#tg_table').on('click', '.tupdate_form_submit', function () {
				var formId = $(this).attr('data-form-id');
		    	$("#"+formId).validate({
					rules: {
						ticket: {required:true,minlength: 2,maxlength:30},
						status: {required:true,min:0,max:9}
					},
					messages: {
			            ticket: {
			                required: "输入交易组名称",
			                minlength: "名称不能小于{0}个字符",
			                maxlength: "名称不能大于{0}个字符"
			            },
			            status: {
			                required: "状态必输",
			                min: "状态不能小于{0}",
			                max: "状态不能大于{0}"
			            }
			        },
			        submitHandler: function(form) { 
			        	$(form).ajaxSubmit({
			    			type:"post",
			    			dataType:"json",
			    			url:'${ctx}/front/tg/ts/updateInfo',
			    			success:function(data){
			    				if(data.success==true){
			    					flushData(data.result)
			    				}else{
			    					sysAlert(data.errorMessage);
			    				}
			                }  
			            });
			            return false;
			        }
				});
	    } );
	    
	    function flushData(result){
	    	sysAlert("操作成功");
	    	tg_table.ajax.reload();
	    	$("#tg_analysisAmount").empty().append(result.analysisAmount);
	    	$("#tg_analysisPl").empty().append(result.analysisPl);
	    }
	    
	    
	    
	    $('#saveAs').on('click', '.saveAs_form_submit', function () {
	    	var rows = tg_table.rows('.selected');
		 	if(rows&&rows.data().length>0){
		 		var ids=[];
		 		$.each(rows.data(),function(i,v){
		 			ids.push(this.id);
		 		});
		 		if(ids.length>0){
		 			console.log(ids);
		 			$('#saveAs_form_ids').val(ids);
		 			console.log("submit");
		 			$('#saveAs_form').submit();
		 			console.log("ok");
		 		}else{
			 		sysAlert("请选择操作的数据！");
			 	}
		 	}else{
		 		sysAlert("请选择操作的数据！");
		 	}
	    });
	    
		$("#saveAs_form").validate({
			rules: {
				name: {required:true,minlength: 2,maxlength:30}
			},
			messages: {
	            name: {
	                required: "输入交易组名称",
	                minlength: "名称不能小于{0}个字符",
	                maxlength: "名称不能大于{0}个字符"
	            }
	        },
	        submitHandler: function(form) {
	            return true;
	        }
		});
	    
	});
</script>
    
</@template.html>

