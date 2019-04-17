package com.vaadin.flow.component.grid.demo;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.DropLocation;
import com.vaadin.flow.component.grid.DropMode;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridDragStartEvent;
import com.vaadin.flow.component.grid.GridDropEvent;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.HeaderRow.HeaderCell;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.demo.DemoView;
import com.vaadin.flow.router.Route;

@Route("vaadin-grid")
@HtmlImport("grid-demo-styles.html")
public class GridDemo extends DemoView {

    public static final List<Person> items = createItems();

    // begin-source-example
    // source-example-heading: Grid example model

    /**
     * Example object.
     */
    public static class Person implements Cloneable {
        private int id;
        private String firstName;
        private String lastName;
        private int age;
        private Address address;
        private String phoneNumber;
        private MaritalStatus maritalStatus;
        private LocalDate birthdate;
        private boolean isSubscriber;
        private String email;

        public Person() {

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getfirstName() {
            return firstName;
        }

        public void setfirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public MaritalStatus getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(MaritalStatus maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public LocalDate getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(LocalDate birthdate) {
            this.birthdate = birthdate;
        }

        public String getImage() {
            return "https://randomuser.me/api/portraits/men/" + getId()
                    + ".jpg";
        }

        public boolean isSubscriber() {
            return isSubscriber;
        }

        public void setSubscriber(boolean isSubscriber) {
            this.isSubscriber = isSubscriber;
        }

        public String getEmail() {
            return this.lastName + "@example.com";
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public int hashCode() {
            return id;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof Person)) {
                return false;
            }
            Person other = (Person) obj;
            return id == other.id;
        }

        public Person(int id, String firstName, String lastName, int age,
                Address address, String phoneNumber) {
            super();
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.address = address;
            this.phoneNumber = phoneNumber;
        }

        public Person(int id, String firstName, String lastName, int age,
                Address address, String phoneNumber,
                MaritalStatus maritalStatus, LocalDate birthDate) {
            super();
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.address = address;
            this.phoneNumber = phoneNumber;
            this.maritalStatus = maritalStatus;
            this.birthdate = birthDate;
        }

        @Override
        public String toString() {
            return firstName;
        }

        @Override
        public Person clone() {
            try {
                return (Person) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(
                        "The Person object could not be cloned.", e);
            }
        }
    }

    public static class Address {
        private String street;
        private int number;
        private String postalCode;
        private String city;

        public Address() {

        }

        public Address(String postalCode, String city) {
            this.postalCode = postalCode;
            this.city = city;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public String toString() {
            return String.format("%s %s", postalCode, city);
        }

    }

    public enum MaritalStatus {
        Married, Single;
    }

    // end-source-example

    public class PersonService {
        private PersonData personData = new PersonData();

        public List<Person> fetch(int offset, int limit) {
            return personData.getPersons().subList(offset, offset + limit);
        }

        public int count() {
            return personData.getPersons().size();
        }

        public List<Person> fetchAll() {
            return personData.getPersons();
        }
    }

    // begin-source-example
    // source-example-heading: Using Components in Grid

    /**
     * Component used for the cell rendering.
     */
    public static class PersonComponent extends Div {

        private String text;

        /**
         * Creates a new component with the given item.
         *
         * @param person
         *            the person to set
         */
        public PersonComponent(Person person) {
            setPerson(person);
        }

        /**
         * Sets the person for the component.
         *
         * @param person
         *            the person to be inside inside the cell
         */
        public void setPerson(Person person) {
            text = "Hi, i'm the component for " + person.getfirstName() + "!";
            setText(text);
        }
    }

    /**
     * Component used for the details row.
     */
    public static class PersonCard extends Div {

        /**
         * Constructor that takes a Person as parameter.
         *
         * @param person
         *            the person to be used inside the card
         */
        public PersonCard(Person person) {
            addClassName("custom-details");
            setId("person-card-" + person.getId());

            VerticalLayout layout1 = new VerticalLayout();
            layout1.add(new Label("Name: " + person.getfirstName()));
            layout1.add(new Label("Id: " + person.getId()));
            layout1.add(new Label("Age: " + person.getAge()));

            VerticalLayout layout2 = new VerticalLayout();
            layout2.add(
                    new Label("Street: " + person.getAddress().getStreet()));
            layout2.add(new Label(
                    "Address number: " + person.getAddress().getNumber()));
            layout2.add(new Label(
                    "Postal Code: " + person.getAddress().getPostalCode()));

            HorizontalLayout hlayout = new HorizontalLayout(layout1, layout2);
            hlayout.getStyle().set("border", "1px solid gray")
                    .set("padding", "10px").set("boxSizing", "border-box")
                    .set("width", "100%");

            add(hlayout);
        }
    }
    // end-source-example

    public static class Item {

        private String name;
        private double price;
        private LocalDateTime purchaseDate;
        private LocalDate EstimatedDeliveryDate;

        public Item() {
        }

        public Item(String name, double price, LocalDateTime purchaseDate,
                LocalDate estimatedDeliveryDate) {
            this.name = name;
            this.price = price;
            this.purchaseDate = purchaseDate;
            EstimatedDeliveryDate = estimatedDeliveryDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public LocalDateTime getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(LocalDateTime purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public LocalDate getEstimatedDeliveryDate() {
            return EstimatedDeliveryDate;
        }

        public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
            EstimatedDeliveryDate = estimatedDeliveryDate;
        }

        @Override
        public String toString() {
            return getName();
        }

    }

    public static class Order {

        private String name;
        private int numberOfOrder;
        private float price;
        private LocalDateTime purchaseDate;
        private LocalDate estimatedDeliveryDate;
        private String personName;
        private Address address;

        public Order(String name, int numberOfOrder, float price,
                LocalDateTime purchaseDate, LocalDate estimatedDeliveryDate,
                String personName, Address address) {
            this.name = name;
            this.numberOfOrder = numberOfOrder;
            this.price = price;
            this.purchaseDate = purchaseDate;
            this.estimatedDeliveryDate = estimatedDeliveryDate;
            this.personName = personName;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public LocalDateTime getPurchaseDate() {
            return purchaseDate;
        }

        public void setPurchaseDate(LocalDateTime purchaseDate) {
            this.purchaseDate = purchaseDate;
        }

        public LocalDate getEstimatedDeliveryDate() {
            return estimatedDeliveryDate;
        }

        public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
            this.estimatedDeliveryDate = estimatedDeliveryDate;
        }

        public String getPersonName() {
            return personName;
        }

        public void setPersonName(String personName) {
            this.personName = personName;
        }

        public int getNumberOfOrder() {
            return numberOfOrder;
        }

        public void setNumberOfOrder(int numberOfOrder) {
            this.numberOfOrder = numberOfOrder;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

    }

    public static class Benefit {

        private int year;
        private int quarter1;
        private int quarter2;
        private int quarter3;
        private int quarter4;

        public Benefit(int year, int quarter1, int quarter2, int quarter3,
                int quarter4) {

            this.year = year;
            this.quarter1 = quarter1;
            this.quarter2 = quarter2;
            this.quarter3 = quarter3;
            this.quarter4 = quarter4;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getQuarter1() {
            return quarter1;
        }

        public void setQuarter1(int quarter1) {
            this.quarter1 = quarter1;
        }

        public int getQuarter2() {
            return quarter2;
        }

        public void setQuarter2(int quarter2) {
            this.quarter2 = quarter2;
        }

        public int getQuarter3() {
            return quarter3;
        }

        public void setQuarter3(int quarter3) {
            this.quarter3 = quarter3;
        }

        public int getQuarter4() {
            return quarter4;
        }

        public void setQuarter4(int quarter4) {
            this.quarter4 = quarter4;
        }

    }

    @Override
    protected void initView() {
        createBasicUsage();// Basic Grid
        createGridWithLazyLoading();
        addVariantFeature();
        createArrayData();// Assigning data
        createDynamicHeight();
        createSingleSelect();
        createMultiSelect();
        createProgrammaticSelect();
        createGridWithSortableColumns();// Sorting
        createGridWithTextFieldFilters();// Filtering
        createGridWithFilters();
        createGridWithDataTypeSpecificFilters();
        createConfiguringColumns();// Configuring Columns
        createManuallyDefiningColumns();
        createFrozenColumns();
        createColumnAlignment();
        createHeaderAndFooter();// Header and footer
        createColumnGrouping();
        createHeaderAndFooterUsingComponents();
        createFormattingText();// Formatting contents
        createHtmlTemplateRenderer();
        createGridUsingComponent();// Using components
        createGridUsingComponentFilters();
        createGridWithItemDetails();
        createItemDetailsOpenedProgrammatically();
        createContextMenu();// Context Menu
        createContextSubMenu();// Context Sub Menu
        createClickListener();// Click Listener
        createDoubleClickListener();
        createBufferedEditor();// Grid Editor
        createNotBufferedEditor();
        createBufferedDynamicEditor();
        createNotBufferedDynamicEditor();
        createRowReordering();
        createDragRowsBetweenGrids();
        createDropLocations();
        createDragData();
        createDragDropFilters();

        addCard("Grid example model",
                new Label("These objects are used in the examples above"));

        addCard("Using Components", "Using Components in Grid",
                new Label("These objects are used in the examples above"));
    }

