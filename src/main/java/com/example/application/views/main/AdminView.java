package com.example.application.views.main;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import javax.annotation.security.RolesAllowed;


@RolesAllowed({"admin"})
@Route(value = "admin", layout = AbstractLayout.class)
public class AdminView extends VerticalLayout {

    public AdminView() {
        add(new Span("Admin"));
    }
}


