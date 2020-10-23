package seedu.address.model.item.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.item.Item;

public class NameContainsKeywordsPredicate implements Predicate<Item> {

    private final List<String> keywords;

    /**
     * Constructor for NameContainsKeywordsPredicate.
     *
     * @param keywords List of keywords
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(item.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
