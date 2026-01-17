<%@page contentType="text/html;charset=UTF-8"%>
<%@ include file="/index_up.jsp"%>
<%@ include file="/platform/platform_include_importprj.jsp"%>
<%
/*
***Main BLOCK-程式資訊-START--------------------------------------------------------------程式資訊--START------>
***程式編號：bmss100m.jsp
***程式名稱：執照資料統計
***程式種類：新版Grid模版
***版面編排：Snow
***撰寫人  ：Snow
***撰寫日期：20250619
***程式說明： 
***版本資訊：v1.0  MaliGo_Mali Info Inc. Co.版本日期:980514
--------------------------修改記錄--------------START----->
--------------------------修改記錄--------------END------->
***Main BLOCK-程式資訊-END----------------------------------------------------------------程式資訊--END-------->
*/

String vTitle = "執照資料統計";
String vProgramNo = "bmss100m";
//*Main BLOCK-Config-END----------------------------------------------------------------------------END-------->
//*查詢台欄位建立-----------------------------------------------------------------------------------START------>
Combobox CB_ORGAN = HtmlObject.createComboboxByusingOptionYes(ivContext, "ORGAN", "機關縣市",
		new String[] {"IF0,台南市"}, false);
String[] Ary_lickind = new String[]{"01,建照", "03,使照", "04,拆照"};
String[] Ary_chkstat = new String[]{"ok,正常", "er,異常"};
String[] Ary_reportKind = new String[]{"01,按用途別分", "02,按構造別分", "03,按高度別分", "04,按層數別分", "05,按土地使用分區別分","06,縣市二級表-建照及拆照","07,縣市二級表-使照","08,縣市二級表-開工"};


CB_ORGAN.setDefaultValue(P_ORGID);

InputText IT_YYY = HtmlObject.createInputText("YYY", "核發年份", 3, "5vw", false);
Combobox CB_MM = HtmlObject.createComboboxByusingOptionYes(ivContext, "MM", "核發月份", new String[] {"12,十二月份"},
		false);
Combobox CB_LICKIND = HtmlObject.createComboboxByusingOptionYes(ivContext, "LICKIND", "執照類別", Ary_lickind, false);
Combobox CB_CHKSTAT = HtmlObject.createComboboxByusingOptionYes(ivContext, "CHKSTAT", "檢核結果", Ary_chkstat, false);
Combobox CB_REPORTKIND = HtmlObject.createComboboxByusingOptionYes(ivContext, "REPORTKIND", "匯出報表類別", Ary_reportKind,
		false);
String sLastMonth = commonbean.addMonth(commonbean.ShowDate(), -1);
IT_YYY.setDefaultValue(String.valueOf(Integer.parseInt(sLastMonth.substring(0, 4)) - 1911));
CB_MM.setDefaultValue(sLastMonth.substring(4, 6));
//*查詢台欄位建立-----------------------------------------------------------------------------------END-------->
//*查詢台欄位組合-----------------------------------------------------------------------------------START------>
int QRY_COL = 1;
HtmlColumnObject QRY_COL_YYYMM = new HtmlColumnObject();
QRY_COL_YYYMM.setDisplayName("核發年月");
QRY_COL_YYYMM.setFieldObject(QRY_COL++, "民國");
QRY_COL_YYYMM.setFieldObject(QRY_COL++, IT_YYY);
QRY_COL_YYYMM.setFieldObject(QRY_COL++, "年");
QRY_COL_YYYMM.setFieldObject(QRY_COL++, CB_MM);
//*查詢台欄位組合-----------------------------------------------------------------------------------END-------->
//*查詢台欄位屬性-----------------------------------------------------------------------------------START------>

//*查詢台欄位屬性-----------------------------------------------------------------------------------END-------->
//*查詢台直式排列-----------------------------------------------------------------------------------START------>
int QRY_VECTOR = 1;
HtmlFieldVector QryVector = new HtmlFieldVector();
QryVector.setHtmlVector(QRY_VECTOR++, CB_ORGAN);
QryVector.setHtmlVector(QRY_VECTOR++, CB_LICKIND);
QryVector.setHtmlVector(QRY_VECTOR++, QRY_COL_YYYMM);
QryVector.setHtmlVector(QRY_VECTOR++, CB_CHKSTAT);
QryVector.setHtmlVector(QRY_VECTOR++, CB_REPORTKIND);

