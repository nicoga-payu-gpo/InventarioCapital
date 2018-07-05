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
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
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

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved credentials/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CLIENT_SECRET_DIR = "/client_secret.json";

    public final int laptop = 1;
    public final int desktop = 0;
    public final int allInOne = 2;
    private final String[] tiposRam = {"Unknown", "Other", "DRAM", "Synchronous DRAM", "Cache DRAM", "EDO", "EDRAM", "VRAM", "SRAM", "RAM", "ROM", "Flash", "EEPROM", "FEPROM", "EPROM", "CDRAM", "CDRAM", "3DRAM", "SDRAM", "SGRAM", "RDRAMRDRAM", "DDR", "DDR2", "DDR2 FB-DIMM", "DDR3", "FBD2"};
    private int filaDocumento;
    private List<List<Object>> filaTitulos;
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
    private String[] datosPC = {numeroFactura, tipoDeEquipo, nombreAnteriorPC, nombrePC, personaAsignada, lugar, area, marcaPC, modeloPC, serialPC, placaPC, userAdmin, Grupo, Dominio, marcaPantalla, modeloPantalla,
        serialPantalla, ip, procesador, ram, placaBase, tarjetaGrafica, discoDuro, unidadCD, antivirus, distribucionOffice, versionOffice, licenciaOffice, officeActivo, officeInstalado, distribucionWindows,
        idWindows, licenciaWindows, licenciaWindowsBIOS, coaWindows, distribucionProject, versionProject, licenciaProject, distribucionAutocad, versionAutocad, licenciaAutocad};

    ;

    public InformacionPC() {

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

    public void guardarDatos() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange respuesta = service.spreadsheets().values()
                .get(spreadsheetId, "Oficina!B5:C")
                .execute();
        filaDocumento = respuesta.getValues().size() + 4;
        ValueRange respuesta1 = service.spreadsheets().values()
                .get(spreadsheetId, "Oficina!B4:AW4")
                .execute();
        filaTitulos = respuesta1.getValues();

        for (List fila : filaTitulos) {
            int columna = 0;
            while (fila.size() > columna) {
                System.out.println(fila.get(columna));
                columna += 1;
            }

        }
        guardarFecha(service);
        guardarDato("N FACTURA", numeroFactura, service);
        guardarDato("TIPO EQUIPO", tipoDeEquipo, service);

    }

    private void guardarFecha(Sheets s) throws IOException {
        List<Request> requests = new ArrayList<>();

        List<CellData> valores = new ArrayList<>();
        Date fecha = new Date();
        String fe = new SimpleDateFormat("dd-MM-yyyy").format(fecha);
        valores.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(fe.substring(1))));
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
        ;

    }

    private void guardarDato(String c, String p, Sheets s) throws IOException {
        List<Request> requests = new ArrayList<>();

        List<CellData> valores = new ArrayList<>();

        valores.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(p)));
        
        int columna = 0;
        for (List fila : filaTitulos) {
            int cont = -1;
            boolean salida = false;
            
            while (fila.size() > cont && !salida) {
                cont++;
                if (fila.get(cont).equals(c)) {
                    salida = true;
                    columna = cont+1;
                }
                
            }
        }
        

        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(1561897843)
                                .setRowIndex(filaDocumento)
                                .setColumnIndex(columna))
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
        nombreAnteriorPC = "N/A";
        nombrePC = buscarNombrePC();
        numeroFactura = "N/A";
        personaAsignada = "N/A";
        lugar = "N/A";
        area = "N/A";
        placaPC = "N/A";
        marcaPC = buscarMarcaPC();
        modeloPC = buscarModeloPC();
        serialPC = buscarSerialPC();
        userAdmin = buscarUsuarioPC();
        Grupo = buscarGrupoDeTrabajoPC();
        Dominio = buscarDominioPC();
        if (tipoPC != 1 || multiplesPantallas) {
            for (String s : buscarMarcaPantallasPC()) {
                marcaPantalla = marcaPantalla + "\n" + s;
            }
            for (String s : buscarModeloPantallasPC()) {
                modeloPantalla = modeloPantalla + "\n" + s;
            }
            for (String s : buscarSerialPantallasPC()) {
                serialPantalla = serialPantalla + "\n" + s;
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
        procesador = buscarProcesadorPC();
        ram = buscarMemoriaRamPC();
        placaBase = buscarPlacaBasePC();
        tarjetaGrafica = buscarTarjetaGraficaPC();
        discoDuro = buscarDiscoDuroPC();
        unidadCD = buscarUnidadCDPC();
        antivirus = "N/A";
        distribucionOffice = "N/A";
        versionOffice = "N/A";
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
        try {
            guardarDatos();
        } catch (IOException ex) {
            Logger.getLogger(InformacionPC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(InformacionPC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private ArrayList<String> ejecutarComandoWMIC(String[] c) {
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
            Logger.getLogger(InformacionPC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(InformacionPC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return respuesta;
    }

    private ArrayList<String> ejecutarComandoDirectX(String especificacion, boolean recortar) {
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
                        respuesta.add(line.trim().substring(especificacion.length()));
                    } else {
                        respuesta.add(line.trim());
                    }

                }
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return respuesta;
    }

    private String buscarIP() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    private String buscarNombrePC() {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET Name"};
        return ejecutarComandoWMIC(comando).get(2);

    }

    private String buscarMarcaPC() {
        String[] comando = {"CMD", "/C", "WMIC csproduct GET Vendor"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String buscarModeloPC() {
        String[] comando = {"CMD", "/C", "WMIC CSPRODUCT GET Name"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String buscarSerialPC() {
        String[] comando = {"CMD", "/C", "WMIC BIOS GET SerialNumber"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String buscarUsuarioPC() {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET UserName"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String buscarGrupoDeTrabajoPC() {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET Workgroup"};
        String grupo = ejecutarComandoWMIC(comando).get(2);
        if (grupo.equals("           ")) {
            return "N/A";
        } else {
            return grupo;
        }

    }

    private String buscarDominioPC() {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET Domain"};
        String dominio = ejecutarComandoWMIC(comando).get(2);
        if (dominio.trim().equals("")) {
            return "N/A";
        } else {
            return dominio;
        }
    }

    private ArrayList<String> buscarMarcaPantallasPC() {
        return ejecutarComandoDirectX("Monitor Name: ", true);

    }

    private ArrayList<String> buscarModeloPantallasPC() {
        return ejecutarComandoDirectX("Monitor Model: ", true);
    }

    private ArrayList<String> buscarSerialPantallasPC() {
        return ejecutarComandoDirectX("Monitor Id: ", true);
    }

    private String buscarProcesadorPC() {
        return ejecutarComandoDirectX("Processor: ", true).get(0);
    }

    private String buscarMemoriaRamPC() {
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

    private String buscarPlacaBasePC() {
        String[] comando = {"CMD", "/C", "wmic baseboard get product,Manufacturer,version,serialnumber"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String buscarTarjetaGraficaPC() {
        String tarjeta;
        try {
            tarjeta = ejecutarComandoDirectX("Name: NVIDIA", false).get(0).substring(6);
        } catch (IndexOutOfBoundsException e) {
            tarjeta = ejecutarComandoDirectX("Card name: ", true).get(0);
        }
        return tarjeta;
    }

    private String buscarDiscoDuroPC() {
        String[] comandoNombre = {"CMD", "/C", "wmic diskdrive get caption"};
        String[] comandoTamaño = {"CMD", "/C", "wmic diskdrive get size"};
        return Long.toString(Long.parseLong(ejecutarComandoWMIC(comandoTamaño).get(2).trim()) / 1073741824) + " GB " + ejecutarComandoWMIC(comandoNombre).get(2);
    }

    private String buscarUnidadCDPC() {
        String[] comando = {"CMD", "/C", "wmic cdrom get Name"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    public void setNombrePC(String nombrePC) {
        this.nombrePC = nombrePC;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public void setPersonaAsignada(String personaAsignada) {
        this.personaAsignada = personaAsignada;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setPlacaPC(String placaPC) {
        this.placaPC = placaPC;
    }

    public void setMarcaPC(String marcaPC) {
        this.marcaPC = marcaPC;
    }

    public void setModeloPC(String ModeloPC) {
        this.modeloPC = ModeloPC;
    }

    public void setSerialPC(String SerialPC) {
        this.serialPC = SerialPC;
    }

    public void setUserAdimn(String userAdimn) {
        this.userAdmin = userAdimn;
    }

    public void setGrupo(String Grupo) {
        this.Grupo = Grupo;
    }

    public void setDominio(String Dominio) {
        this.Dominio = Dominio;
    }

    public void setMarcaPantalla(String marcaPantalla) {
        this.marcaPantalla = marcaPantalla;
    }

    public void setModeloPantalla(String modeloPantalla) {
        this.modeloPantalla = modeloPantalla;
    }

    public void setSerialPantalla(String serialPantalla) {
        this.serialPantalla = serialPantalla;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setProcesador(String procesador) {
        this.procesador = procesador;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setPlacaBase(String placaBase) {
        this.placaBase = placaBase;
    }

    public void setTarjetaGrafica(String tarjetaGrafica) {
        this.tarjetaGrafica = tarjetaGrafica;
    }

    public void setDiscoDuro(String discoDuro) {
        this.discoDuro = discoDuro;
    }

    public void setUnidadCD(String unidadCD) {
        this.unidadCD = unidadCD;
    }

    public void setAntivirus(String antivirus) {
        this.antivirus = antivirus;
    }

    public void setDistribucionOffice(String distribucionOffice) {
        this.distribucionOffice = distribucionOffice;
    }

    public void setVersionOffice(String versionOffice) {
        this.versionOffice = versionOffice;
    }

    public void setDistribucionWindows(String distribucionWindows) {
        this.distribucionWindows = distribucionWindows;
    }

    public void setIdWindows(String idWindows) {
        this.idWindows = idWindows;
    }

    public void setLicenciaWindows(String licenciaWindows) {
        this.licenciaWindows = licenciaWindows;
    }

    public void setLicenciaWindowsBIOS(String licenciaWindowsBIOS) {
        this.licenciaWindowsBIOS = licenciaWindowsBIOS;
    }

    public void setCoaWindows(String coaWindows) {
        this.coaWindows = coaWindows;
    }

    public void setDistribucionProject(String distribucionProject) {
        this.distribucionProject = distribucionProject;
    }

    public void setVersionProject(String versionProject) {
        this.versionProject = versionProject;
    }

    public void setLicenciaProject(String licenciaProject) {
        this.licenciaProject = licenciaProject;
    }

    public void setDistribucionAutocad(String distribucionAutocad) {
        this.distribucionAutocad = distribucionAutocad;
    }

    public void setVersionAutocad(String versionAutocad) {
        this.versionAutocad = versionAutocad;
    }

    public void setLicenciaAutocad(String licenciaAutocad) {
        this.licenciaAutocad = licenciaAutocad;
    }
}
