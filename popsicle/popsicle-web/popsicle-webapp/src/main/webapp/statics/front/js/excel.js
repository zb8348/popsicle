var excel ={
		anaylsis:function(workbook,timearea,dataTable){
		    var result = {};
		    var ws = workbook.Sheets['综合账号结算报告'];
		
		    result.broker = 'FXCM';
		    result.type = 'fxcm_ts_xlsx';
		
		    result.accountName = ws['B5'].v;
		    result.accountNumber = ws['B10'].v;
		    result.address = ws['A9'].v;
		    result.transactions = [];
		    // result.pl = 0;
		
		    if (ws['A14'].v === '单据号码') {
		        // var i =0;
		        var n = 15;
		        var date ;
		        var dStr ;
		        while (ws['A'+n].v !== '总和:') {
		            var transaction = {};
		            transaction.ticket = ws['A'+n].v;
		            transaction.currency = ws['B'+n].v;
		            transaction.volume = ws['C'+n].v;
		            transaction.lots = transaction.volume/100000;
		            
		            dStr = ws['D'+n].v.replace('上午','AM').replace('下午','PM');
		            transaction.openTime=moment(dStr,['MM/DD/YY HH:mm A','YYYY/MM/DD HH:mm']).add(timearea, 'hours');
		            dStr = ws['D'+(n+1)].v.replace('上午','AM').replace('下午','PM');
		            transaction.closeTime=moment(dStr,['MM/DD/YY HH:mm A','YYYY/MM/DD HH:mm']).add(timearea, 'hours');
		            transaction.openType = ws['M'+n].v;
		            transaction.closeType = ws['M'+(n+1)].v;
		
		            if(ws['E'+n].t === 's') {
		                transaction.direction = 'buy';
		                transaction.openPosition = ws['F'+n].v;
		                transaction.closePosition = ws['E'+(n+1)].v;
		                transaction.netPL = this.countGap(transaction.closePosition,transaction.openPosition);
		            } else {
		                transaction.direction = 'sell';
		                transaction.openPosition = ws['E'+n].v;
		                transaction.closePosition = ws['F'+(n+1)].v;
		                transaction.netPL = this.countGap(transaction.openPosition,transaction.closePosition);
		            }
		
		            transaction.grossPL = transaction.netPL*transaction.lots*10;
					transaction.status=1;
					//dataTable.row.add(transaction ).draw( false );
		
		            result.transactions.push(transaction);
		            n += 2;
		        }
		        
		        dataTable.rows.add(result.transactions).draw();
		    }
		    return result;
		},
		countGap:function(a,b) {
		    var s_a = (a*10000).toFixed(0).toString();
		    var s_b = (b*10000).toFixed(0).toString();
		    var n_a;
		    var n_b;
		    if (a<0) {
		        n_a = parseInt(s_a.substring(0,4));
		        n_b = parseInt(s_b.substring(0,4));
		    } else {
		        n_a = parseInt(s_a.substring(0,5));
		        n_b = parseInt(s_b.substring(0,5));
		    }
		
		    return n_a-n_b;
		}
}