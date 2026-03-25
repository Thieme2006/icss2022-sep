package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.switch_case.Case;
import nl.han.ica.icss.ast.switch_case.Switch;
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

    protected void walkThroughASTTree(ASTNode node) {
        // ALs de node een IF, ELSE of STYLERULE is dan moet hij een nieuwe scope toevoegen.
        if (node instanceof Scoped) {
            variableTypes.addFirst(new HashMap<>());
        }

        validateNode(node);

        for (ASTNode child : node.getChildren()) {
            walkThroughASTTree(child);
        }

        // ALs de node een IF, ELSE of STYLERULE is dan moet hij de laatste scope verwijderen.
        if (node instanceof Scoped) {
            variableTypes.removeFirst();
        }
    }

    protected void validateNode(ASTNode node) {
        switch (node) {
            case VariableAssignment va -> handleValidation(va);
            case VariableReference vr -> handleValidation(vr);
            case Declaration d -> check(d);
            case IfClause ifClause -> validateIfClause(ifClause);
            case ElseClause elseClause -> validateElseClause(elseClause);
            case Switch s -> handleSwitchCase(s);
            default -> {}
        }
    }

    private void validateElseClause(ElseClause elseClause) {
        for (ASTNode child : elseClause.getChildren()) {
            walkThroughASTTree(child);
        }
    }

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
        ExpressionType type = getExpressionType(variableAssignment.expression);

        if (type == null) {
            variableAssignment.setError(ErrorMessages.variableNotDefined(variableAssignment.name.name));
            return;
        }
        // UndefinedVariables die gereturned worden hebben altijd al een variabele.
        if(type == ExpressionType.UNDEFINED) {
            return;
        }

        variableTypes.getFirst().put(variableAssignment.name.name, type);
    }

    protected void handleValidation(VariableReference variableReference) {
        addErrorIfVariableNotDefined(variableReference, variableReference.name);
    }

    protected void handleSwitchCase(Switch switchCase) {
        for(Case caseItem : switchCase.cases) {
            validateSwitchCase(caseItem, switchCase.condition);
        }
    }

    private void validateSwitchCase(Case caseNode, Expression condition) {
        if(caseNode.condition instanceof Expression expression) {
            ExpressionType type = getExpressionType(expression);

            ExpressionType switchConditionType = getExpressionType(condition);
            if(!(switchConditionType == type)) {
                caseNode.setError("Case \"" + type + "\" does not match the type of the switch condition \"" + switchConditionType + "\".");
            }
        }
        validateNode(caseNode.condition);

        for(ASTNode child : caseNode.body) {
            validateNode(child);
        }
    }

    // =================================================================================================================
    // VALIDATION HELPERS
    // =================================================================================================================

    protected boolean addErrorIfVariableNotDefined(ASTNode node, String variableName) {
        ExpressionType type = getVariableTypeFromName(variableName);
        if (type == null) {
            node.setError(ErrorMessages.variableNotDefined(variableName));
            return false;
        }
        return true;
    }

    public void validateColorProperty(Declaration colorDeclaration) {
        ExpressionType type = getExpressionType(colorDeclaration.expression);

        if(type != ExpressionType.COLOR) {
            colorDeclaration.setError(
                    ErrorMessages.wrongPropertyType(
                            colorDeclaration.property.name,
                            ExpressionType.COLOR,
                            type));
            return;
        }
    }

    protected ExpressionType validateOperationType(Operation operation) {
        ExpressionType left = getExpressionType(operation.lhs);
        ExpressionType right = getExpressionType(operation.rhs);

        if ((left == null || right == null) ||
           (left == ExpressionType.UNDEFINED || right == ExpressionType.UNDEFINED)) {
            operation.setError(ErrorMessages.undefinedVariableInOperation());
            return ExpressionType.UNDEFINED;
        }

        if(left != right && !isScalar(left) && !isScalar(right)) {
            operation.setError(ErrorMessages.mixedTypes(left, right));
            return ExpressionType.UNDEFINED;
        }

        if(!isCompatible(left, right)) {
            operation.setError(ErrorMessages.mixedTypes(left, right));
            return ExpressionType.UNDEFINED;
        }

        if(left == ExpressionType.COLOR || right == ExpressionType.COLOR) {
            operation.setError("A color is not allowed to be used in an operation.");
            return ExpressionType.UNDEFINED;
        }

        return switch(operation) {
            case MultiplyOperation ignore1 -> validateMultiplyOperation(operation, left, right);
            case AddOperation ignore2 -> validateAddOrSubtractOperation(operation, left, right);
            case SubtractOperation ignore3 -> validateAddOrSubtractOperation(operation, left, right);
            default -> ExpressionType.UNDEFINED;
        };
    }

    protected ExpressionType validateMultiplyOperation(Operation operation, ExpressionType left, ExpressionType right) {
        if(!isScalar(left) && !isScalar(right)) {
            operation.setError(ErrorMessages.invalidMultiplyOperation());
            return ExpressionType.UNDEFINED;
        }

        if(isScalar(left)) {
            return right;
        } else {
            return left;
        }
    }

    protected ExpressionType validateAddOrSubtractOperation(Operation operation, ExpressionType left, ExpressionType right) {
        if(isScalar(left) && isScalar(right)) {
            operation.setError(ErrorMessages.usingOnlyScalarValuesInAddOrSubtractNotPermitted());
            return ExpressionType.UNDEFINED;
        }

        if(isScalar(left) || isScalar(right)) {
            operation.setError(ErrorMessages.invalidAddOrSubtractOperation());
            return ExpressionType.UNDEFINED;
        }
        return isScalar(left) ? right : left;
    }

    protected void validateSizeDeclaration(Declaration declaration) {
        validateExpression(declaration.expression, declaration.property.name);
    }

    protected void validateExpression(Expression expression, String propertyName) {
        ExpressionType expressionType = getExpressionType(expression);

        if(expressionType == ExpressionType.UNDEFINED) {
            return;
        }

        if(expressionType != ExpressionType.PERCENTAGE && expressionType != ExpressionType.PIXEL) {
            expression.setError(ErrorMessages.invalidSizeType(propertyName, expressionType));
        }
    }

    protected void validateIfClause(IfClause ifClause) {
        ExpressionType type = getExpressionType(ifClause.conditionalExpression);
        if(type != ExpressionType.BOOL) {
            ifClause.setError(ErrorMessages.invalidIfCondition(ExpressionType.BOOL.name()));
        }

        variableTypes.addFirst(new HashMap<>());
        for(ASTNode child : ifClause.body) {
            walkThroughASTTree(child);
        }
        variableTypes.removeFirst();

        if(ifClause.elseClause != null) {
            variableTypes.addFirst(new HashMap<>());
            walkThroughASTTree(ifClause.elseClause);
            variableTypes.removeFirst();
        }
    }
    // =================================================================================================================
    // HELPER FUNCTIONS
    // =================================================================================================================

    protected ExpressionType getExpressionType(Expression expression) {
        return switch (expression) {
            case BoolLiteral ignore1 -> ExpressionType.BOOL;
            case Literal l -> l.getType();
            case VariableReference vr -> getVariableTypeFromVariableReference(vr);
            case Operation o -> validateOperationType(o);
            default -> ExpressionType.UNDEFINED;
        };
    }

    private ExpressionType getVariableTypeFromVariableReference(VariableReference vr) {
        ExpressionType type = getVariableTypeFromName(vr.name);
        if(type == null) {
            vr.setError(ErrorMessages.variableNotDefined(vr.name));
            return ExpressionType.UNDEFINED;
        }
        return type;
    }

    protected static boolean isScalar(ExpressionType expressionType) {
        return expressionType == ExpressionType.SCALAR;
    }

    protected static boolean isCompatible(ExpressionType left, ExpressionType right) {
        if(left == right) return true;
        return isScalar(left) || isScalar(right);
    }

    protected ExpressionType getVariableTypeFromName(String variableName) {
        for (int i = variableTypes.getSize() - 1; i >= 0; i--) {
            HashMap<String, ExpressionType> scopeVariableMap = variableTypes.get(i);
            ExpressionType result = scopeVariableMap.get(variableName);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
