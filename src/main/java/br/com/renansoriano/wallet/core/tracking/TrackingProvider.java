package br.com.renansoriano.wallet.core.tracking;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class TrackingProvider {

	private static final ThreadLocal<Map<String, String>> value = new ThreadLocal<>();
	private static final String KEY_INNER_ID = "innerId";
	private static final String KEY_CID = "cid";

	private static TrackingProvider instance;

	private Map<String, List<Consumer<String>>> observers;

	private TrackingProvider() {
		this.observers = new ConcurrentHashMap<>();
	}

	public static TrackingProvider getInstance() {
		if (Objects.isNull(instance)) {
			instance = new TrackingProvider();
		}
		return instance;
	}

	public void addInnerIdObserver(Consumer<String> observer) {
		addObserver(KEY_INNER_ID, observer);
	}

	public String getInnerId() {
		return get(KEY_INNER_ID);
	}

	public void setInnerId(String value) {
		set(KEY_INNER_ID, value);
	}

	public void addCidObserver(Consumer<String> observer) {
		addObserver(KEY_CID, observer);
	}

	public String getCid() {
		return get(KEY_CID);
	}

	public void setCid(String value) {
		set(KEY_CID, value);
	}

	private void addObserver(String key, Consumer<String> observer) {
		List<Consumer<String>> listObservers = getObservers(key);
		listObservers.add(observer);

		if (!this.observers.containsKey(key)) {
			this.observers.put(key, listObservers);
		}
	}

	private List<Consumer<String>> getObservers(String key) {
		return this.observers.getOrDefault(key, new ArrayList<>());
	}

	private String get(String key) {
		return getThreadLocalValue().get(key);
	}

	private void set(String key, String value) {
		getThreadLocalValue().put(key, value);
		getObservers(key).forEach(observer -> observer.accept(value));
	}

	private Map<String, String> getThreadLocalValue() {
		return Optional.ofNullable(TrackingProvider.value.get()).orElseGet(() -> {
			TrackingProvider.value.set(new LinkedHashMap<>());
			return TrackingProvider.value.get();
		});
	}

}
