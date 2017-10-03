package fr.intech.s5.tp.eatthatfood.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ListRecipe implements Parcelable {

    private Integer recipeId;
    private String recipeName;
    private String image;
    private String description;

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String RecipeName) {
        this.recipeName = RecipeName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.recipeId);
        dest.writeString(this.recipeName);
        dest.writeString(this.image);
        dest.writeString(this.description);
    }

    public ListRecipe() {
    }

    protected ListRecipe(Parcel in) {
        this.recipeId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.recipeName = in.readString();
        this.image = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<ListRecipe> CREATOR = new Parcelable.Creator<ListRecipe>() {
        @Override
        public ListRecipe createFromParcel(Parcel source) {
            return new ListRecipe(source);
        }

        @Override
        public ListRecipe[] newArray(int size) {
            return new ListRecipe[size];
        }
    };
}