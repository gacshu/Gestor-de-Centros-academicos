<%@page import="es.jahernandez.gestion.EdicionesGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@page import="es.jahernandez.datos.EdicionesVO"%>
<%@page import="es.jahernandez.accesodatos.EdicionesDAO"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Mensaje Matricula</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>
<%
    String  mensaje   = "";
    String  tipoMen   = "";  //1 alta 2 edicion
    String  codAlu    = "";
    String  codEdi    = "";
    int     codErr    = -99;
    boolean verBotMat = false;
    
    EdicionesVO ediVO = new EdicionesVO();
    
    
    if(request.getParameter("codOper") != null)
    {
       tipoMen = request.getParameter("codOper");
    }
    
    if(request.getParameter("codMen") != null)
    {
       codErr = new Integer(request.getParameter("codMen")).intValue();
    }
	
    if(request.getParameter("codAlu") != null)
    {
       codAlu = request.getParameter("codAlu");
    }
	
    if(request.getParameter("codEdi") != null)
    {
       codEdi = request.getParameter("codEdi");
       ediVO = EdicionesGestion.devolverDatosEdi(codEdi);
    }
	
    
    if(tipoMen.equals("1"))
    {
        if(codErr > 0)
        {
            mensaje = "La matriculación se realizó de manera correcta";
			verBotMat = true;
        }
        else
        {
            mensaje = "Se produjo un error realizando la matriculación";
            if(codErr == -2)
            {
                mensaje = mensaje + "<br>No hay plazas disponibles";
            }
        }
            
    }
    
    if(tipoMen.equals("2"))
    {
        if(codErr > 0)
        {
            mensaje = "La edición de la matricula se realizó de manera correcta";
        }
        else
        {
            mensaje = "Se produjo un error editando  la matricula";
            if(codErr == -2)
            {
                mensaje = mensaje + "<br>No hay plazas disponibles";
            }
        }
    }
%>


<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
</head>

<body class="fondoFormularios">
<table width="100%" border="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td class="tdDef"><%=mensaje%></td>
  </tr>
  <tr>
    <td class="cellBtnSub">
    	<%if(verBotMat){%>
            <input type="button" name="btnImpMat" id="btnImpMat" value="Imprimir" onclick="window.open('../ImpImpresoMatServlet?codAlu=<%=codAlu%>&codEdi=<%=codEdi%>','_impresoMat');  " />
            <%if(ediVO.getPrecioR()>0){%>
            <input type="button" name="btnRecibo" id="btnRecibo" value="Generar Recibo" onclick="window.open('../ImprimirReciboMesAluServlet?codAlu=<%=codAlu%>&codEdi=<%=codEdi%>','_impresoMat');  " />
        <%}}%>    
    </td>
  </tr>
</table>

</body>
</html>