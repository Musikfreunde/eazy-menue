# eazy-menue

Commanf rür realm export (nur unter linux oder git-bash ausführbar): 
 ./standalone.sh -Djboss.socket.binding.port-offset=100 -Dkeycloak.migration.action=export -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.realmName=menuRealm -Dkeycloak.migration.usersExportStrategy=REALM_FILE -Dkeycloak.migration.file=eazymenu-realm.json
