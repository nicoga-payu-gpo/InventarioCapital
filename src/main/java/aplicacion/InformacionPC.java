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
import com.google.api.client.util.Data;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.CellFormat;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.NumberFormat;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static javax.swing.GroupLayout.Alignment.values;

/**
 *
 * @author ngarcia
 */
public class InformacionPC {

    private static final String APPLICATION_NAME = "inventario Capital";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FOLDER = "credentials"; // Directory to store user credentials
    private final String spreadsheetId = "1RNKrOzttbIwK0gjnJRqc0aoQ7jGjC23SMoro1t0kZ-I";
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
    private ArrayList<String> lugaresOficina = new ArrayList<String>();
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
    private String[] abc = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};

    String[] titulos = {"N FACTURA", "TIPO EQUIPO", "NOMBRE ANTERIOR", "NOMBRE ACTUAL EQUIPO", "ASIGNADO A", "LUGAR", "AREA", "MARCA", "MODELO", "SERIAL", "PLACA",
        "USER ADMIN", "GPO", "USER DOM", "P.MARCA", "P.MODELO", "P.SERIAL", "IP", "PROCESADOR", "RAM", "MOTHERBOARD", "TJ GRAFICA", "DISCO DURO", "UNIDAD CD/DVD",
        "ANTIVIRUS", "DISTRIBUCION OFFICE", "VERSIÓN OFFICE", "LICENCIA OFFICE", "ACTIVO", "instalada", "DISTRUBUCION WINDOWS", "ID PRODUCTO", "LICENCIA WINDOWS", "LICENCIA (BIOS)",
        "COA", "DISTRIBUCION PROJECT", "VERSIÓN PROJECT", "LICENCIA PROJECT", "DISTRIBUCION AUTOCAD", "VERSIÓN AUTOCAD", "LICENCIA AUTOCAD", "LICENCIA OTRO"};

    public InformacionPC() {
        nombreAnteriorPC = "N/A";
        nombrePC = "N/A";
        numeroFactura = "N/A";
        personaAsignada = "N/A";
        lugar = "N/A";
        area = "N/A";
        placaPC = "N/A";
        marcaPC = "N/A";
        modeloPC = "N/A";
        serialPC = "N/A";
        userAdmin = "N/A";
        Grupo = "N/A";
        Dominio = "N/A";      
        ip = "N/A";;
        procesador = "N/A";
        ram = "N/A";
        placaBase = "N/A";
        tarjetaGrafica = "N/A";
        discoDuro = "N/A";
        unidadCD = "N/A";
        antivirus = "N/A";
        distribucionOffice = "N/A";
        versionOffice = "N/A";
        licenciaOffice = "N/A";
        officeActivo = "N/A";
        officeInstalado = "N/A";
        distribucionWindows = "N/A";
        idWindows = "N/A";
        licenciaWindows = "N/A";
        licenciaWindowsBIOS = "N/A";
        coaWindows = "N/A";
        distribucionProject = "N/A";
        versionProject = "N/A";
        licenciaProject = "N/A";
        distribucionAutocad = "N/A";
        versionAutocad = "N/A";
        licenciaAutocad = "N/A";
        licenciaOtro = "N/A";
    }

    public void definirTipoInventario(int ind) throws ExcepcionInventario {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            ValueRange respuesta = service.spreadsheets().values()
                    .get(spreadsheetId, "Opciones Programa!A2:K2")
                    .execute();
            filaTitulosConfiguracion = respuesta.getValues();
            if (ind == OFICINA) {
                buscarDatosOficina(service);
            } else if (ind == OBRA) {

            }

        } catch (IOException | GeneralSecurityException ex) {
            throw new ExcepcionInventario("Error al establecer conexion con google sheets");
        }

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

    public void guardarDatosOficina() throws ExcepcionInventario {
        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
            ValueRange respuesta;
            respuesta = service.spreadsheets().values()
                    .get(spreadsheetId, "Oficina!B5:C")
                    .execute();
            filaDocumento = respuesta.getValues().size() + 4;
            ValueRange respuesta1 = service.spreadsheets().values()
                    .get(spreadsheetId, "Oficina!B4:AW4")
                    .execute();
            filaTitulosInventarioOficina = respuesta1.getValues();
            guardarFecha(service);
            String[] datosPC = {numeroFactura, tipoDeEquipo, nombreAnteriorPC, nombrePC, personaAsignada, lugar, area, marcaPC, modeloPC, serialPC, placaPC, userAdmin, Grupo, Dominio, marcaPantalla, modeloPantalla,
                serialPantalla, ip, procesador, ram, placaBase, tarjetaGrafica, discoDuro, unidadCD, antivirus, distribucionOffice, versionOffice, licenciaOffice, officeActivo, officeInstalado, distribucionWindows,
                idWindows, licenciaWindows, licenciaWindowsBIOS, coaWindows, distribucionProject, versionProject, licenciaProject, distribucionAutocad, versionAutocad, licenciaAutocad, licenciaOtro};
            for (int i = 0; i < titulos.length; i++) {
                guardarDato(titulos[i], datosPC[i], service, filaTitulosInventarioOficina);
            }
        } catch (Exception ex) {
            throw new ExcepcionInventario("Error al intentar guardar datos en Google sheets: " + ex.getMessage());
        }

    }

    private void guardarFecha(Sheets s) throws IOException {
        List<Request> requests = new ArrayList<>();

        List<CellData> valores = new ArrayList<>();
        Date fecha = new Date();
        String fe = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
        System.out.println(fe);
        CellData cell = new CellData();
        cell.setUserEnteredValue(new ExtendedValue().setStringValue(fe));
        cell.setUserEnteredFormat(new CellFormat().setNumberFormat(new NumberFormat().setType("DATE")));
        valores.add(cell);
        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(1561897843)
                                .setRowIndex(filaDocumento)
                                .setColumnIndex(1))
                        .setRows(Arrays.asList(
                                new RowData().setValues(valores)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        s.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest)
                .execute();

    }

    private void buscarDatosOficina(Sheets s) throws ExcepcionInventario {
        try {
            obtenerColumna(s, buscarColumna("LUGARES OFICINA", filaTitulosConfiguracion), lugaresOficina);
            obtenerColumna(s, buscarColumna("AREAS OFICINA", filaTitulosConfiguracion), areasOficina);
            obtenerColumna(s, buscarColumna("DISTRIBUCION OFFICE", filaTitulosConfiguracion), listaDistribucionOffice);
            obtenerLicencias("Office", "D", s, listaLicenciasOffice);
            obtenerColumna(s, buscarColumna("VERSION OFFICE", filaTitulosConfiguracion), listaVersionOffice);
            obtenerColumna(s, buscarColumna("DISTRIBUCION WINDOWS", filaTitulosConfiguracion), listaDistribucionWindows);
            obtenerColumna(s, buscarColumna("VERSION PROJECT", filaTitulosConfiguracion), listaVersionProject);
            obtenerColumna(s, buscarColumna("DISTRIBUCION PROJECT", filaTitulosConfiguracion), listaDistribucionProject);
            obtenerLicencias("Project", "C", s, listaLicenciasProject);
            obtenerColumna(s, buscarColumna("DISTRIBUCION AUTOCAD", filaTitulosConfiguracion), listaDistribucionAutocad);
            obtenerColumna(s, buscarColumna("VERSION AUTOCAD", filaTitulosConfiguracion), listaVersionAutocad);
            obtenerLicencias("AutoCad", "E", s, listaLicenciasAutocad);
        } catch (IOException ex) {
            throw new ExcepcionInventario("Error al buscar datos en el apartado Opciones Programa de google sheets.");
        }
    }

    private void crearLista(ArrayList<String> a, List<List<Object>> l) {
        a.add("N/A");
        for (int columna = 2; columna < l.size(); columna++) {
            if (!l.get(columna).isEmpty()) {
                a.add((String) l.get(columna).get(0));
            }
        }
    }

    private void obtenerLicencias(String hoja, String columna, Sheets s, ArrayList<String> l) throws IOException {
        ValueRange respuesta1 = s.spreadsheets().values()
                .get(spreadsheetId, hoja + "!" + columna + ":" + columna)
                .execute();
        crearLista(l, respuesta1.getValues());
    }

    private void obtenerColumna(Sheets s, int columna, ArrayList<String> l) throws IOException {
        ValueRange respuesta1 = s.spreadsheets().values()
                .get(spreadsheetId, "Opciones Programa!" + abc[columna - 1] + ":" + abc[columna - 1])
                .execute();

        crearLista(l, respuesta1.getValues());

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

    private void guardarDato(String c, String p, Sheets s, List<List<Object>> l) throws IOException {
        List<Request> requests = new ArrayList<>();
        List<CellData> valores = new ArrayList<>();
        valores.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(p)));

        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(1561897843)
                                .setRowIndex(filaDocumento)
                                .setColumnIndex(buscarColumna(c, l)))
                        .setRows(Arrays.asList(
                                new RowData().setValues(valores)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));
        BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                .setRequests(requests);
        s.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest)
                .execute();
        ;
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
        Dominio = buscarDominioPC().toUpperCase();
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

    private ArrayList<String> ejecutarComandoWMIC(String[] c) throws ExcepcionInventario {
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
            throw new ExcepcionInventario("Error al ejecutar comando DirectX.");
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
        return ejecutarComandoWMIC(comando).get(2);
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
        System.out.println(personaAsignada);
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
}
