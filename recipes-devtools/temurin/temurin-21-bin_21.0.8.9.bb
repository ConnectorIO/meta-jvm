SUMMARY = "Eclipse Temurin 21"
HOMEPAGE = "https://github.com/adoptium/jdk21u"

DV_PLUS = "${@'+'.join(d.getVar('PV').rsplit('.', 1))}"
DV_UNDER = "${@'_'.join(d.getVar('PV').rsplit('.', 1))}"

SRC_URI = ""
BASE = "jdk-${DV_PLUS}"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[aarch64.md5sum]
SRC_URI:aarch64 = "https://github.com/adoptium/temurin21-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK21U-jdk_aarch64_linux_hotspot_${DV_UNDER}.tar.gz;name=aarch64"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[x86-64.md5sum]
SRC_URI:x86-64 = "https://github.com/adoptium/temurin21-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK21U-jdk_x64_linux_hotspot_${DV_UNDER}.tar.gz;name=x86-64"

# you can find checksum here: https://github.com/adoptium/temurin21-binaries/releases since devtool upgrade can only do one arch atm.
SRC_URI[x86-64.sha256sum] = "f2dc5418092c43003db8f9005c4a286e1c0104fea96ccdd49e8ebd037cac9219"
SRC_URI[aarch64.sha256sum] = "e5c41a1ab0865ea5de9b4529bf8526005f1d4593090845387d14fe450ce39c33"

# also available in master (not kirkstone) in classes-recipe: github-releases
UPSTREAM_CHECK_REGEX ?= "releases/tag/jdk-?(?P<pver>\d+(\.\d+)+)"

UPSTREAM_CHECK_URI = "https://github.com/adoptium/temurin21-binaries/tags"

ALTERNATIVE_PRIORITY = "60"

# nooelint: oelint.file.underscores
require temurin-common.inc

# nooelint: oelint.vars.insaneskip:INSANE_SKIP
INSANE_SKIP:${PN} += "ldflags"

RPROVIDES:${PN} = "temurin-jdk"
PROVIDES:append = "virtual/java"

inherit update-alternatives ptest