/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package es.jahernandez.servlets.impresos;

import java.io.IOException;
import java.util.Vector;

import org.apache.log4j.Logger;

//Paquetes de manejo de pdf
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import es.jahernandez.datos.AlumnosVO;
import es.jahernandez.datos.ConUsuVO;
import es.jahernandez.datos.CursosAluVO;
import es.jahernandez.datos.CursosVO;
import es.jahernandez.datos.EmpresasVO;
import es.jahernandez.datos.InformacionConf;
import es.jahernandez.datos.NivelesVO;
import es.jahernandez.gestion.AlumnosGestion;
import es.jahernandez.gestion.CursosGestion;
import es.jahernandez.gestion.EmpresasGestion;
import es.jahernandez.gestion.NivelesGestion;
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
public class ImpHistMatServlet extends HttpServlet
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
        HttpSession      sesion = request.getSession();
        ServletContext   sc     = null;

        Vector     alumnosBusqueda = new Vector();
        AlumnosVO  aluVO           = null;
        CursosVO   curVO           = null;
        NivelesVO  nivVO           = null;
        CursosVO   datCur          = null;
        EmpresasVO empVO           = null;

        String     strNombre       = "";
        String     strApe1         = "";
        String     strMov          = "";
        String     strFij          = "";
        String     strPob          = "";
        String     strMail         = "";
        String     strEmp          = "";
        String     ND              = "";

        String     auxNomCur       = "";

        PdfPTable tablaDatos       = new PdfPTable(1);
        PdfPCell  celda            = new PdfPCell();
        Font      fuenteDoc        = null;

        int cuentaLineas           = 0;  //Cuenta lineas impresas para saber cuando hacer el salto de página


        Logger      log      = null;
        ConUsuVO    conUsoVO = null;

        //Cargamos atributos de log
        if(sesion.getAttribute("logControl") != null && sesion.getAttribute("usuario") != null)
        {
            log = (Logger) sesion.getAttribute("logControl");
            conUsoVO = (ConUsuVO) sesion.getAttribute("usuario");

            log.info((conUsoVO.getUsuario() + "               " ).substring(0,10) + "Imprimir histÃ³rico matrículas" );

        }


        tablaDatos.setWidthPercentage(100);
        tablaDatos.setSpacingBefore(10);



        alumnosBusqueda   = (Vector) sesion.getAttribute("busAluC");
        curVO             = CursosGestion.devolverDatosCurso( ((CursosAluVO) alumnosBusqueda.elementAt(0)).getIdCur());

        // step 1
        Document document  = new Document(PageSize.A4.rotate(), 16, 16, 16, 16);
        Image    logoImage = null;

        try
        {
            sc = getServletContext();
            fuenteDoc =new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
            fuenteDoc.setSize(8);

            logoImage = Image.getInstance(sc.getRealPath("/" + "imagenes" + "/" + InformacionConf.logo));
            logoImage.scaleAbsolute(150, 38);


            // step 2: we set the ContentType and create an instance of the Writer
            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

            // step 3
            document.open();

            // step 4

            //Mostramos cabecera
            logoImage.setAlignment(Element.ALIGN_LEFT);

            document.add(new Paragraph(""));
            document.add(logoImage);
            document.add(new Paragraph(""));
            document.add(new Paragraph(""));

            document.add(new Paragraph("HISTÓRICO DE MATRÍCULAS", new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));
            document.add(new Paragraph(""));

            //document.Add(new Paragraph("Tipo de Curso: " +TipoCursoGestion.devuelveNombreTipo(curVO.TipCur),new Font(BaseFont.CreateFont("c:\\windows\\fonts\\cour.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));

            celda.addElement(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO                 MAIL                 EMPRESA                     ND", fuenteDoc));
            tablaDatos.addCell(celda);

            //document.Add(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO                 MAIL                 EMPRESA                     ND", fuenteDoc));
            //document.Add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------------------------------",fuenteDoc));

            for (int ind = 0; ind < alumnosBusqueda.size(); ind++)
            {
                aluVO  = AlumnosGestion.devolverDatosAlumno(((CursosAluVO) alumnosBusqueda.elementAt(ind)).getIdAlu());
                nivVO  = NivelesGestion.devolverDatosNivel(((CursosAluVO) alumnosBusqueda.elementAt(ind)).getIdNiv());
                datCur = CursosGestion.devolverDatosCurso(((CursosAluVO) alumnosBusqueda.elementAt(ind)).getIdCur());

                strNombre = aluVO.getNombre().trim();
                strApe1 = aluVO.getAp1Alu().trim();
                strPob = aluVO.getLocalAlu().trim();
                strMov = aluVO.getMovAlu().trim();
                strFij = aluVO.getFijAlu().trim();
                empVO = EmpresasGestion.devolverDatEmp(aluVO.getEmpAlu());
                strMail = aluVO.getEmail().trim();

                if (empVO != null)
                {
                    strEmp = empVO.getNombreEmpresa();
                }
                else
                {
                    strEmp = "                                           ";
                }

                if (aluVO.isAlND())
                {
                    ND = "X";
                }
                else
                {
                    ND = " ";
                }


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

                    document.add(new Paragraph(""));
                    document.add(new Paragraph(""));

                    document.add(new Paragraph("HISTÓRICO DE MATRÍCULAS", new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));

                    document.add(new Paragraph(""));

                    celda = new PdfPCell();
                    celda.setMinimumHeight(20);

                    celda.addElement(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO                 MAIL                 EMPRESA                     ND", fuenteDoc));

                    tablaDatos.addCell(celda);


                    //document.Add(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO                 MAIL                 EMPRESA                     ND", fuenteDoc));
                    //document.Add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------------------------------------------------", fuenteDoc));
                }


                strNombre = strNombre + "                                  ";

                strApe1 = strApe1 + "                                  ";

                strPob = strPob + "                                  ";

                strMov = strMov + "                                  ";

                strFij = strFij + "                                  ";

                strEmp = strEmp + "                                  ";

                strMail = strMail + "                                      ";

                strNombre = strNombre.substring(0, 15);
                strApe1 = strApe1.substring(0, 25);
                strPob = strPob.substring(0, 30);
                strMov = strMov.substring(0, 9);
                strFij = strFij.substring(0, 9);
                strEmp = strEmp.substring(0, 25);
                strMail = strMail.substring(0, 35);



                if (! datCur.getNomCur().equals(auxNomCur))
                {
                    cuentaLineas++;
                    if (cuentaLineas > 21)
                    {
                        cuentaLineas = 0;


                        //Hacemos salto de pÃ¡gina
                        document.add(tablaDatos);
                        document.newPage();

                        //Reiniciamos la tabla
                        tablaDatos = new PdfPTable(1);
                        tablaDatos.setWidthPercentage(100);
                        tablaDatos.setSpacingBefore(10);


                        //Mostramos cabecera
                        document.add(logoImage);

                        document.add(new Paragraph(""));
                        document.add(new Paragraph(""));

                        document.add(new Paragraph("HISTÓRICO DE MATRÍCULAS", new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED))));

                        document.add(new Paragraph(""));

                        celda = new PdfPCell();
                        celda.setMinimumHeight(20);

                        celda.addElement(new Paragraph("       NOMBRE                 APELLIDOS          POBLACION                       MOVIL       FIJO                 MAIL                 EMPRESA                     ND", fuenteDoc));

                        tablaDatos.addCell(celda);
                    }

                    auxNomCur = datCur.getNomCur();
                    Paragraph parNiv = new Paragraph(datCur.getNomCur().trim(), new Font(BaseFont.createFont(sc.getRealPath("/" + "fonts" + "/" + "cour.ttf"), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)));
                    parNiv.setAlignment(Element.ALIGN_CENTER);
                    celda = new PdfPCell();
                    celda.setMinimumHeight(20);
                    celda.addElement(parNiv);
                    tablaDatos.addCell(celda);

                }

                celda = new PdfPCell();
                celda.setMinimumHeight(20);
                celda.addElement(new Paragraph("  " + strNombre + "    " + strApe1 + " " + strPob + " " + strMov + "   " + strFij + "  " + strMail + "  " + strEmp + " " + ND, fuenteDoc));
                tablaDatos.addCell(celda);

                cuentaLineas++;
                if (alumnosBusqueda.size() > 0 && alumnosBusqueda.size() <= 21)
                {
                    document.add(tablaDatos);
                }
            }
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
        return "Imprimir historico matriculas servlet";
    }// </editor-fold>
}
