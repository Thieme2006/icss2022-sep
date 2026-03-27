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
}