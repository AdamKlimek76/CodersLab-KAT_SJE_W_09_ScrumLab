package pl.coderslab.model;

public class PlanDetails {
    private int planId;
    private int recipeId;
    private String planName;
    private String planDescription;
    private String recipeName;
    private String recipeIngredients;
    private String recipeDescription;
    private int preparationTime;
    private String recipePreparation;
    private String dayName;
    private String mealName;

    public PlanDetails(int planId, int recipeId, String planName, String planDescription, String recipeName, String recipeIngredients, String recipeDescription, int preparationTime, String recipePreparation, String dayName, String mealName) {
        this.planId = planId;
        this.recipeId = recipeId;
        this.planName = planName;
        this.planDescription = planDescription;
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
        this.recipeDescription = recipeDescription;
        this.preparationTime = preparationTime;
        this.recipePreparation = recipePreparation;
        this.dayName = dayName;
        this.mealName = mealName;
    }

    public PlanDetails(){}

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getRecipePreparation() {
        return recipePreparation;
    }

    public void setRecipePreparation(String recipePreparation) {
        this.recipePreparation = recipePreparation;
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

    @Override
    public String toString() {
        return "PlanDetails{" +
                "planId=" + planId +
                ", recipeId=" + recipeId +
                ", planName='" + planName + '\'' +
                ", planDescription='" + planDescription + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", recipeIngredients='" + recipeIngredients + '\'' +
                ", recipeDescription='" + recipeDescription + '\'' +
                ", preparationTime=" + preparationTime +
                ", recipePreparation='" + recipePreparation + '\'' +
                ", dayName='" + dayName + '\'' +
                ", mealName='" + mealName + '\'' +
                '}';
    }
}
