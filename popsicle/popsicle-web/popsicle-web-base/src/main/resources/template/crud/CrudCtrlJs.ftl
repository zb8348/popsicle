var cols = ${jsonEntityinfo};
var datatableConfig = {
	ajax: {
        url: ctx+"${preUrl}/r",
        dataSrc: "data",
        type: "POST",
        data:function(d){
        	if(d.order){
        		var index = d.order[0].column;
        		var orderType = d.order[0].dir;
        		var orderName = d.columns[index].data;
        		d.orderType=orderType;
				d.orderName=orderName;
        	}
        }
    },
    order: [ 1, 'asc' ]
};
var crud = $('#CRUD_data_table').crud({
	_entityAttrs:cols,
	_dtEL:'#CRUD_data_table',
	_dtSettings:datatableConfig,
	
	_btnsEL:'#CRUD_table_tools',
	_edit:{
		_create : {
			_EL : '#CRUD_crudModal_create',
			_formEL : '#CRUD_create_form',
			_url:ctx+'${preUrl}/c'
		},
		_update : {
			_EL : '#CRUD_crudModal_update',
			_formEL : '#CRUD_update_form',
			_url:ctx+'${preUrl}/u'
		},
		_find : {
			_EL : '#CRUD_crudModal_find',
			_formEL : '#CRUD_find_form',
			_url:ctx+'${preUrl}/r'
		},
		_delete : {
			_EL : '#CRUD_crudModal_delete',
			_formEL : '#CRUD_delete_form',
			_url:ctx+'${preUrl}/d'
		}
	}
});
var viewer = crud.getViewer();
var CRUD_table = viewer.getDataTable();
var controller = crud.getController();