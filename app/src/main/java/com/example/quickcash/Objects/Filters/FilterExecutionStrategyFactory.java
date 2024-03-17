package com.example.quickcash.Objects.Filters;

import java.util.List;

/**
 * A factory for instantiating the appropriate strategy for a given set of filters
 */
public class FilterExecutionStrategyFactory {
    private List<IFilter> filters;
    /**
     * The constant MAX_UNCHAINED_QUERIES_SIZE.
     * If you set more filters than this it will default to the client-side loop strategy because you will be querying too much
     * anyway.
     */
    public static final int MAX_UNCHAINED_QUERIES_SIZE = 3;

    /**
     * Instantiates a new Filter execution strategy factory.
     */
    public FilterExecutionStrategyFactory() {}

    /**
     * Instantiates a new Filter execution strategy factory with te set of filters you wish to use.
     *
     * @param filters the filters you wish to use
     */
    public FilterExecutionStrategyFactory(List<IFilter> filters) {
        this.filters = filters;
    }

    /**
     * Gets the list of currently active filters.
     *
     * @return the list of currently active filters
     */
    public List<IFilter> getFilters() {
        return filters;
    }

    /**
     * Sets the list of currently active filters.
     *
     * @param filters the list of currently active filters
     */
    public void setFilters(List<IFilter> filters) {
        this.filters = filters;
    }

    /**
     * Gets the preferred execution strategy, derived from the list of filters you put in.
     *
     * @return the preferred execution strategy
     */
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
