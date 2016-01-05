package com.pfw.popsicle.front.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pfw.popsicle.front.dao.TransactionDao;
import com.pfw.popsicle.front.entity.Transaction;
import com.pfw.popsicle.vo.RequestResultVO;
/**
 * 
 * @author wulibing
 *
 */
@Service
@Transactional
public class TransactionService {
	@Autowired
	private TransactionDao transactionDao;

	public void batchSave(long groupId, List<Transaction> transactions) {
		if(transactions!=null&&transactions.size()>0){
			for (Transaction transaction : transactions) {
				transaction.setGroupId(groupId);
			}
			transactionDao.batchSave(transactions);
		}
	}

	@Transactional(readOnly=true)
	public List<Transaction> findByGroupId(long groupId,Integer status) {
		if(groupId>0){
			return transactionDao.findByGroupId(groupId,status);
		}
		return null;
	}

	public void deleteByGroupId(long groupId) {
		transactionDao.deleteByGroupId(groupId);
	}

	public void deleteByIds(List<Long> ids) {
		if(ids!=null&&ids.size()>0){
			transactionDao.deleteByIds(ids);
		}
	}

	public RequestResultVO<Transaction> update(Transaction t) {
		RequestResultVO<Transaction> r = new RequestResultVO<Transaction>();
		if(t!=null&&t.getId()>0){
			try {
				transactionDao.update(t);
				 r.success(t);
			} catch (Exception e) {
				r.error("ERROR", e.getMessage());
			}
		}
		return r;
	}

	public List<Transaction> findByIds(List<Long> ids) {
		if(ids!=null&&ids.size()>0){
			try {
				return transactionDao.findByIds(ids);
			} catch (Exception e) {
			}
		}
		return null;
	}

}