    // Grid Basics begin
    private void createBasicUsage() {
        // begin-source-example
        // source-example-heading: Grid Basics
        List<Person> personList = new ArrayList<>();

        personList.add(new Person(100, "Lucas", "Kane", 68,
                new Address("12080", "Washington"), "127-942-237"));
        personList.add(new Person(101, "Peter", "Buchanan", 38,
                new Address("93849", "New York"), "201-793-488"));
        personList.add(new Person(102, "Samuel", "Lee", 53,
                new Address("86829", "New York"), "043-713-538"));
        personList.add(new Person(103, "Anton", "Ross", 37,
                new Address("63521", "New York"), "150-813-6462"));
        personList.add(new Person(104, "Aaron", "Atkinson", 18,
                new Address("25415", "Washington"), "321-679-8544"));
        personList.add(new Person(105, "Jack", "Woodward", 28,
                new Address("95632", "New York"), "187-338-588"));

        Grid<Person> grid = new Grid<>(Person.class);
        grid.setItems(personList);

        grid.removeColumnByKey("id");

        // The Grid<>(Person.class) sorts the properties and in order to
        // reorder the properties we use the 'setColumns' method.
        grid.setColumns("firstName", "lastName", "age", "address",
                "phoneNumber");

        // end-source-example
        grid.setId("basic-usage");

        addCard("Grid Basics", grid);
    }

    private void createGridWithLazyLoading() {
        // begin-source-example
        // source-example-heading: Grid with lazy loading
        Grid<Person> grid = new Grid<>();
        PersonService personService = new PersonService();

        /*
         * This Data Provider doesn't load all items into the memory right away.
         * Grid will request only the data that should be shown in its current
         * view "window". The Data Provider will use callbacks to load only a
         * portion of the data.
         */
        CallbackDataProvider<Person, Void> provider = DataProvider
                .fromCallbacks(query -> personService
                        .fetch(query.getOffset(), query.getLimit()).stream(),
                        query -> personService.count());
        grid.setDataProvider(provider);

        grid.addColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getLastName).setHeader("Last name");
        grid.addColumn(Person::getAge).setHeader("Age");

        // end-source-example

        grid.setId("lazy-loading");

        addCard("Grid with lazy loading", grid);
    }

    private void addVariantFeature() {
        // begin-source-example
        // source-example-heading: Theme variants usage
        List<Person> personList = getItems();
        Grid<Person> grid = new Grid<>();
        grid.setItems(personList);
        grid.addColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getAge).setHeader("age");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        // end-source-example

        addVariantsDemo(() -> {
            return grid;
        } , Grid::addThemeVariants, Grid::removeThemeVariants,
                GridVariant::getVariantName, GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);
    }

    // Assigning Data Begin
    private void createArrayData() {
        // begin-source-example
        // source-example-heading: Assigning Array Data

        List<Person> personList = getItems();

        // Providing a bean-type generates columns for all of it's properties
        Grid<Person> grid = new Grid<>();
        grid.setItems(personList);

        Grid.Column<Person> firstNameColumn = grid
                .addColumn(Person::getfirstName).setHeader("First name");
        Grid.Column<Person> lastNameColumn = grid.addColumn(Person::getLastName)
                .setHeader("Last name");
        grid.addColumn(Person::getAge).setHeader("Age");

        Button addButton = new Button("Add Item", event -> {

            personList.add(new Person(10000, "X", "Y", 16,
                    new Address("95632", "New York"), "187-338-588"));
            // The dataProvider knows which List it is based on, so when you
            // edit the list
            // you edit the dataprovider.
            grid.getDataProvider().refreshAll();

        });

        Button removeButton = new Button("Remove last", event -> {

            personList.remove(personList.size() - 1);
            // The dataProvider knows which List it is based on, so when you
            // edit the list
            // you edit the dataprovider.
            grid.getDataProvider().refreshAll();
        });

        FooterRow footerRow = grid.appendFooterRow();
        footerRow.getCell(firstNameColumn).setComponent(addButton);
        footerRow.getCell(lastNameColumn).setComponent(removeButton);

        // end-source-example
        grid.setId("assigning-array-data");
        addButton.setId("assigning-array-data-add");
        removeButton.setId("assigning-array-data-remove");
        addCard("Assigning Data", "Assigning Array Data", grid, addButton,
                removeButton);
    }

    private void createDynamicHeight() {
        // begin-source-example
        // source-example-heading: Dynamic Height

        List<Person> personList = getItems();
        // Providing a bean-type generates columns for all of it's properties
        Grid<Person> grid = new Grid<>();

        // When using heightByRows, all items are fetched and
        // Grid uses all the space needed to render everything.
        grid.setHeightByRows(true);

        grid.setItems(personList);

        Grid.Column<Person> firstNameColumn = grid
                .addColumn(Person::getfirstName).setHeader("First name");
        Grid.Column<Person> lastNameColumn = grid.addColumn(Person::getLastName)
                .setHeader("Last name");
        Grid.Column<Person> ageColumn = grid.addColumn(Person::getAge)
                .setHeader("Age");

        ListDataProvider<Person> dataProvider = (ListDataProvider<Person>) grid
                .getDataProvider();

        Button addButton = new Button("Add Item", event -> {

            dataProvider.getItems().add(new Person(106, "X", "Y", 16,
                    new Address("95632", "New York"), "187-338-588"));
            // The dataProvider knows which List it is based on, so when you
            // edit the list
            // you edit the dataprovider.
            grid.getDataProvider().refreshAll();

        });

        Button removeButton = new Button("Remove last", event -> {

            personList.remove(personList.size() - 1);
            // The dataProvider knows which List it is based on, so when you
            // edit the list
            // you edit the dataprovider.
            grid.getDataProvider().refreshAll();
        });

        FooterRow footerRow = grid.appendFooterRow();
        footerRow.getCell(firstNameColumn).setComponent(addButton);
        footerRow.getCell(lastNameColumn).setComponent(removeButton);

        // end-source-example
        grid.setId("dynamic-height");
        addButton.setId("dynamic-height-add");
        removeButton.setId("dynamic-height-remove");
        addCard("Assigning Data", "Dynamic Height", grid, addButton,
                removeButton);
    }

    // Selection Begin
    private void createSingleSelect() {
        Div messageDiv = new Div();
        // begin-source-example
        // source-example-heading: Grid Single Selection
        List<Person> personList = getItems();
        Grid<Person> grid = new Grid<>();
        grid.setItems(personList);

        grid.addColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getAge).setHeader("Age");

        grid.asSingleSelect().addValueChangeListener(event -> {
            String message = String.format("Selection changed from %s to %s",
                    event.getOldValue(), event.getValue());
            messageDiv.setText(message);
        });
        // end-source-example
        grid.setId("single-selection");
        messageDiv.setId("single-selection-message");
        addCard("Selection", "Grid Single Selection", grid, messageDiv);
    }

    private void createMultiSelect() {
        Div messageDiv = new Div();
        // begin-source-example
        // source-example-heading: Grid Multi Selection
        List<Person> personList = getItems();
        Grid<Person> grid = new Grid<>();
        grid.setItems(personList);

        grid.addColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getAge).setHeader("Age");

        grid.setSelectionMode(SelectionMode.MULTI);

        grid.asMultiSelect().addValueChangeListener(event -> {
            String message = String.format("Selection changed from %s to %s",
                    event.getOldValue(), event.getValue());
            messageDiv.setText(message);
        });

        // You can pre-select items
        grid.asMultiSelect().select(personList.get(0), personList.get(1));
        // end-source-example
        grid.setId("multi-selection");
        messageDiv.setId("multi-selection-message");
        addCard("Selection", "Grid Multi Selection", grid, messageDiv);
    }

    private void createProgrammaticSelect() {
        // begin-source-example
        // source-example-heading: Grid with Programmatic Selection
        PersonService personService = new PersonService();
        List<Person> personList = personService.fetchAll();

        H3 firstHeader = new H3("Grid with single select");
        Grid<Person> firstGrid = new Grid<>();
        firstGrid.setItems(personList);

        H3 secondHeader = new H3("Grid with multi select");
        Grid<Person> secondGrid = new Grid<>();
        secondGrid.setItems(personList);
        secondGrid.setSelectionMode(SelectionMode.MULTI);

        TextField filterField = new TextField();
        filterField.setValueChangeMode(ValueChangeMode.EAGER);
        filterField.addValueChangeListener(event -> {
            Optional<Person> foundPerson = personList.stream()
                    .filter(person -> person.getfirstName().toLowerCase()
                            .startsWith(event.getValue().toLowerCase()))
                    .findFirst();

            firstGrid.asSingleSelect().setValue(foundPerson.orElse(null));

            secondGrid.getSelectionModel().deselectAll();
            Set<Person> foundpersons = personList.stream()
                    .filter(person -> person.getfirstName().toLowerCase()
                            .startsWith(event.getValue().toLowerCase()))
                    .collect(Collectors.toSet());
            secondGrid.asMultiSelect().setValue(foundpersons);
        });

        firstGrid.addColumn(Person::getfirstName).setHeader("First name");
        firstGrid.addColumn(Person::getAge).setHeader("Age");

        secondGrid.addColumn(Person::getfirstName).setHeader("First name");
        secondGrid.addColumn(Person::getAge).setHeader("Age");

        NativeButton deselectBtn = new NativeButton("Deselect all");
        deselectBtn.addClickListener(
                event -> secondGrid.asMultiSelect().deselectAll());
        NativeButton selectAllBtn = new NativeButton("Select all");
        selectAllBtn.addClickListener(
                event -> ((GridMultiSelectionModel<Person>) secondGrid
                        .getSelectionModel()).selectAll());
        // end-source-example
        filterField.setId("programmatic-select-filter");
        firstHeader.setId("programmatic-select-first-header");
        firstGrid.setId("programmatic-select");
        secondHeader.setId("programmatic-select-second-header");
        secondGrid.setId("programmatic-select-second-filter");
        selectAllBtn.setId("programmatic-select-select-all");
        deselectBtn.setId("programmatic-select-deselect");
        addCard("Selection", "Grid with Programmatic Selection", filterField,
                firstHeader, firstGrid, secondHeader, secondGrid, selectAllBtn,
                deselectBtn);
    }

