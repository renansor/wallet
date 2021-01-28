package br.com.renansoriano.wallet.infrastructure.order;

import br.com.renansoriano.wallet.core.order.OrderRepositoryException;

public class OrderSaveException extends OrderRepositoryException {

	private static final long serialVersionUID = 4309525891601404095L;

	public OrderSaveException(Throwable throwable, String messageFormat, Object... messageObjects) {
		super(throwable, messageFormat, messageObjects);
	}
}
