// Generated from XPath.g4 by ANTLR 4.6
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XPathParser}.
 */
public interface XPathListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code apSL}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterApSL(XPathParser.ApSLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code apSL}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitApSL(XPathParser.ApSLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code apDSL}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterApDSL(XPathParser.ApDSLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code apDSL}
	 * labeled alternative in {@link XPathParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitApDSL(XPathParser.ApDSLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpDOT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpDOT(XPathParser.RpDOTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpDOT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpDOT(XPathParser.RpDOTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpDDOT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpDDOT(XPathParser.RpDDOTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpDDOT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpDDOT(XPathParser.RpDDOTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpTEXT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpTEXT(XPathParser.RpTEXTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpTEXT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpTEXT(XPathParser.RpTEXTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpSL}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpSL(XPathParser.RpSLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpSL}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpSL(XPathParser.RpSLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpDSL}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpDSL(XPathParser.RpDSLContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpDSL}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpDSL(XPathParser.RpDSLContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpTAG}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpTAG(XPathParser.RpTAGContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpTAG}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpTAG(XPathParser.RpTAGContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpPARA}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpPARA(XPathParser.RpPARAContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpPARA}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpPARA(XPathParser.RpPARAContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpATT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpATT(XPathParser.RpATTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpATT}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpATT(XPathParser.RpATTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpSTAR}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpSTAR(XPathParser.RpSTARContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpSTAR}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpSTAR(XPathParser.RpSTARContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpF}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpF(XPathParser.RpFContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpF}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpF(XPathParser.RpFContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpCOMMA}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpCOMMA(XPathParser.RpCOMMAContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpCOMMA}
	 * labeled alternative in {@link XPathParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpCOMMA(XPathParser.RpCOMMAContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterIS}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterIS(XPathParser.FilterISContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterIS}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterIS(XPathParser.FilterISContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterPara}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterPara(XPathParser.FilterParaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterPara}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterPara(XPathParser.FilterParaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterRP(XPathParser.FilterRPContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterRP}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterRP(XPathParser.FilterRPContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterEQ}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterEQ(XPathParser.FilterEQContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterEQ}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterEQ(XPathParser.FilterEQContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterOR}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterOR(XPathParser.FilterORContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterOR}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterOR(XPathParser.FilterORContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterAND}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterAND(XPathParser.FilterANDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterAND}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterAND(XPathParser.FilterANDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterNOT}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterNOT(XPathParser.FilterNOTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterNOT}
	 * labeled alternative in {@link XPathParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterNOT(XPathParser.FilterNOTContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#tagName}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XPathParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#tagName}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XPathParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#attName}.
	 * @param ctx the parse tree
	 */
	void enterAttName(XPathParser.AttNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#attName}.
	 * @param ctx the parse tree
	 */
	void exitAttName(XPathParser.AttNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPathParser#fileName}.
	 * @param ctx the parse tree
	 */
	void enterFileName(XPathParser.FileNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPathParser#fileName}.
	 * @param ctx the parse tree
	 */
	void exitFileName(XPathParser.FileNameContext ctx);
}