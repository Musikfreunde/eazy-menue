package htl.diplomarbeit.menuebestellung.api

object ApiObject {
    @JvmField
    var initMenuesUrl = "http://10.0.2.2:8080/menue/menues/"

    @JvmField
    var bestellungUrl = "http://10.0.2.2:8080/menue/bestellung/"

    @JvmField
    var oeffnungszeiten = "http://10.0.2.2:8080/menue/oeffnungszeiten"

    @JvmField
    var keycloak = "http://10.0.2.2:8082/auth/realms/menuRealm/protocol/openid-connect/token"
}