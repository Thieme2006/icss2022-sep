// Generated from C:/Users/aweus/Desktop/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
package nl.han.ica.icss.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ICSSParser}.
 */
public interface ICSSListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ICSSParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ICSSParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void enterSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 */
	void exitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(ICSSParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(ICSSParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(ICSSParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(ICSSParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ICSSParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ICSSParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#bodyItem}.
	 * @param ctx the parse tree
	 */
	void enterBodyItem(ICSSParser.BodyItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#bodyItem}.
	 * @param ctx the parse tree
	 */
	void exitBodyItem(ICSSParser.BodyItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#ifClause}.
	 * @param ctx the parse tree
	 */
	void enterIfClause(ICSSParser.IfClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#ifClause}.
	 * @param ctx the parse tree
	 */
	void exitIfClause(ICSSParser.IfClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(ICSSParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(ICSSParser.ElseClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(ICSSParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(ICSSParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(ICSSParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(ICSSParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void enterStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 */
	void exitStylesheet(ICSSParser.StylesheetContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#switchCaseBlock}.
	 * @param ctx the parse tree
	 */
	void enterSwitchCaseBlock(ICSSParser.SwitchCaseBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#switchCaseBlock}.
	 * @param ctx the parse tree
	 */
	void exitSwitchCaseBlock(ICSSParser.SwitchCaseBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#caseBlock}.
	 * @param ctx the parse tree
	 */
	void enterCaseBlock(ICSSParser.CaseBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#caseBlock}.
	 * @param ctx the parse tree
	 */
	void exitCaseBlock(ICSSParser.CaseBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link ICSSParser#defaultBlock}.
	 * @param ctx the parse tree
	 */
	void enterDefaultBlock(ICSSParser.DefaultBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link ICSSParser#defaultBlock}.
	 * @param ctx the parse tree
	 */
	void exitDefaultBlock(ICSSParser.DefaultBlockContext ctx);
}