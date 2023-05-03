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
public class ImpHistRecServlet extends HttpServlet 
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
        ServletContext sc  = null;
        HttpSession          sesion   = request.getSession();
        
        AlumnosVO   aluVO  = null;
        EdicionesVO ediVO  = null;
        CursosVO    curVO  = null;
        AluEdiVO    alEvo  = null;

        String      codCur = "";
        String      fec1   = ""; 
        String      fec2   = "";
        String      codAlu = "";
        int         pagado = 0;
        int         domici = 0;
        int         centro = 0;

        Date        fechaHoy = new Date(System.currentTimeMillis());

        Vector      listaRecibos = new Vector();
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir historico recibos" );
               
        }
        

        if (request.getParameter("lstCursos") != null)
        { 
            codCur = request.getParameter("lstCursos");
        }
        
        if (request.getParameter("hidFechaDe") != null)
        { 
            if (! request.getParameter("hidFechaDe").equals(""))
            {
                String strFechaDe = request.getParameter("hidFechaDe");
                
                fec1 = strFechaDe.substring(6,10) + "/" + 
                       strFechaDe.substring(3,5)  + "/" +
                       strFechaDe.substring(0,2);
            }
        }
        
        if (request.getParameter("hidFechaA") != null)
        { 
            if (! request.getParameter("hidFechaA").equals(""))
            {
                String strFechaA = request.getParameter("hidFechaA");
                
                fec2 = strFechaA.substring(6,10) + "/" + 
                       strFechaA.substring(3,5)  + "/" +
                       strFechaA.substring(0,2);
            }
        }
            
        if (request.getParameter("lstAlum") != null)
        { 
            codAlu = request.getParameter("lstAlum");
            if(codAlu.equals("-1")) codAlu = "";
        }

        if (request.getParameter("lstEstado") != null)
        { 
            pagado = new Integer(request.getParameter("lstEstado")).intValue();
        }
        
        if (request.getParameter("lstCentro") != null)
        { 
            centro = new Integer(request.getParameter("lstCentro")).intValue();
            if(centro == -1) centro = 0;
        }
   
        if (request.getParameter("chkDomic") != null)
        {
            if(request.getParameter("chkDomic").equals("true"))
            {
                domici = 1;
            }
        }
        
        //Se cargan datos de la lista de recibos
        listaRecibos = HisRecGestion.devRecHisAluEdi(codCur,fec1,fec2,codAlu,pagado,centro,domici);
        
        // step 1
        Document document = new Document();
        
        Paragraph par14  = null;

        //Instrucciones para meter los datos de concepto en una tabla
        PdfPTable tablaRecibo  = new PdfPTable(1);
        PdfPTable tablaDatAlu  = new PdfPTable(1);
        PdfPTable tablaFin     = new PdfPTable(3);
        PdfPTable tablaFec     = new PdfPTable(2);
        PdfPTable tablaCue     = new PdfPTable(1);
        PdfPCell  cellRecibo    = null;
        PdfPCell  cellAlumno    = null;
        PdfPCell  cellDatNumRec = null;
        PdfPCell  cellDatLocRec = null;
        PdfPCell  cellDatImpRec = null;
        PdfPCell  cellFecExpRec = null;
        PdfPCell  cellFecVenRec = null;
        PdfPCell  cellDatCuenta = null;
        Phrase    frasDatCuenta = null;
        Phrase    frasDatNumRec = null;
        Phrase    frasLocExpedi = null;
        Phrase    frasImportCur = null;
        Paragraph parIma        = new Paragraph();
        Image     logoImage     = null;
        
        String   strRecibo     = "";
        String   strDatAlu     = "";
        String   strDatNumRec  = "";
        String   strDatLocRec  = "";
        String   strDatImpRec  = "";
        String   strFecExpRec  = "";
        String   strFecVenRec  = "";
        String   strDatCuenta  = "";
        String   strNombreBan  = "";
        
        tablaRecibo.setWidthPercentage(100); 
        tablaDatAlu.setWidthPercentage(100);
        tablaFin.setWidthPercentage(100);
        tablaFec.setWidthPercentage(100);
        tablaFec.setWidthPercentage(100);
        
        try
        {
            sc = getServletContext();
            
            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
            
            // step 3
            document.setMargins(32, 32, 16, 0);
            document.open();
            
            for(int ind=0 ; ind<listaRecibos.size() ; ind++)
            {
                aluVO = AlumnosGestion.devolverDatosAlumno(((HisRecVO) listaRecibos.elementAt(ind)).getIdAlu());
                ediVO = EdicionesGestion.devolverDatosEdi(((HisRecVO) listaRecibos.elementAt(ind)).getIdEdi());
                curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());
                alEvo = AluEdiGestion.devDatAluEdi(aluVO.getIdAlu(),ediVO.getIdEdi());

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
                
                strDatNumRec  = "RECIBO NUMERO\n" + ((HisRecVO) listaRecibos.elementAt(ind)).getNumRec();
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

                strFecExpRec = "FECHA EXPEDICION\n" + new SimpleDateFormat("dd/MM/yyy").format(((HisRecVO) listaRecibos.elementAt(ind)).getFecExp());
                cellFecExpRec = new PdfPCell(new Phrase(strFecExpRec));
                tablaFec.addCell(cellFecExpRec);

                strFecVenRec = "FECHA VENCIMIENTO\n" + new SimpleDateFormat("dd/MM/yyy").format(new Date(((HisRecVO) listaRecibos.elementAt(ind)).getFecExp().getTime() + 432000000));
                cellFecVenRec = new PdfPCell(new Phrase(strFecVenRec));
                tablaFec.addCell(cellFecVenRec);

                strDatCuenta = "PAGADERO EN CAJA O BANCO        OFICINA       CLAVE O NÚMERO DE CUENTA\n";
                if (alEvo.getNumCuenta().trim().equals(""))
                {
                    strDatCuenta = strDatCuenta + "Pago al contado";
                }
                else
                {
                    strNombreBan = BancosGestion.devolverNombreBanco(alEvo.getNumCuenta().substring(0,4));
                    strNombreBan = strNombreBan + "                                                 ";
                    
                    strNombreBan = strNombreBan.substring(0, 30);

                    strDatCuenta = strDatCuenta + strNombreBan + "   " +  alEvo.getNumCuenta().substring(4,4) + "             "  + alEvo.getNumCuenta() ;
                }
                frasDatCuenta = new Phrase(strDatCuenta,new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

                frasDatCuenta.font().setSize(12);
                frasDatCuenta.font().setStyle(Font.BOLD);

                cellDatCuenta = new PdfPCell(frasDatCuenta);
                tablaCue.addCell(cellDatCuenta);


                strDatAlu = "NOMBRE Y DOMICILIO DEL LIBRADO\n\n   " + aluVO.getNombre() + " " + aluVO.getAp1Alu() + "\n\n   " + aluVO.getNumDocAlu() + "\n\n   " + aluVO.getDirAlu() + "\n\n   " + aluVO.getCodPosAlu() + "                         " + aluVO.getLocalAlu() + "\n\n   " + AlumnosGestion.devolverNombreProv(aluVO.getCodProvAlu()).toUpperCase();
                cellAlumno = new PdfPCell(new Phrase(strDatAlu));
                tablaDatAlu.addCell(cellAlumno);

                //strRecibo = "                                                                    CONCEPTO\n   GRUPO             " + curVO.NomCur + "\n   MES                  " + devuelveMes(listaRecibos[ind].FecExp.Month);
                strRecibo = "                                                                    CONCEPTO\n   GRUPO             " + TipoCursoGestion.devuelveNombreTipo(curVO.getTipCur()) + " " + curVO.getNomCur() + " " + NivelesGestion.devolverDatosNivel(ediVO.getIdNiv()).getNomNiv() + "\n   MES                  " + devuelveMes( new Integer ( new SimpleDateFormat("MM").format( ((HisRecVO)listaRecibos.elementAt(ind)).getFecExp())).intValue());
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
            }

            if (listaRecibos.size() == 0)
            {
                document.add(new Paragraph("No existen documentos para las condiciones dadas"));
            }
            
        }
        catch (DocumentException ex)
        {

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
        return "Imprimir Historico recibo servlet";
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
