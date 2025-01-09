package org.jhotdraw.draw.action;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.TextFigure;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.mockito.Mockito.*;

import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINE;

public class AttributeTogglerTest {

    private AttributeToggler instance;

    @Before
    public void setUp(){

        boolean value1 = false;
        boolean value2 = true;
        Set<Figure> figureSet = new HashSet<Figure>();
        figureSet.add(new TextFigure());

        //creating mocks
        DrawingEditor mockEditer = mock(DrawingEditor.class);
        DrawingView mockView = mock(DrawingView.class);
        //defining mock behaviour
        when(mockEditer.getActiveView()).thenReturn(mockView);
        when(mockView.getSelectedFigures()).thenReturn(figureSet);
        when(mockView.getDrawing()).thenReturn(new DefaultDrawing());

        instance = new AttributeToggler<>(mockEditer,
                FONT_UNDERLINE, value1, value2);
    }

    //Testing the toggle
    @Test
    public void testDefault(){
        //Assert start value
        Assert.assertFalse((Boolean) instance.getToggleValue());

        //Trigger toggle
        instance.actionPerformed(null);

        //Assert outcome
        Assert.assertTrue((Boolean) instance.getToggleValue());

        //Trigger toggle again
        instance.actionPerformed(null);

        //Assert outcome again
        Assert.assertFalse((Boolean) instance.getToggleValue());
    }
}
