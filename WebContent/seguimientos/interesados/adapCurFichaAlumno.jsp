<%@page import="es.jahernandez.gestion.AdapCurricularGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import = "es.jahernandez.accesodatos.AdapCurricularDAO"%>
<%@ page import = "es.jahernandez.datos.AdapCurricularVO"%>
<%@ page import  = "java.util.Vector"%>


<html xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="../controlAcceso/includeComAut.jsp"%>
    
<%
   AdapCurricularVO adapCurVO  = new AdapCurricularVO();
   Vector           vecAdapCur = null; 

   int      indInf  = 0;
   int      indSup  = 0;
   String   codAlu  = "";
   boolean  errAlta = false;
   boolean  errEdi  = false;
   int      resBor  = -99;
   
   //Paginación de resultados
   if(request.getParameter("valInfAC") != null)
   {
            indInf = new Integer(request.getParameter("valInfAC")).intValue();
   }

   if(request.getParameter("codInt") != null)
   {
            codAlu     =  request.getParameter("codInt").trim();
            vecAdapCur =  AdapCurricularGestion.devolverAdapCurAlu(codAlu);
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

   indSup = indInf + 10;
   if(indSup > vecAdapCur.size()) 
   {
        indSup = vecAdapCur.size();
   }

 %>   
<head>

<title>Adaptación curricular</title>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../js/validaGlobal.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script><script>
function validaForm()
{
     var validarCampos  = true;		
		
     //Validar campos alta nivel        
     validarCampos = sprytextfield1.validate() & sprytextfield2.validate();          
                
	if(validarCampos)
	{
		document.frmAdapCur.action = "../AltaAdapCurServlet?valInfACu=<%=indInf%>";
		document.frmAdapCur.submit();
	}
	else
	{
		alert("Valide el valor de los campos resaltados");
	}
	
}

function habilitaEdi(codAdapCur)
{
    eval("document.frmAdapCur.txtMateria"   + codAdapCur).disabled  = false; 
    eval("document.frmAdapCur.txtCurso"     + codAdapCur).disabled  = false;       
    eval("document.frmAdapCur.btnEdiAdapCur"+ codAdapCur).disabled  = false;       
}

function editaAdapCur(codAdapCur)
{
    var validaCampos = true;
    var valActivo    = "";
	
    if(Trim(eval("document.frmAdapCur.txtMateria" + codAdapCur).value) == "")
    {
    	validaCampos = false;
    }
    
    if(Trim(eval("document.frmAdapCur.txtCurso"   + codAdapCur).value) == "")
    {
    	validaCampos = false;
    }
        
    if(validaCampos)
    {    			
		document.frmAdapCur.action = "../ModAdapCurServlet?txtCodAdapCur=" + codAdapCur   + 
                                                         "&codInt=<%=codAlu%>"            + 
					                					 "&valInfACu=<%=indInf%>";
        document.frmAdapCur.submit();
    }
    else
    {
        alert("Se produjo un error en la introducción de datos de la nueva adaptación curricular");
    }
}

function bajaAdapCur(codAdapCur,valInfAdapCur ,codAlu)
{
    if(confirm("¿Desea eliminar esta adaptación Curricular?"))
    {
        document.frmAdapCur.action = "../BajaAdapCurServlet?codAdapCur="  + codAdapCur 
                                                        + "&codInt="      + codAlu
                                                        + "&valInfACu="   + valInfAdapCur;
        document.frmAdapCur.submit();
    }
}


</script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
</head>

<body class="colorFondoPrincipalPestana">
<%if(errAlta){%>
<script>alert("Se produjo un error\n en el alta de la adaptación curricular")</script>
<%}%>

<%if(errEdi){%>
<script>alert("Se produjo un error\n en la edición de la adaptación curricular")</script>
<%}%>

<%if(resBor != -99){
    if(resBor>0){%>
        <script>alert("La adaptación curricular se borró correctamente ")</script>
    <%}else{%>
        <script>alert("La adaptación curricular no pudo ser borrada ")</script>
<%}}%>

<form action="" method="post" name="frmAdapCur" id="frmAdapCur">
<table width="82%" border="0" align="center" class="tablaListados">
  <tr class="tablaListados">
    <td width="41%" class="tdDef"><strong>Materia</strong></td>
    <td colspan="3" class="tdDef"><strong>Curso</strong></td>
    <td colspan="2" class="tdDef"><img src="../imagenes/newhab.png" width="30" height="30" onclick="txtNuevaMateria.style.visibility='visible';txtNuevoCurso.style.visibility='visible';btnAnadir.style.visibility='visible'" style="cursor:pointer"/></td>
  </tr>
  <%for(int ind = indInf; ind<indSup;ind++){
      adapCurVO = (AdapCurricularVO) vecAdapCur.elementAt(ind);
  %>
  <tr class="tdDef">
    <td class="tdDef"><input name="txtMateria<%=adapCurVO.getCodAdapCur()%>" type="text" disabled="disabled" id="txtMateria<%=adapCurVO.getCodAdapCur()%>" value="<%=adapCurVO.getMateria()%>" size="45" maxlength="30" /></td>
      <td colspan="2"><input name="txtCurso<%=adapCurVO.getCodAdapCur()%>"   type="text" disabled="disabled" id="txtCurso<%=adapCurVO.getIdAlu()%>"        value="<%=adapCurVO.getCurso()%>" size="45" maxlength="100" /></td>
      <td width="2%" class="center"><input name="btnEdiAdapCur<%=adapCurVO.getCodAdapCur()%>" type="button"  id="btnEdiAdapCur<%=adapCurVO.getCodAdapCur()%>" value="E" onclick="editaAdapCur('<%=adapCurVO.getCodAdapCur()%>')" disabled="disabled"/></td>
      <td width="5%"><img src="../imagenes/editar.png" width="30" height="30" onclick="habilitaEdi('<%=adapCurVO.getCodAdapCur()%>');" style="cursor:pointer"/></td>
      <td width="12%" class="center">
        <img src="../imagenes/papelera.png" width="30" height="30" onclick="bajaAdapCur('<%=adapCurVO.getCodAdapCur()%>','<%=indInf%>','<%=adapCurVO.getIdAlu()%>');" style="cursor:pointer"/> 
      </td>
  </tr>
  <%}%>
  <tr class="tdDef">
    <td height="26" class="tdDef"><span id="valMateria">
      <input name="txtNuevaMateria" type="text" style="visibility:hidden"  id="txtNuevaMateria" size="45" maxlength="30" />
      <span class="textfieldRequiredMsg">*</span></span></td>
    <td colspan="2"><span id="valCurso">
      <input name="txtNuevoCurso" type="text" style="visibility:hidden" id="txtNuevoCurso" size="45" maxlength="100" />
      <span class="textfieldRequiredMsg">*</span></span>      <input name="txtCodAlu" type="hidden" id="txtCodAlu" value="<%=codAlu%>" /></td>
    <td colspan="3"><span class="center">
      <input type="button" name="btnAnadir" id="btnAnadir" value="Añadir Registro" style="visibility:hidden" onclick="validaForm();"/>
    </span></td>
    </tr>
  <tr>
    <td height="30" colspan="2" class="center">
      <%if( indInf >= 5){%>
      <img src="../imagenes/btnprev.png" width="35" height="35" onclick="window.open('adapCurFichaAlumno.jsp?codInt=<%=codAlu%>&valInfAC=<%=(indInf - 10)%>','_self','');">
      <%}%>
      </td>
    <td colspan="2" class="center">&nbsp;</td>
    <td colspan="2" class="center"><%if( indSup < vecAdapCur.size()){%>
      <img src="../imagenes/btnsig.png" width="35" height="35" onclick="window.open('adapCurFichaAlumno.jsp?codInt=<%=codAlu%>&valInfAC=<%=(indInf + 10)%>','_self','');"/>
      <%}%></td>
  </tr>
  <tr>
    <td colspan="6" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("valMateria");
var sprytextfield2 = new Spry.Widget.ValidationTextField("valCurso");
</script>
</body>
</html>