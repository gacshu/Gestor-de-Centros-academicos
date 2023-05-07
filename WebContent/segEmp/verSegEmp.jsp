<%@page import="es.jahernandez.gestion.SegEmpGestion"%>
<%@page import="sun.print.resources.serviceui"%>
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

SegEmpVO        segEmpVO = null;
Vector          listSeg  = new Vector();
String          codEmp   = "";

//Control de paginación
int             valInf   = 0;
int             valSup   = 0;
int             valAnt   = 0;



int             errCodBajSeg = 0;

if(request.getParameter("codEmp") != null)
{
    listSeg = SegEmpGestion.devolverSegEmp(request.getParameter("codEmp"));
    codEmp  = request.getParameter("codEmp");
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

valSup = valInf + 10 ;
valAnt = valInf - 10 ;

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
<form action="altaSegEmp.jsp?codEmp=<%=codEmp%>" method="post" name="frmSeguimientosInteresado" target="_self" id="frmSeguimientosInteresado">
  <table style="width:90%" border="0" class="tablaCursos">
    <tr>
    <td><strong>Fecha</strong></td>
    <td><strong>Usuario</strong></td>
    <td><strong>Incidencias</strong></td>
    <td><input name="btnAddSeguimiento" type="button" value="Añadir Seguimiento" onclick="frmSeguimientosInteresado.submit();" /></td>
  </tr>
    <%for(int ind = valInf ; ind < valSup; ind++){
        segEmpVO = (SegEmpVO) listSeg.elementAt(ind);
    %>
        <tr>
            <td><%=new SimpleDateFormat("dd/MM/yyyy").format(segEmpVO.getFecha())%></td>
            <td><%=segEmpVO.getUsuario()%></td>
            <td><%=segEmpVO.getIncidencias()%></td>
            <td><a href="../BorrarSegEmpServlet?codSeg=<%=segEmpVO.getCodSeg()%>&codEmp=<%=segEmpVO.getIdEmp()%>">Eliminar</a></td>
        </tr>
    <%}%>
 
</table>
<table style="width:100%" border="0">
<tr>
    <td style="width:55%" class="cellBtnSub" scope="col"><%if(valAnt >= 0){%><a href="./verSegEmp.jsp?ind=<%=valAnt%>&codEmp=<%=codEmp%>"><img src="../imagenes/btnprev.png" alt="&lt;---" style="width:50" height="50" border="0" /></a><%}%></td>
    <td style="width:45%" class="cellBtnSub" scope="col"><%if(valSup < listSeg.size()){%><a href="./verSegEmp.jsp?ind=<%=valSup%>&codEmp=<%=codEmp%>"><img src="../imagenes/btnsig.png" alt="---&gt;" style="width:50" height="50" border="0" /></a><%}%></td>
</tr>
</table>
  	
</form>
</body>
</html>