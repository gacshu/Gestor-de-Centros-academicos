<%@page import="es.jahernandez.gestion.SeguimientosGestion"%>
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
<html lang="es" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Cursos interesado</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
//Carga de datos del formulario de búsqueda
AlumnosVO       aluVO        = new AlumnosVO();
Vector          listSeg      = new Vector();
SeguimientosVO  segVO        = null;
int             errCodBajSeg = 0;


//Control de paginación
int             valInf   = 0;
int             valSup   = 0;
int             valAnt   = 0;

if(request.getParameter("codInt") != null)
{
    aluVO   = AlumnosGestion.devolverDatosAlumno(request.getParameter("codInt"));
    listSeg = SeguimientosGestion.devolverSegAlu(aluVO.getIdAlu());
}

if(request.getParameter("codErrorBorSeg") != null)
{
    errCodBajSeg = new Integer(request.getParameter("codErrorBorSeg")).intValue();
}

if(request.getParameter("ind") != null)
{
    valInf = new Integer(request.getParameter("ind")).intValue();
}
else
{
    valInf = 0;
}

valSup = valInf + 15 ;
valAnt = valInf - 15 ;

if(valSup > listSeg.size()) valSup = listSeg.size();


%>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />

</head>
<%if(errCodBajSeg == -1){%>
<script>
    alert("Se produjeron errores en\nla baja del seguimiento");
</script>
<%}%>
<body class="colorFondoPrincipalPestana" >
<form action="altaSeguimiento.jsp?codInt=<%=aluVO.getIdAlu()%>&ind=<%=valInf%>" method="post" name="frmSeguimientosInteresado" target="_self" id="frmSeguimientosInteresado">
  <table style="width:90%" border="0" class="tablaCursos">
    <tr>
    <td height="41"><strong>Fecha</strong></td>
    <td><strong>Usuario</strong></td>
    <td><strong>Incidencias</strong></td>
    <td><strong>Curso</strong></td>
    <td>
      <img src="../imagenes/newhab.png" style="width:30" height="30" onclick="frmSeguimientosInteresado.submit();" style="cursor:pointer"/>
    </td>
  </tr>
    <%for(int ind = valInf; ind < valSup; ind++){
        segVO = (SeguimientosVO) listSeg.elementAt(ind);
    %>
        <tr>
            <td><%=new SimpleDateFormat("dd/MM/yyyy").format(segVO.getFecha())%></td>
            <td><%=segVO.getUsuario()%></td>
            <td><%=segVO.getIncidencias()%></td>
            <td><%=CursosGestion.devolverDatosCurso(segVO.getIdCur()).getNomCur()%></td>
            <td><img src="../imagenes/papelera.png" style="width:30" height="30" onclick="window.open('../BorrarSeguimientoServlet?codSeg=<%=segVO.getCodSeg()%>&codInt=<%=segVO.getIdAlu()%>&ind=<%=valInf%>','_self','');" style="cursor:pointer"/></td>
        </tr>
    <%}%>
 
</table>
<table style="width:90%" border="0">
<tr>
    <td style="width:54%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./verSegAlu.jsp?ind=<%=valAnt%>&codInt=<%=aluVO.getIdAlu()%>"><img src="../imagenes/btnprev.png" alt="&lt;---" style="width:50" height="50" border="0" /></a><%}%></td>
    <td style="width:46%" class="cellBtnSub" scope="col"><%if(valSup < listSeg.size()){%><a href="./verSegAlu.jsp?ind=<%=valSup%>&codInt=<%=aluVO.getIdAlu()%>"><img src="../imagenes/btnsig.png" alt="---&gt;" style="width:50" height="50" border="0" /></a><%}%></td>
</tr>
</table>
  	
</form>
</body>
</html>