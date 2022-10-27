package com.example.application.views.main;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.sso.starter.AuthenticationContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.stream.Streams;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return authenticationContext.getAuthenticatedUser().isPresent() && authenticationContext.getAuthenticatedUser().get().getAuthorities()
//                extractRoles(authenticationContext)
                .stream()
                .filter(grantedAuthority -> StringUtils.startsWith(grantedAuthority.toString(), AUTHORITY_PREFIX))
                .map(grantedAuthority -> StringUtils.replace(grantedAuthority.toString(), AUTHORITY_PREFIX, ""))
                .anyMatch(grantedRole -> StringUtils.equals(grantedRole, role));
    }

//    private Collection<? extends GrantedAuthority> extractRoles(AuthenticationContext authenticationContext) {
//        var list = (Collection<String>) authenticationContext.getAuthenticatedUser().get()
//                .getUserInfo()
//                .getClaimAsMap("realm_access")
//                .get("roles");
//
//        return list.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
//    }
}
