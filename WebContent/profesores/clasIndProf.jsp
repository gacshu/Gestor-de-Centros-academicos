<%@page import="es.jahernandez.gestion.CursosGestion"%>
<%@page import="es.jahernandez.gestion.ClasesIndivGestion"%>
<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@page import="es.jahernandez.accesodatos.AlumnosDAO"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "es.jahernandez.accesodatos.ClasesIndivDAO"%>
<%@ page import = "es.jahernandez.datos.ClasesIndivVO"%>
<%@ page import  = "java.util.Vector"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="es.jahernandez.accesodatos.CursosDAO"%>

<html lang="es" xml:lang="es" xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="../controlAcceso/includeComAutProf.jsp"%>
    
<%
   ClasesIndivVO clasIndVO  = new ClasesIndivVO();
   Vector        vecClasInd = null; 

   int      indInf       = 0;
   int      indSup       = 0;
   String   codProf      = "";
   int      annoAct      = new Integer(new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()) ));
   
   int      mesFiltro    = 0;
   int      annoFiltro   = 0;
   
   String   fecFilt      = ""; 
   boolean  mostrarTotal = false;
   
   String   tablaMeses[] = {"Enero" , "Febrero" , "Marzo" , "Abril", "Mayo" , "Junio" , "Julio" , "Agosto" , "Septiembre", "Octubre" , "Noviembre" , "Diciembre" };
   
   
   //Paginación de resultados
   if(request.getParameter("valInfClasInd") != null)
   {
            indInf = new Integer(request.getParameter("valInfClasInd")).intValue();
   }

   if(request.getParameter("strFecha") != null)
   {
            fecFilt = request.getParameter("strFecha").trim();
   }

   if(request.getParameter("codProf") != null)
   {
            codProf     =  request.getParameter("codProf").trim();
            
            if(fecFilt.equals(""))
            {
                vecClasInd =  ClasesIndivGestion.devolverClasesIndProf(codProf);
            }
            else
            {
                vecClasInd   = ClasesIndivGestion.devolverClasesIndProfMes(codProf, fecFilt);
                mostrarTotal = true;
                mesFiltro    = new Integer(fecFilt.substring(0,2)).intValue();
                annoFiltro   = new Integer(fecFilt.substring(2,6)).intValue();
            }
   }
   
         
   if(indInf < 0)
   {
		indInf = 0;
   }

   indSup = indInf + 10;
   if(indSup > vecClasInd.size()) 
   {
        indSup = vecClasInd .size();
   }

 %>   
<head>

<title>Clases individuales</title>
<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../js/validaGlobal.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/js/jquery-ui-1.9.0.js"></script>
<script type="text/javascript" src="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/external/jquery.bgiframe-2.1.2.js"></script>

<link rel="stylesheet" type="text/css" href="../js/bibliotecas/jquery-ui-1.9.0.custom/development-bundle/themes/custom-theme16/jquery.ui.all.css"/>

<script>



function cargaFiltro()
{
	var fecEnvio = "";
	
	if(document.frmClasesInd.lstMesFilt.value  != "-1"  && 
	   document.frmClasesInd.lstAnnoFilt.value != "-1" )
	{
		fecEnvio = document.frmClasesInd.lstMesFilt.value + document.frmClasesInd.lstAnnoFilt.value;
	}
	
	window.open("clasIndProf.jsp?codProf=<%=codProf%>&strFecha=" + fecEnvio, "_self" ,"");	
}



</script>
</head>

<body class="fondoFormularios">

<form action="" method="post" name="frmClasesInd" id="frmClasesInd">
<table style="width:100%" border="0" align="center" class="tablaListados">
            <tr>
               <td height="66" style="width: 374px"><img  onmouseover="this.src='../imagenes/imprimirGr.png'" onmouseout="this.src='../imagenes/imprimirPe.png'" src="../imagenes/imprimirPe.png" style="width:64" height="64" onclick="window.open('../ImpClasesIndProfServlet?codProf=<%=codProf%>&strFecha=<%=fecFilt%>','_listaAlu','');"/></td>
               <td style="text-align: right; margin-left: 80px">&nbsp;</td>
               <td style="text-align: right; margin-left: 80px">&nbsp;
              </td>
            </tr>
            </table>    
