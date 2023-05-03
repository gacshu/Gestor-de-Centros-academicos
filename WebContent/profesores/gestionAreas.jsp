<%@page import="es.jahernandez.gestion.ProfAreaGestion"%>
<%@page import="es.jahernandez.accesodatos.ProfAreaDAO"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.accesodatos.ProfAreaDAO"%>
<%@ page import = "es.jahernandez.accesodatos.EdicionesDAO"%>
<%@ page import = "es.jahernandez.accesodatos.ModulosDAO"%>

<%@ page import = "es.jahernandez.datos.ProfAreaVO"%>

<%@page import  = "java.util.Vector"%>


<html xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="../controlAcceso/includeComAut.jsp"%>
    
<%
   ProfAreaVO  profAreaVO  = new ProfAreaVO();
   Vector      vecProfArea = null; 

   int      indInf  = 0;
   int      indSup  = 0;
   String   codProf = "";
   boolean  errAlta = false;
   int      resBor  = -99;
   
   
   
   //Paginación de resultados
   if(request.getParameter("valInfProfArea") != null)
   {
        indInf = new Integer(request.getParameter("valInfProfArea")).intValue();
   }

   if(request.getParameter("codProf") != null)
   {
        codProf     =  request.getParameter("codProf").trim();
        vecProfArea =  ProfAreaGestion.devolverAreasProf(codProf);
   }
   
   if(request.getParameter("errorCode") != null)
   {
        errAlta = true;
   }
   
   if(request.getParameter("resultBor") != null)
   {
        resBor = new Integer(request.getParameter("resultBor").trim()).intValue();
   }
            
   if(indInf < 0)
   {
        indInf = 0;
   }

   indSup = indInf + 5;
   if(indSup > vecProfArea.size()) 
   {
        indSup = vecProfArea.size();
   }

 %>   
<head>

<title>Gestion Areas</title>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../js/validaGlobal.js" type="text/javascript"></script><script src="../SpryAssets/SpryValidationSelect.js"    type="text/javascript"></script>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>


<script>
function validaForm()
{
     var validarCampos  = true;			
     //Validar campos alta nivel        
     validarCampos = spryselect1.validate();          
                
	if(validarCampos)
	{
		document.frmAreasProf.action = "../AltaAreaProfServlet?valInfProfArea=<%=indInf%>";
		document.frmAreasProf.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados");
	}
}

function habilitaEdi(codProfArea)
{
	eval("document.frmAreas.lstAreas"    + codProfArea).disabled  = false;       
}


function bajaArea(codProf,codArea,valInf)
{
    if(confirm("¿Desea eliminar este área?"))
    {
        document.frmAreasProf.action = "../BajaAreaProfServlet?codProf="        + codProf 
                                                         + "&codArea="        + codArea
                                                         + "&valInfProfArea=" + valInf;
        document.frmAreasProf.submit();
    }
}

function cargarCombosAreas(idArea)
{   
	$.ajax({
	  url: "../CargaComboAreaServlet?valSel=" + idArea,
	  success: function(data) {
		$(document).ready(function() {
			<%for(int ind=0; ind < vecProfArea.size(); ind++)
                          { ProfAreaVO paAux = (ProfAreaVO) vecProfArea.elementAt(ind);%>
                            $("#lstArea<%=paAux.getCodProf() + paAux.getCodArea()%>").html(data);
                            $("#lstArea<%=paAux.getCodProf() + paAux.getCodArea()%>").val("<%=paAux.getCodArea()%>");
			<%}%>
			$("#lstNuevoArea").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando las áreas");
	  }
	});
}
	 
</script>
                                                
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
</head>

<body class="fondoFormularios" onload="cargarCombosAreas();">
<%if(errAlta){%>
<script>alert("Se produjo un error\n en el alta del módulo ")</script>
<%}%>

<%if(resBor != -99){
    if(resBor>0){%>
        <script>alert("El área se borró correctamente ")</script>
    <%}else{%>
        <script>alert("El área no pudo ser borrado ")</script>
<%}}%>

<form action="" method="post" name="frmAreasProf" id="frmAreasProf">
<table width="63%" border="0" align="center" class="tablaListados">
  <tr class="tablaListados">
    <td colspan="2"             class="tdDef"><strong>&Aacute;rea</strong></td>
    <td colspan="2" class="tdDef"><img src="../imagenes/newhab.png" width="30" height="30" onclick="lstNuevoArea.style.visibility='visible';btnAnadir.style.visibility='visible'" style="cursor:pointer"/></td>
  </tr>
  <%for(int ind = indInf; ind<indSup;ind++){
      profAreaVO = (ProfAreaVO)   vecProfArea.elementAt(ind);
  %>
  <tr class="tdDef">
    <td colspan="2">
        <select name="lstAreas<%=profAreaVO.getCodProf() + profAreaVO.getCodArea()%>" id="lstArea<%=profAreaVO.getCodProf() + profAreaVO.getCodArea()%>" disabled="disabled"></select></td>
      <td width="56%" class="center">
        <img src="../imagenes/papelera.png" width="30" height="30" onclick="bajaArea('<%=profAreaVO.getCodProf()%>','<%=profAreaVO.getCodArea()%>', '<%=indInf%>');" style="cursor:pointer"/>
      </td>
  </tr>
  <%}%>
  <tr class="tdDef">
    <td width="16%" height="26"><span id="valNuevoArea">
      <select name="lstNuevoArea" id="lstNuevoArea" style="visibility:hidden"></select>
      <span class="selectInvalidMsg">*.</span><span class="selectRequiredMsg">*</span></span></td>
    <td colspan="2"><span class="center">
      <input type="button" name="btnAnadir" id="btnAnadir" value="Añadir Área" style="visibility:hidden" onclick="validaForm();"/>
    </span></td>
    </tr>
  <tr>
    <td colspan="2" class="center"><input type="hidden" name="hidCodProf" id="hidCodProf" value="<%=codProf%>" />
        <%if( indInf > 0){%>
        <a href="gestionAreas.jsp?codProf=<%=codProf%>&valInfProfArea=<%=(indInf - 5)%>"><img src="../imagenes/btnprev.png" alt="" width="25" height="25" /></a><%}%></td>
    <td class="center"><%if( indSup < vecProfArea.size()){%>
      <a href="gestionAreas.jsp?codProf=<%=codProf%>&valInfProfArea=<%=(indInf + 5)%>"><img src="../imagenes/btnsig.jpg" width="25" height="25" /></a>
      <%}%></td>
  </tr>
  <tr>
    <td colspan="3" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
var spryselect1 = new Spry.Widget.ValidationSelect("valNuevoArea", {invalidValue:"-1"});
</script>
</body>
</html>