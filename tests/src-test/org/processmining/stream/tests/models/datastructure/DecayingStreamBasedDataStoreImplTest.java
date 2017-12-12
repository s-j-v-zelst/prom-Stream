package org.processmining.stream.tests.models.datastructure;

import junit.framework.TestCase;

public class DecayingStreamBasedDataStoreImplTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(DecayingStreamBasedDataStoreImplTest.class);
	}

	//	@Test
	//	public void testAddElements() {
	//		DecayFunction d = new ExponentialDecayFunctionImpl(1, 0.25);
	//		DataStructure<Integer> s = new DecayingStreamBasedDataStoreImpl<>(d, 10, 0.15);
	//
	//		assertTrue("Set Size Should be 0", s.getSize() == 0);
	//
	//		for (int i = 1; i < 10; i++) {
	//			s.add(i);
	//			assertTrue("Size Should be " + i, s.getSize() == i);
	//			System.out.println(s.toString());
	//		}
	//
	//		s.add(2);
	//		s.add(15);
	//		s.add(16);
	//		System.out.println(s.toString());
	//	}

}