//*查詢台直式排列-----------------------------------------------------------------------------------END-------->
//*查詢台設定---------------------------------------------------------------------------------------START------>
theQueryParam.setis顯示查詢台(false);
theQueryParam.setQueryParams(QryVector);
theQueryParam.setIsUseAjax(true);
theQueryParam.setAjaxinnerHtmlDivID("body_content");
theQueryParam.setFormId("form");
theQueryParam.set執行查詢PostUrl(ContextPath + "/maliapp/bmss100/bmss100m/bmss100m_lst.jsp");

htmElement.setQueryParambean(theQueryParam);
//*查詢台設定---------------------------------------------------------------------------------------END-------->
//*按鈕---------------------------------------------------------------------------------------------START------>
ExportButton vbt_export = HtmlObject.createExrportButton();
vbt_export.setID("vbt_Sta");
vbt_export.setDisplayName("產生統計資料");
vbt_export.setOnClickEvent("run_Sta();");

ExportButton vbt_commit = HtmlObject.createExrportButton();
vbt_commit.setID("vbt_commit");
vbt_commit.setDisplayName("確認完成");
vbt_commit.setOnClickEvent("run_commit();");

UploadButton vbt_uploadtxt = HtmlObject.createUploadButton();
vbt_uploadtxt.setDisplayName("建管系統匯出檔匯入(上傳txt檔)");
vbt_uploadtxt.setOnClickEvent("txtUpload();");
vbt_uploadtxt.setTDClass("text-center");

ExportButton vbt_exfile = HtmlObject.createExrportButton();
vbt_exfile.setID("vbt_exfile");
vbt_exfile.setDisplayName("匯出檔案");
vbt_exfile.setOnClickEvent("vbt_exfile();");

ExportButton vbt_export2 = HtmlObject.createExrportButton();
vbt_export2.setID("vbt_export2");
vbt_export2.setDisplayName("匯出報表");
vbt_export2.setOnClickEvent("run_export();");

ExportButton vbt_checkView = HtmlObject.createExrportButton();
vbt_checkView.setID("vbt_checkview");
vbt_checkView.setDisplayName("檢視及上傳");
vbt_checkView.setOnClickEvent("runCheckView();");

ExportButton vbt_exportcitylv2 = HtmlObject.createExrportButton();
vbt_exportcitylv2.setID("vbt_exportcitylv2");
vbt_exportcitylv2.setDisplayName("匯出縣市二級表");
vbt_exportcitylv2.setOnClickEvent("run_exportcitylv2();");

Button[] MButton = new Button[]{vbt_export, vbt_commit, vbt_exfile, vbt_uploadtxt, vbt_export2, vbt_checkView,vbt_exportcitylv2};
//是否為自建
/*int index = Arrays.asList(publicKeyUtility.KEY_SelfORGANArray).indexOf(P_ORGID);
if (index > -1) {
	MButton = new Button[]{vbt_uploadtxt, vbt_commit, vbt_exfile, vbt_export2, vbt_checkView};
} else {
	MButton = new Button[]{vbt_export, vbt_commit, vbt_exfile, vbt_export2, vbt_checkView};
}*/
//*按鈕---------------------------------------------------------------------------------------------END-------->
%>
<!--html--------------------------------------------------------------------------------------------START------>
<%
MHeader header = new MHeader();
MBody body = new MBody();
MButtonGroup body_btgroup = new MButtonGroup();
MForm form = new MForm();

header.setLeftTitle(vTitle);
header.set功能編號(vProgramNo);
header.setContent底色為淺黃色();
header.setContentStyle為tableStyle();
header.setContentHtml(htmElement.getQueryParamForm());

body_btgroup.setLeftButton(MButton);
form.setHeaderObject(new Object[]{header});
form.setBodyObject(new Object[]{body_btgroup, body});
maliWrite.println(form.toString());
%>
<!--html--------------------------------------------------------------------------------------------END-------->
<!--javascript--------------------------------------------------------------------------------------START------>
<script type="text/javascript"
	src="<%=ContextPath%>/maliapp/public/js/common.js"></script>
