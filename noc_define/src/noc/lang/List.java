package noc.lang;

import noc.annotation.Abstract;

@Abstract
public interface List<T> {
	T sum();
	T avg();
	T max();
	T min();

	Count count();	
	
	public interface Match<T>{
		Bool match(T o);
	}
	
	//TODO 
//	Number count(Bool match);
//	T sum(Bool match);
//	T avg(Bool match);
//	T max(Bool match);
//	T min(Bool match);	 
	
	List<T> sub(Match<T> match);
}
