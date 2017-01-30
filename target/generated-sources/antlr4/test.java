
import java.util.List;

import org.antlr.v4.runtime.*;
    import org.antlr.v4.runtime.tree.*;
import org.w3c.dom.Node;
    public class test 
    {
        public static void main( String[] args) throws Exception 
        {
            ANTLRInputStream input = new ANTLRInputStream( System.in);

            XPathLexer lexer = new XPathLexer(input);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            XPathParser parser = new XPathParser(tokens);
            ParseTree tree = parser.ap(); // begin parsing at rule 'exp'
            ParseTreeWalker walker = new ParseTreeWalker();
            testWalker tw = new testWalker();
            //walker.walk(tw, tree);
            //tw.getResult();
            //testWalker2 tw2 = new testWalker2();
            testWalker3 tw3 = new testWalker3();
            //walker.walk(tw3, tree);
            List<Node> res = tw3.visit(tree);
            System.out.println(res.size());
            for (Node node:res)
            	System.out.println(node.toString());
            System.out.println(tree.toStringTree(parser)); // print LISP-style tree
        }
        
    }
