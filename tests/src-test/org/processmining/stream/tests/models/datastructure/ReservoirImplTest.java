package org.processmining.stream.tests.models.datastructure;
//package org.processmining.stream.tests.models.streamdatastore;
//
//import org.junit.Test;
//import org.processmining.stream.model.datastructure.DataStructure;
//import org.processmining.stream.model.datastructure.reservoir.ReservoirImpl;
//
//import junit.framework.TestCase;
//
//public class ReservoirImplTest extends TestCase {
//
//	public static void main(String[] args) {
//		junit.textui.TestRunner.run(ReservoirImplTest.class);
//	}
//
//	@Test
//	public void testAddElements() {
//		DataStructure<Integer> res = new ReservoirImpl<Integer>(5, System.currentTimeMillis());
//		assertTrue("Reservoir Size Should be 0", res.getSize() == 0);
//
//		for (int i = 1; i < 6; i++) {
//			res.add(i);
//			assertTrue("Reservoir Size Should be " + i, res.getSize() == i);
//			System.out.println(res.toString());
//		}
//
//		res.add(6);
//		System.out.println(res.toString());
//		assertTrue("Reservoir Size should be 5", res.getSize() == 5);
//		System.out.println(res.toString());
//	}
//
//}
