package com.mobi.iou.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.mobi.iou.client.presenter.IouMasterListPresenter;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.HasClickHandlers;

public class IouMasterListView extends Composite implements
		IouMasterListPresenter.Display {
	private Button button;

	public IouMasterListView() {

		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);

		button = new Button("New button");
		verticalPanel.add(button);

	}

	public HasClickHandlers getButton() {
		return button;
	}

	@Override
	public Widget asWidget() {
		return this;
	}

}
