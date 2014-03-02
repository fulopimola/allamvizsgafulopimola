package com.example.proba1;

import javax.servlet.annotation.WebServlet;


import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MyHierarchicalUI extends UI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3055620865303970254L;
	//ha ezt irjuk be, akkor ezt az oldalt adja meg http://localhost:8080/VaadinProba/proba
	@WebServlet(value = "/valami", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MyHierarchicalUI.class)
	public static class Servlet extends VaadinServlet {
	}
    protected void init(VaadinRequest request) {
        // The root of the component hierarchy
        VerticalLayout content = new VerticalLayout();
        content.setSizeFull(); // Use entire window
        setContent(content);   // Attach to the UI
        
        // Add some component
        content.addComponent(new Label("Hello!"));
        
        System.out.println("Hello");
        // Layout inside layout
        HorizontalLayout hor = new HorizontalLayout();
        hor.setSizeFull(); // Use all available space

        // Couple of horizontally laid out components
        Tree tree = new Tree("My Tree", TreeExample.createTreeContent());
        hor.addComponent(tree);

        Table table = new Table("My Table",
                TableExample.generateContent());
        table.setSizeFull();
        hor.addComponent(table);
        hor.setExpandRatio(table, 1); // Expand to fill

        content.addComponent(hor);
        content.setExpandRatio(hor, 1); // Expand to fill
    }
}