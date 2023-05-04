/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
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
import es.jahernandez.gestion.CentrosGestion;
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
public class ImpListFIPServlet extends HttpServlet 
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
        String nomCent     = "";
        
        Logger      log      = null;
        ConUsuVO    conUsoVO = null;
        
        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");
            
            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir listado FIP" );
               
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
        
        if(request.getParameter("txtCent") != null)
        {
            nomCent = CentrosGestion.nomCentro(request.getParameter("txtCent"));
        }
        
        //Se pasan los datos introducidos a mayusculas
        numCur  = numCur.toUpperCase();
        nomProf = nomProf.toUpperCase();
        
        //Se carga la lista de alumnos
        Vector      listaAlu = AluEdiGestion.devAluMatEdi(codEdi);

        AlumnosVO   aluVO = null;
        EdicionesVO ediVO = null;
        CursosVO    curVO = null;
        AluEdiVO    alEvo = null;

        String      strLitNC = "";

        boolean     hayPag = false;

        String      datCL1 = "";
        String      datCL2 = "";
        String      diaInS = "";
        String      diaFiS = "";
        String      mesCur = "";


        diaInS = diaIni;
        diaFiS = diaFin;
        mesCur = numMes;


        ediVO = EdicionesGestion.devolverDatosEdi(codEdi);
        curVO = CursosGestion.devolverDatosCurso(ediVO.getIdCur());

        strLitNC = curVO.getNomCur();

        strLitNC = strLitNC + "                                                                                      ";
        strLitNC = strLitNC.substring(0, 60);


        datCL1 = "Nº Curso " + numCur + "                               " + "Especialidad " + strLitNC +
                 "Fecha inicio   " +
                 new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecIn()) + "               Fecha finalización " +
                 new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecFi());

        datCL2 = "Centro Colaborador " + nomCent + "                                                                                           Total días lectivos " + numDiasLect;


        // step 1
        Document document = new Document();

        Paragraph parTitPag1 = new Paragraph("PLAN FIP");
        Paragraph parTitPag2 = new Paragraph("CONTROL DE FIRMAS DE ASISTENCIA DE ALUMNOS");
       

        parTitPag1.font().setSize(10);
        parTitPag2.font().setSize(10);

        parTitPag1.font().setStyle(Font.BOLD);
        parTitPag2.font().setStyle(Font.BOLD);

        parTitPag1.setAlignment(Image.ALIGN_CENTER);
        parTitPag2.setAlignment(Image.ALIGN_CENTER);

        //Instrucciones para meter los datos de concepto en una tabla
        float[] widths = { 60f, 5f, 200f, 5f, 70f, 5f, 70f, 5f, 70f, 5f, 70f, 5f, 70f, 5f, 70f };

        PdfPTable tablaDatAlu = null;
        PdfPTable tablaDatSem = new PdfPTable(2);
        PdfPTable tablaDatSeg = new PdfPTable(3);
        tablaDatSem.setWidthPercentage(100);
        tablaDatSeg.setWidthPercentage(100);
        
        PdfPCell celPS = new PdfPCell(new Phrase(""));

        PdfPCell cellDNI = null;
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


        Phrase   fraLiD = new Phrase("EL DOCENTE O TUTOR DEL CURSO");
        Phrase   fraLDi = new Phrase("EL DIRECTOR DEL CENTRO");
        Phrase   fraLSe = new Phrase("VºBº POR SEGUIMIENTO DEL CURSO");

        Phrase   fraPro = new Phrase("FDO:" + nomProf);
        Phrase   fraDir = new Phrase("FDO:" + InformacionConf.director);
        Phrase   fraSeg = new Phrase("FDO:");


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
        Phrase fraDNI = null;
        Phrase fraNom = null;
        Phrase fraLun = new Phrase("LUNES");
        Phrase fraMar = new Phrase("MARTES");
        Phrase fraMie = new Phrase("MIERCOLES");
        Phrase fraJue = new Phrase("JUEVES");
        Phrase fraVie = new Phrase("VIERNES");
        Phrase fraSab = new Phrase("SABADO");

        Phrase fraTiF = new Phrase("Nota: Indicar en la casilla correspondiente de alumno y día: J=Falta Justificada //NJ=Falta No Justificada ");
        

        Phrase fraSem = new Phrase("SEMANA DEL " + diaInS + " AL " + diaFiS + " DE " + mesCur + " DE " + new SimpleDateFormat("yyyy").format(new Date(System.currentTimeMillis())));         
        PdfPCell celSem = new PdfPCell(fraSem);

        celSem.setHorizontalAlignment(Image.ALIGN_CENTER);


        fraDC1.font().setSize(10);
        fraDC2.font().setSize(10);
        fraTiF.font().setSize(8);
        fraSab.font().setSize(8);
        fraSem.font().setSize(8);
        fraSeg.font().setSize(8);

        celPS.setBorderWidthLeft(0);
        celPS.setBorderWidthTop(0);
        celPS.setBorderWidthBottom(0);

        tablaDatSem.addCell(celPS);
        tablaDatSem.addCell(celSem);
        tablaDatSem.setSpacingBefore(5);
        tablaDatSem.setSpacingAfter(5);

        //cellFecVenRec.Colspan = 2;
       
        Image logoImage = null;

        try
        {
            sc = getServletContext();
            
            logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + "logofip.jpg"));
            
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
            fraDNI = new Phrase("DNI");
            fraDNI.font().setSize(10);
            cellDNI = new PdfPCell(fraDNI);
            cellDNI.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cellDNI.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cellDNI);

            tablaDatAlu.addCell(cellEsp);

            fraNom = new Phrase("APELLIDOS Y NOMBRE");
            fraNom.font().setSize(10);
            cellNom = new PdfPCell(fraNom);
            cellNom.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cellNom.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cellNom);

            tablaDatAlu.addCell(cellEsp);

            fraLun.font().setSize(10);
            cellLun = new PdfPCell(fraLun);
            cellLun.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cellLun.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cellLun);

            tablaDatAlu.addCell(cellEsp);

            fraMar.font().setSize(10);
            cellMar = new PdfPCell(fraMar);
            cellMar.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cellMar.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cellMar);

            tablaDatAlu.addCell(cellEsp);

            fraMie.font().setSize(10);
            cellMie = new PdfPCell(fraMie);
            cellMie.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cellMie.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cellMie);

            tablaDatAlu.addCell(cellEsp);

            fraJue.font().setSize(10);
            cellJue = new PdfPCell(fraJue);
            cellJue.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cellJue.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cellJue);

            tablaDatAlu.addCell(cellEsp);

            fraVie.font().setSize(10);
            cellVie = new PdfPCell(fraVie);
            cellVie.setVerticalAlignment (Image.ALIGN_MIDDLE);
            cellVie.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cellVie);

            tablaDatAlu.addCell(cellEsp);

            fraSab.font().setSize(10);
            cellSab = new PdfPCell(fraSab);
            cellSab.setVerticalAlignment(Image.ALIGN_MIDDLE);
            cellSab.setHorizontalAlignment(Image.ALIGN_CENTER);
            tablaDatAlu.addCell(cellSab);

            for (int ind = 0; ind < 15; ind++)
            {
                tablaDatAlu.addCell(cellEsF);
            }


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
                    aluVO = AlumnosGestion.devolverDatosAlumno( ((AluEdiVO) listaAlu.elementAt(ind)).getIdAlu());
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

                tablaDatAlu.addCell(cellEsp);

                fraNom = new Phrase(aluVO.getAp1Alu() + " " + aluVO.getNombre());
                fraNom.font().setSize(10);
                cellNom = new PdfPCell(fraNom);
                cellNom.setVerticalAlignment(Image.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellNom);

                tablaDatAlu.addCell(cellEsp);

                fraLun = new Phrase("");
                fraLun.font().setSize(10);
                cellLun = new PdfPCell(fraLun);
                cellLun.setVerticalAlignment(Image.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellLun);

                tablaDatAlu.addCell(cellEsp);

                fraMar = new Phrase("");
                fraMar.font().setSize(10);
                cellMar = new PdfPCell(fraMar);
                cellMar.setVerticalAlignment(Image.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellMar);

                tablaDatAlu.addCell(cellEsp);

                fraMie = new Phrase("");
                fraMie.font().setSize(10);
                cellMie = new PdfPCell(fraMie);
                cellMie.setVerticalAlignment(Image.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellMie);

                tablaDatAlu.addCell(cellEsp);

                fraJue = new Phrase("");
                fraJue.font().setSize(10);
                cellJue = new PdfPCell(fraJue);
                cellJue.setVerticalAlignment(Image.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellJue);

                tablaDatAlu.addCell(cellEsp);

                fraVie = new Phrase("");
                fraVie.font().setSize(10);
                cellVie = new PdfPCell(fraVie);
                cellVie.setVerticalAlignment(Image.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellVie);

                tablaDatAlu.addCell(cellEsp);

                fraSab = new Phrase("");
                fraSab.font().setSize(10);
                cellSab = new PdfPCell(fraSab);
                cellSab.setVerticalAlignment(Image.ALIGN_MIDDLE);
                tablaDatAlu.addCell(cellSab);
            }

            tablaDatAlu.setSpacingBefore(10);
            document.add(logoImage);
            document.add(new Paragraph(fraDC1));
            document.add(new Paragraph(fraDC2));
            document.add(tablaDatSem);
            document.add(tablaDatAlu);
            document.add(fraTiF);
            document.add(new Paragraph("                                           "));
            document.add(tablaDatSeg);
 
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
        return "Omprimir Listado FIP Servlet";
    }// </editor-fold>
}
