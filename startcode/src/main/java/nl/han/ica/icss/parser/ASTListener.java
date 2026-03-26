package nl.han.ica.icss.parser;

import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;
import nl.han.ica.icss.ast.switch_case.Case;
import nl.han.ica.icss.ast.switch_case.DefaultCase;
import nl.han.ica.icss.ast.switch_case.Switch;

import java.util.ArrayList;

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
		handleAddingChild(node);
	}

	private void handleAddingChild(Declaration declaration) {
		ASTNode possibleParent = currentContainer.peek();
		switch(possibleParent) {
			case Case c -> c.body.add(declaration);
			case DefaultCase defaultCase -> defaultCase.body.add(declaration);
			default -> possibleParent.addChild(declaration);
		}
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		// Idem als bij "exitVariable"
	}

	@Override
	public void enterIfClause(ICSSParser.IfClauseContext ctx) {
		IfClause node = new IfClause();
		node.conditionalExpression = createIfCondition(ctx.if_condition());
		currentContainer.peek().addChild(node);
		currentContainer.push(node);
	}

	private Expression createIfCondition(ICSSParser.If_conditionContext ctx) {
		if(ctx.TRUE() != null || ctx.FALSE() != null) {
			return new BoolLiteral(ctx.getText());
		} else if (ctx.comparison() != null) {
			return createComparison(ctx.comparison());
		} else if (ctx.value() != null) {
			return createValue(ctx.value());
		} else {
			throw new IllegalStateException("Unknown condition: " + ctx.getText());
		}
	}

	private Expression createComparison(ICSSParser.ComparisonContext ctx) {
		if(ctx.value().size() == 1) {
			return createValue(ctx.value(0));
		}

		Expression lhs = createValue(ctx.value(0));
		Expression rhs = createValue(ctx.value(1));
		String op = ctx.getChild(1).getText();

		Operator operator = switch (op) {
			case "==" -> Operator.EQ;
			case "!=" -> Operator.NEQ;
			case ">" -> Operator.GT;
			case ">=" -> Operator.GTE;
			case "<" -> Operator.ST;
			case "<=" -> Operator.STE;
			default -> throw new IllegalStateException("Unknown operator: " + op);
		};

		ComparisonOperation comparisonOperation = new ComparisonOperation(operator);
		comparisonOperation.lhs = lhs;
		comparisonOperation.rhs = rhs;
		return comparisonOperation;
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

	@Override
	public void enterSwitchCaseBlock(ICSSParser.SwitchCaseBlockContext ctx) {
		Switch node = new Switch();
		node.condition = createExpression(ctx.expression());

		currentContainer.peek().addChild(node);
		currentContainer.push(node);
	}

	@Override
	public void exitSwitchCaseBlock(ICSSParser.SwitchCaseBlockContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterCaseBlock(ICSSParser.CaseBlockContext ctx) {
		Case node = new Case();

		node.condition = createExpression(ctx.expression());
		currentContainer.peek().addChild(node);

		if(currentContainer.peek() instanceof Switch parent) {
			parent.cases.add(node);
		}

		currentContainer.push(node);
	}

	@Override
	public void exitCaseBlock(ICSSParser.CaseBlockContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterDefaultBlock(ICSSParser.DefaultBlockContext ctx) {
		DefaultCase node = new DefaultCase();

		currentContainer.peek().addChild(node);

		if(currentContainer.peek() instanceof Switch parent) {
			parent.defaultCase = node;
		}

		currentContainer.push(node);
	}

	@Override
	public void exitDefaultBlock(ICSSParser.DefaultBlockContext ctx) {
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
					break;
				case "-":
					SubtractOperation op1 = new SubtractOperation();
					op1.lhs = result;
					op1.rhs = right;
					result = op1;
					break;
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