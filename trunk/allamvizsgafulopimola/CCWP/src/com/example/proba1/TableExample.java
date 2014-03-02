package com.example.proba1;

import com.vaadin.data.Container;
import com.vaadin.data.util.IndexedContainer;

public class TableExample {

	public static Container generateContent() {
		// TODO Auto-generated method stub
		// Create a container
		Container container = new IndexedContainer();
		 
		// Define the properties (columns) if required by container
		container.addContainerProperty("name", String.class, "none");
		container.addContainerProperty("volume", Double.class, 0.0);
		return container;
	}

}
