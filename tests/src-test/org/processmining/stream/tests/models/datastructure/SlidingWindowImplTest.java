package org.processmining.stream.tests.models.datastructure;
//package org.processmining.stream.tests.models.streamdatastore;
//
//import org.junit.Test;
//import org.processmining.stream.model.datastructure.DataStructure;
//import org.processmining.stream.model.datastructure.slidingwindow.SlidingWindowImpl;
//
//import junit.framework.TestCase;
//
//public class SlidingWindowImplTest extends TestCase {
//
//	public static void main(String[] args) {
//		junit.textui.TestRunner.run(SlidingWindowImplTest.class);
//	}
//
//	@Test
//	public void testAddElements() {
//		//		StreamBasedDataStore<Integer> window = StreamBasedDataStoreFactory.createSlidingWindow(5);
//		DataStructure<Integer> window = new SlidingWindowImpl<>(5);
//		assertTrue("Window should be empty", window.getSize() == 0);
//
//		for (int i = 1; i < 6; i++) {
//			window.add(i);
//			assertTrue("Window Size Should be " + i, window.getSize() == i);
//			System.out.println(window.toString());
//		}
//
//		window.add(6);
//		assertTrue("Window Size should be 5", window.getSize() == 5);
//		assertTrue("1 should be gone ", !window.contains(1));
//		System.out.println(window.toString());
//	}
//
//}
