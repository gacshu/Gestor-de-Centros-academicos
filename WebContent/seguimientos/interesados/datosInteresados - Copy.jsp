<%@page import="es.jahernandez.gestion.AlumnosGestion"%>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.Date"%>
<%@ page import = "java.util.GregorianCalendar"%>
<%@ page import = "java.util.Vector"%>
<%@ page import = "java.text.SimpleDateFormat"%>


<%@ page import = "es.jahernandez.datos.*"%>
<%@ page import = "es.jahernandez.accesodatos.*"%>

<html>

<head>
<title>Alta Interesados</title>
<%@ include file="../controlAcceso/includeComAut.jsp"%>

<%
//Carga de datos de session del alumno
AlumnosVO aluVO          = new AlumnosVO();
String    valIniList     = "0";

//Comprueba que si viene del servlet de alta de alumnos o de la lista de búsqueda de alumnos
if(session.getAttribute("datosAlumnoAlta") != null)
{
    aluVO = (AlumnosVO) session.getAttribute("datosAlumnoAlta");
    session.removeAttribute("datosAlumnoAlta");
}
else
{
    if(request.getParameter("codInt") != null)
    {
        aluVO = AlumnosGestion.devolverDatosAlumno(request.getParameter("codInt"));
    }
}

if(request.getParameter("ind") != null)
{
    valIniList = request.getParameter("ind");
}


%>

<link href="../css/disenno.css" rel="stylesheet" type="text/css" />
<script src="../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationSelect.js" type="text/javascript"></script>
<script src="../SpryAssets/SpryValidationTextarea.js" type="text/javascript"></script>
<link href="../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationSelect.css" rel="stylesheet" type="text/css" />
<link href="../SpryAssets/SpryValidationTextarea.css" rel="stylesheet" type="text/css" />
</head>

<body class="fondoFormularios">
<script src="../js/validaGlobal.js"></script>
<script language="JavaScript" type="text/JavaScript">
function gestionPestana(pestana)
{
	switch (pestana)
	{
		case 1: 
				frmFichaInteresado.btnDatPer.disabled      = true;
				frmFichaInteresado.btnCursos.disabled      = false;
				frmFichaInteresado.btnMatriculas.disabled  = false;
				
				frmFichaInteresado.btnDatPer.className     = "colorFondoPrincipalPestana";
				frmFichaInteresado.btnCursos.className     = "colorFondoBtnDesactivado";
				frmFichaInteresado.btnMatriculas.className = "colorFondoBtnDesactivado";
								
				document.all("fraPestanas").src="./datPerFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>";
				break;				
		case 2:
				frmFichaInteresado.btnDatPer.disabled      = false;
				frmFichaInteresado.btnCursos.disabled      = true;
				frmFichaInteresado.btnMatriculas.disabled  = false;
				
				frmFichaInteresado.btnDatPer.className     = "colorFondoBtnDesactivado";				
				frmFichaInteresado.btnCursos.className     = "colorFondoPrincipalPestana";
				frmFichaInteresado.btnMatriculas.className = "colorFondoBtnDesactivado";
												
				document.all("fraPestanas").src="./cursosFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>";
				break; 
		case 3:
				frmFichaInteresado.btnDatPer.disabled      = false;
				frmFichaInteresado.btnCursos.disabled      = false;
				frmFichaInteresado.btnMatriculas.disabled  = true;
				
				frmFichaInteresado.btnDatPer.className     = "colorFondoBtnDesactivado";
				frmFichaInteresado.btnCursos.className     = "colorFondoBtnDesactivado";
				frmFichaInteresado.btnMatriculas.className = "colorFondoPrincipalPestana";
				
				document.all("fraPestanas").src="./matriculasFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>";
				break;
		
	}
	
}

function cargaPaginaAltaEmpresa()
{
	if(sprytextfield3.validate())  //La fecha tiene que ser correcta o vacia
	{
		frmAltaInteresado.action = "../CargaAltaEmpresasServlet";
		frmAltaInteresado.submit();
	}
}


</script>


<form action="./datosInteresados.jsp" method="post" name="frmFichaInteresado" target="_self" id="frmFichaInteresado">
  <table width="100%" border="0">
  <tr class="thDef">
    <th height="50" colspan="3" scope="col">Ficha Interesado</th>
  </tr>
  <tr>
    <td width="38%">
      <span id="txtValNombre">
      <input name="txtNombre" type="text" class="tdDef" id="txtNombre" value="<%=aluVO.getNombre()%>" size="50" maxlength="50" />
      <span class="textfieldRequiredMsg">*.</span><span class="textfieldInvalidFormatMsg">*</span></span>
    </td>
    <td width="41%"><span id="txtValApe">
    <input name="txtApe" type="text" class="tdDef" id="txtApe" value="<%=aluVO.getAp1Alu()%>" size="50" maxlength="50" />
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*</span></span></td>
    <td width="21%" class="tdDef"><span id="txtValFecNac">
    <label>Fecha Nac.
        <input name="txtFecNac" type="text" id="txtFecNac" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(aluVO.getFecNac())%>" />
    </label>
    <span class="textfieldRequiredMsg">*</span><span class="textfieldInvalidFormatMsg">*.</span></span></td>
  </tr>
</table>
<br><br>
<table width="100%" border="0" cellspacing="0">
  <tr>
    <th class="tdDef" scope="col"><input name="btnDatPer" type="button" class="colorFondoPrincipalPestana" id="btnDatPer" value="Datos Personales" onclick="gestionPestana(1);" />
      <input name="btnCursos" type="button" class="colorFondoBtnDesactivado" id="btnCursos" value="Cursos" onclick="gestionPestana(2);"/> <input name="btnMatriculas" type="button" class="colorFondoBtnDesactivado" id="btnMatriculas" value="Matrículas" onclick="gestionPestana(3);"/></th>
  </tr>
  <tr>
    <th align="left" scope="col"><iframe class="colorFondoPrincipalPestana" id="fraPestanas" frameborder="0" src="datPerFichaAlumno.jsp?codInt=<%=aluVO.getIdAlu()%>" width="1400" height="600" >
    </iframe>&nbsp;</th>
  </tr>
  <tr>
      <td align="left" class="cellBtnSub" scope="col"><a href="./RBInteresado.jsp?ind=<%=valIniList%>" target="_self">Volver lista</a></td>
  </tr>
</table>
</form>
<script type="text/javascript">
var sprytextfield1 = new Spry.Widget.ValidationTextField("txtValNombre", "none");
var sprytextfield2 = new Spry.Widget.ValidationTextField("txtValApe", "none");
var sprytextfield3 = new Spry.Widget.ValidationTextField("txtValFecNac", "date", {format:"dd/mm/yyyy", hint:"dd/mm/yyyy", isRequired:false});
</script>
</body>
</html>