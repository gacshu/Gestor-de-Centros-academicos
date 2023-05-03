/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.empresas;

import es.jahernandez.accesodatos.EmpresasDAO;
import es.jahernandez.accesodatos.ProfesoresDAO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.EmpresasVO;
import es.jahernandez.gestion.EmpresasGestion;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
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
public class BusEmpServlet extends HttpServlet 
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
             
        Vector      listBusq = new Vector();
        
        EmpresasVO  empVO    = new EmpresasVO();
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Busqueda empresa" );
               
        }
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtRazSoc") != null)
        {
            empVO.setNombreEmpresa(request.getParameter("txtRazSoc").trim());
        }
        
        if(request.getParameter("txtNomCom") != null)
        {
            empVO.setNomComercial(request.getParameter("txtNomCom").trim());
        }

        if(request.getParameter("txtLocalidad") != null)
        {
            empVO.setPobEmpresa(request.getParameter("txtLocalidad").trim());
        }
        
        if(request.getParameter("selActEmp") != null)
        {
            empVO.setCodAct(new Integer(request.getParameter("selActEmp").trim()).intValue());
        }
        
        if(request.getParameter("txtCNAE") != null)
        {
           if(! request.getParameter("txtCNAE").trim().equals(""))
           { 
                empVO.setCnae(new Integer(request.getParameter("txtCNAE").trim()).intValue());
           }
        }
        
        if(request.getParameter("selImpExp") != null)
        {
            empVO.setImpExp(request.getParameter("selImpExp").trim());
        }
        
        if(request.getParameter("chkCliente") != null)
        {
            if(! request.getParameter("chkCliente").trim().equals("true") )
            {
                empVO.setEsCliente(true);
            }
        }
        
      listBusq = EmpresasGestion.buscarEmpresas(empVO);

      sesion.setAttribute("busEmp", listBusq);


      if (listBusq.size() == 0)
      {
            response.sendRedirect("empresas/altaEmp.jsp")   ;
                                                             
                                                            
        }
        else
        {
            response.sendRedirect("empresas/RBEmpresas.jsp?ind=0");
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
        return "Servlet de búsqueda de empresas";
    }// </editor-fold>


}
