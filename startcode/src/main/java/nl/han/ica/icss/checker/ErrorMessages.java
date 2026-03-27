package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.types.ExpressionType;

public final class ErrorMessages {

    private ErrorMessages() {
    }

    public static String variableNotDefined(String name) {
        return "Variable \"" + name + "\" is not defined";
    }

    public static String invalidIfCondition(String expected) {
        return "The condition of an if clause must be of type \"" + expected + "\".";
    }

    public static String invalidProperty(String property) {
        return "Property \"" + property + "\" is not allowed.";
    }

    public static String mixedTypes(ExpressionType left, ExpressionType right) {
        return "It's not permitted to mix different non-scalar types \"" + left + "\" and \"" + right + "\" in an operation.";
    }

    public static String undefinedVariableInOperation() {
        return "Using an undefined variable in an operation is not allowed.";
    }

    public static String usingOnlyScalarValuesInAddOrSubtractNotPermitted() {
        return "Using only scalar values in an add or subtract operation is not permitted.";
    }

    public static String invalidAddOrSubtractOperation() {
        return "Using a scalar value in an add or substract operation is not permitted.";
    }

    public static String invalidMultiplyOperation() {
        return "Using 2 non-scalar literals is not permitted in an multiplicative operation.";
    }

    public static String invalidPropertyDeclarationType(String property, ExpressionType type) {
        return "Property \"" + property + "\" does not permit \"" + type + "\".";
    }

    public static String caseDoesNotMatchConditionType(ExpressionType type, ExpressionType switchConditionType) {
        return "Case \"" + type + "\" does not match the type of the switch condition \"" + switchConditionType + "\".";
    }

    public static String colorNotAllowedInOperation() {
        return "Colors are not allowed to be used in an operation. (Add, Subtract, Multiply).";
    }

    public static String comparisonOperationDoesNotHaveTheSameTypes(String type1, String type2) {
        return "An comparison operation must have the same literal types on either side of the comparison \nExpected: 2x \""
                + type1 + "\" or 2x \"" + type2 + "\"\nReceived: \"" + type1 + "\" and \"" + type2 + "\".";
    }

    public static String unknownLiteralTypeOnComparisonOperation() {
        return "There is an unknown literal type on either side of the comparison operation.";
    }

    public static String colorComparisonNotAllowed() {
        return "Colors can only be compared to each other with \"==\" (Equal) or \"!=\" (Not Equal).";
    }
}
