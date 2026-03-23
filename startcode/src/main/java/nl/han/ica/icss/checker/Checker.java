package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.icss.ast.*;
import java.util.HashMap;

public class Checker extends BaseChecker {

    public Checker() {
        this.variableTypes = new HANLinkedList<>();
    }

    public void check(AST ast) {
         this.variableTypes = new HANLinkedList<>();
         this.variableTypes.addFirst(new HashMap<>());
         walkThroughASTTree(ast.root);
    }

    private void walkThroughASTTree(ASTNode node) {
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

    private void validateNode(ASTNode node) {
        switch (node) {
            case VariableAssignment va -> handleValidation(va);
            case VariableReference vr -> handleValidation(vr);
            case Declaration d -> check(d);
            case IfClause ifClause -> validateIfClause(ifClause);
            default -> {}
        }
    }
}