/*
 * JDokan : Java library for Dokan Copyright (C) 2008 Yu Kobayashi http://yukoba.accelart.jp/ http://decas-dev.net/en This
 * program is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the License, or (at your option) any later version. This
 * program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have
 * received a copy of the GNU Lesser General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.decasdev.dokan;

import org.apache.log4j.Logger;

public class Dokan {

	private static final Logger logger = Logger
			.getLogger("org.panbox.desktop.common");

	/**
	 * Bad Drive letter
	 */
	public static final int DOKAN_DRIVE_LETTER_ERROR = -2;
	/**
	 * Can't install driver
	 */
	public static final int DOKAN_DRIVER_INSTALL_ERROR = -3;
	/**
	 * General Error
	 */
	public static final int DOKAN_ERROR = -1;
	/**
	 * Can't assign a drive letter
	 */
	public static final int DOKAN_MOUNT_ERROR = -5;
	/**
	 * Driver something wrong
	 */
	public static final int DOKAN_START_ERROR = -4;
	// mount returns error codes
	public static final int DOKAN_SUCCESS = 0;

	static {
		try {
			System.loadLibrary("JDokan");
		} catch (UnsatisfiedLinkError ex) {
			// TODO: error handling
			logger.fatal("Dokan : !!!Static construction while loading native library!!! : Exception: "
					+ ex.getMessage());
		}
	}

	public static native int getDriverVersion();

	public static native int getVersion();

	/**
	 * Check whether Name can match Expression. Expression can contain wildcard
	 * characters (? and *)
	 */
	public static native boolean isNameInExpression(String expression,
			String name, boolean ignoreCase);

	// DokanMain
	public static native int mount(DokanOptions options,
			DokanOperations operations);

	public static native boolean removeMountPoint(String mountPoint);

	public static native boolean unmount(char driveLetter);

	private Dokan() {
	}

	public static String getErrorString(int result) {
		switch (result) {
		case Dokan.DOKAN_ERROR:
			return "General error occurred.";
		case Dokan.DOKAN_DRIVE_LETTER_ERROR:
			return "Bad drive letter.";
		case Dokan.DOKAN_DRIVER_INSTALL_ERROR:
			return "Can't install driver.";
		case Dokan.DOKAN_START_ERROR:
			return "Driver something wrong.";
		case Dokan.DOKAN_MOUNT_ERROR:
			return "Can't assign a drive letter.";
		default:
			return "Unknown error.";
		}
	}
}
