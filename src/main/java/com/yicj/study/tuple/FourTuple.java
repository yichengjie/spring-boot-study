package com.yicj.study.tuple;

public class FourTuple<A, B, C, D> extends ThreeTuple<A, B, C> {
	public final D fourth ;
	public FourTuple(A a, B b, C c, D d) {
		super(a, b, c);
		this.fourth = d ;
	}
	
	@Override
	public String toString() {
		return String.format("(%s, %s, %s, %s)", first,second,third,first).toString() ;
	}
}
