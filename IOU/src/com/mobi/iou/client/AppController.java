package com.mobi.iou.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.mobi.iou.client.presenter.IouMasterListPresenter;
import com.mobi.iou.client.presenter.Presenter;
import com.mobi.iou.client.view.IouMasterListView;

public class AppController implements Presenter, ValueChangeHandler<String> {

	private final HandlerManager eventBus;
	private HasWidgets container;

	public AppController(HandlerManager eventBus) {
		this.eventBus = eventBus;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
	    
	    if ("".equals(History.getToken())) {
	      History.newItem("list");
	    }
	    else {
	      History.fireCurrentHistoryState();
	    }

	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
	    
	    if (token != null) {
	      Presenter presenter = null;

	      if (token.equals("list")) {
	        presenter = new IouMasterListPresenter(eventBus, new IouMasterListView());
	      }
	      
	      if (presenter != null) {
	        presenter.go(container);
	      }
	    }

	}

}
