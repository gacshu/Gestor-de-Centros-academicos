/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

import java.io.IOException;

//import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.NivelesVO;
import es.jahernandez.gestion.NivelesGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Alberto
 */
public class ModNivelServlet extends HttpServlet
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

        HttpSession   sesion       = request.getSession();
        NivelesVO     nivVO        = new NivelesVO();
        int           resultadoAlt = 0;
        String        pagGesNiv    = "";


        Logger               log      = null;
        ConUsuVO             conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Modificar nivel" );

        }


        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtCodNivel") != null)
        {
            nivVO.setIdNiv(request.getParameter("txtCodNivel").trim());
        }

        if(request.getParameter("txtNombre") != null)
        {
            nivVO.setNomNiv(request.getParameter("txtNombre").trim());
        }

        if(request.getParameter("txtContenido") != null)
        {
            nivVO.setContenidos(request.getParameter("txtContenido"));
        }

        if(request.getParameter("txtCodCur") != null)
        {
            nivVO.setCodCur(request.getParameter("txtCodCur"));
        }

        if(request.getParameter("valInfNiv") != null)
        {
            pagGesNiv =  request.getParameter("valInfNiv");
        }

        resultadoAlt = NivelesGestion.editaNivel(nivVO);

        if(resultadoAlt <= 0)
        {

            //Redireccionar a página de gestion de niveles
            response.sendRedirect("cursos/gestionNiveles.jsp?codCurso="  + nivVO.getCodCur()
                                                         + "&errorEdi="  + resultadoAlt
                                                         + "&valInfNiv=" + pagGesNiv);
        }
        else
        {
            //Redireccionar a página de gestión de niveles
            response.sendRedirect("cursos/gestionNiveles.jsp?codCurso="  + nivVO.getCodCur()
                                                         + "&valInfNiv=" + pagGesNiv);
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
        return "Modifica NIvel Srvlet";
    }// </editor-fold>
}
