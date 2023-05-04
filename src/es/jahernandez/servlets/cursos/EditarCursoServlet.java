/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

import java.io.IOException;

//import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.CursosVO;
import es.jahernandez.gestion.CursosGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Alberto
 */
public class EditarCursoServlet extends HttpServlet 
{

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        HttpSession sesion       = request.getSession();
        CursosVO    curEdi       = new CursosVO();
        int         resultadoEdi = 0;

        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Editar curso" );
               
        }

        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("hidCodCurso") != null)
        {
            curEdi.setIdCur(request.getParameter("hidCodCurso").trim());
        }

        if(request.getParameter("txtNombre") != null)
        {
            curEdi.setNomCur(request.getParameter("txtNombre").trim());
        }

        if(request.getParameter("lstTipoCurso") != null)
        {
            curEdi.setTipCur(new Integer(request.getParameter("lstTipoCurso").trim()).intValue());
        }

        if(request.getParameter("lstCentros") != null)
        {
            curEdi.setCenCurso(new Integer(request.getParameter("lstCentros").trim()).intValue());
        }

        if(request.getParameter("txtContenido") != null)
        {
             curEdi.setContenido(request.getParameter("txtContenido").trim());
        }       

        resultadoEdi = CursosGestion.editaCurso(curEdi);

        if(resultadoEdi > 0 )
        {
            //Redireccionar a página edición de curso
            response.sendRedirect("cursos/ediCurso.jsp?codCurso=" + curEdi.getIdCur() + "&msgEdiCor=1");
        }
        else
        {
            //Cargamos datos de curso en sesión para cargar páginas siguientes
            sesion.setAttribute("ediCursoErr", curEdi);

            //Redireccionar a edicion curso
            response.sendRedirect("cursos/ediCurso.jsp?errorEdiCode=" + resultadoEdi);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Editar Curso Servlet";
    }// </editor-fold>
}
