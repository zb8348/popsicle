package com.pfw.popsicle.front.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pfw.popsicle.common.DateUtil;
//import com.pfw.popsicle.common.JsonUtils;
import com.pfw.popsicle.front.entity.Kschema;
//import com.pfw.popsicle.front.entity.Transaction;
import com.pfw.popsicle.front.service.ImportPriceData;
import com.pfw.popsicle.front.service.KschemaService;

/**
 * 
 * @author wulibing
 *
 */
@Controller
public class KschemaController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private KschemaService kschemaService;
	
	@RequestMapping(value = "/k/init")
	public @ResponseBody String init() {
		BufferedReader reader = null;
		ImportPriceData importPrice = new ImportPriceData(kschemaService);
        try {
            reader = new BufferedReader(new FileReader(new File("D:\\mysrc\\云单\\EURUSD.txt")));
        	//reader = new BufferedReader(new FileReader(new File("/home/EURUSD.txt")));
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	if(line>0&&StringUtils.isNotBlank(tempString)){
            		importPrice.store(tempString);
            		log.info(line+" : "+ tempString);
            	}
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        
		kschemaService.batchSave(importPrice._1MPrices);
		kschemaService.batchSaveDuration(importPrice._5MPrices,"5M");
		kschemaService.batchSaveDuration(importPrice._15MPrices,"15M");
		kschemaService.batchSaveDuration(importPrice._30MPrices,"30M");
		kschemaService.batchSaveDuration(importPrice._1HPrices,"1H");
		kschemaService.batchSaveDuration(importPrice._4HPrices,"4H");
		kschemaService.batchSaveDuration(importPrice._1DPrices,"1D");
		kschemaService.batchSaveDuration(importPrice._1WPrices,"1W");
		kschemaService.batchSaveDuration(importPrice._1MonthPrices,"1Month");
        
        return "";         
	}
	
	
	/**
	 * <TICKER>,<DTYYYYMMDD>,<TIME>,<OPEN>,<HIGH>,<LOW>,<CLOSE>,<VOL>
	 * EURUSD,20010102,230100,0.9507,0.9507,0.9507,0.9507,4
	 * EURUSD,20010102,230200,0.9506,0.9506,0.9505,0.9505,4
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/backend/k/importTxt",method=RequestMethod.POST)
	public String importTxt(HttpServletRequest request,@RequestParam MultipartFile file) {
		InputStreamReader read = null;
		BufferedReader reader = null;
		ImportPriceData importPrice = new ImportPriceData(kschemaService);
        try {
        	read = new InputStreamReader(file.getInputStream());
            reader = new BufferedReader(read);
            String tempString = null;
            int line = 0;
            while ((tempString = reader.readLine()) != null) {
            	if(line>0&&StringUtils.isNotBlank(tempString)){
            		importPrice.store(tempString);
            	}
                line++;
            }
            read.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (read != null) {
                try {
                	read.close();
                } catch (IOException e1) {
                }
            }
        }
        
        kschemaService.batchSave(importPrice._1MPrices);
		kschemaService.batchSaveDuration(importPrice._5MPrices,"5M");
		kschemaService.batchSaveDuration(importPrice._15MPrices,"15M");
		kschemaService.batchSaveDuration(importPrice._30MPrices,"30M");
		kschemaService.batchSaveDuration(importPrice._1HPrices,"1H");
		kschemaService.batchSaveDuration(importPrice._4HPrices,"4H");
		kschemaService.batchSaveDuration(importPrice._1DPrices,"1D");
		kschemaService.batchSaveDuration(importPrice._1WPrices,"1W");
		kschemaService.batchSaveDuration(importPrice._1MonthPrices,"1Month");
        return "";         
	}
	
	
//	@RequestMapping(value = "/front/k/kData",method = RequestMethod.POST)
//	public @ResponseBody List<Kschema> kData(@RequestParam(value="ts",required=true) String tsJson,Model model) {
//		Transaction ts = (Transaction)JsonUtils.toObject(tsJson, Transaction.class);
//	    String duration = null;
//
//	    Date openTime = ts.getOpenTime();
//	    Date closeTime = ts.getCloseTime();
//
//	    long seconds = closeTime.getTime() - openTime.getTime();
//	    if (seconds<60000) {//1分钟
////	        duration = "1M";
//	    } else if(seconds<300000) {
//	        duration = "5M";
//	    } else if(seconds<900000) {
//	        duration = "15M";
//	    } else if(seconds<1800000) {
//	        duration = "30M";
//	    } else if(seconds<3600000) {
//	        duration = "1H";
//	    } else if(seconds<14400000) {
//	        duration = "4H";
//	    } else{
//	        duration = "1D";
//	    } //外汇通常不会更久了，除非是股票做成了股东....
//
//	    //显示时间段扩充：
//	    openTime.setTime(openTime.getTime()-seconds*5);
//	    closeTime.setTime(closeTime.getTime()+seconds*5);
//	    return kschemaService.findDuration(duration,openTime,closeTime,null);
//	}
	
	@InitBinder("openTime")   
    public void initBinderOpenTime(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    } 
	@InitBinder("closeTime")   
    public void initBinderCloseTime(WebDataBinder binder) {   
		initBinderOpenTime(binder);
    } 
	
	@RequestMapping(value = "/k/load/{ticker}/{duration}",method = RequestMethod.POST)
	public @ResponseBody List<Kschema> loadKData(@PathVariable(value="duration")String duration,@PathVariable(value="ticker")String ticker,
		   @RequestParam("openTime") Date openTime,@RequestParam("closeTime") Date closeTime, Model model) {
	   if(closeTime==null){
		   closeTime = DateUtil.getChineseTime();
	   }
	   if(openTime==null){
		   Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
		   cal.setTime(closeTime);
		    if(	"5M".equalsIgnoreCase(duration)){
			    cal.add(Calendar.DAY_OF_YEAR, -2); //2天前
			}else if("15M".equalsIgnoreCase(duration)){
				cal.add(Calendar.WEEK_OF_YEAR, -1); //1星期前
			}else if("30M".equalsIgnoreCase(duration)){
				cal.add(Calendar.WEEK_OF_YEAR, -3); //
			}else if("1H".equalsIgnoreCase(duration)){
				cal.add(Calendar.MONTH, -1); 		//
			}else if("4H".equalsIgnoreCase(duration)){
				cal.add(Calendar.MONTH,-6); 		//
			}else if("1D".equalsIgnoreCase(duration)){
				cal.add(Calendar.YEAR, -2); 		//
			}else if("1W".equalsIgnoreCase(duration)){
				cal.add(Calendar.YEAR, -5); 		//
			}else if("1MONTH".equalsIgnoreCase(duration)){
				cal.add(Calendar.YEAR, -10); 		//
			}else{
				 cal.add(Calendar.DAY_OF_YEAR, -1); //
			}
			openTime = cal.getTime();
	   }
	    return kschemaService.findDuration(duration,openTime,closeTime,null);
	}
	
}
