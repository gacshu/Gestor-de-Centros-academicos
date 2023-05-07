<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.accesodatos.TrastornosDAO"%>
<%@ page import = "es.jahernandez.accesodatos.TipTrastDAO"%>
<%@page import="es.jahernandez.gestion.TrastornosGestion"%>
<%@ page import = "es.jahernandez.datos.TrastornosVO"%>

<%@page import  = "java.util.Vector"%>


<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="../controlAcceso/includeComAut.jsp"%>
    
<%
   TrastornosVO trastVO  = new TrastornosVO();
   Vector       vecTras  = null; 

   int          indInf  = 0;
   int          indSup  = 0;
   String       codInt  = "";
   boolean      errAlta = false;
   boolean      errEdi  = false;
   int          resBor  = -99;
   
   //Paginación de resultados
   if(request.getParameter("valInfTrast") != null)
   {
            indInf = new Integer(request.getParameter("valInfTrast")).intValue();
   }

   if(request.getParameter("codInt") != null)
   {
            codInt  =  request.getParameter("codInt").trim();
            vecTras =  TrastornosGestion.devolverTrastAlu(codInt);
   }
   
   if(request.getParameter("errorCode") != null)
   {
            errAlta = true;
   }
   
   if(request.getParameter("errorEdi") != null)
   {
            errEdi = true;
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
   if(indSup > vecTras.size()) 
   {
        indSup = vecTras.size();
   }

 %>   
<head>

<title>Tienda Virtual</title>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../js/validaGlobal.js" type="text/javascript"></script><script src="../SpryAssets/SpryValidationTextarea.js"  type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js"    type="text/javascript"></script>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>


<script>
function validaForm()
{
     var validarCampos  = true;			
     //Validar campos alta nivel        
     validarCampos = spryselect1.validate() & sprytextarea1.validate() ;
	 			               
	if(validarCampos)
	{
		document.frmTrastornos.action = "../AltaTrastornoServlet?valInfTrast=<%=indInf%>";
		document.frmTrastornos.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados");
	}
}

function habilitaEdi(codTrast)
{
    
	eval("document.frmTrastornos.lstTipTras"    + codTrast).disabled  = false; 
	eval("document.frmTrastornos.chkMedicado"   + codTrast).disabled  = false;
	eval("document.frmTrastornos.txtMedicacion" + codTrast).disabled  = false;       
    eval("document.frmTrastornos.btnEdiMod"     + codTrast).disabled  = false;
}

function editaTrast(codTrast)
{
    var validaCampos = true;
    
    if(Trim(eval("document.frmTrastornos.lstTipTras"  + codTrast).value) == "-1")
    {
    	validaCampos = false;
    }
        
    if(validaCampos)
    {    			
        document.frmTrastornos.action = "../EditarTrastornoServlet?txtCodTrast=" + codTrast  + 
														         "&txtCodInt=<%=codInt%>"    + 
													             "&valInfTrast=<%=indInf%>";
        document.frmTrastornos.submit();
    }
    else
    {
        alert("Se produjo un error en la edición de datos del trastorno");
    }
}

function bajaModulo(codTrast,valInfTrast,codInt)
{
    if(confirm("¿Desea eliminar este trastorno?"))
    {
        document.frmTrastornos.action = "../BajaTrastornoServlet?codTrast="    + codTrast 
                                                             + "&codInt="      + codInt
                                                             + "&valInfTrast=" + valInfTrast;
        document.frmTrastornos.submit();
    }
}

function cargarCombosTipoTrast(idTipTrast)
{   
	$.ajax({
	  url: "../CargaComboTipoTrastornosServlet?valSel=" + idTipTrast,
	  success: function(data) {
		$(document).ready(function() {
			<%for(int ind=0; ind < vecTras.size(); ind++)
                          { TrastornosVO trastAux = (TrastornosVO) vecTras.elementAt(ind);%>
                            $("#lstTipTras<%=trastAux.getCodTrastorno()%>").html(data);
                            $("#lstTipTras<%=trastAux.getCodTrastorno()%>").val("<%=trastAux.getCodTipoTrastorno()%>");
			<%}%>
			$("#lstNuevoTipTrast").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los tipos de trastorno");
	  }
	});
}
	 
</script>
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
</head>

<body class="colorFondoPrincipalPestana" onload="cargarCombosTipoTrast();">
<%if(errAlta){%>
<script>alert("Se produjo un error\n en el alta del trastorno ")</script>
<%}%>

<%if(errEdi){%>
<script>alert("Se produjo un error\n en la edición del trastorno ")</script>
<%}%>

<%if(resBor != -99){
    if(resBor>0){%>
        <script>alert("El trastorno se borró correctamente ")</script>
    <%}else{%>
        <script>alert("El trastorno no pudo ser borrado ")</script>
<%}}%>

<form action="" method="post" name="frmTrastornos" id="frmTrastornos">
<table style="width:82%" border="0" align="center" class="tablaListados">
  <tr class="tablaListados">
    <td style="width:28%" class="tdDef"><strong>Tipo Trastorno</strong></td>
    <td style="width:11%" class="tdDef"><strong>Medicado</strong></td>
    <td colspan="3"             class="tdDef"><strong>Medicaci&oacute;n</strong></td>
    <td colspan="2" class="tdDef"><img src="../imagenes/newhab.png" style="width:30" height="30" onclick="lstNuevoTipTrast.style.visibility='visible';chkNuevoMedicado.style.visibility='visible';txtNuevaMedicacion.style.visibility='visible'; btnAnadir.style.visibility='visible'" style="cursor:pointer"/></td>
  </tr>
  <%for(int ind = indInf; ind<indSup;ind++){
      trastVO = (TrastornosVO) vecTras.elementAt(ind);
  %>
  <tr class="tdDef">
    <td class="tdDef">
    	<select name="lstTipTras<%=trastVO.getCodTrastorno()%>" id="lstTipTras<%=trastVO.getCodTrastorno()%>" disabled="disabled"></select>
    </td>
    <td>
    	<input name="chkMedicado<%=trastVO.getCodTrastorno()%>" type="checkbox" id="chkMedicado<%=trastVO.getCodTrastorno()%>" value="true" <%if(trastVO.isMedicado()){%>checked="checked"<%}%>  disabled="disabled"/>
    </td>
    <td style="width:45%">
    	<textarea name="txtMedicacion<%=trastVO.getCodTrastorno()%>" cols="50" rows="3" disabled="disabled" id="txtMedicacion<%=trastVO.getCodTrastorno()%>"><%=trastVO.getMedicacion()%></textarea>
     </td>
     <td style="width:2%" class="center"><input name="btnEdiMod<%=trastVO.getCodTrastorno()%>" type="button" id="btnEdiMod<%=trastVO.getCodTrastorno()%>" value="E" onclick="editaTrast('<%=trastVO.getCodTrastorno()%>')" disabled="disabled"/></td>
      <td style="width:6%"><img src="../imagenes/editar.png" style="width:30" height="30" onclick="habilitaEdi('<%=trastVO.getCodTrastorno()%>');" style="cursor:pointer"/></td>
      <td style="width:8%" class="center">
          <%//if(! EdicionesGestion.estaNivelenEdicion(nivVO.getIdNiv())){%>
            <img src="../imagenes/papelera.png" style="width:30" height="30" onclick="bajaModulo('<%=trastVO.getCodTrastorno()%>','<%=indInf%>','<%=trastVO.getIdAlu()%>');" style="cursor:pointer"/>
          <%//}%>
      </td>
  </tr>
  <%}%>
  <tr class="tdDef">
    <td height="26" class="tdDef">
    	 <span id="valTipTrast">
      		<select name="lstNuevoTipTrast" id="lstNuevoTipTrast" style="visibility:hidden"></select>
      	 	<span class="selectInvalidMsg">*</span>
            <span class="selectRequiredMsg">*</span>
         </span>
    </td>
    <td><input name="chkNuevoMedicado" type="checkbox" id="chkNuevoMedicado" value="true" style="visibility:hidden"/></td>
    <td>
      <span id="valMedicacion">
      	<textarea name="txtNuevaMedicacion" cols="50" rows="2"  style="visibility:hidden" id="txtNuevaMedicacion"></textarea>
      	<span class="textareaRequiredMsg">*</span>
      </span>        
      <input name="txtCodAlu" type="hidden" id="txtCodAlu" value="<%=codInt%>" /> 
    </td>
    <td colspan="3">
    	<span class="center">
      		<input type="button" name="btnAnadir" id="btnAnadir" value="Añadir Trastorno" style="visibility:hidden" onclick="validaForm();"/>
    	</span></td>
    </tr>
  <tr>
    <td colspan="2" class="center">
      <%if( indInf >= 5){%>
      <img src="../imagenes/btnprev.png" style="width:35" height="35" onclick="window.open('trastornosFichaAlumno.jsp?codInt=<%=codInt%>&valInfTrast=<%=(indInf - 5)%>','_self','');">
      <%}%>
      </td>
    <td colspan="2" class="center">&nbsp;</td>
    <td colspan="2" class="center"><%if( indSup < vecTras.size()){%>
      <img src="../imagenes/btnsig.png" style="width:35" height="35" onclick="window.open('trastornosFichaAlumno.jsp?codInt=<%=codInt%>&valInfTrast=<%=(indInf + 5)%>','_self','');"/>
      <%}%></td>
  </tr>
  <tr>
    <td colspan="6" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextarea1  = new Spry.Widget.ValidationTextarea("valMedicacion", {isRequired:false});
var spryselect1 = new Spry.Widget.ValidationSelect("valTipTrast", {invalidValue:"-1"});
</script>
</body>
</html>