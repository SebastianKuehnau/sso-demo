package com.example.application.views.main;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route("hello-world")
public class HelloWorldView extends VerticalLayout {

        public HelloWorldView() {
            add(new Span("Hello world"));
        }
}