    // Sorting Begin
    private void createGridWithSortableColumns() {
        Div messageDiv = new Div();
        // begin-source-example
        // source-example-heading: Grid with sortable columns
        List<Person> personList = getItems();
        Grid<Person> grid = new Grid<>();
        grid.setItems(personList);
        grid.setSelectionMode(SelectionMode.NONE);

        grid.addColumn(Person::getfirstName, "First name")
                .setHeader("First name");
        grid.addColumn(Person::getLastName, "last name").setHeader("Last name");
        grid.addColumn(Person::getAge, "age").setHeader("Age");

        // addColumn is not Comparable so it uses toString method to sort the
        // column.
        grid.addColumn(TemplateRenderer.<Person> of(
                "<div>[[item.city]]<br><small>[[item.postalCode]]</small></div>")

        .withProperty("city", person -> person.getAddress().getCity())
                .withProperty("postalCode",
                        person -> person.getAddress().getPostalCode()),
                "city", "postalCode").setHeader("Address");

        Checkbox multiSort = new Checkbox("Multiple column sorting enabled");
        multiSort.addValueChangeListener(
                event -> grid.setMultiSort(event.getValue()));

        // you can set the sort order from server-side with the grid.sort method
        NativeButton invertAllSortings = new NativeButton(
                "Invert all sort directions", event -> {
                    List<GridSortOrder<Person>> newList = grid.getSortOrder()
                            .stream()
                            .map(order -> new GridSortOrder<>(order.getSorted(),
                                    order.getDirection().getOpposite()))
                            .collect(Collectors.toList());
                    grid.sort(newList);
                });

        NativeButton resetAllSortings = new NativeButton("Reset all sortings",
                event -> grid.sort(null));
        // end-source-example
        grid.setId("grid-sortable-columns");
        multiSort.setId("grid-multi-sort-toggle");
        invertAllSortings.setId("grid-sortable-columns-invert-sorting");
        resetAllSortings.setId("grid-sortable-columns-reset-sorting");
        messageDiv.setId("grid-sortable-columns-message");
        addCard("Sorting", "Grid with sortable columns", grid, multiSort,
                messageDiv, invertAllSortings, resetAllSortings);
    }

    // Filtering
    private void createGridWithTextFieldFilters() {
        // begin-source-example
        // source-example-heading: Using text fields for filtering items
        List<Person> personList = getItems();
        Grid<Person> grid = new Grid<>();
        ListDataProvider<Person> dataProvider = new ListDataProvider<>(
                personList);
        grid.setDataProvider(dataProvider);

        Grid.Column<Person> firstNameColumn = grid
                .addColumn(Person::getfirstName).setHeader("Name");
        Grid.Column<Person> ageColumn = grid.addColumn(Person::getAge)
                .setHeader("Age");
        Grid.Column<Person> cityColumn = grid
                .addColumn(person -> person.getAddress().getCity())
                .setHeader("City");
        Grid.Column<Person> postalCodeColumn = grid
                .addColumn(person -> person.getAddress().getPostalCode())
                .setHeader("Postal code");

        HeaderRow filterRow = grid.appendHeaderRow();
        // First filter
        TextField firstNameField = new TextField();
        firstNameField.addValueChangeListener(event -> dataProvider.addFilter(
                person -> StringUtils.containsIgnoreCase(person.getfirstName(),
                        firstNameField.getValue())));

        firstNameField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(firstNameColumn).setComponent(firstNameField);
        firstNameField.setSizeFull();
        firstNameField.setPlaceholder("Filter");

        // Second filter
        TextField ageField = new TextField();
        ageField.addValueChangeListener(event -> dataProvider
                .addFilter(person -> StringUtils.containsIgnoreCase(
                        String.valueOf(person.getAge()), ageField.getValue())));

        ageField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(ageColumn).setComponent(ageField);
        ageField.setSizeFull();
        ageField.setPlaceholder("Filter");

        // Third filter
        TextField cityField = new TextField();
        cityField.addValueChangeListener(event -> dataProvider
                .addFilter(person -> StringUtils.containsIgnoreCase(
                        person.getAddress().getCity(), cityField.getValue())));

        cityField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(cityColumn).setComponent(cityField);
        cityField.setSizeFull();
        cityField.setPlaceholder("Filter");

        // Fourth filter
        TextField postalCodeField = new TextField();
        postalCodeField.addValueChangeListener(
                event -> dataProvider.addFilter(person -> StringUtils
                        .containsIgnoreCase(person.getAddress().getPostalCode(),
                                postalCodeField.getValue())));

        postalCodeField.setValueChangeMode(ValueChangeMode.EAGER);

        filterRow.getCell(postalCodeColumn).setComponent(postalCodeField);
        postalCodeField.setSizeFull();
        postalCodeField.setPlaceholder("Filter");
        // end-source-example
        grid.setId("grid-with-filters");
        addCard("Filtering", "Using text fields for filtering items", grid);
    }

    // begin-source-example
    // source-example-heading: Using data type specific filtering
    private ComboBox<MaritalStatus> maritalStatus;
    private DatePicker birthDateField;

    private VerticalLayout createGridWithFilters() {
        VerticalLayout layout = new VerticalLayout();

        PersonService personService = new PersonService();
        List<Person> personList = personService.fetchAll();

        Grid<Person> grid = new Grid<>();
        ListDataProvider<Person> dataProvider = new ListDataProvider<>(
                personList);
        grid.setDataProvider(dataProvider);

        grid.addColumn(Person::getfirstName).setHeader("Name");
        grid.addColumn(Person::getAge).setHeader("Age");
        grid.addColumn(person -> person.getBirthdate()).setHeader("Birth date");
        grid.addColumn(person -> person.getAddress().getPostalCode())
                .setHeader("Postal Code");

        maritalStatus = new ComboBox<>("Filter by marital status: ");
        maritalStatus.setItems(MaritalStatus.values());
        birthDateField = new DatePicker("Filter by birth date: ");

        maritalStatus.addValueChangeListener(event -> {
            applyFilter(dataProvider);
        });

        birthDateField.addValueChangeListener(event -> {
            applyFilter(dataProvider);
        });

        layout.add(maritalStatus, birthDateField, grid);
        return layout;

    }

    private void applyFilter(ListDataProvider<Person> dataProvider) {
        dataProvider.clearFilters();
        if (birthDateField.getValue() != null)
            dataProvider.addFilter(person -> Objects
                    .equals(birthDateField.getValue(), person.getBirthdate()));
        if (maritalStatus.getValue() != null)
            dataProvider.addFilter(person -> maritalStatus.getValue() == person
                    .getMaritalStatus());
    }
    // end-source-example

    private void createGridWithDataTypeSpecificFilters() {
        VerticalLayout layout = createGridWithFilters();
        layout.setId("layout-with-filters");
        addCard("Filtering", "Using data type specific filtering", layout);

    }

    // Configuring Columns Begin
    private void createConfiguringColumns() {
        // begin-source-example
        // source-example-heading: Configuring columns

        List<Person> personList = getItems();
        // Providing a bean-type generates columns for all of it's properties
        Grid<Person> grid = new Grid<>(Person.class);

        // Property-names are automatically set as keys
        // You can remove undesired columns by using the key
        grid.removeColumnByKey("id");

        // It could be used to specify columns order
        grid.setColumns("firstName", "lastName", "age", "address",
                "phoneNumber");

        grid.setItems(personList);

        // Columns for sub-properties can be added easily
        grid.addColumn("address.postalCode");

        // end-source-example
        grid.setId("configuring-columns");
        addCard("Configuring columns", "Configuring columns", grid);
    }

    private void createManuallyDefiningColumns() {
        // begin-source-example
        // source-example-heading: Manually defining columns

        List<Person> personList = getItems();
        Grid<Person> grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.setItems(personList);

        Grid.Column<Person> idColumn = grid.addColumn(Person::getId)
                .setHeader("ID").setFlexGrow(0).setWidth("75px");

        // Combination of properties
        grid.addColumn(
                Person -> Person.getfirstName() + " " + Person.getLastName())
                .setHeader("Full name").setResizable(true);

        // Setting a column-key allows fetching the column later
        grid.addColumn(Person::getAge).setHeader("Age").setKey("age");
        grid.getColumnByKey("age").setResizable(true);

        Checkbox idColumnVisibility = new Checkbox(
                "Toggle visibility of the ID column");
        idColumnVisibility.addValueChangeListener(
                event -> idColumn.setVisible(!idColumn.isVisible()));

        Checkbox userReordering = new Checkbox(
                "Toggle user reordering of columns");
        userReordering.addValueChangeListener(event -> grid
                .setColumnReorderingAllowed(!grid.isColumnReorderingAllowed()));
        // end-source-example

        grid.setId("column-api-example");
        idColumnVisibility.setId("toggle-id-column-visibility");
        userReordering.setId("toggle-user-reordering");
        addCard("Configuring columns", "Manually defining columns", grid,
                new VerticalLayout(grid, idColumnVisibility, userReordering));
    }

