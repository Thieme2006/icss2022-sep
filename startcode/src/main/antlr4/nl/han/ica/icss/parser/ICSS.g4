grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
SWITCH: 'switch';
CASE: 'case';
SWITCH_DEFAULT: 'default';
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
EQUALS: '==';
GT: '>';
ST: '<';
GTE: '>=';
STE: '<=';
NEQUALS: '!=';

// Lexer
// Comments in this file are for me to read over it again to understand whats happening :)

//--- PARSER: ---
variable: CAPITAL_IDENT ASSIGNMENT_OPERATOR expression SEMICOLON;

selector: ID_IDENT | CLASS_IDENT | LOWER_IDENT;

value: COLOR | PIXELSIZE | PERCENTAGE | SCALAR | TRUE | FALSE | CAPITAL_IDENT | LOWER_IDENT;

term: value (MUL value)*;

expression: term ((PLUS | MIN) term)*;

bodyItem: declaration | ifClause | variable | switchCaseBlock;

ifClause   : IF BOX_BRACKET_OPEN if_condition BOX_BRACKET_CLOSE
             OPEN_BRACE bodyItem* CLOSE_BRACE
             elseClause? ;

comparison: value (EQUALS | NEQUALS | GT | GTE | ST | STE) value;

if_condition: comparison | TRUE | FALSE | value;

elseClause : ELSE OPEN_BRACE bodyItem* CLOSE_BRACE ;

declaration: LOWER_IDENT COLON expression SEMICOLON;

block: selector OPEN_BRACE bodyItem* CLOSE_BRACE;

statement: variable | block;

stylesheet: statement* EOF;

switchCaseBlock: SWITCH BOX_BRACKET_OPEN expression BOX_BRACKET_CLOSE
        OPEN_BRACE caseBlock* defaultBlock? CLOSE_BRACE ;

caseBlock: CASE expression COLON OPEN_BRACE bodyItem* CLOSE_BRACE;

defaultBlock: SWITCH_DEFAULT COLON OPEN_BRACE bodyItem* CLOSE_BRACE;