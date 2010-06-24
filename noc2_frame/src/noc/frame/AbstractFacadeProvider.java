package noc.frame;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public abstract class AbstractFacadeProvider<T> extends AbstractProvider<T> {

	ArrayList<Entry<Pattern, Provider<T>>> providers = new ArrayList<Entry<Pattern, Provider<T>>>();

	protected T find(String key) {
		// 对所有符合条件的Provider调用find,返回第一个正确得到的persister,并把Persister放入缓存
		T item = null;

		for (int i = providers.size() - 1; i >= 0; i--) {
			Entry<Pattern, Provider<T>> entry = providers.get(i);
			if (entry.getKey().matcher(key).matches()) {
				item = entry.getValue().get(key);
				if (item != null) {
					break;
				}
			}
		}

		return item;
	}

	@Override public void setup() {
		for (Entry<Pattern, Provider<T>> entry : providers) {
			entry.getValue().setup();
		}
	}

	@Override public void cleanup() {
		for (Entry<Pattern, Provider<T>> entry : providers) {
			entry.getValue().cleanup();
		}
	}

	public void register(String patternString, Provider<T> provider) {
		Pattern pattern = Pattern.compile(patternString);
		providers.add(new ProviderEntry<Pattern, Provider<T>>(pattern, provider));
	}

	class ProviderEntry<KK, VV> implements Map.Entry<KK, VV> {
		KK key;
		VV value;

		public KK getKey() {
			return key;
		}

		public VV getValue() {
			return value;
		}

		ProviderEntry(KK key, VV value) {
			this.key = key;
			this.value = value;
		}

		@Override public VV setValue(VV value) {
			this.value = value;
			return value;
		}
	}
}
