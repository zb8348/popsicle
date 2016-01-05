<#include "/ftl/template/front/contants.ftl">
<#import "/ftl/template/front/template.ftl" as template>

<@template.html title="仪表盘">
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
        	<#list tgs as tg>
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
                        <span class="pull-left"><a href="${ctx}/front/tg/${tg.id}">查看详情</a></span>
                        <span class="pull-right">
                            <a type="button" href="${ctx}/front/tg/remove/${tg.id}" onClick='delConfirm()' class="close" aria-label="Close"><span aria-hidden="true">&times;</span></a>
                            <a type="button" href="${ctx}/front/tg/update/${tg.id}"><i class="fa fa-pencil-square-o fa-1"></i></a>
                        </span>
                        <!-- <span class="pull-right"><a href='/tg/remove/${tg.id}' class="glyphicon glyphicon-remove"></a></span> -->
                    </div>
        		</div>
        		</div>
        	</#list>
        	

            <div class="col-md-3" style="height:210px" >
                <button type="button" class="btn btn-default btn-lg btn-block" style="height:100%" onclick="window.location.href='${ctx}/front/tg/input'">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                </button>
            </div>
        </div>

        <!-- /.row -->
    </div>
    <!-- /#page-wrapper -->
    </div>
</div>
<!-- /#wrapper -->

<script type="text/javascript">
    function delConfirm() {
        if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }
    }
</script>
    
</@template.html>

