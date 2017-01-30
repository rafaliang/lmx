// Generated from XPath.g4 by ANTLR 4.6
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XPathParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XPathVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code apSL}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApSL(XPathParser.ApSLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apDSL}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApDSL(XPathParser.ApDSLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpDOT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpDOT(XPathParser.RpDOTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpDDOT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpDDOT(XPathParser.RpDDOTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpTEXT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpTEXT(XPathParser.RpTEXTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpSL}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpSL(XPathParser.RpSLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpDSL}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpDSL(XPathParser.RpDSLContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpTAG}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpTAG(XPathParser.RpTAGContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpPARA}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpPARA(XPathParser.RpPARAContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpATT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpATT(XPathParser.RpATTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpSTAR}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpSTAR(XPathParser.RpSTARContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpF}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpF(XPathParser.RpFContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpCOMMA}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpCOMMA(XPathParser.RpCOMMAContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterIS}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterIS(XPathParser.FilterISContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterPara}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterPara(XPathParser.FilterParaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterRP(XPathParser.FilterRPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterEQ}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterEQ(XPathParser.FilterEQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterOR}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterOR(XPathParser.FilterORContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterAND}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterAND(XPathParser.FilterANDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterNOT}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterNOT(XPathParser.FilterNOTContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XPathParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#attName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttName(XPathParser.AttNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPathParser#fileName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFileName(XPathParser.FileNameContext ctx);
}