    private void createFrozenColumns() {
        // begin-source-example
        // source-example-heading: Frozen column example
        List<Person> personList = getItems();
        H3 firstHeader = new H3("Freezing the selection column");
        Grid<Person> firstGrid = new Grid<>();

        firstGrid.setItems(personList);

        firstGrid.addColumn(Person::getId).setHeader("ID").setWidth("75px");
        firstGrid.addColumn(Person::getfirstName).setHeader("First name");
        firstGrid.addColumn(Person::getLastName).setHeader("Last name");
        firstGrid.addColumn(Person::getPhoneNumber).setHeader("Phone Number")
                .setWidth("200px");
        firstGrid.addColumn(Person::getAddress).setHeader("Adress")
                .setWidth("200px");
        firstGrid.addColumn(Person::getMaritalStatus)
                .setHeader("Marital Status").setWidth("200px");
        firstGrid.addColumn(Person::getBirthdate).setHeader("Birth Date")
                .setWidth("200px");

        firstGrid.setColumnReorderingAllowed(true);
        ((GridMultiSelectionModel<?>) firstGrid
                .setSelectionMode(Grid.SelectionMode.MULTI))
                        // Freezing the selection column only
                        .setSelectionColumnFrozen(true);

        H3 secondHeader = new H3("Freezing the data columns");
        Grid<Person> secondGrid = new Grid<>();
        secondGrid.setItems(personList);

        // Freezing any column
        secondGrid.addColumn(Person::getId).setHeader("ID").setWidth("75px")
                .setFrozen(true);
        secondGrid.addColumn(Person::getfirstName).setHeader("First name")
                .setFrozen(true);
        secondGrid.addColumn(Person::getLastName).setHeader("Last name")
                .setFrozen(true);
        secondGrid.addColumn(Person::getPhoneNumber).setHeader("Phone number")
                .setWidth("200px");
        secondGrid.addColumn(Person::getAddress).setHeader("Adress")
                .setWidth("200px");
        secondGrid.addColumn(Person::getMaritalStatus)
                .setHeader("Marital status").setWidth("200px");
        secondGrid.addColumn(Person::getBirthdate).setHeader("Birth date")
                .setWidth("200px");

        // end-source-example
        firstGrid.setId("frozen-column-first-grid");
        secondGrid.setId("frozen-column-first-grid");
        addCard("Configuring columns", "Frozen column example", firstHeader,
                firstGrid, secondHeader, secondGrid);
    }

    private void createColumnAlignment() {
        // begin-source-example
        // source-example-heading: Column alignment example
        List<Person> personList = getItems();
        Grid<Person> grid = new Grid<>();

        grid.setItems(personList);

        grid.addColumn(Person::getfirstName).setHeader("First name");

        // Setting a column-key allows fetching the column later
        grid.addColumn(Person::getAge).setHeader("Age").setKey("age");
        grid.getColumnByKey("age");

        RadioButtonGroup<ColumnTextAlign> alignments = new RadioButtonGroup<>();
        alignments.setItems(ColumnTextAlign.values());
        alignments.setLabel("Text alignment for the Age column");

        // ColumnTextAlign is a grid feature enum that is used to configure text
        // alignment inside columns.
        alignments.setValue(ColumnTextAlign.START);
        alignments.addValueChangeListener(event -> grid.getColumnByKey("age")
                .setTextAlign(event.getValue()));

        // end-source-example
        grid.setId("column-alignment-example");
        alignments.setId("column-alignment-example-alignments");
        addCard("Configuring columns", "Column alignment example", grid,
                alignments);
    }

    // Header and footer begin
    private void createHeaderAndFooter() {
        // begin-source-example
        // source-example-heading: Header and footer texts

        List<Person> personList = getItems();
        Grid<Person> grid = new Grid<>();
        grid.setItems(personList);

        grid.addColumn(Person::getfirstName).setHeader("First name")
                .setFooter("Total: " + personList.size() + " people");

        long averageOfAge = Math.round(personList.stream()
                .mapToInt(Person::getAge).average().orElse(0));

        grid.addColumn(Person::getAge).setHeader("Age")
                .setFooter("Average: " + averageOfAge);
        // end-source-example
        grid.setId("header-and-footer");
        addCard("Header and footer", "Header and footer texts", grid);
    }

    private void createColumnGrouping() {
        // begin-source-example
        // source-example-heading: Column Grouping

        int sum = 200;

        List<Benefit> benefitList = new ArrayList<>();
        benefitList.add(new Benefit(2017, sum, sum, sum, sum));
        benefitList.add(new Benefit(2018, sum += 10, sum, sum, sum));
        benefitList.add(new Benefit(2019, sum += 10, sum, sum, sum));
        benefitList.add(new Benefit(2020, sum += 10, sum, sum, sum));
        benefitList.add(new Benefit(2021, sum += 10, sum, sum, sum));

        Grid<Benefit> grid = new Grid<>();
        grid.setItems(benefitList);

        grid.addColumn(Benefit::getYear).setHeader("Year");
        // Setting the alignment of columns
        Grid.Column<Benefit> quarter1 = grid.addColumn(Benefit::getQuarter1, "")
                .setHeader("Quarter 1").setTextAlign(ColumnTextAlign.END);
        Grid.Column<Benefit> quarter2 = grid.addColumn(Benefit::getQuarter2, "")
                .setHeader("Quarter 2").setTextAlign(ColumnTextAlign.END);
        Grid.Column<Benefit> quarter3 = grid.addColumn(Benefit::getQuarter3, "")
                .setHeader("Quarter 3").setTextAlign(ColumnTextAlign.END);
        Grid.Column<Benefit> quarter4 = grid.addColumn(Benefit::getQuarter4, "")
                .setHeader("Quarter 4").setTextAlign(ColumnTextAlign.END);

        HeaderRow halfheaderRow = grid.prependHeaderRow();

        // Setting the alignment of only the header
        Div half1Header = new Div(new Span("Half 1"));
        half1Header.getStyle().set("text-align", "right");
        half1Header.setSizeFull();
        halfheaderRow.join(quarter1, quarter2).setComponent(half1Header);

        // Setting the alignment of only the header
        Div half2Header = new Div(new Span("Half 2"));
        half2Header.getStyle().set("text-align", "right");
        half2Header.setSizeFull();
        halfheaderRow.join(quarter3, quarter4).setComponent(half2Header);

        // Footers can be set with a similar API

        // end-source-example
        grid.setId("grid-with-header-and-footer");
        addCard("Header and footer", "Column Grouping", grid);
    }

    private void createHeaderAndFooterUsingComponents() {
        // begin-source-example
        // source-example-heading: Using components
        List<Person> personList = getItems();

        ListDataProvider<Person> dataProvider = DataProvider
                .ofCollection(personList);

        Grid<Person> grid = new Grid<>();
        grid.setDataProvider(dataProvider);

        Grid.Column<Person> nameColumn = grid.addColumn(Person::getfirstName)
                .setHeader(new Label("Name")).setComparator((p1, p2) -> p1
                        .getfirstName().compareToIgnoreCase(p2.getfirstName()));
        Column<Person> ageColumn = grid.addColumn(Person::getAge, "age")
                .setHeader(new Label("Age"));
        Column<Person> streetColumn = grid
                .addColumn(person -> person.getAddress().getCity())
                .setHeader(new Label("City"));

        Column<Person> postalCodeColumn = grid
                .addColumn(person -> person.getAddress().getPostalCode())
                .setHeader(new Label("Postal code"));

        // Create and combine the header
        HeaderRow topRow = grid.prependHeaderRow();
        HeaderCell buttonsCell = topRow.join(nameColumn, ageColumn,
                streetColumn, postalCodeColumn);

        // Create and add buttons
        Button lessThanTwentyYearsold = new Button("-20 years old", event -> {
            dataProvider.setFilter(person -> person.getAge() < 20);
        });

        Button twentyToForty = new Button("Between 20-40 years old", event -> {
            dataProvider.setFilter(
                    person -> (person.getAge() >= 20 && person.getAge() <= 40));
        });

        Button overForty = new Button("+40 years old", event -> {
            dataProvider.setFilter(person -> person.getAge() > 40);
        });

        HorizontalLayout filter = new HorizontalLayout(lessThanTwentyYearsold,
                twentyToForty, overForty);
        buttonsCell.setComponent(filter);

        grid.appendFooterRow().getCell(nameColumn).setComponent(
                new Label("Total: " + personList.size() + " people"));

        // end-source-example
        grid.setId("using-components");
        addCard("Header and footer", "Using components", grid);
    }

    // Formatting contents
    private void createFormattingText() {
        // begin-source-example
        // source-example-heading: Formatting text
        List<Item> itemList = new ArrayList<>();
        String str = "2016-03-04 11:30:40";
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        LocalDate localDate = LocalDate.parse(str, formatter);

        itemList.add(new Item("Car", 250, dateTime, localDate));
        itemList.add(new Item("Flower", 10, dateTime, localDate));
        itemList.add(new Item("Book", 210, dateTime, localDate));
        itemList.add(new Item("Games", 250, dateTime, localDate));

        Grid<Item> grid = new Grid<>();
        grid.setItems(itemList);

        grid.addColumn(Item::getName).setHeader("Name").setWidth("20px");

        // NumberRenderer to render numbers in general
        grid.addColumn(new NumberRenderer<>(Item::getPrice, "$ %(,.2f",
                Locale.US, "$ 0.00")).setHeader("Price");

        // LocalDateTimeRenderer for date and time
        grid.addColumn(new LocalDateTimeRenderer<>(Item::getPurchaseDate,
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT,
                        FormatStyle.MEDIUM)))
                .setHeader("Purchase date and time").setFlexGrow(2);

