/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.auxiliares;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 


/**
 *
 * @author JuanAlberto
 */
public class SubirArchivoServlet extends HttpServlet 
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
        
        FileItemFactory   factory = new DiskFileItemFactory();
        ServletFileUpload upload  = new ServletFileUpload(factory);
                
        String            nomFic  = "";
        
        ServletContext    sc      = null;
			
        String            errMsg  = "";
        int               resIns  = 0;
        boolean           hayErr  = false;
                
        //Necesario para saber la ubicación física de los archivo
        sc = getServletContext();
           
        
        
        List items = null;
        try 
        {
            items = upload.parseRequest(new ServletRequestContext((javax.servlet.http.HttpServletRequest) request));
        } 
        catch (FileUploadException ex) 
        {
            Logger.getLogger(SubirArchivoServlet.class.getName()).log(Level.SEVERE, null, ex);
            
            errMsg = "Se produjo un error al subir el fichero";
            hayErr = true;
        }

        // Se recorren todos los items, que son de tipo FileItem
        for (Object item : items) 
        {
           FileItem uploaded = (FileItem) item;

           // Hay que comprobar si es un campo de formulario. Si no lo es, se guarda el fichero
           // subido donde nos interese
           if (!uploaded.isFormField()) 
           {
              // No es campo de formulario, guardamos el fichero en algún sitio
              nomFic = FilenameUtils.getName(uploaded.getName());
              File fichero = new File(sc.getRealPath("/" + "docAdj" + "/" + nomFic ) );                                          
              try 
              {
                    uploaded.write(fichero);
              } 
              catch (Exception ex) 
              {
                    Logger.getLogger(SubirArchivoServlet.class.getName()).log(Level.SEVERE, null, ex);
                    
                    errMsg = "Se produjo un error al subir el fichero";
                    hayErr = true;
              }
           } 
           else 
           {
              // es un campo de formulario, podemos obtener clave y valor
              String key = uploaded.getFieldName();
              String valor = uploaded.getString();
           }
        }
        
        //REdireccionamos a la ágina de subida de ficheros
        response.sendRedirect("./mailing/subirFic.jsp?nomArc=" + nomFic);
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
        return "Subir archivo servlet";
    }// </editor-fold>
}
