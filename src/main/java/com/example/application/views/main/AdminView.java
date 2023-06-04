package com.example.application.views.main;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;


@RolesAllowed({"admin"})

@Route(value = "admin", layout = AbstractLayout.class)
public class AdminView extends VerticalLayout {

    public AdminView() {
        add(new Span("Admin"));
    }
}
