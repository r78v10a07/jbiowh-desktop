package org.jbiowhdesktop.component.icons;

import javax.swing.ImageIcon;

/**
 * This Class is hanlded the icon on the Close Tab
 *
 * $Author: r78v10a07@gmail.com $
 * $LastChangedDate: 2012-08-31 15:46:36 +0200 (Fri, 31 Aug 2012) $
 * $LastChangedRevision: 231 $
 * @since Aug 16, 2012
 */
public class IconFetch {

    private static IconFetch instance;

    private IconFetch() {
    }

    /**
     * Return a IconFetch instance
     *
     * @return a IconFetch object
     */
    public static synchronized IconFetch getInstance() {
        if (instance == null) {
            instance = new IconFetch();
        }
        return instance;
    }

    /**
     * Return the ImageIcon from its file name
     *
     * @param iconName the icon file name
     * @return the ImageIcon from its file name
     */
    public ImageIcon getIcon(String iconName) {
        java.net.URL imgUrl = getClass().getResource(iconName);
        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            throw new IllegalArgumentException("This icon file does not exist");
        }
    }
    /**
     * The close icon to be used on the tabs
     */
    public final String CLOSE_ICON = "/images/delete_edit.gif";
}
