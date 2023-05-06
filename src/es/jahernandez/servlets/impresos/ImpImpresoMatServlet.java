/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

//Paquetes de manejo de pdf
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
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
import es.jahernandez.gestion.ListaCodPostGestion;
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
public class ImpImpresoMatServlet extends HttpServlet
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

        ServletContext   sc       = null;
        HttpSession      sesion   = request.getSession();


        AlumnosVO        aluVO    = null;
        EdicionesVO      ediVO    = null;
        CursosVO         curVO    = null;
        AluEdiVO         alEdiVO  = null;


        //Se cargan datos de alumno y edición
        aluVO   = AlumnosGestion.devolverDatosAlumno(request.getParameter("codAlu"));
        ediVO   = EdicionesGestion.devolverDatosEdi(request.getParameter("codEdi"));
        curVO   = CursosGestion.devolverDatosCurso(ediVO.getIdCur());
        alEdiVO = AluEdiGestion.devDatAluEdi(request.getParameter("codAlu"),request.getParameter("codEdi"));

        Logger      log      = null;
        ConUsuVO    conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir impreso matrícula" );

        }

        String formaPago = "";// "Contado";

        if (alEdiVO.getNumCuenta().trim().equals(""))
        {
            formaPago = "Contado";
        }
        else
        {
            formaPago = "Número cuenta:" + alEdiVO.getNumCuenta();
        }

        // step 1
        // need to write to memory first due to IE wanting
        // to know the length of the pdf beforehand
        Document  document  = new Document();
        Paragraph parIma    = new Paragraph();
        Image     logoImage = null;
        //iTextSharp.text.Image logoImage = null;

        try
        {
            sc = getServletContext();

            logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + InformacionConf.logo));
            logoImage.scaleAbsolute(150, 38);

            parIma.setAlignment(Element.ALIGN_LEFT);
            parIma.add(logoImage);

            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            PdfPTable tablaDatAlu = new PdfPTable(1);
            tablaDatAlu.setWidthPercentage(100);
            String strDatAlu = "";
            PdfPCell cellDatAlu = null;
            Phrase fraseDatAlu = null;

            strDatAlu = "                              DATOS DEL ALUMNO\n\n" +
                        "NOMBRE             "  + aluVO.getNombre()   + "\n\n" +
                        "APELLIDOS           " + aluVO.getAp1Alu()   + "\n\n" +
                        "DIRECCION           " + aluVO.getDirAlu()   + "\n\n" +
                        "POBLACION           " + aluVO.getLocalAlu() + "            CODIGO POSTAL:  " + aluVO.getCodPosAlu() + "\n\n" +
                        "PROVINCIA           " + ListaCodPostGestion.devuelveNombreProv(aluVO.getCodProvAlu()).toUpperCase() + "\n\n" +
                        "NIF/NIE             " + aluVO.getNumDocAlu()+ "            E-MAIL    " + aluVO.getEmail();


            PdfPTable tablaDatCur = new PdfPTable(1);
            tablaDatCur.setWidthPercentage(100);
            String strDatCur = "";
            PdfPCell cellDatCur = null;
            Phrase fraseDatCur = null;

            String strMinIn = "";
            String strMinFi = "";

            strMinIn = String.valueOf(ediVO.getMinIn()).trim();
            strMinFi = String.valueOf(ediVO.getMinFin()).trim();

            if (strMinIn.length() < 2) strMinIn = "0" + strMinIn;
            if (strMinFi.length() < 2) strMinFi = "0" + strMinFi;

            strDatCur = "                              DATOS DEL CURSO\n\n" +
                        "CURSO               " + curVO.getNomCur() + "\n\n" +
                        "FECHA INICIO        " + new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecIn()) + "        FECHA FIN   " + new SimpleDateFormat("dd/MM/yyyy").format(ediVO.getFecFi()) + "\n\n" +
                        "HORARIO             " + ediVO.getHorIn() + ":" + strMinIn +  " a " + ediVO.getHorFi() + ":" + strMinFi + "  Días Semana ";

            strDatCur = strDatCur + ediVO.devuelveDiasClase();

            PdfPTable tablaDatEco = new PdfPTable(1);
            tablaDatEco.setWidthPercentage(100);
            String strDatEco = "";
            PdfPCell cellDatEco = null;
            Phrase fraseDatEco = null;


            if (ediVO.getPrecioT() == 0)
            {
                strDatEco = "                              DATOS ECONOMICOS\n\n" +
                            "PRECIO MATRICULA     " + ediVO.getPrecioM() + " Euros\n\n" +
                            "PRECIO MES           " + ediVO.getPrecioR() + " Euros    Nº MESES        " + getMonthsDifference(ediVO.getFecFi() , ediVO.getFecIn()) + "\n\n" +
                            "FORMA DE PAGO        " + formaPago;
            }
            else
            {
                strDatEco = "                              DATOS ECONOMICOS\n\n" +
                            "PRECIO MATRICULA     " + ediVO.getPrecioM() + " Euros   PRECIO CURSO    " + ediVO.getPrecioT() + "\n\n" +
                            "PRECIO MES           " + ediVO.getPrecioR() + " Euros    Nº MESES        " + getMonthsDifference(ediVO.getFecFi() , ediVO.getFecIn()) + "\n\n" +
                            "FORMA DE PAGO        " + formaPago;

            }

            if (ediVO.isPlazos())
            {
                String meses =  "";
                if (ediVO.isEne()) meses = meses + "Ene ";
                if (ediVO.isFeb()) meses = meses + "Feb ";
                if (ediVO.isMar()) meses = meses + "Mar ";
                if (ediVO.isAbr()) meses = meses + "Abr ";
                if (ediVO.isMay()) meses = meses + "May ";
                if (ediVO.isJun()) meses = meses + "Jun ";
                if (ediVO.isJul()) meses = meses + "Jul ";
                if (ediVO.isAgo()) meses = meses + "Ago ";
                if (ediVO.isSep()) meses = meses + "Sep ";
                if (ediVO.isOct()) meses = meses + "Oct ";
                if (ediVO.isNov()) meses = meses + "Nov ";
                if (ediVO.isDic()) meses = meses + "Dic ";

                strDatEco = "                              DATOS ECONOMICOS\n\n" +
                            "PRECIO MATRICULA     " + ediVO.getPrecioM() + " Euros\n\n" +
                            "PRECIO MES           " + ediVO.getPrecioR() + " Euros    PLAZOS  " + meses + "\n\n" +
                            "FORMA DE PAGO        " + formaPago;
            }


            fraseDatAlu = new Phrase(strDatAlu, new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "JAi.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

            cellDatAlu = new PdfPCell(fraseDatAlu);
            tablaDatAlu.addCell(cellDatAlu);

            fraseDatCur = new Phrase(strDatCur, new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "JAi.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

            cellDatCur = new PdfPCell(fraseDatCur);
            tablaDatCur.addCell(cellDatCur);

            fraseDatEco = new Phrase(strDatEco, new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "JAi.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

            cellDatEco = new PdfPCell(fraseDatEco);
            tablaDatEco.addCell(cellDatEco);


            //Datos alumnos
            Paragraph par01 = new Paragraph("      MATRICULA               FECHA:           " + new SimpleDateFormat("dd/MM/yyy").format(alEdiVO.getFecAlta()), new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "JAi.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
            Paragraph par02 = new Paragraph("                              Nº Matrícula:    " + aluVO.getIdAlu() + "/" + ediVO.getIdEdi(), new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "JAi.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

            //Datos Curso



            //Datos firma
            Paragraph par18 = new Paragraph("El alumno                                               El Centro" , new Font(BaseFont.createFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));


            Paragraph parrafoFinal = new Paragraph("El afectado queda informado de que los datos recogidos, conforme  a lo previsto en la Ley Orgánica " +
                                                   "15/1999 de Protección de Datos de Carácter personal y del Real Decreto 1720/2007 del Reglamento " +
                                                   "de desarrollo de la LOPD, serán incluidos en un fichero denominado CLIENTES inscrito en el Registro " +
                                                   "General de Protección de Datos, cuyo responsable es " + InformacionConf.nombEmp + " y cuya finalidad es " +
                                                   "la formación, información y fidelización de los usuarios.\n" +
                                                   "Asimismo, mediante la presente cláusula, solicitamos su consentimiento expreso para la cesión de sus " +
                                                   "datos a entidades de grupo y asociadas, con la única finalidad de proporcionar información sobre " +
                                                   "distintas actividades formativas. Asimismo , el afectado presta su pleno consentimiento a " + InformacionConf.nombEmp + " " +
                                                   "para que pueda proceder al envío al afectado de comunicaciones comerciales, publicitarias y promocionales por correo electrónico, " +
                                                   "fax o por otros medios de comunicación electrónica equivalentes. No obstante, le informamos que podrá revocar el consentimiento, en cada comunicado comercial o publicitario " +
                                                   "que se le haga llegar.\n\n",new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));


            Chunk cuadro = new Chunk("0", new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "WINGDNG2.TTF"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
            Chunk cesDat1 = new Chunk(" Autorizo la Cesión de mis datos                    ", new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
            Chunk cesDat2 = new Chunk(" NO autorizo la Cesión de mis datos\n ", new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));

            Chunk cesCom1 = new Chunk(" Autorizo las comunicaciones comerciales            ", new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
            Chunk cesCom2 = new Chunk(" NO autorizo las comunicaciones comerciales ", new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));



            Phrase   fraseCons1 = new Phrase();
            fraseCons1.add("          ");
            fraseCons1.add(cuadro);
            fraseCons1.add(cesDat1);
            fraseCons1.add(cuadro);
            fraseCons1.add(cesDat2);



            Phrase   fraseCons2 = new Phrase();
            fraseCons2.add("        ");
            fraseCons2.add(cuadro);
            fraseCons2.add(cesCom1);
            fraseCons2.add(cuadro);
            fraseCons2.add(cesCom2);

            Paragraph parrafoFinal2 = new Paragraph("En cualquier momento e igualmente, podrá ejercitar sus derechos de acceso, rectificación, cancelación y oposición dirigiéndose a " +
                                                    InformacionConf.dirEmp + " o a través de correo electrónico a " +InformacionConf.mailInfo + " junto con una " +
                                                   "prueba válida en derecho, como fotocopia del D.N.I. e indicando en el asunto \"PROTECCIÓN DE DATOS\".\n",new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));


            //Paragraph parFinal = new Paragraph();
            //parFinal.Add(parFinal);


            par01.font().setSize(10);
            par02.font().setSize(10);


            par18.font().setSize(10);

            fraseCons1.font().setSize(7);
            fraseCons2.font().setSize(7);
            parrafoFinal.font().setSize(7);
            parrafoFinal2.font().setSize(7);
            cuadro.font().setSize(14);
            cesDat1.font().setSize(7);
            cesDat2.font().setSize(7);
            cesCom1.font().setSize(7);
            cesCom2.font().setSize(7);

            parrafoFinal.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafoFinal2.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafoFinal.setIndentationLeft(10);
            parrafoFinal.setIndentationRight(10);

            parrafoFinal2.setIndentationLeft(10);
            parrafoFinal2.setIndentationRight(10);

            // step 3
            document.open();


            // step 4

            //Mostramos cabecera
            //logoImage.Alignment = iTextSharp.text.Image.ALIGN_LEFT;

            document.add(parIma);
            document.add(par01);
            document.add(par02);
            document.add(new Paragraph(" "));

            document.add(tablaDatAlu);
            document.add(new Paragraph(" "));
            document.add(tablaDatCur);
            document.add(new Paragraph(" "));
            document.add(tablaDatEco);

            //document.Add(new Paragraph(" "));
            document.add(par18);
            //document.Add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(parrafoFinal);
            document.add(parrafoFinal2);
            document.add(new Paragraph(" "));
            document.add(fraseCons1);
            //document.Add(new Paragraph(" "));
            document.add(fraseCons2);

            //DateTime diaHoy = DateTime.Now;

            //document.Add(new Paragraph("Salamanca a " + Convert.ToString(diaHoy.Day) + " de " + devuelveMes(diaHoy.Month) + " de " + Convert.ToString(diaHoy.Year), new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));

            Font fuenteCond = new Font();

            fuenteCond.setSize(9);

            document.newPage();

            document.add(new Paragraph("     "));
            document.add(new Paragraph("     "));
            document.add(new Paragraph("     "));
            document.add(new Paragraph("     "));
            document.add(new Paragraph("     "));
            document.add(new Paragraph("     "));
            document.add(new Paragraph("     "));
            document.add(new Paragraph("        1. " + InformacionConf.nombEmp + ", es un centro de formación legalmente establecido.", fuenteCond));
            //document.Add(new Paragraph("     "));
            document.add(new Paragraph("        2. El director de " + InformacionConf.nombEmp + " es D. " + InformacionConf.director , fuenteCond));
            //document.Add(new Paragraph("     "));
            document.add(new Paragraph("        3. La dirección de " + InformacionConf.nombEmp + " se encarga de Planificar y Desarrollar los programas de estudios.", fuenteCond));
            //document.Add(new Paragraph("     "));
            document.add(new Paragraph("        4. La dirección de " + InformacionConf.nombEmp + " queda facultada para la modificación de los cursos en función de las necesidades de los alumnos o de " + InformacionConf.nombEmp + ".", fuenteCond));
            //document.Add(new Paragraph("     "));
            document.add(new Paragraph("        5. El alumno se compromete a realizar cuantos pagos haya acordado con " + InformacionConf.nombEmp + ", dicho compromiso queda reflejado en el documento de Matrícula.", fuenteCond));
            //document.Add(new Paragraph("     "));
            document.add(new Paragraph("        6. El alumno realizará el pago de los recibos antes de los 5 días siguientes a la emisión del mismo. ", fuenteCond));
            //document.Add(new Paragraph("     "));
            document.add(new Paragraph("        7. No se recuperará ninguna clase a la que el alumno no asista, ni podrá exigir reducción alguna en la correspondiente mensualidad. Asi mismo no se devolverá cantidad alguna satisfecha por el alumno bajo ningún concepto.", fuenteCond));
            //document.Add(new Paragraph("     "));
            document.add(new Paragraph("        8. El alumno realizará el curso en las fechas, días y horario pactados en la presente matrícula.", fuenteCond));
            //document.Add(new Paragraph("     "));
            document.add(new Paragraph("        9." + InformacionConf.nombEmp + ", se reserva el derecho a dar de BAJA como alumno, a aquel que no abone sus cuotas en los plazos establecidos, dificulte el desarrollo de su formación o realice cualquier acto de ofensa a otros alumnos o personal del centro, así como si realiza actos que deterioren las instalaciones del Centro.", fuenteCond));
            //document.Add(new Paragraph("     "));
            document.add(new Paragraph("       10." + InformacionConf.nombEmp + " entregará al alumno que finaliza correctamente el curso un DIPLOMA que acredite los conocimientos adquiridos por el alumno. Dicho diploma carece de validez académica.", fuenteCond));


        }
        catch (Exception ex)
        {
            ex.getClass();
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
        return "Short description";
    }// </editor-fold>


    private int getMonthsDifference(Date dateFi, Date dateIn)
    {
        //int m1 = date1.getYear() * 12 + (date1.getMonth()+1);
        //int m2 = date2.getYear() * 12 + (date2.getMonth()+1);

        int m1 = Integer.parseInt(new SimpleDateFormat("yyyy").format(dateFi)) * 12 + (Integer.parseInt(new SimpleDateFormat("MM").format(dateFi)) + 1);
        int m2 = Integer.parseInt(new SimpleDateFormat("yyyy").format(dateIn)) * 12 + (Integer.parseInt(new SimpleDateFormat("MM").format(dateIn)) + 1);

        return m1 - m2 ;
    }

}
