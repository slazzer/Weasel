package org.dresign.bus;

public interface AsyncBusTransactionalHandler {

	public abstract void endTransaction();

	public abstract void startNewTransaction();

	public abstract void performTransaction();

}