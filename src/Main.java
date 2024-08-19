package src;



import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Путь к папке savegames
        String saveDir = "/home/igor/Загрузки";  // Заменить на ваш путь

        // Создание экземпляров GameProgress
        GameProgress game1 = new GameProgress(100, 2, 10, 50.5);
        GameProgress game2 = new GameProgress(80, 3, 11, 100.0);
        GameProgress game3 = new GameProgress(50, 4, 12, 150.75);

        // Сохранение игр
        GameSetup.saveGame(saveDir + "/save1.dat", game1);
        GameSetup.saveGame(saveDir + "/save2.dat", game2);
        GameSetup.saveGame(saveDir + "/save3.dat", game3);

        // Создание списка файлов для архивации
        List<String> saveFiles = new ArrayList<>();
        saveFiles.add(saveDir + "/save1.dat");
        saveFiles.add(saveDir + "/save2.dat");
        saveFiles.add(saveDir + "/save3.dat");

        // Архивирование файлов
        GameSetup.zipFiles(saveDir + "/saves.zip", saveFiles);

        // Удаление исходных файлов сохранений
        for (String filePath : saveFiles) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Deleted file: " + filePath);
            } else {
                System.out.println("Failed to delete file: " + filePath);
            }
        }
    }
}
