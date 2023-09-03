package archivosaleatorios;

import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ArchivosAleatorios {
    private static final String archivoCSV = "C:/Users/Hp/Downloads/vendors.csv";

    
    public static void main(String[] args) throws CsvValidationException {
      Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Agregar Vendedor");
            System.out.println("2. Borrar Vendedor por Codigo");
            System.out.println("3. Modificar Datos de Vendedor por Codigo");
            System.out.println("4. Consultar Vendedores por Zona");
            System.out.println("5. Salir");
            System.out.print("Elija una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    agregarVendedor(scanner);
                    break;
                case 2:
                    borrarVendedor();
                    break;
                case 3:
                    modificarVendedor();
                    break;
                case 4:
                    consultarVendedores();
                    break;
                case 5:
                    scanner.close();
                    System.out.println("El programa ha finalizado");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Por favor, elija una opción válida.");
            }
        }
    }

    private static void agregarVendedor(Scanner scanner) { //Punto 1 y 2 realizado por Luz Elena Rodriguez
        // Solicitar datos al usuario
    System.out.println("Ingrese el codigo:");
    String codigo = scanner.nextLine();
    System.out.println("Ingrese el nombre:");
    String nombre = scanner.nextLine();
    System.out.println("Ingrese la fecha de nacimiento (formato dia/mes/año):");
    String fechaNacimientoStr = scanner.nextLine();
    System.out.println("Ingrese la zona:");
    String zona = scanner.nextLine();
    System.out.println("Ingrese las ventas:");
    String ventas = scanner.nextLine();

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date fechaNacimiento = null;

    try {
        fechaNacimiento = dateFormat.parse(fechaNacimientoStr);
    } catch (java.text.ParseException e) {
        e.printStackTrace();
        System.err.println("Error al parsear la fecha de nacimiento.");
        return;
    }

    // Agregar datos al archivo CSV
    try (CSVWriter writer = new CSVWriter(new FileWriter(archivoCSV, true))) {
        String[] datos = {codigo, nombre, dateFormat.format(fechaNacimiento), zona, ventas};
        writer.writeNext(datos);
        System.out.println("Datos del vendedor agregados exitosamente.");
    } catch (IOException e) {
        e.printStackTrace();
        System.err.println("Error al escribir en el archivo CSV.");
    }
}
  
    private static void borrarVendedor() { //Punto 3 realizado por Adriana Leon Camcho
          try {
        // Leer el archivo CSV y cargar su contenido en una lista
        List<String> lineas = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(archivoCSV));
        String linea;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce el codigo del vendedor que deseas eliminar: ");
        String codigoAEliminar = scanner.nextLine().trim();

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length > 0 && partes[0].trim().equals(codigoAEliminar)) {
                continue; // Saltar esta línea (eliminarla)
            }
            lineas.add(linea);
        }
        br.close();

        // Escribir los datos actualizados en el archivo CSV
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivoCSV));
        for (String nuevaLinea : lineas) {
            bw.write(nuevaLinea);
            bw.newLine();
        }
        bw.close();

        System.out.println("El vendedor con codigo " + codigoAEliminar + " ha sido eliminado.");

    } catch (IOException e) {
        e.printStackTrace();
    }
    
    }


    private static void modificarVendedor()  { //Punto 4 realizado por Adriana Leon
        try {
            // Leer el archivo CSV y cargar su contenido en una lista
            List<String> lineas = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(archivoCSV));
            String linea;
            Scanner scanner = new Scanner(System.in);

            System.out.print("Introduce el codigo del vendedor que deseas editar: ");
            String codigoAEditar = scanner.nextLine().trim();

            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
            br.close();

            // Buscar el código en los datos cargados
            boolean encontrado = false;
            for (int i = 0; i < lineas.size(); i++) {
                String[] partes = lineas.get(i).split(",");
                if (partes.length > 0 && partes[0].trim().equals(codigoAEditar)) {
                    encontrado = true;
                    System.out.println("Vendedor encontrado:");
                    System.out.println("1. Editar codigo");
                    System.out.println("2. Editar nombre");
                    System.out.println("3. Editar fecha");
                    System.out.println("4. Editar zona");
                    System.out.println("5. Editar ventas");
                    System.out.print("Selecciona el número de la opción que deseas editar: ");
                    int opcion = Integer.parseInt(scanner.nextLine());
                    
                    if (opcion >= 1 && opcion <= 5) {
                        System.out.print("Nuevo valor: ");
                        String nuevoValor = scanner.nextLine();
                        partes[opcion - 1] = nuevoValor;
                        lineas.set(i, String.join(",", partes));
                        System.out.println("Edicion completada.");
                    } else {
                        System.out.println("Opcion no valida.");
                    }
                    break;
                }
            }

            // Si no se encontró el vendedor
            if (!encontrado) {
                System.out.println("Vendedor no encontrado.");
            } else {
                // Escribir los datos actualizados en el archivo CSV
                BufferedWriter bw = new BufferedWriter(new FileWriter(archivoCSV));
                for (String nuevaLinea : lineas) {
                    bw.write(nuevaLinea);
                    bw.newLine();
                }
                bw.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private static void consultarVendedores()  { //Punto 5 realizado por Adriana Leon
       try {
            BufferedReader br = new BufferedReader(new FileReader(archivoCSV));
            String linea;
            Scanner scanner = new Scanner(System.in);

            System.out.print("Introduce la zona a la que pertenecen los vendedores: ");
            
            String zonaConsultar = scanner.nextLine().trim();

            boolean encontrado = false;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length >= 4 && partes[3].trim().equalsIgnoreCase(zonaConsultar)) {
                    encontrado = true;
                    System.out.println("Codigo: " + partes[0]);
                    System.out.println("Nombre: " + partes[1]);
                    System.out.println("Fecha: " + partes[2]);
                    System.out.println("Zona: " + partes[3]);
                    System.out.println("Ventas: " + partes[4]);
                    System.out.println("-------------------------");
                }
            }
            br.close();

            if (!encontrado) {
                System.out.println("No se encontraron vendedores en la zona especificada.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } 
        
    }
    
}
