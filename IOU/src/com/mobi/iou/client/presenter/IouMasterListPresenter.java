package com.mobi.iou.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

public class IouMasterListPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getButton();
		Widget asWidget();
	}

	@SuppressWarnings("unused")
	private final HandlerManager eventBus;
	private final Display display;

	public IouMasterListPresenter(HandlerManager eventBus, Display view) {
		this.eventBus = eventBus;
	    this.display = view;
	}
	
	public void bind() {
		display.getButton().addClickHandler(new ClickHandler() {   
		      public void onClick(ClickEvent event) {
	    	  	Window.alert("test");
		      }
		    });
	}

	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());

	}

}
