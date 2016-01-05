var cols = [ {
	"attr" : "id",
	"delete" : {
		"type" : "checkbox"
	},
	"name" : "ID",
	"orderable" : false,
	"update" : {
		"defualtValue" : "",
		"type" : "hidden"
	},
	"visible" : false
}, {
	"attr" : "name",
	"create" : {
		"defualtValue" : "",
		"type" : "text"
	},
	"delete" : {
		"type" : "none"
	},
	"find" : {
		"defualtValue" : "",
		"type" : "text"
	},
	"name" : "姓名",
	"orderable" : true,
	"update" : {
		"defualtValue" : "",
		"type" : "text"
	},
	"validate" : {
		"messages" : {
			"name" : {
				"maxlength" : "分组名不能大于{0}个字符",
				"minlength" : "姓名不能小于{0}个字符",
				"required" : "没有填写姓名"
			}
		},
		"rules" : {
			"name" : {
				"maxlength" : 30,
				"minlength" : 2,
				"required" : true
			}
		}
	},
	"visible" : true
}, {
	"attr" : "sex",
	"create" : {
		"defualtValue" : "0",
		"list" : [ {
			"name" : "不详",
			"value" : "0"
		}, {
			"name" : "男",
			"value" : "1"
		}, {
			"name" : "女",
			"value" : "2"
		} ],
		"type" : "radio"
	},
	"delete" : {
		"type" : "none"
	},
	"find" : {
		"list" : [ {
			"name" : "不详",
			"value" : "0"
		}, {
			"name" : "男",
			"value" : "1"
		}, {
			"name" : "女",
			"value" : "2"
		} ],
		"type" : "radio"
	},
	"name" : "性别",
	"orderable" : true,
	"update" : {
		"defualtValue" : "0",
		"list" : [ {
			"name" : "不详",
			"value" : "0"
		}, {
			"name" : "男",
			"value" : "1"
		}, {
			"name" : "女",
			"value" : "2"
		} ],
		"type" : "radio"
	},
	"validate" : {
		"rules" : {
			"sex" : {
				"required" : true
			}
		}
	},
	"visible" : true
}, {
	"attr" : "createTime",
	"create" : {
		"defualtValue" : "",
		"type" : "dateTime"
	},
	"find" : {
		"defualtValue" : "",
		"type" : "dateTime"
	},
	"name" : "创建时间",
	"orderable" : true,
	"visible" : true
}, {
	"attr" : "remark",
	"create" : {
		"defualtValue" : "",
		"type" : "textarea"
	},
	"name" : "备注",
	"orderable" : false,
	"update" : {
		"defualtValue" : "",
		"type" : "textarea"
	},
	"visible" : false
} ];
var datatableConfig = {
	ajax : {
		url : ctx + "/demo/crud/r",
		dataSrc : "data",
		type : "POST",
		data : function(d) {
			if (d.order) {
				var index = d.order[0].column;
				var orderType = d.order[0].dir;
				var orderName = d.columns[index].data;
				d.orderType = orderType;
				d.orderName = orderName;
			}
		}
	},
	order : [ 1, 'asc' ],
	columnDefs : [ {
		targets : 3,
		render : function(a, b, c, d) {
			var html = "未知";
			switch (c.sex) {
			case 0:
				break;
			case 1:
				html = "男";
				break;
			case 2:
				html = "女";
				break;
			default:
				break;
			}
			return html;
		}
	} ]
};
var crud = $('#CRUD_data_table').crud({
	_entityAttrs : cols,
	_dtEL : '#CRUD_data_table',
	_dtSettings : datatableConfig,

	_btnsEL : '#CRUD_table_tools',
	_edit : {
		_create : {
			_EL : '#CRUD_crudModal_create',
			_formEL : '#CRUD_create_form',
			_url : ctx + '/demo/crud/c'
		},
		_update : {
			_EL : '#CRUD_crudModal_update',
			_formEL : '#CRUD_update_form',
			_url : ctx + '/demo/crud/u'
		},
		_find : {
			_EL : '#CRUD_crudModal_find',
			_formEL : '#CRUD_find_form',
			_url : ctx + '/demo/crud/r'
		},
		_delete : {
			_EL : '#CRUD_crudModal_delete',
			_formEL : '#CRUD_delete_form',
			_url : ctx + '/demo/crud/d'
		}
	}
});
var viewer = crud.getViewer();
var CRUD_table = viewer.getDataTable();
var controller = crud.getController();