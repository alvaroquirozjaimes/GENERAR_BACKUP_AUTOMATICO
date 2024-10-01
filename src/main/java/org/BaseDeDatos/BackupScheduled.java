package org.BaseDeDatos;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ExecutorService; // Importa la interfaz para manejar un grupo de hilos
import java.util.concurrent.Executors; // Importa la clase para crear instancias de ExecutorService
import java.util.concurrent.TimeUnit; // Importa la clase para manejar unidades de tiempo

// Clase de configuración de Spring para la programación de respaldos
@Configuration
@EnableScheduling // Habilita la programación de tareas en esta clase
public class BackupScheduled {

    // Método programado que se ejecuta todos los días a la medianoche
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleBackup() {
        // Configuración de las bases de datos para las que se realizará el respaldo
        DataBaseConfig dbConfigA = new DataBaseConfig("admin", "admin", "systemdb");
        DataBaseConfig dbConfigB = new DataBaseConfig("blog", "blog", "blogdb");
        DataBaseConfig dbConfigC = new DataBaseConfig("admin", "admin", "estadisticasdb");

        ExecutorService service = null; // Inicializa el servicio de ejecución

        try {
            // Crea un ExecutorService con un grupo de hilos programados
            service = Executors.newScheduledThreadPool(10);
            // Envía tareas al ExecutorService para realizar respaldos en paralelo
            service.submit(() -> BackupTask.backup(dbConfigA)); // Respaldo para systemdb
            service.submit(() -> BackupTask.backup(dbConfigB)); // Respaldo para blogdb
            service.submit(() -> BackupTask.backup(dbConfigC)); // Respaldo para estadisticasdb
        } finally {
            // Asegura que el ExecutorService se cierre correctamente
            if (service != null) {
                service.shutdown(); // Inicia el proceso de apagado del ExecutorService
                try {
                    // Espera hasta 60 segundos para que todas las tareas finalicen
                    if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                        service.shutdownNow(); // Si no finalizan, fuerza el cierre
                    }
                } catch (InterruptedException e) {
                    service.shutdownNow(); // Si hay una interrupción, fuerza el cierre
                }
            }
        }
    }
}
