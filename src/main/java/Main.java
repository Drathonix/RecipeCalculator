import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        loadTXT("subnautica_realistic_rocket.txt");
        System.out.println("");
        System.out.println("Registered Recipes: ");
        printRecipes();
        CalculationStorage storage = new CalculationStorage("Fuel Reserve",1);
        storage.calculate();
        System.out.println("");
        System.out.println("Produces: " + storage.getResult());
        System.out.println("Raw Materials: ");
        for (ItemRegistration.Stack value : storage.getRaw()) {
            System.out.println("    " + value);
        }
        System.out.println("Crafted Materials: ");
        for (ItemRegistration.Stack value : storage.getIntermediates()) {
            System.out.println("    " + value);
        }
        System.out.println("Consumed: ");
        for (ItemRegistration.Stack value : storage.getConsumed().values()) {
            System.out.println("    " + value);
        }
        System.out.println("Recipes Involved: ");
        for (RecipeRegistration.Stack value : storage.getRecipes().values()) {
            System.out.println("    " + value);
        }
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
                System.out.println("Reading: " + line);
                RecipeRegistration.fromLine(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