<script language="javascript">
	function run_Sta(){            
        if (!CheckData(Array(document.all("Qry_ORGAN"), "機關縣市")))
            return document.all("Qry_ORGAN").focus();
        if (!CheckData(Array(document.all("Qry_YYY"), "申報年份")))
            return document.all("Qry_YYY").focus();
        if (!CheckData(Array(document.all("Qry_MM"), "申報月份")))
            return document.all("Qry_MM").focus();
        if (!CheckData(Array(document.all("Qry_LICKIND"), "執照類別")))
            return document.all("Qry_LICKIND").focus();
        
        var paras = new Array();
        paras.push(encodeNameAndValue("lickind",document.all("Qry_LICKIND").value));
        paras.push(encodeNameAndValue("organ",document.all("Qry_ORGAN").value));
        paras.push(encodeNameAndValue("YYY",document.all("Qry_YYY").value));
        paras.push(encodeNameAndValue("MM", document.all("Qry_MM").value));    
        var url   = "<%=ContextPath%>/bmsa99/lcstexportvt.do?"+paras.join("&");
        window.location.href=url;     
        if (getISOK(obj) == "true") {
            $jq("#<%=theQueryParam.get自訂查詢台執行查詢Button().getFieldName()%>").click();
        }      

        }
	function run_commit(){
        if (!CheckData(Array(document.all("Qry_ORGAN"), "機關縣市")))
            return document.all("Qry_ORGAN").focus();
        if (!CheckData(Array(document.all("Qry_YYY"), "核發年份")))
            return document.all("Qry_YYY").focus();
        if (!CheckData(Array(document.all("Qry_MM"), "核發月份")))
            return document.all("Qry_MM").focus();
        if (!CheckData(Array(document.all("Qry_LICKIND"), "執照類別")))
            return document.all("Qry_LICKIND").focus();
        
        var url   = "<%=ContextPath%>/licupload.do";
        var paras = new Array();
        paras.push(encodeNameAndValue("lickind",document.all("Qry_LICKIND").value));
        paras.push(encodeNameAndValue("organ",document.all("Qry_ORGAN").value));
        paras.push(encodeNameAndValue("YYY",document.all("Qry_YYY").value));
        paras.push(encodeNameAndValue("MM", document.all("Qry_MM").value));    
        runPostreturnXMLMesg(url, paras.join("&"));
        var obj = getResult();
        if (getISOK(obj) == "true") {
            $jq("#<%=theQueryParam.get自訂查詢台執行查詢Button().getFieldName()%>")
					.click();
		}
		alert(getMESG(obj));
	}
	//檔案上傳
	function txtUpload() {

		if (!CheckData(Array(document.all("Qry_ORGAN"), "機關縣市")))
			return document.all("Qry_ORGAN").focus();
		if (!CheckData(Array(document.all("Qry_LICKIND"), "執照類別")))
			return document.all("Qry_LICKIND").focus();
		if (!CheckData(Array(document.all("Qry_YYY"), "核發年份")))
			return document.all("Qry_YYY").focus();
		if (!CheckData(Array(document.all("Qry_MM"), "核發月份")))
			return document.all("Qry_MM").focus();

		var url = ContextPath + "/maliapp/public/UPLOAD.jsp?Organ="
				+ document.all("Qry_ORGAN").value + "&LicKind="
				+ document.all("Qry_LICKIND").value + "&YYYMM="
				+ document.all("Qry_YYY").value + document.all("Qry_MM").value;
		var paras = new Array();
		paras.push(encodeNameAndValue("", ""));
		loadPopWindow(url, paras);
	}

	function vbt_exfile() {
		var locationform = document.createElement("form");
		locationform.target = "xwin";
		locationform.method = "post";
		locationform.id = "location";
		locationform.action = ContextPath + "/bmss/bmssexfile_csv.do";

		addHiddenField(locationform, "reportname", "bmss100m");
		addHiddenField(locationform, "ORGAN", document.all("Qry_ORGAN").value);
		addHiddenField(locationform, "LICKIND",
				document.all("Qry_LICKIND").value);
		addHiddenField(locationform, "YYY", document.all("Qry_YYY").value);
		addHiddenField(locationform, "MM", document.all("Qry_MM").value);
		addHiddenField(locationform, "CHKSTAT",
				document.all("Qry_CHKSTAT").value);

		document.body.appendChild(locationform);
		locationform.submit();
	}
	function run_export() {
		var RepSource;
		var REPORTKIND;

		if (!CheckData(Array(document.all("Qry_ORGAN"), "機關縣市")))
			return document.all("Qry_ORGAN").focus();
		if (!CheckData(Array(document.all("Qry_LICKIND"), "執照類別")))
			return document.all("Qry_LICKIND").focus();
		if (!CheckData(Array(document.all("Qry_YYY"), "核發年份")))
			return document.all("Qry_YYY").focus();
		if (!CheckData(Array(document.all("Qry_MM"), "核發月份")))
			return document.all("Qry_MM").focus();
		if (!CheckData(Array(document.all("Qry_REPORTKIND"), "報表類別"))) {
			return document.all("Qry_REPORTKIND").focus();
		} else {
			REPORTKIND = document.all("Qry_REPORTKIND").value;
		}

		var paras = new Array();
		var Class
		if (document.all("Qry_LICKIND").value == "01") {
			if (REPORTKIND == "01") {
				Class = "bmss.report.bmss2053602012r";
				RepSource = "2053601012";
			} else if (REPORTKIND == "02") {
				Class = "bmss.report.bmss2053602022r";
				RepSource = "2053601022";
			} else {
				return alert("建照僅提供用途別及構造別");
			}
		} else if (document.all("Qry_LICKIND").value == "03") {
			if (REPORTKIND == "01") {
				Class = "bmss.report.bmss2053602012r";
				RepSource = "2053602012";
			} else if (REPORTKIND == "02") {
				Class = "bmss.report.bmss2053602022r";
				RepSource = "2053602022";
			} else if (REPORTKIND == "03") {
				Class = "bmss.report.bmss2053602032r";
				RepSource = "2053602032";
			} else if (REPORTKIND == "04") {
				Class = "bmss.report.bmss2053602042r";
				RepSource = "2053602042";
			} else if (REPORTKIND == "05") {
				Class = "bmss.report.bmss2053602052r";
				RepSource = "2053602052";
			}
		} else if (document.all("Qry_LICKIND").value == "04") {
			if (REPORTKIND == "01") {
				Class = "bmss.report.bmss2053601032r";
				RepSource = "2053601032";
			} else if (REPORTKIND == "02") {
				Class = "bmss.report.bmss2053602022r";
				RepSource = "2053601042";
			} else {
				return alert("拆照僅提供用途別及構造別");
			}
		}

		var report = RepSource;

		var url = ContextPath + "/mali/IReportaction.do";

		paras.push(encodeNameAndValue("YYY", LPad(
				document.all("Qry_YYY").value, 3, 0)));
		paras.push(encodeNameAndValue("MM", document.all("Qry_MM").value));
		paras
				.push(encodeNameAndValue("ORGAN",
						document.all("Qry_ORGAN").value));
		paras.push(encodeNameAndValue("LICKIND",
				document.all("Qry_LICKIND").value));

		paras.push(encodeNameAndValue("REPSOURCE", RepSource));
		paras.push(encodeNameAndValue("className", Class));
		paras.push(encodeNameAndValue("reportName", report));
		paras.push(encodeNameAndValue("reportType", "XLS")); // "PDF" "XLS"

		url = url + "?" + paras.join("&");
		form.action = url;
		form.method = "post";
		form.submit();

	}
	
	
	
	function run_exportcitylv2(){
		var RepSource;
		var REPORTKIND;		
		if (!CheckData(Array(document.all("Qry_ORGAN"), "機關縣市")))
			return document.all("Qry_ORGAN").focus();
		if (!CheckData(Array(document.all("Qry_YYY"), "核發年份")))
			return document.all("Qry_YYY").focus();
		if (!CheckData(Array(document.all("Qry_MM"), "核發月份")))
			return document.all("Qry_MM").focus();
		if (!CheckData(Array(document.all("Qry_REPORTKIND"), "報表類別"))) {
			return document.all("Qry_REPORTKIND").focus();
		} else {
			REPORTKIND = document.all("Qry_REPORTKIND").value;
		}
		
		var paras = new Array();
		var Class
		if (REPORTKIND == "06"){
			Class = "bmss.report.bmss20536010102r";
			RepSource = "20536010102";
		}else if(REPORTKIND == "07"){
			Class = "bmss.report.bmss20536010102r";
			RepSource = "20536020108";
		}else if(REPORTKIND == "08"){
			Class = "bmss.report.bmss20536010102r";
			RepSource = "20536030102";
		}
		
		
		var report = RepSource;

		var url = ContextPath + "/mali/IReportaction.do";

		paras.push(encodeNameAndValue("YYY", LPad(
				document.all("Qry_YYY").value, 3, 0)));
		paras.push(encodeNameAndValue("MM", document.all("Qry_MM").value));
		paras
				.push(encodeNameAndValue("ORGAN",
						document.all("Qry_ORGAN").value));
		paras.push(encodeNameAndValue("LICKIND",
				document.all("Qry_LICKIND").value));

		paras.push(encodeNameAndValue("REPSOURCE", RepSource));
		paras.push(encodeNameAndValue("className", Class));
		paras.push(encodeNameAndValue("reportName", report));
		paras.push(encodeNameAndValue("reportType", "XLS")); // "PDF" "XLS"

		url = url + "?" + paras.join("&");
		form.action = url;
		form.method = "post";
		form.submit();
	}
</script>
<!--javascript--------------------------------------------------------------------------------------END-------->
<%@include file="/index_foot.jsp"%>