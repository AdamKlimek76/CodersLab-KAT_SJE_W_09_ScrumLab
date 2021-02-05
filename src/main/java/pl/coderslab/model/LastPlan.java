package pl.coderslab.model;

public class LastPlan {

    private int id;
    private String planName;
    private String dayName;
    private String mealName;
    private String recipeName;
    private String recipeDestination;

    public LastPlan(int id, String planName, String dayName, String mealName, String recipeName, String recipeDestination) {
        this.id = id;
        this.planName = planName;
        this.dayName = dayName;
        this.mealName = mealName;
        this.recipeName = recipeName;
        this.recipeDestination = recipeDestination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeDestination() {
        return recipeDestination;
    }

    public void setRecipeDestination(String recipeDestination) {
        this.recipeDestination = recipeDestination;
    }
}
