package noc.frame;

public interface EntityFactory<T> {
	T newInstance();
	T decorate(T t);		
}
