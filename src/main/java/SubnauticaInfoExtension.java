import java.util.HashMap;
import java.util.Map;

public class SubnauticaInfoExtension {
    private static final Map<ItemRegistration, Integer> spaceMap = new HashMap<>();
    static {
        spaceMap.put(ItemRegistration.get("Creepvine Seed Cluster"),4);
        spaceMap.put(ItemRegistration.get("Creepvine Sample"),4);
        spaceMap.put(ItemRegistration.get("Blood Oil"),4);
    }

    public static void printInfoFor(CalculationStorage storage){
        int spaceRaw = 0;
        for (ItemRegistration.Stack stack : storage.getRaw()) {
            int space = spaceMap.getOrDefault(stack.getItem(),1);
            spaceRaw+=stack.getSize()*space;
        }
        System.out.println("");
        System.out.println("Recipe Information: ");
        System.out.println("Produces: " + storage.getResult());
        int lockers = (int) Math.ceil(spaceRaw/(6.0*8.0));
        System.out.println("Needs: " + spaceRaw + " slots of space using: " + lockers + " vanilla lockers.");
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
        }    }
}
