package org.jhotdraw.samples.svg.figures.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.samples.svg.SVGAttributeKeys;
import org.jhotdraw.samples.svg.figures.SVGTextAreaFigure;

import java.awt.*;

public class WhenEditing extends Stage<WhenEditing> {

    @ExpectedScenarioState
    @ProvidedScenarioState
    public SVGTextAreaFigure textAreaFigure;


    public WhenEditing ChangingTheFont(){
        textAreaFigure.set(SVGAttributeKeys.FONT_FACE,new Font("Arial",Font.PLAIN,12));
        return this;
    }

    public WhenEditing IncreasingTheFont(){
        textAreaFigure.setFontSize(30);
        return this;
    }

    public WhenEditing ActivatingBold(){
        textAreaFigure.set(SVGAttributeKeys.FONT_BOLD,true);
        return this;
    }

    public WhenEditing ActivatingItalic(){
        textAreaFigure.set(SVGAttributeKeys.FONT_ITALIC,true);
        return this;
    }
    public WhenEditing ActivatingUnderline(){
        textAreaFigure.set(SVGAttributeKeys.FONT_UNDERLINE,true);
        return this;
    }

}
