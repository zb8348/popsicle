var ScirptFileArgs=(function(){
	var sc=document.getElementsByTagName('script');
	var paramsArr=sc[sc.length-1].src.split('?')[1].split('&');
	var args={},argsStr=[],param,t,name,value;
	for(var i=0,len=paramsArr.length;i<len;i++){
		param=paramsArr[i].split('=');
		name=param[0],value=param[1];
		if(typeof args[name]=="undefined"){ //参数尚不存在
			args[name]=value;
		}else if(typeof args[name]=="string"){ //参数已经存在则保存为数组
			args[name]=[args[name]]
			args[name].push(value);
		}else{ //已经是数组的
			args[name].push(value);
		}
	}
	/*在实际应用中下面的showArg和args.toString可以删掉，这里只是为了测试函数getArgs返回的内容*/
	var showArg=function(x){ //转换不同数据的显示方式
	if(typeof(x)=="string"&&!/\d+/.test(x)) return "'"+x+"'"; //字符串
	if(x instanceof Array) return "["+x+"]" //数组
	return x; //数字
	}
	//组装成json格式
	args.toString=function(){
	for(var i in args) argsStr.push(i+':'+showArg(args[i]));
		return '{'+argsStr.join(',')+'}';
	}
	return function(){return args;} //以json格式返回获取的所有参数
})();

