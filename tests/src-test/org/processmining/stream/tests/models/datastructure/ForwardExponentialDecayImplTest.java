package org.processmining.stream.tests.models.datastructure;

import java.util.Random;

import org.junit.Test;
import org.processmining.stream.model.datastructure.DSParameterFactory;
import org.processmining.stream.model.datastructure.DSParameterMissingException;
import org.processmining.stream.model.datastructure.DSWrongParameterException;
import org.processmining.stream.model.datastructure.DataStructure;
import org.processmining.stream.model.datastructure.DataStructure.Type;
import org.processmining.stream.model.datastructure.DataStructureFactory;

import junit.framework.TestCase;

public class ForwardExponentialDecayImplTest extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ForwardExponentialDecayImplTest.class);
	}

	@Test
	public void testAddElements() {

		DataStructure<Integer> s = null;
		try {
			s = DataStructureFactory.createDataStructure(Type.FORWARD_EXPONENTIAL_DECAY,
					DSParameterFactory.createDefaultParameters(Type.FORWARD_EXPONENTIAL_DECAY));
		} catch (DSParameterMissingException | DSWrongParameterException e) {
			e.printStackTrace();
		}

		assertTrue("Set Size Should be 0", s.getSize() == 0);

		Random r = new Random();

		for (int i = 1; i < 999999999; i++) {
			s.add(r.nextInt(100));
		}
		System.out.println(s.toString());
	}

}
