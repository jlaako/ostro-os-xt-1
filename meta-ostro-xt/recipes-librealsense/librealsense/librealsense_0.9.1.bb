SUMMARY = "A cross-platform library for capturing data from the RealSense F200, SR300 and R200 cameras"
SECTION = "libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

DEPENDS = "libpng libusb1 libglu glfw"
RDEPENDS_${PN} = "bash"
RDEPENDS_${PN}-examples = "librealsense"
RDEPENDS_${PN}-graphical-examples = "libgl-mesa librealsense"

SRC_URI = "https://github.com/IntelRealSense/librealsense/archive/v${PV}.tar.gz"
SRC_URI[md5sum] = "f2f2bad22f3cffb8f1bb3f509a90f3fb"
SRC_URI[sha256sum] = "f6f45f5134834ccd1549ea9da897973ca8ac47d9a773604768458e6f3f6d2c0a"

PR = "r0"

inherit pkgconfig

S = "${WORKDIR}/${PN}-${PV}"

BACKEND ?= "V4L2"

EXTRA_OEMAKE = "\
    'BACKEND=${BACKEND}' \
    'CC=${CC}' \
    'CXX=${CXX}' \
    'CFLAGS=${CFLAGS} -std=c11 -fPIC -pedantic -DRS_USE_$(BACKEND)_BACKEND $(LIBUSB_FLAGS)' \
    'CXXFLAGS=${CXXFLAGS} -std=c++11 -fPIC -pedantic -mssse3 -O3 -Wno-missing-field-initializers -Wno-switch -Wno-multichar -DRS_USE_$(BACKEND)_BACKEND $(LIBUSB_FLAGS)' \
"

do_install () {
	install -d "${D}${bindir}"
	install -m 755 ${S}/bin/* ${D}${bindir}

	install -d "${D}${libdir}"
	install -m 0644 ${S}/lib/librealsense.so ${D}${libdir}

	install -d "${D}${includedir}/${PN}"
	install -m 0644 ${S}/include/${PN}/*.h ${D}${includedir}/${PN}
	install -m 0644 ${S}/include/${PN}/*.hpp ${D}${includedir}/${PN}

	install -d "${D}${sysconfdir}/udev/rules.d"
	install -m 0644 ${S}/config/99-realsense-libusb.rules ${D}${sysconfdir}/udev/rules.d
}

PACKAGES = "${PN} ${PN}-dbg ${PN}-dev ${PN}-examples ${PN}-graphical-examples"

FILES_${PN} = "${libdir}/* ${sysconfdir}/udev/rules.d/*"
FILES_${PN}-dev += "${includedir}/${PN}"
FILES_${PN}-examples += "\
	${bindir}/cpp-enumerate-devices \
	${bindir}/cpp-headless \
"
FILES_${PN}-graphical-examples += "\
	${bindir}/c-tutorial-1-depth \
	${bindir}/c-tutorial-3-pointcloud \
	${bindir}/c-tutorial-2-streams \
	${bindir}/cpp-alignimages \
	${bindir}/cpp-capture \
	${bindir}/cpp-config-ui \
	${bindir}/cpp-multicam \
	${bindir}/cpp-pointcloud \
	${bindir}/cpp-restart \
	${bindir}/cpp-tutorial-1-depth \
	${bindir}/cpp-tutorial-3-pointcloud \
	${bindir}/cpp-tutorial-2-streams \
"

# Configuration is not required
do_configure[noexec] = "1"
