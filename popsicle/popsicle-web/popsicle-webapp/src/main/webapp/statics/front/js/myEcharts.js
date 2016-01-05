var myEcharts = {
	k_option:{
            animation : false,
            tooltip : {
                trigger: 'axis',
                formatter: function (params) {
                    var res = params[0].seriesName + ' ' + params[0].name;
                    res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : ' + params[0].value[3];
                    res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : ' + params[0].value[2];
                    return res;
                }
            },
            // legend: {
            //     data:['上证指数']
            // },
            toolbox: {
            	x:'left',
                show : true,
                feature : {
                    mark : {show: true},
                    dataZoom : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            dataZoom : {
                show : true,
                realtime: true,
                start : 30,
                end : 70
            },
            grid :{
            	x:50,
            	y:40,
            	x2:20,
            	y2:60
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : true,
                    axisTick: {onGap:false},
                    splitLine: {show:false},
                    data:[]
                    //data:kxAxis
                    /*
                    data : function (){
		                var list = [];
		                for (var i = 1; i <= 30; i++) {
		                    list.push('2015-03-' + i);
		                }
		                return list;
		            }()*/
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    scale:true,
                    boundaryGap: [0.01, 0.01]
                }
            ],
            series : [
                {
                    name:'上证指数',
                    type:'k',
                    barMaxWidth: 15,
                    itemStyle: {
                        normal: {
                             color: '#fff',          // 阳线填充颜色
		                	color0: '#00aa11',      // 阴线填充颜色
                            lineStyle: {
                                width: 1,
                                color: '#ff3200',   // 阳线边框颜色
		                    	color0: '#00aa11'   // 阴线边框颜色
                            }
                        },
                        emphasis: {
                            color: 'white',         // 阳线填充颜色
                            color0: 'black'         // 阴线填充颜色
                        }
                    },
                    data:[],
                    //data:seriesData,
                    /*
                    data:function (){
		                var list = [];
		                for (var i = 1; i <= 30; i++) {
		                    list.push([Math.round(Math.random()* 30) + 30,Math.round(Math.random()* 30) + 30,Math.round(Math.random()* 30) + 30,Math.round(Math.random()* 30) + 30]);
		                }
		                return list;
		            }(),
		            */
                    markPoint : {
                        symbol: 'star',
                        //symbolSize:20,
                        itemStyle:{
                            normal:{label:{position:'top'}}
                        },
                        data : [
                            {name : '最高', value : 2444.8, xAxis: '2013/2/18', yAxis: 2466}
                        ]
                    }
                }
            ]
        },
		
        
        
        
        trend_option : {
    	        title: {
    	            text: '盈亏曲线图',
    	            subtext: '点数累计'
    	        },
    	        animation: false,
    	        tooltip: {
    	            trigger: 'axis',
    	            showDelay: 0,
    	        },
    	        // legend: {
    	        //     data: ['点数']
    	        // },
    	        toolbox: {
    	            show: true,
    	            feature: {
    	                mark: {
    	                    show: false
    	                },
    	                dataView: {
    	                    show: false,
    	                    readOnly: false
    	                },
    	                magicType: {
    	                    show: false,
    	                    type: ['line', 'bar', 'stack', 'tiled']
    	                },
    	                restore: {
    	                    show: false
    	                },
    	                saveAsImage: {
    	                    show: true
    	                }
    	            }
    	        },
    	        calculable: false,
    	        xAxis: [{
    	            type: 'category',
    	            boundaryGap: false,
    	            data: []
    	        }],
    	        yAxis: [{
    	            type: 'value'
    	        }],
    	        series: [{
    	            name: '成交',
    	            type: 'line',
    	            smooth: true,
    	            symbol : 'none',
    	            itemStyle: {
    	                normal: {
    	                    areaStyle: {
    	                        type: 'default'
    	                    }
    	                }
    	            },
    	            data: []
    	        }]
    	    },
    	    
    	    
    	    
    	    grain_option : {
                    title: {
                        text: '交易时长及盈亏散点图',
                    },
                    tooltip: {
                        trigger: 'axis',
                        showDelay: 0,
                        axisPointer: {
                            show: true,
                            type: 'none',
                            lineStyle: {
                                type: 'dashed',
                                width: 1
                            }
                        },
                        formatter : function(params) {
                            return params.seriesName + ' :<br/>' + params.value[0] + '分钟 ' + params.value[1] + '点 ' + params.value[2]+ '手';
                        }
                    },
                    legend: {
                        data: []
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: {
                                show: false
                            },
                            dataZoom: {
                                show: true
                            },
                            dataView: {
                                show: false,
                                readOnly: false
                            },
                            restore: {
                                show: false
                            },
                            saveAsImage: {
                                show: true
                            }
                        }
                    },
                    xAxis: [{
                        type: 'value',
                        scale: true,
                        axisLabel: {
                            formatter: '{value} 分钟'
                        }
                    }],
                    yAxis: [{
                        type: 'value',
                        scale: true,
                        axisLabel: {
                            formatter: '{value} 点'
                        }
                    }],
                    series: []
                }
    	
}
