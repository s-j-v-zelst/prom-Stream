package org.processmining.stream.tests.models.datastructure;

import java.util.Map;

import javax.naming.OperationNotSupportedException;

import org.junit.Test;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.DSParameterFactory;
import org.processmining.stream.model.datastructure.DSParameterMissingException;
import org.processmining.stream.model.datastructure.DSWrongParameterException;
import org.processmining.stream.model.datastructure.DataStructure;
import org.processmining.stream.model.datastructure.DataStructure.Type;
import org.processmining.stream.model.datastructure.DataStructureFactory;
import org.processmining.stream.model.datastructure.PointerBasedDataStructure;
import org.processmining.stream.model.datastructure.lossy.LossyCountingParameterDefinition;

import junit.framework.TestCase;

public class LossyCountingTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(LossyCountingTest.class);
	}

	@Test
	public void testAddElement() {
		String testString = "testing";
		Map<LossyCountingParameterDefinition, DSParameter<?>> parameters = DSParameterFactory
				.createDefaultParameters(Type.LOSSY_COUNTING);
		parameters.put(LossyCountingParameterDefinition.SIZE, DSParameterFactory.createParameter(5));
		DataStructure<String> bucket;
		try {
			bucket = DataStructureFactory.createDataStructure(Type.LOSSY_COUNTING, parameters);
			bucket.add(testString);
			assertTrue("The <string> element \"" + testString + "\" should be preseent in the bucket: "
					+ bucket.toString(), bucket.contains(testString));
		} catch (DSParameterMissingException | DSWrongParameterException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testDisjunctElements() {
		int bucketSize = 5;
		int numElems = 100;
		Map<LossyCountingParameterDefinition, DSParameter<?>> parameters = DSParameterFactory
				.createDefaultParameters(Type.LOSSY_COUNTING);
		parameters.put(LossyCountingParameterDefinition.SIZE, DSParameterFactory.createParameter(5));
		DataStructure<Integer> bucket;
		try {
			bucket = DataStructureFactory.createDataStructure(Type.LOSSY_COUNTING, parameters);
			for (int i = 1; i <= numElems; i++) {
				bucket.add(i);
				if (i % bucketSize != 0) {
					assertTrue("elem " + i + " should be present in the bucket: " + bucket.toString(),
							bucket.contains(i));
				} else {
					assertTrue("elem " + i + " should not be present in the bucket: " + bucket.toString(),
							!bucket.contains(i));
				}
				for (int j = 1; j < i; j++) {
					if (i - bucketSize < j && i % bucketSize > j % bucketSize && j % bucketSize != 0) {
						assertTrue("elem " + j + " should be present in the bucket: " + bucket.toString(),
								bucket.contains(j));
					} else {
						assertTrue("elem " + j + " should not be present in the bucket: " + bucket.toString(),
								!bucket.contains(j));
					}
				}
			}
		} catch (DSParameterMissingException | DSWrongParameterException e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testPresenceAndRemovalOfElements() {
		Map<LossyCountingParameterDefinition, DSParameter<?>> parameters = DSParameterFactory
				.createDefaultParameters(Type.LOSSY_COUNTING);
		parameters.put(LossyCountingParameterDefinition.SIZE, DSParameterFactory.createParameter(5));
		DataStructure<Integer> bucket;
		try {
			bucket = DataStructureFactory.createDataStructure(Type.LOSSY_COUNTING, parameters);
			bucket.add(1);
			bucket.add(1);
			bucket.add(2);
			bucket.add(2);
			bucket.add(3);
			for (int i = 1; i <= 3; i++) {
				if (i % 3 != 0) {
					assertTrue("elem " + i + " should be present in the bucket: " + bucket.toString(),
							bucket.contains(i));
				} else {
					assertTrue("elem " + i + " should not be present in the bucket: " + bucket.toString(),
							!bucket.contains(i));
				}
			}
			bucket.add(1);
			bucket.add(1);
			bucket.add(1);
			bucket.add(3);
			bucket.add(4);
			for (int i = 1; i <= 3; i++) {
				if (i == 1) {
					assertTrue("elem " + i + " should be present in the bucket: " + bucket.toString(),
							bucket.contains(i));
				} else {
					assertTrue("elem " + i + " should not be present in the bucket: " + bucket.toString(),
							!bucket.contains(i));
				}
			}
		} catch (DSParameterMissingException | DSWrongParameterException e) {
			e.printStackTrace();
			assertTrue(false);
		}

	}

	@Test
	public void testPointerVariant() {
		Map<LossyCountingParameterDefinition, DSParameter<?>> parameters = DSParameterFactory
				.createDefaultParameters(Type.LOSSY_COUNTING);
		parameters.put(LossyCountingParameterDefinition.SIZE, DSParameterFactory.createParameter(5));
		PointerBasedDataStructure<Integer, String> bucket;
		try {
			bucket = DataStructureFactory.createPointerDataStructure(Type.LOSSY_COUNTING_POINTER, parameters);
			bucket.add(1, "a");
			bucket.add(2, "b");
			assertTrue(bucket.contains(1) && bucket.getPointedElement(1).equals("a"));
			assertTrue(bucket.contains(2) && bucket.getPointedElement(2).equals("b"));
			bucket.add(2, "c");
			assertTrue(bucket.contains(2) && bucket.getPointedElement(2).equals("c"));
			assertTrue(bucket.contains(2) && !bucket.getPointedElement(2).equals("b"));
			System.out.println(bucket.toString());
		} catch (DSParameterMissingException | DSWrongParameterException | OperationNotSupportedException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
}
