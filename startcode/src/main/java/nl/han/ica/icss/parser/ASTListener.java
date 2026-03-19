package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		ast = new AST();
		currentContainer = new HANStack<>();
		currentContainer.push(ast.root);
	}
    public AST getAST() {
        return this.ast;
    }

	@Override
	public void enterStylesheet(ICSSParser.StylesheetContext ctx) {
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
	}
	@Override
	public void enterBlock (ICSSParser.BlockContext blockContext) {
		Stylerule node = new Stylerule();
		currentContainer.peek().addChild(node);
		currentContainer.push(node);
	}

	@Override
	public void exitBlock (ICSSParser.BlockContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		Selector node = createSelector(ctx);
		currentContainer.peek().addChild(node);
		currentContainer.push(node);
	}

	@Override
	public void exitSelector(ICSSParser.SelectorContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterValue(ICSSParser.ValueContext ctx) {
		super.enterValue(ctx);
	}

	@Override
	public void enterVariable(ICSSParser.VariableContext ctx) {
		VariableReference varName = new VariableReference(ctx.CAPITAL_IDENT().getText());

		Expression expr = createExpression(ctx.expression());

		VariableAssignment node = new VariableAssignment();
		node.name = varName;
		node.expression = expr;

		currentContainer.peek().addChild(node);
	}

	@Override
	public void exitVariable(ICSSParser.VariableContext ctx) {
		// Geen pop gedaan omdat anders de root verwijderd wordt :/
	}

	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration node = new Declaration(ctx.LOWER_IDENT().getText());

		node.expression = createExpression(ctx.expression());
		currentContainer.peek().addChild(node);
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		// Idem als bij "exitVariable"
	}

	@Override
	public void enterIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause node = new IfClause();
		node.conditionalExpression = createValue(ctx.value());
		currentContainer.peek().addChild(node);
		currentContainer.push(node);
	}

	@Override
	public void exitIfClause(ICSSParser.IfClauseContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterElseClause(ICSSParser.ElseClauseContext ctx) {
		ElseClause node = new ElseClause();
		currentContainer.peek().addChild(node);
		currentContainer.push(node);
	}

	@Override
	public void exitElseClause(ICSSParser.ElseClauseContext ctx) {
		currentContainer.pop();
	}

	private Selector createSelector(ICSSParser.SelectorContext ctx) {
		switch(ctx.getStart().getType()) {
			case ICSSParser.ID_IDENT:
				return new IdSelector(ctx.ID_IDENT().getText());
			case ICSSParser.CLASS_IDENT:
				return new ClassSelector(ctx.CLASS_IDENT().getText());
			case ICSSParser.LOWER_IDENT:
				return  new TagSelector(ctx.LOWER_IDENT().getText());
			default:
				throw new IllegalStateException("Unexpected token type: " + ctx.getStart().getType());
		}
	}

	private Expression createExpression(ICSSParser.ExpressionContext ctx) {
		Expression result = createTerm(ctx.term(0));

		for(int i = 1; i < ctx.term().size(); i++) {
			Expression right = createTerm(ctx.term(i));

			switch (ctx.getChild(2 * i - 1).getText()) {
				case "+":
					AddOperation op = new AddOperation();
					op.lhs = result;
					op.rhs = right;
					result = op;
				case "-":
					SubtractOperation op1 = new SubtractOperation();
					op1.lhs = result;
					op1.rhs = right;
					result = op1;
			}
		}
		return result;
	}

	private Expression createTerm(ICSSParser.TermContext ctx) {
		Expression result = createValue(ctx.value(0));

		for(int i = 1; i < ctx.value().size(); i++) {
			Expression right = createValue(ctx.value(i));

			MultiplyOperation op = new MultiplyOperation();
			op.lhs = result;
			op.rhs = right;

			result = op;
		}

		return result;
	}

	private Expression createValue(ICSSParser.ValueContext ctx) {
		switch (ctx.getStart().getType()) {
			case ICSSParser.COLOR:
				return new ColorLiteral(ctx.getText());
			case ICSSParser.PIXELSIZE:
				return new PixelLiteral(ctx.getText());
			case ICSSParser.PERCENTAGE:
				return new PercentageLiteral(ctx.getText());
			case ICSSParser.SCALAR:
				return new ScalarLiteral(ctx.getText());

			case ICSSParser.TRUE, ICSSParser.FALSE:
				return new BoolLiteral(ctx.getText());

			case ICSSParser.CAPITAL_IDENT, ICSSParser.LOWER_IDENT:
				return new VariableReference(ctx.getText());
			default:
				throw new IllegalStateException("Unknown value: " + ctx.getText());
		}
	}
}