/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.alumnos;

import es.jahernandez.accesodatos.ClasesIndivDAO;
import es.jahernandez.datos.ClasesIndivVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.gestion.ClasesIndivGestion;

import java.io.IOException;
import java.util.GregorianCalendar;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author JuanAlberto
 */
public class ModClaseIndServlet extends HttpServlet 
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
        int           resEdi    = 0;

        Logger        log       = null;
        ConUsuVO      conUsoVO  = null;
        String        indPag    = "";   
        String        strFecha  = "";
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Edición clase individual" );
               
        }
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtCodClasInd") != null)
        {
            clasIndVO.setIdClaseInd(request.getParameter("txtCodClasInd").trim());
        }
        
        if(request.getParameter("codInt") != null)
        {
            clasIndVO.setIdAlu(request.getParameter("codInt").trim());
        }
        
        if(request.getParameter("txtFecha" + clasIndVO.getIdClaseInd()) != null)
        {
            if (! request.getParameter("txtFecha" + clasIndVO.getIdClaseInd()).equals(""))
            {
                String strFechaCla = request.getParameter("txtFecha" + clasIndVO.getIdClaseInd());
                clasIndVO.setFecClase(new GregorianCalendar(new Integer(strFechaCla.substring(6,10)).intValue(),
                                                            new Integer(strFechaCla.substring(3,5)).intValue() - 1,
                                                            new Integer(strFechaCla.substring(0,2)).intValue()).getTime());
            }
        }
        
        if(request.getParameter("lstProf" + clasIndVO.getIdClaseInd()) != null)
        {
            clasIndVO.setIdProf(request.getParameter("lstProf" + clasIndVO.getIdClaseInd()).trim());
        }
        
        if(request.getParameter("txtTarifa" + clasIndVO.getIdClaseInd()) != null)
        {
            clasIndVO.setTarifa(new Float(request.getParameter("txtTarifa" + clasIndVO.getIdClaseInd()).trim()).floatValue());
        }
        
        if(request.getParameter("valInfClasInd") != null)
        {
            indPag = request.getParameter("valInfClasInd").trim();
        }
        
        if(request.getParameter("strFecha") != null)
        {
            strFecha = request.getParameter("strFecha").trim();
        }
        
        resEdi = ClasesIndivGestion.editarClaseInd(clasIndVO);

        if(resEdi > 0)
        {
            //Redireccionar a página de clases individuales
            response.sendRedirect("interesados/clasIndFichaAlumno.jsp?codInt="        + clasIndVO.getIdAlu() 
                                                                  + "&valInfClasInd=" + indPag
                                                                  + "&strFecha="      + strFecha);
        }
        else
        {
            //Redireccionar a página de clases individuales
            response.sendRedirect("interesados/clasIndFichaAlumno.jsp?codInt="        + clasIndVO.getIdAlu() 
                                                                  + "&valInfClasInd=" + indPag
                                                                  + "&errorEdi="      + resEdi
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
        return "Edición clase individual Servlet";
    }// </editor-fold>
}
