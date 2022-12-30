# SSO Demo

- needs a running and appropriated configured keycloak server (setup instruction will follow soon)
- currently the sso kit needs to include the [PR#48](https://github.com/vaadin/sso-kit/pull/48)
- Keycloak can be setup with following docker command:
  ` docker-compose -f ./keycloak-setup/docker-compose.yml up --build -d`
- Keycloak Management Console can be opened in your browser with localhost:9000 and the credential are "admin" for 
  username and "admin" as the password (this configuration shouldn't be used in production)
- the vaadin application has two different user types with the following credentials:
  - type: user ("myuser"/"myuser")
  - type: admin ("myadmin"/"myadmin")
  and can be started in the browser with localhost:8080