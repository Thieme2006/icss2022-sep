package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.switch_case.Case;
import nl.han.ica.icss.ast.switch_case.Switch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public abstract class BaseTransformer {
    protected IHANLinkedList<HashMap<String, Literal>> variableValues;

    private ArrayList<ASTNode> transformAstNode(ASTNode node) {
        ArrayList<ASTNode> result = new ArrayList<>();
        switch (node) {
            case VariableAssignment va -> addVariableAssignmentToVariable(va);
            case Declaration d -> result.add(createDeclaration((d)));
            case IfClause i -> result.addAll(addIfClause(i));
            case Switch s -> result.addAll(handleSwitchCaseValidation(s));
            default -> throw new IllegalArgumentException("Unsupported node: " + node);
        }
        return result;
    }

    protected ASTNode transformStylerule(Stylerule stylerule) {
        variableValues.addFirst(new HashMap<>());
        LinkedList<ASTNode> processed = new LinkedList<>();

        for(ASTNode node : stylerule.body) {
            processed.addAll(transformAstNode(node));
        }

        // Hierin kijk ik na of alle items wel geparsed zijn naar een Literal,
        // als een gemist is dan geeft hij een exception.
        for(ASTNode node : processed) {
            if(node instanceof Declaration d && !(d.expression instanceof Literal)) {
                throw new IllegalArgumentException("Expression of \"" + node.getClass().getSimpleName() + "\" is not evaluated: " + d);
            }
        }
        stylerule.body = getRemainingASTNodes(processed);
        variableValues.removeFirst();

        return stylerule;
    }

    // ==================================================================================================
    // VARIABLE ASSIGNMENT
    // ==================================================================================================

    protected void addVariableAssignmentToVariable(VariableAssignment variableAssignment) {
        Literal value = evaluateExpression(variableAssignment.expression);
        assignVariable(variableAssignment.name.name, value);
    }

    private void assignVariable(String name, Literal value) {
        for(int i = 0; i < variableValues.getSize(); i++) {
            HashMap<String, Literal> scope = variableValues.get(i);
            scope.computeIfPresent(name, (ignore1, ignore2) -> value); // SonarQube kwam hier met een voorstel en was wel benieuwd of dit ging werken :)
            // Bevorderd debugging niet echt maar het maakt de code wat compacter
        }

        variableValues.getFirst().put(name, value);
    }

    // ==================================================================================================
    // Switch case validation
    // ==================================================================================================
    private ArrayList<ASTNode> handleSwitchCaseValidation(Switch switchCase) {
        Literal switchCondition = evaluateExpression(switchCase.condition);

        ArrayList<ASTNode> result = new ArrayList<>();
        for (Case caseItem : switchCase.cases) {
            if(caseItem.condition instanceof Expression condition) {
                Literal caseCondition = evaluateExpression(condition);

                if (caseCondition.equals(switchCondition)) {
                    variableValues.addFirst(new HashMap<>());

                    for(ASTNode node : caseItem.body) {
                        result.addAll(transformAstNode(node));
                    }
                    variableValues.removeFirst();
                    return result;
                }
            }
        }
        if(switchCase.defaultCase != null) {
            for(ASTNode node : switchCase.defaultCase.body) {
                result.addAll(transformAstNode(node));
            }
        }

        return result;
    }

    // ==================================================================================================
    // IF CLAUSE
    // ==================================================================================================

    private ArrayList<ASTNode> addIfClause(IfClause ifClause) {
        Literal condition = evaluateExpression(ifClause.conditionalExpression);
        ArrayList<ASTNode> body = null;

        if(condition instanceof BoolLiteral bool && bool.value) {
            body = ifClause.body;
        } else if (ifClause.elseClause != null) {
            body = ifClause.elseClause.body;
        }

        if(body == null) {
            return new ArrayList<>();
        }

        // In een if clause stap je een nieuwe scope in waarbij je ook nieuwe variabelen kan definen
        variableValues.addFirst(new HashMap<>());
        ArrayList<ASTNode> result = new ArrayList<>();

        for(ASTNode node : body) {
            switch (node) {
                case VariableAssignment va -> addVariableAssignmentToVariable(va);
                case Declaration d -> result.add(createDeclaration(d));
                case IfClause i -> result.addAll(addIfClause(i));
                default -> {}
            }
        }

        variableValues.removeFirst();
        return result;
    }

    // ==================================================================================================
    //  DECLARATION
    // ==================================================================================================

    private Declaration createDeclaration(Declaration d) {
        Declaration declaration = new Declaration();
        declaration.property = d.property;
        declaration.expression = evaluateExpression(d.expression);

        return declaration;
    }

    // ==================================================================================================
    // EXPRESSIONS
    // ==================================================================================================

    private Literal evaluateExpression(Expression expression) {
        return switch(expression) {
            case VariableReference vr -> handleVariableReferenceExpression(vr);
            case Literal l -> l;
            case Operation o -> evaluateOperation(o);
            default -> throw new RuntimeException("Unknown expression type");
        };
    }

    // ==================================================================================================
    // OPERATIONS
    // ==================================================================================================

    private Literal evaluateOperation(Operation operation) {
        return switch(operation) {
            case AddOperation addOperation -> addLiteral(evaluateExpression(addOperation.lhs), evaluateExpression(addOperation.rhs));
            case SubtractOperation subtractOperation -> minusLiteral(evaluateExpression(subtractOperation.lhs), evaluateExpression(subtractOperation.rhs));
            case MultiplyOperation multiplyOperation -> multiplyLiteral(evaluateExpression(multiplyOperation.lhs), evaluateExpression(multiplyOperation.rhs));
            case ComparisonOperation comparisonOperation -> evaluateComparisonOperation(comparisonOperation);
            default -> throw new IllegalStateException("Unknown operation.");
        };
    }

    private Literal addLiteral(Literal left, Literal right) {
        return switch (left) {
            // Pixel + pixel
            case PixelLiteral pxl when right instanceof PixelLiteral pxr -> new PixelLiteral(pxl.value + pxr.value);
            // Scalar + Scalar
            case ScalarLiteral scl when right instanceof ScalarLiteral scr -> new ScalarLiteral(scl.value + scr.value);
            // Percentage + Percentage
            case PercentageLiteral pcl when right instanceof PercentageLiteral pcr -> new PercentageLiteral(pcl.value + pcr.value);
            default -> throw new RuntimeException("Type mismatch while doing an add operation");
        };
    }

    private Literal minusLiteral(Literal left, Literal right) {
        return switch(left) {
            // Pixel - Pixel
            case PixelLiteral pxl when right instanceof PixelLiteral pxr -> new PixelLiteral(pxl.value - pxr.value);
            // Scalar - Scalar
            case ScalarLiteral scl when right instanceof ScalarLiteral scr -> new ScalarLiteral(scl.value - scr.value);
            // Percentage - Percentage
            case PercentageLiteral pcl when right instanceof PercentageLiteral pcr -> new PercentageLiteral(pcl.value - pcr.value);
            default -> throw new RuntimeException("Type mismatch while doing a minusOperation.");
        };
    }

    private Literal multiplyLiteral(Literal left, Literal right) {
        return switch(left) {
            // Pixel * pixel
            case PixelLiteral pxl when right instanceof PixelLiteral pxr -> new PixelLiteral(pxl.value * pxr.value);
            // Scalar * Scalar
            case ScalarLiteral scl when right instanceof ScalarLiteral scr -> new ScalarLiteral(scl.value * scr.value);
            // Percentage * Percentage
            case PercentageLiteral pcl when right instanceof PercentageLiteral pcr -> new PercentageLiteral(pcl.value * pcr.value);

            // Scalar * Pixel
            case ScalarLiteral scl when right instanceof PixelLiteral pxr -> new PixelLiteral(scl.value * pxr.value);
            // Scalar * Percentage
            case ScalarLiteral scl when right instanceof PercentageLiteral scr -> new PercentageLiteral(scl.value * scr.value);
            // Pixel * Scalar
            case PixelLiteral pxl when right instanceof ScalarLiteral scr -> new PixelLiteral(pxl.value * scr.value);
            // Percentage * Scalar
            case PercentageLiteral pxl when right instanceof ScalarLiteral scr -> new PercentageLiteral(pxl.value * scr.value);
            default -> throw new RuntimeException("Type mismatch while doing multiplyOperation.");
        };
    }

    private Literal evaluateComparisonOperation(ComparisonOperation operation) {
        return new BoolLiteral(compareLiterals(evaluateExpression(operation.lhs), evaluateExpression(operation.rhs), operation.operator));
    }

    private static boolean compareLiterals(Literal left, Literal right, Operator op) {
        if(left.getClass() != right.getClass()) {
            throw new IllegalArgumentException("Cannot compare different literal types: " + left.getClass().getSimpleName() + " vs " + right.getClass().getSimpleName());
        }

        return switch(left) {
            case PixelLiteral pxl when right instanceof PixelLiteral pxr -> compareNumbers(pxl.value, pxr.value, op);
            case ScalarLiteral scl when right instanceof ScalarLiteral scr -> compareNumbers(scl.value, scr.value, op);
            case PercentageLiteral pcl when right instanceof PercentageLiteral pcr -> compareNumbers(pcl.value, pcr.value, op);
            case BoolLiteral bl when right instanceof BoolLiteral br -> compareBooleans(bl.value, br.value, op);
            case ColorLiteral cl when right instanceof ColorLiteral cr -> compareColors(cl, cr, op);
            default -> throw new IllegalStateException("Unsupported literal type for comparison: " + left.getClass().getSimpleName());
        };
    }

    private static boolean compareColors(ColorLiteral left, ColorLiteral right, Operator op) {
        if(op != Operator.EQ && op != Operator.NEQ) {
            throw new IllegalArgumentException("Cannot compare colors with " + op);
        }
        return (op == Operator.EQ) == left.value.equals(right.value);
    }

    private static boolean compareNumbers(int left, int right, Operator op) {
        return switch(op) {
            case EQ -> left == right;
            case NEQ -> left != right;
            case GT -> left > right;
            case GTE -> left >= right;
            case ST -> left < right;
            case STE -> left <= right;
        };
    }

    private static boolean compareBooleans(boolean left, boolean right, Operator op) {
        return switch(op) {
            case EQ -> left == right;
            case NEQ -> left != right;
            default -> throw new IllegalArgumentException("Invalid boolean comparison: " + op);
        };
    }

    // ==================================================================================================
    // Other
    // ==================================================================================================
    private ArrayList<ASTNode> getRemainingASTNodes(LinkedList<ASTNode> processed) {
        ArrayList<ASTNode> remaining = new ArrayList<>();
        HashSet<String> viewedItems = new HashSet<>();

        for(int i = processed.size() - 1; i >= 0; i--) {
            ASTNode node = processed.get(i);

            if(node instanceof Declaration declaration) {
                if(viewedItems.add(declaration.property.name)) {
                    remaining.addFirst(node);
                }
            } else {
                remaining.addFirst(node);
            }
        }
        return remaining;
    }

    private Literal handleVariableReferenceExpression(VariableReference vr) {
        for(int i = variableValues.getSize() - 1; i >= 0; i--) {
            HashMap<String, Literal> scope = variableValues.get(i);
            if(scope.containsKey(vr.name)) {
                return scope.get(vr.name);
            }
        }
        throw new RuntimeException("Unknown expression type");
    }
}
