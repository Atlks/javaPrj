<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*"%>

<%

//=	
String s7="";
//DataGridView GridView1.DataSource
	String s=filex.read("c:\\dt.txt");
		JSONArray ja= net.sf.json.JSONArray.fromObject(s);
			 
			List li=	(List) request.getSession().getAttribute("wait2confirmDataDetailList");
				JSONArray ja2= CollX.List2jsonArr(li);
		
		DataGridView GridView1 = new DataGridView();
		Object myds = null;
	 
		GridView1.DataSource=ja2;
		GridView1.column="equipmentId";
		GridView1.columnCN="�豸id";

	    //    GridView1.DataKeyNames = new string[] { "id" };//����

	        GridView1.DataBind();
	        	request.getSession().setAttribute("GridView1",GridView1);
	        		request.getSession().setAttribute("GridView2","gv2test");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<%

%>
<script type="text/javascript">
function MM_callJS(jsStr) { //v2.0
 // return eval(jsStr)
 var handle=<%=request.getParameter("confmEveHandle")%>;
 handle();
// window.opener.confirmSubmit();
 window.close();
}
function cancel()
{
	 window.close();
}

</script>
</head>

<body>
<p>有时间段的覆盖：：<a href="lookOverdetail.jsp?tmplt=<%=request.getParameter("tmplt")%>" target="_blank">  查看
  详细 </a>
</p>
<p>
  <input name="button" type="button" id="button" onclick="MM_callJS('aaa')" value="确认提交" />
  <input name="button2" type="button" id="button2" onclick="cancel()" value="取消提交" />
</p>
</body>
</html>
