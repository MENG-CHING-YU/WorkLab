package bmss.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bmss.query.BMSSTARAWQuery;
import bmss.query.impl.BMSSTARAWQueryImpl;
import bmss.table.BMSSTARAW;
import mali.util.commonbean;
import mali.util.getsysCode;
import tw.com.mali.IReport.IReportObject;
import tw.mali.prj.util.publicKeyUtility;

public class bmss20536010102r extends IReportObject {

    private String LICKIND;
    private String YYY;
    private String MM;
    private String ORGAN;
    private String REPSOURCE;

    /** 原本封裝的 SQL 出口，保留 */
    private String sSQL = "";

    /** 用途別排序 */
    private static final int[] PROCESS_ORDER_USAGE = {
        11, 12, 13, 14, 15, 16, 17, 18, 19, 21, 20, 99, 98
    };
    /** 構造別排序 */
    private static final int[] PROCESS_ORDER_STRUCTURE = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 99
    };

    public String getLICKIND() { return LICKIND; }
    public void setLICKIND(String lICKIND) { LICKIND = lICKIND; }

    public String getYYY() { return YYY; }
    public void setYYY(String yYY) { YYY = yYY; }

    public String getMM() { return MM; }
    public void setMM(String mM) { MM = mM; }

    public String getORGAN() { return ORGAN; }
    public void setORGAN(String oRGAN) { ORGAN = oRGAN; }

    public String getREPSOURCE() { return REPSOURCE; }
    public void setREPSOURCE(String rEPSOURCE) { REPSOURCE = rEPSOURCE; }

    @Override
    public String getJasperFile() {
        if ("20536010102".equals(REPSOURCE)) {
            return "/report/20536010102/20536_01_01_2.jasper";
        }
        return "";
    }

    HashMap subjectmap = new HashMap();

    @Override
    public List getData() {
    	if (REPSOURCE.equals("20536010102")) {
			getParameters().put("subreportpath1", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "20536010102" + File.separatorChar + "20536_02_01_2_subreport1.jasper");
			getParameters().put("subreportpath2", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "20536010102" + File.separatorChar + "20536_02_01_2_subreport2.jasper");
			getParameters().put("subreportpath3", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "20536010102" + File.separatorChar + "20536_02_01_2_subreport3.jasper");
			getParameters().put("subreportpath4", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "20536010102" + File.separatorChar + "20536_02_01_2_subreport4.jasper");
			getParameters().put("subreportpath5", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "20536010102" + File.separatorChar + "20536_02_01_2_subreport5.jasper");
		}

        getParameters().put("subjectRowData1", getSubData1());
        getParameters().put("subjectRowData2", getParameters().get("subjectRowData1"));
        getParameters().put("subjectRowData3", getParameters().get("subjectRowData1"));
        getParameters().put("subjectRowData4", getParameters().get("subjectRowData1"));
        getParameters().put("subjectRowData5", getParameters().get("subjectRowData1"));

        List<Map<String, Object>> masterData = new ArrayList<>();

        String DATE1 = String.format("中華民國 %s 年 %s 月", YYY, MM);
        subjectmap.put("ORGAN",
                commonbean.getCodeName(publicKeyUtility.KEY_PUB_主管機關Array, ORGAN));
        subjectmap.put("DATE1", DATE1);
        subjectmap.put("DATE2",
                "中華民國" + commonbean.ShowCDate(
                        commonbean.ShowDate(),
                        new String[] { "年", "月", "日" }) + "編製");

        masterData.add(subjectmap);
        return masterData;
    }

    /**
     * 目前示範先使用「建造－用途別」
     * 之後你可以拆成 getSubDataBuildByUsage() 等
     */
    public List<Map<String, Object>> getSubData1() {

        List<Map<String, Object>> subDataList = new ArrayList<>();
        Map submap = new HashMap();

        BMSSTARAWQuery qry = new BMSSTARAWQueryImpl(getMaliContext());

        // ⭐ 在這裡選要用哪一支 SQL
        sSQL = buildSqlBuildByUsage();
        sSQL = buildSqlBuildByStructure();
        sSQL = buildSqlDemolishByUsage();
        sSQL = buildSqlDemolishByStructure();

        qry.setSQLText(sSQL);
        BMSSTARAW[] datas = qry.getDataContent();

        if (datas != null && datas.length > 0) {
            submap.put("CITY", "總 計");
            int iNum = 4;

           
            subDataList.add(submap);
        }

        return subDataList;
    }

  

    /** 建造－用途別 */
    private String buildSqlBuildByUsage() {
        return
        		sSQL =
        	    "SELECT " +
        	    "    bmsta_usagecode, " +
        	    "    SUM(icount)        AS icount, " +
        	    "    SUM(ifloorArea)    AS ifloorArea, " +
        	    "    SUM(iconstruction) AS iconstruction " +
        	    "FROM ( " +
        	    "    SELECT " +
        	    "        bmsta_usagecode, " +
        	    "        COUNT(1) AS icount, " +
        	    "        ROUND( " +
        	    "            SUM( " +
        	    "                COALESCE(bmsta_totgrarea, 0) " +
        	    "              + COALESCE(bmsta_totdnarea, 0) " +
        	    "            ), 0 " +
        	    "        ) AS ifloorArea, " +
        	    "        ROUND(SUM(COALESCE(bmsta_contprice, 0)) / 1000, 0) AS iconstruction " +
        	    "    FROM bmsstaraw " +
        	    "    WHERE bmsta_stakind = '01' " +
        	    "      AND bmsta_yymm = '" + YYY + MM + "' " +
        	    "      AND bmsta_organ = '" + ORGAN + "' " +
        	    "      AND COALESCE(bmsta_usagecode, '') IN " +
        	    "          ('11','12','13','14','15','16','17','18','19','21','20','98') " +
        	    "    GROUP BY bmsta_usagecode " +

        	    "    UNION ALL " +

        	    "    SELECT " +
        	    "        '99' AS bmsta_usagecode, " +
        	    "        COUNT(1) AS icount, " +
        	    "        ROUND( " +
        	    "            SUM( " +
        	    "                COALESCE(bmsta_totgrarea, 0) " +
        	    "              + COALESCE(bmsta_totdnarea, 0) " +
        	    "            ), 0 " +
        	    "        ) AS ifloorArea, " +
        	    "        ROUND(SUM(COALESCE(bmsta_contprice, 0)) / 1000, 0) AS iconstruction " +
        	    "    FROM bmsstaraw " +
        	    "    WHERE bmsta_stakind = '01' " +
        	    "      AND bmsta_yymm = '" + YYY + MM + "' " +
        	    "      AND bmsta_organ = '" + ORGAN + "' " +
        	    "      AND COALESCE(bmsta_usagecode, '') NOT IN " +
        	    "          ('11','12','13','14','15','16','17','18','19','21','20') " +
        	    ") a " +
        	    "GROUP BY bmsta_usagecode";
    }

    /** 建造－構造別 */
    private String buildSqlBuildByStructure() {
        return
        		sSQL =
        	    "select " +
        	    "    bmsta_buildkindtype, " +
        	    "    sum(icount) icount, " +
        	    "    sum(ibuildsum) ibuildsum, " +
        	    "    sum(ifloorArea) ifloorArea, " +
        	    "    sum(iconstruction) iconstruction " +
        	    "from ( " +

        	    "    select " +
        	    "        bmsta_buildkindtype, " +
        	    "        count(1) icount, " +
        	    "        round(sum(coalesce(bmsta_buildsum, 0)), 0) ibuildsum, " +
        	    "        round(sum(coalesce(bmsta_totgrarea, 0) + coalesce(bmsta_totdnarea, 0)), 0) ifloorArea, " +
        	    "        round(sum(coalesce(bmsta_contprice, 0)) / 1000, 0) iconstruction " +
        	    "    from bmsstaraw " +
        	    "    where bmsta_stakind = '01' " +                    
        	    "      and bmsta_yymm = '" + YYY + MM + "' " +
        	    "      and coalesce(bmsta_buildkindtype, '') in ('1','2','3','4','5','6','7','8','9','98') " +
        	    "    group by bmsta_buildkindtype " +

        	    "    union all " +

        	    "    select " +
        	    "        '99' bmsta_buildkindtype, " +
        	    "        count(1) icount, " +
        	    "        round(sum(coalesce(bmsta_buildsum, 0)), 0) ibuildsum, " +
        	    "        round(sum(coalesce(bmsta_totgrarea, 0) + coalesce(bmsta_totdnarea, 0)), 0) ifloorArea, " +
        	    "        round(sum(coalesce(bmsta_contprice, 0)) / 1000, 0) iconstruction " +
        	    "    from bmsstaraw " +
        	    "    where bmsta_stakind = '01' " +               
        	    "      and bmsta_yymm = '" + YYY + MM + "' " +
        	    "      and coalesce(bmsta_buildkindtype, '') not in ('1','2','3','4','5','6','7','8','9') " +

        	    ") a " +
        	    "group by bmsta_buildkindtype";

    }

    /** 拆除－用途別 */
    private String buildSqlDemolishByUsage() {
        return
        		sSQL =
        	    "SELECT " +
        	    "    bmsta_usagecode, " +
        	    "    SUM(icount)        AS icount, " +
        	    "    SUM(ifloorArea)    AS ifloorArea, " +
        	    "    SUM(iconstruction) AS iconstruction " +
        	    "FROM ( " +
        	    "    SELECT " +
        	    "        bmsta_usagecode, " +
        	    "        COUNT(1) AS icount, " +
        	    "        ROUND( " +
        	    "            SUM( " +
        	    "                COALESCE(bmsta_totgrarea, 0) " +
        	    "              + COALESCE(bmsta_totdnarea, 0) " +
        	    "            ), 0 " +
        	    "        ) AS ifloorArea, " +
        	    "        ROUND(SUM(COALESCE(bmsta_contprice, 0)) / 1000, 0) AS iconstruction " +
        	    "    FROM bmsstaraw " +
        	    "    WHERE bmsta_stakind = '02' " +
        	    "      AND bmsta_yymm = '" + YYY + MM + "' " +
        	    "      AND bmsta_organ = '" + ORGAN + "' " +
        	    "      AND COALESCE(bmsta_usagecode, '') IN " +
        	    "          ('11','12','13','14','15','16','17','18','19','21','20','98') " +
        	    "    GROUP BY bmsta_usagecode " +

        	    "    UNION ALL " +

        	    "    SELECT " +
        	    "        '99' AS bmsta_usagecode, " +
        	    "        COUNT(1) AS icount, " +
        	    "        ROUND( " +
        	    "            SUM( " +
        	    "                COALESCE(bmsta_totgrarea, 0) " +
        	    "              + COALESCE(bmsta_totdnarea, 0) " +
        	    "            ), 0 " +
        	    "        ) AS ifloorArea, " +
        	    "        ROUND(SUM(COALESCE(bmsta_contprice, 0)) / 1000, 0) AS iconstruction " +
        	    "    FROM bmsstaraw " +
        	    "    WHERE bmsta_stakind = '02' " +
        	    "      AND bmsta_yymm = '" + YYY + MM + "' " +
        	    "      AND bmsta_organ = '" + ORGAN + "' " +
        	    "      AND COALESCE(bmsta_usagecode, '') NOT IN " +
        	    "          ('11','12','13','14','15','16','17','18','19','21','20') " +
        	    ") a " +
        	    "GROUP BY bmsta_usagecode";
    }

    /** 拆除－構造別 */
    private String buildSqlDemolishByStructure() {
        return
        		sSQL =
        	    "select " +
        	    "    bmsta_buildkindtype, " +
        	    "    sum(icount) icount, " +
        	    "    sum(ibuildsum) ibuildsum, " +
        	    "    sum(ifloorArea) ifloorArea, " +
        	    "    sum(iconstruction) iconstruction " +
        	    "from ( " +

        	    "    select " +
        	    "        bmsta_buildkindtype, " +
        	    "        count(1) icount, " +
        	    "        round(sum(coalesce(bmsta_buildsum, 0)), 0) ibuildsum, " +
        	    "        round(sum(coalesce(bmsta_totgrarea, 0) + coalesce(bmsta_totdnarea, 0)), 0) ifloorArea, " +
        	    "        round(sum(coalesce(bmsta_contprice, 0)) / 1000, 0) iconstruction " +
        	    "    from bmsstaraw " +
        	    "    where bmsta_stakind = '02' " +                    
        	    "      and bmsta_yymm = '" + YYY + MM + "' " +
        	    "      and coalesce(bmsta_buildkindtype, '') in ('1','2','3','4','5','6','7','8','9','98') " +
        	    "    group by bmsta_buildkindtype " +

        	    "    union all " +

        	    "    select " +
        	    "        '99' bmsta_buildkindtype, " +
        	    "        count(1) icount, " +
        	    "        round(sum(coalesce(bmsta_buildsum, 0)), 0) ibuildsum, " +
        	    "        round(sum(coalesce(bmsta_totgrarea, 0) + coalesce(bmsta_totdnarea, 0)), 0) ifloorArea, " +
        	    "        round(sum(coalesce(bmsta_contprice, 0)) / 1000, 0) iconstruction " +
        	    "    from bmsstaraw " +
        	    "    where bmsta_stakind = '02' " +                     
        	    "      and bmsta_yymm = '" + YYY + MM + "' " +
        	    "      and coalesce(bmsta_buildkindtype, '') not in ('1','2','3','4','5','6','7','8','9') " +

        	    ") a " +
        	    "group by bmsta_buildkindtype";

    }

    @Override
    public void run() {}

    @Override
    public String getDownloadFileName() {
        if ("20536010102".equals(REPSOURCE)) {
            return "核發建築物建造及拆除執照-縣市二級表";
        }
        return "";
    }
}
