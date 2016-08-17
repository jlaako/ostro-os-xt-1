SUMMARY = "gstreamer codec parsers"
DESCRIPTION = "gstreamer codec parsers split out of gstreamer-plugins-bad"

DEPENDS = "gstreamer1.0"
RDEPENDS_${PN} = "gstreamer1.0"

LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=55ca817ccb7d5b5b66355690e9abc605"

SRC_URI = " \
	git://github.com/01org/gstreamer-codecparsers.git;protocol=https \
	file://codecparsers-disable-check.patch \
"
SRCREV = "88fa925c44876daec942820fd2eee4e4e0fc3305"

S = "${WORKDIR}/git"

inherit pkgconfig autotools
