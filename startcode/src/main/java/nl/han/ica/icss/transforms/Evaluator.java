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

public class Evaluator extends BaseTransformer implements Transform {
    public Evaluator() {
        this.variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        this.variableValues = new HANLinkedList<>();
        this.variableValues.addFirst(new HashMap<>());
        ast.root.body = walkThroughASTTree(ast.root);
    }

    private ArrayList<ASTNode> walkThroughASTTree(ASTNode node) {
        ArrayList<ASTNode> result = new ArrayList<>();
        for (ASTNode childNode : node.getChildren()) {
            switch (childNode) {
                case Stylerule rule -> result.add(transformStylerule(rule));
                case VariableAssignment va -> addVariableAssignmentToVariable(va);
                default -> {
                }
            }
        }
        return result;
    }
}
