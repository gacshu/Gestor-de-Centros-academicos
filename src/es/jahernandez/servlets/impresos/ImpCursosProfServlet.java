/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import java.awt.Color;
import java.io.IOException;
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

import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.EdiModProfAulaVO;
import es.jahernandez.datos.InformacionConf;
import es.jahernandez.gestion.AreasGestion;
import es.jahernandez.gestion.AulasGestion;
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.EdiModProfAulaGestion;
import es.jahernandez.gestion.EdicionesGestion;
import es.jahernandez.gestion.ModulosGestion;
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
public class ImpCursosProfServlet extends HttpServlet 
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
        String           txtFec       = forFec.format(cal.getTime());


        Vector           listaModProf = new Vector();
        EdiModProfAulaVO empaVO       = new EdiModProfAulaVO();
        String           codProf      = "";
        int              tipBus       = -99;         
      
        String           strMod       = "";
        String           strCursEdi   = "";
        String           strDias      = "";
        String           strAula      = "";
        String           strArea      = "";
        String           strFecIni    = "";
        String           strFecFin    = "";
        String           strHoraIni   = "";
        String           strHoraFin   = "";

        String           muestraCond  = "";

        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir cursos profesor" );
               
        }
      
        if(request.getParameter("codProf") != null)
        {
            codProf =  request.getParameter("codProf");
        
            if(request.getParameter("tipBus") != null)
            {
                tipBus = new Integer(request.getParameter("tipBus")).intValue();
            }
        
            switch (tipBus) 
            {
                case 1:  listaModProf = EdiModProfAulaGestion.devolverModProf(codProf); 
                         muestraCond  = "HISTORICO CURSOS DE " + ProfesoresGestion.devolverDatosProfesor(codProf).getNombre() + " " + ProfesoresGestion.devolverDatosProfesor(codProf).getApellidos();
                         break;
                case 2:  listaModProf = EdiModProfAulaGestion.devolverModProfPend(codProf); 
                         muestraCond  = "CURSOS PENDIENTES DE " + ProfesoresGestion.devolverDatosProfesor(codProf).getNombre() + " " + ProfesoresGestion.devolverDatosProfesor(codProf).getApellidos();
                         break;
                default: listaModProf = new Vector(); break;
            }
        }
        
        
        int       cuentaLineas = 0;  //Cuenta lineas impresas para saber cuando hacer el salto de página

        PdfPTable tablaDatos   = new PdfPTable(1);
        PdfPCell  celda        = new PdfPCell();
        Font      fuenteDoc    = null;
        Font      fuenteCab    = null;

        // step 1
        Document document = new Document(PageSize.A4.rotate(),16,16,16,16);
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
            document.add(new Paragraph("FECHA " + txtFec + " " + muestraCond));
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));
                
            celda.addElement(new Paragraph("       MODULO                                                        CURSO Y EDICION                      DIAS        AREAS       FECHA INIC   FECHA FIN     HORARIO          ", fuenteCab));
            
            
            celda.setBackgroundColor(Color.BLUE);
            
            
            tablaDatos.addCell(celda);

            for (int ind = 0; ind < listaModProf.size() ; ind++)
            {
                empaVO     = (EdiModProfAulaVO) listaModProf.elementAt(ind);

                strMod     = ModulosGestion.devolverDatosModulo(empaVO.getIdMod()).getNombre();
                strCursEdi = CursosGestion.devolverDatosCurso(EdicionesGestion.devolverDatosEdi(empaVO.getIdEdi()).getIdCur()).getNomCur()+ " --- "  +   EdicionesGestion.devolverDatosEdi(empaVO.getIdEdi()).getDescripcion();
                strDias    = EdicionesGestion.devolverDatosEdi(empaVO.getIdEdi()).devuelveDiasClase();
                strArea    = empaVO.getIdAul().trim().equals("") ? "" : AulasGestion.devolverDatosAula(empaVO.getIdAul()).getNombre(); 
                strArea    = AreasGestion.devuelveNombreArea(ModulosGestion.devolverDatosModulo(empaVO.getIdMod()).getCodArea());
                strFecIni  = forFec.format(empaVO.getFecIni());
                strFecFin  = forFec.format(empaVO.getFecFin());    
                strHoraIni = empaVO.getHorIni();
                strHoraFin = empaVO.getHorFin();

                if (cuentaLineas > 21)
                {
                    cuentaLineas = 0;

                    //Hacemos salto de página
                    document.add(tablaDatos);
                    document.newPage();

                    //Reiniciamos la tabla
                    tablaDatos = new PdfPTable(1);
                    tablaDatos.setWidthPercentage(100);
                    tablaDatos.setSpacingBefore(10);

                    //Mostramos cabecera
                    document.add(logoImage);
                    document.add(new Paragraph("FECHA " + txtFec + " " + muestraCond));

                    document.add(new Paragraph(""));
                    document.add(new Paragraph(""));
                    celda = new PdfPCell();
                    celda.setMinimumHeight(20);

                    celda.setBackgroundColor(Color.BLUE);
                                                   
                    celda.addElement(new Paragraph("       MODULO                                                        CURSO Y EDICION                      DIAS        AREAS       FECHA INIC   FECHA FIN     HORARIO          ", fuenteCab));
                    
                    
                    tablaDatos.addCell(celda);
                }



                strMod      = strMod     + "                                                          ";

                strCursEdi  = strCursEdi + "                                                          ";

                strDias    = strDias     + "                                                          ";

                strAula    = strAula     + "                                                          ";

                strArea    = strArea     + "                                                          ";

                strFecIni  = strFecIni   + "                                                          ";

                strFecFin  = strFecFin   + "                                                          ";

                strHoraIni = strHoraIni  + "                                                          ";

                strHoraFin = strHoraFin  + "                                                          ";
                
                
                strMod     = strMod.    substring(0,35);
                strCursEdi = strCursEdi.substring(0,60);
                strDias    = strDias.   substring(0,11);
                strAula    = strAula.   substring(0,20);
                strArea    = strArea.   substring(0,15);
                strFecIni  = strFecIni. substring(0,10);
                strFecFin  = strFecFin .substring(0,10);
                strHoraIni = strHoraIni.substring(0, 5);
                strHoraFin = strHoraFin.substring(0, 5);
                
                celda = new PdfPCell();
                celda.setMinimumHeight(20);
                
                if(ind%2 != 0)
                {
                    celda.setBackgroundColor(Color.LIGHT_GRAY);
                }
                celda.addElement(new Paragraph("  " + strMod + "    " + strCursEdi + " " + strDias + " " +  strArea + "  " + strFecIni + "  "  + strFecFin + " " + strHoraIni + " a " + strHoraFin ,fuenteDoc));
                //document.Add(new Paragraph("  " + strNombre + "    " + strApe1 + " " + strPob+ " " + strMov + "   " + strFij,new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));

                tablaDatos.addCell(celda);

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
