package com.example.application.views.main;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

@RolesAllowed({"admin"})
@Route(value = "admin", layout = AbstractLayout.class)
public class AdminView extends Div {

    public AdminView() {
        add(new Span("Admin"));
    }
}
