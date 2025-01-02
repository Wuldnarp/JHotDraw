package org.jhotdraw.samples.svg.figures;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.jhotdraw.samples.svg.figures.steps.GivenText;
import org.jhotdraw.samples.svg.figures.steps.Then;
import org.jhotdraw.samples.svg.figures.steps.WhenEditing;
import org.junit.Test;

public class SVGTextAreaFigureTest extends ScenarioTest<GivenText, WhenEditing, Then> {


    @Test
    public void changeFontTest(){
        given().SomeText();
        when().ChangingTheFont();
        then().TheFontShouldChange();
    }

    @Test
    public void increaseFontSizeTest(){
        given().SomeText();
        when().IncreasingTheFont();
        then().TheFontSizeShouldIncrease();
    }

    @Test
    public void changeFontToBoldTest(){
        given().SomeText();
        when().ActivatingBold();
        then().TheFontIsBold();
    }

    @Test
    public void changeFontToItalicTest(){
        given().SomeText();
        when().ActivatingItalic();
        then().TheFontIsItalic();
    }

    @Test
    public void changeFontToUnderlineTest(){
        given().SomeText();
        when().ActivatingUnderline();
        then().TheFontIsUnderline();
    }
}
