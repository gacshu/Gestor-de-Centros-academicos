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
public class AltaCursosServlet extends HttpServlet {

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

        HttpSession   sesion       = request.getSession();
        CursosVO      cursoVO      = new CursosVO();
        String        resultadoAlt = "";

        Logger               log      = null;
        ConUsuVO             conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta cursos" );

        }

        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtNombre") != null)
        {
            cursoVO.setNomCur(request.getParameter("txtNombre").trim());
        }

        if(request.getParameter("lstTipoCurso") != null)
        {
            cursoVO.setTipCur(new Integer(request.getParameter("lstTipoCurso").trim()).intValue());
        }

        if(request.getParameter("lstCentros") != null)
        {
            cursoVO.setCenCurso(new Integer(request.getParameter("lstCentros").trim()).intValue());
        }

        if(request.getParameter("txtContenido") != null)
        {
            cursoVO.setContenido(request.getParameter("txtContenido").trim());
        }

        resultadoAlt = CursosGestion.guardarCurso(cursoVO);

        if(resultadoAlt.equals("-1") || resultadoAlt.equals("-2"))
        {
            //Cargamos datos de nueva aula en sesión para cargar páginas siguientes
            sesion.setAttribute("nuevoCursoErr", cursoVO);

            //Redireccionar a alta de aula
            response.sendRedirect("cursos/altaCurso.jsp?errorCode=" + resultadoAlt);
        }
        else
        {
            //Redireccionar a página edición de aulas
            response.sendRedirect("cursos/ediCurso.jsp?codCurso="    + resultadoAlt);
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
        return "AltaCursoServlet";
    }// </editor-fold>
}
