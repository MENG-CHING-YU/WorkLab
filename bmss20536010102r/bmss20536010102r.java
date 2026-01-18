package bmss.report;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bmss.query.BMSSTARAWQuery;
import bmss.query.impl.BMSSTARAWQueryImpl;
import bmss.table.BMSSTARAW;
import mali.util.commonbean;
import tw.com.mali.IReport.IReportObject;
import tw.mali.prj.util.publicKeyUtility;

public class bmss20536010102r extends IReportObject {

    private String LICKIND;
    private String YYY;
    private String MM;
    private String ORGAN;
    private String REPSOURCE;

    private String sSQL = "";

    /** 用途別排序 */
    private static final int[] PROCESS_ORDER_USAGE = {
        11, 12, 13, 14, 15, 16, 17, 18, 19, 21, 20, 99, 98
    };
    
    /** 構造別排序 */
    private static final int[] PROCESS_ORDER_STRUCTURE = {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 99
    };

    private static final String[] ITEM_NAMES_USAGE_MAIN = { "件數", "總樓地板面積", "工程造價" };
    private static final String[] ITEM_NAMES_USAGE_SUB = { "件數", "總樓地板面積", "工程造價" };
    private static final String[] ITEM_NAMES_STRUCTURE_BUILD = { "件數", "棟數", "總樓地板面積", "工程造價" };
    private static final String[] ITEM_NAMES_USAGE_DEMOLISH = { "件數", "戶數", "總樓地板面積" };
    private static final String[] ITEM_NAMES_STRUCTURE_DEMOLISH = { "件數", "棟數", "總樓地板面積" };

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

    
@Override
public List getData() {
    if (REPSOURCE.equals("20536010102")) {
        // 設定子報表路徑
        String basePath = getMaliContext().getWebRealPath() + "report" + File.separatorChar + "20536010102" + File.separatorChar;
         
        getParameters().put("subreportpath1", basePath + "20536_02_01_2_subreport1.jasper");
        getParameters().put("subreportpath2", basePath + "20536_02_01_2_subreport2.jasper");
        getParameters().put("subreportpath3", basePath + "20536_02_01_2_subreport3.jasper");
        getParameters().put("subreportpath4", basePath + "20536_02_01_2_subreport4.jasper");
        getParameters().put("subreportpath5", basePath + "20536_02_01_2_subreport5.jasper");
    }

 Map<String, Object> subjectmap = new HashMap<>();
    getParameters().put("subjectRowData1", getSubDataBuildUsageMain());    // 建造-用途別(主)
    getParameters().put("subjectRowData2", getSubDataBuildUsageSub());     // 建造-用途別(次)
    getParameters().put("subjectRowData3", getSubDataBuildStructure());    // 建造-構造別
    getParameters().put("subjectRowData4", getSubDataDemolishUsage());     // 拆除-用途別
    getParameters().put("subjectRowData5", getSubDataDemolishStructure()); // 拆除-構造別

    // 主報表資料
    List<Map<String, Object>> masterData = new ArrayList<>();

    String DATE1 = String.format("中華民國 %s 年 %s 月", YYY, MM);
    subjectmap.put("ORGAN", commonbean.getCodeName(publicKeyUtility.KEY_PUB_主管機關Array, ORGAN));
    subjectmap.put("DATE1", DATE1);
    subjectmap.put("DATE2", "中華民國" + commonbean.ShowCDate(commonbean.ShowDate(), new String[] { "年", "月", "日" }) + "編製");

    masterData.add(subjectmap);
    return masterData;
}
    public List<Map<String, Object>> getSubDataBuildUsageMain() {
        Map<String, long[]> usageData = fetchUsageData(buildSqlBuildByUsage());
        List<Map<String, Object>> subDataList = new ArrayList<>();
        subDataList.add(buildUsageRow(ITEM_NAMES_USAGE_MAIN[0], usageData, 0, 7, true, 0));
        subDataList.add(buildUsageRow(ITEM_NAMES_USAGE_MAIN[1], usageData, 0, 7, true, 2));
        subDataList.add(buildUsageRow(ITEM_NAMES_USAGE_MAIN[2], usageData, 0, 7, true, 3));
        return subDataList;
    }

