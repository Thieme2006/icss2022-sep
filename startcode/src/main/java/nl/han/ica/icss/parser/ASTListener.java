package nl.han.ica.icss.parser;

import java.util.Stack;


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
		currentContainer.push(ast.root);
	}

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		currentContainer.pop();
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
	public void enterBodyItem(ICSSParser.BodyItemContext ctx) {
		Declaration node = new Declaration(ctx.declaration().getText());
		currentContainer.peek().addChild(node);
		currentContainer.push(node);
	}

	@Override
	public void exitBodyItem(ICSSParser.BodyItemContext ctx) {
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
}