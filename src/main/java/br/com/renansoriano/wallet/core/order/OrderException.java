package br.com.renansoriano.wallet.core.order;

public abstract class OrderException extends RuntimeException {

	private static final long serialVersionUID = -7742257860031329932L;

	public OrderException(String messageFormat, Object... messageObjects) {
		super(String.format(messageFormat, messageObjects));
	}

	public OrderException(Throwable throwable, String messageFormat, Object... messageObjects) {
		super(String.format(messageFormat, messageObjects), throwable);
	}
}
