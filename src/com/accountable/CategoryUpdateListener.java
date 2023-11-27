package com.accountable;

import java.util.List;

public interface CategoryUpdateListener {
    void updateCategories(List<String> updatedCategories);
    void updateBudgetCategories(List<String> updatedCategories, List<Double> updatedBudgets);
}


