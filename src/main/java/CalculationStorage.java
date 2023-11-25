import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculationStorage {
    private final Map<ItemRegistration, ItemRegistration.Stack> consumed = new HashMap<>();

    private final Map<ItemRegistration, RecipeRegistration.Stack> recipes = new HashMap<>();
    private final ItemRegistration result;
    private final int amount;

    public CalculationStorage(String output, int amount){
        result = ItemRegistration.get(output);
        this.amount = amount;
    }

    public void calculate(){
        calculate(result.getProviders().get(0), amount);
    }

    private void calculate(RecipeRegistration recipe, int times){
        grow(recipe,times);
        for (ItemRegistration.Stack input : recipe.getInputs()) {
            int amount = input.getSize();
            ItemRegistration item = input.getItem();
            if(item.hasProvider()){
                RecipeRegistration provider = item.getProviders().get(0);
                double fraction = (double) amount / provider.getOutput().getSize();
                calculate(provider, (int) Math.ceil(fraction*times));
            }
            grow(item,amount*times);
        }
    }

    private void grow(RecipeRegistration recipe, int times) {
        RecipeRegistration.Stack stack = recipes.get(recipe.getOutputItem());
        if(stack == null){
            recipes.put(recipe.getOutputItem(), new RecipeRegistration.Stack(recipe,times));
        }
        else{
            stack.grow(times);
        }
    }

    private void grow(ItemRegistration item, int times){
        ItemRegistration.Stack stack = consumed.get(item);
        if(stack == null){
            consumed.put(item, new ItemRegistration.Stack(item,times));
        }
        else{
            stack.grow(times);
        }
    }

    public Map<ItemRegistration, ItemRegistration.Stack> getConsumed() {
        return consumed;
    }

    public List<ItemRegistration.Stack> getIntermediates(){
        List<ItemRegistration.Stack> out = new ArrayList<>();
        for (ItemRegistration.Stack value : consumed.values()) {
            if(value.getItem().hasProvider()){
                out.add(value);
            }
        }
        return out;
    }

    public List<ItemRegistration.Stack> getRaw(){
        List<ItemRegistration.Stack> out = new ArrayList<>();
        for (ItemRegistration.Stack value : consumed.values()) {
            if(!value.getItem().hasProvider()){
                out.add(value);
            }
        }
        return out;
    }

    public Map<ItemRegistration, RecipeRegistration.Stack> getRecipes() {
        return recipes;
    }

    public ItemRegistration.Stack getResult() {
        return new ItemRegistration.Stack(result,amount);
    }
}
