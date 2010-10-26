package com.mobi.iou.client;


import com.google.gwt.core.client.JavaScriptObject;

public class AccountLineItemJSON extends JavaScriptObject {
	
	protected AccountLineItemJSON() {}
	
	public final native String getName() /*-{ return this.name; }-*/;
	public final native void setName(String value) /*-{ this.name = value; }-*/;
	
	public final native String getDescription() /*-{ return this.description; }-*/;
	public final native void setDescription(String value) /*-{ this.description = value; }-*/;

	public final native double getAmount() /*-{ return this.amount; }-*/;
	public final native void setAmount(double value) /*-{ this.amount = value; }-*/;
	
	public final native String getDate() /*-{ return this.date; }-*/;
	public final native void setDate(String value) /*-{ this.date = value; }-*/;
	
}
