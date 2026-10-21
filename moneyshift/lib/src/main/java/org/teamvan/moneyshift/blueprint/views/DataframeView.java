// DO NOT EDIT: generated from Rerun type definitions.
package org.teamvan.moneyshift.blueprint.views;

/**
 * A view to display any data in a tabular form.
 * 
 * Any data from the store can be shown, using a flexible, user-configurable query.
 * 
 * See <a href="https://rerun.io/docs/concepts/query-and-transform/dataframe-queries">Dataframe queries</a> to learn more about the query model.
 * 
 */
public record DataframeView(
    /**
     * Query of the dataframe.
     */
    org.teamvan.moneyshift.blueprint.archetypes.DataframeQuery query
) {
    public static final java.lang.String VIEW_NAME = "rerun.blueprint.views.DataframeView";

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private org.teamvan.moneyshift.blueprint.archetypes.DataframeQuery query;
        private Builder() {}

        public Builder withQuery(org.teamvan.moneyshift.blueprint.archetypes.DataframeQuery value) {
            this.query = value;
            return this;
        }

        public DataframeView build() {
            return new DataframeView(query);
        }
    }
}
