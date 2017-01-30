// Generated from XPath.g4 by ANTLR 4.6
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class XPathLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, WS=14, AND=15, OR=16, EQ=17, IS=18, 
		NOT=19, SSL=20, DSL=21, NAME=22, FILENAME=23;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "WS", "AND", "OR", "EQ", "IS", "NOT", 
		"SSL", "DSL", "NAME", "FILENAME"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'doc('", "')/'", "')//'", "'@'", "'.'", "'..'", "'*'", "'text()'", 
		"'('", "')'", "'['", "']'", "','", null, "'and'", "'or'", null, null, 
		"'not'", "'/'", "'//'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, "WS", "AND", "OR", "EQ", "IS", "NOT", "SSL", "DSL", "NAME", 
		"FILENAME"
	};
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


	public XPathLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "XPath.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\31\u008a\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3"+
		"\r\3\16\3\16\3\17\6\17Y\n\17\r\17\16\17Z\3\17\3\17\3\20\3\20\3\20\3\20"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\5\22i\n\22\3\23\3\23\3\23\3\23\5\23o\n"+
		"\23\3\24\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\7\27|\n\27"+
		"\f\27\16\27\177\13\27\3\30\6\30\u0082\n\30\r\30\16\30\u0083\3\30\3\30"+
		"\3\30\3\30\3\30\2\2\31\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\3\2\6\5\2"+
		"\13\f\17\17\"\"\5\2C\\aac|\5\2\62;C\\c|\5\2\61;C\\c|\u008e\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2"+
		"\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2"+
		"\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2"+
		"\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\3\61\3\2\2"+
		"\2\5\66\3\2\2\2\79\3\2\2\2\t=\3\2\2\2\13?\3\2\2\2\rA\3\2\2\2\17D\3\2\2"+
		"\2\21F\3\2\2\2\23M\3\2\2\2\25O\3\2\2\2\27Q\3\2\2\2\31S\3\2\2\2\33U\3\2"+
		"\2\2\35X\3\2\2\2\37^\3\2\2\2!b\3\2\2\2#h\3\2\2\2%n\3\2\2\2\'p\3\2\2\2"+
		")t\3\2\2\2+v\3\2\2\2-y\3\2\2\2/\u0081\3\2\2\2\61\62\7f\2\2\62\63\7q\2"+
		"\2\63\64\7e\2\2\64\65\7*\2\2\65\4\3\2\2\2\66\67\7+\2\2\678\7\61\2\28\6"+
		"\3\2\2\29:\7+\2\2:;\7\61\2\2;<\7\61\2\2<\b\3\2\2\2=>\7B\2\2>\n\3\2\2\2"+
		"?@\7\60\2\2@\f\3\2\2\2AB\7\60\2\2BC\7\60\2\2C\16\3\2\2\2DE\7,\2\2E\20"+
		"\3\2\2\2FG\7v\2\2GH\7g\2\2HI\7z\2\2IJ\7v\2\2JK\7*\2\2KL\7+\2\2L\22\3\2"+
		"\2\2MN\7*\2\2N\24\3\2\2\2OP\7+\2\2P\26\3\2\2\2QR\7]\2\2R\30\3\2\2\2ST"+
		"\7_\2\2T\32\3\2\2\2UV\7.\2\2V\34\3\2\2\2WY\t\2\2\2XW\3\2\2\2YZ\3\2\2\2"+
		"ZX\3\2\2\2Z[\3\2\2\2[\\\3\2\2\2\\]\b\17\2\2]\36\3\2\2\2^_\7c\2\2_`\7p"+
		"\2\2`a\7f\2\2a \3\2\2\2bc\7q\2\2cd\7t\2\2d\"\3\2\2\2ei\7?\2\2fg\7g\2\2"+
		"gi\7s\2\2he\3\2\2\2hf\3\2\2\2i$\3\2\2\2jk\7?\2\2ko\7?\2\2lm\7k\2\2mo\7"+
		"u\2\2nj\3\2\2\2nl\3\2\2\2o&\3\2\2\2pq\7p\2\2qr\7q\2\2rs\7v\2\2s(\3\2\2"+
		"\2tu\7\61\2\2u*\3\2\2\2vw\7\61\2\2wx\7\61\2\2x,\3\2\2\2y}\t\3\2\2z|\t"+
		"\4\2\2{z\3\2\2\2|\177\3\2\2\2}{\3\2\2\2}~\3\2\2\2~.\3\2\2\2\177}\3\2\2"+
		"\2\u0080\u0082\t\5\2\2\u0081\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0081"+
		"\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\3\2\2\2\u0085\u0086\7\60\2\2"+
		"\u0086\u0087\7z\2\2\u0087\u0088\7o\2\2\u0088\u0089\7n\2\2\u0089\60\3\2"+
		"\2\2\b\2Zhn}\u0083\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}