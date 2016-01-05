package com.pfw.popsicle.front.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.pfw.popsicle.front.entity.Kschema;

/**
 * 
 * @author wulibing
 *
 */
public class ImportPriceData {
	/**
	 * Created by Terry on 2015/6/29.
	 * 从原始的forextester csv格式1分钟交易数据导入mongo,兼容格式如下,且必须按时间由远及近排好序
	 *  <TICKER>,<DATE>,<TIME>,<OPEN>,<HIGH>,<LOW>,<CLOSE>,<VOL>
	 *  EURUSD,20010102,230100,0.9507,0.9507,0.9507,0.9507,4
	 * 导入需修改的参数有 1.源数据文件地址 2.collection的命名 3.数据提供商 4.时区（根据源数据设置，否则设UTC）
	 */
	private static final  DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private KschemaService kschemaService;
	public ImportPriceData(){
	}
	public ImportPriceData(KschemaService kschemaService){
		this.kschemaService = kschemaService;
	}
	public List<Kschema> _1MPrices = new ArrayList<Kschema>(); 
	public List<Kschema> _5MPrices = new ArrayList<Kschema>(); 
	public List<Kschema> _15MPrices = new ArrayList<Kschema>(); 
	public List<Kschema> _30MPrices = new ArrayList<Kschema>(); 
	public List<Kschema> _1HPrices = new ArrayList<Kschema>(); 
	public List<Kschema> _4HPrices = new ArrayList<Kschema>();  
	public List<Kschema> _1DPrices = new ArrayList<Kschema>(); 
	public List<Kschema> _1WPrices = new ArrayList<Kschema>(); 
	public List<Kschema> _1MonthPrices = new ArrayList<Kschema>(); 
	
	private Kschema getPrice(String line) {
	    if (StringUtils.isBlank(line)) {
	        return null;
	    }
	    String[] strs = line.split(",");
	    if (strs!=null&& strs.length == 8 ) {
	    	Kschema k = new Kschema();
	 		k.setTicker(strs[0]);
	 		k.setDayStr(strs[1]);
	 		k.setTimeStr(strs[2]);
	 		k.setOpen(new BigDecimal(strs[3]));
	 		k.setHigh(new BigDecimal(strs[4]));
	 		k.setLow(new BigDecimal(strs[5]));
	 		k.setClose(new BigDecimal(strs[6]));
	 		k.setVolume(Long.valueOf(strs[7]));
	 		try {
				k.setTime(ImportPriceData.format.parse(k.getDayStr()+k.getTimeStr()));
			} catch (ParseException e) {
			}
	        return k;
	    }
	    return null;
	}
	

	//根据时间区间，生成往前最近的一个虚拟价格
	private Kschema makePrice(Kschema price,String duration) {
		Kschema fillVirtualPrice = Kschema.copy(price);
		Date time = null;
	    if (StringUtils.equals(duration, "5M")) {
	    	time = new Date(price.getTime().getTime()-price.getTime().getMinutes()%5*60000);
	    } else if (StringUtils.equals(duration, "15M")) {
	    	time = new Date(price.getTime().getTime()-price.getTime().getMinutes()%15*60000);
	    } else if (StringUtils.equals(duration, "30M")) {
	    	time = new Date(price.getTime().getTime()-price.getTime().getMinutes()%30*60000);
	    } else if (StringUtils.equals(duration, "1H")) {
	    	time = new Date(price.getTime().getTime()-price.getTime().getMinutes()*60000);
	    } else if (StringUtils.equals(duration, "4H")) {
	    	time = new Date(price.getTime().getTime()- price.getTime().getHours()%4*3600000-price.getTime().getMinutes()*60000);
	    } else if(StringUtils.equals(duration, "1D") ) {
	    	time = new Date(price.getTime().getTime());
	    	time.setHours(0);
	    	time.setMinutes(0);
	    } else if(StringUtils.equals(duration, "1W")) {
	    	time = new Date(price.getTime().getTime() - (price.getTime().getDay()-1)*86400000);
	    	time.setHours(0);
	    	time.setMinutes(0);
	    } else if(StringUtils.equals(duration, "1Month")) {
	    	time = new Date(price.getTime().getTime());
	    	time.setDate(1);
	    	time.setHours(0);
	    	time.setMinutes(0);
	    } 
	    fillVirtualPrice.setTime(time);
	    return fillVirtualPrice;
	}

