var cols = [
		{attr:'id',name:'ID',visible: false,orderable: false,
			update:{type:'hidden'},
			delete:{type:'checkbox'}
		},
		{attr:'name',name:'分组名',visible: true,orderable: true,
			create:{type:'text'},
			update:{type:'none'},
			find:{type:'text'},
			delete:{type:'none'},
			validate:{
				rules:{name: {required:true,minlength: 2,maxlength:30}},
				messages:{name: {required: "没有填写分组名",minlength: "分组名不能小于{0}个字符",maxlength: "分组名不能大于{0}个字符"}}
			}
		},
		{attr:'broker',name:'代理商',visible: true,orderable: true,
			create:{type:'text',defualtValue:'FXCM'},
			update:{type:'text'},
			delete:{type:'none'},
		},
		{attr:'type',name:'类型',visible: true,orderable: true,
			create:{type:'select',defualtValue:'2',list:[{name:'a',value:1},{name:'b',value:2},{name:'c',value:3}]},
			update:{type:'select',list:[{name:'a',value:1},{name:'b',value:2},{name:'c',value:3}],readonly:true},
			find:{type:'select',list:[{name:'----',value:''},{name:'a',value:1},{name:'b',value:2},{name:'c',value:3}]},
			validate:{
				rules:{type: "required"}
			}
		},
		{attr:'accountName',name:'外汇账号',visible: true,orderable: true,
			create:{type:'checkbox',defualtValue:[1,3],list:[{name:'a',value:1},{name:'b',value:2},{name:'c',value:3}]},
			update:{type:'checkbox',list:[{name:'a',value:1},{name:'b',value:2},{name:'c',value:3}]},
			find:{type:'checkbox',list:[{name:'a',value:1},{name:'b',value:2},{name:'c',value:3}]}
		},
		{attr:'address',name:'地址',visible: false,orderable: false,
			create:{type:'textarea',defualtValue:''},
			update:{type:'textarea'}
		},
		{attr:'analysisAmount',name:'总额',visible: true,orderable: true,
			create:{type:'radio',defualtValue:'2',list:[{name:'a',value:1},{name:'b',value:2},{name:'c',value:3}]},
			update:{type:'radio',list:[{name:'a',value:1},{name:'b',value:2},{name:'c',value:3}]},
			find:{type:'radio',list:[{name:'a',value:1},{name:'b',value:2},{name:'c',value:3}]}
		},
		{attr:'analysisPl',name:'PL',visible: true,orderable: true,
			create:{type:'text',defualtValue:''},
			update:{type:'text'}
		},
		{attr:'creator',name:'创建人',visible: true,orderable: false,
			update:{type:'text'}
		},
		{attr:'createTime',name:'创建时间',visible: true,orderable: true,edit:true,
			create:{type:'dateTime',defualtValue:''},
			update:{type:'dateTime',defualtValue:'',readonly:true},
			find:{type:'dateTime'}
		},
		{attr:'fileName',name:'文件名',visible: false,orderable: true},
		{attr:'parentId',name:'父组ID',visible: false,orderable: true},
		{attr:'userId',name:'创建人ID',visible: false,orderable: false}];

	var datatableConfig = {
		ajax: {
            url: ctx+"/backend/tg/r",
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
				_url:ctx+'/backend/tg/c'
			},
			_update : {
				_EL : '#CRUD_crudModal_update',
				_formEL : '#CRUD_update_form',
				_url:ctx+'/backend/tg/u'
			},
			_find : {
				_EL : '#CRUD_crudModal_find',
				_formEL : '#CRUD_find_form',
				_url:ctx+'/backend/tg/r'
			},
			_delete : {
				_EL : '#CRUD_crudModal_delete',
				_formEL : '#CRUD_delete_form',
				_url:ctx+'/backend/tg/d'
			}
		}
	});
	var viewer = crud.getViewer();
	var CRUD_table = viewer.getDataTable();
	
	var controller = crud.getController();
