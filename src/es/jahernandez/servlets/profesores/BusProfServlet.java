/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.profesores;

import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.DatosBusqProfVO;
import es.jahernandez.gestion.ProfesoresGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author JuanAlberto
 */
public class BusProfServlet extends HttpServlet   
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
             
        Vector               listBusq = new Vector();
        
        DatosBusqProfVO      datBP    = new DatosBusqProfVO();
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Búsqueda profesor" );
               
        }
        
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtNombre") != null)
        {
            datBP.setNombre(request.getParameter("txtNombre").trim());
        }

        if(request.getParameter("txtApellidos") != null)
        {
            datBP.setApellidos(request.getParameter("txtApellidos").trim());
        }
        
        if(request.getParameter("txtNumDoc") != null)
        {
            datBP.setNumDoc(request.getParameter("txtNumDoc").trim());
        }
        
        if(request.getParameter("lstAreas") != null)
        {
            datBP.setCodArea(request.getParameter("lstAreas").trim());
        }
        
        if(request.getParameter("chkActivo") != null &&
           request.getParameter("chkActivo").equals("true"))
        {
            datBP.setActivo(true);
        } 
    
           
      listBusq = ProfesoresGestion.devolverDatosConsultaProf(datBP);

      sesion.setAttribute("busProf", listBusq);


      if (listBusq.size() == 0)
      {
            response.sendRedirect("profesores/altaProfesor.jsp?txtNombre="    + datBP.getNombre()    +
                                                             "&txtApellidos=" + datBP.getApellidos() +
                                                             "&txtNumDoc="    + datBP.getNumDoc())   ;
                                                             
                                                            
        }
        else
        {
            response.sendRedirect("profesores/RBProfesores.jsp?ind=0");
        }

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
    throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Servlet de búsqueda de profesores";
    }// </editor-fold>


}
