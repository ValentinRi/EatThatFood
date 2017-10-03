package fr.intech.s5.tp.eatthatfood.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import fr.intech.s5.tp.eatthatfood.model.ListRecipe;

public class Recipes implements Parcelable {

    private List<ListRecipe> listRecipe = null;

    public List<ListRecipe> getListRecipe() {
        return listRecipe;
    }

    public void setListRecipe(List<ListRecipe> listRecipe) {
        this.listRecipe = listRecipe;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.listRecipe);
    }

    public Recipes() {
    }

    protected Recipes(Parcel in) {
        this.listRecipe = in.createTypedArrayList(ListRecipe.CREATOR);
    }

    public static final Parcelable.Creator<Recipes> CREATOR = new Parcelable.Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel source) {
            return new Recipes(source);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };
}