/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

//Paquetes de manejo de pdf
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.jahernandez.accesodatos.*;
import es.jahernandez.datos.*;
import es.jahernandez.gestion.AluEdiGestion;
import es.jahernandez.gestion.AlumnosGestion;
import es.jahernandez.gestion.BancosGestion;
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.EdicionesGestion;
import es.jahernandez.gestion.HisRecGestion;
import es.jahernandez.gestion.NivelesGestion;
import es.jahernandez.gestion.TipoCursoGestion;
import java.awt.Color;
import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import jakarta.servlet.ServletContext;
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
public class ImprimirReciboMesAluServlet extends HttpServlet 
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
        
        HttpSession    sesion       = request.getSession();
        ServletContext sc           = null;
        
        AlumnosVO      aluVO        = null;
        EdicionesVO    ediVO        = null;
        CursosVO       curVO        = null;
        AluEdiVO       alEvo        = null;
        HisRecVO       hCurVO       = null;
        boolean        generaPag    = false;

        ConUsuVO       conUsVO      = new ConUsuVO();
           
        String         mesAc        = new SimpleDateFormat("MM").format(new Date(System.currentTimeMillis()));   
        String         annoAc       = new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis()));  
        

        Date           fechaHoy     = new Date(System.currentTimeMillis());

        Vector         listaRecibos = new Vector();

        int            numRecMes    = 0;
        
        conUsVO =(ConUsuVO) sesion.getAttribute("usuario");

        Logger         log      = null;
       
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsVO.getUsuario() + "               " ).substring(0,10) + "Imprimir recibo mes alumno" );
               
        }
        
        
        //Se cargan datos de la lista de recibos
        listaRecibos = AluEdiGestion.devRecPendAluEdi();
        numRecMes = HisRecGestion.devNumRecMes(annoAc + "/" + mesAc);

        // step 1
        // need to write to memory first due to IE wanting
        // to know the length of the pdf beforehand
        Document document = new Document();

        Paragraph par14 = null;

        //Instrucciones para meter los datos de concepto en una tabla
        PdfPTable tablaRecibo  = new PdfPTable(1);
        PdfPCell cellRecibo    = null;
        PdfPCell cellAlumno    = null;
        PdfPCell cellDatNumRec = null;
        PdfPCell cellDatLocRec = null;
        PdfPCell cellDatImpRec = null;
        PdfPCell cellFecExpRec = null;
        PdfPCell cellFecVenRec = null;
        PdfPCell cellDatCuenta = null;
        Phrase   frasDatCuenta = null;
        Phrase   frasDatNumRec = null;
        Phrase   frasLocExpedi = null;
        Phrase   frasImportCur = null;
        
        String   strDatNumRec  = "";
        String   strDatLocRec  = "";
        String   strDatImpRec  = "";
        String   strFecExpRec  = "";
        String   strFecVenRec  = "";
        String   strDatCuenta  = "";
        String   strNombreBan  = "";
        String   strRecibo     = "";
        String   strDatAlu     = "";
        
        tablaRecibo.setWidthPercentage(100);
        
        PdfPTable tablaDatAlu = new PdfPTable(1);
        tablaDatAlu.setWidthPercentage(100);
       
        PdfPTable tablaFin = new PdfPTable(3);
        tablaFin.setWidthPercentage(100);

        PdfPTable tablaFec = new PdfPTable(2);
        tablaFec.setWidthPercentage(100);

        PdfPTable tablaCue = new PdfPTable(1);
        tablaFec.setWidthPercentage(100);

        Paragraph parIma = new Paragraph();
        Image     logoImage = null; // iTextSharp.text.Image.GetInstance(System.Web.HttpContext.Current.Server.MapPath("~/imagenes/logoEnTeST.jpg"));

        
        String    codAlu = "";
        String    codEdi = "";
        
         // Se comprueba que se hayan pasado los parámetros y se inicializan valores
        if(request.getParameter("codAlu") != null)
        {
           codAlu = request.getParameter("codAlu").trim();
        }
       
        if(request.getParameter("codEdi") != null)
        {
           codEdi = request.getParameter("codEdi").trim();
        }
        
        

        try
        {
            sc = getServletContext();
            
            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            
            // step 3
            document.setMargins(32, 32, 16, 0);
            document.open();

            int contRecGen = 0;

            aluVO = AlumnosGestion.devolverDatosAlumno(codAlu);
            ediVO = EdicionesGestion.devolverDatosEdi(codEdi);
            
            if (! HisRecGestion.reciboGenerado( codAlu, codEdi ) && ediVO.getPrecioR() > 0)
            {          
                
                curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());
                alEvo = AluEdiGestion.devDatAluEdi(aluVO.getIdAlu() , ediVO.getIdEdi() );

                hCurVO = new HisRecVO();

                //Mostrar solo datos segun academia
                if((ediVO.getCodCen() == conUsVO.getIdCentro()) || conUsVO.getIdCentro()==0)
                {
                    contRecGen++;
                    generaPag = true;
                    tablaRecibo = new PdfPTable(1);
                    tablaRecibo.setWidthPercentage(100);
                    tablaDatAlu = new PdfPTable(1);
                    tablaDatAlu.setWidthPercentage(100);
                    tablaFin = new PdfPTable(3);
                    tablaFin.setWidthPercentage(100);
                    tablaFec = new PdfPTable(2);
                    tablaFec.setWidthPercentage(100);
                    tablaCue = new PdfPTable(1);
                    tablaCue.setWidthPercentage(100);

                    logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + InformacionConf.logo));
                    logoImage.scaleAbsolute(150, 38);

                    par14 = new Paragraph(InformacionConf.nombEmp + " " + InformacionConf.dirEmp + " CIF: " + InformacionConf.CIFEmp, new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
                    strDatLocRec = "LUGAR DE EXPEDICION\n"+ InformacionConf.pobEmp;

                    par14.font().setSize(8);
                    parIma = new Paragraph();
                    parIma.setAlignment(Image.ALIGN_LEFT);
                    parIma.add(logoImage);

                    // step 4

                    //Mostramos cabecera

                    numRecMes = HisRecGestion.devNumRecMes(annoAc + "/" + mesAc);

                    //strDatNumRec = "RECIBO NUMERO\n" + annoAc + "/" + mesAc + "-" + (numRecMes + 1);
                    strDatNumRec = "RECIBO NUMERO\n" + annoAc + "/" + mesAc + "-" + (HisRecGestion.numeroRecGenMes(annoAc, mesAc) + 1);

                    frasDatNumRec = new Phrase(strDatNumRec, new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
                    frasDatNumRec.font().setSize(12);
                    frasDatNumRec.font().setStyle(Font.BOLD);
                    cellDatNumRec = new PdfPCell(frasDatNumRec);
                    tablaFin.addCell(cellDatNumRec);

                    frasLocExpedi = new Phrase(strDatLocRec, new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
                    frasLocExpedi.font().setSize(12);
                    frasLocExpedi.font().setStyle(Font.BOLD);
                    cellDatLocRec = new PdfPCell(frasLocExpedi);
                    tablaFin.addCell(cellDatLocRec);

                    strDatImpRec = "IMPORTE\n           " + ediVO.getPrecioR() + " €";
                    frasImportCur = new Phrase(strDatImpRec, new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
                    frasImportCur.font().setSize(12);
                    frasImportCur.font().setStyle(Font.BOLD);
                    cellDatImpRec = new PdfPCell(frasImportCur);
                    tablaFin.addCell(cellDatImpRec);

                    strFecExpRec = "FECHA EXPEDICION\n" + new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis()));
                    cellFecExpRec = new PdfPCell(new Phrase(strFecExpRec));
                    tablaFec.addCell(cellFecExpRec);

                    strFecVenRec = "FECHA VENCIMIENTO\n" + new SimpleDateFormat("dd/MM/yyyy").format(new Date(System.currentTimeMillis() + 432000000));
                    cellFecVenRec = new PdfPCell(new Phrase(strFecVenRec));
                    tablaFec.addCell(cellFecVenRec);

                    strDatCuenta = "PAGADERO EN CAJA O BANCO        OFICINA       CLAVE O NÚMERO DE CUENTA\n";
                    if (alEvo.getNumCuenta().trim().equals(""))
                    {
                        strDatCuenta = strDatCuenta + "Pago al contado";
                    }
                    else
                    {
                        strNombreBan = BancosGestion.devolverNombreBanco(alEvo.getNumCuenta().substring(0, 4));
                        strNombreBan = strNombreBan + "                                                 ";

                        strNombreBan = strNombreBan.substring(0, 30);

                        strDatCuenta = strDatCuenta + strNombreBan + "   " + alEvo.getNumCuenta().substring(4, 4) + "             " + alEvo.getNumCuenta();
                    }
                    frasDatCuenta = new Phrase(strDatCuenta, new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

                    frasDatCuenta.font().setSize(12);
                    frasDatCuenta.font().setStyle(Font.BOLD);

                    cellDatCuenta = new PdfPCell(frasDatCuenta);
                    tablaCue.addCell(cellDatCuenta);


                    strDatAlu = "NOMBRE Y DOMICILIO DEL LIBRADO\n\n   " + aluVO.getNombre() + " " + aluVO.getAp1Alu() + "\n\n   " + aluVO.getNumDocAlu() + "\n\n   " + aluVO.getDirAlu() + "\n\n   " + aluVO.getCodPosAlu() + "                         " + aluVO.getLocalAlu() + "\n\n   " + AlumnosGestion.devolverNombreProv(aluVO.getCodProvAlu()).toUpperCase();
                    cellAlumno = new PdfPCell(new Phrase(strDatAlu));
                    tablaDatAlu.addCell(cellAlumno);

                    //strRecibo = "                                                                    CONCEPTO\n   GRUPO             " + curVO.NomCur + "\n   MES                  " + devuelveMes(fechaHoy.Month);
                    strRecibo = "                                                                    CONCEPTO\n   GRUPO             " + TipoCursoGestion.devuelveNombreTipo(curVO.getTipCur()) + " " + curVO.getNomCur() + " " + NivelesGestion.devolverDatosNivel(ediVO.getIdNiv()).getNomNiv()  + "\n   MES                  " + devuelveMes(new Integer(mesAc).intValue());
                    cellRecibo = new PdfPCell(new Phrase(strRecibo));
                    tablaRecibo.addCell(cellRecibo);


                    document.add(parIma);
                    document.add(new Paragraph(" "));
                    document.add(tablaFin);
                    document.add(tablaFec);
                    document.add(new Paragraph(" "));
                    document.add(tablaRecibo);
                    document.add(new Paragraph(" "));
                    document.add(tablaCue);
                    document.add(new Paragraph(" "));
                    document.add(tablaDatAlu);
                    document.add(par14);

                    document.add(new Paragraph(" "));

                    document.add(parIma);
                    document.add(new Paragraph(" "));
                    document.add(tablaFin);
                    document.add(tablaFec);
                    document.add(new Paragraph(" "));
                    document.add(tablaRecibo);
                    document.add(new Paragraph(" "));
                    document.add(tablaCue);
                    document.add(new Paragraph(" "));
                    document.add(tablaDatAlu);
                    document.add(par14);

                    document.newPage();

                    //Se guarda el histórico de recibos

                    hCurVO.setIdEdi(ediVO.getIdEdi());
                    hCurVO.setIdAlu(aluVO.getIdAlu());
                    hCurVO.setNumRec(annoAc + "/" + mesAc + "-" + (numRecMes + 1));
                    hCurVO.setFecExp(fechaHoy);
                    hCurVO.setPagado(false);
                    hCurVO.setIdCur(ediVO.getIdCur());

                    //if (conUsVO.getIdCentro() != 0)
                    //{
                    HisRecGestion.guardarHisRec(hCurVO);
                    //}
                    //Fin control centro
                }
            }
            

            if (! generaPag)
            {
                document.add(new Paragraph("No hay ningún recibo nuevo que generar"));
            }

        }
        catch (DocumentException ex)
        {
              ex.getCause();
        }
        // step 5: Close document
        
        
        document.close();

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
        return "Imprimir recibos pendientes servlet";
    }// </editor-fold>
    
    private String devuelveMes(int mes)
    {
        switch (mes)
        {
            case 1:  return "Enero";
            case 2:  return "Febrero";
            case 3:  return "Marzo";
            case 4:  return "Abril";
            case 5:  return "Mayo";
            case 6:  return "Junio";
            case 7:  return "Julio";
            case 8:  return "Agosto";
            case 9:  return "Septiembre";
            case 10: return "Octubre";
            case 11: return "Noviembre";
            case 12: return "Diciembre";
            default: return null;
        }
    }
    
}
