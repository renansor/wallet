package br.com.renansoriano.wallet.core.user;

public abstract class UserException extends RuntimeException {

	private static final long serialVersionUID = 5050831592488094541L;

	public UserException(String messageFormat, Object... messageObjects) {
		super(String.format(messageFormat, messageObjects));
	}

	public UserException(Throwable throwable, String messageFormat, Object... messageObjects) {
		super(String.format(messageFormat, messageObjects), throwable);
	}
}
