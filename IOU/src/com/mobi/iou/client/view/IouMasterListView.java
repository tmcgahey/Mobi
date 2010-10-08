package com.mobi.iou.client.view;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.mobi.iou.client.presenter.IouMasterListPresenter;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;

public class IouMasterListView extends Composite implements
		IouMasterListPresenter.Display {
	
	private Button btnAddIOU;
	private TextBox txtName;
	private TextBox txtDescription;
	private TextBox txtAmount;
	private FlexTable SummaryTable;
	private ListBox cboLoan;
	
	public HasClickHandlers getBtnAddIOU() {
		return btnAddIOU;
	}


	public TextBox getTxtName() {
		return txtName;
	}

	public TextBox getTxtDescription() {
		return txtDescription;
	}


	public TextBox getTxtAmount() {
		return txtAmount;
	}
	
	public ListBox cboLoan() {
		return cboLoan;
	}


	public void setData(List<String[]> data) {
		
		for (int row = 1; row < SummaryTable.getRowCount(); row++) {
			SummaryTable.removeRow(row);
		}
			
		for (int i = 0; i < data.size(); i++) {
			int currentRow = i + 1;
			SummaryTable.setText(currentRow, 0, data.get(i)[0]);
			SummaryTable.setText(currentRow, 1, data.get(i)[1]);
			SummaryTable.setText(currentRow, 2, data.get(i)[2]);
		}
		
		txtName.setText("");
		txtDescription.setText("");
		txtAmount.setText("");
		
	}
	

	public IouMasterListView() {
		
		VerticalPanel AddIOUPanel = new VerticalPanel();
		initWidget(AddIOUPanel);
		AddIOUPanel.setWidth("664px");
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		AddIOUPanel.add(horizontalPanel);
		horizontalPanel.setWidth("700px");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_1);
		
		Label lblName_1 = new Label("Name");
		verticalPanel_1.add(lblName_1);
		
		txtName = new TextBox();
		verticalPanel_1.add(txtName);
		
		cboLoan = new ListBox();
		cboLoan.addItem("loaned me");
		cboLoan.addItem("owes me");
		cboLoan.setName("cboLoan");
		horizontalPanel.add(cboLoan);
		horizontalPanel.setCellVerticalAlignment(cboLoan, HasVerticalAlignment.ALIGN_BOTTOM);
		
		VerticalPanel verticalPanel_4 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_4);
		
		Label lblAmount_1 = new Label("Amount");
		verticalPanel_4.add(lblAmount_1);
		
		txtAmount = new TextBox();
		verticalPanel_4.add(txtAmount);
		txtAmount.setWidth("90px");
		
		VerticalPanel verticalPanel_3 = new VerticalPanel();
		horizontalPanel.add(verticalPanel_3);
		
		Label lblDescription_1 = new Label("Description");
		verticalPanel_3.add(lblDescription_1);
		
		txtDescription = new TextBox();
		verticalPanel_3.add(txtDescription);
		
		btnAddIOU = new Button("New button");
		btnAddIOU.setText("Add IOU");
		horizontalPanel.add(btnAddIOU);
		btnAddIOU.setSize("78px", "28px");
		horizontalPanel.setCellVerticalAlignment(btnAddIOU, HasVerticalAlignment.ALIGN_BOTTOM);
		
		SummaryTable = new FlexTable();
		AddIOUPanel.add(SummaryTable);
		
		Label lblName = new Label("Name");
		SummaryTable.setWidget(0, 0, lblName);
		
		Label lblDescription = new Label("Description");
		SummaryTable.setWidget(0, 1, lblDescription);
		
		Label lblAmount = new Label("Amount");
		SummaryTable.setWidget(0, 2, lblAmount);

	}

	
	@Override
	public Widget asWidget() {
		return this;
	}

}
