import java.util.ArrayList;
import java.util.List;

public class RecipeRegistration {
    private static final List<RecipeRegistration> recipes = new ArrayList<>();
    public static RecipeRegistration fromLine(String line) {
        int stage = 0;
        StringBuilder output = new StringBuilder();
        StringBuilder input = new StringBuilder();
        List<ItemRegistration.Stack> ingredients = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (stage == 0) {
                if (c == ':') {
                    stage++;
                } else {
                    output.append(c);
                }
            }
            else if (stage == 1) {
                if (c == ',' || c == '\n') {
                    String in = input.toString().strip();
                    if (!in.isEmpty()) {
                        int amount = 1;
                        int begin = -1;
                        if(Character.isDigit(in.charAt(0))){
                            begin = in.indexOf('x');
                            amount = Integer.parseInt(in.substring(0,begin));
                        }
                        ingredients.add(new ItemRegistration.Stack(ItemRegistration.get(in.substring(begin+1).strip()),amount));
                    }
                    input = new StringBuilder();
                } else {
                    input.append(c);
                }
            }
        }
        String in = input.toString().strip();
        if (!in.isEmpty()) {
            int amount = 1;
            int begin = -1;
            if(Character.isDigit(in.charAt(0))){
                begin = in.indexOf('x');
                amount = Integer.parseInt(in.substring(0,begin));
            }
            ingredients.add(new ItemRegistration.Stack(ItemRegistration.get(in.substring(begin+1).strip()),amount));
        }
        RecipeRegistration reg = new RecipeRegistration(output.toString().strip(),ingredients);
        recipes.add(reg);
        return reg;
    }

    public static List<RecipeRegistration> getRecipes() {
        return recipes;
    }

    private final ItemRegistration.Stack output;
    private final List<ItemRegistration.Stack> inputs = new ArrayList<>();

    public RecipeRegistration(String output, List<ItemRegistration.Stack> ingredients){
        String in = output.strip();
        if (!in.isEmpty()) {
            int amount = 1;
            int begin = -1;
            if(Character.isDigit(in.charAt(0))){
                begin = in.indexOf('x');
                amount = Integer.parseInt(in.substring(0,begin));
            }
            this.output = new ItemRegistration.Stack(ItemRegistration.get(in.substring(begin+1).strip()),amount);
        }
        else{
            throw new IllegalArgumentException("Empty Result.");
        }
        this.output.getItem().addProvidingRecipe(this);
        for (ItemRegistration.Stack ingredient : ingredients) {
            ingredient.getItem().addConsumingRecipe(this);
            this.inputs.add(ingredient);
        }
    }

    public ItemRegistration.Stack getOutput() {
        return output;
    }

    public String getKey(){
        return output.getItem().getKey();
    }

    public List<ItemRegistration.Stack> getInputs() {
        return inputs;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        if(output.getSize() > 1){
            out.append(output.getSize()).append("x ");
        }
        out.append(output.getItem().getDisplayName()).append(": ");
        for (int i = 0; i < inputs.size(); i++) {
            ItemRegistration.Stack stack = inputs.get(i);
            if(stack.getSize() > 1){
                out.append(stack.getSize()).append("x ");
            }
            out.append(stack.getItem().getDisplayName());
            if(i < inputs.size()-1){
                out.append(", ");
            }
        }
        return out.toString();
    }

    public ItemRegistration getOutputItem() {
        return output.getItem();
    }

    public static class Stack{
        private final RecipeRegistration recipe;
        private int size;

        public Stack(RecipeRegistration recipe){
            this.recipe =recipe;
            this.size=0;
        }
        public Stack(RecipeRegistration recipe, int size){
            this.recipe =recipe;
            this.size=size;
        }

        public RecipeRegistration getRecipe() {
            return recipe;
        }

        public int getSize() {
            return size;
        }

        public void grow(int amount){
            this.size+=amount;
        }

        public void shrink(int amount){
            this.size-=amount;
        }

        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public String toString() {
            return size + "x (" + recipe + ")";
        }
    }
}
