package com.mobi.iou.client;

import com.google.gwt.core.client.JavaScriptObject;

public class SummaryDetailsJSON extends JavaScriptObject {

	protected SummaryDetailsJSON() {}

	public final native String getName() /*-{ return this.name; }-*/;
	public final native String getDescription() /*-{ return this.description; }-*/;
	public final native double getAmount() /*-{ return this.amount; }-*/;
	
	
}
