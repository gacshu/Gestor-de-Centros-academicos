<%
	//Comprueba si se está autorizado a ver la página
        boolean          autorizado = false;
        
        if(session.getAttribute("usuario")==null)
	{
		response.sendRedirect("/GestorCentrosAcademicos/errorPages/errorNoAutor.jsp");
	}
	else
	{
		es.jahernandez.datos.ConUsuVO cVO = (es.jahernandez.datos.ConUsuVO) session.getAttribute("usuario");
		if(cVO.getNivelAcceso() >= 2)
		{
			autorizado= true;
		}
		if(! autorizado)
		{
			response.sendRedirect("/GestorCentrosAcademicos/errorPages/errorNoAutor.jsp");
		}
	}
%>