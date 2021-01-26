package br.com.renansoriano.wallet.infrastructure.jpa;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Consumer;

import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.context.annotation.Bean;

public class EntityManagerProvider implements Closeable {

	private final EntityManagerFactory factory;
	private final EntityManager entityManager;

	@Bean
	public EntityManagerProvider getProvider(EntityManagerFactory factory) {
		return new EntityManagerProvider(factory);
	}

	public EntityManagerProvider(EntityManagerFactory factory) {
		this.factory = factory;
		this.entityManager = factory.createEntityManager();
	}

	public EntityManagerFactory getFactory() {
		return factory;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void executeInNewTransaction(Consumer<EntityManager> consumer) {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		consumer.accept(entityManager);
		transaction.commit();
	}

	@PreDestroy
	@Override
	public void close() throws IOException {
		entityManager.close();
	}
}
