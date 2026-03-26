package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
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

public class Evaluator implements Transform {

    private IHANLinkedList<HashMap<String, Literal>> variableValues;

    public Evaluator() {
        this.variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        this.variableValues = new HANLinkedList<>();
        this.variableValues.addFirst(new HashMap<>());
        ast.root.body = walkThroughASTTree(ast.root);
    }

    public ArrayList<ASTNode> walkThroughASTTree(ASTNode node) {
        ArrayList<ASTNode> result = new ArrayList<>();
        for(ASTNode childNode : node.getChildren()) {
            switch(childNode) {
                case Stylerule rule -> result.add(transformStylerule(rule));
                case VariableAssignment va -> addVariableAssignmentToVariable(va);
                default -> {}
            }
        }
        return result;
    }

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

    private ASTNode transformStylerule(Stylerule stylerule) {
        variableValues.addFirst(new HashMap<>());
        LinkedList<ASTNode> processed = new LinkedList<>();

        for(ASTNode node : stylerule.body) {
            processed.addAll(transformAstNode(node));
        }

        // Hierin kijk ik na of alle items wel geparsed zijn naar een Literal, als een gemist is dan geeft hij een exception.
        for(ASTNode node : processed) {
            if(node instanceof Declaration d && !(d.expression instanceof Literal)) {
                    throw new IllegalArgumentException("Expression not evaluated: " + d);
            }
        }

        LinkedList<ASTNode> unique = getRemainingASTNodes(processed);
        stylerule.body = new ArrayList<>(unique);
        variableValues.removeFirst();

        return stylerule;
    }

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

    private LinkedList<ASTNode> getRemainingASTNodes(LinkedList<ASTNode> processed) {
        LinkedList<ASTNode> remaining = new LinkedList<>();
        HashSet<String> viewedItems = new HashSet<>();

        for(int i = processed.size() - 1; i >= 0; i--) {
            ASTNode node = processed.get(i);

            if(node instanceof Declaration decl) {
                if(viewedItems.add(decl.property.name)) {
                    remaining.addFirst(node);
                }
            } else {
                remaining.addFirst(node);
            }
        }
        return remaining;
    }

    private void addVariableAssignmentToVariable(VariableAssignment variableAssignment) {
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

    private Declaration createDeclaration(Declaration d) {
        Declaration declaration = new Declaration();
        declaration.property = d.property;
        declaration.expression = evaluateExpression(d.expression);

        return declaration;
    }

    private Literal evaluateExpression(Expression expression) {
        return switch(expression) {
            case VariableReference vr -> handleVariableReferenceExpression(vr);
            case Literal l -> l;
            case Operation o -> evaluateOperation(o);
            default -> throw new RuntimeException("Unknown expression type");
        };
    }

    private Literal evaluateOperation(Operation operation) {
        return switch(operation) {
            case AddOperation addOperation -> addLiteral(evaluateExpression(addOperation.lhs), evaluateExpression(addOperation.rhs));
            case SubtractOperation subtractOperation -> minusLiteral(evaluateExpression(subtractOperation.lhs), evaluateExpression(subtractOperation.rhs));
            case MultiplyOperation multiplyOperation -> multiplyLiteral(evaluateExpression(multiplyOperation.lhs), evaluateExpression(multiplyOperation.rhs));
            case ComparisonOperation comparisonOperation -> evaluateComparisonOperation(comparisonOperation);
            default -> throw new IllegalStateException("Unknown operation.");
        };
    }

    private Literal evaluateComparisonOperation(ComparisonOperation operation) {
        boolean result = compareLiterals(evaluateExpression(operation.lhs), evaluateExpression(operation.rhs), operation.operator);
        return new BoolLiteral(result);
    }

    private static boolean compareLiterals(Literal left, Literal right, Operator op) {
        if(left.getClass() != right.getClass()) {
            throw new IllegalArgumentException("Cannot compare different literal types: " + left.getClass().getSimpleName() + " vs " + right.getClass().getSimpleName());
        }

        return switch(left) {
            case PixelLiteral pxl when right instanceof PixelLiteral pxr -> compareNumbers(pxl.value, pxr.value, op);
            case ScalarLiteral scl when right instanceof ScalarLiteral scr -> compareNumbers(scl.value, scr.value, op);
            case PercentageLiteral percl when right instanceof PercentageLiteral percr -> compareNumbers(percl.value, percr.value, op);
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

    private Literal addLiteral(Literal left, Literal right) {
        if(left instanceof PixelLiteral pxl && right instanceof PixelLiteral pxr) {
            return new PixelLiteral(pxl.value + pxr.value);
        }
        if(left instanceof ScalarLiteral scl && right instanceof ScalarLiteral scr) {
            return new ScalarLiteral(scl.value + scr.value);
        }
        if(left instanceof PercentageLiteral percl && right instanceof PercentageLiteral percr) {
            return new PercentageLiteral(percl.value + percr.value);
        }
        throw new RuntimeException("Type mismatch while doing an addOperation.");
    }

    private Literal minusLiteral(Literal left, Literal right) {
        if(left instanceof PixelLiteral pxl && right instanceof PixelLiteral pxr) {
            return new PixelLiteral(pxl.value - pxr.value);
        }
        if(left instanceof ScalarLiteral scl && right instanceof ScalarLiteral scr) {
            return new ScalarLiteral(scl.value - scr.value);
        }
        if(left instanceof PercentageLiteral percl && right instanceof PercentageLiteral percr) {
            return new PercentageLiteral(percl.value - percr.value);
        }
        throw new RuntimeException("Type mismatch while doing an subtractOperation.");
    }

    private Literal multiplyLiteral(Literal left, Literal right) {
        if(left instanceof PixelLiteral pxl && right instanceof PixelLiteral pxr) {
            return new PixelLiteral(pxl.value * pxr.value);
        }
        if(left instanceof ScalarLiteral scl && right instanceof ScalarLiteral scr) {
            return new ScalarLiteral(scl.value * scr.value);
        }
        if(left instanceof PercentageLiteral percl && right instanceof PercentageLiteral percr) {
            return new PercentageLiteral(percl.value * percr.value);
        }
        if(left instanceof ScalarLiteral scl && right instanceof PixelLiteral pxr) {
            return new PixelLiteral(scl.value * pxr.value);
        }
        if(left instanceof ScalarLiteral scl && right instanceof PercentageLiteral percr) {
            return new PercentageLiteral(scl.value * percr.value);
        }
        if(left instanceof PixelLiteral pxl && right instanceof ScalarLiteral scl) {
            return new PixelLiteral(pxl.value * scl.value);
        }
        if(left instanceof PercentageLiteral percl && right instanceof ScalarLiteral scr) {
            return new PercentageLiteral(percl.value * scr.value);
        }

        throw new RuntimeException("Type mismatch while doing an multiplyOperation.");
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
