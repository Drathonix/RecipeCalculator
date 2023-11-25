import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        loadTXT("subnautica_death_run.txt");
        loadTXT("subnautica_realistic_rocket.txt");
        loadTXT("subnautica1_default.txt");
        System.out.println("");
        System.out.println("Registered Recipes: ");
        printRecipes();
        CalculationStorage storage = new CalculationStorage("Neptune",1);
        storage.calculate();
        SubnauticaInfoExtension.printInfoFor(storage);
    }

    private static void printRecipes() {
        for (RecipeRegistration recipe : RecipeRegistration.getRecipes()) {
            System.out.println(recipe);
        }
    }

    public static void loadTXT(String file){
        try {
            Scanner scanner = new Scanner(new File(file));
            System.out.println("Loading Recipes From " + file);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.isEmpty()){
                    continue;
                }
                System.out.println("Reading: " + line);
                RecipeRegistration.fromLine(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
