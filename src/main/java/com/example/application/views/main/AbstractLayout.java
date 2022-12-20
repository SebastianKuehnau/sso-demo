package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.Arrays;
import java.util.Collection;

public class AbstractLayout extends Div implements RouterLayout {

    public static final String AUTHORITY_PREFIX = "ROLE_";
    private final HorizontalLayout menuLayout = new HorizontalLayout();
    private final AuthenticationContext authenticationContext;

    public AbstractLayout(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
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

        return routeData.getNavigationTarget().isAnnotationPresent(PermitAll.class) ||
                Arrays.stream(routeData.getNavigationTarget().getAnnotation(RolesAllowed.class).value())
                        .anyMatch(this::matchRole);
    }

    private boolean matchRole(String role) {
        DefaultOidcUser defaultOidcUser =
                authenticationContext.getAuthenticatedUser(DefaultOidcUser.class).get();
        var grantedRoles = (Collection<String>) defaultOidcUser.getUserInfo()
                .getClaimAsMap("realm_access")
                .get("roles");
        return grantedRoles.stream()
                .anyMatch(grantedRole -> StringUtils.equals(grantedRole, role));
    }
}
