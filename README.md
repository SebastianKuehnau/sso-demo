# SSO Demo

- needs a running and appropriated configured keycloak server (setup instruction will follow soon)
- currently the sso kit needs to include the [PR#48](https://github.com/vaadin/sso-kit/pull/48)
- Keycloak can be setup with following docker command:
  `docker run --name keycloak_unoptimized -p 9000:8080 \
    -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin \
    -v ./src/main/resources/keycloak:/opt/keycloak/data/import \
    quay.io/keycloak/keycloak:latest \
    --import-realm`
