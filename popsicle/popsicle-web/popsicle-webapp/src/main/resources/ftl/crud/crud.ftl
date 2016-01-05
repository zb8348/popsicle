<#include "/ftl/common/include.ftl">
<#import "/ftl/macro/ajaxTemplate.ftl" as ajax>
<style  type='text/css'>
.Wdate{
	height:34px;
	width:100%;
}
</style>
<@ajax.template '交易组'>
		<@f.htmlrow>
      		<@f.htmlcol 12>
                  	<div id="CRUD_table_tools">
                  		<div class="text-right" style="padding-bottom: 2px;">
               	 			<div class="btn-group">
							  <button type="button" class="btn btn-info btn-sm">列属性</button>
							  <button type="button" class="btn btn-info btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							    <span class="caret"></span>
							    <span class="sr-only">Toggle Dropdown</span>
							  </button>
							  <ul class="dropdown-menu" style="padding-left: 10px;">
							  </ul>
							</div>
               	 		</div>
                  	</div>
                  	 <table id="CRUD_data_table" class="table table-striped table-bordered display" cellspacing="0" width="100%" style="margin: 0px 0px 1px 0px;">
				        <thead>
				        <tr>
				        </tr>
				        </thead>      
				        <tbody></tbody>
				        <!-- tbody是必须的 -->
				    </table>
      		</@f.htmlcol>     
      	</@f.htmlrow>
</@ajax.template>

<!-- 涉及表单验证时，同一个表单操作容易出错，故每个操作使用一个表单 -->
<@ajax.modal id='CRUD_crudModal_create' title='新增'>
	<form id="CRUD_create_form" class="form-horizontal" action="#" method="post">
     </form>
</@ajax.modal>

<@ajax.modal id='CRUD_crudModal_update' title='修改'>
	<form id="CRUD_update_form" class="form-horizontal" action="#" method="post">
     </form>
</@ajax.modal>

<@ajax.modal id='CRUD_crudModal_find' title='查找'>
	<form id="CRUD_find_form" class="form-horizontal" action="#" method="post">
     </form>
</@ajax.modal>

<@ajax.modal id='CRUD_crudModal_delete' title='修改'>
	<form id="CRUD_delete_form" class="form-horizontal" action="#" method="post">
     </form>
</@ajax.modal>

<@ajax.lgModal id='CRUD_ajaxCrudModal' title=''>
</@ajax.lgModal>

<script type="text/javascript">
var ctx='${ctx}';
</script>
<script src="${statics}/popsicle/js/crud.js"></script>
<script src="${statics}/popsicle/js/web${mvc_js}"></script>
<script type="text/javascript">

$(function () {

		
});
</script>
