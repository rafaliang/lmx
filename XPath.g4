/**
 * Define a grammar called XPath
 */
grammar XPath;

ap :
'doc("'fileName'")/'rp      #apSL
|'doc("'fileName'")//'rp    #apDSL
;

rp : 
tagName 	 #rpTAG
| '@'+attName #rpATT
| '.' 		 #rpDOT
| '..' 		 #rpDDOT
| '*'        #rpSTAR
| 'text()'   #rpTEXT
| '('rp')'   #rpPARA
| left=rp'/'right=rp 	 #rpSL
| left=rp'//'right=rp 	 #rpDSL
| rp'['f']'  #rpF
| left=rp','right=rp 	 #rpCOMMA
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
