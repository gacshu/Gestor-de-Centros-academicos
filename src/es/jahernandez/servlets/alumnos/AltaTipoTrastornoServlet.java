/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.alumnos;

import java.io.IOException;

import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.TipTrastVO;
import es.jahernandez.gestion.TipTrastGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author JuanAlberto
 */
public class AltaTipoTrastornoServlet extends HttpServlet
{

     /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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

        HttpSession sesion    = request.getSession();

        TipTrastVO  tipTrasVO = new TipTrastVO();
        int         resAlt    = 0;

        Logger               log      = null;
        ConUsuVO             conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta tipo trastorno" );

        }


        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtNombre") != null)
        {
            tipTrasVO.setDescrip(request.getParameter("txtNombre").trim());
        }

        resAlt = TipTrastGestion.guardarTipTrast(tipTrasVO);

        if(resAlt >= 0)
        {
            //Redireccionar a página de edición de Tipo Cursos
            response.sendRedirect("interesados/ediTipTrast.jsp");
        }
        else
        {
            //Guardamos el tipo de curso en sesion
            sesion.setAttribute("nuevoTipTrastErr",tipTrasVO);
            //Volvemos a alta de de seguimientos
            response.sendRedirect("interesados/altaTipTrast.jsp?errorCode=" + resAlt);
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
    public String getServletInfo()
    {
        return "Alta clase individual Servlet";
    }// </editor-fold>
}
