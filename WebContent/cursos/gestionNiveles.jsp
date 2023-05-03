<%@page import="es.jahernandez.gestion.NivelesGestion"%>
<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.accesodatos.CursosDAO"%>
<%@ page import = "es.jahernandez.accesodatos.EdicionesDAO"%>
<%@ page import = "es.jahernandez.accesodatos.NivelesDAO"%>

<%@ page import = "es.jahernandez.datos.NivelesVO"%>

<%@page import  = "java.util.Vector"%>


<html xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="../controlAcceso/includeComAut.jsp"%>
    
<%
   NivelesVO nivVO  = new NivelesVO();
   Vector    vecNiv = null; 

   int      indInf  = 0;
   int      indSup  = 0;
   String   codCur  = "";
   boolean  errAlta = false;
   boolean  errEdi  = false;
   int      resBor  = -99;
   
   //Paginación de resultados
   if(request.getParameter("valInfNiv") != null)
   {
            indInf = new Integer(request.getParameter("valInfNiv")).intValue();
   }

   if(request.getParameter("codCurso") != null)
   {
            codCur =  request.getParameter("codCurso").trim();
            vecNiv =  NivelesGestion.devolverNivCur(codCur);
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
   if(indSup > vecNiv.size()) 
   {
        indSup = vecNiv.size();
   }

 %>   
<head>

<title>Tienda Virtual</title>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../js/validaGlobal.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<script>
function validaForm()
{
     var validarCampos  = true;		
		
     //Validar campos alta nivel        
     validarCampos = sprytextfield1.validate() & sprytextarea1.validate();          
                
	if(validarCampos)
	{
		document.frmNiveles.action = "../AltaNivelServlet?valInfNiv=<%=indInf%>";
		document.frmNiveles.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados");
	}
	
}

function habilitaEdi(codNivel)
{
    eval("document.frmNiveles.txtNombre"      + codNivel).disabled  = false; 
    eval("document.frmNiveles.txtContenidos"  + codNivel).disabled  = false;       
    eval("document.frmNiveles.btnEdiNivel"    + codNivel).disabled  = false;       
}

function editaNivel(codNivel)
{
    var validaCampos = true;
    var valActivo    = "";
	
   
	
    if(Trim(eval("document.frmNiveles.txtNombre"    + codNivel).value) == "")
    {
    	validaCampos = false;
    }
    
    if(Trim(eval("document.frmNiveles.txtContenidos" + codNivel).value) == "")
    {
    	validaCampos = false;
    }
        
    if(validaCampos)
    {    			
        document.frmNiveles.action = "../ModNivelServlet?txtCodNivel="  + codNivel                                                   + 
                                                       "&txtNombre="    + eval("document.frmNiveles.txtNombre"     + codNivel).value +
                                                       "&txtContenido=" + eval("document.frmNiveles.txtContenidos" + codNivel).value + 
                                                       "&txtCodCur="    + eval("document.frmNiveles.txtCodCurso"             ).value + 
													   "&valInfNiv=<%=indInf%>"
        document.frmNiveles.submit();
    }
    else
    {
        alert("Se produjo un error en la introducción de datos del nuevo nivel");
    }
}

function bajaNivel(codNivel,valInfNivel,codCurso)
{
    if(confirm("¿Desea eliminar este nivel?"))
    {
        document.frmNiveles.action = "../BajaNivelServlet?codNivel="    + codNivel 
                                                      + "&codCurso="    + codCurso
                                                      + "&valInfNivel=" + valInfNivel;
        document.frmNiveles.submit();
    }
}



</script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />
</head>

<body class="colorFondoPrincipalPestana">
<%if(errAlta){%>
<script>alert("Se produjo un error\n en el alta del nivel ")</script>
<%}%>

<%if(errEdi){%>
<script>alert("Se produjo un error\n en la edición del nivel ")</script>
<%}%>

<%if(resBor != -99){
    if(resBor>0){%>
        <script>alert("El nivel se borró correctamente ")</script>
    <%}else{%>
        <script>alert("El nivel no pudo ser borrado ")</script>
<%}}%>

<form action="" method="post" name="frmNiveles" id="frmNiveles">
<table width="82%" border="0" align="center" class="tablaListados">
  <tr class="tablaListados">
    <td width="41%" class="tdDef"><strong>Nombre</strong></td>
    <td colspan="3" class="tdDef"><strong>Contenidos</strong></td>
    <td colspan="2" class="tdDef"><img src="../imagenes/newhab.png" width="30" height="30" onclick="txtNuevoNombre.style.visibility='visible';txtNuevoCont.style.visibility='visible';btnAnadir.style.visibility='visible'" style="cursor:pointer"/></td>
  </tr>
  <%for(int ind = indInf; ind<indSup;ind++){
      nivVO = (NivelesVO) vecNiv.elementAt(ind);
  %>
  <tr class="tdDef">
    <td class="tdDef"><input name="txtNombre<%=nivVO.getIdNiv()%>" type="text" disabled="disabled" id="txtNombre<%=nivVO.getIdNiv()%>" value="<%=nivVO.getNomNiv()%>" size="45" maxlength="100" /></td>
      <td colspan="2"><textarea name="txtContenidos<%=nivVO.getIdNiv()%>" cols="50" rows="2" disabled="disabled" id="txtContenidos<%=nivVO.getIdNiv()%>"><%=nivVO.getContenidos()%></textarea></td>
      <td width="2%" class="center"><input name="btnEdiNivel<%=nivVO.getIdNiv()%>" type="button" id="btnEdiNivel<%=nivVO.getIdNiv()%>" value="E" onclick="editaNivel('<%=nivVO.getIdNiv()%>')" disabled="disabled"/></td>
      <td width="5%"><img src="../imagenes/editar.png" width="30" height="30" onclick="habilitaEdi('<%=nivVO.getIdNiv()%>');" style="cursor:pointer"/></td>
      <td width="12%" class="center">
          <%if(! EdicionesGestion.estaNivelenEdicion(nivVO.getIdNiv())){%>
            <img src="../imagenes/papelera.png" width="30" height="30" onclick="bajaNivel('<%=nivVO.getIdNiv()%>','<%=indInf%>','<%=nivVO.getCodCur()%>');" style="cursor:pointer"/>
          <%}%>
      </td>
  </tr>
  <%}%>
  <tr class="tdDef">
    <td height="26" class="tdDef"><span id="valNombre">
      <input name="txtNuevoNombre" type="text" style="visibility:hidden"  id="txtNuevoNombre" size="45" maxlength="100" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    <td colspan="2"><span id="valContenidos">
      <textarea name="txtNuevoCont" cols="50" rows="2"  style="visibility:hidden" id="txtNuevoCont"></textarea>
      <span class="textareaRequiredMsg">*</span></span>        <input name="txtCodCurso" type="hidden" id="txtCodCurso" value="<%=codCur%>" /></td>
    <td colspan="3"><span class="center">
      <input type="button" name="btnAnadir" id="btnAnadir" value="Añadir Nivel" style="visibility:hidden" onclick="validaForm();"/>
    </span></td>
    </tr>
  <tr>
    <td height="30" colspan="2" class="center">
      <%if( indInf >= 5){%>
      <img src="../imagenes/btnprev.png" width="35" height="35" onclick="window.open('gestionNiveles.jsp?codCurso=<%=codCur%>&valInfNiv=<%=(indInf - 5)%>','_self','');">
      <%}%>
      </td>
    <td colspan="2" class="center">&nbsp;</td>
    <td colspan="2" class="center"><%if( indSup < vecNiv.size()){%>
      <img src="../imagenes/btnsig.png" width="35" height="35" onclick="window.open('gestionNiveles.jsp?codCurso=<%=codCur%>&valInfNiv=<%=(indInf + 5)%>','_self','');"/>
      <%}%></td>
  </tr>
  <tr>
    <td colspan="6" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("valNombre");
var sprytextarea1  = new Spry.Widget.ValidationTextarea ("valContenidos");
</script>
</body>
</html>