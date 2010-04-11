package noc.frame;

import noc.lang.Bool;

@Deprecated public interface Op<P, T> {
	P set(String v);

	P add(T value);

	P plus(T value);

	P minus(T value);

	P multiply(T value);

	P power(T value);

	P mod(T value);

	int compareTo(T value);

	boolean isEmpty();

	@Deprecated interface Comparible<T> {
		/**
		 * > : Greater Than
		 * 
		 * @param value
		 * @return
		 */
		Bool GT(T value);

		/**
		 * >= : Greater than or Equivalent with
		 * 
		 * @param value
		 * @return
		 */
		Bool GE(T value);

		/**
		 * < : Less than
		 * 
		 * @param value
		 * @return
		 */
		Bool LT(T value);

		/**
		 * <= : Less than or Equivalent with
		 * 
		 * @param value
		 * @return
		 */
		Bool LE(T value);

		/**
		 * == : EQuivalent with
		 * 
		 * @param value
		 * @return
		 */
		Bool EQ(T value);

		/**
		 * != : Not Equivalent with
		 * 
		 * @param value
		 * @return
		 */
		Bool NE(T value);
	}
}
