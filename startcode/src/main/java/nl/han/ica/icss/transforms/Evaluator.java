package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;

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
        walkThroughASTTree(ast.root);
    }

    public void walkThroughASTTree(ASTNode node) {
        for(ASTNode childNode : node.getChildren()) {
            switch(childNode) {
                case Stylerule rule -> transformStylerule(rule);
                case VariableAssignment va -> addVariableAssignmentToVariable(va);
                default -> {}
            }
        }
    }

    private void transformStylerule(Stylerule stylerule) {
        variableValues.addFirst(new HashMap<>());
        LinkedList<ASTNode> processed = new LinkedList<>();

        for(ASTNode node : stylerule.body) {
            switch(node) {
                case VariableAssignment va -> addVariableAssignmentToVariable(va);
                case IfClause ifClause -> processed.addAll(addIfClause(ifClause));
                case Declaration declaration -> handleAddingDeclaration(declaration, processed);
                default -> throw new RuntimeException("Unsupported node in stylerule: " + node);
            }
        }
        // Hierin kijk ik na of alle items wel geparsed zijn naar een Literal, als een gemist is dan geeft hij een exception.
        for(ASTNode node : processed) {
            if(node instanceof Declaration d) {
                if(!(d.expression instanceof Literal)) {
                    throw new RuntimeException("Expression not evaluated: " + d);
                }
            }
        }

        LinkedList<ASTNode> unique = getRemainingASTNodes(processed);
        stylerule.body = new ArrayList<>(unique);
        variableValues.removeFirst();
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

    private void handleAddingDeclaration(Declaration declaration, LinkedList<ASTNode> processed) {
        Declaration newDecl = new Declaration();
        newDecl.property = declaration.property;
        newDecl.expression = evaluateExpression(declaration.expression);

        processed.add(newDecl);
    }

    private void addVariableAssignmentToVariable(VariableAssignment variableAssignment) {
        Literal value = evaluateExpression(variableAssignment.expression);
        assignVariable(variableAssignment.name.name, value);
    }

    private void assignVariable(String name, Literal value) {
        for(int i = 0; i < variableValues.getSize(); i++) {
            HashMap<String, Literal> scope = variableValues.get(i);
            scope.computeIfPresent(name, (_, _) -> value); // SonarQube kwam hier met een voorstel en was wel benieuwd of dit ging werken :)
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
            case AddOperation addOperation -> addLiteral(evaluateExpression(addOperation.lhs),
                                                         evaluateExpression(addOperation.rhs));
            case SubtractOperation subtractOperation -> minusLiteral(evaluateExpression(subtractOperation.lhs),
                                                                     evaluateExpression(subtractOperation.rhs));
            case MultiplyOperation multiplyOperation -> multiplyLiteral(evaluateExpression(multiplyOperation.lhs),
                                                                        evaluateExpression(multiplyOperation.rhs));
            default -> throw new IllegalStateException("Unknown operation.");
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
