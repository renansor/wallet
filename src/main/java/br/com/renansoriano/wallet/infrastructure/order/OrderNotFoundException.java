package br.com.renansoriano.wallet.infrastructure.order;

import br.com.renansoriano.wallet.core.order.OrderRepositoryException;

public abstract class OrderNotFoundException extends OrderRepositoryException {

	private static final long serialVersionUID = -63001406406430041L;

	public OrderNotFoundException(String messageFormat, Object... messageObjects) {
		super(messageFormat, messageObjects);
	}
}