    public List<Map<String, Object>> getSubDataBuildUsageSub() {
        Map<String, long[]> usageData = fetchUsageData(buildSqlBuildByUsage());
        List<Map<String, Object>> subDataList = new ArrayList<>();
        subDataList.add(buildUsageRow(ITEM_NAMES_USAGE_SUB[0], usageData, 7, PROCESS_ORDER_USAGE.length - 7, false, 0));
        subDataList.add(buildUsageRow(ITEM_NAMES_USAGE_SUB[1], usageData, 7, PROCESS_ORDER_USAGE.length - 7, false, 2));
        subDataList.add(buildUsageRow(ITEM_NAMES_USAGE_SUB[2], usageData, 7, PROCESS_ORDER_USAGE.length - 7, false, 3));
        return subDataList;
    }

    public List<Map<String, Object>> getSubDataBuildStructure() {
        Map<String, long[]> structureData = fetchStructureData(buildSqlBuildByStructure());
        List<Map<String, Object>> subDataList = new ArrayList<>();
        subDataList.add(buildStructureRow(ITEM_NAMES_STRUCTURE_BUILD[0], structureData, 0, 6, true, 0));
        subDataList.add(buildStructureRow(ITEM_NAMES_STRUCTURE_BUILD[1], structureData, 0, 6, true, 1));
        subDataList.add(buildStructureRow(ITEM_NAMES_STRUCTURE_BUILD[2], structureData, 0, 6, true, 2));
        subDataList.add(buildStructureRow(ITEM_NAMES_STRUCTURE_BUILD[3], structureData, 0, 6, true, 3));
        return subDataList;
    }

    public List<Map<String, Object>> getSubDataDemolishUsage() {
        Map<String, long[]> usageData = fetchUsageData(buildSqlDemolishByUsage());
        List<Map<String, Object>> subDataList = new ArrayList<>();

        long[] counts = buildUsageDemolishRow(usageData, 0);
        long[] houses = buildUsageDemolishRow(usageData, 1);
        long[] areas = buildUsageDemolishRow(usageData, 2);

        addRow(subDataList, ITEM_NAMES_USAGE_DEMOLISH[0], counts);
        addRow(subDataList, ITEM_NAMES_USAGE_DEMOLISH[1], houses);
        addRow(subDataList, ITEM_NAMES_USAGE_DEMOLISH[2], areas);

        return subDataList;
    }

    public List<Map<String, Object>> getSubDataDemolishStructure() {
        Map<String, long[]> structureData = fetchStructureData(buildSqlDemolishByStructure());
        List<Map<String, Object>> subDataList = new ArrayList<>();
        subDataList.add(buildStructureRow(ITEM_NAMES_STRUCTURE_DEMOLISH[0], structureData, 0, 6, true, 0));
        subDataList.add(buildStructureRow(ITEM_NAMES_STRUCTURE_DEMOLISH[1], structureData, 0, 6, true, 1));
        subDataList.add(buildStructureRow(ITEM_NAMES_STRUCTURE_DEMOLISH[2], structureData, 0, 6, true, 2));
        return subDataList;
    }

