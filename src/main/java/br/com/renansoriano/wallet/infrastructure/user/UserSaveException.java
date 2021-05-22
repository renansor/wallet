package br.com.renansoriano.wallet.infrastructure.user;

import br.com.renansoriano.wallet.core.user.UserRepositoryException;

public class UserSaveException extends UserRepositoryException {

	private static final long serialVersionUID = -2678983098974654088L;

	public UserSaveException(Throwable throwable, String messageFormat, Object... messageObjects) {
		super(throwable, messageFormat, messageObjects);
	}
}
