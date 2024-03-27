SUMMARY = "Eclipse Temurin 25"
HOMEPAGE = "https://github.com/adoptium/jdk"

DV_PLUS = "${@'+'.join(d.getVar('PV').rsplit('.', 1))}"
DV_UNDER = "${@'_'.join(d.getVar('PV').rsplit('.', 1))}"

SRC_URI = ""
BASE = "jdk-${DV_PLUS}"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[aarch64.md5sum]
SRC_URI:aarch64 = "https://github.com/adoptium/temurin25-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK25U-jdk_aarch64_linux_hotspot_${DV_UNDER}.tar.gz;name=aarch64"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[x86-64.md5sum]
SRC_URI:x86-64 = "https://github.com/adoptium/temurin25-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK25U-jdk_x64_linux_hotspot_${DV_UNDER}.tar.gz;name=x86-64"

# you can find checksum here: https://github.com/adoptium/temurin25-binaries/releases since devtool upgrade can only do one arch atm.
SRC_URI[x86-64.sha256sum] = "ee04de95ab9da7287d40bd2173076ecc2a6dd662f007bedfc6eb0380c0ef90e8"
SRC_URI[aarch64.sha256sum] = "95716d04bdfc8b10c94f4448ea8d57a3ba872d98b53c752e4c6b48f1c95bc582"

# also available in master (not kirkstone) in classes-recipe: github-releases
UPSTREAM_CHECK_REGEX ?= "releases/tag/jdk-?(?P<pver>\d+(\.\d+)+)"

UPSTREAM_CHECK_URI = "https://github.com/adoptium/jdk/tags"

ALTERNATIVE_PRIORITY = "60"

# nooelint: oelint.file.underscores
require temurin-common.inc

# nooelint: oelint.vars.insaneskip:INSANE_SKIP
INSANE_SKIP:${PN} += "ldflags"

RPROVIDES:${PN} = "temurin-jdk"
PROVIDES:append = "virtual/java"

inherit update-alternatives ptest