package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.switch_case.Case;
import nl.han.ica.icss.ast.switch_case.DefaultCase;
import nl.han.ica.icss.ast.switch_case.Switch;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {

    //Accumulator attributes:
    private AST ast;

    //Use this to keep track of the parent nodes when recursively traversing the ast
    private IHANStack<ASTNode> stack;

    private BaseAstListener helper;

    public ASTListener() {
        ast = new AST();
        helper = new BaseAstListener();
        stack = new HANStack<>();
        stack.push(ast.root);
    }

    public AST getAST() {
        return this.ast;
    }

    @Override
    public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
        // N.v.t. doordat ast.root altijd de stylesheet is. Hierdoor komt deze te vervallen en gaan we vanaf enterBlock pas de listener door.
    }

    @Override
    public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
        // N.v.t. doordat ast.root altijd de stylesheet is. Hierdoor komt deze te vervallen en gaan we vanaf enterBlock pas de listener door.
    }

    @Override
    public void enterBlock(ICSSParser.BlockContext blockContext) {
        Stylerule node = new Stylerule();
        stack.peek().addChild(node);
        stack.push(node);
    }

    @Override
    public void exitBlock(ICSSParser.BlockContext ctx) {
        stack.pop();
    }

    @Override
    public void enterSelector(ICSSParser.SelectorContext ctx) {
        Selector node = helper.createSelector(ctx);
        stack.peek().addChild(node);
        stack.push(node);
    }

    @Override
    public void exitSelector(ICSSParser.SelectorContext ctx) {
        stack.pop();
    }

    @Override
    public void enterValue(ICSSParser.ValueContext ctx) {
        // EnterValue is niet nodig doordat de value van een item geset wordt door middel van createExpression.
    }

    @Override
    public void enterVariable(ICSSParser.VariableContext ctx) {
        VariableReference varName = new VariableReference(ctx.CAPITAL_IDENT().getText());

        Expression expression = helper.createExpression(ctx.expression());

        VariableAssignment node = new VariableAssignment();
        node.name = varName;
        node.expression = expression;

        handleAddingChild(node);
    }

    @Override
    public void exitVariable(ICSSParser.VariableContext ctx) {
        // Geen pop gedaan omdat anders de root verwijderd kan wordt :/
    }

    @Override
    public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
        Declaration node = new Declaration(ctx.LOWER_IDENT().getText());

        node.expression = helper.createExpression(ctx.expression());
        handleAddingChild(node);
    }

    @Override
    public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
        // Idem als bij "exitVariable"
    }

    @Override
    public void enterIfClause(ICSSParser.IfClauseContext ctx) {
        IfClause node = new IfClause();
        node.conditionalExpression = helper.createIfCondition(ctx.if_condition());
        handleAddingChild(node);
        stack.push(node);
    }

    @Override
    public void exitIfClause(ICSSParser.IfClauseContext ctx) {
        stack.pop();
    }

    @Override
    public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
        ElseClause node = new ElseClause();
        handleAddingChild(node);
        stack.push(node);
    }

    @Override
    public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
        stack.pop();
    }

    @Override
    public void enterSwitchCaseBlock(ICSSParser.SwitchCaseBlockContext ctx) {
        Switch node = new Switch();
        node.condition = helper.createExpression(ctx.expression());

        handleAddingChild(node);
        stack.push(node);
    }

    @Override
    public void exitSwitchCaseBlock(ICSSParser.SwitchCaseBlockContext ctx) {
        stack.pop();
    }

    @Override
    public void enterCaseBlock(ICSSParser.CaseBlockContext ctx) {
        Case node = new Case();

        node.condition = helper.createExpression(ctx.expression());
        handleAddingChild(node);

        if (stack.peek() instanceof Switch parent) {
            parent.cases.add(node);
        }

        stack.push(node);
    }

    @Override
    public void exitCaseBlock(ICSSParser.CaseBlockContext ctx) {
        stack.pop();
    }

    @Override
    public void enterDefaultBlock(ICSSParser.DefaultBlockContext ctx) {
        DefaultCase node = new DefaultCase();

        handleAddingChild(node);

        if (stack.peek() instanceof Switch parent) {
            parent.defaultCase = node;
        }

        stack.push(node);
    }

    @Override
    public void exitDefaultBlock(ICSSParser.DefaultBlockContext ctx) {
        stack.pop();
    }

    // Om ervoor te zorgen dat een case uit een switch-case statement ook meerdere ASTNodes kan bevatten naast een declaration
    // wordt deze functie gebruikt om te kijken of de huidige node een Case of Default case is, indien dit niet zo is dan wordt
    // deze aan de parent toegevoegd. Indien deze wel een Case of DefaultCase is wordt deze toegevoegd aan zijn body.
    private void handleAddingChild(ASTNode declaration) {
        ASTNode possibleParent = stack.peek();
        switch (possibleParent) {
            case Case c -> c.body.add(declaration);
            case DefaultCase defaultCase -> defaultCase.body.add(declaration);
            default -> possibleParent.addChild(declaration);
        }
    }
}