	private void pushTo(Kschema price,List<Kschema> targetList,String duration,long ms) {
		Kschema lastPrice = null;
	    if (targetList.size()==0) {
	    	targetList.add(makePrice(price,duration));
	    } else {
	        lastPrice = targetList.get(targetList.size()-1);
	        	if((ms>0&&(price.getTime().getTime()-lastPrice.getTime().getTime())<ms) ||
	        			(ms<=0&&price.getTime().getMonth()==lastPrice.getTime().getMonth())) {
		        	BigDecimal high = lastPrice.getHigh().compareTo(price.getHigh())>0?lastPrice.getHigh():price.getHigh();
		        	lastPrice.setHigh(high);
		        	BigDecimal low = lastPrice.getLow().compareTo(price.getLow())>0?lastPrice.getLow():price.getLow();
		        	lastPrice.setLow(low);
		        	lastPrice.setClose(price.getClose());
		        	targetList.set(targetList.size()-1,lastPrice) ;
		        } else {
		        	targetList.add(makePrice(price,duration));
		        	pushTo(price,targetList,duration,ms);
		        }
	    }
	}
	
	private void fullfillPrice(Kschema price) {
	    if (price!=null) {
	        _1MPrices.add(price);
	        pushTo(price,_5MPrices,"5M",300000);
	    	pushTo(price,_15MPrices,"15M",900000); 
	    	pushTo(price,_30MPrices,"30M",1800000); 
	    	pushTo(price,_1HPrices,"1H",3600000); 
	    	pushTo(price,_4HPrices,"4H",14400000);
	    	pushTo(price,_1DPrices,"1D",86400000);
	    	pushTo(price,_1WPrices,"1W",604800000);
	    	pushTo(price,_1MonthPrices,"1Month",0);
	    	
	    	if(kschemaService!=null){
	    		if(_1MPrices.size()>=500){
	    			kschemaService.batchSave(_1MPrices);
	    			_1MPrices.clear(); 
	    		}
	    		if(_5MPrices.size()>=500){
	    			kschemaService.batchSaveDuration(_5MPrices,"5M");
	    			_5MPrices.clear(); 
	    		}
	    		if(_15MPrices.size()>=500){
	    			kschemaService.batchSaveDuration(_15MPrices,"15M");
	    			_15MPrices.clear(); 
	    		}
	    		if(_30MPrices.size()>=500){
	    			kschemaService.batchSaveDuration(_30MPrices,"30M");
	    			_30MPrices.clear(); 
	    		}
	    		if(_1HPrices.size()>=500){
	    			kschemaService.batchSaveDuration(_1HPrices,"1H");
	    			_1HPrices.clear(); 
	    		}
	    		if(_4HPrices.size()>=500){
	    			kschemaService.batchSaveDuration(_4HPrices,"4H");
	    			_4HPrices.clear(); 
	    		}
	    		if(_1DPrices.size()>=500){
	    			kschemaService.batchSaveDuration(_1DPrices,"1D");
	    			_1DPrices.clear(); 
	    		}
	    		if(_1WPrices.size()>=500){
	    			kschemaService.batchSaveDuration(_1WPrices,"1W");
	    			_1WPrices.clear(); 
	    		}
	    		if(_1MonthPrices.size()>=500){
	    			kschemaService.batchSaveDuration(_1MonthPrices,"1Month");
	    			_1MonthPrices.clear(); 
	    		}
    		}
	    }
	};
	public void store(String line){
		Kschema price = getPrice(line);
		fullfillPrice(price);
	}
	
}
