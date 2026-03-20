package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.types.ExpressionType;

public final class ErrorMessages {

    private ErrorMessages() {}

    public static String variableNotDefined(String name) {
        return "Variable \"" + name + "\" is not defined";
    }

    public static String invalidIfCondition(String expected) {
        return "The condition of an if clause must be of type \"" + expected + "\".";
    }

    public static String invalidProperty(String property) {
        return "Property \"" + property + "\" is not allowed.";
    }

    public static String wrongPropertyType(String property, ExpressionType expected, ExpressionType actual) {
        return "\"" + property + "\" expects \"" + expected + "\" but got \"" + actual + "\".";
    }

    public static String mixedTypes(ExpressionType left, ExpressionType right) {
        return "It's not permitted to mix different non-scalar types \"" + left + "\" and \"" + right + "\" in an operation.";
    }

    public static String undefinedVariableInOperation() {
        return "Using an undefined variable in an operation is not allowed.";
    }

    public static String invalidAddOperation() {
        return "Using a scalar value in an add operation is not permitted.";
    }

    public static String invalidMultiplyOperation() {
        return "Using 2 non-scalar literals is not permitted in an multiplicative operation.";
    }

    public static String invalidSizeType(String property, ExpressionType type) {
        return "Property \"" + property + "\" does not permit \"" + type + "\".";
    }
}
