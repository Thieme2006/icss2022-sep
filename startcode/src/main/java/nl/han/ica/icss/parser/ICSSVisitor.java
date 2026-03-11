// Generated from C:/Users/aweus/Desktop/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
package nl.han.ica.icss.parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ICSSParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ICSSVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ICSSParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(ICSSParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(ICSSParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#css_rule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCss_rule(ICSSParser.Css_ruleContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBody(ICSSParser.BodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(ICSSParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#if_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_statement(ICSSParser.If_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(ICSSParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#selector}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelector(ICSSParser.SelectorContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(ICSSParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(ICSSParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#wiskunde}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWiskunde(ICSSParser.WiskundeContext ctx);
	/**
	 * Visit a parse tree produced by {@link ICSSParser#stylesheet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStylesheet(ICSSParser.StylesheetContext ctx);
}