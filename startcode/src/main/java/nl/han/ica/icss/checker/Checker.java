package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.switch_case.Switch;

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
}