SUMMARY = "Eclipse Temurin 11"
HOMEPAGE = "https://github.com/adoptium/jdk11u"

DV_PLUS = "${@'+'.join(d.getVar('PV').rsplit('.', 1))}"
DV_UNDER = "${@'_'.join(d.getVar('PV').rsplit('.', 1))}"

SRC_URI = ""
BASE = "jdk-${DV_PLUS}"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[aarch64.md5sum]
SRC_URI:aarch64 = "https://github.com/adoptium/temurin11-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK11U-jdk_aarch64_linux_hotspot_${DV_UNDER}.tar.gz;name=aarch64"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[arm.md5sum]
SRC_URI:arm = "https://github.com/adoptium/temurin11-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK11U-jdk_arm_linux_hotspot_${DV_UNDER}.tar.gz;name=arm"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[x86-64.md5sum]
SRC_URI:x86-64 = "https://github.com/adoptium/temurin11-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK11U-jdk_x64_linux_hotspot_${DV_UNDER}.tar.gz;name=x86-64"

# you can find checksum here: https://github.com/temurin11-binaries/releases since devtool upgrade can only do one arch atm.
SRC_URI[x86-64.sha256sum] = "25cf602cac350ef36067560a4e8042919f3be973d419eac4d839e2e0000b2cc8"
SRC_URI[arm.sha256sum] = "7d0e71d8aea6bba27dfbb9ad7434066896c3085327e58776ca89d5e262040bc5"
SRC_URI[aarch64.sha256sum] = "ca1dc604352e9b3906b8955a700745a0a650ed59947f7f354af597871876a716"

COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"

# also available in master (not kirkstone) in classes-recipe: github-releases
UPSTREAM_CHECK_REGEX ?= "releases/tag/jdk-?(?P<pver>\d+(\.\d+)+)"

UPSTREAM_CHECK_URI = "https://github.com/adoptium/temurin11-binaries/tags"

ALTERNATIVE_PRIORITY = "60"

# nooelint: oelint.file.underscores
require temurin-common.inc

# nooelint: oelint.vars.insaneskip:INSANE_SKIP
INSANE_SKIP:${PN} += "ldflags"

RPROVIDES:${PN} = "temurin-jdk"
PROVIDES:append = "virtual/java"

inherit update-alternatives ptest