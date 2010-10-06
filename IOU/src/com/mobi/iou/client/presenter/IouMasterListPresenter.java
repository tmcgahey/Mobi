package com.mobi.iou.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mobi.iou.client.IOUSummary;
import com.mobi.iou.client.IOUSummaryAsync;

public class IouMasterListPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getBtnAddIOU();

		TextBox getTxtName();

		TextBox getTxtEmail();

		TextBox getTxtDescription();

		TextBox getTxtAmount();

		void setData(List<String[]> data);

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

		display.getBtnAddIOU().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				populateSummary();
			}
		});

	}

	void populateSummary() {

		//IOUSummaryAsync rpcService = GWT.create(IOUSummary.class);
		
		String lineItem[] = new String[3];
		List<String[]> tableData = new ArrayList<String[]>();
		lineItem[0] = display.getTxtName().getText();
		lineItem[1] = display.getTxtDescription().getText();
		lineItem[2] = display.getTxtAmount().getText();
		tableData.add(lineItem);
		
		display.setData(tableData);
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());

	}

}
