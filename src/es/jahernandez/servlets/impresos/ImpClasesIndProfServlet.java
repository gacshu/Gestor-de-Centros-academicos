/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.jahernandez.datos.ClasesIndivVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.InformacionConf;
import es.jahernandez.gestion.AlumnosGestion;
import es.jahernandez.gestion.ClasesIndivGestion;
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.ProfesoresGestion;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 *
 * @author JuanAlberto
 */
public class ImpClasesIndProfServlet extends HttpServlet 
{
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        HttpSession      sesion       = request.getSession();
        ServletContext   sc           = null;
        Calendar         cal          = GregorianCalendar.getInstance();
        SimpleDateFormat forFec       = new SimpleDateFormat("dd/MM/yyyy");
               

        Vector           listaClaProf = new Vector();
        ClasesIndivVO    clasIndVO    = new ClasesIndivVO();
        String           codProf      = "";
        String           fecFilt      = "";
        
             
        String           strCurso     = "";
        String           strFecha     = "";
        String           strAlumno    = "";
        String           strTarifa    = "";
        
        String           muestrames   = "";

        Logger          log           = null;
        ConUsuVO        conUsoVO      = null;
        
        String          tablaMeses[]  = {"Enero"     , "Febrero" , "Marzo"     , "Abril", 
                                         "Mayo"      , "Junio"   , "Julio"     , "Agosto" , 
                                         "Septiembre", "Octubre" , "Noviembre" , "Diciembre" };
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir clases individuales profesor" );
               
        }
      
        if(request.getParameter("codProf") != null)
        {
            codProf =  request.getParameter("codProf");
        }
        
        if(request.getParameter("strFecha") != null)
        {
           fecFilt = request.getParameter("strFecha").trim();
        }

        if(request.getParameter("codProf") != null)
        {
            codProf     =  request.getParameter("codProf").trim();
            
            if(fecFilt.equals(""))
            {
                listaClaProf =  ClasesIndivGestion.devolverClasesIndProf(codProf);
            }
            else
            {
                listaClaProf   = ClasesIndivGestion.devolverClasesIndProfMes(codProf, fecFilt);
                muestrames     = "Clases del mes de " + tablaMeses[new Integer(fecFilt.substring(0,2)).intValue() - 1] + " " + fecFilt.substring(2,6);
            }
        }
        
        int       cuentaLineas = 0;  //Cuenta lineas impresas para saber cuando hacer el salto de página

        PdfPTable tablaDatos   = new PdfPTable(1);
        PdfPCell  celda        = new PdfPCell();
        Font      fuenteDoc    = null;
        Font      fuenteCab    = null;

        // step 1
        //Document document = new Document(PageSize.A4.rotate(),16,16,16,16);
        Document document = new Document(PageSize.A4,16,16,16,16);
        //iTextSharp.text.Image logoImage = iTextSharp.text.Image.GetInstance(System.web.HttpContext.Current.Server.MapPath());

        Image logoImage = null;

        try
        {
            sc = getServletContext();
            fuenteDoc = new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            fuenteCab = new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED));                        
            
            //logoImage = Image.getInstance("~/imagenes/logoEnS.jpg");
                                                           
            logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + InformacionConf.logo));
            logoImage.scaleAbsolute(150, 38);

            tablaDatos.setWidthPercentage(100);
            tablaDatos.setSpacingBefore(10);

            celda.setMinimumHeight(20);
            fuenteDoc.setSize(8);
            
            fuenteCab.setColor(Color.WHITE);
            fuenteCab.setSize(8);  

            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            // step 3
            document.open();

            // step 4

            //Mostramos cabecera
            logoImage.setAlignment(Image.ALIGN_LEFT);


            document.add(new Paragraph(""));
            document.add(logoImage);
            document.add(new Paragraph("Profesor: " + ProfesoresGestion.devolverDatosProfesor(codProf).getNombre() + " " + ProfesoresGestion.devolverDatosProfesor(codProf).getApellidos() + " " + muestrames));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
                
            
            //celda.addElement(new Paragraph("       CURSO                                              FECHA           ALUMNO                                       TARIFA ", fuenteCab));
            celda.addElement(new Paragraph("       CURSO                                         FECHA           ALUMNO                              TARIFA ", fuenteCab));
            
            
            
            celda.setBackgroundColor(Color.BLUE);
            
            
            tablaDatos.addCell(celda);

            for (int ind = 0; ind < listaClaProf.size() ; ind++)
            {
                clasIndVO  = (ClasesIndivVO) listaClaProf.elementAt(ind);

                strCurso   = CursosGestion.devolverDatosCurso(clasIndVO.getIdCur()).getNomCur();
                strFecha   = forFec.format(clasIndVO.getFecClase());
                strAlumno  = AlumnosGestion.devolverDatosAlumno(clasIndVO.getIdAlu()).getNombre() + " " + AlumnosGestion.devolverDatosAlumno(clasIndVO.getIdAlu()).getAp1Alu();
                strTarifa  = new DecimalFormat("##########0.00").format(clasIndVO.getTarifa());
                
                if (cuentaLineas > 24)
                {
                    cuentaLineas = 0;
                    document.add(tablaDatos);

                    //Hacemos salto de página
                    document.newPage();

                    //Reiniciamos la tabla
                    tablaDatos = new PdfPTable(1);
                    tablaDatos.setWidthPercentage(100);
                    tablaDatos.setSpacingBefore(10);

                    //Mostramos cabecera
                    logoImage.setAlignment(Image.ALIGN_LEFT);


                    document.add(new Paragraph(""));
                    document.add(logoImage);
                    document.add(new Paragraph("Profesor: " + ProfesoresGestion.devolverDatosProfesor(codProf).getNombre() + " " + ProfesoresGestion.devolverDatosProfesor(codProf).getApellidos() + " " + muestrames));
                    document.add(new Paragraph(""));
                    document.add(new Paragraph(""));

                    //celda.addElement(new Paragraph("       CURSO                                              FECHA           ALUMNO                                       TARIFA ", fuenteCab));
                    celda.addElement(new Paragraph("       CURSO                                         FECHA           ALUMNO                              TARIFA ", fuenteCab));
            
            
                   celda.setBackgroundColor(Color.BLUE);
                   
                   tablaDatos.addCell(celda);
                }



                strCurso  = strCurso  + "                                                                                                                     ";

                strFecha  = strFecha  + "                                                          ";

                strAlumno = strAlumno + "                                                          ";

                strTarifa = strTarifa + "                                                          ";

                
                
                strCurso  = strCurso. substring(0,45); //50
                strFecha  = strFecha. substring(0,12);
                strAlumno = strAlumno.substring(0,40); //50
                strTarifa = strTarifa.substring(0,10);
                
                celda = new PdfPCell();
                celda.setMinimumHeight(20);
                
                if(ind%2 != 0)
                {
                    celda.setBackgroundColor(Color.LIGHT_GRAY);
                }
                celda.addElement(new Paragraph("  " + strCurso + "    " + strFecha + " " + strAlumno + " " +  strTarifa ,fuenteDoc));
                //document.Add(new Paragraph("  " + strNombre + "    " + strApe1 + " " + strPob+ " " + strMov + "   " + strFij,new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));

                tablaDatos.addCell(celda);

                celda = new PdfPCell();
                
                cuentaLineas++;
            }
            //if (alumnosBusqueda.size() > 0 && alumnosBusqueda.size() <= 21)
            //{
            //    document.add(tablaDatos);
            //}
            document.add(tablaDatos);
        }
        catch (Exception ex)
        {
            ex.getCause();
        }
        // step 5: Close document
        document.close();
        
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

}
