package com.mobi.iou.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.mobi.iou.client.AccountLineItemJSON;
import com.mobi.iou.client.SummaryDetailsJSON;

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

		String url = GWT.getModuleBaseURL() + "summary";
		url = URL.encode(url);

	    RequestBuilder summaryRequest = new RequestBuilder(RequestBuilder.GET, url);
	    
	    try {
		      @SuppressWarnings("unused")
			Request request = summaryRequest.sendRequest(null, new RequestCallback() {
		        public void onError(Request request, Throwable exception) {
		          Window.alert("Couldn't retrieve JSON");
		        }

		        public void onResponseReceived(Request request, Response response) {
		          if (200 == response.getStatusCode()) {
		        	  JsArray<SummaryDetailsJSON> summaryDetails = ConvertAccountSummaryJSONArray(response.getText());
		        	  displaySummaryData(summaryDetails);
		        	  
		          } else {
		            Window.alert("Couldn't retrieve JSON (" + response.getStatusText()
			                + ")");
		          }
		        }
		      });
	    } catch (RequestException e) {
	      Window.alert("Couldn't retrieve JSON");
	    }

	}
	
	private final native JsArray<SummaryDetailsJSON> ConvertAccountSummaryJSONArray(String json) /*-{
		return eval(json);
	}-*/;
	

	void addLineItem() {
		
		double amount = NumberFormat.getDecimalFormat().parse( display.getTxtAmount().getText());
		if(display.getCboLoan().getSelectedIndex() == 0) {
			amount = amount * -1;
		}
		
		
		String url = GWT.getModuleBaseURL() + "accountlineitem";
		url = URL.encode(url);
		
		RequestBuilder addItemRequest = new RequestBuilder(RequestBuilder.POST,url);
		addItemRequest.setHeader("Content-Type","application/x-www-form-urlencoded");

		
		
		AccountLineItemJSON lineItemJSON = (AccountLineItemJSON) AccountLineItemJSON.createObject();
		lineItemJSON.setName("test");

		JSONObject addLineItem = new JSONObject();
		addLineItem.put("addLineItem", new JSONObject(lineItemJSON) );
		
		try {
			@SuppressWarnings("unused")
			Request request = addItemRequest.sendRequest(addLineItem.toString(), new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						populateSummary();
					} else {
						Window.alert("Couldn't add Item (" + response.getStatusText()
								+ ")");
					}
					
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Couldn't add item");
				}
			});
			
		} catch (RequestException e) {
			Window.alert("Error adding item.");
		}
		
	}
	
	private void displaySummaryData(JsArray<SummaryDetailsJSON> summaryList) {
		
		List<String[]> tableData = new ArrayList<String[]>();

		for (int i = 0; i < summaryList.length(); i++) {
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
