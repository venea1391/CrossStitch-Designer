import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.*;

public class CanvasPanelHost extends JPanel implements Scrollable {
    private static final long serialVersionUID = 1L;

    private final JPanel panel;

    public CanvasPanelHost(JPanel p) {
        super(new BorderLayout());
        this.panel = p;
        add(panel);
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        Dimension preferredSize = panel.getPreferredSize();
        if (getParent() instanceof JViewport) {
            preferredSize.width += ((JScrollPane) getParent().getParent()).getVerticalScrollBar()
                    .getPreferredSize().width;
        }
        return preferredSize;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return orientation == SwingConstants.HORIZONTAL ? Math.max(visibleRect.width * 9 / 10, 1)
                : Math.max(visibleRect.height * 9 / 10, 1);
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        if (getParent() instanceof JViewport) {
            JViewport viewport = (JViewport) getParent();
            return getPreferredSize().height < viewport.getHeight();
        }
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return orientation == SwingConstants.HORIZONTAL ? Math.max(visibleRect.width / 10, 1)
                : Math.max(visibleRect.height / 10, 1);
    }

}