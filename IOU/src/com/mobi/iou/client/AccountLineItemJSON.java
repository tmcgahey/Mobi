package com.mobi.iou.client;

import java.util.Date;

import com.google.gwt.core.client.JavaScriptObject;

public class AccountLineItemJSON extends JavaScriptObject {
	
	protected AccountLineItemJSON() {}
	
	public final native String getName() /*-{ return this.name; }-*/;
	public final native String getDescription() /*-{ return this.description; }-*/;
	public final native double getAmount() /*-{ return this.amount; }-*/;
	public final native Date getDate() /*-{ return this.date; }-*/;
	
	public final native void setName(String name) /*-{ this.name = name; }-*/;
	public final native void setDescription(String description) /*-{ this.description = description; }-*/;
	public final native void setAmount(double amount) /*-{ this.amount = amount; }-*/;
	public final native void setDate(Date date) /*-{ this.date = date; }-*/;
}
