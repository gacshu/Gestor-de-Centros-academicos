/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.jahernandez.servlets.alumnos;

import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.DatosBusquedaAlumnos;
import es.jahernandez.gestion.AlumnosGestion;
import es.jahernandez.gestion.EmpresasGestion;
import es.jahernandez.gestion.NivelForGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Alberto
 */
public class BusquedaInteresadosServlet extends HttpServlet
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

        HttpSession          sesion   = request.getSession();
             
        Vector               listBusq = new Vector();
        String               muesCond = "";
        
        DatosBusquedaAlumnos datBA    = new DatosBusquedaAlumnos();
        
        Logger               log      = null;
        ConUsuVO             conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Busqueda Alumnos" );
               
        }
        
        // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("txtNumDoc") != null)
        {
            datBA.setNumDoc(request.getParameter("txtNumDoc").trim());
        }

        if(request.getParameter("txtApellidos") != null)
        {
            datBA.setApellidos(request.getParameter("txtApellidos").trim());
        }

        if(request.getParameter("txtNombreAlu") != null)
        {
            datBA.setNombre(request.getParameter("txtNombreAlu").trim());
        }

        if(request.getParameter("chkDesempleado") != null)
        {
            datBA.setDesempleado(request.getParameter("chkDesempleado").trim());

            if(! datBA.getDesempleado().equals("true") )
            {
                datBA.setDesempleado("");
            }
            
           muesCond = muesCond + "Desempleados "; 
        }
        
        if(request.getParameter("lstNivFor") != null)
        {
            datBA.setNivFor(request.getParameter("lstNivFor").trim());
            
            if(! datBA.getNivFor().equals("0"))
            {
                muesCond = muesCond + "Formación " +  NivelForGestion.nomNivFor(datBA.getNivFor()) + " ";
            }
        }
        
        if(request.getParameter("lstEmpresas") != null)
        {
            datBA.setCodEmp(request.getParameter("lstEmpresas").trim());
            
            if(! datBA.getCodEmp().equals("0"))
            {    
                muesCond = muesCond + "Empresa " +   EmpresasGestion.devuelveNombreEmpresa(datBA.getCodEmp()) + " ";
            }
        }
         
        if(request.getParameter("chkNoInterCurso") != null)
        {
            datBA.setNoIntCur(request.getParameter("chkNoInterCurso").trim());

            if(! datBA.getNoIntCur().equals("true") )
            {
                datBA.setNoIntCur("");
            }
            
            muesCond = muesCond + "No interesados en curso ";
        } 
        
        if(request.getParameter("txtCodPost") != null)
        {
            datBA.setCodPost(request.getParameter("txtCodPost").trim());
            
            if(! datBA.getCodPost().equals(""))
            {
                muesCond = muesCond + "Cod. Postal " + datBA.getCodPost() + " ";
            }
        }
        
        if(request.getParameter("txtEmail") != null)
        {
            datBA.seteMail(request.getParameter("txtEmail").trim());
            if(! datBA.geteMail().equals(""))
            {    
                muesCond = muesCond + "Mail " + datBA.geteMail() + " "; 
            }                                         
        }

      listBusq = AlumnosGestion.devolverDatosConsAlumno(datBA);

      sesion.setAttribute("muesCond", muesCond);
      sesion.setAttribute("busAlu", listBusq);


      if (listBusq.size() == 0)
      {
            response.sendRedirect("interesados/altaInteresados.jsp?parNumDoc="  + datBA.getNumDoc()        +
                                                             "&parNombreAlu="   + datBA.getNombre()        +
                                                             "&parApellidos="   + datBA.getApellidos()     +
                                                             "&parNivFor="      + datBA.getNivFor()        +
                                                             "&parEmpresas="    + datBA.getCodEmp()        +
                                                             "&parEmail="       + datBA.geteMail()         +
                                                             "&parCodPost="     + datBA.getCodPost()       +
                                                             "&parDesempleado=" + datBA.getDesempleado());
       }
       else
       {
            response.sendRedirect("interesados/RBInteresado.jsp?ind=0");
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
        return "Servlet de búsqueda de alumnos";
    }// </editor-fold>

}
