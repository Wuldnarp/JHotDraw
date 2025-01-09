package org.jhotdraw.samples.svg.figures.steps;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.samples.svg.figures.SVGTextAreaFigure;

public class GivenText extends Stage<GivenText> {

    @ProvidedScenarioState
    SVGTextAreaFigure textAreaFigure;

    public GivenText SomeText() {
        textAreaFigure = new SVGTextAreaFigure("text example");
        return this;
    }
}
