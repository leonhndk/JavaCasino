package de.esg.java.ausbildung.honl.game;

import com.github.weisj.jsvg.SVGDocument;
import com.github.weisj.jsvg.attributes.ViewBox;

import javax.swing.*;
import java.awt.*;

/**
 * An Icon that holds an SVG blueprint and renders it dynamically to fit the
 * component it is painted on. This allows for perfect, high-quality scaling.
 */
public class ScalingSuitIcon implements Icon {

    private final SVGDocument svgDocument;

    public ScalingSuitIcon(SVGDocument svgDocument) {
        this.svgDocument = svgDocument;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (svgDocument == null) {
            return;
        }
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // null as first argument, create viewBox based on component's size.
        svgDocument.render(null, g2d, new ViewBox(0, 0, c.getWidth(), c.getHeight()));
        g2d.dispose();
    }

    /**
     * Returns 0 as icon width (width not fixed, depends on component size)
     * same for icon height.
     * @return 0
     */
    @Override
    public int getIconWidth() {
        return 0;
    }

    @Override
    public int getIconHeight() {
        return 0;
    }
}
