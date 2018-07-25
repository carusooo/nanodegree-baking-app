package com.example.macarus0.bakingapp.View;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.macarus0.bakingapp.R;
import com.example.macarus0.bakingapp.ViewModel.RecipeViewModel;


public class RecipeListFragment extends Fragment {

    private RecipeAdapter mRecipeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeViewModel mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        final View rootView = inflater.inflate(R.layout.recipe_list_fragment, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(), 1);

        mRecipeAdapter = new RecipeAdapter();
        mRecipeViewModel.getAllRecipes().observe(this, recipes -> mRecipeAdapter.setRecipes(recipes));
        try {
            mRecipeAdapter.setRecipeClickHandler((RecipeAdapter.RecipeClickHandler) getContext());
        } catch (ClassCastException e) {
            throw new ClassCastException(getContext().toString()
                    + " must implement RecipeClickHandler");
        }
        recyclerView.setLayoutManager(mGridLayoutManager);
        recyclerView.setAdapter(mRecipeAdapter);
        return rootView;
    }


}
