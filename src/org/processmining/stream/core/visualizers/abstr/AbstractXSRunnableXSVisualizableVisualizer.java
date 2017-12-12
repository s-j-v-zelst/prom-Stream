package org.processmining.stream.core.visualizers.abstr;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.processmining.stream.core.interfaces.XSRunnable;
import org.processmining.stream.core.interfaces.XSVisualizable;
import org.processmining.stream.core.visualizers.views.StartPauseStopXSRunnableInteractionJPanel;
import org.processmining.widgets.ui.utils.JSplitPaneUtils;

public abstract class AbstractXSRunnableXSVisualizableVisualizer<T extends XSRunnable & XSVisualizable> {

	private final JPanel container = new JPanel();
	private final JPanel interactionPanel;

	public AbstractXSRunnableXSVisualizableVisualizer(T visualized) {
		interactionPanel = new StartPauseStopXSRunnableInteractionJPanel(visualized);
		container.setLayout(new BorderLayout());
		JSplitPane split = JSplitPaneUtils.borderlessSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				visualized.getVisualization().asComponent(), interactionPanel);
		split.setResizeWeight(0.75d);
		split.setEnabled(false);
		container.add(split, BorderLayout.CENTER);
	}

	public JComponent show() {
		return container;
	}
}
