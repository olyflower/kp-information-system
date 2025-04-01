import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class Main extends JFrame {

    private final JTabbedPane tabbedPane;
    private final JButton addButton;
    private final JButton deleteButton;

    public Main() {
        setTitle("Міський водопровід");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        addButton = new JButton("Додати дані в базу");
        deleteButton = new JButton("Видалити дані з бази");
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        JPanel requestsTabPanel = createTabPanel();
        JPanel billsTabPanel = createTabPanel();
        JPanel suppliersTabPanel = createTabPanel();
        JPanel teamsTabPanel = createTabPanel();
        JPanel clientsTabPanel = createTabPanel();
        JPanel dispatchersTabPanel = createTabPanel();
        JPanel zoneTabPanel = createTabPanel();


        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Заявки", requestsTabPanel);
        tabbedPane.addTab("Розрахунки", billsTabPanel);
        tabbedPane.addTab("Постачальники", suppliersTabPanel);
        tabbedPane.addTab("Бригади", teamsTabPanel);
        tabbedPane.addTab("Клієнти", clientsTabPanel);
        tabbedPane.addTab("Диспетчери", dispatchersTabPanel);
        tabbedPane.addTab("Зони обслуговування", zoneTabPanel);

        tabbedPane.addChangeListener(_ -> updateButtonActions());

        loadData();

        JButton exitButton = new JButton("Вихід");
        exitButton.addActionListener(_ -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Ви впевнені, що хочете вийти?", "Підтвердження виходу", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
        add(exitButton, BorderLayout.SOUTH);

        addButton.addActionListener(_ -> addRequest());
        deleteButton.addActionListener(_ -> deleteRequest());
    }

    private JPanel createTabPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("", SwingConstants.CENTER);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private void loadData() {
        showZones();
        showDispatchers();
        showClients();
        showTeams();
        showSuppliers();
        showBills();
        showRequests();
    }

    private void updateButtonActions() {
        String tabName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        if (addButton.getActionListeners().length > 0) {
            addButton.removeActionListener(addButton.getActionListeners()[0]);
        }
        if (deleteButton.getActionListeners().length > 0) {
            deleteButton.removeActionListener(deleteButton.getActionListeners()[0]);
        }

        switch (tabName) {
            case "Заявки":
                showRequests();
                addButton.addActionListener(_ -> addRequest());
                deleteButton.addActionListener(_ -> deleteRequest());
                break;
            case "Розрахунки":
                showBills();
                break;
            case "Постачальники":
                showSuppliers();
                break;
            case "Бригади":
                showTeams();
                break;
            case "Клієнти":
                showClients();
                addButton.addActionListener(_ -> addClient());
                deleteButton.addActionListener(_ -> deleteClient());
                break;
            case "Диспетчери":
                showDispatchers();
                addButton.addActionListener(_ -> addDispatcher());
                break;
            case "Зони обслуговування":
                showZones();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Помилка");
                break;
        }
    }

    private JTable currentTable;

    /**
     * Загальний метод для оновлення графічного інтерфейсу таблиці
     */
    private void updateTableUI(DefaultTableModel tableModel) {
        JPanel currentTabPanel = (JPanel) tabbedPane.getSelectedComponent();
        currentTabPanel.removeAll();

        currentTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(currentTable);
        currentTabPanel.setLayout(new BorderLayout());
        currentTabPanel.add(scrollPane, BorderLayout.CENTER);

        currentTabPanel.revalidate();
        currentTabPanel.repaint();
    }

    /**
     * Формує HashMap, де ключем є ID клієнта, а значенням — сам об'єкт клієнта
     */
    private Map<Integer, Client> createClientMap(List<Client> clientsList) {
        Map<Integer, Client> clientMap = new HashMap<>();
        for (Client client : clientsList) {
            clientMap.put(client.getClientId(), client);
        }
        return clientMap;
    }


    /**
     * Відображення даних
     */

    private void showRequests() {
        List<Request> requestsList = DatabaseConnection.getAllRequests();
        List<Client> clientsList = DatabaseConnection.getAllClients();
        List<Dispatcher> dispatchersList = DatabaseConnection.getAllDispatchers();

        Map<Integer, Client> clientMap = createClientMap(clientsList);

        Map<Integer, Dispatcher> dispatchersMap = new HashMap<>();
        for (Dispatcher dispatcher : dispatchersList) {
            dispatchersMap.put(dispatcher.getDispatcherId(), dispatcher);
        }

        String[] columnNames = {"ID заявки", "Клієнт", "Диспетчер", "Дата створення", "Опис", "Пріоритет", "Статус"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Request request : requestsList) {
            int clientId = request.getClientId();
            int dispatcherId = request.getDispatcherId();

            String clientSurname = clientMap.containsKey(clientId) ? clientMap.get(clientId).getSurname() : "Немає";
            String dispatcherSurname = dispatchersMap.containsKey(dispatcherId) ? dispatchersMap.get(dispatcherId).getSurname() : "Немає";
            String createdAtString = request.getCreatedAt() != null ? request.getCreatedAt().toString() : "Невідомо";

            tableModel.addRow(new Object[]{
                    request.getRequestId(),
                    clientSurname,
                    dispatcherSurname,
                    createdAtString,
                    request.getDescription(),
                    request.getPriority(),
                    request.getStatus()
            });
        }

        updateTableUI(tableModel);
    }


    private void showBills() {
        List<Bill> billsList = DatabaseConnection.getAllBills();
        List<Client> clientsList = DatabaseConnection.getAllClients();
        List<Incident> incidentsList = DatabaseConnection.getAllIncidents();
        List<Request> requestsList = DatabaseConnection.getAllRequests();

        Map<Integer, Client> clientMap = createClientMap(clientsList);

        Map<Integer, String> incidentToDescriptionMap = new HashMap<>();
        Map<Integer, Integer> incidentToClientMap = new HashMap<>();
        for (Incident incident : incidentsList) {
            incidentToDescriptionMap.put(incident.getIncidentId(), incident.getDescription());
            int requestId = incident.getRequestId();
            for (Request request : requestsList) {
                if (request.getRequestId() == requestId) {
                    incidentToClientMap.put(incident.getIncidentId(), request.getClientId());
                    break;
                }
            }
        }

        String[] columnNames = {"ID рахунка", "Опис інциденту", "Клієнт", "Назва", "Сума", "Дата створення", "Дата оплати", "Статус"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Bill bill : billsList) {
            int incidentId = bill.getIncidentId();
            String incidentDescription = incidentToDescriptionMap.getOrDefault(incidentId, "Немає");

            int clientId = incidentToClientMap.getOrDefault(incidentId, -1);
            String clientSurname = clientMap.containsKey(clientId) ? clientMap.get(clientId).getSurname() : "Немає";

            String dateBillString = bill.getDateBill() != null ? bill.getDateBill().toString() : "Невідомо";
            String datePaymentString = bill.getDatePayment() != null ? bill.getDatePayment().toString() : "Невідомо";

            tableModel.addRow(new Object[]{
                    bill.getBillId(),
                    incidentDescription,
                    clientSurname,
                    bill.getBillName(),
                    bill.getAmount(),
                    dateBillString,
                    datePaymentString,
                    bill.getStatus()
            });
        }

        updateTableUI(tableModel);
    }


    private void showClients() {
        List<Client> clientsList = DatabaseConnection.getAllClients();
        List<ServiceZone> serviceZonesList = DatabaseConnection.getAllServiceZones();

        Map<Integer, String> serviceZoneMap = new HashMap<>();
        for (ServiceZone zone : serviceZonesList) {
            serviceZoneMap.put(zone.getServiceZoneId(), zone.getName());
        }
        String[] columnNames = {"ID клієнта", "Зона обслуговування", "Ім'я", "Прізвище", "Адреса", "Електрона пошта", "Номер телефону", "Дата реєстрації"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Client client : clientsList) {
            String serviceZone = serviceZoneMap.getOrDefault(client.getServiceZoneId(), "Немає");

            String registrationDateString = client.getRegistrationDate() != null ? client.getRegistrationDate().toString() : "Невідомо";

            tableModel.addRow(new Object[]{
                    client.getClientId(),
                    serviceZone,
                    client.getName(),
                    client.getSurname(),
                    client.getAddress(),
                    client.getPhoneNumber(),
                    client.getEmail(),
                    registrationDateString
            });
        }

        updateTableUI(tableModel);
    }

    private void showDispatchers() {
        List<Dispatcher> dispatchersList = DatabaseConnection.getAllDispatchers();
        String[] columnNames = {"ID диспетчера", "Ім'я", "Прізвище", "Електрона пошта", "Номер телефону"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Dispatcher dispatcher : dispatchersList) {
            tableModel.addRow(new Object[]{
                    dispatcher.getDispatcherId(),
                    dispatcher.getName(),
                    dispatcher.getSurname(),
                    dispatcher.getEmail(),
                    dispatcher.getPhoneNumber()
            });
        }

        updateTableUI(tableModel);
    }

    private void showTeams() {
        List<Team> teams = DatabaseConnection.getAllTeams();
        String[] columnNames = {"ID Бригади", "Спеціалізація", "Статус"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Team team : teams) {
            tableModel.addRow(new Object[]{
                    team.getTeamId(),
                    team.getSpecialization(),
                    team.getStatus()
            });
        }

        updateTableUI(tableModel);
    }

    private void showSuppliers() {
        List<Supplier> suppliers = DatabaseConnection.getAllSuppliers();
        String[] columnNames = {"ID Постачальника", "Назва", "Номер телефону", "Електрона пошта"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        for (Supplier supplier : suppliers) {
            tableModel.addRow(new Object[]{
                    supplier.getSupplierId(),
                    supplier.getName(),
                    supplier.getPhoneNumber(),
                    supplier.getEmail(),
            });
        }

        updateTableUI(tableModel);

        currentTable.getColumnModel().getColumn(0).setCellEditor(null);

        currentTable.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();

                if (column != 0) {
                    String name = (String) currentTable.getValueAt(row, 1);
                    String phoneNumber = (String) currentTable.getValueAt(row, 2);
                    String email = (String) currentTable.getValueAt(row, 3);

                    Supplier updatedSupplier = new Supplier(
                            (int) currentTable.getValueAt(row, 0),
                            name,
                            phoneNumber,
                            email
                    );

                    DatabaseConnection.updateSupplier(updatedSupplier);
                }
            }
        });
    }

    private void showZones() {
        List<ServiceZone> zonesList = DatabaseConnection.getAllServiceZones();
        String[] columnNames = {"ID зони обслуговування", "Назва"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        for (ServiceZone serviceZone : zonesList) {
            tableModel.addRow(new Object[]{
                    serviceZone.getServiceZoneId(),
                    serviceZone.getName()
            });
        }

        updateTableUI(tableModel);

        currentTable.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (column != 0) {
                    String name = (String) currentTable.getValueAt(row, 1);

                    ServiceZone updatedZone = new ServiceZone(
                            (int) currentTable.getValueAt(row, 0),
                            name
                    );

                    DatabaseConnection.updateServiceZone(updatedZone);
                }
            }
        });
    }


    /**
     * Додавання даних
     */

    private void addRequest() {
        List<Client> clientsList = DatabaseConnection.getAllClients();
        List<Dispatcher> dispatchersList = DatabaseConnection.getAllDispatchers();

        if (clientsList.isEmpty() || dispatchersList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Немає доступних клієнтів або диспетчерів.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(0, 2));
        JComboBox<Integer> clientComboBox = new JComboBox<>();
        JComboBox<Integer> dispatcherComboBox = new JComboBox<>();

        for (Client client : clientsList) {
            clientComboBox.addItem(client.getClientId());
        }
        for (Dispatcher dispatcher : dispatchersList) {
            dispatcherComboBox.addItem(dispatcher.getDispatcherId());
        }

        JDateChooser dateChooser = new JDateChooser();
        JTextField descriptionField = new JTextField();
        JTextField priorityField = new JTextField();
        JTextField statusField = new JTextField();

        panel.add(new JLabel("Клієнт:"));
        panel.add(clientComboBox);
        panel.add(new JLabel("Диспетчер:"));
        panel.add(dispatcherComboBox);
        panel.add(new JLabel("Дата створення:"));
        panel.add(dateChooser);
        panel.add(new JLabel("Опис:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Пріоритет:"));
        panel.add(priorityField);
        panel.add(new JLabel("Статус:"));
        panel.add(statusField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Додавання заявки", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Integer clientId = (Integer) clientComboBox.getSelectedItem();
                Integer dispatcherId = (Integer) dispatcherComboBox.getSelectedItem();

                if (clientId == null || dispatcherId == null) {
                    JOptionPane.showMessageDialog(this, "Необхідно вибрати клієнта та диспетчера.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                java.util.Date selectedDate = dateChooser.getDate();
                LocalDate createdAt = (selectedDate != null)
                        ? selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        : LocalDate.now();

                String description = descriptionField.getText().trim();
                String priority = priorityField.getText().trim();
                String status = statusField.getText().trim();

                if (description.isEmpty() || priority.isEmpty() || status.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Всі поля повинні бути заповнені.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Request newRequest = new Request(clientId, dispatcherId, createdAt, description, priority, status);
                boolean success = DatabaseConnection.addRequest(newRequest);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Заявку успішно додано.");
                    showRequests();
                } else {
                    JOptionPane.showMessageDialog(this, "Помилка при додаванні заявки.", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Помилка при обробці даних: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void addClient() {
        List<ServiceZone> zonesList = DatabaseConnection.getAllServiceZones();
        if (zonesList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Немає доступних зон обслуговування.", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JPanel panel = new JPanel(new GridLayout(0, 2));
        JComboBox<ServiceZone> zoneComboBox = new JComboBox<>();

        for (ServiceZone serviceZone : zonesList) {
            zoneComboBox.addItem(serviceZone);
        }

        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneNumberField = new JTextField();
        JTextField emailField = new JTextField();
        JDateChooser registrationDateChooser = new JDateChooser();

        panel.add(new JLabel("Зона обслуговування:"));
        panel.add(zoneComboBox);
        panel.add(new JLabel("Ім'я:"));
        panel.add(nameField);
        panel.add(new JLabel("Прізвище:"));
        panel.add(surnameField);
        panel.add(new JLabel("Адреса:"));
        panel.add(addressField);
        panel.add(new JLabel("Номер телефону:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Електронна пошта:"));
        panel.add(emailField);
        panel.add(new JLabel("Дата реєстрації:"));
        panel.add(registrationDateChooser);

        int option = JOptionPane.showConfirmDialog(this, panel, "Додавання клієнта", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String surname = surnameField.getText().trim();
                String address = addressField.getText().trim();
                String phoneNumber = phoneNumberField.getText().trim();
                String email = emailField.getText().trim();

                ServiceZone selectedZone = (ServiceZone) zoneComboBox.getSelectedItem();
                if (selectedZone == null) {
                    JOptionPane.showMessageDialog(this, "Необхідно вибрати зону обслуговування.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                java.util.Date selectedDate = registrationDateChooser.getDate();
                LocalDate registrationDate = (selectedDate != null)
                        ? selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        : LocalDate.now();

                if (name.isEmpty() || surname.isEmpty() || address.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Всі поля повинні бути заповнені.", "Помилка", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Client newClient = new Client(
                        selectedZone.getServiceZoneId(),
                        name,
                        surname,
                        address,
                        phoneNumber,
                        email,
                        registrationDate
                );

                boolean success = DatabaseConnection.addClient(newClient);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Клієнта успішно додано.");
                    showClients();
                } else {
                    JOptionPane.showMessageDialog(this, "Помилка при додаванні клієнта.", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Помилка при додаванні клієнта: " + e.getMessage(), "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addDispatcher() {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneNumberField = new JTextField();

        panel.add(new JLabel("Ім'я:"));
        panel.add(nameField);
        panel.add(new JLabel("Прізвище:"));
        panel.add(surnameField);
        panel.add(new JLabel("Електрона пошта:"));
        panel.add(emailField);
        panel.add(new JLabel("Номер телефону:"));
        panel.add(phoneNumberField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Додавання диспетчера", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            String phoneNumber = phoneNumberField.getText();

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Всі поля повинні бути заповнені.");
            } else {
                Dispatcher newDispatcher = new Dispatcher(name, surname, email, phoneNumber);
                boolean success = DatabaseConnection.addDispatcher(newDispatcher);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Диспетчера успішно додано.");
                    showDispatchers();
                } else {
                    JOptionPane.showMessageDialog(this, "Помилка при додаванні диспетчера.");
                }
            }
        }
    }


    /**
     * Функція для підтвердження видалення
     */
    private boolean confirmDeletion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Ви впевнені, що хочете видалити вибраний запис?",
                "Підтвердження видалення",
                JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }

    /**
     * Видалення даних
     */
    private void deleteRequest() {
        int selectedRow = currentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Виберіть заявку для видалення!", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int requestId = (int) currentTable.getValueAt(selectedRow, 0);

        if (confirmDeletion()) {
            boolean success = DatabaseConnection.deleteRequest(requestId);
            JOptionPane.showMessageDialog(this, success ? "Заявку успішно видалено." : "Помилка при видаленні заявки.",
                    "Результат", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (success) showRequests();
        }
    }

    private void deleteClient() {
        int selectedRow = currentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Виберіть клієнта для видалення!", "Помилка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int clientId = (int) currentTable.getValueAt(selectedRow, 0);
        if (confirmDeletion()) {
            boolean success = DatabaseConnection.deleteClient(clientId);
            JOptionPane.showMessageDialog(this, success ? "Клієнта успішно видалено." : "Помилка при видаленні клієнта.",
                    "Результат", success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
            if (success) showClients();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}
