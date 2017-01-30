
import org.antlr.v4.runtime.*;
    import org.antlr.v4.runtime.tree.*;
    public class test 
    {
        public static void main( String[] args) throws Exception 
        {
            //ssssss
            //ssss
            ANTLRInputStream input = new ANTLRInputStream( System.in);

            XPathLexer lexer = new XPathLexer(input);

            CommonTokenStream tokens = new CommonTokenStream(lexer);

            XPathParser parser = new XPathParser(tokens);
            ParseTree tree = parser.ap(); // begin parsing at rule 'exp'
            ParseTreeWalker walker = new ParseTreeWalker();
            testWalker tw = new testWalker();
            walker.walk(tw, tree);
            tw.getResult();
            //tree.
            System.out.println(tree.toStringTree(parser)); // print LISP-style tree
        }
    }
