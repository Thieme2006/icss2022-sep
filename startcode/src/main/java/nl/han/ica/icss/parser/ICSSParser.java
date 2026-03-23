// Generated from C:/Users/aweus/Desktop/icss2022-sep/startcode/src/main/antlr4/nl/han/ica/icss/parser/ICSS.g4 by ANTLR 4.13.2
package nl.han.ica.icss.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class ICSSParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		IF=1, ELSE=2, SWITCH=3, CASE=4, SWITCH_DEFAULT=5, BOX_BRACKET_OPEN=6, 
		BOX_BRACKET_CLOSE=7, TRUE=8, FALSE=9, PIXELSIZE=10, PERCENTAGE=11, SCALAR=12, 
		COLOR=13, ID_IDENT=14, CLASS_IDENT=15, LOWER_IDENT=16, CAPITAL_IDENT=17, 
		WS=18, OPEN_BRACE=19, CLOSE_BRACE=20, SEMICOLON=21, COLON=22, PLUS=23, 
		MIN=24, MUL=25, ASSIGNMENT_OPERATOR=26;
	public static final int
		RULE_variable = 0, RULE_selector = 1, RULE_value = 2, RULE_term = 3, RULE_expression = 4, 
		RULE_bodyItem = 5, RULE_ifClause = 6, RULE_elseClause = 7, RULE_declaration = 8, 
		RULE_block = 9, RULE_statement = 10, RULE_stylesheet = 11, RULE_switchCaseBlock = 12, 
		RULE_caseBlock = 13, RULE_defaultBlock = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"variable", "selector", "value", "term", "expression", "bodyItem", "ifClause", 
			"elseClause", "declaration", "block", "statement", "stylesheet", "switchCaseBlock", 
			"caseBlock", "defaultBlock"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'if'", "'else'", "'switch'", "'case'", "'default'", "'['", "']'", 
			"'TRUE'", "'FALSE'", null, null, null, null, null, null, null, null, 
			null, "'{'", "'}'", "';'", "':'", "'+'", "'-'", "'*'", "':='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "IF", "ELSE", "SWITCH", "CASE", "SWITCH_DEFAULT", "BOX_BRACKET_OPEN", 
			"BOX_BRACKET_CLOSE", "TRUE", "FALSE", "PIXELSIZE", "PERCENTAGE", "SCALAR", 
			"COLOR", "ID_IDENT", "CLASS_IDENT", "LOWER_IDENT", "CAPITAL_IDENT", "WS", 
			"OPEN_BRACE", "CLOSE_BRACE", "SEMICOLON", "COLON", "PLUS", "MIN", "MUL", 
			"ASSIGNMENT_OPERATOR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ICSS.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ICSSParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VariableContext extends ParserRuleContext {
		public TerminalNode CAPITAL_IDENT() { return getToken(ICSSParser.CAPITAL_IDENT, 0); }
		public TerminalNode ASSIGNMENT_OPERATOR() { return getToken(ICSSParser.ASSIGNMENT_OPERATOR, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ICSSParser.SEMICOLON, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(CAPITAL_IDENT);
			setState(31);
			match(ASSIGNMENT_OPERATOR);
			setState(32);
			expression();
			setState(33);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SelectorContext extends ParserRuleContext {
		public TerminalNode ID_IDENT() { return getToken(ICSSParser.ID_IDENT, 0); }
		public TerminalNode CLASS_IDENT() { return getToken(ICSSParser.CLASS_IDENT, 0); }
		public TerminalNode LOWER_IDENT() { return getToken(ICSSParser.LOWER_IDENT, 0); }
		public SelectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selector; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterSelector(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitSelector(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitSelector(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SelectorContext selector() throws RecognitionException {
		SelectorContext _localctx = new SelectorContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_selector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 114688L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode COLOR() { return getToken(ICSSParser.COLOR, 0); }
		public TerminalNode PIXELSIZE() { return getToken(ICSSParser.PIXELSIZE, 0); }
		public TerminalNode PERCENTAGE() { return getToken(ICSSParser.PERCENTAGE, 0); }
		public TerminalNode SCALAR() { return getToken(ICSSParser.SCALAR, 0); }
		public TerminalNode TRUE() { return getToken(ICSSParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(ICSSParser.FALSE, 0); }
		public TerminalNode CAPITAL_IDENT() { return getToken(ICSSParser.CAPITAL_IDENT, 0); }
		public TerminalNode LOWER_IDENT() { return getToken(ICSSParser.LOWER_IDENT, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 212736L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> MUL() { return getTokens(ICSSParser.MUL); }
		public TerminalNode MUL(int i) {
			return getToken(ICSSParser.MUL, i);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			value();
			setState(44);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MUL) {
				{
				{
				setState(40);
				match(MUL);
				setState(41);
				value();
				}
				}
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(ICSSParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(ICSSParser.PLUS, i);
		}
		public List<TerminalNode> MIN() { return getTokens(ICSSParser.MIN); }
		public TerminalNode MIN(int i) {
			return getToken(ICSSParser.MIN, i);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_expression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			term();
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MIN) {
				{
				{
				setState(48);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MIN) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(49);
				term();
				}
				}
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BodyItemContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public IfClauseContext ifClause() {
			return getRuleContext(IfClauseContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public SwitchCaseBlockContext switchCaseBlock() {
			return getRuleContext(SwitchCaseBlockContext.class,0);
		}
		public BodyItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bodyItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterBodyItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitBodyItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitBodyItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BodyItemContext bodyItem() throws RecognitionException {
		BodyItemContext _localctx = new BodyItemContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_bodyItem);
		try {
			setState(59);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LOWER_IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(55);
				declaration();
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 2);
				{
				setState(56);
				ifClause();
				}
				break;
			case CAPITAL_IDENT:
				enterOuterAlt(_localctx, 3);
				{
				setState(57);
				variable();
				}
				break;
			case SWITCH:
				enterOuterAlt(_localctx, 4);
				{
				setState(58);
				switchCaseBlock();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfClauseContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(ICSSParser.IF, 0); }
		public TerminalNode BOX_BRACKET_OPEN() { return getToken(ICSSParser.BOX_BRACKET_OPEN, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode BOX_BRACKET_CLOSE() { return getToken(ICSSParser.BOX_BRACKET_CLOSE, 0); }
		public TerminalNode OPEN_BRACE() { return getToken(ICSSParser.OPEN_BRACE, 0); }
		public TerminalNode CLOSE_BRACE() { return getToken(ICSSParser.CLOSE_BRACE, 0); }
		public List<BodyItemContext> bodyItem() {
			return getRuleContexts(BodyItemContext.class);
		}
		public BodyItemContext bodyItem(int i) {
			return getRuleContext(BodyItemContext.class,i);
		}
		public ElseClauseContext elseClause() {
			return getRuleContext(ElseClauseContext.class,0);
		}
		public IfClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterIfClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitIfClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitIfClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfClauseContext ifClause() throws RecognitionException {
		IfClauseContext _localctx = new IfClauseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ifClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(IF);
			setState(62);
			match(BOX_BRACKET_OPEN);
			setState(63);
			value();
			setState(64);
			match(BOX_BRACKET_CLOSE);
			setState(65);
			match(OPEN_BRACE);
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 196618L) != 0)) {
				{
				{
				setState(66);
				bodyItem();
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(72);
			match(CLOSE_BRACE);
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(73);
				elseClause();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElseClauseContext extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(ICSSParser.ELSE, 0); }
		public TerminalNode OPEN_BRACE() { return getToken(ICSSParser.OPEN_BRACE, 0); }
		public TerminalNode CLOSE_BRACE() { return getToken(ICSSParser.CLOSE_BRACE, 0); }
		public List<BodyItemContext> bodyItem() {
			return getRuleContexts(BodyItemContext.class);
		}
		public BodyItemContext bodyItem(int i) {
			return getRuleContext(BodyItemContext.class,i);
		}
		public ElseClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterElseClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitElseClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitElseClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseClauseContext elseClause() throws RecognitionException {
		ElseClauseContext _localctx = new ElseClauseContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_elseClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(ELSE);
			setState(77);
			match(OPEN_BRACE);
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 196618L) != 0)) {
				{
				{
				setState(78);
				bodyItem();
				}
				}
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(84);
			match(CLOSE_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclarationContext extends ParserRuleContext {
		public TerminalNode LOWER_IDENT() { return getToken(ICSSParser.LOWER_IDENT, 0); }
		public TerminalNode COLON() { return getToken(ICSSParser.COLON, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(ICSSParser.SEMICOLON, 0); }
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			match(LOWER_IDENT);
			setState(87);
			match(COLON);
			setState(88);
			expression();
			setState(89);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public SelectorContext selector() {
			return getRuleContext(SelectorContext.class,0);
		}
		public TerminalNode OPEN_BRACE() { return getToken(ICSSParser.OPEN_BRACE, 0); }
		public TerminalNode CLOSE_BRACE() { return getToken(ICSSParser.CLOSE_BRACE, 0); }
		public List<BodyItemContext> bodyItem() {
			return getRuleContexts(BodyItemContext.class);
		}
		public BodyItemContext bodyItem(int i) {
			return getRuleContext(BodyItemContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			selector();
			setState(92);
			match(OPEN_BRACE);
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 196618L) != 0)) {
				{
				{
				setState(93);
				bodyItem();
				}
				}
				setState(98);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(99);
			match(CLOSE_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_statement);
		try {
			setState(103);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CAPITAL_IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				variable();
				}
				break;
			case ID_IDENT:
			case CLASS_IDENT:
			case LOWER_IDENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StylesheetContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ICSSParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public StylesheetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stylesheet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterStylesheet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitStylesheet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitStylesheet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StylesheetContext stylesheet() throws RecognitionException {
		StylesheetContext _localctx = new StylesheetContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_stylesheet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 245760L) != 0)) {
				{
				{
				setState(105);
				statement();
				}
				}
				setState(110);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(111);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SwitchCaseBlockContext extends ParserRuleContext {
		public TerminalNode SWITCH() { return getToken(ICSSParser.SWITCH, 0); }
		public TerminalNode BOX_BRACKET_OPEN() { return getToken(ICSSParser.BOX_BRACKET_OPEN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode BOX_BRACKET_CLOSE() { return getToken(ICSSParser.BOX_BRACKET_CLOSE, 0); }
		public TerminalNode OPEN_BRACE() { return getToken(ICSSParser.OPEN_BRACE, 0); }
		public TerminalNode CLOSE_BRACE() { return getToken(ICSSParser.CLOSE_BRACE, 0); }
		public List<CaseBlockContext> caseBlock() {
			return getRuleContexts(CaseBlockContext.class);
		}
		public CaseBlockContext caseBlock(int i) {
			return getRuleContext(CaseBlockContext.class,i);
		}
		public DefaultBlockContext defaultBlock() {
			return getRuleContext(DefaultBlockContext.class,0);
		}
		public SwitchCaseBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_switchCaseBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterSwitchCaseBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitSwitchCaseBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitSwitchCaseBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SwitchCaseBlockContext switchCaseBlock() throws RecognitionException {
		SwitchCaseBlockContext _localctx = new SwitchCaseBlockContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_switchCaseBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(SWITCH);
			setState(114);
			match(BOX_BRACKET_OPEN);
			setState(115);
			expression();
			setState(116);
			match(BOX_BRACKET_CLOSE);
			setState(117);
			match(OPEN_BRACE);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CASE) {
				{
				{
				setState(118);
				caseBlock();
				}
				}
				setState(123);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(125);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SWITCH_DEFAULT) {
				{
				setState(124);
				defaultBlock();
				}
			}

			setState(127);
			match(CLOSE_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CaseBlockContext extends ParserRuleContext {
		public TerminalNode CASE() { return getToken(ICSSParser.CASE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode COLON() { return getToken(ICSSParser.COLON, 0); }
		public TerminalNode OPEN_BRACE() { return getToken(ICSSParser.OPEN_BRACE, 0); }
		public TerminalNode CLOSE_BRACE() { return getToken(ICSSParser.CLOSE_BRACE, 0); }
		public List<BodyItemContext> bodyItem() {
			return getRuleContexts(BodyItemContext.class);
		}
		public BodyItemContext bodyItem(int i) {
			return getRuleContext(BodyItemContext.class,i);
		}
		public CaseBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_caseBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterCaseBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitCaseBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitCaseBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CaseBlockContext caseBlock() throws RecognitionException {
		CaseBlockContext _localctx = new CaseBlockContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_caseBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(CASE);
			setState(130);
			expression();
			setState(131);
			match(COLON);
			setState(132);
			match(OPEN_BRACE);
			setState(136);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 196618L) != 0)) {
				{
				{
				setState(133);
				bodyItem();
				}
				}
				setState(138);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(139);
			match(CLOSE_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DefaultBlockContext extends ParserRuleContext {
		public TerminalNode SWITCH_DEFAULT() { return getToken(ICSSParser.SWITCH_DEFAULT, 0); }
		public TerminalNode COLON() { return getToken(ICSSParser.COLON, 0); }
		public TerminalNode OPEN_BRACE() { return getToken(ICSSParser.OPEN_BRACE, 0); }
		public TerminalNode CLOSE_BRACE() { return getToken(ICSSParser.CLOSE_BRACE, 0); }
		public List<BodyItemContext> bodyItem() {
			return getRuleContexts(BodyItemContext.class);
		}
		public BodyItemContext bodyItem(int i) {
			return getRuleContext(BodyItemContext.class,i);
		}
		public DefaultBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_defaultBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).enterDefaultBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ICSSListener ) ((ICSSListener)listener).exitDefaultBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ICSSVisitor ) return ((ICSSVisitor<? extends T>)visitor).visitDefaultBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DefaultBlockContext defaultBlock() throws RecognitionException {
		DefaultBlockContext _localctx = new DefaultBlockContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_defaultBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			match(SWITCH_DEFAULT);
			setState(142);
			match(COLON);
			setState(143);
			match(OPEN_BRACE);
			setState(147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 196618L) != 0)) {
				{
				{
				setState(144);
				bodyItem();
				}
				}
				setState(149);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(150);
			match(CLOSE_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u001a\u0099\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003+\b"+
		"\u0003\n\u0003\f\u0003.\t\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0005"+
		"\u00043\b\u0004\n\u0004\f\u00046\t\u0004\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0003\u0005<\b\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0005\u0006D\b\u0006\n\u0006"+
		"\f\u0006G\t\u0006\u0001\u0006\u0001\u0006\u0003\u0006K\b\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0005\u0007P\b\u0007\n\u0007\f\u0007S\t\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\t\u0005\t_\b\t\n\t\f\tb\t\t\u0001\t\u0001\t\u0001\n\u0001"+
		"\n\u0003\nh\b\n\u0001\u000b\u0005\u000bk\b\u000b\n\u000b\f\u000bn\t\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0005\fx\b\f\n\f\f\f{\t\f\u0001\f\u0003\f~\b\f\u0001\f\u0001\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0005\r\u0087\b\r\n\r\f\r\u008a\t\r"+
		"\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0005"+
		"\u000e\u0092\b\u000e\n\u000e\f\u000e\u0095\t\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0000\u0000\u000f\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0018\u001a\u001c\u0000\u0003\u0001\u0000\u000e\u0010"+
		"\u0002\u0000\b\r\u0010\u0011\u0001\u0000\u0017\u0018\u0098\u0000\u001e"+
		"\u0001\u0000\u0000\u0000\u0002#\u0001\u0000\u0000\u0000\u0004%\u0001\u0000"+
		"\u0000\u0000\u0006\'\u0001\u0000\u0000\u0000\b/\u0001\u0000\u0000\u0000"+
		"\n;\u0001\u0000\u0000\u0000\f=\u0001\u0000\u0000\u0000\u000eL\u0001\u0000"+
		"\u0000\u0000\u0010V\u0001\u0000\u0000\u0000\u0012[\u0001\u0000\u0000\u0000"+
		"\u0014g\u0001\u0000\u0000\u0000\u0016l\u0001\u0000\u0000\u0000\u0018q"+
		"\u0001\u0000\u0000\u0000\u001a\u0081\u0001\u0000\u0000\u0000\u001c\u008d"+
		"\u0001\u0000\u0000\u0000\u001e\u001f\u0005\u0011\u0000\u0000\u001f \u0005"+
		"\u001a\u0000\u0000 !\u0003\b\u0004\u0000!\"\u0005\u0015\u0000\u0000\""+
		"\u0001\u0001\u0000\u0000\u0000#$\u0007\u0000\u0000\u0000$\u0003\u0001"+
		"\u0000\u0000\u0000%&\u0007\u0001\u0000\u0000&\u0005\u0001\u0000\u0000"+
		"\u0000\',\u0003\u0004\u0002\u0000()\u0005\u0019\u0000\u0000)+\u0003\u0004"+
		"\u0002\u0000*(\u0001\u0000\u0000\u0000+.\u0001\u0000\u0000\u0000,*\u0001"+
		"\u0000\u0000\u0000,-\u0001\u0000\u0000\u0000-\u0007\u0001\u0000\u0000"+
		"\u0000.,\u0001\u0000\u0000\u0000/4\u0003\u0006\u0003\u000001\u0007\u0002"+
		"\u0000\u000013\u0003\u0006\u0003\u000020\u0001\u0000\u0000\u000036\u0001"+
		"\u0000\u0000\u000042\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u0000"+
		"5\t\u0001\u0000\u0000\u000064\u0001\u0000\u0000\u00007<\u0003\u0010\b"+
		"\u00008<\u0003\f\u0006\u00009<\u0003\u0000\u0000\u0000:<\u0003\u0018\f"+
		"\u0000;7\u0001\u0000\u0000\u0000;8\u0001\u0000\u0000\u0000;9\u0001\u0000"+
		"\u0000\u0000;:\u0001\u0000\u0000\u0000<\u000b\u0001\u0000\u0000\u0000"+
		"=>\u0005\u0001\u0000\u0000>?\u0005\u0006\u0000\u0000?@\u0003\u0004\u0002"+
		"\u0000@A\u0005\u0007\u0000\u0000AE\u0005\u0013\u0000\u0000BD\u0003\n\u0005"+
		"\u0000CB\u0001\u0000\u0000\u0000DG\u0001\u0000\u0000\u0000EC\u0001\u0000"+
		"\u0000\u0000EF\u0001\u0000\u0000\u0000FH\u0001\u0000\u0000\u0000GE\u0001"+
		"\u0000\u0000\u0000HJ\u0005\u0014\u0000\u0000IK\u0003\u000e\u0007\u0000"+
		"JI\u0001\u0000\u0000\u0000JK\u0001\u0000\u0000\u0000K\r\u0001\u0000\u0000"+
		"\u0000LM\u0005\u0002\u0000\u0000MQ\u0005\u0013\u0000\u0000NP\u0003\n\u0005"+
		"\u0000ON\u0001\u0000\u0000\u0000PS\u0001\u0000\u0000\u0000QO\u0001\u0000"+
		"\u0000\u0000QR\u0001\u0000\u0000\u0000RT\u0001\u0000\u0000\u0000SQ\u0001"+
		"\u0000\u0000\u0000TU\u0005\u0014\u0000\u0000U\u000f\u0001\u0000\u0000"+
		"\u0000VW\u0005\u0010\u0000\u0000WX\u0005\u0016\u0000\u0000XY\u0003\b\u0004"+
		"\u0000YZ\u0005\u0015\u0000\u0000Z\u0011\u0001\u0000\u0000\u0000[\\\u0003"+
		"\u0002\u0001\u0000\\`\u0005\u0013\u0000\u0000]_\u0003\n\u0005\u0000^]"+
		"\u0001\u0000\u0000\u0000_b\u0001\u0000\u0000\u0000`^\u0001\u0000\u0000"+
		"\u0000`a\u0001\u0000\u0000\u0000ac\u0001\u0000\u0000\u0000b`\u0001\u0000"+
		"\u0000\u0000cd\u0005\u0014\u0000\u0000d\u0013\u0001\u0000\u0000\u0000"+
		"eh\u0003\u0000\u0000\u0000fh\u0003\u0012\t\u0000ge\u0001\u0000\u0000\u0000"+
		"gf\u0001\u0000\u0000\u0000h\u0015\u0001\u0000\u0000\u0000ik\u0003\u0014"+
		"\n\u0000ji\u0001\u0000\u0000\u0000kn\u0001\u0000\u0000\u0000lj\u0001\u0000"+
		"\u0000\u0000lm\u0001\u0000\u0000\u0000mo\u0001\u0000\u0000\u0000nl\u0001"+
		"\u0000\u0000\u0000op\u0005\u0000\u0000\u0001p\u0017\u0001\u0000\u0000"+
		"\u0000qr\u0005\u0003\u0000\u0000rs\u0005\u0006\u0000\u0000st\u0003\b\u0004"+
		"\u0000tu\u0005\u0007\u0000\u0000uy\u0005\u0013\u0000\u0000vx\u0003\u001a"+
		"\r\u0000wv\u0001\u0000\u0000\u0000x{\u0001\u0000\u0000\u0000yw\u0001\u0000"+
		"\u0000\u0000yz\u0001\u0000\u0000\u0000z}\u0001\u0000\u0000\u0000{y\u0001"+
		"\u0000\u0000\u0000|~\u0003\u001c\u000e\u0000}|\u0001\u0000\u0000\u0000"+
		"}~\u0001\u0000\u0000\u0000~\u007f\u0001\u0000\u0000\u0000\u007f\u0080"+
		"\u0005\u0014\u0000\u0000\u0080\u0019\u0001\u0000\u0000\u0000\u0081\u0082"+
		"\u0005\u0004\u0000\u0000\u0082\u0083\u0003\b\u0004\u0000\u0083\u0084\u0005"+
		"\u0016\u0000\u0000\u0084\u0088\u0005\u0013\u0000\u0000\u0085\u0087\u0003"+
		"\n\u0005\u0000\u0086\u0085\u0001\u0000\u0000\u0000\u0087\u008a\u0001\u0000"+
		"\u0000\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0088\u0089\u0001\u0000"+
		"\u0000\u0000\u0089\u008b\u0001\u0000\u0000\u0000\u008a\u0088\u0001\u0000"+
		"\u0000\u0000\u008b\u008c\u0005\u0014\u0000\u0000\u008c\u001b\u0001\u0000"+
		"\u0000\u0000\u008d\u008e\u0005\u0005\u0000\u0000\u008e\u008f\u0005\u0016"+
		"\u0000\u0000\u008f\u0093\u0005\u0013\u0000\u0000\u0090\u0092\u0003\n\u0005"+
		"\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0092\u0095\u0001\u0000\u0000"+
		"\u0000\u0093\u0091\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000"+
		"\u0000\u0094\u0096\u0001\u0000\u0000\u0000\u0095\u0093\u0001\u0000\u0000"+
		"\u0000\u0096\u0097\u0005\u0014\u0000\u0000\u0097\u001d\u0001\u0000\u0000"+
		"\u0000\r,4;EJQ`gly}\u0088\u0093";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}