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

public class bmss2053602022r extends IReportObject {

	private String LICKIND;
	private String YYY;
	private String MM;
	private String ORGAN;
	private String REPSOURCE;

	private String className;
	private String reportType;
	private String reportName;
	private String sSQL = "";
	private long vNum1 = 0;
	private long vNum2 = 0;
	private long vNum3 = 0;
	private static final int[] PROCESS_ORDER = { 11, 12, 13, 14, 15, 16, 17, 18, 19, 21, 20, 99, 98 };

	public String getLICKIND() {
		return LICKIND;
	}

	public void setLICKIND(String lICKIND) {
		LICKIND = lICKIND;
	}

	public String getYYY() {
		return YYY;
	}

	public void setYYY(String yYY) {
		YYY = yYY;
	}

	public String getMM() {
		return MM;
	}

	public void setMM(String mM) {
		MM = mM;
	}

	public String getREPSOURCE() {
		return REPSOURCE;
	}

	public void setREPSOURCE(String rEPSOURCE) {
		REPSOURCE = rEPSOURCE;
	}

	public String getORGAN() {
		return ORGAN;
	}

	public void setORGAN(String oRGAN) {
		ORGAN = oRGAN;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	@Override
	public String getJasperFile() {
		String report = "";
		if (REPSOURCE.equals("2053601012")) {
			report = "/report/205360101/20536_01_01.jasper";
		} else if (REPSOURCE.equals("2053602012")) {
			report = "/report/205360201/20536_02_01.jasper";
		} else if (REPSOURCE.equals("2053603012")) {
			report = "/report/205360301/20536_03_01.jasper";
		}
		return report;
	}

	HashMap subjectmap = new HashMap();

	@Override
	public List getData() {
		if (REPSOURCE.equals("2053601012")) {
			getParameters().put("subreportpath1", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360101" + File.separatorChar + "20536_01_01_subreport1.jasper");
			getParameters().put("subreportpath2", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360101" + File.separatorChar + "20536_01_01_subreport2.jasper");
			getParameters().put("subreportpath3", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360101" + File.separatorChar + "20536_01_01_subreport3.jasper");
			getParameters().put("subreportpath4", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360101" + File.separatorChar + "20536_01_01_subreport4.jasper");
		} else if (REPSOURCE.equals("2053602012")) {
			getParameters().put("subreportpath1", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360201" + File.separatorChar + "20536_02_01_subreport1.jasper");
			getParameters().put("subreportpath2", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360201" + File.separatorChar + "20536_02_01_subreport2.jasper");
			getParameters().put("subreportpath3", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360201" + File.separatorChar + "20536_02_01_subreport3.jasper");
			getParameters().put("subreportpath4", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360201" + File.separatorChar + "20536_02_01_subreport4.jasper");
		} else if (REPSOURCE.equals("2053603012")) {
			getParameters().put("subreportpath1", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360301" + File.separatorChar + "20536_03_01_subreport1.jasper");
			getParameters().put("subreportpath2", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360301" + File.separatorChar + "20536_03_01_subreport2.jasper");
			getParameters().put("subreportpath3", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360301" + File.separatorChar + "20536_03_01_subreport3.jasper");
			getParameters().put("subreportpath4", getMaliContext().getWebRealPath() + "report" + File.separatorChar
					+ "205360301" + File.separatorChar + "20536_03_01_subreport4.jasper");
		}

		getParameters().put("subjectRowData1", getSubData1());
		getParameters().put("subjectRowData2", getParameters().get("subjectRowData1"));
		getParameters().put("subjectRowData3", getParameters().get("subjectRowData1"));
		getParameters().put("subjectRowData4", getParameters().get("subjectRowData1"));

		List<Map<String, Object>> masterData = new ArrayList<Map<String, Object>>();

		getsysCode codeQuery = new getsysCode(getMaliContext());
		String DATE1 = String.format("中華民國 %s 年 %s 月", YYY, MM);
		subjectmap.put("ORGAN", commonbean.getCodeName(publicKeyUtility.KEY_PUB_主管機關Array, ORGAN));
		subjectmap.put("DATE1", DATE1);
		subjectmap.put("DATE2",
				"中華民國" + commonbean.ShowCDate(commonbean.ShowDate(), new String[] { "年", "月", "日" }) + "編製");
		masterData.add(subjectmap);
		return masterData;
	}

	public List<Map<String, Object>> getSubData1() {
		List<Map<String, Object>> subDataList = new ArrayList<Map<String, Object>>();
		Map submap = new HashMap();
		BMSSTARAWQuery qry = new BMSSTARAWQueryImpl(getMaliContext());

		sSQL = "select" + "	bmsta_usagecode," + "	sum(icount) icount," + "	sum(tothousesum) tothousesum,"
				+ "	sum(ifloorArea) ifloorArea," + "	sum(iconstruction) iconstruction" + " from" + "	(" + "	select"
				+ "		bmsta_usagecode," + "		count(1) icount,"
				+ "		round(sum(coalesce(bmsta_tothousesum, 0)), 0) tothousesum,"
				+ "		round(sum(coalesce(bmsta_totgrarea, 0)+ coalesce(bmsta_totdnarea, 0)), 0) ifloorArea,"
				+ "		round(sum(coalesce(bmsta_contprice, 0))/ 1000, 0) iconstruction" + "	from"
				+ "		bmsstaraw" + "	where" + "		bmsta_stakind = '" + LICKIND + "'" + "		and bmsta_yymm = '"
				+ YYY + MM + "'"
				+ "		and coalesce(bmsta_usagecode, '') in ('11', '12', '13', '14', '15', '16', '17', '18', '19', '21', '20', '98')"
				+ "	group by" + "		bmsta_usagecode" + " union all" + "	select" + "		'99' bmsta_usagecode,"
				+ "		count(1) icount," + "		round(sum(coalesce(bmsta_buildsum, 0)), 0) buildsum,"
				+ "		round(sum(coalesce(bmsta_totgrarea, 0)+ coalesce(bmsta_totdnarea, 0)), 0) ifloorArea,"
				+ "		round(sum(coalesce(bmsta_contprice, 0))/ 1000, 0) iconstruction" + "	from"
				+ "		bmsstaraw" + "	where" + "		bmsta_stakind = '" + LICKIND + "'" + "		and bmsta_yymm = '"
				+ YYY + MM + "'"
				+ "		and coalesce(bmsta_usagecode, '') not in ('11', '12', '13', '14', '15', '16', '17', '18', '19', '21', '20')"
				+ "	group by" + "		bmsta_usagecode)a" + " group by" + "	bmsta_usagecode";

		qry.setSQLText(sSQL);
		BMSSTARAW[] alldatas = qry.getDataContent();
		if (alldatas != null) {
			submap = new HashMap();
			int iNum = 4;
			vNum1 = 0;
			vNum2 = 0;
			vNum3 = 0;
			submap.put("CITY", "總 計");
			for (int i : PROCESS_ORDER) {
				String iKey = String.valueOf(i);
				submap.put("NUM" + String.valueOf(iNum++),
						Arrays.stream(alldatas).filter(e -> iKey.equals(e.getBMSTA_USAGECODE())).map(e -> {
							// 因為[農業設施]已包含在[其他]項目裏，排除不累加至總計
							if (i != 98)
								vNum1 += Long.parseLong(e.getStringfield("ICOUNT"));
							// String s = commonbean.getMoneyformat(e.getStringfield("ICOUNT"));
							return publicKeyUtility.getStcNumFmt(e.getStringfield("ICOUNT"), 0);
						}).findFirst().orElse("0"));// 件數
				if (i == 19 || i == 21) {
					// 19:住宅<不含農舍>(H-2類)、21:農舍(H-2類) 加印 戶數加總
					submap.put("NUM" + String.valueOf(iNum++),
							Arrays.stream(alldatas).filter(e -> iKey.equals(e.getBMSTA_USAGECODE())).map(e -> {
								// String s = commonbean.getMoneyformat(e.getStringfield("TOTHOUSESUM"));
								return publicKeyUtility.getStcNumFmt(e.getStringfield("TOTHOUSESUM"), 0);
							}).findFirst().orElse("0"));// 戶數
				}
				submap.put("NUM" + String.valueOf(iNum++),
						Arrays.stream(alldatas).filter(e -> iKey.equals(e.getBMSTA_USAGECODE())).map(e -> {
							if (i != 98)
								vNum2 += Long.parseLong(e.getStringfield("IFLOORAREA"));
							// String s = commonbean.getMoneyformat(e.getStringfield("IFLOORAREA"));
							return publicKeyUtility.getStcNumFmt(e.getStringfield("IFLOORAREA"), 0);
						}).findFirst().orElse("0"));// 總樓地板面積
				submap.put("NUM" + String.valueOf(iNum++),
						Arrays.stream(alldatas).filter(e -> iKey.equals(e.getBMSTA_USAGECODE())).map(e -> {
							if (i != 98)
								vNum3 += Long.parseLong(e.getStringfield("ICONSTRUCTION"));
							// String s = commonbean.getMoneyformat(e.getStringfield("ICONSTRUCTION"));
							return publicKeyUtility.getStcNumFmt(e.getStringfield("ICONSTRUCTION"), 0);
						}).findFirst().orElse("0"));// 工程造價
			}

			// 總計(因進位問題改成自行加總)
			submap.put("NUM1", publicKeyUtility.getStcNumFmt(String.valueOf(vNum1), 0));
			submap.put("NUM2", publicKeyUtility.getStcNumFmt(String.valueOf(vNum2), 0));
			submap.put("NUM3", publicKeyUtility.getStcNumFmt(String.valueOf(vNum3), 0));
			subDataList.add(submap);

		} else {
			submap = new HashMap();
			submap.put("CITY", "總 計");

			for (int i = 1; i <= 44; i++) {
				submap.put("NUM" + String.valueOf(i), "0");
			}
			subDataList.add(submap);

		}
		return subDataList;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getDownloadFileName() {
		String reportname = "";
		if (REPSOURCE.equals("2053601012")) {
			reportname = "核發建築物建造執照統計-按用途別分";
		} else if (REPSOURCE.equals("2053602012")) {
			reportname = "核發建築物使用執照統計-按用途別分";
		} else if (REPSOURCE.equals("2053603012")) {
			reportname = "核發建築物使用執照統計-按用途別分";
		}

		return reportname;
	}
}
