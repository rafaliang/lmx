/**
 * Define a grammar called XPath
 */
grammar XQuery;

xq:
Var
| StringConstant
| ap
| '(' xq ')'
| left=xq ',' right=xq
| xq '/' rp
| xq '//' rp
| '<'leftT=tagName'>' '{'xq'}' '</'rightT=tagName'>'
| forClause letClause? whereClause? returnClause
| letClause xq
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
left=xq EQ right=xq
| left=xq IS right=xq
| 'empty(' xq ')'
| 'some' Var 'in' xq (',' Var 'in' xq)* 'satisfies' cond
| '(' cond ')'
| leftCond=cond AND rightCond=cond
| leftCond=cond OR rightCond=cond
| NOT cond
;

ap :
'doc(\''fileName'\')/'rp    #apSL
|'doc("'fileName'")/'rp    #apSL
|'doc(\''fileName'\')//'rp   #apDSL
| 'doc("'fileName'")//'rp   #apDSL
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

fileName : FILENAME;
 
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
StringConstant : '\"' (StringCharacter+)? '\"';
StringCharacter: ~[\"\\@];

 /*
  * NAME
  */
//LETTER : [a-zA-Z_];
//LETTERANDDIGIT : [a-zA-Z0-9_-];
FILENAME : [a-zA-Z0-9/._]+'.xml';
NAME : [a-zA-Z_] [a-zA-Z0-9_-]*;


