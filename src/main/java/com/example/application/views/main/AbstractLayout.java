package com.example.application.views.main;

import com.example.application.KeycloakSSOUserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import java.util.Arrays;
import java.util.Optional;

public class AbstractLayout extends Div implements RouterLayout {
    private final HorizontalLayout menuLayout = new HorizontalLayout();
    private final AuthenticationContext authenticationContext;
    private final KeycloakSSOUserService keycloakSSOUserService;

    public AbstractLayout(AuthenticationContext authenticationContext, KeycloakSSOUserService keycloakSSOUserService) {
        this.authenticationContext = authenticationContext;
        this.keycloakSSOUserService = keycloakSSOUserService;
        add(menuLayout);

        RouteConfiguration.forApplicationScope()
                .getAvailableRoutes()
                .forEach(routeData -> {
                    var routerLink = new RouterLink(
                            routeData.getNavigationTarget().getSimpleName(),
                            routeData.getNavigationTarget());
                    routerLink.setVisible(hasPermission(routeData));
                    menuLayout.add(routerLink);
                });

        menuLayout.setMargin(true);

        Button logout = new Button("Logout", event -> this.authenticationContext.logout());
        logout.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        menuLayout.add(logout);

    }

    private boolean hasPermission(RouteData routeData) {

        return (routeData.getNavigationTarget().isAnnotationPresent(PermitAll.class) ||
                routeData.getNavigationTarget().isAnnotationPresent(AnonymousAllowed.class)) ||
                Arrays.stream(routeData.getNavigationTarget().getAnnotation(RolesAllowed.class).value())
                        .anyMatch(this::matchRole);
    }

    private boolean matchRole(String role) {
        Optional<DefaultOidcUser> authenticatedUser = authenticationContext.getAuthenticatedUser(DefaultOidcUser.class);

        return authenticatedUser.map(user -> keycloakSSOUserService.matchRole(user, role)).orElse(false);
    }
}
