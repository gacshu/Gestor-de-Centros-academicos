/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.auxiliares;

import es.jahernandez.accesodatos.CursosDAO;
import es.jahernandez.accesodatos.EdicionesDAO;
import es.jahernandez.accesodatos.HisRecDAO;
import es.jahernandez.datos.HisRecVO;
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.EdicionesGestion;
import es.jahernandez.gestion.HisRecGestion;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author JuanAlberto
 */
public class MuestraDatosReciboServlet extends HttpServlet 
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
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //Control de caché
        response.setDateHeader ("Expires", -1); 
        response.setHeader("Pragma","no-cache"); 
        if(request.getProtocol().equals("HTTP/1.1")) 
            response.setHeader("Cache-Control","no-cache"); 
        
        
        PrintWriter out    = response.getWriter();
         
        HisRecVO hisRecVO  = new HisRecVO();
        
       if(request.getParameter("numRec") != null && request.getParameter("codAlu") != null )
       {
           hisRecVO = HisRecGestion.devDatRecHis(request.getParameter("numRec"),request.getParameter("codAlu"));
       }
          
       try 
       {            
           
            out.printf("function Recibo() {");
            out.printf("this.fechaEx="   + "'" + new SimpleDateFormat("dd/MM/yyy").format(hisRecVO.getFecExp()) + "';");
            out.printf("this.curso="     + "'" + CursosGestion.devolverDatosCurso(hisRecVO.getIdCur()).getNomCur()  + "';");
            out.printf("this.importe="   + "'" + EdicionesGestion.devolverDatosEdi(hisRecVO.getIdEdi()).getPrecioR()+ "';");
            
            out.printf("}");
       } 
       finally 
       {            
            if (out!=null)
            {
                out.flush();
                out.close();        
            }
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
        return "Muestra datos Edición ";
    }// </editor-fold>

}
