SRC_URI = " \
    file://switch-rootfs.in \
    file://verify-slot \
    file://abort-blupdate \
"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit mender-state-scripts

PERSIST_MACHINE_ID = ""
PERSIST_MACHINE_ID:mender-persist-systemd-machine-id = "yes"

do_compile() {
    sed -e 's#@@TEGRA_MENDER_INSTALL_ONLY_IF_DIFFERENT@@#'"${TEGRA_MENDER_INSTALL_ONLY_IF_DIFFERENT}"'#' \
        -e 's#@@TEGRA_MENDER_ALLOW_FIRMWARE_VERSION_MISSMATCH@@#'"${TEGRA_MENDER_ALLOW_FIRMWARE_VERSION_MISSMATCH}"'#' \
        "${UNPACKDIR}/switch-rootfs.in" > "${UNPACKDIR}/switch-rootfs"

    cp ${UNPACKDIR}/switch-rootfs ${MENDER_STATE_SCRIPTS_DIR}/ArtifactInstall_Leave_50_switch-rootfs
    cp ${UNPACKDIR}/verify-slot ${MENDER_STATE_SCRIPTS_DIR}/ArtifactCommit_Leave_50_verify-slot
    cp ${UNPACKDIR}/abort-blupdate ${MENDER_STATE_SCRIPTS_DIR}/ArtifactRollback_Leave_50_abort-blupdate
}

# Make sure scripts aren't left around from old builds
do_deploy:prepend() {
    rm -rf ${DEPLOYDIR}/mender-state-scripts
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
