package com.example.application.views.main;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
@AnonymousAllowed
@Route(value ="about")
public class AboutView extends VerticalLayout {

    public AboutView() {
        add(new Span("About View"));
    }
}
