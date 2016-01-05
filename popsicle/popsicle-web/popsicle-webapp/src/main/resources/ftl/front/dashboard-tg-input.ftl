<#include "/ftl/template/front/contants.ftl">
<#import "/ftl/template/front/template.ftl" as template>

<@template.html title="仪表盘-交易组导入">
	 <!-- DataTables CSS -->
	 <link rel="stylesheet" type="text/css" href="${statics}/statics/lib/DataTables-1.10.7/media/css/jquery.dataTables.min.css?version=${version}">
	 <script src="${statics}/statics/popsicle/js/sys.js?version=${version}"></script>
	
	 <link rel="stylesheet" type="text/css" href="${statics}/statics/lib/DataTables-1.10.7/media/css/jquery.dataTables.min.css?version=${version}">
	  <link rel="stylesheet" type="text/css" href="${statics}/statics/front/css/front.css?version=${version}">
<style  type='text/css'>
</style>
<script>
	selectMen('front','front_dashboard');
</script>
	
	
<div class='container' id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">仪表盘</h1>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
        	  <div class="col-lg-8">
                <!--enctype="multipart/form-data"-->
                <form class="form-horizontal" action="${ctx}/front/tg/importJson" method="post" id='newTGForm'>
                	 <input type="hidden" id="tg" name="tg" value="">
                    <div class="form-group">
                        <label for="tgName" class="col-sm-3 control-label">标识 ： </label>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="tgName" name="tgName" placeholder="取个名字" value="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="timearea" class="col-sm-3 control-label">时区调整： </label>
                        <div class="col-sm-9">
                        	<select class="form-control" id="timearea" name="timearea" style="width:100px;">
                        		<#list -23..23 as i>
							  		<option value="${i}">${i}</option>
							  	</#list>
							</select>
							将导入文件数据时间调整到UTC标准时间,输入-23~23
                        </div>
                    </div>
                    
                    <div id="tg_file_group" class="form-group">
                        <input type="file" class="form-control" id="tgSelector" name="file" style="display:none">
                        <a class="btn col-sm-3 control-label" onclick="$('input[id=tgSelector]').click();">点击选择</a>
                        <div class="col-sm-9">
                            <input type="text" class="form-control" id="tgFileName" name="tgFileName" value="" readonly>
                        </div>
                    </div>
                    
                    <div class="text-right">
                	 <button type="submit" class="btn btn-primary btn_submit">导入</button>
                	</div> 
                </form>
            </div>
        </div>
        <!-- /.row -->
        <div class="row">
        	<div class="col-lg-12">
                  	 <table id="tg_table" class="table table-striped table-bordered">
				        <thead>
				        <tr>
				        	<th>ticket</th>
				        	<th><input id='checkall' type="checkbox" name="checkall" value="全选"/></th>
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
		$('#tgFileName').val('');
		$('#tgName').val('');
		$('#tg').val('');
		$('#file').val('');
		$("#timearea").val(0);
		
		var tg_table = $('#tg_table').DataTable({
	    	//searching: false,
	    	ordering:true,
	    	"processing": true,
        	//data: dataTableData,
	    	processing:true,
	    	serverSide: false,
	    	"scrollY": 300,
        	"scrollX": true,
        	scrollCollapse: true,
        	paging:false,
	        columns: [
	        	{"data": "ticket"},
	        	{"data": null},
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
	        columnDefs: [
	        	{
	               	targets: 1,
	               	searchable: false,
	                orderable: false,
	                render: function (a, b, c, d) {
	                	var html = '<input class="switch select_btn" type="checkbox" name="checkedBtn" value="'+c.ticket+'"/>';
	                    return html;
	                }
	            },{ 
	        		targets: 5,
	        	  	render: function (a, b, c, d) {
	                    return moment(c.openTime).format('YYYY-MM-DD HH:mm:ss');
	                }			
	        	},{ 
	        		targets: 6,
	        	  	render: function (a, b, c, d) {
	                    return moment(c.closeTime).format('YYYY-MM-DD HH:mm:ss');
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
		
		 $('#table_tools').on('click','.deleteSelectedData',function(e){
		 	tg_table.rows('.selected').remove().draw();
		 });
                            
                            
        var tgContent = {};
        var X = XLSX;
        
         $('#newTGForm').on('change','#tgSelector',function(e){
         	$('#tgFileName').val('');
			$('#tg').val('');
			$('#file').val('');
			$('#timearea').attr("disabled","disabled");  
         	tg_table.rows().remove().draw();
         	
	   		tgContent = {};
		    var files = e.target.files;
		    var f = files[0];
		    {
		        var reader = new FileReader();
		        tgContent.fileName = f.name;
		        reader.onload = function(e) {
		            var data = e.target.result;
		            try{
		            	var wb = X.read(data, {type: 'binary'});
		            	var timearea = $('#timearea').val();
				    	var output = excel.anaylsis(wb,timearea,tg_table);
				    	$.extend(tgContent, output);
		            }catch(e){
		            	console.log(e);
		            	sysAlert("导入数据格式错误");
		            }
		        };
		        reader.readAsBinaryString(f);
		    }
		
		    $('#tgFileName').val(f.name);
    	});
    	
    	
    	$("#newTGForm").validate({
			rules: {
				tgName: {required:true,minlength: 2,maxlength:30},
				tgFileName: {required:true}
			},
			messages: {
	            tgName: {
	                required: "输入交易组名称",
	                minlength: "名称不能小于{0}个字符",
	                maxlength: "名称不能大于{0}个字符"
	            },
	            tgFileName: {
	                required: "请选择导入文件"
	            }
	        },
	        submitHandler: function(form) { 
	        	if(tg_table.data().length<=0){
	        		sysAlert("没有相关数据，请核实输入");
	        		return false;
	        	}
	         	tgContent.name = $('#tgName').val();
		    
	        	tgContent['transactions']=[];
	        	$.each(tg_table.data(),function(i,v){
	        		tgContent['transactions'].push(this);
	        	});
	        	var tg = $('#tg').val(JSON.stringify(tgContent));
	        	console.log(tg);
	        	if(tg&&tg.length>0&&a&&a.length>0){
	        		form.submit(); 
	        	}else{
	        		sysAlert("没有相关数据，请核实输入");
	        	}
	        }
		});
	});
</script>
    
</@template.html>

