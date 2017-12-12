package org.processmining.stream.tests.models.datastructure;
//package org.processmining.stream.tests.models.streamdatastore;
//
//import junit.framework.TestCase;
//
//import org.junit.Test;
//import org.processmining.stream.model.datastructure.lossy.LossyBucket;
//import org.processmining.stream.model.datastructure.lossy.LossyBucketBudgetImpl;
//
//public class LossyBucketBudgetImplTest extends TestCase {
//
//	public static void main(String[] args) {
//		junit.textui.TestRunner.run(LossyBucketBudgetImplTest.class);
//	}
//
//	@Test
//	public void testAddElement() {
//		String testString = "testing";
//		LossyBucket<String> bucket = new LossyBucketBudgetImpl<>(5);
//		bucket.add(testString);
//		assertTrue(
//				"The <string> element \"" + testString + "\" should be preseent in the bucket: " + bucket.toString(),
//				bucket.contains(testString));
//	}
//
//	@Test
//	public void testConstructionByBucketSize() {
//		int bucketSize = 5;
//		LossyBucket<Integer> bucket = new LossyBucketBudgetImpl<>(5);
//		assertTrue(bucket.getMaximumApproximationError() == 1d / bucketSize);
//	}
//
//	@Test
//	public void testConstructionByEpsilon() {
//		double epsilon = 0.2;
//		LossyBucket<String> bucket = new LossyBucketBudgetImpl<>(epsilon);
//		assertTrue(bucket.getBucketSize() == 1d / epsilon);
//	}
//
//	@Test
//	public void testDisjunctElements() {
//		int bucketSize = 5;
//		int numElems = 100;
//		LossyBucket<Integer> bucket = new LossyBucketBudgetImpl<>(bucketSize);
//		for (int i = 1; i <= numElems; i++) {
//			bucket.add(i);
//			for (int j = i; j > i - (i % bucketSize); j--) {
//				assertTrue("elem " + j + " should be present in the bucket: " + bucket.toString(), bucket.contains(j));
//			}
//		}
//	}
//
//	@Test
//	public void testIterator() {
//		int bucketSize = 5;
//		LossyBucket<Integer> bucket = new LossyBucketBudgetImpl<>(bucketSize);
//		bucket.add(1);
//		bucket.add(2);
//		int i = 0;
//		for (@SuppressWarnings("unused")
//		int j : bucket) {
//			i++;
//		}
//		assertTrue(i == 2);
//
//	}
//
//	@Test
//	public void testPresenceAndAutomatedRemovalOfElements() {
//		int bucketSize = 2;
//		LossyBucket<Integer> bucket = new LossyBucketBudgetImpl<>(bucketSize);
//		bucket.add(1);
//		bucket.add(1);
//		bucket.add(2);
//		bucket.add(3);
//		for (int i = 1; i <= 3; i++) {
//			if (i == 1 || i == 3) {
//				assertTrue("elem " + i + " should be present in the bucket: " + bucket.toString(), bucket.contains(i));
//			} else {
//				assertTrue("elem " + i + " should not be present in the bucket: " + bucket.toString(),
//						!bucket.contains(i));
//			}
//		}
//		bucket.add(1);
//		bucket.add(1);
//		bucket.add(1);
//		bucket.add(2);
//		for (int i = 1; i <= 3; i++) {
//			if (i % 3 != 0) {
//				assertTrue("elem " + i + " should be present in the bucket: " + bucket.toString(), bucket.contains(i));
//			} else {
//				assertTrue("elem " + i + " should not be present in the bucket: " + bucket.toString(),
//						!bucket.contains(i));
//			}
//		}
//	}
//}
