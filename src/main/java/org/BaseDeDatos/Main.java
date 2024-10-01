package org.BaseDeDatos;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Clase principal donde se ejecuta la lógica de las copias de seguridad
public class Main {
    public static void main(String[] args) {

        // Instancia de BackupTask, aunque no es usada directamente aquí
        BackupTask bt = new BackupTask();

        // Configuración de tres bases de datos con sus respectivos credenciales y nombres
        DataBaseConfig dbConfigA = new DataBaseConfig("admin", "admin", "systemdb");
        DataBaseConfig dbConfigB = new DataBaseConfig("blog", "blog", "blogdb");
        DataBaseConfig dbConfigC = new DataBaseConfig("admin", "admin", "estadisticasdb");

        // Declaración del ExecutorService, que gestionará los hilos para la ejecución de las tareas
        ExecutorService service = null;

        try {
            // Crear un pool de 10 hilos. Cada tarea de copia de seguridad se ejecutará en un hilo separado.
            service = Executors.newScheduledThreadPool(10);

            // Ejecutar las tareas de backup para cada base de datos en hilos separados
            // Se usa un lambda (expresión lambda) para pasar la tarea de backup a cada hilo
            service.submit(() -> BackupTask.backup(dbConfigA));  // Tarea de backup para dbConfigA
            service.submit(() -> BackupTask.backup(dbConfigB));  // Tarea de backup para dbConfigB
            service.submit(() -> BackupTask.backup(dbConfigC));  // Tarea de backup para dbConfigC
        } finally {
            // En el bloque finally, nos aseguramos de cerrar el ExecutorService
            // si no es nulo, esto evita fugas de recursos.
            if (service != null) service.shutdown();
        }
    }
}
