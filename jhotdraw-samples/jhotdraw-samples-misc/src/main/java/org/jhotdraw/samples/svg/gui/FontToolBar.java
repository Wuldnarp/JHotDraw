/*
 * @(#)StrokeToolBar.java
 *
 * Copyright (c) 2007-2008 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.samples.svg.gui;

import org.jhotdraw.gui.action.ButtonFactory;
import org.jhotdraw.gui.plaf.palette.PaletteButtonUI;
import org.jhotdraw.gui.plaf.palette.PaletteFormattedTextFieldUI;
import org.jhotdraw.gui.plaf.palette.PaletteSliderUI;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.SliderUI;
import static org.jhotdraw.draw.AttributeKeys.FONT_FACE;
import static org.jhotdraw.draw.AttributeKeys.FONT_SIZE;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.draw.event.FigureAttributeEditorHandler;
import org.jhotdraw.draw.event.SelectionComponentDisplayer;
import org.jhotdraw.draw.event.SelectionComponentRepainter;
import org.jhotdraw.draw.gui.JAttributeSlider;
import org.jhotdraw.draw.gui.JAttributeTextField;
import org.jhotdraw.draw.tool.TextAreaCreationTool;
import org.jhotdraw.draw.tool.TextCreationTool;
import org.jhotdraw.gui.JFontChooser;
import org.jhotdraw.gui.JPopupButton;
import org.jhotdraw.formatter.FontFormatter;
import org.jhotdraw.formatter.JavaNumberFormatter;
import org.jhotdraw.util.*;

/**
 * StrokeToolBar.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class FontToolBar extends AbstractToolBar {

    private static final long serialVersionUID = 1L;
    private SelectionComponentDisplayer displayer;

    /**
     * Creates new instance.
     */
    public FontToolBar() {
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        setName(labels.getString("font.toolbar"));
        JFontChooser.loadAllFonts();
        setDisclosureStateCount(3);
    }

    @Override
    public void setEditor(DrawingEditor newValue) {
        if (displayer != null) {
            displayer.dispose();
            displayer = null;
        }
        super.setEditor(newValue);
        if (newValue != null) {
            displayer = new SelectionComponentDisplayer(editor, this) {
                @Override
                public void updateVisibility() {
                    boolean newValue = editor != null
                            && editor.getActiveView() != null
                            && (isVisibleIfCreationTool && ((editor.getTool() instanceof TextCreationTool) || editor.getTool() instanceof TextAreaCreationTool)
                            || containsTextHolderFigure(editor.getActiveView().getSelectedFigures()));
                    JComponent component = getComponent();
                    if (component == null) {
                        dispose();
                        return;
                    }
                    component.setVisible(newValue);
                    // The following is needed to trick BoxLayout
                    if (newValue) {
                        component.setPreferredSize(null);
                    } else {
                        component.setPreferredSize(new Dimension(0, 0));
                    }
                    component.revalidate();
                }

                private boolean containsTextHolderFigure(Collection<Figure> figures) {
                    for (Figure f : figures) {
                        if (f instanceof TextHolderFigure || (f instanceof CompositeFigure && containsTextHolderFigure(((CompositeFigure) f).getChildren()))) {
                            return true;
                        }
                    }
                    return false;
                }
            };
        }
    }

    @Override
    protected JComponent createDisclosedComponent(int state) {
        JPanel p = null;
        int faceFieldColumnsNumber = 0;
        int gridwidthNumber = 0;
        p = new JPanel();
        JPanel p1 = new JPanel(new GridBagLayout());
        JPanel p2 = new JPanel(new GridBagLayout());
        JPanel p3 = new JPanel(new GridBagLayout());
        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.samples.svg.Labels");
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc;
        AbstractButton btn;
        String clientPropertyKay = "Palette.Component.segmentPosition";
        String clientPropertyValue = "first";


        if (state == 1) {
            faceFieldColumnsNumber = 2;
            gridwidthNumber = 2;
        } else if (state == 2) {
            faceFieldColumnsNumber = 12;
            gridwidthNumber = 3;
        }

        p.setOpaque(false);
        p.setBorder(new EmptyBorder(5, 5, 5, 8));
        p1.setOpaque(false);
        p2.setOpaque(false);
        p3.setOpaque(false);
        p.setLayout(layout);

        // Font face field and popup button
        JAttributeTextField<Font> faceField = new JAttributeTextField<Font>();
        faceField.setColumns(faceFieldColumnsNumber);
        faceField.setToolTipText(labels.getString("attribute.font.toolTipText"));
        faceField.setHorizontalAlignment(JAttributeTextField.RIGHT);
        faceField.putClientProperty(clientPropertyKay, clientPropertyValue);
        faceField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(faceField));
        faceField.setHorizontalAlignment(JTextField.LEADING);
        faceField.setFormatterFactory(FontFormatter.createFormatterFactory());
        disposables.add(new FigureAttributeEditorHandler<Font>(FONT_FACE, faceField, editor));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridwidth = gridwidthNumber;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p.add(faceField, gbc);
        btn = ButtonFactory.createFontButton(editor, FONT_FACE, labels, disposables);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        p.add(btn, gbc);

        // Font size field with slider
        JAttributeTextField<Double> sizeField = new JAttributeTextField<Double>();
        sizeField.setColumns(1);
        sizeField.setToolTipText(labels.getString("attribute.fontSize.toolTipText"));
        sizeField.setHorizontalAlignment(JAttributeTextField.RIGHT);
        sizeField.putClientProperty(clientPropertyKay, clientPropertyValue);
        sizeField.setUI((PaletteFormattedTextFieldUI) PaletteFormattedTextFieldUI.createUI(sizeField));
        sizeField.setFormatterFactory(JavaNumberFormatter.createFormatterFactory(0d, 1000d, 1d));
        sizeField.setHorizontalAlignment(JTextField.LEADING);
        disposables.add(new FigureAttributeEditorHandler<Double>(FONT_SIZE, sizeField, editor));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(3, 0, 0, 0);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridwidth = 2;
        gbc.weightx = 1f;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p2.add(sizeField, gbc);
        JPopupButton sizePopupButton = new JPopupButton();
        JAttributeSlider sizeSlider = new JAttributeSlider(JSlider.VERTICAL, 0, 100, 12);
        sizePopupButton.add(sizeSlider);
        labels.configureToolBarButton(sizePopupButton, "attribute.fontSize");
        sizePopupButton.setUI((PaletteButtonUI) PaletteButtonUI.createUI(sizePopupButton));
        sizePopupButton.setPopupAnchor(SOUTH_EAST);
        disposables.add(new SelectionComponentRepainter(editor, sizePopupButton));
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(3, 0, 0, 0);
        p2.add(sizePopupButton, gbc);
        sizeSlider.setUI((SliderUI) PaletteSliderUI.createUI(sizeSlider));
        sizeSlider.setScaleFactor(1d);
        disposables.add(new FigureAttributeEditorHandler<Double>(FONT_SIZE, sizeSlider, editor));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(0, 0, 0, 0);
        p.add(p2, gbc);

        // Font style buttons
        btn = ButtonFactory.createFontStyleBoldButton(editor, labels);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.putClientProperty(clientPropertyKay, "first");
        gbc = new GridBagConstraints();
        gbc.gridy = 2;
        gbc.insets = new Insets(3, 0, 0, 0);
        p.add(btn, gbc);
        btn = ButtonFactory.createFontStyleItalicButton(editor, labels);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.putClientProperty(clientPropertyKay, "middle");
        gbc = new GridBagConstraints();
        gbc.gridy = 2;
        gbc.insets = new Insets(3, 0, 0, 0);
        p.add(btn, gbc);
        btn = ButtonFactory.createFontStyleUnderlineButton(editor, labels);
        btn.setUI((PaletteButtonUI) PaletteButtonUI.createUI(btn));
        btn.putClientProperty(clientPropertyKay, "last");
        gbc = new GridBagConstraints();
        gbc.gridy = 2;
        gbc.insets = new Insets(3, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        p.add(btn, gbc);

        return p;
    }

    @Override
    protected String getID() {
        return "font";
    }

    @Override
    protected int getDefaultDisclosureState() {
        return 1;
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
