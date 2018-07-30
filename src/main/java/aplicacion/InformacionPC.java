/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.CellFormat;
import com.google.api.services.sheets.v4.model.DeleteDimensionRequest;
import com.google.api.services.sheets.v4.model.DeleteRangeRequest;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.GridRange;
import com.google.api.services.sheets.v4.model.NumberFormat;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ngarcia
 */
public class InformacionPC {

    private static final String APPLICATION_NAME = "inventario Capital";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FOLDER = "credentials"; // Directory to store user credentials
    private static final String spreadsheetId = "1RNKrOzttbIwK0gjnJRqc0aoQ7jGjC23SMoro1t0kZ-I";
    public static final int OFICINA = 0;
    public static final int OBRA = 1;

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved credentials/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CLIENT_SECRET_DIR = "/client_secret.json";

    public static final int laptop = 1;
    public static final int desktop = 0;
    public static final int allInOne = 2;
    private final String[] tiposRam = {"Unknown", "Other", "DRAM", "Synchronous DRAM", "Cache DRAM", "EDO", "EDRAM", "VRAM", "SRAM", "RAM", "ROM", "Flash", "EEPROM", "FEPROM", "EPROM", "CDRAM", "CDRAM", "3DRAM", "SDRAM", "SGRAM", "RDRAMRDRAM", "DDR", "DDR2", "DDR2 FB-DIMM", "DDR3", "FBD2"};
    private int filaDocumento;
    private List<List<Object>> filaTitulosInventarioOficina;
    private List<List<Object>> filaTitulosInventarioObra;
    private List<List<Object>> filaTitulosConfiguracion;
    private String filePath;
    private String nombrePC;
    private String nombreAnteriorPC;
    private String numeroFactura;
    private String personaAsignada;
    private String tipoDeEquipo;
    private String lugar;
    private String area;
    private int tipoInventario;
    private String placaPC;
    private String marcaPC;
    private String modeloPC;
    private String serialPC;
    private String userAdmin;
    private String Grupo;
    private String Dominio;
    private String marcaPantalla;
    private String modeloPantalla;
    private String serialPantalla;
    private String ip;
    private String procesador;
    private String ram;
    private String placaBase;
    private String tarjetaGrafica;
    private String discoDuro;
    private String unidadCD;
    private String antivirus;
    private String distribucionOffice;
    private String versionOffice;
    private String licenciaOffice;
    private String officeActivo;
    private String officeInstalado;
    private String distribucionWindows;
    private String idWindows;
    private String licenciaWindows;
    private String licenciaWindowsBIOS;
    private String coaWindows;
    private String distribucionProject;
    private String versionProject;
    private String licenciaProject;
    private String distribucionAutocad;
    private String versionAutocad;
    private String licenciaAutocad;
    private String licenciaOtro;
    private int filaActualizacion;
    private int inventarioActualizacion;
    private ArrayList<String> lugaresOficina = new ArrayList<String>();
    private ArrayList<String> lugaresObra = new ArrayList<String>();
    private ArrayList<String> areasOficina = new ArrayList<String>();
    private ArrayList<String> listaDistribucionOffice = new ArrayList<String>();
    private ArrayList<String> listaVersionOffice = new ArrayList<String>();
    private ArrayList<String> listaLicenciasOffice = new ArrayList<String>();
    private ArrayList<String> listaDistribucionWindows = new ArrayList<String>();
    private ArrayList<String> listaDistribucionProject = new ArrayList<String>();
    private ArrayList<String> listaVersionProject = new ArrayList<String>();
    private ArrayList<String> listaLicenciasProject = new ArrayList<String>();
    private ArrayList<String> listaVersionAutocad = new ArrayList<String>();
    private ArrayList<String> listaDistribucionAutocad = new ArrayList<String>();
    private ArrayList<String> listaLicenciasAutocad = new ArrayList<String>();
    private ArrayList<String> seriales = new ArrayList<String>();
    private Sheets service;
    private String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
        "Y", "Z", "AA", "AB", "AC", "AD", "AE", "AF", "AG", "AH", "AI", "AJ", "AK", "AL", "AM", "AN", "AO", "AP", "AQ", "AR", "AS"};

    String[] titulosOficina = {"N FACTURA", "TIPO EQUIPO", "NOMBRE ANTERIOR", "NOMBRE ACTUAL EQUIPO", "ASIGNADO A", "LUGAR", "AREA", "MARCA", "MODELO", "SERIAL", "PLACA",
        "USER ADMIN", "GPO", "USER DOM", "P.MARCA", "P.MODELO", "P.SERIAL", "IP", "PROCESADOR", "RAM", "MOTHERBOARD", "TJ GRAFICA", "DISCO DURO", "UNIDAD CD/DVD",
        "ANTIVIRUS", "DISTRIBUCION OFFICE", "VERSIÓN OFFICE", "LICENCIA OFFICE", "ACTIVO", "instalada", "DISTRUBUCION WINDOWS", "ID PRODUCTO", "LICENCIA WINDOWS", "LICENCIA (BIOS)",
        "COA", "DISTRIBUCION PROJECT", "VERSIÓN PROJECT", "LICENCIA PROJECT", "DISTRIBUCION AUTOCAD", "VERSIÓN AUTOCAD", "LICENCIA AUTOCAD", "LICENCIA OTRO"};
    String[] titulosObra = {"TIPO EQUIPO", "NOMBRE ANTERIOR", "NOMBRE ACTUAL EQUIPO", "ASIGNADO A", "LUGAR", "MARCA", "MODELO", "SERIAL", "PLACA",
        "USER ADMIN", "GPO", "USER DOM", "P.MARCA", "P.MODELO", "P.SERIAL", "IP", "PROCESADOR", "RAM", "MOTHERBOARD", "TJ GRAFICA", "DISCO DURO", "UNIDAD CD/DVD",
        "ANTIVIRUS", "DISTRIBUCION OFFICE", "VERSIÓN OFFICE", "LICENCIA OFFICE", "DISTRUBUCION WINDOWS", "ID PRODUCTO", "LICENCIA WINDOWS", "LICENCIA (BIOS)",
        "COA"};

    public InformacionPC() {
        resetearCampos();
    }

    public void resetearCampos() {
        tipoDeEquipo = "DESKTOP";
        nombreAnteriorPC = "";
        nombrePC = "";
        numeroFactura = "";
        personaAsignada = "";
        lugar = "N/A";
        area = "N/A";
        placaPC = "";
        marcaPC = "";
        modeloPC = "";
        serialPC = "";
        userAdmin = "";
        Grupo = "";
        Dominio = "";
        ip = "";
        procesador = "";
        ram = "";
        placaBase = "";
        tarjetaGrafica = "";
        discoDuro = "";
        unidadCD = "";
        antivirus = "BITDEFENDER";
        distribucionOffice = "N/A";
        versionOffice = "N/A";
        licenciaOffice = "N/A";
        officeActivo = "SI";
        officeInstalado = "SI";
        distribucionWindows = "N/A";
        idWindows = "";
        licenciaWindows = "";
        licenciaWindowsBIOS = "";
        coaWindows = "";
        distribucionProject = "N/A";
        versionProject = "N/A";
        licenciaProject = "N/A";
        distribucionAutocad = "N/A";
        versionAutocad = "N/A";
        licenciaAutocad = "N/A";
        licenciaOtro = "N/A";

    }

    public ArrayList<ArrayList<String>> buscarPCEnInventarioOficina() throws ExcepcionInventario {
        try {
            ArrayList<ArrayList<String>> computadoresConMismoSerial = new ArrayList<ArrayList<String>>();
            String serial = buscarSerialPC();
            ValueRange respuesta1 = service.spreadsheets().values()
                    .get(spreadsheetId, "Oficina!B4:AW4")
                    .execute();
            filaTitulosInventarioOficina = respuesta1.getValues();
            int columna = buscarColumna("SERIAL", filaTitulosInventarioOficina) + 1;
            ValueRange respuesta2 = service.spreadsheets().values()
                    .get(spreadsheetId, "Oficina!" + abc[columna - 1] + ":" + abc[columna - 1])
                    .execute();
            seriales = crearLista(respuesta2.getValues());
            for (int i = 0; i < seriales.size(); i++) {

                if (seriales.get(i).trim().equals(serial.trim())) {
                    computadoresConMismoSerial.add(new ArrayList<String>());
                    computadoresConMismoSerial.get(computadoresConMismoSerial.size() - 1).add(Integer.toString(i + 3));
                    computadoresConMismoSerial.get(computadoresConMismoSerial.size() - 1).add("OFICINA - " + datosComputadorConMismoSerial(i + 3, OFICINA));
                }
            }

            return buscarPCEnInventarioObra(computadoresConMismoSerial, serial);
        } catch (IOException ex) {
            throw new ExcepcionInventario("Error al intentar buscar PCs con el mismo rerial en el inventario de oficina: " + ex.getMessage());
        }
    }

    public ArrayList<ArrayList<String>> buscarPCEnInventarioObra(ArrayList<ArrayList<String>> pcs, String serial) throws ExcepcionInventario {
        try {
            ValueRange respuesta1 = service.spreadsheets().values()
                    .get(spreadsheetId, "salas-obras!B4:AW4")
                    .execute();
            filaTitulosInventarioObra = respuesta1.getValues();
            int columna = buscarColumna("SERIAL", filaTitulosInventarioObra) + 1;
            ValueRange respuesta2 = service.spreadsheets().values()
                    .get(spreadsheetId, "salas-obras!" + abc[columna - 1] + ":" + abc[columna - 1])
                    .execute();
            seriales = crearLista(respuesta2.getValues());
            for (int i = 0; i < seriales.size(); i++) {
                if (seriales.get(i).trim().equals(serial.trim())) {
                    pcs.add(new ArrayList<String>());
                    pcs.get(pcs.size() - 1).add(Integer.toString(i + 4));
                    pcs.get(pcs.size() - 1).add("OBRA - " + datosComputadorConMismoSerial(i + 4, OBRA));
                }
            }

            return pcs;
        } catch (IOException ex) {
            throw new ExcepcionInventario("Error al intentar buscar PCs con el mismo rerial en el inventario de obra: " + ex.getMessage());
        }
    }

    private String datosComputadorConMismoSerial(int fila, int ind) throws ExcepcionInventario {
        String a = "";
        String sheet;
        List<List<Object>> filaTitulos;
        if (ind == OBRA) {
            filaTitulos = filaTitulosInventarioObra;
            sheet = "salas-obras";
        } else {
            filaTitulos = filaTitulosInventarioOficina;
            sheet = "Oficina";
        }
        try {
            int columnaInicial = buscarColumna("TIPO EQUIPO", filaTitulos) + 1;
            int columnaFinal = buscarColumna("ASIGNADO A", filaTitulos) + 1;
            ValueRange respuesta = service.spreadsheets().values()
                    .get(spreadsheetId, sheet + "!" + abc[columnaInicial - 1] + (fila) + ":" + abc[columnaFinal - 1] + (fila))
                    .execute();
            List<List<Object>> datos = respuesta.getValues();
            int fila1 = 0;
            int columna1 = 0;

            while (fila1 < datos.size()) {
                while (columna1 < datos.get(fila1).size()) {
                    if (!datos.get(fila1).isEmpty() && !datos.get(fila1).get(columna1).toString().trim().equals("")) {
                        a = a + datos.get(fila1).get(columna1) + " - ";
                    }
                    columna1++;
                }
                fila1++;
            }

        } catch (IOException ex) {
            throw new ExcepcionInventario("Error al intetar obtener datos de computadores con el mismo serial");
        }

        return a.substring(0, a.length() - 3);
    }

    public ArrayList<ArrayList<String>> definirTipoInventario(int ind) throws ExcepcionInventario {
        ArrayList<ArrayList<String>> computadoresConSerialesiguales = new ArrayList<ArrayList<String>>();
        tipoInventario = ind;
        try {

            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            ValueRange respuesta = service.spreadsheets().values()
                    .get(spreadsheetId, "Opciones Programa!A2:K2")
                    .execute();
            filaTitulosConfiguracion = respuesta.getValues();
            buscarDatosInventario();
            if (tipoInventario == OFICINA) {
                ValueRange respuesta1;
                respuesta1 = service.spreadsheets().values().get(spreadsheetId, "Oficina!B5:C").execute();
                filaDocumento = respuesta1.getValues().size() + 4;
                System.out.println(filaDocumento);

            } else if (tipoInventario == OBRA) {
                ValueRange respuesta2;
                respuesta2 = service.spreadsheets().values().get(spreadsheetId, "salas-obras!B5:C").execute();
                filaDocumento = respuesta2.getValues().size() + 4;
            }
            computadoresConSerialesiguales = buscarPCEnInventarioOficina();
            ArrayList<String> a = new ArrayList<String>();
            a.add(Integer.toString(filaDocumento));
            a.add("Agregar como nuevo computador.");
            computadoresConSerialesiguales.add(a);

        } catch (IOException | GeneralSecurityException ex) {
            throw new ExcepcionInventario("Error al establecer conexion con google sheets");
        }
        return computadoresConSerialesiguales;
    }

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If there is no client_secret.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = InformacionPC.class.getResourceAsStream(CLIENT_SECRET_DIR);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public void guardarDatos() throws ExcepcionInventario {

        try {
            if (tipoInventario == OBRA) {

                String[] datosPC = {tipoDeEquipo, nombreAnteriorPC, nombrePC, personaAsignada, lugar, marcaPC, modeloPC, serialPC, placaPC, userAdmin, Grupo, Dominio, marcaPantalla, modeloPantalla,
                    serialPantalla, ip, procesador, ram, placaBase, tarjetaGrafica, discoDuro, unidadCD, antivirus, distribucionOffice, versionOffice, licenciaOffice, distribucionWindows,
                    idWindows, licenciaWindows, licenciaWindowsBIOS, coaWindows};
                if(inventarioActualizacion==OBRA){
                    filaDocumento=filaActualizacion;
                }else{
                    borrarFila(filaActualizacion+1,1561897843);
                    filaDocumento=filaDocumento;
                }
                    guardarFecha();

                for (int i = 0; i < titulosObra.length; i++) {
                    guardarDato(titulosObra[i], datosPC[i], filaTitulosInventarioObra);
                }
            } else if(tipoInventario == OFICINA){
                String[] datosPC1 = {numeroFactura, tipoDeEquipo, nombreAnteriorPC, nombrePC, personaAsignada, lugar, area, marcaPC, modeloPC, serialPC, placaPC, userAdmin, Grupo, Dominio, marcaPantalla, modeloPantalla,
                    serialPantalla, ip, procesador, ram, placaBase, tarjetaGrafica, discoDuro, unidadCD, antivirus, distribucionOffice, versionOffice, licenciaOffice, officeActivo, officeInstalado, distribucionWindows,
                    idWindows, licenciaWindows, licenciaWindowsBIOS, coaWindows, distribucionProject, versionProject, licenciaProject, distribucionAutocad, versionAutocad, licenciaAutocad, licenciaOtro};
                if(inventarioActualizacion==OFICINA){
                    filaDocumento=filaActualizacion;
                }else{
                    borrarFila(filaActualizacion+1,1111344361);
                    filaDocumento=filaDocumento;
                }
                guardarFecha();
                for (int i = 0; i < titulosOficina.length; i++) {
                    guardarDato(titulosOficina[i], datosPC1[i], filaTitulosInventarioOficina);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExcepcionInventario("Error al intentar guardar datos en Google sheets: " + ex.getMessage());
        }

    }

    private void guardarFecha() throws IOException {
        int sheetid;
        if (tipoInventario == OBRA) {
            sheetid = 1111344361;
        } else {
            sheetid = 1561897843;
        }
        List<Request> requests = new ArrayList<>();

        List<CellData> valores = new ArrayList<>();
        Date fecha = new Date();
        String fe = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
        CellData cell = new CellData();
        cell.setUserEnteredValue(new ExtendedValue().setStringValue(fe));
        cell.setUserEnteredFormat(new CellFormat().setNumberFormat(new NumberFormat().setType("DATE")));
        valores.add(cell);
        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(sheetid)
                                .setRowIndex(filaDocumento)
                                .setColumnIndex(1))
                        .setRows(Arrays.asList(
                                new RowData().setValues(valores)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest)
                .execute();

    }

    private void obtenerInformacionAnteriorPC() throws ExcepcionInventario {
        try {
            int columnaInicial;
            int columnaFinal;
            List<List<Object>> datos=null;
            List<List<Object>> filaTitulos=null;
            if (inventarioActualizacion == OFICINA) {
                filaTitulos = filaTitulosInventarioOficina;
                columnaInicial = buscarColumna("FECHA", filaTitulos) + 1;
                columnaFinal = buscarColumna("LICENCIA OTRO", filaTitulos) + 1;
                ValueRange respuesta = service.spreadsheets().values()
                        .get(spreadsheetId, "Oficina!" + abc[columnaInicial - 1] + (filaActualizacion + 1) + ":" + abc[columnaFinal] + (filaActualizacion + 1))
                        .execute();
                datos = respuesta.getValues();
                numeroFactura = (String) datos.get(0).get(buscarColumna("N FACTURA", filaTitulos));
                area = (String) datos.get(0).get(buscarColumna("AREA", filaTitulos));
                officeActivo = (String) datos.get(0).get(buscarColumna("ACTIVO", filaTitulos));
                officeInstalado = (String) datos.get(0).get(buscarColumna("instalada", filaTitulos));
                distribucionProject = (String) datos.get(0).get(buscarColumna("DISTRIBUCION PROJECT", filaTitulos));
                versionProject = (String) datos.get(0).get(buscarColumna("VERSIÓN PROJECT", filaTitulos));
                licenciaProject = (String) datos.get(0).get(buscarColumna("LICENCIA PROJECT", filaTitulos));
                distribucionAutocad = (String) datos.get(0).get(buscarColumna("DISTRIBUCION AUTOCAD", filaTitulos));
                versionAutocad = (String) datos.get(0).get(buscarColumna("VERSIÓN AUTOCAD", filaTitulos));
                licenciaAutocad = (String) datos.get(0).get(buscarColumna("LICENCIA AUTOCAD", filaTitulos));
                licenciaOtro = (String) datos.get(0).get(buscarColumna("LICENCIA OTRO", filaTitulos));
            }else if(inventarioActualizacion == OBRA){
                filaTitulos = filaTitulosInventarioObra;
                columnaInicial = buscarColumna("FECHA", filaTitulos) + 1;
                columnaFinal = buscarColumna("COA", filaTitulos) + 1;
                ValueRange respuesta = service.spreadsheets().values()
                        .get(spreadsheetId, "salas-obras!" + abc[columnaInicial - 1] + (filaActualizacion + 1) + ":" + abc[columnaFinal] + (filaActualizacion + 1))
                        .execute();
                datos = respuesta.getValues();
            }
            tipoDeEquipo = (String) datos.get(0).get(buscarColumna("TIPO EQUIPO", filaTitulos));
            personaAsignada = (String) datos.get(0).get(buscarColumna("ASIGNADO A", filaTitulos));
            lugar = (String) datos.get(0).get(buscarColumna("LUGAR", filaTitulos));
            placaPC = (String) datos.get(0).get(buscarColumna("PLACA", filaTitulos));
            antivirus = (String) datos.get(0).get(buscarColumna("ANTIVIRUS", filaTitulos));
            distribucionOffice = (String) datos.get(0).get(buscarColumna("DISTRIBUCION OFFICE", filaTitulos));
            versionOffice = (String) datos.get(0).get(buscarColumna("VERSIÓN OFFICE", filaTitulos));
            licenciaOffice = (String) datos.get(0).get(buscarColumna("LICENCIA OFFICE", filaTitulos));
            distribucionWindows = (String) datos.get(0).get(buscarColumna("DISTRUBUCION WINDOWS", filaTitulos));
            idWindows = (String) datos.get(0).get(buscarColumna("ID PRODUCTO", filaTitulos));
            licenciaWindows = (String) datos.get(0).get(buscarColumna("LICENCIA WINDOWS", filaTitulos));
            licenciaWindowsBIOS = (String) datos.get(0).get(buscarColumna("LICENCIA (BIOS)", filaTitulos));
            coaWindows = (String) datos.get(0).get(buscarColumna("COA", filaTitulos));
            nombreAnteriorPC = (String) datos.get(0).get(buscarColumna("NOMBRE ACTUAL EQUIPO", filaTitulos)) + " - " + (String) datos.get(0).get(buscarColumna("ASIGNADO A", filaTitulos));
        } catch (IOException ex) {
            throw new ExcepcionInventario("Error al intentar obtener informacio anterior del PC " + ex.getMessage());
        }

    }

    private void buscarDatosInventario() throws ExcepcionInventario {
        try {
            if (tipoInventario == OBRA) {
                lugaresObra = obtenerColumna(buscarColumna("LUGARES OBRA", filaTitulosConfiguracion));

            } else if (tipoInventario == OFICINA) {
                lugaresOficina = obtenerColumna(buscarColumna("LUGARES OFICINA", filaTitulosConfiguracion));
                areasOficina = obtenerColumna(buscarColumna("AREAS OFICINA", filaTitulosConfiguracion));
                listaVersionProject = obtenerColumna(buscarColumna("VERSION PROJECT", filaTitulosConfiguracion));
                listaDistribucionProject = obtenerColumna(buscarColumna("DISTRIBUCION PROJECT", filaTitulosConfiguracion));
                listaLicenciasProject = obtenerLicencias("Project", "C");
                listaDistribucionAutocad = obtenerColumna(buscarColumna("DISTRIBUCION AUTOCAD", filaTitulosConfiguracion));
                listaVersionAutocad = obtenerColumna(buscarColumna("VERSION AUTOCAD", filaTitulosConfiguracion));
                listaLicenciasAutocad = obtenerLicencias("AutoCad", "E");
            }

            listaDistribucionOffice = obtenerColumna(buscarColumna("DISTRIBUCION OFFICE", filaTitulosConfiguracion));
            listaLicenciasOffice = obtenerLicencias("Office", "D");
            listaLicenciasOffice.add(0, "SIN LICENCIA");
            listaVersionOffice = obtenerColumna(buscarColumna("VERSION OFFICE", filaTitulosConfiguracion));
            listaDistribucionWindows = obtenerColumna(buscarColumna("DISTRIBUCION WINDOWS", filaTitulosConfiguracion));

        } catch (IOException ex) {
            throw new ExcepcionInventario("Error al buscar datos en el apartado Opciones Programa de google sheets.");
        }
    }

    private ArrayList<String> crearLista(List<List<Object>> l) {
        ArrayList<String> a = new ArrayList<String>();
        a.add("N/A");
        for (int columna = 2; columna < l.size(); columna++) {
            if (!l.get(columna).isEmpty()) {
                a.add((String) l.get(columna).get(0));
            }
        }
        return a;
    }

    private ArrayList<String> obtenerLicencias(String hoja, String columna) throws IOException {
        ValueRange respuesta1 = service.spreadsheets().values()
                .get(spreadsheetId, hoja + "!" + columna + ":" + columna)
                .execute();

        return crearLista(respuesta1.getValues());
    }

    private ArrayList<String> obtenerColumna(int columna) throws IOException {
        ValueRange respuesta1 = service.spreadsheets().values()
                .get(spreadsheetId, "Opciones Programa!" + abc[columna - 1] + ":" + abc[columna - 1])
                .execute();

        return crearLista(respuesta1.getValues());
    }

    private int buscarColumna(String s, List<List<Object>> l) {
        int columna = 0;
        for (List fila : l) {
            int cont = 0;
            boolean salida = false;
            while (fila.size() > cont && !salida) {
                if (fila.get(cont).equals(s)) {
                    salida = true;
                    columna = cont + 1;
                }
                cont++;
            }
        }
        return columna;
    }

    private void guardarDato(String c, String p, List<List<Object>> l) throws IOException {
        List<Request> requests = new ArrayList<>();
        List<CellData> valores = new ArrayList<>();
        if (p.isEmpty() || p.trim().equals("")) {
            p = "N/A";
        }
        int sheetid;
        if (tipoInventario == OBRA) {
            sheetid = 1111344361;
        } else {
            sheetid = 1561897843;
        }
        valores.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(p)));
        int col = buscarColumna(c, l);
        if (col != 0) {
            requests.add(new Request()
                    .setUpdateCells(new UpdateCellsRequest()
                            .setStart(new GridCoordinate()
                                    .setSheetId(sheetid)
                                    .setRowIndex(filaDocumento)
                                    .setColumnIndex(col))
                            .setRows(Arrays.asList(
                                    new RowData().setValues(valores)))
                            .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
            BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                    .setRequests(requests);
            service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest)
                    .execute();
            ;
        }
    }

    public void borrarFila(Integer fila,int codHoja) {
        Spreadsheet spreadsheet = null;
        try {
            spreadsheet = service.spreadsheets().get(spreadsheetId).execute();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        BatchUpdateSpreadsheetRequest content = new BatchUpdateSpreadsheetRequest();
        Request request = new Request();
        DeleteDimensionRequest deleteDimensionRequest = new DeleteDimensionRequest();
        DimensionRange dimensionRange = new DimensionRange();
        dimensionRange.setDimension("ROWS");
        dimensionRange.setStartIndex(fila - 1);
        dimensionRange.setEndIndex(fila);

        dimensionRange.setSheetId(codHoja);
        deleteDimensionRequest.setRange(dimensionRange);

        request.setDeleteDimension(deleteDimensionRequest);

        List<Request> requests = new ArrayList<Request>();
        requests.add(request);
        content.setRequests(requests);

        try {
            service.spreadsheets().batchUpdate(spreadsheetId, content).execute();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dimensionRange = null;
            deleteDimensionRequest = null;
            request = null;
            requests = null;
            content = null;
        }
    }

    public void buscarInformacion(int tipoPC, boolean multiplesPantallas) throws ExcepcionInventario {
        if (tipoPC == 0) {
            tipoDeEquipo = "DESKTOP";
        } else if (tipoPC == 1) {
            tipoDeEquipo = "LAPTOP";
        } else if (tipoPC == 2) {
            tipoDeEquipo = "TODO EN UNO";
        }
        nombrePC = buscarNombrePC();
        marcaPC = buscarMarcaPC().toUpperCase();
        modeloPC = buscarModeloPC().toUpperCase();
        serialPC = buscarSerialPC().toUpperCase();
        userAdmin = buscarUsuarioPC().toUpperCase();
        Grupo = buscarGrupoDeTrabajoPC().toUpperCase();
        if (Grupo.equals("N/A")) {
            Dominio = buscarDominioPC().toUpperCase();
        } else {
            Dominio = "N/A";
        }

        if (tipoPC != 1 || multiplesPantallas) {
            for (String s : buscarMarcaPantallasPC()) {
                if (marcaPantalla == null) {
                    marcaPantalla = s.toUpperCase();
                } else {
                    marcaPantalla = marcaPantalla + "\n" + s;
                }
            }
            for (String s : buscarModeloPantallasPC()) {
                if (modeloPantalla == null) {
                    modeloPantalla = s.toUpperCase();
                } else {
                    modeloPantalla = modeloPantalla + "\n" + s;
                }
            }
            for (String s : buscarSerialPantallasPC()) {
                if (serialPantalla == null) {
                    serialPantalla = s.toUpperCase();
                } else {
                    serialPantalla = serialPantalla + "\n" + s;
                }
            }
        } else {
            marcaPantalla = "N/A";
            modeloPantalla = "N/A";
            serialPantalla = "N/A";
        }
        try {
            ip = buscarIP();
        } catch (UnknownHostException ex) {
            throw new ExcepcionInventario("Error al obtener ip del PC.");
        }
        procesador = buscarProcesadorPC().toUpperCase();
        ram = buscarMemoriaRamPC().toUpperCase();
        placaBase = buscarPlacaBasePC().toUpperCase();
        tarjetaGrafica = buscarTarjetaGraficaPC().toUpperCase();
        discoDuro = buscarDiscoDuroPC().toUpperCase();
        unidadCD = buscarUnidadCDPC().toUpperCase();

    }

    public ArrayList<String> ejecutarComandoWMIC(String[] c) throws ExcepcionInventario {
        BufferedReader reader = null;
        ArrayList<String> respuesta = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(c);
            process.getOutputStream().close();
            String s = null;
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((s = reader.readLine()) != null) {
                respuesta.add(s);
            }
            reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = reader.readLine()) != null) {

            }
        } catch (IOException ex) {
            throw new ExcepcionInventario("Error al ejecutar comando mediante WMIC.");
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                throw new ExcepcionInventario("Error al cerrar el stream de lectura.");
            }
        }
        return respuesta;
    }

    private ArrayList<String> ejecutarComandoDirectX(String especificacion, boolean recortar) throws ExcepcionInventario {
        ArrayList<String> respuesta = new ArrayList<String>();
        try {
            if (filePath == null) {
                filePath = new File(System.getProperty("java.io.tmpdir") + "/results.txt").getAbsolutePath();
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "dxdiag", "/t", filePath);
                Process p = pb.start();
                p.waitFor();
            }
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith(especificacion)) {
                    if (recortar) {
                        respuesta.add(line.trim().substring(especificacion.length()).toUpperCase());
                    } else {
                        respuesta.add(line.trim().toUpperCase());
                    }
                }
            }
        } catch (IOException | InterruptedException ex) {
            throw new ExcepcionInventario("Error al ejecutar comando DirectX: " + ex.getMessage());
        }
        return respuesta;
    }

    private String buscarIP() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    private String buscarNombrePC() throws ExcepcionInventario {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET Name"};
        return ejecutarComandoWMIC(comando).get(2);

    }

    private String buscarMarcaPC() throws ExcepcionInventario {
        String[] comando = {"CMD", "/C", "WMIC csproduct GET Vendor"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String buscarModeloPC() throws ExcepcionInventario {
        String[] comando = {"CMD", "/C", "WMIC CSPRODUCT GET Name"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String buscarSerialPC() throws ExcepcionInventario {
        String[] comando = {"CMD", "/C", "WMIC BIOS GET SerialNumber"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String buscarUsuarioPC() throws ExcepcionInventario {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET UserName"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String buscarGrupoDeTrabajoPC() throws ExcepcionInventario {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET Workgroup"};
        String grupo = ejecutarComandoWMIC(comando).get(2);
        if (grupo.equals("           ")) {
            return "N/A";
        } else {
            return grupo;
        }
    }

    private String buscarDominioPC() throws ExcepcionInventario {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET Domain"};
        String dominio = ejecutarComandoWMIC(comando).get(2);
        if (dominio.trim().equals("")) {
            return "N/A";
        } else {
            return dominio;
        }
    }

    private ArrayList<String> buscarMarcaPantallasPC() throws ExcepcionInventario {
        return ejecutarComandoDirectX("Monitor Name: ", true);

    }

    private ArrayList<String> buscarModeloPantallasPC() throws ExcepcionInventario {
        return ejecutarComandoDirectX("Monitor Model: ", true);
    }

    private ArrayList<String> buscarSerialPantallasPC() throws ExcepcionInventario {
        return ejecutarComandoDirectX("Monitor Id: ", true);
    }

    private String buscarProcesadorPC() throws ExcepcionInventario {
        return ejecutarComandoDirectX("Processor: ", true).get(0);
    }

    public void definirTipoActualizacion(String inventario, int fila) throws ExcepcionInventario {
        if(inventario.equals("NUEVO")&& fila==0){
            filaActualizacion=filaDocumento;
            System.out.println(filaActualizacion+" definirTipoActualizacion");
        }else{
            filaActualizacion = fila-1;
            if (inventario.equals("OFICINA")) {
            inventarioActualizacion = OFICINA;
        } else if (inventario.equals("OBRA")) {
            inventarioActualizacion = OBRA;
        }
        obtenerInformacionAnteriorPC();
        }        
    }

    private String buscarMemoriaRamPC() throws ExcepcionInventario {
        String[] comandoCapacidad = {"CMD", "/C", "WMIC MemoryChip GET Capacity"};
        String[] comandoVelocidad = {"CMD", "/C", "WMIC MemoryChip GET Speed"};
        String[] comandoTipo = {"CMD", "/C", "WMIC MemoryChip GET MemoryType"};
        String respuesta = "";
        ArrayList<String> memorias = ejecutarComandoWMIC(comandoCapacidad);
        for (int i = 1; i < memorias.size(); i++) {
            if (!memorias.get(i).trim().equals("")) {
                try {
                    respuesta = respuesta + Long.toString(Long.parseLong(memorias.get(i).trim()) / 1073741824) + " GB " + tiposRam[Integer.parseInt(ejecutarComandoWMIC(comandoTipo).get(i).trim())] + " " + ejecutarComandoWMIC(comandoVelocidad).get(i).trim() + "MHz \n";
                } catch (ArrayIndexOutOfBoundsException e) {
                    respuesta = respuesta + Long.toString(Long.parseLong(memorias.get(i).trim()) / 1073741824) + " GB " + tiposRam[0] + " " + ejecutarComandoWMIC(comandoVelocidad).get(i).trim() + "MHz \n";
                }
            }
        }

        return respuesta.substring(0, respuesta.length() - 2);

    }

    private String buscarPlacaBasePC() throws ExcepcionInventario {
        String[] comando = {"CMD", "/C", "wmic baseboard get product,Manufacturer,version,serialnumber"};
        return ejecutarComandoWMIC(comando).get(2).trim();
    }

    private String buscarTarjetaGraficaPC() throws ExcepcionInventario {
        String tarjeta;
        try {
            tarjeta = ejecutarComandoDirectX("Name: NVIDIA", false).get(0).substring(6);
        } catch (IndexOutOfBoundsException e) {
            tarjeta = ejecutarComandoDirectX("Card name: ", true).get(0);
        }
        return tarjeta;
    }

    private String buscarDiscoDuroPC() throws ExcepcionInventario {
        String[] comandoNombre = {"CMD", "/C", "wmic diskdrive get caption"};
        String[] comandoTamaño = {"CMD", "/C", "wmic diskdrive get size"};
        return Long.toString(Long.parseLong(ejecutarComandoWMIC(comandoTamaño).get(2).trim()) / 1073741824) + " GB " + ejecutarComandoWMIC(comandoNombre).get(2);
    }

    private String buscarUnidadCDPC() throws ExcepcionInventario {
        String[] comando = {"CMD", "/C", "wmic cdrom get Name"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    public void setNombrePC(String nombrePC) {
        this.nombrePC = nombrePC.toUpperCase();
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura.toUpperCase();
    }

    public void setPersonaAsignada(String personaAsignada) {
        this.personaAsignada = personaAsignada.toUpperCase();
    }

    public void setLugar(String lugar) {
        this.lugar = lugar.toUpperCase();
    }

    public void setArea(String area) {
        this.area = area.toUpperCase();
    }

    public void setPlacaPC(String placaPC) {
        this.placaPC = placaPC.toUpperCase();
    }

    public void setMarcaPC(String marcaPC) {
        this.marcaPC = marcaPC.toUpperCase();
    }

    public void setModeloPC(String ModeloPC) {
        this.modeloPC = ModeloPC.toUpperCase();
    }

    public void setSerialPC(String SerialPC) {
        this.serialPC = SerialPC.toUpperCase();
    }

    public void setUserAdimn(String userAdimn) {
        this.userAdmin = userAdimn.toUpperCase();
    }

    public void setGrupo(String Grupo) {
        this.Grupo = Grupo.toUpperCase();
    }

    public void setDominio(String Dominio) {
        this.Dominio = Dominio.toUpperCase();
    }

    public void setMarcaPantalla(String marcaPantalla) {
        this.marcaPantalla = marcaPantalla.toUpperCase();
    }

    public void setModeloPantalla(String modeloPantalla) {
        this.modeloPantalla = modeloPantalla.toUpperCase();
    }

    public void setSerialPantalla(String serialPantalla) {
        this.serialPantalla = serialPantalla.toUpperCase();
    }

    public void setIp(String ip) {
        this.ip = ip.toUpperCase();
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador.toUpperCase();
    }

    public void setRam(String ram) {
        this.ram = ram.toUpperCase();
    }

    public void setPlacaBase(String placaBase) {
        this.placaBase = placaBase.toUpperCase();
    }

    public void setTarjetaGrafica(String tarjetaGrafica) {
        this.tarjetaGrafica = tarjetaGrafica.toUpperCase();
    }

    public void setDiscoDuro(String discoDuro) {
        this.discoDuro = discoDuro.toUpperCase();
    }

    public void setUnidadCD(String unidadCD) {
        this.unidadCD = unidadCD.toUpperCase();
    }

    public void setAntivirus(String antivirus) {
        this.antivirus = antivirus.toUpperCase();
    }

    public void setDistribucionOffice(String distribucionOffice) {
        this.distribucionOffice = distribucionOffice.toUpperCase();
    }

    public void setVersionOffice(String versionOffice) {
        this.versionOffice = versionOffice.toUpperCase();
    }

    public void setDistribucionWindows(String distribucionWindows) {
        this.distribucionWindows = distribucionWindows.toUpperCase();
    }

    public void setIdWindows(String idWindows) {
        this.idWindows = idWindows.toUpperCase();
    }

    public void setLicenciaWindows(String licenciaWindows) {
        this.licenciaWindows = licenciaWindows.toUpperCase();
    }

    public void setLicenciaWindowsBIOS(String licenciaWindowsBIOS) {
        this.licenciaWindowsBIOS = licenciaWindowsBIOS.toUpperCase();
    }

    public void setCoaWindows(String coaWindows) {
        this.coaWindows = coaWindows.toUpperCase();
    }

    public void setDistribucionProject(String distribucionProject) {
        this.distribucionProject = distribucionProject.toUpperCase();
    }

    public void setVersionProject(String versionProject) {
        this.versionProject = versionProject.toUpperCase();
    }

    public void setLicenciaProject(String licenciaProject) {
        this.licenciaProject = licenciaProject.toUpperCase();
    }

    public void setDistribucionAutocad(String distribucionAutocad) {
        this.distribucionAutocad = distribucionAutocad.toUpperCase();
    }

    public void setVersionAutocad(String versionAutocad) {
        this.versionAutocad = versionAutocad.toUpperCase();
    }

    public void setLicenciaAutocad(String licenciaAutocad) {
        this.licenciaAutocad = licenciaAutocad.toUpperCase();
    }

    public ArrayList<String> getListaDistribucionOffice() {
        return listaDistribucionOffice;
    }

    public ArrayList<String> getListaVersionOffice() {
        return listaVersionOffice;
    }

    public ArrayList<String> getListaLicenciasOffice() {
        return listaLicenciasOffice;
    }

    public ArrayList<String> getListaDistribucionWindows() {
        return listaDistribucionWindows;
    }

    public ArrayList<String> getListaDistribucionProject() {
        return listaDistribucionProject;
    }

    public ArrayList<String> getListaVersionProject() {
        return listaVersionProject;
    }

    public ArrayList<String> getListaLicenciasProject() {
        return listaLicenciasProject;
    }

    public ArrayList<String> getListaVersionAutocad() {
        return listaVersionAutocad;
    }

    public ArrayList<String> getListaDistribucionAutocad() {
        return listaDistribucionAutocad;
    }

    public ArrayList<String> getLugaresOficina() {
        return lugaresOficina;
    }

    public ArrayList<String> getAreasOficina() {
        return areasOficina;
    }

    public void setTipoDeEquipo(String tipoDeEquipo) {
        this.tipoDeEquipo = tipoDeEquipo.toUpperCase();
    }

    public void setLicenciaOffice(String licenciaOffice) {
        this.licenciaOffice = licenciaOffice;
    }

    public void setOfficeActivo(String officeActivo) {
        this.officeActivo = officeActivo;
    }

    public void setOfficeInstalado(String officeInstalado) {
        this.officeInstalado = officeInstalado;
    }

    public void setLicenciaOtro(String licenciaOtro) {
        this.licenciaOtro = licenciaOtro.toUpperCase();
    }

    public ArrayList<String> getListaLicenciasAutocad() {
        return listaLicenciasAutocad;
    }

    public String getNombrePC() {
        return nombrePC;
    }

    public String getPlacaPC() {
        return placaPC;
    }

    public String getMarcaPC() {
        return marcaPC;
    }

    public String getModeloPC() {
        return modeloPC;
    }

    public String getSerialPC() {
        return serialPC;
    }

    public String getUserAdmin() {
        return userAdmin;
    }

    public String getGrupo() {
        return Grupo;
    }

    public String getDominio() {
        return Dominio;
    }

    public String getMarcaPantalla() {
        return marcaPantalla;
    }

    public String getSerialPantalla() {
        return serialPantalla;
    }

    public String getIp() {
        return ip;
    }

    public String getProcesador() {
        return procesador;
    }

    public String getRam() {
        return ram;
    }

    public String getPlacaBase() {
        return placaBase;
    }

    public String getTarjetaGrafica() {
        return tarjetaGrafica;
    }

    public String getDiscoDuro() {
        return discoDuro;
    }

    public String getUnidadCD() {
        return unidadCD;
    }

    public String getModeloPantalla() {
        return modeloPantalla;
    }

    public void setFilaDocumento(int filaDocumento) throws ExcepcionInventario {
        this.filaDocumento = filaDocumento;
    }

    public String getNombreAnteriorPC() {
        return nombreAnteriorPC;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public String getPersonaAsignada() {
        return personaAsignada;
    }

    public String getArea() {
        return area;
    }

    public String getAntivirus() {
        return antivirus;
    }

    public String getCoaWindows() {
        return coaWindows;
    }

    public ArrayList<String> getSeriales() {
        return seriales;
    }

    public String getIdWindows() {
        return idWindows;
    }

    public String getLicenciaWindows() {
        return licenciaWindows;
    }

    public String getLicenciaWindowsBIOS() {
        return licenciaWindowsBIOS;
    }

    public String getLugar() {
        return lugar;
    }

    public String getDistribucionOffice() {
        return distribucionOffice;
    }

    public String getVersionOffice() {
        return versionOffice;
    }

    public String getLicenciaOffice() {
        return licenciaOffice;
    }

    public String getOfficeActivo() {
        return officeActivo;
    }

    public String getOfficeInstalado() {
        return officeInstalado;
    }

    public String getDistribucionWindows() {
        return distribucionWindows;
    }

    public String getDistribucionProject() {
        return distribucionProject;
    }

    public String getVersionProject() {
        return versionProject;
    }

    public String getLicenciaProject() {
        return licenciaProject;
    }

    public String getDistribucionAutocad() {
        return distribucionAutocad;
    }

    public String getVersionAutocad() {
        return versionAutocad;
    }

    public String getLicenciaAutocad() {
        return licenciaAutocad;
    }

    public String getLicenciaOtro() {
        return licenciaOtro;
    }

    public String getTipoDeEquipo() {
        return tipoDeEquipo;
    }

    public int getTipoInventario() {
        return tipoInventario;
    }

    public void setTipoInventario(int tipoInventario) {
        this.tipoInventario = tipoInventario;
    }

    public ArrayList<String> getLugaresObra() {
        return lugaresObra;
    }

    public void setLugaresObra(ArrayList<String> lugaresObra) {
        this.lugaresObra = lugaresObra;
    }

}
