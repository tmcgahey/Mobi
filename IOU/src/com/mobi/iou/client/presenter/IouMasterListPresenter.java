package com.mobi.iou.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.mobi.iou.client.IOUSummaryService;
import com.mobi.iou.client.IOUSummaryServiceAsync;
import com.mobi.iou.shared.SummaryDetails;

public class IouMasterListPresenter implements Presenter {

	public interface Display {
		HasClickHandlers getBtnAddIOU();
		TextBox getTxtName();
		TextBox getTxtDescription();
		TextBox getTxtAmount();
		ListBox getCboLoan();
		DateBox getDateItem();
		void setSignOutLink(String signOutLink);
		void setData(List<String[]> data);

		Widget asWidget();
	}

	@SuppressWarnings("unused")
	private final HandlerManager eventBus;
	private final Display display;
	private final IOUSummaryServiceAsync summaryRPCService = GWT
			.create(IOUSummaryService.class);

	public IouMasterListPresenter(HandlerManager eventBus,String logoutURL, Display view) {
		this.eventBus = eventBus;
		this.display = view;
		display.setSignOutLink(logoutURL);
		populateSummary();
	}

	public void bind() {

		display.getBtnAddIOU().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				addLineItem();
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

						displaySummaryData(result);
					}

				});

	}

	void addLineItem() {
		
		double amount = NumberFormat.getDecimalFormat().parse( display.getTxtAmount().getText());
		if(display.getCboLoan().getSelectedIndex() == 0) {
			amount = amount * -1;
		}
		
		summaryRPCService.AddItemReturnSummary(display.getTxtName().getText(), display.getTxtDescription().getText(),amount, new AsyncCallback<ArrayList<SummaryDetails>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error adding line item");
				
			}

			@Override
			public void onSuccess(ArrayList<SummaryDetails> result) {
				displaySummaryData(result);
				
			}
		});
		
	}
	
	private void displaySummaryData(ArrayList<SummaryDetails> summaryList) {
		
		List<String[]> tableData = new ArrayList<String[]>();

		for (int i = 0; i < summaryList.size(); i++) {
			String lineItem[] = new String[3];
			lineItem[0] = summaryList.get(i).getName();
			lineItem[1] = summaryList.get(i).getDescription();
			lineItem[2] = String.valueOf(summaryList.get(i).getAmount());
			tableData.add(lineItem);
		}

		display.setData(tableData);
		
	}
	
	@Override
	public void go(HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());

	}

}
