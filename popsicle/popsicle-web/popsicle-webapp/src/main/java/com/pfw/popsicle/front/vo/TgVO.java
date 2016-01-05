package com.pfw.popsicle.front.vo;

import java.util.List;

import com.pfw.popsicle.front.entity.TransactGroup;
import com.pfw.popsicle.front.entity.Transaction;

public class TgVO extends TransactGroup{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4561083336177473108L;
	private List<Transaction> transactions;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
}
