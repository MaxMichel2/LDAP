include(":common")
include(":data-mock")
include(":domain")
include(":data")
//include(":presentation")
include(":presentation-compose") // Use this for a compose app
include(":app")
rootProject.name = "LDAP"

plugins {
    id("de.fayard.refreshVersions") version "0.40.2"
}