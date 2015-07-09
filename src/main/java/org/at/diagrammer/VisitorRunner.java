package org.at.diagrammer;

import org.at.diagrammer.generator.Visitor;

/**
 * Created by otarasenko on 7/8/15.
 */
public class VisitorRunner {

    private static final String MULTIPLE_IF_STATEMENT = "if (x > w) { x = firstVar; x = secondVar;} else { x = thirdVar;}  x = common;";
    private static final String MULTIPLE_ELSE_STATEMENT = "if (x > w) { x = firstVar;} else { x = secondVar; x = thirdVar;}  x = common;";
    private static final String IF_ELSE_STATEMENT = "if (x > w) { x = firstVar;} else { x = secondVar;}  x = common;";

    public static final String MULTIPLE_IF_BRANCHING = "if (x > w) " +
                                                        "{ if (x > condition2)" +
                                                            "{ x = firstVar; }" +
                                                           "else { x = secondVar; } }  " +
                                                        "else {x = thirdVar;} " +
                                                        "x = common;";
    public static final String MULTIPLE_ELSE_BRANCHING = "if (x > w) { x = firstVar; } else {if (x > condition2) { x = secondVar;} else {x = thirdVar; y = fourthVar;}} x = common;";
    private static EmbeddedTranslator translator;

    public static void main(String [] args) {

        Lexer lexer = new Lexer(MULTIPLE_IF_BRANCHING);
        translator = new EmbeddedTranslator();
        Parser parser = new Parser(lexer, translator);
        parser.flow();

        Visitor visitor = new Visitor();
        visitor.run(translator.getStartNode());
    }

}
