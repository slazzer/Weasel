package org.dresign.spring;

import org.dresign.bus.AsyncBusTransactionalHandler;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class AsynchronousTransactionManager extends JpaTransactionManager {
	
	public AsyncBusTransactionalHandler getAsyncBusHandler() {
		return asyncBusHandler;
	}

	public void setAsyncBusHandler(AsyncBusTransactionalHandler asyncBusHandler) {
		this.asyncBusHandler = asyncBusHandler;
	}

	private AsyncBusTransactionalHandler asyncBusHandler;
	
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