        // LocalDateRenderer for dates
        grid.addColumn(new LocalDateRenderer<>(Item::getEstimatedDeliveryDate,
                DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                .setHeader("Estimated delivery date");

        // end-source-example

        grid.setId("grid-formatting-contents");
        addCard("Formatting contents", "Formatting text", grid);
    }

    private void createHtmlTemplateRenderer() {
        // begin-source-example
        // source-example-heading: Grid with HTML template renderer
        List<Order> orderList = new ArrayList<>();

        String str = "2016-03-04 11:30:40";
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        LocalDate localDate = LocalDate.parse(str, formatter);

        orderList.add(new Order("Tshit", 2, 20, dateTime, localDate, "Mickael",
                new Address("12080", "Washington")));
        orderList.add(new Order("Pant", 2, 70, dateTime, localDate, "Peter",
                new Address("93849", "New York")));
        orderList.add(new Order("Bag", 1, 60, dateTime, localDate, "Samuel",
                new Address("86829", "New York")));

        Grid<Order> grid = new Grid<>();
        grid.setItems(orderList);

        grid.addColumn(Order::getName).setHeader("Buyer").setFlexGrow(1);

        NumberFormat moneyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        // You can also set complex objects directly. Internal properties of the
        // bean are accessible in the template.
        grid.addColumn(TemplateRenderer
                .<Order> of(
                        "<div>[[item.name]],[[item.price]] <br> purchased on: <small>[[item.purchasedate]]</small></div>")
                .withProperty("name", Order::getName)
                // NumberRenderer to render numbers in general
                .withProperty("price",
                        order -> moneyFormat.format(order.getPrice()))
                .withProperty("purchasedate",
                        order -> formatter.format(order.getPurchaseDate())))
                .setHeader("Purchase").setFlexGrow(6);

        grid.addColumn(
                TemplateRenderer
                        .<Order> of(
                                "<div>Estimated delivery date: <small>[[item.estimatedDeliveryDate]]<small> <br>to: <small>[[item.address.city]],[[item.address.postalCode]]</small> </div>")
                        .withProperty("estimatedDeliveryDate",
                                order -> formatter
                                        .format(order.getPurchaseDate()))
                        .withProperty("address", order -> order.getAddress()))
                .setHeader("Delivery").setFlexGrow(6);

        // end-source-example
        grid.setId("template-renderer");
        addCard("Formatting Contents", "Grid with HTML template renderer",
                grid);
    }

    // Using components begin
    private Grid<Person> createGridUsingComponent() {
        // begin-source-example
        // source-example-heading: Using Components
        List<Person> personList = getItems();
        Grid<Person> grid = new Grid<>();
        grid.setItems(personList);

        // Use the component constructor that accepts an item ->
        // new PersonComponent(Person person)
        grid.addComponentColumn(PersonComponent::new).setHeader("Person");

        // Or you can use an ordinary function to setup the component
        grid.addComponentColumn(item -> createRemoveButton(grid, item))
                .setHeader("Actions");

        grid.setSelectionMode(Grid.SelectionMode.NONE);
        return grid;
    }

    private Button createRemoveButton(Grid<Person> grid, Person item) {
        Button button = new Button("Remove", clickEvent -> {
            ListDataProvider<Person> dataProvider = (ListDataProvider<Person>) grid
                    .getDataProvider();
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        });
        return button;
    }

    // end-source-example

    private void createGridUsingComponentFilters() {
        Grid<Person> grid = createGridUsingComponent();
        grid.setId("using-components");
        addCard("Using Components", "Using Components", grid);
    }

    // Item details
    private void createGridWithItemDetails() {
        // begin-source-example
        // source-example-heading: Grid with item details
        List<Person> personList = getItems();
        H3 header = new H3("Clicking on a row will show more details");
        Grid<Person> grid = new Grid<>();

        grid.setItems(personList);

        grid.addColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getAge).setHeader("Age");

        grid.setSelectionMode(Grid.SelectionMode.NONE);

        // You can use any renderer for the item details. By default, the
        // details are opened and closed by clicking the rows.
        grid.setItemDetailsRenderer(TemplateRenderer
                .<Person> of(
                        "<div style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
                                + "<div>Hi! My name is <b>[[item.firstName]]!</b></div>"
                                + "<div><img style='height: 80px; width: 80px;' src='[[item.image]]'/></div>"
                                + "</div>")
                .withProperty("firstName", Person::getfirstName)
                .withProperty("lastname", Person::getLastName)
                .withProperty("address", Person::getAddress)
                .withProperty("image", Person::getImage)
                .withEventHandler("handleClick", person -> {
                    grid.getDataProvider().refreshItem(person);
                }));

        // end-source-example
        grid.setId("item-details");
        header.setId("item-details-header");
        addCard("Item details", "Grid with item details", header, grid);
    }

    private void createItemDetailsOpenedProgrammatically() {

        // begin-source-example
        // source-example-heading: Open details programmatically
        // Disable the default way of opening item details:
        List<Person> personList = getItems();
        H3 header = new H3("Clicking on buttons will show more details");
        Grid<Person> grid = new Grid<>();

        grid.setItems(personList);

        grid.addColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getAge).setHeader("Age");

        grid.setSelectionMode(Grid.SelectionMode.NONE);

        // You can use any renderer for the item details. By default, the
        // details are opened and closed by clicking the rows.
        grid.setItemDetailsRenderer(TemplateRenderer
                .<Person> of(
                        "<div class='custom-details' style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
                                + "<div>Hi! My name is <b>[[item.firstName]]!</b></div>"
                                + "</div>")
                .withProperty("firstName", Person::getfirstName)
                // This is now how we open the details
                .withEventHandler("handleClick", person -> {
                    grid.getDataProvider().refreshItem(person);
                }));

        // Disable the default way of opening item details:
        grid.setDetailsVisibleOnClick(false);

        grid.addColumn(new NativeButtonRenderer<>("Details", item -> grid
                .setDetailsVisible(item, !grid.isDetailsVisible(item))));

        // end-source-example
        grid.setId("open-details-programmatically");
        header.setId("open-details-programmatically-header");
        addCard("Item details", "Open details programmatically", header, grid);
    }

    // Context Menu begin
    private void createContextMenu() {
        TextArea message = new TextArea("");
        message.setHeight("100px");
        message.setReadOnly(true);
        // begin-source-example
        // source-example-heading: Using ContextMenu With Grid
        Grid<Person> grid = new Grid<>();
        grid.setItems(getItems());
        grid.addColumn(Person::getfirstName).setHeader("First name")
                .setId("First name");
        grid.addColumn(Person::getAge).setHeader("Age").setId("Age");
        grid.setSelectionMode(SelectionMode.MULTI);
        GridContextMenu<Person> contextMenu = new GridContextMenu<>(grid);
        contextMenu.addItem("Update",
                event -> event.getItem().ifPresent(person -> {
                    person.setfirstName(person.getfirstName() + " Updated");
                    DataProvider<Person, ?> dataProvider = grid
                            .getDataProvider();
                    dataProvider.refreshItem(person);
                }));
        contextMenu.addItem("Remove",
                event -> event.getItem().ifPresent(person -> {
                    ListDataProvider<Person> dataProvider = (ListDataProvider<Person>) grid
                            .getDataProvider();
                    dataProvider.getItems().remove(person);
                    dataProvider.refreshAll();
                }));
        contextMenu.addGridContextMenuOpenedListener(event -> message.setValue(
                String.format("Menu opened on\n Row: '%s'\n Column: '%s'",
                        event.getItem().map(Person::toString)
                                .orElse("-no item-"),
                        event.getColumnId().orElse("-no column-"))));
        // end-source-example
        grid.setId("context-menu-grid");
        addCard("Context Menu", "Using ContextMenu With Grid", grid,
                contextMenu, message);
    }

    // Context sub Menu begin
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void createContextSubMenu() {
        // begin-source-example
        // source-example-heading: Using Context Sub Menu With Grid
        Grid<Person> grid = new Grid<>();

        ListDataProvider<Person> dataProvider = DataProvider
                .ofCollection(new PersonService().fetchAll());

        grid.setDataProvider(dataProvider);

        grid.addColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getAge).setHeader("Age");
        GridContextMenu<Person> contextMenu = new GridContextMenu<>(grid);
        GridMenuItem<Person> insert = contextMenu.addItem("Insert");

