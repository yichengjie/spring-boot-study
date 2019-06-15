package com.yicj.study.tuple;

public class FiveTuple<A, B, C, D, E> extends FourTuple<A, B, C, D> {
	public final E fifth ;
	public FiveTuple(A a, B b, C c, D d,E e) {
		super(a, b, c, d);
		this.fifth = e;
	}
	@Override
	public String toString() {
		return  String.format("(%s, %s, %s, %s, %s)", first,second,third,fourth,fifth);
	}
}
