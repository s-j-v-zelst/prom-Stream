package org.processmining.stream.reader.views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.processmining.framework.util.Pair;
import org.processmining.stream.core.abstracts.AbstractXSRunnable;
import org.processmining.stream.core.interfaces.XSDataPacket;
import org.processmining.stream.core.interfaces.XSReader;
import org.processmining.stream.core.interfaces.XSReaderResultVisualizer;
import org.processmining.stream.core.interfaces.XSVisualization;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class XSReaderSequenceOfResultsView<T> extends AbstractXSRunnable implements XSVisualization<T> {

	private final JComponent visualization = new JPanel();
	private final XSReaderResultVisualizer<T> readerResultVisualizer;
	private final List<JComponent> results = new ArrayList<>();
	private XSReader<? extends XSDataPacket<?, ?>, T> reader;
	private final long SLEEP_TIME = 1000l;

	public void setReader(XSReader<? extends XSDataPacket<?, ?>, T> reader) {
		this.reader = reader;
	}

	private final int refreshRate;
	private long lastSample = 0;
	private long lastPacketAtReader = 0;

	private final JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 0, 0);
	private final JCheckBox autoUpdate = SlickerFactory.instance().createCheckBox("Refresh view", false);
	private static final String PACKETS_RECEIVEC_LABEL_PREFIX = "#packets: ";
	private final JLabel packetsReceived = new JLabel(PACKETS_RECEIVEC_LABEL_PREFIX + "0");
	private final JComponent modelInteractionContainer;
	private final JButton triggerNewResultButton = SlickerFactory.instance().createButton("Update Result");

	private JComponent currentResult = SlickerFactory.instance().createLabel("No result to show yet.");
	//	private final JSplitPane resultAndSliderPane;

	public XSReaderSequenceOfResultsView(String name, XSReader<? extends XSDataPacket<?, ?>, T> reader, int refrehsRate,
			XSReaderResultVisualizer<T> readerResultVisualizer) {
		super(name);
		this.reader = reader;
		this.refreshRate = refrehsRate;
		this.readerResultVisualizer = readerResultVisualizer;
		modelInteractionContainer = constructModelInteractionContainer(refrehsRate);
		visualization.setLayout(new BorderLayout());
		visualization.add(currentResult, BorderLayout.CENTER);
		visualization.add(modelInteractionContainer, BorderLayout.SOUTH);
	}

	public JComponent asComponent() {
		return visualization;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComponent constructModelInteractionContainer(int refreshRate) {
		JComponent container = new JPanel();
		container.setLayout(new BorderLayout());
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		Dictionary labelTable = new Hashtable();
		labelTable.put(new Integer(0), new JLabel("no model yet"));
		slider.setLabelTable(labelTable);
		slider.setPaintLabels(true);

		slider.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				if (!slider.getValueIsAdjusting()) {
					int solutionToShow = slider.getValue();
					selectResult(solutionToShow);
					visualization.revalidate();
					visualization.repaint();
				}
			}
		});

		container.add(slider, BorderLayout.CENTER);
		JComponent autoRefreshNewModelContainer = new JPanel();
		if (refreshRate != -1) {
			autoRefreshNewModelContainer.add(autoUpdate);
		}
		autoRefreshNewModelContainer.add(packetsReceived);

		triggerNewResultButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshResultsView(true);
			}
		});

		autoRefreshNewModelContainer.add(triggerNewResultButton);

		container.add(autoRefreshNewModelContainer, BorderLayout.EAST);
		return container;
	}

	//FIXME: Change by using a Queue of results via update function
	protected void workPackage() {
		// prevent reader from receiving new messages when visualizing.
		boolean sleep = false;
		synchronized (reader.getDeliveryLock()) {
			if (lastPacketAtReader == reader.getNumberOfPacketsReceived()) {
				sleep = true;
				Thread.yield();
			} else {
				lastPacketAtReader = reader.getNumberOfPacketsReceived();
				packetsReceived.setText(PACKETS_RECEIVEC_LABEL_PREFIX + lastPacketAtReader);
				if ((lastPacketAtReader - lastSample) / refreshRate > 0) {
					refreshResultsView(autoUpdate.isSelected());
					lastSample = lastPacketAtReader;
				}
				visualization.revalidate();
				visualization.repaint();
			}
		}
		if (sleep) {
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void refreshResultsView(boolean updateToLatest) {
		T curRes = null;
		synchronized (reader.getDeliveryLock()) {
			lastPacketAtReader = reader.getNumberOfPacketsReceived();
			curRes = reader.getCurrentResult();
		}
		if (curRes != null) {
			results.add(readerResultVisualizer.visualize(curRes));
			slider.setMaximum(results.size() - 1);
			@SuppressWarnings("rawtypes")
			Dictionary labelTable = slider.getLabelTable();
			labelTable.put(new Integer(results.size() - 1), new JLabel(String.valueOf(lastPacketAtReader)));
			slider.setLabelTable(labelTable);
			if (updateToLatest || results.size() == 1) {
				selectResult(results.size() - 1);
				slider.setValue(results.size() - 1);
			}
			visualization.revalidate();
			visualization.repaint();
		}
	}

	private void selectResult(int i) {
		assert (i < results.size());
		assert (i >= 0);
		visualization.remove(currentResult);
		currentResult = results.get(i);
		visualization.add(currentResult, BorderLayout.CENTER);
	}

	public void update(Pair<Date, String> message) {
	}

	public void update(String object) {
	}

	public void updateVisualization(Pair<Date, T> newArtifact) {	
	}

	public void updateVisualization(T newArtifact) {
	}

}
