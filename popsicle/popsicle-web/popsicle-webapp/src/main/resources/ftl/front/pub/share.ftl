<#include "/ftl/template/front/contants.ftl">
<#import "/ftl/template/front/template.ftl" as template>

<@template.html title="晒单">
<script>
	selectMen('share');
</script>
	
	
	<div class='container' id="wrapper">
    <div id="page-wrapper">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">晒单 TOP榜</h1>
            </div>
        </div>
        <!-- /.row -->
        <#list tgs as tg>
        <div class="row">
				<div class="col-md-3">
	        		<div class="panel <#if (tg.analysisPl>0)>panel-success<#else>panel-danger</#if>">
	        			<div class="panel-heading">
	                        <div class="row">
	                            <div class="col-xs-9 ">
	                                <div class="huge">
	                                    <p style="overflow:hidden;text-overflow:ellipsis;height:16px">
	                                    	${tg.name}
	                                    </p>
	                                </div>
	                            </div>
	                            <div class="col-xs-3 text-right">
	                                <div class="huge">${tg.analysisPl}</div>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="panel-body" style="background-color:#CCCC66;padding:5px;height:120px">
	                    	<#if thumbnails?exists&&thumbnails[tg_index]?exists>
	                    		 <img class="img-responsive" style="height:100%;width:100%" src="${thumbnails[tg_index]}" alt=""/>
	                    	</#if>
	                    </div>
	                    <div class="panel-footer" style="height:41px">
	                        <span class="pull-left"><a href="${ctx}/tg/${tg.id}">查看详情</a></span>
	                    </div>
	        		</div>
        		</div>
        		<div class="col-md-9">
        			<hr/>
        			<div id="talkList${tg.id}"></div>
        		</div>
        </div>
		</#list>
        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->
    </div>
</div>
<!-- /#wrapper -->
<script type="text/javascript">
	$(function() {
		function loadList(elId,objectId){
			$.ajax({
				url:'${ctx}/viewTg/talks/'+objectId,
				//data:,
				type:'post',
				dataType:"json", 
				success:function(data){
					var html = '<ol>';
					$.each(data,function(i,v){
					console.log(this);
						html += '<li>'+i+'</il>';
					});
					html += '</ol>';
					$('#'+elId).empty().append(html);
				}
			});
		}
	<#list tgs as tg>
		loadList('talkList${tg.id}',${tg.id});
	</#list>
	});
</script>   
</@template.html>

