/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.auxiliares;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import es.jahernandez.datos.EdicionesVO;
import es.jahernandez.gestion.EdicionesGestion;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author JuanAlberto
 */
public class MuestraDatosEdicionServlet extends HttpServlet
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

        EdicionesVO ediVO  = null;



       if(request.getParameter("codEdi") != null)
       {
           ediVO = EdicionesGestion.devolverDatosEdi(request.getParameter("codEdi"));
       }

       try
       {

            out.printf("function Edicion() {");
            out.printf("this.fechaIn="   + "'" + new SimpleDateFormat("dd/MM/yyy").format(ediVO.getFecIn()) + "';");
            out.printf("this.fechaFi="   + "'" + new SimpleDateFormat("dd/MM/yyy").format(ediVO.getFecFi()) + "';");
            out.printf("this.horasPr="   + "'" + ediVO.getNumHor() + "';");
            out.printf("this.horasDi="   + "'" + ediVO.getHorDis() + "';");
            out.printf("this.horasTe="   + "'" + ediVO.getHorTelef() + "';");
            out.printf("this.horIni="    + "'" + ediVO.getHorIn()+ ":" + ediVO.getMinIn()  + "';" );
            out.printf("this.horFin="    + "'" + ediVO.getHorFi()+ ":" + ediVO.getMinFin() + "';" );
            out.printf("this.precioMat=" + "'" + ediVO.getPrecioM() + "';");
            out.printf("this.precioRec=" + "'" + ediVO.getPrecioR() + "';");
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
