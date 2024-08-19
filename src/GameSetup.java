package src;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class GameSetup {
    public static void main(String[] args) {


        String baseDir = "";  // Заменить на реальный путь к вашей папке Games

        // StringBuilder для хранения лога
        StringBuilder log = new StringBuilder();

        // Создание директорий
        createDir(baseDir + "/src", log);
        createDir(baseDir + "/res", log);
        createDir(baseDir + "/savegames", log);
        createDir(baseDir + "/temp", log);

        // Создание поддиректорий
        createDir(baseDir + "/src/main", log);
        createDir(baseDir + "/src/test", log);
        createDir(baseDir + "/res/drawables", log);
        createDir(baseDir + "/res/vectors", log);
        createDir(baseDir + "/res/icons", log);

        // Создание файлов
        createFile(baseDir + "/src/main/Main.java", log);
        createFile(baseDir + "/src/main/Utils.java", log);
        createFile(baseDir + "/temp/temp.txt", log);

        // Запись лога в temp.txt
        writeLog(baseDir + "/temp/temp.txt", log.toString());
    }

    private static void createDir(String path, StringBuilder log) {
        File dir = new File(path);
        if (dir.mkdirs()) {
            log.append("Directory created: ").append(path).append("\n");
        } else {
            log.append("Failed to create directory: ").append(path).append("\n");
        }
    }

    private static void createFile(String path, StringBuilder log) {
        File file = new File(path);
        try {
            if (file.createNewFile()) {
                log.append("File created: ").append(path).append("\n");
            } else {
                log.append("Failed to create file: ").append(path).append("\n");
            }
        } catch (IOException e) {
            log.append("Error creating file: ").append(path).append(" - ").append(e.getMessage()).append("\n");
        }
    }

    private static void writeLog(String filePath, String log) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(log);
        } catch (IOException e) {
            System.out.println("Error writing log to file: " + e.getMessage());
        }
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }


    public static void zipFiles(String zipFilePath, List<String> filePaths) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath))) {
            for (String filePath : filePaths) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    ZipEntry zipEntry = new ZipEntry(new File(filePath).getName());
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    System.out.println("Failed to add file to zip: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to create zip file: " + e.getMessage());
        }
    }




}