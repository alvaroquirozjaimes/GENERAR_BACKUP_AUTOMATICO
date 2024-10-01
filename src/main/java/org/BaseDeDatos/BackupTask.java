package org.BaseDeDatos;
import java.io.File; // Importa la clase File para manejar archivos y directorios
import java.io.IOException; // Importa la clase IOException para manejar excepciones de entrada/salida
import java.text.SimpleDateFormat; // Importa la clase SimpleDateFormat para formatear fechas
import java.util.Date; // Importa la clase Date para manejar fechas

// Clase que maneja la tarea de respaldo de bases de datos
public class BackupTask {
    // Ruta donde se almacenarán los respaldos
    private static final String FOLDER_PATH = "C:/backups";

    // Método estático y sincronizado que inicia el proceso de respaldo
    public static synchronized void backup(DataBaseConfig dbconfig) {
        // Imprime la fecha y hora en que comienza el respaldo
        System.out.println("Backup Started at: " + new Date());

        // Obtiene la fecha actual y la formatea en el formato "yyyy-MM-dd"
        Date backupDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String backupDateStr = format.format(backupDate);

        // Crea un objeto File para la carpeta de respaldo
        File file = new File(FOLDER_PATH);
        // Crea la carpeta si no existe
        file.mkdir();

        // Crea el nombre del archivo de respaldo usando el nombre de la base de datos y la fecha
        String saveFileName = "backup" + dbconfig.getDbdname() + "_" + backupDateStr + ".sql";
        // Construye la ruta completa del archivo de respaldo
        String savePath = FOLDER_PATH + File.separator + saveFileName;

        // Comando a ejecutar en la línea de comandos para realizar el respaldo
        // Esta línea está comentada porque se usará una línea de prueba en su lugar
        // String executeCmd = "mysqldump -u " + dbconfig.getUser() + " -p" + dbconfig.getPassword()
        //        + " --databases " + dbconfig.getDbdname()
        //        + " -r" + savePath;

        // Comando de prueba que simplemente crea un archivo vacío en la ubicación de respaldo
        String executeCmd = "cmd.exe /c echo. 2>" + savePath;

        // Llama al método para ejecutar el comando
        execCommand(executeCmd);
    }

    // Método privado y estático que ejecuta un comando en la línea de comandos
    private static synchronized void execCommand(String executeCmd) {
        // Imprime el comando que se va a ejecutar
        System.out.println(executeCmd);
        Process runtimeProcess = null;
        try {
            // Ejecuta el comando en un nuevo proceso
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        } catch (IOException e) {
            // Maneja cualquier excepción de entrada/salida
            e.printStackTrace();
        }

        int processComplete = 0;
        try {
            // Espera a que el proceso se complete y obtiene el estado del proceso
            processComplete = runtimeProcess.waitFor();
        } catch (InterruptedException e) {
            // Maneja cualquier interrupción al esperar el proceso
            e.printStackTrace();
        }

        // Verifica si el proceso se completó correctamente
        if (processComplete == 0) {
            // Imprime la fecha y hora cuando se completa el comando
            System.out.println("Command complete at: " + new Date());
        } else {
            // Imprime un mensaje de error si el comando falla
            System.out.println("Command Failure");
        }
    }
}

