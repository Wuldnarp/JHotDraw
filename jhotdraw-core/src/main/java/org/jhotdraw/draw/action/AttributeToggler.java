/*
 * @(#)AttributeToggler.java
 *
 * Copyright (c) 1996-2010 The authors and contributors of JHotDraw.
 * You may not use, copy or modify this file, except in compliance with the
 * accompanying license terms.
 */
package org.jhotdraw.draw.action;

import org.jhotdraw.draw.figure.Figure;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.undo.*;
import org.jhotdraw.draw.*;
import org.jhotdraw.util.ActionUtil;
import org.jhotdraw.util.ResourceBundleUtil;

/**
 * AttributeToggler toggles an attribute of the selected figures between two
 * different values.
 * If the name of a compatible JTextComponent action is specified, the toggler
 * checks if the current permant focus owner is a JTextComponent, and if it is,
 * it will apply the text action to the JTextComponent.
 *
 * @author Werner Randelshofer
 * @version $Id$
 */
public class AttributeToggler<T> extends AbstractAction {

    private static final long serialVersionUID = 1L;
    private DrawingEditor editor;
    private AttributeKey<T> key;
    private transient T value1;
    private transient T value2;
    private T toggleValue;

    /**
     * Creates a new instance.
     */

    public AttributeToggler(DrawingEditor editor, AttributeKey<T> key, T value1, T value2) {
        this.editor = editor;
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.toggleValue = this.value1;
    }

    public DrawingView getView() {
        return editor.getActiveView();
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Determine the new value
        Iterator<Figure> i = getView().getSelectedFigures().iterator();
        toggleValue = value1;
        if (i.hasNext()) {
            Figure f = i.next();
            Object attr = f.get(key);
            if (value1 == null && attr == null
                    || (value1 != null && attr != null && attr.equals(value1))) {
                toggleValue = value2;
            }
        }
        final T newValue = toggleValue;
        //--
        final ArrayList<Figure> selectedFigures = new ArrayList<>(getView().getSelectedFigures());
        final ArrayList<Object> restoreData = new ArrayList<>(selectedFigures.size());
        for (Figure figure : selectedFigures) {
            restoreData.add(figure.getAttributesRestoreData());
            figure.willChange();
            figure.set(key, newValue);
            figure.changed();
        }
        UndoableEdit edit = new AbstractUndoableEdit() {
            private static final long serialVersionUID = 1L;

            @Override
            public String getPresentationName() {
                String name = (String) getValue(ActionUtil.UNDO_PRESENTATION_NAME_KEY);
                if (name == null) {
                    name = (String) getValue(AbstractAction.NAME);
                }
                if (name == null) {
                    ResourceBundleUtil labels = ResourceBundleUtil.getBundle("org.jhotdraw.draw.Labels");
                    name = labels.getString("attribute.text");
                }
                return name;
            }

            @Override
            public void undo() {
                super.undo();
                Iterator<Object> iRestore = restoreData.iterator();
                for (Figure figure : selectedFigures) {
                    figure.willChange();
                    figure.restoreAttributesTo(iRestore.next());
                    figure.changed();
                }
            }

            @Override
            public void redo() {
                super.redo();
                for (Figure figure : selectedFigures) {
                    figure.willChange();
                    figure.set(key, newValue);
                    figure.changed();
                }
            }
        };
        getView().getDrawing().fireUndoableEditHappened(edit);
    }

    public T getToggleValue() {return toggleValue;}
}
