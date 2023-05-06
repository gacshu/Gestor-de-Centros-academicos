/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.cursos;

import java.io.IOException;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import es.jahernandez.datos.AluEdiVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.gestion.AluEdiGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author JuanAlberto
 */
public class BajaMatriculaServlet extends HttpServlet
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

        HttpSession sesion   = request.getSession();

        AluEdiVO aluEdiVO = new AluEdiVO();
        int      resEdi   = -1;


        Logger               log      = null;
        ConUsuVO             conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Baja matricula" );

        }

        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("hidCodAlu") != null)
        {
            aluEdiVO.setIdAlu(request.getParameter("hidCodAlu"));
        }

        if(request.getParameter("hidCodEdi") != null)
        {
            aluEdiVO.setIdEdi(request.getParameter("hidCodEdi"));
        }

        if(request.getParameter("hidFecAlta") != null &&
          !request.getParameter("hidFecAlta").equals(""))
        {
            String strFechaAlta = request.getParameter("hidFecAlta");
            aluEdiVO.setFecAlta(new GregorianCalendar(new Integer(strFechaAlta.substring(6,10)).intValue(),
                                                    new Integer(strFechaAlta.substring(3,5)).intValue() - 1,
                                                    new Integer(strFechaAlta.substring(0,2)).intValue()).getTime());
        }


        if(request.getParameter("chkBaja") != null &&
           request.getParameter("chkBaja").equals("true"))
        {
            aluEdiVO.setEsBaja(true);
        }


        if(request.getParameter("chkSusPag") != null &&
           request.getParameter("chkSusPag").equals("true"))
        {
            aluEdiVO.setEsCong(true);
        }

        if(request.getParameter("txtNumCuenta") != null)
        {
            aluEdiVO.setNumCuenta(request.getParameter("txtNumCuenta"));
        }

        if(request.getParameter("txtObserv") != null)
        {
            aluEdiVO.setObserv(request.getParameter("txtObserv"));
        }

        resEdi = AluEdiGestion.bajaMatAlu(aluEdiVO);

        //Redireccionar a página de ficha alumnos
        response.sendRedirect("interesados/mensajeMat.jsp?codMen=" + resEdi+ "&codOper=2");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Baja Matricula servlet";
    }// </editor-fold>

}