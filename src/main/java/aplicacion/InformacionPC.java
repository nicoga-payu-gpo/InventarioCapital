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
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ngarcia
 */
public class InformacionPC {

    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FOLDER = "credentials"; // Directory to store user credentials.

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved credentials/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CLIENT_SECRET_DIR = "/client_secret.json";

    public final int laptop = 1;
    public final int desktop = 0;
    private final String[] tiposRam = {"Unknown", "Other", "DRAM", "Synchronous DRAM", "Cache DRAM", "EDO", "EDRAM", "VRAM", "SRAM", "RAM", "ROM", "Flash", "EEPROM", "FEPROM", "EPROM", "CDRAM", "CDRAM", "3DRAM", "SDRAM", "SGRAM", "RDRAMRDRAM", "DDR", "DDR2", "DDR2 FB-DIMM", "DDR3", "FBD2"};

    private String filePath;
    private String nombrePC;
    private String marcaPC;
    private String ModeloPC;
    private String SerialPC;
    private String userAdimn;
    private String Grupo;
    private String Dominio;
    private String marcaPantalla;
    private String modeloPantalla;
    private String serialPantalla;
    private String placaPantalla;
    private String ip;
    private String procesador;
    private String ram;

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

    
    public static void probar() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "1RNKrOzttbIwK0gjnJRqc0aoQ7jGjC23SMoro1t0kZ-I";
        final String range = "Oficina!A5:B";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            for (List row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
                //System.out.printf("%s, %s\n", row.get(0), row.get(4));
                System.out.println( row.get(0));
            }
            
        }
        System.out.println(Integer.toString(values.size()));
    }

    public void buscarInformacion(int tipoPC, boolean multiplesPantallas) {
        try {
            probar();
        } catch (IOException ex) {
            Logger.getLogger(InformacionPC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(InformacionPC.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(getNombrePC());
        System.out.println(getMarcaPC());
        System.out.println(getModeloPC());
        System.out.println(getSerialPC());
        System.out.println(getUsuarioPC());
        System.out.println(getGrupoDeTrabajoPC());
        System.out.println(getDominioPC());
        for (String s : getMarcaPantallasPC()) {
            System.out.println(s);
        }
        for (String s : getModeloPantallasPC()) {
            System.out.println(s);
        }
        for (String s : getSerialPantallasPC()) {
            System.out.println(s);
        }
        System.out.println(getProcesadorPC());
        System.out.println(getMemoriaRamPC());
        System.out.println(getPlacaBasePC());
        System.out.println(getTarjetaGraficaPC());
        System.out.println(getDiscoDuroPC());
        System.out.println(getUnidadCDPC());
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

    private String getNombrePC() {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET Name"};
        return ejecutarComandoWMIC(comando).get(2);

    }

    private String getMarcaPC() {
        String[] comando = {"CMD", "/C", "WMIC csproduct GET Vendor"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String getModeloPC() {
        String[] comando = {"CMD", "/C", "WMIC CSPRODUCT GET Name"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String getSerialPC() {
        String[] comando = {"CMD", "/C", "WMIC BIOS GET SerialNumber"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String getUsuarioPC() {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET UserName"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String getGrupoDeTrabajoPC() {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET Workgroup"};
        String grupo = ejecutarComandoWMIC(comando).get(2);
        if (grupo.equals("           ")) {
            return "N/A";
        } else {
            return grupo;
        }

    }

    private String getDominioPC() {
        String[] comando = {"CMD", "/C", "WMIC COMPUTERSYSTEM GET Domain"};
        String dominio = ejecutarComandoWMIC(comando).get(2);
        if (dominio.trim().equals("")) {
            return "N/A";
        } else {
            return dominio;
        }
    }

    private ArrayList<String> getMarcaPantallasPC() {
        return ejecutarComandoDirectX("Monitor Name: ", true);

    }

    private ArrayList<String> getModeloPantallasPC() {
        return ejecutarComandoDirectX("Monitor Model: ", true);
    }

    private ArrayList<String> getSerialPantallasPC() {
        return ejecutarComandoDirectX("Monitor Id: ", true);
    }

    private String getProcesadorPC() {
        return ejecutarComandoDirectX("Processor: ", true).get(0);
    }

    private String getMemoriaRamPC() {
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

    private String getPlacaBasePC() {
        String[] comando = {"CMD", "/C", "wmic baseboard get product,Manufacturer,version,serialnumber"};
        return ejecutarComandoWMIC(comando).get(2);
    }

    private String getTarjetaGraficaPC() {
        String tarjeta;
        try {
            tarjeta = ejecutarComandoDirectX("Name: NVIDIA", false).get(0).substring(6);
        } catch (IndexOutOfBoundsException e) {
            tarjeta = ejecutarComandoDirectX("Card name: ", true).get(0);
        }
        return tarjeta;
    }

    private String getDiscoDuroPC() {
        String[] comandoNombre = {"CMD", "/C", "wmic diskdrive get caption"};
        String[] comandoTamaño = {"CMD", "/C", "wmic diskdrive get size"};
        return Long.toString(Long.parseLong(ejecutarComandoWMIC(comandoTamaño).get(2).trim()) / 1073741824) + " GB " + ejecutarComandoWMIC(comandoNombre).get(2);
    }

    private String getUnidadCDPC() {
        String[] comando = {"CMD", "/C", "wmic cdrom get Name"};
        return ejecutarComandoWMIC(comando).get(2);
    }
}
