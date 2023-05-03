
<%@page import="es.jahernandez.gestion.NivelesGestion"%>
<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.GregorianCalendar"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.text.SimpleDateFormat"%>


<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>
<%@page import="es.jahernandez.gestion.AluEdiGestion"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Cursos interesado</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Carga de datos del formulario de búsqueda
AlumnosVO aluVO      = new AlumnosVO();
Vector    listMatAlu = new Vector();
AluEdiVO  aluEdiVO   = null;

String    cssFI      = "";

if(request.getParameter("codInt") != null)
{
    aluVO      = AlumnosGestion.devolverDatosAlumno(request.getParameter("codInt"));
    listMatAlu = AluEdiGestion.devAluEdi(aluVO.getIdAlu());
}

%>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />

</head>

<body class="colorFondoPrincipalPestana" >
<form action="./matriculasFichaAlumno.jsp" method="post" name="frmMatriculasInteresado" target="_self" id="frmMatriculasInteresado">
    <table width="90%" border="0" class="tablaCursos">
      <tr>
    <td width="15%" height="41"><strong>Curso</strong></td>
    <td width="11%"><strong>Nivel</strong></td>
    <td width="5%"><strong>Inicio</strong></td>
    <td width="5%"><strong>Fin</strong></td>
    <td width="6%"><strong>Alta</strong></td>
    <td width="5%"><strong>Baja</strong></td>
    <td width="9%"><strong>Pago Susp.</strong></td>
    <td width="23%"><strong>Observaciones</strong></td>
    <td colspan="2"><strong>
     <input type="button" name="btnVerRec" id="btnVerRec" value="Ver Recibos" onclick="window.open('../ImpHistRecServlet?lstAlum=<%=aluVO.getIdAlu()%>','ra','');"/>
     <input type="button" name="btnLiqRec" id="btnLiqRec" value="Liquidar Recibos" onclick="parent.cargaPagRec('<%=aluVO.getIdAlu()%>')"/>
    </strong></td>
  </tr>
    <%for(int ind = 0; ind < listMatAlu.size(); ind++){  
        aluEdiVO = (AluEdiVO) listMatAlu.elementAt(ind);   
		
		cssFI = ind%2 == 0 ? "tablaListadoExtensa" : "colorFondoFilaImparListadoPestana";
    %>
        <tr class="<%=cssFI%>">
            <td height="26"><%=CursosGestion.devolverDatosCurso(EdicionesGestion.devolverDatosEdi(aluEdiVO.getIdEdi()).getIdCur()).getNomCur() %></td>
            <td><%=NivelesGestion.devolverDatosNivel(EdicionesGestion.devolverDatosEdi(aluEdiVO.getIdEdi()).getIdNiv()).getNomNiv()%></td>
            <td><%=new SimpleDateFormat("dd/MM/yyyy").format(EdicionesGestion.devolverDatosEdi(aluEdiVO.getIdEdi()).getFecIn())%></td>
            <td><%=new SimpleDateFormat("dd/MM/yyyy").format(EdicionesGestion.devolverDatosEdi(aluEdiVO.getIdEdi()).getFecFi())%></td>
            <td><%=new SimpleDateFormat("dd/MM/yyyy").format(aluEdiVO.getFecAlta())%></td>
            <td><%if(aluEdiVO.isEsBaja()){%><img src="../imagenes/si.png" width="16" height="16" alt="X" /><%}%></td>
            <td><%if(aluEdiVO.isEsCong()){%><img src="../imagenes/si.png" width="16" height="16" alt="X" /><%}%></td>
            <td><%=aluEdiVO.getObserv()%></td>
            <td width="10%" align="center">
            <input type="button" name="btnImpMat<%=ind%>" id="btnImpMat<%=ind%>" value="Imprimir" onclick="window.open('../ImpImpresoMatServlet?codAlu=<%=aluEdiVO.getIdAlu()%>&codEdi=<%=aluEdiVO.getIdEdi()%>','_impresoMat','');  " />
            </td>
            <td width="11%" align="center">
              <input type="button" name="btnMat<%=ind%>" id="btnMat<%=ind%>" value="Baja" onclick="parent.cargaPaginaBajMat('<%=aluEdiVO.getIdAlu()%>','<%=aluEdiVO.getIdEdi()%>')"/>
            </td>
        </tr>
    <%}%>
 
</table>

  	
</form>
</body>
</html>