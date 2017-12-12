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
import org.processmining.stream.model.datastructure.countmin.CountMinSketchParameterDefinition;

import junit.framework.TestCase;

public class CountMinSketchTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(CountMinSketchTest.class);
	}

	@Test
	public void testAddElement() {
		Map<CountMinSketchParameterDefinition, DSParameter<?>> parameters = DSParameterFactory
				.createDefaultParameters(Type.COUNT_MIN_SKETCH);
		DataStructure<Integer> ds;
		try {
			ds = DataStructureFactory.createDataStructure(Type.COUNT_MIN_SKETCH, parameters);
			ds.add(1);
			assertTrue(ds.getFrequencyOf(1) == 1);
			ds.add(1);
			assertTrue(ds.getFrequencyOf(1) == 2);
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
			ds.add(5);
			ds.add(6);
			assertTrue(ds.contains(1));
			assertTrue(ds.contains(3));
			assertTrue(ds.contains(4));
			assertTrue(ds.contains(5));
			assertTrue(ds.contains(6));
			assertTrue(ds.getFrequencyOf(3) == 2);

		} catch (DSParameterMissingException | DSWrongParameterException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

	@Test
	public void testAddElementPointer() {
		Map<CountMinSketchParameterDefinition, DSParameter<?>> parameters = DSParameterFactory
				.createDefaultParameters(Type.COUNT_MIN_SKETCH);
		PointerBasedDataStructure<Integer, String> ds;
		try {
			ds = DataStructureFactory.createPointerDataStructure(Type.COUNT_MIN_SKETCH_POINTER, parameters);
			ds.add(1, "Hello");
			assertTrue(ds.getFrequencyOf(1) == 1);
			assertTrue(ds.getPointedElement(1).equals("Hello"));
			ds.add(1, "Monkey");
			assertTrue(ds.getFrequencyOf(1) == 2);
			assertTrue(ds.getPointedElement(1).equals("Monkey"));
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
			ds.add(5);
			ds.add(6);
			assertTrue(ds.contains(1));
			assertTrue(ds.contains(3));
			assertTrue(ds.contains(4));
			assertTrue(ds.contains(5));
			assertTrue(ds.contains(6));
			assertTrue(ds.getFrequencyOf(3) == 2);

		} catch (DSParameterMissingException | DSWrongParameterException | OperationNotSupportedException e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}

}
