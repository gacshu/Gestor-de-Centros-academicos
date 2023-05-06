/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.alumnos;

import java.io.IOException;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import es.jahernandez.datos.ClasesIndivVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.gestion.ClasesIndivGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author JuanAlberto
 */
public class AltaClaseIndServlet extends HttpServlet
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

        HttpSession   sesion    = request.getSession();

        ClasesIndivVO clasIndVO = new ClasesIndivVO();
        int           resAlt    = 0;

        Logger        log       = null;
        ConUsuVO      conUsoVO  = null;
        String        indPag    = "";
        String        strFecha  = "";

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Alta clase individual" );

        }

        // Se comprueba que se hayan pasado los parÃ¡metros y se inicializan valores
        if(request.getParameter("lstNuevoCurso") != null)
        {
            clasIndVO.setIdCur(request.getParameter("lstNuevoCurso").trim());
        }

        if(request.getParameter("txtCodAlu") != null)
        {
            clasIndVO.setIdAlu(request.getParameter("txtCodAlu").trim());
        }

        if(request.getParameter("hidNuevaFecha") != null  &&
          ! request.getParameter("hidNuevaFecha").equals(""))
        {
            String strFechaCla = request.getParameter("hidNuevaFecha");
            clasIndVO.setFecClase(new GregorianCalendar(new Integer(strFechaCla.substring(6,10)).intValue(),
                                                        new Integer(strFechaCla.substring(3,5)).intValue() - 1,
                                                        new Integer(strFechaCla.substring(0,2)).intValue()).getTime());
        }


        if(request.getParameter("lstNuevoProf") != null)
        {
            clasIndVO.setIdProf(request.getParameter("lstNuevoProf").trim());
        }

        if(request.getParameter("txtNuevaTarifa") != null)
        {
            clasIndVO.setTarifa(new Float(request.getParameter("txtNuevaTarifa").trim()).floatValue());
        }

        if(request.getParameter("valInfClasInd") != null)
        {
            indPag = request.getParameter("valInfClasInd").trim();
        }

        if(request.getParameter("strFecha") != null)
        {
            strFecha = request.getParameter("strFecha").trim();
        }

        resAlt = ClasesIndivGestion.guardarClasInd(clasIndVO);

        if(resAlt > 0)
        {
            //Redireccionar a pÃ¡gina de clases individuales
            response.sendRedirect("interesados/clasIndFichaAlumno.jsp?codInt="        + clasIndVO.getIdAlu()
                                                                  + "&valInfClasInd=" + indPag
                                                                  + "&strFecha="      + strFecha);
        }
        else
        {
            //Redireccionar a pÃ¡gina de clases individuales
            response.sendRedirect("interesados/clasIndFichaAlumno.jsp?codInt="        + clasIndVO.getIdAlu()
                                                                  + "&valInfClasInd=" + indPag
                                                                  + "&errorCode="     + resAlt
                                                                  + "&strFecha="      + strFecha);
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
