FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://grub.cfg \
    "
do_install_class-native() {
    install -d ${D}${bindir}
    install -m 755 grub-mkimage ${D}${bindir}
    install -m 755 grub-editenv ${D}${bindir}
}

do_deploy() {

    grub-mkimage -c ../cfg -p /EFI/BOOT -d ./grub-core/ \
                   -O ${GRUB_TARGET}-efi -o ./${GRUB_IMAGE} \
                   ${GRUB_BUILDIN}
    install -m 644 ${B}/${GRUB_IMAGE} ${DEPLOYDIR}

    # create empty environment file
    # installation into EFI dir is made using wic .wks file, through
    # IMAGE_BOOT_FILES variable
    grub-editenv grubenv create
    install -m 644 ${B}/grubenv ${DEPLOYDIR}

    # provide custom grub config
    install -m 644 ${WORKDIR}/grub.cfg ${DEPLOYDIR}
}
