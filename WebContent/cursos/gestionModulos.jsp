<%@page import="es.jahernandez.gestion.ModulosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.accesodatos.CursosDAO"%>
<%@ page import = "es.jahernandez.accesodatos.EdicionesDAO"%>
<%@ page import = "es.jahernandez.accesodatos.ModulosDAO"%>

<%@ page import = "es.jahernandez.datos.ModulosVO"%>

<%@page import  = "java.util.Vector"%>


<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="../controlAcceso/includeComAut.jsp"%>
    
<%
   ModulosVO modVO  = new ModulosVO();
   Vector    vecMod = null; 

   int      indInf  = 0;
   int      indSup  = 0;
   String   codCur  = "";
   boolean  errAlta = false;
   boolean  errEdi  = false;
   int      resBor  = -99;
   
   //Paginación de resultados
   if(request.getParameter("valInfMod") != null)
   {
            indInf = new Integer(request.getParameter("valInfMod")).intValue();
   }

   if(request.getParameter("codCurso") != null)
   {
            codCur =  request.getParameter("codCurso").trim();
            vecMod =  ModulosGestion.devolverModCur(codCur);
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
   if(indSup > vecMod.size()) 
   {
        indSup = vecMod.size();
   }

 %>   
<head>

<title>Tienda Virtual</title>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../js/validaGlobal.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js"  type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js"    type="text/javascript"></script>

<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>


<script>
function validaForm()
{
     var validarCampos  = true;			
     //Validar campos alta nivel        
     validarCampos = sprytextfield1.validate() & sprytextarea1.validate() &
	 			     sprytextfield2.validate() & spryselect1.validate();          
                
	if(validarCampos)
	{
		document.frmModulos.action = "../AltaModuloServlet?valInfMod=<%=indInf%>";
		document.frmModulos.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados");
	}
}

function habilitaEdi(codMod)
{
    eval("document.frmModulos.txtNombre"   + codMod).disabled  = false; 
    eval("document.frmModulos.txtDescrip"  + codMod).disabled  = false;
	eval("document.frmModulos.txtHoras"    + codMod).disabled  = false;       
    eval("document.frmModulos.btnEdiMod"   + codMod).disabled  = false;
	eval("document.frmModulos.lstAreas"    + codMod).disabled  = false;       
}

function editaMod(codMod)
{
    var validaCampos = true;
    var valActivo    = "";
	
    if(Trim(eval("document.frmModulos.txtNombre"  + codMod).value) == "")
    {
    	validaCampos = false;
    }
    
    if(Trim(eval("document.frmModulos.txtDescrip" + codMod).value) == "")
    {
    	validaCampos = false;
    }
	
	if(Trim(eval("document.frmModulos.txtHoras"   + codMod).value) == "" ||
	   isNaN(Trim(eval("document.frmModulos.txtHoras"   + codMod).value)))
    {
    	validaCampos = false;
    }
        
    if(validaCampos)
    {    			
        document.frmModulos.action = "../EditarModuloServlet?txtCodMod="  + codMod                                                + 
                                                           "&txtNombre="  + eval("document.frmModulos.txtNombre"  + codMod).value +
                                                           "&txtDescrip=" + eval("document.frmModulos.txtDescrip" + codMod).value + 
                                                           "&txtHoras="   + eval("document.frmModulos.txtHoras"   + codMod).value + 
														   "&txtArea="    + eval("document.frmModulos.lstArea"    + codMod).value + 
														   "&txtCodCur="  + eval("document.frmModulos.txtCodCurso"        ).value + 
													       "&valInfNiv=<%=indInf%>"
        document.frmModulos.submit();
    }
    else
    {
        alert("Se produjo un error en la edición de datos del nuevo módulo");
    }
}

function bajaModulo(codMod,valInfMod,codCurso)
{
    if(confirm("¿Desea eliminar este módulo?"))
    {
        document.frmModulos.action = "../BajaModuloServlet?codMod="    + codMod 
                                                       + "&codCurso="  + codCurso
                                                       + "&valInfMod=" + valInfMod;
        document.frmModulos.submit();
    }
}

function cargarCombosAreas(idArea)
{   
	$.ajax({
	  url: "../CargaComboAreaServlet?valSel=" + idArea,
	  success: function(data) {
		$(document).ready(function() {
			<%for(int ind=0; ind < vecMod.size(); ind++)
                          { ModulosVO modAux = (ModulosVO) vecMod.elementAt(ind);%>
                            $("#lstArea<%=modAux.getCodMod()%>").html(data);
                            $("#lstArea<%=modAux.getCodMod()%>").val("<%=modAux.getCodArea()%>");
			<%}%>
			$("#lstNuevoArea").html(data);
		});
	  },
	  error:function (xhr, ajaxOptions, thrownError) 
	  {
		alert("Se ha producido un error cargando los tipos de cursos");
	  }
	});
}
	 
</script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
</head>

<body class="colorFondoPrincipalPestana" onload="cargarCombosAreas();">
<%if(errAlta){%>
<script>alert("Se produjo un error\n en el alta del módulo ")</script>
<%}%>

<%if(errEdi){%>
<script>alert("Se produjo un error\n en la edición del módulo ")</script>
<%}%>

<%if(resBor != -99){
    if(resBor>0){%>
        <script>alert("El módulo se borró correctamente ")</script>
    <%}else{%>
        <script>alert("El módulo no pudo ser borrado ")</script>
<%}}%>

<form action="" method="post" name="frmModulos" id="frmModulos">
<table style="width:82%" border="0" align="center" class="tablaListados">
  <tr class="tablaListados">
    <td style="width:26%" class="tdDef"><strong>Nombre</strong></td>
    <td style="width:33%" class="tdDef"><strong>Descripci&oacute;n</strong></td>
    <td             class="tdDef"><strong>&Aacute;rea</strong></td>
    <td colspan="3" class="tdDef"><strong>Horas</strong></td>
    <td colspan="2" class="tdDef"><img src="../imagenes/newhab.png" style="width:30" height="30" onclick="txtNuevoNombre.style.visibility='visible';txtNuevoDescrip.style.visibility='visible';lstNuevoArea.style.visibility='visible'; txtNuevoHoras.style.visibility='visible';btnAnadir.style.visibility='visible'" style="cursor:pointer"/></td>
  </tr>
  <%for(int ind = indInf; ind<indSup;ind++){
      modVO = (ModulosVO) vecMod.elementAt(ind);
  %>
  <tr class="tdDef">
    <td class="tdDef"><input name="txtNombre<%=modVO.getCodMod()%>" type="text" disabled="disabled" id="txtNombre<%=modVO.getCodMod()%>" value="<%=modVO.getNombre()%>" size="45" maxlength="100" /></td>
      <td><textarea name="txtDescrip<%=modVO.getCodMod()%>" cols="50" rows="2" disabled="disabled" id="txtDescrip<%=modVO.getCodMod()%>"><%=modVO.getDescripcion()%></textarea></td>
      <td style="width:16%">
        <select name="lstAreas<%=modVO.getCodMod()%>" id="lstArea<%=modVO.getCodMod()%>" disabled="disabled"></select></td>
      <td style="width:8%"><input name="txtHoras<%=modVO.getCodMod()%>" type="text" disabled="disabled" id="txtHoras<%=modVO.getCodMod()%>" value="<%=modVO.getNumHoras()%>" size="5" maxlength="4" /></td>
      <td style="width:4%" class="center"><input name="btnEdiMod<%=modVO.getCodMod()%>" type="button" id="btnEdiMod<%=modVO.getCodMod()%>" value="E" onclick="editaMod('<%=modVO.getCodMod()%>')" disabled="disabled"/></td>
      <td style="width:4%"><img src="../imagenes/editar.png" style="width:30" height="30" onclick="habilitaEdi('<%=modVO.getCodMod()%>');" style="cursor:pointer"/></td>
      <td style="width:9%" class="center">
          <%//if(! EdicionesGestion.estaNivelenEdicion(nivVO.getIdNiv())){%>
            <img src="../imagenes/papelera.png" style="width:30" height="30" onclick="bajaModulo('<%=modVO.getCodMod()%>','<%=indInf%>','<%=modVO.getCodCur()%>');" style="cursor:pointer"/>
          <%//}%>
      </td>
  </tr>
  <%}%>
  <tr class="tdDef">
    <td height="26" class="tdDef"><span id="valNombre">
      <input name="txtNuevoNombre" type="text" style="visibility:hidden"  id="txtNuevoNombre" size="45" maxlength="100" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    <td><span id="valDescrip">
      <textarea name="txtNuevoDescrip" cols="50" rows="2"  style="visibility:hidden" id="txtNuevoDescrip"></textarea>
      <span class="textareaRequiredMsg">*</span></span>        <input name="txtCodCurso" type="hidden" id="txtCodCurso" value="<%=codCur%>" /></td>
    <td><span id="valNuevoArea">
      <select name="lstNuevoArea" id="lstNuevoArea" style="visibility:hidden"></select>
      <span class="selectInvalidMsg">*.</span><span class="selectRequiredMsg">*</span></span></td>
    <td><span id="valHoras">
    <input name="txtNuevoHoras" type="text" id="txtNuevoHoras" size="5" maxlength="4" style="visibility:hidden" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td colspan="3"><span class="center">
      <input type="button" name="btnAnadir" id="btnAnadir" value="Añadir Módulo" style="visibility:hidden" onclick="validaForm();"/>
    </span></td>
    </tr>
  <tr>
    <td colspan="2" class="center">
      <%if( indInf >= 5){%>
      <img src="../imagenes/btnprev.png" style="width:35" height="35" onclick="window.open('gestionModulos.jsp?codCurso=<%=codCur%>&valInfMod=<%=(indInf - 5)%>','_self','');">
      <%}%>
      </td>
    <td colspan="3" class="center">&nbsp;</td>
    <td colspan="2" class="center"><%if( indSup < vecMod.size()){%>
      <img src="../imagenes/btnsig.png" style="width:35" height="35" onclick="window.open('gestionModulos.jsp?codCurso=<%=codCur%>&valInfMod=<%=(indInf + 5)%>','_self','');"/>
      <%}%></td>
  </tr>
  <tr>
    <td colspan="7" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("valNombre");
var sprytextfield2 = new Spry.Widget.ValidationTextField("valHoras", "integer");
var sprytextarea1  = new Spry.Widget.ValidationTextarea ("valDescrip");
var spryselect1 = new Spry.Widget.ValidationSelect("valNuevoArea", {invalidValue:"-1"});
</script>
</body>
</html>