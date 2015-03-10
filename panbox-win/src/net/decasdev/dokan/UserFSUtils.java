package net.decasdev.dokan;

import java.io.File;
import java.lang.reflect.Field;

import org.apache.log4j.Logger;
import org.panbox.desktop.common.vfs.Files;


public final class UserFSUtils {

	private static final Logger logger = Logger.getLogger("org.panbox.desktop.common");
    public static final class DokanNotInstalledException extends Exception {

        private static final long serialVersionUID = -332027883155497034L;
    }

    public static final class DokanVersionException extends Exception {

        private static final long serialVersionUID = -3397141069355824035L;
    }

    public enum OperatingSystem {

        LINUX32, LINUX64, OSX32, WIN32;

        private File getLibraryPath() {
            switch (this) {
                case WIN32:
                    return Files.libDirectoryWindows;
                case OSX32:
                    return Files.libDirectoryOSX;
                case LINUX32:
                    return Files.libDirectoryLinux32;
                case LINUX64:
                    return Files.libDirectoryLinux64;
                default:
                    return null;
            }
        }

        public boolean isWindows() {
            return equals(WIN32);
        }
    }
    /**
     * Null for uninitialized, true for initialized successfully, false not
     * unsuccessful initialization
     */
    private static Boolean initStatus = null;
    private static OperatingSystem operatingSystem = null;

    private static void addPathToLibrary(final File path) {
        final String newLibPath = path.getAbsolutePath() + File.pathSeparator + System.getProperty("java.library.path");
        System.setProperty("java.library.path", newLibPath);
        Field fieldSysPath;
        try {
            fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            if (fieldSysPath != null) {
                fieldSysPath.set(System.class.getClassLoader(), null);
            }
        } catch (final Exception e) {
            // Oh well
        }
    }

    public static OperatingSystem getOperatingSystem() {
        if (operatingSystem != null) {
            return operatingSystem;
        }
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            operatingSystem = OperatingSystem.WIN32;
        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
            operatingSystem = OperatingSystem.OSX32;
        } else if (System.getProperty("os.arch").contains("64")) {
            operatingSystem = OperatingSystem.LINUX64;
        } else {
            operatingSystem = OperatingSystem.LINUX32;
        }
        return operatingSystem;
    }

    /**
     * Initialize the user filesystem library. Will throw an exception if things
     * go wrong.
     *
     * @throws DokanNotInstalledException When Dokan is not installed
     * @throws DokanVersionException When Dokan is installed but doesn't have
     * the right version number
     * @return True if successful
     */
    public static boolean init() throws DokanNotInstalledException, DokanVersionException {
        if (initStatus != null) {
            return initStatus;
        }
        final OperatingSystem os = getOperatingSystem();
        addPathToLibrary(os.getLibraryPath());
        if (os.isWindows()) {
            try {
                if (Dokan.getVersion() == 600) {
                	logger.debug("Dokan : UserFSUtils : Starting with version = " + Dokan.getVersion() + " / Driver = "
                            + Dokan.getDriverVersion() + ".");
                    initStatus = true;
                    return true;
                }
            	logger.debug("Dokan : UserFSUtils : Invalid Dokan version: " + Dokan.getVersion());
            } catch (final Throwable e) {
            	logger.debug("Dokan : UserFSUtils : Error caught while initializing Dokan" + e.getMessage());
                initStatus = false;
                throw new DokanNotInstalledException();
            }
            initStatus = false;
            throw new DokanVersionException();
        }
        initStatus = true;
        return true;
    }
}
