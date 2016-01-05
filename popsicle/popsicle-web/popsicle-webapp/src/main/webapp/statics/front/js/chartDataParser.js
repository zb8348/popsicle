/**
 * Created by Terry on 2015/8/14.
 */
var chartDataParser = {
        parseTrend: function(tsData) {
            var option = {
                xAxis: [{
                    data: []
                }],
                series: [{
                    data: [],
                    id: []
                }]
            };

            var accumulate = 0;
            for (var i = 0; i < tsData.length; i++) {
                //fullfill trend data
                accumulate += tsData[i].grossPL;
                option.xAxis[0].data.push(i + 1);
                option.series[0].data.push(accumulate);
                option.series[0].id.push(i);
            }
            return option;
        },

        parseScatter: function(tsData) {
        	try{
		            var option = {
		                legend: {
		                    data: []
		                },
		                series: []
		            };
		
		            var currency = {};
		            var ts = {};
		            
		            var regEx = new RegExp("\\-","gi");
		            
		            for (var i = 0; i < tsData.length; i++) {
		                ts = tsData[i];
		
		                if (!currency[ts.currency]) {
		                    currency[ts.currency] = {};
		                    currency[ts.currency].scatterData = [];
		                    currency[ts.currency].id = [];
		                } 
		                //parse 需要 2005/3/4   这种格式
		                currency[ts.currency].scatterData.push([(new Date(ts.closeTime.replace(regEx,'/')).getTime() - new Date(ts.openTime.replace(regEx,'/')).getTime()) / 60000, ts.netPL, ts.lots]);
		                //currency[ts.currency].scatterData.push([(ts.closeTime - ts.openTime) / 60000, ts.netPL, ts.lots]);
		                currency[ts.currency].id.push(i);
		            }
		
		            //fullfill scatter data
		            for (var i in currency) {
		                if (currency.hasOwnProperty(i)) {
		                    option.legend.data.push(i);
		                    option.series.push({
		                        name: i,
		                        data: currency[i].scatterData,
		                        id: currency[i].id
		                    });
		                }
		            }
            
            
        	}catch (e){
	        	alert(e.message);
	        	alert(e.description)
	        	alert(e.number)
	        	alert(e.name)
        	} 
            return option;
        },

        parseK: function(kData) {
            var option = {
                xAxis: [
                    {
                        type: 'category',
                        data: []
                    }
                ],
                series: [
                    {
                        data: []
                    }
                ]
            };

            var k = {};
            var date;
            for (var i = 0; i < kData.length; i++) {
                k = kData[i];
                // date = new Date(Date.parse(k.time));
                option.xAxis[0].data.push(moment(k.time).format('YYYY/MM/DD HH:mm'));
                option.series[0].data.push([k.open,k.close,k.low,k.high]);

            }
            return option;
        }
    };