    /** 建造－用途別 */
    private String buildSqlBuildByUsage() {
        return sSQL =
            "SELECT bmsta_usagecode, " +
            "  SUM(icount) icount, " +
            "  SUM(ifloorArea) ifloorArea, " +
            "  SUM(iconstruction) iconstruction " +
            "FROM ( " +
            "  SELECT bmsta_usagecode, " +
            "    COUNT(1) icount, " +
            "    ROUND(SUM(COALESCE(bmsta_totgrarea, 0) + COALESCE(bmsta_totdnarea, 0)), 0) ifloorArea, " +
            "    ROUND(SUM(COALESCE(bmsta_contprice, 0)) / 1000, 0) iconstruction " +
            "  FROM bmsstaraw " +
            "  WHERE bmsta_stakind = '01' " +
            "    AND bmsta_yymm = '" + YYY + MM + "' " +
            "    AND bmsta_organ = '" + ORGAN + "' " +
            "    AND COALESCE(bmsta_usagecode, '') IN ('11','12','13','14','15','16','17','18','19','21','20','98') " +
            "  GROUP BY bmsta_usagecode " +
            "  UNION ALL " +
            "  SELECT '99' bmsta_usagecode, " +
            "    COUNT(1) icount, " +
            "    ROUND(SUM(COALESCE(bmsta_totgrarea, 0) + COALESCE(bmsta_totdnarea, 0)), 0) ifloorArea, " +
            "    ROUND(SUM(COALESCE(bmsta_contprice, 0)) / 1000, 0) iconstruction " +
            "  FROM bmsstaraw " +
            "  WHERE bmsta_stakind = '01' " +
            "    AND bmsta_yymm = '" + YYY + MM + "' " +
            "    AND bmsta_organ = '" + ORGAN + "' " +
            "    AND COALESCE(bmsta_usagecode, '') NOT IN ('11','12','13','14','15','16','17','18','19','21','20') " +
            ") a " +
            "GROUP BY bmsta_usagecode";
    }

    /** 建造－構造別 */
    private String buildSqlBuildByStructure() {
        return sSQL =
            "SELECT bmsta_buildkindtype, " +
            "  SUM(icount) icount, " +
            "  SUM(ibuildsum) ibuildsum, " +
            "  SUM(ifloorArea) ifloorArea, " +
            "  SUM(iconstruction) iconstruction " +
            "FROM ( " +
            "  SELECT bmsta_buildkindtype, " +
            "    COUNT(1) icount, " +
            "    ROUND(SUM(COALESCE(bmsta_buildsum, 0)), 0) ibuildsum, " +
            "    ROUND(SUM(COALESCE(bmsta_totgrarea, 0) + COALESCE(bmsta_totdnarea, 0)), 0) ifloorArea, " +
            "    ROUND(SUM(COALESCE(bmsta_contprice, 0)) / 1000, 0) iconstruction " +
            "  FROM bmsstaraw " +
            "  WHERE bmsta_stakind = '01' " +
            "    AND bmsta_yymm = '" + YYY + MM + "' " +
            "    AND bmsta_organ = '" + ORGAN + "' " +
            "    AND COALESCE(bmsta_buildkindtype, '') IN ('1','2','3','4','5','6','7','8','9','98') " +
            "  GROUP BY bmsta_buildkindtype " +
            "  UNION ALL " +
            "  SELECT '99' bmsta_buildkindtype, " +
            "    COUNT(1) icount, " +
            "    ROUND(SUM(COALESCE(bmsta_buildsum, 0)), 0) ibuildsum, " +
            "    ROUND(SUM(COALESCE(bmsta_totgrarea, 0) + COALESCE(bmsta_totdnarea, 0)), 0) ifloorArea, " +
            "    ROUND(SUM(COALESCE(bmsta_contprice, 0)) / 1000, 0) iconstruction " +
            "  FROM bmsstaraw " +
            "  WHERE bmsta_stakind = '01' " +
            "    AND bmsta_yymm = '" + YYY + MM + "' " +
            "    AND bmsta_organ = '" + ORGAN + "' " +
            "    AND COALESCE(bmsta_buildkindtype, '') NOT IN ('1','2','3','4','5','6','7','8','9') " +
            ") a " +
            "GROUP BY bmsta_buildkindtype";
    }

