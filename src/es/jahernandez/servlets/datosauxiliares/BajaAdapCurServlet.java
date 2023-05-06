/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.datosauxiliares;

import java.io.IOException;

//import org.apache.catalina.SessionEvent;
import org.apache.log4j.Logger;

import es.jahernandez.datos.AdapCurricularVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.gestion.AdapCurricularGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author JuanAlberto
 */
public class BajaAdapCurServlet extends HttpServlet
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

        HttpSession      sesion       = request.getSession();
        AdapCurricularVO adapCurVO    = new AdapCurricularVO();
        int              resultadoBor = -99;
        String           pagAdapCur   = "0";

        Logger           log          = null;
        ConUsuVO         conUsoVO     = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Borrar Adaptación curricular" );

        }

        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("codAdapCur") != null)
        {
            adapCurVO.setCodAdapCur(request.getParameter("codAdapCur").trim());
        }

        if(request.getParameter("codInt") != null)
        {
            adapCurVO.setIdAlu(request.getParameter("codInt").trim());
        }

        if(request.getParameter("valInfACu") != null)
        {
            pagAdapCur =  request.getParameter("valInfACu");
        }

        resultadoBor = AdapCurricularGestion.eliminaAdapCur(adapCurVO.getCodAdapCur());

        //Redireccionar a gestión niveles
        response.sendRedirect("interesados/adapCurFichaAlumno.jsp?codInt="    + adapCurVO.getIdAlu()
                                                              + "&valInfAC="  + pagAdapCur
                                                              + "&resultBor=" + resultadoBor);

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
        return "Borrar Adaptación curricular servlet";
    }// </editor-fold>


}
