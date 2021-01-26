package br.com.renansoriano.wallet.core.order;

public abstract class OrderRepositoryException extends RuntimeException {

	private static final long serialVersionUID = -7742257860031329932L;

	public OrderRepositoryException(String messageFormat, Object... messageObjects) {
		super(String.format(messageFormat, messageObjects));
	}

	public OrderRepositoryException(Throwable throwable, String messageFormat, Object... messageObjects) {
		super(String.format(messageFormat, messageObjects), throwable);
	}
}
