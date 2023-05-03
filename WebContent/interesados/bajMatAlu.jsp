<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="es.jahernandez.datos.AlumnosVO"%>
<%@page import="es.jahernandez.datos.AluEdiVO"%>
<%@page import="es.jahernandez.datos.EdicionesVO"%>
<%@page import="es.jahernandez.datos.CursosVO"%>

<%@page import="es.jahernandez.accesodatos.AlumnosDAO"%>
<%@page import="es.jahernandez.accesodatos.AluEdiDAO"%>
<%@page import="es.jahernandez.accesodatos.EdicionesDAO"%>
<%@page import="es.jahernandez.accesodatos.CursosDAO"%>

<%@page import="es.jahernandez.gestion.AluEdiGestion"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Matricular Alumno</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>
<%
   //Control de caché
        response.setDateHeader ("Expires", -1); 
        response.setHeader("Pragma","no-cache"); 
        if(request.getProtocol().equals("HTTP/1.1")) 
            response.setHeader("Cache-Control","no-cache"); 
   
    EdicionesVO ediVO    = new EdicionesVO();
    AluEdiVO    aluEdiVO = new AluEdiVO();
    CursosVO    curVO    = new CursosVO();
    AlumnosVO   aluVO    = new AlumnosVO();
    
    if(request.getParameter("codAlu") != null && request.getParameter("codEdi") != null)
    {
       aluEdiVO = AluEdiGestion.devDatAluEdi(request.getParameter("codAlu"), request.getParameter("codEdi"));
       
       ediVO = EdicionesGestion.devolverDatosEdi(aluEdiVO.getIdEdi());
       aluVO = AlumnosGestion.devolverDatosAlumno(aluEdiVO.getIdAlu());
       curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());
    }
%>



<script>
function validarForm()
{
	parent.desactBotBaja();
	frmBajMat.submit();	
}
</script>



</head>

<body class="fondoFormularios" onload="cargarComboEdiciones();">
<form action="../bajaMatriculaServlet" method="post" name="frmBajMat" target="_self" id="frmBajMat">
  <table width="100%" border="0" class="tdDef">
    <tr>
      <td width="13%">Alumno</td>
      <td colspan="3"><%=aluVO.getNombre().trim() + " " + aluVO.getAp1Alu().trim()%></td>
    </tr>
    <tr>
      <td>Curso</td>
      <td colspan="3"><%=curVO.getNomCur().trim()%></td>
    </tr>
    <tr>
      <td>Inicio</td>
      <td width="24%"><%=new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecIn())%></td>
      <td width="10%">Fin</td>
      <td width="53%"><%=new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecFi())%></td>
    </tr>
    <tr>
      <td>Baja</td>
      <td><input name="chkBaja" type="checkbox" id="chkBaja" value="true" <%if(aluEdiVO.isEsBaja()){%>checked="checked"<%}%> /></td>
      <td>Suspensión Pagos</td>
      <td><input name="chkSusPag" type="checkbox" id="chkSusPag" value="true" <%if(aluEdiVO.isEsCong()){%>checked="checked"<%}%>/></td>
    </tr>
    <tr>
      <td>N&uacute;mero Cuenta</td>
      <td colspan="3"><input name="txtNumCuenta" type="text" id="txtNumCuenta" size="30" maxlength="20" value="<%=aluEdiVO.getNumCuenta()%>" /></td>
    </tr>
    <tr>
      <td>Observaciones</td>
      <td colspan="3"><textarea name="txtObserv" id="txtObserv" cols="80" rows="5"><%=aluEdiVO.getObserv()%></textarea></td>
    </tr>
    <tr>
        <td>
            <input type="hidden" name="hidCodAlu"  id="hidCodAlu"  value="<%=aluEdiVO.getIdAlu()%>" />
            <input type="hidden" name="hidCodEdi"  id="hidCodEdi"  value="<%=aluEdiVO.getIdEdi()%>" />
            <input type="hidden" name="hidFecAlta" id="hidFecAlta" value="<%=new SimpleDateFormat("dd/MM/yyy").format(aluEdiVO.getFecAlta())%>" /></td>
      <td colspan="3">&nbsp;</td>
    </tr>
    <tr class="cellBtnSub">
      <td colspan="4">&nbsp;</td>
    </tr>
  </table>
</form>
</body>
</html>