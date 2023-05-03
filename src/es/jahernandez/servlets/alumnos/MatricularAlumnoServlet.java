/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.alumnos;

import es.jahernandez.accesodatos.AluEdiDAO;
import es.jahernandez.datos.AluEdiVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.gestion.AluEdiGestion;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
////import org.apache.catalina.SessionEvent;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author JuanAlberto
 */
public class MatricularAlumnoServlet extends HttpServlet 
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
        
        HttpSession sesion = request.getSession();

        AluEdiVO aluEdiVO = new AluEdiVO();
        int      resIns   = -1;


        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Matricular alumno" );
               
        }
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("hidCodAlu") != null)
        {
            aluEdiVO.setIdAlu(request.getParameter("hidCodAlu"));
        }
        
        if(request.getParameter("lstEdiciones") != null)
        {
            aluEdiVO.setIdEdi(request.getParameter("lstEdiciones"));
        }

        if(request.getParameter("txtNumCuenta") != null)
        {
            aluEdiVO.setNumCuenta(request.getParameter("txtNumCuenta"));
        }
        
        aluEdiVO.setFecAlta(new Date(System.currentTimeMillis()));
        
        resIns = AluEdiGestion.guardarMatAlu(aluEdiVO);

        //Redireccionar a página de ficha alumnos
        response.sendRedirect("interesados/mensajeMat.jsp?codMen="   + resIns              + 
                                                        "&codOper=1" +
                                                        "&codAlu="   + aluEdiVO.getIdAlu() +
                                                        "&codEdi="   + aluEdiVO.getIdEdi());
        
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
        return "Matricular alumno servlet";
    }// </editor-fold>

}


   