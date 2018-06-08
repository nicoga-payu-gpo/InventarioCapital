/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplicacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ngarcia
 */
public class InformacionPC {

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

    public void buscarInformacion(int tipoPC,boolean multiplesPantallas) {

        

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
    
    private String getDiscoDuroPC(){
        String[] comandoNombre={"CMD", "/C", "wmic diskdrive get caption"};
        String[] comandoTamaño={"CMD", "/C", "wmic diskdrive get size"};
        return Long.toString(Long.parseLong(ejecutarComandoWMIC(comandoTamaño).get(2).trim()) / 1073741824) +" GB "+ ejecutarComandoWMIC(comandoNombre).get(2);
    }
    
    private String getUnidadCDPC(){
        String[] comando={"CMD", "/C", "wmic cdrom get Name"};
        return ejecutarComandoWMIC(comando).get(2);
    }
}
