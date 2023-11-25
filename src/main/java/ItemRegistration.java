import java.util.*;

public class ItemRegistration {
    private static final Map<String, ItemRegistration> registry = new HashMap<>();

    public static ItemRegistration get(String providedName){
        String key = providedName.toLowerCase().replace(" ","_");
        if(registry.containsKey(key)){
            return registry.get(key);
        }
        else{
            ItemRegistration out = new ItemRegistration(key,providedName);
            registry.put(key,out);
            return out;
        }
    }

    private final String key;
    private final String displayName;

    private final List<RecipeRegistration> providers = new ArrayList<>();
    private final List<RecipeRegistration> consumers = new ArrayList<>();

    public ItemRegistration(String key, String providedName) {
        this.key=key;
        this.displayName=providedName;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void addProvidingRecipe(RecipeRegistration recipe) {
        providers.add(recipe);
    }

    public void addConsumingRecipe(RecipeRegistration recipe) {
        consumers.add(recipe);
    }

    public List<RecipeRegistration> getProviders() {
        return providers;
    }

    public List<RecipeRegistration> getConsumers() {
        return consumers;
    }

    public boolean hasProvider() {
        return !providers.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemRegistration that = (ItemRegistration) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public static class Stack{
        private final ItemRegistration item;
        private int size;

        public Stack(ItemRegistration item){
            this.item=item;
            this.size=0;
        }
        public Stack(ItemRegistration item, int size){
            this.item=item;
            this.size=size;
        }

        public ItemRegistration getItem() {
            return item;
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
            return size + "x " + item.getDisplayName();
        }
    }
}
