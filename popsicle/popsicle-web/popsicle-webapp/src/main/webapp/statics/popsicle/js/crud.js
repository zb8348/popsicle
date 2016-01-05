/**
*基于jquery 和 dataTable 插件
*
*/
;(function($) {
	"use strict";
	
	var docloaded = false;
	$(document).ready(function () {docloaded = true} );	
	
	var pagetable_config={
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
		selected:function(dataTableContainer)	{
			$('#dataTable_checkall').on('click',function(){
	            $('.select_btn').trigger("click")
			});
			$(dataTableContainer).on('click','.select_btn',function(){
				if(this.checked){
					$(this).closest('tr').addClass('selected');
				}else{
					$(this).closest('tr').removeClass('selected');
				}
			});
		}
	};
	
	/**
	*实体的所有属性和属性所具有的全部其他属性
	*/
	var entity = function(attrs){
					var _attrs = attrs;
					this.getAttrs= function(){
						return _attrs;
					};
					this.getAttrsByType = function(type){
						var cAttrs=[];
						$.each(_attrs,function(index,obj){
							if(undefined != obj[type]){
								var _a={};
								var _ca = obj[type];
								for(var p in _ca){
									_a[p]=_ca[p];
								} 
								_a.attr=obj.attr;
								_a.name=obj.name;
								cAttrs.push(_a);
							}
//							else{
//								cAttrs.push(obj);
//							}
						});
						return cAttrs;
					};
					this.getValidates=function(){
						var v={rules:{},messages:{}};
						$.each(_attrs,function(index,obj){
							if(undefined != obj.validate){
								for(var p in obj.validate.rules){
									v.rules[p]=obj.validate.rules[p];
								} 
								for(var m in obj.validate.messages){
									v.messages[m]=obj.validate.messages[m];
								} 
							}
						})
						return v;
					};
				};
	/**
	*dataTableContainer:分页显示的容器——符合dataTable规范的table的id或class 
	*editTableContainer:新增或修改页面的容器——form的id或class
	*/
	var viewer = function (entity,settings){
					var cols = entity.getAttrs();
					var _getCols = function(){
						var _cols=[];
						var trContainer = $(dataTableEL+' thead tr');
						trContainer.empty();
						trContainer.append('<th><input id="dataTable_checkall" type="checkbox" name="checkall" value="全选"/></th>');
						
						_cols.push({"data": null,searchable: false,orderable: false,
				                "defaultContent": '<input class="select_btn" type="checkbox" name="checkedBtn" value="1"/>'});
						$.each(cols,function(index,i){
							trContainer.append('<th>'+i.name+'</th>');
							_cols.push({"data": i.attr ,orderable: i.orderable,visible:i.visible});
						});
						return _cols;
					 };
					 
					 var selectCols = function(){
						var containerSelector = settings._btnsEL;
						var html = '';
						$.each(cols,function(index,i){
							if(index==5){
								html+='<li role="separator" class="divider"></li>';
							}
							if(i.visible){
								html+='<li><label class="checkbox-inline"><input type="checkbox" class="select_cols_btn" id="'+i.attr+'" value="'+(index+1)+'" checked>'+i.name+'</label></li>';
							}else{
								html+='<li><label class="checkbox-inline"><input type="checkbox" class="select_cols_btn" id="'+i.attr+'" value="'+(index+1)+'">'+i.name+'</label></li>';
							}
						});
						$(containerSelector+" .dropdown-menu").empty().append(html);
					}();
					 
					var dataTableEL = settings._dtEL;
					
					var datatableSetting =  settings._dtSettings;
					datatableSetting.columns= _getCols();
					datatableSetting.dom=pagetable_config.dom;
					datatableSetting.language=pagetable_config.language;
					
					if(undefined == datatableSetting.ordering){
						datatableSetting.ordering = true;
					}
					if(undefined == datatableSetting.searching){
						datatableSetting.searching = false;
					}
					if(undefined == datatableSetting.processing){
						datatableSetting.processing = true;
					}
					if(undefined == datatableSetting.serverSide){
						datatableSetting.serverSide = true;
					}
					if(undefined == datatableSetting.scrollX){
						datatableSetting.scrollX = true;
					}
					if(undefined == datatableSetting.scrollCollapse){
						datatableSetting.scrollCollapse = false;
					}
					var dt = $(dataTableEL).DataTable(datatableSetting);
					pagetable_config.selected(dataTableEL);
					
					var bandEventListener = function(){
						var containerSelector = settings._btnsEL;
						  $(containerSelector).on( 'click','.select_cols_btn', function (e) {
					        var column = dt.column( $(this).val() );
					        try{
					        	column.header(dt.columns().header($(this).val()));
					        	column.visible(!column.visible());
					        }catch(e){
					        };
					        dt.draw(false);
					    } );
					}();
					
					this.getDataTable=function(){
						return dt;
					};
					
					this.makeCrudView=function(el,attrs,type,s){
						var html = "";
						
						if('checked' == s){//delete
							var rows = dt.rows('.selected');
							if(rows&&rows.data().length>0){
								
						 	}else{
						 		sysAlert("请选择操作的数据！");
						 		return false;
						 	}
						}
						
						var _selectObj ;
						if('selected' == s){
							var rows = dt.rows('.selected');
							if(rows&&rows.data().length==1){
								_selectObj = rows.data()[0];
						 	}else{
						 		sysAlert("请选择操作的数据！");
						 		return false;
						 	}
						}
						switch(type){
						case 'table':
							$.each(attrs,function(index,obj){
								if(undefined != obj.type){
									var _value= undefined != obj.defualtValue?obj.defualtValue:'';
									if(_selectObj != null){
										_value = _selectObj[obj.attr];
									}
									switch(obj.type){
									case 'hidden':
										html+='<input type="hidden" name="'+obj.attr+'" value="'+_value+'">';
										break;
									case 'select':
										html+='<div class="form-group">'
								            +'<label class="col-sm-3 control-label">'+obj.name+'</label><div class="col-sm-9">'
								            +'<select name="'+obj.attr+'" class="form-control">';
										if(undefined != obj.list){
											$.each(obj.list,function(index,v){
												var s = "";
												if(_value==v.value){
													s="selected";
												}
												html+='<option value="'+v.value+'" '+s+'>'+v.name+'</option>';
											});
										}
										html+='</select></div></div>';
										break;
									case 'radio':
										html+='<div class="form-group">'
								            +'<label class="col-sm-3 control-label">'+obj.name+'</label><div class="col-sm-9">'
										if(undefined != obj.list){
											$.each(obj.list,function(index,v){
												var s = "";
												if(_value==v.value){
													s="checked";
												}
												html+='<div class="radio-inline"><label><input type="radio" name="'+obj.attr+'" value="'+v.value+'" '+s+'>'+v.name+"</label></div>";
											});
										}
										html+='</div></div>';
										break;
									case 'checkbox':
										html+='<div class="form-group">'
								            +'<label class="col-sm-3 control-label">'+obj.name+'</label><div class="col-sm-9">'
										if(undefined != obj.list){
											var _mapV={};
											if(_value instanceof Array){
												for(var p in _value){
													_mapV[_value[p]]=1;
												}
											}else{
												_mapV[_value]=1
											}
											
											$.each(obj.list,function(index,v){
												var s="";
												if(_mapV[v.value]==1){
													s="checked";
												}
												html+='<div class="checkbox"><label><input type="checkbox" name="'+obj.attr+'" value="'+v.value+'" '+s+'>'+v.name+"</label></div>";
											});
										}
										html+='</div></div>';
										break;
									case 'textarea':
										html+='<div class="form-group">'
								            +'<label class="col-sm-3 control-label">'+obj.name+'</label><div class="col-sm-9">'
								            +'<textarea name="'+obj.attr+'" class="form-control">'+_value+'</textarea>'
								        +'</div></div>';
										break;
									case 'dateTime':
										var df = 'WdatePicker({dateFmt:\'yyyy-MM-dd HH:mm:ss\'})';
										html+='<div class="form-group">'
								            +'<label class="col-sm-3 control-label">'+obj.name+'</label><div class="col-sm-9">'
								            +'<input type="text" name="'+obj.attr+'" value="'+_value+'" class="Wdate form-control" onFocus="'+df+'"/>'
								        +'</div></div>';
										break;
									case 'text':
										html+='<div class="form-group">'
								            +'<label class="col-sm-3 control-label">'+obj.name+'</label><div class="col-sm-9">'
								            +'<input type="text" class="form-control" name="'+obj.attr+'" value="'+_value+'">'
								        +'</div></div>';
										break;
									case 'none':
									default:
										html+='<div class="form-group">'
								            +'<label class="col-sm-3 control-label">'+obj.name+'</label><div class="col-sm-9">'
								            +'<span>'+_value+'</span>'
								        +'</div></div>';
										break;
									}
								}
							});
							break;
						case 'list':
							var key ;
							var showAttrs=[];
							var _mapping={};
							$.each(attrs,function(index,obj){
								showAttrs.push(obj.attr);
								var _a={};
								for(var p in obj){
									_a[p]=obj[p];
								} 
								_mapping[obj.attr]=_a;
							});
							html+='<table  class="table table-striped table-bordered" ><tr>';
							if(showAttrs instanceof Array){
								for(var p in showAttrs){
									html+='<th>'+_mapping[showAttrs[p]].name+'</th>';
								}
							}
							html+='</tr>';
							$.each( rows.data(),function(index,obj){
								html+='<tr>';
								for(var p in showAttrs){
									switch(_mapping[showAttrs[p]].type){
									case 'hidden':
										break;
									case 'select':
										break;
									case 'radio':
										break;
									case 'checkbox':
										html+='<td><input type="checkbox" name="'+_mapping[showAttrs[p]].attr+'" value="'+obj[showAttrs[p]] + '" checked></td>';
										break;
									case 'textarea':
										break;
									case 'dateTime':
										break;
									case 'text':
										break;
									case 'none':
									default:
										html+='<td>'+obj[showAttrs[p]]+'</td>';
										break;
									}
								}
								html+='</tr>';
							});
							html+='</table>';
							break;
						default:
								break;
						}
						$(el).empty().append(html);
						return true;
					};
			}

	
	
	var controller = function(entity,viewer,settings){
		var containerSelector = settings._btnsEL;
		var date = new Date();
		var tag = date.getMilliseconds();
		
		this.addBtn = function(id,name,callback){
			if(undefined != containerSelector){
				$(containerSelector+" .text-right").prepend("<button type='button' class='btn btn-primary btn-sm' id='"+id+"'>"+name+"</button>   ");
	            $(containerSelector).on('click','#'+id,callback);
			}
        }
		
		this.optCtl = function(_opts,type,btnId,text,callback){
			var _EL = _opts._EL;
			var _formEL = _opts._formEL;
			if(undefined != _EL && undefined != _formEL){
				this.addBtn(btnId,text, function(){
					var attrs = entity.getAttrsByType(type);
					$(' .modal-title').empty().html(text);
					var s='';
					var t='';
					switch(type){
					case 'update':
						s='selected';
						t='table';
						break;
					case 'create':
						t='table';
						break;
					case 'find':
						t='table';
						break;
					case 'delete':
						s='checked';
						t='list';
						break;
					}
					if(viewer.makeCrudView(_formEL,attrs,t,s)){
						$(_EL).modal('show');
						$(_EL).off('click',_EL+'Btn');
						switch(type){
						case 'update':
						case 'create':
							$(_EL).on('click',_EL+'Btn',function(){
								$(_formEL).submit();
							});
							var _v = entity.getValidates();
							$(_formEL).validate({
								rules: _v.rules,
								messages: _v.messages,
								submitHandler: function(form) {
									$(_formEL).ajaxSubmit({
						    			type:"post",
						    			dataType:"json",
						    			url:_opts._url,
						    			success:function(data){
						    				if(data.success==true){
						    					$(_EL).modal('hide') ;
						    					viewer.getDataTable().draw();
						    				}else{
						    					sysAlert(data.errorMessage);
						    				}
						                }  
						            });
						    		return false;
								}
							});
							break;
						case 'find':
							$(_EL).one('click',_EL+'Btn',function(){
								$(_EL).modal('hide') ;
								var paras = $(_formEL).formSerialize();
								viewer.getDataTable().context[0].ajax.data=function(d){
									if(d.order){
					            		var index = d.order[0].column;
					            		var orderType = d.order[0].dir;
					            		var orderName = d.columns[index].data;
					            		d.orderType=orderType;
										d.orderName=orderName;
					            	}
									if(paras!=null){
										var ps = paras.split("\&");
										var toDelP = [];
										$.each(ps,function(index,obj){
											var nv = obj.split("\=");
											if(d[nv[0]]){
												var name = nv[0]+'\[\]';
												if(d[name]){
													d[name].push(nv[1]);
												}else{
													var ar = new Array();
													ar.push(d[nv[0]]);
													ar.push(nv[1]);
													d[name]=ar;
													toDelP.push(nv[0]);
												}
											}else{
												d[nv[0]]=nv[1];
											}
										});
										
										$.each(toDelP,function(index,obj){
											delete d[obj]; 
										});
									}
								};
								viewer.getDataTable().draw();
							});
							break;
						case 'delete':
							$(_EL).one('click',_EL+'Btn',function(){
								$(_formEL).attr('action',_opts._ur);
								$(_formEL).ajaxSubmit({
					    			type:"post",
					    			dataType:"json",
					    			url:_opts._url,
					    			success:function(data){
					    				if(data.success==true){
					    					$(_EL).modal('hide') ;
					    					viewer.getDataTable().draw();
					    				}else{
					    					sysAlert(data.errorMessage);
					    				}
					                }  
					            });
							});
							break;
						}
					}
				});
			}
		}
		var _edit = settings._edit;
		if(undefined != _edit){
			var _create = _edit._create;
			if(undefined != _create){
				this.optCtl(_create,'create','_create'+tag,'新增');
			}
			
			var _update = _edit._update;
			if(undefined != _update){
				this.optCtl(_update,'update','_update'+tag,'修改');
			}
			
			var _find = _edit._find;
			if(undefined != _find){
				this.optCtl(_find,'find','_find'+tag,'查找');
			}
			
			var _delete = _edit._delete;
			if(undefined != _delete){
				this.optCtl(_delete,'delete','_delete'+tag,'删除');
			}
		}
        
	}


	
	
	var CRUD = function (settings){
		var _e = new entity(settings._entityAttrs);
		this.getEntity=function(){
			return _e;
		}
		
		var _v = new viewer(_e,settings);
		this.getViewer=function(){
			return _v;
		}
		
		var _w = new controller(_e,_v,settings);
		this.getController=function(){
			return _w;
		}
	}
	
	$.fn.crud = function(settings) {
		
		var _settings = $.extend($.fn.crud.settings, settings);
		
//		return $.each(this,function(index,obj) {
//			var _c = new CRUD(settings);
//			if (!docloaded){
//				//$(this).hide();
//				var t = obj;
//				$(document).ready(
//					function (){
//						return _c;
//					}
//				);
//			} else {
//				return _c;
//			}
//		});
		var _c = new CRUD(_settings);
		return _c;
	}; 
	
	/**
	 * _*EL :表示DOM 的id或class
	 * _*HTML:表示DOM 的html字符串
	 */
	$.fn.crud.settings = {
			_btnsEL:null,
			_dtEL:null,//datatable
			_dtSettings:{
		    	searching: false,
		    	ordering:false,
		    	processing:true,
		    	serverSide: true,
		    	//"scrollY": 300,
		    	"scrollX": true,
		    	//scrollCollapse: false,
		        ajax: {
		            url: '',
		            dataSrc: "data",
		            type: "POST",
		            data:function(d){
		            	//if(d.order){
		            	//	var index = d.order[0].column;
		            	//	var orderType = d.order[0].dir;
		            	//	var name = d.columns[index].data;
		            	//}
		            	//d.name=$("#${portletid}search_name").val();
		            	//d.loginName=$("#${portletid}search_loginName").val();
		            	//d.status=$("#${portletid}search_trader_status").val();
		            }
		        },
		        footerCallback: function ( row, data, start, end, display ) {
		        }
			},//datatable 的相关设置
			_entityAttrs:[]//实体属性和名称等新信息[{attr:'id',name:'ID',visible: false,orderable: false},。。。。。]
	};
})(jQuery); 