User={};
User.init=function(ctx,portletid){
	User._ctx=ctx;
	User._portletid=portletid;
}
User.init(ScirptFileArgs()["ctx"],ScirptFileArgs()["portletid"]);


				var editFlag = false;
			    var table = $('#'+User._portletid+'user_table').DataTable({
			        	searching: false,
			        	ordering:false,
			        	processing:true,
			        	serverSide: true,
			            ajax: {
			                url: User._ctx+"/security/user/find",
			                dataSrc: "data",
			                type: "POST",
			                data:function(d){
			                	d.name=$('#'+User._portletid+'search_name').val();
			                	d.loginName=$('#'+User._portletid+'search_loginName').val();
			                }
			            },
			            columns: [
			            	{"data": "id"},
			                {"data": "name"},
			                {"data": "loginName"},
			                {"data": "imgUrl",class:'hidden-xs hidden-sm'},
			                {"data": "createTime",class:'hidden-xs hidden-sm'},
			                {"data": "creator",class:'hidden-xs hidden-sm'},
			                {"data": null}
			            ],
			            columnDefs: [
			            	{ 
			            		targets: 0,
			            	  	visible: false,
			            	  	searchable: false		
			            	},
			            	{
			                    targets: 3,
			                    render: function (a, b, c, d) {
			                    	var html = "";
			                    	if(c.imgUrl){
			                    		html="<img src='"+c.imgUrl+"'/>";
			                    	}
			                        return html;
			                    }
			                },
			                {
			                    targets: 6,
			                    render: function (a, b, c, d) {
			                         var btns='<button type="button" class="btn btn-primary btn-sm" onclick="edit(\'' + c.id + '\',\'' + c.name + '\')">修改</button>'
			                         +'<button type="button" class="btn btn-danger btn-sm" onclick="del(\'' + c.id + '\')">删除</button>';
			                        return btns;
			                    }
			                }
			            ],
			            "language": {
			            	"processing": "处理中...",
			            	"search": "搜索:",
			            	"loadingRecords": "载入中...",
			                "lengthMenu": "每页_MENU_",
			                "zeroRecords": "没有找到记录",
			                "info": "当前 _START_到 _END_条，共 _TOTAL_条记录",
			                "infoEmpty": "无记录",
			                "infoFiltered": "(从 _MAX_ 条记录过滤)",
			                "paginate": {
			                     "first": "首页",
				                 "previous": "上一页 ",
				                 "next": "下一页 ",
				                 "last": "尾页 "
			                }
			            },
			            "dom": "<'row'<'col-xs-6'l><'#"+User._portletid+"table_tools.col-xs-6'>r>t<'row'<'col-xs-6'i><'col-xs-6'p>>",
			            initComplete: function () {
			                $("#${portletid}table_tools").append($("#"+User._portletid+"tools").html());
			                $("#${portletid}datainit").on("click", initData);
			            }
			        });
			 
			        $("#save").click(add);
			 
			        $('#'+User._portletid+'table_tools').on('click','.'+User._portletid+'updateSelectedData',function () {
			        	alert("updateSelectedData");
			        	var row = table.row('.selected');
			        	if(row){
			        		alert(row.data().id);
			        	}
				    } );
				    $('#'+User._portletid+'table_tools').on('click','.'+User._portletid+'deleteSelectedData', function () {
				    alert("deleteSelectedData");
				    	table.row('.selected').remove().draw( false );
				    } );
				                      
			        $("#"+User._portletid+"initData").click(initSingleData);
			        
				 	$('#'+User._portletid+'user_table tbody').on( 'click', 'tr', function () {
				        if ( $(this).hasClass('selected') ) {
				            $(this).removeClass('selected');
				        }
				        else {
				            table.$('tr.selected').removeClass('selected');
				            $(this).addClass('selected');
				        }
				    } );
				    var form1 = $("#"+User._portletid+"user_search_form").validate({
			    		rules: {
			    			name:{maxlength:20},
			    			loginName:{maxlength:20}
			    		},
			    		submitHandler: function(form) {
				    		$(form).submit(function(){
				    			table.draw();
				    			return false;
				    		});
						}
			    	});
				    /**
				     * 初始化基础数据
				     */
				    function initData() {
				        var flag = confirm("本功能将添加数据到数据库，你确定要添加么？");
				        if (flag) {
				            $.get("/objects.txt", function (data) {
				                var jsondata = JSON.parse(data);
				                $(jsondata.data).each(function (index, obj) {
				                    ajax(obj);
				                });
				            });
				        }
				    }
				 
				    /**
				     * 初始化基础数据
				     */
				    function initSingleData() {
				        $("#name").val("http://dt.thxopen.com");
				        $("#position").val("ShiMen");
				        $("#salary").val("1");
				        $("#start_date").val("2015/04/01");
				        $("#office").val("Home");
				        $("#extn").val("001");
				    }
				 
				    /**
				     * 清除
				     */
				    function clear() {
				        $("#name").val("").attr("disabled",false);
				        $("#position").val("");
				        $("#salary").val("");
				        $("#start_date").val("");
				        $("#office").val("");
				        $("#extn").val("");
				        editFlag = false;
				    }
				 
				    /**
				     * 添加数据
				     **/
				    function add() {
				        var addJson = {
				            "name": $("#name").val(),
				            "position": $("#position").val(),
				            "salary": $("#salary").val(),
				            "start_date": $("#start_date").val(),
				            "office": $("#office").val(),
				            "extn": $("#extn").val()
				        };
				        ajax(addJson);
				    }
				 
				    /**
				     *编辑方法
				     **/
				    function edit(id,name) {
				        console.log(name);
				        editFlag = true;
				        alert(id);
				        $("#myModalLabel").text("修改");
				        $("#name").val(name).attr("disabled",true);
				        $("#myModal").modal("show");
				    }
				 
				    function ajax(obj) {
				        var url ="/add.jsp" ;
				        if(editFlag){
				            url = "/edit.jsp";
				        }
				        $.ajax({
				            url:url ,
				            data: {
				                "name": obj.name,
				                "position": obj.position,
				                "salary": obj.salary,
				                "start_date": obj.start_date,
				                "office": obj.office,
				                "extn": obj.extn
				            }, success: function (data) {
				                table.ajax.reload();
				                $("#myModal").modal("hide");
				                $("#myModalLabel").text("新增");
				                clear();
				                console.log("结果" + data);
				            }
				        });
				    }
				 
				 
				    /**
				     * 删除数据
				     * @param name
				     */
				    function del(name) {
				        $.ajax({
				            url: "/del.jsp",
				            data: {
				                "name": name
				            }, success: function (data) {
				                table.ajax.reload();
				                console.log("删除成功" + data);
				            }
				        });
					}