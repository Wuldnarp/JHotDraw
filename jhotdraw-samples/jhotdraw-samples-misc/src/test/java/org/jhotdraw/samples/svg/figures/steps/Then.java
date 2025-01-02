package org.jhotdraw.samples.svg.figures.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.samples.svg.SVGAttributeKeys;
import org.jhotdraw.samples.svg.figures.SVGTextAreaFigure;
import org.junit.Assert;

import java.awt.*;

public class Then extends Stage<Then> {

    @ExpectedScenarioState
    public SVGTextAreaFigure textAreaFigure;

    public Then TheFontShouldChange() {
        Assert.assertEquals(new Font("Arial",Font.PLAIN,12), textAreaFigure.getFont());
        return this;
    }

    public Then TheFontSizeShouldIncrease() {
        Assert.assertEquals(30, textAreaFigure.getFont().getSize());
        return this;
    }

    public Then TheFontIsBold(){
        Assert.assertTrue(textAreaFigure.getFont().isBold());
        return this;
    }

    public Then TheFontIsItalic(){
        Assert.assertTrue(textAreaFigure.getFont().isItalic());
        return this;
    }

    public Then TheFontIsUnderline(){
        Assert.assertTrue((Boolean) textAreaFigure.getAttributes().get(AttributeKeys.FONT_UNDERLINE));
        return this;
    }
}
