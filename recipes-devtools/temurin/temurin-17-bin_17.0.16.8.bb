SUMMARY = "Eclipse Temurin 17"
HOMEPAGE = "https://github.com/adoptium/jdk17u"

DV_PLUS = "${@'+'.join(d.getVar('PV').rsplit('.', 1))}"
DV_UNDER = "${@'_'.join(d.getVar('PV').rsplit('.', 1))}"

SRC_URI = ""
BASE = "jdk-${DV_PLUS}"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[aarch64.md5sum]
SRC_URI:aarch64 = "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK17U-jdk_aarch64_linux_hotspot_${DV_UNDER}.tar.gz;name=aarch64"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[arm.md5sum]
SRC_URI:arm = "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK17U-jdk_arm_linux_hotspot_${DV_UNDER}.tar.gz;name=arm"

# nooelint: oelint.vars.srcurichecksum:SRC_URI[x86-64.md5sum]
SRC_URI:x86-64 = "https://github.com/adoptium/temurin17-binaries/releases/download/jdk-${DV_PLUS}/OpenJDK17U-jdk_x64_linux_hotspot_${DV_UNDER}.tar.gz;name=x86-64"

# you can find checksum here: https://github.com/adoptium/temurin17-binaries/releases since devtool upgrade can only do one arch atm.
SRC_URI[x86-64.sha256sum] = "166774efcf0f722f2ee18eba0039de2d685b350ee14d7b69e6f83437dafd2af1"
SRC_URI[arm.sha256sum] = "bc8ba665df25378cfca76b2d2ca6821ba32c4d45934aa5beea5b542d6658f5d6"
SRC_URI[aarch64.sha256sum] = "4b331711459e30ba7a64c8b383f4c0db9e699be42876ac6ae12aec3147dfe980"

COMPATIBLE_MACHINE:armv7a = "(.*)"
COMPATIBLE_MACHINE:armv7ve = "(.*)"

# also available in master (not kirkstone) in classes-recipe: github-releases
UPSTREAM_CHECK_REGEX ?= "releases/tag/jdk-?(?P<pver>\d+(\.\d+)+)"

UPSTREAM_CHECK_URI = "https://github.com/adoptium/temurin17-binaries/tags"

ALTERNATIVE_PRIORITY = "60"

# nooelint: oelint.file.underscores
require temurin-common.inc

# nooelint: oelint.vars.insaneskip:INSANE_SKIP
INSANE_SKIP:${PN} += "ldflags"

RPROVIDES:${PN} = "temurin-jdk"
PROVIDES:append = "virtual/java"

inherit update-alternatives ptest