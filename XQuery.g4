/**
 * Define a grammar called XPath
 */
grammar XQuery;

xq:
Var #xqVAR
| StringConstant #xqString
| ap #xqAP
| '(' xq ')' #xqPARA
| left=xq ',' right=xq #xqCOMMA
| xq '/' rp #xqSL
| xq '//' rp #xqDSL
| '<'leftT=tagName'>' '{'xq'}' '</'rightT=tagName'>' #xqTAG
| forClause letClause? whereClause? returnClause #xqFOR
| letClause xq #xqLET
;

forClause:
'for' Var 'in' xq (',' Var 'in' xq)*
;

letClause:
'let' Var ':=' xq (',' Var ':=' xq)*
;

whereClause:
'where' cond
;

returnClause:
'return' xq
;

cond:
left=xq EQ right=xq  #condEQ
| left=xq IS right=xq #condIS
| 'empty(' xq ')' #condEMPTY

| '(' cond ')' #condPARA
| leftCond=cond AND rightCond=cond #condAND
| leftCond=cond OR rightCond=cond #condOR
| NOT cond #condNOT
| 'some' Var 'in' xq (',' Var 'in' xq)* 'satisfies' cond #condSATISFY
;

ap :
'doc('fileName')/'rp    #apSL
|'document('fileName')/'rp    #apSL
| 'doc('fileName')//'rp   #apDSL
| 'document('fileName')//'rp   #apDSL
;

rp : 
tagName 	 #rpTAG
| '@'+attName #rpATT
| '.' 		 #rpDOT
| '..' 		 #rpDDOT
| '*'        #rpSTAR
| 'text()'   #rpTEXT
| '(' rp ')'   #rpPARA
| left=rp '/' right=rp 	 #rpSL
| left=rp '//' right=rp 	 #rpDSL
| rp '[' f ']'  #rpF
| left=rp ',' right=rp 	 #rpCOMMA
;

f:
rp		    #filterRP
| left=rp EQ right=rp  #filterEQ
| left=rp IS right=rp  #filterIS
| '('f')'   #filterPara
| leftF=f AND rightF=f   #filterAND
| leftF=f OR rightF=f    #filterOR
| NOT f     #filterNOT
;

tagName : NAME;  

attName : NAME;

fileName : StringConstant;
 
WS : [ \t\r\n]+ -> skip ;  // skip spaces, tabs, newlines
 
/*
 * OPERATIONS
  */
AND : 'and';
OR : 'or';
EQ : '=' | 'eq';
IS : '==' | 'is';
NOT : 'not';
//SSL : '/';
//DSL : '//';

Var: '$'NAME;
StringConstant : '\"' (Strings)? '\"' | '\'' (Strings)? '\'';
fragment Strings : StringCharacter+;
fragment StringCharacter: ~[\"\\@\'];

 /*
  * NAME
  */
//LETTER : [a-zA-Z_];
//LETTERANDDIGIT : [a-zA-Z0-9_-];
//FILENAME : [a-zA-Z0-9/._]+'.xml';
NAME : [a-zA-Z_] [a-zA-Z0-9_-]*;

fragment LETTER : [a-zA-Z_];
fragment LETTER_DIGIT : [a-zA-Z0-9_-];

