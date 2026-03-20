package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseChecker {
    protected IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    protected final Map<String, ExpressionTypes> validProperties = new HashMap<>(Map.of(
            "color", ExpressionTypes.COLOR,
            "background-color", ExpressionTypes.COLOR,
            "width", ExpressionTypes.SIZE,
            "height", ExpressionTypes.SIZE
    ));

    protected void check(Declaration declaration) {
        ExpressionTypes expressionGroup = validProperties.get(declaration.property.name);

        if(expressionGroup == null) {
            declaration.setError(ErrorMessages.invalidProperty(declaration.property.name));
            return;
        }

        switch(expressionGroup) {
            case COLOR ->  validateColorProperty(declaration);
            case SIZE -> validateSizeDeclaration(declaration);
        }
    }

    // =================================================================================================================
    // VALIDATORS
    // =================================================================================================================

    protected void handleValidation(VariableAssignment variableAssignment) {
        ExpressionType type = null;

        switch (variableAssignment.expression) {
            case Literal l -> type = l.getType();
            case VariableReference vr -> type = getVariableTypeFromName(vr.name);
            default -> {}
        }

        if (type == null || type == ExpressionType.UNDEFINED) {
            variableAssignment.setError(ErrorMessages.variableNotDefined(variableAssignment.name.name));
            return;
        }

        variableTypes.get(0).put(variableAssignment.name.name, type);
    }

    protected void handleValidation(VariableReference variableReference) {
        addErrorIfVariableNotDefined(variableReference, variableReference.name);
    }

    // =================================================================================================================
    // VALIDATION HELPERS
    // =================================================================================================================

    protected boolean addErrorIfVariableNotDefined(ASTNode node, String variableName) {
        if (getVariableTypeFromName(variableName) == null) {
            node.setError(ErrorMessages.variableNotDefined(variableName));
        }
        return getVariableTypeFromName(variableName) != null;
    }

    public void validateColorProperty(Declaration colorDeclaration) {
        ExpressionType type = getExpressionType(colorDeclaration.expression);

        if(type != ExpressionType.COLOR) {
            colorDeclaration.setError(ErrorMessages.wrongPropertyType(colorDeclaration.property.name, ExpressionType.COLOR, type));
        }
    }

    protected ExpressionType validateOperationType(Operation operation) {
        ExpressionType left = getExpressionType(operation.lhs);
        ExpressionType right = getExpressionType(operation.rhs);

        if(left == null || right == null) {
            operation.setError(ErrorMessages.undefinedVariableInOperation());
        }

        if(left != right && !isScalar(left) && !isScalar(right)) {
            operation.setError(ErrorMessages.mixedTypes(left, right));
        }

        switch(operation) {
            case MultiplyOperation _ -> {return validateMultiplyOperation(operation, left, right);}
            case AddOperation _ -> {return validateAddOperation(operation, left, right);}
            default -> {return ExpressionType.UNDEFINED;}
        }
    }

    protected ExpressionType validateMultiplyOperation(Operation operation, ExpressionType left, ExpressionType right) {
        if(!isScalar(left) && !isScalar(right)) {
            operation.setError(ErrorMessages.invalidMultiplyOperation());
            return left;
        }

        if(isScalar(left)) {
            return right;
        } else {
            return left;
        }
    }

    protected ExpressionType validateAddOperation(Operation operation, ExpressionType left, ExpressionType right) {
        if(isScalar(left) || isScalar(right)) {
            operation.setError(ErrorMessages.invalidAddOperation());
            return left;
        }
        return right;
    }

    protected void validateSizeDeclaration(Declaration declaration) {
        validateExpression(declaration.expression, declaration.property.name);
    }

    protected void validateExpression(Expression expression, String propertyName) {
        ExpressionType expressionType = getExpressionType(expression);

        if(expressionType != ExpressionType.PERCENTAGE && expressionType != ExpressionType.PIXEL) {
            expression.setError(ErrorMessages.invalidSizeType(propertyName, expressionType));
        }
    }

    protected void validateIfClause(IfClause ifClause) {
        ExpressionType type = getExpressionType(ifClause.conditionalExpression);
        if(type != ExpressionType.BOOL) {
            ifClause.setError(ErrorMessages.invalidIfCondition(ExpressionType.BOOL.name()));
        }
    }
    // =================================================================================================================
    // HELPER FUNCTIONS
    // =================================================================================================================

    protected ExpressionType getExpressionType(Expression expression) {
        switch (expression) {
            case BoolLiteral _ -> { return ExpressionType.BOOL; }
            case Literal l -> {return l.getType();}
            case VariableReference vr -> {return getVariableTypeFromName(vr.name);}
            case Operation o -> { return validateOperationType(o);}
            default -> {
                return ExpressionType.UNDEFINED;
            }
        }
    }

    protected static boolean isScalar(ExpressionType expressionType) {
        return expressionType == ExpressionType.SCALAR;
    }

    protected ExpressionType getVariableTypeFromName(String variableName) {
        for (HashMap<String, ExpressionType> scopeVariableMap : variableTypes) {
            ExpressionType result = scopeVariableMap.get(variableName);
            if (result != null) {
                return result;
            }
        }

        return null;
    }
}