<table style="width:100%" border="0" align="center" class="tablaListados">
  <tr>
  	<td style="width:36%" class="tdDef"><select name="lstMesFilt" id="lstMesFilt" on onchange="lstAnnoFilt.value=<%=annoAct%>">
  	  <option value="-1">Seleccione...</option>
  	  <option value="01">Enero</option>
  	  <option value="02">Febrero</option>
  	  <option value="03">Marzo</option>
  	  <option value="04">Abril</option>
  	  <option value="05">Mayo</option>
  	  <option value="06">Junio</option>
  	  <option value="07">Julio</option>
  	  <option value="08">Agosto</option>
  	  <option value="09">Septiembre</option>
  	  <option value="10">Octubre</option>
  	  <option value="11">Noviembre</option>
  	  <option value="12">Diciembre</option>
	  </select>
  	  <select name="lstAnnoFilt" id="lstAnnoFilt">
  	    <option>Seleccione...</option>
            <%for(int ind = annoAct -50;ind < annoAct+50;ind++){%>
            <option value="<%=ind%>"><%=ind%></option>
            <%}%>
      </select></td>
    <td style="width:9%" class="tdDef">
    	<input type="button" name="btnVerRecibos" id="btnVerRecibos" value="Ver Clases Mes" onclick="cargaFiltro();"/>
    </td>
    <td style="width:20%" class="tdDef"></td>
    <td colspan="3" class="tdDef"><%if(mostrarTotal){%>
      <strong>Clases del mes de <%=tablaMeses[mesFiltro-1] + " de " + annoAct%></strong><%}%>
    </td>
    </tr>
  <tr class="tablaListados">
    <td style="width:36%" class="tdDef"><strong>Curso</strong></td>
    <td style="width:9%" class="tdDef"><strong>Fecha</strong></td>
    <td colspan="2" class="tdDef"><strong>Alumno</strong></td>
    <td colspan="2" class="tdDef"><strong>Tarifa</strong></td>
    </tr>
  <%for(int ind = indInf; ind<indSup;ind++){
      clasIndVO = (ClasesIndivVO) vecClasInd.elementAt(ind);
  %>
  <tr class="tdDef">
      <td class="tdDef"><%=CursosGestion.devolverDatosCurso(clasIndVO.getIdCur()).getNomCur()%></td>
    <td class="tdDef"><%=new SimpleDateFormat("dd/MM/yyyy").format(clasIndVO.getFecClase())%></td>
    <td colspan="2" class="tdDef"><%=AlumnosGestion.devolverDatosAlumno(clasIndVO.getIdAlu()).getNombre() + " " + AlumnosGestion.devolverDatosAlumno(clasIndVO.getIdAlu()).getAp2Alu()%></td>
      <td colspan="2"><%=new DecimalFormat("#######.##").format(clasIndVO.getTarifa())%></td>
    </tr>
  <%}%>
  <tr>
    <td height="30" colspan="5" class="center">
      <%if( indInf >= 10){%>
      <img src="../imagenes/btnprev.png" style="width:35" height="35" onclick="window.open('clasIndProf.jsp?codProf=<%=codProf%>&strFecha=<%=fecFilt%>&valInfClasInd=<%=(indInf - 10)%>','_self','');">
      <%}%>
      </td>
    <td style="width:27%" class="center">
    	<%if( indSup < vecClasInd.size()){%>
      <img src="../imagenes/btnsig.png" style="width:35" height="35" onclick="window.open('clasIndProf.jsp?codInt=<%=codProf%>&strFecha=<%=fecFilt%>&valInfClasInd=<%=(indInf + 10)%>','_self','');"/>
      <%}%>
    </td>
    </tr>
  <tr>
    <td colspan="6" class="cellBtnSub">&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>