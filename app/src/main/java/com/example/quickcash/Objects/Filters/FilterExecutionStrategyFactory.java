package com.example.quickcash.Objects.Filters;

import java.util.List;

public class FilterExecutionStrategyFactory {
    private List<IFilter> filters;
    public static final int MAX_UNCHAINED_QUERIES_SIZE = 3;

    public FilterExecutionStrategyFactory() {}
    public FilterExecutionStrategyFactory(List<IFilter> filters) {
        this.filters = filters;
    }

    public List<IFilter> getFilters() {
        return filters;
    }
    public void setFilters(List<IFilter> filters) {
        this.filters = filters;
    }

    public FilterExecutionStrategy getPreferredExecutionStrategy() {
        boolean shouldForceLoopStrategy = false;
        for (IFilter filter : filters) {
            if (filter instanceof DistanceFilter) {
                shouldForceLoopStrategy = true;
                break;
            }
            if (filter instanceof TitleFilter) {
                shouldForceLoopStrategy = true; // Favor the .contains() of the client-side version of the title filter
                break;
            }
        }
        if (shouldForceLoopStrategy) {
            // Distance filter is always executed in the Loop strategy
            return setUpStrategy(new FilterExecutionLoopStrategy());
        }

        if (filters.size() > MAX_UNCHAINED_QUERIES_SIZE) {
            return setUpStrategy(new FilterExecutionLoopStrategy());
        } else {
            return setUpStrategy(new FilterExecutionSetIntersectionStrategy());
        }
    }

    private FilterExecutionStrategy setUpStrategy(FilterExecutionStrategy strategy) {
        strategy.setFilters(filters);
        return strategy;
    }
}
