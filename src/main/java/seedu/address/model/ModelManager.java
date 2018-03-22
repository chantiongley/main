package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CatalogueChangedEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Catalogue catalogue;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given catalogue and userPrefs.
     */
    public ModelManager(ReadOnlyCatalogue catalogue, UserPrefs userPrefs) {
        super();
        requireAllNonNull(catalogue, userPrefs);

        logger.fine("Initializing with address book: " + catalogue + " and user prefs " + userPrefs);

        this.catalogue = new Catalogue(catalogue);
        filteredPersons = new FilteredList<>(this.catalogue.getPersonList());
    }

    public ModelManager() {
        this(new Catalogue(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyCatalogue newData) {
        catalogue.resetData(newData);
        indicateCatalogueChanged();
    }

    @Override
    public ReadOnlyCatalogue getCatalogue() {
        return catalogue;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateCatalogueChanged() {
        raise(new CatalogueChangedEvent(catalogue));
    }

    @Override
    public synchronized void deletePerson(Person target) throws PersonNotFoundException {
        catalogue.removePerson(target);
        indicateCatalogueChanged();
    }

    @Override
    public synchronized void addPerson(Person person) throws DuplicatePersonException {
        catalogue.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateCatalogueChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        catalogue.updatePerson(target, editedPerson);
        indicateCatalogueChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code catalogue}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return catalogue.equals(other.catalogue)
                && filteredPersons.equals(other.filteredPersons);
    }

}
