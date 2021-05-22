package br.com.renansoriano.wallet.core.user;

public abstract class UserRepositoryException extends RuntimeException {

	private static final long serialVersionUID = 3241044370807659796L;

	public UserRepositoryException(String messageFormat, Object... messageObjects) {
		super(String.format(messageFormat, messageObjects));
	}

	public UserRepositoryException(Throwable throwable, String messageFormat, Object... messageObjects) {
		super(String.format(messageFormat, messageObjects), throwable);
	}
}