        insert.getSubMenu().addItem("Insert a row above", event -> {
            Optional<Person> item = event.getItem();
            if (!item.isPresent()) {
                // no selected row
                return;
            }
            List<Person> items = (List) dataProvider.getItems();
            items.add(items.indexOf(item.get()), createItems(1).get(0));
            dataProvider.refreshAll();
        });
        insert.getSubMenu().add(new Hr());
        insert.getSubMenu().addItem("Insert a row below", event -> {
            Optional<Person> item = event.getItem();
            if (!item.isPresent()) {
                // no selected row
                return;
            }
            List<Person> items = (List) dataProvider.getItems();
            items.add(items.indexOf(item.get()) + 1, createItems(1).get(0));
            dataProvider.refreshAll();
        });
        // end-source-example
        grid.setId("context-menu-grid");
        addCard("Context Menu", "Using Context Sub Menu With Grid", grid,
                contextMenu);
    }

    // Click Listener Begin
    private void createClickListener() {
        Div message = new Div();
        message.setId("clicked-item");

        // begin-source-example
        // source-example-heading: Item Click Listener
        Grid<Person> grid = new Grid<>();
        grid.setItems(getItems());
        grid.addColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getAge).setHeader("Age");

        // Disable selection: will receive only click events instead
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        grid.addItemClickListener(
                event -> message.setText("Clicked Item: " + event.getItem()));

        // end-source-example
        grid.setId("item-click-listener");

        message.addClickListener(event -> message.setText(""));
        addCard("Click Listeners", "Item Click Listener", message, grid);
    }

    private void createDoubleClickListener() {
        Div message = new Div();
        message.setId("doubleclicked-item");

        // begin-source-example
        // source-example-heading: Item Double Click Listener
        Grid<Person> grid = new Grid<>();
        grid.setItems(getItems());
        grid.addColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getAge).setHeader("Age");

        grid.addItemDoubleClickListener(event -> message
                .setText("Double Clicked Item: " + event.getItem()));

        // end-source-example
        grid.setId("item-doubleclick-listener");
        message.addClickListener(event -> message.setText(""));
        addCard("Click Listeners", "Item Double Click Listener", message, grid);
    }

    // Grid Editor
    private void createBufferedEditor() {
        Div message = new Div();
        message.setId("buffered-editor-msg");

        // begin-source-example
        // source-example-heading: Editor in Buffered Mode
        Grid<Person> grid = new Grid<>();
        List<Person> persons = getItems();
        grid.setItems(persons);
        Grid.Column<Person> nameColumn = grid.addColumn(Person::getfirstName)
                .setHeader("First name");
        Grid.Column<Person> subscriberColumn = grid
                .addColumn(Person::isSubscriber).setHeader("Subscriber");

        Binder<Person> binder = new Binder<>(Person.class);
        Editor<Person> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        Div validationStatus = new Div();
        validationStatus.setId("validation");

        TextField field = new TextField();
        binder.forField(field)
                .withValidator(name -> !name.isEmpty(),
                        "Name should not be empty")
                .withStatusLabel(validationStatus).bind("firstName");
        nameColumn.setEditorComponent(field);

        Checkbox checkbox = new Checkbox();
        binder.bind(checkbox, "subscriber");
        subscriberColumn.setEditorComponent(checkbox);

        Collection<Button> editButtons = Collections
                .newSetFromMap(new WeakHashMap<>());

        Grid.Column<Person> editorColumn = grid.addComponentColumn(person -> {
            Button edit = new Button("Edit");
            edit.addClassName("edit");
            edit.addClickListener(e -> {
                editor.editItem(person);
                field.focus();
            });
            edit.setEnabled(!editor.isOpen());
            editButtons.add(edit);
            return edit;
        });

        editor.addOpenListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));

        Button save = new Button("Save", e -> editor.save());
        save.addClassName("save");

        Button cancel = new Button("Cancel", e -> editor.cancel());
        cancel.addClassName("cancel");

        // Add a keypress listener that listens for an escape key up event.
        // Note! some browsers return key as Escape and some as Esc
        grid.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");

        Div buttons = new Div(save, cancel);
        editorColumn.setEditorComponent(buttons);

        editor.addSaveListener(
                event -> message.setText(event.getItem().getfirstName() + ", "
                        + event.getItem().isSubscriber));

        // end-source-example
        grid.setId("buffered-editor");
        addCard("Grid Editor", "Editor in Buffered Mode", message,
                validationStatus, grid);
    }

    private void createNotBufferedEditor() {
        Div message = new Div();
        message.setId("not-buffered-editor-msg");

        // begin-source-example
        // source-example-heading: Editor in Not Buffered Mode
        Grid<Person> grid = new Grid<>();
        List<Person> persons = getItems();
        grid.setItems(persons);
        Grid.Column<Person> nameColumn = grid.addColumn(Person::getfirstName)
                .setHeader("First name");
        Grid.Column<Person> subscriberColumn = grid
                .addColumn(Person::isSubscriber).setHeader("Subscriber");

        Binder<Person> binder = new Binder<>(Person.class);
        grid.getEditor().setBinder(binder);

        TextField field = new TextField();
        // Close the editor in case of backward between components
        field.getElement()
                .addEventListener("keydown",
                        event -> grid.getEditor().closeEditor())
                .setFilter("event.key === 'Tab' && event.shiftKey");

        binder.bind(field, "firstName");
        nameColumn.setEditorComponent(field);

        Checkbox checkbox = new Checkbox();
        binder.bind(checkbox, "subscriber");
        subscriberColumn.setEditorComponent(checkbox);

        // Close the editor in case of forward navigation between
        checkbox.getElement()
                .addEventListener("keydown",
                        event -> grid.getEditor().closeEditor())
                .setFilter("event.key === 'Tab' && !event.shiftKey");

        grid.addItemDoubleClickListener(event -> {
            grid.getEditor().editItem(event.getItem());
            field.focus();
        });

        grid.addItemClickListener(event -> {
            if (binder.getBean() != null) {
                message.setText(binder.getBean().getfirstName() + ", "
                        + binder.getBean().isSubscriber());
            }
        });

        // end-source-example
        grid.setId("not-buffered-editor");
        addCard("Grid Editor", "Editor in Not Buffered Mode", message, grid);
    }

    private void createBufferedDynamicEditor() {
        Div message = new Div();
        message.setId("buffered-dynamic-editor-msg");

        // begin-source-example
        // source-example-heading: Dynamic Editor in Buffered Mode
        Grid<Person> grid = new Grid<>();
        List<Person> persons = new ArrayList<>();
        persons.addAll(createItems());
        grid.setItems(persons);

        Grid.Column<Person> nameColumn = grid.addColumn(Person::getfirstName)
                .setHeader("Name");
        Grid.Column<Person> subscriberColumn = grid
                .addColumn(Person::isSubscriber).setHeader("Subscriber");
        Grid.Column<Person> emailColumn = grid.addColumn(Person::getEmail)
                .setHeader("E-mail");

        Binder<Person> binder = new Binder<>(Person.class);
        Editor<Person> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        TextField field = new TextField();
        binder.bind(field, "firstName");
        nameColumn.setEditorComponent(field);

        Div validationStatus = new Div();
        validationStatus.getStyle().set("color", "red");
        validationStatus.setId("email-validation");

        Checkbox checkbox = new Checkbox();
        binder.bind(checkbox, "subscriber");
        subscriberColumn.setEditorComponent(checkbox);

        TextField emailField = new TextField();

        // When not a subscriber, we want to show a read-only text-field that
        // ignores whatever is set to it
        TextField readOnlyEmail = new TextField();
        readOnlyEmail.setValue("Not a subscriber");
        readOnlyEmail.setReadOnly(true);

        Runnable bindEmail = () -> binder.forField(emailField)
                .withValidator(new EmailValidator("Invalid email"))
                .withStatusLabel(validationStatus).bind("email");

        Runnable setEmail = () -> emailColumn.setEditorComponent(item -> {
            if (item.isSubscriber()) {
                bindEmail.run();
                return emailField;
            } else {
                return readOnlyEmail;
            }
        });

        // Sets the binding based on the Person bean state
        setEmail.run();

        // Refresh subscriber editor component when checkbox value is changed
        checkbox.addValueChangeListener(event -> {
            // Only updates from the client-side should be taken into account
            if (event.isFromClient()) {

                // When using buffered mode, the partial updates shouldn't be
                // propagated to the bean before the Save button is clicked, so
                // here we need to override the binding function to take the
                // checkbox state into consideration instead
                emailColumn.setEditorComponent(item -> {
                    if (checkbox.getValue()) {
                        bindEmail.run();
                        return emailField;
                    } else {
                        return readOnlyEmail;
                    }
                });
                grid.getEditor().refresh();
            }
        });

        Collection<Button> editButtons = Collections
                .newSetFromMap(new WeakHashMap<>());

        // Resets the binding function to use the bean state whenever the editor
        // is closed
        editor.addCloseListener(event -> {
            setEmail.run();
            editButtons.stream().forEach(button -> button.setEnabled(true));
        });

        Grid.Column<Person> editorColumn = grid.addComponentColumn(person -> {
            Button edit = new Button("Edit");
            edit.addClassName("edit");
            edit.addClickListener(e -> {
                editor.editItem(person);
                field.focus();
            });
            edit.setEnabled(!editor.isOpen());
            editButtons.add(edit);
            return edit;
        });

        editor.addOpenListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));

        Button save = new Button("Save", e -> editor.save());
        save.addClassName("save");

        Button cancel = new Button("Cancel", e -> editor.cancel());
        cancel.addClassName("cancel");

        // Add a keypress listener that listens for an escape key up event.
        // Note! some browsers return key as Escape and some as Esc
        grid.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");

        Div buttons = new Div(save, cancel);
        editorColumn.setEditorComponent(buttons);

        editor.addSaveListener(
                event -> message.setText(event.getItem().getfirstName() + ", "
                        + event.getItem().isSubscriber + ", "
                        + event.getItem().getEmail()));

        // end-source-example
        grid.setId("buffered-dynamic-editor");
        addCard("Grid Editor", "Dynamic Editor in Buffered Mode", message,
                validationStatus, grid);
    }

    private void createNotBufferedDynamicEditor() {
        Div message = new Div();
        message.setId("not-buffered-dynamic-editor-msg");

        // begin-source-example
        // source-example-heading: Dynamic Editor in Not Buffered Mode
        Grid<Person> grid = new Grid<>();
        List<Person> persons = new ArrayList<>();
        persons.addAll(createItems());
        grid.setItems(persons);

        Grid.Column<Person> nameColumn = grid.addColumn(Person::getfirstName)
                .setHeader("Name");
        Grid.Column<Person> subscriberColumn = grid
                .addColumn(Person::isSubscriber).setHeader("Subscriber");
        Grid.Column<Person> emailColumn = grid.addColumn(Person::getEmail)
                .setHeader("E-mail");

        Binder<Person> binder = new Binder<>(Person.class);
        Editor<Person> editor = grid.getEditor();
        editor.setBinder(binder);

        TextField field = new TextField();
        // Close the editor in case of backward navigation between components
        field.getElement()
                .addEventListener("keydown",
                        event -> grid.getEditor().closeEditor())
                .setFilter("event.key === 'Tab' && event.shiftKey");
        binder.bind(field, "firstName");
        nameColumn.setEditorComponent(field);

        Checkbox checkbox = new Checkbox();
        binder.bind(checkbox, "subscriber");
        subscriberColumn.setEditorComponent(checkbox);
        // Close the editor in case of forward navigation between components
        checkbox.getElement().addEventListener("keydown", event -> {
            if (!checkbox.getValue()) {
                grid.getEditor().closeEditor();
            }
        }).setFilter("event.key === 'Tab' && !event.shiftKey");

        TextField emailField = new TextField();
        emailColumn.setEditorComponent(item -> {
            if (item.isSubscriber()) {
                binder.bind(emailField, "email");
                return emailField;
            } else {
                return null;
            }
        });
        // Close the editor in case of forward navigation between components
        emailField.getElement()
                .addEventListener("keydown",
                        event -> grid.getEditor().closeEditor())
                .setFilter("event.key === 'Tab' && !event.shiftKey");

        grid.addItemDoubleClickListener(event -> {
            grid.getEditor().editItem(event.getItem());
            field.focus();
        });

        // Re-validates the editors every time something changes on the Binder.
        // This is needed for the email column to turn into nothing when the
        // checkbox is deselected, for example.
        binder.addValueChangeListener(event -> {
            grid.getEditor().refresh();
        });

        grid.addItemClickListener(event -> {
            if (binder.getBean() != null) {
                message.setText(binder.getBean().getfirstName() + ", "
                        + binder.getBean().isSubscriber() + ", "
                        + binder.getBean().getEmail());
            }
        });

        // end-source-example
        grid.setId("not-buffered-dynamic-editor");
        addCard("Grid Editor", "Dynamic Editor in Not Buffered Mode", message,
                grid);
    }

    // Drag and drop

    private List<Person> draggedItems;

    private void createRowReordering() {
        // begin-source-example
        // source-example-heading: Row Reordering

        Grid<Person> grid = new Grid<>(Person.class);
        grid.setColumns("firstName", "lastName", "phoneNumber");
        List<Person> items = new PersonService().fetch(0, 5);
        grid.setItems(items);

        grid.setRowsDraggable(true);
        grid.setDropMode(DropMode.BETWEEN);

        grid.addDragStartListener(
                event -> draggedItems = event.getDraggedItems());

        grid.addDropListener(event -> {
            if (event.getDropTargetRow().isPresent()) {
                items.remove(draggedItems.get(0));
                int dropIndex = items.indexOf(event.getDropTargetRow().get())
                        + (event.getDropLocation() == DropLocation.BELOW ? 1
                                : 0);
                items.add(dropIndex, draggedItems.get(0));
                grid.setItems(items);
                draggedItems = null;
            }
        });

        // end-source-example

        addCard("Drag and Drop", "Row Reordering", grid);
    }

    private void createDragRowsBetweenGrids() {
        // begin-source-example
        // source-example-heading: Drag Rows Between Grids

        ComponentEventListener<GridDragStartEvent<Person>> dragStartListener = event -> {
            draggedItems = event.getDraggedItems();
        };

        ComponentEventListener<GridDropEvent<Person>> dropListener = event -> {
            Grid<Person> sourceGrid = (Grid<Person>) event
                    .getDragSourceComponent().get();
            if (!(sourceGrid instanceof Grid) || draggedItems == null) {
                return;
            }

            // Remove the items from the source grid
            ListDataProvider<Person> sourceDataProvider = (ListDataProvider<Person>) sourceGrid
                    .getDataProvider();
            List<Person> sourceItems = new ArrayList<Person>(
                    sourceDataProvider.getItems());
            sourceItems.removeAll(draggedItems);
            sourceGrid.setItems(sourceItems);

            // Add dragged items to the target Grid
            Grid<Person> targetGrid = event.getComponent();
            ListDataProvider<Person> targetDataProvider = (ListDataProvider<Person>) targetGrid
                    .getDataProvider();
            List<Person> targetItems = new ArrayList<Person>(
                    targetDataProvider.getItems());

            int index = targetItems.size();
            if (event.getDropTargetRow().isPresent()) {
                index = targetItems.indexOf(event.getDropTargetRow().get())
                        + (event.getDropLocation() == DropLocation.BELOW ? 1
                                : 0);
            }

            targetItems.addAll(index, draggedItems);
            targetGrid.setItems(targetItems);

            draggedItems = null;
        };

        PersonService personService = new PersonService();

        Grid<Person> grid = new Grid<>(Person.class);
        grid.setItems(personService.fetch(0, 5));
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addDropListener(dropListener);
        grid.addDragStartListener(dragStartListener);
        grid.setRowsDraggable(true);
        grid.setDropMode(DropMode.BETWEEN);
        grid.setColumns("firstName", "lastName");

        Grid<Person> grid2 = new Grid<>(Person.class);
        grid2.setItems(personService.fetch(5, 1));
        grid2.setSelectionMode(Grid.SelectionMode.MULTI);
        grid2.addDropListener(dropListener);
        grid2.addDragStartListener(dragStartListener);
        grid2.setRowsDraggable(true);
        grid2.setDropMode(DropMode.BETWEEN);
        grid2.setColumns("firstName", "lastName");

        // end-source-example

        HorizontalLayout hl = new HorizontalLayout(grid, grid2);

        addCard("Drag and Drop", "Drag Rows Between Grids", hl);
    }

    private void createDropLocations() {

        // begin-source-example
        // source-example-heading: Drop Location
        PersonService personService = new PersonService();

        Grid<Person> grid = new Grid<>(Person.class);
        grid.setItems(personService.fetch(0, 50));

        grid.addDragStartListener(
                event -> draggedItems = event.getDraggedItems());
        grid.setColumns("firstName", "lastName", "phoneNumber");
        grid.setRowsDraggable(true);

        TreeData<Person> td = new TreeData<>();
        td.addItems(null, personService.fetch(51, 2));

        TreeGrid<Person> treeGrid = new TreeGrid<>();
        treeGrid.setDataProvider(new TreeDataProvider<Person>(td));
        /*
         * This enables dropping in between or on top of the existing grid rows.
         */
        treeGrid.setDropMode(DropMode.ON_TOP_OR_BETWEEN);
        treeGrid.addHierarchyColumn(Person::getfirstName)
                .setHeader("firstName");
        treeGrid.addColumn(Person::getLastName).setHeader("lastName");
        treeGrid.addColumn(Person::getPhoneNumber).setHeader("phoneNumber");
        treeGrid.addDropListener(event -> {
            // Remove the items from the source grid
            ListDataProvider<Person> sourceDataProvider = (ListDataProvider<Person>) grid
                    .getDataProvider();
            List<Person> sourceItems = new ArrayList<Person>(
                    sourceDataProvider.getItems());
            sourceItems.remove(draggedItems.get(0));
            grid.setItems(sourceItems);

            // Add the item to target grid
            Optional<Person> dropOverItem = event.getDropTargetRow();
            if (DropLocation.ON_TOP.equals(event.getDropLocation())) {
                td.addItem(dropOverItem.get(), draggedItems.get(0));
            } else {
                Person parent = td.getParent(dropOverItem.get());
                td.addItem(parent, draggedItems.get(0));

                List<Person> siblings = td.getChildren(parent);
                int dropIndex = td.getChildren(parent)
                        .indexOf(dropOverItem.get()) - 1
                        + (event.getDropLocation() == DropLocation.BELOW ? 1
                                : 0);
                td.moveAfterSibling(draggedItems.get(0),
                        siblings.get(dropIndex));
            }

            treeGrid.getDataProvider().refreshAll();

        });

        // end-source-example

        HorizontalLayout hl = new HorizontalLayout(grid, treeGrid);

        addCard("Drag and Drop", "Drop Location", hl);
    }

    private void createDragData() {

        // begin-source-example
        // source-example-heading: Custom Drag Data
        PersonService personService = new PersonService();

        Grid<Person> grid = new Grid<>(Person.class);
        List<Person> persons = new ArrayList<Person>(
                personService.fetch(0, 50));
        List<Person> availablePersons = new ArrayList<Person>(
                personService.fetch(0, 50));

        grid.setItems(persons);

        grid.addDragStartListener(
                event -> draggedItems = event.getDraggedItems());
        grid.setColumns("firstName", "lastName", "phoneNumber");
        grid.setRowsDraggable(true);
        grid.setDropMode(DropMode.BETWEEN);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addDragStartListener(
                event -> draggedItems = event.getDraggedItems());

        grid.setDragDataGenerator("text",
                person -> String.valueOf(person.getId()));

        grid.addDropListener(event -> {
            String text = event.getDataTransferText();
            // The drag data tokens are separated by line breaks
            Arrays.asList(text.split("\n")).forEach(idString -> {
                Optional<Person> matchOptional = availablePersons
                        .stream().filter(person -> String
                                .valueOf(person.getId()).equals(idString))
                        .findFirst();

                if (matchOptional.isPresent()) {
                    Person match = matchOptional.get();
                    Person clone = new Person(match.getId(),
                            match.getfirstName(), match.getLastName(),
                            match.getAge(), match.getAddress(),
                            match.getPhoneNumber());

                    int dropIndex = persons
                            .indexOf(event.getDropTargetRow().get())
                            + (event.getDropLocation() == DropLocation.BELOW ? 1
                                    : 0);
                    persons.add(dropIndex, clone);
                }
            });
            grid.getDataProvider().refreshAll();
        });

        grid.addDragEndListener(event -> {
            // Additional validation logic might be needed to verify
            // the drop was successful (on another browser window)
            // The demo just removes the items regardless of whether the drop
            // was legal.
            persons.removeAll(draggedItems);
            grid.getDataProvider().refreshAll();
        });

        // end-source-example

        addCard("Drag and Drop", "Custom Drag Data", grid);
    }

    private void createDragDropFilters() {
        // begin-source-example
        // source-example-heading: Drag and Drop Filters
        PersonService personService = new PersonService();

        TreeGrid<Person> grid = new TreeGrid<>();
        TreeData<Person> td = new TreeData<>();

        // Disallow dragging supervisors
        grid.setDragFilter(person -> td.getParent(person) != null);

        grid.setDropFilter(person -> {
            return td.getRootItems().contains(person) // Only support dropping
                                                      // on top of supervisors
                    && td.getChildren(person).size() < 4 // Don't allow more
                                                         // than 4 subordinates
                    && !td.getChildren(person).contains(draggedItems.get(0)); // Disallow
                                                                              // dropping
                                                                              // on
                                                                              // own
                                                                              // supervisor
        });

        grid.addHierarchyColumn(Person::getfirstName).setHeader("First name");
        grid.addColumn(Person::getLastName).setHeader("Last name");
        grid.addColumn(Person::getPhoneNumber).setHeader("Phone");
        grid.setRowsDraggable(true);

        td.addItems(null, personService.fetch(0, 3));
        td.addItems(td.getRootItems().get(0), personService.fetch(3, 3));
        td.addItems(td.getRootItems().get(1), personService.fetch(6, 2));

        grid.setDataProvider(new TreeDataProvider<Person>(td));
        grid.expand(td.getRootItems());

        grid.addDragStartListener(event -> {
            draggedItems = event.getDraggedItems();
            grid.setDropMode(DropMode.ON_TOP);

            // Refresh all related items to get the drop filter run for them
            td.getRootItems().forEach(supervisor -> {
                grid.getDataProvider().refreshItem(supervisor);

                td.getChildren(supervisor).forEach(subordinate -> {
                    grid.getDataProvider().refreshItem(subordinate);
                });
            });

        });

        grid.addDragEndListener(event -> grid.setDropMode(null));

        grid.addDropListener(event -> {
            Optional<Person> supervisor = event.getDropTargetRow();
            if (supervisor.isPresent()) {
                // Remove the item from it's previous supervisor's subordinates
                // list
                td.removeItem(draggedItems.get(0));

                // Avoid a tree-grid issue
                td.getRootItems().forEach(root -> {
                    if (td.getChildren(root).isEmpty()) {
                        grid.collapse(root);
                    }
                });

                // Add the item to the target supervisor's subordinates list
                td.addItem(supervisor.get(), draggedItems.get(0));

                grid.getDataProvider().refreshAll();
            }

        });

        // end-source-example
        VerticalLayout vl = new VerticalLayout(new Label("Supervisors"), grid);

        addCard("Drag and Drop", "Drag and Drop Filters", vl);
    }

    private List<Person> getItems() {
        // return
        // items.stream().map(Person::clone).collect(Collectors.toList());
        PersonService personService = new PersonService();
        List<Person> personList = personService.fetchAll();
        return personList;
    }

    private static List<Person> createItems() {
        return createItems(500);
    }

    private static List<Person> createItems(int number) {
        return new PeopleGenerator().generatePeople(number);
    }

    private static final String[] companies = new String[] { "Deomic",
            "Seumosis", "Feortor", "Deynazu", "Deynomia", "Leaudous",
            "Aembizio", "Rehyic", "Ceervous", "Ientralium", "Deicee", "Uenimbo",
            "Reetroyo", "Heemicy", "Aevinix", "Aemor", "Reoolane", "Keify",
            "Deisor", "Geradindu", "Teelembee", "Seysil", "Meutz", "Seubil",
            "Seylible", "Zeare", "Ceomescent", "Ceapill", "Heyperend",
            "Felinix", "Heyponte", "Veertent", "Ceentimbee", "Heomovu",
            "Deiante", "Meedido", "Perexo", "Neeotri", "Aecerile", "Meovive",
            "Ferontent", "Meultimbee", "Meisile", "Aerdonia", "Deiegen",
            "Meonible", "Oepe", "Aentemba", "Ceorore", "Peaner", "Seuril",
            "Oeutill", "Aenill", "Aezmie", "Ceheckmarks", "Aeponu",
            "Iesonoodle", "Ceogipe", "Beellescent", "Deuoveo", "Seufible",
            "Veicejo", "Aemphidel", "Ceryova", "Seucous", "Aeudoid", "Iediomba",
            "Deivagen", "Meicrofy", "Qeuinyx", "Seemizzy", "Eequzu", "Aebante",
            "Peedic", "Feelosis", "Meifix", "Pelanix", "Eeipe", "Deemoxo",
            "Eenity", "Peostil", "Ceogen", "Ienent", "Eeafix", "Sekicero",
            "Seugil", "Leunive", "Ceircumity", "Seupratri", "Eecofy", "Aentizz",
            "Peyrolium", "Ceryptonyx", "Seuposis", "Keayor", "Ceamiveo",
            "Neonise", "Kealith", "Aeloo", "Deelith", "Aequnu", "Peremose",
            "Qeuambo", "Tewimba", "Meanuta", "Veiva", "Veenity", "Eepimia",
            "Ueberore", "Mealible", "Ceonose", "Peortill", "Meidile", "Leupill",
            "Aeginoodle", "Seurosis", "Veerize", "Reedo", "Beiocy", "Geeodel",
            "Pearadel", "Yeajo", "Geenive", "Aeutonix", "Terimbu", "Seynend",
            "Ceedescent", "Ceanise", "Ceontranti", "Seuperoid", "Ueltradoo",
            "Veivity", "Mearil", "Peolyive", "Cealcose", "Leeenix", "Gearore",
            "Teaveo", "Seocinix", "Aestromba", "Meetanu", "Zeoodeo",
            "Iensulill", "Leaveo", "Peodible", "Meegatz", "Eesend", "Aevamba",
            "Veooloo", "Oectombo", "Neymba", "Qeuasinoodle", "Eexise", "Seusor",
            "Teenoid", "Seyill", "Pealeoveo", "Geefy", "Beonill", "Peerosis",
            "Teransise", "Aeurive", "Beinoodle", "Aerchile", "Ceolent",
            "Perosaria", "Meaxidoo", "Feinile", "Deemilane", "Leocill",
            "Deudel", "Aenimil", "Deominose", "Perondo", "Deifity", "Peeridoo",
            "Jeaxo", "Feafy", "Beefy", "Deolible", "Heydrombu", "Ienfratz",
            "Sekyic", "Meyil", "Ienterer", "Eexecure", "Feoril", "Seymist",
            "Peixope", "Aelbent", "Oemninoodle", "Uenose", "Secimbo", "Beovic",
            "Fealcoid", "Perotope", "Yeozz", "Aeicero", "Aelicy", "Eelectrombu",
            "Ceoracee", "Kewivu", "Weikiyo", "Meeevee", "Eeurodel", "Yeakitude",
            "Oeyovee", "Ceisic", "Terufix", "Meistijo", "Iedeofix", "Sekazu" };

    // begin-source-example
    // source-example-heading: Grid Basic Features Demo

    /**
     * Example Object for Basic Features Demo
     */
    public static class YearlyBudgetInfo {
        BigDecimal firstHalf;
        BigDecimal secondHalf;

        public YearlyBudgetInfo(BigDecimal firstHalf, BigDecimal secondHalf) {
            this.firstHalf = firstHalf;
            this.secondHalf = secondHalf;
        }

        public BigDecimal getFirstHalf() {
            return firstHalf;
        }

        public void setFirstHalf(BigDecimal firstHalf) {
            this.firstHalf = firstHalf;
        }

        public BigDecimal getSecondHalf() {
            return secondHalf;
        }

        public void setSecondHalf(BigDecimal secondHalf) {
            this.secondHalf = secondHalf;
        }
    }

    /**
     * Example Object for Basic Features Demo
     */
    public static class CompanyBudgetHistory {
        String company;
        Map<Integer, YearlyBudgetInfo> budgetHistory;

        public CompanyBudgetHistory(String company,
                Map<Integer, YearlyBudgetInfo> budgetHistory) {
            this.company = company;
            this.budgetHistory = budgetHistory;
        }

        public String getCompany() {
            return company;
        }

        public BigDecimal getFirstHalfOfYear(int year) {
            if (!budgetHistory.containsKey(year)) {
                return null;
            }
            return budgetHistory.get(year).getFirstHalf();
        }

        public BigDecimal getSecondHalfOfYear(int year) {
            if (!budgetHistory.containsKey(year)) {
                return null;
            }
            return budgetHistory.get(year).getSecondHalf();
        }

        public static ListDataProvider<CompanyBudgetHistory> getBudgetDataProvider(
                final int baseYear, final int numYears) {
            Collection<CompanyBudgetHistory> companyBudgetHistories = new ArrayList<>();

            for (String company : companies) {
                Map<Integer, YearlyBudgetInfo> budgetHistory = new HashMap<>();
                for (int year = baseYear; year < baseYear + numYears; year++) {
                    YearlyBudgetInfo budgetInfo = new YearlyBudgetInfo(
                            getRandomBigDecimal(), getRandomBigDecimal());
                    budgetHistory.put(year, budgetInfo);
                }
                companyBudgetHistories
                        .add(new CompanyBudgetHistory(company, budgetHistory));
            }
            return new ListDataProvider<>(companyBudgetHistories);
        }

        public static BigDecimal getRandomBigDecimal() {
            return new BigDecimal(
                    100 + Math.random() * 100 + Math.random() * 10000);
        }
    }
    // end-source-example
}