package com.mobi.iou.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mobi.iou.client.IOUSummary;
import com.mobi.iou.client.IOUSummaryAsync;
import com.mobi.iou.shared.SummaryDetails;

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
	private final IOUSummaryAsync summaryRPCService = GWT.create(IOUSummary.class);
	
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

		summaryRPCService.getSummaryDetails(new AsyncCallback<ArrayList<SummaryDetails>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching summary details");
			}

			@Override
			public void onSuccess(ArrayList<SummaryDetails> result) {
				
				List<String[]> tableData = new ArrayList<String[]>();
				
				for(int i = 0; i < result.size(); i++) {
					String lineItem[] = new String[3];
					lineItem[0] = result.get(i).getName();
					lineItem[1] = result.get(i).getDescription();
					lineItem[2] = String.valueOf( result.get(i).getAmount());
					tableData.add(lineItem);
				}
				
				display.setData(tableData);
				
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
