LICENSE = "GPL-2.0-with-classpath-exception"
LIC_FILES_CHKSUM = "file://legal/java.base/LICENSE;md5=3e0b59f8fac05c3c03d4a26bbda13f8f"

CVE_PRODUCT = "temurin"

COMPATIBLE_MACHINE = "(^$)"
COMPATIBLE_MACHINE:aarch64 = "(.*)"
COMPATIBLE_MACHINE:x86-64 = "(.*)"

# also available in master (not kirkstone) in classes-recipe: github-releases
UPSTREAM_CHECK_REGEX ?= "releases/tag/v?(?P<pver>\d+(\.\d+)+)"

SRC_URI:append = " file://run-ptest"

JAVA_VERSION = "${@d.getVar('PV').split('.')[0]}"
S = "${WORKDIR}/${BASE}"

FILES:${PN} += "\
    /usr/lib/jvm/java-${JAVA_VERSION}-temurin/ \
    "
do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    # temurin
    install -d ${D}${bindir}
    install -d ${D}${libdir}/jvm/java-${JAVA_VERSION}-temurin
    cp -r ${S}/* ${D}${libdir}/jvm/java-${JAVA_VERSION}-temurin

    for alt_name in ${ALTERNATIVE_NAMES}; do
        # ln -sf ${datadir}/zulu-${PV}_${PV_UPDATE}/bin/java ${D}${bindir}/java
        ln -srf ${D}${libdir}/jvm/java-${JAVA_VERSION}-temurin/bin/${alt_name} ${D}${bindir}/${alt_name}
    done
}

do_install:append:x86-64() {
    # create symbolic link /lib64/ld-linux-x86-64.so.2 to enable
    # loading the binary When maintainers build binaries on ubuntu,
    # this is the library they are linking to, and if we don't set it
    # up this way on the device, the program will not run.
    install -d ${D}/lib64
    cd ${D}/lib64
    ln -s ../lib/ld-linux-x86-64.so.2 ld-linux-x86-64.so.2
}

FILES:${PN} += "/lib64"

# nooelint: oelint.vars.insaneskip:INSANE_SKIP
INSANE_SKIP:${PN} += "file-rdeps"
INSANE_SKIP:${PN} += "libdir"

RDEPENDS:${PN} += "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'alsa', 'alsa-lib', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'libx11 libxext libxi libxrender libxtst ', '', d)} \
    zlib \
"

do_install:append() {
    if ${@bb.utils.contains('DISTRO_FEATURES','x11','false','true',d)}; then
        rm ${D}${libdir}/${SHR}/lib/libawt_xawt.so
        rm ${D}${libdir}/${SHR}/lib/libjawt.so
        rm ${D}${libdir}/${SHR}/lib/libsplashscreen.so
    fi
    if ${@bb.utils.contains('DISTRO_FEATURES','alsa','false','true',d)}; then
        rm ${D}${libdir}/${SHR}/lib/libjsound.so
    fi
}

#inherit update-alternatives

ALTERNATIVE_NAMES ?= "\
    jar \
    jarsigner \
    java \
    javac \
    javadoc \
    javap \
    jcmd \
    jconsole \
    jdb \
    jdeprscan \
    jdeps \
    jfr \
    jhsdb \
    jimage \
    jinfo \
    jlink \
    jmap \
    jmod \
    jps \
    jrunscript \
    jshell \
    jstack \
    jstat \
    jstatd \
    serialver \
"

python do_package:prepend () {
    prio = d.getVar('ALTERNATIVE_PRIORITY')
    alt_names = d.getVar('ALTERNATIVE_NAMES')

    for alt_name in alt_names.split():
        d.appendVar("ALTERNATIVE_PRIORITY_VARDEPS", ' ' + alt_name + ':' + prio)
}


ALTERNATIVE:${PN} = "${ALTERNATIVE_NAMES}"