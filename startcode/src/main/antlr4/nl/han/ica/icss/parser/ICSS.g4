grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';

// Lexer
// Comments in this file are for me to read over it again to understand whats happening :)

// Lexer
variable: CAPITAL_IDENT ASSIGNMENT_OPERATOR expression SEMICOLON;

selector: ID_IDENT | CLASS_IDENT | LOWER_IDENT;

value: COLOR | PIXELSIZE | PERCENTAGE | SCALAR | TRUE | FALSE | CAPITAL_IDENT | LOWER_IDENT;

term: value (MUL value)*;

expression: term ((PLUS | MIN) term)*;

bodyItem: declaration | ifClause | variable;

ifClause   : IF BOX_BRACKET_OPEN value BOX_BRACKET_CLOSE
             OPEN_BRACE bodyItem* CLOSE_BRACE
             elseClause? ;

elseClause : ELSE OPEN_BRACE bodyItem* CLOSE_BRACE ;

declaration: LOWER_IDENT COLON expression SEMICOLON;

block: selector OPEN_BRACE bodyItem* CLOSE_BRACE;

statement: variable | block;

//--- PARSER: ---
stylesheet: statement* EOF;


//statement : variable | css_rule; // Either variable or css_rule
//
//variable: CAPITAL_IDENT ASSIGNMENT_OPERATOR expression SEMICOLON; // VarableName := #ffffff;
//
//css_rule: selector OPEN_BRACE body* CLOSE_BRACE; // background-color: #ff00ff;
//
//body: declaration | if_statement; // Either decleration like background-color: #ff00ff; or an If-statement.
//
//declaration: LOWER_IDENT COLON expression SEMICOLON; // see above.
//
//if_statement // if[Test] { // ... } else { // ... }
//    : IF BOX_BRACKET_OPEN condition BOX_BRACKET_CLOSE
//      OPEN_BRACE body* CLOSE_BRACE
//      else_statement?
//    ;
//
//else_statement: ELSE OPEN_BRACE body* CLOSE_BRACE;
//
//condition: CAPITAL_IDENT | TRUE | FALSE; // function condition
//
//selector: LOWER_IDENT | ID_IDENT | CLASS_IDENT; // # .help help
//
//expression: value (additiveExpression value)*; //
//
//value: COLOR | PIXELSIZE | PERCENTAGE | SCALAR | TRUE | FALSE | CAPITAL_IDENT | PLUS | MIN | MUL;
//
//multiplicative
//    : expression (MUL expression)*
//    ;
//
//additiveExpression
//    : multiplicative
//    | additiveExpression (PLUS) multiplicative*
//    | additiveExpression (MIN) multiplicative*
//    ;
//
////--- PARSER: ---
//stylesheet: statement* EOF;

