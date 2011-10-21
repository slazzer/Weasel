package org.dresign.spring;

import org.dresign.bus.AsyncBusHandler;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class AsynchronousTransactionManager extends JpaTransactionManager {
	
	public AsyncBusHandler getAsyncBusHandler() {
		return asyncBusHandler;
	}

	public void setAsyncBusHandler(AsyncBusHandler asyncBusHandler) {
		this.asyncBusHandler = asyncBusHandler;
	}

	private AsyncBusHandler asyncBusHandler;
	
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		asyncBusHandler.startNewTransaction();
		super.doBegin(transaction, definition);
	}

	@Override
	protected void doCommit(DefaultTransactionStatus status) {
		super.doCommit(status);
		asyncBusHandler.performTransaction();
	}

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		asyncBusHandler.endTransaction();
		super.doCleanupAfterCompletion(transaction);
	}
	

}