    /** 拆除－用途別 */
    private String buildSqlDemolishByUsage() {
        return sSQL =
            "SELECT bmsta_usagecode, " +
            "  SUM(icount) AS icount, " +
            "  SUM(itothousesum) AS itothousesum, " +
            "  SUM(ifloorArea) AS ifloorArea " +
            "FROM ( " +
            "  SELECT bmsta_usagecode, " +
            "    COUNT(1) AS icount, " +
            "    ROUND(SUM(COALESCE(bmsta_tothousesum, 0)), 0) AS itothousesum, " +
            "    ROUND(SUM(COALESCE(bmsta_totgrarea, 0) + COALESCE(bmsta_totdnarea, 0)), 0) AS ifloorArea " +
            "  FROM bmsstaraw " +
            "  WHERE bmsta_stakind = '04' " +
            "    AND bmsta_yymm = '" + YYY + MM + "' " +
            "    AND bmsta_organ = '" + ORGAN + "' " +
            "    AND COALESCE(bmsta_usagecode, '') IN ('11','12','13','14','15','16','17','18','19','21','20','98') " +
            "  GROUP BY bmsta_usagecode " +
            "  UNION ALL " +
            "  SELECT '99' AS bmsta_usagecode, " +
            "    COUNT(1) AS icount, " +
            "    ROUND(SUM(COALESCE(bmsta_tothousesum, 0)), 0) AS itothousesum, " +
            "    ROUND(SUM(COALESCE(bmsta_totgrarea, 0) + COALESCE(bmsta_totdnarea, 0)), 0) AS ifloorArea " +
            "  FROM bmsstaraw " +
            "  WHERE bmsta_stakind = '04' " +
            "    AND bmsta_yymm = '" + YYY + MM + "' " +
            "    AND bmsta_organ = '" + ORGAN + "' " +
            "    AND COALESCE(bmsta_usagecode, '') NOT IN ('11','12','13','14','15','16','17','18','19','21','20') " +
            ") a " +
            "GROUP BY bmsta_usagecode";
    }

    /** 拆除－構造別 */
    private String buildSqlDemolishByStructure() {
        return sSQL =
            "SELECT bmsta_buildkindtype, " +
            "  SUM(icount) icount, " +
            "  SUM(ibuildsum) ibuildsum, " +
            "  SUM(ifloorArea) ifloorArea " +
            "FROM ( " +
            "  SELECT bmsta_buildkindtype, " +
            "    COUNT(1) icount, " +
            "    ROUND(SUM(COALESCE(bmsta_buildsum, 0)), 0) ibuildsum, " +
            "    ROUND(SUM(COALESCE(bmsta_totgrarea, 0) + COALESCE(bmsta_totdnarea, 0)), 0) ifloorArea " +
            "  FROM bmsstaraw " +
            "  WHERE bmsta_stakind = '04' " +
            "    AND bmsta_yymm = '" + YYY + MM + "' " +
            "    AND bmsta_organ = '" + ORGAN + "' " +
            "    AND COALESCE(bmsta_buildkindtype, '') IN ('1','2','3','4','5','6','7','8','9','98') " +
            "  GROUP BY bmsta_buildkindtype " +
            "  UNION ALL " +
            "  SELECT '99' bmsta_buildkindtype, " +
            "    COUNT(1) icount, " +
            "    ROUND(SUM(COALESCE(bmsta_buildsum, 0)), 0) ibuildsum, " +
            "    ROUND(SUM(COALESCE(bmsta_totgrarea, 0) + COALESCE(bmsta_totdnarea, 0)), 0) ifloorArea " +
            "  FROM bmsstaraw " +
            "  WHERE bmsta_stakind = '04' " +
            "    AND bmsta_yymm = '" + YYY + MM + "' " +
            "    AND bmsta_organ = '" + ORGAN + "' " +
            "    AND COALESCE(bmsta_buildkindtype, '') NOT IN ('1','2','3','4','5','6','7','8','9') " +
            ") a " +
            "GROUP BY bmsta_buildkindtype";
    }

    private Map<String, long[]> fetchUsageData(String sql) {
        BMSSTARAWQuery qry = new BMSSTARAWQueryImpl(getMaliContext());
        qry.setSQLText(sql);
        BMSSTARAW[] datas = qry.getDataContent();

        Map<String, long[]> result = new HashMap<>();
        if (datas == null) {
            return result;
        }

        for (BMSSTARAW data : datas) {
            String code = data.getStringfield("BMSTA_USAGECODE");
            if (code == null) {
                continue;
            }
            long[] values = result.computeIfAbsent(code, key -> new long[4]);
            values[0] += parseLong(data.getStringfield("ICOUNT"));
            values[1] += parseLong(data.getStringfield("ITOTHOUSESUM"));
            values[2] += parseLong(data.getStringfield("IFLOORAREA"));
            values[3] += parseLong(data.getStringfield("ICONSTRUCTION"));
        }
        return result;
    }

