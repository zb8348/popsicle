package com.pfw.popsicle.front.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfw.popsicle.front.dao.KschemaDao;
import com.pfw.popsicle.front.entity.Kschema;

/**
 * k线和相关统计
 * @author wulibing
 *
 */
@Service
@Transactional
public class KschemaService {
	@Autowired
	private KschemaDao kschemaDao;
	
//	@Transactional(readOnly=true)
//	public List<Kschema> countByTime(String ticker, Date openTime, Date closeTime, long begin, long end) {
//		if(groupId>0&&openTime!=null&&closeTime!=null){
//			return transactionDao.countByTime(groupId,openTime,closeTime,begin,end);
//		}
//		return null;
//	}

	public void batchSave(List<Kschema> prices) {
		if(prices!=null&&prices.size()>0){
			int maxL = 500;
			if(prices.size()<=maxL){
				kschemaDao.batchSave(prices);
			}else{
				int fromIndex=0;
				int toIndex=500;
				int size = prices.size();
				List<Kschema> nPrices = null;
				while(toIndex<=size){
					if(toIndex>=size){
						toIndex=size-1;
					}
					if(fromIndex>=toIndex){
						break;
					}
					nPrices = prices.subList(fromIndex, toIndex);
					kschemaDao.batchSave(nPrices);
					fromIndex = toIndex;
					toIndex+=500;
				}
			}
		}
	}

	public void batchSaveDuration(List<Kschema> prices, String duration) {
		if(prices!=null&&prices.size()>0){
			int maxL = 500;
			if(prices.size()<=maxL){
				kschemaDao.batchSaveDuration(prices,duration);
			}else{
				int fromIndex=0;
				int toIndex=500;
				int size = prices.size();
				List<Kschema> nPrices = null;
				while(toIndex<=size){
					if(toIndex>=size){
						toIndex=size-1;
					}
					if(fromIndex>=toIndex){
						break;
					}
					nPrices = prices.subList(fromIndex, toIndex);
					kschemaDao.batchSaveDuration(nPrices,duration);
					fromIndex = toIndex;
					toIndex+=500;
				}
			}
		}
	}

	@Transactional(readOnly=true)
	public List<Kschema> findDuration(String duration, Date openTime, Date closeTime,Long limit) {
		if(	"5M".equalsIgnoreCase(duration)||
				"15M".equalsIgnoreCase(duration)||
				"30M".equalsIgnoreCase(duration)||
				"1H".equalsIgnoreCase(duration)||
				"4H".equalsIgnoreCase(duration)||
				"1D".equalsIgnoreCase(duration)||
				"1W".equalsIgnoreCase(duration)||
				"1MONTH".equalsIgnoreCase(duration)){
		}else{
			duration = null;
		}
		return kschemaDao.findDuration(duration,openTime,closeTime,limit);
	}

	
}
