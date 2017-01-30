/**
 * Define a grammar called XPath
 */
grammar XPath;

ap :
'doc('fileName')/'rp      #apSL
|'doc('fileName')//'rp    #apDSL
;

rp : 
tagName 	 #rpTAG
| '@'+attName #rpATT
| '.' 		 #rpDOT
| '..' 		 #rpDDOT
| '*'        #rpSTAR
| 'text()'   #rpTEXT
| '('rp')'   #rpPARA
| rp'/'rp 	 #rpSL
| rp'//'rp 	 #rpDSL
| rp'['f']'  #rpF
| rp','rp 	 #rpCOMMA
;

f:
rp		    #filterRP
| rp EQ rp  #filterEQ
| rp IS rp  #filterIS
| '('f')'   #filterPara
| f AND f   #filterAND
| f OR f    #filterOR
| NOT f     #filterNOT
;

tagName : NAME;  

attName : NAME;

fileName : FILENAME;

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
 
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

 /*
  * NAME
  */

NAME : [a-zA-Z_][a-zA-Z0-9]*;
FILENAME : [a-zA-Z0-9/]+'.xml';