    private Map<String, long[]> fetchStructureData(String sql) {
        BMSSTARAWQuery qry = new BMSSTARAWQueryImpl(getMaliContext());
        qry.setSQLText(sql);
        BMSSTARAW[] datas = qry.getDataContent();

        Map<String, long[]> result = new HashMap<>();
        if (datas == null) {
            return result;
        }

        for (BMSSTARAW data : datas) {
            String code = data.getStringfield("BMSTA_BUILDKINDTYPE");
            if (code == null) {
                continue;
            }
            long[] values = result.computeIfAbsent(code, key -> new long[4]);
            values[0] += parseLong(data.getStringfield("ICOUNT"));
            values[1] += parseLong(data.getStringfield("IBUILDSUM"));
            values[2] += parseLong(data.getStringfield("IFLOORAREA"));
            values[3] += parseLong(data.getStringfield("ICONSTRUCTION"));
        }
        return result;
    }

    private void addRow(List<Map<String, Object>> dataList, String itemName, long[] values) {
        Map<String, Object> row = new HashMap<>();
        row.put("ITEMNAME", itemName);
        for (int i = 0; i < values.length; i++) {
            row.put("NUM" + (i + 1), formatNumber(values[i]));
        }
        dataList.add(row);
    }

    private long getValue(Map<String, long[]> data, String code, int index) {
        long[] values = data.get(code);
        if (values == null || index >= values.length) {
            return 0;
        }
        return values[index];
    }

    private long sumAll(Map<String, long[]> data, int index) {
        long total = 0;
        for (long[] values : data.values()) {
            if (index < values.length) {
                total += values[index];
            }
        }
        return total;
    }

    private long[] buildUsageDemolishRow(Map<String, long[]> data, int index) {
        long[] values = new long[3];
        long total = sumAll(data, index);
        long housing = getValue(data, "19", index) + getValue(data, "21", index);
        values[0] = total;
        values[1] = housing;
        values[2] = total - housing;
        return values;
    }

    private String formatNumber(long value) {
        return publicKeyUtility.getStcNumFmt(String.valueOf(value), 0);
    }

    private long parseLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return 0;
        }
        try {
            return Long.parseLong(value.replace(",", "").trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private Map<String, Object> buildUsageRow(String itemName, Map<String, long[]> data, int start, int length,
            boolean includeTotal, int index) {
        Map<String, Object> row = new HashMap<>();
        row.put("ITEMNAME", itemName);

        int numIndex = 1;
        if (includeTotal) {
            row.put("NUM" + numIndex++, formatNumber(sumAll(data, index)));
        }
        for (int i = start; i < start + length; i++) {
            String code = String.valueOf(PROCESS_ORDER_USAGE[i]);
            row.put("NUM" + numIndex++, formatNumber(getValue(data, code, index)));
        }
        return row;
    }

    private Map<String, Object> buildStructureRow(String itemName, Map<String, long[]> data, int start, int length,
            boolean includeTotal, int index) {
        Map<String, Object> row = new HashMap<>();
        row.put("ITEMNAME", itemName);

        int numIndex = 1;
        if (includeTotal) {
            row.put("NUM" + numIndex++, formatNumber(sumAll(data, index)));
        }
        for (int i = start; i < start + length; i++) {
            String code = String.valueOf(PROCESS_ORDER_STRUCTURE[i]);
            row.put("NUM" + numIndex++, formatNumber(getValue(data, code, index)));
        }
        long other = 0;
        for (int i = start + length; i < PROCESS_ORDER_STRUCTURE.length; i++) {
            String code = String.valueOf(PROCESS_ORDER_STRUCTURE[i]);
            other += getValue(data, code, index);
        }
        row.put("NUM" + numIndex, formatNumber(other));
        return row;
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