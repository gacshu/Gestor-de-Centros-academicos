/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
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
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.EdicionesGestion;
import es.jahernandez.gestion.NivelesGestion;
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
public class ImpListPropServlet extends HttpServlet  {

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
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
     
        ServletContext sc     = null;
        HttpSession    sesion = request.getSession();
        
        String         nomMes = "";
        String         codEdi = "";
        String         lugImp = "";
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir listado propio" );
               
        }
        
        if(request.getParameter("codEdi") != null)
        {
            codEdi = request.getParameter("codEdi");
        }
        
        if(request.getParameter("txtMes") != null)
        {
            nomMes = request.getParameter("txtMes").toUpperCase();
        }
        
        if(request.getParameter("txtLugImp") != null)
        {
            lugImp = request.getParameter("txtLugImp");
        }
               
        //Se pasan los datos introducidos a mayusculas
        /*txtNumCur.Text = txtNumCur.Text.ToUpper();
        txtNomProf.Text = txtNomProf.Text.ToUpper();
        txtLugImp.Text = txtLugImp.Text.ToUpper();*/

        //Se carga la lista de alumnos
        Vector listaAlu = AluEdiGestion.devAluMatEdi(codEdi);

        AlumnosVO aluVO = null;
        EdicionesVO ediVO = null;
        CursosVO curVO = null;
        AluEdiVO alEvo = null;

        String strLitNC = "";

        boolean hayPag = false;

        String datCL1 = "";
        String datCL2 = "";
        String datCL3 = "";

        String diaInS = "";
        String diaFiS = "";
        String mesCur = "";


        //diaInS = txtDiaIn.Text;
        //diaFiS = txtDiaFi.Text;
        mesCur = nomMes;

        ediVO = EdicionesGestion.devolverDatosEdi(codEdi);
        curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());

        strLitNC = curVO.getNomCur();

        strLitNC = strLitNC + "                                                                                   ";
        strLitNC = strLitNC.substring(0, 60);


        datCL1 = "DENOMINACIÓN DE LA ACCIÓN FORMATIVA       " + strLitNC + "                          " + "NIVEL    " + NivelesGestion.devolverDatosNivel(ediVO.getIdNiv()).getNomNiv();
        datCL2 = "LUGAR Y FECHAS DE CELEBRACION     " + lugImp + "        " +
                  new SimpleDateFormat("dd").format(ediVO.getFecIn())    + " DE " + devuelveMes(new Integer(new SimpleDateFormat("MM").format(ediVO.getFecIn())).intValue()).toUpperCase()+ " DE " + new SimpleDateFormat("yyyy").format(ediVO.getFecIn()) + " A " +
                  new SimpleDateFormat("dd").format(ediVO.getFecFi())    + " DE " + devuelveMes(new Integer(new SimpleDateFormat("MM").format(ediVO.getFecFi())).intValue()).toUpperCase()+ " DE " + new SimpleDateFormat("yyyy").format(ediVO.getFecFi());

        datCL3 = "AÑO  " + new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis())) + " MES " + nomMes;

        // step 1
        Document document = new Document();

        Paragraph parTitPag2 = new Paragraph("JUSTIFICANTE DE ASISTENCIA DE LOS ALUMNOS");
        Paragraph parTitPag3 = new Paragraph();

        parTitPag2.font().setSize(10);

        parTitPag2.font().setStyle(Font.BOLD);

        parTitPag2.setAlignment(Image.ALIGN_CENTER);

        //Instrucciones para meter los datos de concepto en una tabla
        float[] widths = { 5f, 10f, 1f, 1f, 1f, 1f, 1f, 
                            1f, 1f, 1f, 1f, 1f, 1f, 1f, 
                            1f, 1f, 1f, 1f, 1f, 1f, 1f, 
                            1f, 1f, 1f, 1f ,1f, 1f, 1f,
                            1f, 1f, 1f, 1f, 1f};

        PdfPTable tablaDatAlu = null;
        PdfPTable tablaDatSem = new PdfPTable(2);
        PdfPTable tablaDatSeg = new PdfPTable(3);
        tablaDatSem.setWidthPercentage(100);
        tablaDatSeg.setWidthPercentage(100);


        PdfPCell celPS = new PdfPCell(new Phrase(""));

        PdfPCell cellDNI = null;
        PdfPCell cellNom = null;
        PdfPCell cell01 = null;
        PdfPCell cell02 = null;
        PdfPCell cell03 = null;
        PdfPCell cell04 = null;
        PdfPCell cell05 = null;
        PdfPCell cell06 = null;
        PdfPCell cell07 = null;
        PdfPCell cell08 = null;
        PdfPCell cell09 = null;
        PdfPCell cell10 = null;
        PdfPCell cell11 = null;
        PdfPCell cell12 = null;
        PdfPCell cell13 = null;
        PdfPCell cell14 = null;
        PdfPCell cell15 = null;
        PdfPCell cell16 = null;
        PdfPCell cell17 = null;
        PdfPCell cell18 = null;
        PdfPCell cell19 = null;
        PdfPCell cell20 = null;
        PdfPCell cell21 = null;
        PdfPCell cell22 = null;
        PdfPCell cell23 = null;
        PdfPCell cell24 = null;
        PdfPCell cell25 = null;
        PdfPCell cell26 = null;
        PdfPCell cell27 = null;
        PdfPCell cell28 = null;
        PdfPCell cell29 = null;
        PdfPCell cell30 = null;
        PdfPCell cell31 = null;

        Phrase fraDC1 = new Phrase(datCL1);
        Phrase fraDC2 = new Phrase(datCL2);
        Phrase fraDNI = null;
        Phrase fraNom = null;
        Phrase fra01 = new Phrase("1");
        Phrase fra02 = new Phrase("2");
        Phrase fra03 = new Phrase("3");
        Phrase fra04 = new Phrase("4");
        Phrase fra05 = new Phrase("5");
        Phrase fra06 = new Phrase("6");
        Phrase fra07 = new Phrase("7");
        Phrase fra08 = new Phrase("8");
        Phrase fra09 = new Phrase("9");
        Phrase fra10 = new Phrase("10");
        Phrase fra11 = new Phrase("11");
        Phrase fra12 = new Phrase("12");
        Phrase fra13 = new Phrase("13");
        Phrase fra14 = new Phrase("14");
        Phrase fra15 = new Phrase("15");
        Phrase fra16 = new Phrase("16");
        Phrase fra17 = new Phrase("17");
        Phrase fra18 = new Phrase("18");
        Phrase fra19 = new Phrase("19");
        Phrase fra20 = new Phrase("20");
        Phrase fra21 = new Phrase("21");
        Phrase fra22 = new Phrase("22");
        Phrase fra23 = new Phrase("23");
        Phrase fra24 = new Phrase("24");
        Phrase fra25 = new Phrase("25");
        Phrase fra26 = new Phrase("26");
        Phrase fra27 = new Phrase("27");
        Phrase fra28 = new Phrase("28");
        Phrase fra29 = new Phrase("29");
        Phrase fra30 = new Phrase("30");
        Phrase fra31 = new Phrase("31");


        Phrase fraTiF = new Phrase("Nota: Indicar en la casilla correspondiente de alumno y día: J=Falta Justificada //NJ=Falta No Justificada ");


        Phrase fraSem = new Phrase(datCL3);
        PdfPCell celSem = new PdfPCell(fraSem);

        celSem.setHorizontalAlignment(Image.ALIGN_CENTER);


        fraDC1.font().setSize(10);
        fraDC2.font().setSize(10);
        fraTiF.font().setSize(8);
        fraSem.font().setSize(10);
        
        celPS.setBorderWidthLeft(0);
        celPS.setBorderWidthTop(0);
        celPS.setBorderWidthBottom(0);

        //cellFecVenRec.Colspan = 2;

        Paragraph parIma = new Paragraph();
        Image logoImage =  null;

        try
        {
            sc = getServletContext();
            
            logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + InformacionConf.logo));
            logoImage.scaleAbsolute(150, 38);

            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
           
            // step 3
            document.setPageSize(PageSize.A4.rotate());
            document.setMargins(16, 16, 16, 16);
            document.open();

            //Se inicializa la tabla
            tablaDatAlu = new PdfPTable(widths);
            tablaDatAlu.setWidthPercentage(100);

            //Se genera la cabecera de la tabla
            fraDNI = new Phrase("DNI");
            fraDNI.font().setSize(10);
            cellDNI = new PdfPCell(fraDNI);
            cellDNI.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cellDNI.setHorizontalAlignment(Image.ALIGN_CENTER);
            //cellDNI.BackgroundColor = Color.GRAY;
            tablaDatAlu.addCell(cellDNI);

            fraNom = new Phrase("APELLIDOS Y NOMBRE");
            fraNom.font().setSize(10);
            cellNom = new PdfPCell(fraNom);
            cellNom.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cellNom.setHorizontalAlignment(Image.ALIGN_CENTER);
            //cellNom.BackgroundColor = Color.GRAY;
            tablaDatAlu.addCell(cellNom);

            fra01.font().setSize(10);
            cell01 = new PdfPCell(fra01);
            cell01.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell01.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell01);

            fra02.font().setSize(10);
            cell02 = new PdfPCell(fra02);
            cell02.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell02.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell02);

            fra03.font().setSize(10);
            cell03 = new PdfPCell(fra03);
            cell03.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell03.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell03);

            fra04.font().setSize(10);
            cell04 = new PdfPCell(fra04);
            cell04.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell04.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell04);

            fra05.font().setSize(10);
            cell05 = new PdfPCell(fra05);
            cell05.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell05.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell05);

            fra06.font().setSize (10);
            cell06 = new PdfPCell(fra06);
            cell06.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell06.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell06);

            fra07.font().setSize(10);
            cell07 = new PdfPCell(fra07);
            cell07.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell07.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell07);

            fra08.font().setSize(10);
            cell08 = new PdfPCell(fra08);
            cell08.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell08.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell08);

            fra09.font().setSize(10);
            cell09 = new PdfPCell(fra09);
            cell09.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell09.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell09);

            fra10.font().setSize(10);
            cell10 = new PdfPCell(fra10);
            cell10.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell10.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell10);

            fra11.font().setSize(10);
            cell11 = new PdfPCell(fra11);
            cell11.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell11.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell11);

            fra12.font().setSize(10);
            cell12 = new PdfPCell(fra12);
            cell12.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell12.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell12);

            fra13.font().setSize(10);
            cell13 = new PdfPCell(fra13);
            cell13.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell13.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell13);

            fra14.font().setSize(10);
            cell14 = new PdfPCell(fra14);
            cell14.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell14.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell14);

            fra15.font().setSize(10);
            cell15 = new PdfPCell(fra15);
            cell15.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell15.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell15);

            fra16.font().setSize(10);
            cell16 = new PdfPCell(fra16);
            cell16.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell16.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell16);

            fra17.font().setSize(10);
            cell17 = new PdfPCell(fra17);
            cell17.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell17.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell17);

            fra18.font().setSize(10);
            cell18 = new PdfPCell(fra18);
            cell18.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell18.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell18);

            fra19.font().setSize(10);
            cell19 = new PdfPCell(fra19);
            cell19.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell19.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell19);

            fra20.font().setSize(10);
            cell20 = new PdfPCell(fra20);
            cell20.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell20.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell20);

            fra21.font().setSize(10);
            cell21 = new PdfPCell(fra21);
            cell21.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell21.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell21);

            fra22.font().setSize(10);
            cell22 = new PdfPCell(fra22);
            cell22.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell22.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell22);

            fra23.font().setSize(10);
            cell23 = new PdfPCell(fra23);
            cell23.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell23.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell23);

            fra24.font().setSize(10);
            cell24 = new PdfPCell(fra24);
            cell24.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell24.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell24);

            fra25.font().setSize(10);
            cell25 = new PdfPCell(fra25);
            cell25.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell25.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell25);

            fra26.font().setSize(10);
            cell26 = new PdfPCell(fra26);
            cell26.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell26.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell26);

            fra27.font().setSize(10);
            cell27 = new PdfPCell(fra27);
            cell27.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell27.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell27);

            fra28.font().setSize(10);
            cell28 = new PdfPCell(fra28);
            cell28.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell28.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell28);

            fra29.font().setSize(10);
            cell29 = new PdfPCell(fra29);
            cell29.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell29.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell29);

            fra30.font().setSize(10);
            cell30 = new PdfPCell(fra30);
            cell30.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell30.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell30);

            fra31.font().setSize(10);
            cell31 = new PdfPCell(fra31);
            cell31.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cell31.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cell31);

         
            for (int ind = 0; ind < 15; ind++)
            {

                if (ind < listaAlu.size())
                {
                    aluVO = AlumnosGestion.devolverDatosAlumno(((AluEdiVO) listaAlu.elementAt(ind)).getIdAlu());
                    alEvo = AluEdiGestion.devDatAluEdi(aluVO.getIdAlu(), ediVO.getIdEdi());
                }
                else
                {
                    aluVO = new AlumnosVO();
                    alEvo = new AluEdiVO();
                }

                hayPag = true;

                // step 4

                //Mostramos datos alumno

                fraDNI = new Phrase(aluVO.getNumDocAlu());
                fraDNI.font().setSize(10);
                cellDNI = new PdfPCell(fraDNI);
                cellDNI.setMinimumHeight(20);
                cellDNI.setVerticalAlignment(Image.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellDNI);


                fraNom = new Phrase((aluVO.getAp1Alu() + " " + aluVO.getNombre() + "                                                       ").substring(0,25));
                fraNom.font().setSize(10);
                cellNom = new PdfPCell(fraNom);
                cellNom.setMinimumHeight(25);
                cellNom.setVerticalAlignment(Image.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellNom);


                for (int indIn = 0; indIn < 31; indIn++)
                {
                    fra01 = new Phrase("");
                    fra01.font().setSize(10);
                    cell01 = new PdfPCell(fra01);
                    cell01.setVerticalAlignment(Image.ALIGN_MIDDLE);
                    tablaDatAlu.addCell(cell01);
                }

                
            }

            tablaDatAlu.setSpacingBefore(10);
            document.add(logoImage);
            document.add(parTitPag2);
            document.add(new Paragraph(fraDC1));
            document.add(new Paragraph(fraDC2));
            document.add(new Paragraph(fraSem));
            document.add(tablaDatSem);
            document.add(tablaDatAlu);
            
            if (!hayPag)
            {
                document.add(new Paragraph("No existen alumnos para generar listado"));
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
        return "Imprimir Listado Propio Servlet";
    }// </editor-fold>
    
    private String devuelveMes(int mes)
    {
        switch (mes)
        {
            case  1: return "Enero";
            case  2: return "Febrero";
            case  3: return "Marzo";
            case  4: return "Abril";
            case  5: return "Mayo";
            case  6: return "Junio";
            case  7: return "Julio";
            case  8: return "Agosto";
            case  9: return "Septiembre";
            case 10: return "Octubre";
            case 11: return "Noviembre";
            case 12: return "Diciembre";
            default: return null;
        }
    }
}
