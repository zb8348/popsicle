package com.pfw.popsicle.front.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfw.popsicle.common.CollectionUtils;
import com.pfw.popsicle.front.dao.TransactGroupDao;
import com.pfw.popsicle.front.entity.TransactGroup;
import com.pfw.popsicle.front.entity.Transaction;
import com.pfw.popsicle.vo.RequestResultVO;
/**
 * 
 * @author wulibing
 *
 */
@Service
@Transactional
public class TransactGroupService {

	@Autowired
	private TransactGroupDao transactGroupDao;
	@Autowired
	private TransactionService transactionService;

	public void save(TransactGroup tg) {
		if(tg!=null){
			transactGroupDao.save(tg);
		}
	}
	@Transactional(readOnly=true)
	public TransactGroup getById(long id) {
		if(id>0){
			return transactGroupDao.getById(id);
		}
		return null;
	}

	@Transactional(readOnly=true)
	public List<TransactGroup> findByCreator(String loginName) {
		if(StringUtils.isNotBlank(loginName)){
			return transactGroupDao.findByCreator(loginName);
		}
		return null;
	}

	public TransactGroup analysis(TransactGroup tgEntity, List<Transaction> transactions) {
		if(tgEntity!=null&&transactions!=null&&transactions.size()>0){
			tgEntity.setAnalysisAmount(Long.valueOf(transactions.size()));
			CollectionUtils.sortTheList(transactions, "closeTime", CollectionUtils.SORT_ORDER_ASC);
			  long accumulate = 0;
			  for (Transaction transaction : transactions) {
				  accumulate += transaction.getGrossPL();
			  }
			  tgEntity.setAnalysisPl(accumulate);  
		}
		return tgEntity;
	}
	public void remove(long id) {
		transactGroupDao.delete(id);
		transactionService.deleteByGroupId(id);
	}
	public RequestResultVO<TransactGroup> update(TransactGroup tg) {
		RequestResultVO<TransactGroup> r = new RequestResultVO<TransactGroup>();
		if(tg!=null&&tg.getId()>0){
			try {
				transactGroupDao.update(tg);
				 r.success(tg);
			} catch (Exception e) {
				r.error("ERROR", e.getMessage());
			}
		}
		return r;
	}

	public RequestResultVO<TransactGroup> deleteTs(List<Long> ids, long groupId) {
		RequestResultVO<TransactGroup> r = new RequestResultVO<TransactGroup>();
		if(ids!=null&&ids.size()>0&&groupId>0){
			try {
				transactionService.deleteByIds(ids);
				r = updateTGAnalysis(groupId);
			} catch (Exception e) {
				r.error("ERROR", e.getMessage());
			}
		}else{
			r.error("NOINPUT", "没有输入数据");
		}
		return r;
	}
	
	private RequestResultVO<TransactGroup> updateTGAnalysis(long groupId){
		RequestResultVO<TransactGroup> r = new RequestResultVO<TransactGroup>();
		if(groupId>0){
			try {
				TransactGroup tgEntity = getById(groupId);
				List<Transaction> transactions = transactionService.findByGroupId(groupId, Transaction.status_valid);
				tgEntity = analysis(tgEntity, transactions);
				r = update(tgEntity);
			} catch (Exception e) {
				r.error("ERROR", e.getMessage());
			}
		}else{
			r.error("NOINPUT", "没有输入数据");
		}
		return r;
	}
	public RequestResultVO<TransactGroup> updateT(Transaction t) {
		RequestResultVO<TransactGroup> r = new RequestResultVO<TransactGroup>();
		if(t!=null&&t.getId()>0){
			try {
				transactionService.update(t);
				if(t.getGroupId()!=null&&t.getGroupId()>0){
					r = updateTGAnalysis(t.getGroupId());
				}
			} catch (Exception e) {
				r.error("ERROR", e.getMessage());
			}
		}else{
			r.error("NOINPUT", "没有输入数据");
		}
		return r;
	}
	
	/**
	 * 查看最新的10个晒单
	 * @param limit
	 * @return
	 */
	public List<TransactGroup> findByCreateTimeDesc(int limit) {
		return transactGroupDao.findByCreateTimeDesc(limit);
	}
}
