package org.processmining.stream.tests.models.datastructure;

import java.util.Map;

import org.junit.Test;
import org.processmining.stream.model.datastructure.DSParameter;
import org.processmining.stream.model.datastructure.DSParameterFactory;
import org.processmining.stream.model.datastructure.DSParameterMissingException;
import org.processmining.stream.model.datastructure.DSWrongParameterException;
import org.processmining.stream.model.datastructure.DataStructure;
import org.processmining.stream.model.datastructure.DataStructure.Type;
import org.processmining.stream.model.datastructure.DataStructureFactory;
import org.processmining.stream.model.datastructure.frequent.FrequentAlgorithmParameterDefinition;

import junit.framework.TestCase;

public class FrequencyAlgorithmTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(FrequencyAlgorithmTest.class);
	}

	@Test
	public void testAddElement() {
		Map<FrequentAlgorithmParameterDefinition, DSParameter<?>> parameters = DSParameterFactory
				.createDefaultParameters(Type.FREQUENT_ALGORITHM);
		parameters.put(FrequentAlgorithmParameterDefinition.SIZE, DSParameterFactory.createParameter(5));
		DataStructure<Integer> ds;
		try {
			ds = DataStructureFactory.createDataStructure(Type.FREQUENT_ALGORITHM, parameters);
			ds.add(1);
			ds.add(1);
			assertTrue(ds.contains(1));
			ds.add(2);
			assertTrue(ds.contains(1));
			assertTrue(ds.contains(2));
			ds.add(3);
			assertTrue(ds.contains(1));
			assertTrue(ds.contains(2));
			assertTrue(ds.contains(3));
			ds.add(4);
			assertTrue(ds.contains(1));
			assertTrue(ds.contains(2));
			assertTrue(ds.contains(3));
			assertTrue(ds.contains(4));
			ds.add(3);
			ds.add(4);
			ds.add(5);
			// removes 2, sets counters of others to 1.
			ds.add(5);
			ds.add(6);
			ds.add(5);
			assertTrue(ds.contains(5));

		} catch (DSParameterMissingException | DSWrongParameterException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
