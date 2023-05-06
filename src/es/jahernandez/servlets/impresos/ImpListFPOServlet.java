/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.jahernandez.datos.AluEdiVO;
import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.CursosVO;
import es.jahernandez.datos.EdicionesVO;
import es.jahernandez.datos.InformacionConf;
import es.jahernandez.gestion.AluEdiGestion;
import es.jahernandez.gestion.AlumnosGestion;
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.EdicionesGestion;
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
public class ImpListFPOServlet extends HttpServlet
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
        ServletContext sc     = null;
        HttpSession    sesion = request.getSession();

        String codEdi      = "";
        String numCur      = "";
        String diaIni      = "";
        String diaFin      = "";
        String numDiasLect = "";
        String numMes      = "";
        String nomProf     = "";
        String lugImp      = "";
        String lugyCent    = "";

        Logger      log      = null;
        ConUsuVO    conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir listado FPO" );

        }

        if(request.getParameter("codEdi") != null)
        {
            codEdi = request.getParameter("codEdi");
        }

        if(request.getParameter("txtNumCur") != null)
        {
            numCur = request.getParameter("txtNumCur");
        }

        if(request.getParameter("txtDiaIni") != null)
        {
            diaIni = request.getParameter("txtDiaIni");
        }

        if(request.getParameter("txtDiaFin") != null)
        {
            diaFin = request.getParameter("txtDiaFin");
        }

        if(request.getParameter("txtNumDiasLect") != null)
        {
            numDiasLect = request.getParameter("txtNumDiasLect");
        }

        if(request.getParameter("txtMes") != null)
        {
            numMes = request.getParameter("txtMes");
        }

        if(request.getParameter("txtNomProf") != null)
        {
            nomProf = request.getParameter("txtNomProf");
        }

        if(request.getParameter("txtLugImp") != null)
        {
            lugImp = request.getParameter("txtLugImp");
        }

        if(request.getParameter("txtLugCen") != null)
        {
            lugyCent = request.getParameter("txtLugCen");
        }

        //Se pasan los datos introducidos a mayusculas
        numCur = numCur.toUpperCase();
        //nomProf = nomProf.toUpp();
        lugImp = lugImp.toUpperCase();

        //Se carga la lista de alumnos
        Vector listaAlu = AluEdiGestion.devAluMatEdi(codEdi);

        AlumnosVO   aluVO    = null;
        EdicionesVO ediVO    = null;
        CursosVO    curVO    = null;
        AluEdiVO    alEvo    = null;

        String      strLitNC = "";

        boolean     hayPag   = false;

        String      datCL1   = "";
        String      datCL2   = "";
        String      datCL3   = "";

        String      diaInS   = "";
        String      diaFiS   = "";
        String      mesCur   = "";


        diaInS = diaIni;
        diaFiS = diaFin;
        mesCur = mesCur;


        ediVO = EdicionesGestion.devolverDatosEdi(codEdi);
        curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());

        strLitNC = curVO.getNomCur() ;

        strLitNC = strLitNC + "......................................................................................";
        strLitNC = strLitNC.substring(0, 60);


        datCL1 = "DENOMINACIÃ“N DE LA ACCIÃ“N FORMATIVA....." + strLitNC + "..............................." + "EXPEDIENTE " + numCur;
        datCL2 = "LUGAR Y FECHAS DE CELEBRACION..." + lugImp + "......" +
                  new SimpleDateFormat("dd").format(ediVO.getFecIn()) + " DE " + devuelveMes(new Integer(new SimpleDateFormat("MM").format(ediVO.getFecIn())).intValue()).toUpperCase() + " DE " + new SimpleDateFormat("yyyy").format(ediVO.getFecIn()) + " A " +
                  new SimpleDateFormat("dd").format(ediVO.getFecFi()) + " DE " + devuelveMes(new Integer(new SimpleDateFormat("MM").format(ediVO.getFecFi())).intValue()).toUpperCase() + " DE " + new SimpleDateFormat("yyyy").format(ediVO.getFecFi()) ;

        datCL3 = "AÃ‘O  " + new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis())) + " MES " + numMes + "    SEMANA DEL DÃ�A " + diaIni + " AL " + diaFin;

        // step 1
        Document document = new Document();

        Paragraph parTitPag1 = new Paragraph("FORMACIÓN PARA EL EMPLEO-ANEXO X");
        Paragraph parTitPag2 = new Paragraph("JUSTIFICANTE DE ASISTENCIA DE LOS ALUMNOS");

        parTitPag1.font().setSize(10);
        parTitPag2.font().setSize(10);

        parTitPag1.font().setStyle(Font.BOLD);
        parTitPag2.font().setStyle(Font.BOLD);

        parTitPag1.setAlignment(Element.ALIGN_CENTER);
        parTitPag2.setAlignment(Element.ALIGN_CENTER);

        //Instrucciones para meter los datos de concepto en una tabla
        float[] widths = {200f, 70f, 70f, 70f, 70f, 70f, 70f };

        PdfPTable tablaDatAlu = null;
        PdfPTable tablaDatSem = new PdfPTable(2);
        PdfPTable tablaDatSeg = new PdfPTable(3);

        tablaDatSem.setWidthPercentage(100);
        tablaDatSeg.setWidthPercentage(100);
        PdfPCell celPS = new PdfPCell(new Phrase(""));

        PdfPCell cellNom = null;
        PdfPCell cellLun = null;
        PdfPCell cellMar = null;
        PdfPCell cellMie = null;
        PdfPCell cellJue = null;
        PdfPCell cellVie = null;
        PdfPCell cellSab = null;
        PdfPCell cellEsp = null;
        PdfPCell cellEsF = null;
        PdfPCell cellEsS = null;


        Phrase fraLiD = new Phrase("EL DOCENTE O TUTOR DEL CURSO");
        Phrase fraLDi = new Phrase("EL DIRECTOR DEL CENTRO");
        Phrase fraLSe = new Phrase("Vº Bº POR SEGUIMIENTO DEL CURSO");

        Phrase fraPro = new Phrase("FDO:" + nomProf);
        Phrase fraDir = new Phrase("FDO:" + InformacionConf.director);
        Phrase fraSeg = new Phrase("FDO:");


        fraLiD.font().setSize(8);
        fraLDi.font().setSize(8);
        fraLSe.font().setSize(8);

        fraPro.font().setSize(8);
        fraDir.font().setSize(8);
        fraSeg.font().setSize(8);


        PdfPCell cellDoc = new PdfPCell(fraLiD);
        PdfPCell cellDi1 = new PdfPCell(fraLDi);
        PdfPCell cellSeg = new PdfPCell(fraLSe);

        PdfPCell cellPro = new PdfPCell(fraPro);
        PdfPCell cellDi2 = new PdfPCell(fraDir);
        PdfPCell cellFip = new PdfPCell(fraSeg);

        cellDoc.setBorderWidth(0);
        cellDi1.setBorderWidth(0);
        cellSeg.setBorderWidth(0);

        cellPro.setBorderWidth(0);
        cellDi2.setBorderWidth(0);
        cellFip.setBorderWidth(0);

        Phrase fraDC1 = new Phrase(datCL1);
        Phrase fraDC2 = new Phrase(datCL2);
        Phrase fraNom = null;
        Phrase fraLun = new Phrase("LUNES");
        Phrase fraMar = new Phrase("MARTES");
        Phrase fraMie = new Phrase("MIERCOLES");
        Phrase fraJue = new Phrase("JUEVES");
        Phrase fraVie = new Phrase("VIERNES");
        Phrase fraSab = new Phrase("SABADO");

        Phrase fraTiF = new Phrase("Nota: Indicar en la casilla correspondiente de alumno y día: J=Falta Justificada //NJ=Falta No Justificada ");


        Phrase fraSem = new Phrase(datCL3);
        PdfPCell celSem = new PdfPCell(fraSem);

        celSem.setHorizontalAlignment(Element.ALIGN_CENTER);


        fraDC1.font().setSize(10);
        fraDC2.font().setSize(10);
        fraTiF.font().setSize(8);
        fraSab.font().setSize(8);
        fraSem.font().setSize(10);
        fraSeg.font().setSize(8);

        celPS.setBorderWidthLeft(0);
        celPS.setBorderWidthTop(0);
        celPS.setBorderWidthBottom(0);

        //cellFecVenRec.Colspan = 2;

        Image logoImage  = null;//
        try
        {
            sc = getServletContext();

            logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + "logofpo.jpg"));

            // step 2: we set the ContentType and create an instance of the Writer
             PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            // step 3
            document.setPageSize(PageSize.A4.rotate());
            document.setMargins(16, 16, 16, 16);
            document.open();

            //Se inicializa la tabla
            tablaDatAlu = new PdfPTable(widths);
            tablaDatAlu.setWidthPercentage(100);


            //Se formatea la celda de espacio
            cellEsp = new PdfPCell();
            cellEsp.setBorderWidthTop(0);
            cellEsp.setBorderWidthBottom(0);
            cellEsF = new PdfPCell();
            cellEsF.setBorderWidthBottom(0);
            cellEsF.setBorderWidthLeft(0);
            cellEsF.setBorderWidthTop(0);
            cellEsF.setBorderWidthRight(0);
            cellEsF.setMinimumHeight(10);
            cellEsS = new PdfPCell();
            cellEsS.setBorderWidth(0);
            cellEsS.setMinimumHeight(30);



            //Se genera la cabecera de la tabla
            //fraDNI = new Phrase("DNI");
            //fraDNI.Font.Size = 10;
            //cellDNI = new PdfPCell(fraDNI);
            //cellDNI.VerticalAlignment = Element.ALIGN_MIDDLE;
            //cellDNI.HorizontalAlignment = Element.ALIGN_CENTER;
            //cellDNI.BackgroundColor = Color.GRAY;
            //tablaDatAlu.AddCell(cellDNI);


            fraNom = new Phrase("APELLIDOS Y NOMBRE");
            fraNom.font().setSize(10);
            cellNom = new PdfPCell(fraNom);
            cellNom.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellNom.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellNom.setBackgroundColor(Color.LIGHT_GRAY);
            tablaDatAlu.addCell(cellNom);

            fraLun.font().setSize(10);
            cellLun = new PdfPCell(fraLun);
            cellLun.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellLun.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellLun.setBackgroundColor(Color.LIGHT_GRAY);
            tablaDatAlu.addCell(cellLun);


            fraMar.font().setSize(10);
            cellMar = new PdfPCell(fraMar);
            cellMar.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellMar.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellMar.setBackgroundColor(Color.LIGHT_GRAY);
            tablaDatAlu.addCell(cellMar);


            fraMie.font().setSize(10);
            cellMie = new PdfPCell(fraMie);
            cellMie.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellMie.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellMie.setBackgroundColor(Color.LIGHT_GRAY);
            tablaDatAlu.addCell(cellMie);


            fraJue.font().setSize(10);
            cellJue = new PdfPCell(fraJue);
            cellJue.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellJue.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellJue.setBackgroundColor(Color.LIGHT_GRAY);
            tablaDatAlu.addCell(cellJue);


            fraVie.font().setSize(10);
            cellVie = new PdfPCell(fraVie);
            cellVie.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellVie.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellVie.setBackgroundColor(Color.LIGHT_GRAY);
            tablaDatAlu.addCell(cellVie);


            fraSab.font().setSize(10);
            cellSab = new PdfPCell(fraSab);
            cellSab.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cellSab.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSab.setBackgroundColor(Color.LIGHT_GRAY);
            tablaDatAlu.addCell(cellSab);

            //Se introducen datos seguimiento
            tablaDatSeg.addCell(cellDoc);
            tablaDatSeg.addCell(cellDi1);
            tablaDatSeg.addCell(cellSeg);

            tablaDatSeg.addCell(cellEsS);
            tablaDatSeg.addCell(cellEsS);
            tablaDatSeg.addCell(cellEsS);

            tablaDatSeg.addCell(cellPro);
            tablaDatSeg.addCell(cellDi2);
            tablaDatSeg.addCell(cellFip);


            for (int ind = 0; ind < 15; ind++)
            {

                if (ind < listaAlu.size())
                {
                    aluVO = AlumnosGestion.devolverDatosAlumno(((AluEdiVO)listaAlu.elementAt(ind)).getIdAlu());
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

                //fraDNI = new Phrase(aluVO.NumDocAlu);
                //fraDNI.Font.Size = 10;
                //cellDNI = new PdfPCell(fraDNI);
                //cellDNI.MinimumHeight = 35;
                //cellDNI.VerticalAlignment = Element.ALIGN_MIDDLE;
                //tablaDatAlu.AddCell(cellDNI);


                fraNom = new Phrase(aluVO.getAp1Alu() + " " + aluVO.getNombre());
                fraNom.font().setSize(10);
                cellNom = new PdfPCell(fraNom);
                cellNom.setMinimumHeight(25);
                cellNom.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellNom);


                fraLun = new Phrase("");
                fraLun.font().setSize(10);
                cellLun = new PdfPCell(fraLun);
                cellLun.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellLun);


                fraMar = new Phrase("");
                fraMar.font().setSize(10);
                cellMar = new PdfPCell(fraMar);
                cellMar.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellMar);

                fraMie = new Phrase("");
                fraMie.font().setSize(10);
                cellMie = new PdfPCell(fraMie);
                cellMie.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellMie);

                fraJue = new Phrase("");
                fraJue.font().setSize(10);
                cellJue = new PdfPCell(fraJue);
                cellJue.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellJue);

                fraVie = new Phrase("");
                fraVie.font().setSize(10);
                cellVie = new PdfPCell(fraVie);
                cellVie.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellVie);

                fraSab = new Phrase("");
                fraSab.font().setSize(10);
                cellSab = new PdfPCell(fraSab);
                cellSab.setVerticalAlignment(Element.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellSab);
            }

            tablaDatAlu.setSpacingBefore(10);
            document.add(logoImage);
            document.add(parTitPag1);
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
        	System.out.println("Error generando informe");
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
        return "Imprimir listado FPO Servlet";
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
