package com.sismics.reader.core.util;

import java.io.File;

import com.sismics.reader.core.constant.ConfigType;
import com.sismics.reader.core.dao.jpa.ConfigDao;
import com.sismics.reader.core.model.jpa.Config;

/**
 * Utilities to gain access to the storage directories used by the application.
 * 
 * @author jtremeaux
 */
public class DirectoryUtil {

    /**
     * Returns the base data directory.
     * 
     * @return Base data directory
     */
    public static File getBaseDataDirectory() {
        ConfigDao configDao = new ConfigDao();
        Config baseDataDirConfig = configDao.getById(ConfigType.BASE_DATA_DIR);
        File baseDataDir = null;
        if (baseDataDirConfig != null) {
            // If the directory is specified in the configuration parameter, use this value
            baseDataDir = new File(baseDataDirConfig.getValue());
            if (!baseDataDir.isDirectory()) {
                baseDataDir.mkdirs();
            }
        } else {
            String webappRoot = getWebappRoot();
            if (webappRoot != null) {
                // If we are in a Jetty context, use the base of the Web directory
                baseDataDir = new File(webappRoot + File.separator + "sismicsreader");
                if (!baseDataDir.isDirectory()) {
                    baseDataDir.mkdir();
                }
            } else {
                // Or else (for unit testing), use a temporary directory
                baseDataDir = new File(System.getProperty("java.io.tmpdir"));
            }
        }
        
        return baseDataDir;
    }
    
    /**
     * Returns the favicons directory.
     * 
     * @return Favicons directory.
     */
    public static File getFaviconDirectory() {
        return getDataSubDirectory("favicon");
    }

    /**
     * Returns the lucene indexes directory.
     * 
     * @return Lucene indexes directory.
     */
    public static File getLuceneDirectory() {
        return getDataSubDirectory("lucene");
    }

    /**
     * Returns the themes directory.
     * 
     * @return Theme directory.
     */
    public static File getThemeDirectory() {
        String webappRoot = getWebappRoot();
        File themeDir = null;
        if (webappRoot != null) {
            themeDir = new File(webappRoot + File.separator + "stylesheets" + File.separator + "theme");
        } else {
            themeDir = new File(DirectoryUtil.class.getResource("/stylesheets/theme").getFile());
        }
        if (themeDir != null && themeDir.isDirectory()) {
            return themeDir;
        }
        return null;
    }

    /**
     * Return the webapp root.
     * 
     * @return Webapp root
     */
    private static String getWebappRoot() {
        return System.getProperty("webapp.root");
    }

    /**
     * Returns a subdirectory of the base data directory
     * 
     * @return Subdirectory
     */
    private static File getDataSubDirectory(String subdirectory) {
        File baseDataDir = getBaseDataDirectory();
        File faviconDirectory = new File(baseDataDir.getPath() + File.separator + subdirectory);
        if (!faviconDirectory.isDirectory()) {
            faviconDirectory.mkdirs();
        }
        return faviconDirectory;
    }
}
