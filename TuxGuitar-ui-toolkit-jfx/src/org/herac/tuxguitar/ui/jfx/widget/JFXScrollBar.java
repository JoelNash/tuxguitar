package org.herac.tuxguitar.ui.jfx.widget;

import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Region;

import org.herac.tuxguitar.ui.event.UISelectionListener;
import org.herac.tuxguitar.ui.jfx.event.JFXSelectionListenerChangeManager;
import org.herac.tuxguitar.ui.widget.UIScrollBar;

public class JFXScrollBar extends JFXControl<ScrollBar> implements UIScrollBar {
	
	private Integer thumb;
	private JFXSelectionListenerChangeManager<Number> selectionListener;
	
	public JFXScrollBar(JFXContainer<? extends Region> parent, Orientation orientation) {
		super(new ScrollBar(), parent);
		
		this.getControl().setOrientation(orientation);
		this.selectionListener = new JFXSelectionListenerChangeManager<Number>(this);
	}
	
	public void setValue(int value) {
		this.getControl().setValue(value);
	}

	public int getValue() {
		return (int) Math.round(this.getControl().getValue());
	}

	public void setMaximum(int maximum) {
		this.getControl().setMax(maximum);
		this.updateVisibleAmount();
	}

	public int getMaximum() {
		return (int) Math.round(this.getControl().getMax());
	}

	public void setMinimum(int minimum) {
		this.getControl().setMin(minimum);
		this.updateVisibleAmount();
	}

	public int getMinimum() {
		return (int) Math.round(this.getControl().getMin());
	}

	public void setIncrement(int increment) {
		this.getControl().setUnitIncrement(increment);
	}

	public int getIncrement() {
		return (int) Math.round(this.getControl().getUnitIncrement());
	}
	
	public void setThumb(int thumb) {
		this.thumb = thumb;
		this.updateVisibleAmount();
	}
	
	public int getThumb() {
		return (this.thumb != null ? this.thumb : -1);
	}
	
	public void updateVisibleAmount() {
		double amount = 0;
		if( this.thumb != null ) {
			double size = (Orientation.HORIZONTAL.equals(this.getControl().getOrientation()) ? this.getControl().getWidth() : this.getControl().getHeight());
			double maximumValue = this.getControl().getMax();
			double maximumSize = (size + maximumValue);
			if( maximumSize > 0 ) {
				amount = (this.thumb * maximumValue / maximumSize);
			}
		}
		this.getControl().setVisibleAmount(amount);
	}
	
	public void addSelectionListener(UISelectionListener listener) {
		if( this.selectionListener.isEmpty() ) {
			this.getControl().valueProperty().addListener(this.selectionListener);
		}
		this.selectionListener.addListener(listener);
	}

	public void removeSelectionListener(UISelectionListener listener) {
		this.selectionListener.removeListener(listener);
		if( this.selectionListener.isEmpty() ) {
			this.getControl().valueProperty().removeListener(this.selectionListener);
		}
	}
}
